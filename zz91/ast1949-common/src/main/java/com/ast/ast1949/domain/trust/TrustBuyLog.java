/**
 * @author kongsj
 * @date 2015年5月7日
 * 
 */
package com.ast.ast1949.domain.trust;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class TrustBuyLog extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9171008334623987205L;
	private Integer id;
	private Integer buyId;
	private String content;
	private Date gmtCreated;
	private Date gmtModified;

	private Integer star; // 星级
	private Date gmtNextVisit; // 下次联系时间
	private Integer situation; // 是否有效小计
	private Integer trustType; // 公司类型 生产商 贸易商
	private Integer offerCompanyId; // 供货商 id
	private String offerCompanyName; // 供货公司名
	private String trustAccount; // 小計交易員

	private Date gmtRefresh;
	private Integer companyId;

	private String from;
	private String to;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBuyId() {
		return buyId;
	}

	public void setBuyId(Integer buyId) {
		this.buyId = buyId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Date getGmtRefresh() {
		return gmtRefresh;
	}

	public void setGmtRefresh(Date gmtRefresh) {
		this.gmtRefresh = gmtRefresh;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Date getGmtNextVisit() {
		return gmtNextVisit;
	}

	public void setGmtNextVisit(Date gmtNextVisit) {
		this.gmtNextVisit = gmtNextVisit;
	}

	public Integer getSituation() {
		return situation;
	}

	public void setSituation(Integer situation) {
		this.situation = situation;
	}

	public Integer getTrustType() {
		return trustType;
	}

	public void setTrustType(Integer trustType) {
		this.trustType = trustType;
	}

	public Integer getOfferCompanyId() {
		return offerCompanyId;
	}

	public void setOfferCompanyId(Integer offerCompanyId) {
		this.offerCompanyId = offerCompanyId;
	}

	public String getOfferCompanyName() {
		return offerCompanyName;
	}

	public void setOfferCompanyName(String offerCompanyName) {
		this.offerCompanyName = offerCompanyName;
	}

	public String getTrustAccount() {
		return trustAccount;
	}

	public void setTrustAccount(String trustAccount) {
		this.trustAccount = trustAccount;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
