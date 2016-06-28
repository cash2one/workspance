package com.ast.ast1949.service.market.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.market.Market;
import com.ast.ast1949.domain.market.ProductMarket;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsHide;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.market.MarketDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.market.MarketDao;
import com.ast.ast1949.persist.market.ProductMarketDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.products.ProductsHideDao;
import com.ast.ast1949.persist.products.ProductsPicDAO;
import com.ast.ast1949.persist.sample.SampleDao;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.market.ProductMarketService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

@Component("productMarketService")
public class ProductMarketServiceImpl implements ProductMarketService {
	@Resource
	private ProductMarketDao productMarketDao;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private ProductsHideDao productsHideDao;
	@Resource
	private SampleDao sampleDao;
	@Resource
	private ProductsPicDAO productsPicDAO;
	@Resource
	private MarketDao marketDao;

	@Override
	public PageDto<ProductsDto> pageSearchProducts(PageDto<ProductsDto> page, Integer marketId, String type, String industry, Integer flag,Integer isVip,Integer isYP) {
		if (page.getPageSize() == null || page.getPageSize().intValue() <= 0) {
			page.setPageSize(20);
		}

		if (page.getStartIndex() != null && page.getStartIndex() >= 10000) {
			page.setStartIndex(10000);
		}
		List<ProductsDto> list=new ArrayList<ProductsDto>();
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		if(marketId!=null||flag==1){
			sb.append("@(market_list) ").append(","+marketId+",");
		}
		//所属行业非空
		if(StringUtils.isNotEmpty(type)){
			sb.append(" @(type_code) ").append(type);
		}
		if(StringUtils.isNotEmpty(industry)){
			sb.append(" @(industry,category) ").append(industry);
		}
		
		try {
			// 判断是否高会
			if (isVip!=null&&isVip==1) {
				cl.SetFilterRange("viptype", 1, 5, false);
			}
			// 样品信息
			if (isYP!=null&&isYP==1) {
				cl.SetFilterRange("havesample", 1, 5000, false);
				cl.SetFilter("sampleDel", 0, false);
			}
			cl.SetFilter("is_del", 0, false); //未删除
			cl.SetFilter("is_pause", 0, false);//已发布
			cl.SetFilter("check_status", 1, false);//审核通过
			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			if(flag!=null&&flag==1){
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "viptype desc,zst_year desc,refresh_time desc");
			}else{
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
			}
			SphinxResult res = cl.Query(sb.toString(), "product_market");
			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				if(res.totalFound>10000){
					page.setTotalRecords(10000);
				}
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					ArrayList l = info.attrValues;
					ProductsDO product=productsDAO.queryProductsById(Integer.valueOf(""+l.get(0)));
					ProductsDto dto=new ProductsDto();
					if(product!=null){
						if(StringUtils.isNotEmpty(product.getManufacture())){
							product.setManufacture(CategoryFacade.getInstance().getValue(product.getManufacture()));
						}
						List<ProductsPicDO> plist=productsPicDAO.queryProductPicInfoByProductsId(product.getId());
						if(plist.size()>0){
							dto.setCoverPicUrl(plist.get(0).getPicAddress());
						}
					}
					dto.setProducts(product);
					if(product!=null&&flag!=null&&flag==1){
						Company company=companyDAO.queryCompanyById(product.getCompanyId());
						CompanyAccount account=companyAccountDao.queryAccountByCompanyId(product.getCompanyId());
						if(company!=null){
							if(StringUtils.isNotEmpty(company.getIntroduction())){
								company.setIntroduction(Jsoup.clean(company.getIntroduction(),Whitelist.none()));
							}
							dto.setCompany(company);
						}
						if(account!=null&&StringUtils.isNotEmpty(account.getMobile())){
							dto.setCompanyContacts(account);
						}
					}
					if(dto.getProducts()!=null&&StringUtils.isNotEmpty(dto.getProducts().getDetails())){
						dto.getProducts().setDetails((Jsoup.clean(dto.getProducts().getDetails(),Whitelist.none())));
					}
					list.add(dto);
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public List<ProductsDto> queryProductMarketBySize(Integer size) {
		List<Integer> list=productMarketDao.queryProductMarketBySize(size);
		List<ProductsDto> listR=new ArrayList<ProductsDto>();
		for(Integer li:list){
			ProductsDO product=productsDAO.queryProductsById(li);
			if(product!=null){
				ProductsDto dto=new ProductsDto();
//				product.setId(li);
				dto.setProducts(product);
				dto.setProductsTypeLabel(CategoryFacade.getInstance().getValue(product.getProductsTypeCode()));
				listR.add(dto);
			}
		}
		return listR;
	}

	@Override
	public Integer countProducts() {
		return productMarketDao.countProducts();
	}

	@Override
	public PageDto<ProductsDto> pageSearchProductsByAdmin(PageDto<ProductsDto> page, Integer marketId, String type,String industry, Integer flag, Integer isVip, Integer isYP) {
		if (page.getPageSize() == null || page.getPageSize().intValue() <= 0) {
			page.setPageSize(20);
		}

		if (page.getStartIndex() != null && page.getStartIndex() >= 10000) {
			page.setStartIndex(10000);
		}
		List<ProductsDto> list=new ArrayList<ProductsDto>();
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		if(marketId!=null&&StringUtils.isEmpty(industry)){
			sb.append("@(market_list) ").append(","+marketId+",");
		}
		//所属行业非空
		if(StringUtils.isNotEmpty(type)){
	        sb.append(" @(type_code) ").append(type);
	    }
		if(StringUtils.isNotEmpty(industry)){
			 sb.append(" @(industry,category) ").append(industry);
		}
		
		try {
			// 判断是否高会
			if (isVip!=null&&isVip==1) {
				cl.SetFilterRange("viptype", 1, 5, false);

			}
			// 样品信息
			if (isYP!=null&&isYP==1) {
				cl.SetFilterRange("havesample", 1, 5000, false);
				cl.SetFilter("sampleDel", 0, false);
			}
			cl.SetFilter("is_del", 0, false); //未删除
			cl.SetFilter("is_pause", 0, false);//已发布
			cl.SetFilter("check_status", 1, false);//审核通过
			cl.SetMatchMode(SphinxClient.SPH_SORT_EXTENDED);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			
			if(flag!=null&&flag==1){
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "viptype desc,zst_year desc,refresh_time desc");
			}else{
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
			}
			SphinxResult res = cl.Query(sb.toString(), "product_market");
			
			if (res == null) {
				page.setTotalRecords(0);
			} else {
				page.setTotalRecords(res.totalFound);
				if(res.totalFound>10000){
					page.setTotalRecords(10000);
				}
				CategoryFacade categoryFacade = CategoryFacade.getInstance();
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					ProductsDO products=new ProductsDO();
					products.setId(Integer.valueOf(""+info.docId));
					ProductsDto dto=productsDAO.queryProductsByAdminId(products);
					String status = "";
					if (dto != null) {
						
						do {
						
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
						list.add(dto);
					}
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public PageDto<MarketDto> pageSearchProductsForZhuanTi(PageDto<MarketDto> page, String province, String industry,Integer isVip, Integer isYP,Integer index) {
		List<MarketDto> list=new ArrayList<MarketDto>();
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
//		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
//		List<Integer> listC = new ArrayList<Integer>();
		try {
			if(StringUtils.isNotEmpty(industry)){
				 sb.append(" @(industry,category) ").append(industry);
			}
			if(StringUtils.isNotEmpty(province)){
			      sb.append(" @(province) ").append(province);
			}
			cl.SetFilterRange("havepic", 1, 5000, false);//有供求图片
			cl.SetFilter("is_del", 0, false);//未删除
			cl.SetFilter("is_pause", 0, false);//已发布
			cl.SetFilter("check_status", 1, false);//审核通过
			cl.SetMatchMode(SphinxClient.SPH_SORT_EXTENDED);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 4000);
			cl.SetGroupBy("company_id", SphinxClient.SPH_GROUPBY_ATTR);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "viptype desc,refresh_time desc");
			SphinxResult res = cl.Query(sb.toString(), "product_market");
			if(res != null){
				page.setTotalRecords(res.totalFound);
				for (int i = 0; i < res.matches.length; i++) {
					SphinxMatch info = res.matches[i];
					MarketDto dto =new MarketDto();
					//供求
					ProductsDO product = productsDAO.queryProductsById(Integer.valueOf(""+info.attrValues.get(0)));
					dto.setProducts(product);
					//市场与供求的关系
					ProductMarket pm = productMarketDao.queryProductMarketByProductId(Integer.valueOf(""+info.attrValues.get(0)));
					//市场信息
					Market market = null;
					if(pm!=null){
						market = marketDao.queryMarketById(Integer.valueOf(pm.getMarketList().split(",")[1]));
						dto.setMarket(market);
					}
					//公司信息
					Company	company = companyDAO.queryCompanyById(product.getCompanyId());
					dto.setCompany(company);
					//图片
					String pic =productsPicDAO.queryPicPathByProductId(Integer.valueOf(""+info.attrValues.get(0)));
					if(StringUtils.isNotEmpty(pic)){
						dto.setPicAddress(pic);
					}
					list.add(dto);
				}
			}else{
				LogUtil.getInstance().log("coreseek", "error", "127.0.0.1", "date:"+DateUtil.toString(new Date(), "yyyy-MM-dd hh:mm")+" ; error:"+cl.GetLastError());;
			}
		} catch (SphinxException e) {
		}
//		if(index*8<listC.size()){
//			for(int i=(index-1)*8;i<index*8;i++){
//				
//			}
//		}else{
//			for(int i=(index-1)*8;i<listC.size();i++){
//				MarketDto dto =new MarketDto();
//				//供求
//				ProductsDO product = productsDAO.queryProductsById(map.get(listC.get(i)));
//				dto.setProducts(product);
//				//市场与供求的关系
//				ProductMarket pm = productMarketDao.queryProductMarketByProductId(map.get(listC.get(i)));
//				//市场信息
//				Market market = null;
//				if(pm!=null){
//					market = marketDao.queryMarketById(Integer.valueOf(pm.getMarketList().split(",")[1]));
//					dto.setMarket(market);
//				}
//				//公司信息
//				Company	company = companyDAO.queryCompanyById(listC.get(i));
//				dto.setCompany(company);
//				//图片
//				String pic =productsPicDAO.queryPicPathByProductId(map.get(listC.get(i)));
//				if(StringUtils.isNotEmpty(pic)){
//					dto.setPicAddress(pic);
//				}
//				list.add(dto);
//			}
//		}
		page.setRecords(list);
		return page;
	}

}
