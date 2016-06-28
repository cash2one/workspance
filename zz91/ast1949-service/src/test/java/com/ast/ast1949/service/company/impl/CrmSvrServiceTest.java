package com.ast.ast1949.service.company.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ast.ast1949.domain.company.CrmSvr;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.company.CrmSvrService;

public class CrmSvrServiceTest extends BaseServiceTestCase{
	@Resource
	CrmSvrService crmService;
	@Test
	public void testQuerySvr() {
		clean();
		createOneTestRecord(new CrmSvr("13646", "lixh", null, null, null, null, null));
		createOneTestRecord(new CrmSvr("13647", "liubo", null, null, null, null, null));
		createOneTestRecord(new CrmSvr("13648", "liming", null, null, null, null, null));
		createOneTestRecord(new CrmSvr("13649", "zhangsan", null, null, null, null, null));
		List<CrmSvr> list=crmService.querySvr();
		assertEquals(4,list.size());
		
	}
	private void clean() {
		try {
			connection.prepareStatement("delete from crm_service");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public Integer createOneTestRecord(CrmSvr cr)	{
		String sql="insert into `crm_service`(`code`,`name`,`remark`,`unit_price`,`units`,`gmt_created`,`gmt_modified`)" +
		"values('"
		+ cr.getCode()
		+ "','"
		+ cr.getName()
		+ "','"
		+ cr.getRemark()
		+ "',"
		+ cr.getUnitPrice()
		+ ",'"
		+ cr.getUnits()
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
	public List<CrmSvr> queryManyTestRecord(Integer id){
		
		String sql="select `code`,`remark`,`unit_price`,`units`,`gmt_created`,`gmt_modified` from `crm_service` where id=" +
				id;
		try {
			ResultSet rs=connection.createStatement().executeQuery(sql);
			List<CrmSvr> list=new ArrayList<CrmSvr>();
			while(rs.next()){
				
				CrmSvr cs=new CrmSvr(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),null,null);
				list.add(cs);
			}
			return list;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
}
