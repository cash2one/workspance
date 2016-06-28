package com.ast.ast1949.persist.score;

import java.util.List;

import com.ast.ast1949.domain.score.ScoreSummaryDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.score.ScoreSummaryDto;

public interface ScoreSummaryDao {
 
	/**
	 * 更新积分汇总
	 * @param summary
	 * @return
	 */
	public Integer updateSummary(ScoreSummaryDo summary);
	
	/**
	 * 根据公司编号查询积分汇总
	 * @param companyId 公司编号
	 * @return
	 */
	public ScoreSummaryDo querySummaryByCompanyId(Integer companyId);
	
	/**
	 * 添加一条积分汇总记录
	 * @param summary 
	 * @return
	 */
	public Integer insertSummary(ScoreSummaryDo summary);
	
	/**
	 * 查询出积分总数最多的前N条记录
	 * @param max 查询的记录数，参数不能为空
	 * @return
	 */
	public List<ScoreSummaryDto> queryMostOfUserScore(int max);
	
	/**
	 * 查询积分汇总
	 * @param condictions 查询条件
	 * @param page 分页参数
	 * @return
	 */
	public List<ScoreSummaryDto> querySummaryByCondictions(ScoreSummaryDto condictions, PageDto page);
	
	/**
	 * 统计积分汇总记录总数
	 * @param condictions 查询条件
	 * @return
	 */
	public Integer querySummaryByCondictionsCount(ScoreSummaryDto condictions);
}
 
