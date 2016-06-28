/**
 * 
 */
package com.ast.ast1949.service.dataIndex;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.dataindex.DataIndexCategoryDO;
import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.dto.dataindex.DataIndexDto;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.dataindex.DataIndexCategoryService;
import com.ast.ast1949.service.dataindex.DataIndexService;


/**
 * @author yuyh
 *
 */
public class DataIndexTest extends BaseServiceTestCase{

	@Autowired
	private DataIndexService dataIndexService;
	@Autowired
	private DataIndexCategoryService dataIndexCategoryService;
	
//	@Test
//	public void test_queryDataIndexByCode_codeIsNull() throws SQLException{
//		cleanDataIndex();
//		String code=null;
//		try {
//			dataIndexService.queryDataIndexByCode(code,1);
//		} catch (Exception e) {
//			assertEquals("code is not null", e.getMessage());
//		}
//	}
	@Test
	public void test_insertDataIndex() throws SQLException{
		cleanDataIndex();
		Integer i=	dataIndexService.insertDataIndex(createDataIndex());
		assertTrue(i>0);
	}
	@Test
	public void test_deleteDataIndex_isNull() throws SQLException{
		cleanDataIndex();
		Integer id=null;
		try {
			dataIndexService.deleteDataIndex(id);
		} catch (Exception e) {
			assertEquals("id is not null", e.getMessage());
		}
	}
	@Test
	public void test_deleteDataIndex() throws SQLException{
		cleanDataIndex();
		Integer id=createOneDataIndex();
		assertTrue(id>0);
		Integer i=dataIndexService.deleteDataIndex(id);
		assertTrue(i.toString(), i.intValue()>0);
	}
//	@Test 
//	public void test_pageDataIndexByCondition(){
//		DataIndexDto dataIndexDto=new DataIndexDto();
//		dataIndexService.pageDataIndexByCondition(dataIndexDto);
//	}
//	@Test
//	public void test_countDataIndexByCondition(){
//		DataIndexDto dataIndexDto=new DataIndexDto();
//		dataIndexService.countDataIndexByCondition(dataIndexDto);
//	}
	@Test
	public void test_insertDataIndexCategory_codeIsNull() throws SQLException{
		cleanDataIndexCategory();
		DataIndexCategoryDO dataIndexCategoryDO=new DataIndexCategoryDO();
		String preCode=null;
		try {
			dataIndexCategoryService.insertDataIndexCategory(dataIndexCategoryDO, preCode);
		} catch (Exception e) {
			assertEquals("preCode is not null", e.getMessage());
		}
	}
//	@Test
//	public void test_insertDataIndexCategory() throws SQLException{
//		cleanDataIndexCategory();
//		String preCode="1000";
//		Integer i=dataIndexCategoryService.insertDataIndexCategory(createDataIndexCategory(), preCode);
//		assertTrue(i>0);
//		DataIndexCategoryDO dCategoryDO=dataIndexCategoryService.queryDataIndexCategoryById(i);
//		List<DataIndexCategoryDO> list=new ArrayList<DataIndexCategoryDO>();
//		list.add(dCategoryDO);
//		assertTrue(list.size()>0);
//	}
	@Test
	public void test_updateDataIndexCategory() throws SQLException{
		cleanDataIndexCategory();
		DataIndexCategoryDO dataIndexCategoryDO=new DataIndexCategoryDO();
		String preCode="1000";
		Integer id=dataIndexCategoryService.insertDataIndexCategory(createDataIndexCategory(), preCode);
		dataIndexCategoryDO.setId(id);
		dataIndexCategoryDO.setCode("1000");
		dataIndexCategoryDO.setLabel("test");
		dataIndexCategoryDO.setIsDelete("0");
		dataIndexCategoryDO.setGmtCreated(new Date());
		dataIndexCategoryDO.setGmtModified(new Date());
		
		Integer i=dataIndexCategoryService.updateDataIndexCategory(dataIndexCategoryDO);
		assertTrue(i>0);
	}
//	@Test
//	public void test_queryDataIndexCategorybyPreCode() throws SQLException{
//		cleanDataIndexCategory();
//		String preCode="1000";
//		dataIndexCategoryService.insertDataIndexCategory(createDataIndexCategory(), preCode);
//		String code="10001000";
//		dataIndexCategoryService.insertDataIndexCategory(createDataIndexCategory(), code);
//		
//		List<DataIndexCategoryDO> list=dataIndexCategoryService.queryDataIndexCategorybyPreCode(preCode);
//		assertTrue(list.size()>0);
//		assertEquals(code, list.get(0).getCode());
//		
//	}
	@Test
	public void test_getMaxCode_isNull() throws SQLException{
		cleanDataIndexCategory();
		String preCode=null;
		try {
			dataIndexCategoryService.getMaxCode(preCode);
		} catch (Exception e) {
			assertEquals("preCode is not null", e.getMessage());
		}
	}
	public void test_getMaxCode() throws SQLException{
		cleanDataIndexCategory();
		String code="10001001";
		
		dataIndexCategoryService.insertDataIndexCategory(createDataIndexCategory(), code);

		String preCode="1000";
		String code1=dataIndexCategoryService.getMaxCode(preCode);
		assertEquals("10001000", code1);
		
	}
	private void cleanDataIndex() throws SQLException {
		connection.prepareStatement("delete from data_index").execute();
	}
	private void cleanDataIndexCategory() throws SQLException{
		connection.prepareStatement("delete from data_index_category").execute();
	}
	private DataIndexDO createDataIndex(){
		DataIndexDO dataIndexDO=new DataIndexDO();
		dataIndexDO.setCategoryCode("1002");
		dataIndexDO.setIsChecked("0");
		dataIndexDO.setLink("http://www.zz91.com");
		dataIndexDO.setPic("url");
		dataIndexDO.setSort(2);
		dataIndexDO.setTitle("this is the test");
		dataIndexDO.setGmtCreated(new Date());
		dataIndexDO.setGmtModified(new Date());
		return dataIndexDO;
	}
	private DataIndexCategoryDO createDataIndexCategory(){
		DataIndexCategoryDO dataIndexCategoryDO=new DataIndexCategoryDO();
		dataIndexCategoryDO.setCode("1000");
		dataIndexCategoryDO.setLabel("test");
		dataIndexCategoryDO.setIsDelete("0");
		dataIndexCategoryDO.setGmtCreated(new Date());
		dataIndexCategoryDO.setGmtModified(new Date());
		return dataIndexCategoryDO;
	}

	private Integer createOneDataIndex() throws SQLException{
		DataIndexDO dataIndexDO=new DataIndexDO();
		dataIndexDO.setCategoryCode("1002");
		dataIndexDO.setIsChecked("0");
		dataIndexDO.setLink("http://www.zz91.com");
		dataIndexDO.setPic("url");
		dataIndexDO.setSort(2);
		dataIndexDO.setTitle("this is the test");
		dataIndexDO.setGmtCreated(new Date());
		dataIndexDO.setGmtModified(new Date());
		connection.prepareStatement("insert into data_index(title,link,pic,category_code,is_checked,gmt_created,gmt_modified) " +
				"values('"+dataIndexDO.getTitle()+"','"+dataIndexDO.getLink()+"','"+dataIndexDO.getPic()+"','"+dataIndexDO.getCategoryCode()+"','"+dataIndexDO.getIsChecked()+"','2010-11-20','2010-11-20')").execute();
	    ResultSet rs=connection.createStatement().executeQuery("select last_insert_id()");
	    
	    if(rs.next()){
	            return rs.getInt(1);
	    }
		return 0;

	}
}
