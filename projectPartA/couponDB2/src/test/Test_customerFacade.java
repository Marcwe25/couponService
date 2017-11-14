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

public class Test_customerFacade {
	public static void main(String[] args) throws CouponException {

		
		//-----create database (using class DButility)-----//
		
//		DButility.createDataBase();
//		DButility.addTables();

		//-----testing customer facade command-----//
		
		/*0 log in*/
		
		CouponSystem cs = CouponSystem.getInstance();
		CustomerFacade custFacade = (CustomerFacade) cs.login("marc", "100", "customer");
		
		/*1 get all customer coupon by price*/
		
//		System.out.println(custFacade.getAllCouponByPrice(400.0));
		
		/*2 get all customer coupon by type*/

//		System.out.println(custFacade.getAllCouponByType(CouponType.store));
		
		/*3 get all customer coupon*/
		
//		System.out.println(custFacade.getAllPurchasedCoupon());
		
		/*4 purchase a coupon*/
		
//		System.out.println(custFacade.getAllPurchasedCoupon());
//		custFacade.purchaceCoupon(7);
//		System.out.println(custFacade.getAllPurchasedCoupon());
		
		/*5 shutting down coupon system*/

//		cs.shutdown();
	}
}
