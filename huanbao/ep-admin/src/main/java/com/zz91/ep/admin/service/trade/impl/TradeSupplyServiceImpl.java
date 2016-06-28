/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-16
 */
package com.zz91.ep.admin.service.trade.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.comp.CompAccountDao;
import com.zz91.ep.admin.dao.comp.CompProfileDao;
import com.zz91.ep.admin.dao.credit.CreditFileDao;
import com.zz91.ep.admin.dao.sys.ParamDao;
import com.zz91.ep.admin.dao.trade.TradeSupplyDao;
import com.zz91.ep.admin.service.trade.TradeSupplyService;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompProfileDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.util.Assert;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * @author totly
 * 
 * created on 2011-9-16
 */
@Component("tradeSupplyService")
public class TradeSupplyServiceImpl implements TradeSupplyService {

	@Resource
	private TradeSupplyDao tradeSupplyDao;
	@Resource
	private CompProfileDao compProfileDao;
	@Resource
	private CreditFileDao creditFileDao;
	@Resource
	private ParamDao paramDao;
	@Resource
	private CompAccountDao compAccountDao;
	

	@Override
	public Integer createTradeSupply(TradeSupply tradeSupply) {
		Assert.notNull(tradeSupply, "the tradeSupply can not be null");
		Assert.notNull(tradeSupply.getUid(), "the uid can not be null");
		Assert.notNull(tradeSupply.getCid(), "the cid can not be null");
		Assert.notNull(tradeSupply.getCategoryCode(),"the categoryCode can not be null");
		// TODO 检查敏感词，设置审核状态
		tradeSupply.setCheckStatus((short)TradeSupplyService.STATUS_CHECK_YES);
		// 设置过期时间
		if (tradeSupply.getValidDays() == null || tradeSupply.getValidDays() == 0 ) {
			try {
				tradeSupply.setGmtExpired(DateUtil.getDate("9999-12-30", "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			tradeSupply.setGmtExpired(DateUtil.getDateAfterDays(new Date(), tradeSupply.getValidDays()));
		}
		// 设置发布时间
		tradeSupply.setGmtPublish(new Date());
		// 设置刷新时间
		tradeSupply.setGmtRefresh(new Date());
		// 设置详细信息的查询文本（提取详细信息的部分纯文本信息）
		if (StringUtils.isNotEmpty(tradeSupply.getDetails())) {
			String string = Jsoup.clean(tradeSupply.getDetails(), Whitelist.none());
			tradeSupply.setDetailsQuery(string.replace(" ", ""));
		}
		if (tradeSupply.getInfoComeFrom()==null){
			tradeSupply.setInfoComeFrom(1);
		}
		return tradeSupplyDao.insertTradeSupply(tradeSupply);
	}

//	@Override
//	public PageDto<TradeSupply> pageSupplyByCompany(String categoryCode, Integer cid, PageDto<TradeSupply> page) {
//		Assert.notNull(page, "the page can not be null");
//		page.setRecords(tradeSupplyDao.querySupplyByCompany(categoryCode, null, null, cid, page));
//		page.setTotals(tradeSupplyDao.querySupplyByCompanyCount(categoryCode, null, null, cid));
//		return page;
//	}
	
//	@Override
//	public PageDto<TradeSupply> pageSupplyByCompany(String categoryCode, Integer group, String keywords, Integer cid, PageDto<TradeSupply> page) {
//		Assert.notNull(page, "the page can not be null");
//		page.setRecords(tradeSupplyDao.querySupplyByCompany(categoryCode,group, keywords, cid, page));
//		page.setTotals(tradeSupplyDao.querySupplyByCompanyCount(categoryCode, group, keywords, cid));
//		return page;
//	}

//	@Override
//	public List<TradeSupplyDto> querySimpleSupplyByIds(Integer[] id) {
//		List<TradeSupplyDto> list = new ArrayList<TradeSupplyDto>();
//		do {
//			if(id == null || id.length<=0){
//				break;
//			}
//			for (Integer i:id) {
//				Assert.notNull(i, "the id[i] can not be null");
//				TradeSupplyDto tradeSupplyDto = tradeSupplyDao .queryShortDetailsById(i);
//				
//				if (tradeSupplyDto != null) {
//					tradeSupplyDto.setBusinessName(ParamUtils.getInstance().getValue("comp_business_type", tradeSupplyDto.getBusinessCode()));
//					list.add(tradeSupplyDto);
//				}
//			}
//		} while (false);
//		return list;
//	}

//	@Override
//	public TradeSupplyDto queryLongDetailsById(Integer id) {
//		Assert.notNull(id, "the id can not be null");
//		TradeSupplyDto tradeSupplyDto = tradeSupplyDao.queryLongDetailsById(id);
//		return tradeSupplyDto;
//	}

//	@Override
//	public TradeSupplyDto queryShortDetailsById(Integer id) {
//		Assert.notNull(id, "the id can not be null");
//		TradeSupplyDto tradeSupplyDto = tradeSupplyDao.queryShortDetailsById(id);
//		return tradeSupplyDto;
//	}

	@Override
	public Integer updatePhotoCoverById(Integer id, String photoCover) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(photoCover, "the photoCover can not be null");
		return tradeSupplyDao.updatePhotoCoverById(id, photoCover);
	}

//	@Override
//	public PageDto<TradeSupply> pageSupplyByConditions(Integer cid,
//			Integer pauseStatus,Integer overdueStatus, Integer checkStatus, Integer groupId,
//			PageDto<TradeSupply> page) {
//		Assert.notNull(cid, "the cid can not be null");
//		Assert.notNull(page, "the page can not be null");
//		page.setRecords(tradeSupplyDao.querySupplyByConditions(cid, pauseStatus, overdueStatus, checkStatus,null, groupId, page));
//		page.setTotals(tradeSupplyDao.querySupplyByConditionsCount(cid, pauseStatus, overdueStatus, checkStatus,null, groupId));
//		return page;
//	}
	
//	@Override
//	public PageDto<TradeSupply> pageSupplyByConditions(Integer cid,
//			Integer pauseStatus,Integer overdueStatus, Integer checkStatus, Integer recommend, Integer groupId,
//			PageDto<TradeSupply> page) {
//		Assert.notNull(cid, "the cid can not be null");
//		Assert.notNull(page, "the page can not be null");
//		page.setRecords(tradeSupplyDao.querySupplyByConditions(cid, pauseStatus, overdueStatus, checkStatus,recommend, groupId, page));
//		page.setTotals(tradeSupplyDao.querySupplyByConditionsCount(cid, pauseStatus, overdueStatus, checkStatus,recommend, groupId));
//		return page;
//	}

//	@Override
//	public Integer updateBaseSupplyById(TradeSupply supply, Integer id, Integer cid) {
//		Assert.notNull(id, "the id can not be null");
//		Assert.notNull(cid, "the cid can not be null");
//		Assert.notNull(supply, "the supply can not be null");
//		Assert.notNull(supply.getTitle(), "the title can not be null");
//		// TODO 检查敏感词，设置审核状态
//		supply.setCheckStatus((short)TradeSupplyService.STATUS_CHECK_YES);
//		// 设置详细信息的查询文本（提取详细信息的部分纯文本信息）
//		if (StringUtils.isNotEmpty(supply.getDetails())) {
//			String string = Jsoup.clean(supply.getDetails(), Whitelist.none());
//			string = string.replace(" ", "");
//			if (string.length() > 500) {
//				supply.setDetailsQuery(string.substring(0,500));
//			} else {
//				supply.setDetailsQuery(string);
//			}
//		}
//		return tradeSupplyDao.updateBaseSupplyById(supply, id, cid);
//	}

//	@Override
//	public Integer updateCategoryById(String category,String propertyValue, Integer id, Integer cid) {
//		Assert.notNull(id, "the id can not be null");
//		Assert.notNull(cid, "the cid can not be null");
//		Assert.notNull(category, "the category can not be null");
//		// 更新供应信息类别
//		return tradeSupplyDao.updateCategoryById(category,propertyValue, id, cid);
//	}

	
//	@Override
//	public Integer updatePauseStatusById(Integer id, Integer cid, Integer newStatus) {
//		Assert.notNull(id, "the id can not be null");
//		Assert.notNull(cid, "the cid can not be null");
//		Assert.notNull(newStatus, "the id[] can not be null");
//		return tradeSupplyDao.updatePauseStatusById(id, cid, newStatus);
//	}

//	@Override
//	public Integer updateRefreshById(Integer id,  Integer cid) {
//		Assert.notNull(id, "the id can not be null");
//		return tradeSupplyDao.updateRefreshById(id, cid);
//	}

//	@Override
//	public Integer updateSupplyGroupIdById(Integer id, Integer cid, Integer gid) {
//		Assert.notNull(id, "the id can not be null");
//		Assert.notNull(cid, "the cid can not be null");
//		Assert.notNull(gid, "the gid can not be null");
//		return tradeSupplyDao.updateSupplyGroupIdById(id, cid, gid);
//	}

//	@Override
//	public boolean insertTradeSupply(TradeSupply tradeSupply) {
//		if(createTradeSupply(tradeSupply)>0) return true;
//		else return false;
//	}

//	@Override
//	public Integer deleteSupplyById(Integer id) {
//		Assert.notNull(id, "the id can not be null");
//		return tradeSupplyDao.deleteSupplyById(id);
//	}
	
//	@Override
//	public PageDto<TradeSupplySearchDto> pageSupplyBySearch(SearchSupplyDto search,
//			PageDto<TradeSupplySearchDto> page) throws SolrServerException {
//		SolrServer server = SorlUtil.getInstance().getSolrServer("/tradesupply");
//		SolrQuery query = new SolrQuery();
//
//		//关键字不为空时，有高亮显示
//		if (StringUtils.isNotEmpty(search.getKeywords())) {
//			String keywords = buildKeyWord(search.getKeywords());
//			if (StringUtils.isEmpty(keywords)) {
//				return page;
//			}
//			query.setQuery(keywords);
//			query.setHighlight(true);
//            query.addHighlightField("title");
//            query.addHighlightField("detailsQuery");
//            query.setHighlightSimplePre("<em>"); 
//            query.setHighlightSimplePost("</em>");
//		} else {
//			query.setQuery("*:*");
//		}
//		query.addFilterQuery("delStatus:0");
//		query.addFilterQuery("checkStatus:1");
//		query.addFilterQuery("pauseStatus:0");
//		if (StringUtils.isNotEmpty(search.getCategory())) {
//			if (search.getCategory().length() == 4) {
//				query.addFilterQuery("category4:"+search.getCategory());
//			} else if(search.getCategory().length() == 8) {
//				query.addFilterQuery("category8:"+search.getCategory());
//			} else if(search.getCategory().length() == 12) {
//				query.addFilterQuery("category12:"+search.getCategory());
//			} else if(search.getCategory().length() == 16) {
//				query.addFilterQuery("category16:"+search.getCategory());
//			} else if(search.getCategory().length() == 20) {
//				query.addFilterQuery("category20:"+search.getCategory());
//			} else {
//				query.addFilterQuery("category4:"+search.getCategory());
//			}
//		}
//		if (StringUtils.isNotEmpty(search.getRegion())) {
//			query.addFilterQuery("provinceCode:"+search.getRegion()); 
//		}
//		if (search.getPriceFrom() != null && search.getPriceTo() != null) {
//			query.addFilterQuery("priceNum:"+"["+search.getPriceFrom()+" TO "+search.getPriceTo()+"]");
//		} else if (search.getPriceFrom() != null && search.getPriceTo() == null ) {
//			query.addFilterQuery("priceNum:"+"["+search.getPriceFrom()+" TO *]"); 
//		} else if (search.getPriceFrom() != null && search.getPriceTo() == null ) {
//			query.addFilterQuery("priceNum:"+"[0 TO "+search.getPriceTo()+"]");
//		}
//		// 按专业属性搜索
//		if (StringUtils.isNotEmpty(search.getPropertyValue())) {
//			String propertyValueArr[] = search.getPropertyValue().split(":");
//			for (String propertyValue:propertyValueArr) {
//				if (StringUtils.isNotEmpty(propertyValue)) {
//					query.addFilterQuery("propertyQuery:"+propertyValue);
//				}
//			}
//		}
//		//搜索按公司分组
//		if (search.getGroup() != null && search.getGroup() == 1) {
//
//		}
//		if ("1".equals(page.getSort())) {
//			query.addSortField("priceNum", ORDER.desc);
//		} else if("2".equals(page.getSort())){
//			query.addSortField("priceNum", ORDER.asc);
//		}
//		if (search.getTime() != null && search.getTime() > 0) {
//			query.addSortField("gmtRefresh", ORDER.desc);
//		}
//		query.addSortField("memberCode", ORDER.desc);
//		if(StringUtils.isNotEmpty(search.getKeywords())) {
//			query.addSortField("score", ORDER.desc);
//			query.addSortField("gmtRefresh", ORDER.desc);
//		} else {
//			query.addSortField("gmtRefresh", ORDER.desc);
//			query.addSortField("score", ORDER.desc);
//		}
//
//		query.setStart(page.getStart());
//		query.setRows(page.getLimit());
//		QueryResponse rsp = server.query(query);
//		List<TradeSupplySearchDto> beans = rsp.getBeans(TradeSupplySearchDto.class);
//		page.setTotals((int)rsp.getResults().getNumFound());
//		//高亮显示(替换普通文本)
//		if (StringUtils.isNotEmpty(search.getKeywords())) {
//			Map<String,Map<String,List<String>>> high = rsp.getHighlighting();
//			for (TradeSupplySearchDto bean : beans) {
//				Map<String,List<String>> root = high.get(bean.getId().toString());
//				if ( root != null) {
//					if (root.get("title") != null) {
//						bean.setTitle(root.get("title").get(0));
//					}
//					if (root.get("detailsQuery") != null) {
//						bean.setDetailsQuery(root.get("detailsQuery").get(0));
//					}
//				}
//			}
//		}
//		page.setRecords(buildBeans(beans));
//		return page;
//	}
	
//	private List<TradeSupplySearchDto> buildBeans(
//			List<TradeSupplySearchDto> beans) {
//		for (TradeSupplySearchDto bean : beans) {
//			bean.setProvinceName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, bean.getProvinceCode()));
//			bean.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, bean.getAreaCode()));
//		}
//		return beans;
//	}

//	private String buildKeyWord(String keywords){
//		//`~!@#$%^&*()+=|{}':;',\\[\\].<>/?
//		String regEx="[~!@#$%^&*()+=|{}':;',\\[\\].<>/?]";
//		Pattern   p   =   Pattern.compile(regEx);
//		Matcher   m   =   p.matcher(keywords);
//		return   m.replaceAll("").trim();
//	}

//	@Override
//	public TradeSupplyDto queryUpdateSupplyById(Integer id, Integer cid) {
//		Assert.notNull(id, "the id can not be null");
//		Assert.notNull(cid, "the cid can not be null");
//		TradeSupplyDto tradeSupplyDto = tradeSupplyDao.queryUpdateSupplyById(id, cid);
//		if (tradeSupplyDto != null) {
//			tradeSupplyDto.setPropertyValue(buildPropertyValues(tradeSupplyDto.getSupply().getPropertyQuery()));
//		}
//		return tradeSupplyDto;
//	}


//	private Map<Integer, Object> buildPropertyValues(String propertyQuery) {
//		Map<Integer, Object> map = new HashMap<Integer, Object>();
//		if (StringUtils.isNotEmpty(propertyQuery)) {
//			String[] propertys = propertyQuery.split(";");
//			for (int i = 0; i < propertys.length; i++) {
//				String property = propertys[i];
//				if (StringUtils.isNotEmpty(property)) {
//					String[] value = property.split("_");
//					if (value.length > 1) {
//						map.put(Integer.valueOf(value[0]), value[1]);
//					}
//				}
//			}
//		}
//		return map;
//	}

	@Override
	public Integer updateStatusOfTradeSupply(Integer id, Integer checkStatus) {
		return tradeSupplyDao.updateStatusOfTradeSupply(id, checkStatus);
	}

//	@Override
//	public PageDto<TradeSupplyDto> pageSupplyByCategoryCode(
//			String categoryCode, PageDto<TradeSupplyDto> page) {
//		TradeSupplyDto dto = new TradeSupplyDto(); 
//		TradeSupply tradeSupply=new TradeSupply();
//		
//		tradeSupply.setCategoryCode(categoryCode);
//		
//		dto.setSupply(tradeSupply);
//		
//		List<TradeSupplyDto> list=tradeSupplyDao.querySupplyByCategoryCode(dto,page);
//		for(TradeSupplyDto obj:list){
//			obj.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, obj.getSupply().getCategoryCode()));
//		}
//		page.setRecords(list);
//		page.setTotals(tradeSupplyDao.querySupplysCount(dto));
//		
//		return page;
//	}

	@Override
	public PageDto<TradeSupplyDto> pageSupplyByCategoryCodeAndTitleAndCheckStatus(
			String categoryCode, Integer cid,PageDto<TradeSupplyDto> page, String title,
			Integer checkStatus,Integer delStatus, String codeBlock , Integer infoComeFrom, String gmtPublishStart,
			String gmtPublishEnd,String queryType,String memberCode,Short recommendType,String compName,String subRecommend,String checkAdmin) {
		TradeSupplyDto dto = new TradeSupplyDto(); 
		TradeSupply supply = new TradeSupply();
		if (compName !=null && StringUtils.isNotEmpty(compName)){
			dto.setCompName(compName);
		}
		supply.setCategoryCode(categoryCode);
		supply.setTitle(title);
		if (checkStatus!=null){
			supply.setCheckStatus(checkStatus.shortValue());
		}
		if (delStatus!=null){
			supply.setDelStatus(delStatus.shortValue());
		}
		if(StringUtils.isNotEmpty(checkAdmin)){
			supply.setCheckAdmin(checkAdmin);
		}
		if(cid != null){
            supply.setCid(cid);
        }
		supply.setInfoComeFrom(infoComeFrom);
		dto.setSupply(supply);
		
		List<TradeSupplyDto> list=tradeSupplyDao.querySupplyByCategoryCodeAndTitleAndCheckStatus(dto, page,gmtPublishStart, gmtPublishEnd,queryType,memberCode,recommendType,subRecommend, codeBlock);
		
		for(TradeSupplyDto obj:list){
		    String status = "";
		    String compNameStatus = "";
			do {
			    if(obj == null) {
			        break;
			    }
			    if (obj.getSupply() == null) {
			        break;
			    }
			    // 公司是否已删除
			    if (StringUtils.isNotEmpty(obj.getMemberCodeBlock()) && 
			            obj.getMemberCodeBlock() != null) {
			        compNameStatus = "(已冻结)";
                    
                }
                if (obj.getIsDel() == 1) {
                    compNameStatus += "(已删除)";
                }
                obj.setCompName(obj.getCompName() + compNameStatus);
                
			    // 信息已删除 或者 账户被禁用
			    if(obj.getSupply().getDelStatus() == 1) {
			        status = "(已删除)";
                    break;
			    }
			    if (obj.getSupply().getPauseStatus() ==1) {
			        status = "(暂不发布)";
                    break;
			    }
			    // 是否过期(老数据错误)
			    if(obj.getSupply().getValidDays()==null||obj.getSupply().getValidDays()==0){
			    	break;
			    }
			    if (obj.getSupply().getGmtExpired() != null) {
			        long expired = obj.getSupply().getGmtExpired().getTime();
	                long today = new Date().getTime();
	                long result = today - expired;
	                if (result > 0) {
	                    status = "(已过期)";
	                    break;
	                }
			    } else {
			        status = "(已过期)";
			    }
			} while (false);
			obj.getSupply().setTitle(Jsoup.clean(obj.getSupply().getTitle(), Whitelist.none()));
			obj.getSupply().setTitle(obj.getSupply().getTitle() + status);
			obj.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, obj.getSupply().getCategoryCode()));
		}
		
		page.setRecords(list);
		page.setTotals(tradeSupplyDao.querySupplysCountByTitleAndCheckStatus(dto, page, gmtPublishStart, gmtPublishEnd,queryType,memberCode,recommendType,subRecommend,codeBlock ));
		
		return page;
	}

