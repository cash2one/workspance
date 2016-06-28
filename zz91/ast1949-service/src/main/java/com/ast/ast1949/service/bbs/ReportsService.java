/**
 * Copyright 2010 ASTO
 * All right reserved
 *	Created on 2010-11-12 by luocheng
 */
package com.ast.ast1949.service.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.ReportsDO;
import com.ast.ast1949.dto.PageDto;
/**
 * @author luocheng
 *
 */
public interface ReportsService {
	/**
	 * 添加举报信息
	 * @param reportsDO
	 * @return
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
	public Boolean updateReportsDOCheckstateById(String checkstate,String ids);
	
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
