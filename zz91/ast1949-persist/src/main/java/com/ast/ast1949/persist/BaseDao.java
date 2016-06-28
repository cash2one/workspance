package com.ast.ast1949.persist;

import java.util.LinkedHashMap;
import java.util.List;

import com.ast.ast1949.domain.DomainSupport;
import com.ast.ast1949.domain.PaginationResult;
import com.ast.ast1949.dto.PageDto;

public interface BaseDao {
	/**
	 * 新增记录
	 * 
	 * @param domainObj
	 * @return 新记录ID
	 */
	Integer insert(DomainSupport domainObj);

	/**
	 * 删除实体
	 * 
	 * @param domainObj
	 * @return 删除记录量
	 */
	int delete(String sqlKey, Integer id);

	/**
	 * 修改实体
	 * 
	 * @param domainObj
	 * @return 是否修改成功
	 */
	Integer update(DomainSupport domainObj);

	/**
	 * 清除所有数据
	 * @param sqlKey
	 */
	void clearAllData(String sqlKey);
	/**
	 * 查询实体
	 * 
	 * @param domainObj
	 * @return
	 */
	DomainSupport query(String sqlKey, Integer id);

	/**
	 * 查询所有实体
	 * 
	 * @param domainObj
	 * @return
	 */
	List<? extends DomainSupport> queryAll(String sqlKey);

	/**
	 * 查询所有记录总数
	 * 
	 * @param domainObj
	 * @return
	 */
	int queryResultCount(String sqlKey);

	
	int queryPaginationResultCount(DomainSupport domainObj);
	
	PaginationResult queryPaginationData(DomainSupport domainObj,PageDto pager);
	
	
	int queryPaginationResultCount(String sqlKey, String where, Object[] params);

	/**
	 * 查询分页数据
	 * 
	 * @param querySql
	 *            查询的SQL编码
	 * @return
	 */
	PaginationResult queryPaginationData(String querySql);

	/**
	 * 查询分页数据
	 * 
	 * @param querySql
	 *            查询的SQL编码
	 * @param firstResult
	 *            数据索引开始位置,value=-1,查询所有记录
	 * @param maxResult
	 *            返回的最大记录量,value=-1,查询所有记录
	 * @return
	 */
	PaginationResult queryPaginationData(String querySql, int firstResult, int maxResult);

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
	 *            查询条件
	 * @param params
	 *            查询条件参数
	 * @return
	 */
	PaginationResult queryPaginationData(String querySql, int firstResult, int maxResult,
			String where, Object[] params);

	/**
	 * 查询分页数据
	 * 
	 * @param querySql
	 *            查询的SQL编码
	 * @param firstResult
	 *            数据索引开始位置,value=-1,查询所有记录
	 * @param maxResult
	 *            返回的最大记录量,value=-1,查询所有记录
	 * @param orderBy
	 *            排序,key为排序字段,value为排序方式［asc,desc］
	 * @return
	 */
	PaginationResult queryPaginationData(String querySql, int firstResult, int maxResult,
			LinkedHashMap<String, String> orderBy);

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
	 *            查询条件
	 * @param params
	 *            查询条件参数
	 * @param orderBy
	 *            排序,key为排序字段,value为排序方式［asc,desc］
	 * @return
	 */
	PaginationResult queryPaginationData(String querySql, int firstResult, int maxResult,
			String where, Object[] params, LinkedHashMap<String, String> orderBy);
}
