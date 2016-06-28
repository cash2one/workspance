/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-7
 */
package com.ast.ast1949.service.information;

import java.util.List;

import com.ast.ast1949.domain.information.WeeklyPageDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.WeeklyDTO;

/**
 * @author yuyonghui
 *
 */
public interface WeeklyPageService {

	/**
	 *   分页列表显示所有版面
	 * @param page  不能为空
	 * @return  List<WeeklyPageDO>
	 */
//	public List<WeeklyPageDO> listWeeklyPageByPage(PageDto page);

	/**
	 *   根据期刊查看对应的版面
	 * @param periodicalId  不能为空
	 * @return List<WeeklyPageDO>
	 * 
	 */ 
	public List<WeeklyPageDO> listWeeklyPageByPeriodicalId(Integer periodicalId);
	/**
	 *   根据ID 查询版面信息
	 * @param id 不能为空
	 * @return WeeklyPageDO
	 */
	public WeeklyPageDO listWeeklyPageById(Integer id);
	
	/**
	 *    期刊添加
	 * @param weeklyPageDO 不能为空
	 * @return  结果 >0 添加成功，反之添加失败
	 */
	public Integer insertWeeklyPage(WeeklyPageDO weeklyPageDO);
	/**
	 *   修改期刊
	 * @param weeklyPageDO 不能为空
	 * @return  结果>0  修改成功 反之失败
	 */
	public Integer updateWeeklyPage(WeeklyPageDO weeklyPageDO);
	
	/**
	 *   删除期刊
	 * @param entities 不能为空
	 * @return  结果>0 删除成功
	 *          否则删除失败
	 */
	public Integer batchDeleteWeeklyPageById(int[] entities);
	
	/**
	 *  	查询上一版面
	 * @param id 不能为空
	 * @return WeeklyPageDO
	 */
	public WeeklyPageDO listOnWeeklyPageById(Integer id);
	/**
	 * 		查询下一版面
	 * @param id 不能为空
	 * @return WeeklyPageDO
	 */
	public WeeklyPageDO listDownWeeklyPageById(Integer id);
	
	/**
	 *  查询版面和期刊名
	 * @param pageId 不能为空
	 * @return WeeklyDTO
	 */
	public WeeklyDTO listPerdicalAndPageById(Integer pageId);
}
