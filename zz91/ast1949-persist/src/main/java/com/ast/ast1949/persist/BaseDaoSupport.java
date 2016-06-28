package com.ast.ast1949.persist;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ast.ast1949.domain.DomainSupport;
import com.ast.ast1949.domain.PaginationResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.util.DateUtil;

/**
 * 
 * @author liuwb
 * 
 */
public class BaseDaoSupport extends SqlMapClientDaoSupport implements BaseDao {
	

	/**
	 * 获取SQL语句名称
	 * 
	 * @param sqlPreFix
	 *            对应的Ibaits命名空间
	 * @param sqlKey
	 *            对应的SQL语句KEY
	 * @return
	 */
	protected String addSqlKeyPreFix(String sqlPreFix, String sqlKey) {
		StringBuilder buf = new StringBuilder();
		if (sqlPreFix != null && sqlPreFix.trim().length() > 0)
			buf.append(sqlPreFix).append(".").append(sqlKey);
		else
			return sqlKey;
		return buf.toString();
	}

	@Override
	public int delete(String sqlKey, Integer id) {
		int result = getSqlMapClientTemplate().delete(sqlKey, id);
		if (result > 0)
			return result;
		return 0;
	}

	@Override
	public void clearAllData(String sqlKey) {
		getSqlMapClientTemplate().delete(sqlKey);
	}

	@Override
	public Integer insert(DomainSupport domainObj) {
		Object obj = getSqlMapClientTemplate().insert(domainObj.getSqlKey(), domainObj);
		if (obj != null) {//primary key
			return (Integer) obj;
		}
		return null;
	}

	@Override
	public DomainSupport query(String sqlKey, Integer id) {
		return (DomainSupport) getSqlMapClientTemplate().queryForObject(sqlKey, id);
	}

	@Override
	public List<? extends DomainSupport> queryAll(String sqlKey) {
		return (List<? extends DomainSupport>) getSqlMapClientTemplate().queryForList(sqlKey);
	}

	@Override
	public Integer update(DomainSupport domainObj) {
		return getSqlMapClientTemplate().update(domainObj.getSqlKey(), domainObj);
	}

	@Override
	public int queryResultCount(String sqlKey) {
		return (Integer) getSqlMapClientTemplate().queryForObject(sqlKey);
	}
	//=======================根据domain object获取分页============================================
	@Override
	public int queryPaginationResultCount(DomainSupport domainObj) {
		return (Integer) getSqlMapClientTemplate().queryForObject(domainObj.getSqlKey()+"Count",domainObj);
	}
	
	@Override
	public PaginationResult queryPaginationData(DomainSupport domainObj, PageDto pager) {
		PaginationResult queryResult = new PaginationResult();
		queryResult.setResultTotal(queryPaginationResultCount(domainObj));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("queryParam", domainObj);
		paramMap.put("firstResult", pager.getStartIndex());
		paramMap.put("maxResult", pager.getPageSize());
		paramMap.put("orderByColumn", pager.getSort());
		paramMap.put("orderByDir", pager.getDir());
		queryResult.setResultList(getSqlMapClientTemplate().queryForList(domainObj.getSqlKey(), paramMap));
		return queryResult;
	}
	//=======================手动拼装分页================================================
	@Override
	public int queryPaginationResultCount(String sqlKey, String where, Object[] params) {
		where = setWhereParams(where, params);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("subSqlWhere", where);
		return (Integer) getSqlMapClientTemplate().queryForObject(sqlKey, paramMap);
	}

	@Override
	public PaginationResult queryPaginationData(String querySql) {
		return queryPaginationData(querySql, -1, -1, null, null, null);
	}

	@Override
	public PaginationResult queryPaginationData(String querySql, int firstResult, int maxResult) {
		return queryPaginationData(querySql, firstResult, maxResult, null, null, null);
	}

	@Override
	public PaginationResult queryPaginationData(String querySql, int firstResult, int maxResult,
			LinkedHashMap<String, String> orderBy) {
		return queryPaginationData(querySql, firstResult, maxResult, null, null, orderBy);
	}

