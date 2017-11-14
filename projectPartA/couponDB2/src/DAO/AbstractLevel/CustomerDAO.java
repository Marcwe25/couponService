package DAO.AbstractLevel;

import java.util.List;

import cpCore.CouponException;
import cpCore.beans.Customer;

public interface CustomerDAO extends DAO{

	public abstract long createCustomer(Customer customer) throws CouponException;
	
	public abstract void removeCustomer(long customerID) throws CouponException;
	
	public abstract void updateCustomer(Customer customer) throws CouponException;

	public abstract Customer getCustomer(long id) throws CouponException;

	public abstract List<Customer> getAllCustomer() throws CouponException;
	
	public abstract boolean login(String CustomerName, String password) throws CouponException;

	public abstract long getCustomerID(String customerName) throws CouponException;
	
	public abstract Customer updatableCustomer(Customer customer)  throws CouponException;
}
