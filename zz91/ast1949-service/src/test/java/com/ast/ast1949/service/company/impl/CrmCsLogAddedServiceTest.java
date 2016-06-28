package com.ast.ast1949.service.company.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.junit.Test;

import com.ast.ast1949.domain.company.CrmCsLogAdded;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.company.CrmCsLogAddedService;

public class CrmCsLogAddedServiceTest extends BaseServiceTestCase{
	@Resource 
	CrmCsLogAddedService crmCsLogAddedService;
	
	@Test
	public void testCreateAdded() {
		clean();
		Integer id=crmCsLogAddedService.createAdded(new CrmCsLogAdded(1, "lixh", "满意的", null, null),1);
		CrmCsLogAdded added=queryOneTestRecord(id);
		assertNotNull(added);
		assertEquals(1, added.getCrmCsLogId().intValue());
		assertEquals("lixh", added.getCsAccount());
		assertEquals("满意的", added.getContent());
	}
	
	public void clean()	{
		try {
			connection.prepareStatement("delete from crm_cs_log_added").execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	private CrmCsLogAdded queryOneTestRecord(Integer id)	{
		String sql="select `crm_cs_log_id`,`cs_account`,`content`,`gmt_created`,`gmt_modified` from crm_cs_log_added where id=" +
				id;
		System.out.println(sql);
		try {
			ResultSet rs=connection.createStatement().executeQuery(sql);
			if(rs.next()){
				
				return new CrmCsLogAdded(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getDate(5));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
}
