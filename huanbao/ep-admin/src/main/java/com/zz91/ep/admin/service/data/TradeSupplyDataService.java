package com.zz91.ep.admin.service.data;

import java.util.Map;

public interface TradeSupplyDataService {
	
	public Integer updateSupplyByKeywords(String keywords,String account);
	
	public Map<String,Object> querySupplyIdByKeywords(Integer start,Integer limit,String keywords);
}
