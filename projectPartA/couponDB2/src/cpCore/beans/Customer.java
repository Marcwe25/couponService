package cpCore.beans;

import java.util.Collection;

public class Customer {

	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	public Customer() {
	}

	public Customer(long id, String custName, String password, Collection<Coupon> coupons) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
		this.coupons = coupons;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	public boolean haveIdentity(){
		return	custName != null ||
				id > 0;
	}

	@Override
	public String toString() {

		// create first a string with customer's coupon id
		String couponStr = "";
		if (coupons != null && !coupons.isEmpty()) {
			couponStr = "coupon id's: ";
			for (Coupon coupon : coupons) {
				couponStr += coupon.getId() + ", ";
			}
			couponStr = couponStr.substring(0, couponStr.length() - 2);
		} else {
			couponStr = "no coupon to display";
		}

		// return string with customer information
		return "\n" + "Customer [id=" + id + ", custName=" + custName + ", password=" + password + ", " + couponStr
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
