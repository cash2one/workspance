package com.ast.feiliao91.service.company.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.feiliao91.domain.company.CompanyService;
import com.ast.feiliao91.service.BaseServiceTestCase;
import com.ast.feiliao91.service.company.CompanyServiceService;

public class CompanyServiceTest extends BaseServiceTestCase{
	@Autowired
	private CompanyServiceService companyServiceService;
	
	/**
	 * 插入一个服务
	 */
	@Test
	public void test_insert() {
		//clean();
//		for(int i=0; i < 50; i = i+1){
//			CompanyService cs = new CompanyService();
//			cs.setCompanyId(45);
//			cs.setServiceScore(100);
//			cs.setServiceName("废料通");
//			cs.setServiceCode("10001002");
//			cs.setIsDel(0);
//			Integer count = companyServiceService.insertCompanyService(cs);
//		}
//		Integer count2 = selectLastInsertID();
//		/**
//		 * 断言信息是否插入
//		 */
//		assertEquals(count, count2);
	}
//	/**
//	 * 根据id获得一个服务
//	 */
//	@Test
//	public void test_selectById() {
//		Integer count2 = insertResult();
//		CompanyService cs1 = (CompanyService) companyServiceService.queryCompanyServiceById(count2);
//		CompanyService cs2 = queryById(count2);
//		/**
//		 * 断言是否相等
//		 */
//		assertEquals(cs1.getServiceName(), cs2.getServiceName());
//		assertEquals(cs1.getId(), cs2.getId());
//	}
	/**
	 * 根据company_id获得服务列表
	 */
	@Test
	public void test_selectListByCompanyId() throws SQLException{
		//查询所有测试
		List<CompanyService> companyService = companyServiceService.queryCompanyServiceListByCompanyId(45,55);
		System.out.println(companyService);
		System.out.println("您查询了改公司的服务服务个数为："+companyService.size());
//		assertEquals((Integer) companyService.size(),count);
	}
	
	
	/**
	 * 清除表信息
	 */
	public void clean() {
		try {
			connection.prepareStatement("delete from company_service").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询插入的最后一条数据ID
	 */
	public int selectLastInsertID() {
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select id from company_service order by id desc limit 1");
			if (rs.next()) {

				return rs.getInt(1);
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
	public CompanyService queryById(Integer id) {
		CompanyService cs = new CompanyService();
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(
					"select * from feedback where id='" + id + "'");
			if (rs.next()) {
				cs.setId(rs.getInt(1));
				cs.setCompanyId(rs.getInt(2));
				cs.setServiceScore(rs.getInt(3));
				cs.setServiceName(rs.getString(4));
				cs.setServiceCode(rs.getString(5));
				cs.setIsDel(rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cs;
	}
	@SuppressWarnings("unused")
	private Integer selectListByCompanyId(Integer companyId) throws SQLException {
		ResultSet rs = null;
		try {
			rs = connection
					.createStatement()
					.executeQuery(
							"select count(*) from company_service where is_del=0 and company_id="+
									 companyId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

}
