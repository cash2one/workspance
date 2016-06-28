//package com.zz91.ep.admin.service.data.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.annotation.Resource;
//
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocument;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.zz91.ep.admin.dao.trade.TradeBuyDao;
//import com.zz91.ep.admin.service.data.TradeBuyDataService;
//import com.zz91.util.lang.StringUtils;
//import com.zz91.util.search.solr.SolrQueryUtil;
//import com.zz91.util.search.solr.SolrReadHandler;
//
///**
// * @author 黄怀清
// *
// * created on 2012-9-11
// */
//@Transactional
//@Service("tradeBuyDataServiceImpl")
//public class TradeBuyDataServiceImpl implements TradeBuyDataService{
//
//	public static String CHECK_REFUSE_DEFAULT="对不起，您的信息被识别为垃圾信息，系统已拒绝了您的信息，如有问题请联系我们客服处理";
//	@Resource
//	private TradeBuyDao tradeBuyDao;
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public Integer updateBuyByKeywords(String keywords,String account){
//		Map<String, Object> map =null;
//		Integer exeCount=0;
//		//一次处理条数
//		Integer start=0;
//		Integer limit=100;
//		do {
//			map=queryBuyIdByKeywords(start,limit,keywords);
//			
//			start = start + limit;
//			
//			List<Integer> idList = (List<Integer>) map.get("idList");
//			for(Integer id : idList){
//				if(id<10000000){
//					if(tradeBuyDao.updateBuyCheckStatus(id, 2,account,CHECK_REFUSE_DEFAULT)>0){
//						exeCount++;
//					}
//				}
//			}
//		} while (start+limit<(Integer)map.get("totals"));
//		
//		return exeCount;
//	}
//	
//	@Override
//	public Map<String, Object> queryBuyIdByKeywords(Integer start,Integer limit,String keywords){
//		SolrQuery query = new SolrQuery();
//		
//		if (StringUtils.isNotEmpty(keywords)) {
//			keywords =buildKeyWord(keywords);
//			if(StringUtils.isEmpty(keywords)){
//				return null;
//			}
//			query.setQuery(keywords);
//
//		} else {
//			query.setQuery("*:*");
//		}
//		
//		query.setStart(start);
//		query.setRows(limit);
//		final Map<String, Object> map = new HashMap<String, Object>();
//	
//		try {
//			SolrQueryUtil.getInstance().search("hbtradebuy", query, new SolrReadHandler() {
//				
//				@Override
//				public void handlerReader(QueryResponse response)
//						throws SolrServerException {
//					map.put("totals",(int)response.getResults().getNumFound());
//					List<SolrDocument> list = response.getResults();
//					List<Integer> idList = new ArrayList<Integer>();
//					for (SolrDocument doc : list) {
//						idList.add(Integer.valueOf(""+ doc.getFieldValue("id")));
//					}
//					map.put("idList", idList);
//				}
//			});
//		} catch (SolrServerException e) {
//		}
//		return map;
//	}
//	
//	private String buildKeyWord(String keywords){
//		//`~!@#$%^&*()+=|{}':;',\\[\\].<>/?
//		String regEx="[~!@#$%^&*()+=|{}':;',\\[\\].<>/?]";
//		Pattern   p   =   Pattern.compile(regEx);
//		Matcher   m   =   p.matcher(keywords);
//		return   m.replaceAll("").trim();
//	}
//}
