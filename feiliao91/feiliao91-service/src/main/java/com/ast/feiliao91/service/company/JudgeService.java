package com.ast.feiliao91.service.company;

import java.util.Map;

import com.ast.feiliao91.domain.company.Judge;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.JudgeDto;

public interface JudgeService {
	
	final static Integer GOOD_TYPE=1; //好评
	final static Integer ORDINARY_TYPE=2; //中评
	final static Integer BAD_TYPE=3; //差评
	final static Integer PIC_TYPE=4; //图片
	
	
	/**
	 * 获取公司信誉
	 * @param companyId
	 * @return
	 */
	public Integer getTradeNum(Integer companyId);
	
	/**
	 * 获取公司所有评价均值 ,加入四射五入逻辑，精确到小数点后1位
	 * @param companyId
	 * @return  goodStar 描述 ; serveStar 服务 ; mailStar 物流;
	 */
	public Map<String, String> getAvgStar(Integer companyId,Integer month);

	/**
	 * 统计好评，中评，差评，带图片的 评论条数方法用于产品最终页
	 * @return map key有如下： goodNum好评数 ,badNum差评数,ordinaryNum 中评数 picNum图片数
	 */
	public Map<String, Integer> countJudgeNumByCondition(Integer goodId);
	/**
	 * 获取评价列表
	 * @param page
	 * @param type
	 * @param companyId
	 * @param targetId
	 * @param goodId
	 * @return
	 */
	public PageDto<JudgeDto> pageJudgeByCondition(PageDto<JudgeDto> page, Integer type, Integer companyId, Integer targetId, Integer goodId);
	/**
	 * 产品信誉（描述相符）
	 * @param companyId
	 * @return
	 */
	public String avgGoodStar(Integer companyId);
    
	/**
	 * 查询评分人数
	 */
	public Integer getcount(Integer companyId);
	/**
	 * 查询各个评价的条数
	 * @param level　评价等级 0 好评　１　中评　２　差评
	 * @param month 表示几个月内的信息条数  1000 为　６个月之前的信息
	 * @param companyId 公司
	 */
	public Integer getEvaluation(Integer month,Integer level, Integer companyId);
	
	/**
	 * 查询买家评价列表
	 * type １　好评　2　中评　３差评　1000　所有评价
	 *  type2 0 所有评价　　１带评价的评价　　２无评价内容
	 */
	public PageDto<JudgeDto> pageJudgeByAll(PageDto<JudgeDto> page, Integer type, Integer companyId,Integer type2);
    /**
     * 获取信用积分
     */
	public Integer countTradeNum(Integer compangyId);
	/**
	 * 查询卖家评价列表
	 * type １　好评　2　中评　３差评　1000　所有评价
	 *  type2 0 所有评价　　１带评价的评价　　２无评价内容
	 */
	public PageDto<JudgeDto> pageJudgeBySellAll(PageDto<JudgeDto> page,Integer type, Integer companyId, Integer type2);
	
	public boolean hasJudgeByOrderId(Integer orderId);
    /**
     * 添加评论
     * @param judge
     * @return
     */
	public Integer insert(Judge judge);

}
