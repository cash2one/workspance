package com.ast.ast1949.persist.bbs.impl;
/**
 * @author shiqp
 */
import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostCategory;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.bbs.BbsPostCategoryDao;

@Component("bbsPostCategoryDao")
public class BbsPostCategoryDaoImpl extends BaseDaoSupport implements BbsPostCategoryDao {
	
	final static String SQL_PREFIX="bbsPostCategory";
	@Override
	public Integer insertCategory(BbsPostCategory bbsPostCategory) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertCategory"), bbsPostCategory);
	}

	@Override
	public Integer updateCategoryById(BbsPostCategory bbsPostCategory) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCategoryById"), bbsPostCategory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostCategory> queryCategoryByParentId(Integer parentId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryCategoryByParentId"), parentId);
	}

	@Override
	public Integer queryMaxCategoryIdByParentId(Integer parentId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryMaxCategoryIdByParentId"), parentId);
	}

	@Override
	public BbsPostCategory querySimpleCategoryById(Integer id) {
		return (BbsPostCategory) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "querySimpleCategoryById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbsPostCategory> queryAllCategory() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryAllCategory"));
	}

}
