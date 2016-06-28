package com.zz91.ep.domain.sys;

import java.io.Serializable;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-11-17 
 */
public class SysAvatar implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private String avatar;
	
	
	
	public SysAvatar() {
		super();
	}
	
	public SysAvatar(String url, String avatar) {
		super();
		this.url = url;
		this.avatar = avatar;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}
	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
