/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-16
 */
package com.ast.ast1949.domain.company;

import java.io.Serializable;
import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-2-16
 */
public class EsiteCustomTopicDo extends DomainSupport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer companyId;
	private String account;
	private String topicContent;
	private Date gmtCreated;
	private Date gmtModified;
	private String cover;
	
	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
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
	 * @return the topicContent
	 */
	public String getTopicContent() {
		return topicContent;
	}
	/**
	 * @param topicContent the topicContent to set
	 */
	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}
	/**
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}
	/**
	 * @param cover the cover to set
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}
	
}
