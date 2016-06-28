package com.ast.ast1949.service.spot;

import java.util.List;

import com.ast.ast1949.domain.spot.SpotPromotions;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotPromotionsDto;

/**
 *	author:kongsj
 *	date:2013-3-6
 */
public interface SpotPromotionsService {
	
	final static String CHECK_STATUS_NO_CHECK = "0"; // 未审核
	final static String CHECK_STATUS_PASS = "1"; // 审核通过
	final static String CHECK_STATUS_FAILURE = "2"; // 退回
	
	/**
	 * 根据spot_id
	 * @param spotId
	 * @return
	 */
	public SpotPromotions queryOnePromotions(Integer spotId);
	
	/**
	 * 根据id & company_id
	 */
	public SpotPromotions queryByIdAndCompanyId(Integer id,Integer companyId);
	
	/**
	 * 添加新促销区 现货
	 * @param spotPromotions
	 * @return
	 */
	public Integer addOnePromotions(SpotPromotions spotPromotions);
	
	/**
	 * 修改促销区 现货 信息
	 * @param spotPromotions
	 * @return
	 */
	public Integer editOnePromotions(SpotPromotions spotPromotions);

	/**
	 * 申请现货供求进入促销区
	 * @param spotId
	 * @param productId
	 * @return
	 */
	public Integer applyForPromotions(Integer spotId,Integer productId,Integer companyId);
	
	/**
	 * 分页现实促销现货
	 * @param spotPromotions
	 * @param page
	 * @return
	 */
	public PageDto<SpotPromotionsDto> pagePromotions(SpotPromotions spotPromotions,PageDto<SpotPromotionsDto> page);

	/**
	 * 后台审核促销区产品 通过、退回
	 * @param id
	 * @param status
	 * @return
	 */
	public Integer updateStatusById(Integer id, String status);
	
	/**
	 * 搜索最新促销 
	 * @return
	 */
	public List<SpotPromotionsDto> queryPromotionsBySize(Integer size);
	
	/**
	 * 搜索最新过期的促销
	 * @param size
	 * @return
	 */
	public List<SpotPromotionsDto> queryExpiredPromotionsBySize(Integer size);
}