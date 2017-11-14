package webBeans;

import java.util.Collection;

import cpCore.beans.Coupon;
import cpCore.beans.Customer;

public class WebCustomer {
	
	private long id;
	private String custName;
	private Collection<Coupon> coupons;
	
	public WebCustomer() {
		super();
	}

	public WebCustomer(long id, String custName, Collection<Coupon> coupons) {
		super();
		this.id = id;
		this.custName = custName;
		this.coupons = coupons;
	}

	public WebCustomer(Customer customer) {
		super();
		this.id = customer.getId();
		this.custName = customer.getCustName();
		this.coupons = customer.getCoupons();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	public Customer getCustomer(){
		Customer customer = new Customer();
		customer.setCustName(custName);
		customer.setId(id);
		customer.setCoupons(coupons);
		return customer;
	}

	@Override
	public String toString() {
		return "WebCustomer [id=" + id + ", custName=" + custName + ", coupons=" + coupons + "]";
	}
	
	

}
