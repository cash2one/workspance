/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.service.ad;

import com.zz91.ads.board.domain.ad.PositionExactType;

/**
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public interface PositionExactTypeService {

	/**
	 * 添加关联 
	 * 注：先判断记录已存在，若不存则添加一条记录， 并返回true；若已存在，则直接返回ture。
	 * position_id，exact_put_id不能为空。
	 */
	public Boolean savePositionExactType(PositionExactType positionExactType);

	/**
	 * 删除指定广告位的关联记录 
	 * 注：id 广告位编号，不能为空。
	 */
	public Integer deletePositionExactTypeByPositionId(Integer id);

	/**
	 * 删除指定精确投放的关联记录 
	 * 注：id : 精确投放编号，不能为空。
	 */
	public Integer deletePositionExactTypeByExactTypeId(Integer id);

	/**
	 * 删除指定关联记录 
	 * 注：adPositionId 广告位编号， exactTypeId : 精确投放编号。
	 * adPositionId，exactTypeId不能为空。
	 */
	public Integer deletePositionExactTypeByPositionIdAndExactTypeId(Integer adPositionId,
			Integer exactTypeId);
}
