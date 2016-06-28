/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7
 */
package com.ast.ast1949.domain.site;

import java.io.Serializable;
import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-4-7
 */
public class WebBaseDataStatDo extends DomainSupport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String statCate;
	public String statCateName;
	public Date gmtStatDate;
	public Integer statCount;
	public Date gmtCreated;

	/**
	 * 
	 */
	public WebBaseDataStatDo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param statCate
	 * @param statCateName
	 * @param gmtStatDate
	 * @param statCount
	 * @param gmtCreated
	 */
	public WebBaseDataStatDo(String statCate, String statCateName,
			Date gmtStatDate, Integer statCount, Date gmtCreated) {
		super();
		this.statCate = statCate;
		this.statCateName = statCateName;
		this.gmtStatDate = gmtStatDate;
		this.statCount = statCount;
		this.gmtCreated = gmtCreated;
	}

	/**
	 * @return the statCate
	 */
	public String getStatCate() {
		return statCate;
	}

	/**
	 * @param statCate
	 *            the statCate to set
	 */
	public void setStatCate(String statCate) {
		this.statCate = statCate;
	}

	/**
	 * @return the statCateName
	 */
	public String getStatCateName() {
		return statCateName;
	}

	/**
	 * @param statCateName
	 *            the statCateName to set
	 */
	public void setStatCateName(String statCateName) {
		this.statCateName = statCateName;
	}

	/**
	 * @return the gmtStatDate
	 */
	public Date getGmtStatDate() {
		return gmtStatDate;
	}

	/**
	 * @param gmtStatDate
	 *            the gmtStatDate to set
	 */
	public void setGmtStatDate(Date gmtStatDate) {
		this.gmtStatDate = gmtStatDate;
	}

	/**
	 * @return the statCount
	 */
	public Integer getStatCount() {
		return statCount;
	}

	/**
	 * @param statCount
	 *            the statCount to set
	 */
	public void setStatCount(Integer statCount) {
		this.statCount = statCount;
	}

	/**
	 * @return the gmtCreated
	 */
	public Date getGmtCreated() {
		return gmtCreated;
	}

	/**
	 * @param gmtCreated
	 *            the gmtCreated to set
	 */
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

}
