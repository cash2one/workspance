package com.ast.ast1949.service.spot.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.domain.spot.SpotOrder;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.dataindex.DataIndexDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.products.ProductsSpotDao;
import com.ast.ast1949.service.company.MyfavoriteService;
import com.ast.ast1949.service.spot.SpotOrderService;
import com.ast.ast1949.service.spot.SpotService;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

/**
 * author:kongsj date:2013-3-20
 */
@Component("spotService")
public class SpotServiceImpl implements SpotService {

	final static Integer SPOT_SIZE = 10 ; // 搜索引擎现货供求 条数基数
	
	@Resource
	private DataIndexDao dataIndexDao;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private ProductsSpotDao productsSpotDao; 
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private SpotOrderService spotOrderService;
	@Resource
	private MyfavoriteService myfavoriteService;

	@Override
	public List<Map<String, Object>> getAreaSpotByDataIndex(String code,
			Integer size) {
		List<DataIndexDO> list = dataIndexDao.queryDataIndexByParentCode(code, "%",size);
		List<Map<String, Object>> nlist = new ArrayList<Map<String, Object>>();
		for (DataIndexDO obj : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dataIndex", obj);
			JSONObject js = new JSONObject();
			try {
				js = JSONObject.fromObject(obj.getTitle());
				map.put("json", js);
			} catch (Exception e) {
				js = null;
			}
			nlist.add(map);
		}
		return nlist;
	}
	
