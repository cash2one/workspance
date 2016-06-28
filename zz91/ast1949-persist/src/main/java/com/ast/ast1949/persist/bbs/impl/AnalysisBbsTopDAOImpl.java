package com.ast.ast1949.persist.bbs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.AnalysisBbsTop;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.AnalysisBbsTopDAO;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-8-17 
 */
@Component("analysisBbsTopDAO")
public class AnalysisBbsTopDAOImpl extends BaseDaoSupport implements AnalysisBbsTopDAO {

	final static String SQL_PREFIX="analysisBbsTop";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AnalysisBbsTop> queryBbsTopByType(String category,long gmtTarget,Integer max) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("category", category);
		root.put("gmtTarget", gmtTarget);
		root.put("max", max);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryBbsTopByType"),root);
	}

}
