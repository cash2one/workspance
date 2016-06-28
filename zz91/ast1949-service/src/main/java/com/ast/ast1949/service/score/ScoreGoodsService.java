package com.ast.ast1949.service.score;

import java.util.List;

import com.ast.ast1949.domain.score.ScoreGoodsDo;
import com.ast.ast1949.dto.PageDto;

public interface ScoreGoodsService {
 
	/**
	 * 查找明确要求显示在首页的积分商品，并按照开始时间倒序排列
	 * max：null时表示获取默认条记录
	 */
	public List<ScoreGoodsDo> queryIndexScroeGoods(Integer max);
	/**
	 * 查找兑换次数最多的尚未结束的积分商品，按照次数从多到少排序
	 * max：null表示获取默认条数据
	 */
	public List<ScoreGoodsDo> queryMostConversionGoods(Integer max);
	/**
	 * 查找热门商品，并按照开始时间倒序排列
	 * max：null时表示获取默认条记录
	 */
	public List<ScoreGoodsDo> queryHotScoreGoods(Integer max);
	/**
	 * 按照类别查找积分商品，，并按照开始时间倒序排列
	 * max：null时表示获取默认条记录
	 * category：null表示不论类别，查找全部商品
	 */
//	public List<ScoreGoodsDo> queryScoreGoodsByCategory(Integer category, Integer max);
	/**
	 *  
	 */
	public PageDto<ScoreGoodsDo> pageScoreGoodsByCategory(Integer category, PageDto<ScoreGoodsDo>  page);
	/**
	 *  
	 */
	public Integer insertGoods(ScoreGoodsDo goods);
	public Integer updateGoodsById(ScoreGoodsDo goods);
	public ScoreGoodsDo queryGoodsById(Integer id);
	public Integer deleteGoodsById(Integer id);
}
 
