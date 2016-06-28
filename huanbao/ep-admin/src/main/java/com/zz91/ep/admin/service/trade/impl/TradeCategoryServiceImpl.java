/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-16
 */
package com.zz91.ep.admin.service.trade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.trade.TradeCategoryDao;
import com.zz91.ep.admin.dao.trade.TradePropertyDao;
import com.zz91.ep.admin.service.trade.TradeCategoryService;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.util.Assert;

/**
 * @author totly
 *
 * created on 2011-9-16
 */
@Component("tradeCategoryService")
public class TradeCategoryServiceImpl implements TradeCategoryService {

    @Resource
    private TradeCategoryDao tradeCategoryDao;
    @Resource
    private TradePropertyDao tradePropertyDao;

//    @Override
//    public List<TradeCategory> queryCategoryByName(String name, Integer deep,
//            Integer max) {
//        if (max == null) {
//            max = MAX_SIZE; 
//         }
//        Assert.notNull(name, "the name can not be null");
//        Assert.notNull(deep, "the deep can not be null");
//        return tradeCategoryDao.queryCategoryByName(name, deep, max);
//    }

//    @Override
//    public List<TradeCategory> queryCategoryByParent(String parentCode,
//            Integer deep, Integer max) {
//        if (max == null) {
//            max = MAX_SIZE; 
//         }
//        Assert.notNull(parentCode, "the parentCode can not be null");
//        Assert.notNull(deep, "the deep can not be null");
//        return tradeCategoryDao.queryCategoryByParent(parentCode, deep, max);
//    }

//    @Override
//    public List<TradeCategory> queryCategoryByTags(String parentCode, String tags, Integer deep,
//            Integer max) {
//        if (max == null) {
//           max = MAX_SIZE; 
//        }
//        Assert.notNull(parentCode, "the parentCode can not be null");
//        Assert.notNull(tags, "the tags can not be null");
//        Assert.notNull(deep, "the deep can not be null");
//        return tradeCategoryDao.queryCategoryByTags(parentCode, tags, deep, max);
//    }

    @Override
    public List<TradeCategory> queryTradeSupplyAll() {
        return tradeCategoryDao.queryTradeSupplyAll();
    }

//    @Override
//    public Integer queryIsLeafByCode(String code) {
//        Assert.notNull(code, "the code can not be null");
//        return tradeCategoryDao.queryIsLeafByCode(code);
//    }

	@Override
	public Integer createTradeCategory(TradeCategory tradeCategory,
			String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		if(tradeCategory.getShowIndex()==null || tradeCategory.getShowIndex()==0) {
			tradeCategory.setShowIndex((short)0);
		}
		tradeCategory.setLeaf((short)1);
		//更新父类的leaf属性
		tradeCategoryDao.updateParentLeafByParentCode(parentCode);
		String code=tradeCategoryDao.queryMaxCodeOfChild(parentCode);
		if(code!=null && code.length()>0){
			code = code.substring(parentCode.length());
			Integer codeInt=Integer.valueOf(code);
			codeInt++;
			tradeCategory.setCode(parentCode+String.valueOf(codeInt));
		}else{
			tradeCategory.setCode(parentCode+"1000");
		}
		return tradeCategoryDao.insertTradeCategory(tradeCategory);
	}

	@Override
	public Integer deleteTradeCategory(String code) {
		Assert.notNull(code, "the code can not be null");
		tradePropertyDao.deleteTradePropertyByCode(code);
		return tradeCategoryDao.deleteTradeCategory(code);
	}

	@Override
	public TradeCategory queryCategoryByCode(String code) {
		if(code==null){
			return null;
		}
		return tradeCategoryDao.queryCategoryByCode(code);
	}

	@Override
	public List<ExtTreeDto> queryTradeCategoryNode(String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		
		List<TradeCategory> list=tradeCategoryDao.queryChild(parentCode);
		List<ExtTreeDto> nodeList=new ArrayList<ExtTreeDto>();
		for(TradeCategory tradeCategory:list){
			ExtTreeDto node=new ExtTreeDto();
			node.setId(String.valueOf(tradeCategory.getId()));
			node.setText(tradeCategory.getName());
			node.setData(tradeCategory.getCode());
			Integer i = tradeCategoryDao.countChild(tradeCategory.getCode());
			if(i==null || i.intValue()<=0){
				node.setLeaf(true);
			}
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public Integer updateTradeCategory(TradeCategory tradeCategory) {
		Assert.notNull(tradeCategory.getCode(), "the code can not be null");
		if(tradeCategory.getShowIndex()==null || tradeCategory.getShowIndex()==0) {
			tradeCategory.setShowIndex((short)0);
		}
		return tradeCategoryDao.updateTradeCategory(tradeCategory);
	}

	@Override
	public List<ExtTreeDto> queryTradeSupplyCategoryNode(String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		
		List<TradeCategory> list=tradeCategoryDao.querySupplyChild(parentCode);
		List<ExtTreeDto> nodeList=new ArrayList<ExtTreeDto>();
		for(TradeCategory tradeCategory:list){
			ExtTreeDto node=new ExtTreeDto();
			node.setId(String.valueOf(tradeCategory.getId()));
			node.setText(tradeCategory.getName());
			node.setData(tradeCategory.getCode());
			Integer i = tradeCategoryDao.countSupplyChild(tradeCategory.getCode());
			if(i==null || i.intValue()<=0){
				node.setLeaf(true);
			}
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public List<ExtTreeDto> queryTradeBuyCategoryNode(String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		
		List<TradeCategory> list=tradeCategoryDao.queryBuyChild(parentCode);
		List<ExtTreeDto> nodeList=new ArrayList<ExtTreeDto>();
		for(TradeCategory tradeCategory:list){
			ExtTreeDto node=new ExtTreeDto();
			node.setId(String.valueOf(tradeCategory.getId()));
			node.setText(tradeCategory.getName());
			node.setData(tradeCategory.getCode());
			Integer i = tradeCategoryDao.countBuyChild(tradeCategory.getCode());
			if(i==null || i.intValue()<=0){
				node.setLeaf(true);
			}
			nodeList.add(node);
		}
		return nodeList;
	}

//	@Override
//	public String buildCategory(String categoryCode) {
//		String resultString = "";
//		if (StringUtils.isNotEmpty(categoryCode) && categoryCode.length() > 0) {
//			for (int i = 1;i < categoryCode.length()/4; i++) {
//				String code = categoryCode.substring(0,i*4+4);
//				if (!"".equals(resultString)) {
//					resultString = resultString + "&gt;";
//				}
//				resultString = resultString + CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, code);
//			}
//		}
//		return resultString;
//	}
	
//	@Override
//	public String buildCategoryUrl(String categoryCode,String urlpre,String urlpost) {
//		String resultString = "";
//		if (StringUtils.isNotEmpty(categoryCode) && categoryCode.length() > 0) {
//			for (int i = 0;i < categoryCode.length()/4; i++) {
//				String code = categoryCode.substring(0,i*4+4);
//				if (!"".equals(resultString)) {
//					resultString = resultString + " &gt; ";
//				}
//				resultString = resultString + "<a href='"+urlpre+code+urlpost+"'>"+CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, code)+"</a>";
//			}
//		}
//		return resultString;
//	}

	@Override
	public Integer queryCountByNameOrTags(String name, String tags) {
		return tradeCategoryDao.queryCountByNameOrTags(name,tags);
	}
}