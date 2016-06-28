/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-5 by liulei.
 */
package com.ast.ast1949.domain.price;

import java.util.Date;

/**
 * 上传
 * @author liulei
 */
public class UploadFilesDO implements java.io.Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer id; 			//编号
	private String fileName;		//文件名
	private Integer fileType;		//类型
	private String fileExtension;	//扩展名
	private String filePath;		//文件路径
	private Integer tableId;		//关联项编号
	private String tableName;		//关联表名称
	private Date gmtCreated;		//上传时间
	private Date gmtModified;		//修改时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getFileType() {
		return fileType;
	}
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getTableId() {
		return tableId;
	}
	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
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
