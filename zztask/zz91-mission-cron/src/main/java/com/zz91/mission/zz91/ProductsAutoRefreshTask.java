package com.zz91.mission.zz91;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zz91.mission.domain.subscribe.Product;
import com.zz91.mission.domain.subscribe.ProductsAutoRefresh;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

/**
 * 用于自动刷新供求信息
 * @author zhozuk
 *DATE ： 2013.1.17
 */
public class ProductsAutoRefreshTask implements ZZTask{

	final static String DB = "ast";
	
	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		
		final List<ProductsAutoRefresh> list = new ArrayList<ProductsAutoRefresh>();
		final List <Product> proList = new ArrayList<Product>();
		Date now = new Date();
		String sql = "select company_id,refresh_date from products_auto_refresh";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					ProductsAutoRefresh  productsAutoRefresh = new ProductsAutoRefresh();
					productsAutoRefresh.setCompanyId(rs.getInt(1));
					productsAutoRefresh.setRefreshDate(rs.getString(2));
					list.add(productsAutoRefresh);
				}
			}
		});
		for (ProductsAutoRefresh pro : list) {
			String[] refreshDate = pro.getRefreshDate().split(",");
			for (int i = 0 ; i < refreshDate.length; i++) {
				if (refreshDate[i].equals(String.valueOf(DateUtil.getDayOfMonthForDate(now)))) {
					String proSql = "select id,expire_time from products where company_id="+pro.getCompanyId();
					DBUtils.select(DB, proSql, new IReadDataHandler() {
						@Override
						public void handleRead(ResultSet rs) throws SQLException {
							while (rs.next()) {
								Product  product = new Product();
								product.setId(rs.getInt(1));
								product.setExpireTime(rs.getDate(2));
								proList.add(product);
							}
						}
					});
					break;
				}
			}
		}
		
		for (Product p : proList) {
			String updateSql = "";
			if (now.getTime() - p.getExpireTime().getTime() < 0 ) {
				updateSql = "update products set refresh_time=now(),gmt_modified=now() where id="+p.getId();
			} else {
				updateSql = "update products set expire_time = adddate(now(),interval to_days(expire_time)-to_days(refresh_time) day)," +
						"refresh_time=now(),gmt_modified=now() where  id="+p.getId();
			}
			DBUtils.insertUpdate(DB, updateSql);
		}
		return true;
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		ProductsAutoRefreshTask obj = new ProductsAutoRefreshTask();
		obj.exec(new Date());
	}
}
