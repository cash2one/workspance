package com.kl91.domain.company;

import java.util.Date;

import com.kl91.domain.DomainSupport;

public class UploadPic extends DomainSupport{

	/**
	 * 供求图片表
	 */
	private static final long serialVersionUID = 8451679959236397908L;
	
	private Integer id;
	private Integer cid;//公司ID
	private Integer targetId;//相关ID
	private Integer targetType;//关联类型：\n例如\n供求图片\n公司图片
	private String filePath;//'文件路径'
	private String thumbPath;//缩略图地址
	private String fileName;//文件名
	private String fileType;//文件类型\n
	private String fileSize;//文件大小
	private String remark;//备注
	private String album;//相册
	private Date gmtCreated;
	private Date gmtModified;
	
	public UploadPic() {
		super();
	}

	public UploadPic(Integer id, Integer cid, Integer targetId,
			Integer targetType, String filePath, String thumbPath,
			String fileName, String fileType, String fileSize, String remark,
			String album, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.cid = cid;
		this.targetId = targetId;
		this.targetType = targetType;
		this.filePath = filePath;
		this.thumbPath = thumbPath;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.remark = remark;
		this.album = album;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
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
	
	
}
