/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-21
 */
package com.ast.ast1949.service.common;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.company.CategoryGardenDO;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.company.CategoryGardenService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
public class categoryGardenServiceImplTest extends BaseServiceTestCase {

	@Autowired
	CategoryGardenService categoryGardenService;
	
	

	/**
	 * 
	 *测试园区类别列表
	 */
	public void test_queryCategoryGardenByCondition_isNull() {
		clean();
	try {
		categoryGardenService.queryCategoryGardenByCondition(null);
		fail();
	} catch (IllegalArgumentException e) {
	   
	}
		
		
	}

	public void test_getCategoryGardenRecordCountByCondition() throws IllegalArgumentException{
		clean();
		 try {
				categoryGardenService.getCategoryGardenRecordCountByCondition(null);
				fail();
		} catch (IllegalArgumentException e) {
			
		}
	
	}

	public void test_queryCategoryGardenById()throws IllegalArgumentException {
		clean();
		 int id=3;
       categoryGardenService.queryCategoryGardenById(id);
      
	}

	public void test_updateCategoryGarden_isNull() {
		clean();
		try {	
			categoryGardenService.updateCategoryGrden(null);
			fail();
		} catch (IllegalArgumentException e) {
		
		}
	}
   public void test_updateCategoryGarden_notNull()
   {
	   clean();
	   CategoryGardenDO categoryGardenDO=new CategoryGardenDO();
	   categoryGardenService.updateCategoryGrden(categoryGardenDO);
	   
   }
	public void test_insertCategoryGarden_isNull() {
		clean();
		try {
           categoryGardenService.insertCategoryGrden(null);
           fail();
		} catch (IllegalArgumentException e) {
			
		}

	}
	public void test_insertCategoryGarden_notNull()
	{
		clean();
		CategoryGardenDO categoryGardenDO = new CategoryGardenDO();
		categoryGardenDO.setAreaCode("1007");
		categoryGardenDO.setGardenTypeCode("1012");
		categoryGardenDO.setIndustryCode("1013");
		categoryGardenDO.setName("ffgfg");
		categoryGardenDO.setShorterName("s");
		categoryGardenDO.setGmtCreated(new Date());
		categoryGardenDO.setGmtModified(new Date());
		
		categoryGardenService.insertCategoryGrden(categoryGardenDO);
	
	}

	public void test_deleteByIds() throws IllegalArgumentException{
           clean();
		String ids = "";
		Assert.notNull(ids, "ids can not be null!");

	}
	
	private void clean(){
		try {
			cleanupCategoryGarden();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// 清除表中的数据
	protected void cleanupCategoryGarden() throws SQLException{
		connection.prepareStatement("delete from category_garden").execute();
	}
//	private void createData(int max){
//
//		CategoryGardenDO c = new CategoryGardenDO();
//		c.setAreaCode("");
//		c.setGardenTypeCode("");
//		c.setIndustryCode("");
//		c.setName("");
//		c.setShorterName("");
//		c.setGmtCreated(new Date());
//		c.setGmtModified(new Date());
//	
//		try {
//			connection.prepareStatement("insert into category_garden(name,shorter_name,area_code,industry_code,garden_type_code,gmt_created,gmt_modified) " +
//					"values('"+c.getName()+"',"+c.getShorterName()+",'"+c.getAreaCode()+"','"+c.getIndustryCode()+"','"+c.getGardenTypeCode()+"','"+c.getGmtCreated()+"','"+c.getGmtModified()+"')").execute();
//
//		} catch (SQLException e1) {
//		}
//
//		for(int i=1;i<max+1;i++){
//			CategoryGardenDO categoryGardenDO = new CategoryGardenDO();
//			categoryGardenDO.setAreaCode("");
//			categoryGardenDO.setGardenTypeCode("");
//			categoryGardenDO.setIndustryCode("");
//			categoryGardenDO.setName("");
//			categoryGardenDO.setShorterName("");
//			categoryGardenDO.setGmtCreated(new Date());
//			categoryGardenDO.setGmtModified(new Date());
//			try {
//				connection.prepareStatement("insert into category_garden(name,shorter_name,area_code,industry_code,garden_type_code,gmt_created,gmt_modified) " +
//						"values('"+categoryGardenDO.getName()+"',"+categoryGardenDO.getShorterName()+",'"+categoryGardenDO.getAreaCode()+"','"+categoryGardenDO.getIndustryCode()+"','"+categoryGardenDO.getGardenTypeCode()+"','"+categoryGardenDO.getGmtCreated()+"','"+categoryGardenDO.getGmtModified()+"')").execute();
//			} catch (SQLException e) {
//			}
//		}
//	}
}
