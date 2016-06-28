package com.ast.ast1949.service.spot.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsSpot;
import com.ast.ast1949.domain.spot.SpotOrder;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotOrderDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.products.ProductsSpotDao;
import com.ast.ast1949.persist.spot.SpotOrderDao;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.spot.SpotOrderService;
import com.zz91.util.Assert;
import com.zz91.util.lang.StringUtils;

/**
 * author:kongsj date:2013-3-25
 */
@Component("spotOrderService")
public class SpotOrderServiceImpl implements SpotOrderService {

	@Resource
	private SpotOrderDao spotOrderDao;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private ProductsSpotDao productsSpotDao;
	@Resource
	private CompanyAccountDao companyAccountDao;

	@Override
	public Integer deleteSpotOrderById(String idStr, Integer companyId) {
		Assert.notNull(idStr, "id must not be null");
		Assert.notNull(companyId, "companyId must not be null");
		return spotOrderDao.batchDelete(
				StringUtils.StringToIntegerArray(idStr), companyId);
	}

	@Override
	public Integer insert(SpotOrder spotOrder) {
		do {
			if (spotOrder == null) {
				break;
			}
			if (validateCart(spotOrder.getCompanyId(), spotOrder.getSpotId())) {
				break;
			}
			// 默认不删除
			if (StringUtils.isEmpty(spotOrder.getIsDel())) {
				spotOrder.setIsDel(SpotOrderService.IS_NO_DEL);
			}
			// 计算总量
			if (spotOrder.getTotal() == null) {
				// TODO 后期修改总量 改为分别显示
				spotOrder.setTotal(spotOrder.getPrice()
						* spotOrder.getQuantity());
			}
			return spotOrderDao.insert(spotOrder);
		} while (false);
		return 0;
	}

	@Override
	public PageDto<SpotOrderDto> pageSpotOrderForFront(Integer companyId,
			String orderStatus, PageDto<SpotOrderDto> page) {
		SpotOrder obj = new SpotOrder();
		obj.setCompanyId(companyId);
		obj.setOrderStatus(orderStatus);
		obj.setIsDel(SpotOrderService.IS_NO_DEL);
		return pageSpotOrder(obj, page);
	}

	@Override
	public PageDto<SpotOrderDto> pageSpotOrder(SpotOrder spotOrder,
			PageDto<SpotOrderDto> page) {
		page.setTotalRecords(spotOrderDao.queryCountByCondition(spotOrder));
		List<SpotOrder> list = spotOrderDao.queryByCondition(spotOrder, page);
		List<SpotOrderDto> nlist = new ArrayList<SpotOrderDto>();
		for (SpotOrder obj : list) {
			SpotOrderDto dto = new SpotOrderDto();
			dto.setSpotOrder(obj);
			nlist.add(dto);
			ProductsSpot psdo = productsSpotDao.queryById(obj.getSpotId());
			if (psdo != null) {
				ProductsDO pdo = productsDAO.queryProductsById(psdo
						.getProductId());
				// 供求信息
				if (ProductsService.PRODUCTS_TYPE_OFFER.equals(pdo
						.getProductsTypeCode())) {
					dto.setProductsTypeCode("供应");
				} else if (ProductsService.PRODUCTS_TYPE_BUY.equals(pdo
						.getProductsTypeCode())) {
					dto.setProductsTypeCode("求购");
				}
				if (StringUtils.isNotEmpty(pdo.getCategoryProductsMainCode())) {
					dto.setProductsCategory(CategoryProductsFacade
							.getInstance().getValue(
									pdo.getCategoryProductsMainCode()));
				}
				// 订单拥有者 联系方式 下单者联系方式
				String orderMobile = companyAccountDao
						.queryMobileByCompanyId(obj.getCompanyId());
				if (StringUtils.isNotEmpty(orderMobile)) {
					dto.setOrderMobile(orderMobile);
				}
				// 现货所有人 联系方式
				String mobile = companyAccountDao.queryMobileByCompanyId(pdo
						.getCompanyId());
				if (StringUtils.isNotEmpty(mobile)) {
					dto.setMobile(mobile);
				}
			}
		}
		page.setRecords(nlist);
		return page;
	}

	@Override
	public Integer confirmTransaction(String orderStatus, String idStr,Integer companyId) {
		return spotOrderDao.batchUpdateByStatusAndId(orderStatus, StringUtils.StringToIntegerArray(idStr), companyId);
	}

	@Override
	public boolean validateCart(Integer companyId, Integer spotId) {
		Integer i = spotOrderDao.validateCart(companyId, spotId);
		if (i > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Integer countBySpotId(Integer spotId) {
		Assert.notNull(spotId, "spotId must not be null");
		Integer i = spotOrderDao.countBySpotId(spotId);
		if(i!=null){
			return i;
		}
		return 0;
	}

	@Override
	public List<SpotOrder> queryOrder(Integer size) {
		if(size>50){
			size = 50;
		}
		if(size ==null){
			size = 10;
		}
		List<SpotOrder> list = spotOrderDao.queryOrder(size*10);
		List<SpotOrder> nlist = new ArrayList<SpotOrder>(); 
		Set<Integer> set = new HashSet<Integer>();
		for(SpotOrder obj :list){
			if(list==null||list.size()<2){
				break;
			}
			if(nlist.size()==2){
				break;
			}
			if(nlist.size()>=2){
				return null;
			}
			if(!set.contains(obj.getSpotId())){
				set.add(obj.getSpotId());
				nlist.add(obj);
			}
		}
		return nlist;
	}

}
