/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-17 by liulei
 */
package com.ast.ast1949.dto.bbs;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.bbs.BbsPostCategoryDO;
import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author liulei
 *
 */
public class BbsPostDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PageDto pageDto;				//分页信息
	List<BbsPostDTO> list;
	private BbsPostDO bbsPost;				//主贴信息
	private BbsPostReplyDO bbsPostReply;	//主贴回复信息
	private BbsPostCategoryDO bbsPostCategory; //板块
	private BbsUserProfilerDO bbsUserProfiler; //个人基本信息
	
	private Integer postId;					//主贴ID
	private String account;					//账号名
	
	private Integer topicsId;				//话题ID
	
	private Integer tagsId;					//标签ID
	private String tagsName;				//标签名
	
	private Integer categoryId;				//模块ID
	
	private Integer userProfilerId;			//个人基本信息ID
	private String nickname;				//昵称
	private String categoryName;            //模块类别名
	private String picturePath;				//个人头像路径
	private Integer userPostNumber;			//用户发帖数
	private Integer userReplyNumber;		//用户回帖数
	private Integer userDailyIntegral;		//用户每日积分
	private Integer userTotalIntegral;		//用户总积分
	
	private Integer bbsPostReplyId;			//回复主贴信息ID
	private String replyTitle;				//回复主贴标题 
	private String replyCheckStatus;		//回复主贴状态
	private Date replyGmtModified;		//回复主贴修改日期
	
	private String title;					//帖子标题
	private Integer postBrowseCount;        //帖子访问数 
	private Integer visitedCount;			//访问数
	private Integer replyCount;            	//回帖数
	private String signType;				
	private Date postTime;					//发帖时间
	private String postType;				//帖子类型
	private String content;
	
	private String signType1;
	private String signType2;
	private String signType3;
	private String signType4;
	
	private String filePath;
	
	private String membershipCode;			//客户会员类型编号
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPostBrowseCount() {
		return postBrowseCount;
	}

	public void setPostBrowseCount(Integer postBrowseCount) {
		this.postBrowseCount = postBrowseCount;
	}

	public Integer getVisitedCount() {
		return visitedCount;
	}

	public void setVisitedCount(Integer visitedCount) {
		this.visitedCount = visitedCount;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public PageDto getPageDto() {
		return pageDto;
	}

	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}

	public BbsPostDO getBbsPost() {
		return bbsPost;
	}

	public void setBbsPost(BbsPostDO bbsPost) {
		this.bbsPost = bbsPost;
	}

	public BbsPostReplyDO getBbsPostReply() {
		return bbsPostReply;
	}

	public void setBbsPostReply(BbsPostReplyDO bbsPostReply) {
		this.bbsPostReply = bbsPostReply;
	}

	public BbsPostCategoryDO getBbsPostCategory() {
		return bbsPostCategory;
	}

	public void setBbsPostCategory(BbsPostCategoryDO bbsPostCategory) {
		this.bbsPostCategory = bbsPostCategory;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getTopicsId() {
		return topicsId;
	}

	public void setTopicsId(Integer topicsId) {
		this.topicsId = topicsId;
	}

	public BbsUserProfilerDO getBbsUserProfiler() {
		return bbsUserProfiler;
	}

	public void setBbsUserProfiler(BbsUserProfilerDO bbsUserProfiler) {
		this.bbsUserProfiler = bbsUserProfiler;
	}

	public Integer getTagsId() {
		return tagsId;
	}

	public void setTagsId(Integer tagsId) {
		this.tagsId = tagsId;
	}

	public String getTagsName() {
		return tagsName;
	}

	public void setTagsName(String tagsName) {
		this.tagsName = tagsName;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getUserProfilerId() {
		return userProfilerId;
	}

	public void setUserProfilerId(Integer userProfilerId) {
		this.userProfilerId = userProfilerId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public Integer getUserPostNumber() {
		return userPostNumber;
	}

	public void setUserPostNumber(Integer userPostNumber) {
		this.userPostNumber = userPostNumber;
	}

	public Integer getUserReplyNumber() {
		return userReplyNumber;
	}

	public void setUserReplyNumber(Integer userReplyNumber) {
		this.userReplyNumber = userReplyNumber;
	}

	public Integer getUserDailyIntegral() {
		return userDailyIntegral;
	}

	public void setUserDailyIntegral(Integer userDailyIntegral) {
		this.userDailyIntegral = userDailyIntegral;
	}

	public Integer getUserTotalIntegral() {
		return userTotalIntegral;
	}

	public void setUserTotalIntegral(Integer userTotalIntegral) {
		this.userTotalIntegral = userTotalIntegral;
	}

	public Integer getBbsPostReplyId() {
		return bbsPostReplyId;
	}

	public void setBbsPostReplyId(Integer bbsPostReplyId) {
		this.bbsPostReplyId = bbsPostReplyId;
	}

	public String getReplyTitle() {
		return replyTitle;
	}

	public void setReplyTitle(String replyTitle) {
		this.replyTitle = replyTitle;
	}

	public String getReplyCheckStatus() {
		return replyCheckStatus;
	}

	public void setReplyCheckStatus(String replyCheckStatus) {
		this.replyCheckStatus = replyCheckStatus;
	}


	public Date getReplyGmtModified() {
		return replyGmtModified;
	}

	public void setReplyGmtModified(Date replyGmtModified) {
		this.replyGmtModified = replyGmtModified;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getSignType1() {
		return signType1;
	}

	public void setSignType1(String signType1) {
		this.signType1 = signType1;
	}

	public String getSignType2() {
		return signType2;
	}

	public void setSignType2(String signType2) {
		this.signType2 = signType2;
	}

	public String getSignType3() {
		return signType3;
	}

	public void setSignType3(String signType3) {
		this.signType3 = signType3;
	}

	public String getSignType4() {
		return signType4;
	}

	public void setSignType4(String signType4) {
		this.signType4 = signType4;
	}

	public List<BbsPostDTO> getList() {
		return list;
	}

	public void setList(List<BbsPostDTO> list) {
		this.list = list;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMembershipCode() {
		return membershipCode;
	}

	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}

}
