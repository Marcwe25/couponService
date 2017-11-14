package webBeans;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import cpCore.beans.Company;
import cpCore.beans.Coupon;
import other.ListUtility;

@XmlRootElement
public class WebCompany{

	private long id;
	private String name;
	private String email;
	private Collection<WebCoupon> coupons;
	
	
	
	public WebCompany() {}

	public WebCompany(long id, String name, String email, Collection<WebCoupon> coupons) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.coupons = coupons;
	}
	
	public WebCompany(Company company) {
		super();
		this.id = company.getId();
		this.name = company.getName();
		this.email = company.getEmail();
		Collection<Coupon> cps = company.getCoupons();
		this.coupons = new ArrayList<>();
		for (Coupon cp : cps){
			coupons.add(new WebCoupon(cp));
		}
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		String couponIds = "";
		for (WebCoupon cp : coupons) {
			couponIds += cp.getId() + ", ";
		}
		couponIds = couponIds.length()==0 ? "no coupon to show" : couponIds.substring(0, couponIds.length()-3);
		return "WebCompany [id=" + id + ", name=" + name + ", email=" + email + ", coupons=" + couponIds
				+ "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	
	public Collection<WebCoupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(Collection<WebCoupon> coupons) {
		this.coupons = coupons;
	}
	public Company getCompany(){
		Company company = new Company();
		company.setId(getId());
		company.setName(getName());
		company.setEmail(getEmail());
		company.setCoupons(ListUtility.getCouponsList(getCoupons()));
		return company;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebCompany other = (WebCompany) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
