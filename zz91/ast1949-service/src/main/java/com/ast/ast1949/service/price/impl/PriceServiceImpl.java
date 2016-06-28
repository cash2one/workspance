package com.ast.ast1949.service.price.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.price.ForPriceDTO;
import com.ast.ast1949.dto.price.PriceCategoryDTO;
import com.ast.ast1949.dto.price.PriceDTO;
import com.ast.ast1949.dto.price.PriceDto2;
import com.ast.ast1949.persist.price.PriceCategoryDAO;
import com.ast.ast1949.persist.price.PriceDAO;
import com.ast.ast1949.service.price.PriceService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.CNToHexUtil;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

@Component("priceService")
public class PriceServiceImpl implements PriceService {

	@Autowired
	private PriceDAO priceDAO;
	@Autowired
	private PriceCategoryDAO priceCategoryDAO;

	final static String CONTENT_PRICE_TEMPLATE = "<div class='p4'>"
			+ "<div class='l1'>"
			+ "<ul>"
			+ "#foreach($obj in $!{list})"
			+ "<li><a href='http://price.zz91.com/priceDetails_$!{obj.id}.htm' style='color:#06F'>${velocityCount}.<span>$!{obj.title}</span></a></li>"
			+ "#end"
			+ "</ul>"
			+ "</div>"
			+ "<div class='r1'>"
			+ "<ul>"
			+ "<li>本文标签： "
			+ "#foreach($obj in $keyMap.keySet())"
			+ "<a href='http://tags.zz91.com/s/$!{obj}/' target='_blank'>$keyMap.get($!{obj})</a>"
			+ "#end"
			+ "</li>"
			+ "<li><a href='http://xiazai.zz91.com/search/result/?searchid=9' target='_blank'>【ZZ91废金属行情早参】每天5分钟，纵揽全球废金属最精彩行情“早餐”！</a></li>"
			+ "<li><a href='http://xiazai.zz91.com/search/result/?searchid=48' target='_blank'>【ZZ91废金属晚报】每日17点半，ZZ91为您整合最新废金属行情动态！</a></li>"
			+ "<li><a href='http://xiazai.zz91.com/view/387.html' target='_blank'>【ZZ91废料内参】每周一刊，盘点一周重大废旧行业新闻！</a></li>"
			+ "</ul>" + "</div>" + "</div>";

	public Integer batchDeleteUserById(int[] entities) {
		Assert.notNull(entities, "The entities must not be null.");
		return priceDAO.batchDeleteUserById(entities);
	}

	// public Integer deletePriceById(Integer id) throws
	// IllegalArgumentException {
	// Assert.notNull(id, "The id must not be null.");
	// return priceDAO.deletePriceById(id);
	// }
	@Override
	public String queryPriceTtpePlasticOrMetal(Integer id) {
		String code = "";
		PriceCategoryDO priceCategoryDO = priceCategoryDAO
				.queryPriceCategoryById(id);
		if (id != null && id <= 8) {
			if (priceCategoryDO != null) {
				code = priceCategoryDO.getName();
			} else {
				code = "";
			}
		} else if (priceCategoryDO != null) {
			Integer parentId = priceCategoryDO.getParentId();
			for (int i = 0; i < 6; i++) {
				priceCategoryDO = priceCategoryDAO
						.queryPriceCategoryById(parentId);
				if (priceCategoryDO != null) {
					if (priceCategoryDO.getParentId() != null) {
						if (priceCategoryDO.getParentId() == 1) {
							code = priceCategoryDO.getName();
							break;
						} else {
							parentId = priceCategoryDO.getParentId();
						}
					}
				}
			}
		}
		return code;
	}

