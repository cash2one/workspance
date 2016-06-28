package com.zz91.ep.admin.service.exhibit.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.exhibit.ExhibitPlateCategoryDao;
import com.zz91.ep.admin.service.exhibit.ExhibitPlateCategoryService;
import com.zz91.ep.domain.exhibit.ExhibitPlateCategory;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.util.Assert;

@Component("exhibitPlateCategoryService")
public class ExhibitPlateCategoryServiceImpl implements ExhibitPlateCategoryService{
	@Resource
	private ExhibitPlateCategoryDao exhibitPlateCategoryDao;
	@Override
	public List<ExhibitPlateCategory> queryExhibitPlateCategoryAll() {
		
		return exhibitPlateCategoryDao.queryExhibitPlateCategoryAll();
	}
	@Override
	public Integer createCategory(ExhibitPlateCategory ex, String parentCode) {
		if(parentCode==null){
			parentCode="";
		}
		
		String code=exhibitPlateCategoryDao.queryMaxCodeOfChild(parentCode);
		if(code!=null && code.length()>0){
			code = code.substring(parentCode.length());
			Integer codeInt=Integer.valueOf(code);
			codeInt++;
			ex.setCode(parentCode+String.valueOf(codeInt));
		}else{
			ex.setCode(parentCode+"1000");
		}
		return exhibitPlateCategoryDao.insertExhibitCategory(ex);
	}
	@Override
	public Integer deleteCategory(String code) {
		Assert.notNull(code, "the code can not be null");
		return exhibitPlateCategoryDao.deleteCategory(code);
	}
	@Override
	public ExhibitPlateCategory queryCategoryByCode(String code) {
		if(code==null) {
			return null;
		}
		return exhibitPlateCategoryDao.queryCategoryByCode(code);
	}
	@Override
	public List<ExtTreeDto> queryCategoryNode(String parentCode) {

		if(parentCode==null){
			parentCode="";
		}
		
		List<ExhibitPlateCategory> list=exhibitPlateCategoryDao.queryChild(parentCode);
		List<ExtTreeDto> nodeList=new ArrayList<ExtTreeDto>();
		for(ExhibitPlateCategory exhibitCategory:list){
			ExtTreeDto node=new ExtTreeDto();
			node.setId(String.valueOf(exhibitCategory.getId()));
			node.setText(exhibitCategory.getName());
			node.setData(exhibitCategory.getCode());
			Integer i = exhibitPlateCategoryDao.countChild(exhibitCategory.getCode());
			if(i==null || i.intValue()<=0){
				node.setLeaf(true);
			}
			nodeList.add(node);
		}
		return nodeList;
	}
	@Override
	public Integer updateCategory(ExhibitPlateCategory ex) {
		Assert.notNull(ex.getCode(), "the code can not be null");
		return exhibitPlateCategoryDao.updateCategory(ex);
	}

}
