//package com.zz91.ep.admin.service.data.impl;
//
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
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.zz91.ep.admin.dao.comp.CompProfileDao;
//import com.zz91.ep.admin.service.data.CompProfileDataService;
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
//@Service("compProfileDataService")
//public class CompProfileDataServiceImpl implements CompProfileDataService{
//	@Resource
//    private CompProfileDao compProfileDao;
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public Integer updateCompByKeywords(String keywords){
//		Map<String, Object> map = null;
//		Integer exeCount=0;
//		//一次处理条数
//		Integer start=0;
//		Integer limit=100;
//		do {
//			map=queryCompanyIdByKeywords(start,limit,keywords);
//			
//			start = start + limit ;
//			
//			List<Integer> idList =(List<Integer>)map.get("idList");
//			for(Integer id : idList){
//				if(id<50000000){
//					if(compProfileDao.updateMeberCodeBlockById(id, "10001002")>0){
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
//	public Map<String, Object> queryCompanyIdByKeywords(Integer start,Integer limit,String keywords){
//		
//		SolrQuery query = new SolrQuery();
//		//关键字不为空时，有高亮显示
//		if (StringUtils.isNotEmpty(keywords)) {
//			keywords =buildKeyWord(keywords);
//			if(StringUtils.isEmpty(keywords)){
//				return null;
//			}
//			query.setQuery(keywords);
//		} else {
//			query.setQuery("*:*");
//		}
//		
//		query.setStart(start);
//		query.setRows(limit);
//		
//		final Map<String, Object> map = new HashMap<String, Object>();
//		
//		try {
//			SolrQueryUtil.getInstance().search("hbcompany", query, new SolrReadHandler() {
//				
//				@Override
//				public void handlerReader(QueryResponse response)
//						throws SolrServerException {
//					map.put("totals", (int)response.getResults().getNumFound());
//					List<SolrDocument> list=response.getResults();
//					List<Integer> idList= new ArrayList<Integer>();
//					for (SolrDocument doc : list) {
//						idList.add(Integer.valueOf(""+doc.getFieldValue("id")));
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
