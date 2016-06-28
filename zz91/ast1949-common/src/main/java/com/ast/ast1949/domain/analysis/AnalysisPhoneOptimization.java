package com.ast.ast1949.domain.analysis;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class AnalysisPhoneOptimization extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2854681007673934172L;
	private Integer id;
	private Integer phoneLogId;
	private String ip;
	private String utmSource;
	private String utmTerm;
	private String utmContent;
	private String utmCampaign;
	private Integer isValid;
	private Integer isFirst;
	private String pageFirst;
	private String pageLast;
	private String pageCalling;
	private String gmtTarget;
	private Date gmtCreated;
	private Date gmtModified;

	private String area;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPhoneLogId() {
		return phoneLogId;
	}

	public void setPhoneLogId(Integer phoneLogId) {
		this.phoneLogId = phoneLogId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUtmSource() {
		return utmSource;
	}

	public void setUtmSource(String utmSource) {
		this.utmSource = utmSource;
	}

	public String getUtmTerm() {
		return utmTerm;
	}

	public void setUtmTerm(String utmTerm) {
		this.utmTerm = utmTerm;
	}

	public String getUtmContent() {
		return utmContent;
	}

	public void setUtmContent(String utmContent) {
		this.utmContent = utmContent;
	}

	public String getUtmCampaign() {
		return utmCampaign;
	}

	public void setUtmCampaign(String utmCampaign) {
		this.utmCampaign = utmCampaign;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public String getPageFirst() {
		return pageFirst;
	}

	public void setPageFirst(String pageFirst) {
		this.pageFirst = pageFirst;
	}

	public String getPageLast() {
		return pageLast;
	}

	public void setPageLast(String pageLast) {
		this.pageLast = pageLast;
	}

	public String getPageCalling() {
		return pageCalling;
	}

	public void setPageCalling(String pageCalling) {
		this.pageCalling = pageCalling;
	}

	public String getGmtTarget() {
		return gmtTarget;
	}

	public void setGmtTarget(String gmtTarget) {
		this.gmtTarget = gmtTarget;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}
