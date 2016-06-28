package com.ast.ast1949.service.products;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ast.ast1949.domain.products.ProductsViewHistory;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.util.DateUtil;

/**
 * @Author:kongsj
 * @Date:2011-12-27
 */
public class ProductsViewHistoryServiceImplTest extends BaseServiceTestCase {

	@Resource
	private ProductsViewHistoryService productsViewHistoryService;

	// public Integer create(ProductsViewHistory productsViewHistory);
	//
	// public List<ProductsViewHistory> queryHistory(String cookieKey, Integer
	// size);
	//
	// public Integer updateCompanyIdByCookieKey(Integer companyId, String
	// cookieKey);
	//
	// public String queryKeyByCompanyId(Integer companyId);
	//
	// public Integer updateGmtLastView(Integer id);

	public void test_create_success() {
		clean();

		Integer id = productsViewHistoryService.create(new ProductsViewHistory(
				null, "123", 1, 1, "", "", null, null, null));

		ProductsViewHistory obj = queryOne(id);

		assertNotNull(obj);

		assertEquals("123", obj.getCookieKey());
		assertNotNull(obj.getGmtLastView());

	}

	public void test_queryHistory_records() {
		clean();
		createOneRecord(new ProductsViewHistory(null, "cookieKey", 1, 2,
				"name", "url", new Date(), null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey", 1, 3,
				"name", "url", new Date(), null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey", 1, 4,
				"name", "url", new Date(), null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey", 1, 6,
				"name", "url", new Date(), null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey", 1, 7,
				"name", "url", new Date(), null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey", 1, 8,
				"name", "url", new Date(), null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey", 1, 9,
				"name", "url", new Date(), null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey", 1, 10,
				"name", "url", new Date(), null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey", 1, 11,
				"name", "url", new Date(), null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey", 1, 12,
				"name", "url", new Date(), null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey1", 2, 1,
				"name", "url", new Date(), null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey1", 2, 2,
				"name", "url", new Date(), null, null));

		List<ProductsViewHistory> list = productsViewHistoryService
				.queryHistory("cookieKey", 5);
		for (ProductsViewHistory obj : list) {
			assertTrue(obj.getCookieKey().equals("cookieKey"));
		}
		assertEquals(5, list.size());
	}

	public void test_queryHistory_order_by_gmtListView() {
		clean(); 
		Date now=new Date();
		
		Integer id = createOneRecord(new ProductsViewHistory(null, "cookieKey",
				1, 2, "name", "url", now, null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey", 1, 3,
				"name", "url", DateUtil.getDateAfterDays(now, -1), null, null));
		createOneRecord(new ProductsViewHistory(null, "cookieKey", 1, 4,
				"name", "url", DateUtil.getDateAfterDays(now, -2), null, null));

		List<ProductsViewHistory> list = productsViewHistoryService
				.queryHistory("cookieKey", 10);

		assertTrue(list.size() == 3);
		assertEquals(3, list.get(0).getProductId().intValue());
		assertEquals(2, list.get(2).getProductId().intValue());

	}

	public void test_updateCompanyIdByCookieKey() {
		clean();
		Integer id1 = createOneRecord(new ProductsViewHistory(null, "key", 1,
				1, "", "", new Date(), null, null));
		Integer id2 = createOneRecord(new ProductsViewHistory(null, "key", 1,
				2, "", "", new Date(), null, null));
		Integer id3 = createOneRecord(new ProductsViewHistory(null, "key1", 1,
				3, "", "", new Date(), null, null));
		Integer id4 = createOneRecord(new ProductsViewHistory(null, "key2", 1,
				4, "", "", new Date(), null, null));

		Integer i = productsViewHistoryService.updateCompanyIdByCookieKey(2,"key");
		assertTrue(i == 2);
		ProductsViewHistory obj = queryOne(id1);
		assertEquals(2, obj.getCompanyId().intValue());
		obj = queryOne(id2);
		assertEquals(2, obj.getCompanyId().intValue());
		obj = queryOne(id3);
		assertEquals(1, obj.getCompanyId().intValue());
		obj = queryOne(id4);
		assertEquals(1, obj.getCompanyId().intValue());
	}

	public void test_queryKeyByCompanyId() {
		clean();
		createOneRecord(new ProductsViewHistory(null, "key1", 1, 1, "", "",
				new Date(), null, null));
		createOneRecord(new ProductsViewHistory(null, "key2", 1, 1, "", "",
				new Date(), null, null));

		String key = productsViewHistoryService.queryKeyByCompanyId(1);
		assertEquals("key1", key);

	}

	public void test_updateGmtLastView() {
		clean();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		Date date = c.getTime();
		Integer id = createOneRecord(new ProductsViewHistory(null, "key", 1, 1,
				"", "", date, null, null));
		
		Integer i = productsViewHistoryService.updateGmtLastView(id);
		assertTrue(i == 1);
		ProductsViewHistory obj = queryOne(id);
		assertTrue(date.before(obj.getGmtLastView()));
	}

	private void clean() {
		try {
			connection.prepareStatement("delete from products_view_history")
					.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private ProductsViewHistory queryOne(Integer id) {
		try {
			ResultSet rs = connection
					.createStatement()
					.executeQuery(
							"select id,cookie_key,company_id,product_id,product_name,product_pic_url,gmt_last_view,gmt_created,gmt_modified from products_view_history where id ="
									+ id);
			ProductsViewHistory obj = new ProductsViewHistory();
			if (rs.next()) {
				obj.setId(rs.getInt(1));
				obj.setCookieKey(rs.getString(2));
				obj.setCompanyId(rs.getInt(3));
				obj.setProductId(rs.getInt(4));
				obj.setProductName(rs.getString(5));
				obj.setProductPicUrl(rs.getString(6));
				obj.setGmtLastView(rs.getDate(7));
				obj.setGmtCreated(rs.getDate(8));
				obj.setGmtModified(rs.getDate(9));
				return obj;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Integer createOneRecord(ProductsViewHistory obj) {
		try {
			connection
					.prepareStatement(
							"INSERT INTO products_view_history (cookie_key, company_id, product_id, product_name, product_pic_url, gmt_last_view, gmt_created, gmt_modified) VALUES ('"
									+ obj.getCookieKey()
									+ "', "
									+ obj.getCompanyId()
									+ ", "
									+ obj.getProductId()
									+ ", '"
									+ obj.getProductName()
									+ "', '"
									+ obj.getProductPicUrl()
									+ "', '"
									+ DateUtil.toString(obj.getGmtLastView(), "yyyy-MM-dd HH:mm:ss") + "', now(),now())")
					.execute();
			return insertResult();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
