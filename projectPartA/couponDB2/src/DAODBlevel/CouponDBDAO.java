package DAODBlevel;

import cpCore.*;
import java.sql.*;
import java.util.*;
import cpCore.beans.*;
import DAO.AbstractLevel.CouponDAO;

public class CouponDBDAO implements CouponDAO {
	private String selectByType = "SELECT * FROM coupon WHERE type = ?";

	private String selectByTitle = " SELECT * FROM coupon WHERE title = ?";

	private String getCompanyIDbyCouponID = "SELECT comp_id FROM company_coupon WHERE coupon_id = ?";

	private String updateCoupon = "UPDATE COUPON SET end_date = ? , price = ? WHERE id = ?";

	private String updateCouponAmount = "UPDATE COUPON SET amount = amount + ? WHERE id = ?";

	private String selectionParameter = "coupon.id,coupon.title,coupon.start_date , coupon.end_date,coupon.amount,coupon.coupon_type,coupon.message,coupon.price, coupon.image ";

	private String fromCompanyCoupon = "FROM company_coupon INNER JOIN company ON company.id= company_coupon.comp_id JOIN coupon ON company_coupon.coupon_id = coupon.id WHERE company.id = ?";

	private String selectAllCouponByCompany = "SELECT " + selectionParameter + fromCompanyCoupon;

	private String selectAllCouponByCompanyByType = selectAllCouponByCompany + " AND coupon.coupon_type=?";

	private String selectAllCouponByCompanyCheaperThan = selectAllCouponByCompany + " AND price <= ?";

	private String selectAllCouponByCompanyByEndDate = selectAllCouponByCompany + " AND coupon.end_date<= ?";

	private String fromCustomerCoupon = "FROM customer_coupon INNER JOIN customer ON customer.id= customer_coupon.cust_id JOIN coupon ON customer_coupon.coupon_id = coupon.id WHERE customer.id = ?";

	private String selectAllCouponByCustomerID = "SELECT " + selectionParameter + fromCustomerCoupon;

	private String getCouponToBuyByCustomerID = "select * from coupon where id not in (select coupon_id from customer_coupon where cust_id = ?)";

	private String selectAllCouponByCustomerIDCheaperThan = selectAllCouponByCustomerID + " AND price <= ?";

	private String selectAllCouponByCustomerIDByType = selectAllCouponByCustomerID + " AND coupon.coupon_type=?";

	private String AddCouponToCompany = "INSERT INTO company_coupon VALUES (?,?)";

	private String AddCouponToCustomer = "INSERT INTO customer_coupon VALUES (?,?)";

	Statement coupSt;
	Statement coupCompSt;
	Statement coupCust;

	public CouponDBDAO() {
		coupSt = new Statement("coupon", "id", "title", "start_date", "end_date", "amount", "coupon_type", "message",
				"price", "image");
		coupCompSt = new Statement("Company_Coupon", "coupon_id", "comp_id");
		coupCust = new Statement("Customer_Coupon", "coupon_id", "comp_id");
	}

	/**
	 * ad the provided coupon to the database, and return the generated id
	 */
	public long createCoupon(Coupon coupon) throws CouponException {
		System.out.println("1" + " dbdao create coupon" + "starting");
		Long ID = -1l;
		Connection cn = null;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt = cn.prepareStatement(coupSt.create());
			pstt.setString(1, coupon.getTitle());
			pstt.setDate(2, new java.sql.Date(coupon.getStart_date().getTime()));
			pstt.setDate(3, new java.sql.Date(coupon.getEnd_date().getTime()));
			pstt.setInt(4, coupon.getAmount());
			pstt.setString(5, coupon.getType().toString());
			pstt.setString(6, coupon.getMessage());
			pstt.setDouble(7, coupon.getPrice());
			pstt.setString(8, coupon.getImage());
			pstt.execute();
			pstt = cn.prepareStatement(coupSt.returnLastCreatedID());
			ResultSet rs = pstt.executeQuery();
			if (rs.next()) {
				ID = rs.getLong(1);
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new CouponException("insuffisant data while creating coupon", e);
		} catch (SQLException e) {
			e.printStackTrace();
			if (e.getSQLState().equals("23505"))
				throw new CouponException("duplicate", e);
			throw new CouponException("exception creating coupon", e);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.returnCon(cn);
		}

		return ID;
	}

