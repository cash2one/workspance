/*
 * 文件名称：TradeCategoryServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.trade.TradeCategoryDao;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.domain.trade.TradeProperty;
import com.zz91.ep.dto.search.SearchPropertyDto;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.util.Assert;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.lang.StringUtils;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：交易中心供求信息类别实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("tradeCategoryService")
public class TradeCategoryServiceImpl implements TradeCategoryService {

    @Resource
    private TradeCategoryDao tradeCategoryDao;
    
    private final static String TOP_SUPPLY="1000";
	
	public List<TradeCategory> queryCategoryByParent(String parentCode, Integer deep, Integer max) {
		Assert.notNull(parentCode, "the parentCode can not be null");
        Assert.notNull(deep, "the deep can not be null");
		return tradeCategoryDao.queryCategoryByParent(parentCode, deep, max);
	}

	@Override
	public List<String> queryTagsByCode(String categoryCode) {
		String tags = tradeCategoryDao.queryTagsByCode(categoryCode);
    	if (tags != null && StringUtils.isNotEmpty(tags)) {
    		List<String> tagsList = new ArrayList<String>();
    		String[] tagsArr = tags.split(",");
    		for (int j = 0; j < tagsArr.length; j++) {
    			tagsList.add(tagsArr[j]);
			}
    		return tagsList;
		}
		return null;
	}

	@Override
	public String buildCategoryUrl(String categoryCode, String urlpre, String urlpost) {
		String resultString = "";
		if (StringUtils.isNotEmpty(categoryCode) && categoryCode.length() > 0) {
			for (int i = 0;i < categoryCode.length()/4; i++) {
				String code = categoryCode.substring(0,i*4+4);
				TradeCategory tc=tradeCategoryDao.getCategoryByCode(code);
				if (!"".equals(resultString)) {
					resultString = resultString + " &gt; ";
				}
				resultString = resultString + "<a href=\""+urlpre+tc.getCode()+urlpost+"\">"+CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, code)+"</a>";
			}
		}
		return resultString;
	}

	@Override
	public List<TradeCategory> queryCategoryByTags(String parentCode,
			String tags, Integer deep, Integer max) {
        Assert.notNull(parentCode, "the parentCode can not be null");
        Assert.notNull(tags, "the tags can not be null");
        Assert.notNull(deep, "the deep can not be null");
        return tradeCategoryDao.queryCategoryByTags(parentCode, tags, deep, max);
	}
	
	@Override
	public String queryCodeById(Integer id) {
		return tradeCategoryDao.queryCodeById(id);
	}

	@Override
	public Integer queryIsLeafByCode(String code) {
		Assert.notNull(code, "the code can not be null");
        return tradeCategoryDao.queryIsLeafByCode(code);
	}

	@Override
	public List<SearchPropertyDto> querySearchPropertyByCategory(String categoryCode) {
		Assert.notNull(categoryCode, "the categoryCode can not be null");
		List<TradeProperty> list = tradeCategoryDao.querySearchPropertyByCategory(categoryCode);
		List<SearchPropertyDto> searchList = new ArrayList<SearchPropertyDto>();
		for (int i = 0; i < list.size(); i++) {
			SearchPropertyDto searchProperty = new SearchPropertyDto();
			searchProperty.setId(list.get(i).getId());
			searchProperty.setName(list.get(i).getName());
			searchProperty.setSearchValue(list.get(i).getSearchValue().split(","));
			searchList.add(searchProperty);
		}
		return searchList;
	}

	@Override
	public TradeCategory queryTagsById(Integer id) {
		return tradeCategoryDao.queryTagsById(id);
		
	}

	@Override
	public String buildCategory(String categoryCode) {
		String resultString = "";
		if (StringUtils.isNotEmpty(categoryCode) && categoryCode.length() > 0) {
			for (int i = 1;i < categoryCode.length()/4; i++) {
				String code = categoryCode.substring(0,i*4+4);
				if (!"".equals(resultString)) {
					resultString = resultString + "&gt;";
				}
				resultString = resultString + CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, code);
			}
		}
		return resultString;
	}
	@Override
	public TradeCategory getCategoryByCode(String code){
		return tradeCategoryDao.getCategoryByCode(code);
	}

	@Override
	public String queryNameByCode(String code) {
		
		return tradeCategoryDao.queryNameByCode(code);
	}

	@Override
	public List<TradeCategory> queryBroCategoryByCode(String code, Integer size) {
		
		return tradeCategoryDao.queryBroCategoryByCode(code, size);
	}

	@Override
	public void initSupplySearchTbar(String keywords, String categoryCode,
			Map<String, Object> out) {
		
		List<TradeCategory> categoryList = null;
		
		if (StringUtils.isNotEmpty(keywords) && TOP_SUPPLY.equals(categoryCode)) {
			// 查询包含关键字的所有类别
			categoryList = queryCategoryByTags(categoryCode, keywords, 0, 0);
		} else {
			Integer leaf = tradeCategoryDao.queryIsLeafByCode(categoryCode);

			if (leaf != null && leaf == 1) {
				List<SearchPropertyDto> searchProperty = querySearchPropertyByCategory(categoryCode);
				out.put("searchProperty", searchProperty);
				// 是叶子节点，查询该节点同级所有叶子节点
				categoryList = tradeCategoryDao.queryCategoryByParent(
						categoryCode.substring(0, categoryCode.length() - 4),
						1, 0);
			} else {
				// 不是叶子节点，查询该节点下的所有叶子节点
				categoryList = tradeCategoryDao.queryCategoryByParent(
						categoryCode, TOP_SUPPLY.equals(categoryCode) ? 0 : 1,
						0);
			}
		}

		out.put("searchCategory", categoryList);
		String parentCode = categoryCode.length() > 8 ? categoryCode.substring(
				0, 8) : categoryCode;
		out.put("parentCode", parentCode);
	}

	@Override
	public List<TradeCategory> buildCategoryPath(String categoryCode) {
		
		List<TradeCategory> list = new ArrayList<TradeCategory>();
		int ln=categoryCode.length();
		for(int i=4;i<=ln;i=i+4){
			list.add(tradeCategoryDao.getCategoryByCode(categoryCode.substring(0, i)));
		}
		
		return list;
	}
	
}