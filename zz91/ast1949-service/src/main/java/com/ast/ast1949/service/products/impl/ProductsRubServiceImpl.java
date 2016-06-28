package com.ast.ast1949.service.products.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsRub;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.persist.products.ProductsRubDao;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.products.ProductsRubService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.lang.StringUtils;

@Component("productsRubService")
public class ProductsRubServiceImpl implements ProductsRubService {

	@Resource
	private ProductsRubDao productsRubDao;
	@Resource
	private ProductsService productsService; 
	@Resource
	private CompanyService companyService;

	@Override
	public Integer addProductsRub(ProductsRub productsRub) {
		do {
			if (productsRub == null) {
				break;
			}
			if (productsRub.getProductId() == null) {
				break;
			}
			// 验证是否已经存在，确保唯一性
			ProductsRub obj = productsRubDao.queryRubByProductId(productsRub.getProductId());
			if (obj != null) {
				break;
			}
			return productsRubDao.insert(productsRub);
		} while (false);
		return null;
	}
	
	@Override
	public Integer editProductsRub(ProductsRub productsRub){
		return productsRubDao.update(productsRub);
	}

	@Override
	public Integer deleteProductsRubByProductId(Integer productId) {
		do {
			if (productId == null) {
				break;
			}
			return productsRubDao.deleteByProductId(productId);
		} while (false);
		return null;
	}

	@Override
	public PageDto<ProductsRub> pageRub(ProductsRub productsRub,
			PageDto<ProductsRub> page) {
		page.setSort("refresh_time");
		page.setDir("desc");
		page.setTotalRecords(productsRubDao.queryRubCount(productsRub));
		page.setRecords(productsRubDao.queryRub(productsRub, page));
		return page;
	}

	@Override
	public ProductsRub queryRubByProductId(Integer productId) {
		do {
			if (productId == null) {
				break;
			}
			return productsRubDao.queryRubByProductId(productId);
		} while (false);
		return null;
	}

	@Override
	public ProductsRub productsToRub(ProductsDO product) {
		do {
		ProductsRub productsRub = new ProductsRub();
		if(product==null||product.getId()==null){
			break;
		}
		productsRub.setProductId(product.getId());
		productsRub.setCheckPerson(product.getCheckPerson());
		productsRub.setDetails(product.getDetails());
		productsRub.setLocation(product.getLocation());
		if(product.getMaxPrice()!=null&&product.getMaxPrice()>0){
			productsRub.setMaxPrice(product.getMaxPrice().intValue());
		}
		if(product.getMinPrice()!=null&&product.getMinPrice()>0){
			productsRub.setMinPrice(product.getMaxPrice().intValue());
		}
		if(StringUtils.isEmpty(product.getPriceUnit())){
			productsRub.setPriceUnit("元");
		}else{
			productsRub.setPriceUnit(product.getPriceUnit());
		}
		productsRub.setProductsTypeCode(product.getProductsTypeCode());
		productsRub.setQuantity(product.getQuantity());
		if(StringUtils.isEmpty(product.getQuantityUnit())){
			productsRub.setQuantityUnit("吨");
		}else{
			productsRub.setQuantityUnit(product.getQuantityUnit());
		}
		productsRub.setRefreshTime(product.getRefreshTime()); // 刷新时间
		productsRub.setExpiredTime(product.getExpireTime()); // 过期时间
		productsRub.setSource(product.getSource()); // 货源地
		productsRub.setSpecification(product.getSpecification());  //产品规格
		// 后台标签
		if(StringUtils.isNotEmpty(product.getTagsAdmin())){
			productsRub.setTags(product.getTagsAdmin()); 
		}else if(StringUtils.isNotEmpty(product.getCategoryProductsMainCode())){
			productsRub.setTags(CategoryProductsFacade.getInstance().getValue(product.getCategoryProductsMainCode()));
		}
		productsRub.setTitle(product.getTitle());
		return productsRub;
		} while (false);
		return null;
	}
	
	@Override
	public ProductsDto rubToProductsDto(ProductsRub productsRub) {
		do {
			ProductsDto productsDto = new ProductsDto();
			ProductsDO productsDO = new ProductsDO();
			if (productsRub == null || productsRub.getId() == null) {
				break;
			}
			productsDO.setId(productsRub.getId());
			productsDO.setCheckPerson(productsRub.getCheckPerson());
			productsDO.setDetails(productsRub.getDetails());
			productsDO.setLocation(productsRub.getLocation());
			if(productsRub.getMaxPrice()!=null){
				productsDO.setMaxPrice(Float.valueOf(productsRub.getMaxPrice()));
			}
			if(productsRub.getMaxPrice()!=null){
				productsDO.setMinPrice(Float.valueOf(productsRub.getMaxPrice()));
			}
			productsDO.setPriceUnit(productsRub.getPriceUnit());
			productsDO.setProductsTypeCode(productsRub.getProductsTypeCode());
			productsDO.setQuantity(productsRub.getQuantity());
			productsDO.setQuantityUnit(productsRub.getQuantityUnit());
			productsDO.setRefreshTime(productsRub.getRefreshTime()); // 刷新时间
			productsDO.setExpireTime(productsRub.getExpiredTime()); // 过期时间
			productsDO.setSource(productsRub.getSource()); // 货源地
			productsDO.setSpecification(productsRub.getSpecification()); // 产品规格
			productsDO.setTagsAdmin(productsRub.getTags()); // 后台标签
			productsDO.setTitle(productsRub.getTitle());
			ProductsDO product = productsService.queryProductsById(productsRub.getProductId());
			productsDO.setCompanyId(product.getCompanyId()); // 公司id
			productsDto.setCategoryProductsMainLabel(CategoryProductsFacade.getInstance().getValue(product.getCategoryProductsMainCode())); // 产品类别
			productsDto.setProducts(productsDO);
			return productsDto;
		} while (false);
		return null;
	}

	@Override
	public List<ProductsRub> queryRubForDetail(Integer companyId,Integer size) {
		return productsRubDao.queryRubForDetail(companyId,size);
	}

	@Override
	public PageDto<ProductsDto> pageRubByAdmin(ProductsRub productsRub,
			PageDto<ProductsDto> page) {
		PageDto<ProductsRub> pageDto = new PageDto<ProductsRub>();
		pageDto.setSort("refresh_time");
		pageDto.setDir("desc");
		pageDto.setStartIndex(page.getStartIndex());
		page.setTotalRecords(productsRubDao.queryRubCount(productsRub));
		List<ProductsRub> list = productsRubDao.queryRub(productsRub, pageDto);
		List<ProductsDto> nlist = new ArrayList<ProductsDto>();
		for(ProductsRub obj:list){
			ProductsDto dto = productsService.queryProductsDetailsById(obj.getProductId());
			if(dto!=null){
				dto.setCompany(companyService.queryCompanyById(dto.getProducts().getCompanyId()));
			}
			nlist.add(dto);
		}
		page.setRecords(nlist);
		return page;
	}
}