	public Map<Integer, List<PriceDto2>> queryLatestPriceUsePageEngine(
			Map<Integer, List<PriceDto2>> map, String queryKey) {
		// set the page size 12
		PageDto<PriceDO> pageprice = new PageDto<PriceDO>();
		pageprice.setPageSize(12);
		// query informationn by searchEngine
		pageprice = pagePriceBySearchEngine(queryKey, null, pageprice);
		// map
		Map<Integer, String> keyMap = new HashMap<Integer, String>();
		for (PriceDO priceDo : pageprice.getRecords()) {
			PriceDto2 priceDto2 = new PriceDto2();
			List<PriceDto2> list = new ArrayList<PriceDto2>();
			// type_id
			Integer typeId = priceDo.getTypeId();
			// get mapresult
			String mapResult = keyMap.get(priceDo.getTypeId());

			// if empty then query and put
			if (StringUtils.isEmpty(mapResult)) {
				mapResult = priceCategoryDAO.queryTagNameByTypeId(typeId);
				keyMap.put(typeId, mapResult);
			}

			// set data
			priceDto2.setPrice(priceDo);
			priceDto2.setTypeName(mapResult);

			// list add
			list.add(priceDto2);

			// construct map
			map.put(priceDo.getId(), list);

		}

		return map;
	};

	@Override
	public String queryContentByPageEngine(String priceSearchKey,
			String zaocanSearchKey, String wanbaoSearchKey) {
		String content = "<div> <span >相关文章：</span></div> "
				+ "<div><p> "
				+ "#foreach($price in $!{list})"
				+ " <p>"
				+ "<span style='color:#0000ff;'>${velocityCount}.</span></a>"
				+ "<a href='http://price.zz91.com/priceDetails_$!{price.id}.htm' style='font-size:12px;' target='_blank'>"
				+ "<span style='color:#0000ff;'>$!{price.title}</span></a>"
				+ "</p>"
				+ "#end"
				+ "</div> <div> </div> <div> "
				+ "<p> <a href='http://xiazai.zz91.com/search/result/?searchid=9' style='margin: 0px; padding: 0px; color: rgb(16, 16, 16); text-decoration: initial; text-indent: 2em; background-color: rgb(248, 248, 248); font-family: 宋体, serif;' target='_blank'>"
				+ "<span >【ZZ91废金属行情早参】每天5分钟，纵揽全球废金属最精彩行情“早餐”！</span></a></p> "
				+ "<div> "
				+ "<div> "
				+ "<div style='font-size: 14px;'>"
				+ "<div> "
				+ "<div> "
				+ " <p> <span><a href='http://xiazai.zz91.com/search/result/?searchid=48' style='margin: 0px; padding: 0px; color: rgb(16, 16, 16); text-decoration: initial;' target='_blank'>"
				+ "<span>【ZZ91废金属晚报】每日17点半，ZZ91为您整合最新废金属行情动态！</span></a></span></p>"
				+ "<p> <span><a href='http://xiazai.zz91.com/view/387.html' style='margin: 0px; padding: 0px; color: rgb(16, 16, 16); text-decoration: initial;' target='_blank'>"
				+ "<span>【ZZ91废料内参】每周一刊，盘点一周重大废旧行业新闻！</span></a></span></p>"
				+ "</div> "
				+ "<p> <span><a href='http://xiazai.zz91.com/view/892.html' style='margin: 0px; padding: 0px; color: rgb(16, 16, 16); text-decoration: initial;'>&nbsp;</a></span></p>"
				+ "<p> <span>&nbsp;<strong><a href='http://daohang.zz91.com/kuaijie' style='margin: 0px; padding: 0px; color: rgb(0, 146, 65); text-decoration: initial;' target='_blank'><img src='http://img1.zz91.com/bbsPost/2012/3/26/13327300632836987.jpg' style='margin: 0px; padding: 0px; width: 482px; height: 115px;' /></a></strong></span></p>"
				+ "<div></div>";
		// 存放price
		Map<String, Object> outmap = new HashMap<String, Object>();
		// 存放title
		PageDto<PriceDO> pagePrice = new PageDto<PriceDO>();
		PageDto<PriceDO> pageZC = new PageDto<PriceDO>();
		PageDto<PriceDO> pageWB = new PageDto<PriceDO>();

		pagePrice.setPageSize(4);
		pageZC.setPageSize(1);
		pageWB.setPageSize(1);
		// 后两条数据四条数据
		pagePrice = pagePriceBySearchEngine(priceSearchKey, null, pagePrice);
		// 早参数据一条数据
		pageZC = pagePriceBySearchEngine(zaocanSearchKey, null, pageZC);
		// 晚报数据一条数据
		pageWB = pagePriceBySearchEngine(wanbaoSearchKey, null, pageWB);

		// 以下是使用list的方式
		List<PriceDO> priceDOList = pageZC.getRecords();
		// 晚报列表add
		priceDOList.addAll(pageWB.getRecords());
		// 循环判断是否重复 添加 两条最新报价
		for (PriceDO priceDO : pagePrice.getRecords()) {
			if (priceDOList.size() > 3) {
				break;
			} else {
				boolean b = true;
				for (PriceDO obj : priceDOList) {
					if ((priceDO.getId().equals(obj.getId()))) {
						b = false;
						break;
					}
				}
				if (b) {
					priceDOList.add(priceDO);
				}
			}
		}
		outmap.put("list", priceDOList);

		return buildVelocityByTemplate(outmap, content);
	}

