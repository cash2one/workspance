/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-31 下午04:34:40
 */
package com.ast.ast1949.service.company.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.SubscribeDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.domain.site.MemberRuleDO;
import com.ast.ast1949.dto.company.SubscribeDTO;
import com.ast.ast1949.dto.company.SubscribeForMyrcDTO;
import com.ast.ast1949.persist.company.SubscribeDAO;
import com.ast.ast1949.persist.price.PriceDAO;
import com.ast.ast1949.persist.site.MemberRuleDAO;
import com.ast.ast1949.service.company.SubscribeService;
import com.ast.ast1949.util.Assert;

/**
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
@Component("subscribeService")
public class SubscribeServiceImpl implements SubscribeService {
  
	@Autowired
	private SubscribeDAO subscribeDAO;
	@Autowired
	private PriceDAO priceDAO;
	@Autowired
	MemberRuleDAO memberRuleDAO;
	
	public Integer batchDeleteSubscribeById(int[] entities) {
		Assert.notNull(entities, "entities is not null");
		return subscribeDAO.deleteSubscribeById(entities);
	}

	public Integer insertSubscribe(SubscribeDO subscribeDO) {
		return subscribeDAO.insertSubscribe(subscribeDO);
	}

	public List<SubscribeDO> querySubscribeByCompanyIdAndSubscribeType(Integer companyId, String subscribeType) {
		return subscribeDAO.querySubscribeByCompanyIdAndSubscribeType(companyId, subscribeType);
	}
	
	public Integer deleteSubscribeByCompanyIdAndSubscribeType(Integer companyId, String subscribeType) {
		return subscribeDAO.deleteSubscribeByCompanyIdAndSubscribeType(companyId, subscribeType);
	}
	
	public SubscribeDO selectSubscribeById(Integer id) {
		return subscribeDAO.selectSubscribeById(id);
	}
   
	public List<SubscribeDTO> selectSubscribeByCondition(SubscribeDTO subscribeDto){
		return subscribeDAO.selectSubscribeByCondition(subscribeDto);
	}
	
	public Integer updateSubscribe(SubscribeDO subscribeDO) {
		return subscribeDAO.updateSubscribe(subscribeDO);
	}
	
	public Integer selectCountSubscribeByCondition(SubscribeDTO subscribeDto){
		return subscribeDAO.selectCountSubscribeByCondition(subscribeDto);
	}
	
	public SubscribeDTO selectByIdSubscribe(Integer id){
		return subscribeDAO.selectByIdSubscribe(id);
	}
	
	public Integer updateByIdSubscribe(SubscribeDTO subscribeDto){
		return subscribeDAO.updateByIdSubscribe(subscribeDto);
	}

	public Integer deleteSubscribeById(Integer id) {
		return subscribeDAO.deleteSubscribeById(id);
	}

	public Integer batchResendSubscript(Integer[] ids) {
		Assert.notNull(ids, "the ids can not be null");
		int impact = 0;
		for(int i=ids.length; i>0; i--){
			SubscribeDO subscribe = subscribeDAO.selectSubscribeById(ids[i-1]);
			if(subscribe!=null){
				//send email
				//list productservice.queryProductList(subscribe.getKeyword(),100);
				//String s="";
				//for(obj:list){
				// s+="<a href='' target='_blank'>"+obj.getTitle()+"</a>"
				// s+="...."
				//}
//				Map<String, String> paramMap = ParamFacade.getInstance().getParamByType("email");
//				Email.getInstance().send(paramMap.get("service"), 
//						"", 
//						paramMap.get("service_password"), 
//						"", 
//						"");
				impact++;
			}
		}
		return impact;
	}

	@Override
	public Integer countSubscribeForMycrByCondition(SubscribeForMyrcDTO subscribeForMyrcDTO) {
		return subscribeDAO.countSubscribeForMycrByCondition(subscribeForMyrcDTO);
	}

//	@Override
//	public List<SubscribeForMyrcDTO> querySubscribeForMycrByCondition(
//			SubscribeForMyrcDTO subscribeForMyrcDTO) {
//		return subscribeDAO.querySubscribeForMycrByCondition(subscribeForMyrcDTO);
//	}

	@Override
	public List<SubscribeForMyrcDTO> querySubscribeForMyrc(SubscribeForMyrcDTO subscribeForMyrcDTO) {
		List<SubscribeForMyrcDTO> records = subscribeDAO.querySubscribeForMycrByCondition(subscribeForMyrcDTO);
		for (SubscribeForMyrcDTO sub : records) {
			PriceDO price = null;
			if(sub.getPriceTypeId()!=null){
				price = priceDAO.queryPriceForSubscribe(sub.getPriceTypeId(), sub.getPriceAssistTypeId());
			}
			if(price!=null) {
				sub.setPriceId(price.getId());
				sub.setPriceTitle(price.getTitle());
				sub.setPriceContent(price.getContent());
			}
		}
		return records;
	}

	@Override
	public SubscribeDO queryKeywordsByAccount(String account) {
		
		return subscribeDAO.queryKeywordsByAccount(account);
	}

	@Override
	public SubscribeDO querySubscribeById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return subscribeDAO.querySubscribeById(id);
	}
	
	final static String RULE_PRICE="subscribe_price_max";
	final static String RULE_PRODUCT ="subscribe_max";

	@Override
	public boolean allowSubscribeByMemberRule(
			Integer companyId, String memberShipCode, String subscribeType) {
		Assert.notNull(companyId, "the companyId can not be null");
		Assert.notNull(subscribeType, "the subscribeType can not be null");
		
		MemberRuleDO member=new MemberRuleDO();
		member.setMembershipCode(memberShipCode);
		if("1".equals(subscribeType)){
			member.setOperationCode(RULE_PRICE);
		}else{
			member.setOperationCode(RULE_PRODUCT);
		}
		do {
			List<MemberRuleDO> list=memberRuleDAO.queryMemberRuleByCondition(member);
			if(list==null){
				break;
			}
			if(list.size()<=0){
				break;
			}
			Integer max=Integer.valueOf(list.get(0).getResults());
			Integer numNow = subscribeDAO.querySubscribeByCompanyIdAndSubscribeTypeCount(companyId, subscribeType);
			if(numNow==null){
				break;
			}
			
			if(numNow.intValue()<max.intValue()){
				return true;
			}
		} while (false);
		
		return false;
	}
}
