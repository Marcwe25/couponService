package marcwe.couponService;

import java.io.Serializable;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import webBeans.*;
import cpCore.CouponException;
import cpCore.beans.*;
import facade.AdminFacade;
import other.ResponseUtility;
import other.ListUtility;

/**
 * @author Marc Weiss
 *
 */
@Path("admin")
public class AdminRessource implements Serializable {
	private static final Logger log4j = LogManager.getLogger("cpFileLogger");
	private static final long serialVersionUID = -2269100535011585728L;
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	public AdminRessource() {
	}

	/**
	 * @return an AdminFacade associated with a session in the container
	 */
	private AdminFacade adminFacade(){
		AdminFacade af = null;
		try {
			af = (AdminFacade) request.getSession().getAttribute("facade");
		} catch (ClassCastException e) {
			e.printStackTrace();
			return null;
		}
		return af;

	}
	
	
	/**
	 * @return Response indicating if a facade exist for the current session
	 */
	@GET
	public Response getIt() {
		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null) {
			return ResponseUtility.badLogin("admin");
		} else {
			return ResponseUtility.myResponse("admin", Status.OK);
		}
	}

	/**
	 * return all coupon in database
	 * @return all webcoupons
	 */
	@GET
	@Path("/coupon")
	public Response getAllWebCoupon(){
		// Getting facade
		AdminFacade fa;
		fa = adminFacade();
		if (fa == null)
			return ResponseUtility.badLogin("admin");

		// Getting collection of webCoupon
		Collection<Coupon> lc;
		try {
			lc = fa.getAllCoupon();
		} catch (CouponException e) {
			return ResponseUtility.myResponse("admin" + "-updateCoupon" + " unknown ", Status.RESET_CONTENT);
		}
		Collection<WebCoupon> lwc = ListUtility.getWebCouponsList(lc);

		// returning custom response with GenericEntity class
		log4j.trace("returned list of coupon");
		GenericEntity<Collection<WebCoupon>> entity = new GenericEntity<Collection<WebCoupon>>(lwc) {
		};
		return Response.ok(entity, MediaType.APPLICATION_JSON).status(Status.ACCEPTED).build();

	}

	/**
	 * Create a coupon in database with state provided by the parameter
	 * @param webCoupon
	 * @return Response indicating the result of creating a coupon
	 * @throws CouponException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/coupon")
	public Response createCoupon(WebCoupon webCoupon){
		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null) {
			return ResponseUtility.badLogin("admin");
		}
		Coupon editedCoupon = webCoupon.getCoupon();
		// checking required data validity
		if (!editedCoupon.containSuffisantData()) {
			return ResponseUtility.myResponse("admin" + "-createCoupon", "incomplete", Status.ACCEPTED);
		}
		// checking if duplicate
		try {
			if (fa.duplicateCoupon(editedCoupon.getTitle())) {
				return ResponseUtility.myResponse("admin" + "-createCoupon", "duplicate", Status.ACCEPTED);
			}
		} catch (CouponException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// creating coupon
		Long id = -1l;
		try {
			id = fa.createCoupon(editedCoupon);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtility.myResponse("admin" + "-createCoupon", "unknownError", Status.ACCEPTED);
		}
		return ResponseUtility.myResponse("admin" + "-createCoupon id:" + Long.toString(id), Long.toString(id), Status.CREATED);
	}

	/**
	 * Update coupon in database based on the state provided by the parameter
	 * Missing fields are completed with previously existing state in the database
	 * or aborted if unsuccessful
	 * @param webCoupon
	 * @return Response indicating the result of updating a Coupon
	 * or the coupon it self if succeed
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/coupon")
	public Response updateCoupon(WebCoupon webCoupon){
		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null) {
			return ResponseUtility.badLogin("admin");
		}
		Coupon editedCoupon = webCoupon.getCoupon();
		try {
			editedCoupon = fa.updatableCoupon(editedCoupon);
			if (editedCoupon == null) {
				return ResponseUtility.myResponse("admin" + "-updateCoupon" + " insuffidantData ", "insuffidantData",
						Status.RESET_CONTENT);
			}
			fa.updateCoupon(editedCoupon);
		} catch (CouponException e) {
			e.printStackTrace();
			return ResponseUtility.myResponse("admin" + "-updateCoupon ", Status.RESET_CONTENT);
		}
		WebCoupon wc = new WebCoupon(editedCoupon);
		log4j.trace("admin" + " update coupon: " + editedCoupon.toString());
		GenericEntity<WebCoupon> entity = new GenericEntity<WebCoupon>(wc) {
		};
		return Response.ok(entity, MediaType.APPLICATION_JSON).status(Status.OK).build();

	}

	/**
	 * Update a list of coupons from states provided in the parameter
	 * Prior the method check the validity of all the states in the list
	 * and cancel the all on the first invalid state it found
	 * @param webCoupons
	 * @return a Response with string indicating an error or a list of webcoupons if succeed
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/coupons")
	public Response updateCoupons(WebCoupon[] webCoupons) {
		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null) {
			return ResponseUtility.badLogin("admin");
		}
		Collection<Coupon> coupons = ListUtility.getCouponsList(Arrays.asList(webCoupons));
		for (Coupon coupon : coupons) {
			try {
				coupon = fa.updatableCoupon(coupon);
			} catch (CouponException e) {
				e.printStackTrace();
				return ResponseUtility.myResponse("admin" + "-updateCoupon" + " unknown ", Status.RESET_CONTENT);
			}
			if (coupon == null) {
				return ResponseUtility.myResponse("admin" + "-updateCoupon" + " insuffidantData ", "insuffidantData", Status.ACCEPTED);
			}
		}
		for (Coupon coupon : coupons) {
			try {
				fa.updateCoupon(coupon);
			} catch (CouponException e) {
				e.printStackTrace();
				return ResponseUtility.myResponse("admin" + "-updateCoupon" + " unknown ", Status.RESET_CONTENT);
			}
		}
		return getAllWebCoupon();
	}

	/**
	 * Delete the coupon with the provided id
	 * @param couponID
	 * @return a Response indicating success or not of deletion 
	 */
	@DELETE
	@Path("/coupon/{index}")
	public Response deleteCoupon(@PathParam("index") long couponID) {
		AdminFacade fa = adminFacade();
		if (fa == null) {
			return ResponseUtility.badLogin("admin");
		}
		try {
			fa.removeCoupon(couponID);
		} catch (Exception e) {
			return ResponseUtility.myResponse("admin" + "-deleteCoupon", Status.RESET_CONTENT);
		}
		return ResponseUtility.myResponse("admin" + "-deleteCoupon id: " + Long.toString(couponID), Status.OK);

	}

	/**
	 * return all companies in database
	 * @return all companies in database or a String with an error message
	 * @throws CouponException
	 */
	@GET
	@Path("/company")
	public Response getAllWebCompanies() throws CouponException {
		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null)
			return ResponseUtility.badLogin("admin");

		// Getting collection of webCompany
		Collection<Company> companies = fa.getAllCompany();
		Collection<WebCompany> lwc = ListUtility.getWebCompaniesList(companies);
		// returning custom response with GenericEntity class
		log4j.trace("admin: " + " returning list of coupon");
		GenericEntity<Collection<WebCompany>> entity = new GenericEntity<Collection<WebCompany>>(lwc) {
		};
		return Response.ok(entity, MediaType.APPLICATION_JSON).status(Status.ACCEPTED).build();
	}

	/**
	 * Update company in database based on the state provided by the parameter
	 * Missing fields are completed with previously existing state in the database
	 * or aborted if unsuccessful
	 * @param webCompany
	 * @return Response with the updated company state or a string with an error message
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/company")
	public Response updateCompany(WebCompany webCompany) {
		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null)
			return ResponseUtility.badLogin("admin");

		// updating company
		Company editedCompany = webCompany.getCompany();

		try {
			editedCompany = fa.updatableCompany(editedCompany);
			if (editedCompany == null) {
				return ResponseUtility.myResponse("admin: " + "-updateCoupon" + " insuffidantData ", "insuffidantData",
						Status.RESET_CONTENT);
			}
			fa.updateCompany(editedCompany);
		} catch (CouponException e) {
			e.printStackTrace();
			return ResponseUtility.myResponse("admin: " + "-updateCoupon ", Status.RESET_CONTENT);
		}
		WebCompany wc = new WebCompany(editedCompany);
		log4j.trace("admin ressource - company" + webCompany.getName() + " updated:" + editedCompany.toString());
		GenericEntity<WebCompany> entity = new GenericEntity<WebCompany>(wc) {
		};
		return Response.ok(entity, MediaType.APPLICATION_JSON).status(Status.OK).build();
	}

	/**
	 * Update a list of companies from states provided in the parameter
	 * Prior the method check the validity of all the states in the list
	 * and cancel the all on the first invalid state it found
	 * @param webCompanies
	 * @return a Response with string indicating an error or a list of webcompanies if succeed
	 * @throws CouponException
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/companys")
	public Response updateCompanies(WebCompany[] webCompanies) throws CouponException {
		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null)
			return ResponseUtility.badLogin("admin");

		// getting list of companies
		Collection<Company> companies = ListUtility.getCompaniesList(Arrays.asList(webCompanies));
		// updating list of companies in database
		for (Company company : companies) {
			company = fa.updatableCompany(company);
			if (company == null) {
				return ResponseUtility.myResponse("admin: " + "-updateCoupon" + " insuffidantData ", "insuffidantData",
						Status.ACCEPTED);
			}
		}
		for (Company company : companies) {
			try {
				fa.updateCompany(company);
				;
			} catch (CouponException e) {
				e.printStackTrace();
				return ResponseUtility.myResponse("admin: " + "-updateCoupon" + " unknown ", Status.RESET_CONTENT);
			}
		}
		// returning updated list of companies
		return getAllWebCompanies();
	}

	/**
	 * Delete the company with the provided id
	 * @param companyID
	 * @return a Response indicating success or not of deletion 
	 * @throws CouponException
	 */
	@DELETE
	@Path("/company/{index}")
	public Response deleteCompanies(@PathParam("index") long companyID) throws CouponException {

		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null) return ResponseUtility.badLogin("admin");

		// removing company from database
		try {
			fa.removeCompany(companyID);
		} catch (Exception e) {
    		return ResponseUtility.myResponse("admin: " + "-deleteCoupon" ,Status.RESET_CONTENT);	
		}
		return ResponseUtility.myResponse("admin: " + "-deleteCompany id: " + Long.toString(companyID)  ,Status.OK);

	}

	/**
	 * Create a company in database with state provided by the parameter
	 * @param webCompany
	 * @return Response indicating the result of creating a company
	 * @throws CouponException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/company")
	public Response createCompany(WebCompany webCompany) throws CouponException {

		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null) return ResponseUtility.badLogin("admin");
		
		// creating company in database
		Company editedCompany = webCompany.getCompany();
		editedCompany.setPassword("123");
		if(editedCompany.getName()==null){
    		return ResponseUtility.myResponse("admin: " + "-createCompany","incomplete",Status.ACCEPTED);
		}
		
//		checking if duplicate
		if(fa.duplicateCompany(editedCompany.getName())){
    		return ResponseUtility.myResponse("admin: " + "-createCompany","duplicate",Status.ACCEPTED);
		}
		long id = -1;
		try {
			id = fa.createCompany(editedCompany);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtility.myResponse("admin: " + "-createCoupon","unknownError",Status.ACCEPTED);
		}

		// returning updated list of companies
		return ResponseUtility.myResponse("admin: " + "-createCoupon id:" + Long.toString(id),Long.toString(id),Status.CREATED);	
	}

	/**
	 * return all customers in database
	 * @return all companies in database or a String with an error message
	 * @throws CouponException
	 */
	@GET
	@Path("/customer")
	public Response getAllWebCustomers() throws CouponException {
		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null)return ResponseUtility.badLogin("admin");

		// Getting list of webCustomer from database
		Collection<Customer> customers = fa.getAllCustomer();
		Collection<WebCustomer> lwc = ListUtility.getWebCustomerList(customers);

		// returning custom response with GenericEntity class
		log4j.trace("admin ressource - returning list of customer");
		GenericEntity<Collection<WebCustomer>> entity = new GenericEntity<Collection<WebCustomer>>(lwc) {
		};
		return Response.ok(entity, MediaType.APPLICATION_JSON).status(Status.ACCEPTED).build();
	}

	/**
	 * Update customer in database based on the state provided by the parameter
	 * Missing fields are completed with previously existing state in the database
	 * or aborted if unsuccessful
	 * @param webCustomer
	 * @return Response with the updated customer state or a string with an error message
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/customer")
	public Response updateCustomer(WebCustomer webCustomer){
		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null) return ResponseUtility.badLogin("admin");

		// updating customer in database
		Customer editedCustomer = webCustomer.getCustomer();
		try {
			editedCustomer = fa.updatableCustomer(editedCustomer);
			if(editedCustomer == null){
				return ResponseUtility.myResponse("admin: " + "-updateCustomer" + " insuffidantData ","insuffidantData",Status.RESET_CONTENT);	
				}
			fa.updateCustomer(editedCustomer);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtility.myResponse("admin: " + "-updateCustomer ",Status.RESET_CONTENT);	
		}

		// returning list of webCustomer from database
		WebCustomer wc = new WebCustomer(editedCustomer);
		log4j.trace("admin: " + " update coupon: "+editedCustomer.toString());
		GenericEntity<WebCustomer> entity = new GenericEntity<WebCustomer>(wc){};
		return Response.ok(entity,MediaType.APPLICATION_JSON).status(Status.OK).build();	
	}

	/**
	 * Update a list of customers from states provided in the parameter
	 * Prior the method check the validity of all the states in the list
	 * and cancel the all on the first invalid state it found
	 * @param webCustomers
	 * @return a Response with string indicating an error or a list of webCustomers if succeed
	 * @throws CouponException
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/customers")
	public Response updateCustomers(WebCustomer[] webCustomers) throws CouponException {
		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null) return ResponseUtility.badLogin("admin");
		Collection<Customer> customers = ListUtility.getCustomerList(Arrays.asList(webCustomers));

		for(Customer customer : customers){
			customer = fa.updatableCustomer(customer);
			if(customer==null){
				return ResponseUtility.myResponse("admin: " + "-updateCoupon" + " insuffidantData ","insuffidantData",Status.ACCEPTED);}
		}
		for(Customer customer : customers){
			try {
				fa.updateCustomer(customer);
			} catch (CouponException e) {
				e.printStackTrace();
				return ResponseUtility.myResponse("admin: " + "-updateCustomer" + " unknown ",Status.RESET_CONTENT);	
			}		}
		return getAllWebCustomers();
	}

	/**
	 * Delete the customer with the provided id
	 * @param customerID
	 * @return a Response indicating success or not of deletion 
	 * @throws CouponException
	 */
	@DELETE
	@Path("/customer/{index}")
	public Response deleteCustomer(@PathParam("index") long customerID) throws CouponException {
		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null) return ResponseUtility.badLogin("admin");
		try {
			fa.removeCustomer(customerID);
		} catch (Exception e) {
    		return ResponseUtility.myResponse("admin: " + "-deleteCustomer" ,Status.RESET_CONTENT);	
		}
		return ResponseUtility.myResponse("admin: " + "-deleteCustomer id: " + Long.toString(customerID)  ,Status.OK);

	}

	/**
	 * Create a customer in database with state provided by the parameter
	 * @param webCustomer
	 * @return Response indicating the result of creating a customer
	 * @throws CouponException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/customer")
	public Response createCustomer(WebCustomer webCustomer) throws CouponException {
//		checking required data validity
		if(webCustomer == null || webCustomer.getCustName() == null || webCustomer.getCustName().equals("")){
			return ResponseUtility.myResponse("admin: " + "-createCustomer","incomplete",Status.ACCEPTED);
		}
		// Getting facade
		AdminFacade fa = adminFacade();
		if (fa == null) return ResponseUtility.badLogin("admin");
		

		// add new customer to database
		Customer editedCustomer = webCustomer.getCustomer();
//		checking if duplicate
		if(fa.duplicateCustomer(editedCustomer.getCustName())){
    		return ResponseUtility.myResponse("admin: " + "-createCoupon","duplicate",Status.ACCEPTED);
		}
//		setting default password
		editedCustomer.setPassword("123");
//		creating coupon
		Long id = -1l;
		try {
			id = fa.createCustomer(editedCustomer);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtility.myResponse("admin: " + "-createCoupon","unknownError",Status.ACCEPTED);
		}
		String sid = Long.toString(id);
		return ResponseUtility.myResponse("admin: " + "-createCoupon id:" + sid , sid ,Status.CREATED);	
	}

}
