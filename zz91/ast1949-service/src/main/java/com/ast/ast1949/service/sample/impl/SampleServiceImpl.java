package com.ast.ast1949.service.sample.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.domain.sample.SampleRelateProduct;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.InquiryCountDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.products.ProductsPicDAO;
import com.ast.ast1949.persist.sample.SampleDao;
import com.ast.ast1949.persist.sample.SampleRelateProductDao;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.sample.SampleService;
import com.zz91.util.lang.StringUtils;

@Component("sampleService")
public class SampleServiceImpl implements SampleService{

	@Resource
	private SampleDao sampleDao;
	@Resource
	private SampleRelateProductDao sampleRelateProductDao;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private InquiryCountDao inquiryCountDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private ProductsPicDAO productsPicDAO;
	
	
	@Override
	public Integer createSample(Sample sample, Integer productId) {
		// 存在的样品信息
		Sample obj = queryByIdOrProductId(null,productId);
		if (obj!=null) {
			sample.setId(obj.getId());
			return editSample(sample);
		}
		sample.setIsDel(IS_NO_DEL);
		if (sample.getAmount()!=null) {
			sample.setAmount(Math.abs(sample.getAmount()));
		}else{
			sample.setAmount(0);
		}
		if (sample.getSendPrice()!=null) {
			sample.setSendPrice(Math.abs(sample.getSendPrice()));
		}else{
			sample.setSendPrice(0f);
		}
		if (sample.getTakePrice()!=null) {
			sample.setTakePrice(Math.abs(sample.getTakePrice()));
		}else{
			sample.setTakePrice(0f);
		}
		if (sample.getWeight()!=null) {
			sample.setWeight(Math.abs(sample.getWeight()));
		}else{
			sample.setWeight(0f);
		}
		Integer i = sampleDao.insert(sample);
		if (i>0) {
			SampleRelateProduct sampleRelateProduct = new SampleRelateProduct();
			sampleRelateProduct.setProductId(productId);
			sampleRelateProduct.setSampleId(i);
			sampleRelateProductDao.insert(sampleRelateProduct);
		}
		return i;
		
	}

	@Override
	public Integer editSample(Sample sample) {
		if (sample==null||sample.getId()==null) {
			return 0;
		}
		return sampleDao.update(sample);
	}

	@Override
	public Sample queryByIdOrProductId(Integer id, Integer productId) {
		if (id==null&&productId==null) {
			return null;
		}
		if (id!=null&&id>0) {
			return sampleDao.queryById(id);
		}
		if (productId!=null&&productId>0) {
			return sampleDao.queryByProductId(productId);
		}
		return null;
	}

	@Override
	public Integer checkSample(Integer productId, Integer isDel,String unpassReason) {
		Sample obj = queryByIdOrProductId(null,productId);
		if (obj==null) {
			return 0;
		}
		Integer i = sampleDao.updateDelStatus(obj.getId(), isDel);
		if (i>0&&1==isDel&&StringUtils.isNotEmpty(unpassReason)) {
			sampleDao.updateSampleForUnpassReason(obj.getId(), unpassReason);
		}
		if (i>0&&0==isDel) {
			sampleDao.updateSampleForUnpassReason(obj.getId(), "");
		}
		
		return i;
	}
	
	@Override
	public void countSampleInfo(Map<String, Object>out,Integer companyId) {
		// 总共样品 总共供求 总共询盘
		out.put("ypCount", sampleDao.countByCompanyId(companyId));
		
		out.put("gqCount", productsDAO.countProductsByCompanyId(companyId));
		
		out.put("inquiryCount", inquiryCountDao.queryByCompanyId(companyId));
		
	}
	
	@Override
	public List<ProductsDto> queryListByCompanyId(Integer companyId,Integer productId){
		List<ProductsDO> list = new ArrayList<ProductsDO>();
//		list = sampleDao.queryListByCompanyId(companyId,11);
		PageDto<ProductsDO> page = new PageDto<ProductsDO>();
		page.setPageSize(11); // 取11条。。。防止一条重复排除当前这条
		page.setDir("desc");
		page.setSort("refresh_time");
		list = productsDAO.queryProductsOfCompanyByStatus(companyId, null, "1", null, "0", null, null, null, page);
		List<ProductsDto> nlist =new ArrayList<ProductsDto>();
		for (ProductsDO obj:list) {
			ProductsDto dto =new ProductsDto();
			if (productId.equals(obj.getId())) {
				continue;
			}
			dto.setProducts(obj);
			Sample sample = sampleDao.queryByProductId(obj.getId());
			if (sample!=null) {
				dto.setSample(sample);
			}
			nlist.add(dto);
			if (nlist.size()==10) {
				break;
			}
		}
		return nlist;
	}

	@Override
	public Integer countAmountByCompanyId(Integer companyId) {
		if (companyId==null) {
			return 0;
		}
		return sampleDao.countAmountByCompanyId(companyId);
	}

	@Override
	public PageDto<Sample> queryListByFilter(PageDto<Sample> page, Map<String, Object> filterMap) {
		if(page.getSort()==null){
			page.setSort("s.id");
		}
		
		if (filterMap.get("from")!=null||filterMap.get("to")!=null) {
			if(filterMap.get("dateType")==null||StringUtils.isEmpty(""+filterMap.get("dateType"))||filterMap.get("dateType")=="null"){
				filterMap.put("dateType", "s.gmt_created");
			}
		}
		
		filterMap.put("page", page);
		page.setTotalRecords(sampleDao.queryListByFilterCount(filterMap));
		List<Sample> list = sampleDao.queryListByFilter(filterMap);
		for (Sample obj : list) {
			String code =obj.getAreaCode();
			String name = "";
			if (StringUtils.isNotEmpty(obj.getAreaCode())) {
				name = CategoryFacade.getInstance().getValue(obj.getAreaCode());
			}
			if (StringUtils.isNotEmpty(code)&&code.length()==16) {
				name = CategoryFacade.getInstance().getValue(obj.getAreaCode().substring(0,12)) + name;
			}
			obj.setAreaCode(name);
			// 供求 相关
			Integer productId = sampleRelateProductDao.queryBySampleIdForProductId(obj.getId());
			if (productId!=null) {
				ProductsDO product = productsDAO.queryProductsById(productId);
				obj.setProductId(""+productId);
				if (product!=null) {
					obj.setTitle(product.getTitle());
				}
			}
			// 公司相关
			Company company = companyDAO.queryCompanyById(obj.getCompanyId());
			if (company!=null) {
				obj.setCompanyName(company.getName());
			}
			
			// 图片
			List<ProductsPicDO> picList =  productsPicDAO.queryProductPicInfoByProductsIdForFront(productId);
			for (ProductsPicDO picObj : picList) {
				if (StringUtils.isNotEmpty(picObj.getPicAddress())) {
					obj.setPicCover(picObj.getPicAddress());
					break;
				}
			}
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public Integer updateSampleForUnpassReason(Integer id, String unpassReason) {
			if(id!=null){
				return sampleDao.updateSampleForUnpassReason(id, unpassReason);
			}else{
				return 0;
			}
		
	}

}
