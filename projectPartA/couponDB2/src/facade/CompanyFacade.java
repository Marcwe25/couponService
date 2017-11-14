package facade;

import java.util.Date;
import java.util.List;
import DAO.AbstractLevel.*;
import DAODBlevel.*;
import cpCore.*;
import cpCore.beans.*;

public class CompanyFacade implements Facade{
	CompanyDAO compDAO;
	CouponDAO coupDAO;
	private Company currentCompany;
	private CompanyFacade(){}
	
	private CompanyFacade(String companyName) throws CouponException{
		this.compDAO = new CompanyDBDAO();
		this.coupDAO = new CouponDBDAO();
		this.currentCompany = compDAO.getCompany(compDAO.getCompanyID(companyName));
	}
	
	public static CompanyFacade login (String companyName, String password) throws CouponException{
		CompanyDBDAO cd = new CompanyDBDAO();
		if(cd.login(companyName, password)) return new CompanyFacade(companyName);
		else return null;
	}
	
	public String getCompanyDetail() throws CouponException{
		return compDAO.getCompany(currentCompany.getId()).toString();
	}
	
	public Company getCompany(){
		return currentCompany;
	}
	
	public List<Coupon> getCompanyCoupon() throws CouponException{
		return coupDAO.getCouponByCompany(currentCompany.getId());
	}

	public Coupon getCompanyCouponById(long couponID) throws CouponException {
		if (CorrectCompany(couponID))
		{return coupDAO.getCoupon(couponID);}
		else {throw new CouponException("invalid coupon id");}
	}

	public List<Coupon> getCompanyCouponByType(CouponType couponType) throws CouponException{
		return coupDAO.getCouponByCompanyIDandByType(currentCompany.getId(), couponType);
	}
	
	public List<Coupon> getCompanyCouponCheaperThen(double couponPrice) throws CouponException{
		return coupDAO.getCouponByCompanyIDCheaperthan(currentCompany.getId(), couponPrice);
	}
	
	public List<Coupon> getCompanyCouponEndingBefore(java.util.Date endDate) throws CouponException{
		return coupDAO.getCouponByCompanyIDByEnDate(currentCompany.getId(), endDate);
	}

	/**create coupon*/
	public long createCoupon(Coupon coupon) throws CouponException{
		try {
			if (!coupon.containSuffisantData()) throw new NullPointerException();
			Long couponID = coupDAO.createCoupon(coupon);
			coupDAO.addCouponToCompany(currentCompany.getId(), couponID);
			return couponID;
		} catch (NullPointerException e) {
			System.out.println(e.getStackTrace());
			throw new CouponException("exception while creating coupon, \n---> coupon need title, start_date, end_date, amount, type, and price",e);
		}
	}

	/**remove coupon*/
	public void removeCoupon(long couponID) throws CouponException{
		if (CorrectCompany(couponID))
		{	coupDAO.removeFromCustomerCoupon(couponID);
			coupDAO.removeFromCompanyCoupon(couponID);
			coupDAO.removeCoupon(couponID);
		}
		else {throw new CouponException("invalid coupon id");}
	}
	
	/**fill uncompleted information if possible*/
	public Coupon updatableCoupon(Coupon coupon) throws CouponException{
		return coupDAO.updatableCoupon(coupon);
		}
	
	/**update coupon information*/
	public void updateCoupon(Coupon coupon) throws CouponException{
		coupDAO.updateCoupon(coupon);
	}
	
	/**check if a coupon belong to a company*/
	public boolean CorrectCompany(long couponID) throws CouponException{
		return coupDAO.getCompanyIDbyCouponID(couponID)==currentCompany.getId();	
	}
	
	/**check if a coupon with the provided title already exist*/
	public boolean duplicateCoupon(String title) throws CouponException{
		return coupDAO.getCouponByTitle(title)!=null;
	}
	

	/**fill uncompleted information if possible*/
	public Company updatableCompany(Company company) throws CouponException{
		return compDAO.updatableCompany(company);
	}
	
	/**update company information (email and password if provided).
	 * the name is not updated*/
	public void updateCompany(Company company) throws CouponException{
		if(company==null) throw new CouponException("no coupon to update");
		compDAO.updateCompany(company);
		currentCompany.setEmail(company.getEmail());
		currentCompany.setPassword(company.getPassword());
	}
}
