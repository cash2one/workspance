package com.ast.ast1949.domain.download;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * author:kongsj date:2013-6-7
 */
public class DownloadInfoSwf extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1305731218974751454L;
	private Integer downloadId;
	private String url;
	private Date gmtCreated;
	private Date gmtModified;

	public Integer getDownloadId() {
		return downloadId;
	}

	public String getUrl() {
		return url;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setDownloadId(Integer downloadId) {
		this.downloadId = downloadId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}
