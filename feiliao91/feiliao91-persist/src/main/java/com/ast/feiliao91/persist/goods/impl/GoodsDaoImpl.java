/**
 * @author shiqp
 * @date 2016-01-14
 */
package com.ast.feiliao91.persist.goods.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.GoodsDto;
import com.ast.feiliao91.domain.goods.GoodsSearchDto;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.goods.GoodsDao;

@Component("goodsDao")
public class GoodsDaoImpl extends BaseDaoSupport implements GoodsDao {
	final static String SQL_PREFIX="goods";

	@Override
	public Integer insertGoods(Goods goods) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertGoods"), goods);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryCategoryByCompanyId(Integer companyId, Integer size) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryCategoryByCompanyId"), map);
	}

	@Override
	public Goods queryGoodById(Integer id) {
		return (Goods) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryGoodById"), id);
	}

	@Override
	public Integer updateGoods(Goods goods) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateGoods"), goods);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> queryBySearchDto(GoodsSearchDto searchDto, PageDto<GoodsDto> page) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("searchDto", searchDto);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryBySearchDto"), map);
	}

	@Override
	public Integer queryCountBySearchDto(GoodsSearchDto searchDto) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("searchDto", searchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCountBySearchDto"), map);
	}

	@Override
	public Goods queryById(Integer id) {
		return (Goods) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryById"), id);
	}

	@Override
	public Integer querySuccessOrder(Integer goodsId,Integer companyId) {
		Map<String, Integer> map =new HashMap<String, Integer>();
		map.put("goodsId", goodsId);
		map.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "querySuccessOrder"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> queryGoodsByCategory(Integer companyId, String mainCategory, Integer size, Integer goodsId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("mainCategory", mainCategory);
		map.put("size", size);
		map.put("goodsId", goodsId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryGoodsByCategory"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> queryNewGoodsBySameCategory(String mainCategory,Integer size, Integer goodsId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mainCategory", mainCategory);
		map.put("size", size);
		map.put("goodsId", goodsId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryNewGoodsBySameCategory"), map);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> queryHighSalesGoodsBySameCategory(String mainCategory,Integer size, Integer goodsId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mainCategory", mainCategory);
		map.put("size", size);
		map.put("goodsId", goodsId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryHighSalesGoodsBySameCategory"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> queryRandomGoodsBySameCategory(String mainCategory,Integer size, Integer goodsId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mainCategory", mainCategory);
		map.put("size", size);
		map.put("goodsId", goodsId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryRandomGoodsBySameCategory"), map);
	}

	@Override
	public Integer updateStatusByUser(Integer id,Integer isDel,Integer isSell,Integer isGarbage){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("id", id);
		map.put("isDel", isDel);
		map.put("isSell", isSell);
		map.put("isGarbage", isGarbage);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateStatusByUser"), map);
	}

	@Override
	public Integer updateStatus(Integer id, String checkPerson, Integer checkStatus) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("checkPerson", checkPerson);
		map.put("checkStatus", checkStatus);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCheckStatus"), map);
	}
	
	@Override
	public Integer updateGoodsQuantityByGoodsId(String quantity,Integer goodsId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", goodsId);
		map.put("quantity", quantity);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateGoodsQuantityByGoodsId"), map);
	}
}
