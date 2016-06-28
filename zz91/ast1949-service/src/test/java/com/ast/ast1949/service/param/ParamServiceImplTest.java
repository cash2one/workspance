package com.ast.ast1949.service.param;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.persist.auth.ParamDao;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.auth.ParamService;
import com.ast.ast1949.util.AstConst;
import com.zz91.util.domain.Param;

public class ParamServiceImplTest extends BaseServiceTestCase{

	@Autowired
	private ParamService paramService;
	@Autowired
	private ParamDao paramDao;

	private String TYPE_KEY = "config_test";
	public void test_insertParam_noAttribute() {
		clean();
		try {
			paramService.insertParam(null);
			fail();
		} catch (Exception e) {
		}

		//param.key is null
		try {
			Param p = new Param();
			paramService.insertParam(p);
			fail();
		} catch (Exception e) {
		}

		//param.types is null
		try {
			Param p11 = new Param();
			p11.setKey("key1");
			paramService.insertParam(p11);
			fail();
		} catch (Exception e) {
		}
	}

	public void test_insertParam_hasAttribute(){
		clean();
		createOneParamType(TYPE_KEY);
		Param p2 = new Param();
		p2.setKey("key1");
		p2.setTypes(TYPE_KEY);
		paramService.insertParam(p2);

		List<Param> paramList=paramDao.listParam(p2);
		assertNotNull(paramList);
		assertEquals(paramList.get(0).getKey(), "key1");
		assertTrue(paramList.get(0).getIsuse().intValue() == AstConst.ISUSE_TRUE);
	}

	public void test_listParamByTypes_noAttribute() {
		clean();

		try {
			paramService.listParamByTypes(null);
			fail();
		} catch (Exception e) {
		}
	}

	public void test_listParamByTypes_hasAttribute(){
		clean();
		createOneParamType(TYPE_KEY);
		createMore(5, TYPE_KEY);

		List<Param> list = paramService.listParamByTypes(TYPE_KEY);
		assertNotNull(list);
		assertTrue(list.size()==5);
	}

	public void test_updateParam_noAttribute() {
		try {
			paramService.updateParam(null);
			fail();
		} catch (Exception e) {
		}

		//id is null
		try {
			Param param = new Param();
			paramService.updateParam(param);
		} catch (Exception e) {
		}

		//key is null
		try {
			Param param = new Param();
			param.setId(1);
			paramService.updateParam(param);
		} catch (Exception e) {
		}

	}

	public void test_updateParam_hasAttribute(){
		clean();

		Param p = getOneParam(TYPE_KEY, "key");

		p.setName("new name");
		p.setValue("new value");
		p.setKey("new key");

		Integer i = paramService.updateParam(p);
		assertNotNull(i);
		assertTrue(i.intValue()==1);

	}
	
	@Test
	public void test_backup(){
		clean();
		createOneParamType(TYPE_KEY);
		createMore(5, TYPE_KEY);
		
		String sql = paramService.backupToSqlString();
		
		assertNotNull(sql);
		System.out.println(sql);
	} 

	/*********prepare data**********/
	private void clean(){
		cleanParamAndTypes();
	}

	private void cleanParamAndTypes(){
		try {
			connection.prepareStatement("delete from param").execute();
			connection.prepareStatement("delete from param_type").execute();
		} catch (SQLException e) {
		}
	}
	private void createOneParamType(String key){
		try {
			connection.prepareStatement("insert into param_type(`key`,name) values('"+key+"','test')").execute();
		} catch (SQLException e) {
		}
	}

	private void createOneParam(String name,String key,String value,String types){
		if(types==null || "".equals(types)){
			types = TYPE_KEY;
		}
		try {
			connection.prepareStatement("insert into param(name,types,`key`,value,sort,isuse) " +
					"values('"+name+"','"+types+"','"+key+"','"+value+"',0,1)").execute();
		} catch (SQLException e) {
		}
	}

	private void createMore(int many,String paramType){
		for(int i=0;i<many;i++){
			createOneParam("param"+i, "key"+i, "value"+i, paramType);
		}
	}

	private Param getOneParam(String paramType,String key){
		createOneParamType(TYPE_KEY);
		createOneParam("oneParam", key, "123", paramType);
		Param p = new Param();
		p.setKey(key);
		p.setTypes(paramType);
		List<Param> paramList=paramDao.listParam(p);
		return paramList.get(0);
	}

}
