package DAODBlevel;

import cpCore.*;
import java.sql.*;
import java.util.*;
import cpCore.beans.*;
import DAO.AbstractLevel.CustomerDAO;

public class CustomerDBDAO implements CustomerDAO {

	Statement statement;

	public CustomerDBDAO() {
		statement = new Statement("customer", "ID", "cust_name", "password");
	}

	/** create the customer in the database */
	public long createCustomer(Customer customer) throws CouponException {
		Connection cn = null;
		Long ID = -1l;
		try {
			if ((customer.getCustName().equals("")) || (customer.getPassword().equals("")))
				throw new CouponException("insuffisantData");
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt = cn.prepareStatement(statement.create());
			pstt.setString(1, customer.getCustName());
			pstt.setString(2, customer.getPassword());
			pstt.execute();
			pstt = cn.prepareStatement(statement.returnLastCreatedID());
			ResultSet rs = pstt.executeQuery();
			if (rs.next()) {
				ID = rs.getLong(1);
			}
		} catch (SQLException e) {
			if (e.getSQLState().equals("23505"))
				throw new CouponException("duplicate", e);
			throw new CouponException("exception creating customer", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return ID;
	}

	/** remove customer based on customer id */
	public void removeCustomer(long customerID) throws CouponException {
		Connection cn = null;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt;
			pstt = cn.prepareStatement(statement.deleteByID());
			pstt.setLong(1, customerID);
			pstt.execute();
		} catch (SQLException e) {
			throw new CouponException("exception creating customer", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
	}

	/** update info to customer with same id */
	public void updateCustomer(Customer customer) throws CouponException {
		Connection cn = null;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt = cn.prepareStatement(statement.update());
			pstt.setString(1, customer.getPassword());
			pstt.setLong(2, customer.getId());
			pstt.execute();
		} catch (SQLException e) {
			throw new CouponException("exception creating customer", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
	}

	/** return the customer with the provided id */
	public Customer getCustomer(long customerID) throws CouponException {
		Connection cn = null;
		Customer cp = new Customer();
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt = cn.prepareStatement(statement.selectByID());
			pstt.setLong(1, customerID);
			ResultSet rs = pstt.executeQuery();
			if (rs.next()) {
				cp.setId(rs.getLong(1));
				cp.setCustName(rs.getString(2));
				cp.setPassword(rs.getString(3));
				cp.setCoupons(new CouponDBDAO().getCouponByCustomerID(cp.getId()));
			}
		} catch (SQLException e) {
			throw new CouponException("exception creating customer", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return cp;
	}

	/** return a list of customer in the database */
	public List<Customer> getAllCustomer() throws CouponException {
		Connection cn = null;
		List<Customer> customers = new ArrayList<>();
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt = cn.prepareStatement(statement.selectAll());
			ResultSet rs = pstt.executeQuery();
			while (rs.next()) {
				Customer cp = new Customer();
				cp.setId(rs.getLong(1));
				cp.setCustName(rs.getString(2));
				cp.setPassword(rs.getString(3));
				cp.setCoupons(new CouponDBDAO().getCouponByCustomerID(cp.getId()));
				customers.add(cp);
			}
		} catch (SQLException e) {
			throw new CouponException();
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return customers;
	}

	/** return the customer id based in his name */
	public long getCustomerID(String customerName) throws CouponException {
		Connection cn = null;
		long id = -1;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = null;
			ResultSet rs = null;
			pst = cn.prepareStatement(statement.GetIDbyName());
			pst.setString(1, customerName);
			rs = pst.executeQuery();
			if (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (SQLException e) {
			throw new CouponException("error getting customer id", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return id;
	}

	/** login method returning true if password is correct */
	public boolean login(String customerName, String password) throws CouponException {
		Connection cn = null;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = null;
			ResultSet rs = null;
			pst = cn.prepareStatement(statement.getPassword());
			pst.setString(1, customerName);
			rs = pst.executeQuery();
			if (rs.next()) {
				String ps = rs.getString(1);
				if (ps.equals(password)) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw new CouponException("error while trying to login", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return false;
	}

	@Override
	public Customer updatableCustomer(Customer customer) throws CouponException {
		if(customer == null) return null;
		Customer customerOriginal = null;
		//searching customer id
		if(customer.getId() == 0){
			if(customer.getCustName() != null && !customer.getCustName().equals("")) {
				customerOriginal = getCustomer(getCustomerID(customer.getCustName()));
				customer.setId(customerOriginal.getId());
			}
			else return null;
		}else {
			customerOriginal = getCustomer(customer.getId());
			if(customerOriginal==null) return null;
		}

		//setting password to update
		if(customer.getPassword() == null || customer.getPassword().equals("")){
			customer.setPassword(customerOriginal.getPassword());}
		
		//returning
		return customer;
	}

}