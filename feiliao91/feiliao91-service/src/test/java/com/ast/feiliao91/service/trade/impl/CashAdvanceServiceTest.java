package com.ast.feiliao91.service.trade.impl;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.feiliao91.domain.trade.CashAdvance;
import com.ast.feiliao91.service.BaseServiceTestCase;
import com.ast.feiliao91.service.trade.CashAdvanceService;

public class CashAdvanceServiceTest extends BaseServiceTestCase{
	@Autowired
    private CashAdvanceService cashAdvanceService;
	
	@Test
	public void test_insert(){
		CashAdvance cashAdvance = new CashAdvance();
		cashAdvance.setCompanyId(25);
		cashAdvance.setLinkman("不vsadklas");
		cashAdvance.setMobile("139845132");
		cashAdvance.setBank("局部有");
		cashAdvance.setBankName("中国人民");
		cashAdvance.setBankAccount("488932423412312");
		cashAdvance.setMoney((float) 2000.0);
		cashAdvance.setStatus(0);
		cashAdvance.setCheckPerson("sad");
		cashAdvanceService.updateCashAdvanceTransactional(cashAdvance);
	}
	
//	@Test
//	public void test_query(){
//		CashAdvance cashAdvance = new CashAdvance();
//		cashAdvance.setCompanyId(20);
//		cashAdvance.setLinkman("fwefw");
//		List<CashAdvance> list = cashAdvanceService.queryByCondition(cashAdvance);
//		for (CashAdvance ca : list) {
//			System.out.println(ca.getId());
//		}
//	}
}
