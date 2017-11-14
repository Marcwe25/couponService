package webBeans;

import javax.xml.bind.annotation.XmlRootElement;

import cpCore.beans.Coupon;
import cpCore.beans.CouponType;

@XmlRootElement
public class WebCoupon {
	private long id;
	private String title;
	private java.sql.Date start_date;
	private java.sql.Date end_date;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	public WebCoupon() {}
	
	public WebCoupon(Coupon anotherCoupon){
		super();
		setId(anotherCoupon.getId());
		setTitle(anotherCoupon.getTitle());
		setStart_date(anotherCoupon.getStart_date());
		setEnd_date(anotherCoupon.getEnd_date());
		setAmount(anotherCoupon.getAmount());
		setType(anotherCoupon.getType());
		setMessage(anotherCoupon.getMessage());
		setPrice(anotherCoupon.getPrice());
		setImage(anotherCoupon.getImage());
	}
	
	public WebCoupon(long id, String title, java.util.Date start_date, java.util.Date end_date, int amount, CouponType type, String message,
			double price, String image) {
		super();
		setId(id);
		setTitle(title);
		setStart_date(start_date);
		setEnd_date(end_date);
		setAmount(amount);
		setType(type);
		setMessage(message);
		setPrice(price);
		setImage(image);

	}
	
	public Coupon getCoupon(){
		Coupon c = new Coupon();
		c.setAmount(this.amount);
		if(this.end_date==null){
			java.util.Date tmp = null;
			c.setEnd_date(tmp);}
		else{c.setEnd_date(this.end_date);}
		c.setId(this.id);
		c.setImage(this.getImage());
		c.setMessage(this.message);
		c.setPrice(this.price);
		c.setStart_date(this.start_date);
		c.setTitle(this.title);
		c.setType(this.type);
		return c;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public java.util.Date getStart_date() {
		return start_date;
	}

	public void setStart_date(java.util.Date start_date) {
		this.start_date = new java.sql.Date(start_date.getTime());
	}

	public void setStart_date(java.sql.Date start_date) {
		this.start_date = start_date;
	}
	
	public java.util.Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(java.util.Date end_date) {
		this.end_date = new java.sql.Date(end_date.getTime());
	}

	public void setEnd_date(java.sql.Date end_date) {
		this.end_date = end_date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public boolean containSuffisantData(){
		if(start_date==null) return false;
		if(end_date == null) return false;
		if(amount<0 ) return false;
		if(type == null) return false;
		if(price<=0) return false;
		return true;
	}

	@Override
	public String toString() {
		return "\n"+"Coupon [a_id=" + id + ", b_title=" + title + ", start_date=" + start_date + ", end_date=" + end_date
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
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
		WebCoupon other = (WebCoupon) obj;
		if (id != other.getId())
			return false;
		return true;
	}
}
