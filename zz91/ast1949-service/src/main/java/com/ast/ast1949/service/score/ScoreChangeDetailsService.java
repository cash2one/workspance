package com.ast.ast1949.service.score;

import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.PageDto;

public interface ScoreChangeDetailsService {
 
	/**
	 * 保存积分变更
	 * 当rule_code为null或空时，使用name和score的值，否则从积分规则表中查找code对应的name和score
	 * 保存完变更详细记录后，要同时变更score_summary对应的公司总分
	 *
	 *  
	 */
	public Integer saveChangeDetails(ScoreChangeDetailsDo details);
	/**
	 * 查找公司的积分变更明细，按照变更时间倒序排列
	 * isPlus：true表示只查加分项，false表示只查减分项，null表示查找全部
	 */
	public PageDto<ScoreChangeDetailsDo> pageChangeDetailsByCompanyId(Integer companyId, Boolean isPlus, PageDto<ScoreChangeDetailsDo> page);
}
 
