/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-24
 */
package com.ast.ast1949.service.information.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.ExhibitDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.ExhibitDTO;
import com.ast.ast1949.persist.information.ExhibitDAO;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.information.ExhibitService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("exhibitService")
public class ExhibitServiceImpl implements ExhibitService{

	@Autowired
	private ExhibitDAO exhibitDAO;
	
	public Integer insertExhibit(ExhibitDO exhibitDO) {
       Assert.notNull(exhibitDO, "exhibitDO is not null");
		return exhibitDAO.insertExhibit(exhibitDO);
	}

//	public List<ExhibitDTO> queryExhibitByCondition(ExhibitDTO exhibitDTO) {
//        Assert.notNull(exhibitDTO, "exhibitDTO is not null");
//		return exhibitDAO.queryExhibitByCondition(exhibitDTO);
//	}

//	public Integer queryExhibitCountByCondition(ExhibitDTO exhibitDTO) {
//       Assert.notNull(exhibitDTO, "exhibitDTO is not null");
//		return exhibitDAO.queryExhibitCountByCondition(exhibitDTO);
//	}

	public Integer updateExhibit(ExhibitDO exhibitDO) {
        Assert.notNull(exhibitDO, "exhibitDO is not null");
		return exhibitDAO.updateExhibit(exhibitDO);
	}

	public ExhibitDTO queryExhibitById(Integer id) {
		Assert.notNull(id, "id is not null");
		ExhibitDO exhibit=exhibitDAO.selectExhibitById(id);
		
		return buildDto(exhibit);
	}

	public Integer batchDeleteExhibitById(int[] entities) {
        Assert.notNull(entities, "entities is not null");
		return exhibitDAO.deleteExhibitById(entities);
	}

	public List<ExhibitDTO> queryExhibit(String plateCategoryCode,String exhibitCategoryCode,
			Integer limitSize) {
		if(plateCategoryCode==null&&exhibitCategoryCode==null){
			Assert.notNull(null, "plateCategoryCode or exhibitCategoryCode can not both be null");
		}
		List<ExhibitDO> list=exhibitDAO.queryExhibitByCategoryCode(plateCategoryCode,exhibitCategoryCode, limitSize);
		
		List<ExhibitDTO> resultList=new ArrayList<ExhibitDTO>();
		for(ExhibitDO e:list){
			resultList.add(buildDto(e));
		}
		return resultList;
	}
//	public List<ExhibitDO> queryExhibit(String plateCategoryCode,String exhibitCategoryCode,
//			Integer limitSize) {
//		ExhibitDO exhibit = new ExhibitDO();
//		PageDto<ExhibitDTO> page = new PageDto<ExhibitDTO>();
//		page.setPageSize(limitSize);
//		return exhibitDAO.queryExhibit(exhibit, page);
//	}

	public ExhibitDO selectExhibitById(Integer id) {
		Assert.notNull(id, "id is not null");
		return exhibitDAO.selectExhibitById(id);
	}

	@Override
	public PageDto<ExhibitDTO> pageExhibit(ExhibitDO exhibit,
			PageDto<ExhibitDTO> page) {
		List<ExhibitDO> list=exhibitDAO.queryExhibit(exhibit, page);
		List<ExhibitDTO> resultList=new ArrayList<ExhibitDTO>();
		for(ExhibitDO e:list){
			resultList.add(buildDto(e));
		}
		page.setRecords(resultList);
		page.setTotalRecords(exhibitDAO.queryExhibitCount(exhibit));
		return page;
	}

	@Override
	public List<ExhibitDTO> queryExhibitByPlateCategory(String plateCategory,
			Integer max) {
		if(max==null){
			max=10;
		}
		List<ExhibitDO> list=exhibitDAO.queryExhibitByPlateCategory(plateCategory, max);
		
		List<ExhibitDTO> resultList=new ArrayList<ExhibitDTO>();
		for(ExhibitDO e:list){
			resultList.add(buildDto(e));
		}
		return resultList;
	}
	
	private ExhibitDTO buildDto(ExhibitDO exhibit){
		CategoryFacade category=CategoryFacade.getInstance();
		ExhibitDTO dto=new ExhibitDTO();
		dto.setExhibitDO(exhibit);
		if(exhibit.getAreaCode()!=null && exhibit.getAreaCode().length()>=12){
			dto.setAreaName(category.getValue(exhibit.getAreaCode().substring(0, 12)));
		}else {
			dto.setAreaName(category.getValue(exhibit.getAreaCode()));
		}
		dto.setExhibitCategoryName(category.getValue(exhibit.getExhibitCategoryCode()));
		dto.setPlateCategoryName(category.getValue(exhibit.getPlateCategoryCode()));
		return dto;
	}

	@Override
	public String queryContent(Integer id) {
		return exhibitDAO.queryContent(id);
	}

	@Override
	public Integer updateContent(Integer id, String content) {
		return exhibitDAO.updateContent(id, content);
	}

	@Override
	public PageDto<ExhibitDTO> pageExhibitByAdmin(ExhibitDO exhibit,
			PageDto<ExhibitDTO> page) {
		
		if(page.getSort()==null){
			page.setSort("gmt_created");
			page.setDir("desc");
		}
		
		List<ExhibitDO> list=exhibitDAO.queryExhibitByAdmin(exhibit, page);
		List<ExhibitDTO> resultList=new ArrayList<ExhibitDTO>();
		for(ExhibitDO e:list){
			resultList.add(buildDto(e));
		}
		page.setRecords(resultList);
		page.setTotalRecords(exhibitDAO.queryExhibitByAdminCount(exhibit));
		return page;
	}

	@Override
	public Integer deleteExhibit(Integer id) {
		if(id==null || id.intValue()<=0){
			return null;
		}
		return exhibitDAO.deleteExhibit(id);
	}

	@Override
	public List<ExhibitDTO> queryNewestExhibit(String plateCategoryCode,
			String exhibitCategoryCode, Integer size) {
		return exhibitDAO.queryNewestExhibit(plateCategoryCode,exhibitCategoryCode,size);
	}

	@Override
	public ExhibitDO queryUpNews(String plateCategory, String gmtCreated) {
		return exhibitDAO.queryUpNews(plateCategory, gmtCreated);
	}

	@Override
	public ExhibitDO queryDownNews(String plateCategory, String gmtCreated) {
		return exhibitDAO.queryDownNews(plateCategory, gmtCreated);
	}

}
