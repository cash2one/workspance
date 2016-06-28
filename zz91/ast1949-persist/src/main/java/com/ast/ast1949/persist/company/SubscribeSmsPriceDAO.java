package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.SubscribeSmsPriceDO;

public interface SubscribeSmsPriceDAO {
	
	public Integer addSubscribeSMS(Integer companyId,String categoryCode,Integer areaNodeId);
	
	public List<SubscribeSmsPriceDO> querySubscribeSMS(Integer companyId);
	
	public Integer deleteSubscribeSMS(String categoryCode,Integer companyId);
	
	public Integer countSubscribeSmsByCategoryCode(String categoryCode,Integer companyId);
	
	public String selectSubscribeSmsForList(Integer companyId);
	
	public Integer countSubscribeSms(Integer companyId);
	
	public Integer deleteSubscribeSMSPrice(Integer id,Integer companyId);
	
	public Integer deleteSubscribeSMSByArea(String categoryCode,Integer areaNodeId,Integer companyId);
	
}
