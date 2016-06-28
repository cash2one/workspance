/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-24
 */
package com.ast.ast1949.service.information;

import java.util.List;

import com.ast.ast1949.domain.information.ExhibitDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.ExhibitDTO;

/**
 * @author yuyonghui
 * 
 */
public interface ExhibitService {
	/**
	 * 后台分页列表查询所有展会
	 * @param exhibitDTO  不能为空
	 *  pageDto 分页参数 name 按展会名称查询
	 * @return 成功返回List<ExhibitDTO> 否则返回null
	 */
//	public List<ExhibitDTO> queryExhibitByCondition(ExhibitDTO exhibitDTO);

	/**
	 * 后台查询展会记录总数
	 * @param exhibitDTO
	 * name 按展会名称查询
	 * @return 返回记录总数
	 * 
	 */
//	public Integer queryExhibitCountByCondition(ExhibitDTO exhibitDTO);
	
	public PageDto<ExhibitDTO> pageExhibit(ExhibitDO exhibit, PageDto<ExhibitDTO> page);
	
	public List<ExhibitDTO> queryExhibitByPlateCategory(String plateCategory, Integer max);
	
	
    /**
     *      根据展会类别查询展会信息
     * @param plateCategoryCode  不能为空
     * @param exhibitCategoryCode  不能为空
     * @param limitSize    列表显示条数
     * @return  List<ExhibitDO>
     */
	public List<ExhibitDTO> queryExhibit(String plateCategoryCode,String exhibitCategoryCode,Integer limitSize);
	
	/**
	 * 按Id查询线下展会信息 
	 * @param id 不能为空
	 * @return ExhibitDTO
	 */
	public ExhibitDTO queryExhibitById(Integer id);
	/**
	 *   按Id查询线下展会信息
	 * @param id 不能为空
	 * @return ExhibitDO
	 */
	public ExhibitDO selectExhibitById(Integer id);
	/**
	 * 展会修改
	 * @param exhibitDO   不能为空
	 * @return 结果集大于0修改成功 否则修改失败
	 */
	public Integer updateExhibit(ExhibitDO exhibitDO);

	/**
	 * 添加展会
	 * @param exhibitDO    不能为空
	 * @return 结果集大于0添加成功 否则添加失败
	 */
	public Integer insertExhibit(ExhibitDO exhibitDO);
	
	/**
	 * 批量删除线下展会
	 * @param entities 不能为空
	 * @return  影响行数>0 删除成功  反之 删除失败
	 */
	public Integer batchDeleteExhibitById(int[] entities);
	
	public String queryContent(Integer id);
	
	public Integer updateContent(Integer id, String content);
	
	public PageDto<ExhibitDTO> pageExhibitByAdmin(ExhibitDO exhibit, PageDto<ExhibitDTO> page);
	
	public Integer deleteExhibit(Integer id);
	
	public List<ExhibitDTO> queryNewestExhibit(String plateCategoryCode,String exhibitCategoryCode,Integer size);
	/**
	 * 上一篇
	 * @param plateCategory
	 * @param gmtCreated
	 * @return
	 */
	
	public ExhibitDO queryUpNews(String plateCategory,String gmtCreated);
	/**
	 * 下一篇
	 * @param plateCategory
	 * @param gmtCreated
	 * @return
	 */
	public ExhibitDO queryDownNews(String plateCategory,String gmtCreated);
}
