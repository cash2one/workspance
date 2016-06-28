package com.kl91.service.junit.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import com.kl91.domain.company.EsiteFriendlink;
import com.kl91.service.company.EsiteFriendlinkService;
import com.kl91.service.junit.BaseServiceTestCase;

public class EsiteFriendLinkServiceTest extends BaseServiceTestCase{
	
	@Resource
	private EsiteFriendlinkService esiteFriendlinkService;
	
	public void test_createFriendlink(){
		clean();
		Integer i = esiteFriendlinkService.createFriendlink(getEsiteFriendlink());
		assertNotNull(i);
		assertTrue(i.intValue() > 0);
		
		EsiteFriendlink esiteFriendlink = queryOne(i);
		assertNotNull(esiteFriendlink);
		assertEquals("测试添加", esiteFriendlink.getName());
	}
	
	public void test_editFriendlink(){
		clean();
			Integer id = insert("测试更新");
			insert("测试更新2");
			insert("测试更新3");
			insert("测试更新4");
			insert("测试更新5");
			insert("测试更新6");
			insert("测试更新7");
			assertTrue(id.toString(), id.intValue() > 0);

			EsiteFriendlink esiteFriendlink=getEsiteFriendlink();
			esiteFriendlink.setId(id);
			esiteFriendlink.setName("测试更新后的数据");
			Integer i = esiteFriendlinkService.editFriendlink(esiteFriendlink);
			assertNotNull(i);
			assertEquals("测试更新后的数据",esiteFriendlink.getName());
	}
	public  void test_deleteById(){
			clean();
			Integer id1 = insert("测试删除1");
			Integer id2 = insert("测试删除2");
	
			Integer i =esiteFriendlinkService.deleteById(id1);
			assertNotNull(i);
			assertEquals(1, i.intValue());
			
			EsiteFriendlink esiteFriendlink = queryOne(id1);
			assertNull(esiteFriendlink);
			
			EsiteFriendlink esiteFriendlink2= queryOne(id2);
			assertNotNull(esiteFriendlink2);
	}
	
	public void test_queryById(){
		clean();
		Integer id1 = insert("测试查询1");
		insert("测试查询2");

		EsiteFriendlink esiteFriendlink=esiteFriendlinkService.queryById(id1);
		assertNotNull(esiteFriendlink);
		assertEquals("测试查询1", esiteFriendlink.getName());
	}
	
	public void test_queryFriendlink() {
		clean();
		for (int i = 1; i <= 10; i++) {
			insert("测试查询" + i);
		}

		List<EsiteFriendlink> esiteFriendlinks = esiteFriendlinkService.queryFriendlink(null);
		assertEquals(10, esiteFriendlinks.size());
	}

	private Integer insert(String name) {
		if(name==null){
			name="";
		}
		String sql="insert into esite_friendlink(cid,name,logo_url,url,show_flag,remark,orderby,gmt_created,gmt_modified)" +
				"values(1,'"+name+"','logoUrl','Url',0,'remark',0,now(),now())";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertResult();
	}

	private EsiteFriendlink queryOne(Integer id) {
		EsiteFriendlink esiteFriendlink = null;
		try {
			ResultSet rs = connection.prepareStatement(
					"SELECT * FROM esite_friendlink WHERE id = " + id).executeQuery();
			if (rs.next()) {
				esiteFriendlink=new EsiteFriendlink(rs.getInt("id"), rs.getInt("cid"), 
						rs.getString("name"), rs.getString("logo_url"), rs.getString("url"), rs.getInt("show_flag"), 
						rs.getString("remark"), rs.getInt("orderby"), rs.getDate("gmt_created"), rs.getDate("gmt_modified"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return esiteFriendlink;
	}

	private EsiteFriendlink getEsiteFriendlink() {
		return new EsiteFriendlink(null, 12, "测试添加", "logoUrl", "url", 0, "remark", 1, new Date(), new Date());
	}

	private void clean() {
		try {
			connection.prepareStatement("DELETE FROM esite_friendlink").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
