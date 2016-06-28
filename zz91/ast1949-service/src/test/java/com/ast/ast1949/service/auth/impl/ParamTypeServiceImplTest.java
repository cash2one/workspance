package com.ast.ast1949.service.auth.impl;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.exception.ServiceLayerException;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.auth.ParamTypeService;
import com.zz91.util.domain.ParamType;

public class ParamTypeServiceImplTest extends BaseServiceTestCase{

	@Autowired
	ParamTypeService paramTypeService;

	final static int DATA_NUM = 5;

	@Test
	public void testCreateParamType() throws SQLException {
		clean();

		ParamType type = new ParamType();
		type.setKey("test key");
		type.setName("test name");
		paramTypeService.createParamType(type);

		ParamType t=paramTypeService.listOneParamTypeByKey(type.getKey());
		assertNotNull(t);
		assertEquals(t.getKey(), "test key");

		try {
			paramTypeService.createParamType(type);
			fail();
		} catch (ServiceLayerException e) {
		}
	}

	@Test
	public void testListAllParamType() throws SQLException {
		clean();
		prepareParamType();

		List<ParamType> list = paramTypeService.listAllParamType();
		assertEquals(DATA_NUM,list.size());
	}

	@Test
	public void testListOneParamTypeByKey() throws SQLException {
		clean();
		ParamType type = new ParamType();
		type.setKey("test key");
		type.setName("test name");
		createParamType(type);

		ParamType pt = paramTypeService.listOneParamTypeByKey("test key");
		assertEquals(pt.getKey(), "test key");
	}

	@Test
	public void testDeleteParamType() throws SQLException {
		clean();
		ParamType type = new ParamType();
		type.setKey("test key");
		type.setName("test name");
		createParamType(type);

		paramTypeService.deleteParamType("test key");

		ParamType pt2 = paramTypeService.listOneParamTypeByKey("test key");
		assertNull(pt2);
	}

	@Test
	public void testUpdateParamType() throws SQLException {
		clean();
		ParamType type = new ParamType();
		type.setKey("test key");
		type.setName("test name");
		createParamType(type);

		type.setName("update name");

		paramTypeService.updateParamType(type);

		ParamType tu = paramTypeService.listOneParamTypeByKey("test key");
		assertNotNull(tu);
		assertEquals(tu.getName(), "update name");

	}

	/*************prepare***************/
	private void clean() throws SQLException{
		connection.prepareStatement("delete from param_type").execute();
	}

	private void createParamType(ParamType type) throws SQLException{
		String sql="";
		sql="insert into param_type(`key`,name,gmt_created)";
		sql=sql+" values('"+type.getKey()+"','"+type.getName()+"',now())";

	        connection.prepareStatement(sql).execute();
	}


	private void prepareParamType() throws SQLException{
		for(int i=0;i<DATA_NUM;i++){
			ParamType p = new ParamType();
			p.setKey("testkey"+i);
			p.setName("testname"+i);
			createParamType(p);
		}
	}


}
