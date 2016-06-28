/*
 * 文件名称：SolrServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.common.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.GroupParams;
import org.springframework.stereotype.Service;

import com.zz91.ep.dao.comp.CompNewsDao;
import com.zz91.ep.dao.comp.CompProfileDao;
import com.zz91.ep.dao.exhibit.ExhibitDao;
import com.zz91.ep.dao.news.NewsDao;
import com.zz91.ep.dao.trade.SubnetCategoryDao;
import com.zz91.ep.dao.trade.TradeBuyDao;
import com.zz91.ep.dao.trade.TradeCategoryDao;
import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.domain.news.NewsNormDto;
import com.zz91.ep.domain.trade.SubnetCategory;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.ResultSolrDto;
import com.zz91.ep.dto.comp.CompNewsDto;
import com.zz91.ep.dto.comp.CompanyNormDto;
import com.zz91.ep.dto.exhibit.ExhibitNormDto;
import com.zz91.ep.dto.search.SearchBuyDto;
import com.zz91.ep.dto.search.SearchCompanyDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.TradeBuyNormDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.service.common.SolrService;
import com.zz91.ep.util.AreaUtil;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;
import com.zz91.util.search.solr.SolrQueryUtil;
import com.zz91.util.search.solr.SolrReadHandler;

/**
 * 项目名称：中国环保网 模块编号：数据操作Service层 模块描述：系统参数信息相关数据操作实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
 * 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("solrService")
public class SolrServiceImpl implements SolrService {
	final static Integer ONE_HUNDRED_THOUSAND_LIMIT = 1000000;

	@Resource
	private CompProfileDao compProfileDao;

	@Resource
	private TradeBuyDao tradeBuyDao;

	@Resource
	private NewsDao newsDao;

	@Resource
	private ExhibitDao exhibitDao;

	@Resource
	private TradeCategoryDao tradeCategoryDao;

	@Resource
	private SubnetCategoryDao subnetCategoryDao;
    
	@Resource
	private CompNewsDao compNewsDao;
	
//	@Override
//	public List<News> queryNewByKeywords(String keyword, Integer size) {
//		SolrQuery query = new SolrQuery();
//		if (StringUtils.isNotEmpty(keyword)) {
//			query.setQuery("kw:" + keyword);
//		} else {
//			query.setQuery("*:*");
//		}
//		query.addFilterQuery("pauseStatus:1");
//		query.addSortField("gmtPublish", ORDER.desc);
//		query.addSortField("score", ORDER.desc);
//		query.setStart(0);
//		if (size != null && size > 100) {
//			size = 100;
//		}
//		query.setRows(size);
//		final List<Integer> idList = new ArrayList<Integer>();
//		try {
//
//			SolrQueryUtil.getInstance().search("hbnews", query,
//					new SolrReadHandler() {
//						@Override
//						public void handlerReader(QueryResponse response)
//								throws SolrServerException {
//							List<SolrDocument> list = response.getResults();
//							for (SolrDocument doc : list) {
//								idList.add(Integer.valueOf(""
//										+ doc.getFieldValue("id")));
//							}
//						}
//					});
//		} catch (Exception e) {
//
//		}
//
//		List<News> resultList = new ArrayList<News>();
//
//		for (Integer id : idList) {
//			News news = newsDao.queryNewDetailsById(id);
//			if (news != null) {
//				resultList.add(news);
//			}
//		}
//
//		return resultList;
//	}
	@Override
	public PageDto<News> pageNewsForKeyworks(PageDto<News> page, String keywords) {
		StringBuffer sb=new StringBuffer();
		SphinxClient cl=SearchEngineUtils.getInstance().getClient();
		if(StringUtils.isEmpty(keywords)){
			return page;	
		}
		if(StringUtils.isNotEmpty(keywords)){
			keywords = keywords.replaceAll("/", "");
			keywords=keywords.replace("%","");
    		keywords=keywords.replace("\\","");
    		keywords=keywords.replace("-","");
    		keywords=keywords.replace("(","");
    		keywords=keywords.replace(")","");
			sb.append(keywords);
		}
		List<News> list=new ArrayList<News>();
		try {
			cl.SetMatchMode (SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(0,page.getLimit(),10);
			cl.SetConnectTimeout(120000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_publish desc");
			SphinxResult res = cl.Query(sb.toString(), "news_keyword");
			if(res==null){
				 return null;
			}
			for ( int i=0; i<res.matches.length; i++ ){
				SphinxMatch info = res.matches[i];
				News news=newsDao.queryNewDetailsById(Integer.valueOf(String.valueOf(""+info.docId)));
				if(news!=null){
					list.add(news);
				}
			}
		} catch (SphinxException e) {
			return null;
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public List<CompProfile> queryCompanyByKeyword(String keyword, Integer size) {

		SolrQuery query = new SolrQuery();
		if (StringUtils.isNotEmpty(keyword)) {
			query.setQuery("kw:" + keyword);
		} else {
			query.setQuery("*:*");
		}
		// query.addFilterQuery("mainBuy:0");
		query.addFilterQuery("mainSupply:1");
		query.addFilterQuery("memberCodeBlock: \"-1\"");
		query.addSortField("sortMemberCode", ORDER.desc);
		query.addSortField("score", ORDER.desc);
		query.setStart(0);
		if (size != null && size > 100) {
			size = 100;
		}
		query.setRows(size);
		final List<Integer> idList = new ArrayList<Integer>();
		try {
			SolrQueryUtil.getInstance().search("hbcompany", query,
					new SolrReadHandler() {
						@Override
						public void handlerReader(QueryResponse response)
								throws SolrServerException {
							List<SolrDocument> list = response.getResults();
							for (SolrDocument doc : list) {
								idList.add(Integer.valueOf(""
										+ doc.getFieldValue("id")));
							}
						}
					});
		} catch (Exception e) {

		}
		List<CompProfile> resultList = new ArrayList<CompProfile>();

		for (Integer id : idList) {
			CompProfile comp = compProfileDao.queryCompProfileById(id);
			if (comp != null) {
				resultList.add(comp);
			}
		}

		return resultList;
	}

	@Override
	public List<Exhibit> queryExhibitByKeywords(String keyword, Integer size) {

		SolrQuery query = new SolrQuery();
		if (StringUtils.isNotEmpty(keyword)) {
			query.setQuery("kw:" + keyword);
		} else {
			query.setQuery("*:*");
		}
		query.addFilterQuery("pauseStatus:1");
		query.addSortField("gmtPublish", ORDER.desc);
		query.addSortField("score", ORDER.desc);
		query.setStart(0);
		if (size != null && size > 100) {
			size = 100;
		}
		query.setRows(size);
		final List<Integer> idList = new ArrayList<Integer>();
		try {
			SolrQueryUtil.getInstance().search("hbexhibit", query,
					new SolrReadHandler() {

						@Override
						public void handlerReader(QueryResponse response)
								throws SolrServerException {
							List<SolrDocument> list = response.getResults();
							for (SolrDocument doc : list) {
								idList.add(Integer.valueOf(""
										+ doc.getFieldValue("id")));
							}

						}
					});
		} catch (Exception e) {

		}
		List<Exhibit> resultList = new ArrayList<Exhibit>();
		for (Integer id : idList) {
			Exhibit exhibit = exhibitDao.queryExhibitDetailsById(id);
			if (exhibit != null) {
				resultList.add(exhibit);
			}
		}
		return resultList;
	}

	//
	// @Override
	// public List<TradeSupplySearchDto> queryTradeSupplyByKeyword(String
	// keyword, Integer size) throws SolrServerException {
	// SolrServer server = SolrUtil.getInstance().getSolrServer("tradesupply");
	// SolrQuery query = new SolrQuery();
	// if (StringUtils.isNotEmpty(keyword)) {
	// query.setQuery("kw:"+keyword);
	// } else {
	// query.setQuery("*:*");
	// }
	// query.addFilterQuery("memberCodeBlock: \"-1\"");
	// query.addFilterQuery("delStatus:0");
	// query.addFilterQuery("checkStatus:1");
	// query.addFilterQuery("pauseStatus:0");
	//
	// query.set(GroupParams.GROUP, true);
	// query.set(GroupParams.GROUP_FIELD, "gcid");
	// query.set(GroupParams.GROUP_MAIN, true);
	// query.set(GroupParams.GROUP_LIMIT,2);
	//
	// query.addSortField("sortRefresh", ORDER.desc);
	// query.addSortField("sortMember", ORDER.desc);
	// query.addSortField("score", ORDER.desc);
	// query.addSortField("gmtRefresh", ORDER.desc);
	//
	//
	// query.setStart(0);
	// if (size != null && size > 100) {
	// size = 100;
	// }
	// query.setRows(size);
	// QueryResponse rsp = server.query(query);
	//
	// List<TradeSupplySearchDto> beans =
	// rsp.getBeans(TradeSupplySearchDto.class);
	// return beans;
	// }
	//
//	@SuppressWarnings("unchecked")
//	@Override
//	public PageDto<CompanyNormDto> pageCompanyBySearch(final SearchCompanyDto search, final PageDto<CompanyNormDto> page, Integer sort) {
//		SolrQuery query = new SolrQuery();
//		// 关键字不为空时，有高亮显示
//		do {
//			if (StringUtils.isNotEmpty(search.getKeywords())) {
//				String keywords = buildKeyWord(search.getKeywords());
//				if (StringUtils.isEmpty(keywords)) {
//					return page;
//				}
//				query.setQuery("kw:" + keywords);
//				query.setHighlight(true);
//				query.addHighlightField("name");
//				query.addHighlightField("detailsQuery");
//				query.setHighlightSimplePre("<em>");
//				query.setHighlightSimplePost("</em>");
//				break;
//			}
//			query.setQuery("*:*");
//
//		} while (false);
//
//		// query.addFilterQuery("mainBuy:0");
//		query.addFilterQuery("mainSupply:1");
//		query.addFilterQuery("memberCodeBlock: \"-1\"");
//		
//		if (search.getIndustryChainId() != null) {
//			query.addFilterQuery("chainId:" + search.getIndustryChainId());
//		}
//		
//		if (StringUtils.isNotEmpty(search.getBusinessCode())) {
//			query.addFilterQuery("businessCode:" + search.getBusinessCode());
//		}
//		if (StringUtils.isNotEmpty(search.getIndustryCode())) {
//			query.addFilterQuery("industryCode:" + search.getIndustryCode());
//		}
//		if (StringUtils.isNotEmpty(search.getAreaCode())) {
//			query.addFilterQuery("areaCode:" + search.getAreaCode());
//		}
//		if (StringUtils.isNotEmpty(search.getProvinceCode())) {
//			query.addFilterQuery("provinceCode:" + search.getProvinceCode());
//		}
//		do {
//			if (sort.intValue() == 1) {
//				query.addSortField("sortMemberCode", ORDER.desc);
//				break;
//			}
//			if (sort.intValue() == 0) {
//				query.addSortField("gmtCreated", ORDER.desc);
//				break;
//			}
//		} while (false);
//		query.addSortField("score", ORDER.desc);
//		query.setStart(page.getStart());
//		query.setRows(page.getLimit());
//		final List<Integer> idList = new ArrayList<Integer>();
//		final Object[] highColum = new Object[1];
//
//		try {
//			SolrQueryUtil.getInstance().search("hbcompany", query,
//					new SolrReadHandler() {
//
//						@Override
//						public void handlerReader(QueryResponse response)
//								throws SolrServerException {
//							List<SolrDocument> list = response.getResults();
//							for (SolrDocument doc : list) {
//								idList.add(Integer.valueOf(""
//										+ doc.getFieldValue("id")));
//							}
//
//							// 高亮显示(替换普通文本)
//							if (StringUtils.isNotEmpty(search.getKeywords())) {
//								Map<String, Map<String, List<String>>> high = response
//										.getHighlighting();
//								highColum[0] = high;
//							}
//
//							page.setTotals((int) response.getResults()
//									.getNumFound());
//
//						}
//					});
//		} catch (Exception e) {
//			page.setTotals(0);
//		}
//		List<CompanyNormDto> resultList = new ArrayList<CompanyNormDto>();
//
//		for (Integer id : idList) {
//			CompanyNormDto dto = buildCompany(id,
//					(Map<String, Map<String, List<String>>>) highColum[0]);
//			if (dto != null) {
//				resultList.add(dto);
//			}
//		}
//
//		page.setRecords(resultList);
//		return page;
//	}
	@Override
	public PageDto<CompanyNormDto> pageCompanyBySearch(SearchCompanyDto search, PageDto<CompanyNormDto> page) {
		StringBuffer sb=new StringBuffer();
		SphinxClient cl=SearchEngineUtils.getInstance().getClient();
		String keywords=search.getKeywords();
		if(StringUtils.isNotEmpty(keywords)){
			keywords = keywords.replaceAll("/", "");
			keywords=keywords.replace("%","");
    		keywords=keywords.replace("\\","");
    		keywords=keywords.replace("-","");
    		keywords=keywords.replace("(","");
    		keywords=keywords.replace(")","");
    		sb.append("@(name,details_query) ").append(keywords);
		}
		List<CompanyNormDto> resultList=new ArrayList<CompanyNormDto>();
		try {
			cl.SetFilter("main_supply", 1, false);
			cl.SetFilterRange("member_code_block", 0, 0, false);
			cl.SetFilterRange("name_len", 0, 0, true);
			cl.SetFilterRange("account_len", 0, 0, true);
			cl.SetFilter("del_status", 1, true);
			if(StringUtils.isNotEmpty(search.getBusinessCode())){
				cl.SetFilter("business_code", Integer.valueOf(search.getBusinessCode()), false);
			}
			if(StringUtils.isNotEmpty(search.getIndustryCode())){
				cl.SetFilter("industry_code", Integer.valueOf(search.getIndustryCode()), false);
			}
			if(StringUtils.isNotEmpty(search.getAreaCode())){
				sb.append("@(area_code) ").append(search.getAreaCode());
			}
			if(StringUtils.isNotEmpty(search.getProvinceCode())){
				sb.append("@(province_code) ").append(search.getProvinceCode());
			}
			cl.SetSortMode(SphinxClient.SPH_SORT_ATTR_DESC, "gmt_created");
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStart(),page.getLimit(),ONE_HUNDRED_THOUSAND_LIMIT);
			SphinxResult res=cl.Query(sb.toString(), "huanbao_new_company");
			if(res==null){
				 return null;
			}
			if(res.totalFound>ONE_HUNDRED_THOUSAND_LIMIT){
				page.setTotals(ONE_HUNDRED_THOUSAND_LIMIT);
			}else{
				page.setTotals(res.totalFound);
			}
			for ( int i=0; i<res.matches.length; i++ ){
				SphinxMatch info = res.matches[i];
				CompanyNormDto cnd=new CompanyNormDto();
				CompProfile cp=compProfileDao.queryCompProfileById(Integer.valueOf(String.valueOf(""+info.docId)));
					cnd.setCompany(cp);
					resultList.add(cnd);
			}
		} catch (SphinxException e) {
			return null;
		}
		page.setRecords(resultList);
		return page;
			
	}
	@SuppressWarnings("unused")
	private CompanyNormDto buildCompany(Integer id,
			Map<String, Map<String, List<String>>> high) {
		CompProfile comp = compProfileDao.queryCompProfileById(id);
		if (comp == null) {
			return null;
		}

		CompanyNormDto dto = new CompanyNormDto();
		if (high != null) {
			Map<String, List<String>> root = high.get(id.toString());
			if (root != null) {
				if (root.get("name") != null) {
					comp.setName(root.get("name").get(0));
				}
				if (root.get("detailsQuery") != null) {
					comp.setDetailsQuery(root.get("detailsQuery").get(0));
				}
			}
		}
		dto.setCompany(comp);
		if (StringUtils.isNotEmpty(comp.getAreaCode())) {
			dto.setArea(AreaUtil.buildArea(comp.getAreaCode()));
		}
		dto.setCompCategoryName(CodeCachedUtil.getValue("comp_industry",
				comp.getIndustryCode()));
		return dto;
	}

	// @SuppressWarnings("unchecked")
	// @Override
	// public PageDto<CompanyNormDto> pageNewCompanyBySearch(
	// final SearchCompanyDto search,final PageDto<CompanyNormDto> page)
	// throws SolrServerException {
	// SolrQuery query = new SolrQuery();
	// //关键字不为空时，有高亮显示
	// if (StringUtils.isNotEmpty(search.getKeywords())) {
	// String keywords = buildKeyWord(search.getKeywords());
	// if (StringUtils.isEmpty(keywords)) {
	// return page;
	// }
	// query.setQuery("kw:"+keywords);
	// query.setHighlight(true);
	// query.addHighlightField("name");
	// query.addHighlightField("detailsQuery");
	// query.setHighlightSimplePre("<em>");
	// query.setHighlightSimplePost("</em>");
	// } else {
	// query.setQuery("*:*");
	// }
	// query.addFilterQuery("memberCodeBlock: \"-1\"");
	// if (StringUtils.isNotEmpty(search.getBusinessCode())) {
	// query.addFilterQuery("businessCode:"+search.getBusinessCode());
	// }
	// if (StringUtils.isNotEmpty(search.getIndustryCode())) {
	// query.addFilterQuery("industryCode:"+search.getIndustryCode());
	// }
	// if (StringUtils.isNotEmpty(search.getAreaCode())) {
	// query.addFilterQuery("areaCode:"+search.getAreaCode());
	// }
	// if (StringUtils.isNotEmpty(search.getProvinceCode())) {
	// query.addFilterQuery("provinceCode:"+search.getProvinceCode());
	// }
	// query.addSortField("gmtCreated", ORDER.desc);
	// query.addSortField("score", ORDER.desc);
	// query.setStart(page.getStart());
	// query.setRows(page.getLimit());
	// final List<Integer> idList = new ArrayList<Integer>();
	// final Object [] highColum = new Object [1];
	// try {
	// SolrQueryUtil.getInstance().search("hbcompany", query, new
	// SolrReadHandler() {
	//
	// @Override
	// public void handlerReader(QueryResponse response)
	// throws SolrServerException {
	// List<SolrDocument> list = response.getResults();
	// for (SolrDocument doc : list) {
	// idList.add(Integer.valueOf(""+ doc.getFieldValue("id")));
	// }
	//
	// //高亮显示(替换普通文本)
	// if (StringUtils.isNotEmpty(search.getKeywords())) {
	// Map<String,Map<String,List<String>>> high = response.getHighlighting();
	// highColum[0] = high;
	// }
	//
	// page.setTotals((int)response.getResults().getNumFound());
	//
	// }
	//
	// });
	// } catch (Exception e) {
	// page.setTotals(0);
	// }
	// List<CompanyNormDto> resultList = new ArrayList<CompanyNormDto>();
	//
	// for(Integer id : idList){
	// CompanyNormDto dto =
	// buildCompany(id,(Map<String,Map<String,List<String>>>)highColum[0]);
	// if(dto!=null){
	// resultList.add(dto);
	// }
	// }
	// page.setRecords(resultList);
	// return page;
	// }

//	@Override
//	public PageDto<ExhibitNormDto> pageExhibitBySearch(Exhibit exhibit,
//			final PageDto<ExhibitNormDto> page) {
//
//		SolrQuery query = new SolrQuery();
//		String keywords = exhibit.getName();
//		if(StringUtils.isNotEmpty(keywords)){
//			query.setQuery("kw:"+keywords);
//		}else{
//			query.setQuery("*:*");
//		}
//		query.addFilterQuery("pauseStatus:1");
//		if (StringUtils.isNotEmpty(exhibit.getOrganizers())) {
//			query.addFilterQuery("organizers:" + exhibit.getOrganizers());
//		}
//		if (StringUtils.isNotEmpty(exhibit.getIndustryCode())) {
//			query.addFilterQuery("industryCode:" + exhibit.getIndustryCode());
//		}
//		if (StringUtils.isNotEmpty(exhibit.getPlateCategoryCode())) {
//			query.addFilterQuery("plateCategoryCode:"
//					+ exhibit.getPlateCategoryCode());
//		}
//		if (StringUtils.isNotEmpty(exhibit.getProvinceCode())) {
//			query.addFilterQuery("provinceCode:" + exhibit.getProvinceCode());
//		}
//		if (StringUtils.isNotEmpty(exhibit.getAreaCode())) {
//			query.addFilterQuery("areaCode:" + exhibit.getAreaCode());
//		}
//		// TODO日期范围
//		// if (exhibit.getGmtStart() != null) {
//		// query.addFilterQuery("gmtStart:[2011-11-08/T00:00:00/Z TO 2012-11-08/T00:00:00/Z]");
//		// }
//		// if (exhibit.getGmtEnd() != null) {
//		// query.addFilterQuery("gmtEnd:[* TO "+exhibit.getGmtEnd()+"]");
//		// }
//		query.addSortField("gmtPublish", ORDER.desc);
//		query.addSortField("score", ORDER.desc);
//		query.setStart(page.getStart());
//		query.setRows(page.getLimit());
//		final List<Integer> idList = new ArrayList<Integer>();
//		try {
//			SolrQueryUtil.getInstance().search("hbexhibit", query,
//					new SolrReadHandler() {
//
//						@Override
//						public void handlerReader(QueryResponse response)
//								throws SolrServerException {
//
//							page.setTotals((int) response.getResults()
//									.getNumFound());
//
//							List<SolrDocument> list = response.getResults();
//							for (SolrDocument doc : list) {
//								idList.add(Integer.valueOf(""
//										+ doc.getFieldValue("id")));
//							}
//						}
//					});
//		} catch (Exception e) {
//			page.setTotals(0);
//		}
//
//		List<ExhibitNormDto> resultList = new ArrayList<ExhibitNormDto>();
//		for (Integer id : idList) {
//			ExhibitNormDto dto = bulidExhibit(id);
//			if (dto != null) {
//				resultList.add(dto);
//			}
//		}
//		page.setRecords(resultList);
//		return page;
//	}
	@Override
	public PageDto<ExhibitNormDto> pageExhibitBySearch(Exhibit exhibit, final PageDto<ExhibitNormDto> page) {
		StringBuffer sb=new StringBuffer();
		SphinxClient cl=SearchEngineUtils.getInstance().getClient();
		page.setLimit(20);
		String keywords="";
		if(StringUtils.isNotEmpty(exhibit.getName())){
			keywords=exhibit.getName();
		}
		if(StringUtils.isNotEmpty(exhibit.getProvinceCode())){
			keywords=" "+exhibit.getProvinceCode();
		}
		if(StringUtils.isNotEmpty(exhibit.getAreaCode())){
			keywords=" "+exhibit.getAreaCode();
		}
		if(StringUtils.isNotEmpty(exhibit.getOrganizers())){
			keywords=" "+exhibit.getOrganizers();
		}
		if(StringUtils.isNotEmpty(keywords)){
			keywords = keywords.replaceAll("/", "");
			keywords=keywords.replace("%","");
    		keywords=keywords.replace("\\","");
    		keywords=keywords.replace("-","");
    		keywords=keywords.replace("(","");
    		keywords=keywords.replace(")","");
    		sb.append("@(name,province_code,area_code,organizers) ").append(keywords);
		}
		List<ExhibitNormDto> resultList=new ArrayList<ExhibitNormDto>();
		try {
			cl.SetFilter("pause_status", 1, false);
			if(StringUtils.isNotEmpty(exhibit.getIndustryCode())){
				cl.SetFilter("industry_code", Integer.valueOf(exhibit.getIndustryCode()), false);
			}
			if(StringUtils.isNotEmpty(exhibit.getPlateCategoryCode())){
				cl.SetFilter("plate_category_code", Integer.valueOf(exhibit.getPlateCategoryCode()), false);
			}
		    if(exhibit.getGmtStart()!=null){
		    	if(exhibit.getGmtEnd()==null){
		    		try {
						exhibit.setGmtEnd(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
					} catch (ParseException e) {
					}
		    	}
		    	cl.SetFilterRange("start_date", exhibit.getGmtStart().getTime()/1000, exhibit.getGmtEnd().getTime()/1000, false);
		    }
			cl.SetSortMode(SphinxClient.SPH_SORT_ATTR_DESC, "gmt_publish");
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStart(),page.getLimit(),ONE_HUNDRED_THOUSAND_LIMIT);
			SphinxResult res=cl.Query(sb.toString(), "huanbao_new_exhibit");
			if(res==null){
				 return null;
			}
			if(res.totalFound>ONE_HUNDRED_THOUSAND_LIMIT){
				page.setTotals(ONE_HUNDRED_THOUSAND_LIMIT);
			}else{
				page.setTotals(res.totalFound);
			}
			for ( int i=0; i<res.matches.length; i++ ){
				SphinxMatch info = res.matches[i];
				ExhibitNormDto dto = bulidExhibit(Integer.valueOf(String.valueOf(info.docId)));
			    resultList.add(dto);
			}
		} catch (SphinxException e) {
			return null;
		}
		page.setRecords(resultList);
		return page;
			
	}
	private ExhibitNormDto bulidExhibit(Integer id) {
		ExhibitNormDto dto = null;
		Exhibit exhibit = exhibitDao.queryExhibitDetailsById(id);
		if (exhibit != null) {
			dto = new ExhibitNormDto();
			dto.setExhibit(exhibit);
			if (StringUtils.isNotEmpty(exhibit.getAreaCode())) {
				dto.setArea(AreaUtil.buildArea(exhibit.getAreaCode()));
			}
			String name = CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_EXHIBIT,
					exhibit.getIndustryCode());
			dto.setIndustryName(name);
			dto.setPlateCategoryName(name);
		}
		return dto;
	}

	@Override
	public PageDto<NewsNormDto> pageNewsBySearch(String keywords,
			String categoryCode, final PageDto<NewsNormDto> page) {
		SolrQuery query = new SolrQuery();
		// 关键字不为空时，有高亮显示
		if (StringUtils.isNotEmpty(keywords)) {
			String keywordstmp = buildKeyWord(keywords);
			if (StringUtils.isEmpty(keywordstmp)) {
				return page;
			}
			query.setQuery("kw:" + keywords);
		} else {
			query.setQuery("*:*");
		}
		query.addFilterQuery("pauseStatus:1");
		if (StringUtils.isNotEmpty(categoryCode)) {
			query.addFilterQuery("categoryCode:" + categoryCode);
		}
		query.addSortField("gmtPublish", ORDER.desc);
		query.addSortField("score", ORDER.desc);
		query.setStart(page.getStart());
		query.setRows(page.getLimit());

		final List<Integer> idList = new ArrayList<Integer>();
		try {
			SolrQueryUtil.getInstance().search("hbnews", query,
					new SolrReadHandler() {

						@Override
						public void handlerReader(QueryResponse response)
								throws SolrServerException {

							page.setTotals((int) response.getResults()
									.getNumFound());

							List<SolrDocument> list = response.getResults();
							for (SolrDocument doc : list) {
								idList.add(Integer.valueOf(""
										+ doc.getFieldValue("id")));
							}
						}
					});
		} catch (Exception e) {
			page.setTotals(0);
		}

		List<NewsNormDto> resultList = new ArrayList<NewsNormDto>();
		for (Integer id : idList) {
			NewsNormDto dto = bulidNews(id);
			if (dto != null) {
				resultList.add(dto);
			}

		}
		page.setRecords(resultList);

		return page;
	}

	private NewsNormDto bulidNews(Integer id) {
		NewsNormDto dto = null;
		News news = newsDao.queryNewDetailsById(id);
		if (news != null) {
			dto = new NewsNormDto();
			dto.setNews(news);
			dto.setCategoryName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_NEWS, news.getCategoryCode()));
		}

		return dto;
	}

	@Override
	public ResultSolrDto pageSupplysBySearch(final SearchSupplyDto search,
			final PageDto<TradeSupplyNormDto> page, String sort) {

		if (page.getLimit() == 20) {
			page.setLimit(30);
		}

		// 构建查询条件
		// 查询ID
		// 从数据库获取数据，充实查询结果

		// SolrServer server =
		// SolrUtil.getInstance().getSolrServer("tradesupply");
		//
		SolrQuery query = new SolrQuery();

		// 关键字不为空时，有高亮显示
		if (StringUtils.isNotEmpty(search.getKeywords())) {
			String keywords = buildKeyWord(search.getKeywords());
			if (StringUtils.isEmpty(keywords)) {
				return null;
			}
			query.setQuery("kw:" + keywords);
			query.setHighlight(true);
			query.addHighlightField("title");
			query.setHighlightSimplePre("<em>");
			query.setHighlightSimplePost("</em>");
		} else {
			query.setQuery("*:*");
		}

		query.addFilterQuery("memberCodeBlock: \"-1\"");
		query.addFilterQuery("delStatus:0");
		query.addFilterQuery("checkStatus:1");
		query.addFilterQuery("pauseStatus:0");

		if (StringUtils.isNotEmpty(search.getCategory())) {
			query.addFilterQuery("category" + search.getCategory().length()
					+ ":" + search.getCategory());
		}
		if (StringUtils.isNotEmpty(search.getRegion())) {
			query.addFilterQuery("provinceCode:" + search.getRegion());
		}

		if (search.getPriceFrom() != null && search.getPriceTo() != null) {
			query.setQuery(query.getQuery() + " AND " + "priceNum:" + "["
					+ search.getPriceFrom() + " TO " + search.getPriceTo()
					+ "]");
		} else if (search.getPriceFrom() != null && search.getPriceTo() == null) {
			query.setQuery(query.getQuery() + " AND " + "priceNum:" + "["
					+ search.getPriceFrom() + " TO *]");
		} else if (search.getPriceFrom() != null && search.getPriceTo() == null) {
			query.setQuery(query.getQuery() + " AND " + "priceNum:" + "[0 TO "
					+ search.getPriceTo() + "]");
		}

		// 按专业属性搜索
		if (StringUtils.isNotEmpty(search.getPropertyValue())) {
			String propertyValueArr[] = search.getPropertyValue().split(":");
			for (String propertyValue : propertyValueArr) {
				if (StringUtils.isNotEmpty(propertyValue)) {
					query.addFilterQuery("propertyQuery:" + propertyValue);
				}
			}
		}
		if (!"3".equals(sort)) {
			query.set(GroupParams.GROUP, true);
			query.set(GroupParams.GROUP_FIELD, "gcid");
			query.set(GroupParams.GROUP_MAIN, true);
			query.set(GroupParams.GROUP_LIMIT, 2);
		}
		if ("1".equals(page.getSort())) {
			query.addSortField("priceNum", ORDER.desc);
		} else if ("2".equals(page.getSort())) {
			query.addSortField("priceNum", ORDER.asc);
		}

		do {
			if ("3".equals(sort)) {
				query.addSortField("gmtRefresh", ORDER.desc);
				break;
			}
			query.addSortField("sortRefresh", ORDER.desc);
			query.addSortField("sortMember", ORDER.desc);
			if ("1".equals(sort)) {
				query.addSortField("score", ORDER.desc);
				query.addSortField("gmtRefresh", ORDER.desc);
				break;
			}
			if ("2".equals(sort)) {
				if (StringUtils.isNotEmpty(search.getKeywords())) {
					query.addSortField("score", ORDER.desc);
					query.addSortField("gmtRefresh", ORDER.desc);
				} else {
					query.addSortField("gmtRefresh", ORDER.desc);
					query.addSortField("score", ORDER.desc);
				}
				break;
			}
		} while (false);
		query.setStart(page.getStart());
		query.setRows(page.getLimit());
		final List<Integer> idList = new ArrayList<Integer>();
		final ResultSolrDto solrDto = new ResultSolrDto();
		try {
			SolrQueryUtil.getInstance().search("hbtradesupply", query,
					new SolrReadHandler() {

						@Override
						public void handlerReader(QueryResponse response)
								throws SolrServerException {
							// 获取ID
							// 获取高亮
							solrDto.setTotals((int) response.getResults()
									.getNumFound());
							List<SolrDocument> list = response.getResults();
							Map<String, Map<String, List<String>>> high = null;
							if (StringUtils.isNotEmpty(search.getKeywords())) {
								high = response.getHighlighting();
								solrDto.setHighMap(high);
							}
							for (SolrDocument doc : list) {
								idList.add(Integer.valueOf(""
										+ doc.getFieldValue("id")));

							}
						}
					});
		} catch (SolrServerException e) {
			// 异常时当作无记录处理
			solrDto.setTotals(0);
		}

		// List<TradeSupplyNormDto> resultList = new
		// ArrayList<TradeSupplyNormDto>();
		//
		// for (Integer id : idList) {
		// TradeSupplyNormDto dto = buildSupplyFromDB(id);
		// if (dto != null) {
		// resultList.add(dto);
		// }
		// }
		//
		solrDto.setIdList(idList);

		return solrDto;

	}

	// private TradeSupplyNormDto buildSupplyFromDB(Integer id) {
	// TradeSupply ts = tradeSupplyDao.querySupplyOmitDetails(id);
	//
	// if (ts == null) {
	// return null;
	// }
	//
	// //
	// CompProfileNormDto comp = compProfileDao.queryProfileWithAccount(ts
	// .getCid());
	// if (comp == null || comp.getComp() == null) {
	// return null;
	// }
	//
	// TradeSupplyNormDto dto = new TradeSupplyNormDto();
	// dto.setSupply(ts);
	// dto.setComp(comp.getComp());
	// dto.setAccount(comp.getAccount());
	//
	// dto.setArea(AreaUtil.buildArea(ts.getAreaCode()));
	//
	// return dto;
	// }

	// @Override
	// public PageDto<TradeSupplySearchDto> pageNewSupplys(SearchSupplyDto
	// search, PageDto<TradeSupplySearchDto> page) throws SolrServerException {
	//
	// SolrServer server = SolrUtil.getInstance().getSolrServer("tradesupply");
	//
	// SolrQuery query = new SolrQuery();
	//
	// if (StringUtils.isNotEmpty(search.getKeywords())) {
	// String keywords = buildKeyWord(search.getKeywords());
	// if (StringUtils.isEmpty(keywords)) {
	// return page;
	// }
	// query.setQuery("kw:"+keywords);
	// } else {
	// query.setQuery("*:*");
	// }
	//
	// query.addFilterQuery("delStatus:0");
	// query.addFilterQuery("checkStatus:1");
	// query.addFilterQuery("pauseStatus:0");
	//
	// if (StringUtils.isNotEmpty(search.getCategory())) {
	// query.addFilterQuery("category"+search.getCategory().length()+":"+search.getCategory());
	// }
	// if (StringUtils.isNotEmpty(search.getRegion())) {
	// query.addFilterQuery("provinceCode:"+search.getRegion());
	// }
	//
	//
	//
	// query.addSortField("gmtRefresh", ORDER.desc);
	//
	// query.setStart(page.getStart());
	// query.setRows(page.getLimit());
	//
	// QueryResponse rsp = new QueryResponse();
	// List<TradeSupplySearchDto> beans = new ArrayList<TradeSupplySearchDto>();
	// try {
	// rsp = server.query(query);
	// beans = rsp.getBeans(TradeSupplySearchDto.class);
	// page.setTotals((int)rsp.getResults().getNumFound());
	// } catch (Exception e) {
	// page.setTotals(page.getStart()+1);
	// }
	// page.setRecords(buildSupplyBeans(beans));
	// return page;
	// }
	//
	@Override
	public PageDto<TradeBuyNormDto> pageBuysBySearch(SearchBuyDto search,
			final PageDto<TradeBuyNormDto> page, String sort) {

		SolrQuery query = new SolrQuery();
		// 关键字不为空时，有高亮显示
		if (StringUtils.isNotEmpty(search.getKeywords())) {
			String keywords = buildKeyWord(search.getKeywords());
			if (StringUtils.isEmpty(keywords)) {
				return page;
			}
			query.setQuery("kw:" + keywords);
			query.setHighlight(true);
			query.addHighlightField("title");
			query.setHighlightSimplePre("<em>");
			query.setHighlightSimplePost("</em>");
		} else {
			query.setQuery("*:*");
		}
		query.addFilterQuery("memberCodeBlock: \"-1\"");
		query.addFilterQuery("delStatus:0");
		query.addFilterQuery("checkStatus:1");
		query.addFilterQuery("pauseStatus:0");

		if (StringUtils.isNotEmpty(search.getRegion())) {
			query.addFilterQuery("provinceCode:" + search.getRegion());
		}
		if (search.getTime() != null && search.getTime() > 0) {
			query.addSortField("gmtRefresh", ORDER.desc);
		}
		do {
			if ("1".equals(sort)) {
				// 排序
				if ("1".equals(page.getSort())) {
					query.addSortField("quantity", ORDER.desc);
				} else if ("2".equals(page.getSort())) {
					query.addSortField("quantity", ORDER.asc);
				} else if ("3".equals(page.getSort())) {
					query.addSortField("messageCount", ORDER.desc);
				} else if ("4".equals(page.getSort())) {
					query.addSortField("messageCount", ORDER.asc);
				} else if ("5".equals(page.getSort())) {
					query.addSortField("gmtRefresh", ORDER.desc);
				} else if ("6".equals(page.getSort())) {
					query.addSortField("gmtRefresh", ORDER.asc);
				} else {
					query.addSortField("gmtRefresh", ORDER.desc);
				}
				query.addSortField("score", ORDER.desc);
			}

			if ("2".equals(sort)) {
				if (StringUtils.isNotEmpty(search.getKeywords())) {
					query.addSortField("score", ORDER.desc);
					query.addSortField("gmtRefresh", ORDER.desc);
				} else {
					query.addSortField("gmtRefresh", ORDER.desc);
					query.addSortField("score", ORDER.desc);
				}
			}
		} while (false);
		query.setStart(page.getStart());
		query.setRows(page.getLimit());

		final List<Integer> idList = new ArrayList<Integer>();
		try {
			SolrQueryUtil.getInstance().search("hbtradebuy", query,
					new SolrReadHandler() {

						@Override
						public void handlerReader(QueryResponse response)
								throws SolrServerException {
							page.setTotals((int) response.getResults()
									.getNumFound());

							List<SolrDocument> list = response.getResults();
							for (SolrDocument doc : list) {
								idList.add(Integer.valueOf(""
										+ doc.getFieldValue("id")));
							}
						}
					});
		} catch (SolrServerException e) {
			page.setTotals(0);
		}

		List<TradeBuyNormDto> resultList = new ArrayList<TradeBuyNormDto>();

		for (Integer id : idList) {
			TradeBuyNormDto dto = buildBuyFromDB(id);
			if (dto != null) {
				resultList.add(dto);
			}
		}

		page.setRecords(resultList);

		return page;

		// QueryResponse rsp = server.query(query);
		// List<TradeBuySearchDto> beans =
		// rsp.getBeans(TradeBuySearchDto.class);
		// page.setTotals((int)rsp.getResults().getNumFound());
		// //高亮显示(替换普通文本)
		// if (StringUtils.isNotEmpty(search.getKeywords())) {
		// Map<String,Map<String,List<String>>> high = rsp.getHighlighting();
		// for (TradeBuySearchDto bean : beans) {
		// Map<String,List<String>> root = high.get(bean.getId().toString());
		// if ( root != null) {
		// if (root.get("title") != null) {
		// bean.setTitle(root.get("title").get(0));
		// }
		// }
		// }
		// }
		// try {
		// page.setRecords(buildBuyBeans(beans));
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// return page;
	}

	private TradeBuyNormDto buildBuyFromDB(Integer id) {
		TradeBuy tb = tradeBuyDao.queryBuySimpleDetailsById(id);
		if (tb == null) {
			return null;
		}
		TradeBuyNormDto dto = new TradeBuyNormDto();
		dto.setBuy(tb);
		Date d = tb.getGmtExpired();
		if (d != null) {
			try {
				int num = DateUtil.getIntervalDays(d, new Date());
					dto.setHasEnable(num);
			} catch (ParseException e) {
				dto.setHasEnable(0);
			}
		}
		if (StringUtils.isNotEmpty(tb.getAreaCode())) {
			dto.setArea(AreaUtil.buildArea(tb.getAreaCode()));
		}
		return dto;
	}

	// @Override
	// public List<TradeSupplySearchDto> queryHotSupplys(String categoryCode,
	// String keyWords, Integer size) throws SolrServerException {
	// SolrServer server = SolrUtil.getInstance().getSolrServer("tradesupply");
	// SolrQuery query = new SolrQuery();
	// //关键字不为空时，有高亮显示
	// if (StringUtils.isNotEmpty(keyWords)) {
	// String keywords = buildKeyWord(keyWords);
	// if (StringUtils.isEmpty(keywords)) {
	// return null;
	// }
	// query.setQuery("kw:"+keywords);
	// } else {
	// query.setQuery("*:*");
	// }
	// query.addFilterQuery("memberCodeBlock: \"-1\"");
	// query.addFilterQuery("delStatus:0");
	// query.addFilterQuery("delStatus:0");
	// query.addFilterQuery("checkStatus:1");
	// query.addFilterQuery("pauseStatus:0");
	//
	//
	// if (StringUtils.isNotEmpty(categoryCode)) {
	// query.addFilterQuery("category"+categoryCode.length()+":"+categoryCode);
	// }
	//
	// query.set(GroupParams.GROUP, true);
	// query.set(GroupParams.GROUP_FIELD, "gcid");
	// query.set(GroupParams.GROUP_MAIN, true);
	// query.set(GroupParams.GROUP_LIMIT,2);
	//
	// query.addSortField("sortRefresh", ORDER.desc);
	// query.addSortField("sortMember", ORDER.desc);
	//
	// if(StringUtils.isNotEmpty(keyWords)) {
	// query.addSortField("score", ORDER.desc);
	// query.addSortField("gmtRefresh", ORDER.desc);
	// } else {
	// query.addSortField("gmtRefresh", ORDER.desc);
	// query.addSortField("score", ORDER.desc);
	// }
	// query.setStart(0);
	// query.setRows(size);
	// QueryResponse rsp = server.query(query);
	// List<TradeSupplySearchDto> beans =
	// rsp.getBeans(TradeSupplySearchDto.class);
	// return buildSupplyBeans(beans);
	// }
	//
	// @Override
	// public List<TradeBuySearchDto> queryHotBuys(String keyWords, Integer
	// size) throws SolrServerException, ParseException {
	// SolrServer server = SolrUtil.getInstance().getSolrServer("tradebuy");
	// SolrQuery query = new SolrQuery();
	// //关键字不为空时，有高亮显示
	// if (StringUtils.isNotEmpty(keyWords)) {
	// String key = buildKeyWord(keyWords);
	// if (StringUtils.isEmpty(key)) {
	// return null;
	// }
	// query.setQuery("kw:"+key);
	// } else {
	// query.setQuery("*:*");
	// }
	// query.addFilterQuery("memberCodeBlock: \"-1\"");
	// query.addFilterQuery("delStatus:0");
	// query.addFilterQuery("checkStatus:1");
	// query.addFilterQuery("pauseStatus:0");
	// if(StringUtils.isNotEmpty(keyWords)) {
	// query.addSortField("score", ORDER.desc);
	// query.addSortField("gmtRefresh", ORDER.desc);
	// } else {
	// query.addSortField("gmtRefresh", ORDER.desc);
	// query.addSortField("score", ORDER.desc);
	// }
	// query.setStart(0);
	//
	// query.setRows(size);
	// QueryResponse rsp = server.query(query);
	// List<TradeBuySearchDto> beans = rsp.getBeans(TradeBuySearchDto.class);
	// return buildBuyBeans(beans);
	// }

	// @Override
	// public PageDto<TradeSupplySearchDto> pageSupplysByCategory(String
	// categoryCode, PageDto<TradeSupplySearchDto> page) throws
	// SolrServerException {
	//
	// if(page.getLimit()==20){
	// page.setLimit(30);
	// }
	//
	// SolrServer server = SolrUtil.getInstance().getSolrServer("tradesupply");
	//
	// SolrQuery query = new SolrQuery();
	//
	// query.setQuery("*:*");
	//
	// if (StringUtils.isNotEmpty(categoryCode)) {
	// query.addFilterQuery("category"+categoryCode.length()+":"+categoryCode);
	// }
	//
	// query.addFilterQuery("memberCodeBlock: \"-1\"");
	// query.addFilterQuery("delStatus:0");
	// query.addFilterQuery("checkStatus:1");
	// query.addFilterQuery("pauseStatus:0");
	//
	// query.set(GroupParams.GROUP, true);
	// query.set(GroupParams.GROUP_FIELD, "gcid");
	// query.set(GroupParams.GROUP_MAIN, true);
	// query.set(GroupParams.GROUP_LIMIT,2);
	//
	// query.addSortField("sortRefresh", ORDER.desc);
	// query.addSortField("sortMember", ORDER.desc);
	// query.addSortField("score", ORDER.desc);
	// query.addSortField("gmtRefresh", ORDER.desc);
	//
	// // query.addSortField("gmtRefresh", ORDER.desc);
	// // query.addSortField("score", ORDER.desc);
	//
	// query.setStart(page.getStart());
	// query.setRows(page.getLimit());
	// QueryResponse rsp = new QueryResponse();
	// List<TradeSupplySearchDto> beans = new ArrayList<TradeSupplySearchDto>();
	// try {
	// rsp = server.query(query);
	// beans = rsp.getBeans(TradeSupplySearchDto.class);
	// page.setTotals((int)rsp.getResults().getNumFound());
	// } catch (Exception e) {
	// page.setTotals(page.getStart()+1);
	// }
	// page.setRecords(buildSupplyBeans(beans));
	// return page;
	// }
	//
	// @Override
	// public PageDto<TradeBuySearchDto> pageBuysByCategory(String categoryCode,
	// PageDto<TradeBuySearchDto> page) throws SolrServerException,
	// ParseException{
	// SolrServer server = SolrUtil.getInstance().getSolrServer("tradebuy");
	// SolrQuery query = new SolrQuery();
	//
	// query.setQuery("*:*");
	//
	// if (StringUtils.isNotEmpty(categoryCode)) {
	// query.addFilterQuery("category"+categoryCode.length()+":"+categoryCode);
	// }
	//
	// query.addFilterQuery("memberCodeBlock: \"-1\"");
	// query.addFilterQuery("delStatus:0");
	// query.addFilterQuery("checkStatus:1");
	// query.addFilterQuery("pauseStatus:0");
	// query.addSortField("gmtRefresh", ORDER.desc);
	// query.addSortField("score", ORDER.desc);
	//
	// query.setStart(page.getStart());
	// query.setRows(page.getLimit());
	// QueryResponse rsp = server.query(query);
	// List<TradeBuySearchDto> beans = rsp.getBeans(TradeBuySearchDto.class);
	// page.setTotals((int)rsp.getResults().getNumFound());
	// page.setRecords(buildBuyBeans(beans));
	// return page;
	// }
	//
	@Override
	public PageDto<TradeCategory> pageCategory(TradeCategory cat,
			final PageDto<TradeCategory> page) {
		// SolrServer server =
		// SolrUtil.getInstance().getSolrServer("tradecategory");
		SolrQuery query = new SolrQuery();

		query.setQuery("*:*");

		if (StringUtils.isNotEmpty(cat.getCode())) {
			query.addFilterQuery("code" + cat.getCode().length() + ":"
					+ cat.getCode());
		}
		if (cat.getLeaf() != null) {
			query.addFilterQuery("leaf:" + cat.getLeaf());
		}

		query.addSortField("gmtCreated", ORDER.desc);
		query.addSortField("score", ORDER.desc);

		query.setStart(page.getStart());
		query.setRows(page.getLimit());
		final List<Integer> idList = new ArrayList<Integer>();
		try {
			SolrQueryUtil.getInstance().search("hbtradecategory", query,
					new SolrReadHandler() {

						@Override
						public void handlerReader(QueryResponse response)
								throws SolrServerException {
							page.setTotals((int) response.getResults()
									.getNumFound());
							List<SolrDocument> list = response.getResults();
							for (SolrDocument doc : list) {
								idList.add(Integer.valueOf(""
										+ doc.getFieldValue("id")));
							}

						}
					});

		} catch (Exception e) {
			page.setTotals(0);
		}
		List<TradeCategory> resultList = new ArrayList<TradeCategory>();
		for (Integer id : idList) {
			TradeCategory tradecate = tradeCategoryDao
					.queryTradeCategoryById(id);
			if (tradecate != null) {
				resultList.add(tradecate);
			}
		}
		page.setRecords(resultList);
		return page;
	}

	@Override
	public PageDto<SubnetCategory> pageSubCategory(SubnetCategory sub,
			final PageDto<SubnetCategory> page) {
		// SolrServer server =
		// SolrUtil.getInstance().getSolrServer("subnetcategory");
		SolrQuery query = new SolrQuery();

		query.setQuery("*:*");

		if (sub.getParentId() != null) {
			query.addFilterQuery("parentId:" + sub.getParentId());
		}

		query.addSortField("gmtCreated", ORDER.desc);
		query.addSortField("score", ORDER.desc);

		query.setStart(page.getStart());
		query.setRows(page.getLimit());
		final List<Integer> idList = new ArrayList<Integer>();
		try {
			SolrQueryUtil.getInstance().search("hbsubnetcategory", query,
					new SolrReadHandler() {

						@Override
						public void handlerReader(QueryResponse response)
								throws SolrServerException {
							page.setTotals((int) response.getResults()
									.getNumFound());
							List<SolrDocument> list = response.getResults();
							for (SolrDocument doc : list) {
								idList.add(Integer.valueOf(""
										+ doc.getFieldValue("id")));
							}
						}
					});
		} catch (Exception e) {
			page.setTotals(0);
		}
		List<SubnetCategory> resultList = new ArrayList<SubnetCategory>();
		for (Integer id : idList) {
			SubnetCategory sb = subnetCategoryDao.querySubCateById(id);
			if (sb != null) {
				resultList.add(sb);
			}
		}
		page.setRecords(resultList);
		return page;
	}

	private String buildKeyWord(String keywords) {
		// `~!@#$%^&*()+=|{}':;',\\[\\].<>/?
		String regEx = "[~!@#$%^&*()+=|{}':;',\\[\\].<>/?]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(keywords);
		return m.replaceAll("").trim();
	}

	@Override
	public PageDto<CompNewsDto> pageCompNewsBySearch(final PageDto<CompNewsDto> page,
			CompNewsDto compNewsDto,String keywords) {
	   
		
		SolrQuery query = new SolrQuery();
		if(StringUtils.isNotEmpty(keywords)){
			String keywordstmp=buildKeyWord(keywords);
			if(StringUtils.isEmpty(keywordstmp)){
				return page;
			}
			query.setQuery("kw:"+keywords);
		}else{
			query.setQuery("*:*");
		}
		query.addHighlightField("tags");
		query.addFacetQuery("checkStatus:1");
		query.addFacetQuery("categoryCode:1001");
		query.addFacetQuery("pauseStatus:1");
        query.addFacetQuery("deleteStatus");
		query.addSortField("gmtPublish", ORDER.desc);
		query.addSortField("memberCode", ORDER.desc);
		query.setStart(page.getStart());
		query.setRows(page.getLimit());
		
		final List<Integer> idList = new ArrayList<Integer>();
		
		try {
			SolrQueryUtil.getInstance().search("hbcompnews", query,
					new SolrReadHandler() {

						@Override
						public void handlerReader(QueryResponse response)
								throws SolrServerException {

							page.setTotals((int) response.getResults()
									.getNumFound());

							List<SolrDocument> list = response.getResults();
							for (SolrDocument doc : list) {
								idList.add(Integer.valueOf(""
										+ doc.getFieldValue("id")));
							}
						}
					});
		} catch (Exception e) {
			page.setTotals(0);
		}
    
		List<CompNewsDto> resuList=new ArrayList<CompNewsDto>();
		for (Integer id : idList) {
			CompNewsDto dto= bulidCompNews(id);
			if(dto!=null){
				resuList.add(dto);
			}
		}
		page.setRecords(resuList);
		
		return page;
	}
	
	private CompNewsDto bulidCompNews(Integer id) {
		CompNewsDto dto = null;
		CompProfile compProfile=null;
		CompNews compNews=compNewsDao.queryCompNewsById(id);
		
		if (compNews != null) {
			dto = new CompNewsDto();
			dto.setCompNews(compNews);
			compProfile=compProfileDao.queryShortCompProfileById(compNews.getCid());
		    dto.setCompName(compProfile.getName());
		    dto.setMemberCode(compProfile.getMemberCode());
		}

		return dto;
	}
}