package com.ast.ast1949.persist.yuanliao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.yuanliao.ProvincePinyin;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.yuanliao.ProvincePinyinDao;

@Component("provincePinyinDao")
public class ProvincePinyinDaoImpl extends BaseDaoSupport implements ProvincePinyinDao {
	final static String SQL_FIX = "provincePinyin";

	@SuppressWarnings("unchecked")
	@Override
	public List<ProvincePinyin> queryAllProvincePinyin() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryAllProvincePinyin"));
	}

}
