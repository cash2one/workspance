package com.ast.ast1949.persist.yuanliao.impl;

/**
 * @date 2015-08-22
 * @author shiqp
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.yuanliao.YuanLiaoSearch;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.yuanliao.SearchDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.yuanliao.YuanliaoDao;

@Component("yuanliaoDao")
public class YuanliaoDaoImpl extends BaseDaoSupport implements YuanliaoDao {
	final static String SQL_FIX = "yuanliao";

	@Override
	public Integer insertYuanliao(Yuanliao yuanliao) {
		return (Integer) getSqlMapClientTemplate().insert(
				addSqlKeyPreFix(SQL_FIX, "insertYuanliao"), yuanliao);
	}

	@Override
	public Integer updateYuanliao(Yuanliao yuanliao) {
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "updateYuanliao"), yuanliao);
	}

	@Override
	public Yuanliao queryYuanliaoById(Integer id) {
		return (Yuanliao) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "queryYuanliaoById"), id);
	}

	@Override
	public Integer countYuanliaoList(Yuanliao yuanliao, YuanLiaoSearch search) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("yuanliao", yuanliao);
		map.put("search", search);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "countYuanliaoList"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Yuanliao> queryYuanliaoList(Yuanliao yuanliao,
			PageDto<YuanliaoDto> page, YuanLiaoSearch search) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("yuanliao", yuanliao);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryYuanliaoList"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryYuanliaoForCategory(Integer companyId) {
		return getSqlMapClientTemplate()
				.queryForList(
						addSqlKeyPreFix(SQL_FIX, "queryYuanliaoForCategory"),
						companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Yuanliao> queryYuanliaoListByAdmin(PageDto<YuanliaoDto> page,
			YuanLiaoSearch search) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("search", search);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryYuanliaoListByAdmin"), map);
	}

	@Override
	public Integer countYunaliaoListByAdmin(YuanLiaoSearch search) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_FIX, "countYunaliaoListByAdmin"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Yuanliao> queryYuanLiaoByCondition(YuanLiaoSearch search,
			Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(search.getCategoryYuanliaoCode()!=null){
		search.setCategoryYuanliaoCode(search.getCategoryYuanliaoCode() + "%");
		}
		map.put("search", search);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryYuanLiaoByCondition"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Yuanliao> queryYuanliaoBYCompanyId(Integer companyId) {
		return getSqlMapClientTemplate()
				.queryForList(
						addSqlKeyPreFix(SQL_FIX, "queryYuanliaoBYCompanyId"),
						companyId);
	}

	@Override
	public Integer updateRefreshTime(Integer id, String expireTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expireTime", expireTime);
		map.put("id", id);
		return getSqlMapClientTemplate().update(
				addSqlKeyPreFix(SQL_FIX, "updateRefreshTime"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Yuanliao> queryYuanliaoSearchDto(SearchDto search,
			PageDto<YuanliaoDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(search.getCategoryYuanliaoCode()!=null){
		search.setCategoryYuanliaoCode(search.getCategoryYuanliaoCode() + "%");
		}
		map.put("search", search);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_FIX, "queryYuanliaoSearchDto"), map);
	}

	@Override
	public Integer queryYuanliaoSearchDtoCount(SearchDto search) {
		Map<String, Object> map = new HashMap<String, Object>();
		search.setCategoryYuanliaoCode(search.getCategoryYuanliaoCode()+"%");
		map.put("search", search);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryYuanliaoSearchDtoCount"),map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Yuanliao> queryNewSize(Integer size) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryNewSize"), size);
	}

}
