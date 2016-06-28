package com.zz91.ep.admin.service.exhibit.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.exhibit.ExhibitDao;
import com.zz91.ep.admin.service.exhibit.ExhibitService;
import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.exhibit.ExhibitDto;
import com.zz91.util.Assert;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.lang.StringUtils;

@Component("exhibitService")
public class ExhibitServiceImpl implements ExhibitService{
	@Autowired
	private ExhibitDao exhibitDao;
	
//	@Override
//	public PageDto<ExhibitDto> pageExhibitBySearch(Exhibit exhibit, PageDto<ExhibitDto> page) {
//		page.setSort("gmt_sort");
//		page.setDir("desc");
//		List<Exhibit> list = exhibitDao.queryExhibitBySearch(exhibit, page);
//		List<ExhibitDto> resultList = new ArrayList<ExhibitDto>();
//		for(Exhibit e:list) {
//			resultList.add(buildDto(e));
//		}
//		page.setRecords(resultList);
//		page.setTotals(exhibitDao.queryExhibitBySearchCount(exhibit));
//		return page;
//	}

//	@Override
//	public List<ExhibitDto> queryExhibitByCategory(String plateCategoryCode, Integer max) {
//		if(plateCategoryCode == null) {
//			return null;
//		}
//		if(max==null) {
//			max = MAX_SIZE;
//		}
//		List<Exhibit> list=exhibitDao.queryExhibitByCategory(plateCategoryCode, max);
//		
//		List<ExhibitDto> resultList=new ArrayList<ExhibitDto>();
//		for(Exhibit e:list){
//			resultList.add(buildDto(e));
//		}
//		return resultList;
//	}
	
//	@Override
//	public PageDto<ExhibitDto> pageExhibitByCategory(String plateCategoryCode,PageDto<ExhibitDto> page) {
//		
//		if(plateCategoryCode==null) {
//			return null;
//		}
//		if(page.getLimit()==null){
//			page.setLimit(20);
//		}
//		page.setSort("gmt_sort");
//		page.setDir("desc");
//		
//		List<Exhibit> list = exhibitDao.queryExhibitByPlateCategory(plateCategoryCode, page);
//		List<ExhibitDto> resultList = new ArrayList<ExhibitDto>();
//		for(Exhibit e:list) {
//			resultList.add(buildDto(e));
//		}
//		page.setRecords(resultList);
//		page.setTotals(exhibitDao.queryExhibitByPlateCategoryCount(plateCategoryCode));
//		return page;
//	}
	
	@Override
	public Exhibit queryExhibitDetailsById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		
		return exhibitDao.queryExhibitDetailsById(id);
	}
	
	@Override
	public Integer deleteExhibit(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return exhibitDao.deleteExhibit(id);
	}

	@Override
	public Integer createExhibitByAdmin(Exhibit exhibit,String admin) {
		Assert.notNull(exhibit, "the exhibit can not be null");
		//pauseStatus=0默认为未发布
		if(exhibit.getPauseStatus()==null) {
			exhibit.setPauseStatus(0);
		}
		if(StringUtils.isNotEmpty(admin)) {
			exhibit.setAdminAccount(admin);
			exhibit.setAdminName(AuthUtils.getInstance().queryStaffNameOfAccount(admin));
		}
		exhibit.setGmtPublish(new Date());
		return exhibitDao.insertExhibit(exhibit);
	}

	@Override
	public Integer updateExhibit(Exhibit exhibit) {
		Assert.notNull(exhibit.getId(),"the id can not be null");
		if(exhibit.getPauseStatus()==null) {
			exhibit.setPauseStatus(0);
		}
		return exhibitDao.updateExhibit(exhibit);
	}

	@Override
	public PageDto<ExhibitDto> pageExhibitByAdmin(String name, Integer status,
			String plateCategoryCode,String recommendType,PageDto<ExhibitDto> page) {
		Assert.notNull(page, "the page can not be null");
		List<ExhibitDto> list = exhibitDao.queryExhibitByAdmin(name, status, plateCategoryCode,recommendType, page);
		for(ExhibitDto dto:list) {
			if(StringUtils.isNotEmpty(dto.getExhibit().getAreaCode())) {
				dto.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, dto.getExhibit().getAreaCode()));
			}
			if(StringUtils.isNotEmpty(dto.getExhibit().getProvinceCode())) {
				dto.setProviceName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, dto.getExhibit().getProvinceCode()));
			}
			if(StringUtils.isNotEmpty(dto.getExhibit().getIndustryCode())) {
				dto.setIndustryCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_EXHIBIT, dto.getExhibit().getIndustryCode()));
			}
			if(StringUtils.isNotEmpty(dto.getExhibit().getPlateCategoryCode())) {
				dto.setPlateCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_EXHIBIT, dto.getExhibit().getPlateCategoryCode()));
			}
		}
		page.setRecords(list);
		page.setTotals(exhibitDao.queryExhibitCountByAdmin(name, status,recommendType, plateCategoryCode));
		return page;
	}
	
	@Override
	public Integer updateStatus(Integer id, Integer status) {
		Assert.notNull(id, "the id can not be null");
		if(status==null) {
			status = 0;
		}
		return exhibitDao.updateStatus(id,status);
	}
	
//	private ExhibitDto buildDto(Exhibit exhibit) {
//		ExhibitDto dto = new ExhibitDto();
//		dto.setExhibit(exhibit);
//		if(StringUtils.isNotEmpty(exhibit.getAreaCode())) {
//			dto.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, exhibit.getAreaCode()));
//		}
//		if(StringUtils.isNotEmpty(exhibit.getProvinceCode())) {
//			dto.setProviceName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, exhibit.getProvinceCode()));
//		}
//		if(StringUtils.isNotEmpty(exhibit.getIndustryCode())) {
//			dto.setIndustryCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_EXHIBIT, exhibit.getIndustryCode()));
//		}
//		if(StringUtils.isNotEmpty(exhibit.getPlateCategoryCode())) {
//			dto.setPlateCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_EXHIBIT, exhibit.getPlateCategoryCode()));
//		}
//		return dto;
//	}

//	@Override
//	public List<Exhibit> queryExhibitByIndustryCode(String industryCode,
//			Integer size) {
//		return exhibitDao.queryExhibitByIndustryCode(industryCode,size);
//	}

	@Override
	public List<Exhibit> queryExhibitByRecommend(String type, Integer size) {
		return exhibitDao.queryExhibitByRecommend(type,size);
	}
}