	@Override
	public String buildTemplateContent(String priceSearchKey,
			String zaocanSearchKey, String wanbaoSearchKey, String tags) {
		// 存放price
		Map<String, Object> outmap = new HashMap<String, Object>();
		// 存放title
		PageDto<PriceDO> pagePrice = new PageDto<PriceDO>();
		PageDto<PriceDO> pageZC = new PageDto<PriceDO>();
		PageDto<PriceDO> pageWB = new PageDto<PriceDO>();
		pagePrice.setPageSize(4);
		pageZC.setPageSize(1);
		pageWB.setPageSize(1);
		// 后两条数据四条数据
		pagePrice = pagePriceBySearchEngine(priceSearchKey, null, pagePrice);
		// 早参数据一条数据
		pageZC = pagePriceBySearchEngine(zaocanSearchKey, null, pageZC);
		// 晚报数据一条数据
		pageWB = pagePriceBySearchEngine(wanbaoSearchKey, null, pageWB);

		// 以下是使用list的方式
		List<PriceDO> priceDOList = pageZC.getRecords();
		// 晚报列表add
		priceDOList.addAll(pageWB.getRecords());
		// 循环判断是否重复 添加 两条最新报价
		for (PriceDO priceDO : pagePrice.getRecords()) {
			if (priceDOList.size() > 3) {
				break;
			} else {
				boolean b = true;
				for (PriceDO obj : priceDOList) {
					if ((priceDO.getId().equals(obj.getId()))) {
						b = false;
						break;
					}
				}
				if (b) {
					priceDOList.add(priceDO);
				}
			}
		}
		outmap.put("list", priceDOList);

		// 本文标签 信息
		Map<String, String> encodeTagsMap = new HashMap<String, String>();
		for (String t : tags.split(",")) {
			encodeTagsMap.put(CNToHexUtil.getInstance().encode(t), t);
		}
		outmap.put("keyMap", encodeTagsMap);
		return buildVelocityByTemplate(outmap, CONTENT_PRICE_TEMPLATE);
	}

