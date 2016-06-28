/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-16
 */
package com.zz91.ep.admin.service.trade.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.trade.TradeBuyDao;
import com.zz91.ep.admin.service.trade.TradeBuyService;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradeBuyDto;
import com.zz91.util.Assert;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;

/**
 * @author totly
 * 
 *         created on 2011-9-16
 */
@Component("tradeBuyService")
public class TradeBuyServiceImpl implements TradeBuyService {

	@Resource
	private TradeBuyDao tradeBuyDao;

	final static String TRADE_BUY_TYPE = "trade_buy_type";

	@Override
	public Integer createTradeBuy(TradeBuy tradeBuy) {
		Assert.notNull(tradeBuy, "the tradeBuy can not be null");
		Assert.notNull(tradeBuy.getUid(), "the uid can not be null");
		Assert.notNull(tradeBuy.getUid(), "the cid can not be null");
		Assert.notNull(tradeBuy.getCategoryCode(),
				"the categoryCode can not be null");
		// 设置过期时间
		if (tradeBuy.getValidDays() == null || tradeBuy.getValidDays() == 0) {
			try {
				tradeBuy.setGmtExpired(DateUtil.getDate("9999-12-30",
						"yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			tradeBuy.setGmtExpired(DateUtil.getDateAfterDays(new Date(),
					tradeBuy.getValidDays()));
		}
		// 设置发布时间
		tradeBuy.setGmtPublish(new Date());
		// 设置刷新时间
		tradeBuy.setGmtRefresh(new Date());
		tradeBuy.setCheckStatus((short) TradeBuyService.STATUS_CHECK_UN);
		tradeBuy.setPauseStatus((short) TradeBuyService.STATUS_PAUSE_NO);
		// 设置详细信息的查询文本（提取详细信息的部分纯文本信息）
		if (StringUtils.isNotEmpty(tradeBuy.getDetails())) {
			tradeBuy.setDetailsQuery(Jsoup.parse(tradeBuy.getDetails()).body()
					.text());
		}
		return tradeBuyDao.insertTradeBuy(tradeBuy);
	}

	// @Override
	// public TradeBuy queryBuyDetailsById(Integer id) {
	// Assert.notNull(id, "the id can not be null");
	// return tradeBuyDao.queryBuyDetailsById(id);
	// }

	// @Override
	// public TradeBuy queryBuySimpleDetailsById(Integer id) {
	// Assert.notNull(id, "the id can not be null");
	// return tradeBuyDao.queryBuySimpleDetailsById(id);
	// }

	// @Override
	// public List<Integer> queryBuyListById(Integer cid, Integer max) {
	// if (max == null) {
	// max = MAX_SIZE;
	// }
	// Assert.notNull(cid, "the cid can not be null");
	// return tradeBuyDao.queryBuyListById(cid, max);
	// }

	// @Override
	// public List<TradeBuy> queryCommonBuyByCategory(String categoryCode,
	// Integer max) {
	// if (max == null) {
	// max = MAX_SIZE;
	// }
	// Assert.notNull(categoryCode, "the categoryCode can not be null");
	// return tradeBuyDao.queryCommonBuyByCategory(categoryCode, max);
	// }

	// @Override
	// public List<TradeBuy> queryNewestBuyByCategory(String category,
	// Boolean isDirect, Integer max) {
	// if (max == null) {
	// max = MAX_SIZE;
	// }
	// return tradeBuyDao.queryNewestBuyByCategory(category, isDirect, max);
	// }

	// @Override
	// public PageDto<TradeBuy> pageBuyByConditions(Integer cid,
	// Integer pauseStatus, Integer overdueStatus, Integer checkStatus,
	// PageDto<TradeBuy> page) {
	// Assert.notNull(cid, "the cid can not be null");
	// Assert.notNull(page, "the page can not be null");
	// page.setRecords(tradeBuyDao.queryBuyByConditions(cid, pauseStatus,
	// overdueStatus, checkStatus, page));
	// page.setTotals(tradeBuyDao.queryBuyByConditionsCount(cid, pauseStatus,
	// overdueStatus, checkStatus));
	// return page;
	// }

	// @Override
	// public Integer updateBaseBuyById(TradeBuy buy, Integer id, Integer cid) {
	// Assert.notNull(id, "the id can not be null");
	// Assert.notNull(cid, "the cid can not be null");
	// Assert.notNull(buy, "the buy can not be null");
	// buy.setCheckStatus((short)TradeBuyService.STATUS_CHECK_UN);
	// return tradeBuyDao.updateBaseBuyById(buy, id, cid);
	// }

	// @Override
	// public Integer updateCategoryById(Integer id, String category, Integer
	// cid) {
	// Assert.notNull(id, "the id can not be null");
	// Assert.notNull(cid, "the cid can not be null");
	// return tradeBuyDao.updateCategoryById(id, category,cid);
	// }

	// @Override
	// public Integer updatePauseStatusById(Integer id, Integer cid, Integer
	// newStatus) {
	// Assert.notNull(id, "the id can not be null");
	// Assert.notNull(cid, "the cid can not be null");
	// Assert.notNull(newStatus, "the newStatus can not be null");
	// return tradeBuyDao.updatePauseStatusById(id, cid, newStatus);
	// }

	// @Override
	// public Integer updateRefreshById(Integer id, Integer cid) {
	// Assert.notNull(id, "the id can not be null");
	// Assert.notNull(cid, "the cid can not be null");
	// return tradeBuyDao.updateRefreshById(id, cid);
	// }

	// @Override
	// public TradeBuy queryUpdateBuyById(Integer id, Integer cid) {
	// Assert.notNull(id, "the id can not be null");
	// Assert.notNull(cid, "the cid can not be null");
	// return tradeBuyDao.queryUpdateBuyById(id, cid);
	// }

	// @Override
	// public PageDto<TradeBuySearchDto> pageBuyBySearch(SearchBuyDto search,
	// PageDto<TradeBuySearchDto> page) throws SolrServerException {
	// SolrServer server = SorlUtil.getInstance().getSolrServer("/tradebuy");
	// SolrQuery query = new SolrQuery();
	// //关键字不为空时，有高亮显示
	// if (StringUtils.isNotEmpty(search.getKeywords())) {
	// String keywords = buildKeyWord(search.getKeywords());
	// if (StringUtils.isEmpty(keywords)) {
	// return page;
	// }
	// query.setQuery(keywords);
	// query.setHighlight(true);
	// query.addHighlightField("title");
	// query.setHighlightSimplePre("<em>");
	// query.setHighlightSimplePost("</em>");
	// } else {
	// query.setQuery("*:*");
	// }
	// query.addFilterQuery("delStatus:0");
	// query.addFilterQuery("checkStatus:1");
	// query.addFilterQuery("pauseStatus:0");
	// if (StringUtils.isNotEmpty(search.getRegion())) {
	// query.addFilterQuery("provinceCode:"+search.getRegion());
	// }
	// if (search.getTime() != null && search.getTime() > 0) {
	// query.addSortField("gmtRefresh", ORDER.desc);
	// }
	// //排序
	// if ("1".equals(page.getSort())) {
	// query.addSortField("quantity", ORDER.desc);
	// } else if("2".equals(page.getSort())){
	// query.addSortField("quantity", ORDER.asc);
	// } else if("3".equals(page.getSort())){
	// query.addSortField("messageCount", ORDER.desc);
	// } else if("4".equals(page.getSort())){
	// query.addSortField("messageCount", ORDER.asc);
	// } else if("5".equals(page.getSort())){
	// query.addSortField("gmtRefresh", ORDER.desc);
	// } else if("6".equals(page.getSort())){
	// query.addSortField("gmtRefresh", ORDER.asc);
	// } else {
	// query.addSortField("gmtRefresh", ORDER.desc);
	// }
	// query.addSortField("score", ORDER.desc);
	// query.setStart(page.getStart());
	// query.setRows(page.getLimit());
	// QueryResponse rsp = server.query(query);
	// List<TradeBuySearchDto> beans = rsp.getBeans(TradeBuySearchDto.class);
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
	// page.setRecords(buildBeans(beans));
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// return page;
	// }

	// private List<TradeBuySearchDto> buildBeans(
	// List<TradeBuySearchDto> beans) throws ParseException {
	// for (TradeBuySearchDto bean : beans) {
	// bean.setProvinceName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA,
	// bean.getProvinceCode()));
	// bean.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA,
	// bean.getAreaCode()));
	// if (bean.getValidDays() != null && bean.getValidDays() != 0) {
	// if (bean.getGmtExpired() != null) {
	// bean.setHasEnable(DateUtil.getIntervalDays(bean.getGmtExpired(), new
	// Date()));
	// } else {
	// bean.setHasEnable(0);
	// }
	// }
	// }
	// return beans;
	// }

	// private String buildKeyWord(String keywords){
	// //`~!@#$%^&*()+=|{}':;',\\[\\].<>/?
	// String regEx="[~!@#$%^&*()+=|{}':;',\\[\\].<>/?]";
	// Pattern p = Pattern.compile(regEx);
	// Matcher m = p.matcher(keywords);
	// return m.replaceAll("").trim();
	// }

	// @Override
	// public TradeBuyDto buildTradeBuyDto(TradeBuy tradeBuy) {
	// TradeBuyDto dto = new TradeBuyDto();
	// dto.setBuyTypeName(ParamUtils.getInstance().getValue(TRADE_BUY_TYPE,
	// String.valueOf(tradeBuy.getBuyType())));
	// dto.setProvinceName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA,
	// tradeBuy.getProvinceCode()));
	// dto.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA,
	// tradeBuy.getAreaCode()));
	// dto.setSupplyAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA,
	// tradeBuy.getSupplyAreaCode()));
	// dto.setTradeBuy(tradeBuy);
	// dto.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE,
	// tradeBuy.getCategoryCode()));
	// return dto;
	// }

	// @Override
	// public List<TradeBuyDto> buildTradeBuyDtoList(List<TradeBuy> list) {
	// if (list==null) {
	// return null;
	// }
	// List<TradeBuyDto> listDto = new ArrayList<TradeBuyDto>();
	// for (TradeBuy tradeBuy : list) {
	// TradeBuyDto dto = new TradeBuyDto();
	// dto.setTradeBuy(tradeBuy);
	// dto.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE,
	// tradeBuy.getCategoryCode()));
	// dto.setProvinceName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA,
	// tradeBuy.getProvinceCode()));
	// dto.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA,
	// tradeBuy.getAreaCode()));
	// listDto.add(dto);
	// }
	// return listDto;
	// }

	// @Override
	// public PageDto<TradeBuyDto> pageBuyByCategoryCode(String categoryCode,
	// PageDto<TradeBuyDto> page) {
	// TradeBuyDto dto = new TradeBuyDto();
	// TradeBuy tradeBuy=new TradeBuy();
	//		
	// tradeBuy.setCategoryCode(categoryCode);
	//		
	// dto.setTradeBuy(tradeBuy);
	//		
	// List<TradeBuyDto> list=tradeBuyDao.queryBuyByCategoryCode(dto,page);
	// for (TradeBuyDto tradeBuyDto : list) {
	// tradeBuyDto.setCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_TRADE,
	// tradeBuyDto.getTradeBuy().getCategoryCode()));
	// }
	// page.setRecords(list);
	// page.setTotals(tradeBuyDao.queryBuyCount(dto));
	//		
	// return page;
	// }

	@Override
	public Integer updateStatusOfTradeBuy(Integer id, Integer checkStatus) {
		return tradeBuyDao.updateStatusOfTradeBuy(id, checkStatus);
	}
	@Override
	public Integer querySupplyCount(Integer companyId) {
		return tradeBuyDao.querySupplyCount(companyId);
	}

	@Override
	public PageDto<TradeBuyDto> pageBuyByAdmin(Integer cid, String title,
			Integer checkStatus, PageDto<TradeBuyDto> page) {

		TradeBuy tradeBuy = new TradeBuy();
		tradeBuy.setCid(cid);
		tradeBuy.setTitle(title);
		if (checkStatus != null) {
			tradeBuy.setCheckStatus(checkStatus.shortValue());
		}
		List<TradeBuyDto> list = tradeBuyDao
				.queryCompBuyByAdmin(tradeBuy, page);
		for (TradeBuyDto obj : list) {
			obj.setBuyTypeName(ParamUtils.getInstance().getValue(
					TRADE_BUY_TYPE,
					String.valueOf(obj.getTradeBuy().getBuyType())));
			obj.setProvinceName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, obj.getTradeBuy()
							.getProvinceCode()));
			obj.setAreaName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, obj.getTradeBuy()
							.getAreaCode()));
			obj.setSupplyAreaName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, obj.getTradeBuy()
							.getSupplyAreaCode()));
			obj.setCategoryName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_TRADE, obj.getTradeBuy()
							.getCategoryCode()));
		}

		page.setRecords(list);
		page.setTotals(tradeBuyDao.queryCompBuyByAdminCount(tradeBuy));
		return page;
	}

	@Override
	public PageDto<TradeBuyDto> pageBuyByCategoryCodeAndTitleAndCheckStatus(
			String categoryCode, Integer cid, PageDto<TradeBuyDto> page, String title,
			Integer checkStatus, Integer delStatus, String gmtPublishStart,
			String gmtPublishEnd, String queryType , String compName,String memberCode,Short recommendType, Integer infoComeFrom,
			Integer regComeFrom,String checkAdmin) {
		TradeBuyDto dto = new TradeBuyDto();
		TradeBuy tradeBuy = new TradeBuy();

		tradeBuy.setCategoryCode(categoryCode);
		tradeBuy.setTitle(title);
		if (compName !=null && StringUtils.isNotEmpty(compName)){
            dto.setCompName(compName);
        }
		if (memberCode !=null && StringUtils.isNotEmpty(memberCode)){
            dto.setMemberCode(memberCode);
        }
		if (checkStatus != null) {
			tradeBuy.setCheckStatus(checkStatus.shortValue());
		}
		if (delStatus != null) {
			tradeBuy.setDelStatus(delStatus.shortValue());
		}
		if(StringUtils.isNotEmpty(checkAdmin)){
			tradeBuy.setCheckAdmin(checkAdmin);
		}
		if(cid != null){
            tradeBuy.setCid(cid);
        }
		dto.setTradeBuy(tradeBuy);

		List<TradeBuyDto> list = tradeBuyDao
				.queryBuyByCategoryCodeAndTitleAndCheckStatus(dto, page,
						gmtPublishStart, gmtPublishEnd,queryType,recommendType,
						infoComeFrom, regComeFrom);
		for (TradeBuyDto obj : list) {
		    String status = "";
		    String compNameStatus = "";
            do {
                if(obj == null) {
                    break;
                }
                if (obj.getTradeBuy() == null) {
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
                if(obj.getTradeBuy().getDelStatus() == 1) {
                    status = "(已删除)";
                    break;
                }
                if (obj.getTradeBuy().getPauseStatus() ==1) {
                    status = "(暂不发布)";
                    break;
                }
                // 是否过期
                if (obj.getTradeBuy().getGmtExpired() != null) {
                    long expired = obj.getTradeBuy().getGmtExpired().getTime();
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
            obj.getTradeBuy().setTitle(obj.getTradeBuy().getTitle() + status);
			obj.setCategoryName(CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_TRADE, obj.getTradeBuy()
							.getCategoryCode()));
		}
		page.setRecords(list);
		page.setTotals(tradeBuyDao.queryBuysCountByTitleAndCheckStatus(dto,
				page, gmtPublishStart, gmtPublishEnd,queryType, recommendType,
				infoComeFrom, regComeFrom));

		return page;
	}

	@Override
	public String queryCategoryCodeById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer queryCidById(Integer id) {
		return tradeBuyDao.queryCidById(id);
	}

	@Override
	public Integer refreshBuy(Integer id) {
		Assert.notNull(id, "id不能为空");
		TradeBuy buy = tradeBuyDao.queryOneBuy(id);
		Date gmtExpired = buy.getGmtExpired();
        long refreshTime = new Date().getTime() + 20 * 24 * 60 * 60 *1000;
        Date refreshDate = new Date(refreshTime);
        int validDays = buy.getValidDays();
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
		return tradeBuyDao.updateGmtRefresh(id , gmtExpired , validDays);
	}

	@Override
	public Integer updateDelStatus(Integer id, Integer delStatus) {

		return tradeBuyDao.updateDelStatus(id, delStatus);
	}

	@Override
	public Integer updateUnPassCheckStatus(Integer intId,
			Integer intCheckStatus, String admin, String checkRefuse) {
		return tradeBuyDao.updateUnPassCheckStatus(intId, intCheckStatus,
				admin, checkRefuse);
	}

	@Override
	public Integer uupdateTradeBuy(TradeBuy buy) {
		return tradeBuyDao.updateTradeBuy(buy);
	}

	@Override
	public TradeBuy queryOneBuy(Integer id) {
		return tradeBuyDao.queryOneBuy(id);
	}

	@Override
	public boolean updateBuyCategory(Integer id, String categoryCode) {
		Assert.notNull(id, "id不能为空");
		Assert.notNull(categoryCode, "categoryCode不能为空");
		if (tradeBuyDao.updateCategoryCodeById(id, categoryCode) > 0)
			return true;
		else
			return false;
	}

	// @Override
	// public List<TradeBuy> queryRecommendBuy(Integer size) {
	// if (size == null) {
	// size = 10;
	// }
	// return tradeBuyDao.queryRecommendBuy(size);
	// }

	@Override
	public Integer updateGmtmodifiedBySvrClose(Integer cid) {
		return tradeBuyDao.updateGmtmodifiedBySvrClose(cid);
	}

	// @Override
	// public Integer updatePhotoCoverById(Integer id, String path, Integer cid)
	// {
	// return tradeBuyDao.updatePhotoCoverById(id,path,cid);
	// }

	// @Override
	// public List<TradeBuySearchDto> queryMatchBuy(String keywords, Integer
	// pageSize) throws SolrServerException {
	// SolrServer server = SorlUtil.getInstance().getSolrServer("/tradebuy");
	// SolrQuery query = new SolrQuery();
	// keywords = buildKeyWord(keywords);
	// if (StringUtils.isNotEmpty(keywords)){
	// query.setQuery(keywords);
	// } else {
	// query.setQuery("*:*");
	// }
	// query.addSortField("score", ORDER.desc);
	// query.setStart(0);
	// query.setRows(pageSize);
	// QueryResponse rsp = server.query(query);
	// List<TradeBuySearchDto> beans = rsp.getBeans(TradeBuySearchDto.class);
	// return beans;
	// }

	@Override
	public Integer queryMaxId() {
		return tradeBuyDao.queryMaxId();
	}
}