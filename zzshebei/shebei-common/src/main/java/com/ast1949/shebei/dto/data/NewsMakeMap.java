/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-7 上午08:56:35
 */
package com.ast1949.shebei.dto.data;

import java.io.Serializable;

public class NewsMakeMap implements Serializable {
	
	/**
	 * ZZ91二手设备(资讯/报价信息导入类)
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer title;
	private Integer description;
	private String categoryCode;
	private Integer type;
	private Integer details;
	private Integer source;
	private Integer sourceUrl;
	private Integer tags;
	private Integer photoCover;
	private Integer auth;
	private Integer gmtPublish;

	public Integer getPhotoCover() {
		return photoCover;
	}
	public void setPhotoCover(Integer photoCover) {
		this.photoCover = photoCover;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(Integer sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public Integer getAuth() {
		return auth;
	}
	public void setAuth(Integer auth) {
		this.auth = auth;
	}
	public void setTitle(Integer title) {
		this.title = title;
	}
	public Integer getTitle() {
		return title;
	}

	public void setDescription(Integer description) {
		this.description = description;
	}
	public Integer getDescription() {
		return description;
	}
	public void setDetails(Integer details) {
		this.details = details;
	}
	public Integer getDetails() {
		return details;
	}
	public void setTags(Integer tags) {
		this.tags = tags;
	}
	public Integer getTags() {
		return tags;
	}
	public void setGmtPublish(Integer gmtPublish) {
		this.gmtPublish = gmtPublish;
	}
	public Integer getGmtPublish() {
		return gmtPublish;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryCode() {
		return categoryCode;
	}

}
