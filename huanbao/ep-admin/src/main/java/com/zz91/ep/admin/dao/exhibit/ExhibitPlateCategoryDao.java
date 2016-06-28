package com.zz91.ep.admin.dao.exhibit;

import java.util.List;

import com.zz91.ep.domain.exhibit.ExhibitPlateCategory;


public interface ExhibitPlateCategoryDao {
 
	public List<ExhibitPlateCategory> queryExhibitPlateCategoryAll();

	public String queryMaxCodeOfChild(String parentCode);

	public Integer insertExhibitCategory(ExhibitPlateCategory ex);

	public Integer deleteCategory(String code);

	public ExhibitPlateCategory queryCategoryByCode(String code);

	public List<ExhibitPlateCategory> queryChild(String parentCode);
	
	public Integer countChild(String code);

	public Integer updateCategory(ExhibitPlateCategory ex);
}
 
