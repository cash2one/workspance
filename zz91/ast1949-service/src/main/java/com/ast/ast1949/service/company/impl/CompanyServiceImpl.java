/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009 下午03:39:25
 */
package com.ast.ast1949.service.company.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.ast.ast1949.domain.company.CategoryGardenDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.persist.company.CategoryGardenDAO;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.site.CategoryDAO;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
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

	public Integer registerCompany(Company company) throws Exception{
		Assert.notNull(company, "the object company can not be null");
		
		company.setIsBlock(CompanyDAO.BLOCK_FALSE);
		company.setZstFlag(CompanyDAO.ZST_FLAG_FALSE);
		company.setMembershipCode(CompanyDAO.DEFAULT_MEMBERSHIP);
		company.setClassifiedCode(CompanyDAO.DEFAULT_CLASSIFIED);
		
		if(StringUtils.isEmpty(company.getRegfromCode())){
			company.setRegfromCode(CompanyDAO.DEFAULT_REGFROM);
		}
		if(StringUtils.isEmpty(company.getIndustryCode())){
			company.setIndustryCode("");
		}
		if(StringUtils.isEmpty(company.getBusiness())){
			company.setBusiness("");
		}
		if(StringUtils.isEmpty(company.getName())){
			company.setName("");
		}
		if(StringUtils.isEmpty(company.getAreaCode())){
			// 默认中国
			company.setAreaCode("10011000");
		}
		return companyDAO.insertCompany(company);
	}
	
	public Company queryCompanyById(Integer id){
		Assert.notNull(id, "the id can not be null");
		return companyDAO.queryCompanyById(id);
	}
	
	public Company querySimpleCompanyById(Integer id){
		Assert.notNull(id, "the id can not be null");
		return companyDAO.querySimpleCompanyById(id);
	}
	
	public CompanyDto queryCompanyDetailById(Integer id){
		Assert.notNull(id, "the id can not be null");
		Company company=companyDAO.queryCompanyById(id);
		if(company==null){
			return null;
		}
		CompanyDto dto=fillDetail(company);
		if(company.getCategoryGardenId()!=null && company.getCategoryGardenId().intValue()>0){
			CategoryGardenDO garden=categoryGardenDAO.queryCategoryGardenById(company.getCategoryGardenId());
			dto.setCategoryGardenName(garden.getName());
			dto.setCategoryGardenShorterName(garden.getShorterName());
		}
		// 获取公司帐号信息
		dto.setAccount(companyAccountDao.queryAccountByCompanyId(id));
		return dto;
	}
	
	public CompanyDto querySimpleCompanyDetailById(Integer id){
		Assert.notNull(id, "the id can not be null");
		Company company=companyDAO.querySimpleCompanyById(id);
		if(company==null){
			return null;
		}
		CompanyDto dto=fillDetail(company);
		if(company.getCategoryGardenId()!=null && company.getCategoryGardenId().intValue()>0){
			CategoryGardenDO garden=categoryGardenDAO.queryCategoryGardenById(company.getCategoryGardenId());
			dto.setCategoryGardenName(garden.getName());
			dto.setCategoryGardenShorterName(garden.getShorterName());
		}
		return dto;
	}
	
	public String queryMembershipOfCompany(Integer id){
		Assert.notNull(id, "the id can not be null");
		return companyDAO.queryMembershipOfCompany(id);
	}
	
	public String queryAreaCodeOfCompany(Integer id){
		Assert.notNull(id, "the id can not be null");
		return companyDAO.queryAreaCodeOfCompany(id);
	}
	
	private CompanyDto fillDetail(Company company){
		CompanyDto dto=new CompanyDto();
		dto.setCompany(company);
		
		dto.setRegfromLabel(CategoryFacade.getInstance().getValue(company.getRegfromCode()));
		dto.setIndustryLabel(CategoryFacade.getInstance().getValue(company.getIndustryCode()));
		dto.setServiceLabel(CategoryFacade.getInstance().getValue(company.getServiceCode()));
		dto.setMembershipLabel(CategoryFacade.getInstance().getValue(company.getMembershipCode()));
		
		//地区相关
		if(StringUtils.isNotEmpty(company.getAreaCode())){
			dto.setAreaLabel(CategoryFacade.getInstance().getValue(company.getAreaCode()));
			if(company.getAreaCode().length()>=8){
				dto.setCountry(CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0,8)));
			}
			if(company.getAreaCode().length()>=12){
				dto.setProvince(CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0,12)));
			}
			if(company.getAreaCode().length()>=16){
				dto.setCity(CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0,16)));
			}
		}
		
		return dto;
	}
	
	public Integer updateIntroduction(Integer id, String intro){
		Assert.notNull(id, "the id can not be null");
		
		return companyDAO.updateIntroduction(id, intro);
	}
	
	public List<Company> queryGoodCompany(Integer maxSize){
		if(maxSize==null){
			maxSize=10;
		}
		if(maxSize.intValue()<=0){
			return new ArrayList<Company>();
		}
		
		
		return companyDAO.queryCompanyByClassifiedCode("10101003",maxSize);
	}
	
	public List<CompanyDto> queryCompanyByArea(String areaCode,Integer maxSize){
		
		if(maxSize==null){
			maxSize=10;
		}
		if(maxSize.intValue()<=0){
			return new ArrayList<CompanyDto>();
		}
		List<Company> list=companyDAO.queryCompanyByArea(areaCode, maxSize);
		List<CompanyDto> resultList=new ArrayList<CompanyDto>();
		for(Company company:list){
			resultList.add(fillDetail(company));
		}
		
		return resultList;
	}
	
	public Integer updateCompanyByUser(Company company){
		Assert.notNull(company, "the object company can not be null" );
		Assert.notNull(company.getId(), "the company id can not be null");
		int id = company.getId();
		CompanyAccount companyAccount = companyAccountService.queryAccountByCompanyId(id);
		String tel = companyAccount.getMobile();
		if (StringUtils.isEmpty(company.getAreaCode())) {
		    try {
                company.setAreaCode(getMobileLocation(tel));
            } catch (Exception e) {
            }
		}
		if (StringUtils.isEmpty(company.getAddress())) {
		    String address = "";
            String code = company.getAreaCode();
            if(code.length()>=8){
                address +=CategoryFacade.getInstance().getValue(code.substring(0,8));
            }
            if(code.length()>=12){
                address +=CategoryFacade.getInstance().getValue(code.substring(0,12));
            }
            if(code.length()>=16){
                address +=CategoryFacade.getInstance().getValue(code.substring(0,16));
            }
            try {
                company.setAddress(address);
            } catch (Exception e) {
            }
        }
		return companyDAO.updateCompanyByUser(company);
	}
	
	public Integer updateCompanyByAdmin(Company company){
		Assert.notNull(company, "the object company can not be null" );
		Assert.notNull(company.getId(), "the company id can not be null");
		return companyDAO.updateCompanyByAdmin(company);
	}
	
	public PageDto<CompanyDto> pageCompanyBySearch(Company company, PageDto<CompanyDto> page){
		Assert.notNull(company, "the object company can not be null");
		List<Company> list=companyDAO.queryCompanyBySearch(company, page);
		if(list==null || list.size()<=0){
			page.setRecords(new ArrayList<CompanyDto>());
			page.setTotalRecords(0);
			return page;
		}
		
		List<CompanyDto> resultList=new ArrayList<CompanyDto>();
		for(Company c:list){
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
	public CompanyDto queryCompanyByAccountEmail(String email,Boolean isVip) {
		if(isVip!=null&&isVip!=true){
			isVip = null;
		}
		return companyDAO.queryCompanyByAccountEmail(email,isVip);
	}

	@Override
	public List<Company> queryCompanyByLoginNum(Integer max) {
		if(max!=null){
			max=5;
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
		if(page.getPageSize()==null){
			page.setPageSize(15);
		}
		if(page.getStartIndex()!=null && page.getStartIndex()>=7500){
			page.setStartIndex(7500);
		}
		StringBuffer sb=new StringBuffer();
		SphinxClient cl=SearchEngineUtils.getInstance().getClient();
		
		List<CompanyDto> list=new ArrayList<CompanyDto>();
		try {
			if(company.getCategoryGardenId()!=null){
				cl.SetFilter("category_garden_id", company.getCategoryGardenId(), false);
			}
			if(StringUtils.isNotEmpty(company.getAreaCode())){
				if(sb.indexOf("@")!=-1){
					sb.append("&");
				}
				sb.append("@(area_name,area_province) ").append(CategoryFacade.getInstance().getValue(company.getAreaCode()));
			}
			if(StringUtils.isNotEmpty(company.getIndustryCode())){
				cl.SetFilter("industry_code", Integer.valueOf(company.getIndustryCode()), false);
			}
			if(StringUtils.isNotEmpty(company.getName())){
				if(sb.indexOf("@")!=-1){
					sb.append("&");
				}
				sb.append("@(name,business,buy_details,sale_details,tags) ").append(company.getName());
			}

			cl.SetMatchMode (SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "gmt_start desc");
			
			SphinxResult res=cl.Query(sb.toString(), "company");
			
			if(res==null){
				page.setTotalRecords(0);
			}else{
				page.setTotalRecords(res.totalFound);
				for ( int i=0; i<res.matches.length; i++ ){
					SphinxMatch info = res.matches[i];
					CompanyDto dto=queryCompanyDetailById(Integer.valueOf(""+info.docId));
					if(dto!=null){
						if(dto.getCompany().getIntroduction()!=null){
							dto.getCompany().setIntroduction(com.zz91.util.lang.StringUtils.removeHTML(dto.getCompany().getIntroduction()));
						}
						PageDto<ProductsDO> proPage = new PageDto<ProductsDO>();
						List<ProductsDO> offerList = productsService.queryProductsByTyepOfCompany(dto.getCompany().getId().toString(), ProductsService.PRODUCTS_TYPE_OFFER, proPage);
						if(offerList!=null&&offerList.size()>0){
							dto.setOfferPro(offerList.get(0));
						}
						
						List<ProductsDO> buyList = productsService.queryProductsByTyepOfCompany(dto.getCompany().getId().toString(), ProductsService.PRODUCTS_TYPE_BUY, proPage);
						if(buyList!=null&&buyList.size()>0){
							dto.setBuyPro(buyList.get(0));
						}
						
						Phone phone = phoneService.queryByCompanyId(Integer.valueOf(""+info.docId));
						if(phone!=null){
							dto.setIsLDB(true);
						}
						
						list.add(dto);
					}
				}
			}
			
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}
		//id,com_id,com_name,com_cx,com_intro,com_viptype,com_province,com_keywords,province_code,city_code,garden_code,com_subname,com_productslist_en,sales_buy
		return page;
	}

	@Override
	public String queryDetails(Integer cid) {
		if(cid==null || cid.intValue()==0){
			return "";
		}
		return companyDAO.queryDetails(cid);
	}

	@Override
	public List<Company> queryRecentZst(Integer size) {
		if(size==null){
			size=100;
		}
		if(size.intValue()>200){
			size=200;
		}
		Date start=DateUtil.getDateAfterMonths(new Date(), -3);
		return companyDAO.queryRecentZst(start, size);
	}
	
	@Override
	public boolean validateDomainZz91(Integer companyId, String domainZz91) {
		if(companyId==null || companyId.intValue()==0){
			return false;
		}
		Integer cid=companyDAO.queryCompanyIdByDomainZz91(domainZz91);
		if(cid==null || cid.intValue()==companyId){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean validateIsBlack(Integer companyId){
		do {
			if(companyId==null){
				break;
			}
			int i = companyDAO.queryCountBlackByCompanyId(companyId);
			if(i>0){
				return true;
			}
		} while (false);
		return false;
	}

	@Override
	public Integer updateMembershipCode(String membershipCode, Integer companyId) {
		if(StringUtils.isEmpty(membershipCode)){
			return null;
		}
		return companyDAO.updateMembershipCode(companyId, membershipCode);
	}

	@Override
	public PageDto<Company> pageCompanyByAdmin(Company company,Date gmtRegisterStart,Date gmtRegisterEnd,Integer cid,
			String email,String account,String mobile,PageDto<Company> page) {
		Assert.notNull(company, "the company can not be null");
		Assert.notNull(page, "the page can not be null");
		if(StringUtils.isNotEmpty(email)){
			cid = companyAccountService.queryCompanyIdByEmail(email);
		}
		if(StringUtils.isNotEmpty(account)) {
			cid = companyAccountService.queryCompanyIdByAccount(account);
		}
		if(StringUtils.isNotEmpty(mobile)) {
			cid = companyAccountService.queryComapnyIdByMobile(mobile);
		}
		page.setRecords(companyDAO.queryCompany(company,gmtRegisterStart,gmtRegisterEnd,cid, page));
		page.setTotalRecords(companyDAO.queryCompanyCountByAdmin(company,gmtRegisterStart,gmtRegisterEnd,cid));
		return page;
	}
	
	public PageDto<CompanyDto> pageCompanyByAdmin(Company company, CompanyAccount account,
			Date gmtRegisterStart,Date gmtRegisterEnd, String activeFlag, 
			PageDto<CompanyDto> page){
		
		if(page.getSort()==null){
			page.setSort("c.regtime");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
		
		List<Company> list=companyDAO.queryCompanyByAdmin(company, account, gmtRegisterStart, gmtRegisterEnd, activeFlag, page);
//		List<CompanyAccount> list=companyAccountService.queryAccountByAdmin(account, page);
		if(list==null || list.size()<=0){
			page.setRecords(new ArrayList<CompanyDto>());
			page.setTotalRecords(0);
			return page;
		}
		CategoryFacade category=CategoryFacade.getInstance();
		List<CompanyDto> resultList=new ArrayList<CompanyDto>();
		for(Company comp:list){
			CompanyDto dto=new CompanyDto();
			dto.setMembershipLabel(category.getValue(comp.getMembershipCode()));
			dto.setRegfromLabel(CategoryFacade.getInstance().getValue(comp.getRegfromCode()));
			dto.setCompany(comp);
			dto.setId(comp.getId());
			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(companyDAO.queryCompanyByAdminCount(company, account, gmtRegisterStart, gmtRegisterEnd, activeFlag));
		return page;
	}
	
	@Override
	public Integer updateIsBlock(Integer companyId, String isBlock) {
		Assert.notNull(companyId, "the companyId can not be null");
		return companyDAO.updateIsBlock(companyId,isBlock);
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
		
		List<Company> list=companyDAO.queryCompanyByAdmin(company, account, gmtRegisterStart, gmtRegisterEnd, activeFlag, page);
//		List<CompanyAccount> list=companyAccountService.queryAccountByAdmin(account, page);
		if(list==null || list.size()<=0){
			return new ArrayList<CompanyDto>();
		}
		CategoryFacade category=CategoryFacade.getInstance();
		List<CompanyDto> resultList=new ArrayList<CompanyDto>();
		for(Company comp:list){
			CompanyDto dto=new CompanyDto();
			dto.setMembershipLabel(category.getValue(comp.getMembershipCode()));
			dto.setRegfromLabel(CategoryFacade.getInstance().getValue(comp.getRegfromCode()));
			dto.setCompany(comp);
			dto.setId(comp.getId());
			dto.setAccount(companyAccountService.queryAccountByCompanyId(comp.getId()));
			resultList.add(dto);
		}
		return resultList;
	}

	@Override
	public List<Company> queryCompanyZstMember(Integer maxSize,String productsTypeCode) {

		if(maxSize==null){
			maxSize=20;
		}
		if(maxSize.intValue()<=0){
			return new ArrayList<Company>();
		}
		
		return companyDAO.queryCompanyZstMember(maxSize,productsTypeCode);
	}
	
	@Override
	public List<CompanyDto> queryCompanyZstMemberByAreacode(String industryCode,String areaCode,String keywords,PageDto<ProductsDto> page) {
		return companyDAO.queryCompanyZstMemberByAreacode(industryCode,areaCode,keywords,page);
	}

	@Override
	public PageDto<CompanyDto> queryCompanyVip(Company company,
			PageDto<CompanyDto> page,String areaCode,String keywords) {
		page.setRecords(companyDAO.queryCompanyVip(company, page,areaCode,keywords));
		page.setTotalRecords(companyDAO.countQueryCompanyVip(company, page,areaCode,keywords));
		return page;
	}

	@Override
	public List<ProductsDto> queryVipNoSame(Integer size,ProductsDO products) {
		List<ProductsDto> list = new ArrayList<ProductsDto>();
		if(products==null){
			products = new ProductsDO();
		}
		List<Company> clist = queryCompanyZstMember(size,products.getProductsTypeCode());
		for(Company obj :clist){
			ProductsDto dto = new ProductsDto();
			dto.setCompany(obj);
			ProductsDO product = productsService.queryProductsByCidForLatest(obj.getId(),products);
			if(product!=null){
				dto.setProducts(product);
				List<ProductsPicDO> plist = productsPicService.queryProductPicInfoByProductsId(product.getId());
				if(plist!=null&&plist.size()>0){
					dto.setCoverPicUrl(plist.get(0).getPicAddress());
				}
			}
			list.add(dto);
		}
		return list;
	}

	@Override
	public Integer countQueryCompanyZstMemberByAreacode(
			String industryCode, String areaCode, String keywords) {
		// 主营金属
		if("1000".equals(industryCode)){
			industryCode = "10001001";
		}
		// 主营塑料
		if("1001".equals(industryCode)){
			industryCode = "10001000";
		}
		// 主营综合
		if("1002".equals(industryCode)){
			industryCode = "zh";
		}
		return companyDAO.countQueryCompanyZstMemberByAreacode(industryCode,areaCode,keywords);
	}

	@Override
	public List<Company> queryZstMemberByIndustryCode(String industryCode,
			Integer size) {
		if(StringUtils.isEmpty(industryCode)){
			industryCode = "10001001";
		}
		return companyDAO.queryZstMemberByIndustryCode(industryCode, size);
	}

	@Override
	public PageDto<CompanyDto> queryBlackListForAdmin(Company company,CompanyAccount companyAccount,String reason,PageDto<CompanyDto> page) {
		// 默认 注册时间 倒序
		page.setSort("c.gmt_created");
		page.setDir("desc");
		List<Company> list = companyDAO.queryBlackList(company,companyAccount,reason, page);
		List<CompanyDto> dtoList = new ArrayList<CompanyDto>();
		for(Company obj :list){
			CompanyDto dto = new CompanyDto();
			CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(obj.getId());
			if(ca ==null){
				ca = new CompanyAccount();
			}
			dto.setAccount(ca);
			dto.setCompany(obj);
			dtoList.add(dto);
		}
		page.setRecords(dtoList);
		page.setTotalRecords(companyDAO.queryCountBlackList(company,companyAccount,reason));
		return page;
	}

	@Override
	public Integer updateRegFromCode(Integer id, String code) {
		if(id==null||id<1){
			return 0;
		}
		if(StringUtils.isEmpty(code)){
			return 0;
		}
		return companyDAO.updateRegFromCode(id,code);
	}
	//根据手机号获得地址与地区
	@Override
	 public String getMobileLocation(String tel) throws Exception{
	     Pattern pattern = Pattern.compile("1\\d{10}");
	     if (StringUtils.isNotEmpty(tel) && StringUtils.isNumber(tel)) {
	         Matcher matcher = pattern.matcher(tel);
	         CategoryDO categoryDO;
	         String code;
	         if(matcher.matches()){
	             String url = "http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi?chgmobile=" + tel;
	             String result = HttpUtils.getInstance().httpGet(url, "GBK");
	             StringReader stringReader = new StringReader(result); 
	             InputSource inputSource = new InputSource(stringReader);
	             DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
	             DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	             Document document = documentBuilder.parse(inputSource);
	             String retmsg = document.getElementsByTagName("retmsg").item(0).getFirstChild().getNodeValue();
	             if(retmsg.equals("OK")){
	                 String city = document.getElementsByTagName("city").item(0).getFirstChild().getNodeValue().trim();
	                 categoryDO = categoryDAO.queryCategoryBylabel(city);
	                 code = categoryDO.getCode();
	                return code;
	             }else {
	                 return "";
	             }
	         }else{
	             return "";
	         }
	     } else {
	         return "";
	     }
     }
}