package com.kl91.service.junit.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import com.kl91.domain.company.Company;
import com.kl91.service.company.CompanyService;
import com.kl91.service.junit.BaseServiceTestCase;

public class CompanyServiceTest extends BaseServiceTestCase{
	
	@Resource
	private CompanyService companyService;
	
	/**
	 * 测试添加
	 */								
	public void test_insert_company(){
		clean();
		Integer i = companyService.createCompany(getCompany());
		assertNotNull(i);
		assertTrue(i.intValue() > 0);
		
		Company company = queryOne(i);
		assertNotNull(company);
		assertEquals("测试添加", company.getCompanyName());
	}
	
	/**
	 * 测试修改
	 * @return
	 */
	public void test_update_company() {
		clean();
		Integer id = insert("测试更新","","",1);
		insert("测试更新2","","",1);
		insert("测试更新3","","",1);
		insert("测试更新4","","",1);
		insert("测试更新5","","",1);
		insert("测试更新6","","",1);
		insert("测试更新7","","",1);
		assertTrue(id.toString(), id.intValue() > 0);

		Company company = getCompany();
		company.setId(id);
		company.setCompanyName("测试更新后的数据");
		Integer i = companyService.editCompany(company);
		assertNotNull(i);
		assertEquals("测试更新后的数据", company.getCompanyName());
	}
	/**
	 * 测试删除
	 */
//	public void test_delete_company() {
//		clean();
//		int id1 = insert("测试删除1","","");
//		int id2 = insert("测试删除2","","");
//
//		Integer i = companyService.deleteCompanyById(id1);
//		assertNotNull(i);
//		assertEquals(1, i.intValue());
//		
//		Company company = queryOne(id1);
//		assertNull(company);
//		
//		Company company1 = queryOne(id2);
//		assertNotNull(company1);
//	}
	/**
	 * 测试查询一条
	 */
	public void test_queryOne_company() {
		clean();
		Integer id1 = insert("测试查询1","","",1);
		insert("测试查询2","","",1);

		Company t =companyService.queryById(id1);
		assertNotNull(t);
		assertEquals("测试查询1", t.getCompanyName());
	}
	/**
	 * 测试查询introduction
	 */
	public void test_queryIntroductionById(){
		clean();
		Integer id1 = insert("测试查询1","introduction12345","",1);
		insert("测试查询2","introduction123","",1);
		
		Company t =companyService.queryById(id1);
		assertNotNull(t);
		assertEquals("introduction12345", t.getIntroduction());
	}
	/**
	 * 测试查询domain
	 */
	public void test_queryDomainById(){
		clean();
		Integer id1 = insert("测试查询1","introduction12345","domain12345",1);
		insert("测试查询2","introduction123","domain123",1);
		
		Company t =companyService.queryById(id1);
		assertNotNull(t);
		assertEquals("domain12345", t.getDomain());
	}
	/**
	 * 测试queryCompanyFromSolr
	 */
	public void test_queryCompanyFromSolr(){
		
	}
	
	public void test_updateNumPassById(){
		clean();
		Integer id=insert("测试修改", "introduction", "domain", 3);
		insert("测试修改", "introduction", "domain", 4);
		assertTrue(id.toString(), id.intValue() > 0);
		
		Company company = getCompany();
		company.setId(id);
		company.setNumPass(2);
		Integer i = companyService.updateNumPassById(id, null);
		assertNotNull(i);
		assertTrue(2==company.getNumPass());
	}
	
	/**
	 * 测试queryMostPublic
	 */
	public void test_queryMostPublic(){
		clean();
		
	}
	
	private Integer insert(String companyName,String introduction,String domain,Integer numPass) {
		if(companyName==null){
			companyName="nihao";
		}
		if(introduction==null){
			introduction="";
		}
		if(domain==null){
			domain="";
		}
		String sql="insert into company(account,company_name,industry_code,membership_code,sex,contact,password,mobile,is_active,qq," +
				"email,tel,fax,area_code,zip,address,position,department,introduction,business," +
				"domain,website,num_login,regist_flag,show_time,gmt_last_login,gmt_created,gmt_modified,num_pass)values(" +
				"'account','"+companyName+"','industryCode','membershipCode',0," +
				"'contact','password','mobile',0,'qq','email','tel','fax','areaCode'," +
				"'zip','address','position','department','"+introduction+"','business','"+domain+"'," +
				"'website',0,0,now(),now(),now(),now(),"+numPass+")";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertResult();

	}

	
	public Company getCompany() {
		return new Company(null, "account", "测试添加", "industryCode", "membershipCode", 1, "contact",
				"password", "mobile",0, "qq", "email", "tel", "fax", "areaCode", "zip", 
				"address", "position", "department", "introduction", "business", "domain", "website", 
				1, 1, new Date(), new Date(), new Date(), new Date(), 1);
	}

	private Company queryOne(Integer id) {
		Company company = null;
		try {
			ResultSet rs = connection.prepareStatement(
					"SELECT * FROM company WHERE id = " + id).executeQuery();
			if (rs.next()) {
				company =new Company(rs.getInt("id"), rs.getString("account"), rs.getString("company_name"), 
				rs.getString("industry_code"), rs.getString("membership_code"), rs.getInt("sex"), rs.getString("contact"), 
				rs.getString("password"), rs.getString("mobile"),rs.getInt("is_active"), rs.getString("qq"), rs.getString("email"), 
				rs.getString("tel"), rs.getString("fax"), rs.getString("area_code"), rs.getString("zip"),
				rs.getString("address"), rs.getString("position"), rs.getString("department"), rs.getString("introduction"), 
				rs.getString("business"), rs.getString("domain"), rs.getString("website"), 
				rs.getInt("num_login"), rs.getInt("regist_flag"), rs.getDate("show_time"), rs.getDate("gmt_last_login"), 
				rs.getDate("gmt_created"), rs.getDate("gmt_modified"), rs.getInt("num_pass"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}
	
	public void clean() {
		try {
			connection.prepareStatement("DELETE FROM company").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
