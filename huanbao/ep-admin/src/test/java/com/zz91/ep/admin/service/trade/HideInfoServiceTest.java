//package com.zz91.ep.admin.service.trade;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.zz91.ep.admin.service.common.HideInfoService;
//import com.zz91.ep.domain.common.HideInfo;
//
//
//public class HideInfoServiceTest extends BaseServiceTestCase{
//     
//	@Autowired
//	HideInfoService hideInfoService;
//	
//	//删除
//	@Test 
//	public void testDelete(){
//		clean();
//	  Integer id=createOneRecord(oneTestRecord(1, "0"));
//	  assertNotNull(id);
//      assertEquals(1, id.intValue());
//      HideInfo hideInfo=queryOneByIdAndtype(1, "0");
//      assertNotNull(hideInfo);   
//      Integer dId=hideInfoService.delete(id); 
//      assertNotNull(dId);
//      assertEquals(1, dId.intValue());
//	}
//	//添加查询
//	@Test
//	public void testInsert(){
//		clean();
//	  Integer	id=createOneRecord(oneTestRecord(1, "0"));
//	  assertNotNull(id);
//      HideInfo hideInfo=queryOneByIdAndtype(1, "0");
//      assertEquals(1, (int)hideInfo.getTargetId());
//      assertEquals("0",hideInfo.getTargetType());  
//	}
//	
//	//添加查询1
//	@Test
//	public void testInsert1(){
//		clean();
//     Integer id=createOneRecord(oneTestRecord(1, "0"));
//	  assertNotNull(id);
//      HideInfo hideInfo=hideInfoService.queryHideInfoByIdAndType(1,"0");
//      assertEquals(1, (int)hideInfo.getTargetId());
//      assertEquals("0",hideInfo.getTargetType());  
//	}
//	
//	
//	
//	
//	//修改
//	@Test
//	public void testUpdate(){
//	  clean();
//	  Integer	id=createOneRecord(oneTestRecord(1, "0"));
//	  assertNotNull(id);
//	  HideInfo hideInfo=queryOneByIdAndtype(1, "0");
//	  assertNotNull(hideInfo);
//	  hideInfo.setTargetId(1);
//	  hideInfo.setTargetType("0");
//	  Integer uid=update(hideInfo);
//	  assertNotNull(uid);
//	  HideInfo hideInfo1=queryOneByIdAndtype(1, "0");
//	  assertNotNull(hideInfo);
//	  assertEquals(1, (int)hideInfo1.getTargetId());
//      assertEquals("0",hideInfo1.getTargetType()); 
//	}
//	
//	//修改1
//	@Test
//	public void testUpdate1(){
//	  clean();
//	  Integer	id=hideInfoService.insert((oneTestRecord(1, "0")));
//	  assertNotNull(id);
//	  Integer uid=hideInfoService.update(oneTestRecord(1, "0"));
//	  assertNotNull(uid);
//	  HideInfo hideInfo=hideInfoService.queryHideInfoByIdAndType(1, "0");
//	  assertNotNull(hideInfo);
//	  assertEquals(1, (int)hideInfo.getTargetId());
//      assertEquals("0",hideInfo.getTargetType());  
//	}
//	
//	
//	
//	
//	public Integer update(HideInfo  hideInfo){
//		
//		String sql = "";
//		try {
//			sql = "UPDATE  `ep-test`.hide_info SET `gmt_modified`=now() where `target_id`="+hideInfo.getTargetId()
//					+ " and  `target_type`="
//					+ hideInfo.getTargetType();
//			connection.prepareStatement(sql).execute();
//			return insertResult();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
//	
//	
//	
//	
//	
//	//添加
//
//	public HideInfo oneTestRecord(Integer targetId, String targetType) {
//		return new HideInfo(targetId, targetType,null, null);
//	}
//	
//	public Integer createOneRecord(HideInfo hideInfo) {
//		String sql = "";
//		try {
//			sql = "INSERT INTO `ep-test`.hide_info(`target_id`,`target_type`,`gmt_created`,`gmt_modified`)"
//					+ "VALUES("
//					+ hideInfo.getTargetId()
//					+ ",'"
//					+ hideInfo.getTargetType()
//					+ "',now(),now()"
//				     + ")";
//			connection.prepareStatement(sql).execute();
//			return insertResult();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
//
//	
//	 
//	
//    //查询one
//	
//	public HideInfo queryOneByIdAndtype(Integer targetId, String targetType) {
//		String sql = "select `target_id`,`target_type`,`gmt_created`,`gmt_modified` FROM `ep-test`.hide_info "
//				+ "where `target_id`=" + targetId+" and `target_type`="+targetType;
//		try {
//			ResultSet rs = connection.createStatement().executeQuery(sql);
//			if (rs.next()) {
//				return new HideInfo(rs.getInt(1), rs.getString(2), rs
//						.getDate(3), rs.getDate(4));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	
//	
//	
//	
//	// 清除表中的数据
//	public void clean() {
//		try {
//			connection.prepareStatement("TRUNCATE table`ep-test`.hide_info").execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//}
