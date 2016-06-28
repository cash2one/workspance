package com.zz91.crm.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.zz91.crm.domain.CrmCompany;
import com.zz91.crm.domain.CrmSaleComp;

/**
 * @author qizj
 * @email qizj@zz91.net
 * @version 创建时间：2011-12-14
 */
public class CrmSaleCompServiceTest extends BaseServiceTestCase {

	@Resource
	private CrmSaleCompService crmSaleCompService;
	@Resource
	private CrmCompanyService crmCompanyService;
	

	@Test
	public void testCreateCrmSaleComp() {
		clean();
		createTestOneResult(1);
	}
	
	public Integer createCompany(CrmCompany crmCompany) {
    	Integer result = 0;
    	try {
    		result = crmCompanyService.createCompany(crmCompany);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
	
	public Integer createCompany() {
    	Integer result = 0;
    	try {
    		result = createCompany(
    			new CrmCompany(
    					null, 1, (short)1, null,
    					null, null, "测试公司名称", 1,
    					"user1", "user1@huanbao.com", "测试名字", (short)0,
    					"15812345678", "86", "0578", "8108663",
    					null, null, null, "测试地址",
    					"310023", "公司简介", "10001000",
    					"10001001", (short)1, "10001002",
    					"100010011000", "1000100110001000", (short)0,
    					null, (short)1, null,
    					12, new Date(), new Date(), new Date(),
    					null, null, "1", "2",
    					null, null));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
	 public void createTestOneResult(Integer size) {
    	try {
    		for (int i = 0; i < size; i++) {
        		Integer count = createCompany(
        			new CrmCompany(
        					null, 1, (short)1, null,
        					null, null, "测试公司名称"+i, 1,
        					"user"+i, "user"+i+"@huanbao.com", "测试名字"+i, (short)0,
        					"1581234567"+i, "86", "0578", "810866"+i,
        					null, null, null, "测试地址"+i,
        					"310023", "公司简介"+i, "10001000",
        					"10001001", (short)1, "10001002",
        					"100010011000", "1000100110001000", (short)0,
        					null, (short)1, null,
        					12, new Date(), new Date(), new Date(),
        					null, null, "tulf", "10001000",
        					null, null));
        		CrmSaleComp crmSaleComp = new CrmSaleComp(null, count, (short)1, (short)0, "10001000", "tulf", "涂灵峰", (short)0, null, null, null, null, null, null, null, null, null, null, 0, 0, null, null);
        		Integer csc=crmSaleCompService.createCrmSaleComp(crmSaleComp,null);// 放入个人库 同时更新公司表ctype
        		
        		crmSaleCompService.updateStatus(count, (short)0);//0自动掉公海 ,1手动掉公海
        		
        		crmSaleCompService.updateCompanyType(csc, (short)1); //设置是否为重点用户 0普通 1重点
        		
        		crmSaleCompService.updateDisableStatus(csc); //放入废品池
        		
        		crmSaleCompService.checkDisableStatus(count, (short)0);//审核放入废品池 成功变更公司表Ctype 失败更新crmsalecomp disable_status为0(即审核失败)
			
        		crmSaleCompService.queryCrmSaleCompByCid(count);
        		
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	 
	@Test
	public void testUpdateStatus() {
		clean();
		createTestOneResult(1);
	}

	@Test
	public void testUpdateCompanyType() {
		clean();
		createTestOneResult(1);
	}

	@Test
	public void testUpdateDisableStatus() {
		clean();
		createTestOneResult(1);
	}

	@Test
	public void testCheckDisableStatus() {
		clean();
		createTestOneResult(1);
	}

	@Test
	public void testQueryCrmSaleCompByCid() {
		clean();
		createTestOneResult(1);
	}
	
	public CrmSaleComp queryCrmSaleCompById(Integer id) {
		String sql = "select `id`,`cid`,`status`,`sale_type`,`sale_dept`,`sale_account`,`sale_name`,`company_type`,`log_id`,`auto_block`,"
				+ "`gmt_block`,`gmt_contact`,`gmt_next_contact`,`contact_count`,`contact_able_count`,`contact_disable_count`,`disable_status`,"
				+ "`success_status`,`gmt_modified`,`gmt_created` from `crm_sale_comp` where id="
				+ id;
		try {
			ResultSet rs = connection.prepareStatement(sql).executeQuery();
			if (rs.next()) {
				return new CrmSaleComp(rs.getInt(1), rs.getInt(2),
						rs.getShort(3), rs.getShort(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getShort(8),
						rs.getInt(9), rs.getShort(10), rs.getDate(11),
						rs.getDate(12), rs.getDate(13), rs.getInt(14),
						rs.getInt(15), rs.getInt(16), rs.getShort(17),
						rs.getShort(18),0,0, null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

//	public Integer createCrmSaleComp(CrmSaleComp crmSaleComp){
//		String sql="insert into `crm_sale_comp` (`id`,`cid`,`status`,`sale_dept`,`sale_account`,`sale_name`," +
//				"`gmt_modified`,`gmt_created`) values (" 
//				+crmSaleComp.getId()+","+crmSaleComp.getCid()+","+crmSaleComp.getStatus()+","+crmSaleComp.getSaleDept()
//				+",'"+crmSaleComp.getSaleAccount()+"','"+crmSaleComp.getSaleName()+"',now(),now())";
//		try {
//			connection.prepareStatement(sql).execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	public CrmSaleComp crmSaleComp(){
//		CrmSaleComp company = new CrmSaleComp();
//		company.setId(50);
//		company.setCid(82);
//		company.setStatus((short)0);
//		company.setSaleDept("技术部");
//		company.setSaleAccount("qizj");
//		return company;
//	}
	
	public void clean() {
		try {
			connection
					.prepareStatement("delete from crm_sale_comp")
					.execute();
			connection
					.prepareStatement("delete from crm_company")
					.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
