package com.ast.ast1949.persist.score;

import java.util.List;

import com.ast.ast1949.domain.score.ScoreExchangeRulesDo;
import com.ast.ast1949.dto.PageDto;

public interface ScoreExchangeRulesDao {
 
	/**
	 * 根据规则Code值查询积分兑换规则
	 * @param code 规则Code值，参数不能为空。 
	 * @return
	 */
	public ScoreExchangeRulesDo queryRulesByCode(String code);
	
	/**
	 * 根据规则Code值前缀查询积分兑换规则
	 * @param preCode 规则Code值前缀，参数不能为空。 
	 * @return
	 */
	public List<ScoreExchangeRulesDo> queryRulesByPreCode(String preCode);
	/**
	 * 查询规则列表（带分页）
	 * @param page
	 * @return
	 */
	public List<ScoreExchangeRulesDo> queryRules(PageDto<ScoreExchangeRulesDo> page);
	/**
	 * 统计规则记录总数
	 * @param page
	 * @return
	 */
	public Integer queryRulesCount(PageDto<ScoreExchangeRulesDo> page);
}
 
