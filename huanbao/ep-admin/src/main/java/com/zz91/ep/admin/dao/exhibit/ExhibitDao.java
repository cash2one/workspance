package com.zz91.ep.admin.dao.exhibit;

import java.util.List;

import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.exhibit.ExhibitDto;

public interface ExhibitDao {

	/**
	 * 多条件查找展会 查询条件：name
	 * ，organizers，industryCode，plateCategoryCode，provinceCode，
	 * areaCode，startTime，endTime 返回展会列表
	 */
//	public List<Exhibit> queryExhibitBySearch(Exhibit exhibit,
//			PageDto<ExhibitDto> page);

	/**
	 * 查找展会数量 查询条件：name
	 * ，organizers，industryCode，plateCategoryCode，provinceCode，areaCode
	 * ，startTime，endTime 返回展会数量
	 */
//	public Integer queryExhibitBySearchCount(Exhibit exhibit);

	/**
	 * 根据展会ID查询展会信息
	 */
	public Exhibit queryExhibitDetailsById(Integer id);

	/**
	 * 根据展会模块查找展会
	 * 
	 * 查询条件：plateCategoryCode
	 */
//	public List<Exhibit> queryExhibitByPlateCategory(String plateCategoryCode,
//			PageDto<ExhibitDto> page);

	/**
	 * 根据展会模块查找展会该行业展会数量 查询条件：plateCategoryCode
	 */
//	public Integer queryExhibitByPlateCategoryCount(String plateCategoryCode);

	public Integer insertExhibit(Exhibit exhibit);

	public Integer updateExhibit(Exhibit exhibit);

	public Integer deleteExhibit(Integer id);

	public List<ExhibitDto> queryExhibitByAdmin(String name, Integer status,
			String industryCode, String recommendType, PageDto<ExhibitDto> page);

	public Integer queryExhibitCountByAdmin(String name, Integer status,
			String industryCode, String recommendType);

	public Integer updateStatus(Integer id, Integer status);

//	public Integer queryExhibitById(Integer id);

//	public List<Exhibit> queryExhibitByCategory(String plateCategoryCode,
//			Integer max);

//	public List<Exhibit> queryExhibitByIndustryCode(String industryCode,
//			Integer size);

	public List<Exhibit> queryExhibitByRecommend(String type, Integer size);

}