	/** remove coupon with matching id */
	public void removeCoupon(long couponID) throws CouponException {
		Connection cn = null;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt;
			pstt = cn.prepareStatement(coupSt.deleteByID());
			pstt.setLong(1, couponID);
			pstt.execute();
		} catch (SQLException e) {
			throw new CouponException("exception removing coupon", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}

	}

	/** remove coupon from company_coupon table based on provided coupon id */
	public void removeFromCompanyCoupon(long couponID) throws CouponException {
		Connection cn = null;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt;
			pstt = cn.prepareStatement(coupCompSt.deleteByID());
			pstt.setLong(1, couponID);
			;
			pstt.execute();
		} catch (SQLException e) {
			throw new CouponException("exception removing coupon", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
	}

	/** remove coupon from customer_coupon table based on provided coupon id */
	public void removeFromCustomerCoupon(long couponID) throws CouponException {
		Connection cn = null;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt;
			pstt = cn.prepareStatement(coupCust.deleteByID());
			pstt.setLong(1, couponID);
			;
			pstt.execute();
		} catch (SQLException e) {
			throw new CouponException("exception removing coupon from customer's coupon", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
	}

	/** update coupon with matching id with provided info */
	public void updateCoupon(Coupon coupon) throws CouponException {
		Connection cn = null;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt = cn.prepareStatement(updateCoupon);
			pstt.setDate(1, new java.sql.Date(coupon.getEnd_date().getTime()));
			pstt.setDouble(2, coupon.getPrice());
			pstt.setLong(3, coupon.getId());
			pstt.execute();
		} catch (SQLException e) {
			throw new CouponException("error updating coupon", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
	}

	/** update coupon amount */
	public void updateCouponAmount(int amount, long couponID) throws CouponException {
		Connection cn = null;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt = cn.prepareStatement(updateCouponAmount);
			pstt.setInt(1, amount);
			pstt.setLong(2, couponID);
			pstt.execute();

		} catch (SQLException e) {
			throw new CouponException("error updating coupon", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
	}

	/**
	 * return all coupon
	 * 
	 * @throws CouponException
	 */
	public List<Coupon> getAllCoupon() throws CouponException {
		Connection cn = null;
		List<Coupon> cc = new ArrayList<>();
		try {
			ResultSet rs = null;
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt = cn.prepareStatement(coupSt.selectAll());
			rs = pstt.executeQuery();
			while (rs.next()) {
				Coupon cp = new Coupon();
				cp.setId(rs.getLong(1));
				cp.setTitle(rs.getString(2));
				cp.setStart_date(rs.getDate(3));
				cp.setEnd_date(rs.getDate(4));
				cp.setAmount(rs.getInt(5));
				cp.setType(CouponType.valueOf(rs.getString(6)));
				cp.setMessage(rs.getString(7));
				cp.setPrice(rs.getDouble(8));
				cp.setImage(rs.getString(9));
				cc.add(cp);
			}
		} catch (SQLException e) {
			throw new CouponException("exception getting all coupon", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return cc;
	}

	/** return company id owning the coupon based on coupon id */
	public long getCompanyIDbyCouponID(long couponID) throws CouponException {
		Connection cn = null;
		long companyID = -1;
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = cn.prepareStatement(getCompanyIDbyCouponID);
			pst.setLong(1, couponID);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				companyID = rs.getLong(1);
			}
		} catch (SQLException e) {
			throw new CouponException("error getting company id", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return companyID;
	}

	/** return coupon with matching id */
	public Coupon getCoupon(long couponID) throws CouponException {
		Connection cn = null;
		Coupon cp = null;
		try {
			ResultSet rs = null;
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt;
			pstt = cn.prepareStatement(coupSt.selectByID());
			pstt.setLong(1, couponID);
			rs = pstt.executeQuery();
			if (rs.next()) {
				cp = new Coupon();
				cp.setId(rs.getLong(1));
				cp.setTitle(rs.getString(2));
				cp.setStart_date(rs.getDate(3));
				cp.setEnd_date(rs.getDate(4));
				cp.setAmount(rs.getInt(5));
				cp.setType(CouponType.valueOf(rs.getString(6)));
				cp.setMessage(rs.getString(7));
				cp.setPrice(rs.getDouble(8));
				cp.setImage(rs.getString(9));

			}
		} catch (SQLException e) {
			throw new CouponException("error getting coupon", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return cp;
	}

	/** return coupon with matching id */
	public Coupon getCouponByTitle(String title) throws CouponException {
		Connection cn = null;
		Coupon cp = null;
		try {
			ResultSet rs = null;
			cn = ConnectionPool.getConnection();
			PreparedStatement pstt;
			pstt = cn.prepareStatement(selectByTitle);
			pstt.setString(1, title);
			rs = pstt.executeQuery();
			if (rs.next()) {
				cp = new Coupon();
				cp.setId(rs.getLong(1));
				cp.setTitle(rs.getString(2));
				cp.setStart_date(rs.getDate(3));
				cp.setEnd_date(rs.getDate(4));
				cp.setAmount(rs.getInt(5));
				cp.setType(CouponType.valueOf(rs.getString(6)));
				cp.setMessage(rs.getString(7));
				cp.setPrice(rs.getDouble(8));
				cp.setImage(rs.getString(9));
			}
		} catch (SQLException e) {
			throw new CouponException("error getting coupon", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return cp;
	}

	/** return a list of coupon by company id */
	public List<Coupon> getCouponByCompany(long companyID) throws CouponException {
		Connection cn = null;
		ResultSet rs = null;
		List<Coupon> ac = new ArrayList<>();
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = cn.prepareStatement(selectAllCouponByCompany);
			pst.setLong(1, companyID);
			rs = pst.executeQuery();
			while (rs.next()) {
				Coupon cp = new Coupon();
				cp.setId(rs.getLong(1));
				cp.setTitle(rs.getString(2));
				cp.setStart_date(rs.getDate(3));
				cp.setEnd_date(rs.getDate(4));
				cp.setAmount(rs.getInt(5));
				String type = rs.getString(6);
				if (type != null) {
					cp.setType(CouponType.valueOf(type));
				}
				cp.setMessage(rs.getString(7));
				cp.setPrice(rs.getDouble(8));
				cp.setImage(rs.getString(9));
				ac.add(cp);
			}
		} catch (SQLException e) {
			throw new CouponException("exception getting company coupons", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}

		return ac;
	}

	/** return a list of coupon by company id and coupon type */
	public ArrayList<Coupon> getCouponByCompanyIDandByType(long companyID, CouponType coupontype)
			throws CouponException {
		Connection cn = null;
		ResultSet rs = null;
		ArrayList<Coupon> ac = new ArrayList<>();
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = cn.prepareStatement(selectAllCouponByCompanyByType);
			pst.setLong(1, companyID);
			pst.setString(2, coupontype.toString());
			rs = pst.executeQuery();
			while (rs.next()) {
				Coupon cp = new Coupon();
				cp.setId(rs.getLong(1));
				cp.setTitle(rs.getString(2));
				cp.setStart_date(rs.getDate(3));
				cp.setEnd_date(rs.getDate(4));
				cp.setAmount(rs.getInt(5));
				cp.setType(CouponType.valueOf(rs.getString(6)));
				cp.setMessage(rs.getString(7));
				cp.setPrice(rs.getDouble(8));
				cp.setImage(rs.getString(9));
				ac.add(cp);
			}
		} catch (SQLException e) {
			throw new CouponException("exception getting coupon by type for company", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return ac;
	}

	/**
	 * return a list of coupon by company id and cheaper than the provided price
	 */
	public ArrayList<Coupon> getCouponByCompanyIDCheaperthan(long companyID, double couponPrice)
			throws CouponException {
		Connection cn = null;
		ResultSet rs = null;
		ArrayList<Coupon> ac = new ArrayList<>();
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = cn.prepareStatement(selectAllCouponByCompanyCheaperThan);
			pst.setLong(1, companyID);
			pst.setDouble(2, couponPrice);
			rs = pst.executeQuery();
			while (rs.next()) {
				Coupon cp = new Coupon();
				cp.setId(rs.getLong(1));
				cp.setTitle(rs.getString(2));
				cp.setStart_date(rs.getDate(3));
				cp.setEnd_date(rs.getDate(4));
				cp.setAmount(rs.getInt(5));
				cp.setType(CouponType.valueOf(rs.getString(6)));
				cp.setMessage(rs.getString(7));
				cp.setPrice(rs.getDouble(8));
				cp.setImage(rs.getString(9));
				ac.add(cp);
			}
		} catch (SQLException e) {
			throw new CouponException("exception getting coupon by price for company", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return ac;
	}

	/**
	 * return a list of coupon by company id and cheaper than the provided price
	 */
	public ArrayList<Coupon> getCouponByCompanyIDByEnDate(long companyID, java.util.Date endDate) throws CouponException {
		Connection cn = null;
		ArrayList<Coupon> ac = new ArrayList<>();
		try {
			ResultSet rs = null;
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = cn.prepareStatement(selectAllCouponByCompanyByEndDate);
			pst.setLong(1, companyID);
			pst.setDate(2, new java.sql.Date(endDate.getTime()));
			rs = pst.executeQuery();
			while (rs.next()) {
				Coupon cp = new Coupon();
				cp.setId(rs.getLong(1));
				cp.setTitle(rs.getString(2));
				cp.setStart_date(rs.getDate(3));
				cp.setEnd_date(rs.getDate(4));
				cp.setAmount(rs.getInt(5));
				cp.setType(CouponType.valueOf(rs.getString(6)));
				cp.setMessage(rs.getString(7));
				cp.setPrice(rs.getDouble(8));
				cp.setImage(rs.getString(9));
				ac.add(cp);
			}
		} catch (SQLException | NullPointerException e) {
			throw new CouponException("exception getting coupon by date for company", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return ac;
	}

	/** return a list of coupon by customer id */
	public List<Coupon> getCouponByCustomerID(long customerID) throws CouponException {
		Connection cn = null;
		ArrayList<Coupon> ac = new ArrayList<>();
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = cn.prepareStatement(selectAllCouponByCustomerID);
			pst.setLong(1, customerID);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Coupon cp = new Coupon();
				cp.setId(rs.getLong(1));
				cp.setTitle(rs.getString(2));
				cp.setStart_date(rs.getDate(3));
				cp.setEnd_date(rs.getDate(4));
				cp.setAmount(rs.getInt(5));
				cp.setType(CouponType.valueOf(rs.getString(6)));
				cp.setMessage(rs.getString(7));
				cp.setPrice(rs.getDouble(8));
				cp.setImage(rs.getString(9));
				ac.add(cp);
			}
		} catch (SQLException e) {
			throw new CouponException("exception getting customer coupon", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return ac;
	}

	/** return a list of coupon to buy by customer id */
	public List<Coupon> getCouponToBuyByCustomerID(long customerID) throws CouponException {
		Connection cn = null;
		ArrayList<Coupon> ac = new ArrayList<>();
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = cn.prepareStatement(getCouponToBuyByCustomerID);
			pst.setLong(1, customerID);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Coupon cp = new Coupon();
				cp.setId(rs.getLong(1));
				cp.setTitle(rs.getString(2));
				cp.setStart_date(rs.getDate(3));
				cp.setEnd_date(rs.getDate(4));
				cp.setAmount(rs.getInt(5));
				cp.setType(CouponType.valueOf(rs.getString(6)));
				cp.setMessage(rs.getString(7));
				cp.setPrice(rs.getDouble(8));
				cp.setImage(rs.getString(9));
				ac.add(cp);
			}
		} catch (SQLException e) {
			throw new CouponException("exception getting customer coupon", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return ac;
	}

	/** return a list of coupon by customer id and by type */
	public List<Coupon> getCouponByCustomerIDByType(long customerID, CouponType couponType) throws CouponException {
		Connection cn = null;
		ArrayList<Coupon> ac = new ArrayList<>();
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = cn.prepareStatement(selectAllCouponByCustomerIDByType);
			pst.setLong(1, customerID);
			pst.setString(2, couponType.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Coupon cp = new Coupon();
				cp.setId(rs.getLong(1));
				cp.setTitle(rs.getString(2));
				cp.setStart_date(rs.getDate(3));
				cp.setEnd_date(rs.getDate(4));
				cp.setAmount(rs.getInt(5));
				cp.setType(CouponType.valueOf(rs.getString(6)));
				cp.setMessage(rs.getString(7));
				cp.setPrice(rs.getDouble(8));
				cp.setImage(rs.getString(9));
				ac.add(cp);
			}
		} catch (SQLException e) {
			throw new CouponException("exception getting customer coupon", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return ac;
	}

	/**
	 * return a list of coupon by customer id and cheaper than the provided
	 * price
	 */
	public List<Coupon> getCouponByCustomerIDCheaperThan(long customerID, double couponPrice) throws CouponException {
		Connection cn = null;
		List<Coupon> ac = new ArrayList<>();
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = cn.prepareStatement(selectAllCouponByCustomerIDCheaperThan);
			pst.setLong(1, customerID);
			pst.setDouble(2, couponPrice);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Coupon cp = new Coupon();
				cp.setId(rs.getLong(1));
				cp.setTitle(rs.getString(2));
				cp.setStart_date(rs.getDate(3));
				cp.setEnd_date(rs.getDate(4));
				cp.setAmount(rs.getInt(5));
				cp.setType(CouponType.valueOf(rs.getString(6)));
				cp.setMessage(rs.getString(7));
				cp.setPrice(rs.getDouble(8));
				cp.setImage(rs.getString(9));
				ac.add(cp);
			}
		} catch (SQLException e) {
			throw new CouponException("exception getting customer coupon", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return ac;
	}

	/**
	 * return a list of coupon by type
	 * 
	 * @throws CouponException
	 */
	public List<Coupon> getCouponByType(String couponType) throws CouponException {
		Connection cn = null;
		List<Coupon> ls = new ArrayList<>();
		try {
			cn = ConnectionPool.getConnection();
			PreparedStatement pst = cn.prepareStatement(selectByType);
			pst.setString(1, couponType.toString());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStart_date(rs.getDate(3));
				coupon.setEnd_date(rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setType(CouponType.valueOf(rs.getString(6)));
				coupon.setMessage(rs.getString(7));
				coupon.setPrice(rs.getDouble(8));
				coupon.setImage(rs.getString(9));
				ls.add(coupon);
			}
		} catch (SQLException e) {
			throw new CouponException("exception while getting coupon by type", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
		return ls;
	}

	/** add a coupon to a company */
	public void addCouponToCompany(long companyID, long couponID) throws CouponException {
		Connection cn = null;
		PreparedStatement pstt;
		try {
			cn = ConnectionPool.getConnection();
			pstt = cn.prepareStatement(AddCouponToCompany);
			pstt.setLong(1, companyID);
			pstt.setLong(2, couponID);
			pstt.execute();
		} catch (SQLException e) {
			throw new CouponException("error adding coupon to company", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
	}

	/** add a coupon to a customer */
	public void addCouponToCustomer(long customerID, long couponID) throws CouponException {
		Connection cn = null;
		PreparedStatement pstt;
		try {
			cn = ConnectionPool.getConnection();
			pstt = cn.prepareStatement(AddCouponToCustomer);
			pstt.setLong(1, customerID);
			pstt.setLong(2, couponID);
			pstt.execute();
		} catch (SQLException e) {
			throw new CouponException("error adding coupon to customer", e);
		} finally {
			ConnectionPool.returnCon(cn);
		}
	}

	@Override
	public Coupon updatableCoupon(Coupon coupon) throws CouponException {
		if(coupon==null) return null;
		Coupon couponOriginal = null;
		//searching for coupon id
		if(coupon.getId()==0){
			if(coupon.getTitle() != null && !coupon.getTitle().equals("")){
				couponOriginal = getCouponByTitle(coupon.getTitle());
				coupon.setId(couponOriginal.getId());
				}
			else return null;
		}else {
			couponOriginal = getCoupon(coupon.getId());
			if(couponOriginal==null) return null;
			coupon.setTitle(couponOriginal.getTitle());
		}
		
		//searching for coupon price
		if (coupon.getPrice()==0){
			coupon.setPrice(couponOriginal.getPrice());
		}
		//searching for coupon end date
		if(coupon.getEnd_date() == null){
			coupon.setEnd_date(couponOriginal.getEnd_date());
		}
		//returning updatable coupon
		return coupon;
	}

}
