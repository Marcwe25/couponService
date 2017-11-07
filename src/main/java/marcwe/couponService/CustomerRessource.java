package marcwe.couponService;

import java.io.Serializable;
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
import cpCore.beans.Customer;
import cpCore.beans.Coupon;
import facade.CustomerFacade;
import other.ResponseUtility;
import other.UserType;
import other.ListUtility;
import webBeans.WebCustomer;
import webBeans.WebCoupon;

/**
 * @author Marc Weiss
 *
 */
@Path("customer")
public class CustomerRessource implements Serializable  {
	private static final long serialVersionUID = -2891948375621018790L;
	private static final Logger log4j = LogManager.getLogger("cpFileLogger");
	@Context private HttpServletRequest request;
	@Context private HttpServletResponse response;
	public CustomerRessource(){}
	
	/**
	 * @return a CustomerFacade associated with a session in the container
	 */
	private CustomerFacade customerFacade(){
		CustomerFacade fa = null;
		 try {
			fa = (CustomerFacade)request.getSession().getAttribute("facade");
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
    	CustomerFacade fa = customerFacade();
		if(fa == null){return ResponseUtility.badLogin("customer");}
    	else{
    		return ResponseUtility.myResponse(customerID(fa),Status.OK);	
    	}
    }
	
	/**
	 * return all coupon in database
	 * owned by the customer
	 * @return all WebCoupon
	 */
	@GET
	@Path("/coupon")
	public Response getOwnedWebCoupon(){
//		Getting facade
		CustomerFacade fa = customerFacade();
		if(fa == null){return ResponseUtility.badLogin("customer");}
//		Getting collection of webCoupon
		Collection<Coupon> lc;
		try {
			lc = fa.getAllPurchasedCoupon();
		} catch (CouponException e) {
			e.printStackTrace();
			return ResponseUtility.myResponse(customerID(fa) + "-getOwnedWebCoupon" ,Status.RESET_CONTENT);	
		}
		Collection<WebCoupon> lwc = ListUtility.getWebCouponsList(lc);
//		logging with log4j
		log4j.trace(customerID(fa)+" returning list of owned coupon");
//		returning custom response with GenericEntity class
		GenericEntity<Collection<WebCoupon>> entity = new GenericEntity<Collection<WebCoupon>>(lwc){};
		return Response.ok(entity,MediaType.APPLICATION_JSON).status(Status.ACCEPTED).build();
	}
	
	/**
	 * return all coupon in database
	 * the customer can buy
	 * @return all WebCoupon
	 */
		@GET
		@Path("/purchase")
		public Response getCouponToBuy(){
	//		Getting facade
			CustomerFacade fa = customerFacade();
			if(fa == null){return ResponseUtility.badLogin("customer");}
			
	//		Getting collection of webCoupon
			Collection<Coupon> lc;
			try {
				lc = fa.getCouponToBuyByCustomerID();
			} catch (CouponException e) {
				e.printStackTrace();
	    		return ResponseUtility.myResponse(customerID(fa) + "-getCouponToBuy" ,Status.RESET_CONTENT);	
			}
			Collection<WebCoupon> lwc = ListUtility.getWebCouponsList(lc);
			
	//		returning custom response with GenericEntity class
			log4j.trace(customerID(fa)+" returning list of owned coupon");
			GenericEntity<Collection<WebCoupon>> entity = new GenericEntity<Collection<WebCoupon>>(lwc){};
			return Response.ok(entity,MediaType.APPLICATION_JSON).status(Status.ACCEPTED).build();
		}

	/**
	 * Delete  from customer coupon the coupon with the provided id
	 * @param couponID
	 * @return a Response indicating success or not of deletion 
	 */
	@DELETE
	@Path("/coupon/{index}")
	public Response deleteCoupon(@PathParam("index") long couponID){
		CustomerFacade fa = customerFacade();
		if(fa == null){return ResponseUtility.badLogin("customer");}
		try {
			fa.removeCoupon(couponID);
		} catch (Exception e) {
    		return ResponseUtility.myResponse(customerID(fa) + "-deleteCoupon" ,Status.RESET_CONTENT);	
		}
		return ResponseUtility.myResponse(customerID(fa) + "-deleteCoupon id: " + Long.toString(couponID)  ,Status.OK);
	}
	

	/**
	 * @param webCoupon
	 * @return Response indicating success of the purchase
	 */
	@POST
	@Path("/purchase")
	public Response purchaseWebCoupon(WebCoupon webCoupon){
//		Getting facade
		CustomerFacade fa = customerFacade();
		if(fa == null){return ResponseUtility.badLogin("customer");}
		
//		Purchase coupon
		try {
			fa.purchaceCoupon(webCoupon.getId());
		} catch (CouponException e) {
			e.printStackTrace();
			return ResponseUtility.myResponse(customerID(fa) + "-update" ,Status.RESET_CONTENT);	
		}
		
//		Returning successful response
		return ResponseUtility.myResponse(customerID(fa) + "-purchase id:" + Long.toString(webCoupon.getId()),Status.CREATED);	

	}
	
	/**
	 * Method to get the current customer state
	 * who use the facade in the session
	 * @return Customer
	 */
	@GET
	@Path("/profile")
	public Response getCustomer(){
//		Getting facade
		CustomerFacade fa = customerFacade();
		if(fa == null){return ResponseUtility.badLogin("customer");}
		
//		Getting customer name
		WebCustomer[] wa = new WebCustomer[1];
		wa[0] = new WebCustomer(fa.getCustomer());
		
//		returning custom response with GenericEntity class
		GenericEntity<WebCustomer[]> entity = new GenericEntity<WebCustomer[]>(wa){};
		return Response.ok(entity,MediaType.APPLICATION_JSON).status(Status.ACCEPTED).build();			
	}
	
	
	/**
	 * Method to update the current customer state
	 * who use the facade in the session
	 * only the password can be changed
	 * @return Customer
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/profile")
	public Response update(Customer customer){
		CustomerFacade fa = customerFacade();
		if(fa == null){return ResponseUtility.badLogin("customer");}
		try {
			Customer newData = fa.updatableCustomer(customer);
			if(newData==null) return ResponseUtility.badLogin("customer");;	
			fa.updateCustomer(newData);
		} catch (CouponException e) {
			e.printStackTrace();
			return ResponseUtility.myResponse(customerID(fa) + "-update" ,Status.RESET_CONTENT);	
		}
		return getCustomer();
	}

	public String customerID(CustomerFacade fa){
		return (UserType.customer.toString()+" id:"+Long.toString(fa.getCustomer().getId()));
	}

}
