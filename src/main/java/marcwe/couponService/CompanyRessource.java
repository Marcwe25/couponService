package marcwe.couponService;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpCore.CouponException;
import cpCore.beans.Company;
import cpCore.beans.Coupon;
import facade.CompanyFacade;
import other.ResponseUtility;
import other.UserType;
import other.ListUtility;
import webBeans.WebCompany;
import webBeans.WebCoupon;

/**
 * @author Marc Weiss
 *
 */
@Path("company")
public class CompanyRessource implements Serializable {
	private static final long serialVersionUID = 7952594567523937280L;
	private static final Logger log4j = LogManager.getLogger("cpFileLogger");
	@Context private HttpServletRequest request;
	@Context private HttpServletResponse response;
	
	public CompanyRessource() {}
	
	/**
	 * @return a CompanyFacade associated with a session in the container
	 */
	private CompanyFacade companyFacade(){
		CompanyFacade fa = null;
		 try {
			fa = (CompanyFacade)request.getSession().getAttribute("facade");
		} catch (ClassCastException e) {
			e.printStackTrace();
			return null;
		}
		 return fa;
	}
	
	/**
	 * @return Response indicating if a facade exist for the current session
	 */
    @GET
    public Response getIt() {
		CompanyFacade fa = companyFacade();
		if(fa == null){
    		return ResponseUtility.badLogin("company");
		}
    	else{
    		return ResponseUtility.myResponse(companyID(fa),Status.OK);	
    	}
    }
	
	/**
	 * return all Coupon in database associated with the current company
	 * @return all webcoupons
	 */
	@GET
	@Path("/coupon")
	public Response getAllWebCoupon(){
//		Getting facade
		CompanyFacade fa = companyFacade();
		if(fa == null){
    		return ResponseUtility.badLogin("company");
		}
//		Getting collection of webCoupon
		Collection<Coupon> lc;
		try {
			lc = fa.getCompanyCoupon();
		} catch (CouponException e) {
			e.printStackTrace();
			return ResponseUtility.myResponse(companyID(fa) + "-getAllWebCoupon" + " unknown ",Status.RESET_CONTENT);	
		}
		Collection<WebCoupon> lwc = ListUtility.getWebCouponsList(lc);
//		returning custom response with GenericEntity class
		log4j.trace(companyID(fa)+" returning list of coupon");
		GenericEntity<Collection<WebCoupon>> entity = new GenericEntity<Collection<WebCoupon>>(lwc){};
		return Response.ok(entity,MediaType.APPLICATION_JSON).status(Status.ACCEPTED).build();	
	}
	
	/**
	 * Create a Coupon in database with state provided by the parameter
	 * associated with the current company
	 * @param webCoupon
	 * @return Response indicating the result of creating a coupon
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/coupon")
	public Response createCoupon(WebCoupon webCoupon){
//		getting facade
		CompanyFacade fa = companyFacade();
		if(fa == null){
			return ResponseUtility.badLogin("company");
		}
		Coupon editedCoupon = webCoupon.getCoupon();
//		checking required data validity
		if(!editedCoupon.containSuffisantData()){
    		return ResponseUtility.myResponse(companyID(fa) + "-createCoupon","incomplete",Status.ACCEPTED);
		}
		Long id = -1l;
		try {
//		checking if duplicate
			if(fa.duplicateCoupon(editedCoupon.getTitle())){
				return ResponseUtility.myResponse(companyID(fa) + "-createCoupon","duplicate",Status.ACCEPTED);
			}
//		creating coupon
			id = fa.createCoupon(editedCoupon);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtility.myResponse(companyID(fa) + "-createCoupon","unknownError",Status.ACCEPTED);
		}
		return ResponseUtility.myResponse(companyID(fa) + "-createCoupon id:" + Long.toString(id),Long.toString(id),Status.CREATED);	
	}
	
	/**
	 * Delete the Coupon with the provided id
	 * and remove association with company or customers
	 * @param couponID
	 * @return a Response indicating success or not of deletion 
	 */
	@DELETE
	@Path("/coupon/{index}")
	public Response deleteCoupon(@PathParam("index") long couponID){
		CompanyFacade fa = companyFacade();
		if(fa == null){
    		return ResponseUtility.badLogin("company");
		}
		try {
			if(!fa.CorrectCompany(couponID)){
				return ResponseUtility.myResponse(companyID(fa) + "-deleteCoupon" + " notYours ","notYours",Status.RESET_CONTENT);	}
			fa.removeCoupon(couponID);
		} catch (Exception e) {
    		return ResponseUtility.myResponse(companyID(fa) + "-deleteCoupon" ,Status.RESET_CONTENT);	
		}
		return ResponseUtility.myResponse(companyID(fa) + "-deleteCoupon id: " + Long.toString(couponID)  ,Status.OK);

	}
	
