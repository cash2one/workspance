/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-24
 */
package com.ast.ast1949.service.company.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CategoryCompanyPriceDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyPriceDTO;
import com.ast.ast1949.dto.company.CompanyPriceDtoForMyrc;
import com.ast.ast1949.dto.company.CompanyPriceSearchDTO;
import com.ast.ast1949.persist.company.CategoryCompanyPriceDAO;
import com.ast.ast1949.persist.company.CompanyPriceDAO;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.facade.ParseAreaCode;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.AstConst;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

/**
 * @author yuyonghui
 * 
 */
@Component("companyPriceService")
public class CompanyPriceServiceImpl implements CompanyPriceService {

	@Autowired
	private CompanyPriceDAO companyPriceDAO;
	@Autowired
	private CategoryCompanyPriceDAO categoryCompanyPriceDAO;
	@Resource
	private CompanyService companyService;
	
	public Integer batchDeleteCompanyPriceById(Integer[] entities) {
		Assert.notNull(entities, "entities is not null");
		return companyPriceDAO.batchDeleteCompanyPriceById(entities);
	}

	public Integer insertCompanyPrice(String membershipCode,CompanyPriceDO companyPriceDO) {
		//再次判断单位不能为空
		Assert.notNull(companyPriceDO, "companyPriceDO is not null");
		
//		if (membershipCode==null || AstConst.COMMON_MEMBERSHIP_CODE.equals(membershipCode)) {
//			companyPriceDO.setIsChecked(AstConst.IS_CHECKED_FALSE);
//		} else {
//			companyPriceDO.setIsChecked(AstConst.IS_CHECKED_TRUE);
//		}
//		if(companyPriceDO.getIsChecked()==null) {
		companyPriceDO.setIsChecked("0");
//		}
		companyPriceDO.setPrice(companyPriceDO.getMinPrice());
//		companyPriceDO.setProductId(0);
		if (StringUtils.isEmpty(companyPriceDO.getPriceUnit())) {
			return 0;
		}
		return companyPriceDAO.insertCompanyPrice(companyPriceDO);
	}

	public Integer updateCompanyPrice(CompanyPriceDO companyPriceDO) {
		Assert.notNull(companyPriceDO, "companyPriceDO is not null");
		return companyPriceDAO.updateCompanyPrice(companyPriceDO);
	}

	public CompanyPriceDO queryCompanyPriceById(Integer id) {
		Assert.notNull(id, "id is not null");
		CompanyPriceDO companyPriceDO = companyPriceDAO.queryCompanyPriceById(id);
		return companyPriceDO;
	}

//	public List<CompanyPriceDTO> queryCompanyPriceByCondition(CompanyPriceDTO companyPriceDTO) {
//		Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
//		return companyPriceDAO.queryCompanyPriceByCondition(companyPriceDTO);
//	}

	public List<CompanyPriceDTO> queryCompanyPriceForFront(CompanyPriceDTO companyPriceDTO) {
		Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
		return companyPriceDAO.queryCompanyPriceForFront(companyPriceDTO);
	}

	public Integer queryCompanyPriceRecordCount(CompanyPriceDTO companyPriceDTO) {
		Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
		return companyPriceDAO.queryCompanyPriceRecordCount(companyPriceDTO);
	}

	@Override
	public PageDto<CompanyPriceDTO> queryCompanyPricePagiationList(
			CompanyPriceDTO companyPriceDTO, PageDto<CompanyPriceDTO> pager) {
		Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
		pager=companyPriceDAO.queryCompanyPricePagiationList(companyPriceDTO,pager);
		// 查询省市
		for (Object obj : pager.getRecords()) {
			CompanyPriceDTO cPriceDTO = (CompanyPriceDTO) obj;
			ParseAreaCode parser = new ParseAreaCode();
			parser.parseAreaCode(cPriceDTO.getCompanyPriceDO().getAreaCode());
			cPriceDTO.setProvinceName(parser.getProvince());
		}
		return pager;
	}

	public Integer queryCompanyPriceValidityTimeById(Date refreshTime, Date expiredTime) throws ParseException {
		if(expiredTime==null || refreshTime==null){
			return 20;
		}
		if(expiredTime.equals(DateUtil.getDate(AstConst.MAX_TIMT, AstConst.DATE_FORMATE_WITH_TIME))){
			return -1;
		}else {
			return DateUtil.getIntervalDays(expiredTime, refreshTime);
		}
	}

