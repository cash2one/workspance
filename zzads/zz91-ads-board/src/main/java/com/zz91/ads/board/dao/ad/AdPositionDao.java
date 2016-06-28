/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.dao.ad;

import java.util.List;

import com.zz91.ads.board.domain.ad.AdPosition;
import com.zz91.ads.board.dto.ad.AdPositionDto;

/**
 * 广告位接口
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface AdPositionDao {

	final static Integer DEF_PARENT_ID = 0;
	final static Integer HAS_EXACT_AD_FALSE = 0;

	/**
	 * 添加一个广告位 注：name不能为空。
	 */
	public Integer insertAdPosition(AdPosition adPosition);

	/**
	 * 删除广告位 注：id不能为空。
	 */
	public Integer signDelete(Integer id);

	/**
	 * 更新广告位 注：id，name不能为空。
	 */
	public Integer updateAdPosition(AdPosition adPosition);

	/**
	 * 根据编号读取指定广告位 注：id不能为空。
	 */
	public AdPosition queryAdPositionById(Integer id);

	/**
	 * 根据父节点读取指定广告位 注：id不能为空。
	 */
	public List<AdPosition> queryAdPositionByParentId(Integer id);

	/**
	 * 读取广告位信息
	 */
	public AdPositionDto queryAdPositionDtoById(Integer id);

	/**
	 * 根据父节点统计子节点个数
	 */
	public Integer countAdPositionNodesByParentId(Integer id);
	/**
	 * 根据编号读取指定广告位 注：id不能为空。
	 */
	public AdPositionDto queryAdPositionDtoByIdForEdit(Integer id);
}
