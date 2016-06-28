/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-24
 */
package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.PaginationResult;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.company.CompanyPriceDTO;
import com.ast.ast1949.dto.company.CompanyPriceDtoForMyrc;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CompanyPriceDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
@Component("CompanyPriceDAO")
public class CompanyPriceDAOImpl extends BaseDaoSupport implements CompanyPriceDAO {

	private final static String sqlPreFix = "companyPrice";
	final private int DEFAULT_BATCH_SIZE = 20;

	public Integer batchDeleteCompanyPriceById(Integer[] entities) {
		Assert.notNull(entities, "entities code can not be null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate().update("companyPrice.deleteCompanyPriceById", entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch check friend links failed.", e);
		}
		return impacted;

	}

	public Integer insertCompanyPrice(CompanyPriceDO companyPriceDO) {
		Assert.notNull(companyPriceDO, "companyPriceDO is not null");
		if(companyPriceDO.getMinPrice()==null){
			companyPriceDO.setMinPrice("");
		}
		if(companyPriceDO.getMaxPrice()==null){
			companyPriceDO.setMaxPrice("");
		}
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(sqlPreFix, "insertCompanyPrice"), companyPriceDO);
	}

	public Integer updateCompanyPrice(CompanyPriceDO companyPriceDO) {
		Assert.notNull(companyPriceDO, "companyPriceDO is not null");
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateCompanyPrice"), companyPriceDO);
	}
			
	public CompanyPriceDO queryCompanyPriceById(Integer id) {
		Assert.notNull(id, "id is not null");
		return (CompanyPriceDO) getSqlMapClientTemplate().queryForObject("companyPrice.queryCompanyPriceById", id);
	}

//	@SuppressWarnings("unchecked")
//	public List<CompanyPriceDTO> queryCompanyPriceByCondition(CompanyPriceDTO companyPriceDTO) {
//		Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
//		return getSqlMapClientTemplate().queryForList("companyPrice.queryCompanyPriceByCondition", companyPriceDTO);
//	}

	@SuppressWarnings("unchecked")
	public List<CompanyPriceDTO> queryCompanyPriceForFront(CompanyPriceDTO companyPriceDTO) {
		Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryCompanyPriceForFront"), companyPriceDTO);
	}

	public Integer queryCompanyPriceRecordCount(CompanyPriceDTO companyPriceDTO) {
		Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
		return Integer.valueOf(getSqlMapClientTemplate().queryForObject("companyPrice.queryCompanyPriceRecordCount",
				companyPriceDTO).toString());
	}

	@SuppressWarnings("unchecked")
	public List<CompanyPriceDO> queryCompanyPriceByCompanyIdCount(Integer limitSize) {
		Assert.notNull(limitSize, "limitSize is not null");
		return getSqlMapClientTemplate().queryForList("companyPrice.queryCompanyPriceByCompanyIdCount", limitSize);
	}

