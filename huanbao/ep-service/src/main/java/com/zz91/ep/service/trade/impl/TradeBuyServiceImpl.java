/*
 * 文件名称：TradeBuyServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import com.zz91.ep.dao.trade.MessageDao;
import com.zz91.ep.dao.trade.TradeBuyDao;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.search.SearchBuyDto;
import com.zz91.ep.dto.trade.BuyMessageDto;
import com.zz91.ep.dto.trade.TradeBuyDto;
import com.zz91.ep.dto.trade.TradeBuyNormDto;
import com.zz91.ep.service.trade.TradeBuyService;
import com.zz91.ep.util.AreaUtil;
import com.zz91.util.Assert;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：交易中心求购信息实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("tradeBuyService")
public class TradeBuyServiceImpl implements TradeBuyService {

	final static Integer TEN_THOUSAND = 10000;
	final static Integer Fifty_THOUSAND = 50000;
	final static Integer ONE_HUNDRED_THOUSAND_LIMIT = 100000;
	final static Integer MILLIONS_LIMIT = 1000000;
	
	public final static String TRADE_BUY_TYPE="trade_buy_type";
	
	@Resource
	private TradeBuyDao tradeBuyDao;
	
	@Resource
	private MessageDao messageDao;
	
	@Override
	public List<CommonDto> queryBuysByRecommend(Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		return tradeBuyDao.queryBuysByRecommend(size);
	}

	@Override
	public List<CommonDto> queryNewestBuys(Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		return tradeBuyDao.queryNewestBuys(size);
	}

	@Override
	public List<TradeBuy> queryBuysDetailsByRecommend(Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		return tradeBuyDao.queryBuysDetailsByRecommend(size);
	}

	@Override
	public TradeBuyDto queryBuyDetailsById(Integer id) {
		TradeBuyDto tradebuy = tradeBuyDao.queryBuyDetailsById(id);
		if (tradebuy != null) {
			tradebuy.setProvinceName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, tradebuy.getTradeBuy().getProvinceCode()));
			tradebuy.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, tradebuy.getTradeBuy().getAreaCode()));
			tradebuy.setBuyTypeName(ParamUtils.getInstance().getValue(TRADE_BUY_TYPE, String.valueOf(tradebuy.getTradeBuy().getBuyType())));
			tradebuy.setSupplyAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, tradebuy.getTradeBuy().getSupplyAreaCode()));
		}
		return tradebuy;
	}
	
	 @Override
	    public Integer createTradeBuy(TradeBuy tradeBuy) {
	        Assert.notNull(tradeBuy, "the tradeBuy can not be null");
	        Assert.notNull(tradeBuy.getUid(), "the uid can not be null");
	        Assert.notNull(tradeBuy.getUid(), "the cid can not be null");
	        Assert.notNull(tradeBuy.getCategoryCode(), "the categoryCode can not be null");
			// 设置过期时间
			if (tradeBuy.getValidDays() == null || tradeBuy.getValidDays() == 0 ) {
				try {
					tradeBuy.setGmtExpired(DateUtil.getDate("2100-12-30", "yyyy-MM-dd"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				tradeBuy.setGmtExpired(DateUtil.getDateAfterDays(new Date(), tradeBuy.getValidDays()));
			}
			// 设置发布时间
			tradeBuy.setGmtPublish(new Date());
			// 设置刷新时间
			tradeBuy.setGmtRefresh(new Date());
			tradeBuy.setCheckStatus((short)TradeBuyService.STATUS_CHECK_UN);
			tradeBuy.setPauseStatus((short)TradeBuyService.STATUS_PAUSE_NO);
	        //设置详细信息的查询文本（提取详细信息的部分纯文本信息）
	        if (StringUtils.isNotEmpty(tradeBuy.getDetails())) {
	            tradeBuy.setDetailsQuery(Jsoup.parse(tradeBuy.getDetails()).body().text());
	        }
	        return tradeBuyDao.insertTradeBuy(tradeBuy);
	    }
	 
	 @Override
	 public PageDto<BuyMessageDto> pageBuyByConditions(Integer cid,
	            Integer pauseStatus, Integer overdueStatus, Integer checkStatus, PageDto<BuyMessageDto> page) {
	        Assert.notNull(cid, "the cid can not be null");
	        Assert.notNull(page, "the page can not be null");
	        
	        page.setSort("gmt_refresh");
	        page.setDir("desc");
	        List<TradeBuy> list = tradeBuyDao.queryBuyByConditions(cid, pauseStatus, overdueStatus, checkStatus, page);
	        page.setTotals(tradeBuyDao.queryBuyByConditionsCount(cid, pauseStatus, overdueStatus, checkStatus));
			List<BuyMessageDto> dtoList = new ArrayList<BuyMessageDto>();
			BuyMessageDto dto =null;
			for(TradeBuy buy : list){
				if(buy.getCheckRefuse().indexOf("技术文章版块")!=-1){
				  	buy.setCheckRefuse(buy.getCheckRefuse().replaceAll("技术文章版块", "<a href=\"http://www.huanbao.com/compnews/technicalarticles/publish.htm\">技术文章</a>版块"));
				}
				if(buy.getCheckRefuse().indexOf("发布供应信息")!=-1){
					buy.setCheckRefuse(buy.getCheckRefuse().replaceAll("发布供应信息", "<a href=\"http://www.huanbao.com/myesite/supply/publish_StpOne.htm\">发布供应信息</a>"));
				}
				dto = new BuyMessageDto();
				dto.setTradeBuy(buy);
				dto.setCount(messageDao.queryMessageCountByReplyAndRead(buy.getId(),1,0));
				dtoList.add(dto);
				
			}
			page.setRecords(dtoList);
			return page;
	 }
	 
	 @Override
	 public Integer deleteBuyById(Integer id,Integer cid) {
		Assert.notNull(cid, "the cid can not be null");
		return tradeBuyDao.updateDelStatusById(id, cid);
		}
	 
	 @Override
	 public Integer updatePauseStatusById(Integer id, Integer cid, Integer newStatus) {
	        Assert.notNull(id, "the id can not be null");
	        Assert.notNull(cid, "the cid can not be null");
	        Assert.notNull(newStatus, "the newStatus can not be null");
	        return tradeBuyDao.updatePauseStatusById(id, cid, newStatus);
	    }
	 
	 @Override
	 public Integer updateRefreshById(Integer id, Integer cid) {
	    	Assert.notNull(id, "the id can not be null");
	    	Assert.notNull(cid, "the cid can not be null");
	        return tradeBuyDao.updateRefreshById(id, cid);
	    }

	 @Override
	    public TradeBuy queryBuySimpleDetailsById(Integer id) {
	        Assert.notNull(id, "the id can not be null");
	        return tradeBuyDao.queryBuySimpleDetailsById(id);
	    }
	 
	 @Override
	public TradeBuy queryUpdateBuyById(Integer id, Integer cid) {
	        Assert.notNull(id, "the id can not be null");
	        Assert.notNull(cid, "the cid can not be null");
	        return tradeBuyDao.queryUpdateBuyById(id, cid);
		}
	
	@Override
	public Integer updateBaseBuyById(TradeBuy buy, Integer id, Integer cid) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(cid, "the cid can not be null");
		Assert.notNull(buy, "the buy can not be null");
		buy.setCheckStatus((short)TradeBuyService.STATUS_CHECK_UN);
		 //设置详细信息的查询文本（提取详细信息的部分纯文本信息）
        if (StringUtils.isNotEmpty(buy.getDetails())) {
            buy.setDetailsQuery(Jsoup.parse(buy.getDetails()).body().text());
        }
     // 设置过期时间
        if (buy.getValidDays() == null || buy.getValidDays() == 0) {
            try {
                buy.setGmtExpired(DateUtil.getDate("2100-12-30","yyyy-MM-dd"));
            } catch (ParseException e) {
//              e.printStackTrace();
            }
        } else {
            buy.setGmtExpired(DateUtil.getDateAfterDays(new Date(),buy.getValidDays()));
        }
		return tradeBuyDao.updateBaseBuyById(buy, id, cid);
	}
	
	@Override
	public List<TradeBuy> queryImpTradeBuy(Integer maxId) {
		return tradeBuyDao.queryImpTradeBuy(maxId);
	}

	@Override
	public Integer updateImpTradeBuy(Integer maxId) {
		return tradeBuyDao.updateImpTradeBuy(maxId);
	}

	@Override
	public Map<String, Integer> countSupplyByCompany(Integer cid) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		map.put("all", tradeBuyDao.countBuysOfCompanyByStatus(cid, null, null, null));
		map.put("checkStatus0", tradeBuyDao.countBuysOfCompanyByStatus(cid, 0, 1, 0));
		map.put("checkStatus1", tradeBuyDao.countBuysOfCompanyByStatus(cid, 0, 1, 1));
		map.put("checkStatus2", tradeBuyDao.countBuysOfCompanyByStatus(cid, 0, 1, 2));
		map.put("pauseStatus1", tradeBuyDao.countBuysOfCompanyByStatus(cid, 1, null, null));
		map.put("overdueStatus0", tradeBuyDao.countBuysOfCompanyByStatus(cid, null, 0, null));
		return map;
	}
	
	@Override
	public PageDto<TradeBuyNormDto> pageBySearchEngine(SearchBuyDto search,
			PageDto<TradeBuyNormDto> page) {
		
		StringBuffer sb=new StringBuffer();
		SphinxClient cl=SearchEngineUtils.getInstance().getClient();
		
		List<TradeBuyNormDto> list=new ArrayList<TradeBuyNormDto>();
		try {
			String keywords = "";
			if(StringUtils.isNotEmpty(search.getKeywords())){
				keywords = search.getKeywords();
			}
			
			if(StringUtils.isNotEmpty(keywords)){
				keywords = keywords.replaceAll("/", "");
				keywords=keywords.replace("%","");
	    		keywords=keywords.replace("\\","");
	    		keywords=keywords.replace("-","");
	    		keywords=keywords.replace("(","");
	    		keywords=keywords.replace(")","");
				sb.append("@(ptitle) ").append(keywords);
			}

			cl.SetMatchMode (SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(0,Fifty_THOUSAND,Fifty_THOUSAND);
			cl.SetConnectTimeout(120000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "member_code desc,gmt_refresh desc");
			// 供应刷新时间三天内
			SphinxResult res=cl.Query(sb.toString(), "buyPreTreeDay");
//			cl.SetLimits(0,res.totalFound,res.totalFound);
//			res=cl.Query(sb.toString(), "supplyPreTreeDay");
			List<String> resultList = new ArrayList<String>();
			Integer preCount = res.totalFound; // 三天内 所有数据基数
			
			if(page.getStart()+page.getLimit()<=res.totalFound){
				// 三天内轮回排序
				List<String> vipList = queryThreeDayList(res);
				// 翻页页码没有完全超过三天内的供求 直接调用搜索引擎 三天内的供应
				for (int i = page.getStart(); i < page.getStart()+page.getLimit(); i++) {
					resultList.add(vipList.get(i));
				}
				// 获取三天外的数据量
				cl.SetLimits(0,page.getLimit(),ONE_HUNDRED_THOUSAND_LIMIT); // 三天外 加上 三天内的数据基数
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_refresh desc"); // 三天外 只按 刷新时间排序
				res = cl.Query(sb.toString(), "huanbao_trade_buy"); // 重新搜索另一个 搜索引擎源
			}else if(page.getStart()>res.totalFound){
				// 翻页页码完全超过三天供应 直接调用 三天外的供应
				cl.SetLimits(page.getStart()-res.totalFound, page.getLimit(),ONE_HUNDRED_THOUSAND_LIMIT); // 三天外 加上 三天内的数据基数
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_refresh desc"); // 三天外 只按 刷新时间排序
				res = cl.Query(sb.toString(), "huanbao_trade_buy"); // 重新搜索另一个 搜索引擎源
				for ( int i=0; i<res.matches.length; i++ ){
					SphinxMatch info = res.matches[i];
					resultList.add(String.valueOf(""+info.docId));
				}
			}else{
				// 三天内轮回排序
				List<String> vipList = queryThreeDayList(res);
				// 翻页页码没有超过三天内的供求 调用搜索引擎 
				// 三天内的供求  
				Integer sub = res.totalFound-page.getStart();
				for (int i = page.getStart(); i < res.totalFound; i++) {
					resultList.add(vipList.get(i));
				}
				// 余下由三天外的供应补充
				cl.SetLimits(0, page.getLimit()-sub,ONE_HUNDRED_THOUSAND_LIMIT); // 三天外 加上 三天内的数据基数 
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_refresh desc"); // 三天外 只按 刷新时间排序 
				res = cl.Query(sb.toString(), "huanbao_trade_buy"); // 重新搜索另一个 搜索引擎源
				for ( int i=0; i<res.matches.length; i++ ){
					SphinxMatch info = res.matches[i];
					resultList.add(String.valueOf(""+info.docId));
				}
			}
			// 总数据量
			page.setTotals(res.totalFound+preCount);
			for(String pid : resultList){
				if(StringUtils.isNotEmpty(pid)&&!StringUtils.isNumber(pid)){
					continue;
				}
				TradeBuyNormDto dto = buildBuyFromDB(Integer.valueOf(pid));
				if (dto != null) {
					list.add(dto);
				}
			}
			
			page.setRecords(list);
		} catch (SphinxException e) {
			return null;
		}
		
		return page;
			
	}
	
	/**
	 * 刷新时间三天内 的list 列表
	 * @param bres
	 * @return
	 */
	private List<String> queryThreeDayList(SphinxResult bres){
		
		
		Map<String, Object> hmap = new LinkedHashMap<String, Object>();
		Map<String, Object> cmap = new LinkedHashMap<String, Object>();
		// 高会普会分组
		for( int i=0; i<bres.matches.length; i++ ){
			SphinxMatch info = bres.matches[i];
			Map<String, Object> resultMap=SearchEngineUtils.getInstance().resolveResult(bres,info);
			if("10011001".equals(resultMap.get("member_code").toString())){
				hmap.put(resultMap.get("pid").toString(), resultMap);
			}else{
				cmap.put(resultMap.get("pid").toString(), resultMap);
			}
		}
		
//		List<JSONObject> listHMap = getOrderByMap(hmap);
//		List<JSONObject> listCMap = getOrderByMap(cmap);
		
		
		// 结果 list pid为唯一属性
		List<String> resultList = new ArrayList<String>();
		
		// 高会轮回排序
		resultList.addAll(commonLHSort(hmap));
		
		// 普会轮回排序
		resultList.addAll(commonLHSort(cmap));
		
		return resultList;
	}
	
