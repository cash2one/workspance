/**
 * @author shiqp
 * @date 2015-03-07
 */
package com.ast.ast1949.persist.market;

import java.util.List;

import com.ast.ast1949.domain.market.MarketPic;

public interface MarketPicDao {
	public List<MarketPic> queryPicByMarketId(Integer marketId);
	
	public Integer insert(MarketPic marketPic);
	
	public Integer batchDelMarketPicById(Integer[] entities);
	
	public Integer delMarketPicById(Integer id,Integer checkStatus);
	
	public Integer updateMarketDefaultPic(Integer marketId,String isDefault);
	
	public Integer updateMarketDefaultPicById(Integer id,String isDefault);
	
	public void updateMarketIdById(Integer marketId, Integer id);
	
	public  MarketPic queryPicInfoByMarketId(Integer marketId);
	
	public void updateDefaultById(Integer id , String isDefault);
}