	private String buildVelocityByTemplate(Map<String, Object> out,
			String content) {
		StringWriter w = new StringWriter();
		VelocityContext c = new VelocityContext(out);
		try {
			Velocity.evaluate(c, w, "idANDTitle", content);
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			e.printStackTrace();
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return w.toString();
	}

	public Integer getPriceRecordConutByCondition(PriceDTO price) {
		Assert.notNull(price, "price is not null");
		return priceDAO.getPriceRecordConutByCondition(price);
	}

	public Integer insertPrice(PriceDO price) throws IllegalArgumentException {
		Assert.notNull(price, "The Object of price must not be null.");
		Assert.notNull(price.getTitle(), "The price title must not be null.");
		if (price.getIsChecked() == null) {
			price.setIsChecked("0");
		}
		if (price.getIsIssue() == null) {
			price.setIsIssue("0");
		}
		if (price.getAssistTypeId() == null) {
			price.setAssistTypeId(0);
		}
		if (price.getContent() != null && price.getContent().length() > 65535) {
			price.setContent(Jsoup.clean(price.getContent(), Whitelist
					.relaxed()));
		}
		return priceDAO.insertPrice(price);
	}

	public List<PriceDTO> queryMiniPriceByCondition(PriceDTO price) {
		Assert.notNull(price, "price is not null");
		// 循环查询 UV
		List<PriceDTO> list = priceDAO.queryMiniPriceByCondition(price);
		for (PriceDTO obj : list) {
			obj.setIp(priceDAO.queryUVById(obj.getPrice().getId()));
		}
		return list;
	}

	// @Deprecated
	// public PriceDO queryPriceById(Integer id) throws IllegalArgumentException
	// {
	// Assert.notNull(id, "The id must not be null.");
	// return priceDAO.queryPriceById(id);
	// }

	public Integer updatePriceById(PriceDO price)
			throws IllegalArgumentException {
		Assert.notNull(price, "The object of price must not be null.");
		if (price.getIsChecked() == null) {
			price.setIsChecked("0");
		}
		if (price.getIsIssue() == null) {
			price.setIsIssue("0");
		}
		if (price.getTypeId() == null) {
			price.setTypeId(0);
		}
		return priceDAO.updatePriceById(price);
	}

	public PriceDTO queryPriceByIdForEdit(Integer id)
			throws IllegalArgumentException {
	    
		return priceDAO.queryPriceByIdForEdit(id);
	}

	// public List<PriceDO> queryPriceByClickNumber() {
	//
	// return priceDAO.queryPriceByClickNumber();
	// }

	public List<ForPriceDTO> queryPriceByParentId(Integer parentId, Integer max) {
		if (max == null) {
			max = 10;
		}
		return priceDAO.queryPriceByParentId(parentId, max);
	}

	public List<PriceDO> queryPriceByTitleAndTypeId(String typeId,
			String title, String limitSize) {
		if (!StringUtils.isNumber(limitSize)) {
			limitSize = "10";
		}
		if (StringUtils.isEmpty(title) && StringUtils.isEmpty(typeId)) {
			return null;
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeId", typeId);
		param.put("title", title);
		param.put("limitSize", Integer.valueOf(limitSize));

		return priceDAO.queryPriceByTitleAndTypeId(param);
	}

	// public List<PriceDO> queryPriceByAssistTypeId(PriceDTO priceDTO) {
	// Assert.notNull(priceDTO, "priceDTO is not null");
	// return priceDAO.queryPriceByAssistTypeId(priceDTO);
	// }

	public PriceDO queryDownPriceById(Integer id)
			throws IllegalArgumentException {
		Assert.notNull(id, "id is not null");
		return priceDAO.queryDownPriceById(id);
	}

	public PriceDO queryOnPriceById(Integer id) throws IllegalArgumentException {
		Assert.notNull(id, "id is not null");
		return priceDAO.queryOnPriceById(id);
	}

	public List<ForPriceDTO> queryEachPriceByParentId(Integer parentId) {
		Assert.notNull(parentId, "parentId is not null");
		return priceDAO.queryEachPriceByParentId(parentId);
	}

	public List<ForPriceDTO> queryPriceAndCategoryByTypeId(Integer typeId,
			Integer max) {
		if (max == null) {
			max = 10;
		}
		return priceDAO.queryPriceAndCategoryByTypeId(typeId, max);
	}

	public PriceDO queryTopGmtOrderByParentId(Integer parentId) {
		Assert.notNull(parentId, "parentId is not null");
		return priceDAO.queryTopGmtOrderByParentId(parentId);
	}

	public List<PriceCategoryDTO> queryPriceAndCategory(
			List<PriceCategoryDO> list) {
		List<PriceCategoryDTO> listDTO = new ArrayList<PriceCategoryDTO>();
		for (PriceCategoryDO aDo : list) {
			PriceCategoryDTO priceCategoryDTO = new PriceCategoryDTO();
			priceCategoryDTO.setPriceCategoryDO(aDo);

			List<PriceDO> aDtos = priceDAO.queryPriceByTypeId(aDo.getId(),
					null, null, 3);
			priceCategoryDTO.setPriceChildDO(aDtos);
			listDTO.add(priceCategoryDTO);
		}
		return listDTO;
	}

	public List<PriceCategoryDTO> queryPriceList(Integer id,
			Integer assistTypeId, Integer pageSize) {

		List<PriceCategoryDO> list = priceCategoryDAO
				.queryPriceCategoryByParentId(id);

		List<PriceCategoryDTO> listDTO = new ArrayList<PriceCategoryDTO>();
		for (PriceCategoryDO aDo : list) {
			PriceCategoryDTO priceCategoryDTO = new PriceCategoryDTO();
			priceCategoryDTO.setPriceCategoryDO(aDo);

			List<PriceDO> aDtos = priceDAO.queryPriceByTypeId(aDo.getId(),
					null, assistTypeId, pageSize);

			priceCategoryDTO.setPriceChildDO(aDtos);
			listDTO.add(priceCategoryDTO);
		}
		return listDTO;

	}

	@Override
	public List<PriceCategoryDTO> queryPriceCategoryInfoByParentIdAndAssistId(
			Integer parentId, Integer assistId, Integer topNum) {
		List<PriceCategoryDO> cateList = priceCategoryDAO
				.queryPriceCategoryByParentId(parentId);
		List<PriceCategoryDTO> priceCategoryInfoList = new ArrayList<PriceCategoryDTO>();
		for (PriceCategoryDO pcate : cateList) {
			PriceCategoryDTO dto = new PriceCategoryDTO();
			dto.setPriceCategoryDO(pcate);

			dto.setPriceChildDO(priceDAO.queryPriceByTypeId(pcate.getId(),null, assistId, topNum));

			priceCategoryInfoList.add(dto);
		}
		return priceCategoryInfoList;
	}

	// public Integer queryPriceCountByTypeId(PriceDTO priceDTO) {
	// Assert.notNull(priceDTO, "priceDTO is not null");
	// return priceDAO.queryPriceCountByTypeId(priceDTO);
	// }

	@Override
	public PageDto<PriceDO> queryPricePaginationListByTitle(
			String titleKeywords, PageDto<PriceDO> page) {
		Assert.notNull(titleKeywords, "titleKeywords is not null");
		return priceDAO.queryPricePaginationListByTitle(titleKeywords, page);
	}

	@Override
	public PageDto<PriceDO> pagePriceBySearchEngine(String titleKeywords,
			PriceDO priceDO, PageDto<PriceDO> page) {

		if (page.getStartIndex() != null && page.getStartIndex() >= 10000) {
			page.setStartIndex(10000);
		}

		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		List<PriceDO> list = new ArrayList<PriceDO>();
		// 关键字
		if (StringUtils.isNotEmpty(titleKeywords)) {
			sb = buildStringQuery(sb, "(title,tags,search_label)",
					titleKeywords);
		}
		try {
			// 时间范围
			if (priceDO != null && priceDO.getGmtOrder() != null) {
				long min = DateUtil
						.getDate(priceDO.getGmtOrder(), "yyyy-MM-dd").getTime() / 1000;
				long max = DateUtil.getDateAfterDays(
						DateUtil.getDate(priceDO.getGmtOrder(), "yyyy-MM-dd"),
						1).getTime() / 1000 - 1;
				cl.SetFilterRange("gmt_order", min, max, false);
			}

			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_order desc");
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 10000);
			SphinxResult res = cl.Query(sb.toString(), "price");

			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				for (int i = 0; i < res.matches.length; i++) {
					PriceDO obj = new PriceDO();
					SphinxMatch sphinxMatch = res.matches[i];
					Map<String, Object> resultMap = SearchEngineUtils
							.getInstance().resolveResult(res, sphinxMatch);
					obj.setId(Integer.valueOf(resultMap.get("pid").toString()));
					obj.setTypeId(Integer.valueOf(resultMap.get("type_id")
							.toString()));
					obj.setTitle(resultMap.get("ptitle").toString());
					try {
						// string 格式
						obj.setGmtCreated(DateUtil.getDate(resultMap.get(
								"gmt_time").toString(), "yyyy-MM-dd"));
						// int 格式
						obj.setGmtOrder(new Date(Long.valueOf(resultMap.get(
								"gmt_order").toString()
								+ "000")));
					} catch (ParseException e) {
					}
					list.add(obj);
				}
				page.setRecords(list);
			}
		} catch (SphinxException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return page;
	}

	private StringBuffer buildStringQuery(StringBuffer sb, String column,
			String keywords) {
		if (StringUtils.isNotEmpty(keywords)) {
			if (sb.indexOf("@") != -1) {
				sb.append("&");
			}
			sb.append("@").append(column).append(" ").append(keywords);
		}
		return sb;
	}

	// public Integer queryPriceCountByCondition(PriceDTO priceDTO) {
	// Assert.notNull(priceDTO, "priceDTO is not null");
	// return priceDAO.queryPriceCountByCondition(priceDTO);
	// }

	// public List<PriceDO> queryPriceByTagsCondition(TagsRelateArticleDTO
	// tagsRelateArticleDTO) {
	// Assert.notNull(tagsRelateArticleDTO, "tagsRelateArticleDTO is not null");
	// return priceDAO.queryPriceByTagsCondition(tagsRelateArticleDTO);
	// }

	// public Integer queryPriceCountByTagsCondition(TagsRelateArticleDTO
	// tagsRelateArticleDTO) {
	// Assert.notNull(tagsRelateArticleDTO, "tagsRelateArticleDTO is not null");
	// return priceDAO.queryPriceCountByTagsCondition(tagsRelateArticleDTO);
	// }

	public List<PriceDO> queryLatestPriceByTypeId(Integer id) {
		return priceDAO.queryLatestPriceByTypeId(id);
	}

	@Override
	public Integer updateIsCheckedById(Integer id, String isChecked) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(isChecked, "the isChecked must not be null");

		return priceDAO.updateIsCheckedById(id, isChecked);
	}

