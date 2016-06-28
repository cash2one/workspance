package com.kl91.domain.company;

import java.util.Date;

import com.kl91.domain.DomainSupport;

public class CreditFile extends DomainSupport{

	/**
	 * 荣誉证书
	 */
	private static final long serialVersionUID = 4336216651859461216L;
	
	private Integer id;
	private Integer cid;
	private String fileType;//荣誉证书类型\n0:税务等级证\n1:经营许可类证书\n2:产品类证书
	private String fileName;//证书名
	private String fileAgency;//发证机构'
	private String filePicUrl;//证书图片地址
	private Integer showFlag;//显示状态\n0:不显示\n1:显示
	private Date gmtStart;//有效期始
	private Date gmtEnd;//有效日期末'
	private Date gmtModified;
	private Date gmtCreated;
	
	public CreditFile() {
		super();
	}

	public CreditFile(Integer id, Integer cid, String fileType,
			String fileName, String fileAgency, String filePicUrl,
			Integer showFlag, Date gmtStart, Date gmtEnd, Date gmtModified,
			Date gmtCreated) {
		super();
		this.id = id;
		this.cid = cid;
		this.fileType = fileType;
		this.fileName = fileName;
		this.fileAgency = fileAgency;
		this.filePicUrl = filePicUrl;
		this.showFlag = showFlag;
		this.gmtStart = gmtStart;
		this.gmtEnd = gmtEnd;
		this.gmtModified = gmtModified;
		this.gmtCreated = gmtCreated;
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

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileAgency() {
		return fileAgency;
	}

	public void setFileAgency(String fileAgency) {
		this.fileAgency = fileAgency;
	}

	public String getFilePicUrl() {
		return filePicUrl;
	}

	public void setFilePicUrl(String filePicUrl) {
		this.filePicUrl = filePicUrl;
	}

	public Integer getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(Integer showFlag) {
		this.showFlag = showFlag;
	}

	public Date getGmtStart() {
		return gmtStart;
	}

	public void setGmtStart(Date gmtStart) {
		this.gmtStart = gmtStart;
	}

	public Date getGmtEnd() {
		return gmtEnd;
	}

	public void setGmtEnd(Date gmtEnd) {
		this.gmtEnd = gmtEnd;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

}
