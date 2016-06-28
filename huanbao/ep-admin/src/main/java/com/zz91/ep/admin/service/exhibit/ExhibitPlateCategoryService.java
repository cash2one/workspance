package com.zz91.ep.admin.service.exhibit;

import java.util.List;

import com.zz91.ep.domain.exhibit.ExhibitPlateCategory;
import com.zz91.ep.dto.ExtTreeDto;


public interface ExhibitPlateCategoryService {
 
	public List<ExhibitPlateCategory> queryExhibitPlateCategoryAll();
	
	public Integer createCategory(ExhibitPlateCategory ex,String parentCode);
	
	public Integer updateCategory(ExhibitPlateCategory ex);
	
	public Integer deleteCategory(String code);
	
	public List<ExtTreeDto> queryCategoryNode(String parentCode);
	
	public ExhibitPlateCategory queryCategoryByCode(String code);
}
 