	@Override
	public Integer querySupplyCount(Integer companyId) {
		return tradeSupplyDao.querySupplyCount(companyId);
	}

//	@Override
//	public Integer querySupplyCountByCategory(String categoryCode) {
//		Assert.notNull(categoryCode, "the categoryCode can not be null");
//		return tradeSupplyDao.querySupplyCountByCategory(categoryCode);
//	}

	@Override
	public PageDto<TradeSupplyDto> pageSupplyByAdmin(Integer cid, String title,
			Integer checkStatus,Short type, PageDto<TradeSupplyDto> page) {
		TradeSupply tradeSupply=new TradeSupply();
		tradeSupply.setCid(cid);
		tradeSupply.setTitle(title);
		if(checkStatus != null) {
			tradeSupply.setCheckStatus(checkStatus.shortValue());
		}
		List<TradeSupplyDto> list = tradeSupplyDao.querySupplyByAdmin(page, tradeSupply,type);
		for(TradeSupplyDto obj:list){
			obj.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE, obj.getSupply().getCategoryCode()));
		}
		Integer total = tradeSupplyDao.querySupplyByAdminCount(tradeSupply,type);
		page.setRecords(list);
		page.setTotals(total);
		return page;
	}

//	@Override
//	public Integer queryAllSupplyCount() {
//		return tradeSupplyDao.queryAllSupplyCount();
//	}