	// public List<PriceDO> queryPriceByTypeIdAndAssistTypeId(Integer typeId,
	// Integer assistTypeId) {
	// return priceDAO.queryPriceByTypeIdAndAssistTypeId(typeId, assistTypeId);
	// }

	// @Override
	// public PriceDO queryPriceForSubscribe(Integer typeId, Integer
	// assistTypeId) {
	// Assert.notNull(typeId, "the typeId must not be null");
	//
	// return priceDAO.queryPriceForSubscribe(typeId, assistTypeId);
	// }

	@Override
	public List<PriceDTO> queryNewPriceOnWeek(Date firstDate, Date lastDate,
			Integer size) {

		return priceDAO.queryNewPriceOnWeek(firstDate, lastDate, size);
	}

	public List<PriceDO> queryPriceByTypeId(Integer typeId, Integer parentId,
			Integer assistTypeId, Integer max) {
		if (max == null) {
			max = 10;
		}
		return priceDAO.queryPriceByTypeId(typeId, parentId, assistTypeId, max);
	}

	@Override
	public PageDto<PriceDO> pagePriceByType(Integer typeId, Integer parentId,
			Integer assistTypeId, PageDto<PriceDO> page) {
		page.setRecords(priceDAO.queryPriceByType(typeId, parentId,
				assistTypeId, page));
		page.setTotalRecords(priceDAO.queryPriceByTypeCount(typeId, parentId,
				assistTypeId));
		return page;
	}

