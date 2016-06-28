package com.ast.ast1949.service.spot;

import com.ast.ast1949.domain.spot.SpotInfo;

public interface SpotInfoService {
	
	/**
	 * 检索一条现货详细信息
	 * @param spotId
	 * @return
	 */
	public SpotInfo queryOneSpotInfo(Integer spotId);
	
	/**
	 * 创建新的现货信息或者更新现货信息
	 * 逻辑：1、先检索信息，存在则更新 ；不存在则插入新记录
	 * @param spotInfo
	 * @return
	 */
	public Integer addOrEditOneSpotInfo(SpotInfo spotInfo);

}
