package com.shebei.service.impl;



import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


import javax.annotation.Resource;

import org.junit.Test;
import com.ast1949.shebei.domain.Company;
import com.ast1949.shebei.dto.PageDto;
import com.ast1949.shebei.service.CompanyService;
import com.shebei.service.BaseServiceTestCase;


public class CompranyServiceTest extends BaseServiceTestCase{
	
	@Resource
	private CompanyService companyService;
	
	@Test
	public void testCreateCompany() {
		
	}

	@Test
	public void testQueryCompanyById() {
//		clean();	
//		createOneTestRecord(getCompany(1002));
//		Company c = companyService.queryCompanyById(1002);
//		assertNotNull(c);
//		int  id = c.getId();
//		assertEquals(1002, id);
	}

	@Test
	public void testPageCompanys() {
		clean();
		manyTestRecord(60);
//		PageDto<Company> page=new PageDto<Company>();
//		page.setStart(5);
//		page.setLimit(5);
//		page = companyService.pageCompanys(page,"10001000");
//		assertNotNull(page.getRecords());
//		assertEquals(2, page.getRecords().size());
//		assertEquals(60, page.getTotals().intValue());
//		
//		page.setStart(0);
//		page.setLimit(5);
//		page = companyService.pageCompanys(page,"10001000");
//		assertNotNull(page);
//		assertEquals(5, page.getRecords().size());
//		assertEquals(60, page.getTotals().intValue());
	}

	@Test
	public void testQueryDeatilsById() {
//		clean();
//		createOneTestRecord(getCompany(1002));
//		String details = companyService.queryDeatilsById(1002);
//		assertNotNull(details);
//		assertEquals("甲骨文公司就是好啊好i哦啊", details);
	}

	@Test
	public void testQueryContactById() {
//		clean();
//		createOneTestRecord(getCompany(1002));
//		Company c  = companyService.queryContactById(1002);
//		assertNotNull(c);
//		assertEquals("jie", c.getContact());
	}

	@Test
	public void testQueryNewestCompany() {
//		clean();
//		manyTestRecord(7);
//		List<Company> list = companyService.queryNewestCompany(5,"10001000");
//		assertNotNull(list);
//		assertEquals(5,list.size());
	}

	@Test
	public void testQueryNameById() {
//		clean();
//		createOneTestRecord(getCompany(1002));
//		String name = companyService.queryNameById(1002);
//		assertNotNull(name);
//		assertEquals("xiao", name);
	}

	@Test
	public void testQueryMaxGmtShow() throws ParseException {
//		clean();
//		createOneTestRecord(getCompany(1002));
//		Date date = companyService.queryMaxGmtShow();
//		assertNotNull(date);
	}

	private void clean()	{
		try {
			connection.prepareStatement("delete from company").execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	private Integer createOneTestRecord(Company company)	{
		  StringBuffer sb = new StringBuffer();
		  sb.append("insert into company (id,name,contact,sex,mobile,phone,fax,address,address_zip,details,category_code,main_buy," +
		  		"main_product_buy,main_supply,main_product_supply,account,gmt_show,gmt_created,gmt_modified) values");
		  sb.append("(");
		  sb.append(company.getId()+",");
		  sb.append("'"+company.getName()+"',");
		  sb.append("'"+company.getContact()+"',");
		  sb.append(company.getSex()+",");
		  sb.append("'"+company.getMobile()+"',");
		  sb.append("'"+company.getPhone()+"',");
		  sb.append("'"+company.getFax()+"',");
		  sb.append("'"+company.getAddress()+"',");
		  sb.append("'"+company.getAddressZip()+"',");
		  sb.append("'"+company.getDetails()+"',");
		  sb.append("'"+company.getCategoryCode()+"',");
		  sb.append(company.getMainBuy()+",");
		  sb.append(company.getMainProductBuy()+",");
		  sb.append(company.getMainSupply()+",");
		  sb.append("'"+company.getMainProductSupply()+"',");
		  sb.append("'"+company.getAccount()+"',");
		  sb.append("now(),now(),now())");
		 		 try {
				connection.prepareStatement(sb.toString()).execute();
				return insertResult();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return null;
		}
	
	private Company getCompany(int cid){
		Company company = new Company();
		company.setId(cid);
		company.setName("xiao");
		company.setContact("jie");
		company.setSex((short)1);
		company.setMobile("123456");
		company.setPhone("321554");
		company.setFax("0577-5645");
		company.setAddress("zjhangzhou");
		company.setAddressZip("445406");
		company.setDetails("甲骨文公司就是好啊好i哦啊");
		company.setCategoryCode("10001000");
		company.setMainBuy((short)0);
		company.setMainProductSupply("污水");
		company.setAccount("xtotly");
		company.setMainSupply((short)1);
		return company;
	}
	public void manyTestRecord(int num) {
		for (int i = 0; i < num; i++) {
			createOneTestRecord(getCompany(i));
		}
	}
}
