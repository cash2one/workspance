/*
 * 文件名称：Photo.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.trade;

import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：图片信息实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class Photo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;// 编号
	private Integer uid;// 用户编号
	private Integer cid;// 公司编号
	private Integer photoAlbumId;// 所属相册
	private String title;// 标题
	private String path;// 图片路径
	private String pathThumb;// 缩略图路径
	private Integer targetId;// 产品ID
	private String targetType;// 类型
	private String isDel; //图片删除状态
	private Date gmtCreated;// 创建时间
	private Date gmtModified;// 修改时间
	private Integer size;// 文件大小
	private String checkStatus; //针对所有用户的审核状态 0:未审核,1:审核通过,2:审核不通过
	private String unpassReason; // 不通过原因

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getPhotoAlbumId() {
		return this.photoAlbumId;
	}

	public void setPhotoAlbumId(Integer photoAlbumId) {
		this.photoAlbumId = photoAlbumId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPathThumb() {
		return this.pathThumb;
	}

	public void setPathThumb(String pathThumb) {
		this.pathThumb = pathThumb;
	}

	public Integer getTargetId() {
		return this.targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public Date getGmtCreated() {
		return this.gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getUnpassReason() {
		return unpassReason;
	}

	public void setUnpassReason(String unpassReason) {
		this.unpassReason = unpassReason;
	}
}