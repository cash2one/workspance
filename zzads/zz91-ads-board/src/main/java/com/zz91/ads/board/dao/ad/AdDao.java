/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.dao.ad;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.zz91.ads.board.domain.ad.Ad;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;
import com.zz91.ads.board.dto.ad.ExactTypeDto;

/**
 * 广告接口
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface AdDao {
	/**
	 * 未审核
	 */
	static final String REVIEW_STATUS_UN = "U";

	/**
	 * 审核通过
	 */
	static final String REVIEW_STATUS_TRUE = "Y";

	/**
	 * 不通过
	 */
	static final String REVIEW_STATUS_FALSE = "N";

	/**
	 * 广告上线
	 */
	static final String ONLINE_STATUS_TRUE = "Y";

	/**
	 * 广告下线
	 */
	static final String ONLINE_STATUS_FALSE = "N";

	/**
	 * 设计状态-完成
	 */
	static final String DESIGN_STATUS_FINISH = "Y";
	/**
	 * 设计状态-未设计
	 */
	static final String DESIGN_STATUS_U = "U";

	/**
	 * 添加广告 注：position_id 广告位编号不能为空。 
	 * advertiser_id 广告主不能为空。 
	 * ad_title 广告标题不能位。
	 * ad_target_url 目标URL不能为空。 
	 * applicant 广告申请人（帐号名称）不能为空。
	 */
	public Integer insertAd(Ad ad);

	/**
	 * 删除一条广告信息
	 */
	public Integer deleteAdById(Integer id);

	/**
	 * 分页读取广告信息 
	 * 注：可按广告位，标题，广告主，开始时间，结束时间 申请人，审核状态，设计者查询
	 */
	public List<AdDto> queryAdByConditions(Ad ad, AdSearchDto adSearch, Pager<AdDto> pager);

	public Integer queryAdByConditionsCount(Ad ad, AdSearchDto adSearch);

	/**
	 * 修改广告信息 
	 * 注：reviewStatus为空则不修修改review_status字段。
	 */
	public Integer updateAd(Ad ad);

	/**
	 * 保存设计 
	 * 注：design_status值为空时不更新此字段。
	 */
	public Integer updateAdByDesigner(Ad ad);

	/**
	 * 修改审核状态 
	 * 注： 广告审核状态(U:表示未审核; Y:表示审核通过; N:表示不通过.)
	 */
	public Integer updateReviewStatus(Integer id, String status, String reviewer);

	/**
	 * 修改广告上线状态
	 */
	public Integer updateOnlineStatus(Integer id, String status);

	/**
	 * 修改广告设计状态
	 */
	public Integer updateDesignStatus(Integer id, String status);

	/**
	 * 读取指定广告
	 */
	public Ad queryAdById(Integer id);
	/**
	 * 更新设计者
	 */
	public Integer updateDesigner(Integer id, String designer);
	
	public List<ExactTypeDto> queryAdExact(Integer aid);
	
	public Integer queryPositionOfAd(Integer aid);
	
	public Integer deleteAdExactTypeById(Integer id);
	
	public Integer updateSearchExact(Integer id, String searchExact);
	
	public Integer updateSequence(BigDecimal sequence, Integer id);
	
	public Integer updatePosition(Integer id, Integer positionId);
	
	public Integer countExistsAd(Date gmtPlanEnd, String keywords, Integer positionId);
	
	public Integer updateRent(Integer id, String rent);
}
