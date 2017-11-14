package facade;

import java.util.*;
import DAODBlevel.*;
import cpCore.beans.*;
import DAO.AbstractLevel.*;
import cpCore.CouponException;

public class CustomerFacade implements Facade{
	private Customer currentCustomer;
	CustomerDAO custDAO;
	CouponDAO coupDAO;
	
	private CustomerFacade(){}
	
	private CustomerFacade(String customerName) throws CouponException {
		custDAO = new CustomerDBDAO();
		coupDAO = new CouponDBDAO();
		currentCustomer = custDAO.getCustomer(custDAO.getCustomerID(customerName));
	}
	
	public static CustomerFacade login(String username, String password) throws CouponException{
		CustomerDBDAO cd = new CustomerDBDAO();
		if(cd.login(username, password)) return new CustomerFacade(username);
		else return null;
		}
	
	public Customer getCustomer(){
		return currentCustomer;
	}

	public void purchaceCoupon(long couponID) throws CouponException{
		Coupon cp = coupDAO.getCoupon(couponID);
		
		if (cp.getEnd_date().compareTo(new Date())<0) throw new CouponException("dateExpired");
		if (cp.getAmount()<1) throw new CouponException("outOfStock");
		
		coupDAO.addCouponToCustomer(currentCustomer.getId(), couponID);
		coupDAO.updateCouponAmount(-1, couponID);
		
	}
	
	public List<Coupon> getAllPurchasedCoupon() throws CouponException{
		List<Coupon> coupons = coupDAO.getCouponByCustomerID(currentCustomer.getId());
		return coupons;
	}

	public List<Coupon> getCouponToBuyByCustomerID() throws CouponException{
		List<Coupon> coupons = coupDAO.getCouponToBuyByCustomerID(currentCustomer.getId());
		return coupons;
	}
	
	public List<Coupon> getAllCouponByType(CouponType couponType) throws CouponException{
		return coupDAO.getCouponByCustomerIDByType(currentCustomer.getId(), couponType);
	}
	
	public List<Coupon> getAllCouponByPrice(Double couponPrice) throws CouponException{
		return coupDAO.getCouponByCustomerIDCheaperThan(currentCustomer.getId(), couponPrice);
	}
	
	public void removeCoupon(long couponID) throws CouponException{
		coupDAO.removeFromCustomerCoupon(couponID);
	}
	
	public boolean CorrectCustomer(long couponID) throws CouponException{
		Collection<Coupon> ls = coupDAO.getCouponByCustomerID(currentCustomer.getId());
		Coupon cp = coupDAO.getCoupon(couponID);
		return ls.contains(cp);	
	}
	
	/**complete missing information*/
	public Customer updatableCustomer (Customer customer) throws CouponException{
		return custDAO.updatableCustomer(customer);
	}
	
	/**update customer information (email and password if provided).
	 * the name is not updated*/
	public void updateCustomer(Customer customer) throws CouponException{
		custDAO.updateCustomer(customer);
		currentCustomer.setPassword(customer.getPassword());
	}

}
