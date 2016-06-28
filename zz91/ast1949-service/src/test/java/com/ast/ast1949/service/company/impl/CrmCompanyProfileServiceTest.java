package com.ast.ast1949.service.company.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.junit.Test;

import com.ast.ast1949.domain.company.CrmCompanyProfile;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.company.CrmCompanyProfileService;

public class CrmCompanyProfileServiceTest extends BaseServiceTestCase {

	@Resource
	CrmCompanyProfileService crmCompanyProfileService;

	@Test
	public void testQueryProfile() {
		clean();
		createTestRecord(new CrmCompanyProfile(215, "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", null, null));	
		CrmCompanyProfile cp=crmCompanyProfileService.queryProfile(215);
		assertNotNull(cp);
		assertEquals(215, cp.getCompanyId().intValue());
	}

	@Test
	public void testCreateProfile() {
		clean();
		
		Integer id = crmCompanyProfileService.createProfile(new CrmCompanyProfile(52222, "", "", "", "", "",
						"", "", null, "", "", "", "", "", "", "", "", "", "", "",
						"", null, null));
		CrmCompanyProfile cp = queryOneTestRecord(id);
		
		assertNotNull(cp);
		assertEquals(52222, cp.getCompanyId().intValue());
		
	}

	@Test
	public void testUpdateProfile() {
		clean();
		CrmCompanyProfile profile=new CrmCompanyProfile(1, "old sale details", "ole buy details", "old introduction",
				"old operator name", "old operator phone","old operator tel","old address", "old credit card", "0", "0",
				"0", "0", "0", "0", "0", "0", "0", "0", "0",
						"0", null, null);
		Integer id = createTestRecord(profile);
		profile.setId(id);
		profile.setSaleDetails("new sale details");
		profile.setBuyDetails("new buy details");
		profile.setIntroduction("new introduction");
		profile.setOperatorName("new operatorName");
		profile.setOperatorPhone("new operatorPhone");
		profile.setOperatorTel("new operatorTel");
		profile.setAddress("new address");
		profile.setCreditCard("new creditCard");
		profile.setCreditLicense("1");
		profile.setCreditTax("1");
		profile.setTag1("1");
		profile.setTag2("1");
		profile.setTag3("1");
		profile.setTag4("1");
		profile.setTag5("1");
		profile.setTag6("1");
		profile.setTag7("1");
		profile.setTag8("1");
		profile.setTag9("1");
		profile.setTag10("1");
		
		Integer i=crmCompanyProfileService.updateProfile(profile);
		assertEquals(1, i.intValue());
		assertEquals("new sale details", profile.getSaleDetails());
		assertEquals("new buy details", profile.getBuyDetails());
		assertEquals("new introduction", profile.getIntroduction());
		assertEquals("new operatorName", profile.getOperatorName());
		assertEquals("new operatorPhone", profile.getOperatorPhone());
		assertEquals("new operatorTel", profile.getOperatorTel());
		assertEquals("new address", profile.getAddress());
		assertEquals("new creditCard", profile.getCreditCard());
		assertEquals("1", profile.getCreditLicense());
		assertEquals("1", profile.getTag1());
		assertEquals("1", profile.getTag2());
		assertEquals("1", profile.getTag3());
		assertEquals("1", profile.getTag4());
		assertEquals("1", profile.getTag5());
		assertEquals("1", profile.getTag6());
		assertEquals("1", profile.getTag7());
		assertEquals("1", profile.getTag8());
		assertEquals("1", profile.getTag9());
		assertEquals("1", profile.getTag10());
	}

	private void clean() {
		try {
			connection.prepareStatement("delete from crm_company_profile").execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	private Integer createTestRecord(CrmCompanyProfile cp) {
		String sql = "insert into `crm_company_profile`(`company_id`,`sale_details`,`buy_details`,"
				+ "`introduction`,`operator_name`,`operator_phone`,"
				+ "`operator_tel`,`address`,`credit_card`,`credit_license`,`credit_tax`,`tag1`,`tag2`,"
				+ "`tag3`,`tag4`,`tag5`,`tag6`,`tag7`,`tag8`,`tag9`,`tag10`,`gmt_created`,`gmt_modified`) "
				+ "values("							
				+ cp.getCompanyId()
				+ ",'"
				+ cp.getSaleDetails()
				+ "','"
				+ cp.getBuyDetails()
				+ "','"
				+ cp.getIntroduction()
				+ "','"
				+ cp.getOperatorName()
				+ "','"
				+ cp.getOperatorPhone()
				+ "','"
				+ cp.getOperatorTel()
				+ "','"
				+ cp.getAddress()
				+ "','"
				+ cp.getCreditCard()
				+ "','"
				+ cp.getCreditLicense()
				+ "','"
				+ cp.getCreditTax()
				+ "','"
				+ cp.getTag1()
				+ "','"
				+ cp.getTag2()
				+ "','"
				+ cp.getTag3()
				+ "','"
				+ cp.getTag4()
				+ "','"
				+ cp.getTag5()
				+ "','"
				+ cp.getTag6()
				+ "','"
				+ cp.getTag7()
				+ "','"
				+ cp.getTag8()
				+ "','"
				+ cp.getTag9()
				+ "','"
				+ cp.getTag10()
				+ "',now(),now())";
		System.out.println(sql);
		try {
			connection.prepareStatement(sql).execute();
			return insertResult();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return null;
	}

	private CrmCompanyProfile queryOneTestRecord(Integer id) {
		String sql = "select `company_id`,`sale_details`,`buy_details`,`introduction`,`operator_name`," +
				"`operator_phone`,`operator_tel`,`address`,`credit_card`,`credit_license`,`credit_tax`,`tag1`," +
				"`tag2`,`tag3`,`tag4`,`tag5`,`tag6`,`tag7`,`tag8`,`tag9`,`tag10`,`gmt_created`,`gmt_modified` from `crm_company_profile` where id="
				+ id;
		System.out.println(sql);
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				return new CrmCompanyProfile(rs.getInt(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getString(5), rs
						.getString(6), rs.getString(7), rs.getString(8), rs
						.getString(9), rs.getString(10), rs.getString(11), rs
						.getString(12), rs.getString(13), rs.getString(14), rs
						.getString(15), rs.getString(16), rs.getString(17), rs
						.getString(18), rs.getString(19), rs.getString(20), rs
						.getString(21), null,null);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return null;
	}

}
