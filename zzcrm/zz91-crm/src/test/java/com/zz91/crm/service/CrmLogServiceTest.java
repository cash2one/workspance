//package com.zz91.crm.service;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Date;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//
//import com.zz91.crm.domain.CrmCompany;
//import com.zz91.crm.domain.CrmLog;
//import com.zz91.crm.domain.CrmSaleComp;
//
///** 
// * @author qizj 
// * @email  qizj@zz91.net
// * @version 创建时间：2011-12-13 
// */
//public class CrmLogServiceTest extends BaseServiceTestCase{
//	
//	@Resource
//	private CrmLogService crmLogService;
//	@Resource
//	private CrmCompanyService crmCompanyService;
//	@Resource
//	private CrmSaleCompService crmSaleCompService;
//	
//	
//	public Integer createCompany(CrmCompany crmCompany) {
//    	Integer result = 0;
//    	try {
//    		result = crmCompanyService.createCompany(crmCompany);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	return result;
//    }
//	
//	public Integer createCompany() {
//    	Integer result = 0;
//    	try {
//    		result = createCompany(
//    			new CrmCompany(
//    					null, 1, (short)1, null,
//    					null, null, "测试公司名称", 1,
//    					"user1", "user1@huanbao.com", "测试名字", (short)0,
//    					"15812345678", "86", "0578", "8108663",
//    					null, null, null, "测试地址",
//    					"310023", "公司简介", "10001000",
//    					"10001001", (short)1, "10001002",
//    					"100010011000", "1000100110001000", (short)0,
//    					null, (short)1, null,
//    					12, new Date(), new Date(), new Date(),
//    					null, null, "1", "2",
//    					null, null));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	return result;
//    }
//	 public void createCompanys(Integer size) {
//    	try {
//    		for (int i = 0; i < size; i++) {
//        		Integer count = createCompany(
//        			new CrmCompany(
//        					null, 1, (short)1, null,
//        					null, null, "测试公司名称"+i, 1,
//        					"user"+i, "user"+i+"@huanbao.com", "测试名字"+i, (short)0,
//        					"1581234567"+i, "86", "0578", "810866"+i,
//        					null, null, null, "测试地址"+i,
//        					"310023", "公司简介"+i, "10001000",
//        					"10001001", (short)1, "10001002",
//        					"100010011000", "1000100110001000", (short)0,
//        					null, (short)1, null,
//        					12, new Date(), new Date(), new Date(),
//        					null, null, "tulf", "10001000",
//        					null, null));
//        		CrmSaleComp crmSaleComp = new CrmSaleComp(null, count, (short)1, (short)0, "10001000", "tulf", "涂灵峰", (short)0, null, null, null, null, null, null, null, null, null, null, 0, 0, null, null);
//        		CrmLog crmLog=new CrmLog(null, (short)0, "dept", "account", "saleName", count, (short)0, (short)0, null, (short)0, 
//        				(short)1, null, null, null,null, null, null, null, null, null, null, null);
//        		crmSaleCompService.createCrmSaleComp(crmSaleComp);
//        		crmLogService.createCrmLog(crmLog, count, (short)1);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
//	
//	@Test
//	public void testCreateCrmLog() {
//		clean();
//		createCompanys(2);
//	}
//	
//	public CrmLog queryCrmLogById(Integer id){
//		String sql="select `id`,`call_type`,`sale_dept`,`sale_account`,`sale_name`,`cid`,`star_old`,`star`," +
//				"`gmt_next_contact`,`next_contact`,`situation`,`operation`,`operation_details`,`transaction`," +
//				"`transaction_details`,`feedback`,`suggestion`,`issue_status`,`issue_details`,`remark`,`gmt_created`," +
//				"`gmt_modified` from `crm_log` where id="+id;
//		try {
//			ResultSet rs=connection.prepareStatement(sql).executeQuery();
//			if (rs.next()){
//				return new CrmLog(rs.getInt(1), rs.getShort(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), 
//						rs.getShort(7), rs.getShort(8), rs.getDate(9), rs.getShort(10),rs.getShort(11), rs.getShort(12), rs.getString(13), rs.getShort(14), 
//						rs.getString(15), rs.getString(16), rs.getString(17), rs.getShort(18), rs.getString(19), rs.getString(20), null, null);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	public void clean(){
//		try {
//			connection.prepareStatement("delete from crm_log").execute();
//			connection.prepareStatement("delete from crm_company").execute();
//			connection.prepareStatement("delete from crm_sale_comp").execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//}
