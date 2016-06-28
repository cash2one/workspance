/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-6.
 */
package com.zz91.ads.board.domain.ad;

import java.util.Date;

/**
 * 广告素材
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class AdMaterial implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer adId;//关联的广告
	private String name;//素材名称
	private String materialType;//素材类别
	private String filePath;//素材文件路径
	private String remark;//详细的素材描述
	private Date gmtCreated;
	private Date gmtModified;
	
	public AdMaterial() {
		super();
	}
	
	public AdMaterial(Integer id, Integer adId, String name, String materialType, String filePath,
			String remark, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.adId = adId;
		this.name = name;
		this.materialType = materialType;
		this.filePath = filePath;
		this.remark = remark;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
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
	 * @return the adId
	 */
	public Integer getAdId() {
		return adId;
	}
	/**
	 * @param adId the adId to set
	 */
	public void setAdId(Integer adId) {
		this.adId = adId;
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
	 * @return the materialType
	 */
	public String getMaterialType() {
		return materialType;
	}
	/**
	 * @param materialType the materialType to set
	 */
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
