package com.ast.ast1949.service.spot.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.spot.SpotAuction;
import com.ast.ast1949.domain.spot.SpotAuctionLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotAuctionDto;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.spot.SpotAuctionDao;
import com.ast.ast1949.persist.spot.SpotAuctionLogDao;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.spot.SpotAuctionService;
import com.zz91.util.Assert;
import com.zz91.util.lang.StringUtils;

/**
 * author:kongsj date:2013-3-12
 */
@Component("spotAuctionService")
public class SpotAuctionServiceImpl implements SpotAuctionService {

	@Resource
	private SpotAuctionDao spotAuctionDao;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private SpotAuctionLogDao spotAuctionLogDao;

	@Override
	public Integer applyForAuction(Integer spotId, Integer productId,Integer companyId) {
		do {
			SpotAuction obj = spotAuctionDao.queryBySpotId(spotId);
			if (obj != null) {
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
			SpotAuction spotAuction = new SpotAuction();
			//组装title
			spotAuction = assemblyTitle(spotAuction, productsDO);
			
			// 价格 货币单位
			if (productsDO.getMinPrice() == null) {
				spotAuction.setPrice(0);
			}else{
				spotAuction.setPrice(productsDO.getMinPrice().intValue());
			}
			if (StringUtils.isEmpty(productsDO.getPriceUnit())) {
				spotAuction.setPriceUnit("元");
			} else {
				spotAuction.setPriceUnit(productsDO.getPriceUnit());
			}
			// 数量 数量单位
			if (StringUtils.isNotEmpty(productsDO.getQuantity())) {
				spotAuction.setQuantity(Integer.valueOf(productsDO.getQuantity()));
			}
			if (StringUtils.isNotEmpty(productsDO.getQuantityUnit())) {
				spotAuction.setQuantityUnit(productsDO.getQuantityUnit());
			}
			// 默认状态未 未审核
			spotAuction.setCheckStatus(SpotAuctionService.CHECK_STATUS_NO_CHECK);
			spotAuction.setSpotId(spotId);
			spotAuction.setProductId(productId);
			spotAuction.setCompanyId(companyId);
			spotAuction.setExpiredTime(new Date());
			spotAuction.setStartPrice(0);
			spotAuction.setUpPrice(0);
			return spotAuctionDao.insert(spotAuction);
		} while (false);
		return 0;
	}

	@Override
	public PageDto<SpotAuctionDto> pageByCondition(SpotAuction spotAuction,
			PageDto<SpotAuctionDto> page) {
		page.setTotalRecords(spotAuctionDao.queryCountByCondition(spotAuction));
		List<SpotAuction> list = spotAuctionDao.queryByCondition(spotAuction,page);
		page.setRecords(coverAuctionDoToDto(list));
		return page;
	}

	@Override
	public SpotAuction queryBySpotId(Integer spotId) {
		Assert.notNull(spotId, "spotId must not be null");
		return spotAuctionDao.queryBySpotId(spotId);
	}

	@Override
	public Integer editForAuction(SpotAuction spotAuction) {
		Assert.notNull(spotAuction.getId(), "id must not be null");
		return spotAuctionDao.update(spotAuction);
	}

	@Override
	public Integer updateStatusById(Integer id, String status) {
		Assert.notNull(id, "id must not be null");
		return spotAuctionDao.updateStatusById(id, status);
	}

	@Override
	public List<SpotAuctionDto> queryAuctionBySize(Integer size) {
		if(size==null){
			size =10;
		}
		if(size>50){
			size = 50;
		}
		List<SpotAuction> list = spotAuctionDao.queryAuctionBySize(size);
		return coverAuctionDoToDto(list);
	}
	
	private List<SpotAuctionDto> coverAuctionDoToDto(List<SpotAuction> list){
		List<SpotAuctionDto> nlist = new ArrayList<SpotAuctionDto>();
		for (SpotAuction obj : list) {
			SpotAuctionDto dto = new SpotAuctionDto();
			// 竞拍信息
			dto.setSpotAuction(obj);
			ProductsDO pdo = productsDAO.queryProductsById(obj.getProductId());
			if(pdo!=null){
				// 供求信息
				dto.setProducts(pdo);
				if(ProductsService.PRODUCTS_TYPE_OFFER.equals(pdo.getProductsTypeCode())){
					dto.setProductsTypeCode("供应");
				}else if(ProductsService.PRODUCTS_TYPE_BUY.equals(pdo.getProductsTypeCode())){
					dto.setProductsTypeCode("求购");
				}
				if(StringUtils.isNotEmpty(pdo.getCategoryProductsMainCode())){
					dto.setProductsCategory(CategoryProductsFacade.getInstance().getValue(pdo.getCategoryProductsMainCode()));
				}
				SpotAuctionLog spotAuctionLog=  new SpotAuctionLog();
				spotAuctionLog.setSpotAuctionId(obj.getId());
				Integer i = spotAuctionLogDao.queryCountByCondition(spotAuctionLog);
				if(i!=null){
					dto.setLogCount(i);
				}
			}
			nlist.add(dto);
		}
		return nlist;
	}

	@Override
	public List<SpotAuctionDto> queryExpiredAuctionBySize(Integer size) {
		List<SpotAuction> list = spotAuctionDao.queryExpiredAuctionBySize(size);
		return coverAuctionDoToDto(list);
	}

	@Override
	public SpotAuction queryById(Integer id) {
		Assert.notNull(id, "id must not be null");
		return spotAuctionDao.queryById(id);
	}

	@Override
	public Integer recommendByAdmin(SpotAuction spotAuction) {
		do {
			SpotAuction obj = spotAuctionDao.queryBySpotId(spotAuction.getSpotId());
			if (obj != null) {
				break;
			}
			ProductsDO productsDO = productsDAO.queryProductsById(spotAuction.getProductId());
			if (productsDO == null) {
				break;
			}
			// 组装title
			spotAuction = assemblyTitle(spotAuction,productsDO);
			// 价格 货币单位
			if (productsDO.getMinPrice() == null) {
				spotAuction.setPrice(0);
			}else{
				spotAuction.setPrice(productsDO.getMinPrice().intValue());
			}
			// 数量
			if (productsDO.getQuantity() != null) {
				spotAuction.setQuantity(StringUtils.isNumber(productsDO.getQuantity())?Integer.valueOf(productsDO.getQuantity()):0);
			}
			// 默认状态 审核
			spotAuction.setCheckStatus(SpotAuctionService.CHECK_STATUS_PASS);
			
			// 数量 
			if (StringUtils.isNotEmpty(productsDO.getQuantity())) {
				spotAuction.setQuantity(Integer.valueOf(productsDO.getQuantity()));
			}
			
			// 价格单位
			if (StringUtils.isEmpty(productsDO.getPriceUnit())) {
				spotAuction.setPriceUnit("元");
			} else {
				spotAuction.setPriceUnit(productsDO.getPriceUnit());
			}
			// 数量单位
			if (StringUtils.isNotEmpty(productsDO.getQuantityUnit())) {
				spotAuction.setQuantityUnit(productsDO.getQuantityUnit());
			}
			spotAuction.setCompanyId(productsDO.getCompanyId());
			return spotAuctionDao.insert(spotAuction);
		} while (false);
		return 0;
	}
	
	private SpotAuction assemblyTitle(SpotAuction spotAuction,ProductsDO productsDO){
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
		spotAuction.setTitle(type + productsDO.getTitle());
		return spotAuction;
	}
}
