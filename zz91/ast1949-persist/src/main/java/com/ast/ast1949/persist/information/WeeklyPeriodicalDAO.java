/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-9-7
 */
package com.ast.ast1949.persist.information;

import java.util.List;

import com.ast.ast1949.domain.information.WeeklyPeriodicalDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author yuyonghui
 *
 */
public interface WeeklyPeriodicalDAO {
   
	/**
	 *   分页显示所有期刊
	 * @param page  不能为空
	 * @return  List<WeeklyPeriodicalDO>
	 */
	public List<WeeklyPeriodicalDO> listWeeklyPeriodicalByPage(PageDto page);
	/**
	 *   获取期刊记录总数
	 * @return  记录总数
	 * 
	 */
	public Integer countWeeklyPeriodical();
	/**
	 *  根据Id 查询期刊信息
	 * @param id 不能为空
	 * @return WeeklyPeriodicalDO
	 */
	public WeeklyPeriodicalDO listWeeklyPeriodicalById(Integer id);
	/**
	 *  查询所有期刊
	 * @return List<WeeklyPeriodicalDO>
	 */
	public List<WeeklyPeriodicalDO> listAllWeeklyPeriodical();
	/**
	 *    添加期刊信息
	 * @param weeklyPeriodicalDO 不能为空
	 * @return 如果>0 添加成功
	 *         否则添加失败
	 */
	public Integer insertWeeklyPeriodical(WeeklyPeriodicalDO weeklyPeriodicalDO);
	/**
	 *    修改期刊信息
	 * @param weeklyPeriodicalDO 不能为空
 	 * @return 如果>0 修改成功
	 *         否则修改失败
	 */
	public Integer updateWeeklyPeriodical(WeeklyPeriodicalDO weeklyPeriodicalDO);
	/**
	 *   删除期刊
	 * @param entities 不能为空
	 * @return  结果>0 删除成功
	 *          否则删除失败
	 */
	public Integer deleteWeeklyPeriodicalById(int[] entities);
	/**
	 * 根据发布时间最晚的 查询排在第一的期刊 
	 * @return WeeklyPeriodicalDO
	 */
	public WeeklyPeriodicalDO listFirstWeeklyPeriodical();
	/**
	 *   查询上一期刊
	 * @param id 不能为空
	 * @return WeeklyPeriodicalDO
	 */
	public WeeklyPeriodicalDO listOnWeeklyPeriodical(Integer id);
	/**
	 *  查询下一期刊
	 * @param id 不能为空
	 * @return WeeklyPeriodicalDO
	 */
	public WeeklyPeriodicalDO listDownWeeklyPeriodical(Integer id);
}
