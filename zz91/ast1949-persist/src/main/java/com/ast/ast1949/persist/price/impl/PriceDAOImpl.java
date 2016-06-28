/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-19 by Rolyer.
 */
package com.ast.ast1949.persist.price.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.PaginationResult;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.price.ForPriceDTO;
import com.ast.ast1949.dto.price.PriceDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.price.PriceDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *	实现资讯接口
 */

@Component("priceDAO")
public class PriceDAOImpl extends BaseDaoSupport implements PriceDAO{
	private static String sqlPreFix="price";
	final private int DEFAULT_BATCH_SIZE=20;
	public Integer batchDeleteUserById(int[] entities){
		int impacted=0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1)/DEFAULT_BATCH_SIZE;
		try {
			for(int currentBatch=0;currentBatch<batchNum;currentBatch++){
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch+1)* DEFAULT_BATCH_SIZE;
				endIndex = endIndex>entities.length?entities.length:endIndex;
				for(int i=beginIndex;i<endIndex;i++){
					impacted+=getSqlMapClientTemplate().delete("price.deletePriceById",entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch delete user failed.",e);
		}
		return impacted;
	}

//	public Integer deletePriceById(Integer id) throws IllegalArgumentException {
//		Assert.notNull(id, "The id must not be null.");
//		return getSqlMapClientTemplate().delete("price.deletePriceById", id);
//	}

	public Integer getPriceRecordConutByCondition(PriceDTO price) {
		return (Integer) getSqlMapClientTemplate().queryForObject("price.getPriceRecordConutByCondition", price);
	}

	public Integer insertPrice(PriceDO price) throws IllegalArgumentException {
		Assert.notNull(price, "PriceDo must not be null.");
		Assert.notNull(price.getTitle(), "The price title must not be null.");
		return (Integer) getSqlMapClientTemplate().insert("price.insertPrice", price);
	}

	@SuppressWarnings("unchecked")
	public List<PriceDTO> queryMiniPriceByCondition(PriceDTO price) {
		return getSqlMapClientTemplate().queryForList("price.queryMiniPriceByCondition", price);
	}

//	public PriceDO queryPriceById(Integer id) throws IllegalArgumentException {
//		Assert.notNull(id, "The id must not be null.");
//		return (PriceDO) getSqlMapClientTemplate().queryForObject("price.queryPriceById", id);
//	}

	public Integer updatePriceById(PriceDO price) throws IllegalArgumentException {
		Assert.notNull(price, "The PriceDO must not be null");
		Assert.notNull(price.getId(), "The Id must not be null");
		return getSqlMapClientTemplate().update("price.updatePriceById", price);
	}

	public PriceDTO queryPriceByIdForEdit(Integer id)
			throws IllegalArgumentException {
		return (PriceDTO) getSqlMapClientTemplate().queryForObject("price.queryPriceByIdForEdit", id);
	}

//	@SuppressWarnings("unchecked")
//	public List<PriceDO> queryPriceByClickNumber() {
//
//		return getSqlMapClientTemplate().queryForList("price.queryPriceByClickNumber");
//	}

	@SuppressWarnings("unchecked")
	public List<PriceDO> queryPriceByTypeId(Integer typeId, Integer parentId, Integer assistTypeId, int max) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("typeId", typeId);
		root.put("parentId", parentId);
		root.put("assistTypeId", assistTypeId);
		root.put("max", max);
		return getSqlMapClientTemplate().queryForList("price.queryPriceByTypeId", root);
	}

	@SuppressWarnings("unchecked")
	public List<ForPriceDTO> queryPriceByParentId(Integer parentId, Integer max) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("parentId", parentId);
		root.put("max", max);
		
		return getSqlMapClientTemplate().queryForList("price.queryPriceByParentId", root);
	}

	@SuppressWarnings("unchecked")
	public List<PriceDO> queryPriceByTitleAndTypeId(
			Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("price.queryPriceByTitleAndTypeId", param);
	}

