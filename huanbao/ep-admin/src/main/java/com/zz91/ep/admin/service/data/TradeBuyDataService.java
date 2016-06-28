package com.zz91.ep.admin.service.data;

import java.util.Map;

public interface TradeBuyDataService {

	public Integer updateBuyByKeywords(String keywords,String account);
	
	public Map<String, Object> queryBuyIdByKeywords(Integer start,Integer limit,String keywords);
	
}
