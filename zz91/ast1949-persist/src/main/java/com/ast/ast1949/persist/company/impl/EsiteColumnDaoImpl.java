/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-15
 */
package com.ast.ast1949.persist.company.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.EsiteColumnDo;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.EsiteColumnDao;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-15
 */
@Component("esiteColumnDao")
public class EsiteColumnDaoImpl extends BaseDaoSupport implements
		EsiteColumnDao {

	final static String SQL_PREFIX = "esiteColumn";

	@Override
	public Map<String, EsiteColumnDo> queryAllColumnByCategory(String category) {
		List<EsiteColumnDo> list = getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryAllColumnByCategory"),
				category);
		Map<String, EsiteColumnDo> map = new LinkedHashMap<String, EsiteColumnDo>();
		if(list==null){
			return map;
		}
		for(EsiteColumnDo obj:list){
			map.put(obj.getColumnId(), obj);
		}
		return map;
	}

}
