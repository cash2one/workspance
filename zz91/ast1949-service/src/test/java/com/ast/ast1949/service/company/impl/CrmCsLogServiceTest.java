package com.ast.ast1949.service.company.impl;

import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.ast.ast1949.domain.company.CrmCsLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CrmCsLogDto;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.company.CrmCsLogService;
import com.ast.ast1949.util.DateUtil;

public class CrmCsLogServiceTest extends BaseServiceTestCase {
	@Resource
	CrmCsLogService crmCsLogService;

	@Test
	public void testPageByCompany_companyId_notnull() {
		clean();
		manyTestRecord(7, 1);
		PageDto<CrmCsLogDto> page = new PageDto<CrmCsLogDto>();
		page.setStartIndex(5);
		page.setTotalRecords(5);
		page = crmCsLogService.pageLogByCompany(1, null, null, null, null,
				null, null, page);
		assertNotNull(page.getRecords());
		assertEquals(2, page.getRecords().size());
		assertEquals(7, page.getTotalRecords().intValue());

		page.setPageSize(5);
		page.setStartIndex(0);
		page = crmCsLogService.pageLogByCompany(1, null, null, null, null,
				null, null, page);
		assertNotNull(page);
		assertEquals(5, page.getRecords().size());
		assertEquals(7, page.getTotalRecords().intValue());
	}

	private void clean() {
		try {
			connection.prepareStatement("delete from crm_cs_log").execute();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public Integer createOneRecord(CrmCsLog ccl) {
		String sql = "insert into `crm_cs_log`(`cs_account`,`company_id`,`gmt_next_visit_phone`,"
				+ "`gmt_next_visit_email`,`is_visit_phone`,`is_visit_email`,`is_visit_sms`,"
				+ "`call_type`,`situation`,`operation`,`operation_details`,`transaction`,"
				+ "`transaction_details`,`feedback`,`suggestion`,`issue_status`,"
				+ "`issue_details`,`visit_target`,`star`,`remark`,`gmt_created`,`gmt_modified`)"
				+ "values('"
				+ ccl.getCsAccount()
				+ "',"
				+ ccl.getCompanyId()
				+ ",'"
				+ DateUtil.toString(ccl.getGmtNextVisitPhone(),
						"yyyy-MM-dd hh:mm:ss")
				+ "','"
				+ DateUtil.toString(ccl.getGmtNextVisitEmail(),
						"yyyy-MM-dd hh:mm:ss")
				+ "','"
				+ ccl.getIsVisitPhone()
				+ "','"
				+ ccl.getIsVisitEmail()
				+ "','"
				+ ccl.getIsVisitSms()
				+ "',"
				+ ccl.getCallType()
				+ ","
				+ ccl.getSituation()
				+ ","
				+ ccl.getOperation()
				+ ",'"
				+ ccl.getOperationDetails()
				+ "',"
				+ ccl.getTransaction()
				+ ",'"
				+ ccl.getTransactionDetails()
				+ "','"
				+ ccl.getFeedback()
				+ "','"
				+ ccl.getSuggestion()
				+ "',"
				+ ccl.getIssueStatus()
				+ ",'"
				+ ccl.getIssueDetails()
				+ "','"
				+ ccl.getVisitTarget()
				+ "',"
				+ ccl.getStar()
				+ ",'"
				+ ccl.getRemark() + "',now(),now())";
		System.out.println(sql);
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	public CrmCsLog oneTestRecord(Integer companyId) {
		return null;
		//return new CrmCsLog(null, companyId, new Date(), new Date(), "1", "1",
		//		"1", 1, 2, 3, "", 0, "", "", "", 0, "", "", 0, "", null, null);
	}

	public void manyTestRecord(int num, Integer companyId) {
		for (int i = 0; i < num; i++) {
			createOneRecord(oneTestRecord(companyId));
		}
	}
}
