package com.kl91.service.products.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.products.ProductsDto;
import com.kl91.domain.dto.products.ProductsSearchDto;
import com.kl91.domain.products.Products;
import com.kl91.persist.products.ProductsDao;
import com.kl91.service.company.UploadPicService;
import com.kl91.service.products.ProductsService;
import com.zz91.util.lang.StringUtils;

@Component("productsService")
public class ProductsServiceImpl implements ProductsService {

	@Resource
	private ProductsDao productsDao;
	@Resource
	private UploadPicService uploadPicService;

	@Override
	public Integer createProducts(Products product, Integer picId) {
		Integer id = productsDao.insert(product);
		uploadPicService.editUploadPicById(picId, product.getId(),
				UploadPicService.TARGETTYPE_OF_PRODUCTS);
		return id;
	}

	@Override
	public Integer editProducts(Products product, Boolean nfileFlag,
			Integer picid) {
		Integer id = productsDao.update(product);
		uploadPicService.editUploadPicById(picid, product.getId(),
				UploadPicService.TARGETTYPE_OF_PRODUCTS);
		return id;
	}

	@Override
	public Integer deleteProducts(Integer id) {
		return productsDao.delete(id);
	}

	@Override
	public Products queryById(Integer id) {
		return productsDao.queryById(id);
	}

	@Override
	public Integer updateProductsIsNoPub(String id,Integer publishFlag) {
		Integer[] ids = StringUtils.StringToIntegerArray(id);
		return productsDao.updateProductsIsNoPub(ids,publishFlag);
	}

	@Override
	public Integer refreshProducts(String id) {
		Integer[] ids = StringUtils.StringToIntegerArray(id);
		return productsDao.refreshProductsByIds(ids);
	}

	@Override
	public ProductsDto queryProductsAndCompanyById(Integer id) {

		return productsDao.queryProductsAndCompanyById(id);
	}

	@Override
	public Integer countProductsIsPassByCompanyId(Integer companyId) {

		return productsDao.countProductsIsPassByCompanyId(companyId);
	}

	@Override
	public Integer batchCheckByStatus(String id, Boolean checkStatus) {

		return null;
	}

	@Override
	public PageDto<Products> queryProductsByTypeCode(String code,
			PageDto<Products> page) {

		return null;
	}

	@Override
	public PageDto<Products> queryProductsByCompanyId(Integer companyId,
			PageDto<Products> page, ProductsSearchDto searchDto) {

		return null;
	}

	@Override
	public PageDto<ProductsDto> queryProductsFromSolr(ProductsSearchDto dto,
			PageDto<ProductsDto> page) {

		return null;
	}

	@Override
	public PageDto<Products> queryVIPProducts(ProductsSearchDto searchDto,
			PageDto<Products> page) {

		return null;
	}

	@Override
	public Integer countProducts(Integer companyId, String productType) {
		Integer i = productsDao.countProducts(companyId, productType);
		if (i > 0) {
			return i;
		} else {
			return 0;
		}
	}

	public PageDto<Products> queryProducts(Products products,
			PageDto<Products> page) {
//		page.setRecords(productsDao.queryProducts(products, page));
//		page.setTotalRecords(productsDao.queryProductsCount(searchDto, page);
		return page;
	}

	@Override
	public Integer deleteMost(String id,Integer deletedFlag) {
		Integer[] ids = StringUtils.StringToIntegerArray(id);
		return productsDao.deleteMost(ids,deletedFlag);
	}

	@Override
	public PageDto<ProductsDto> queryProductsForList(
			Integer deletedFlag, Integer publishFlag,Integer checkedFlag,PageDto<ProductsDto> page) {
		ProductsSearchDto searchDto=new ProductsSearchDto();
		searchDto.setDeletedFlag(deletedFlag);
		searchDto.setCheckedFlag(checkedFlag);
		searchDto.setPublishFlag(publishFlag);
		page.setTotalRecords(productsDao.queryProductsCount(searchDto, page));
		page.setRecords(productsDao.queryProductsForList(searchDto, 5, page));
		return page;
	}

}
