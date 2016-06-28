package com.ast.ast1949.persist.score;

import java.util.List;

import com.ast.ast1949.domain.score.ScoreGoodsDo;
import com.ast.ast1949.dto.PageDto;

public interface ScoreGoodsDao {

	/**
	 * 查询兑换商品（首页显示）<br/>
	 * 注：商品必须是已可兑换的状态，即开始时间小于当前时间。
	 * @param max 查询条数，参数不能为空。
	 * @param isHome 是否在积分商城首页显示，Y 显示；N 不显示，参数为空则不限制。
	 * @return
	 */
	public List<ScoreGoodsDo> queryIndexScoreGoods(int max, String isHome);
	
	/**
	 * 查询兑换商品（热门商品）<br/>
	 * 注：商品必须是已可兑换的状态，即开始时间小于当前时间。
	 * @param max 查询条数，参数不能为空。
	 * @param isHot 是不是热门商品，Y 是；N 不是，参数为空则不限制。
	 * @return
	 */
	public List<ScoreGoodsDo> queryHotScoreGoods(int max, String isHot);
	
	/**
	 * 按照num_conversion倒序排列，并且开始时间小于当前时间
	 */
	public List<ScoreGoodsDo> queryMostConversionGoods(int max);
	
	/**
	 * 根据类别查询兑换商品<br/>
	 * 按照发布时间倒序排列
	 * @param category 商品类别，参数不能为空。
	 * @param max 查询条数
	 * @return
	 */
//	public List<ScoreGoodsDo> queryScoreGoodsByCategory(Integer category, int max);
	
	/**
	 * 根据类别查询兑换商品(带分页)
	 * @param category 商品类别，参数不能为空。
	 * @param page 分页条件
	 * @return
	 */
	public List<ScoreGoodsDo> queryScoreGoodsByCategory(Integer category, PageDto page);
	
	/**
	 * 根据类别统计查询兑换商品总数
	 * @param category 商品类别
	 * @return
	 */
	public Integer queryScoreGoodsByCategoryCount(Integer category);
	
	/**
	 * 添加一条兑换商品记录
	 * @param goods
	 * @return
	 */
	public Integer insertGoods(ScoreGoodsDo goods);
	
	/**
	 * 更新一条兑换商品记录
	 * @param goods
	 * @return
	 */
	public Integer updateGoodsById(ScoreGoodsDo goods);
	
	/**
	 * 根据编号将商品的兑换数量加一
	 * @param id 商品编号
	 */
	public Integer updateNumConversion(Integer id);
	
	public ScoreGoodsDo queryGoodsById(Integer id);
	
	public Integer deleteGoodsById(Integer id);
}
