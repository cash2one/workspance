package com.kl91.service.junit.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import com.kl91.domain.company.UploadPic;
import com.kl91.service.company.UploadPicService;
import com.kl91.service.junit.BaseServiceTestCase;

public class UploadPicServiceTest extends BaseServiceTestCase {
	
	@Resource
	private UploadPicService uploadPicService;
	
	public void test_created(){
		clean();
		Integer i = uploadPicService.createUploadPic(getUploadPic());
		assertNotNull(i);
		assertTrue(i.intValue() > 0);
		
		UploadPic uploadPic = queryOne(i);
		assertNotNull(uploadPic);
		assertEquals("测试添加", uploadPic.getFileName());
	}
	
	public void test_editUploadPicById(){
		clean();
		Integer id = insert("测试更新",1,1);
		insert("测试更新2",1,1);
		insert("测试更新3",1,1);
		insert("测试更新4",1,1);
		insert("测试更新5",1,1);
		insert("测试更新6",1,1);
		insert("测试更新7",1,1);
		assertTrue(id.toString(), id.intValue() > 0);

		UploadPic uploadPic = getUploadPic();
		uploadPic.setId(id);
		uploadPic.setFileName("测试更新后的数据");
		Integer i = uploadPicService.editUploadPicById(id, 1, 1);
		assertNotNull(i);
		assertEquals("测试更新后的数据", uploadPic.getFileName());
	}
	
	public void test_deleteById(){
		clean();
		Integer id1 = insert("测试删除1",1,1);
		Integer id2 = insert("测试删除2",1,1);

		Integer i =uploadPicService.deleteById(id1);
		assertNotNull(i);
		assertEquals(1, i.intValue());
		
		UploadPic uploadPic = queryOne(id1);
		assertNull(uploadPic);
		
		UploadPic uploadPic2= queryOne(id2);
		assertNotNull(uploadPic2);
	}

	private Integer insert(String fileName,Integer targetId,Integer targetType) {
		if(fileName==null){
			fileName="";
		}
		if(targetId==null){
			targetId=1;
		}
		String sql="insert into upload_pic(cid,target_id,target_tyoe,file_path,thumb_path,file_name,file_type," +
				"file_size,remark,album,gmt_created,gmt_modified)values" +
				"(1,"+targetId+",'"+targetType+"','filePath','thumbPath'," +
				"'"+fileName+"','fileType','fileSize','remark','album',now(),now())";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertResult();
	}

	private UploadPic queryOne(Integer id) {
		UploadPic uploadPic = null;
		try {
			ResultSet rs = connection.prepareStatement(
					"SELECT * FROM upload_pic WHERE id = " + id).executeQuery();
			if (rs.next()) {
				uploadPic=new UploadPic(rs.getInt("id"), rs.getInt("cid"), rs.getInt("target_id"), rs.getInt("target_tyoe"),
						rs.getString("file_path"), rs.getString("thumb_path"), rs.getString("file_name"), 
						rs.getString("file_type"), rs.getString("file_size"), rs.getString("remark"),
						rs.getString("album"), rs.getDate("gmt_created"), rs.getDate("gmt_modified"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uploadPic;
	}

	private UploadPic getUploadPic() {
		return new UploadPic(null, 1, 1, 1, "filePath", "thumbPath", "测试添加", 
				"fileType", "fileSize", "remark", "album", new Date(), new Date());
	}

	private void clean() {
		try {
			connection.prepareStatement("DELETE FROM upload_pic").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
