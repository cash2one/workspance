/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009 下午03:39:25
 */
package com.ast.ast1949.service.company.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CategoryGardenDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.CompanyUploadFileDO;
import com.ast.ast1949.domain.company.XmlFile;
import com.ast.ast1949.domain.log.LogOperation;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.domain.yuanliao.YuanLiaoSearch;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.domain.yuanliao.YuanliaoPic;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.company.CompanySearch;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.persist.company.CategoryGardenDAO;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.log.LogOperationDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.site.CategoryDAO;
import com.ast.ast1949.persist.yuanliao.YuanliaoDao;
import com.ast.ast1949.persist.yuanliao.YuanliaoPicDao;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyAttestService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyUploadFileService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.dataindex.DataIndexService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.market.MarketCompanyService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.DateUtil;
import com.ast.ast1949.util.StringUtils;
import com.mongodb.DBObject;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.lang.SensitiveUtils;
import com.zz91.util.mongo.MongoDBUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

/**
 * 
 * @author Ryan
 * 
 */
@Component("companyService")
public class CompanyServiceImpl implements CompanyService {
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CategoryGardenDAO categoryGardenDAO;
	@Resource
	private ProductsService productsService;
	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private CategoryDAO categoryDAO;
	@Resource
	private PhoneService phoneService;
	@Resource
	private CompanyUploadFileService companyUploadFileService;
	@Resource
	private BbsService bbsService;
	@Resource
	private InquiryService inquiryService;
	@Resource
	private BbsPostService bbsPostService;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private MarketCompanyService marketCompanyService;
	@Resource
	private CompanyAttestService companyAttestService;
	@Resource
	private YuanliaoDao yuanliaoDao;
	@Resource
	private YuanliaoPicDao yuanliaoPicDao;
	@Resource
	private LogOperationDao logOperationDao;
	@Resource
	private DataIndexService dataIndexService;

	final static Integer TOTAL_RECORDS = 100000;

	public Integer registerCompany(Company company) throws Exception {
		Assert.notNull(company, "the object company can not be null");

		company.setIsBlock(CompanyDAO.BLOCK_FALSE);
		company.setZstFlag(CompanyDAO.ZST_FLAG_FALSE);
		company.setMembershipCode(CompanyDAO.DEFAULT_MEMBERSHIP);
		company.setClassifiedCode(CompanyDAO.DEFAULT_CLASSIFIED);

		if (StringUtils.isEmpty(company.getRegfromCode())) {
			company.setRegfromCode(CompanyDAO.DEFAULT_REGFROM);
		}
		if (StringUtils.isEmpty(company.getIndustryCode())) {
			company.setIndustryCode("");
		}
		if (StringUtils.isEmpty(company.getBusiness())) {
			company.setBusiness("");
		}
		if (StringUtils.isEmpty(company.getName())) {
			company.setName("");
		}
		if (StringUtils.isEmpty(company.getAreaCode())) {
			// 默认中国
			company.setAreaCode("10011000");
		}
		return companyDAO.insertCompany(company);
	}

	public Company queryCompanyById(Integer id) {
		Company company = companyDAO.queryCompanyById(id);
		// company = checkByAdmin(company);// 过滤敏感词
		return company;
	}

