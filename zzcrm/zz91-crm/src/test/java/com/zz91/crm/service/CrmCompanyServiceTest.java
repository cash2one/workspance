package com.zz91.crm.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.junit.Test;

import com.zz91.crm.domain.CrmCompany;
import com.zz91.crm.domain.CrmSaleComp;
import com.zz91.crm.domain.Param;
import com.zz91.crm.dto.CommCompanyDto;
import com.zz91.crm.dto.CompanySearchDto;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.dto.SaleCompanyDto;
import com.zz91.util.http.HttpUtils;

public class CrmCompanyServiceTest extends BaseServiceTestCase{

    @Resource
    private CrmCompanyService crmCompanyService;
    
    @Resource CrmSaleCompService crmSaleCompService;
    
    @Test
    public void testCreateCompany() {
    	clean();
    	try {
    		Integer result = crmCompanyService.createCompany(
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
    		assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testQueryCommCompany() {
    	clean();
    	testCreateCompany();
    	try {
    		List<CrmCompany> list = crmCompanyService.queryCommCompany("测试", "名字", "", "15812345678", "8108663",null, null);
    		assertEquals(list.size(), 1);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
//    @Test
//    public void testQueryCommCompanyByContact() {
//    	clean();
//    	testCreateCompany();
//    	try {
//    		List<CrmCompany> list = crmCompanyService.queryCommCompanyByContact("015812345678", "05788108663", 1);
//    		assertEquals(list.size(), 1);
//    	} catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
    
    @Test
    public void testQueryCompanyById() {
    	clean();
    	Integer count = createCompany();
    	try {
    		CrmCompany company = crmCompanyService.queryCompanyById(count);
    		assertNotNull(company);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testUpdateCompany(){
    	clean();
    	Integer count = createCompany();
    	try {
    		CrmCompany company = crmCompanyService.queryCompanyById(count);
    		company.setAddress("更改后地址");
    		int count2 = crmCompanyService.updateCompany(company);
    		assertEquals(count2, 1);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
//    @Test
//    public void testUpdateRegistStatus(){
//    	clean();
//    	Integer count = createCompany();
//    	try {
//    		int count2 = crmCompanyService.updateRegistStatus(count, CrmCompanyService.REGIST_STATUS_UNCHECK);
//    		assertEquals(count2, 1);
//    	} catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
    
    @Test
    public void testSearchMyCompany() {
    	try {
    		clean();
    		clean2();
    		createCompanys(9);
    		PageDto<SaleCompanyDto> page = new PageDto<SaleCompanyDto>();
    		CompanySearchDto search = new CompanySearchDto();
    		search.setSaleAccount("tulf");
    		search.setSaleDept("10001000");
    		search.setSaleType((short)0);
//    		search.setGmtNextContactStart(DateUtil.getDate(DateUtil.getDateAfter(new Date(), Calendar.MONTH, -1), "yyyy-MM-DD HH:mm:ss"));
//    		search.setGmtNextContactEnd(DateUtil.getDate(DateUtil.getDateAfter(new Date(), Calendar.MONTH, 1), "yyyy-MM-DD HH:mm:ss"));
    		page.setLimit(10);
			page = crmCompanyService.searchMyCompany(search ,page);
			System.out.println(page.getRecords().size());
			System.out.println(page.getTotals());
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
  @Test
  public void testSearchCommCompany() {
  	try {
  		clean();
  		clean2();
  		createCompanys(9);
  		PageDto<CommCompanyDto> page = new PageDto<CommCompanyDto>();
  		CompanySearchDto search = new CompanySearchDto();

//  		search.setGmtNextContactStart(DateUtil.getDate(DateUtil.getDateAfter(new Date(), Calendar.MONTH, -1), "yyyy-MM-DD HH:mm:ss"));
//  		search.setGmtNextContactEnd(DateUtil.getDate(DateUtil.getDateAfter(new Date(), Calendar.MONTH, 1), "yyyy-MM-DD HH:mm:ss"));
  		page.setLimit(10);
			page = crmCompanyService.searchCommCompany(search ,page);
			System.out.println(page.getRecords().size());
			System.out.println(page.getTotals());
  	} catch (Exception e) {
			e.printStackTrace();
		}
  }
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateCompanySys() {
		String responseText = null;
		System.out.println(123);
		try {
//			responseText = HttpUtils.getInstance().httpGet("http://huanbao.zz91.com/ep-admin/api/todayUpdateCompany.htm", HttpUtils.CHARSET_UTF8);
			responseText = HttpUtils.getInstance().httpGet("http://huanbao.zz91.com/ep-admin/api/params.htm?types=register_type", HttpUtils.CHARSET_UTF8);
		} catch (HttpException e) {
			System.out.print("*******************HttpException************************");
		} catch (IOException e) {
			System.out.print("*******************IOException**************************");
		}
		JSONArray jsonarray=JSONArray.fromObject(responseText);
		List<Param> params = new ArrayList<Param>();
		for (Iterator iter = jsonarray.iterator(); iter.hasNext();) {
			JSONObject object = (JSONObject) iter.next();
			Param param = new Param();
			param.setName(object.getString("name"));
			param.setId(object.getInt("id"));
			params.add(param);
		}
		System.out.println(params.size());
	}

    public void createCompanys(Integer size) {
    	try {
    		for (int i = 0; i < size; i++) {
        		Integer count = createCompany(
        			new CrmCompany(
        					null, 1, (short)1, (short)1,
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
        		CrmSaleComp crmSaleComp = new CrmSaleComp(null, count, (short)1, (short)0, "10001000", "tulf", "涂灵峰", (short)0, null, null, null, null, null, null, null, null, null, null,0,0, null, null);
        		crmSaleCompService.createCrmSaleComp(crmSaleComp,null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
    
    public Integer createCompany(CrmCompany crmCompany) {
    	Integer result = 0;
    	try {
    		result = crmCompanyService.createCompany(crmCompany);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
    
    private void clean() {
        try {
            connection.prepareStatement("delete from crm_company").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void clean2() {
        try {
            connection.prepareStatement("delete from crm_sale_comp").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
