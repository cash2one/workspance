/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-16
 */
package com.zz91.ep.admin.service.trade.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.trade.TradePropertyDao;
import com.zz91.ep.admin.service.trade.TradePropertyService;
import com.zz91.ep.domain.trade.TradeProperty;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradePropertyDto;
import com.zz91.util.Assert;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.tags.TagsUtils;

/**
 * @author totly
 * 
 *         created on 2011-9-16
 */
@Component("tradePropertyService")
public class TradePropertyServiceImpl implements TradePropertyService {

	@Resource
	private TradePropertyDao tradePropertyDao;

	@Override
	public List<TradeProperty> queryPropertyByCategoryCode(String categoryCode) {
		Assert.notNull(categoryCode, "the categoryCode can not be null");
		return tradePropertyDao.queryPropertyByCategoryCode(categoryCode);
	}

//	@Override
//	public List<SearchPropertyDto> querySearchPropertyByCategory(
//			String categoryCode) {
//		Assert.notNull(categoryCode, "the categoryCode can not be null");
//		List<TradeProperty> list = tradePropertyDao
//				.querySearchPropertyByCategory(categoryCode);
//		List<SearchPropertyDto> searchList = new ArrayList<SearchPropertyDto>();
//		for (int i = 0; i < list.size(); i++) {
//			SearchPropertyDto searchProperty = new SearchPropertyDto();
//			searchProperty.setId(list.get(i).getId());
//			searchProperty.setName(list.get(i).getName());
//			searchProperty.setSearchValue(list.get(i).getSearchValue().split(
//					","));
//			searchList.add(searchProperty);
//		}
//		return searchList;
//	}

	@Override
	public Integer createTradeProperty(TradeProperty tradeProperty) {
		//对vtypeValue做处理，取出中文逗号，并且对输入进行截取
		String vtypeValue = tradeProperty.getVtypeValue();
		
		//vtypeValue.replace('，', ',');
		vtypeValue=TagsUtils.getInstance().arrangeTags(vtypeValue);
		String[] vtypeValueArr = vtypeValue.split(",");
		//组装vtypeValue成JASON格式 [{k:"a",v:"a"},{k:"b",v:"b"}]
		StringBuffer vtypeValueJason = new StringBuffer();
		vtypeValueJason.append("[");
		for (int i = 0; i < vtypeValueArr.length; i++) {
			vtypeValueJason.append("{k:\"");
			vtypeValueJason.append(vtypeValueArr[i]);
			vtypeValueJason.append("\",v:\"");
			vtypeValueJason.append(vtypeValueArr[i]);
			vtypeValueJason.append("\"},");
		}
		vtypeValueJason.deleteCharAt(vtypeValueJason.length()-1);
		vtypeValueJason.append("]");
		tradeProperty.setVtypeValue(vtypeValueJason.toString());
		tradeProperty.setSearchValue(TagsUtils.getInstance().arrangeTags(tradeProperty.getSearchValue()));
		if (tradeProperty.getSearchable()==null || tradeProperty.getSearchable() != 1) {
			tradeProperty.setSearchable((short) 0);
		}
		if(tradeProperty.getInputRequired()==null || tradeProperty.getInputRequired()!=1) {
			tradeProperty.setInputRequired((short)0);
		}
		String code = tradePropertyDao.queryMaxCode();
		if (code != null && code.length() != 0) {
			tradeProperty.setCode((Integer.parseInt(code) + 1) + "");
		} else {
			tradeProperty.setCode("10000001");
		}
		return tradePropertyDao.insertTradeProperty(tradeProperty);
	}

	@Override
	public Integer deleteTradePropertyById(Integer id) {
		// TODO Auto-generated method stub
		return tradePropertyDao.deleteTradePropertyById(id);
	}

	@Override
	public PageDto<TradePropertyDto> pageTradeProperty(String categoryCode,
			PageDto<TradePropertyDto> page) {
		TradePropertyDto dto = new TradePropertyDto();
		TradeProperty tradeProperty = new TradeProperty();

		tradeProperty.setCategoryCode(categoryCode);

		dto.setTradeProperty(tradeProperty);

		List<TradePropertyDto> list = tradePropertyDao
				.queryPropertys(dto, page);
		for (TradePropertyDto obj : list) {
			obj.setCategoryName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_TRADE, obj.getTradeProperty()
							.getCategoryCode()));
		}
		page.setRecords(list);
		page.setTotals(tradePropertyDao.queryPropertysCount(dto));

		return page;
	}

	@Override
	public TradeProperty queryTradeCategoryById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return tradePropertyDao.queryTradePropertyById(id);
	}

	@Override
	public Integer updateTradeProperty(TradeProperty tradeProperty) {
		tradeProperty.setGmtModified(new Date());
		tradeProperty.setSearchValue(TagsUtils.getInstance().arrangeTags(tradeProperty.getSearchValue()));
		if (tradeProperty.getSearchable()==null || tradeProperty.getSearchable() != 1) {
			tradeProperty.setSearchable((short) 0);
		}
		if (tradeProperty.getInputRequired()==null || tradeProperty.getInputRequired() != 1) {
			tradeProperty.setInputRequired((short) 0);
		}
		return tradePropertyDao.updateTradeProperty(tradeProperty);
	}
}