package com.ast.feiliao91.service.logistics.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ast.feiliao91.domain.logistics.Logistics;
import com.ast.feiliao91.service.BaseServiceTestCase;
import com.ast.feiliao91.service.commom.ParamService;
import com.ast.feiliao91.service.logistics.LogisticsService;
import com.zz91.util.domain.Param;

public class LogisticsServiceTest extends BaseServiceTestCase{
	@Autowired
    private LogisticsService logisticsService;
	@Autowired 
	private ParamService paramService;
	
	public void test_logisticsComName(){
//		String logisticsCompany = "debangwuliu";
//		String s=paramService.queryParamByKey(logisticsCompany).getValue();
//		String s=paramService.getValueBylogisticsCompany(logisticsCompany,1);
//		System.out.println(s);
		String logisticsCode="325498582";
		String type="1";
		logisticsService.updaLogisticsByCode(logisticsCode,type);
	}
/*	public void test_select(){
		String code="111";
		Logistics logistics=logisticsService.selectLogisticsByCode(code);
		Logistics logistics2 = selectByCode(code);
		System.out.println(logistics.getId()+"+++++++++");
		System.out.println(logistics2.getId()+"+++++++++");
		assertEquals(logistics.getLogisticsInfo(),logistics2.getLogisticsInfo());
	}*/
     
	/*public void test_insert(){
		clean();
		Logistics rs = new Logistics();
		rs.setLogisticsNo("11111");
		rs.setLogisticsInfo("你好呀");
		rs.setLogisticsStatus(1);
		Integer i=logisticsService.insertLogistics(rs);
		Integer b=selectByResult();
		assertEquals(i,b);
	}*/
/*	@SuppressWarnings("unused")
	public void test_getMap(){
		String code="11111";
		Logistics logistics=logisticsService.selectLogisticsByCode(code);
		
		Map<String,Object> map = logisticsService.getmap(logistics.getLogisticsInfo());
		Map<String,Object> map2=logisticsService.getmap((String)map.get("data"));
	}
	*/
	private Integer selectByResult() {
		try {
			ResultSet rs =connection.createStatement().executeQuery("select id from logistics order by id desc limit 1");
		   if(rs.next()){
			   return rs.getInt(1);
		   }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	private Logistics selectByCode(String code) {
		ResultSet rs=null;
		Logistics cs = new Logistics();
		try {
			 rs = connection.createStatement().executeQuery("select * from logistics where logistics_no="+code);
				if(rs.next()){
					cs.setId(rs.getInt(1));
					cs.setLogisticsNo(rs.getString(2));
					cs.setLogisticsInfo(rs.getString(3));
					cs.setLogisticsStatus(rs.getInt(4));
				}
				return cs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
     
	private void clean(){
		try {
			connection.prepareStatement("delete  from logistics").execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
     		}
		
	}
	
	
}
