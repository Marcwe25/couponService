package cpCore.beans;


public class Coupon {
	private long id;
	private String title;
	private java.util.Date start_date;
	private java.util.Date end_date;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	public Coupon() {}
	
	public Coupon(Coupon anotherCoupon){
		super();
		setTitle(anotherCoupon.title);
		setStart_date(anotherCoupon.start_date);
		setEnd_date(anotherCoupon.end_date);
		setAmount(anotherCoupon.amount);
		setType(anotherCoupon.type);
		setMessage(anotherCoupon.message);
		setPrice(anotherCoupon.price);
		setImage(anotherCoupon.image);
	}
	
	public Coupon(String title, java.util.Date start_date, java.util.Date end_date, int amount, CouponType type, String message,
			double price, String image) {
		super();
		setTitle(title);
		setStart_date(start_date);
		setEnd_date(end_date);
		setAmount(amount);
		setType(type);
		setMessage(message);
		setPrice(price);
		setImage(image);

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
		this.start_date = start_date;
	}

	public void setStart_date(java.sql.Date start_date) {
		java.util.Date utilDate = new java.util.Date(start_date.getTime());
		this.start_date = utilDate;
	}
	
	public java.util.Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(java.util.Date end_date) {
		this.end_date = end_date;
	}

	public void setEnd_date(java.sql.Date end_date) {
		java.util.Date utilDate = new java.util.Date(end_date.getTime());
		this.end_date = utilDate;
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
	
	public boolean haveIdentity(){
		return	title != null ||
				id > 0;
	}

	@Override
	public String toString() {
		return "\n"+"Coupon [id=" + id + ", title=" + title + ", start_date=" + start_date + ", end_date=" + end_date
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
		Coupon other = (Coupon) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
