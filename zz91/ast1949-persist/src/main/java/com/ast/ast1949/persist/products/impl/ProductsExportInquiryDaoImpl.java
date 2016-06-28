package com.ast.ast1949.persist.products.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsExportInquiry;
import com.ast.ast1949.domain.yuanliao.YuanLiaoExportInquiry;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductsExportInquiryDao;
import com.zz91.util.datetime.DateUtil;

@Component("productsExportInquiryDao")
public class ProductsExportInquiryDaoImpl extends BaseDaoSupport implements ProductsExportInquiryDao {
	
	
	private final int DEFAULT_BATCH_SIZE = 100;
	private final String SQL_FIX = "productsExportInquiry";
	private final String SQL_FIX_YL = "yuanLiaoExportInquiry";

	@Override
	public Integer batchInsert(List<ProductsExportInquiry> list) {
		int impacted = 0;
		int batchNum = (list.size() + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > list.size() ? list.size(): endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					insert(list.get(i));
					impacted++;
				}
				getSqlMapClient().executeBatch();
			}
		} catch (SQLException e) {
		}
		return impacted;
	}
	
	@Override
	public Integer batchInsertYuanLiao(List<YuanLiaoExportInquiry> list){
		int impacted = 0;
		int batchNum = (list.size() + DEFAULT_BATCH_SIZE - 1) / DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > list.size() ? list.size(): endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					insertYuanLiao(list.get(i));
					impacted++;
				}
				getSqlMapClient().executeBatch();
			}
		} catch (SQLException e) {
		}
		return impacted;
	}
	
	private Integer insertYuanLiao(YuanLiaoExportInquiry yuanLiaoExportInquiry) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX_YL, "insertYuanLiao"), yuanLiaoExportInquiry);
	}

	public Integer insert(ProductsExportInquiry productsExportInquiry){
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), productsExportInquiry);
	}
	
	@Override
	public Integer countByProductId(Integer productId){
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByProductId"), productId);
	}

	@Override
	public ProductsExportInquiry queryByProductId(Integer productId) {
		return (ProductsExportInquiry) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByProductId"), productId);
	}

	@Override
	public Integer countByProductIdAndToCompId(Integer productId,Integer toCompanyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("toCompanyId", toCompanyId);
		Date date = new Date();
		map.put("from",DateUtil.toString(date, "yyyy-MM-01"));
		map.put("to", DateUtil.toString(DateUtil.getDateAfterMonths(date, 1), "yyyy-MM-01"));
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByProductIdAndToCompId"), map);
	}
	
	@Override
	public Integer countByCompanyId(Integer toCompanyId){
		Map<String, Object > map  =new HashMap<String, Object>();
		map.put("toCompanyId", toCompanyId);
		Date date = new Date();
		map.put("from", DateUtil.toString(date, "yyyy-MM-dd"));
		map.put("to", DateUtil.toString(DateUtil.getDateAfterDays(date, 1), "yyyy-MM-dd"));
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countByCompanyId"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductsExportInquiry> queryList(Integer productId,Integer companyId,PageDto<ProductsDto> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("companyId", companyId);
		map.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryList"), map);
	}
	
	@Override
	public Integer countList(Integer productId,Integer companyId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("companyId", companyId);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "countList"), map);
	}

}
