package com.ast.ast1949.domain.download;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * author:kongsj date:2013-6-7
 */
public class DownloadInfo extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2686077724943757043L;
	private String title;
	private String detail;
	private String code;
	private String fileUrl;
	private String picCover;
	private String picThumb;
	private String size;
	private String language;
	private String type;
	private String viewCount;
	private String downloadCount;
	private String createdBy;
	private String isDeleted;
	private Date gmtCreated;
	private Date gmtModified;
	
	private String label;

	public String getTitle() {
		return title;
	}

	public String getDetail() {
		return detail;
	}

	public String getCode() {
		return code;
	}

	public String getPicCover() {
		return picCover;
	}

	public String getPicThumb() {
		return picThumb;
	}

	public String getSize() {
		return size;
	}

	public String getLanguage() {
		return language;
	}

	public String getType() {
		return type;
	}

	public String getViewCount() {
		return viewCount;
	}

	public String getDownloadCount() {
		return downloadCount;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setPicCover(String picCover) {
		this.picCover = picCover;
	}

	public void setPicThumb(String picThumb) {
		this.picThumb = picThumb;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public void setDownloadCount(String downloadCount) {
		this.downloadCount = downloadCount;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
}