	public Company querySimpleCompanyById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return companyDAO.querySimpleCompanyById(id);
	}

	public CompanyDto queryCompanyDetailById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		Company company = companyDAO.queryCompanyById(id);
		if (company == null) {
			return null;
		}
		CompanyDto dto = fillDetail(company);
		if (company.getCategoryGardenId() != null
				&& company.getCategoryGardenId().intValue() > 0) {
			CategoryGardenDO garden = categoryGardenDAO
					.queryCategoryGardenById(company.getCategoryGardenId());
			dto.setCategoryGardenName(garden.getName());
			dto.setCategoryGardenShorterName(garden.getShorterName());
		}
		// 地区相关
		if (StringUtils.isNotEmpty(company.getAreaCode())) {
			dto.setAreaLabel(CategoryFacade.getInstance().getValue(
					company.getAreaCode()));
			if (company.getAreaCode().length() >= 8) {
				dto.setCountry(CategoryFacade.getInstance().getValue(
						company.getAreaCode().substring(0, 8)));
			}
			if (company.getAreaCode().length() >= 12) {
				dto.setProvince(CategoryFacade.getInstance().getValue(
						company.getAreaCode().substring(0, 12)));
			}
			if (company.getAreaCode().length() >= 16) {
				dto.setCity(CategoryFacade.getInstance().getValue(
						company.getAreaCode().substring(0, 16)));
			}
		}
		// 获取公司帐号信息
		dto.setAccount(companyAccountDao.queryAccountByCompanyId(id));
		return dto;
	}

	public CompanyDto querySimpleCompanyDetailById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		Company company = companyDAO.querySimpleCompanyById(id);
		if (company == null) {
			return null;
		}
		CompanyDto dto = fillDetail(company);
		if (company.getCategoryGardenId() != null
				&& company.getCategoryGardenId().intValue() > 0) {
			CategoryGardenDO garden = categoryGardenDAO
					.queryCategoryGardenById(company.getCategoryGardenId());
			dto.setCategoryGardenName(garden.getName());
			dto.setCategoryGardenShorterName(garden.getShorterName());
		}
		return dto;
	}

	public String queryMembershipOfCompany(Integer id) {
		if (id == null) {
			return null;
		}
		return companyDAO.queryMembershipOfCompany(id);
	}

	public String queryAreaCodeOfCompany(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return companyDAO.queryAreaCodeOfCompany(id);
	}

	private CompanyDto fillDetail(Company company) {
		CompanyDto dto = new CompanyDto();
		dto.setCompany(company);

		dto.setRegfromLabel(CategoryFacade.getInstance().getValue(
				company.getRegfromCode()));
		dto.setIndustryLabel(CategoryFacade.getInstance().getValue(
				company.getIndustryCode()));
		dto.setServiceLabel(CategoryFacade.getInstance().getValue(
				company.getServiceCode()));
		dto.setMembershipLabel(CategoryFacade.getInstance().getValue(
				company.getMembershipCode()));

		// 地区相关
		if (StringUtils.isNotEmpty(company.getAreaCode())) {
			dto.setAreaLabel(CategoryFacade.getInstance().getValue(
					company.getAreaCode()));
			if (company.getAreaCode().length() >= 8) {
				dto.setCountry(CategoryFacade.getInstance().getValue(
						company.getAreaCode().substring(0, 8)));
			}
			if (company.getAreaCode().length() >= 12) {
				dto.setProvince(CategoryFacade.getInstance().getValue(
						company.getAreaCode().substring(0, 12)));
			}
			if (company.getAreaCode().length() >= 16) {
				dto.setCity(CategoryFacade.getInstance().getValue(
						company.getAreaCode().substring(0, 16)));
			}
		}

		return dto;
	}

	public Integer updateIntroduction(Integer id, String intro) {
		Assert.notNull(id, "the id can not be null");

		return companyDAO.updateIntroduction(id, intro);
	}

	public List<Company> queryGoodCompany(Integer maxSize) {
		if (maxSize == null) {
			maxSize = 10;
		}
		if (maxSize.intValue() <= 0) {
			return new ArrayList<Company>();
		}

		return companyDAO.queryCompanyByClassifiedCode("10101003", maxSize);
	}

	public List<CompanyDto> queryCompanyByArea(String areaCode, Integer maxSize) {

		if (maxSize == null) {
			maxSize = 10;
		}
		if (maxSize.intValue() <= 0) {
			return new ArrayList<CompanyDto>();
		}
		List<Company> list = companyDAO.queryCompanyByArea(areaCode, maxSize);
		List<CompanyDto> resultList = new ArrayList<CompanyDto>();
		for (Company company : list) {
			resultList.add(fillDetail(company));
		}

		return resultList;
	}

	public Integer updateCompanyByUser(Company company) {
		Assert.notNull(company, "the object company can not be null");
		Assert.notNull(company.getId(), "the company id can not be null");
		int id = company.getId();
		CompanyAccount companyAccount = companyAccountService
				.queryAccountByCompanyId(id);
		String tel = companyAccount.getMobile();
		// 公司地区为空
		if (StringUtils.isEmpty(company.getAreaCode())
				|| "10011000".equals(company.getAreaCode())) {
			if (StringUtils.isNotEmpty(tel)) {
				String areaCode = getMobileLocation(tel);
				if (StringUtils.isNotEmpty(areaCode)) {
					company.setAreaCode(areaCode);
				}
			}
		}
		if (StringUtils.isEmpty(company.getAddress())) {
			String address = "";
			String code = company.getAreaCode();
			if (code.length() >= 8) {
				address += CategoryFacade.getInstance().getValue(
						code.substring(0, 8));
			}
			if (code.length() >= 12) {
				address += CategoryFacade.getInstance().getValue(
						code.substring(0, 12));
			}
			if (code.length() >= 16) {
				address += CategoryFacade.getInstance().getValue(
						code.substring(0, 16));
			}
			try {
				company.setAddress(address);
			} catch (Exception e) {
			}
		}
		return companyDAO.updateCompanyByUser(company);
	}

	public Integer updateCompanyByAdmin(Company company) {
		Assert.notNull(company, "the object company can not be null");
		Assert.notNull(company.getId(), "the company id can not be null");
		return companyDAO.updateCompanyByAdmin(company);
	}

	public Integer updateCompanyByAdminCheck(Company company) {
		Assert.notNull(company, "the object company can not be null");
		Assert.notNull(company.getId(), "the company id can not be null");
		return companyDAO.updateCompanyByAdminCheck(company);
	}

	public PageDto<CompanyDto> pageCompanyBySearch(Company company,
			PageDto<CompanyDto> page) {
		Assert.notNull(company, "the object company can not be null");
		List<Company> list = companyDAO.queryCompanyBySearch(company, page);
		if (list == null || list.size() <= 0) {
			page.setRecords(new ArrayList<CompanyDto>());
			page.setTotalRecords(0);
			return page;
		}

		List<CompanyDto> resultList = new ArrayList<CompanyDto>();
		for (Company c : list) {
			resultList.add(fillDetail(c));
		}
		page.setRecords(resultList);
		page.setTotalRecords(companyDAO.queryCompanyBySearchCount(company));
		return page;
	}

	@Override
	public List<CompanyDto> queryCompanyByEmail(String email) {
		Assert.notNull(email, "the email can not be null");
		return companyDAO.queryCompanyByEmail(email, 20);
	}

	@Override
	public Company queryDomainOfCompany(Integer companyId) {
		return companyDAO.queryDomainOfCompany(companyId);
	}

	@Override
	public Integer saveCustomDomain(Integer companyId, String domain) {
		return companyDAO.updateCustomDomain(companyId, domain);
	}

	@Override
	public CompanyDto queryCompanyByAccountEmail(String email, Boolean isVip) {
		if (isVip != null && isVip != true) {
			isVip = null;
		}
		return companyDAO.queryCompanyByAccountEmail(email, isVip);
	}

	@Override
	public List<Company> queryCompanyByLoginNum(Integer max) {
		if (max != null) {
			max = 5;
		}
		return companyDAO.queryCompanyByLoginNum(max);
	}

	@Override
	public CompanyDto queryCompanyWithAccountById(Integer id) {
		return companyDAO.queryCompanyWithAccountById(id);
	}

	@Override
	public PageDto<CompanyDto> pageCompanyBySearchEngine(Company company,
			PageDto<CompanyDto> page) {
		if (page.getPageSize() == null) {
			page.setPageSize(15);
		}
		if (page.getStartIndex() != null && page.getStartIndex() >= 7500) {
			page.setStartIndex(7500);
		}
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		List<CompanyDto> list = new ArrayList<CompanyDto>();
		try {
			if (company.getCategoryGardenId() != null) {
				cl.SetFilter("category_garden_id",
						company.getCategoryGardenId(), false);
			}
			if (StringUtils.isNotEmpty(company.getAreaCode())) {
				if (sb.indexOf("@") != -1) {
					sb.append("&");
				}
				sb.append("@(area_name,area_province) ").append(
						CategoryFacade.getInstance().getValue(
								company.getAreaCode()));
			}
			if (StringUtils.isNotEmpty(company.getIndustryCode())) {
				cl.SetFilter("industry_code",
						Integer.valueOf(company.getIndustryCode()), false);
			}
			if (StringUtils.isNotEmpty(company.getName())) {
				if (sb.indexOf("@") != -1) {
					sb.append("&");
				}
				sb.append("@(name,business,buy_details,sale_details,tags) ")
						.append(company.getName());
			}

			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED,
					"membership_code desc,gmt_start desc");

			SphinxResult res = cl.Query(sb.toString(), "company");

			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					Map<String, Object> resultMap = SearchEngineUtils
							.getInstance().resolveResult(res, info);
					CompanyDto dto = queryCompanyDetailById(Integer.valueOf(""
							+ info.docId));
					if (dto != null) {
						if (dto.getCompany().getIntroduction() != null) {
							dto.getCompany().setIntroduction(
									StringUtils.removeHTML(dto.getCompany()
											.getIntroduction()));
						}
						PageDto<ProductsDO> proPage = new PageDto<ProductsDO>();
						List<ProductsDO> offerList = productsService
								.queryProductsByTyepOfCompany(dto.getCompany()
										.getId().toString(),
										ProductsService.PRODUCTS_TYPE_OFFER,
										proPage);
						if (offerList != null && offerList.size() > 0) {
							dto.setOfferPro(offerList.get(0));
						}

						List<ProductsDO> buyList = productsService
								.queryProductsByTyepOfCompany(dto.getCompany()
										.getId().toString(),
										ProductsService.PRODUCTS_TYPE_BUY,
										proPage);
						if (buyList != null && buyList.size() > 0) {
							dto.setBuyPro(buyList.get(0));
						}

						Phone phone = phoneService.queryByCompanyId(Integer
								.valueOf("" + info.docId));
						if (phone != null) {
							dto.setIsLDB(true);
						}
						// 省份
						dto.setPareaProvince(resultMap.get("parea_province")
								.toString());

						// 终生会员服务 标志判断
						Boolean lifeFlag = crmCompanySvrService.validatePeriod(
								dto.getCompany().getId(),
								CrmCompanySvrService.LIFE_CODE);
						if (lifeFlag) {
							dto.setIsZSVip(true);
						}

						// 产业带获取
						Market market = marketCompanyService
								.queryFirstMarketByCompanyId(dto.getCompany()
										.getId());
						if (market != null) {
							dto.setMarketName(market.getName());
							dto.setMarketWords(market.getWords());
						}

						// 是否认证
						if (companyAttestService.validatePassOrNot(dto
								.getCompany().getId())) {
							dto.setIsAttest(1);
						}

						list.add(dto);
					}
				}
			}

			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}
		// id,com_id,com_name,com_cx,com_intro,com_viptype,com_province,com_keywords,province_code,city_code,garden_code,com_subname,com_productslist_en,sales_buy
		return page;
	}

	@Override
	public String queryDetails(Integer cid) {
		if (cid == null || cid.intValue() == 0) {
			return "";
		}
		return companyDAO.queryDetails(cid);
	}

	@Override
	public List<Company> queryRecentZst(Integer size) {
		if (size == null) {
			size = 100;
		}
		if (size.intValue() > 200) {
			size = 200;
		}
		Date start = DateUtil.getDateAfterMonths(new Date(), -3);
		return companyDAO.queryRecentZst(start, size);
	}

	@Override
	public boolean validateDomainZz91(Integer companyId, String domainZz91) {
		if (companyId == null || companyId.intValue() == 0) {
			return false;
		}
		Integer cid = companyDAO.queryCompanyIdByDomainZz91(domainZz91);
		if (cid == null || cid.intValue() == companyId) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateIsBlack(Integer companyId) {
		do {
			if (companyId == null) {
				break;
			}
			int i = companyDAO.queryCountBlackByCompanyId(companyId);
			if (i > 0) {
				return true;
			}
		} while (false);
		return false;
	}

	@Override
	public Integer updateMembershipCode(String membershipCode, Integer companyId) {
		if (StringUtils.isEmpty(membershipCode)) {
			return null;
		}
		return companyDAO.updateMembershipCode(companyId, membershipCode);
	}

	@Override
	public PageDto<Company> pageCompanyByAdmin(Company company,
			Date gmtRegisterStart, Date gmtRegisterEnd, Integer cid,
			String email, String account, String mobile, PageDto<Company> page) {
		Assert.notNull(company, "the company can not be null");
		Assert.notNull(page, "the page can not be null");
		if (StringUtils.isNotEmpty(email)) {
			cid = companyAccountService.queryCompanyIdByEmail(email);
		}
		if (StringUtils.isNotEmpty(account)) {
			cid = companyAccountService.queryCompanyIdByAccount(account);
		}
		if (StringUtils.isNotEmpty(mobile)) {
			cid = companyAccountService.queryComapnyIdByMobile(mobile);
		}
		page.setRecords(companyDAO.queryCompany(company, gmtRegisterStart,
				gmtRegisterEnd, cid, page));
		page.setTotalRecords(companyDAO.queryCompanyCountByAdmin(company,
				gmtRegisterStart, gmtRegisterEnd, cid));
		return page;
	}

	public PageDto<CompanyDto> pageCompanyByAdmin(Company company,
			CompanyAccount account, Date gmtRegisterStart, Date gmtRegisterEnd,
			String activeFlag, PageDto<CompanyDto> page) {

		if (page.getSort() == null || "activeFlag".equals(page.getSort())) {
			page.setSort("c.regtime");
		}
		if (page.getDir() == null) {
			page.setDir("desc");
		}

		List<Company> list = companyDAO.queryCompanyByAdmin(company, account,
				gmtRegisterStart, gmtRegisterEnd, activeFlag, page);
		if (list == null || list.size() <= 0) {
			page.setRecords(new ArrayList<CompanyDto>());
			page.setTotalRecords(0);
			return page;
		}
		CategoryFacade category = CategoryFacade.getInstance();
		List<CompanyDto> resultList = new ArrayList<CompanyDto>();
		for (Company comp : list) {
			CompanyDto dto = new CompanyDto();
			dto.setMembershipLabel(category.getValue(comp.getMembershipCode()));
			dto.setRegfromLabel(CategoryFacade.getInstance().getValue(
					comp.getRegfromCode()));
			dto.setCompany(comp);
			dto.setId(comp.getId());
			CompanyAccount ca = companyAccountDao
					.queryAdminAccountByCompanyId(comp.getId());
			if (ca == null) {
				dto.setAccount(new CompanyAccount());
			} else {
				dto.setAccount(ca);
			}
			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(companyDAO.queryCompanyByAdminCount(company,
				account, gmtRegisterStart, gmtRegisterEnd, activeFlag));
		return page;
	}

	@Override
	public Integer updateIsBlock(Integer companyId, String isBlock) {
		Assert.notNull(companyId, "the companyId can not be null");
		return companyDAO.updateIsBlock(companyId, isBlock);
	}

	@Override
	public String queryCompanyNameById(Integer companyId) {
		return companyDAO.queryCompanyNameById(companyId);
	}

	@Override
	public void assignActiveFlag(String[] activeFlag, Integer companyId) {
		companyDAO.deleteActiveFlag(companyId);
		companyDAO.createActiveFlag(activeFlag, companyId);
	}

	@Override
	public void reAssignActiveFlag(String activeFlag, String[] activeFlagCode,
			Integer companyId) {
		companyDAO.deleteActiveFlag(companyId);
		companyDAO.createActiveFlag(activeFlagCode, companyId);

		companyDAO.updateActive(activeFlag, companyId);
	}

	@Override
	public List<CompanyDto> exportCompanyByAdmin(Company company,
			CompanyAccount account, Date gmtRegisterStart, Date gmtRegisterEnd,
			String activeFlag) {
		PageDto<CompanyDto> page = new PageDto<CompanyDto>(1000);

		List<Company> list = companyDAO.queryCompanyByAdmin(company, account,
				gmtRegisterStart, gmtRegisterEnd, activeFlag, page);
		// List<CompanyAccount>
		// list=companyAccountService.queryAccountByAdmin(account, page);
		if (list == null || list.size() <= 0) {
			return new ArrayList<CompanyDto>();
		}
		CategoryFacade category = CategoryFacade.getInstance();
		List<CompanyDto> resultList = new ArrayList<CompanyDto>();
		for (Company comp : list) {
			CompanyDto dto = new CompanyDto();
			dto.setMembershipLabel(category.getValue(comp.getMembershipCode()));
			dto.setRegfromLabel(CategoryFacade.getInstance().getValue(
					comp.getRegfromCode()));
			dto.setCompany(comp);
			dto.setId(comp.getId());
			dto.setAccount(companyAccountService.queryAccountByCompanyId(comp
					.getId()));
			resultList.add(dto);
		}
		return resultList;
	}

	@Override
	public List<Company> queryCompanyZstMember(Integer maxSize,
			String productsTypeCode) {

		if (maxSize == null) {
			maxSize = 20;
		}
		if (maxSize.intValue() <= 0) {
			return new ArrayList<Company>();
		}

		return companyDAO.queryCompanyZstMember(maxSize, productsTypeCode);
	}

	@Override
	public List<CompanyDto> queryCompanyZstMemberByAreacode(
			String industryCode, String areaCode, String keywords,
			PageDto<ProductsDto> page) {
		return companyDAO.queryCompanyZstMemberByAreacode(industryCode,
				areaCode, keywords, page);
	}

	@Override
	public PageDto<CompanyDto> queryCompanyVip(Company company,
			PageDto<CompanyDto> page, String areaCode, String keywords) {
		page.setRecords(companyDAO.queryCompanyVip(company, page, areaCode,
				keywords));
		page.setTotalRecords(companyDAO.countQueryCompanyVip(company, page,
				areaCode, keywords));
		return page;
	}

	@Override
	public List<ProductsDto> queryVipNoSame(Integer size, ProductsDO products) {
		List<ProductsDto> list = new ArrayList<ProductsDto>();
		if (products == null) {
			products = new ProductsDO();
		}
		List<Company> clist = queryCompanyZstMember(size,
				products.getProductsTypeCode());
		for (Company obj : clist) {
			ProductsDto dto = new ProductsDto();
			dto.setCompany(obj);
			ProductsDO product = productsService.queryProductsByCidForLatest(
					obj.getId(), products);
			if (product != null) {
				dto.setProducts(product);
				List<ProductsPicDO> plist = productsPicService
						.queryProductPicInfoByProductsId(product.getId());
				if (plist != null && plist.size() > 0) {
					dto.setCoverPicUrl(plist.get(0).getPicAddress());
				}
			}
			list.add(dto);
		}
		return list;
	}

	@Override
	public Integer countQueryCompanyZstMemberByAreacode(String industryCode,
			String areaCode, String keywords) {
		// 主营金属
		if ("1000".equals(industryCode)) {
			industryCode = "10001001";
		}
		// 主营塑料
		if ("1001".equals(industryCode)) {
			industryCode = "10001000";
		}
		// 主营综合
		if ("1002".equals(industryCode)) {
			industryCode = "zh";
		}
		return companyDAO.countQueryCompanyZstMemberByAreacode(industryCode,
				areaCode, keywords);
	}

	@Override
	public List<Company> queryZstMemberByIndustryCode(String industryCode,
			Integer size) {
		if (StringUtils.isEmpty(industryCode)) {
			industryCode = "10001001";
		}
		return companyDAO.queryZstMemberByIndustryCode(industryCode, size);
	}

	@Override
	public PageDto<CompanyDto> queryBlackListForAdmin(Company company,
			CompanyAccount companyAccount, String reason, String crmCode,
			PageDto<CompanyDto> page) {
		// 默认 注册时间 倒序
		page.setSort("c.gmt_created");
		page.setDir("desc");
		List<Company> list = companyDAO.queryBlackList(company, companyAccount,
				reason, crmCode, page);
		List<CompanyDto> dtoList = new ArrayList<CompanyDto>();
		for (Company obj : list) {
			CompanyDto dto = new CompanyDto();
			CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(obj
					.getId());
			if (ca == null) {
				ca = new CompanyAccount();
			}
			dto.setAccount(ca);
			dto.setCompany(obj);
			dtoList.add(dto);
		}
		page.setRecords(dtoList);
		page.setTotalRecords(companyDAO.queryCountBlackList(company,
				companyAccount, reason, crmCode));
		return page;
	}

	@Override
	public Integer updateRegFromCode(Integer id, String code) {
		if (id == null || id < 1) {
			return 0;
		}
		if (StringUtils.isEmpty(code)) {
			return 0;
		}
		return companyDAO.updateRegFromCode(id, code);
	}

	// 根据手机号获得地址与地区
	@Override
	public String getMobileLocation(String tel) {

		Pattern pattern = Pattern.compile("1\\d{10}");
		if (StringUtils.isNotEmpty(tel) && StringUtils.isNumber(tel)) {
			Matcher matcher = pattern.matcher(tel);
			if (matcher.matches()) {
				tel = tel.substring(0, 7);
				String sql = "SELECT province,city FROM  `mobile_number` where numb ='"
						+ tel + "' limit 1";
				final Map<String, String> resultMap = new HashMap<String, String>();
				DBUtils.select("zzother", sql, new IReadDataHandler() {
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while (rs.next()) {
							if (StringUtils.isNotEmpty(rs.getString(1))) {
								resultMap.put("province", rs.getString(1));
							}
							if (StringUtils.isNotEmpty(rs.getString(2))) {
								resultMap.put("city", rs.getString(2));
							}
						}
					}
				});
				CategoryDO cd = new CategoryDO();
				if (resultMap.get("city") != null) {
					cd = categoryDAO
							.queryCategoryBylabel(resultMap.get("city"));
				}
				if (cd == null && resultMap.get("province") != null) {
					cd = categoryDAO.queryCategoryBylabel(resultMap
							.get("province"));
				}
				if (cd != null && StringUtils.isNotEmpty(cd.getCode())) {
					return cd.getCode();
				}
				return "";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	@Override
	public String validateCompanyInfo(SsoUser user) {

		Company company = companyDAO.queryCompanyById(user.getCompanyId());
		// 搜索邮箱
		CompanyAccount account = companyAccountDao.queryAccountByAccount(user
				.getAccount());

		String checkInfo = null;
		do {
			if (company == null) {
				checkInfo = "1";
				break;
			}
			// 检索邮箱
			if (StringUtils.isEmpty(account.getEmail())) {
				checkInfo = "1";
				break;
			}
			// 公司名
			if (StringUtils.isEmpty(company.getName())) {
				checkInfo = "1";
				break;
			}
			// 地址
			if (StringUtils.isEmpty(company.getAddress())) {
				checkInfo = "1";
				break;
			}
			// 国家地区
			if (StringUtils.isEmpty(company.getAreaCode())) {
				checkInfo = "1";
				break;
			}
			// 主营行业
			if (StringUtils.isEmpty(company.getIndustryCode())) {
				checkInfo = "1";
				break;
			}
			// 公司类型
			if (StringUtils.isEmpty(company.getServiceCode())) {
				checkInfo = "1";
				break;
			}
			// 主营业务
			if (StringUtils.isEmpty(company.getBusiness())) {
				checkInfo = "1";
				break;
			}
			// 公司简介
			if (StringUtils.isEmpty(company.getIntroduction())) {
				checkInfo = "1";
				break;
			}

		} while (false);

		return checkInfo;
	}

	@Override
	public void countCompanyInfo(Integer companyId, Map<String, Object> out) {
		Company company = companyDAO.queryCompanyById(companyId);
		CompanyAccount companyAccount = companyAccountDao
				.queryAccountByCompanyId(companyId);
		Integer i = 0;
		// 联系人：未填写 10
		if (StringUtils.isNotEmpty(companyAccount.getContact())) {
			i = i + 10;
		}
		// 固定电话：未填写 5
		if (StringUtils.isNotEmpty(companyAccount.getTel())) {
			i = i + 5;
		}
		// 传真：未填写 5
		if (StringUtils.isNotEmpty(companyAccount.getFax())) {
			i = i + 5;
		}
		// 手机：未填写 10
		if (StringUtils.isNotEmpty(companyAccount.getMobile())) {
			i = i + 10;
		}
		// 公司名称：未填写 10
		if (StringUtils.isNotEmpty(company.getName())) {
			i = i + 10;
		}
		// 主营行业：未填写 5
		if (StringUtils.isNotEmpty(company.getIndustryCode())) {
			i = i + 5;
		}
		// 公司类型：未选择 5
		if (StringUtils.isNotEmpty(company.getServiceCode())) {
			i = i + 5;
		}
		// 国家/地区：未选择 5
		if (StringUtils.isNotEmpty(company.getAreaCode())) {
			i = i + 5;
		}
		// 地址：未填写 5
		if (StringUtils.isNotEmpty(company.getAddress())) {
			i = i + 5;
		}
		// 邮编：未填写 5
		if (StringUtils.isNotEmpty(company.getAddressZip())) {
			i = i + 5;
		}
		// QQ号码：未填写 5
		if (StringUtils.isNotEmpty(companyAccount.getQq())) {
			i = i + 5;
		}
		// 公司简介：未填写 5
		if (StringUtils.isNotEmpty(company.getIntroduction())) {
			i = i + 5;
		}
		// 主营业务：未填写 5
		if (StringUtils.isNotEmpty(company.getBusiness())) {
			i = i + 5;
		}
		// 企业图片：未上传 10
		List<CompanyUploadFileDO> picList = companyUploadFileService
				.queryByCompanyId(companyId);
		if (picList.size() > 0) {
			out.put("haveCompPic", 1);
			i = i + 10;
		}
		// 供求信息：未发布 10
		Integer countProducts = productsService
				.countProductsByCompanyId(companyId);
		if (countProducts > 0) {
			out.put("havePro", 1);
			i = i + 10;
		}
		out.put("countProducts", countProducts);
		out.put("countInfo", i);
		// 互助信息
		bbsService.countBbsInfo(out, companyAccount.getAccount(), companyId);
		// 通过的帖子和问答
		Integer m = bbsPostService.countMyBbs(companyAccount.getAccount(),
				null, "0", 2);
		Integer n = bbsPostService.countMyBbs(companyAccount.getAccount(),
				null, "0", 3);
		out.put("passPost", m + n);

		// 通过的问答
		out.put("passQA", bbsPostService.countMyBbs(
				companyAccount.getAccount(), null, "0", 1));
		// 回复||回答的数量
		bbsService.countBeBbsInfo(out, companyAccount.getAccount());
		// 供求信息
		out.put("productsInfo", productsService.countProductsOfCompanyByStatus(
				companyId, companyAccount.getAccount(), null, null));
		// 询盘信息
		out.put("inquiryInfo",
				inquiryService.countUnviewedInquiry(null,
						companyAccount.getAccount(), companyId));

	}

	@Override
	public PageDto<CompanyDto> companyBySearchEngine(Company companyDo,
			String keyword, PageDto<CompanyDto> page) {
		if (page.getPageSize() == null) {
			page.setPageSize(20);
		}
		// 限制最大页数
		if (page.getStartIndex() != null
				&& page.getStartIndex() >= TOTAL_RECORDS - page.getPageSize()) {
			page.setStartIndex(TOTAL_RECORDS - page.getPageSize());
		}
		Map<Integer, Set<Integer>> maps = new HashMap<Integer, Set<Integer>>();
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		List<Company> list = new ArrayList<Company>();
		List<CompanyDto> list2 = new ArrayList<CompanyDto>();
		try {

			sb.append("@(name) ").append(keyword);
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(0, 100000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED,
					"membership_code desc");

			SphinxResult res = cl.Query(sb.toString(), "company");

			if (res == null) {
				page.setTotalRecords(0);
			} else {
				Integer j = 0;
				// 判断搜索条件中是否有拉黑 isblock 1：拉黑 0:未拉黑
				if (companyDo != null
						&& StringUtils.isNotEmpty(companyDo.getIsBlock())) {
					for (int i = 0; i < res.matches.length; i++) {
						SphinxMatch info = res.matches[i];
						Company company = querySimpleCompanyById(Integer
								.valueOf("" + info.docId));
						if (company != null
								&& company.getId() != null
								&& company.getIsBlock().equals(
										companyDo.getIsBlock())) {
							Set<Integer> idSet = maps.get(company.getId());
							if (idSet == null) {
								Set<Integer> set = new HashSet<Integer>();
								set.add(company.getId());
								maps.put(company.getId(), set);
								list.add(company);
								j++;
							}
						}
					}
					Integer size = 0;
					if (page.getStartIndex() == null) {
						page.setStartIndex(0);
					}
					for (Integer i = page.getStartIndex(); i < list.size(); i++) {
						Company c = list.get(i);
						if (c != null & c.getId() != null) {
							CompanyDto dto = new CompanyDto();
							CompanyAccount companyAccount = companyAccountDao
									.queryAccountByCompanyId(c.getId());
							Integer num = productsDAO
									.countProductsByCompanyId(c.getId());
							dto.setCompany(c);
							dto.setAccount(companyAccount);
							dto.setNumProducts(num);
							list2.add(dto);
							size = size + 1;
						}
						if (size == 20) {
							break;
						}
					}
				} else {
					for (int i = 0; i < res.matches.length; i++) {
						SphinxMatch info = res.matches[i];
						Company company = querySimpleCompanyById(Integer
								.valueOf("" + info.docId));
						if (company != null && company.getId() != null) {
							Set<Integer> idSet = maps.get(company.getId());
							if (idSet == null) {
								Set<Integer> set = new HashSet<Integer>();
								set.add(company.getId());
								maps.put(company.getId(), set);
								list.add(company);
								j++;
							}
						}
					}
					Integer size = 0;
					if (page.getStartIndex() == null) {
						page.setStartIndex(0);
					}

					for (Integer i = page.getStartIndex(); i < list.size(); i++) {
						Company c = list.get(i);
						if (c != null & c.getId() != null) {
							CompanyDto dto = new CompanyDto();
							CompanyAccount companyAccount = companyAccountDao
									.queryAccountByCompanyId(c.getId());
							Integer num = productsDAO
									.countProductsByCompanyId(c.getId());
							dto.setCompany(c);
							dto.setAccount(companyAccount);
							dto.setNumProducts(num);
							list2.add(dto);
							size = size + 1;
						}
						if (size == 20) {
							break;
						}
					}
				}

				page.setRecords(list2);
				page.setTotalRecords(j);
			}
		} catch (SphinxException e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public PageDto<YuanliaoDto> pageYuanliaoByCompanyId(
			PageDto<YuanliaoDto> page, Integer companyId,
			String yuanliaoTypeCode) {
		// 塑料原料列表分页
		List<YuanliaoDto> listResult = new ArrayList<YuanliaoDto>();
		Yuanliao yuanliao = new Yuanliao();
		yuanliao.setCompanyId(companyId);
		yuanliao.setIsDel(0);
		yuanliao.setIsPause(0);
		yuanliao.setCheckStatus(1);
		YuanLiaoSearch search = new YuanLiaoSearch();
		if ("10331000".equals(yuanliaoTypeCode)
				|| "10331001".equals(yuanliaoTypeCode)) {
			search.setYuanliaoTypeCode(yuanliaoTypeCode);
		} else {
			search.setKeyword(yuanliaoTypeCode);
			;
		}

		List<Yuanliao> list = yuanliaoDao.queryYuanliaoList(yuanliao, page,
				search);
		for (Yuanliao yl : list) {
			YuanliaoDto dto = new YuanliaoDto();
			if (StringUtils.isNotEmpty(yl.getDescription())) {
				yl.setDescription(Jsoup.clean(yl.getDescription(),
						Whitelist.none()));
			}
			dto.setYuanliao(yl);
			// 厂家产地
			if (StringUtils.isNotEmpty(yl.getCategoryMainDesc())) {
				dto.setCategoryMainLabel(YuanliaoFacade.getInstance().getValue(
						yl.getCategoryMainDesc()));
			} else if (StringUtils.isNotEmpty(yl.getCategoryAssistDesc())) {
				dto.setCategoryMainLabel(yl.getCategoryAssistDesc());
			}
			// 类型
			if (StringUtils.isNotEmpty(yl.getType())) {
				dto.setTypeLabel(CategoryFacade.getInstance().getValue(
						yl.getType()));
			}
			// 图片
			YuanliaoPic pic = new YuanliaoPic();
			pic.setYuanliaoId(yl.getId());
			pic.setIsDel(0);
			pic.setCheckStatus(1);
			List<YuanliaoPic> listPic = yuanliaoPicDao
					.queryYuanliaoPicByYuanliaoId(pic, 1);
			if (listPic.size() > 0) {
				dto.setPicAddress(listPic.get(0).getPicAddress());
			}
			Company company = companyDAO.queryCompanyById(yl.getCompanyId());
			if (company != null) {
				dto.setCompany(company);
			}
			listResult.add(dto);
		}
		page.setRecords(listResult);
		page.setTotalRecords(yuanliaoDao.countYuanliaoList(yuanliao, search));
		// 塑料原料列表记录总数
		return page;
	}

	/**
	 * 公司信息敏感信息过滤
	 * 
	 * @param company
	 * @return
	 */
	private Company checkByAdmin(Company company) {
		// 公司简介
		try {
			Set<String> sensitiveSet = new HashSet<String>();
			boolean updateFlag = false;
			if (SensitiveUtils.validateSensitiveFilter(company
					.getIntroduction())) {
				Map<String, Object> map = SensitiveUtils
						.getSensitiveFilter(company.getIntroduction());
				@SuppressWarnings("unchecked")
				Set<String> s = (Set<String>) map.get("sensitiveSet");
				for (String obj : s) {
					sensitiveSet.add(obj);
				}
				// company.setIntroduction(SensitiveUtils.getSensitiveValue(
				// company.getIntroduction(), "*"));
				updateFlag = true;
			}
			if (SensitiveUtils.validateSensitiveFilter(company.getName())) {
				Map<String, Object> map = SensitiveUtils
						.getSensitiveFilter(company.getName());
				@SuppressWarnings("unchecked")
				Set<String> s = (Set<String>) map.get("sensitiveSet");
				for (String obj : s) {
					sensitiveSet.add(obj);
				}
				// company.setName(SensitiveUtils.getSensitiveValue(
				// company.getName(), "*"));
				updateFlag = true;
			}
			if (SensitiveUtils.validateSensitiveFilter(company.getBusiness())) {
				Map<String, Object> map = SensitiveUtils
						.getSensitiveFilter(company.getBusiness());
				@SuppressWarnings("unchecked")
				Set<String> s = (Set<String>) map.get("sensitiveSet");
				for (String obj : s) {
					sensitiveSet.add(obj);
				}
				// company.setBusiness(SensitiveUtils.getSensitiveValue(
				// company.getBusiness(), "*"));
				updateFlag = true;
			}
			if (SensitiveUtils.validateSensitiveFilter(company.getAddress())) {
				Map<String, Object> map = SensitiveUtils
						.getSensitiveFilter(company.getAddress());
				@SuppressWarnings("unchecked")
				Set<String> s = (Set<String>) map.get("sensitiveSet");
				for (String obj : s) {
					sensitiveSet.add(obj);
				}
				// company.setAddress(SensitiveUtils.getSensitiveValue(company.getAddress(),
				// "*"));
				updateFlag = true;
			}

			// 更新状态表示公司信息存在敏感词
			if (updateFlag) {
				// companyDAO.updateCompanyByAdmin(company);
				// 拉黑
				companyDAO.updateIsBlock(company.getId(), "1");
				// 拉黑原因
				LogOperation log = new LogOperation();
				log.setTargetId(company.getId());
				log.setOperator("admin");
				log.setOperation("black_operation");
				log.setRemark("该公司信息包含敏感词" + sensitiveSet.toString()
						+ ",在黑名单客户库能够查看");
				log.setGmtCreated(new Date());
				log.setGmtModified(new Date());
				logOperationDao.insert(log);
			}
		} catch (Exception e) {
			return company;
		}
		return company;
	}

	@Override
	public PageDto<CompanyDto> queryCompanyDtoByNameAndMobile(String name,
			String mobile) {
		Integer companyId = companyAccountDao.queryCompanyIdByMobile(mobile);
		if (companyId == null) {
			return new PageDto<CompanyDto>();
		}
		Company c = companyDAO.queryCompanyById(companyId);
		CompanyAccount ca = companyAccountDao
				.queryAccountByCompanyId(companyId);
		CompanyDto dto = new CompanyDto();
		dto.setCompany(c);
		dto.setAccount(ca);
		PageDto<CompanyDto> page = new PageDto<CompanyDto>();
		List<CompanyDto> list = new ArrayList<CompanyDto>();
		list.add(dto);
		page.setRecords(list);
		return page;
	}

	@Override
	public List<Company> queryCompanySearch(CompanySearch search) {
		return companyDAO.queryCompanySearch(search);
	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public XmlFile queryXmlFileByCompanyId(Integer CompanyId){
//		XmlFile xmlFile = new XmlFile();
//		MongoDBManager mongoDBManager = new MongoDBManager();
//		//指定集合esite_xml
//		do{
//			DBObject dbObject = mongoDBManager.findByCompanyId("esite_xml",CompanyId);
//			if(dbObject == null){
//				break;
//			}
//			xmlFile.setCompanyId((Integer) dbObject.get("Company"));
//			xmlFile.setFileName(dbObject.get("fileName").toString());
//			xmlFile.setDomainZz91(dbObject.get("domain_zz91").toString());
//			List<String> l = (List<String>) dbObject.get("urlList");
//			xmlFile.setUrlList(l);
//		}while(false);
//		return xmlFile;
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public XmlFile queryXmlFileByCompanyId(Integer CompanyId){
		XmlFile xmlFile = new XmlFile();
		do{
//			DBObject dbObject = XmlFileUtils.findByCompanyId(CompanyId);
			DBObject dbObject = MongoDBUtils.getInstance().find("esite_xml",CompanyId);
			if(dbObject == null){
				break;
			}
			xmlFile.setCompanyId((Integer) dbObject.get("companyId"));
			xmlFile.setFileName(dbObject.get("fileName").toString());
			xmlFile.setDomainZz91(dbObject.get("domain_zz91").toString());
			List<String> l = (List<String>) dbObject.get("urlList");
			xmlFile.setUrlList(l);
		}while(false);
		return xmlFile;
	}
	
//	@Override
//	public void doUpdateXml(Integer companyId,List<String> ulist){
//		MongoDBManager mongoDBManager = new MongoDBManager();
//		mongoDBManager.updateUrlListByCompanyId("esite_xml",companyId,ulist);
//		return ;
//	}
	
	@Override
	public void doUpdateXml(Integer companyId,List<String> ulist){
//		XmlFileUtils.updateUrlListByCompanyId(companyId,ulist);
		MongoDBUtils.getInstance().updateUrlListByCompanyId("esite_xml",companyId,ulist);
		return ;
	}
	
//	@Override
//	public void doInsertXml(Map<String, Object> map){
//		MongoDBManager m = new MongoDBManager();
//		m.insert("esite_xml",map);
//		return ;
//	}
	
	@Override
	public void doInsertXml(Map<String, Object> map){
//		XmlFileUtils.insert(map);
		MongoDBUtils.getInstance().insert("esite_xml",map);
		return ;
	}
	@Override
	public PageDto<CompanyDto> pageYuanliaoBySearchEngine(
			YuanliaoDto yuanliaoDto, PageDto<CompanyDto> page) {
		if (page.getPageSize() == null) {
			page.setPageSize(10);
		}
		if (page.getCurrentPage() == null) {
			page.setCurrentPage(1);
		}
		page.setStartIndex(page.getPageSize() * (page.getCurrentPage() - 1));
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		List<CompanyDto> list = new ArrayList<CompanyDto>();
		try {
			if (yuanliaoDto.getCompany().getIndustryCode() != null) {
				cl.SetFilter("industry_code", Integer.valueOf(yuanliaoDto
						.getCompany().getIndustryCode()), false);
			}
			if (yuanliaoDto.getMembershipCode() != null) {
				cl.SetFilter("membership_code",
						Integer.valueOf(yuanliaoDto.getMembershipCode()), true);
			}
			if (StringUtils.isNotEmpty(yuanliaoDto.getCompany().getAreaCode())
					&& !StringUtils.isContainCNChar(yuanliaoDto.getCompany()
							.getAreaCode())) {
				if (sb.indexOf("@") != -1) {
					sb.append("&");
				}
				sb.append("@(area_name,area_province) ").append(
						CategoryFacade.getInstance().getValue(
								yuanliaoDto.getCompany().getAreaCode()));
			}
			if (StringUtils.isNotEmpty(yuanliaoDto.getCompany().getName())) {
				if (sb.indexOf("@") != -1) {
					sb.append("&");
				}
				if (yuanliaoDto.getCompany().getName().contains("通用塑料")) {
					yuanliaoDto
							.getCompany()
							.setName(
									"(通用塑料)|ABS|GPPS|HDPE|HIPS|LDPE|LLDPE|PP|PVC|(其他通用塑料)");
				} else if (yuanliaoDto.getCompany().getName().contains("工程塑料")) {
					yuanliaoDto
							.getCompany()
							.setName(
									"(工程塑料)|AS|ASA|EPS|EVA|(K胶)|MBS|PA1010|PA11|PA12|PA46|PA6|PA610|PA612|PA66|PA6T|PA9T|PBT|PC|PET|PLA|PMMA|POM|PPE|PPO|TPU|(沙林树脂)|(其他工程塑料)");
				} else if (yuanliaoDto.getCompany().getName().contains("特种塑料")) {
					yuanliaoDto
							.getCompany()
							.setName(
									"(特种塑料)|LCP|PAI|PEEK|PEI|PES|PFAPI|PPS|PSU|PTFE|PVDF|(其他特种塑料)");
				} else if (yuanliaoDto.getCompany().getName().contains("塑料合金")) {
					yuanliaoDto
							.getCompany()
							.setName(
									"(塑料合金)|(PA-ABS)|(PBT-ABS)|(PBT-ASA)|(PC-ABS)|(PC-PBT)|(PC-PET)|(PC-PS)|(PA-PTFE)|(其他塑料合金)");
				}
				if (yuanliaoDto.getCompany().getName().contains("-")) {
					yuanliaoDto.getCompany().setName(
							yuanliaoDto.getCompany().getName()
									.replace("-", "\\/"));
					;
				}
				sb.append(
						"@(name,business,buy_details,sale_details,tags,area_name,area_province) ")
						.append(yuanliaoDto.getCompany().getName());
			}

			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 10000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED,
					"membership_code desc,gmt_start desc");

			SphinxResult rs = cl.Query(sb.toString(), "company");
			if (rs == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(rs.totalFound);
				for (int i = 0; i < rs.matches.length; i++) {
					SphinxMatch info = rs.matches[i];
					CompanyDto dto = queryYuanliaoCompanyById(Integer
							.valueOf("" + info.docId));
					if (dto != null) {
						if (dto.getCompany() != null) {
							if (dto.getCompany().getIntroduction() != null) {
								dto.getCompany().setIntroduction(
										StringUtils.removeHTML(dto.getCompany()
												.getIntroduction()));
							}
							String location = "";
							if (StringUtils.isNotEmpty(dto.getCompany()
									.getAreaCode())) {
								if (dto.getCompany().getAreaCode().length() > 12) {
									location = CategoryFacade.getInstance()
											.getValue(
													dto.getCompany()
															.getAreaCode()
															.substring(0, 12))
											+ " "
											+ CategoryFacade
													.getInstance()
													.getValue(
															dto.getCompany()
																	.getAreaCode()
																	.substring(
																			0,
																			16));
								} else if (dto.getCompany().getAreaCode()
										.length() > 8) {
									location = CategoryFacade.getInstance()
											.getValue(
													dto.getCompany()
															.getAreaCode()
															.substring(0, 8))
											+ " "
											+ CategoryFacade
													.getInstance()
													.getValue(
															dto.getCompany()
																	.getAreaCode()
																	.substring(
																			0,
																			12));
								} else if (dto.getCompany().getAreaCode()
										.length() == 8) {
									location = CategoryFacade.getInstance()
											.getValue(
													dto.getCompany()
															.getAreaCode()
															.substring(0, 8));
								}
							}
							dto.getCompany().setAreaCode(location);
							List<Yuanliao> offerList = yuanliaoDao
									.queryYuanliaoBYCompanyId(dto.getCompany()
											.getId());
							List<YuanliaoDto> yuanliao = new ArrayList<YuanliaoDto>();
							if (offerList.size() > 0) {
								int count = 2;
								if (offerList.size() < 2) {
									count = 1;
								}
								for (int b = 0; b < count; b++) {
									YuanliaoDto dto2 = new YuanliaoDto();
									dto2.setYuanliao(offerList.get(b));
									YuanliaoPic pic = new YuanliaoPic();
									pic.setYuanliaoId(offerList.get(b).getId());
									pic.setIsDel(0);
									pic.setCheckStatus(1);
									List<YuanliaoPic> listPic = yuanliaoPicDao
											.queryYuanliaoPicByYuanliaoId(pic,
													1);
									if (listPic.size() > 0) {
										dto2.setPicAddress(listPic.get(0)
												.getPicAddress());
									}
									yuanliao.add(dto2);
								}
								dto.setList(yuanliao);
							}
						}
					}
					list.add(dto);
				}
			}
			page.setRecords(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	public CompanyDto queryYuanliaoCompanyById(Integer id) {
		CompanyDto dto = new CompanyDto();
		Company company = companyDAO.queryCompanyById(id);
		if (company != null) {
			dto.setCompany(company);
		}
		// 获取公司帐号信息
		dto.setAccount(companyAccountDao.queryAccountByCompanyId(id));
		return dto;
	}

}