	@Override
	public PageDto<ProductsDto> pageSpotBySearchEngine(ProductsDO product, String areaCode,Boolean havePic,PageDto<ProductsDto> page) {
		if(page.getPageSize()==null){
			page.setPageSize(10);
		}
		
		if(page.getStartIndex()!=null && page.getStartIndex()>=7500){
			page.setStartIndex(7500);
		}
		StringBuffer sb=new StringBuffer();
		SphinxClient cl=SearchEngineUtils.getInstance().getClient();
		
		List<ProductsDto> list=new ArrayList<ProductsDto>();
		try {
			if(StringUtils.isNotEmpty(product.getTitle())){
				sb.append("@(title,tags,label0,label1,label2,label3,label4,city,province) ").append(product.getTitle());
			}
			
			if(StringUtils.isNotEmpty(product.getProductsTypeCode())){
				if("10331000".equals(product.getProductsTypeCode())){
					cl.SetFilter("pdt_kind", 1, true);
				}else{
					cl.SetFilter("pdt_kind", 0, true);
				}
			}
			
			// 是否要图片
			if(havePic!=null&&havePic){
				//cl.SetFilter("havepic", 1, true);
				cl.SetFilterRange("havepic", 1, 5000, false);
			}
			
			// 地区
			if(StringUtils.isNotEmpty(areaCode)){
				sb = checkStringBuffer(sb);
				sb.append("@province ").append(areaCode);
			}
			// group by 去重复公司
//			cl.SetGroupBy("company_id", SphinxClient.SPH_GROUPBY_DAY);
			
			// 所使用的服务
			if(StringUtils.isNotEmpty(product.getCrmCompanySvr())){
				cl.SetFilter("crm_service_code", Integer.valueOf(product.getCrmCompanySvr()), true);
			}
			
			cl.SetMatchMode (SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 10000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
			//判断是否高会
//			if(product.getIsVip()){
//				cl.SetFilterRange("viptype", 1, 5, false);
//			}
			SphinxResult res=cl.Query(sb.toString(), "offersearch_xianhuo");
			
			if(res==null){
				page.setTotalRecords(0);
			}else{
				page.setTotalRecords(res.totalFound);
				for ( int i=0; i<res.matches.length; i++ ){
					SphinxMatch info = res.matches[i];
					ProductsDto dto=productsDAO.queryProductsWithPicAndCompany(Integer.valueOf(""+info.docId));
					if(dto!=null&&dto.getProducts()!=null && StringUtils.isNotEmpty(dto.getProducts().getDetails())){
						dto.getProducts().setDetails(Jsoup.clean(dto.getProducts().getDetails(), Whitelist.none()));
					}else{
						dto = new ProductsDto();
						ProductsDO pdto = new ProductsDO();
						Map<String, Object> resultMap=SearchEngineUtils.getInstance().resolveResult(res,info);
						pdto.setId(Integer.valueOf(""+info.docId));
						pdto.setTitle(resultMap.get("ptitle").toString());
						dto.setProducts(pdto);
					}
					dto.setCompany(companyDAO.querySimpleCompanyById(dto.getProducts().getCompanyId()));
					dto.setProductsSpot(productsSpotDao.queryByProductId(Integer.valueOf(""+info.docId)));
					dto.setProducts(productsDAO.queryProductsWithOutDetailsById(Integer.valueOf(""+info.docId)));
					Integer spotId = dto.getProductsSpot()==null?0:dto.getProductsSpot().getId();
					dto.setSpotSales(spotOrderService.countBySpotId(spotId)+myfavoriteService.countByCodeAndContentId(MyfavoriteService.TYPE_CODE_XIANHUO, spotId));
					list.add(dto);
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}
		
		return page;
	}

	private StringBuffer checkStringBuffer(StringBuffer sb){
		if(sb.length()!=0){
			sb.append("&");
		}
		return sb;
	}

	@Override
	public PageDto<ProductsDto> pageSpotBySearchEngineForSimpleCompany(
			ProductsDO product, Boolean havePic, Integer size,
			PageDto<ProductsDto> page) {
		if(page.getPageSize()==null){
			page.setPageSize(10);
		}
		
		if(page.getStartIndex()!=null && page.getStartIndex()>=7500){
			page.setStartIndex(7500);
		}
		StringBuffer sb=new StringBuffer();
		SphinxClient cl=SearchEngineUtils.getInstance().getClient();
		
		List<ProductsDto> list=new ArrayList<ProductsDto>();
		try {
			if(StringUtils.isNotEmpty(product.getTitle())){
				sb.append("@(title,tags,label0,label1,label2,label3,label4,city,province) ").append(product.getTitle());
			}
			
			if(StringUtils.isNotEmpty(product.getProductsTypeCode())){
				if("10331000".equals(product.getProductsTypeCode())){
					cl.SetFilter("pdt_kind", 1, true);
				}else{
					cl.SetFilter("pdt_kind", 0, true);
				}
			}
			
			// 是否要图片
			if(havePic!=null&&havePic){
				cl.SetFilterRange("havepic", 1, 5000, false);
			}
			
			// group by 去重复公司
			cl.SetGroupBy("company_id", SphinxClient.SPH_GROUPBY_DAY);
			
			// 所使用的服务
			if(StringUtils.isNotEmpty(product.getCrmCompanySvr())){
				cl.SetFilter("crm_service_code", Integer.valueOf(product.getCrmCompanySvr()), true);
			}
			
			cl.SetMatchMode (SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 10000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "refresh_time desc");
			//判断是否高会
//			if(product.getIsVip()){
//				cl.SetFilterRange("viptype", 1, 5, false);
//			}
			SphinxResult res=cl.Query(sb.toString(), "offersearch_xianhuo");
			
			if(res==null){
				page.setTotalRecords(0);
			}else{
				page.setTotalRecords(res.totalFound);
				for ( int i=0; i<res.matches.length; i++ ){
					SphinxMatch info = res.matches[i];
					ProductsDto dto=productsDAO.queryProductsWithPicAndCompany(Integer.valueOf(""+info.docId));
					if(dto!=null&&dto.getProducts()!=null && StringUtils.isNotEmpty(dto.getProducts().getDetails())){
						dto.getProducts().setDetails(Jsoup.clean(dto.getProducts().getDetails(), Whitelist.none()));
					}else{
						dto = new ProductsDto();
						ProductsDO pdto = new ProductsDO();
						Map<String, Object> resultMap=SearchEngineUtils.getInstance().resolveResult(res,info);
						pdto.setId(Integer.valueOf(""+info.docId));
						pdto.setTitle(resultMap.get("ptitle").toString());
						dto.setProducts(pdto);
					}
					dto.setCompany(companyDAO.querySimpleCompanyById(dto.getProducts().getCompanyId()));
					dto.setProductsSpot(productsSpotDao.queryByProductId(Integer.valueOf(""+info.docId)));
					dto.setProducts(productsDAO.queryProductsWithOutDetailsById(Integer.valueOf(""+info.docId)));
					Integer spotId = dto.getProductsSpot()==null?0:dto.getProductsSpot().getId();
					dto.setSpotSales(spotOrderService.countBySpotId(spotId)+myfavoriteService.countByCodeAndContentId(MyfavoriteService.TYPE_CODE_XIANHUO, spotId));
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
	public List<ProductsDto> queryLastestOrderSpot(Integer size) {
		List<SpotOrder> list = spotOrderService.queryOrder(size);
		List<ProductsDto> nlist  = new ArrayList<ProductsDto>();
		for(SpotOrder obj :list){
			ProductsSpot productsSpot = productsSpotDao.queryById(obj.getSpotId());
			if(productsSpot!=null){
				ProductsDto dto = productsDAO.queryProductsWithPicAndCompany(productsSpot.getProductId());
				if (dto==null) {
					continue;
				}
				dto.setProductsSpot(productsSpot);
				dto.setSpotSales(spotOrderService.countBySpotId(obj.getSpotId())+myfavoriteService.countByCodeAndContentId(MyfavoriteService.TYPE_CODE_XIANHUO, obj.getSpotId()));
				nlist.add(dto);
			}
		}
		return nlist;
	}
}
