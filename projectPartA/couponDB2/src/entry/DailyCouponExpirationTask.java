package entry;

import java.util.*;
import DAO.AbstractLevel.CouponDAO;
import DAODBlevel.CouponDBDAO;
import cpCore.CouponException;
import cpCore.beans.Coupon;

public class DailyCouponExpirationTask implements Runnable {

	private CouponDAO coupondao;

	public DailyCouponExpirationTask() {
		coupondao = new CouponDBDAO();
	}

	@Override
	public void run() {
		try {
			dateCheckTask();
		} catch (CouponException e) {
			e.printStackTrace();
		}

	}

	public void dateCheckTask() throws CouponException {
		Date today = new Date();
		while (true) {
			List<Coupon> coupons = coupondao.getAllCoupon();
			int count = 0;
			for (Coupon coupon : coupons) {
				if (coupon.getEnd_date().compareTo(today) < 0) {
					long couponId = coupon.getId();
					coupondao.removeFromCustomerCoupon(couponId);
					coupondao.removeFromCompanyCoupon(couponId);
					coupondao.removeCoupon(couponId);
					count++;
				}
			}
			System.out.println("DailyCouponExpirationTask: " + count + " coupon(s) deleted");
			try {
				Thread.sleep(1000l * 60l * 60l * 24l);
			} catch (InterruptedException e) {
				System.out.println("DailyCouponExpirationTask have been interrupted");
				;
			}
		}
	}
}
