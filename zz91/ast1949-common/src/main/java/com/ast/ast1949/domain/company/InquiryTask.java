package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * author:kongsj date:2013-7-11
 */
public class InquiryTask extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7194118255189450577L;
	private Integer companyId;// ` INT(11) NOT NULL COMMENT '公司id' ,
	private Integer targetId;// ` INT(11) NOT NULL COMMENT '目标id' ,
	private String targetType;// ` CHAR(1) NOT NULL COMMENT '目标类型\\n0:供求信息
								// \\n1:公司 \\n2: 询盘 \\n3:企业报价' ,
	private String postStatus;// ` CHAR(1) NULL COMMENT
								// '发送状态\\n0：待发送\\n1：已发送\\n其他：发送失败' ,
	private String title;// ` VARCHAR(200) NULL COMMENT '询盘标题' ,
	private String content;// ` TEXT NULL COMMENT '询盘内容' ,
	private Date postTime;// ` DATETIME NULL COMMENT '发送时间' ,
	private Date gmtCreated;// ` DATETIME NULL ,
	private Date gmtModified;// ` DATETIME NULL ,

	public Integer getCompanyId() {
		return companyId;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public String getTargetType() {
		return targetType;
	}

	public String getPostStatus() {
		return postStatus;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	

}
