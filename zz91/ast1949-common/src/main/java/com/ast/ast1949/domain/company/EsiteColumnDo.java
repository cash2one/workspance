/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23 by liulei.
 */
package com.ast.ast1949.domain.company;

import java.io.Serializable;
import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author liulei
 *
 */
public class EsiteColumnDo extends DomainSupport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String columnId;
	private String title;
	private String displayed;
	private String category;
	private Date gmtCreated;
	private Date gmtModified;
	
	/**
	 * @return the columnId
	 */
	public String getColumnId() {
		return columnId;
	}
	/**
	 * @param columnId the columnId to set
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the displayed
	 */
	public String getDisplayed() {
		return displayed;
	}
	/**
	 * @param displayed the displayed to set
	 */
	public void setDisplayed(String displayed) {
		this.displayed = displayed;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
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
	
	

}