//	@SuppressWarnings("unchecked")
//	public List<PriceDO> queryPriceByAssistTypeId(PriceDTO priceDTO) {
//		return getSqlMapClientTemplate().queryForList("price.queryPriceByAssistTypeId", priceDTO);
//	}

	public PriceDO queryDownPriceById(Integer id)
			throws IllegalArgumentException {
		Assert.notNull(id, "id is not null");
		return (PriceDO) getSqlMapClientTemplate().queryForObject("price.queryDownPriceById", id);
	}

	public PriceDO queryOnPriceById(Integer id) throws IllegalArgumentException {
		Assert.notNull(id, "id is not null");
		return (PriceDO) getSqlMapClientTemplate().queryForObject("price.queryOnPriceById", id);
	}

	@SuppressWarnings("unchecked")
	public List<ForPriceDTO> queryEachPriceByParentId(Integer parentId) {
          Assert.notNull(parentId, "parentId is not null");
		return getSqlMapClientTemplate().queryForList("price.queryEachPriceByParentId", parentId);
	}
	

	@SuppressWarnings("unchecked")
	public List<ForPriceDTO> queryPriceAndCategoryByTypeId(Integer typeId, Integer max) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("typeId", typeId);
		root.put("max", max);
		return getSqlMapClientTemplate().queryForList("price.queryPriceAndCategoryByTypeId", root);
	}

	public PriceDO queryTopGmtOrderByParentId(Integer parentId) {
            Assert.notNull(parentId, "parentId is not null");
		return (PriceDO) getSqlMapClientTemplate().queryForObject("price.queryTopGmtOrderByParentId", parentId);
	}

//	public Integer queryPriceCountByTypeId(PriceDTO priceDTO) {
//        Assert.notNull(priceDTO, "priceDTO is not null");
//		return (Integer) getSqlMapClientTemplate().queryForObject("price.queryPriceCountByTypeId", priceDTO);
//	}

	@SuppressWarnings("unchecked")
	public PageDto<PriceDO> queryPricePaginationListByTitle(String titleKeywords,PageDto page){
		PriceDO price=new PriceDO();
		price.setTitle(titleKeywords);
		price.setSqlKey(super.addSqlKeyPreFix(sqlPreFix, "queryPricePaginationListByTitle"));
		PaginationResult paginationResult=queryPaginationData(price, page);
		page.setRecords(paginationResult.getResultList());
		page.setTotalRecords(paginationResult.getResultTotal());
		return page;
	}

//	public Integer queryPriceCountByCondition(PriceDTO priceDTO) {
//		Assert.notNull(priceDTO, "priceDTO is not null");
//		return (Integer) getSqlMapClientTemplate().queryForObject("price.queryPriceCountByCondition", priceDTO);
//	}

//	@SuppressWarnings("unchecked")
//	public List<PriceDO> queryPriceByTagsCondition(TagsRelateArticleDTO tagsRelateArticleDTO) {
//	    Assert.notNull(tagsRelateArticleDTO, "tagsRelateArticleDTO is not null");
//		return getSqlMapClientTemplate().queryForList("price.queryPriceByTagsCondition", tagsRelateArticleDTO);
//	}

//	public Integer queryPriceCountByTagsCondition(TagsRelateArticleDTO tagsRelateArticleDTO) {
//         Assert.notNull(tagsRelateArticleDTO, "tagsRelateArticleDTO is not null");
//		return (Integer) getSqlMapClientTemplate().queryForObject("price.queryPriceCountByTagsCondition", tagsRelateArticleDTO);
//	}

	@SuppressWarnings("unchecked")
	public List<PriceDO> queryLatestPriceByTypeId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().queryForList("price.queryLatestPriceByTypeId", id);
	}

	public Integer updateIsCheckedById(Integer id, String isChecked) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(isChecked, "the isChecked must not be null");
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("id", id);
		param.put("isChecked", isChecked);
		
		return getSqlMapClientTemplate().update("price.updateIsCheckedById", param);
	}

