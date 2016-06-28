package com.ast.ast1949.persist.score;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.PageDto;

public interface ScoreChangeDetailsDao {

	/**
	 * 查询指定公司在某周期内积分变更总数
	 * @param companyId 公司编号，参数不能为空
	 * @param ruleCode 规则Code值
	 * @param startTime 开始时间
	 * @return
	 */
	public Integer queryScoreByCycle(Integer companyId, String ruleCode,
			Date startTime);

	/**
	 * 添加一条积分变更记录
	 * 
	 * @param details
	 * @return
	 */
	public Integer insertScoreDetails(ScoreChangeDetailsDo details);

	/**
	 * 查询积分变更记录
	 * @param companyId 公司编号
	 * @param isPlus 是否加分项，true 是；false 否,参数为空则不限制。
	 * @param page 分页参数
	 * @return
	 */
	public List<ScoreChangeDetailsDo> queryChangeDetails(Integer companyId,
			Boolean isPlus, PageDto page);

	/**
	 * 查询公司加/减的积分总数
	 * @param companyId 公司编号
	 * @param isPlus 是否加分项，true 是；false 否,参数为空则不限制。
	 * @return
	 */
	public Integer queryChangeDetailsCount(Integer companyId, Boolean isPlus);

	/**
	 * 删除积分变更记录
	 * @param companyId 公司编号,参数不能为空。
	 * @param ruleCode 规则code值
	 * @param relatedId 相关ID
	 * @return
	 */
	public Integer deleteChangeDetailsByRelatedId(Integer companyId,
			String ruleCode, Integer relatedId);
}
