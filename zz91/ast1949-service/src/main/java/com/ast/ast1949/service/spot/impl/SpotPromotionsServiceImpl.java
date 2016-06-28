package com.ast.ast1949.service.spot.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.domain.spot.SpotInfo;
import com.ast.ast1949.domain.spot.SpotPromotions;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotPromotionsDto;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.products.ProductsSpotDao;
import com.ast.ast1949.persist.spot.SpotInfoDao;
import com.ast.ast1949.persist.spot.SpotPromotionsDao;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.spot.SpotPromotionsService;
import com.zz91.util.Assert;
import com.zz91.util.lang.StringUtils;

/**
 * author:kongsj 
 * date:2013-3-7
 */
@Component("spotPromotionsService")
public class SpotPromotionsServiceImpl implements SpotPromotionsService {

	@Resource
	private SpotPromotionsDao spotPromotionsDao;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private SpotInfoDao spotInfoDao;
	@Resource
	private ProductsSpotDao productsSpotDao;
	
	@Override
	public Integer addOnePromotions(SpotPromotions spotPromotions) {
		do {
			SpotPromotions obj = spotPromotionsDao.queryBySpotId(spotPromotions.getSpotId());
			if(obj!=null){
				break;
			}
			ProductsDO pdo = productsDAO.queryProductsWithOutDetailsById(spotPromotions.getProductId());
			if(pdo==null){
				break;
			}
			// 公司id
			spotPromotions.setCompanyId(pdo.getCompanyId());
			// 价格
			if (pdo.getMinPrice() == null) {
				spotPromotions.setPrice(0);
			}else{
				spotPromotions.setPrice(pdo.getMinPrice().intValue());
			}
			
			// 数量
			if (pdo.getQuantity() != null) {
				spotPromotions.setQuantity(StringUtils.isNumber(pdo.getQuantity())?Integer.valueOf(pdo.getQuantity()):0);
			}
			// 价格单位 数量单位
			if (StringUtils.isEmpty(pdo.getPriceUnit())) {
				spotPromotions.setPriceUnit("元");
			} else {
				spotPromotions.setPriceUnit(pdo.getPriceUnit());
			}
			if (StringUtils.isNotEmpty(pdo.getQuantityUnit())) {
				spotPromotions.setQuantityUnit(pdo.getQuantityUnit());
			}else{
				spotPromotions.setQuantityUnit("吨");
			}
			
			// 组装标题
			assemblyTitle(spotPromotions, pdo);
			return spotPromotionsDao.insert(spotPromotions);
		} while (false);
		return 0;
	}

	@Override
	public Integer applyForPromotions(Integer spotId, Integer productId,Integer companyId) {
		do {
			SpotPromotions obj = spotPromotionsDao.queryBySpotId(spotId);
			if(obj!=null){
				break;
			}
			ProductsDO productsDO = productsDAO.queryProductsById(productId);
			if (productsDO == null) {
				break;
			}
			// 标题
			if (StringUtils.isEmpty(productsDO.getTitle())) {
				break;
			}
			SpotPromotions spotPromotions = new SpotPromotions();
			spotPromotions = assemblyTitle(spotPromotions, productsDO);

			// 价格 货币单位
			if (productsDO.getMinPrice() == null) {
				spotPromotions.setPrice(0);
			}else{
				spotPromotions.setPrice(productsDO.getMinPrice().intValue());
			}
			
			spotPromotions.setPrice(productsDO.getMinPrice().intValue());
			if (StringUtils.isEmpty(productsDO.getPriceUnit())) {
				spotPromotions.setPriceUnit("元");
			} else {
				spotPromotions.setPriceUnit(productsDO.getPriceUnit());
			}
			// 数量 数量单位
			if (StringUtils.isNotEmpty(productsDO.getQuantity())) {
				spotPromotions.setQuantity(Integer.valueOf(productsDO.getQuantity()));
			}
			if (StringUtils.isNotEmpty(productsDO.getQuantityUnit())) {
				spotPromotions.setQuantityUnit(productsDO.getQuantityUnit());
			}else{
				spotPromotions.setQuantityUnit("吨");
			}
			// 审核状态
			spotPromotions.setCheckStatus(SpotPromotionsService.CHECK_STATUS_NO_CHECK);
			spotPromotions.setPromotionsPrice(0);
			// time  ibatis
			spotPromotions.setProductId(productId);
			spotPromotions.setSpotId(spotId);
			spotPromotions.setCompanyId(companyId);
			// insert 促销
			int i = addOnePromotions(spotPromotions);
			if(i>0){
				return i;
			}
		} while (false);
		return 0;
	}

