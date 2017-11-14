package cpCore.beans;

import java.util.Collection;

public class Company {
	private long id;
	private String name;
	private String password;
	private String email;
	private Collection<Coupon> coupons;
	
	public Company() {}
	
	public Company(long id, String name, String password, String email, Collection<Coupon> coupons) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.coupons = coupons;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		// create first a string with company's coupon id
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
		return "\n"+"Company [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", "+couponStr;
	}
	
	public boolean haveIdentity(){
		return	name != null ||
				id > 0;
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
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

	
}
