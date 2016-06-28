/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-11-9.
 */
package com.ast.ast1949.persist.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.ReportsDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public interface ReportsDAO {
	/**
	 * 添加举报信息
	 * @param reportsDO
	 * @return 如果返回值>0则举报成功，否则举报失败
	 */
	public Integer insertReportsDO(ReportsDO reportsDO);
	
	/**
	 * 根据ID删除举报信息
	 * @param id
	 * @return
	 */
	public Integer deleteReportsDOById(Integer id);
	
	/**
	 * 修改举报信息
	 * @param reportsDO
	 * @return
	 */
	public Integer updateReportsDOCheckstateById(String checkstate,Integer ids[]);
	
	/**
	 * 查询举报信息条数
	 * @param pageDto
	 * @param reportsDO
	 * @return
	 */
	public Integer countReportsDO(PageDto pageDto,ReportsDO reportsDO);
	/**
	 * 查询举报信息并分页
	 * @param pageDto
	 * @param reportsDO
	 * @return
	 */
	public List<ReportsDO> queryReportsDO(PageDto pageDto,ReportsDO reportsDO);
}
