package com.ast.ast1949.domain.photo;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class PhotoAbum extends DomainSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer companyId;
	private Integer albumId; // 相册类型  2:公司相册 ，3：产品相册 4：互助相册
	private String name;   //图片名称
	private String picAddress; //图片保存地址
	private Integer isDel;//是否删除 0:未删除 1:已删除
	private Integer isMark; //是否有水印 0:没用水印 1:有水印
	private Date gmtCreated;
	private Date gmtModified;
	
	public Integer getIsMark() {
		return isMark;
	}
	public void setIsMark(Integer isMark) {
		this.isMark = isMark;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getAlbumId() {
		return albumId;
	}
	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicAddress() {
		return picAddress;
	}
	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	

}
