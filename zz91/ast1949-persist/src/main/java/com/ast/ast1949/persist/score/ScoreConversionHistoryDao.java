package com.ast.ast1949.persist.score;

import java.util.List;

import com.ast.ast1949.domain.score.ScoreConversionHistoryDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.score.ScoreConversionHistoryDto;

public interface ScoreConversionHistoryDao {
 
	/**
	 * 查询最近积分兑换历史
	 * @param max 查询条数，参数不能为空。
	 * @param status 兑换状态，0 申请中;1 成功;2 失败
	 * @return
	 */
	public List<ScoreConversionHistoryDto> queryRecentConversionHistory(int max, String status);
	
	/**
	 * 查询公司的积分兑换历史
	 * @param companyId 公司编号，参数不能为空。
	 * @param status 兑换状态，0 申请中;1 成功;2 失败,参数为空不限制。
	 * @param page 分页参数
	 * @return
	 */
	public List<ScoreConversionHistoryDto> queryConversionHistoryByCompanyId(Integer companyId, String status, PageDto page);
	
	/**
	 * 统计公司的积分兑换历史记录总数
	 * @param companyId 公司编号，参数不能为空。
	 * @param status 兑换状态，0 申请中;1 成功;2 失败。参数为时状态不限。
	 * @return
	 */
	public Integer queryConversionHistoryByCompanyIdCount(Integer companyId, String status);
	
	/**
	 * 添加一条积分兑换历史记录
	 * @param conversion
	 * @return
	 */
	public Integer insertConversionHistoryByCompany(ScoreConversionHistoryDo conversion);
	
	/**
	 * 根据编号查询一条积分兑换历史
	 * @param id 编号，参数不能为。
	 * @return
	 */
	public ScoreConversionHistoryDo queryConversionHistoryById(Integer id);
	
	/**
	 * 更新兑换状态
	 * @param id 编号，参数不能为。
	 * @param status 兑换状态，参数不能为。
	 * @param remark 备注信息
	 * @return
	 */
	public Integer updateConversionHistoryStatusById(Integer id, String status, String remark);
	
	/**
	 * 根据物品/服务编号查询兑换历史
	 * @param goodsId 物品/服务编号，参数不能为空。
	 * @param status 兑换状态，0 申请中;1 成功;2 失败。参数为时状态不限。
	 * @param page 分页参数
	 * @return
	 */
	public List<ScoreConversionHistoryDto> queryConversionHistoryByGoodsId(Integer goodsId, String status, PageDto page);
	
	/**
	 * 根据物品/服务编号统计查询兑换历史总数
	 * @param goodsId 参数不能为空。
	 * @param status 兑换状态，0 申请中;1 成功;2 失败。参数为时状态不限。
	 * @return
	 */
	public Integer queryConversionHistoryByGoodsIdCount(Integer goodsId, String status);
	
	/**
	 * 查询积分兑换历史（带有物品/服务信息）
	 * @param conversion 查询条件
	 * @param page 分页参数
	 * @return
	 */
	public List<ScoreConversionHistoryDto> queryConversionHistoryWithGoods(ScoreConversionHistoryDo conversion, PageDto page);
	
	/**
	 * 统计积分兑换历史总数
	 * @param conversion 查询条件
	 * @return
	 */
	public Integer queryConversionHistoryWithGoodsCount(ScoreConversionHistoryDo conversion);
}
 
