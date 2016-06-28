package com.zz91.ep.domain.mblog;

import java.util.Date;

/**
 * author:fangchao date:2013-10-22
 */
public class MBlogInfo {
	private Integer id;
	private Integer cid;// 公司id
	private String account;// 账户
	private String realName;// 真实姓名
	private String headPic;// 头像
	private String name;// 昵称
	private String provinceCode;// 省份
	private String areaCode;// 地区
	private String isDelete;// 冻结状态
	private String sex;
	private Date gmtCreated;
	private Date gmtModified;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCid() {
		return cid;
	}

	public String getAccount() {
		return account;
	}

	public String getHeadPic() {
		return headPic;
	}

	public String getName() {
		return name;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
    
}
