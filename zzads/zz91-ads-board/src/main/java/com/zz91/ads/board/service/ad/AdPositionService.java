/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.service.ad;

import java.util.List;

import com.zz91.ads.board.domain.ad.AdPosition;
import com.zz91.ads.board.dto.ExtTreeDto;
import com.zz91.ads.board.dto.ad.AdPositionDto;

/**
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public interface AdPositionService {
 
	/**
	 * 添加一个广告位
	 * 注：name不能为空。
	 */
	public Integer insertAdPosition(AdPosition adPosition);
	
	/**
	 * 删除广告位
	 * 注：id不能为空。
	 */
	public Integer signDelete(Integer id);
	
	/**
	 * 更新广告位
	 * 注：id，name不能为空。
	 */
	public Integer updateAdPosition(AdPosition adPosition);
	
	/**
	 * 根据编号读取指定广告位
	 * 注：id不能为空。
	 *
	 *  
	 */
	public AdPosition queryAdPositionById(Integer id);
	
	/**
	 * 根据父节点读取指定广告位
	 * 注：id不能为空。
	 */
	public List<AdPosition> queryAdPositionByParentId(Integer id);
	
	/**
	 * 根据父节点读取子节点
	 * 注：id 父节点编号，
	 * 要判断节点是否有子节点。
	 * 
	 */
	public List<ExtTreeDto> queryAdPositionNodesByParentId(Integer id);
	
	/**
	 * 读取广告位信息
	 */
	public AdPositionDto queryAdPositionDtoById(Integer id);
	
	/**
	 * 根据编号读取指定广告位 注：id不能为空。
	 */
	public AdPositionDto queryAdPositionDtoByIdForEdit(Integer id);
	
}
 
