/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.dao.ad;

import com.zz91.ads.board.domain.ad.PositionExactType;

/**
 * 广告位与精确投放关联接口
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface PositionExactTypeDao {

	/**
	 * 添加关联 
	 * 注：position_id，exact_put_id不能为空。
	 */
	public void insertPositionExactType(PositionExactType positionExactAds);

	/**
	 * 删除指定广告位的关联记录 
	 * 注：id 广告位编号，不能为空。
	 */
	public Integer deletePositionExactTypeByPositionId(Integer id);

	/**
	 * 删除指定精确投放的关联记录 注：id : 精确投放编号，不能为空。
	 */
	public Integer deletePositionExactTypeByExactTypeId(Integer id);

	/**
	 * 删除指定关联记录 
	 * 注：positionId 广告位编号， exactPutId : 精确投放编号。
	 * position_id，exact_put_id不能为空。
	 */
	public Integer deletePositionExactTypeByPositionIdAndExactTypeId(Integer adPositionId,
			Integer exactTypeId);

	/**
	 * 统计广告位与精确投放关联数 
	 * 注：positionId 广告位编号， exactPutId : 精确投放编号。
	 * position_id，exact_put_id不能为空。
	 */
	public Integer conut(Integer adPositionId, Integer exactTypeId);
}
