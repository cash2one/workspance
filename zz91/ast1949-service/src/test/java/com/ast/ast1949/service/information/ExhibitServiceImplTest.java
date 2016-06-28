/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25
 */
package com.ast.ast1949.service.information;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.information.ExhibitDO;
import com.ast.ast1949.dto.information.ExhibitDTO;
import com.ast.ast1949.service.BaseServiceTestCase;




/**
 * @author yuyonghui
 *
 */
public class ExhibitServiceImplTest extends BaseServiceTestCase{

	@Autowired
	private ExhibitService exhibitService;
	
	public void test_queryExhibitById(){
		clean();
		ExhibitDO exhibitDO=createData();
		int i=exhibitService.insertExhibit(exhibitDO);
		ExhibitDTO exhibitDTO=	exhibitService.queryExhibitById(i);
		List<ExhibitDTO> list=new ArrayList<ExhibitDTO>(); 
		list.add(exhibitDTO);
		assertTrue("select is ok list >0", list.size()>0);
	}
	public void test_queryExhibitByExhibitCategoryCode(){
		clean();
		ExhibitDO exhibitDO=createData();
		int i=exhibitService.insertExhibit(exhibitDO);
		assertTrue(i>0);
		
		String plateCategoryCode="10381001";
		Integer limitSize=5;
		List<ExhibitDTO> list=exhibitService.queryExhibit(plateCategoryCode,null, limitSize);
		assertTrue(list.size()>0);
	}
	public void test_queryqueryExhibitByExhibitCategoryCodeIsNull(){
		clean();
		try {
			exhibitService.queryExhibit("132212","", null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("limitSize is not null", e.getMessage());

		}
	}
	public void test_insertExhibit(){
		ExhibitDO exhibitDO=new ExhibitDO();
		exhibitDO.setAreaCode("1111");
		exhibitDO.setContent("1111");
		exhibitDO.setEndTime(new Date());
		exhibitDO.setExhibitCategoryCode("10371001");
		exhibitDO.setName("name");
		exhibitDO.setPlateCategoryCode("10381001");
		exhibitDO.setStartTime(new Date());
		exhibitDO.setGmtCreated(new Date());
		exhibitDO.setGmtModified(new Date());
		int i=exhibitService.insertExhibit(exhibitDO);
		assertTrue("insert is successful",i>0);
	}
	public void test_updateExhibit(){
		clean();
		ExhibitDO exhibitDO =createData();
		int a=exhibitService.insertExhibit(exhibitDO);
		assertTrue("insert is ok", a>0);
	    int i=exhibitService.updateExhibit(exhibitDO);
		assertTrue(i>0);
	}
	public void test_updateExhibit_isNull(){
		clean();
		try {
			int i=exhibitService.updateExhibit(null);
			assertTrue(i<0);
			fail();
		} catch (IllegalArgumentException e) {
            assertEquals("exhibitDO is not null", e.getMessage());
		}
	}
	public void test_selectExhibitById(){
		clean();
		exhibitService.selectExhibitById(1);
		
	}
//	public void test_queryExhibitByCondition(){
//		clean();
//		ExhibitDTO exhibitDTO=new ExhibitDTO();
//		exhibitService.queryExhibitByCondition(exhibitDTO);
//		
//	}
	private void clean() {
		try {
			cleanupExhibit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 清除表中的数据
	protected void cleanupExhibit() throws SQLException {
		connection.prepareStatement("delete from exhibit").execute();
	}
	public ExhibitDO createData(){
		ExhibitDO exhibitDO =new ExhibitDO();
		exhibitDO.setId(1);
		exhibitDO.setAreaCode("33");
		exhibitDO.setContent("333");
		exhibitDO.setEndTime(new Date());
		exhibitDO.setExhibitCategoryCode("10371001");
		exhibitDO.setName("name");
		exhibitDO.setPlateCategoryCode("10381001");
		exhibitDO.setStartTime(new Date());
		exhibitDO.setGmtCreated(new Date());
		exhibitDO.setGmtModified(new Date());
		return exhibitDO;
	}
}
