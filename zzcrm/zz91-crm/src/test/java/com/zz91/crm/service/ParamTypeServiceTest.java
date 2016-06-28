package com.zz91.crm.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.zz91.crm.domain.ParamType;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-13 
 */
public class ParamTypeServiceTest extends BaseServiceTestCase{
	
	@Resource
	private ParamTypeService paramTypeService;

	@Test
	public void testQueryAllParamType() {
		clean();
		manyRecords(6);
		List<ParamType> list=paramTypeService.queryAllParamType();
		assertNotNull(list);
		assertEquals(6, list.size());
	}

	@Test
	public void testCreateParamType() {
		clean();
		Integer id=paramTypeService.createParamType(new ParamType(1, "key", "type_name", new Date(), new Date()));
		assertNotNull(id);
		ParamType type=queryParamTypeById(id);
		assertNotNull(type);
		assertEquals("type_name", type.getName());
	}

	@Test
	public void testDeleteParamTypeByKey(){
		clean();
		manyRecords(5);
		Integer i=paramTypeService.deleteParamTypeByKey("key1");
		assertNotNull(i);
		assertEquals(1, i.intValue());

	}

	@Test
	public void testUpdateParamType() {
		clean();
		manyRecords(1);
		ParamType paramType=queryParamTypeById(1);
		paramType.setId(1);
		paramType.setKey("更新key");
		paramType.setName("更新name");
		Integer i=paramTypeService.updateParamType(paramType,"");
		assertEquals(1, i.intValue());
		assertEquals("更新key", paramType.getKey());
		assertEquals("更新name", paramType.getName());
	}

	@Test
	public void testIsExistByKey() {
		clean();
		manyRecords(5);
		boolean flag=paramTypeService.isExistByKey("key1");
		assertNotNull(flag);
		assertEquals(true, flag);
	}
	
	public void clean(){
		try {
			connection.prepareStatement("delete from param_type").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Integer createOneTestRecord(ParamType type){
		String sql="insert into `param_type` (`id`,`key`,`name`,`gmt_created`,`gmt_modified`) values("
			+ type.getId()
			+ ",'"
			+ type.getKey()
			+ "','"
			+ type.getName()
			+ "',now(),now())";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void manyRecords(Integer size){
		for (int i = 0; i < size; i++) {
			createOneTestRecord(new ParamType(i+1, "key"+i, "type_name", new Date(), new Date()));
		}
	}
	public ParamType queryParamTypeById(Integer id){
		String sql="select `id`,`key`,`name`," +
				"`gmt_created`,`gmt_modified` from `param_type` where id="+id;
		try {
			ResultSet rs=connection.prepareStatement(sql).executeQuery();
			if (rs.next()){
				return new ParamType(rs.getInt(1), rs.getString(2), rs.getString(3), null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
