package com.ast.ast1949.service.company.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.ast.ast1949.domain.company.CrmServiceApply;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.company.CrmSvrApplyService;
import com.ast.ast1949.util.DateUtil;

public class CrmSvrApplyServiceTest extends BaseServiceTestCase {
	@Resource
	CrmSvrApplyService crmSvrApplyService;
	@Test
	public void testQueryApplyByGroup() {
		clean();
		createOneTestRecord(new CrmServiceApply("4456",
				"64612", new Date(), null, null, "", "liubo",
				"", "", null, null));
		CrmServiceApply crm=crmSvrApplyService.queryApplyByGroup("4456");
		assertNotNull(crm);
		assertEquals("4456", crm.getApplyGroup());
		
		assertEquals("liubo", crm.getSaleStaff());
	}

	@Test
	public void testUpdateApply() {
		clean();
		CrmServiceApply crm=new CrmServiceApply("1",
				"1", new Date(), "old email", 0, "old amount details", "xiaoxiao",
				"old remark", "old membershipCode", null, null);
		Integer id=createOneTestRecord(crm);
		crm.setId(id);
		crm.setEmail("new email");
		crm.setRemark("new remark");
		crm.setMembershipCode("new membershipCode");
		Integer i=crmSvrApplyService.updateApply(crm);
		assertEquals(1, i.intValue());
		
		assertEquals("new email", crm.getEmail());
		assertEquals("new remark", crm.getRemark());
		assertEquals("new membershipCode", crm.getMembershipCode());
	}

	private void clean() {
		try {
			connection.prepareStatement("delete from crm_service_apply")
					.execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public CrmServiceApply queryOneTestRecordByGroup(Integer id) {
		String sql = "select `apply_group`,`order_no`,`gmt_income`,`email`,`amount`,`amount_details`,`sale_staff`,"
				+ "`remark`,`membership_code`,`gmt_created`,`gmt_modified` from `crm_service_apply` where id="+ id;
		System.out.println(sql);
		try {
			ResultSet rs = connection.createStatement().executeQuery(sql);
			
			if (rs.next()) {
				return new CrmServiceApply(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getString(4), 
						rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), null, null);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return null;
	}

	public Integer createOneTestRecord(CrmServiceApply crm) {
		String sql = "insert into `crm_service_apply` (`apply_group`,`order_no`,`gmt_income`,`email`,`amount`," +
				"`amount_details`,`sale_staff`,`remark`,`membership_code`,`gmt_created`,`gmt_modified`)"
				+ "values('"
				+ crm.getApplyGroup()
				+ "','"
				+ crm.getOrderNo()
				+ "','"
				+ DateUtil.toString(crm.getGmtIncome(),"yyyy-MM-dd hh:mm:ss")
				+ "','"
				+ crm.getEmail()
				+ "',"
				+ crm.getAmount()
				+ ",'"
				+ crm.getAmountDetails()
				+ "','"
				+ crm.getSaleStaff()
				+ "','"
				+ crm.getRemark()
				+ "','"
				+ crm.getMembershipCode()
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
}
