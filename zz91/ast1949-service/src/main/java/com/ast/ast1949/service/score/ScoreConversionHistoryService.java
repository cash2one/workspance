package com.ast.ast1949.service.score;

import java.util.List;

import com.ast.ast1949.domain.score.ScoreConversionHistoryDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.score.ScoreConversionHistoryDto;

public interface ScoreConversionHistoryService {
 
	/**
	 * 查找最近兑换成功的积分兑换记录，按照兑换时间倒序排列
	 * max：null表示获取默认条数据
	 * status：1表示兑换成功，0表示申请中，2表示失败
	 *
	 *  
	 */
	public List<ScoreConversionHistoryDto> queryRecentConversionHistory(Integer max, String status);
	/**
	 * 查找公司的积分兑换记录，按照兑换时间倒序排列
	 * status：null表示查找全部，否则只查找某一状态的积分兑换记录
	 */
	public PageDto<ScoreConversionHistoryDto> pageConversionHistoryByCompanyId(Integer companyId, String status, PageDto<ScoreConversionHistoryDto> page);
	/**
	 * 公司积分兑换申请
	 */
	public Integer insertConversionByCompany(ScoreConversionHistoryDo history);
	public ScoreConversionHistoryDo queryConversionHistoryById(Integer id);
	/**
	 *  
	 */
	public Integer updateConversionSuccess(Integer id, String remark);
	/**
	 *  
	 */
	public Integer updateConversionFailure(Integer id, String remark);
	public PageDto<ScoreConversionHistoryDto> pageConversionHistoryByGoodsId(Integer goodsId, String status, PageDto<ScoreConversionHistoryDto> page);
	public PageDto<ScoreConversionHistoryDto> pageConversionHistoryWithGoods(ScoreConversionHistoryDo conversion, PageDto<ScoreConversionHistoryDto> page);
}
 
