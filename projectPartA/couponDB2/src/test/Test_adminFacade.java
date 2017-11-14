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

public class Test_adminFacade {
	public static void main(String[] args) throws CouponException {

		
		//-----create database (using class DButility)-----//
		
//		DButility.createDataBase();
//		DButility.addTables();

		//-----testing administrator facade command-----//
		
		/*0 log in*/
		
//		CouponSystem cs = CouponSystem.getInstance();
//		AdminFacade adminFacade = (AdminFacade) cs.login("admin", "123", "admin");
		
		/*1 create company*/
		
//		Company company = new Company();
//		company.setPassword("123");
//		company.setName("cola");
//		adminFacade.createCompany(company);
		
		/*2 update company by id, automatically filling missing data*/
		
//		Company company = new Company();
//		company.setId(101);
//		company.setEmail("marc@cola.com");;
//		adminFacade.updateCompany(company);

		/*3 update company with no id automatically filling missing data*/
		
//		Company company = new Company();
//		company.setName("cola");
//		company.setEmail("nica@cola.com");
//		adminFacade.updateCompany(company);
		
		/*4 getting all company or by id*/
		
//		System.out.println(adminFacade.getAllCompany());
//		System.out.println(adminFacade.getCompany(1));

		/*5 create customer*/
		
//		Customer customer = new Customer();
//		customer.setCustName("asaf");
//		customer.setPassword("456");
//		adminFacade.createCustomer(customer);

		/*6 update customer with no id*/
		
//		Customer customer = new Customer();
//		customer.setCustName("marc");
//		customer.setPassword("100");
//		adminFacade.updateCustomer(customer);
		
		/*7 update customer by id*/
		
//		Customer customer = new Customer();
//		customer.setId(3);
//		customer.setPassword("abc");
//		adminFacade.updateCustomer(customer);
		
		/*8 getting all customers or by id*/
		
//		System.out.println(adminFacade.getAllCustomer());
//		System.out.println(adminFacade.getCustomer(102));
		
		/*9 remove company by id*/
		
//		System.out.println(adminFacade.getAllCompany());
//		adminFacade.removeCompany(1);
//		System.out.println(adminFacade.getAllCompany());
//		cs.shutdown();
		
		/*10 remove customer by id*/
		
//		System.out.println(adminFacade.getAllCustomer());
//		adminFacade.removeCustomer(103);
//		System.out.println(adminFacade.getAllCustomer());
		
		/*11 create coupon*/
		
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
//		adminFacade.createCoupon(coupon);
		
		/*12 update coupon*/
		
//		calendar.set(2018, 8,12);
//		end_date = new Date(calendar.getTimeInMillis());
//		System.out.println(adminFacade.getAllCoupon());
//		coupon.setEnd_date(end_date);
//		adminFacade.updateCoupon(coupon);
//		System.out.println(adminFacade.getAllCoupon());
		
		/*13 removing coupon*/
		
//		adminFacade.removeCoupon(1);
//		System.out.println(adminFacade.getAllCoupon());
		
		/*14 getting all coupon or by id*/
		
//		System.out.println(adminFacade.getAllCoupon());
//		System.out.println(adminFacade.getCoupon(3));
		
		/*15 shutting down coupon system*/
		
//		cs.shutdown();
		
		AdminFacade fa = AdminFacade.login("admin", "123");
		Coupon cp = new Coupon();
		cp.setId(5222);
//		cp.setTitle("merco food");
//		System.out.println(cp);
		cp = fa.updatableCoupon(cp);
		System.out.println(cp);
		
//		Company cp = new Company();
//		cp.setName("merco");
//		cp.setId(822);
//		System.out.println(cp);
//		cp = fa.updatableCompany(cp);
//		System.out.println(cp);
		
//		CouponSystem cs = CouponSystem.getInstance();
//		AdminFacade fa = (AdminFacade) cs.login("admin", "123", "admin");
//		fa.getAllCoupon();
//		try {
//			cs.shutdown();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
