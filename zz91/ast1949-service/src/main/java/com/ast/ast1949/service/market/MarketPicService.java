/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.service.market;

import java.util.List;

import com.ast.ast1949.domain.market.MarketPic;

public interface MarketPicService {
	public List<MarketPic> queryPicByMarketId(Integer marketId);
	
	public Integer insert(MarketPic marketPic);
	
	public Integer batchDelMarketPicById(String ids);
	
	public Integer updateMarketDefaultPic(Integer marketId,Integer id);
	
	public void updateMarketIdById(Integer marketId, Integer id);
	
	public void markDefaultPic(Integer marketId);
	
	public void delMarketPicById(Integer id,Integer checkStatus);

}
