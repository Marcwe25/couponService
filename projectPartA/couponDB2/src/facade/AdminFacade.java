package facade;

import DAODBlevel.*;
import cpCore.beans.*;
import DAO.AbstractLevel.*;

import java.util.Date;
import java.util.List;
import cpCore.CouponException;

public class AdminFacade implements Facade{
	
	private CouponDAO coupDAO;
	private CompanyDAO compDAO;
	private CustomerDAO custDAO;
	
	private AdminFacade(){
		coupDAO = new CouponDBDAO();
		compDAO = new CompanyDBDAO();
		custDAO = new CustomerDBDAO();
	}
	
	public static AdminFacade login(String username, String password){
		if(password.equals("123") && username.equals("admin")){
			return new AdminFacade();
		}
		else return null;
	}
	
	/**Create company in the database*/
	public long createCompany(Company company) throws CouponException {
		return compDAO.createCompany(company);
	}
	
	/**fill uncompleted information*/
	public Company updatableCompany(Company company) throws CouponException{
		return compDAO.updatableCompany(company);
			}
		
	/**update company information (email and password if provided).
	 * the name is not updated*/
	public void updateCompany(Company company) throws CouponException{
		compDAO.updateCompany(company);
	}

	/**remove company from database by id and his coupons*/
	public void removeCompany(long companyID) throws CouponException {
		List<Coupon> coupons = coupDAO.getCouponByCompany(companyID);
		for (Coupon coupon : coupons) {
			long id = coupon.getId();
			removeCoupon(id);
		}
		compDAO.removeCompany(companyID);
	}

	/**return a collection containing all the company in the database.
	 * @return Collection<Company>
	 * @throws CouponException
	 */
	public List<Company> getAllCompany() throws CouponException{
		return compDAO.getAllCompany();
	}

	/**return a company by id*/
	public Company getCompany(long companyId) throws CouponException {
		Company company = compDAO.getCompany(companyId);
		return company;
	}

	/**create a customer in the database*/ 
	public long createCustomer (Customer customer) throws CouponException{
		return custDAO.createCustomer(customer);
	}
	
	
	/**complete missing information*/
	public Customer updatableCustomer (Customer customer) throws CouponException{
		return custDAO.updatableCustomer(customer);
	}
	
	/**update customer information (password).
	 * the name is not updated*/
	public void updateCustomer (Customer customer) throws CouponException{
		custDAO.updateCustomer(customer);
	}

	/**remove a customer and his coupon from database*/
	public void removeCustomer (long customerID) throws CouponException{
		List<Coupon> coupons = coupDAO.getCouponByCustomerID(customerID);
		for (Coupon coupon : coupons){
			long id = coupon.getId();
			coupDAO.removeFromCustomerCoupon(id);
		}
		custDAO.removeCustomer(customerID);
	}

	public List<Customer> getAllCustomer() throws CouponException{
		return custDAO.getAllCustomer();
	}

	public Customer getCustomer(long id) throws CouponException{
		return custDAO.getCustomer(id);
	}

	/**create a coupon in the database */
	public long createCoupon (Coupon coupon) throws CouponException{
		return coupDAO.createCoupon(coupon);
	}
	
	/**fill empty field in coupon with existing data*/
	public Coupon updatableCoupon(Coupon coupon) throws CouponException{
		return coupDAO.updatableCoupon(coupon);
	}
	
	/**update coupon information (end date and price).*/
	public void updateCoupon (Coupon coupon) throws CouponException{
		coupDAO.updateCoupon(coupon);
	}

	/**remove a coupon from the database by id*/
	public void removeCoupon (long couponID) throws CouponException{
		coupDAO.removeFromCustomerCoupon(couponID);
		coupDAO.removeFromCompanyCoupon(couponID);
		coupDAO.removeCoupon(couponID);
	}
	
	public List<Coupon> getAllCoupon() throws CouponException{
		return coupDAO.getAllCoupon();
	}

	public Coupon getCoupon(long id) throws CouponException{
		return coupDAO.getCoupon(id);
	}
	
	public boolean duplicateCoupon(String title) throws CouponException{
		return coupDAO.getCouponByTitle(title)!=null;
	}

	public boolean duplicateCompany(String name) throws CouponException{
		return compDAO.getCompanyID(name)>=	0;
	}

	public boolean duplicateCustomer(String name) throws CouponException{
		return custDAO.getCustomerID(name)>=0;
	}

	
}