//	@SuppressWarnings("unchecked")
//	public List<PriceDO> queryPriceByTypeIdAndAssistTypeId(Integer typeId,Integer assistTypeId){
////		Assert.notNull(typeId, "the typeId must not be null");
////		Assert.notNull(assistTypeId, "the assistTypeId must not be null");
//		Map<String,Object> map = new HashMap<String ,Object>();
//		map.put("typeId", typeId);
//		map.put("assistTypeId", assistTypeId);
//		return getSqlMapClientTemplate().queryForList("price.queryPriceByTypeIdAndAssistTypeId", map);
//	}

	@Override
	public PriceDO queryPriceForSubscribe(Integer typeId, Integer assistTypeId) {
		Assert.notNull(typeId, "the typeId must not be null");
		
		Map<String, Object> param =new HashMap<String, Object>();
		param.put("typeId", typeId);
		param.put("assistTypeId", assistTypeId);
		
		return (PriceDO) getSqlMapClientTemplate().queryForObject("price.queryPriceForSubscribe", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDTO> queryNewPriceOnWeek(Date firstDate,Date lastDate,Integer size) {
		Assert.notNull(size, "size  is not null");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("firstDate", firstDate);
		map.put("lastDate", lastDate);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList("price.queryNewPriceOnWeek",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<PriceDO> queryPriceByType(Integer typeId, Integer parentId,
			Integer assistTypeId, PageDto<PriceDO> page){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("typeId", typeId);
		root.put("parentId", parentId);
		root.put("assistTypeId", assistTypeId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList("price.queryPriceByType",root);
	}
	
	public Integer queryPriceByTypeCount(Integer typeId, Integer parentId,
			Integer assistTypeId){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("typeId", typeId);
		root.put("parentId", parentId);
		root.put("assistTypeId", assistTypeId);
		return (Integer) getSqlMapClientTemplate().queryForObject("price.queryPriceByTypeCount",root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDO> queryPriceByTypeIdOrParentId(Integer id, Integer max) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("max", max);
		return getSqlMapClientTemplate().queryForList("price.queryPriceByTypeIdOrParentId", root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ForPriceDTO> queryPriceByIndex(String code, Integer max) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("code", code);
		root.put("max", max);
		return getSqlMapClientTemplate().queryForList("price.queryPriceByIndex", root);
	}

	@Override
	public Integer updateRealClickNumberById(Integer number,Integer Id) {
		Map<String, Object> root=new HashMap<String, Object>();
		Assert.notNull(number, "The number must not be null");
		Assert.notNull(Id, "The Id must not be null");
		root.put("realClickNumber", number);
		root.put("id", Id);
		return getSqlMapClientTemplate().update("price.updateRealClickNumberById", root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ForPriceDTO> queryLatestPrice(String code, Integer max) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("max", max);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryLatestPrice2"), root);
	}

	@Override
	public Integer queryPriceCount(String from) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("from", from);
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryPriceCount"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDO> queryPriByTypeId() {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryPriByTypeId"));
	}

	@Override
	public Integer getTypeidById(Integer id) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "getTypeidById"),id);
	}

	@Override
	public String queryTagsById(Integer id) {
		return (String)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryTagsById"),id);
	}
	
	@Override
	public Integer queryUVById(Integer id){
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryUVById"),id);
	}

	@Override
	public Integer queryIdByTitle(String title,String date) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("date", date);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryIdByTitle"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDO> queryListByFromTo(String from,String to ){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", from);
		map.put("to", to);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryListByFromTo"), map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDO> queryListByTypeId(Integer typeId,Integer size){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId",typeId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryListByTypeId"), map);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<PriceDO> queryListByAllTypeId(Integer typeId, Integer size) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("typeId",typeId);
        map.put("size", size);
        return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryListByAllTypeId"), map);
    }

	@Override
	public Integer updateContentQueryById(Integer id, String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("content", content);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateContentQueryById"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDO> queryNewPrice(Integer typeId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryNewPrice"),typeId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDO> queryNewPrice2(Integer typeId) {
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryNewPrice2"),typeId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDO> queryListByTypeIdHalfYear(Integer type, Integer month) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("month", month);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryListByTypeIdHalfYear"),map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceDO> queryPriceByTypeTwo(Integer typeId, Integer parentId,
			Integer assistTypeId, PageDto<PriceDO> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("typeId", typeId);
		root.put("parentId", parentId);
		root.put("assistTypeId", assistTypeId);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList("price.queryPriceByTypeTwo",root);
	}

	@Override
	public Integer queryPriceByTypeCountTwo(Integer typeId, Integer parentId,
			Integer assistTypeId) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("typeId", typeId);
		root.put("parentId", parentId);
		root.put("assistTypeId", assistTypeId);
		return (Integer) getSqlMapClientTemplate().queryForObject("price.queryPriceByTypeCountTwo",root);
	}
}
