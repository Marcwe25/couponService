package cpCore;

import java.sql.*;
import java.util.*;

public class ConnectionPool {

	private static ConnectionPool connectionPool = null;
	private static final int SIZE = 10;
	private static Stack<Connection> connections = new Stack<>();
	private static Stack<Connection> connectionsInUse = new Stack<>();
	private static final Object poolLock = new Object();

	static{
			try {
				intialize();
			} catch (CouponException e) {
				e.printStackTrace();
			}
	}

	/**
	 * connection pool constructor. because it is supposed to be used as a
	 * singleton, the constructor is private. the singleton can be accessed by
	 * the public method getInstance(), who return the singleton connection pool
	 * 
	 * @throws CouponException
	 * @throws ClassNotFoundException
	 */
	private ConnectionPool() throws CouponException {
	}

	public static void intialize() throws CouponException {
		try {
			Class.forName(link.DRIVER_URL);
			generateStackCollection();
		} catch (ClassNotFoundException e) {
			throw new CouponException("error loading driver", e);
		}
	}

	/**
	 * static method to return the singleton connection pool object. it contain
	 * a stack of sql.Connection that can be accessed with getConnection() and
	 * returned with returnCon(Connection connection).
	 * 
	 * @throws CouponException
	 */
	public static ConnectionPool getInstance() throws CouponException {
		if (connectionPool == null) {
			connectionPool = new ConnectionPool();
		}
	return connectionPool;
	}

	/**
	 * check the communication with database
	 */
	public static void checkDataBase() throws CouponException {
		try (Connection cn = DriverManager.getConnection(link.DATABASE_LINK);) {
			try (Statement st = cn.createStatement()) {
				try (ResultSet res = st.executeQuery("select 1 from company_coupon as c")) {
					if (res == null)
						throw new CouponException("communication exception with coupon database");
				}
			}
		} catch (SQLException e) {
			throw new CouponException("communication exception with coupon database", e);
		}
	}

	/**
	 * static method that return a Connection from the connection pool. the
	 * connection must be returned with returnCon(Connection connection)
	 * 
	 * @throws CouponException
	 */
	public static Connection getConnection() throws CouponException {
		synchronized (poolLock) {
			while (connections.empty()) {
				try {
					poolLock.wait();
				} catch (InterruptedException e) {
					throw new CouponException("exception while waiting to get a connection", e);
				}
			}
			Connection cn = connections.pop();
			connectionsInUse.push(cn);
			return cn;
		}
	}

	/**
	 * static method to return connection to the pool
	 * 
	 * @throws CouponException
	 */
	public static void returnCon(Connection connection) throws CouponException {
		if (connection == null) {
			throw new CouponException("no connection to return");
		}
		synchronized (poolLock) {
			Iterator<Connection> it = connectionsInUse.iterator();
			while (it.hasNext()) {
				Connection cn = it.next();
				if (cn.equals(connection)) {
					it.remove();
					connections.push(connection);
					break;
				}
			}
			poolLock.notify();
		}
	}

	/**
	 * static method that reset all connection
	 * 
	 * @throws CouponException
	 */
	public static void resetConnection() throws CouponException {
		closeAllConnection();
		generateStackCollection();
	}


	private static void closeAllConnection() throws CouponException {
		while (!connectionsInUse.isEmpty()) {
			connections.add(connectionsInUse.pop());
		}
		for (Connection cn : connections) {
			try {
				cn.close();
			} catch (SQLException e) {
				throw new CouponException("Exception while closing", e);
			}
		}
		
	}
	
	public static void shutdownConnectionPool() throws CouponException{
		closeAllConnection();
		connections = null;
		connectionsInUse = null;
		connectionPool= null;
		System.out.println("All connections closed");
	}

	private static void generateStackCollection() throws CouponException {
		connections = new Stack<>();
		for (int i = 1; i <= SIZE; i++) {
			Connection cn;

			try {
				cn = DriverManager.getConnection(link.DATABASE_LINK);
				ConnectionPool.connections.push(cn);
			} catch (SQLException e) {
				throw new CouponException("error generating connection to database", e);
			}

		}
	}

	@SuppressWarnings("unused")
	public static void displayConnection() {
		int numInStock = 0;
		int numInUse = 0;
		for (Connection connection : connections) {
			numInStock++;
		}
		for (Connection connection : connectionsInUse) {
			numInUse++;
		}
		System.out.println("connection in stock\t: " + numInStock);
		System.out.println("connection in use\t: " + numInUse);
	}

}
