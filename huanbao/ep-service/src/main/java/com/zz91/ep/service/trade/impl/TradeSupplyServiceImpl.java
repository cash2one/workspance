/*
 * 文件名称：TradeSupplyServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Service;

import com.zz91.ep.dao.comp.CompAccountDao;
import com.zz91.ep.dao.comp.CompProfileDao;
import com.zz91.ep.dao.trade.MessageDao;
import com.zz91.ep.dao.trade.PhotoDao;
import com.zz91.ep.dao.trade.TradeSupplyDao;
import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.ResultSolrDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.SupplyMessageDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;
import com.zz91.ep.service.common.SolrService;
import com.zz91.ep.service.trade.PhotoService;
import com.zz91.ep.service.trade.TradeSupplyService;
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
 * 项目名称：中国环保网 模块编号：数据操作Service层 模块描述：交易中心供应信息实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容 　　　　　
 * 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("tradeSupplyService")
public class TradeSupplyServiceImpl implements TradeSupplyService {
	
	final static Integer TEN_THOUSAND = 10000;
	final static Integer Fifty_THOUSAND = 50000;
	final static Integer ONE_HUNDRED_THOUSAND_LIMIT = 100000;
	final static Integer MILLIONS_LIMIT = 1000000;
	@Resource
	private TradeSupplyDao tradeSupplyDao;

	@Resource
	private SolrService solrService;

	@Resource
	private CompProfileDao compProfileDao;

	@Resource
	private CompAccountDao compAccountDao;

	@Resource
	private MessageDao messageDao;
	@Resource
	private PhotoDao photoDao;

	@Override
	public Integer querySupplyCountByCategory(String code) {
		Assert.notNull(code, "the code can not be null");
		return tradeSupplyDao.querySupplyCountByCategory(code);
	}

	@Override
	public List<CommonDto> querySupplysByRecommend(Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		return tradeSupplyDao.querySupplysByRecommend(null, (short) 0, size);
	}

	@Override
	public List<TradeSupply> queryNewestSupplys(Integer cid, Integer size,
			String category, Integer uid) {
		if (size != null && size > 100) {
			size = 100;
		}
		return tradeSupplyDao.queryNewestSupplys(cid, size, category, uid);
	}

	@Override
	public List<TradeSupplyDto> querySupplysByIds(Integer[] ids, Integer flag) {
		List<TradeSupplyDto> list = new ArrayList<TradeSupplyDto>();
		TradeSupplyDto dto = null;
		do {
			if (ids == null || ids.length <= 0) {
				break;
			}

			for (Integer i : ids) {
				Assert.notNull(i, "the id[i] can not be null");
				TradeSupply supply = tradeSupplyDao.queryShortDetailsById(i);
				if (supply != null) {
					dto = new TradeSupplyDto();
					if (flag != null && flag == 1) {
						CompProfile comp = compProfileDao
								.queryShortCompProfileById(supply.getCid());
						if (comp != null) {
							dto.setBusinessName(ParamUtils.getInstance()
									.getValue("company_industry_code",
											comp.getBusinessCode()));
							dto.setCompName(comp.getName());
						}
					}
					dto.setSupply(supply);
					list.add(dto);
				}
			}
		} while (false);
		return list;
	}

	@Override
	public PageDto<TradeSupply> pageSupplyByCompany(Integer group,
			String keywords, Integer cid, PageDto<TradeSupply> page) {
		
		 List<TradeSupply> tradeList =new ArrayList<TradeSupply>();
		 
		 List<TradeSupply> tradeLists=tradeSupplyDao.querySupplyByCompany(
				group, keywords, cid, page);
		 
		for (TradeSupply tradeSupply : tradeLists) {
			Photo photo = photoDao.queryPhotoByTypeAndId("supply",tradeSupply.getId());
			if (photo != null) {
				tradeSupply.setPhotoCover(photo.getPath());
			}else{
				tradeSupply.setPhotoCover("");
			}
			tradeList.add(tradeSupply);
		}
        page.setRecords(tradeList);
		page.setTotals(tradeSupplyDao.querySupplyByCompanyCount(group,
				keywords, cid));
		return page;
	}

	@Override
	public List<TradeSupply> queryRecommendSupplysByCid(Integer cid,
			Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		
		List<TradeSupply> list = tradeSupplyDao.queryRecommendSupplysByCid(cid, size); 
		// 封面图片的选取
		for(TradeSupply tradeSupply:list){
			if (StringUtils.isEmpty(tradeSupply.getPhotoCover())) {
				List<Photo> photos = photoDao.queryPhotoByTargetType("supply", tradeSupply.getId(), 100);
				if (photos.size() != 0) {
					for (Photo photo : photos) {
						if (photo.getIsDel().equals("0")) {
							tradeSupply.setPhotoCover(photo.getPath());
							break;
						}
					}
				}
			} 
		}
		return list;
		
	}

	@Override
	public TradeSupplyDto queryDetailsById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		TradeSupply tradeSupply = tradeSupplyDao.queryDetailsById(id);
		TradeSupplyDto dto = null;
		if (tradeSupply != null) {
			dto = new TradeSupplyDto();
			if (StringUtils.isNotEmpty(tradeSupply.getProvinceCode())) {
				dto.setProvinceName(CodeCachedUtil.getValue(
						CodeCachedUtil.CACHE_TYPE_AREA, tradeSupply
								.getProvinceCode()));
			}
			if (StringUtils.isNotEmpty(tradeSupply.getAreaCode())) {
				dto.setAreaName(CodeCachedUtil.getValue(
						CodeCachedUtil.CACHE_TYPE_AREA, tradeSupply
								.getAreaCode()));
			}
			if (StringUtils.isNotEmpty(tradeSupply.getCategoryCode())) {
				dto.setCategoryName(CodeCachedUtil.getValue(
						CodeCachedUtil.CACHE_TYPE_TRADE, tradeSupply
								.getCategoryCode()));
			}
			dto.setPropertyValue(buildPropertyValues(tradeSupply.getPropertyQuery()));
			// 封面图片的选取
			tradeSupply.setPhotoCover("");
			List<Photo> photos = photoDao.queryPhotoByTargetType("supply", id, 100);
			if (photos.size() != 0) {
				for (Photo photo : photos) {
					if (photo!=null&&StringUtils.isNotEmpty(photo.getPath())) {
						String isDel = photo.getIsDel();
						if (isDel.equals("0")&&"1".equals(photo.getCheckStatus())) {
							tradeSupply.setPhotoCover(photo.getPath());
							break;
						}
					}
				}
				
			}
			dto.setSupply(tradeSupply);
		}
		return dto;
	}

	private Map<Integer, Object> buildPropertyValues(String propertyQuery) {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		if (StringUtils.isNotEmpty(propertyQuery)) {
			String[] propertys = propertyQuery.split(";");
			for (int i = 0; i < propertys.length; i++) {
				String property = propertys[i];
				if (StringUtils.isNotEmpty(property)) {
					String[] value = property.split("_");
					if (value.length > 1) {
						map.put(Integer.valueOf(value[0]), value[1]);
					}
				}
			}
		}
		return map;
	}

	@Override
	public Integer updatePhotoCoverById(Integer id, String photoCover,
			Integer cid) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(cid, "the cid can not be null");
		Assert.notNull(photoCover, "the photoCover can not be null");
		return tradeSupplyDao.updatePhotoCoverById(id, photoCover, cid);
	}

	@Override
	public Integer createTradeSupply(TradeSupply tradeSupply) {
		Assert.notNull(tradeSupply, "the tradeSupply can not be null");
		Assert.notNull(tradeSupply.getUid(), "the uid can not be null");
		Assert.notNull(tradeSupply.getCid(), "the cid can not be null");
		Assert.notNull(tradeSupply.getCategoryCode(),"the categoryCode can not be null");
		// TODO 检查敏感词，设置审核状态
		if(tradeSupply.getCheckStatus()==null){
			tradeSupply.setCheckStatus((short) TradeSupplyService.STATUS_CHECK_UN);
		}
		// 设置过期时间
		if (tradeSupply.getValidDays() == null || tradeSupply.getValidDays() == 0) {
			try {
				tradeSupply.setGmtExpired(DateUtil.getDate("2100-12-30","yyyy-MM-dd"));
			} catch (ParseException e) {
//				e.printStackTrace();
			}
		} else {
			tradeSupply.setGmtExpired(DateUtil.getDateAfterDays(new Date(),tradeSupply.getValidDays()));
		}
		
		// 供应总量为0 发布失败
		if(tradeSupply.getTotalNum()<1){
			return 0;
		}
		
		// 设置发布时间
		tradeSupply.setGmtPublish(new Date());
		// 设置刷新时间
		tradeSupply.setGmtRefresh(new Date());
		// 设置详细信息的查询文本（提取详细信息的部分纯文本信息）
		if (StringUtils.isNotEmpty(tradeSupply.getDetails())) {
			String string = Jsoup.clean(tradeSupply.getDetails(), Whitelist
					.none());
			tradeSupply.setDetailsQuery(string.replace(" ", ""));
		}
		if (tradeSupply.getInfoComeFrom() == null) {
			tradeSupply.setInfoComeFrom(1);
		}
		return tradeSupplyDao.insertTradeSupply(tradeSupply);
	}

	@Override
	public PageDto<SupplyMessageDto> pageSupplyByConditions(Integer cid,
			Integer pauseStatus, Integer overdueStatus, Integer checkStatus,
			Integer recommend, Integer groupId, PageDto<SupplyMessageDto> page) {
		Assert.notNull(cid, "the cid can not be null");
		Assert.notNull(page, "the page can not be null");

		page.setSort("ts.gmt_refresh");
		page.setDir("desc");
		List<TradeSupply> list = tradeSupplyDao.querySupplyByConditions(cid,
				pauseStatus, overdueStatus, checkStatus, recommend, groupId,
				page);
		page.setTotals(tradeSupplyDao.querySupplyByConditionsCount(cid,
				pauseStatus, overdueStatus, checkStatus, recommend, groupId));
		List<SupplyMessageDto> dtoList = new ArrayList<SupplyMessageDto>();
		SupplyMessageDto dto = null;
		List pl = null;
		for (TradeSupply supply : list) {
			//  供应信息已审核但产品图片退回的供应信息
			if(1== supply.getCheckStatus()){
				  pl = photoDao.queryPhotoListByTypeAndId("supply",supply.getId(),"2");
				  if(pl!=null &&  pl.size()>0){
					  supply.setIsPicPass(0);
				  }
			}
			
			dto = new SupplyMessageDto();
			dto.setTradeSupply(supply);
			dto.setCount(messageDao.queryMessageCountByReplyAndRead(supply
					.getId(), 1, 0));
			dtoList.add(dto);
		}
		page.setRecords(dtoList);
		return page;
	}

	@Override
	public Integer deleteSupplyById(Integer id, Integer cid) {
		// Assert.notNull(id, "the id can not be null");
		Assert.notNull(cid, "the cid can not be null");
		return tradeSupplyDao.updateDelStatusById(id, cid);
	}

	@Override
	public Integer updateRefreshById(Integer id, Integer cid) {
		Assert.notNull(id, "the id can not be null");
		return tradeSupplyDao.updateRefreshById(id, cid);
	}

	@Override
	public Integer updatePauseStatusById(Integer id, Integer cid,
			Integer newStatus) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(cid, "the cid can not be null");
		Assert.notNull(newStatus, "the id[] can not be null");
		return tradeSupplyDao.updatePauseStatusById(id, cid, newStatus);
	}

	@Override
	public Integer updateSupplyGroupIdById(Integer id, Integer cid, Integer gid) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(cid, "the cid can not be null");
		Assert.notNull(gid, "the gid can not be null");
		return tradeSupplyDao.updateSupplyGroupIdById(id, cid, gid);
	}

	@Override
	public TradeSupplyDto queryUpdateSupplyById(Integer id, Integer cid) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(cid, "the cid can not be null");
		TradeSupply supply = tradeSupplyDao.queryUpdateSupplyById(id, cid);
		TradeSupplyDto dto = null;
		if (supply != null) {
			dto = new TradeSupplyDto();
			dto.setSupply(supply);
			if (StringUtils.isNotEmpty(supply.getPropertyQuery())) {
				dto.setPropertyValue(buildPropertyValues(supply
						.getPropertyQuery()));
			}
		}
		return dto;
	}

	@Override
	public Integer updateCategoryById(String category, String propertyValue,
			Integer id, Integer cid) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(cid, "the cid can not be null");
		Assert.notNull(category, "the category can not be null");
		// 更新供应信息类别
		return tradeSupplyDao.updateCategoryById(category, propertyValue, id,
				cid);
	}

	@Override
	public Integer updateBaseSupplyById(TradeSupply supply, Integer id,
			Integer cid) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(cid, "the cid can not be null");
		Assert.notNull(supply, "the supply can not be null");
		Assert.notNull(supply.getTitle(), "the title can not be null");
		// TODO 检查敏感词
		// 流入审核
		supply.setCheckStatus((short) TradeSupplyService.STATUS_CHECK_UN);
		if (supply.getValidDays() == null) {
			supply.setValidDays((short) 0);
		}
		// 设置详细信息的查询文本（提取详细信息的部分纯文本信息）
		if (StringUtils.isNotEmpty(supply.getDetails())) {
			String string = Jsoup.clean(supply.getDetails(), Whitelist.none());
			string = string.replace(" ", "");
			if (string.length() > 500) {
				supply.setDetailsQuery(string.substring(0, 500));
			} else {
				supply.setDetailsQuery(string);
			}

			// 判断字数不能超过5000，出现报错页面
			if (supply.getDetails().length() > 5000) {
				return 0;
			}
		}
		// 设置过期时间
        if (supply.getValidDays() == null || supply.getValidDays() == 0) {
            try {
                supply.setGmtExpired(DateUtil.getDate("2100-12-30","yyyy-MM-dd"));
            } catch (ParseException e) {
//              e.printStackTrace();
            }
        } else {
            supply.setGmtExpired(DateUtil.getDateAfterDays(new Date(),supply.getValidDays()));
        }
        
		return tradeSupplyDao.updateBaseSupplyById(supply, id, cid);
	}

	@Override
	public Integer updatePropertyQueryById(Integer id, String properyValue) {
		Assert.notNull(id, "id不能为空");
		return tradeSupplyDao.updatePropertyQueryById(id, properyValue);
	}

	@Override
	public TradeSupply queryOneSupplyById(Integer id) {
		return tradeSupplyDao.queryOneSupplyById(id);
	}

	@Override
	public List<TradeSupply> queryImpTradeSupply(Integer maxId) {
		return tradeSupplyDao.queryImpTradeSupply(maxId);
	}

	@Override
	public Integer updateImpTradeSupply(Integer maxId) {
		return tradeSupplyDao.updateImpTradeSupply(maxId);
	}

	@Override
	public List<TradeKeyword> queryBwListByKeyword(String keywords) {
		if (StringUtils.isEmpty(keywords)) {
			return null;
		}

		List<TradeKeyword> list = tradeSupplyDao.queryBwListByKeyword(keywords);
		for (TradeKeyword tk : list) {
			if (StringUtils.isNotEmpty(tk.getAreaCode())) {
				tk.setAreaName(CodeCachedUtil.getValue(
						CodeCachedUtil.CACHE_TYPE_AREA, tk.getAreaCode()));
			}
			if (StringUtils.isNotEmpty(tk.getProvinceCode())) {
				tk.setProvinceName(CodeCachedUtil.getValue(
						CodeCachedUtil.CACHE_TYPE_AREA, tk.getProvinceCode()));
			}
			if (tk.getCid() != null) {
				CompProfile cp = compProfileDao.getCompProfileById(tk.getCid());
				if (cp != null) {
					tk.setMainProductSupply(cp.getMainProductSupply());
				}
			}
		}
		return list;
	}

	// public PageDto<TradeSupplySearchDto>
	// loadCompanyMainProduct(PageDto<TradeSupplySearchDto> page){
	// for(TradeSupplySearchDto dto : page.getRecords()){
	// CompProfile cp= compProfileDao.getCompProfileById(dto.getCid());
	// if(cp!=null){
	// dto.setMainProductSupply(cp.getMainProductSupply());
	// }
	// }
	// return page;
	// }

	// public PageDto<TradeSupplySearchDto>
	// loadCompanyMainProduct(PageDto<TradeSupplySearchDto> page){
	// for(TradeSupplySearchDto dto : page.getRecords()){
	// CompProfile cp= compProfileDao.getCompProfileById(dto.getCid());
	// if(cp!=null){
	// dto.setMainProductBuy(cp.getMainProductBuy());
	// }
	// }
	// return page;
	// }

	@Override
	public Integer updateGmtModefiled(Integer tradeId) {

		return tradeSupplyDao.updateGmtModefiled(tradeId);
	}

	@Override
	public List<TradeSupply> queryRandomSupply(String code, Integer random) {

		return tradeSupplyDao.queryRandomSupply(code, random);
	}

	@Override
	public List<TradeSupply> queryCategoryByCid(Integer cid) {
		return tradeSupplyDao.queryCategoryByCid(cid);
	}

	@Override
	public PageDto<TradeSupplyNormDto> searchSolrSupply(SearchSupplyDto search,
			PageDto<TradeSupplyNormDto> page, String sort) {
		page = querySolrSupply(search, page, sort);
		return page;
	}

	/**
	 * 查询supply封装page
	 * 
	 * @param search
	 * @param page
	 * @param sort
	 * @return
	 */
	private PageDto<TradeSupplyNormDto> querySolrSupply(SearchSupplyDto search,
			PageDto<TradeSupplyNormDto> page, String sort) {
		ResultSolrDto solrDto = solrService.pageSupplysBySearch(search, page,
				sort);
		List<TradeSupplyNormDto> supplyList = new ArrayList<TradeSupplyNormDto>();
		for (Integer id : solrDto.getIdList()) {
			TradeSupply supply = tradeSupplyDao.querySolrSupplybyId(id);
			if (supply == null) {
				continue;
			}
			TradeSupplyNormDto normDto = new TradeSupplyNormDto();

			Map<String, Map<String, List<String>>> high = solrDto.getHighMap();
			if (high != null) {
				Map<String, List<String>> root = high.get(id.toString());
				if (root != null) {
					List<String> title = root.get("title");
					if (title != null) {
						supply.setTitle(title.get(0));
					}
				}
			}
			normDto.setSupply(supply);
			supplyList.add(normDto);
		}
		page.setTotals(solrDto.getTotals());
		page.setRecords(supplyList);
		return page;
	}

	@Override
	public PageDto<TradeSupplyNormDto> searchSupplyByCategory(
			SearchSupplyDto search, PageDto<TradeSupplyNormDto> page,
			String sort) {
		page = querySolrSupply(search, page, sort);
		List<TradeSupplyNormDto> list = page.getRecords();
		for (TradeSupplyNormDto dto : list) {
			dto.setComp(compProfileDao.queryShortCompProfileById(dto
					.getSupply().getCid()));
			dto.setArea(AreaUtil.buildArea(dto.getSupply().getAreaCode()));
		}
		return page;
	}

	@Override
	public PageDto<TradeSupplyNormDto> searchListSupply(SearchSupplyDto search,
			PageDto<TradeSupplyNormDto> page, String sort) {

//		page = querySolrSupply(search, page, sort);
		page = pageBySearchEngine(search, page);

		List<TradeSupplyNormDto> list = page.getRecords();
		for (TradeSupplyNormDto dto : list) {
			Integer cid = dto.getSupply().getCid();
			dto.setComp(compProfileDao.queryShortCompProfileById(cid));
			dto.setArea(AreaUtil.buildArea(dto.getSupply().getAreaCode()));
			CompAccount account = new CompAccount();
			account.setQq(compAccountDao.queryQq(cid));
			dto.setAccount(account);
		}
		return page;
	}

	@Override
	public String queryPhotoCover(Integer id) {
		return tradeSupplyDao.queryPhotoCover(id);
	}

	@Override
	public Map<String, Integer> countSupplyByCompany(Integer cid,
			Integer groupId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("all", tradeSupplyDao.countSupplysOfCompanyByStatus(cid, null,
				null, null, groupId));
		map.put("checkStatus0", tradeSupplyDao.countSupplysOfCompanyByStatus(
				cid, 0, 1, 0, groupId));
		map.put("checkStatus1", tradeSupplyDao.countSupplysOfCompanyByStatus(
				cid, 0, 1, 1, groupId));
		map.put("checkStatus2", tradeSupplyDao.countSupplysOfCompanyByStatus(
				cid, 0, 1, 2, groupId));
		map.put("pauseStatus1", tradeSupplyDao.countSupplysOfCompanyByStatus(
				cid, 1, null, null, groupId));
		map.put("overdueStatus0", tradeSupplyDao.countSupplysOfCompanyByStatus(
				cid, null, 0, null, groupId));
		return map;
	}

	@Override
	public TradeSupply querySimpleDetailsById(Integer id) {
		Assert.notNull(id, "the id is must not null");
		return tradeSupplyDao.querySimpleDetailsById(id);
	}

	@Override
	public Boolean validateToPub(Integer companyId) {
		String from = DateUtil.toString(new Date(), "yyyy-MM-dd");
		String to = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), 1),
				"yyyy-MM-dd");
		Integer count = tradeSupplyDao.countForCidAndDate(companyId, from, to);
		if (count != null && count >= TradeSupplyService.PUB_LIMIT) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean validateTitleRepeat(Integer companyId, String title) {
		Integer i = tradeSupplyDao.countForCidAndTitle(companyId, title.replaceAll("\\s", ""));
		// 没检索到信息，证明不存在，返回true，可以使用该标题
		if (i == null || i == 0) { 
			return true;
		}
		return false;
	}

	@Override
	public Integer updatePhotoCover(Integer id, String photoCover) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(photoCover, "the photoCover can not be null");
		return tradeSupplyDao.updatePhotoCover(id, photoCover);
	}


	@Override
	public PageDto<TradeSupplyNormDto> pageBySearchEngine(SearchSupplyDto search,
			PageDto<TradeSupplyNormDto> page) {
		
		StringBuffer sb=new StringBuffer();
		SphinxClient cl=SearchEngineUtils.getInstance().getClient();
		
		List<TradeSupplyNormDto> list=new ArrayList<TradeSupplyNormDto>();
		try {
			String keywords = "";
			if(StringUtils.isNotEmpty(search.getKeywords())){
				keywords = search.getKeywords();
			}
			if(StringUtils.isNotEmpty(search.getCategoryName())&&!"供应类别".equals(search.getCategoryName())){
				keywords = keywords + "," + search.getCategoryName();
			}
			
			if(StringUtils.isNotEmpty(keywords)){
				keywords = keywords.replaceAll("/", "");
				keywords=keywords.replace("%","");
	    		keywords=keywords.replace("\\","");
	    		keywords=keywords.replace("-","");
	    		keywords=keywords.replace("(","");
	    		keywords=keywords.replace(")","");
				sb.append("@(ptitle,tags,category_label1,category_label2,category_label3,category_label4,area_label1,area_label2,area_label3,area_label4,area_label5,parea_name) ").append(keywords);
			}

			cl.SetMatchMode (SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(0,Fifty_THOUSAND,Fifty_THOUSAND);
			cl.SetConnectTimeout(120000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_refresh desc");
			// 供应刷新时间三天内
			SphinxResult res=cl.Query(sb.toString(), "supplyPreTreeDay");
			if(res==null){
//				System.out.println(cl.GetLastError());
				return new PageDto<TradeSupplyNormDto>();
			}
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
				res = cl.Query(sb.toString(), "huanbao_trade_supply"); // 重新搜索另一个 搜索引擎源
			}else if(page.getStart()>res.totalFound){
				// 翻页页码完全超过三天供应 直接调用 三天外的供应
				cl.SetLimits(page.getStart()-res.totalFound, page.getLimit(),ONE_HUNDRED_THOUSAND_LIMIT); // 三天外 加上 三天内的数据基数
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_refresh desc"); // 三天外 只按 刷新时间排序
				res = cl.Query(sb.toString(), "huanbao_trade_supply"); // 重新搜索另一个 搜索引擎源
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
				res = cl.Query(sb.toString(), "huanbao_trade_supply"); // 重新搜索另一个 搜索引擎源
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
				TradeSupply supply = tradeSupplyDao.querySolrSupplybyId(Integer.valueOf(pid));
				
				if (supply == null) {
					// 本地测试
//					supply= new TradeSupply();
//					supply.setId(Integer.valueOf(pid));
//					normDto.setSupply(supply);
					// 上线
					continue;
				}
				supply.setPhotoCover("");
				supply.setDetailsQuery(Jsoup.clean(supply.getDetailsQuery(), Whitelist.none()));
				List<Photo> photos = photoDao.queryPhotoByTargetType("supply", Integer.valueOf(pid), 100);
				if (photos.size() != 0) {
					for (Photo photo : photos) {
						if (photo!=null&&StringUtils.isNotEmpty(photo.getPath())) {
							String isDel = photo.getIsDel();
							if (isDel.equals("0")&&"1".equals(photo.getCheckStatus())) {
								supply.setPhotoCover(photo.getPath());
								break;
							}
						}
					}
					
				}
				TradeSupplyNormDto normDto = new TradeSupplyNormDto();
				normDto.setSupply(supply);
				normDto.setComp(compProfileDao.queryShortCompProfileById(supply.getCid()));
				normDto.setArea(AreaUtil.buildArea(supply.getAreaCode()));
				list.add(normDto);
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
	@SuppressWarnings("unchecked")
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
		
		// 高会组 按日期分组
		Set<String> dateSet  = new HashSet<String>();
		Map<String,List<Map<String, Object>>> listMap = new TreeMap<String, List<Map<String,Object>>>().descendingMap();
		for(String key : hmap.keySet()){
			Map<String, Object> resultMap = (Map<String, Object>) hmap.get(key);
			Date date = new Date();
			date.setTime(Long.valueOf(resultMap.get("gmt_refresh").toString()+"000"));
			String dateGroup = DateUtil.toString(date, "yyyy-MM-dd");
			if(dateSet.contains(dateGroup)){
				List<Map<String, Object>> nlist = listMap.get(dateGroup);
				nlist.add((Map<String, Object>) hmap.get(key));
				listMap.put(dateGroup, nlist);
			}else{
				List<Map<String, Object>> nlist = new ArrayList<Map<String,Object>>();
				dateSet.add(DateUtil.toString(date, "yyyy-MM-dd"));
				nlist.add((Map<String, Object>) hmap.get(key));
				listMap.put(dateGroup, nlist);
			}
		}
		
		// 高会 单个日期 轮回排序
//		Set<String> intSet = new HashSet<String>();
//		List<String> intList = new ArrayList<String>();
//		List<Map<String, Object>> nlist = new ArrayList<Map<String,Object>>();
//		for(String key:listMap.keySet()){
//			nlist.addAll(listMap.get(key));
//		}
//		do {
//			Set<String> repeatSet = new HashSet<String>();
//			if(intSet.size()>=hmap.size()){
//				break;
//			}
//			for(Map<String, Object> obj:nlist){
//				String cId = obj.get("cid").toString();
//				String pId = obj.get("pid").toString();
//				if(!repeatSet.contains(cId)&&!intSet.contains(pId)){
//					repeatSet.add(cId);
//					intSet.add(pId);
//					intList.add(pId);
//				}
//			}
//		} while (true);
		
		
		// 结果 list pid为唯一属性
		List<String> resultList = new ArrayList<String>();
//		resultList.addAll(intList);
		
//		Date start = new Date();
		
		resultList.addAll(commonLHSort(hmap));
//		Date end = new Date();
//		System.out.println(end.getTime()-start.getTime());
		
		// 普会轮回排序
		resultList.addAll(commonLHSort(cmap));
		
		return resultList;
	}
	
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
		Map<String, Queue<String>> cidGroupMap = new HashMap<String, Queue<String>>();
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

	@Override
	public PageDto<TradeSupplyNormDto> pageBySearchEngineTrade(
			SearchSupplyDto search, PageDto<TradeSupplyNormDto> page) {

		StringBuffer sb=new StringBuffer();
		SphinxClient cl=SearchEngineUtils.getInstance().getClient();
		
		List<TradeSupplyNormDto> list=new ArrayList<TradeSupplyNormDto>();
		try {
			if(search==null){
				search = new SearchSupplyDto();
			}
			
			String keywords = "";
			if(StringUtils.isNotEmpty(search.getKeywords())){
				keywords = search.getKeywords();
			}
			if(StringUtils.isNotEmpty(search.getCategoryName())&&!"供应类别".equals(search.getCategoryName())){
				keywords = keywords + "," + search.getCategoryName();
			}
			
			if(StringUtils.isNotEmpty(keywords)){
				keywords = keywords.replaceAll("/", "");
				keywords=keywords.replace("%","");
				keywords=keywords.replace("\\","");
				keywords=keywords.replace("-","");
				keywords=keywords.replace("(","");
				keywords=keywords.replace(")","");
				sb.append("@(ptitle,tags,category_label1,category_label2,category_label3,category_label4,area_label1,area_label2,area_label3,area_label4,area_label5,parea_name) ").append(keywords);
			}
			
			// 是否要图片
			if(search.getHavePic()!=null&&search.getHavePic()){
				cl.SetFilterRange("havepic", 1, 5000, false);
			}

			cl.SetMatchMode (SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(0,Fifty_THOUSAND,Fifty_THOUSAND);
			cl.SetConnectTimeout(120000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_refresh desc");
			// 供应刷新时间三天内
			SphinxResult res=cl.Query(sb.toString(), "supplyPreTreeDay");
			if(res==null){
				 return null;
			}
//			cl.SetLimits(0,res.totalFound,res.totalFound);
//			res=cl.Query(sb.toString(), "supplyPreTreeDay");
			List<String> resultList = new ArrayList<String>();
			Integer preCount = res.totalFound; // 三天内 所有数据基数
			
			if(page.getStart()+page.getLimit()<=res.totalFound){
				// 三天内轮回排序
				List<String> vipList = queryThreeDayListTrade(res);
				// 翻页页码没有完全超过三天内的供求 直接调用搜索引擎 三天内的供应
				for (int i = page.getStart(); i < page.getStart()+page.getLimit(); i++) {
					resultList.add(vipList.get(i));
				}
				// 获取三天外的数据量
				cl.SetLimits(0,page.getLimit(),ONE_HUNDRED_THOUSAND_LIMIT); // 三天外 加上 三天内的数据基数
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_refresh desc"); // 三天外 只按 刷新时间排序
				res = cl.Query(sb.toString(), "huanbao_trade_supply"); // 重新搜索另一个 搜索引擎源
			}else if(page.getStart()>res.totalFound){
				// 翻页页码完全超过三天供应 直接调用 三天外的供应
				cl.SetLimits(page.getStart()-res.totalFound, page.getLimit(),ONE_HUNDRED_THOUSAND_LIMIT); // 三天外 加上 三天内的数据基数
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_refresh desc"); // 三天外 只按 刷新时间排序
				res = cl.Query(sb.toString(), "huanbao_trade_supply"); // 重新搜索另一个 搜索引擎源
				for ( int i=0; i<res.matches.length; i++ ){
					SphinxMatch info = res.matches[i];
					resultList.add(String.valueOf(""+info.docId));
				}
			}else{
				// 三天内轮回排序
				List<String> vipList = queryThreeDayListTrade(res);
				// 翻页页码没有超过三天内的供求 调用搜索引擎 
				// 三天内的供求  
				Integer sub = res.totalFound-page.getStart();
				for (int i = page.getStart(); i < res.totalFound; i++) {
					resultList.add(vipList.get(i));
				}
				// 余下由三天外的供应补充
				cl.SetLimits(0, page.getLimit()-sub,ONE_HUNDRED_THOUSAND_LIMIT); // 三天外 加上 三天内的数据基数 
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_refresh desc"); // 三天外 只按 刷新时间排序 
				res = cl.Query(sb.toString(), "huanbao_trade_supply"); // 重新搜索另一个 搜索引擎源
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
				TradeSupply supply = tradeSupplyDao.querySolrSupplybyId(Integer.valueOf(pid));
				if (supply == null) {
					// 本地测试
//					supply= new TradeSupply();
//					supply.setId(Integer.valueOf(pid));
//					normDto.setSupply(supply);
					// 上线
					continue;
				}
				TradeSupplyNormDto normDto = new TradeSupplyNormDto();
				// 要求图片供求却没有搜索到图片
				if(search.getHavePic()!=null && StringUtils.isEmpty(supply.getPhotoCover())){
					Photo photo = photoDao.queryPhotoByTypeAndId(PhotoService.TARGET_SUPPLY, supply.getId());
					if(photo!=null&&StringUtils.isNotEmpty(photo.getPath())){
						supply.setPhotoCover(photo.getPath());
					}
				}
				
				normDto.setSupply(supply);
				normDto.setComp(compProfileDao.queryShortCompProfileById(supply.getCid()));
				normDto.setArea(AreaUtil.buildArea(supply.getAreaCode()));
				list.add(normDto);
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
	private List<String> queryThreeDayListTrade(SphinxResult bres){
		
		Map<String, Object> cmap = new LinkedHashMap<String, Object>();
		// 高会普会分组
		for( int i=0; i<bres.matches.length; i++ ){
			SphinxMatch info = bres.matches[i];
			Map<String, Object> resultMap=SearchEngineUtils.getInstance().resolveResult(bres,info);
			if(resultMap.get("member_code").toString()!=null){
				
				cmap.put(resultMap.get("pid").toString(), resultMap);
			}
		}
		
		// 结果 list pid为唯一属性
		List<String> resultList = new ArrayList<String>();

		
		// 普会轮回排序
		resultList.addAll(commonLHSort(cmap));
		
		return resultList;
	}

	@Override
	public Integer  updateUncheckByIdForMyesite(Integer id) {
		return tradeSupplyDao.updateUncheckByIdForMyesite(id);
	}
	
}