	/**
	 * Update coupon in database based on the state provided by the parameter
	 * Missing fields are completed with previously existing state in the database
	 * or aborted if unsuccessful
	 * @param webCoupon
	 * @return Response indicating the result of updating a coupon or the coupon it self if succeed
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/coupon")
	public Response updateCoupon(WebCoupon webCoupon){
		CompanyFacade fa = companyFacade();
		if(fa == null){
			return ResponseUtility.badLogin("company");
		}
		Coupon editedCoupon = webCoupon.getCoupon();
		try {
			editedCoupon = fa.updatableCoupon(editedCoupon);
			if(editedCoupon==null){
				return ResponseUtility.myResponse(companyID(fa) + "-updateCoupon" + " insuffidantData ","insuffidantData",Status.RESET_CONTENT);	
			}
			if(!fa.CorrectCompany(editedCoupon.getId())){
				return ResponseUtility.myResponse(companyID(fa) + "-updateCoupon" + " notYours ",Status.RESET_CONTENT);	
			}
			fa.updateCoupon(editedCoupon);
		} catch (CouponException e) {
			e.printStackTrace();
			return ResponseUtility.myResponse(companyID(fa) + "-updateCoupon ",Status.RESET_CONTENT);	
		}
		WebCoupon wc = new WebCoupon(editedCoupon);
		log4j.trace(companyID(fa)+" update coupon: "+editedCoupon.toString());
		GenericEntity<WebCoupon> entity = new GenericEntity<WebCoupon>(wc){};
		return Response.ok(entity,MediaType.APPLICATION_JSON).status(Status.OK).build();	
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
		CompanyFacade fa = companyFacade();
		if(fa == null){
			return ResponseUtility.badLogin("company");	
		}
		Collection<Coupon> coupons = ListUtility.getCouponsList(Arrays.asList(webCoupons));
		for(Coupon coupon : coupons){
			try {
				coupon = fa.updatableCoupon(coupon);
				if(coupon==null){
					return ResponseUtility.myResponse(companyID(fa) + "-updateCoupon" + " insuffidantData ","insuffidantData",Status.ACCEPTED);}
				if(!fa.CorrectCompany(coupon.getId())){
					return ResponseUtility.myResponse(companyID(fa) + "-updateCoupon" + " notYours ",Status.RESET_CONTENT);	
				}
			} catch (CouponException e) {
				e.printStackTrace();
				return ResponseUtility.myResponse(companyID(fa) + "-updateCoupon" + " unknown ",Status.RESET_CONTENT);	
			}
		}
		for(Coupon coupon : coupons){
			try {
				fa.updateCoupon(coupon);
			} catch (CouponException e) {
				e.printStackTrace();
				return ResponseUtility.myResponse(companyID(fa) + "-updateCoupon" + " unknown ",Status.RESET_CONTENT);	
			}		}
		return getAllWebCoupon();
	}

	
	/**
	 * @return current company state
	 */
	@GET
	@Path("/profile")
	public Response getCompany(){
//		Getting facade
		CompanyFacade fa = companyFacade();
		if(fa == null){
    		return ResponseUtility.badLogin("company");
		}
		
//		Getting company name
		WebCompany[] wa = new WebCompany[1];
		wa[0] = new WebCompany(fa.getCompany());
		
//		returning custom response with GenericEntity class
		GenericEntity<WebCompany[]> entity = new GenericEntity<WebCompany[]>(wa){};
		return Response.ok(entity,MediaType.APPLICATION_JSON).status(Status.ACCEPTED).build();		
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
	@Path("/profile")
	public Response update(Company company){
		CompanyFacade fa = companyFacade();
		if(fa == null){
    		return ResponseUtility.badLogin("company");
		}
		try {
			Company newData = fa.updatableCompany(company);
			if(newData==null) return ResponseUtility.badLogin("company");
			fa.updateCompany(newData);
		} catch (CouponException e) {
			e.printStackTrace();
			return ResponseUtility.myResponse(companyID(fa) + "-updateCoupon" + " unknown ",Status.RESET_CONTENT);	
		}
		return getCompany();
	}

	public String companyID(CompanyFacade fa){
		return (UserType.company.toString()+" id:"+Long.toString(fa.getCompany().getId()));
	}

}
