package com.zz91.ep.dto.mblog;

import java.util.Date;

import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.mblog.MBlogInfo;

public class MBlogInfoDto {
	private MBlogInfo mBlogInfo;
	private CompProfile compProfile;
	private String areaName;
	private String provinceName;
	private Integer fansCount;
	private Integer followCount;
	private Integer mblogCount;
	private String type;
	private String noteName;
	private Date gmtLogin;
	private String address;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getGmtLogin() {
		return gmtLogin;
	}

	public void setGmtLogin(Date gmtLogin) {
		this.gmtLogin = gmtLogin;
	}

	public String getNoteName() {
		return noteName;
	}

	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}

	public CompProfile getCompProfile() {
		return compProfile;
	}

	public void setCompProfile(CompProfile compProfile) {
		this.compProfile = compProfile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getFansCount() {
		return fansCount;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public Integer getMblogCount() {
		return mblogCount;
	}

	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public void setMblogCount(Integer mblogCount) {
		this.mblogCount = mblogCount;
	}

	public MBlogInfo getmBlogInfo() {
		return mBlogInfo;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setmBlogInfo(MBlogInfo mBlogInfo) {
		this.mBlogInfo = mBlogInfo;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

}