	@Override
	public Map<Integer, List<PriceDto2>> queryPriceOfParentCategory(
			Integer parentId, Integer topNum) {
		if (topNum == null) {
			topNum = 10;
		}
		List<PriceCategoryDO> cateList = priceCategoryDAO
				.queryPriceCategoryByParentId(parentId);
		Map<Integer, List<PriceDto2>> map = new LinkedHashMap<Integer, List<PriceDto2>>();
		for (PriceCategoryDO pcate : cateList) {
			List<PriceDO> priceList = priceDAO.queryPriceByTypeIdOrParentId(
					pcate.getId(), topNum);
			if (priceList != null) {
				List<PriceDto2> list = new ArrayList<PriceDto2>();
				for (PriceDO price : priceList) {
					PriceDto2 dto = new PriceDto2();
					dto.setTypeName(pcate.getName());
					dto.setPrice(price);
					list.add(dto);
				}
				map.put(pcate.getId(), list);
			}
		}
		return map;
	}

	@Override
	public List<ForPriceDTO> queryPriceByIndex(String code, Integer max) {
		if (max == null) {
			max = 10;
		}
		if (code == null) {
			return null;
		}
		return priceDAO.queryPriceByIndex(code, max);
	}

	@Override
	public List<ForPriceDTO> queryLatestPrice(String code, Integer max) {
		if (max == null) {
			max = 10;
		}
		return priceDAO.queryLatestPrice(code, max);
	}

