/*
 * 文件名称：ResetPwd.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.domain.comp;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：重置密码实体类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class ResetPwd implements Serializable {

	private static final long serialVersionUID = -1485020001155697256L;

	private Integer id;// 编号
	private String email;// 邮箱
	private String account;// 帐号
	private Integer uid;// 用户ID
	private String authKey;// 自动生成key
	private Date gmtCreated;// 创建时间
	private Date gmtModified;// 修改时间

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
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