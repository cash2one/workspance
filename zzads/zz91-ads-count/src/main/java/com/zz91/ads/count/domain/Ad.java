package com.zz91.ads.count.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告信息实体
 * 
 * @author liuwb
 * 
 */
public class Ad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer positionId;//int(20)'广告位ID，每个广告必需投放到特定的广告位',
	private Integer advertiserId;// int(20)'广告主ID',
	private String adTitle;// varchar(60) '广告标题，可能会在广告展示的时候使用',
	private String adDescription;// varchar(300) 广告详细描述',
	private String adContent;// varchar(300) 广告的内容：广告链接地址（图片，FLASH。。。）
	private String adTargetUrl;// varchar(300) 广告链接的目标URL
	private String onlineStatus;// char(1) '广告上下线状态 Y：表示上线 N：表示下线',
	private Date gmtStart;// datetime '广告生效时间',
	private Date gmtPlanEnd;// datetime 计划下线时间 可以不设置',
	private String remark;// varchar(300) '备注，只是内部查看，不对外公开',
	private String applicant;// varchar(45) '广告申请人：帐号名称',
	private String reviewer;// varchar(45) '广告审核人：帐号名称',
	private String reviewStatus;// char(1)  '广告审核状态 U:表示未审核 Y:表示审核通过 nN:表示不通过',
	private String reviewComment;// varchar(45) '审核说明',
	private String designer;// varchar(45) '广告设计人：帐号名称',
	private String designStatus;// char(1)
	private Date gmtCreated;
	private Date gmtModified;
	
	private Date gmtContentModified;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the positionId
	 */
	public Integer getPositionId() {
		return positionId;
	}
	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	/**
	 * @return the advertiserId
	 */
	public Integer getAdvertiserId() {
		return advertiserId;
	}
	/**
	 * @param advertiserId the advertiserId to set
	 */
	public void setAdvertiserId(Integer advertiserId) {
		this.advertiserId = advertiserId;
	}
	/**
	 * @return the adTitle
	 */
	public String getAdTitle() {
		return adTitle;
	}
	/**
	 * @param adTitle the adTitle to set
	 */
	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}
	/**
	 * @return the adDescription
	 */
	public String getAdDescription() {
		return adDescription;
	}
	/**
	 * @param adDescription the adDescription to set
	 */
	public void setAdDescription(String adDescription) {
		this.adDescription = adDescription;
	}
	/**
	 * @return the adContent
	 */
	public String getAdContent() {
		return adContent;
	}
	/**
	 * @param adContent the adContent to set
	 */
	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}
	/**
	 * @return the adTargetUrl
	 */
	public String getAdTargetUrl() {
		return adTargetUrl;
	}
	/**
	 * @param adTargetUrl the adTargetUrl to set
	 */
	public void setAdTargetUrl(String adTargetUrl) {
		this.adTargetUrl = adTargetUrl;
	}
	/**
	 * @return the onlineStatus
	 */
	public String getOnlineStatus() {
		return onlineStatus;
	}
	/**
	 * @param onlineStatus the onlineStatus to set
	 */
	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	/**
	 * @return the gmtStart
	 */
	public Date getGmtStart() {
		return gmtStart;
	}
	/**
	 * @param gmtStart the gmtStart to set
	 */
	public void setGmtStart(Date gmtStart) {
		this.gmtStart = gmtStart;
	}
	/**
	 * @return the gmtPlanEnd
	 */
	public Date getGmtPlanEnd() {
		return gmtPlanEnd;
	}
	/**
	 * @param gmtPlanEnd the gmtPlanEnd to set
	 */
	public void setGmtPlanEnd(Date gmtPlanEnd) {
		this.gmtPlanEnd = gmtPlanEnd;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the applicant
	 */
	public String getApplicant() {
		return applicant;
	}
	/**
	 * @param applicant the applicant to set
	 */
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	/**
	 * @return the reviewer
	 */
	public String getReviewer() {
		return reviewer;
	}
	/**
	 * @param reviewer the reviewer to set
	 */
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	/**
	 * @return the reviewStatus
	 */
	public String getReviewStatus() {
		return reviewStatus;
	}
	/**
	 * @param reviewStatus the reviewStatus to set
	 */
	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	/**
	 * @return the reviewComment
	 */
	public String getReviewComment() {
		return reviewComment;
	}
	/**
	 * @param reviewComment the reviewComment to set
	 */
	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}
	/**
	 * @return the designer
	 */
	public String getDesigner() {
		return designer;
	}
	/**
	 * @param designer the designer to set
	 */
	public void setDesigner(String designer) {
		this.designer = designer;
	}
	/**
	 * @return the designStatus
	 */
	public String getDesignStatus() {
		return designStatus;
	}
	/**
	 * @param designStatus the designStatus to set
	 */
	public void setDesignStatus(String designStatus) {
		this.designStatus = designStatus;
	}
	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}
	/**
	 * @param gmtCreated the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	/**
	 * @return the gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	/**
	 * @param gmtModified the gmtModified to set
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	/**
	 * @return the gmtContentModified
	 */
	public Date getGmtContentModified() {
		return gmtContentModified;
	}
	/**
	 * @param gmtContentModified the gmtContentModified to set
	 */
	public void setGmtContentModified(Date gmtContentModified) {
		this.gmtContentModified = gmtContentModified;
	}

	

}