//	public Integer queryMyCompanyPriceRecordCount(CompanyPriceDTO companyPriceDTO) {
//		Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
//		return Integer.valueOf(getSqlMapClientTemplate().queryForObject(
//				"companyPrice.queryMyCompanyPriceRecordCount", companyPriceDTO).toString());
//	}

	@SuppressWarnings("unchecked")
	public List<CompanyPriceDO> queryCompanyPriceByCompanyId(Map<String, Object> param) {
		Assert.notNull(param, "param is not null");
		return getSqlMapClientTemplate().queryForList("companyPrice.queryCompanyPriceByCompanyId", param);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageDto<CompanyPriceDtoForMyrc> queryCompanyPriceListByCompanyId(Integer companyId,
			PageDto page) {
		CompanyPriceDtoForMyrc dto=new CompanyPriceDtoForMyrc();
		dto.setCompanyId(companyId);
		dto.setSqlKey(addSqlKeyPreFix(sqlPreFix, "queryCompanyPriceListByCompanyId"));
		PaginationResult paginationResult=queryPaginationData(dto, page);
		page.setTotalRecords(paginationResult.getResultTotal());
		page.setRecords(paginationResult.getResultList());
		return page;
	}

	@SuppressWarnings("unchecked")
	public List<CompanyPriceDO> queryCompanyPriceByRefreshTime(String title, Integer size) {

		Assert.notNull(size, "size is not null");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList("companyPrice.queryCompanyPriceByRefreshTime", map);
	}

	public Integer updateCompanyPriceCheckStatus(int[] entities, String isChecked) {
		Assert.notNull(entities, "entities is not null");
		Assert.notNull(isChecked, "isChecked is not null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length : endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", entities[i]);
					map.put("isChecked", isChecked);
					impacted += getSqlMapClientTemplate().update("companyPrice.updateCompanyPriceCheckStatus", map);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch check friend links failed.", e);
		}
		return impacted;

	}

	public CompanyPriceDTO selectCompanyPriceById(Integer id) {
		Assert.notNull(id, "id is not null");
		return (CompanyPriceDTO) getSqlMapClientTemplate().queryForObject("companyPrice.selectCompanyPriceById", id);
	}

	@Override
	public Integer insertCompanyPriceByAdmin(CompanyPriceDO companyPrice) {
		if(companyPrice.getMinPrice()==null){
			companyPrice.setMinPrice("");
		}
		if(companyPrice.getMaxPrice()==null){
			companyPrice.setMaxPrice("");
		}
		return (Integer) getSqlMapClientTemplate().insert("companyPrice.insertCompanyPriceByAdmin", companyPrice);
	}

	@Override
	public CompanyPriceDO queryCompanyPriceByProductId(Integer productId,String code) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("productId", productId);
		map.put("code", code);
		return (CompanyPriceDO) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix,
				"queryCompanyPriceByProductId"), map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageDto<CompanyPriceDTO> queryCompanyPricePagiationList(CompanyPriceDTO companyPriceDTO, PageDto pager) {
		companyPriceDTO.setSqlKey(addSqlKeyPreFix(sqlPreFix, "queryCompanyPricePagiationList"));
		PaginationResult result=queryPaginationData(companyPriceDTO, pager);
		pager.setTotalRecords(result.getResultTotal());
		pager.setRecords(result.getResultList());
		return pager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyPriceSearchDTO> queryCompanyPriceSearchByFront(CompanyPriceSearchDTO dto, PageDto page) {
			Map<String, Object> root =new HashMap<String, Object>();
			root.put("dto", dto);
			root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryCompanyPriceSearchByFront"),root);
	}

	@Override
	public Integer queryCompanypriceCount(CompanyPriceSearchDTO dto) {
		Map<String, Object> root =new HashMap<String, Object>();
		root.put("dto", dto);
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix,"queryCompanypriceCount"), root);
	}

	@Override
	public Integer queryCompanyPriceCountForAdmin(String title,String isChecked,String categoryCode,String isVip,CompanyPriceSearchDTO searchDto) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("title", title);
		root.put("isChecked", isChecked);
		root.put("categoryCode", categoryCode);
		root.put("isVip", isVip);
		root.put("searchDto", searchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "queryCompanyPriceCountForAdmin"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyPriceDO> queryCompanyPriceForAdmin(String title,String isChecked,String categoryCode,String isVip,CompanyPriceSearchDTO searchDto, PageDto<CompanyPriceDTO> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("title", title);
		root.put("isChecked", isChecked);
		root.put("categoryCode", categoryCode);
		root.put("isVip", isVip);
		root.put("searchDto", searchDto);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryCompanyPriceForAdmin"), root);
	}

	@Override
	public Integer updateCategoryCode(Integer id, String categoryCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("categoryCode", categoryCode);
		root.put("id", id);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateCategoryCode"), root);
	}

	@Override
	public Integer updateCompanyPriceByAdmin(CompanyPriceDO companyPriceDO) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateCompanyPriceByAdmin"), companyPriceDO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyPriceDO> queryNewestVipCompPrice(String code,
			Integer size) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("categoryCode", code);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix,"queryNewestVipCompPrice"), map);
	}

	@Override
	public Integer refreshCompanyPriceByProductId(Integer productId) {
		return (Integer) getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix,"refreshCompanyPriceByProductId"), productId);
	}

	@Override
	public Integer updateCompanyPriceCheckStatusByProductId(Integer productId,String isChecked){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("isChecked", isChecked);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(sqlPreFix, "updateCompanyPriceCheckStatusByProductId"),map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyPriceSearchDTO> queryCompanyPriceList(Integer size) {
			Map<String, Object> root =new HashMap<String, Object>();
			root.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryCompanyPriceList"),root);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> companyPrice(PageDto<CompanyDto> page){
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "companyPrice"),root);
	}
	
	@Override
	public Integer companyPriceCount(){
	     return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(sqlPreFix, "companyPriceCount"));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyPriceDO> queryCompanyPriceByCondition(String categoryCompanyPriceCode,Integer companyId, Integer size) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("categoryCompanyPriceCode", categoryCompanyPriceCode);
		map.put("length", categoryCompanyPriceCode.length());
		map.put("companyId", companyId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryCompanyPriceByCondition"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyPriceDO> queryByCode(String code,Integer size) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(code!=null){
		map.put("code", code+"%");
		}
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(sqlPreFix, "queryByCode"), map);
	}
}
