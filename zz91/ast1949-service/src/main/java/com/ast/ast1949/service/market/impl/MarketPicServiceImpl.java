/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.service.market.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.market.MarketPic;
import com.ast.ast1949.persist.market.MarketPicDao;
import com.ast.ast1949.service.market.MarketPicService;
import com.ast.ast1949.util.Assert;
import com.zz91.util.lang.StringUtils;

@Component("marketPicService")
public class MarketPicServiceImpl implements MarketPicService {
	@Resource
	private MarketPicDao marketPicDao;
	
	@Override
	public List<MarketPic> queryPicByMarketId(Integer marketId) {
		List<MarketPic> list=new ArrayList<MarketPic>();
		if (marketId!=null&&marketId.intValue()>0) {
			list=marketPicDao.queryPicByMarketId(marketId);
		}
		return list;
	}

	@Override
	public Integer insert(MarketPic marketPic) {
		Integer i=0;
		if (marketPic!=null) {
			i= marketPicDao.insert(marketPic);
		}
		return i;
	}
	
	@Override
	public Integer batchDelMarketPicById(String ids) {
		Assert.notNull(ids, "id must not be null");
		return marketPicDao.batchDelMarketPicById(StringUtils.StringToIntegerArray(ids));
	}

	@Override
	public Integer updateMarketDefaultPic(Integer marketId, Integer id) {
			marketPicDao.updateMarketDefaultPic(marketId, "0");
			return marketPicDao.updateMarketDefaultPicById(id, "1");
	}

	@Override
	public void updateMarketIdById(Integer marketId, Integer id) {
		marketPicDao.updateMarketIdById(marketId, id);
	}

	@Override
	public void markDefaultPic(Integer marketId) {
		//最先上传的图片
		MarketPic pic = marketPicDao.queryPicInfoByMarketId(marketId);
		if(pic!=null){
			if(!"1".equals(pic.getIsDefault())){
				marketPicDao.updateDefaultById(pic.getId(), "1");
			}
		}
	}

	@Override
	public void delMarketPicById(Integer id,Integer checkStatus) {
		marketPicDao.delMarketPicById(id,checkStatus);
	}

}
