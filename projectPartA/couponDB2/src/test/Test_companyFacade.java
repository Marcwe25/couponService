package test;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import DAODBlevel.CouponDBDAO;
import cpCore.ConnectionPool;
import cpCore.CouponException;
import cpCore.DButility;
import cpCore.beans.Company;
import cpCore.beans.Coupon;
import cpCore.beans.CouponType;
import cpCore.beans.Customer;
import entry.CouponSystem;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

public class Test_companyFacade {
	public static void main(String[] args) throws CouponException {

		
		//-----create database (using class DButility)-----//
		
//		DButility.createDataBase();
//		DButility.addTables();

		//-----testing company facade command-----//
		
		/*0 log in*/
		
		CouponSystem cs = CouponSystem.getInstance();
		CompanyFacade compFacade = (CompanyFacade) cs.login("cola", "123", "company");
		
		/*1 create coupon for the company logged in*/
		
//		Coupon coupon = new Coupon();
//		coupon.setTitle("holidays in paris");
//		Calendar calendar = Calendar.getInstance();
//		Date start_date = new Date();
//		calendar.set(2017, 8, 12);
//		Date end_date = new Date(calendar.getTimeInMillis());
//		coupon.setEnd_date(end_date);
//		coupon.setStart_date(start_date);
//		coupon.setPrice(1000);
//		coupon.setAmount(10);
//		coupon.setType(CouponType.holidays);
//		compFacade.createCoupon(coupon);
		
//		Coupon coupon = new Coupon();
//		coupon.setTitle("eat well");
//		Calendar calendar = Calendar.getInstance();
//		Date start_date = new Date();
//		calendar.set(2017, 8, 12);
//		Date end_date = new Date(calendar.getTimeInMillis());
//		coupon.setEnd_date(end_date);
//		coupon.setStart_date(start_date);
//		coupon.setPrice(1000);
//		coupon.setAmount(10);
//		coupon.setType(CouponType.entertainment);
//		compFacade.createCoupon(coupon);
		
		/*2 get coupon from company*/
		
//		System.out.println(compFacade.getCompanyCoupon());
		
		/*3 get company coupon by coupon id*/
		
//		System.out.println(compFacade.getCompanyCouponById(6));

		/*4 get coupon from company by type*/
		
//		System.out.println(compFacade.getCompanyCouponByType(CouponType.entertainment));
		
		/*5 get coupon from company cheaper then...*/
		
//		System.out.println(compFacade.getCompanyCouponCheaperThen(500));
		
		/*6 get coupon from company ending before...*/
		
//		Calendar cal = Calendar.getInstance();
//		cal.set(2019,1,1);
//		Date endDate = new Date(cal.getTimeInMillis());
//		System.out.println(compFacade.getCompanyCouponEndingBefore(endDate));
//		
		/*7 update company coupon*/
		
//		System.out.println(compFacade.getCompanyCoupon());
//		Coupon coupon = new Coupon();
//		coupon.setTitle("eat well");
//		Calendar calendar = Calendar.getInstance();
//		Date start_date = new Date();
//		calendar.set(2021, 8, 12);
//		Date end_date = new Date(calendar.getTimeInMillis());
//		coupon.setEnd_date(end_date);
//		coupon.setStart_date(start_date);
//		coupon.setPrice(1000);
//		coupon.setAmount(10);
//		coupon.setType(CouponType.entertainment);
//		compFacade.updateCoupon(coupon);
//		System.out.println(compFacade.getCompanyCoupon());

		/*8 get company detail*/
		
//		System.out.println(compFacade.getCompanyDetail());
		
		/*9 remove company coupon*/
		
//		compFacade.removeCoupon(6);
		
		/*10 shutting down coupon system*/

//		cs.shutdown();
	}
}
