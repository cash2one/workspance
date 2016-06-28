package com.ast.ast1949.persist.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductsExportInquiry;
import com.ast.ast1949.domain.yuanliao.YuanLiaoExportInquiry;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;

public interface ProductsExportInquiryDao{

	public Integer batchInsert(List<ProductsExportInquiry> list);
	
	public Integer batchInsertYuanLiao(List<YuanLiaoExportInquiry> list);

	public Integer countByProductId(Integer productId);
	
	public Integer countByProductIdAndToCompId(Integer productId,Integer toCompanyId);
	
	public ProductsExportInquiry queryByProductId(Integer productId);

	public Integer countByCompanyId(Integer toCompanyId);
	
	public List<ProductsExportInquiry> queryList(Integer productId,Integer companyId,PageDto<ProductsDto> page);

	public Integer countList(Integer productId, Integer companyId);
}
