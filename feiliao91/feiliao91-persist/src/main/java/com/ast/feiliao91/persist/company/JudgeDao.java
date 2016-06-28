package com.ast.feiliao91.persist.company;

import java.util.Map;
import java.util.List;

import com.ast.feiliao91.domain.company.Judge;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.JudgeDto;

public interface JudgeDao {

	public Judge queryById(Integer id);
	
	public Judge queryByOrderId(Integer orderId);

	public Integer insert(Judge judge);

	public Integer countTradeNum(Integer companyId);
	/**
	 * 某产品的评价数
	 * @param goodId
	 * @return
	 */
	public Integer countJudgeByGoodId(Integer goodId);
	

	public String avgGoodStar(Integer companyId);

	public Map<String, String> avgByCondition(Integer companyId, Integer month);

	public Integer countJudgeNumByCondition(Integer goodId, Integer type);
	/**
	 * 评价列表
	 * @param page
	 * @param type
	 * @param companyId
	 * @param targetId
	 * @param goodId
	 * @return
	 */
	public List<Judge> queryJudgeByCondition(PageDto<JudgeDto> page, Integer type, Integer companyId, Integer targetId, Integer goodId);
	/**
	 * 评价列表总条数
	 * @param type
	 * @param companyId
	 * @param targetId
	 * @param goodId
	 * @return
	 */
	public Integer countJudgeNumByCondition(Integer type, Integer companyId, Integer targetId, Integer goodId);
    /**
     * 查询评价人数
     * @param companyId
     * @return
     */
	public Integer getcount(Integer companyId);
    /**
     * 查询各个评价条数
     * @param month　几个月内的信息
     * @param level　0 好评 1 中平　２差评
     * @return
     */
	public Integer getEvaluation(Integer month, Integer level,Integer companyId);

	public Integer getEvaluationTwo(Integer month, Integer level, Integer companyId);

	public List<Judge> pageJudgeByAll(PageDto<JudgeDto> page, Integer type,
			Integer companyId, Integer type2);

	public Integer pageJudgeByAllcount(Integer type, Integer companyId, Integer type2);
	/**
	 * 查询卖家信誉分
	 * @param compangyId
	 * @return
	 */
	public Integer countSellNum(Integer compangyId);

	public List<Judge> pageJudgeBySellAll(PageDto<JudgeDto> page, Integer type,
			Integer companyId, Integer type2);

	public Integer pageJudgeBySellAllCount(Integer type, Integer companyId,
			Integer type2);

}
