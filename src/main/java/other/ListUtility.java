package other;

import java.util.ArrayList;
import java.util.Collection;
import cpCore.beans.*;
import webBeans.*;

/**
 * @author Marc Weiss
 * utility class that handle conversion of Collection
 * between beans and web beans
 */
public class ListUtility {
	
	/**
	 * Method to convert a Collection of Coupon to a Collection of WebCoupon
	 * @param coupons
	 * @return
	 */
	public static Collection<WebCoupon> getWebCouponsList(Collection<Coupon> coupons){
		if(coupons == null) return null;
		Collection<WebCoupon> list = new ArrayList<>();
		for (Coupon cp : coupons){
			list.add(new WebCoupon(cp));
		}
		return list;
	}
	
	/**
	 * Method to convert a Collection of WebCoupon to a Collection of Coupon
	 * @param coupons
	 * @return
	 */
	public static Collection<Coupon> getCouponsList(Collection<WebCoupon> coupons){
		if(coupons == null) return null;
		Collection<Coupon> list = new ArrayList<>();
		for (WebCoupon cp : coupons){
			list.add(cp.getCoupon());
		}
		return list;
	}

	/**
	 * Method to convert a Collection of Company to a Collection of WebCompany
	 * @param companies
	 * @return
	 */
	public static Collection<WebCompany> getWebCompaniesList(Collection<Company> companies){
		if(companies == null) return null;
		Collection<WebCompany> list = new ArrayList<>();
		for (Company cp : companies){
			list.add(new WebCompany(cp));
		}
		return list;
	}

	/**
	 * Method to convert a Collection of WebCompany to a Collection of Company
	 * @param webCompanies
	 * @return
	 */
	public static Collection<Company> getCompaniesList(Collection<WebCompany> webCompanies){
		if(webCompanies == null) return null;
		Collection<Company> list = new ArrayList<>();
		for (WebCompany cp : webCompanies){
			list.add(cp.getCompany());
		}
		return list;
	}

	/**
	 * Method to convert a Collection of Customer to a Collection of WebCustomer
	 * @param customers
	 * @return
	 */
	public static Collection<WebCustomer> getWebCustomerList(Collection<Customer> customers){
		if(customers == null) return null;
		Collection<WebCustomer> list = new ArrayList<>();
		for (Customer ct : customers){
			list.add(new WebCustomer(ct));
		}
		return list;
	}
	
	/**
	 * Method to convert a Collection of WebCustomer to a Collection of Customer
	 * @param webCustomers
	 * @return
	 */
	public static Collection<Customer> getCustomerList(Collection<WebCustomer> webCustomers){
		if(webCustomers == null) return null;
		Collection<Customer> list = new ArrayList<>();
		for (WebCustomer ct : webCustomers){
			list.add(ct.getCustomer());
		}
		return list;
	}

}