	@Override
	public Integer updateRealClickNumberById(Integer id) {
		PriceDTO dto = priceDAO.queryPriceByIdForEdit(id);
		if (dto == null || dto.getPrice() == null) {
			return null;
		}
		Integer number = dto.getPrice().getRealClickNumber();
		if (number == null) {
			number = 1;
		} else {
			number = number + 1;
		}
		return priceDAO.updateRealClickNumberById(number, id);
	}

	@Override
	public Integer queryPriceCount() {
		String from = DateUtil.toString(new Date(), "yyyy-MM-dd");
		return priceDAO.queryPriceCount(from);
	}

	@Override
	public List<PriceDO> queryPriByTypeId() {
		return priceDAO.queryPriByTypeId();
	}

	@Override
	public List<PriceDO> queryPriceByType(Integer typeId, Integer parentId,
			Integer assistTypeId, PageDto<PriceDO> page) {
		if (page.getPageSize() == null || page.getPageSize() == 0) {
			page.setPageSize(10);
		}
		if (StringUtils.isEmpty(page.getSort())) {
			page.setSort("gmt_order");
		}
		if (StringUtils.isEmpty(page.getDir())) {
			page.setDir("desc");
		}
		return priceDAO.queryPriceByType(typeId, parentId, assistTypeId, page);
	}

	@Override
	public Integer queryTypeidById(Integer id) {
		return priceDAO.getTypeidById(id);
	}

	@Override
	public Integer queryUVById(Integer id) {
		if (id == null || id == 0) {
			return 0;
		}
		return priceDAO.queryUVById(id);
	}

	@Override
	public Boolean forbidDoublePub(String title, Date date) {
		do {
			// 默认起始时间为 发报价当天零点
			if (date == null) {
				try {
					date = DateUtil.getDate(new Date(), "yyyy-MM-dd");
				} catch (ParseException e) {
					date = new Date();
				}
			}

			// 空判断
			if (StringUtils.isEmpty(title)) {
				break;
			}
			// 根据标题检索
			Integer id = priceDAO.queryIdByTitle(title, DateUtil.toString(date,
					"yyyy-MM-dd"));
			if (id == null || id <= 0) {
				break;
			}
			return true;
		} while (false);
		return false;
	}

