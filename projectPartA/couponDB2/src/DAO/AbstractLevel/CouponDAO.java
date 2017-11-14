package DAO.AbstractLevel;

import java.util.*;

import cpCore.CouponException;
import cpCore.beans.Coupon;
import cpCore.beans.CouponType;

public interface CouponDAO extends DAO{
	
	public abstract long createCoupon(Coupon coupon) throws CouponException;
	
	public abstract void removeCoupon(long couponID) throws CouponException;
	
	public abstract void removeFromCompanyCoupon(long couponID) throws CouponException;
	
	public abstract void removeFromCustomerCoupon(long couponID) throws CouponException;
	
	public abstract void updateCoupon(Coupon coupon) throws CouponException;
	
	public void updateCouponAmount(int amount, long couponID) throws CouponException;

	public abstract Coupon getCoupon(long couponID) throws CouponException;

	public abstract Coupon getCouponByTitle(String title) throws CouponException;

	public abstract List<Coupon> getAllCoupon() throws CouponException;

	public abstract List<Coupon> getCouponByType(String couponType) throws CouponException;
	
	public abstract List<Coupon> getCouponByCompany(long companyID) throws CouponException;
	
	public abstract long getCompanyIDbyCouponID (long couponID) throws CouponException;
	
	public abstract List<Coupon> getCouponByCustomerID(long customerID) throws CouponException;
	
	public abstract List<Coupon> getCouponToBuyByCustomerID(long customerID) throws CouponException;
	
	public abstract List<Coupon> getCouponByCustomerIDCheaperThan(long customerID, double couponPrice) throws CouponException;
	
	public abstract List<Coupon> getCouponByCustomerIDByType(long customerID, CouponType couponType) throws CouponException;

	public abstract ArrayList<Coupon> getCouponByCompanyIDandByType(long companyID, CouponType coupontype) throws CouponException;

	public abstract ArrayList<Coupon> getCouponByCompanyIDCheaperthan(long companyID, double couponPrice) throws CouponException;
	
	public abstract ArrayList<Coupon> getCouponByCompanyIDByEnDate(long companyID, java.util.Date endDate) throws CouponException;

	public abstract void addCouponToCompany(long companyID, long couponID) throws CouponException;
	
	public abstract void addCouponToCustomer(long customerID, long couponID) throws CouponException;
	
	public abstract Coupon updatableCoupon(Coupon coupon) throws CouponException;
}