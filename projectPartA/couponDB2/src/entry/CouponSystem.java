package entry;

import cpCore.ConnectionPool;
import cpCore.CouponException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;
import facade.Facade;

public class CouponSystem {

	private static CouponSystem couponSystem;
	Thread dc;

	private CouponSystem() {
		dc = new Thread(new DailyCouponExpirationTask());
		dc.setDaemon(true);
		dc.start();
	}

	public static CouponSystem getInstance() {
		if (couponSystem == null) {
			couponSystem = new CouponSystem();
		}
		return couponSystem;
	}

	public Facade login(String username, String password, String usertype) throws CouponException {

		switch (usertype) {
		case "admin":
			AdminFacade adminFacade = AdminFacade.login(username, password);
			if (adminFacade == null) {
				throw new CouponException("invalid login");
			} else {
				System.out.println("login successfull, using admin facade");
				return adminFacade;
			}

		case "company":
			CompanyFacade companyFacade = CompanyFacade.login(username, password);
			if (companyFacade == null) {
				throw new CouponException("invalid login");
			} else {
				System.out.println("login successfull, using company facade for company \"" + username + "\"");
				return companyFacade;
			}

		case "customer":
			CustomerFacade customerFacade = CustomerFacade.login(username, password);
			if (customerFacade == null)
				throw new CouponException("invalid login");
			else {
				System.out.println("login successfull, using customer facade for customer \"" + username + "\"");
				return customerFacade;
			}
		}
		return null;
	}

	public void shutdown() throws CouponException, InterruptedException {
		dc.interrupt();
		dc.join();
		ConnectionPool.shutdownConnectionPool();
		System.out.println("coupon system closed");
	}

}