	@Override
	public PaginationResult queryPaginationData(String querySql, int firstResult, int maxResult,
			String where, Object[] params) {
		return queryPaginationData(querySql, firstResult, maxResult, where, params, null);
	}

	/**
	 * 查询分页数据
	 * 
	 * @param querySql
	 *            查询的SQL编码
	 * @param firstResult
	 *            数据索引开始位置,value=-1,查询所有记录
	 * @param maxResult
	 *            返回的最大记录量,value=-1,查询所有记录
	 * @param where
	 *            查询条件,去where关键字,对应的条件值采用占位符：？index，index从1开始，如：id=?1 and name=?2 有待优化，可以覆盖此方法
	 * @param params
	 *            查询条件参数
	 * @param orderBy
	 *            排序,key为排序字段,value为排序方式［asc,desc］
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PaginationResult queryPaginationData(String querySql, int firstResult, int maxResult,
			String where, Object[] params, LinkedHashMap<String, String> orderBy) {
		if (where != null)
			where = setWhereParams(where, params);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("subSqlWhere", where);
		int resultTotal = (Integer) getSqlMapClientTemplate().queryForObject(querySql + "Count",
				paramMap);
		paramMap.put("subSqlOrder", buildOrderByStmt(orderBy));
		paramMap.put("subSqlLimit", buildLimitStmt(firstResult, maxResult));
		List<DomainSupport> resultList = getSqlMapClientTemplate().queryForList(querySql, paramMap);
		PaginationResult queryResult = new PaginationResult();
		queryResult.setResultList(resultList);
		queryResult.setResultTotal(resultTotal);
		return queryResult;
	}

	/**
	 * 组装 where 条件
	 * 
	 * @param where
	 * @param params
	 *            参数类型限定： ［Integer，Long，Float，Double，Date，String，Boolean］ 其中Boolean型true＝1，false=0
	 * @return
	 */
	private String setWhereParams(String where, Object[] params) {
		int index = 1;
		String indexBuf = "?1";
		while (where.indexOf(indexBuf) != -1) {
			if (index > params.length)
				break;
			Object obj = params[index - 1];
			if (obj instanceof Integer || obj instanceof Long || obj instanceof Float
					|| obj instanceof Double) {
				where = where.replaceAll("\\" + indexBuf, obj.toString());
			} else if (obj instanceof Date) {
				where = where.replaceAll("\\" + indexBuf, "'"
						+ DateUtil.toString((Date) obj, "yyyy-MM-yy") + "'");
			} else if (obj instanceof String) {
				where = where.replaceAll("\\" + indexBuf, "'" + obj.toString() + "'");
			} else if (obj instanceof Boolean) {
				Boolean value = (Boolean) obj;
				if (value)
					where = where.replaceAll("\\" + indexBuf, "1");
				else
					where = where.replaceAll("\\" + indexBuf, "0");
			}
			index++;
			indexBuf = "?" + index;
		}
		//如果还有参数未设定则WHERE所有条件取消
		if (where.indexOf("?") != -1)
			return null;
		return where;
	}

	/**
	 * 组装Order by 子句
	 * 
	 * @param orderBy
	 * @return
	 */
	private String buildOrderByStmt(LinkedHashMap<String, String> orderBy) {
		StringBuilder strBuf = new StringBuilder();
		if (orderBy != null) {
			strBuf.append(" order by ");
			for (Entry<String, String> entry : orderBy.entrySet()) {
				strBuf.append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
			}
			if (','==(strBuf.charAt(strBuf.length() - 1)))
				strBuf.deleteCharAt(strBuf.length() - 1);
			else
				return null;
		}
		return strBuf.toString();
	}

	private String buildLimitStmt(int firstResult, int maxResult) {
		StringBuilder strBuf = new StringBuilder();
		//索引开始位置或最大返回记录量为－1时查询所有记录
		if (firstResult != -1 && maxResult != -1) {
			strBuf.append(" limit ").append(firstResult).append(",").append(maxResult);
		}
		return strBuf.toString();
	}
}