	public List<CompanyPriceDO> queryCompanyPriceByCompanyIdCount(Integer limitSize) {
		Assert.notNull(limitSize, "limitSize is not null");
		return companyPriceDAO.queryCompanyPriceByCompanyIdCount(limitSize);
	}

//	public Integer queryMyCompanyPriceRecordCount(CompanyPriceDTO companyPriceDTO) {
//		Assert.notNull(companyPriceDTO, "companyPriceDTO is not null");
//		return companyPriceDAO.queryMyCompanyPriceRecordCount(companyPriceDTO);
//	}

	public List<CompanyPriceDO> queryCompanyPriceByCompanyId(Map<String, Object> param) {
		Assert.notNull(param, "param is not null");
		return companyPriceDAO.queryCompanyPriceByCompanyId(param);
	}

	public List<CompanyPriceDO> queryCompanyPriceByRefreshTime(String title, Integer size) {
		Assert.notNull(size, "size is not null");
		return companyPriceDAO.queryCompanyPriceByRefreshTime(title, size);
	}

	public Integer updateCompanyPriceCheckStatus(int[] entities, String isChecked) {
		Assert.notNull(entities, "entities is not null");
		Assert.notNull(isChecked, "isChecked is not null");
		return companyPriceDAO.updateCompanyPriceCheckStatus(entities, isChecked);
	}

	public CompanyPriceDTO selectCompanyPriceById(Integer id) {
		Assert.notNull(id, "id is not null");
		return companyPriceDAO.selectCompanyPriceById(id);
	}

	public CompanyPriceDO queryCompanyPriceByProducts(ProductsDO productsDO, String areaCode, Integer productId) {
		// 产品名称   计量单位  产品价格 最小最大价格    地区  产品描述
		Assert.notNull(productsDO, "productsDO is not null");
		CompanyPriceDO companyPriceDO=new CompanyPriceDO();		
		companyPriceDO.setProductId(productId);		
		companyPriceDO.setAreaCode(areaCode);
		companyPriceDO.setTitle(productsDO.getTitle());
		companyPriceDO.setPrice(productsDO.getPrice());
		companyPriceDO.setPriceUnit(productsDO.getPriceUnit());
		if (productsDO.getMinPrice()!=null) {
			if(productsDO.getMinPrice().longValue()>0){
				companyPriceDO.setMinPrice(String.valueOf(productsDO.getMinPrice()));
			}
		}
        if (productsDO.getMaxPrice()!=null) {
        	if(productsDO.getMaxPrice().longValue()>0){
        		companyPriceDO.setMaxPrice(String.valueOf(productsDO.getMaxPrice()));
        	}
		}
		companyPriceDO.setDetails(productsDO.getDetails());
     //   companyPriceDO.setSource("0");
		return companyPriceDO;
	}

	public Integer insertCompanyPriceByAdmin(CompanyPriceDO companyPrice) {
		Assert.notNull(companyPrice, "the object companyprice can not be null");
		Assert.notNull(companyPrice.getProductId(), "the companyprice.productId can not be null");
		Assert.notNull(companyPrice.getAreaCode(), "the companyprice.areaCode can not be null");
		
		return companyPriceDAO.insertCompanyPriceByAdmin(companyPrice);
	}
	public Integer addProductsToCompanyPrice(CompanyPriceDO companyPriceDO,Company company,ProductsDO productsDO){
	
//		if (company.getMembershipCode() != null) {
//			if (company.getMembershipCode()==null||company.getMembershipCode().equals(AstConst.ZST_MEMBERSHIP_CODE)) {
//				companyPriceDO.setIsChecked(AstConst.IS_CHECKED_FALSE);
//			} else {
//				companyPriceDO.setIsChecked(AstConst.IS_CHECKED_TRUE);
//			}
//		}
		
		String categoryLable = "";
		if(productsDO.getCategoryProductsMainCode()!=null){
			categoryLable = CategoryProductsFacade.getInstance().getValue(productsDO.getCategoryProductsMainCode().substring(4));
		}
		if(StringUtils.isEmpty(categoryLable)){
			companyPriceDO.setCategoryCompanyPriceCode("1003");
		}else{
			CategoryCompanyPriceDO categoryCompanyPrice = categoryCompanyPriceDAO
				.queryCategoryCompanyPriceByLabel(categoryLable);
			if (categoryCompanyPrice != null && categoryCompanyPrice.getCode() != null
					&& categoryCompanyPrice.getCode().length() > 0) {
				companyPriceDO.setCategoryCompanyPriceCode(categoryCompanyPrice.getCode());
			}else{
				companyPriceDO.setCategoryCompanyPriceCode("1003");
			}
		}
		if(companyPriceDO.getIsChecked()==null) {
			companyPriceDO.setIsChecked("0");
		}
		companyPriceDO.setProductId(productsDO.getId()); 
		companyPriceDO.setAreaCode(company.getAreaCode());
		companyPriceDO.setTitle(productsDO.getTitle());
		companyPriceDO.setPrice(productsDO.getPrice());
		companyPriceDO.setExpiredTime(productsDO.getExpireTime());
		companyPriceDO.setRefreshTime(productsDO.getRefreshTime());
		companyPriceDO.setPriceUnit(productsDO.getPriceUnit()+"/"+productsDO.getQuantityUnit());
		if(productsDO.getMinPrice()!=null){
			if(productsDO.getMinPrice().longValue()>0){
				companyPriceDO.setMinPrice(String.valueOf(productsDO.getMinPrice()));	
			}
		}
		if(productsDO.getMaxPrice()!=null){
			if(productsDO.getMaxPrice().longValue()>0){
				companyPriceDO.setMaxPrice(String.valueOf(productsDO.getMaxPrice()));	
			}
		}
		companyPriceDO.setDetails(productsDO.getDetails());

		return companyPriceDAO.insertCompanyPrice(companyPriceDO);
	}
	@Override
	public CompanyPriceDO queryCompanyPriceByProductId(Integer productId) {
		Assert.notNull(productId, "the productId can not be null");
		return companyPriceDAO.queryCompanyPriceByProductId(productId);
	}

