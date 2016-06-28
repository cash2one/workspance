/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-30 By lisheng
 */
package com.ast.ast1949.dto.company;

import java.io.Serializable;

import com.ast.ast1949.dto.PageDto;

/**
 * 订制   
 * @author lisheng
 * 
 */ 
public class SubscribeDTO implements Serializable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	private PageDto page;
	private Integer id;
	private String chineseName;
	private String companyName;
	private String email;
	private String keywords;
    private String supplydemandoffer;
    private boolean hendEmail;
    private String isMustSee;
    
	public PageDto getPage() {
		return page;
	}
	public void setPage(PageDto page) {
		this.page = page;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getSupplydemandoffer() {
		return supplydemandoffer;
	}
	public void setSupplydemandoffer(String supplydemandoffer) {
		this.supplydemandoffer = supplydemandoffer;
	}
	
	public boolean isHendEmail() {
		return hendEmail;
	}
	public void setHendEmail(boolean hendEmail) {
		this.hendEmail = hendEmail;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the isMustSee
	 */
	public String getIsMustSee() {
		return isMustSee;
	}
	/**
	 * @param isMustSee the isMustSee to set
	 */
	public void setIsMustSee(String isMustSee) {
		this.isMustSee = isMustSee;
	}
    
    
   
}
