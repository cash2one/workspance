/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-1 下午05:00:23
 */
package com.ast.ast1949.service.products.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.company.CrmCompanySvr;
import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsExpire;
import com.ast.ast1949.domain.products.ProductsHide;
import com.ast.ast1949.domain.products.ProductsKeywordsRankDO;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.domain.products.ProductsStar;
import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.domain.spot.SpotInfo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.products.KeywordSearchDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.dto.products.ProductsKeywordsRankDTO;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.CrmCompanySvrDao;
import com.ast.ast1949.persist.oauth.OauthAccessDao;
import com.ast.ast1949.persist.products.ProductAddPropertiesDAO;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.products.ProductsExpireDao;
import com.ast.ast1949.persist.products.ProductsHideDao;
import com.ast.ast1949.persist.products.ProductsKeywordsRankDAO;
import com.ast.ast1949.persist.products.ProductsPicDAO;
import com.ast.ast1949.persist.products.ProductsSpotDao;
import com.ast.ast1949.persist.products.ProductsStarDao;
import com.ast.ast1949.persist.sample.SampleDao;
import com.ast.ast1949.persist.spot.SpotInfoDao;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.oauth.OauthAccessService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.CNToHexUtil;
import com.zz91.util.analzyer.IKAnalzyerUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.SensitiveUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;
import com.zz91.util.tags.TagsUtils;

/**
 * @author Ryan(rxm1025@gmail.com)
 * 
 */
@Component("productsService")
public class ProductsServiceImpl implements ProductsService {


	final static Integer CARDINAL_NUMBER = 300;
	final static Integer TOTAL_RECORDS = 100000;

	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private ProductsPicDAO productsPicDAO;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private ProductsSpotDao productsSpotDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private SpotInfoDao spotInfoDao;
	@Resource
	private ProductAddPropertiesDAO productAddPropertiesDAO;
	@Resource
	private OauthAccessDao oauthAccessDao;
	@Resource
	private SampleDao sampleDao;
	@Resource
	private ProductsKeywordsRankDAO productsKeywordsRankDAO;
	@Resource
	private CrmCompanySvrDao crmCompanySvrDao;
	@Resource
	private ProductsExpireDao productsExpireDao;
	@Resource
	private ProductsHideDao productsHideDao;
	@Resource
	private ProductsStarDao productsStarDao;
	
	@Override
	public ProductsDto queryProductsDetailsById(Integer id) {
		Assert.notNull(id, "the id can not be null");

		SpotInfo spotInfo = new SpotInfo();
		ProductsDto dto = new ProductsDto();

		ProductsDO products = productsDAO.queryProductsById(id);
		if (products == null) {
			return null;
		}
		products.setDetails(Jsoup.clean(products.getDetails(), Whitelist.relaxed()));
		List<ProductAddProperties> pap = productAddPropertiesDAO.queryByProductId(id, "0");

		// 获取现货属性
		if (ProductsService.GOOD_TYPE_CODE_SPOT.equals(products.getGoodsTypeCode())) {
			ProductsSpot productsSpot = productsSpotDao.queryByProductId(id);
			if (productsSpot != null) {
				spotInfo = spotInfoDao.queryOne(productsSpot.getId());
				if (spotInfo != null) {
					if (spotInfo.getLevel() != null) {
						spotInfo.setLevel(spotInfo.getLevel().trim());
					}
					if (spotInfo.getShape() != null) {
						spotInfo.setShape(spotInfo.getShape().trim());
					}
				}
			}
		}
		String mainCode = products.getCategoryProductsMainCode();
		// 将通用固定属性从增加属性表中添加到dto中
		for (int i = 0; i < pap.size(); i++) {
			// 只有“品位”是属于金属时才放入dto
			if (mainCode.length() >= 4
					&& mainCode.substring(0, 4).equals("1000")
					&& pap.get(i).getProperty().equals("品位")) {
				dto.setGrade(pap.get(i).getContent());
			}
			if (pap.get(i).getProperty().equals("交易说明")) {
				dto.setAddTransaction(pap.get(i).getContent());
			}
			// 只有“形态”是属于塑料和金属时才放入dto
			if (mainCode.length() >= 4
					&& (mainCode.substring(0, 4).equals("1001") || mainCode
							.substring(0, 4).equals("1000"))
					&& pap.get(i).getProperty().equals("形态")) {
				dto.setAddShape(pap.get(i).getContent());
			}
			// 只有“级别”是属于塑料时才放到dto
			if (mainCode.length() >= 4
					&& mainCode.substring(0, 4).equals("1001")
					&& pap.get(i).getProperty().equals("级别")) {
				dto.setAddLevel(pap.get(i).getContent());
			}
		}

		Date start = products.getRefreshTime();
		Date end = products.getExpireTime();
		long day = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);
		if (day > 90) {
			day = -1;
		}