	@Override
	public PageDto<CompanyPriceDtoForMyrc> queryCompanyPriceListByCompanyId(Integer companyId,
			PageDto<CompanyPriceDtoForMyrc> page) {
		Assert.notNull(companyId, "the companyId can not be null");
		return companyPriceDAO.queryCompanyPriceListByCompanyId(companyId,
				page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageDto<CompanyPriceSearchDTO> pageCompanyPriceSearch(CompanyPriceSearchDTO dto,PageDto page) {
		List<CompanyPriceSearchDTO> list=companyPriceDAO.queryCompanyPriceSearchByFront(dto, page);
		for(CompanyPriceSearchDTO search:list){
			if(StringUtils.isNotEmpty(search.getAreaCode())){
				search.setAreaName(CategoryFacade.getInstance().getValue(search.getAreaCode()));
				if(search.getAreaCode().length()>=8){
					search.setCountryName(CategoryFacade.getInstance().getValue(search.getAreaCode().substring(0,8)));
				}
				if(search.getAreaCode().length()>=12){
					search.setProvince(CategoryFacade.getInstance().getValue(search.getAreaCode().substring(0,12)));
				}
				if(search.getAreaCode().length()>=16){
					search.setCity(CategoryFacade.getInstance().getValue(search.getAreaCode().substring(0,16)));
				}
			}
		}
		page.setRecords(list);
		page.setTotalRecords(companyPriceDAO.queryCompanypriceCount(dto));
		return page;
	}

	@Override
	public PageDto<CompanyPriceDO> pageCompanyPriceByAdmin(String title,String isChecked,String categoryCode, PageDto<CompanyPriceDO> page) {
		Assert.notNull(page, "the page can not be null");
		page.setRecords(companyPriceDAO.queryCompanyPriceForAdmin(title,isChecked,categoryCode,page));
		page.setTotalRecords(companyPriceDAO.queryCompanyPriceCountForAdmin(title,isChecked,categoryCode));
		return page;
	}

	@Override
	public Integer updateCategory(Integer id, String categoryCode) {
		
		return companyPriceDAO.updateCategoryCode(id,categoryCode);
	}

	@Override
	public Integer updateCompanyPriceByAdmin(CompanyPriceDO companyPriceDO) {
		return companyPriceDAO.updateCompanyPriceByAdmin(companyPriceDO);
	}
	
	@Override
	public List<CompanyPriceDO> queryNewestVipCompPrice(String code,
			Integer size) {
		return companyPriceDAO.queryNewestVipCompPrice(code,size);
	}
	
	@Override
	public List<CompanyPriceSearchDTO> queryCompanyPriceSearchByFront(CompanyPriceSearchDTO dto,PageDto<CompanyPriceSearchDTO> page) {
		List<CompanyPriceSearchDTO> list=companyPriceDAO.queryCompanyPriceSearchByFront(dto, page);
		return list;
	}
	@Override
	public PageDto<CompanyPriceDTO> pageCompanyPriceBySearchEngine(CompanyPriceDTO companyPriceDTO,PageDto<CompanyPriceDTO> page){
		do{
			if(page==null){
				break;
			}
			if(companyPriceDTO==null){
				break;
			}
			CompanyPriceDO companyPriceDO = companyPriceDTO.getCompanyPriceDO();
			if(companyPriceDO==null){
				companyPriceDO = new CompanyPriceDO();
			}
			// 产品类别不为空 转为类别名
			if(StringUtils.isNotEmpty(companyPriceDO.getCategoryCompanyPriceCode())){
				companyPriceDTO.setCategoryName(categoryCompanyPriceDAO.queryByCode(companyPriceDO.getCategoryCompanyPriceCode()).getLabel());
			}
			// 地区类别不为空 转为地区名
			if(StringUtils.isNotEmpty(companyPriceDO.getAreaCode())){
				companyPriceDTO.setAreaName(CategoryFacade.getInstance().getValue(companyPriceDO.getAreaCode()));
			}
			// 产品名不为空
			if(StringUtils.isNotEmpty(companyPriceDTO.getTitle())){
				companyPriceDO.setTitle(companyPriceDTO.getTitle());
			}
			
			// 数量
			if(page.getPageSize()==null){
				page.setPageSize(10);
			}
			List<CompanyPriceDTO> list = new ArrayList<CompanyPriceDTO>();
			StringBuffer sb=new StringBuffer();
			SphinxClient cl = SearchEngineUtils.getInstance().getClient();
			
			// 关键字标题搜索
			if(StringUtils.isNotEmpty(companyPriceDO.getTitle())){
				sb.append("@(title) ").append(companyPriceDO.getTitle());
			}
			// 类别名搜索
			if(StringUtils.isNotEmpty(companyPriceDTO.getCategoryName())){
				sb.append("@(label1,label2,label3) ").append(companyPriceDTO.getCategoryName());
			}
			// 地区名搜索
			if(StringUtils.isNotEmpty(companyPriceDTO.getAreaName())){
				sb.append("@(area1,area2,area3) ").append(companyPriceDTO.getAreaName());
			}

			// 搜索数据id 组装
			try {
				cl.SetMatchMode (SphinxClient.SPH_MATCH_BOOLEAN);
				cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "post_time desc");
				SphinxResult res=cl.Query(sb.toString(), "company_price");
				if(res==null){
					page.setTotalRecords(0);
				}else{
					page.setTotalRecords(res.totalFound);
					for ( int i=0; i<res.matches.length; i++ ){
						SphinxMatch info = res.matches[i];
						CompanyPriceDTO dto = new CompanyPriceDTO();
						CompanyPriceDO obj = companyPriceDAO.queryCompanyPriceById(Integer.valueOf(""+info.docId));
						if(obj!=null){
							dto.setCompanyPriceDO(obj);
							Company company =  companyService.queryCompanyById(obj.getCompanyId());
							if(company!=null&&StringUtils.isNotEmpty(company.getName())){
								dto.setCompanyName(company.getName());
							}
							if(StringUtils.isNotEmpty(obj.getAreaCode())){
								if(obj.getAreaCode().length()>=16){
									dto.setProvinceName(CategoryFacade.getInstance().getValue(obj.getAreaCode().substring(0, 12)));
									dto.setAreaName(CategoryFacade.getInstance().getValue(obj.getAreaCode().substring(0, 16)));
								}
								if(obj.getAreaCode().length()>=12&&obj.getAreaCode().length()<16){
									dto.setProvinceName(CategoryFacade.getInstance().getValue(obj.getAreaCode().substring(0, 12)));
								}
							}
//							CategoryFacade.getInstance()
						}
						
//						ProductsDto dto=productsDAO.queryProductsWithPicAndCompany(Integer.valueOf(""+info.docId));
//						if(dto!=null && 
//								dto.getProducts()!=null && 
//								StringUtils.isNotEmpty(dto.getProducts().getDetails())){
//							dto.getProducts().setDetails(Jsoup.clean(dto.getProducts().getDetails(), Whitelist.none()));
//						}else{
//							dto = new ProductsDto();
//							ProductsDO pdto = new ProductsDO();
//							Map<String, Object> resultMap=SearchEngineUtils.getInstance().resolveResult(res,info);
//							pdto.setId(Integer.valueOf(""+info.docId));
//							pdto.setTitle(resultMap.get("ptitle").toString());
//							dto.setProducts(pdto);
//						}
						list.add(dto);
					}
				}
			} catch (SphinxException e) {
				e.printStackTrace();
			}
			page.setRecords(list);
		}while(false);
		return page;
	}
}
