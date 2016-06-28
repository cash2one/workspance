package com.zz91.ep.admin.service.exhibit;

import java.util.List;

import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.exhibit.ExhibitDto;


public interface ExhibitService {
	
	public static final int MAX_SIZE = 10;
	
	/**
     * 根据展会模块查找展会列表
     * 查询条件：plateCategoryCode
     */
//	public List<ExhibitDto> queryExhibitByCategory(String plateCategoryCode, Integer max);
	/**
	 * 展会模块所属展会分页
	 * @param categoryCode
	 * @param page
	 */
//	public PageDto<ExhibitDto> pageExhibitByCategory(String plateCategoryCode, PageDto<ExhibitDto> page);
	
	/**
	 * 多条件展会分页
	 * @param page
	 * @return
	 */
	//public PageDto<ExhibitDto> pageExhibitBySearch(Exhibit ex, PageDto<ExhibitDto> page);
//	public PageDto<ExhibitDto> pageExhibitBySearch(Exhibit ex, PageDto<ExhibitDto> page);
	
	/**
	 * 根据ID查找展会详细信息
	 * @param id
	 * @return
	 */
	public Exhibit queryExhibitDetailsById(Integer id);
	
	public Integer createExhibitByAdmin(Exhibit exhibit,String admin);
	
	/**
	 * 更新字段：
	 */
	public Integer updateExhibit(Exhibit exhibit); 
	
	public Integer deleteExhibit(Integer id);
	
	public PageDto<ExhibitDto> pageExhibitByAdmin(String name,Integer status,String industryCode,String recommendType,PageDto<ExhibitDto> page);
	
	public Integer updateStatus(Integer id,Integer status);
	
	//public ExhibitDto buildDto(Exhibit exhibit);
	/**
	 * 根据行业查询展会
	 */
//	public List<Exhibit> queryExhibitByIndustryCode(String industryCode,Integer size);
	
	public List<Exhibit> queryExhibitByRecommend(String type, Integer size);
	
}
 