		dto.setProducts(products);
		dto.setPostlimittime(day);
		dto.setSpotInfo(spotInfo);
		dto.setProductsTypeLabel(CategoryFacade.getInstance().getValue(
				products.getProductsTypeCode()));
		dto.setGoodsTypeLabel(CategoryFacade.getInstance().getValue(
				products.getGoodsTypeCode()));
		dto.setManufactureLabel(CategoryFacade.getInstance().getValue(
				products.getManufacture()));
		dto.setCategoryProductsMainLabel(CategoryProductsFacade.getInstance()
				.getValue(products.getCategoryProductsMainCode()));
		dto.setCategoryProductsAssistLabel(CategoryProductsFacade.getInstance()
				.getAssistValue(products.getCategoryProductsAssistCode()));
		return dto;
	}

	@Override
	public ProductsDO queryProductsById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return productsDAO.queryProductsById(id);
	}

	@Override
	public ProductsDO queryProductsWithOutDetailsById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return productsDAO.queryProductsWithOutDetailsById(id);
	}

	@Override
	public List<ProductsDto> queryProductsOfCompany(Integer companyId,
			Integer maxSize) {
		if (maxSize == null) {
			maxSize = 10;
		}
		List<ProductsDto> list = productsDAO.queryProductsWithPicByCompany(
				companyId, maxSize);
		List<ProductsDto> nlist = new ArrayList<ProductsDto>();
		for (ProductsDto dto : list) {
			dto.getProducts().setDetails(
					Jsoup.clean(productsDAO.queryProductsById(
							dto.getProducts().getId()).getDetails(), Whitelist
							.none()));
			nlist.add(dto);
		}
		return list;
	}

	public List<ProductsDO> queryProductsByIdWithConditon(Integer companyId) {
		Assert.notNull(companyId, "the companyId can not be null");
		List<ProductsDO> list = productsDAO
				.queryProductsByIdWithConditon(companyId);
		return list;
	}

	@Override
	public PageDto<ProductsDto> pageProductsWithPicByCompanyEsite(
			Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page) {
		page.setTotalRecords(productsDAO
				.queryProductsWithPicByCompanyEsiteCount(companyId, kw, sid));
		page.setRecords(productsDAO.queryProductsWithPicByCompanyEsite(
				companyId, kw, sid, page));
		return page;
	}

	@Override
	public List<ProductsDto> queryProductsByMainCode(String mainCode,
			String typeCode, Integer maxSize) {

		List<ProductsDto> productsList = productsDAO.queryProductsByMainCode(
				mainCode, typeCode, maxSize);

		CategoryFacade categoryFacade = CategoryFacade.getInstance();
		for (ProductsDto dto : productsList) {
			dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
					.getProductsTypeCode()));
			dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
					.getAreaCode()));
			dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
					.getMembershipCode()));
			dto.setCategoryProductsMainLabel(CategoryProductsFacade
					.getInstance().getValue(
							dto.getProducts().getCategoryProductsMainCode()));
			dto.setCategoryProductsAssistLabel(CategoryProductsFacade
					.getInstance().getValue(
							dto.getProducts().getCategoryProductsAssistCode()));
		}

		return productsList;
	}

	@Override
	public PageDto<ProductsDto> pageProductsWithCompanyForInquiry(
			Company company, ProductsDO products, PageDto<ProductsDto> page) {
		if (page.getSort() == null) {
			page.setSort("p.refresh_time");
		}
		if (page.getDir() == null) {
			page.setDir("desc");
		}
		page.setRecords(productsDAO.queryProductsWithCompanyForInquiry(company,products, page));
		page.setTotalRecords(productsDAO.queryProductsWithCompanyForInquiryCount(company, products));
		return page;
	}

	@Override
	public List<ProductsDto> queryNewProductsOnWeek(Integer maxSize) {
		Date now = new Date();
		Date start = DateUtil.getDateAfterDays(now, 0);
		Date end = DateUtil.getDateAfterDays(now, -7);
		List<ProductsDto> list = productsDAO.queryNewProductsIntervalDay(start,
				end, maxSize);
		if (list == null) {
			return null;
		}
		CategoryFacade categoryFacade = CategoryFacade.getInstance();
		for (ProductsDto dto : list) {
			dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
					.getProductsTypeCode()));
			dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
					.getAreaCode()));
			dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
					.getMembershipCode()));
		}
		return list;
	}

	@Override
	public PageDto<ProductsDto> pageProductsByAdmin(Company company,
			ProductsDO products, String statusArray, PageDto<ProductsDto> page,
			String from, String to, String selectTime) throws ParseException {
		if (to != null) {
			try {
				to = DateUtil.toString(DateUtil.getDateAfterDays(DateUtil.getDate(to, "yyyy-MM-dd"), 1), "yyyy-MM-dd");
			} catch (ParseException e) {
			}
		}

		if (StringUtils.isEmpty(statusArray)) {
			statusArray = ",";
		}
		List<ProductsDto> list = productsDAO.queryProductsByAdmin(company,products, StringUtils.StringToIntegerArray(statusArray), page,from, to, selectTime);
		if (list == null) {
			return null;
		}
		CategoryFacade categoryFacade = CategoryFacade.getInstance();
		for (ProductsDto dto : list) {
			String status = "";
			do {
				if (dto == null) {
					break;
				}
				if (dto.getProducts() == null) {
					break;
				}
				// 公司是否属于黑名单
				if ("1".equals(dto.getCompany().getIsBlock())) {
					dto.getCompany().setName(dto.getCompany().getName() + "(拉黑)");
				}
				// 信息已删除 或者 账户被禁用
				if (dto.getProducts().getIsDel() == true) {
					status = "(已删除)";
					break;
				}
				// 暂不发布
				if (dto.getProducts().getIsPause().equals("1")) {
					status = "(暂不发布)";
					break;
				}
				ProductsHide productsHide=productsHideDao.queryByProductId(dto.getProducts().getId());
				if(productsHide!=null){
					dto.getProducts().setHide("1");
					status="(已隐藏)";
					break;
				}
				// 是否过期
				long expired = dto.getProducts().getExpireTime().getTime();
				long today = new Date().getTime();
				long result = today - expired;
				if (result > 0) {
					status = "(已过期)";
					break;
				}
			} while (false);
			dto.getProducts().setTitle(dto.getProducts().getTitle() + status);
			dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts().getProductsTypeCode()));
			dto.setAreaLabel(categoryFacade.getValue(dto.getCompany().getAreaCode()));
			dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany().getMembershipCode()));
			Sample sample = sampleDao.queryByProductId(dto.getProducts().getId());
			if (sample!=null&&sample.getIsDel()!=null&&sample.getIsDel()==0) {
				dto.getProducts().setTitle(dto.getProducts().getTitle() + "(样品)");
			}
			// 获得星级
			ProductsStar ps = productsStarDao.queryByProductsId(dto.getProducts().getId());
			if (ps!=null) {
				dto.getProducts().setScore(ps.getScore());
			}else{
				dto.getProducts().setScore(0);
			}
		}
		page.setRecords(list);
		page.setTotalRecords(productsDAO.queryProductsByAdminCount(company,products, StringUtils.StringToIntegerArray(statusArray), from,to, selectTime));
		return page;
	}

	@Override
	public PageDto<ProductsDto> pageProductsByAdminZstExpried(
			Integer isRecheck, PageDto<ProductsDto> page, ProductsDO products) {
		List<ProductsDto> list = productsDAO.queryProductsByAdminZstExpried(
				isRecheck, page, products);
		if (list == null) {
			return null;
		}

		CategoryFacade categoryFacade = CategoryFacade.getInstance();
		for (ProductsDto dto : list) {
			String status = "";
			do {
				// 公司是否属于黑名单
				if ("1".equals(dto.getCompany().getIsBlock())) {
					dto.getCompany().setName(
							dto.getCompany().getName() + "(黑名单)");
				}
				// 信息已删除 或者 账户被禁用
				if (dto.getProducts().getIsDel() == true
						|| "1".equals(dto.getCompany().getIsBlock())) {
					status = "(已删除)";
					break;
				}
				// 暂不发布
				if (dto.getProducts().getIsPause().equals("1")) {
					status = "(暂不发布)";
					break;
				}
				// 是否过期
				long expired = dto.getProducts().getExpireTime().getTime();
				long today = new Date().getTime();
				long result = today - expired;
				if (result > 0) {
					status = "(已过期)";
					break;
				}
			} while (false);
			dto.getProducts().setTitle(dto.getProducts().getTitle() + status);
			dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
					.getProductsTypeCode()));
			dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
					.getAreaCode()));
			dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
					.getMembershipCode()));
			Sample sample = sampleDao.queryByProductId(dto.getProducts().getId());
			if (sample!=null&&sample.getIsDel()!=null&&sample.getIsDel()==0) {
				dto.getProducts().setTitle(dto.getProducts().getTitle() + "(样品)");
			}
		}
		page.setRecords(list);
		page.setTotalRecords(productsDAO.queryProductsByAdminZstExpriedCount(
				isRecheck, products));
		return page;
	}

	public PageDto<ProductsDO> pageProductsOfCompany(Integer companyId,
			PageDto<ProductsDO> page) {
		page.setRecords(productsDAO.queryProductsOfCompany(companyId, null,
				page));
		page.setTotalRecords(productsDAO.queryProductsOfCompanyCount(companyId,
				null));
		return page;
	}

	public PageDto<ProductsDto> pageProductsByCode(Integer companyId,
			String productsTypeCode, PageDto<ProductsDto> page,String hide) {
		page.setRecords(productsDAO.queryProductsByCode(companyId,
				productsTypeCode, null, page,hide));
		page.setTotalRecords(productsDAO.queryProductsByCodeCount(companyId,
				productsTypeCode, null,hide));
		return page;
	}

	public PageDto<ProductsDO> pageProductsOfCompanyByStatus(Integer companyId,
			String account, String checkStatus, String isExpired,
			String isPause, String isPostFromInquiry, Integer groupId,
			String title, PageDto<ProductsDO> page) {
		// 排序
		if (page.getDir() == null) {
			page.setDir("desc");
		}
		if (page.getSort() == null) {
			page.setSort("real_time");
		}
		if (isPostFromInquiry == null) {
			isPostFromInquiry = "0";
		}
		List<ProductsDO> list = productsDAO.queryProductsOfCompanyByStatus(
				companyId, account, checkStatus, isExpired, isPause,
				isPostFromInquiry, groupId, title, page);
		for (ProductsDO obj : list) {
			Integer i = productsPicDAO.countPicIsNoPass(obj.getId());
			if (i > 0) {
				obj.setIsPicPass(0);
			}
			Sample sample = sampleDao.queryByProductId(obj.getId());
			if(sample!=null&&sample.getIsDel()!=null&&sample.getIsDel()==0){
				obj.setIsYP(true);
			}
			if (sample!=null) {
				obj.setSampleReason(sample.getUnpassReason());
			}
		}
		page.setRecords(list);
		page.setTotalRecords(productsDAO.queryProductsOfCompanyByStatusCount(companyId, account, checkStatus, isExpired, isPause,isPostFromInquiry, groupId, title));
		return page;
	}

	public Map<String, Integer> countProductsOfCompanyByStatus(
			Integer companyId, String account, String isPostFromInquiry,
			Integer initCount) {
		if (isPostFromInquiry == null) {
			isPostFromInquiry = "0";
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("checkStatus0", productsDAO
				.productsOfCompanyByStatusCount(companyId, account, "0",
						"0", "0", isPostFromInquiry, null, "",null));
		map.put("checkStatus1", productsDAO
				.productsOfCompanyByStatusCount(companyId, account, "1",
						"0", "0", isPostFromInquiry, null, "",null));
		map.put("checkStatus2", productsDAO
				.productsOfCompanyByStatusCount(companyId, account, "2",
						"0", "0", isPostFromInquiry, null, "",null));
		map.put("expired", productsDAO
				.productsOfCompanyByStatusCount(companyId, account, null,
						"1", "0", isPostFromInquiry, null, "",null));
		map.put("pause", productsDAO.productsOfCompanyByStatusCount(
				companyId, account, null, null, "1", isPostFromInquiry, null,
				"","1"));

		return map;
	}

	public List<ProductsDO> queryNewestProducts(String mainCode,
			String typeCode, Integer maxSize) {
		if (maxSize == null || maxSize.intValue() > 500) {
			maxSize = 20;
		}
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		List<ProductsDO> list =new ArrayList<ProductsDO>();
		try {
			String keyword = "";
			if (StringUtils.isNotEmpty(mainCode)) {
				keyword = CategoryFacade.getInstance().getValue(mainCode);
			}
			if (StringUtils.isNotEmpty(keyword)) {
				sb.append("@(title,tags,label0,label1,label2,label3,label4) ").append(keyword);
			}
			if (StringUtils.isNotEmpty(typeCode)) {
				if ("10331000".equals(typeCode)) {
					cl.SetFilter("pdt_kind", 1, true);
				} else {
					cl.SetFilter("pdt_kind", 0, true);
				}
			}
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
			cl.SetFilter("check_status", 1, false);
			cl.SetFilter("is_del", 0, false);
			cl.SetFilter("is_pause", 0, false);
			cl.SetLimits(0, maxSize, maxSize);
			SphinxResult res = cl.Query(sb.toString(),"offersearch_new,offersearch_new_vip");
			if (res != null){
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					ProductsDO obj = productsDAO.queryProductsById(Integer.valueOf("" + info.docId));
					list.add(obj);
				}
			}
		} catch (SphinxException e) {
			return productsDAO.queryNewestProducts(mainCode, typeCode, maxSize);
		}
		return list;
	}
	

	public Integer countProductsByCompanyId(Integer companyId) {
		Assert.notNull(companyId, "The companyId must not be null");
		return productsDAO.queryProductsOfCompanyByStatusCount(companyId, null,"1", "0", "0", "0", null, "");
	}

	public Boolean queryUserIsAddProducts(Integer id, String membershipCode) {
		// Assert.notNull(company, "company is not null");
		// String membershipCode = company.getMembershipCode();
		if (StringUtils.isEmpty(membershipCode)) {
			membershipCode = AstConst.COMMON_MEMBERSHIP_CODE;
		}

		Integer max = Integer.valueOf(MemberRuleFacade.getInstance().getValue(membershipCode, "publish_products_max_day"));

		Integer size = productsDAO.countUserAddProductsToday(id);
		if (size != null && size.intValue() < max.intValue()) {
			return true;
		} else {
			return false;
		}
	}

	public Date queryMaxRefreshTimeByCompanyId(Integer companyId) {
		return productsDAO.queryMaxRefreshTimeByCompanyId(companyId);
	}

	public Integer publishProductsByCompany(ProductsDO products,
			String membershipCode, String areaCode) {

		// 去除标题头部空格
		if (StringUtils.isEmpty(products.getTitle())) {
			return null;
		}
		products.setTitle(products.getTitle().trim());

		if (products.getCompanyId() == null) {
			return null;
		}
		if (products.getAccount() == null) {
			return null;
		}
		if (products.getMaxPrice() == null) {
			products.setMaxPrice(0f);
		}
		if (products.getMinPrice() == null) {
			products.setMinPrice(0f);
		}

		// try {
		// // 敏感词验证 关键字
		// if(SensitiveUtils.validateSensitiveFilter(products.getTitle())){
		// products.setTitle((String)
		// SensitiveUtils.getSensitiveValue(products.getTitle(), "*"));
		// }
		//
		// // 敏感词验证 详细信息
		// if(SensitiveUtils.validateSensitiveFilter(products.getDetails())){
		// products.setDetails((String)
		// SensitiveUtils.getSensitiveValue(products.getDetails(),"*"));
		// }
		//
		// // 敏感词验证 标签
		// if(SensitiveUtils.validateSensitiveFilter(products.getTags())){
		// products.setTags((String)
		// SensitiveUtils.getSensitiveValue(products.getTags(),"*"));
		// }
		// } catch (Exception e) {
		//
		// }

		// products.setPrice(String.valueOf(products.getMinPrice()));
		products.setPostType("0");
		products.setIsPause("0");
		if (products.getIsPostWhenViewLimit() == null) {
			products.setIsPostWhenViewLimit(false);
		}
		products.setIsPostFromInquiry(false);
		products.setIsDel(false);
		products.setCheckStatus(MemberRuleFacade.getInstance().getValue(
				membershipCode, "new_products_check"));
		if (ProductsService.CHECK_WAIT.equals(products.getCheckStatus())) {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				if (SensitiveUtils.validateSensitiveFilter(products.getTitle())) {
					products.setCheckStatus(ProductsService.CHECK_FAILD);
					map.putAll(SensitiveUtils.getSensitiveFilter(products.getTitle()));
				}
				if (SensitiveUtils.validateSensitiveFilter(products.getDetails())) {
					products.setCheckStatus(ProductsService.CHECK_FAILD);
					map.putAll(SensitiveUtils.getSensitiveFilter(products.getDetails()));
				}
				if (SensitiveUtils.validateSensitiveFilter(products.getTags())) {
					products.setCheckStatus(ProductsService.CHECK_FAILD);
					map.putAll(SensitiveUtils.getSensitiveFilter(products.getTags()));
				}
				if (map.size() > 0) {
					products.setCheckPerson("myrc-check");
					products.setUnpassReason("您发布的供求含有敏感词“" + map.get("sensitiveSet") + "”，不符合我们网站的审核要求，请修改信息");
				}
			} catch (IOException e) {
				products.setCheckStatus(ProductsService.CHECK_WAIT);
			} catch (Exception e) {
				products.setCheckStatus(ProductsService.CHECK_WAIT);
			}
		}
		products.setUncheckedCheckStatus("0");

		// 过期时间判断
		if (products.getExpireTime() == null) {
			try {
				products.setExpireTime(DateUtil.getDate("9999-12-31 23:59:59",
						AstConst.DATE_FORMATE_WITH_TIME));
			} catch (ParseException e) {
			}
		}

		Integer id = productsDAO.insertProduct(products);
		if (id != null && id.intValue() <= 0) {
			return 0;
		}

		CompanyPriceDO companyPriceDO = new CompanyPriceDO();
		companyPriceDO.setProductId(id);
		companyPriceDO.setAccount(products.getAccount());
		companyPriceDO.setCompanyId(products.getCompanyId());
