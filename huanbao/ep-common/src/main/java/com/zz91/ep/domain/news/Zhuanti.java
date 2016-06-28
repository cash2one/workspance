package com.zz91.ep.domain.news;

import java.io.Serializable;
import java.util.Date;
/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：专题实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-09-12　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
 */
public class Zhuanti implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;			//编号
	private String title;		//标题
	private String review;		//导读
	private String targetUrl;	//专题url
	private String category;	//类别
	private String photoPreview;	//图片
	private String tags;		//标签
	private String recommandStatus;//推荐标记
	private String attentionStatus;//关注标记
	private Date gmtPublish;	//发布时间
	private Date gmtCreated;	//创建时间
	private Date gmtModified;	//更新时间
	public Integer getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getReview() {
		return review;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public String getCategory() {
		return category;
	}
	public String getPhotoPreview() {
		return photoPreview;
	}
	public String getTags() {
		return tags;
	}
	public String getRecommandStatus() {
		return recommandStatus;
	}
	public String getAttentionStatus() {
		return attentionStatus;
	}
	public Date getGmtPublish() {
		return gmtPublish;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setPhotoPreview(String photoPreview) {
		this.photoPreview = photoPreview;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public void setRecommandStatus(String recommandStatus) {
		this.recommandStatus = recommandStatus;
	}
	public void setAttentionStatus(String attentionStatus) {
		this.attentionStatus = attentionStatus;
	}
	public void setGmtPublish(Date gmtPublish) {
		this.gmtPublish = gmtPublish;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	
}