//	@SuppressWarnings("unchecked")
//	private List<JSONObject> getOrderByMap(Map<String, Object> map){
//		Map<String, String> tempMap = new TreeMap<String, String>().descendingMap();
//		for(String key : map.keySet()){
//			Map<String, Object> resultMap = (Map<String, Object>) map.get(key);
//			if(resultMap!=null){
//				String timeKey = resultMap.get("gmt_refresh").toString();
//				if(tempMap.get(timeKey)==null){
//					tempMap.put(timeKey, key);
//				}else{
//					tempMap.put(timeKey, tempMap.get(timeKey)+","+key);
//				}
//			}
//		}
//		List<JSONObject> list = new ArrayList<JSONObject>();
//		for (String key : tempMap.keySet()) {
//			String ids = tempMap.get(key);
//			if(ids.indexOf(",")!=-1){
//				String[] idArray = ids.split(",");
//				for (String id:idArray) {
//					list.add(JSONObject.fromObject(map.get(id)));
//				}
//			}else{
//				list.add(JSONObject.fromObject(map.get(tempMap.get(key))));
//			}
//		}
//		
//		return list;
//	}
	
	/**
	 * 获取普会轮回排序列表
	 * @param cmap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<String> commonLHSort(Map<String, Object> cmap) {
		
		List<String> list = new ArrayList<String>();
//		Queue<String> quene = new ConcurrentLinkedQueue<String>();
//		Set<String> intSet = new HashSet<String>();
//		Set<String> repeatSet = new HashSet<String>();
		Map<String, Queue<String>> cidGroupMap = new LinkedHashMap<String, Queue<String>>();
//		Map<String, Queue<String>> cidGroupMap = new HashMap<String, Queue<String>>();
		
		for (String pid : cmap.keySet()) {
			Map<String, Object> map = (Map<String, Object>) cmap.get(pid);
			String cId = map.get("cid").toString();
			String pId = map.get("pid").toString();
			Queue<String> cquene =  new ConcurrentLinkedQueue<String>();
			if(cidGroupMap.get(cId)!=null){
				cquene = cidGroupMap.get(cId);
			}else{
				cquene =  new ConcurrentLinkedQueue<String>();
			}
			cquene.add(pId);
			cidGroupMap.put(cId, cquene);
//				cidGroupMap.get(cId);
//			if (!repeatSet.contains(cId) && !intSet.contains(pId)) {
//				repeatSet.add(cId);
//				intSet.add(pId);
//				list.add(pId);
//			}
		}
		do {
			if(list.size()>=cmap.size()){
				break;
			}
			for(String key:cidGroupMap.keySet()){
				Queue<String> cquene = cidGroupMap.get(key);
				String pid = cquene.poll();
				if(StringUtils.isNotEmpty(pid)){
					list.add(pid);
				}
				if(!cquene.isEmpty()){
					cidGroupMap.put(key, cquene);
				}
			}
		} while (true);

		return list;
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

}