	@Override
	public void updateAllSearchLabel() {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		for (int i = 1; i <= 270; i++) {
			String name = priceCategoryDAO.queryTagNameByTypeId(i);
			// if(StringUtils.isEmpty(name)){
			// System.out.println(name+":"+i);
			// break;
			// }

			Integer parentId = priceCategoryDAO.queryParentIdById(i);
			do {
				if (parentId == null || parentId == 1 || parentId == 2
						|| parentId == 0) {
					break;
				}
				name = priceCategoryDAO.queryTagNameByTypeId(parentId) + "-"
						+ name;
				parentId = priceCategoryDAO.queryParentIdById(parentId);
			} while (true);
			map.put(i, name);
		}
		for (Integer id : map.keySet()) {
			priceCategoryDAO.updateSearchLabel((String) map.get(id), id);
		}
	}

	@Override
	public List<PriceDO> queryListByFromTo(String fromStr, String toStr) {
		Date from = null, to = null;
		if (StringUtils.isNotEmpty(fromStr)) {
			try {
				from = DateUtil.getDate(fromStr, "yyyy-MM-dd");
			} catch (ParseException e) {
				from = new Date();
			}
		}
		if (StringUtils.isNotEmpty(toStr)) {
			try {
				to = DateUtil.getDate(toStr, "yyyy-MM-dd");
			} catch (ParseException e) {
				to = new Date();
			}
		}
		Integer id;
		try {
			id = DateUtil.getIntervalDays(from, to);
		} catch (ParseException e) {
			id = 31;
		}
		if (id.intValue() > 30) {
			from = DateUtil.getDateAfterDays(to, -30);
		}
		return priceDAO.queryListByFromTo(
				DateUtil.toString(from, "yyyy-MM-dd"), DateUtil.toString(to,
						"yyyy-MM-dd"));
	}

	@Override
	public List<PriceDO> queryListByTypeId(Integer typeId, Integer size) {
		if (size == null) {
			size = 10;
		}
		if (size > 50) {
			size = 50;
		}
		return priceDAO.queryListByTypeId(typeId, size);
	}

	@Override
	public List<PriceDTO> queryListByParentId(Integer parentId, Integer size) {
		List<PriceCategoryDO> pcList = priceCategoryDAO
				.queryPriceCategoryByParentId(parentId);
		List<PriceDO> plist = new ArrayList<PriceDO>();
		for (PriceCategoryDO obj : pcList) {
			plist.addAll(priceDAO.queryListByTypeId(obj.getId(), size));
		}
		return getReverseList(plist, size);
	}

	@Override
	public List<PriceDTO> queryListByIntArray(Integer[] ids, Integer size) {
		List<PriceDO> plist = new ArrayList<PriceDO>();
		for (Integer id : ids) {
			plist.addAll(priceDAO.queryListByTypeId(id, size));
		}
		return getReverseList(plist, size);
	}

	private List<PriceDTO> getReverseList(List<PriceDO> plist, Integer size) {
		List<PriceDTO> list = new ArrayList<PriceDTO>();
		Map<Integer, PriceDO> map = new TreeMap<Integer, PriceDO>()
				.descendingMap();
		// 遍历list排序
		for (PriceDO obj : plist) {
			map.put(obj.getId(), obj);
		}

		for (Integer id : map.keySet()) {
			PriceDTO dto = new PriceDTO();
			PriceDO obj = map.get(id);
			dto.setPrice(obj);
			dto.setTypeName(priceCategoryDAO.queryTagNameByTypeId(obj
					.getTypeId()));
			list.add(dto);
			if (list.size() == size) {
				break;
			}
		}

		return list;
	}

	public static void main(String[] args) throws Exception {
		StringWriter w = new StringWriter();
		Map<String, Object> out = new HashMap<String, Object>();
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put("e5ba9fe98791e5b19e283529", "好");
		out.put("keyMap", keyMap);
		VelocityContext c = new VelocityContext(out);
		try {
			Velocity.evaluate(c, w, "idANDTitle", CONTENT_PRICE_TEMPLATE);
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			e.printStackTrace();
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(w.toString());

	}

}
