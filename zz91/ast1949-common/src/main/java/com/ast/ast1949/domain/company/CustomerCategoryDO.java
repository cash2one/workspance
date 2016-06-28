/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-31
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

/**
 * 高质客户类别
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class CustomerCategoryDO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;					//编号
	private Integer parentId;			//父ID
	private String name;				//名称
	private String isAuto;				//否自动归类的类别
	private Date gmtCreated;			//创建时间
	private Date gmtModified;			//修改时间
	
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
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the isAuto
	 */
	public String getIsAuto() {
		return isAuto;
	}
	/**
	 * @param isAuto the isAuto to set
	 */
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
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