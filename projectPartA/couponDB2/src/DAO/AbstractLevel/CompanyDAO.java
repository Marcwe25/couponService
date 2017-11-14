package DAO.AbstractLevel;

import java.util.List;

import cpCore.CouponException;
import cpCore.beans.Company;

public interface CompanyDAO extends DAO{

	public abstract  long createCompany(Company company) throws CouponException;
	
	public abstract void removeCompany(Long companyID) throws CouponException;
	
	public abstract void updateCompany(Company company) throws CouponException;

	public abstract Company getCompany(Long id) throws CouponException;

	public abstract List<Company> getAllCompany() throws CouponException;
	
	public abstract long getCompanyID(String companyName) throws CouponException;
	
	public abstract boolean login(String companyName, String passwor) throws CouponException;
	
	public abstract Company updatableCompany(Company company) throws CouponException;

}