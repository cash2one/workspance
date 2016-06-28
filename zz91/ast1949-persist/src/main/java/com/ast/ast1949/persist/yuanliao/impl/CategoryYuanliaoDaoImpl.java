package com.ast.ast1949.persist.yuanliao.impl;
/**
 * @date 2015-08-21
 * @author shiqp
 */
import java.util.List;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.yuanliao.CategoryYuanliao;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.yuanliao.CategoryYuanliaoDao;

@Component("categoryYuanliaoDao")
public class CategoryYuanliaoDaoImpl extends BaseDaoSupport implements CategoryYuanliaoDao {
	
	final static String SQL_FIX = "categoryYuanliao";

	@Override
	public Integer insertCategoryYuanliao(CategoryYuanliao categoryYuanliao) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insertCategoryYuanliao"), categoryYuanliao);
	}

	@Override
	public CategoryYuanliao queryCategoryYuanliaoByCode(String code) {
		return (CategoryYuanliao) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryCategoryYuanliaoByCode"), code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryYuanliao> queryCategoryYuanliaoByParentCode(String parentCode) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryCategoryYuanliaoByParentCode"), parentCode);
	}

	@Override
	public Integer updateCategoryYuanliao(CategoryYuanliao categoryYuanliao) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateCategoryYuanliao"), categoryYuanliao);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryYuanliao> queryAllCategoryYuanliao() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryAllCategoryYuanliao"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryYuanliao> queryCategoryYuanliaoByKeyword(String keyword) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryCategoryYuanliaoByKeyword"), keyword);
	}

	@Override
	public String queryMaxCategoryYuanLiao(String parentCode) {
		return (String) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryMaxCategoryYuanLiao"), parentCode);
	}

	@Override
	public Integer queryPinyin(String pinyin) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryPinyin"), pinyin);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryYuanliao> querySimilarCategory(String keyword) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "querySimilarCategory"), keyword);
	}

}
