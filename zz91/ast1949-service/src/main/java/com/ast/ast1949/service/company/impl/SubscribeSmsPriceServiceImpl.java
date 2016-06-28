package com.ast.ast1949.service.company.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpException;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.SubscribeSmsPriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.SubscribeSmsPriceDAO;
import com.ast.ast1949.service.company.SubscribeSmsPriceService;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.velocity.AddressTool;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Component("subscribeSmsPriceService")
public class SubscribeSmsPriceServiceImpl implements SubscribeSmsPriceService{
	
	@Resource
	private SubscribeSmsPriceDAO subscribeSmsPriceDAO;

	@Override
	public Integer addSubscribeSMS(Integer companyId, String categoryCode,
			Integer areaNodeId) {
		Integer i=null;
		do {
			Integer count=subscribeSmsPriceDAO.countSubscribeSms(companyId);
			if(count>=20){
				break;
			}else {
				i=subscribeSmsPriceDAO.addSubscribeSMS(companyId, categoryCode, areaNodeId);
			}
		} while (false);
		return i;
	}

	@Override
	public Integer deleteSubscribeSMS(String categoryCode, Integer companyId) {
		return subscribeSmsPriceDAO.deleteSubscribeSMS(categoryCode, companyId);
	}

	@Override
	public List<SubscribeSmsPriceDO> querySubscribeSMS(Integer companyId) {
		return subscribeSmsPriceDAO.querySubscribeSMS(companyId);
	}

	@Override
	public PageDto querySubscribeSMSPrice(
			Integer companyId, String categoryCode, String areaNodeId,Integer startIndex) throws HttpException, IOException {	
		if(companyId==null){
			return null;
		}
		if(areaNodeId==null){
			areaNodeId="";
		}
		if(categoryCode==null){
			categoryCode="";
		}
		PageDto page=new PageDto();
		String responseText=HttpUtils.getInstance().httpGet(AddressTool.getAddress("exadmin")+"/admin/sms/main/getPriceForZZ91Myrc.htm?categoryCode="+categoryCode+"&areaNodeId="+areaNodeId, HttpUtils.CHARSET_UTF8);
		String test="["+responseText+"]";
		JSONArray jobj = JSONArray.fromObject(test);
		page.setPageSize(20);
		page.setRecords(JSONArray.toList(jobj,new JsonConfig()));
		String responseTex=HttpUtils.getInstance().httpGet(AddressTool.getAddress("exadmin")+"/admin/sms/main/getPriceForZZ91MyrcCount.htm?categoryCode="+categoryCode+"&areaNodeId="+areaNodeId+"",HttpUtils.CHARSET_UTF8);
		String data=StringUtils.removeHTML(responseTex);
		page.setTotalRecords(Integer.parseInt(data));
		return page;
	}

	@Override
	public Integer countSubscribeSMS(String categoryCode, Integer companyId) {
		return subscribeSmsPriceDAO.countSubscribeSmsByCategoryCode(categoryCode, companyId);
	}

	@Override
	public String selectSubscribeSmsForList(Integer companyId) {
		return subscribeSmsPriceDAO.selectSubscribeSmsForList(companyId);
	}

	@Override
	public Integer deleteSubscribeSMSPrice(Integer id, Integer companyId) {
		
		return subscribeSmsPriceDAO.deleteSubscribeSMSPrice(id, companyId);
	}

	@Override
	public Integer deleteSubscribeSMSByArea(String categoryCode,
			Integer areaNodeId, Integer companyId) {
		return subscribeSmsPriceDAO.deleteSubscribeSMSByArea(categoryCode, areaNodeId, companyId);
	}

}
