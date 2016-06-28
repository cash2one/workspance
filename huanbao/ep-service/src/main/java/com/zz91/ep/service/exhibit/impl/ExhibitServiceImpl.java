/*
 * 文件名称：ExhibitServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.exhibit.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Service;

import com.zz91.ep.dao.exhibit.ExhibitDao;
import com.zz91.ep.dao.trade.PhotoDao;
import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.exhibit.ExhibitDto;
import com.zz91.ep.service.exhibit.ExhibitService;
import com.zz91.util.Assert;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.lang.StringUtils;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：展会信息Service实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("exhibitService")
public class ExhibitServiceImpl implements ExhibitService {
	
	@Resource
	private ExhibitDao exhibitDao;
    
	
	
	@Override
	public List<Exhibit> queryExhibitsByRecommend(String categoryCode, Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		return exhibitDao.queryExhibitsByRecommend(categoryCode, size);
	}

	@Override
	public List<Exhibit> queryExhibitsByCategory(String categoryCode, String industryCode, Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		return exhibitDao.queryExhibitsByCategory(categoryCode, industryCode, size);
	}

	@Override
	public List<ExhibitDto> queryByCategory(String categoryCode,
			Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		List<Exhibit> list= exhibitDao.queryByCategory(categoryCode, size);
		List<ExhibitDto> resultList=new ArrayList<ExhibitDto>();
		for(Exhibit e:list){
			resultList.add(buildDto(e));
		}
		return resultList;
	}

	private ExhibitDto buildDto(Exhibit exhibit) {
		ExhibitDto dto=new ExhibitDto();
		
		if(StringUtils.isNotEmpty(exhibit.getAreaCode())) {
			dto.setAreaName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, exhibit.getAreaCode()));
		}
		if(StringUtils.isNotEmpty(exhibit.getProvinceCode())) {
			dto.setProviceName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_AREA, exhibit.getProvinceCode()));
		}
		if(StringUtils.isNotEmpty(exhibit.getIndustryCode())) {
			dto.setIndustryCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_EXHIBIT, exhibit.getIndustryCode()));
		}
		if(StringUtils.isNotEmpty(exhibit.getPlateCategoryCode())) {
			dto.setPlateCategoryName(CodeCachedUtil.getValue(CodeCachedUtil.CACHE_TYPE_EXHIBIT, exhibit.getPlateCategoryCode()));
		}
		dto.setExhibit(exhibit);
		return dto;
	}

	@Override
	public PageDto<ExhibitDto> pageExhibitByCategory(String categoryCode, PageDto<ExhibitDto> page) {
		if(categoryCode==null) {
			return null;
		}
		page.setSort("gmt_sort");
		page.setDir("desc");
		List<Exhibit> list = exhibitDao.queryByPlateCategory(categoryCode, page);
		List<ExhibitDto> resultList = new ArrayList<ExhibitDto>();
		for(Exhibit e:list){
			resultList.add(buildDto(e));
		}
		page.setRecords(resultList);
		page.setTotals(exhibitDao.queryByPlateCategoryCount(categoryCode));
		return page;
	}

	@Override
	public Exhibit queryExhibitDetailsById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return exhibitDao.queryExhibitDetailsById(id);
	}

	@Override
	public List<ExhibitDto> queryExhibitsByCategoryCode(String categoryCode,
			String industryCode, Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		List<ExhibitDto> extDtoList=new ArrayList<ExhibitDto>();
		
		List<Exhibit> extList=exhibitDao.queryExhibitsByCategory(categoryCode, industryCode, size);
		for (Exhibit exhibit : extList) {
			ExhibitDto extDto=new ExhibitDto();
			if(exhibit!=null){
			    extDto.setExhibit(exhibit);
			    if(exhibit.getDetails().indexOf("<img")!=-1){
			    		extDto.setOrPhoto("1");
			    }
			    extDto.getExhibit().setDetails(Jsoup.clean(extDto.getExhibit().getDetails(),
							Whitelist.none()));
			}
			  extDtoList.add(extDto);
		}
		
		return extDtoList;
		
	}
}