/**
 * @author shiqp
 * @date 2016-01-31
 */
package com.ast.feiliao91.persist.goods.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.goods.Shopping;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.goods.ShoppingDto;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.goods.ShoppingDao;
@Component("shoppingDao")
public class ShoppingDaoImpl extends BaseDaoSupport implements ShoppingDao {
	final static String SQL_PREFIX="shopping";

	@Override
	public Shopping queryById(Integer id){
		return (Shopping) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryById"), id);
	}
	
	@Override
	public Integer insertShoppingMenu(Shopping shopping) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertShoppingMenu"), shopping);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> querySellCompanyId(PageDto<ShoppingDto> page, Integer buyCompanyId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("buyCompanyId", buyCompanyId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "querySellCompanyId"), map);
	}

	@Override
	public Integer countSellCompanyId(Integer buyCompanyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countSellCompanyId"),buyCompanyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Shopping> queryShoppingByBothId(Integer sellCompanyId, Integer buyCompanyId, Integer goodId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sellCompanyId", sellCompanyId);
		map.put("buyCompanyId", buyCompanyId);
		map.put("goodId", goodId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryShoppingByBothId"), map);
	}

	@Override
	public Integer updateShoppingInfo(Integer id, String attribute, String number, String money) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("attribute", attribute);
		map.put("number", number);
		map.put("money", money);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateShoppingInfo"), map);
	}

	@Override
	public Integer updateIsDel(Integer id, Integer isDel) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("isDel", isDel);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateIsDel"), map);
	}

	@Override
	public Integer countShoppingByCondition(Integer buyCompanyId, Integer sellCompanyId, Integer goodId) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("buyCompanyId", buyCompanyId);
		map.put("sellCompanyId", sellCompanyId);
		map.put("goodId", goodId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countShoppingByCondition"), map);
	}

}