	@Override
	public Integer updateStatusById(Integer id,String status){
		Assert.notNull(id, "id must not be null");
		Assert.notNull(status, "status must not be null");
		do{
			// 判断id 与 审核状态是否 为空
			if(id==null){
				break;
			}
			if(StringUtils.isEmpty(status)){
				break;
			}
			SpotPromotions spotPromotions = new SpotPromotions();
			spotPromotions.setId(id);
			spotPromotions.setCheckStatus(status);
			return editOnePromotions(spotPromotions);
		}while(false);
		return 0;
	}
	
	@Override
	public Integer editOnePromotions(SpotPromotions spotPromotions) {
		Assert.notNull(spotPromotions.getId(), "id must not be null");
		return spotPromotionsDao.update(spotPromotions);
	}

	@Override
	public SpotPromotions queryOnePromotions(Integer spotId) {
		Assert.notNull(spotId, "spotId must not be null");
		return spotPromotionsDao.queryBySpotId(spotId);
	}

	@Override
	public PageDto<SpotPromotionsDto> pagePromotions(SpotPromotions spotPromotions, PageDto<SpotPromotionsDto> page) {
		page.setTotalRecords(spotPromotionsDao.queryCountByCondition(spotPromotions));
		List<SpotPromotions> list =  spotPromotionsDao.queryByCondition(spotPromotions, page);
		page.setRecords(coverPromotionDoToDto(list));
		return page;
	}

	@Override
	public List<SpotPromotionsDto> queryPromotionsBySize(Integer size) {
		if(size==null){
			size = 5;
		}
		if(size>50){
			size=50;
		}
		List<SpotPromotions> list = spotPromotionsDao.queryPromotionsBySize(size);
		return coverPromotionDoToDto(list);
	}
	
	@Override
	public List<SpotPromotionsDto> queryExpiredPromotionsBySize(Integer size) {
		List<SpotPromotions> list = spotPromotionsDao.queryExpiredPromotionsBySize(size);
		return  coverPromotionDoToDto(list);
	}
	
	private List<SpotPromotionsDto> coverPromotionDoToDto(List<SpotPromotions> list){
		List<SpotPromotionsDto> nlist = new ArrayList<SpotPromotionsDto>();
		for(SpotPromotions obj:list){
			SpotPromotionsDto dto = new SpotPromotionsDto();
			// 促销信息
			dto.setSpotPromotions(obj);
			// 供求信息
			ProductsDO product = productsDAO.queryProductsById(obj.getProductId());
			if(product!=null){
				dto.setProducts(product);
			}
			// 供求现货信息
			ProductsSpot productsSpot = productsSpotDao.queryByProductId(obj.getProductId());
			if(productsSpot!=null){
				dto.setProductsSpot(productsSpot);
			}
			// 现货详细信息
			SpotInfo spotInfo= spotInfoDao.queryOne(obj.getSpotId());
			if(spotInfo!=null){
				dto.setSpotInfo(spotInfo);
			}
			nlist.add(dto);
		}
		return nlist;
	}

	@Override
	public SpotPromotions queryByIdAndCompanyId(Integer id, Integer companyId) {
		Assert.notNull(id, "id must not be null");
		Assert.notNull(companyId, "companyId must not be null");
		return spotPromotionsDao.queryByIdAndCompanyId(id, companyId);
	}

	private SpotPromotions assemblyTitle(SpotPromotions spotPromotions,ProductsDO productsDO){
		// 标题
		String type = "";
		if (ProductsService.PRODUCTS_TYPE_OFFER.equals(productsDO.getProductsTypeCode())) {
			type = "供应";
		}
		if (ProductsService.PRODUCTS_TYPE_BUY.equals(productsDO.getProductsTypeCode())) {
			type = "求购";
		}
		if (ProductsService.PRODUCTS_TYPE_COOPERATION.equals(productsDO.getProductsTypeCode())) {
			type = "合作";
		}
		spotPromotions.setTitle(type + productsDO.getTitle());
		return spotPromotions;
	}
}