//	@Override
//	public Integer queryWeekSupplyCount() {
//		return tradeSupplyDao.queryWeekSupplyCount();
//	}

	@Override
	public TradeSupply queryOneSupplyById(Integer id) {
		return tradeSupplyDao.queryOneSupplyById(id);
	}

	@Override
	public Integer updateTradeSupply(TradeSupply supply) {
		return tradeSupplyDao.updateTradeSupply(supply);
	}

	@Override
	public Integer queryCidById(Integer id) {
		return tradeSupplyDao.queryCidById(id);
	}

	@Override
	public Integer updateUnPassCheckStatus(Integer id, Integer checkStatus,
			String checkAdmin, String checkRefuse) {
		return tradeSupplyDao.updateUnPassCheckStatus(id,checkStatus,checkAdmin,checkRefuse);
	}

	@Override
	public Integer updateDelStatus(Integer id, Integer delStatus) {
		return tradeSupplyDao.updateDelStatus(id,delStatus);
	}

	@Override
	public String queryCategoryCodeById(Integer id) {
		return tradeSupplyDao.queryCategoryCodeById(id);
	}

	@Override
	public Integer refreshSupply(Integer id) throws ParseException {
		Assert.notNull(id, "id不能为空");
		TradeSupply supply = tradeSupplyDao.queryOneSupplyById(id);
        Date gmtExpired = supply.getGmtExpired();
        long refreshTime = new Date().getTime() + 20 * 24 * 60 * 60 *1000;
        Date refreshDate = new Date(refreshTime);
        int validDays = supply.getValidDays();
        if (gmtExpired != null) {
            long expired = gmtExpired.getTime();
            long today = new Date().getTime();
            long newExpired = expired + 20 * 24 * 60 * 60 *1000;
            Date expiredDate = new Date(newExpired);
            Date date = new Date();
            try {
                date = DateUtil.getDate("9999-12-30 00:00:00", "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //过期
            if (today-expired > 0) {
                gmtExpired = refreshDate;
                validDays = 20;
            } else if (!gmtExpired.equals(date)){
                gmtExpired = expiredDate;
                validDays = 20 + validDays;
            }
        } else {
            gmtExpired = refreshDate;
            validDays = 20;
        }
        if(gmtExpired.getTime() > DateUtil.getDate("9999-12-30", "yyyy-MM-dd").getTime()){
        	gmtExpired = DateUtil.getDate("9999-12-30", "yyyy-MM-dd");
        }
		return tradeSupplyDao.updateGmtRefresh(id , gmtExpired , validDays);
	}

//	@Override
//	public Integer updatePropertyQueryById(Integer id, String properyValue) {
//		Assert.notNull(id, "id不能为空");
//		return tradeSupplyDao.updatePropertyQueryById(id, properyValue);
//	}

	@Override
	public String queryPropertyQueryById(Integer id) {
		return tradeSupplyDao.queryPropertyQueryById(id);
	}
	
	@Override
	public Integer updatePropertyQueryAllById(Integer supplyId, String query) {
		Assert.notNull(supplyId, "supplyId不能为空");
		return tradeSupplyDao.updatePropertyQueryAllById(supplyId, query);
	}
	
//	@Override
//	public List<CompProfile> queryCompByKeywords(String keywords, Integer size) {
//		return tradeSupplyDao.queryCompByKeywords(keywords, size);
//	}

	@Override
	public boolean updateSupplyCategory(Integer id, String categoryCode) {
		if (tradeSupplyDao.updateCategoryCodeById(id, categoryCode) > 0)
			return true;
		else
			return false;
	}
	public List<TradeSupply> querySupplyByKeywords(String keywords, Integer size) {
		return tradeSupplyDao.querySupplyByKeywords(keywords,size);
	}

//	@Override
//	public List<TradeSupplyDto> queryCompsByKeywords(String keywords,
//			Integer size) {
//		return tradeSupplyDao.querySupplysByKeywords(keywords,size);
//	}

//	@Override
//	public List<TradeSupply> queryRecommendSupply(Integer cid, Integer size) {
//		return tradeSupplyDao.queryRecommendSupply(cid,size);
//	}
	
//	@Override
//	public List<TradeSupply> queryRecommendSupplyC(Integer cid, Integer size) {
//		return tradeSupplyDao.queryRecommendSupplyC(cid,size);
//	}

//	@Override
//	public PageDto<TradeSupply> pageSupplyByCompany(String category, String keywords, Integer cid, PageDto<TradeSupply> page) {
//		return pageSupplyByCompany(category, null, keywords, cid, page);
//	}

	@Override
	public List<TradeSupplyDto> querySimpleByKeyword(String keywords,String compName,Integer loginCount) {
	    List<TradeSupply> list=tradeSupplyDao.querySimplyByKeywords(keywords,compName,loginCount);
	    List<TradeSupplyDto> dtoList= new ArrayList<TradeSupplyDto>();
	    TradeSupplyDto tradeDto=null;
	    CompProfileDto dto=null;
	    TradeSupply supply=null;
	    Integer count=0;
	    Map<Integer,CompProfileDto > compMap = new HashMap<Integer, CompProfileDto>();
		for(TradeSupply trade:list){
			if (compMap.get(trade.getCid())==null){
		         CompProfile comp  = compProfileDao.querySimpProfileById(trade.getCid());
		         if (comp!=null){
		        	 count = compAccountDao.queryLoginCountByCid(trade.getCid());
		        	 tradeDto=new TradeSupplyDto();
		        	 dto=new CompProfileDto();
		        	 dto.setCompProfile(comp);
		        	 if (comp.getRegisterCode()!=null){
		        		 dto.setRegisterSource(paramDao.queryNameByTypeAndValue("register_type", comp.getRegisterCode()));
		        	 }else {
		        		 dto.setRegisterSource("未知信息来源");
		        	 }
		        	 compMap.put(trade.getCid(),dto);
		         }else {
					continue;
				}
			}
			supply=new TradeSupply();
			supply.setId(trade.getId());
			supply.setCid(trade.getCid());
			supply.setUid(trade.getUid());
			supply.setTitle(trade.getTitle());
			tradeDto.setCompName(compMap.get(trade.getCid()).getCompProfile().getName());
			tradeDto.setReceiveTime(compMap.get(trade.getCid()).getCompProfile().getReceiveTime());
			tradeDto.setSendTime(compMap.get(trade.getCid()).getCompProfile().getSendTime());
			tradeDto.setRegisterSource(compMap.get(trade.getCid()).getRegisterSource());
			tradeDto.setLoginCount(count);
			tradeDto.setSupply(supply);
			tradeDto.setMemberCode(compMap.get(trade.getCid()).getCompProfile().getMemberCode());
			dtoList.add(tradeDto);
		}
	    return dtoList;

	}

//	@Override
//	public List<TradeSupplyDto> queryNewSimplySupply(String parentCode,
//			Integer size) {
//		if (size==null){
//			size=10;
//		}
//		return tradeSupplyDao.queryNewSimplySupply(parentCode,size);
//	}

	@Override
	public Integer updateGmtmodifiedBySvrClose(Integer cid) {
		return tradeSupplyDao.updateGmtmodifiedBySvrClose(cid);
	}

	@Override
	public List<TradeSupply> queryTopNewestSupply(Integer cid,Integer max) {
		if (max == null) {
			max = MAX_SIZE;
		}
		return tradeSupplyDao.queryNewestSupply(cid,max);
	}

//	@Override
//	public List<TradeSupply> queryNewestSupply(String category, Integer size) {
//		if (size == null) {
//			size = MAX_SIZE;
//		}
//		return tradeSupplyDao.queryNewestSupply(category,size);
//	}

//	@Override
//	public List<TradeSupplySearchDto> queryMatchSupply(String keywords, Integer pageSize) throws SolrServerException {
//		SolrServer server = SorlUtil.getInstance().getSolrServer("/tradesupply");
//		SolrQuery query = new SolrQuery();
//		//关键字不为空时，有高亮显示
//		if (StringUtils.isNotEmpty(keywords)){
//			keywords = buildKeyWord(keywords);
//			query.setQuery(keywords);
//		} else {
//			query.setQuery("*:*");
//		}
//		query.addSortField("memberCode", ORDER.desc);
//		query.addSortField("score", ORDER.desc);
//		query.setStart(0);
//		query.setRows(pageSize);
//		QueryResponse rsp = server.query(query);
//		List<TradeSupplySearchDto> beans = rsp.getBeans(TradeSupplySearchDto.class);
//		return beans;
//	}

	@Override
	public TradeSupply queryPropertyQueryAndCategoryCodeById(Integer id) {
		return tradeSupplyDao.queryPropertyQueryAndCategoryCodeById(id);
	}

	@Override
	public Integer queryMaxId() {
		return tradeSupplyDao.queryMaxId();
	}

	@Override
	public TradeKeyword buildTradeKeyword(Integer sid) {
		TradeSupply supply =tradeSupplyDao.queryShortBwDetailsById(sid);
		CompProfile profile =compProfileDao.queryShortCompDetailsById(supply.getCid()); 
		TradeKeyword tradeKeyword= new TradeKeyword();
		tradeKeyword.setCid(supply.getCid());
		tradeKeyword.setCname(profile.getName());
		tradeKeyword.setDomainTwo(profile.getDomainTwo());
		tradeKeyword.setGmtRegister(profile.getGmtCreated());
		tradeKeyword.setMemberCode(profile.getMemberCode());
		tradeKeyword.setTitle(supply.getTitle());
		tradeKeyword.setTargetId(sid);
		tradeKeyword.setAreaCode(supply.getAreaCode());
		tradeKeyword.setProvinceCode(supply.getProvinceCode());
		tradeKeyword.setPriceNum(supply.getPriceNum());
		tradeKeyword.setPriceUnits(supply.getPriceUnits());
		tradeKeyword.setPhotoCover(supply.getPhotoCover());
		tradeKeyword.setDetailsQuery(supply.getDetailsQuery());
		tradeKeyword.setCreditFile(creditFileDao.queryCreditFileCount(supply.getCid(), (short)1));
		return tradeKeyword;
	}

	@Override
	public PageDto<TradeSupply> pageSupplys(PageDto<TradeSupply> page) {
		page.setRecords(tradeSupplyDao.querySupplys(page));
		page.setTotals(tradeSupplyDao.querySupplysCount());
		return page;
	}

	@Override
	public List<TradeSupplyDto> queryCheckRefuse() {
		  List<Object> list=new ArrayList<Object>();
		  List<TradeSupplyDto> tradeDtoList=new ArrayList<TradeSupplyDto>();

		  list.add("供求信息重复发布。重复发布信息将十分不利于您的产品的排名，相同的产品（包括同一种型号但不同地点),我们建议您只发布一次。通过“刷新”功能，可大大提升您信息的排名，使您的信息被更多商家查看到。");
		  list.add("供求信息包含联系方式（如QQ，电话，手机，其他网址等）。信息描述仅允许填写您产品的详细情况及您的合作诚意。");
		  list.add("您发布的产品与本网站不符。环保平台只供发布与环保行业相关的的产品、设备等信息。如果您的产品与此相关联，请在供求描述中注明。");
	      list.add("供求信息产品名称不明确，建议您一条信息只填写一种类型的具体产品。");
	      list.add("信息重复发布，重复包括：标题、产品描述等。重复发布信息将十分不利于您的产品的排名，不同型号的产品我们建议您在产品描述中注明。");
	      list.add("供求信息产品描述不明确，建议您通过产品规格，技术参数等方面详细介绍产品。");
	      list.add("您的产品名称与产品描述不吻合,建议您修改相关内容。");
	      list.add("您发布的信息属于技术文章，建议您在技术文章版块重新发布。");
	      list.add("您发布的信息属于展会信息，如有意合作，请致电0571-56633090或在线联系我们客服。");
	      list.add("您发布的信息属于供应信息，建议您在发布供应信息 版块重新发布，以免错失商机。");
	      list.add("您发布的产品图片含有联系电话或其它网站网址。建议您发布清晰并且不含电话及其它网址的产品图片。");
	      list.add("您发布的产品图片不清晰.为使您的产品信息能更准确地传递给您的潜在客户,请重新发布清晰的产品。");
	      list.add("您发布的图片与产品信息内容无关。建议您重新发布与您产品相符的清晰的产品图片。");
	      list.add("您的产品图片重复发布，将十分不利于您的产品展示。同一张产品图片我们只建议您发布一次。");
		  
	      for (Object object : list) {
	    	  TradeSupplyDto dto=new TradeSupplyDto();
	    	  dto.setCheckAllRefuse(object.toString());
	    	  tradeDtoList.add(dto);
		  }

		return tradeDtoList;
	}

}