package com.ast.feiliao91.service.company.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.feiliao91.domain.company.Address;
import com.ast.feiliao91.service.BaseServiceTestCase;
import com.ast.feiliao91.service.company.AddressService;
import com.ast.feiliao91.service.company.MyException;

public class AddressServiceTest extends BaseServiceTestCase {
	@Autowired
	private AddressService addressService;

	@Test
	public void test_insert() {
		clean();
		Address b = new Address();
		b.setAddress("杭州市");
		b.setCompanyId(2000);
		b.setTel("asdf");
		b.setCompanyName("zz91");
		b.setAddressType(0);
		b.setDetail("公司要过年了");
		b.setIsDel(0);
		b.setIsDefault(0);
		int man = 75;
		for (int i = 0; i < 20; i++) {
			man++;
			b.setId(man);
			try {
				addressService.insert(b, 0);
				man++;
				b.setId(man);
				addressService.insert(b, 1);
			} catch (MyException e) {
				e.getMessage();
			}
		}
		// Integer count2 = insertResult();
		/**
		 * 断言信息是否插入
		 */
		// assertEquals(count, count2);
	}

	/*
	 * @Test public void tset_selectById() throws SQLException { List<Address>
	 * address = addressService.selectById(2000); Integer count =
	 * selectByIdSH(2000); assertEquals(count, (Integer) address.size());
	 * 
	 * }
	 * 
	 * @Test public void test_selectByDelId() throws SQLException {
	 * List<Address> address = addressService.selectByDelId(2000); Integer count
	 * = selectByIdFH(2000); assertEquals(count, (Integer) address.size()); }
	 */

	public void test_selectDefaultAddress() {
		Integer count2 = insertResult();
		updateDefaultAddress(count2 - 1);
		Address address = addressService.selectDefaultAddress(2000);
		assertEquals("1", address.getIsDefault().toString());
		assertEquals("0", address.getAddressType().toString());
		updateDefaultDelAddress(count2);
		Address ad = addressService.selectDefaultDelAddress(2000);
		assertEquals("1", ad.getIsDefault().toString());
		assertEquals("1", ad.getAddressType().toString());
	}

	public void test_deleteById() {
		Integer count2 = insertResult();
		addressService.deleteById(count2);
		Integer in = selectLastId(count2);
		assertEquals("1", in.toString());
	}

	public void test_updateDefault() throws SQLException {
		Address address = new Address();
		address.setId(76);
		address.setCompanyId(2000);
		addressService.updateDefault(address);
		Integer i = selectDafault();
		assertEquals((Integer) 76, i);
		address.setId(77);
		addressService.updateDefaultDel(address);
		Integer i2 = selectDafaultDel();
		assertEquals((Integer) 77, i2);
	}

	public void test_selectShopCount() throws SQLException {
		Integer shop = addressService.selectShopCount(2000);
		Integer shop1 = selectShopCount(2000);
		assertEquals(shop, shop1);
		Integer hair = addressService.selectHairCount(2000);
		Integer hair1 = selectHairCount(2000);
		assertEquals(hair, hair1);
	}

	/*
	 * @Test public void test_selectById() {
	 * System.out.println("sadfasdfasdfasd"); Integer count2 = insertResult();
	 * Address d = (Address) addressService.selectById(count2); Address f =
	 * queryById(count2);
	 *//**
	 * 断言是否相等
	 */
	/*
	 * assertEquals(d.getAddress(), f.getAddress());
	 * 
	 * }
	 */

	@Test
	public void test_updateAll() {
		Address b = new Address();
		Integer count2 = insertResult();
		b.setId(count2);
		b.setAddress("北京市中观大海村");
		b.setCompanyName("垃圾公司");
		b.setName("猴年发发");
		b.setTel("猴年大吉");
		b.setIsDefault(10);
		b.setIsDel(0);
		b.setAddressType(0);
		b.setZipCode("0");
		b.setDetail("公司这厮混的还不错");
		b.setMobile("18814871774");
		b.setAreaCode("46874");
		addressService.updateAll(b);
		Address f = queryById(count2);
		assertEquals("北京市中观大海村", f.getAddress());
		assertEquals("垃圾公司", f.getCompanyName());
		assertEquals("猴年发发", f.getName());
		assertEquals("猴年大吉", f.getTel());
		assertEquals("10", f.getIsDefault().toString());
		assertEquals("0", f.getIsDel().toString());
		assertEquals("0", f.getAddressType().toString());
	}

	/**
	 * 清除表信息
	 */
	public void clean() {
		try {
			connection.prepareStatement("delete from address").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询插入的最后一条数据ID
	 */
	public int insertResult() {
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select id from address order by id desc limit 1");
			if (rs.next()) {

				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void updateDefaultAddress(Integer id) {
		try {
			connection.prepareStatement(
					"update  address set is_default=1 where id =" + id)
					.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int selectLastId(Integer id) {
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select * from address where id =" + id);
			if (rs.next()) {

				return rs.getInt(10);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 根据ID查询信息
	 * 
	 * @param id
	 * @return
	 */
	public Address queryById(Integer id) {
		Address b = new Address();
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(
					"select * from address where id='" + id + "'");
			if (rs.next()) {
				b.setId(rs.getInt(1));
				b.setName(rs.getString(3));
				b.setTel(rs.getString(4));
				b.setMobile(rs.getString(5));
				b.setAreaCode(rs.getString(6));
				b.setAddress(rs.getString(7));
				b.setZipCode(rs.getString(8));
				b.setIsDefault(rs.getInt(9));
				b.setIsDel(rs.getInt(10));
				b.setAddressType(rs.getInt(11));
				b.setCompanyName(rs.getString(12));
				b.setDetail(rs.getString(13));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	private void updateDefaultDelAddress(Integer id) {
		try {
			connection.prepareStatement(
					"update  address set is_default=1,address_type=1 where id ="
							+ id).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private Integer selectByIdSH(Integer companyId) throws SQLException {
		ResultSet rs = null;
		try {
			rs = connection
					.createStatement()
					.executeQuery(
							"select count(*) from address where  address_type=0 and is_del=0 and company_id='"
									+ companyId + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs.getInt(1);
	}

	@SuppressWarnings("unused")
	private Integer selectByIdFH(Integer companyId) throws SQLException {
		ResultSet rs = null;
		try {
			rs = connection
					.createStatement()
					.executeQuery(
							"select count(*) from address where  address_type=1 and is_del=0 and company_id='"
									+ companyId + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs.getInt(1);
	}

	private Integer selectDafault() throws SQLException {
		ResultSet rs = null;
		try {
			rs = connection
					.createStatement()
					.executeQuery(
							"select * from address where  address_type=0 and is_del=0 and is_default=1 ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

	private Integer selectDafaultDel() throws SQLException {
		ResultSet rs = null;
		try {
			rs = connection
					.createStatement()
					.executeQuery(
							"select * from address where  address_type=1 and is_del=0 and is_default=1 ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

	private Integer selectHairCount(Integer companyId) throws SQLException {
		ResultSet rs = null;
		try {
			rs = connection
					.createStatement()
					.executeQuery(
							"select count(*) from address where  address_type=1 and is_del=0 and company_id="
									+ companyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

	private Integer selectShopCount(Integer companyId) throws SQLException {
		ResultSet rs = null;
		try {
			rs = connection
					.createStatement()
					.executeQuery(
							"select count(*) from address where  address_type=0 and is_del=0 and company_id="
									+ companyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

}