//		Company c = new Company();
//		c.setMembershipCode(membershipCode);
//		c.setAreaCode(areaCode);
		// 当报价存在并大于1 的情况下，添加到企业报价中
		// if(products.getMinPrice().longValue()>0){
		// companyPriceService.addProductsToCompanyPrice(companyPriceDO,c,
		// products);
		// }
		return id;
	}

	public Integer publishProductsFromInquiry(ProductsDO products) {
		if (products.getCompanyId() == null) {
			return null;
		}
		if (products.getAccount() == null) {
			return null;
		}
		products.setSourceTypeCode("");
		products.setPostType("1");
		products.setIsPause("0");
		products.setIsPostWhenViewLimit(false);
		products.setIsPostFromInquiry(true);
		products.setIsDel(false);
		if (products.getCheckStatus() == null) {
			products.setCheckStatus("0");
		}
		if (products.getUncheckedCheckStatus() == null) {
			products.setUncheckedCheckStatus("0");
		}
		return productsDAO.insertProduct(products);
	}

	public Integer batchDeleteProductsByIds(Integer[] entities,
			Integer companyId) {
		return productsDAO.batchDeleteProductsByIds(entities, companyId);
	}

	public Integer insertProductsPicRelation(Integer productId, Integer[] picIds) {
		Assert.notNull(productId, "the product id can not be null");
		int impact = 0;
		int j = 0;
		for (int i : picIds) {
			if (i > 0) {
				j = productsPicDAO.updateProductsIdById(productId, i);
				if (j > 0) {
					impact++;
				}
			}
		}
		return impact;
	}

	public boolean isProductsAlreadyExists(String title,
			String productsTypeCode, String account) {
		if (StringUtils.isEmpty(title)) {
			return false;
		}
		if (StringUtils.isEmpty(productsTypeCode)) {
			productsTypeCode = "10331000";
		}
		Integer n = productsDAO.countProuductsByTitleAndAccount(title,
				productsTypeCode, account, null, null);
		if (n != null && n.intValue() > 0) {
			return true;
		}
		return false;
	}

	public Integer updateProductsCheckStatusByAdmin(String checkStatus,
			String unpassReason, String checkPerson, Integer productId) {
		Assert.notNull(productId, "the product id can not be null");
		return productsDAO.updateProductsCheckStatusByAdmin(checkStatus,unpassReason, checkPerson, productId);
	}

	public Integer updateProductsUncheckedCheckStatusByAdmin(
			String checkStatus, String unpassReason, String checkPerson,
			Integer productId) {
		return productsDAO.updateProductsUncheckedCheckStatusByAdmin(
				checkStatus, unpassReason, checkPerson, productId);
	}

	public Integer updateProductsIsShowInPrice(Integer id, String flag) {
		Assert.notNull(id, "the id can not be null");
		if (flag == null) {
			flag = "0";
		}
		return productsDAO.updateProductsIsShowInPrice(id, flag);
	}

	public Integer updateProductsIsPause(String isPause, Integer[] ids) {
		if (StringUtils.isEmpty(isPause)) {
			return null;
		}
		return productsDAO.updateProductsIsPause(isPause, ids);
	}
	@Override
	public Integer updateProductsIsPauseByAdmin(String isPause, Integer id) {
		if (StringUtils.isEmpty(isPause)) {
			return null;
		}
		return productsDAO.updateProductsIsPauseByAdmin(isPause, id);
	}
	public Integer refreshProducts(Integer productsId, Integer companyId,
			String membershipCode) throws ParseException {
		do {
			
			ProductsDO products = productsDAO.queryProductsWithOutDetailsById(productsId);
			if (products == null) {
				break;
			}
			if (products.getRefreshTime() == null) {
				products.setRefreshTime(new Date());
			}
			
			if (StringUtils.isEmpty(membershipCode)) {
				membershipCode = "10051000";
			}
			
			// 获取会员类型的刷新时间间隔
			Date now = new Date();
			Long interval = Long.valueOf(MemberRuleFacade.getInstance().getValue(membershipCode, "refresh_product_interval"));
			boolean isRefresh = false;
			long intervalNow = now.getTime() - products.getRefreshTime().getTime();
			if (intervalNow > (interval.longValue() * 1000)) {
				isRefresh = true;
			}else{
				// 不能刷新 检查是否7日内微信绑定帐号
				String account = companyAccountDao.queryAccountByCompanyId(companyId).getAccount();
				OauthAccess oa = oauthAccessDao.queryAccessByAccountAndType(account, OauthAccessService.OPEN_TYPE_WEIXIN);
				if(oa!=null&&DateUtil.getDateAfterDays(oa.getGmtCreated(), 7).getTime()>=new Date().getTime()){
					isRefresh = true;
				}
			}
			
			if (!isRefresh) {
				break;
			}

			return updateNewRefreshTimeById(productsId,companyId);
		
		} while (false);
		
		return null;
	}
	@Override
	public Integer updateIsPauseAndrefreshTimeById(Integer productsId){
		do {
			if(productsId==null|| productsId.intValue()<=0){
				break;
			}
			ProductsDO products = productsDAO.queryProductsWithOutDetailsById(productsId);
			if (products == null) {
				break;
			}
			if (products.getRefreshTime() == null) {
				products.setRefreshTime(new Date());
			}
			//判断过期时间为空这改为9999-12-31 23:59:59
			String expiretime=DateUtil.toString(products.getExpireTime(), AstConst.DATE_FORMATE_WITH_TIME);
			if(StringUtils.isEmpty(expiretime)){
				try {
					products.setExpireTime(DateUtil.getDate("9999-12-31 23:59:59",
							AstConst.DATE_FORMATE_WITH_TIME));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			String isPause=null;
			if(products.getIsPause().equals("2")){
				isPause="0";
			}
			if (AstConst.MAX_TIMT.equals(DateUtil.toString(products.getExpireTime(), AstConst.DATE_FORMATE_WITH_TIME))) {
				return productsDAO.updateProductsRefreshTimeExpireTime(productsId, products.getCompanyId(),isPause);
			} else {
				return productsDAO.updateRefreshTimeOrIsPause(productsId, products.getCompanyId(), isPause);
			}
		}while(false);
			return 0;
	}
	
	public Integer updateProductByAdmin(ProductsDO products) {
		Assert.notNull(products.getId(), "The id can not be null");
		String mainCode = products.getCategoryProductsMainCode();
		if (mainCode != null && mainCode.length() >= 4) {
			if (mainCode.substring(0, 4).equals("1000")) {
				products.setUseful("");
				products.setColor("");
				products.setAppearance("");
				products.setImpurity("");
			} else if (!mainCode.substring(0, 4).equals("1001")) {
				products.setColor("");
				products.setAppearance("");
			}
		}
		return productsDAO.updateProductsByAdmin(products);
	}
	public Integer updateProductByAdminCheck(ProductsDO products) {
		Assert.notNull(products.getId(), "The id can not be null");
		return productsDAO.updateProductsByAdminCheck(products);
		
	}
	public Integer updateProductsByCompany(ProductsDO products,
			String membershipCode) {
		products.setCheckStatus(MemberRuleFacade.getInstance().getValue(
				membershipCode, "update_products_check"));
		products.setUncheckedCheckStatus("0");
		if (products.getCategoryProductsAssistCode() == null) {
			products.setCategoryProductsAssistCode("");
		}
		if (StringUtils.isEmpty(products.getPriceUnit())) {
			products.setPriceUnit("元");
		}
		if (StringUtils.isEmpty(products.getQuantityUnit())) {
			products.setQuantityUnit("吨");
		}
		if (ProductsService.CHECK_WAIT.equals(products.getCheckStatus())) {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				if (SensitiveUtils.validateSensitiveFilter(products.getTitle())) {
					products.setCheckStatus(ProductsService.CHECK_FAILD);
					map = SensitiveUtils
							.getSensitiveFilter(products.getTitle());
				}
				if (SensitiveUtils.validateSensitiveFilter(products
						.getDetails())) {
					products.setCheckStatus(ProductsService.CHECK_FAILD);
					map.putAll(SensitiveUtils.getSensitiveFilter(products
							.getDetails()));
				}
				if (SensitiveUtils.validateSensitiveFilter(products.getTags())) {
					products.setCheckStatus(ProductsService.CHECK_FAILD);
					map.putAll(SensitiveUtils.getSensitiveFilter(products
							.getTags()));
				}
				if (map.size() > 0 && map.get("sensitiveSet") != null) {
					products.setUnpassReason("您发布的供求含有敏感词“"
							+ map.get("sensitiveSet") + "”，不符合我们网站的审核要求，请修改信息");
				}
			} catch (IOException e) {
				products.setCheckStatus(ProductsService.CHECK_WAIT);
			} catch (Exception e) {
				products.setCheckStatus(ProductsService.CHECK_WAIT);
			}
		}
		// try {
		// // 敏感词验证 关键字
		// if(SensitiveUtils.validateSensitiveFilter(products.getTitle())){
		// products.setTitle((String)
		// SensitiveUtils.getSensitiveValue(products.getTitle(), "*"));
		// }
		//
		// // 敏感词验证 详细信息
		// if(SensitiveUtils.validateSensitiveFilter(products.getDetails())){
		// products.setDetails((String)
		// SensitiveUtils.getSensitiveValue(products.getDetails(),"*"));
		// }
		//
		// // 敏感词验证 标签
		// if(SensitiveUtils.validateSensitiveFilter(products.getTags())){
		// products.setTags((String)
		// SensitiveUtils.getSensitiveValue(products.getTags(),"*"));
		// }
		// } catch (IOException e) {
		// return 0;
		// } catch (Exception e) {
		// return 0;
		// }
		String mainCode = products.getCategoryProductsMainCode();
		if (mainCode != null && mainCode.length() >= 4) {
			if (mainCode.substring(0, 4).equals("1000")) {
				products.setUseful("");
				products.setColor("");
				products.setAppearance("");
				products.setImpurity("");
			} else if (!mainCode.substring(0, 4).equals("1001")) {
				products.setColor("");
				products.setAppearance("");
			}
		}
		return productsDAO.updateProductsByCompany(products);
	}

	public List<Integer> queryProductsIdsOfCompany(Integer companyId,
			String categoryCode) {
		Assert.notNull(companyId, "the companyId can not be null");
		return productsDAO.queryProductsIdsOfCompany(companyId, categoryCode);
	}

	/**
	 * 轮回排序，去重复公司
	 */
	@Override
	public PageDto<ProductsDto> pageLHProductsBySearchEngine(
			ProductsDO product, String areaCode, Boolean havePic,
			PageDto<ProductsDto> page) {
		if (page.getPageSize() == null) {
			page.setPageSize(10);
		}

		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		List<ProductsDto> list = new ArrayList<ProductsDto>();
		try {
			if (StringUtils.isNotEmpty(product.getTitle())) {
				sb
						.append(
								"@(title,tags,label0,label1,label2,label3,label4,city,province,color,properties) ")
						.append(product.getTitle());
			}

			if (StringUtils.isNotEmpty(product.getProductsTypeCode())) {
				if ("10331000".equals(product.getProductsTypeCode())) {
					cl.SetFilter("pdt_kind", 1, true);
				} else {
					cl.SetFilter("pdt_kind", 0, true);
				}
			}

			// 是否要图片
			if (havePic != null && havePic) {
				cl.SetFilterRange("havepic", 1, 5000, false);
				// cl.SetFilter("havepic", 1, true);
			}

			// 地区
			if (StringUtils.isNotEmpty(areaCode)) {
				sb = checkStringBuffer(sb);
				sb.append("@(province,city)").append(areaCode);
			}
			
			// 样品信息
			if (product.getIsYP()!=null&&product.getIsYP()) {
				cl.SetFilterRange("havesample", 1, 5000, false);
				cl.SetFilter("sampleDel", 0, false);
				if (product.getIsBaoyou()!=null&&product.getIsBaoyou()) {
					cl.SetFilter("send_price", 0, false);
				}
			}

			// group by 去重复公司
			// cl.SetGroupBy("company_id", SphinxClient.SPH_GROUPBY_DAY);

			// 所使用的服务
			if (StringUtils.isNotEmpty(product.getCrmCompanySvr())) {
				cl.SetFilter("crm_service_code", Integer.valueOf(product
						.getCrmCompanySvr()), true);
			}

			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			// cl.SetLimits(page.getStartIndex(),
			// page.getPageSize()*CARDINAL_NUMBER, TOTAL_RECORDS);
			if (StringUtils.isEmpty(sb.toString())) {
				cl.SetLimits(page.getStartIndex(), 1000, 1000);
			} else {
				if (page.getPageSize() > 300) {
					cl.SetLimits(page.getStartIndex(), TOTAL_RECORDS,
							TOTAL_RECORDS);
				} else {
					cl.SetLimits(page.getStartIndex(), page.getPageSize()
							* CARDINAL_NUMBER, TOTAL_RECORDS);
				}
			}
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
			// 判断是否高会
			// if(product.getIsVip()){
			// cl.SetFilterRange("viptype", 1, 5, false);
			// }
			
			cl.SetFilter("check_status", 1, false);
			cl.SetFilter("is_del", 0, false);
			cl.SetFilter("is_pause", 0, false);

			SphinxResult res = cl.Query(sb.toString(),
					"offersearch_new,offersearch_new_vip");

			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				Set<Integer> idSet = new HashSet<Integer>();
				for (int i = 0; i < res.matches.length; i++) {
					// 列表数据 足够 跳出循环
					if (list.size() >= page.getPageSize()) {
						break;
					}
					// 伪轮回排序 获取不同公司的供求 即一间公司一条供求
					do {
						SphinxMatch info = res.matches[i];
						ProductsDto dto = productsDAO
								.queryProductsWithPicAndCompany(Integer
										.valueOf("" + info.docId));
						if (dto == null || dto.getProducts() == null) {
							break;
						}
						Integer companyId = dto.getProducts().getCompanyId();
						if (idSet.contains(companyId)) {
							break;
						}
						idSet.add(companyId);
						if (dto != null
								&& dto.getProducts() != null
								&& StringUtils.isNotEmpty(dto.getProducts()
										.getDetails())) {
							dto.getProducts().setDetails(
									Jsoup.clean(dto.getProducts().getDetails(),
											Whitelist.none()));
						} else {
							dto = new ProductsDto();
							ProductsDO pdto = new ProductsDO();
							Map<String, Object> resultMap = SearchEngineUtils
									.getInstance().resolveResult(res, info);
							pdto.setId(Integer.valueOf("" + info.docId));
							pdto.setTitle(resultMap.get("ptitle").toString());
							dto.setProducts(pdto);
						}
						// 是否样品
						if (product.getIsYP()!=null&&product.getIsYP()) {
							Sample sample = sampleDao.queryByProductId(dto.getProducts().getId());
							dto.setSample(sample);
						}
						list.add(dto);
					} while (false);
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}

		return page;
	}

	/**
	 * 通用搜索引擎方法 有重复公司
	 */
	@Override
	public PageDto<ProductsDto> pageProductsBySearchEngine(ProductsDO product,
			String areaCode, Boolean havePic, PageDto<ProductsDto> page) {
		if (page.getPageSize() == null) {
			page.setPageSize(10);
		}

		// 限制最大页数
		if (page.getStartIndex() != null
				&& page.getStartIndex() >= TOTAL_RECORDS - page.getPageSize()) {
			page.setStartIndex(TOTAL_RECORDS - page.getPageSize());
		}

		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		List<ProductsDto> list = new ArrayList<ProductsDto>();
		ProductsDto pdtos=new ProductsDto();
		ProductsKeywordsRankDTO pkrd=new ProductsKeywordsRankDTO();
		ProductsKeywordsRankDO ps=new ProductsKeywordsRankDO();
		pkrd.setProductsKeywordsRank(ps);
		pkrd.getProductsKeywordsRank().setType("10431005");
		List<ProductsKeywordsRankDTO> listp=productsKeywordsRankDAO.queryProductsKeywordsRankByConditions(pkrd);
		//标王供求信息——样品中心首页
		if("shouye".equals(areaCode)){
			areaCode=null;
			for(ProductsKeywordsRankDTO pd:listp){
				//产品信息
				ProductsDO products=productsDAO.queryProductsById(pd.getProductsKeywordsRank().getProductId());
				pdtos.setProducts(products);
				//样品信息
				Sample sample=sampleDao.queryByProductId(pd.getProductsKeywordsRank().getProductId());
				pdtos.setSample(sample);
				//样品的图片
				String pp=productsPicDAO.queryPicPathByProductId(pd.getProductsKeywordsRank().getProductId());
				pdtos.setCoverPicUrl(pp);
				list.add(pdtos);
			}
		}
		
		try {

			if (StringUtils.isNotEmpty(product.getTitle())) {
				sb
						.append(
								"@(title,tags,label0,label1,label2,label3,label4,city,province,color,properties) ")
						.append(product.getTitle());
			}

			if (StringUtils.isNotEmpty(product.getProductsTypeCode())) {
				if ("10331000".equals(product.getProductsTypeCode())) {
					cl.SetFilter("pdt_kind", 1, true);
				} else {
					cl.SetFilter("pdt_kind", 0, true);
				}
			}

			// 是否要图片
			if (havePic != null && havePic) {
				cl.SetFilterRange("havepic", 1, 5000, false);
//				 cl.SetFilter("havepic", 1, true);
			}

			// 是否需要价格控制
			if (product.getMinPrice() != null && product.getMinPrice() > 0) {
				cl.SetFilterRange("haveprice", 2, 10, false);
			}
			// 地区
			if (StringUtils.isNotEmpty(areaCode)) {
				sb = checkStringBuffer(sb);
				sb.append("@(province,city)").append(areaCode);
			}
			
			// 样品信息
			if (product.getIsYP()!=null&&product.getIsYP()) {
				cl.SetFilterRange("havesample", 1, 5000, false);
				cl.SetFilter("sampleDel", 0, false);
				if (product.getIsBaoyou()!=null&&product.getIsBaoyou()) {
//					cl.SetFilter("send_price", 0, false);
					cl.SetFilterFloatRange("send_price", 0.0f, 0.0f, false);
				}
			}

			// group by 去重复公司
			// cl.SetGroupBy("company_id", SphinxClient.SPH_GROUPBY_DAY);

			// 所使用的服务
			if (StringUtils.isNotEmpty(product.getCrmCompanySvr())) {
				cl.SetFilter("crm_service_code", Integer.valueOf(product.getCrmCompanySvr()), true);
			}
			
			// 检索刷新时间范围
			if (product.getSearchRangeRefresh()!=null) {
				Date date;
				try {
					date = DateUtil.getDate(new Date(), "yyyy-MM-dd");
				} catch (ParseException e) {
					date = new Date();
				}
				Date rangeTo 	= DateUtil.getDateAfterDays(date, 1);
				Date rangeFrom 	= DateUtil.getDateAfterDays(date, -product.getSearchRangeRefresh());
				
				cl.SetFilterRange("pdt_date", rangeFrom.getTime()/1000, rangeTo.getTime()/1000, false);
			}

			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);

			if (StringUtils.isNotEmpty(product.getTitle())) {
				cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			} else {
				// 用于首页即时供应，如果删掉，则搜索数据过多，有时会读取超时。
				cl.SetLimits(page.getStartIndex(), page.getPageSize(), 10000);
			}
			
			if (StringUtils.isNotEmpty(page.getSearchOrderByAndDir())) {
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, page.getSearchOrderByAndDir());
			}else{
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
			}
			// 判断是否高会
			if(product!=null&&product.getIsVip()!=null&&product.getIsVip()){
				cl.SetFilterRange("viptype", 1, 5, false);
			}
			
			cl.SetFilter("check_status", 1, false);
			cl.SetFilter("is_del", 0, false);
			cl.SetFilter("is_pause", 0, false);
			
			SphinxResult res = cl.Query(sb.toString(),"offersearch_new,offersearch_new_vip");
			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				for (int i = 0; i < res.matches.length; i++) {
					do {
						SphinxMatch info = res.matches[i];
						ProductsDto dto = productsDAO.queryProductsWithPicAndCompany(Integer.valueOf("" + info.docId));
						if (dto != null && dto.getCompany() != null) {
							String a = dto.getCompany().getAreaCode();
							String areaLabel = "";
							if (a.length() >= 12) {
								areaLabel += CategoryFacade.getInstance().getValue(a.substring(0, 12));
							}
							if (a.length() >= 16) {
								areaLabel += CategoryFacade.getInstance()
										.getValue(a.substring(0, 16));
							}
							dto.setAreaLabel(areaLabel);

						}
						if (dto == null || dto.getProducts() == null) {
							break;
						}
						if (dto != null
								&& dto.getProducts() != null
								&& StringUtils.isNotEmpty(dto.getProducts()
										.getDetails())) {
							dto.getProducts().setDetails(
									Jsoup.clean(dto.getProducts().getDetails(),
											Whitelist.none()));
						} else {
							dto = new ProductsDto();
							ProductsDO pdto = new ProductsDO();
							Map<String, Object> resultMap = SearchEngineUtils
									.getInstance().resolveResult(res, info);
							pdto.setId(Integer.valueOf("" + info.docId));
							pdto.setTitle(resultMap.get("ptitle").toString());
							dto.setProducts(pdto);
						}
						// 是否样品
						if (product.getIsYP()!=null&&product.getIsYP()) {
							Sample sample = sampleDao.queryByProductId(dto.getProducts().getId());
							dto.setSample(sample);
							dto.setProducts(productsDAO.queryProductsById(dto.getProducts().getId()));
							ProductAddProperties obj = productAddPropertiesDAO.queryByPidAndProperty(dto.getProducts().getId(), "级别");
							if (obj!=null&&StringUtils.isNotEmpty(obj.getContent())) {
								dto.setAddLevel(obj.getContent());
							}
							
							if (dto.getCompany()!=null&&StringUtils.isNotEmpty(dto.getCompany().getAreaCode())) {
								if (dto.getCompany().getAreaCode().length() >= 12) {
									dto.setAreaLabel(CategoryFacade.getInstance().getValue(dto.getCompany().getAreaCode().substring(0, 12)));
								}
								if (dto.getCompany().getAreaCode().length() >= 16) {
									dto.setAreaLabel(dto.getAreaLabel() + CategoryFacade.getInstance().getValue(dto.getCompany().getAreaCode().substring(0, 16)));
								}
							}
							
						}
						for(ProductsKeywordsRankDTO pdt:listp){
							if(dto.getProducts().getId()!=pdt.getProductsKeywordsRank().getProductId()){
								if(page.getPageSize()==30){
									//map要30条
									if(list.size()<30){
										list.add(dto);
									}
									continue;
								}
								if(list.size()<20){
									list.add(dto);
								}
							}
						}
					} while (false);
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}

		return page;
	}
	
	/**
	 * 通用搜索引擎方法 有重复公司
	 */
	@Override
	public PageDto<ProductsDto> pagePPCProductsBySearchEngine(ProductsDO product,Boolean havePic, PageDto<ProductsDto> page) {
		if (page.getPageSize() == null) {
			page.setPageSize(10);
		}

		// 限制最大页数
		if (page.getStartIndex() != null
				&& page.getStartIndex() >= TOTAL_RECORDS - page.getPageSize()) {
			page.setStartIndex(TOTAL_RECORDS - page.getPageSize());
		}

		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		List<ProductsDto> list = new ArrayList<ProductsDto>();
		try {

			if (StringUtils.isNotEmpty(product.getTitle())) {
				sb.append("@(title,tags,label0,label1,label2,label3,label4,city,province,color,properties) ").append(product.getTitle());
			}

			if (StringUtils.isNotEmpty(product.getProductsTypeCode())) {
				if ("10331000".equals(product.getProductsTypeCode())) {
					cl.SetFilter("pdt_kind", 1, true);
				} else {
					cl.SetFilter("pdt_kind", 0, true);
				}
			}

			// 是否要图片
			if (havePic != null && havePic) {
				cl.SetFilterRange("havepic", 1, 5000, false);
			}

			// 是否需要价格控制
			if (product.getMinPrice() != null && product.getMinPrice() > 0) {
				cl.SetFilterRange("haveprice", 2, 10, false);
			}
			
			// 样品信息
			if (product.getIsYP()!=null&&product.getIsYP()) {
				cl.SetFilterRange("havesample", 1, 5000, false);
				cl.SetFilter("sampleDel", 0, false);
				if (product.getIsBaoyou()!=null&&product.getIsBaoyou()) {
					cl.SetFilterFloatRange("send_price", 0.0f, 0.0f, false);
				}
			}

			// 所使用的服务
			if (StringUtils.isNotEmpty(product.getCrmCompanySvr())) {
				cl.SetFilter("crm_service_code", Integer.valueOf(product.getCrmCompanySvr()), true);
			}
			
			// 检索刷新时间范围
			if (product.getSearchRangeRefresh()!=null) {
				Date date;
				try {
					date = DateUtil.getDate(new Date(), "yyyy-MM-dd");
				} catch (ParseException e) {
					date = new Date();
				}
				Date rangeTo 	= DateUtil.getDateAfterDays(date, 1);
				Date rangeFrom 	= DateUtil.getDateAfterDays(date, -product.getSearchRangeRefresh());
				
				cl.SetFilterRange("pdt_date", rangeFrom.getTime()/1000, rangeTo.getTime()/1000, false);
			}

			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);

			if (StringUtils.isNotEmpty(product.getTitle())) {
				cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			} else {
				// 用于首页即时供应，如果删掉，则搜索数据过多，有时会读取超时。
				cl.SetLimits(page.getStartIndex(), page.getPageSize(), 10000);
			}
			
			if (StringUtils.isNotEmpty(page.getSearchOrderByAndDir())) {
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, page.getSearchOrderByAndDir());
			}else{
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
			}
			
			// 判断是否高会
			if(product!=null&&product.getIsVip()!=null&&product.getIsVip()){
				cl.SetFilterRange("viptype", 1, 5, false);
			}
			
			cl.SetFilter("check_status", 1, false);
			cl.SetFilter("is_del", 0, false);
			cl.SetFilter("is_pause", 0, false);
			
			SphinxResult res = cl.Query(sb.toString(),"offersearch_ppc");
			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				for (int i = 0; i < res.matches.length; i++) {
					do {
						SphinxMatch info = res.matches[i];
						
						ProductsDto dto = productsDAO
								.queryProductsWithPicAndCompany(Integer
										.valueOf("" + info.docId));

						if (dto != null && dto.getCompany() != null) {
							String a = dto.getCompany().getAreaCode();
							String areaLabel = "";
							if (a.length() >= 12) {
								areaLabel += CategoryFacade.getInstance()
										.getValue(a.substring(0, 12));
							}
							if (a.length() >= 16) {
								areaLabel += CategoryFacade.getInstance()
										.getValue(a.substring(0, 16));
							}
							dto.setAreaLabel(areaLabel);

						}
						if (dto == null || dto.getProducts() == null) {
							break;
						}
						if (dto != null
								&& dto.getProducts() != null
								&& StringUtils.isNotEmpty(dto.getProducts()
										.getDetails())) {
							dto.getProducts().setDetails(
									Jsoup.clean(dto.getProducts().getDetails(),
											Whitelist.none()));
						} else {
							dto = new ProductsDto();
							ProductsDO pdto = new ProductsDO();
							Map<String, Object> resultMap = SearchEngineUtils
									.getInstance().resolveResult(res, info);
							pdto.setId(Integer.valueOf("" + info.docId));
							pdto.setTitle(resultMap.get("ptitle").toString());
							dto.setProducts(pdto);
						}
						// 是否样品
						if (product.getIsYP()!=null&&product.getIsYP()) {
							Sample sample = sampleDao.queryByProductId(dto.getProducts().getId());
							dto.setSample(sample);
							dto.setProducts(productsDAO.queryProductsById(dto.getProducts().getId()));
							ProductAddProperties obj = productAddPropertiesDAO.queryByPidAndProperty(dto.getProducts().getId(), "级别");
							if (obj!=null&&StringUtils.isNotEmpty(obj.getContent())) {
								dto.setAddLevel(obj.getContent());
							}
							if (StringUtils.isNotEmpty(dto.getCompany().getAreaCode())) {
								if (dto.getCompany().getAreaCode().length() >= 12) {
									dto.setAreaLabel(CategoryFacade.getInstance().getValue(dto.getCompany().getAreaCode().substring(0, 12)));
								}
								if (dto.getCompany().getAreaCode().length() >= 16) {
									dto.setAreaLabel(dto.getAreaLabel() + CategoryFacade.getInstance().getValue(dto.getCompany().getAreaCode().substring(0, 16)));
								}
							}
						}
						
						list.add(dto);
					} while (false);
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}

		return page;
	}
	
	
	/**
	 * 通用搜索引擎方法
	 */
	@Override
	public PageDto<ProductsDto> pageProductsBySearchEngine(ProductsDO product,
			String areaCode, Boolean havePic, PageDto<ProductsDto> page , Boolean duplicate) {
		if (page.getPageSize() == null) {
			page.setPageSize(10);
		}

		// 限制最大页数
		if (page.getStartIndex() != null
				&& page.getStartIndex() >= TOTAL_RECORDS - page.getPageSize()) {
			page.setStartIndex(TOTAL_RECORDS - page.getPageSize());
		}

		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		List<ProductsDto> list = new ArrayList<ProductsDto>();
		try {

			if (StringUtils.isNotEmpty(product.getTitle())) {
				sb
						.append(
								"@(title,tags,label0,label1,label2,label3,label4,city,province) ")
						.append(product.getTitle());
			}

			if (StringUtils.isNotEmpty(product.getProductsTypeCode())) {
				if ("10331000".equals(product.getProductsTypeCode())) {
					cl.SetFilter("pdt_kind", 1, true);
				} else {
					cl.SetFilter("pdt_kind", 0, true);
				}
			}
			 //所查找的供求信息为已审核的(check_status=1)，未过期的(expire_time>=now()) <br />
			 //发布情况正常的(is_pause=0)，没有被删除的(is_del=0)供求信息
			cl.SetFilter("check_status", 1, false);
			cl.SetFilter("is_pause", 0, false);
			cl.SetFilter("is_del", 0, false);
			
			// 是否要图片
			if (havePic != null && havePic) {
				cl.SetFilterRange("havepic", 1, 5000, false);
				// cl.SetFilter("havepic", 1, true);
			}

			// 是否需要价格控制
			if (product.getMinPrice() != null && product.getMinPrice() > 0) {
				cl.SetFilterRange("haveprice", 2, 10, false);
			}
			//废料热门供求必须要有数量
			if("1".equals(product.getQuantity())){
				cl.SetFilterRange("quantity", 1, 10, false);
			}
			// 地区
			if (StringUtils.isNotEmpty(areaCode)) {
				sb = checkStringBuffer(sb);
				sb.append("@(province,city)").append(areaCode);
			}

			// group by 去重复公司
			if(!duplicate){
				cl.SetGroupBy("company_id", SphinxClient.SPH_GROUPBY_DAY);
			}
 
			// 所使用的服务
			if (StringUtils.isNotEmpty(product.getCrmCompanySvr())) {
				cl.SetFilter("crm_service_code", Integer.valueOf(product
						.getCrmCompanySvr()), true);
			}

			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);

			if (StringUtils.isNotEmpty(product.getTitle())) {
				cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			} else {
				// 用于首页即时供应，如果删掉，则搜索数据过多，有时会读取超时。
				cl.SetLimits(page.getStartIndex(), page.getPageSize(), page.getPageSize());
			}

			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
			
			// 判断是否高会
			if (product.getIsVip() != null) {
				if (product.getIsVip()) {
					cl.SetFilterRange("viptype", 1, 5, false);
				} else {
					// 普会
					cl.SetFilter("viptype", 0, false);
				}
			}
			
			cl.SetFilter("check_status", 1, false);
			cl.SetFilter("is_del", 0, false);
			cl.SetFilter("is_pause", 0, false);
			
			SphinxResult res = cl.Query(sb.toString(),"offersearch_new,offersearch_new_vip");
			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				for (int i = 0; i < res.matches.length; i++) {
					do {
						SphinxMatch info = res.matches[i];
						ProductsDto dto = productsDAO
								.queryProductsWithPicAndCompany(Integer
										.valueOf("" + info.docId));

						if (dto != null && dto.getCompany() != null) {
							String a = dto.getCompany().getAreaCode();
							String areaLabel = "";
							if (a.length() >= 12) {
								areaLabel += CategoryFacade.getInstance()
										.getValue(a.substring(0, 12));
							}
							if (a.length() >= 16) {
								areaLabel += CategoryFacade.getInstance()
										.getValue(a.substring(0, 16));
							}
							dto.setAreaLabel(areaLabel);

						}
						if (dto == null || dto.getProducts() == null) {
							break;
						}
						if (dto != null
								&& dto.getProducts() != null
								&& StringUtils.isNotEmpty(dto.getProducts()
										.getDetails())) {
							dto.getProducts().setDetails(
									Jsoup.clean(dto.getProducts().getDetails(),
											Whitelist.none()));
						} else {
							dto = new ProductsDto();
							ProductsDO pdto = new ProductsDO();
							Map<String, Object> resultMap = SearchEngineUtils
									.getInstance().resolveResult(res, info);
							pdto.setId(Integer.valueOf("" + info.docId));
							pdto.setTitle(resultMap.get("ptitle").toString());
							dto.setProducts(pdto);

						}
						 list.add(dto);
					} while (false);
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}

		return page;
	}
	

	/**
	 * 利用搜索引擎搜索list
	 */
	@Override
	public List<ProductsDto> querypicByKeyWord(ProductsDO productsDO,
			boolean havePic, Integer pagesize) {
		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		page.setPageSize(pagesize);
		page = pageLHProductsBySearchEngine(productsDO, null, havePic, page);
		return page.getRecords();
	}

	@Override
	public List<ProductsDto> queryNewestVipProducts(String productTypeCode,
			String productCategory, Integer size) {
		if (size == null) {
			size = 10;
		}
		if (size > 50) {
			size = 50;
		}
		if (productCategory != null && productCategory.length() > 4) {
			productCategory = productCategory.substring(0, 4);
		}
		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		String title = CategoryProductsFacade.getInstance().getValue(productCategory);
		ProductsDO product = new ProductsDO();
		product.setTitle(title);
		product.setIsVip(true);
		product.setProductsTypeCode(productTypeCode);
		
//		return productsDAO.queryNewestVipProducts(productTypeCode,productCategory, size);
		return pageProductsBySearchEngine(product,null,null,page).getRecords();
	}

	@Override
	public ProductsDO queryProductsByCid(Integer cid) {
		return productsDAO.queryProductsByCid(cid);
	}

	@Override
	public ProductsDO queryProductsByCidForLatest(Integer cid,
			ProductsDO products) {
		return productsDAO.queryProductsByCidForLatest(cid, products);
	}

	@Override
	public boolean queryLastGmtCreateTimeByCId(Integer cid) {

		boolean flag = false;
		Date date = productsDAO.queryLastGmtCreateTimeByCId(cid);
		if (date == null) {
			return true;
		}
		long lastTimes = DateUtil.getMillis(date);
		// long nowTime = DateUtil.getMillis(new Date());
		long nowTime = DateUtil.getMillis(productsDAO.queryNowTimeOfDatebase());
		long timeTemp = (nowTime - lastTimes) / 1000;
		// flag ==true说明可以插入
		if (timeTemp > 30) {
			flag = true;
		}

		return flag;
	}

	@Override
	public PageDto<ProductsDto> pageProductsWithCompany(Company company,
			ProductsDO products, PageDto<ProductsDto> page,
			String industryCode, String areaCode, String keywords) {
		page.setRecords(productsDAO.queryProductsWithCompany(company, products,
				page, industryCode, areaCode, keywords));
		page.setTotalRecords(productsDAO.countQueryProductsWithCompany(company,
				products, page, industryCode, areaCode, keywords));
		return page;
	}

	public List<ProductsDto> queryProductsByAreaCode4Map(String mainCode,
			String title, String typeCode, String areaCode, Integer maxSize) {

		List<ProductsDto> productsList = productsDAO
				.queryProductsByAreaCode4Map(mainCode, title, typeCode,
						areaCode, maxSize);

		CategoryFacade categoryFacade = CategoryFacade.getInstance();
		for (ProductsDto dto : productsList) {
			dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
					.getProductsTypeCode()));
			dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
					.getAreaCode()));
			dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
					.getMembershipCode()));
			dto.setCategoryProductsMainLabel(CategoryProductsFacade
					.getInstance().getValue(
							dto.getProducts().getCategoryProductsMainCode()));
			dto.setCategoryProductsAssistLabel(CategoryProductsFacade
					.getInstance().getValue(
							dto.getProducts().getCategoryProductsAssistCode()));
		}

		return productsList;
	}

	@Override
	public List<ProductsDto> queryNewestVipProducts(String productTypeCode,
			Integer size) {

		List<Company> list = companyDAO.queryCompanyZstMemberAsc((size + 10));
		ProductsDto p = null;
		List<ProductsDto> proList = new ArrayList<ProductsDto>();
		for (int i = 0; i < list.size(); i++) {
			int id = list.get(i).getId();
			p = productsDAO.queryProductsWithPicByCidAndTypeCode(id,
					productTypeCode, 1);
			if (p != null) {
				proList.add(p);
			}
			if (proList.size() == 14) {
				break;
			}
		}

		return proList;
	}

	@Override
	public Integer updateUncheckByIdForMyrc(Integer id) {
		return productsDAO.updateUncheckByIdForMyrc(id);
	}

	public PageDto<ProductsDto> pageProductsWithPicByCompanyEsiteWithDetails(
			Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page) {
		page.setTotalRecords(productsDAO.queryProductsWithPicByCompanyEsiteCount(companyId, kw, sid));
		List<ProductsDto> list = productsDAO.queryProductsWithPicByCompanyEsiteWithDetails(companyId, kw,sid, page);
		List<ProductsDto> nlist = new ArrayList<ProductsDto>(); 
		for (ProductsDto obj : list) {
			// 敏感词过滤
			try {
				if(SensitiveUtils.validateSensitiveFilter(obj.getProducts().getTitle())){
					continue;
				}
			} catch (IOException e) {
				continue;
			}
			obj.getProducts().setDetails(Jsoup.clean(obj.getProducts().getDetails(), Whitelist.none()));
			nlist.add(obj);
		}
		page.setRecords(nlist);
		return page;
	}

	@Override
	public Integer updateGaoCheckStatusByAdmin(String checkStatus,
			String checkPerson, Integer productId, String unpassReason) {
		return productsDAO.updateGaoCheckStatusByAdmin(checkStatus,
				checkPerson, productId, unpassReason);
	}

	@Override
	public List<ProductsDto> queryHotProducts(String mainCode,
			String productsTypeCode, Integer maxSize) {
		// 查询 会员
		List<Company> com = companyDAO
				.queryCompanyZstMemberByLastLoginTime(maxSize);
		// 根据会员查询产品
		List<ProductsDto> list = new ArrayList<ProductsDto>();
		for (Company company : com) {
			ProductsDto pro = productsDAO
					.queryProductsByCidAndTypeCodeAndMainCode(company.getId(),
							mainCode, productsTypeCode, 1);
			if (pro != null) {
				list.add(pro);
			}
		}

		return list;
	}

	@Override
	public List<ProductsDto> queryVipProductsForMyrc(String title, Integer size) {
		PageDto<ProductsDto> page = new PageDto<ProductsDto>();
		page.setPageSize(size);
		ProductsDO product = new ProductsDO();
		product.setTitle(title);
		product.setIsVip(true);
		page = pageProductsBySearchEngine(product, null, null, page);
		return page.getRecords();
	}

	@Override
	public Integer updateProductsCheckStatusForDelByAdmin(String checkStatus,
			String unpassReason, String checkPerson, Integer productId,
			String isDel) {
		return productsDAO.updateProductsUncheckedCheckStatusForDelByAdmin(
				checkStatus, unpassReason, checkPerson, productId, isDel);
	}

	@Override
	public PageDto<ProductsDO> pageProductsByTyepOfCompany(String companyId,
			String productTypeCode, PageDto<ProductsDO> page) {
		if (page.getSort() == null) {
			page.setSort("p.refresh_time");
		}
		if (page.getDir() == null) {
			page.setDir("desc");
		}
		if (StringUtils.isEmpty(productTypeCode)) {
			productTypeCode = "10331000";
		}
		List<ProductsDO> list = productsDAO.queryProductsByTypeOfCompany(
				companyId, productTypeCode, page);
		page.setRecords(list);
		page.setTotalRecords(productsDAO.queryProductsByTypeOfCompany(
				companyId, productTypeCode));
		return page;
	}

	@Override
	public List<ProductsDO> queryProductsByTyepOfCompany(String companyId,
			String productTypeCode, PageDto<ProductsDO> page) {
		if (page.getSort() == null) {
			page.setSort("p.refresh_time");
		}
		if (page.getDir() == null) {
			page.setDir("desc");
		}
		if (StringUtils.isEmpty(productTypeCode)) {
			productTypeCode = "10331000";
		}
		List<ProductsDO> list = productsDAO.queryProductsByTypeOfCompany(
				companyId, productTypeCode, page);
		return list;
	}

	@Override
	public Integer updateProductsIsDelByAdmin(Integer productId, String status) {
		Assert.notNull(productId, "the product id can not be null");
		return productsDAO.updateProductsIsDelByAdmin(productId, status);
	}

	private StringBuffer checkStringBuffer(StringBuffer sb) {
		if (sb.length() != 0) {
			sb.append("&");
		}
		return sb;
	}

	@Override
	public PageDto<ProductsDto> pageProductsForSpot(ProductsDO product,
			PageDto<ProductsDto> page, String isTe, String isHot, String isYou) {
		if (page == null) {
			page = new PageDto<ProductsDto>();
		}
		page.setSort("refresh_time");
		page.setDir("desc");
		ProductsDto dto = new ProductsDto();
		if (StringUtils.isNotEmpty(isTe)) {
			dto.setIsTe(isTe);
		}
		if (StringUtils.isNotEmpty(isHot)) {
			dto.setIsHot(isHot);
		}
		if (StringUtils.isNotEmpty(isYou)) {
			dto.setIsYou(isYou);
		}
		dto.setProducts(product);
		List<ProductsDO> list = productsDAO.queryProductForSpot(dto, page);
		List<ProductsDto> nlist = new ArrayList<ProductsDto>();
		for (ProductsDO obj : list) {
			ProductsDto productsDto = new ProductsDto();
			productsDto.setProducts(obj);
			CompanyDto companyDto = companyDAO.queryCompanyWithAccountById(obj
					.getCompanyId());
			productsDto.setCompany(companyDto.getCompany());
			productsDto.setCompanyContacts(companyDto.getAccount());
			productsDto.setCoverPicUrl(productsPicDAO
					.queryPicPathByProductId(obj.getId()));
			// 获取保证金字段信息
			ProductsSpot ps = productsSpotDao.queryByProductId(obj.getId());
			if (ps == null) {
				productsDto.setIsBail(null);
			} else {
				productsDto.setIsBail(ps.getIsBail());
			}
			nlist.add(productsDto);
		}
		page.setRecords(nlist);
		page.setTotalRecords(productsDAO.queryCountProductForSpot(dto));
		return page;
	}

	@Override
	public List<ProductsDto> queryProductsForSpotByCondition(String isTe,
			String isHot, String isYou, Integer size) {
		ProductsDto dto = new ProductsDto();
		if (StringUtils.isNotEmpty(isTe)) {
			dto.setIsTe(isTe);
		}
		if (StringUtils.isNotEmpty(isHot)) {
			dto.setIsHot(isHot);
		}
		if (StringUtils.isNotEmpty(isYou)) {
			dto.setIsYou(isYou);
		}
		List<ProductsDO> list = productsDAO.queryProductsForSpotByCondition(
				dto, size);
		List<ProductsDto> nlist = new ArrayList<ProductsDto>();
		for (ProductsDO obj : list) {
			ProductsDto productsDto = new ProductsDto();
			productsDto.setProducts(obj);
			CompanyDto companyDto = companyDAO.queryCompanyWithAccountById(obj
					.getCompanyId());
			productsDto.setCompany(companyDto.getCompany());
			productsDto.setCompanyContacts(companyDto.getAccount());
			productsDto.setCoverPicUrl(productsPicDAO
					.queryPicPathByProductId(obj.getId()));
			// 获取保证金字段信息
			ProductsSpot ps = productsSpotDao.queryByProductId(obj.getId());
			if (ps == null) {
				productsDto.setIsBail(null);
			} else {
				productsDto.setIsBail(ps.getIsBail());
			}
			productsDto.setProductsSpot(ps);
			nlist.add(productsDto);
		}
		return nlist;
	}

	@Override
	public PageDto<ProductsDto> pageProductsForSpotByAdmin(Company company,
			ProductsDO products, PageDto<ProductsDto> page, Integer min,
			Integer max, String isStatus) {
		List<ProductsDto> list = productsDAO.queryProductForSpotByAdmin(
				company, products, page, min, max, isStatus);
		for (ProductsDto dto : list) {
			CompanyDto companyDto = companyDAO.queryCompanyWithAccountById(dto
					.getProducts().getCompanyId());
			dto.setCompany(companyDto.getCompany());
			dto.setCompanyContacts(companyDto.getAccount());
			String status = "";
			do {
				// 公司是否属于黑名单
				if ("1".equals(dto.getCompany().getIsBlock())) {
					dto.getCompany().setName(
							dto.getCompany().getName() + "(黑名单)");
				}
				if ("1".equals(dto.getProductsSpot().getIsBail())) {
					status += "(保)";
				}
				if ("1".equals(dto.getProductsSpot().getIsHot())) {
					status += "(热)";
				}
				if ("1".equals(dto.getProductsSpot().getIsTe())) {
					status += "(特)";
				}
				if ("1".equals(dto.getProductsSpot().getIsYou())) {
					status += "(优)";
				}
				// 信息已删除 或者 账户被禁用
				// if(dto.getProducts().getIsDel()==true||"1".equals(dto.getCompany().getIsBlock())){
				// status = "(已删除)";
				// break;
				// }
				// // 暂不发布
				// if(dto.getProducts().getIsPause()==true){
				// status = "(暂不发布)";
				// break;
				// }
				// // 是否过期
				// long expired=dto.getProducts().getExpireTime().getTime();
				// long today=new Date().getTime();
				// long result= today - expired;
				// if(result>0){
				// status = "(已过期)";
				// break;
				// }
			} while (false);
			CategoryFacade categoryFacade = CategoryFacade.getInstance();
			CategoryProductsFacade categoryProductsFacade = CategoryProductsFacade
					.getInstance();
			dto.getProducts().setTitle(dto.getProducts().getTitle() + status);
			dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
					.getProductsTypeCode()));
			dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
					.getAreaCode()));
			dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
					.getMembershipCode()));
			dto.setCategoryProductsMainLabel(categoryProductsFacade
					.getValue(dto.getProducts().getCategoryProductsMainCode()));
		}
		page.setRecords(list);
		page.setTotalRecords(productsDAO.queryCountProductForSpotByAdmin(
				company, products, min, max, isStatus));
		return page;
	}

	@Override
	public Integer queryTodayCopperProductsCount(String code, Date time) {
		String from = "";
		String to = "";
		if (time != null) {
			from = DateUtil.toString(DateUtil.getDateAfterDays(time, -1),
					"yyyy-MM-dd");
			to = DateUtil.toString(time, "yyyy-MM-dd");
		}
		return productsDAO.queryTodayCopperProductsCount(code, from, to);
	}

	@Override
	public List<ProductsDto> queryProductsForPic(ProductsDO products,
			Integer size) {
		List<ProductsDto> list = new ArrayList<ProductsDto>();
		List<ProductsDO> plist = productsDAO
				.queryProductsForPic(products, size);
		for (ProductsDO obj : plist) {
			ProductsDto dto = new ProductsDto();
			if (obj != null && obj.getId() != null) {
				String picPath = productsPicDAO.queryPicPathByProductId(obj
						.getId());
				dto.setCoverPicUrl(picPath);
				dto.setProducts(obj);
				list.add(dto);
			}
		}
		return list;
	}

	@Override
	public PageDto<ProductsDto> queryProductForexportByAdmin(Company company,
			ProductsDO products, PageDto<ProductsDto> page, Integer min,
			Integer max, String isStatus) {
		List<ProductsDto> list = productsDAO.queryProductForexportByAdmin(
				company, products, page, min, max, isStatus);
		for (ProductsDto dto : list) {
			CompanyDto companyDto = companyDAO.queryCompanyWithAccountById(dto
					.getProducts().getCompanyId());
			dto.setCompany(companyDto.getCompany());
			dto.setCompanyContacts(companyDto.getAccount());
			String status = "";
			do {
				// 公司是否属于黑名单
				if ("1".equals(dto.getCompany().getIsBlock())) {
					dto.getCompany().setName(
							dto.getCompany().getName() + "(黑名单)");
				}
			} while (false);
			CategoryFacade categoryFacade = CategoryFacade.getInstance();
			CategoryProductsFacade categoryProductsFacade = CategoryProductsFacade
					.getInstance();
			dto.getProducts().setTitle(dto.getProducts().getTitle() + status);
			dto.setProductsTypeLabel(categoryFacade.getValue(dto.getProducts()
					.getProductsTypeCode()));
			dto.setAreaLabel(categoryFacade.getValue(dto.getCompany()
					.getAreaCode()));
			dto.setMembershipLabel(categoryFacade.getValue(dto.getCompany()
					.getMembershipCode()));
			dto.setCategoryProductsMainLabel(categoryProductsFacade
					.getValue(dto.getProducts().getCategoryProductsMainCode()));
		}
		page.setRecords(list);
		return page;
	}


	@Override
	public List<ProductsDto> buildLIst() {
		List<ProductsDto> listgy = productsDAO.queryNewestVipProducts(
				"10331000", "1001", 20);
		List<ProductsDto> listqg = productsDAO.queryNewestVipProducts(
				"10331001", "1001", 20);
		List<ProductsDto> list = new ArrayList<ProductsDto>();
		for (int i = 0; i < listgy.size(); i++) {
			if(listqg.size()>0&&listqg!=null){	
			   list.add(listqg.get(i));
			   list.add(listgy.get(i));
			  }	else{
					break;
			   }
		}	
		return list;
	}

	@Override
	public PageDto<ProductsDto> pageSPProductsBySearchEngine(
			ProductsDO product, PageDto<ProductsDto> page) {
		page = pageProductsBySearchEngine(product, null, null, page);
		for (ProductsDto dto : page.getRecords()) {
			Integer companyId = dto.getProducts().getCompanyId();
			dto.setCompanyContacts(companyAccountDao
					.queryAccountByCompanyId(companyId));
			Company company = companyDAO.queryCompanyById(companyId);
			dto.setCompany(company);
			// 公司地区
			if (company != null
					&& StringUtils.isNotEmpty(company.getAreaCode())) {
				if (company.getAreaCode().length() >= 12) {
					dto.setAreaLabel(CategoryFacade.getInstance().getValue(
							company.getAreaCode().substring(0, 12)));
				}
				if (company.getAreaCode().length() >= 16) {
					dto.setAreaLabel(dto.getAreaLabel()
							+ CategoryFacade.getInstance().getValue(
									company.getAreaCode().substring(0, 16)));
				}
			}
			// 产品其他信息
			if (dto.getProducts() != null && dto.getProducts().getId() != null) {
				dto.setProducts(productsDAO.queryProductsById(dto.getProducts()
						.getId()));
			}
			// tags list 获取
			String tags = "";
			if (dto.getProducts() != null && dto.getProducts().getTags() != null) {
				tags += dto.getProducts().getTags();
			}
			if (dto.getProducts() != null && dto.getProducts().getTagsAdmin() != null) {
				tags += "," + dto.getProducts().getTagsAdmin();
			}
			Map<String, String> map = TagsUtils.getInstance().encodeTags(tags,"utf-8");
			for (String key : map.keySet()) {
				map.put(key, CNToHexUtil.getInstance().encode(key));
			}
			dto.setTagsMap(map);
		}
		return page;
	}

	@Override
	public PageDto<ProductsDto> pageProductsWithPicByCompanyForSp(Integer companyId, String kw, Integer sid, PageDto<ProductsDto> page) {
		page.setTotalRecords(productsDAO.queryProductsWithPicByCompanyForSpCount(companyId, kw, sid));
		List<ProductsDto> list = productsDAO.queryProductsWithPicByCompanyForSp(companyId, kw, sid, page);
		List<ProductsDto> nlist = new ArrayList<ProductsDto>();
		for (ProductsDto obj : list) {
			// 敏感词过滤
			try {
				if(SensitiveUtils.validateSensitiveFilter(obj.getProducts().getTitle())){
					continue;
				}
			} catch (IOException e) {
				continue;
			}
			obj.getProducts().setDetails(Jsoup.clean(obj.getProducts().getDetails(), Whitelist.none()));
			nlist.add(obj);
		}
		page.setRecords(nlist);
		return page;
	}

	@Override
	public List<ProductsDO> queryPassProductsByDate(String dateStr) {
		if (StringUtils.isEmpty(dateStr)) {
			return null;
		}
		Date date = new Date();
		try {
			date = DateUtil.getDate(dateStr, "yyyy-MM-dd");
		} catch (ParseException e) {
			return null;
		}
		String to = DateUtil.toString(DateUtil.getDateAfterDays(date, 1),
				"yyyy-MM-dd");
		return productsDAO.queryPassProductsByDate(dateStr, to);
	}

	@Override
	public String getTagAdmin(ProductsDto productsDto) {
		List<String> keyList = null;
		String title = productsDto.getProducts().getTitle();
		// 以 大小 逗号 分隔，均分成多个关键字进行关键字切词
		title = title.replace("，", ",");
		String[] key = title.split(",");
		String tagsAdmin = "";
		Set<String> kList = new HashSet<String>();
		for (String kTitle : key) {
			try {
				keyList = IKAnalzyerUtils.getAnalzyerList(kTitle);
			} catch (Exception e) {
				keyList = null;
			}
			for (String str : keyList) {
				// 文字长度大于等于2
				if (StringUtils.isEmpty(str) || str.length() <= 1) {
					continue;
				}
				// 大写
				str = str.toUpperCase();
				kList.add(str);
			}
			for (String str : kList) {
				tagsAdmin += str + ",";
			}
		}
		tagsAdmin += CategoryProductsFacade.getInstance().getValue(
				productsDto.getProducts().getCategoryProductsMainCode());
		if (StringUtils
				.isNotEmpty(productsDto.getCategoryProductsAssistLabel())) {
			tagsAdmin += "," + productsDto.getCategoryProductsAssistLabel();
		}
		return tagsAdmin;
	}

	@Override
	public Boolean countProuductsByTitleAndAccount(String title,
			String productsTypeCode, String account) {
		String from = DateUtil.toString(new Date(), "yyyy-MM-dd");
		String to = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), 1),"yyyy-MM-dd");
		Integer i = productsDAO.countProuductsByTitleAndAccount(title,productsTypeCode, account, from, to);
		if (i != null && i > 1) {
			return false;
		}
		return true;
	}

	@Override
	public Boolean validateTitleAndAccount(String title, String account) {
		Integer i = productsDAO.countProuductsByTitleAndAccount(title,null, account, null, null);
		if(i!=null&& i>0){
			return false;
		}
		return true;
	}
	public List<ProductsDto> queryProductsWithPicAndTypeCode(
			String productsTypeCode, Integer maxSize) {
		List<ProductsDto> list=productsDAO.queryProductsWithPicAndTypeCode(productsTypeCode, maxSize);
		return list;
	}
	
	/**
	 * 
	 */
	public List<ProductsDto> queryProductsWithPicAndTypeCodeAndVip(
			String productsTypeCode, Integer maxSize) {
		List<ProductsDto> list=productsDAO.queryProductsWithPicAndTypeCodeAndVip(productsTypeCode, maxSize);
		return list;
	}

	@Override
	public List<ProductsDto> queryProductbyHavePic(Integer size) {
		if(size==null){
			size = 100;
		}
		
		List<ProductsDto> dtoList = new ArrayList<ProductsDto>();
 		//查询出最新的供求信息
		List<ProductsDto> list=	productsDAO.queryNewProductsBysize(50);
		for (ProductsDto productsDto : list) {
			 if(dtoList.size()==6){
				 break;
			 }
			 String picPath = productsPicDAO.queryPicByProId(productsDto.getProducts().getId());
			 productsDto.setCoverPicUrl(picPath);
			 dtoList.add(productsDto);
			 
		}
		
		
		return dtoList;
	}

	//后台搜索关键字导出
	@Override
	public PageDto<KeywordSearchDto> pageKwproductBySearchEngine(
			PageDto<KeywordSearchDto> page, String keyword) {
		  Map<Integer, Set<Integer>> maps = new HashMap<Integer, Set<Integer>>();
		if(keyword!=null){
				if (page.getPageSize() == null) {
					page.setPageSize(20);
				}
			// 限制最大页数
			if (page.getStartIndex() != null
					&& page.getStartIndex() >= TOTAL_RECORDS - page.getPageSize()) {
				page.setStartIndex(TOTAL_RECORDS - page.getPageSize());
			}

			StringBuffer sb = new StringBuffer();
			SphinxClient cl = SearchEngineUtils.getInstance().getClient();

			List<KeywordSearchDto> list = new ArrayList<KeywordSearchDto>();
			List<KeywordSearchDto> list2 = new ArrayList<KeywordSearchDto>();
			try {

				sb
					.append(
						"@(title,tags,label0,label1,label2,label3,label4) ")
					.append(keyword);

				 //所查找的供求信息为已审核的(check_status=1)，未过期的(expire_time>=now()) <br />
				 //发布情况正常的(is_pause=0)，没有被删除的(is_del=0)供求信息
				cl.SetFilter("check_status", 1, false);
				cl.SetFilter("is_pause", 0, false);
				cl.SetFilter("is_del", 0, false);
				
				cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);

				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
				cl.SetLimits(0, 100000);
				
				SphinxResult res = cl.Query(sb.toString(),"offersearch_new,offersearch_new_vip");
				if (res == null) {
					page.setTotalRecords(0);
				} else {
					
					Integer j=0;
					for (int i = 0; i < res.matches.length; i++) {
						do {
							KeywordSearchDto keywordSearchDto=new KeywordSearchDto();
							SphinxMatch info = res.matches[i];
							ProductsDO dto=productsDAO.queryProductsById(Integer
											.valueOf("" + info.docId));
							if (dto != null && dto.getCompanyId() != null) {
								Set<Integer> idSet=maps.get(dto.getCompanyId());
			    				if(idSet==null){
			    					Set<Integer> set = new HashSet<Integer>();
			    					set.add(dto.getCompanyId());
				    				maps.put(dto.getCompanyId(), set);
				    				j=j+1;
									keywordSearchDto.setId(j);
									keywordSearchDto.setTitle(dto.getTitle());
									keywordSearchDto.setCompanyId(dto.getCompanyId());
									keywordSearchDto.setProductsTypeCode(dto.getProductsTypeCode());
									keywordSearchDto.setGmtCreated(dto.getGmtCreated());
									Company company=companyDAO.queryCompanyById(dto.getCompanyId());
									if(company!=null){
										keywordSearchDto.setCompanyName(company.getName());
										if (StringUtils.isNotEmpty(company.getMembershipCode())) {
											if(company.getMembershipCode().equals("10051000")){
												keywordSearchDto.setVip("否");
											}else {
												keywordSearchDto.setVip("是");
											}
										}
										String a=company.getAreaCode();
										String areaLabel = "";
										if (a.length() >= 12) {
											areaLabel += CategoryFacade.getInstance()
												.getValue(a.substring(0, 12));
										}
										if (a.length() >= 16) {
											areaLabel += CategoryFacade.getInstance()
												.getValue(a.substring(0, 16));
										}
										keywordSearchDto.setArea(areaLabel);
									}
									CompanyAccount companyAccount=companyAccountDao.queryAccountByCompanyId(dto.getCompanyId());
									if(companyAccount!=null){
										keywordSearchDto.setMobile(companyAccount.getMobile());
										keywordSearchDto.setNumLogin(companyAccount.getNumLogin());
									}
									list.add(keywordSearchDto);
								}
							}
							
							 
						} while (false);
					}
					page.setTotalRecords(j);
				}
				Integer size=0;
				if(page.getStartIndex()==null){
					page.setStartIndex(0);
				}
				for(Integer i=page.getStartIndex();i<list.size();i++){
					KeywordSearchDto kw=list.get(i);
					list2.add(kw);
					 size=size+1;
					 if(size==20){
						 break;
					 }
				}
				page.setRecords(list2);
			} catch (SphinxException e) {
				e.printStackTrace();
			}
		}
		return page;
	}
	//后台搜索关键字导出
	@Override
	public List<KeywordSearchDto> kwproductBySearchEngine(String keyword){
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		List<KeywordSearchDto> list = new ArrayList<KeywordSearchDto>();
		Map<Integer, Set<Integer>> maps = new HashMap<Integer, Set<Integer>>();
		
		try {

			sb
				.append(
					"@(title,tags,label0,label1,label2,label3,label4) ")
				.append(keyword);

			 //所查找的供求信息为已审核的(check_status=1)，未过期的(expire_time>=now()) <br />
			 //发布情况正常的(is_pause=0)，没有被删除的(is_del=0)供求信息
			cl.SetFilter("check_status", 1, false);
			cl.SetFilter("is_pause", 0, false);
			cl.SetFilter("is_del", 0, false);
			
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);

			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
			cl.SetLimits(0,100000);
			
			SphinxResult res = cl.Query(sb.toString(),"offersearch_new,offersearch_new_vip");
			if (res != null) {
				Integer j=1;
				for (int i = 0; i < res.matches.length; i++) {
					do {
						KeywordSearchDto keywordSearchDto=new KeywordSearchDto();
						SphinxMatch info = res.matches[i];
						ProductsDO dto=productsDAO.queryProductsById(Integer
										.valueOf("" + info.docId));
						if (dto != null && dto.getCompanyId() != null) {
							Set<Integer> idSet=maps.get(dto.getCompanyId());
		    				if(idSet==null){
		    					Set<Integer> set = new HashSet<Integer>();
		    					set.add(dto.getCompanyId());
			    				maps.put(dto.getCompanyId(), set);
								keywordSearchDto.setId(j);
								j++;
								
								keywordSearchDto.setTitle(Jsoup.clean(dto.getTitle(), Whitelist.none()));
								keywordSearchDto.setCompanyId(dto.getCompanyId());
								keywordSearchDto.setProductsTypeCode(dto.getProductsTypeCode());
								keywordSearchDto.setGmtCreated(dto.getGmtCreated());
								Company company=companyDAO.queryCompanyById(dto.getCompanyId());
								if(company!=null){
									keywordSearchDto.setCompanyName(company.getName());
									if (StringUtils.isNotEmpty(company.getMembershipCode())) {
										if(company.getMembershipCode().equals("10051000")){
											keywordSearchDto.setVip("否");
										}else {
											keywordSearchDto.setVip("是");
										}
									}
									String a=company.getAreaCode();
									String areaLabel = "";
									if (a.length() >= 12) {
										areaLabel += CategoryFacade.getInstance()
											.getValue(a.substring(0, 12));
									}
									if (a.length() >= 16) {
										areaLabel += CategoryFacade.getInstance()
											.getValue(a.substring(0, 16));
									}
									keywordSearchDto.setArea(areaLabel);
								}
								CompanyAccount companyAccount=companyAccountDao.queryAccountByCompanyId(dto.getCompanyId());
								if(companyAccount!=null){
									keywordSearchDto.setMobile(companyAccount.getMobile());
									keywordSearchDto.setNumLogin(companyAccount.getNumLogin());
								}
								list.add(keywordSearchDto);
							}
						}
						
						 
					} while (false);
				}
			}
		} catch (SphinxException e) {
			e.printStackTrace();
		}
	return list;
	}

	@Override
	public PageDto<ProductsDO> productsOfCompanyByStatus(Integer companyId,
			String account, String checkStatus, String isExpired,
			String isPause, String isPostFromInquiry, Integer groupId,
			String title, String isHide, PageDto<ProductsDO> page) {
		// 排序
				if (page.getDir() == null) {
					page.setDir("desc");
				}
				if (page.getSort() == null) {
					page.setSort("real_time");
				}
				if (isPostFromInquiry == null) {
					isPostFromInquiry = "0";
				}
				List<ProductsDO> list = productsDAO.productsOfCompanyByStatus(
						companyId, account, checkStatus, isExpired, isPause,
						isPostFromInquiry, groupId, title,isHide, page);
				for (ProductsDO obj : list) {
					Integer i = productsPicDAO.countPicIsNoPass(obj.getId());
					if (i > 0) {
						obj.setIsPicPass(0);
					}
					Sample sample = sampleDao.queryByProductId(obj.getId());
					if(sample!=null&&sample.getIsDel()!=null&&sample.getIsDel()==0){
						obj.setIsYP(true);
					}
					if (sample!=null) {
						obj.setSampleReason(sample.getUnpassReason());
					}
					ProductsStar ps = productsStarDao.queryByProductsId(obj.getId());
					if (ps!=null) {
						obj.setScore(ps.getScore());
					}else{
						obj.setScore(0);
					}
					
				}
				page.setRecords(list);
				page.setTotalRecords(productsDAO.productsOfCompanyByStatusCount(companyId, account, checkStatus, isExpired, isPause,isPostFromInquiry, groupId, title,isHide));
				return page;
	}

	@Override
	public Integer countExpireProductByCompanyId(ProductsDO products) {
		return productsDAO.countExpireProductByCompanyId(products);
	}

	@Override
	public Integer countValidProductByCompanyId(ProductsDO products) {
		return productsDAO.countValidProductByCompanyId(products);
	}
	public List<CompanyAccount> downLoadCompanyAccountByProduct(String refreshFrom, String refreshTo) {
		List<CompanyAccount>companyAccountList=new ArrayList<CompanyAccount>();
		List<Integer>list= productsDAO.productByRefreshTime(refreshFrom, refreshTo);
		for (Integer companyId : list) {
			if(companyId!=null&&companyId.intValue()>0){
				List<CrmCompanySvr> list1=crmCompanySvrDao.querySvrByCompanyId(companyId);
				if(list1==null||list1.size()<=0){
					CompanyAccount account=new CompanyAccount();
					CompanyAccount companyAccount=companyAccountDao.queryAccountByCompanyId(companyId);
					if(companyAccount!=null){
						account.setContact(companyAccount.getContact());
						account.setEmail(companyAccount.getEmail());
						account.setMobile(companyAccount.getMobile());
						companyAccountList.add(account);
					}
				}
			}
		}
		return companyAccountList;
	}

	@Override
	public List<ProductsDO> queryProductsByType(String productsTypeCode,Integer size) {
		return productsDAO.queryProductsByType(productsTypeCode, size);
	}

	@Override
	public Integer updateNewRefreshTimeById(Integer id, Integer companyId)
			throws ParseException {
		// 这个productsDo是构造用于更新刷新时间的类
		ProductsDO productsDO = new ProductsDO();
		Date now=new Date();
		Integer i = 0;
		do {
			if (id != null && id.intValue() > 0 && companyId != null) {
				productsDO.setId(id);
				productsDO.setCompanyId(companyId);
				productsDO.setRefreshTime(now);
				ProductsExpire productsExpire = productsExpireDao.queryByProductsId(id);
				ProductsDO p = productsDAO.queryProductsById(id);
				
				if (p == null) {
					break;
				}
				if ("2".equals(p.getIsPause())) {
					productsDO.setIsPause("0");
				}
				// 判断该供求是否在product_expire 表中有记录没？
				if (productsExpire != null && productsExpire.getDay() != null) {
					//计算过期时间
					Date expireTime = DateUtil.getDateAfterDays(now,productsExpire.getDay());
					productsDO.setExpireTime(expireTime);
				} else {
					// 计算老供求数据的有效期
					
					//构造用于插入product_expire 表中的类
					ProductsExpire productsExpire2=new ProductsExpire();
					
					if (p.getRefreshTime() != null && p.getExpireTime() != null) {
						Integer day = Math.abs(DateUtil.getIntervalDays(p.getExpireTime(), p.getRefreshTime()));
						if (day <= 10) {
							day = 10;
						} else if (day <= 20 && day > 10) {
							day = 20;
						} else if (day <= 30 && day > 20) {
							day = 30;
						} else if (day <= 90 && day > 30) {
							day = 90;
						} else {
							day = 365;
						}
						productsExpire2.setDay(day);
					} else {
						productsExpire2.setDay(10);
					}
					productsExpire2.setProductsId(id);
					// 插入到 product_expire 表
					productsExpireDao.insert(productsExpire2);
					//计算过期时间
					Date expireTime = DateUtil.getDateAfterDays(now,productsExpire2.getDay());
					productsDO.setExpireTime(expireTime);
				}

				//更新供求刷新时间
				i = productsDAO.updateNewRefreshTimeById(productsDO);
				//判断该供求是否已隐藏
				ProductsHide productsHide=productsHideDao.queryByProductId(id);
				if (productsHide!=null) {
					productsHideDao.delete(id);
				}
			}
		} while (false);
		return i;
	}

	@Override
	public Integer hasTitle(String title) {
		return productsDAO.hasTitle(title);
	}
	@Override
	public Integer countHideProductBycompanyId(Integer companyId){
		Assert.notNull(companyId, "The companyId must not be null");
		return productsDAO.queryProductsOfCompanyByStatusCount(companyId, null,null, null, "2", null, null, "");
	}

	@Override
	public Integer countProductBySoucre(Date from, Date to,String sourceTypeCode) {
		return productsDAO.countProductBySoucre(from, to, sourceTypeCode);
	}

	@Override
	public List<ProductsDO> queryNewProducts(Integer cid) {
		return productsDAO.queryNewProducts(cid);
	}

	@Override
	public List<ProductsDO> queryProductsByTypeFresh(String productsTypeCode,
			Integer size) {
		return productsDAO.queryProductsByTypeFresh(productsTypeCode,size);
	}
	
}
