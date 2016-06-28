/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-26.
 */
package com.ast.ast1949.service.products.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsKeywordsRankDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.products.ProductsCompanyDTO;
import com.ast.ast1949.dto.products.ProductsKeywordsRankDTO;
import com.ast.ast1949.persist.products.ProductsKeywordsRankDAO;
import com.ast.ast1949.persist.products.ProductsPicDAO;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.ParseAreaCode;
import com.ast.ast1949.service.products.ProductsKeywordsRankService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.DateUtil;
import com.ast.ast1949.util.StringUtils;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("productsKeywordsRankService")
public class ProductsKeywordsRankServiceImpl implements ProductsKeywordsRankService {

	@Autowired
	private ProductsKeywordsRankDAO productsKeywordsRankDAO;

	@Resource private ProductsPicDAO productsPicDAO;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.service.products.ProductsKeywordsRankService#countProductsKeywordsRankByConditions(com.ast.ast1949
	 * .dto.products.ProductsKeywordsRankDTO)
	 */
	public Integer countProductsKeywordsRankByConditions(ProductsKeywordsRankDTO condition) {
		return productsKeywordsRankDAO.countProductsKeywordsRankByConditions(condition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.service.products.ProductsKeywordsRankService#deleteProductsKeywordsRankById(java.lang.Integer)
	 */
	public Integer deleteProductsKeywordsRankById(Integer id) {
		return productsKeywordsRankDAO.deleteProductsKeywordsRankById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.service.products.ProductsKeywordsRankService#insertProductsKeywordsRank(com.ast.ast1949.domain
	 * .products.ProductsKeywordsRankDO)
	 */
	public Integer insertProductsKeywordsRank(ProductsKeywordsRankDO productsKeywordsRank) {
		if (productsKeywordsRank.getStartTime()==null) {
			return 0;
		}
		if (productsKeywordsRank.getEndTime()==null) {
			return 0;
		}
		if (StringUtils.isEmpty(productsKeywordsRank.getName())) {
			return 0;
		}
		return productsKeywordsRankDAO.insertProductsKeywordsRank(productsKeywordsRank);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.service.products.ProductsKeywordsRankService#queryProductsKeywordsRankByConditions(com.ast.ast1949
	 * .dto.products.ProductsKeywordsRankDTO)
	 */
	public List<ProductsKeywordsRankDTO> queryProductsKeywordsRankByConditions(
			ProductsKeywordsRankDTO condition) {
		List<ProductsKeywordsRankDTO> list = productsKeywordsRankDAO.queryProductsKeywordsRankByConditions(condition);
		for (ProductsKeywordsRankDTO productsKeywordsRankDTO : list) {
			//结束时间
			Date endTimes = productsKeywordsRankDTO.getProductsKeywordsRank().getEndTime();
			int i;
			try {
				i = DateUtil.getIntervalDays(endTimes, new Date());
				if (i >= 0) {
					productsKeywordsRankDTO.setExpire(true);
				} else {
					productsKeywordsRankDTO.setExpire(false);
				}
			} catch (Exception e) {
				continue;
			}
			// 获取服务名
			productsKeywordsRankDTO.setLabel(CategoryFacade.getInstance().getValue(productsKeywordsRankDTO.getProductsKeywordsRank().getType()));

		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.service.products.ProductsKeywordsRankService#queryProductsKeywordsRankById(java.lang.Integer)
	 */
	public ProductsKeywordsRankDO queryProductsKeywordsRankById(Integer id) {
		return productsKeywordsRankDAO.queryProductsKeywordsRankById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.service.products.ProductsKeywordsRankService#queryProductsKeywordsRankByProductId(java.lang.Integer
	 * )
	 */
//	public List<ProductsKeywordsRankDO> queryProductsKeywordsRankByProductId(Integer id) {
//		return productsKeywordsRankDAO.queryProductsKeywordsRankByProductId(id);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ast.ast1949.service.products.ProductsKeywordsRankService#updateCheckedById(java.lang.Integer,
	 * java.lang.String)
	 */
	public Boolean updateCheckedById(String isChecked, String ids) {
		//return productsKeywordsRankDAO.updateCheckedById(id, value);
		Assert.notNull(ids, "The ids can not be null");
		String[] str = ids.split(",");
		Integer[] i = new Integer [str.length];
		for (int m = 0; m < str.length; m++) {
			i[m] = Integer.valueOf(str[m]);
		}
		if (productsKeywordsRankDAO.updateCheckedById(isChecked, i) > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ast.ast1949.service.products.ProductsKeywordsRankService#updateProductsKeywordsRankById(com.ast.ast1949.domain
	 * .products.ProductsKeywordsRankDO)
	 */
	public Integer updateProductsKeywordsRankById(ProductsKeywordsRankDO productsKeywordsRank) {

		return productsKeywordsRankDAO.updateProductsKeywordsRankById(productsKeywordsRank);
	}

	@Override
	public List<ProductsCompanyDTO> queryProductsByKeywordsAndBuiedType(String keywords,
			String buiedType, int topNum) {
		Assert.notNull("keywords", "关键字不能为空.");
		Assert.notNull("buiedType", "查询标王类型不能为空.");
		List<ProductsCompanyDTO> list = productsKeywordsRankDAO
				.queryProductsByKeywordsAndBuiedType(keywords, buiedType, topNum);
		CategoryFacade categoryFacade = CategoryFacade.getInstance();
		for (ProductsCompanyDTO dto : list) {
			dto.setBuyOrSale(categoryFacade.getValue(dto.getProductTypeCode()));
			dto.setKeywordsBuiedType(categoryFacade.getValue(dto.getKeywordsBuiedTypeCode()));
			dto.setMembership(categoryFacade.getValue(dto.getMembershipCode()));
			ParseAreaCode parser=new ParseAreaCode();
			parser.parseAreaCode(dto.getAreaCode());
			dto.setCity(parser.getCity());
			dto.setProvinceCode(parser.getProvinceCode());
			dto.setProvince(parser.getProvince());
			List<ProductsPicDO> pics=productsPicDAO.queryProductPicInfoByProductsId(dto.getProductId());
			dto.setProductPictrueAddress("");
			if(pics.size()>0){
				dto.setProductPictrueAddress(StringUtils.getNotNullValue(pics.get(0).getPicAddress()));
			}
		}
		return list;
	}
	
	public List<Integer> queryProductsId(String keywords,String buiedType, Integer maxSize){
		Assert.notNull(keywords, "the keywords can not be null");
		if(maxSize==null){
			maxSize=6;
		}
		return productsKeywordsRankDAO.queryProductsId(keywords,buiedType,maxSize);
	}
	
	public List<ProductsKeywordsRankDTO> queryProductsKeywordsRankByCompanyId(Integer companyId){
		return productsKeywordsRankDAO.queryProductsKeywordsRankByCompanyId(companyId);
	}

}
