package com.kl91.service.junit.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import com.kl91.domain.company.CreditFile;
import com.kl91.service.company.CreditFileService;
import com.kl91.service.junit.BaseServiceTestCase;

public class CreditFileServiceTest extends BaseServiceTestCase{
	
	@Resource
	private CreditFileService creditFileService;
	
	public void test_createCredit(){
//		clean();
//		Integer i = creditFileService.createByUser(getCreditFile(), null);
//		assertNotNull(i);
//		assertTrue(i.intValue() > 0);
//		
//		CreditFile createFile = queryOne(i);
//		assertNotNull(createFile);
//		assertEquals("测试添加", createFile.getFileName());
	}
	
	public void test_edit(){
		clean();
		Integer id = insert("测试更新");
		insert("测试更新2");
		insert("测试更新3");
		insert("测试更新4");
		insert("测试更新5");
		insert("测试更新6");
		insert("测试更新7");
		assertTrue(id.toString(), id.intValue() > 0);

		CreditFile creditFile=getCreditFile();
		creditFile.setId(id);
		creditFile.setFileName("测试更新后的数据");
		Integer i = creditFileService.editFile(creditFile, false);
		assertNotNull(i);
		assertEquals("测试更新后的数据",creditFile.getFileName());
	}
	
	public  void test_deleteById(){
		clean();
		Integer id1 = insert("测试删除1");

		Integer i =creditFileService.deleteById(id1);
		assertNotNull(i);
		assertEquals(1, i.intValue());
		
		CreditFile creditFile = queryOne(id1);
		assertNull(creditFile);
	}

	public void test_queryById(){
		clean();
		Integer id1 = insert("测试查询1");
		insert("测试查询2");
	
		CreditFile creditFile=creditFileService.queryById(id1);
		assertNotNull(creditFile);
		assertEquals("测试查询1", creditFile.getFileName());
	}
	
	private Integer insert(String fileName) {
		if(fileName==null){
			fileName="";
		}
		String sql="INSERT INTO credit_file(" +
				"cid,file_type,file_name,file_agency,file_pic_url,show_flag,gmt_start,gmt_end,gmt_modified,gmt_created)" +
				"VALUES(1,'fileType','"+fileName+"','fileAgency','filePicUrl',1,now(),now(),now(),now())";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertResult();
	}


	private CreditFile queryOne(Integer id) {
		CreditFile createFile = null;
		try {
			ResultSet rs = connection.prepareStatement(
					"SELECT * FROM esite_friendlink WHERE id = " + id).executeQuery();
			if (rs.next()) {
				createFile=new CreditFile(rs.getInt("id"), rs.getInt("cid"), rs.getString("file_type"),rs.getString("file_name"),
						rs.getString("file_agency"),rs.getString("file_pic_url"),rs.getInt("show_flag"),
						rs.getDate("gmt_start"),rs.getDate("gmt_end"),rs.getDate("gmt_modified"),rs.getDate("gmt_created"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return createFile;
	}

	private CreditFile getCreditFile() {
		return new CreditFile(null, 2, "fileType", "测试添加", "fileAgency", "filePicUrl",
				1, new Date(), new Date(), new Date(), new Date());
	}

	private void clean() {
		try {
			connection.prepareStatement("DELETE FROM credit_file").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
