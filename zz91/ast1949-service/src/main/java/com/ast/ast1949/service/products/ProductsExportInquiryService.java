package com.ast.ast1949.service.products;

import java.util.HashMap;
import java.util.Map;

import com.ast.ast1949.domain.products.ProductsExportInquiry;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;


public interface ProductsExportInquiryService {
	
	final static Map<Integer, Integer> PROCUCT_WITH_COMPANY = new HashMap<Integer, Integer>();
	final static Map<Integer, String> COMPANY_ID_WITH_NAME= new HashMap<Integer, String>();
	
	/**
	 * 导出供求为询盘
	 * @param productId
	 * @param targetIdArray
	 * @return
	 */
	public Integer exportInquiry(Integer productId,Integer[] targetIdArray,String account);
	
	/**
	 * 统计供求id
	 * @param productId
	 * @return
	 */
	public Integer countByProductId(Integer productId);
	
	/**
	 * 根据目标供求id 查找最新导出记录
	 * @param productId
	 * @return
	 */
	public ProductsExportInquiry queryByProductId(Integer productId);
	
	public PageDto<ProductsDto> pageProductsExport(Integer productId,Integer companyId,PageDto<ProductsDto> page);
	
}
