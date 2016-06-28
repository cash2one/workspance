/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.service.ad;

import java.math.BigDecimal;
import java.util.List;

import com.zz91.ads.board.domain.ad.Ad;
import com.zz91.ads.board.domain.ad.AdExactType;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;
import com.zz91.ads.board.dto.ad.ExactTypeDto;

/**
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public interface AdService {
	/**
	 * 添加广告 
	 * 注：position_id 广告位编号不能为空。 
	 * advertiser_id 广告主不能为空。 
	 * ad_title 广告标题不能位。
	 * ad_target_url 目标URL不能为空。 
	 * applicant 广告申请人（帐号名称）不能为空。 添加精确投放。
	 */
	public Integer applyAd(Ad ad);

	/**
	 * 删除一条广告信息 
	 * 注：同时要删除关联的素材信息、精确投放信息。
	 */
	public Integer deleteAdById(Integer id);

	/**
	 * 分页读取广告信息 
	 * 注：可按广告位，标题，广告主，开始时间，结束时间 申请人，审核状态，设计者查询
	 */
	public Pager<AdDto> pageAdByConditions(Ad ad, AdSearchDto adSearch, Pager<AdDto> pager);

	/**
	 * 保存并审核广告信息 
	 * 注：修改广告信息，并设置review_status=REVIEW_STATUS_TRUE
	 */
	public Integer saveAndCheck(Ad ad);

	/**
	 * 仅保存广告信息 
	 * 注：设置review_status=null，或不设置。
	 */
	public Integer saveOnly(Ad ad);

	/**
	 * 通过审核 
	 * 注：设置review_status=REVIEW_STATUS_TRUE 发送邮件给申请人。
	 */
	public Integer pass(Integer id, String reviewer);

	/**
	 * 退回 
	 * 注：设置review_status=REVIEW_STATUS_FALSE
	 */
	public Integer back(Integer id, String reviewer);

	/**
	 * 广告上线 
	 * 注：online_status=ONLINE_STATUS_TRUE
	 */
	public Integer setOnline(Integer id);

	/**
	 * 广告下线 
	 * 注：online_status=ONLINE_STATUS_FALSE
	 */
	public Integer setOffline(Integer id);

	/**
	 * 保存广告设计 
	 * 注：发送邮件给提交者和审核者。
	 */
	public Integer saveAndDesign(Ad ad);

	/**
	 * 完成广告设计 
	 * 注：更新设计状态design_status=DESIGN_STATUS_TRUE 发送邮件给提交者和审核者
	 */
	public Integer finishAdByDesigner(Ad ad);

	/**
	 * 指派
	 */
	public Integer appoint(Integer id, String designer);

	/**
	 * 读取指定广告
	 */
	public Ad queryAdById(Integer id);

	/**
	 * 添加关联 
	 * 注：ad_id 广告编号不能为空。 
	 * exact_put_id 精确定位的类型编号不能为空。 
	 * anchor_point 锚点（定位条件）不能为空。
	 */
	public Integer insertAdExactType(AdExactType adExactType);

	/**
	 * 提交设计
	 */
	public Integer sendToDesign(Integer id);
	
	public List<ExactTypeDto> queryAdExact(Integer aid);
	
	public Integer queryPositionOfAd(Integer aid);
	
	public Integer deleteAdExactTypeById(Integer id);
	
	public Integer updateSequence(BigDecimal sequence, Integer id);
	
	public Integer updatePosition(Integer id, Integer positionId);
	
	public Integer removeRent(Integer id);
	
}
