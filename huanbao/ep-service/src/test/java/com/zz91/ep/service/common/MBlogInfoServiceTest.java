//package com.zz91.ep.service.common;
//
//import java.sql.SQLException;
//import java.util.List;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.zz91.ep.domain.mblog.MBlog;
//import com.zz91.ep.domain.mblog.MBlogComment;
//import com.zz91.ep.domain.mblog.MBlogFollow;
//import com.zz91.ep.domain.mblog.MBlogGroup;
//import com.zz91.ep.domain.mblog.MBlogInfo;
//import com.zz91.ep.domain.mblog.MBlogSystem;
//import com.zz91.ep.dto.PageDto;
//import com.zz91.ep.service.BaseServiceTestCase;
//import com.zz91.ep.service.mblog.MBlogCommentService;
//import com.zz91.ep.service.mblog.MBlogFollowService;
//import com.zz91.ep.service.mblog.MBlogGroupService;
//import com.zz91.ep.service.mblog.MBlogService;
//import com.zz91.ep.service.mblog.MBlogSystemService;
//import com.zz91.ep.service.mblog.impl.MBlogInfoServiceImpl;
//
//public class MBlogInfoServiceTest extends BaseServiceTestCase {
//	@Autowired
//	MBlogInfoServiceImpl mBlogInfoService;
//	@Autowired 
//	MBlogService mBlogService;
//	@Autowired
//	MBlogGroupService groupService;
//	@Autowired
//	MBlogCommentService commentService;
//	@Autowired
//	MBlogSystemService systemService;
//	@Autowired
//	MBlogFollowService followService;
////	@Test
////	public void testDelete(){
////		clean();
////		MBlogInfo mBlogInfo=new MBlogInfo();
////		mBlogInfo.setCid(1001);
////		mBlogInfo.setAddressCode("1001");
////		mBlogInfo.setHeadPic("aaaa.jpg");
////		mBlogInfo.setName("ac");
////		mBlogInfo.setAccount("aaazh");
////	   Integer id=	mBlogInfoService.insert(mBlogInfo);
////	   assertNotNull(id);
////	   MBlogInfo m= mBlogInfoService.queryInfoById(1);
////	   assertNotNull(m);
////	   System.err.println(m.getAccount());
////	   
////	   mBlogInfo.setId(m.getId());
////	  
////	   mBlogInfo.setName("zzzhhhh");
////	   mBlogInfo.setAddressCode("1000");
//////	   Integer ac= update(mBlogInfo1);
//////	   assertNotNull(ac);
//////	   System.err.println(ac);
////	   Integer a= mBlogInfoService.update(mBlogInfo);
////	   System.out.println(a);
////	   assertNotNull(a);
////	  Integer b= mBlogInfoService.updateIsDeleteStatus(m.getAccount(), "1");
////	  System.out.println(b);
////	   assertNotNull(b);
////	}
//	
////	@Test
////	public void testDelete1(){
////		clean1();
////		MBlog mblog=new MBlog();
////		mblog.setInfoId(1);
////		mblog.setContent("aaa");
////		mblog.setType("1");
////		mblog.setContentQuery(mblog.getContent());
////		Integer i=mBlogService.insert(mblog);
////		 assertNotNull(i);
////		 System.out.println(i);
////		Integer ac=mBlogService.queryAllCountMBlogById(1);
////		System.err.println(ac+"ac");
//		
////	    MBlog mBlog=mBlogService.queryOneById(i);
////	    assertNotNull(mBlog);
////	    System.out.println(mBlog.getContent());
////	    Integer a=mBlogService.delete(mblog.getId());
////	    assertNotNull(a);
////	    Integer ab=mBlogService.updateSentCount(mblog.getId());
////	    assertNotNull(ab);
//	    
//	    
////	}
////	@Test
////	public void testDelete2(){
////		clean2();
////		MBlogGroup mBlogGroup=new MBlogGroup();
////		mBlogGroup.setInfoId(1);
////		mBlogGroup.setGroupName("acc");
////		Integer i=groupService.insert(mBlogGroup);
////		assertNotNull(i);
////		System.err.println(i);
////		List<MBlogGroup> groupList=groupService.queryById(i);
////		for (MBlogGroup mBlogGroup2 : groupList) {
////			System.out.println(mBlogGroup2.getGroupName());;
////		}
////	   Integer	i1= groupService.delete(i, "1");
////	   assertNotNull(i1);
////	   System.err.println(i1);
////	}
////	
//    
////	@Test
////	public void testDelete2(){
////		clean3();
////		MBlogComment comment= new MBlogComment();
////		comment.setInfoId(1);
////		comment.setMblogId(1);
////		comment.setTargetType("1");
////		comment.setTargetId(1);
////		comment.setContent("henhao");
////		Integer a=commentService.sentComment(comment);
////		assertNotNull(a);
////		System.out.println(a);
////		
////		Integer count=commentService.queryCommentCountBymblogId(1);
////		assertNotNull(count);
////		System.out.println("count"+count);
//////		Integer id= commentService.delete(1, "1");
//////		assertNotNull(id);
//////		System.out.println(id);
////		PageDto<MBlogComment> page= new PageDto<MBlogComment>();
////		page.setLimit(2);
////		PageDto<MBlogComment> commentPage=commentService.queryCommentBymblogId(1, page);
////		List<MBlogComment> cList=commentPage.getRecords();
////		for (MBlogComment mBlogComment : cList) {
////			System.out.println(mBlogComment.getTargetId());
////		}
////	}
//	
////	@Test
////	public void testDelete(){
////		clean4();
////		MBlogSystem system=new MBlogSystem();
////		system.setFromId(1);
////		system.setToId(2);
////		system.setType("1");
////		system.setContent("aa");
////		Integer i=systemService.insert(system);
////		assertNotNull(i);
////		System.err.println(i);
////		
////		List<MBlogSystem> list=systemService.queryById(2, "1","0");
////		for (MBlogSystem mBlogSystem : list) {
////			System.out.println(mBlogSystem.getContent());
////		}
////		Integer count=systemService.queryCountById(2, "1", "0");
////		System.err.println("count"+count);
////		
////		Integer ia=systemService.updateIsReadStatus(1);
////		System.out.println(ia);
////	}
//	@Test
//	public void testDelete(){
//		clean5();
//		MBlogFollow follow=new MBlogFollow();
//		follow.setInfoId(1);
//		follow.setTargetId(2);
//		follow.setFollowStatus("1");
//		Integer i=followService.insert(follow);
//		assertNotNull(i);
//		System.err.println(i);
//		
//		MBlogFollow follow1=new MBlogFollow();
//		follow1.setInfoId(3);
//		follow1.setTargetId(2);
//		follow1.setFollowStatus("1");
//		Integer i1=followService.insert(follow1);
//		assertNotNull(i1);
//		System.err.println(i1);
//		MBlogFollow follows=followService.queryByIdAndTargetId(1, 2);
//		assertNotNull(follows);
//		System.out.println(follows.getFollowStatus());
//		
//		MBlogFollow follow2=new MBlogFollow();
//		follow2.setInfoId(2);
//		follow2.setTargetId(1);
//		follow2.setFollowStatus("1");
//		Integer i2=followService.insert(follow2);
//		assertNotNull(i2);
//		System.err.println(i2);
//		MBlogFollow follows2=followService.queryByIdAndTargetId(1, 2);
//		assertNotNull(follows2);
//		System.out.println(follows2.getFollowStatus());
////		
////		
////		PageDto<MBlogFollow> page=new PageDto<MBlogFollow>();
////		page.setLimit(5);
////		PageDto<MBlogFollow> page1=followService.queryFansByConditions(2, page);
////		for (MBlogFollow f : page1.getRecords()) {
////			System.out.println(f.getFollowStatus()+"ff");
////		}
//// 		Integer count=followService.queryFansCountByConditions(2);
//// 		System.out.println(count+"count");
//		  
//// 		PageDto<MBlogFollow> page2=new PageDto<MBlogFollow>();
//// 		page2.setLimit(5);
//// 		PageDto<MBlogFollow> page02=followService.queryFollowByConditions(1, 1, page2);
//// 		for (MBlogFollow f1 : page02.getRecords()) {
////			System.out.println("page02"+f1.getFollowStatus());
////		}
// 		
//	    PageDto<MBlogFollow> pages=new PageDto<MBlogFollow>();
////	    pages.setLimit(5);
////	    PageDto<MBlogFollow> page0s=followService.queryFollowByIdAndType(1, 1, pages);
////		for (MBlogFollow f2 : page0s.getRecords()) {
////			System.err.println("types"+f2.getType());
////		}
////	    Integer i=	followService.queryFollowCountByIdAndType(1, 1);
////	    System.out.println(i);
////	    Integer id=followService.update(1, "0", "1");
////	    System.out.println("id"+id);
//	    //followService.updateFollowGroup(infoId, targetId, groupId)
////	   Integer i= followService.updateFollowGroup(1, 2, 1);
////	    System.out.println("i"+i);
//	    
//	    Integer id=followService.updateTypeById(1, "1");
//	    System.out.println(id+"i");
//	   // followService.u
//	}
//	
//	
//	
//	public Integer update(MBlogInfo  mBlogInfo){
//	
//	String sql = "";
//	try {
//		sql = "UPDATE  `ep-test`.mblog_info SET `head_pic`='"+mBlogInfo.getHeadPic()+"',`name`='"+mBlogInfo.getName()+"',`address_code` ='"+mBlogInfo.getAddressCode() 
//				+ "' where `id`="
//				+ mBlogInfo.getId();
//		connection.prepareStatement(sql).execute();
//		System.out.println(sql);
//		return insertResult();
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//	return 0;
//}
//	
//	public Integer createOneRecord(MBlogInfo mBlogInfo) {
//	String sql = "";
//	try {
//		sql = "INSERT INTO `ep-test`.mblog_info(`cid`,`account`,`head_pic`,`name`,`address_code`,`gmt_created`, " +
//				" `gmt_modified`)"
//				+ "VALUES("
//				+ mBlogInfo.getCid()
//				+ ",'"
//				+mBlogInfo.getAccount()
//				+"','"
//				+mBlogInfo.getHeadPic()
//				+"','"
//				+mBlogInfo.getName()
//				+"','"
//				+mBlogInfo.getAddressCode()
//				+ "',now(),now()"
//			     + ")";
//		connection.prepareStatement(sql).execute();
//		System.out.println(sql);
//		return insertResult();
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//	return 0;
//}
//	
//	public void clean() {
//		try {
//			connection.prepareStatement("TRUNCATE table`ep-test`.mblog_info").execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void clean1() {
//		try {
//			connection.prepareStatement("TRUNCATE table`ep-test`.mblog").execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	public void clean2() {
//		try {
//			connection.prepareStatement("TRUNCATE table`ep-test`.mblog_group").execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	public void clean3() {
//		try {
//			connection.prepareStatement("TRUNCATE table`ep-test`.mblog_comment").execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	public void clean4() {
//		try {
//			connection.prepareStatement("TRUNCATE table`ep-test`.mblog_system").execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	public void clean5() {
//		try {
//			connection.prepareStatement("TRUNCATE table`ep-test`.mblog_follow").execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//}
