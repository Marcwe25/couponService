package cpCore;

import java.sql.*;

public class DButility {
	
	/**create the database it self
	 * @throws CouponException */
	@SuppressWarnings("unused")
	public static void createDataBase() throws CouponException{
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			Connection conn = DriverManager.getConnection(link.DATABASE_LINK+";create=true");
		} catch (ClassNotFoundException | SQLException e) {
			throw new CouponException("Exception while creating coupon database",e);
		}
	}
	
	/**add table to the database*/
	public static void addTables() throws CouponException{
		try {
			Connection cn = ConnectionPool.getConnection();
			Statement stt = cn.createStatement();
			stt.execute("create table app.customer( id integer primary key generated always as identity, cust_name varchar(20) unique not null, password varchar(10))");
			stt.execute("create table app.company ( id integer primary key generated always as identity, comp_name varchar(20) unique not null, password varchar(10), email varchar(20))");
			stt.execute("create table app.coupon( id integer primary key generated always as identity, title varchar(30) unique not null, start_date date, end_date date, amount integer, coupon_type varchar(20), message varchar(100), price double, image varchar(50))");
			stt.execute("create table app.Company_Coupon (comp_id integer , coupon_id integer, foreign key (comp_id) references company(id), foreign key (coupon_id) references coupon(id),primary key(comp_id, coupon_id))");
			stt.execute("create table app.Customer_Coupon (cust_id integer , coupon_id integer, foreign key (cust_id) references customer(id), foreign key (coupon_id) references coupon(id), primary key(cust_id, coupon_id))");
		} catch (SQLException e) {
			throw new CouponException("Exception while creating table",e);
		}
	}
	
	/**add some coupon to the coupon*/
	public static void createCouponSomeCoupon() throws CouponException{
		try {
			Connection cn = ConnectionPool.getConnection();
			Statement stt = cn.createStatement();
			stt.execute("insert into coupon (title , start_date , end_date , amount , coupon_type , message , price) values ( 'food 500','2017-06-01 00:00:00.0','2017-07-01 00:00:00.0',50, 'food500','this coupon allow you to buy food for 500 diram !!!',400)");
			stt.execute("insert into coupon (title , start_date , end_date , amount , coupon_type , message , price)  values ( 'food 1000','2017-06-01 00:00:00.0','2017-07-01 00:00:00.0',50, 'food1000','this coupon allow you to buy food for 1000 diram !!!',700)");
			stt.execute("insert into coupon (title , start_date , end_date , amount , coupon_type , message , price)  values ( 'food 2000', '2017-06-01 00:00:00.0','2017-07-01 00:00:00.0',50, 'food2000','this coupon allow you to buy food for 2000 diram !!!',1500)");
			stt.execute("insert into coupon (title , start_date , end_date , amount , coupon_type , message , price)  values ( 'restaurant with kids','2017-06-01 00:00:00.0','2017-07-01 00:00:00.0',50, 'restaurantA','this coupon allow spent 3000 diram in youre favorite restaurant !!!',2000)");
			stt.execute("insert into coupon (title , start_date , end_date , amount , coupon_type , message , price)  values ( 'restaurant chef','2017-06-01 00:00:00.0','2017-07-01 00:00:00.0',50, 'restaurantB','this coupon allow you to spent 4000 diram in the best restaurant in town !!!',3000)");
			stt.execute("insert into coupon (title , start_date , end_date , amount , coupon_type , message , price)  values ( 'electricity','2017-06-01 00:00:00.0','2017-07-01 00:00:00.0',50, 'electricity','this coupon allow you to pay 1000 diram to hevrat hasmal !!!',990)");
			
		} catch (SQLException e) {
			throw new CouponException("error adding coupon to database",e);
		}
	}
	
	/**reset id count*/
	public static void resetID() throws CouponException, SQLException  {
	Connection cn = ConnectionPool.getConnection();
	Statement stt = cn.createStatement();
	stt.execute("alter table customer alter column id restart with 1");
	}
	
	public static void main(String[] args) {}
}
