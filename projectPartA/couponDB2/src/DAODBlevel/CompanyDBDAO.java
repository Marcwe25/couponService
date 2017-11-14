package DAODBlevel;

import java.sql.*;
import java.util.*;
import cpCore.*;
import DAO.AbstractLevel.CompanyDAO;
import cpCore.beans.Company;

public class CompanyDBDAO implements CompanyDAO{
	
	Statement statement;
	
	public CompanyDBDAO(){
		statement= new Statement("company","id","comp_name","password","email");
	}

	/**add the provided company to the database,
	 *  and return the generated id*/
	public  long createCompany(Company company) throws CouponException{
		Connection cn = null;
		Long ID=-1l;
		try {
			if((company.getName().equals(""))||(company.getPassword().equals(""))) throw new NullPointerException();
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt = cn.prepareStatement(statement.create());
			pstt.setString(1, company.getName());
			pstt.setString(2, company.getPassword());
			pstt.setString(3, company.getEmail());
			pstt.execute();
			pstt = cn.prepareStatement(statement.returnLastCreatedID());
			ResultSet rs = pstt.executeQuery();
			if(rs.next()){ID = rs.getLong(1);}
		} catch (NullPointerException e){
			throw new CouponException("insuffisant data");
		} catch (SQLException e) {
			if(e.getSQLState().equals("23505"))throw new CouponException("duplicate",e);
			throw new CouponException("exception creating company",e);
		} finally{
			ConnectionPool.returnCon(cn);			
		}
		return ID;
	}

	/**remove from database the company matching provided ID*/
	public void removeCompany(Long companyID) throws CouponException {
		Connection cn = null;
		PreparedStatement pstt;
		try {
			cn = ConnectionPool.getConnection();
			pstt = cn.prepareStatement(statement.deleteByID());
			pstt.setLong(1, companyID);;
			pstt.execute();
		} catch (SQLException e) {
			throw new CouponException("exception removing company",e);
		} finally{
			ConnectionPool.returnCon(cn);			
		}

	}
		
	/**update the company with matching ID to the given information except the name*/
	public void updateCompany(Company company) throws CouponException{
		Connection cn = null;
		try {
			if (company.getId()==0){throw new CouponException("--->no company id defined");}
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt = cn.prepareStatement(statement.update());
			pstt.setString(1, company.getPassword());
			pstt.setString(2, company.getEmail());
			pstt.setLong(3, company.getId());
			pstt.execute();
		} catch (SQLException e) {
			throw new CouponException("exception updating company",e);
		} finally{
			ConnectionPool.returnCon(cn);			
		}

	}

	/**return the company matching the provided ID*/
	public Company getCompany(Long id) throws CouponException {
		Company cp = new Company();
		ResultSet rs = null;
		Connection cn = null;
		try {
			cn= ConnectionPool.getConnection();
			PreparedStatement pstt = cn.prepareStatement(statement.selectByID());
			pstt.setLong(1, id);
			rs = pstt.executeQuery();
			if (rs.next()){
			cp.setId(rs.getLong(1));
			cp.setName(rs.getString(2));
			cp.setPassword(rs.getString(3));
			cp.setEmail(rs.getString(4));
			cp.setCoupons(new CouponDBDAO().getCouponByCompany(cp.getId()));
			}
		} catch (SQLException e) {
			throw new CouponException("exception getting company",e);
		} finally{
			ConnectionPool.returnCon(cn);			
		}
		return cp;
	}

	/**return a list of all company*/
	public List<Company> getAllCompany() throws CouponException{
		ResultSet rs = null;
		List<Company> cc = new ArrayList<>();
		Connection cn = null;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt = cn.prepareStatement(statement.selectAll());
			rs = pstt.executeQuery();
			while (rs.next()) {
				Company cp = new Company();
				cp.setId(rs.getLong(1));
				cp.setName(rs.getString(2));
				cp.setPassword(rs.getString(3));
				cp.setEmail(rs.getString(4));
				cp.setCoupons(new CouponDBDAO().getCouponByCompany(cp.getId()));
				cc.add(cp);
			}
		} catch (SQLException e) {
			throw new CouponException("exception getting all companies",e);
		} finally{
			ConnectionPool.returnCon(cn);			
		}
	return cc;
	}

	/**return the id of the company with same name*/
	public  long getCompanyID(String companyName) throws CouponException{
		Connection cn = null;
		long id = -1;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = null;
			ResultSet rs = null;
			pst = cn.prepareStatement(statement.GetIDbyName());
			pst.setString(1, companyName);
			rs=pst.executeQuery();
			if(rs.next()){
				id = rs.getLong(1);
			}
			
		} catch (SQLException e) {
			throw new CouponException("exception getting company",e);
		} finally{
			ConnectionPool.returnCon(cn);			
		}
		return id;
	}

	/**login method returning true if password is correct*/
	public boolean login(String companyName, String password) throws CouponException {
		Connection cn = null;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = null;
			ResultSet rs = null;
			pst = cn.prepareStatement(statement.getPassword());
			pst.setString(1, companyName);
			rs=pst.executeQuery();
			if(rs.next()){
				String ps = rs.getString(1);
				if (ps.equals(password)) {
					return true;
				}
			}
			
		} catch (SQLException e) {
			throw new CouponException("error while trying to login",e);
		} finally{
			ConnectionPool.returnCon(cn);			
		}
		return false;
	}

	@Override
	public Company updatableCompany(Company company) throws CouponException {
		if(company==null) return null;
		Company companyOriginal = null;
		//searching for id
		if(company.getId()==0){
			if(company.getName() != null && !company.getName().equals("")){
				companyOriginal = getCompany(getCompanyID(company.getName()));
				company.setId(companyOriginal.getId());}
			else return null;
		}else {
			companyOriginal = getCompany(company.getId());
			if(companyOriginal==null)return null;
			company.setName(companyOriginal.getName());}
		
//		searching for password
		if(company.getPassword() == null || company.getPassword().equals("")){
			company.setPassword(companyOriginal.getPassword());}
			
//		searching for email
		if(company.getEmail() == null || company.getEmail().equals("")){
			company.setEmail(companyOriginal.getEmail());}
		
		//returning
		return company;
		}
}
