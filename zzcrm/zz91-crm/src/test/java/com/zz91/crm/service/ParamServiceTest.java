package com.zz91.crm.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.zz91.crm.domain.Param;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-12 
 */
public class ParamServiceTest extends BaseServiceTestCase {
	
	@Resource
	private ParamService paramService;

	@Test
	public void testQueryParamByTypes() {
		clean();
		manyRecords(5);
		List<Param> list=paramService.queryParamByTypes("crm_test", 0);
		assertNotNull(list);
		assertEquals(5, list.size());
	}

	@Test
	public void testQueryValueByKey() {
		clean();
		manyRecords(1);
		String value=paramService.queryValueByKey("crm_test", "key");
		assertEquals("value", value);
	}

	@Test
	public void testCreateParam() {
		clean();
		Integer i=paramService.createParam(new Param(1, "creat_test", "test", "key", "value", (short)0, (short)0, new Date(), new Date()));
		Param param=queryParamById(i);
		assertNotNull(i);
		assertEquals("key", param.getKey());
		assertEquals("value", param.getValue());
	}

	@Test
	public void testDeleteParamById() {
		clean();
		manyRecords(5);
		Integer i=paramService.deleteParamById(1);
		assertEquals(1, i.intValue());
	}

	@Test
	public void testUpdateParam() {
		clean();
		manyRecords(1);
		Param param=queryParamById(1);
		assertNotNull(param);
		param.setId(1);
		param.setName("更新后的name");
		param.setValue("更新后的value");
		Integer i=paramService.updateParam(param);
		assertNotNull(i);
		Param newParam=queryParamById(1);
		assertNotNull(newParam);
		assertEquals("更新后的name", param.getName());
		assertEquals("更新后的value", param.getValue());
		
	}

	@Test
	public void testIsExistByKey() {
		clean();
		manyRecords(1);
		boolean flag=paramService.isExistByKey("crm_test", "key");
		assertEquals(true, flag);
	}
	
	public void clean(){
		try {
			connection.prepareStatement("delete from param").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Integer createOneTestRecord(Param param){
		String sql="insert into `param` (`id`,`types`,`name`,`key`,`value`,`sort`,`isuse`,`gmt_created`,`gmt_modified`) values("
			+ param.getId()
			+ ",'"
			+ param.getTypes()
			+ "','"
			+ param.getName()
			+ "','"
			+ param.getKey()
			+ "','"
			+ param.getValue()
			+ "',"
			+ param.getSort()
			+ ","
			+ param.getIsuse()
			+ ",now(),now())";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void manyRecords(Integer size){
		for (int i = 0; i < size; i++) {
			createOneTestRecord(new Param(i+1, "crm_test", "测试"+i, "key", "value", (short)0, (short)0, new Date(), new Date()));
		}
	}
	public Param queryParamById(Integer id){
		String sql="select `id`,`types`,`name`,`key`,`value`,`sort`,`isuse`" +
				",`gmt_created`,`gmt_modified` from `param` where id="+id;
		try {
			ResultSet rs=connection.prepareStatement(sql).executeQuery();
			if (rs.next()){
				return new Param(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5), rs.getShort(6), rs.getShort(7), null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
