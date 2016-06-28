package com.kl91.domain.company;

import java.util.Date;

import com.kl91.domain.DomainSupport;

public class EsiteFriendlink extends DomainSupport {	
	/**
	 * 友情链接
	*/
	private static final long serialVersionUID = -6069055215778667492L;
	
	private Integer id;
	private Integer cid;//公司ID
	private String name;//友情链接锚文本
	private String logoUrl;//图片地址
	private String Url;//'链接地址
	private Integer showFlag;//'是否显示\n0:不显示\n1:显示
	private String remark;//备注
	private Integer orderby;//排序
	private Date gmtCreated;
	private Date gmtModified;
	
	public EsiteFriendlink() {
		super();
	}

	public EsiteFriendlink(Integer id, Integer cid, String name,
			String logoUrl, String url, Integer showFlag, String remark,
			Integer orderby, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.cid = cid;
		this.name = name;
		this.logoUrl = logoUrl;
		this.Url = url;
		this.showFlag = showFlag;
		this.remark = remark;
		this.orderby = orderby;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		this.Url = url;
	}

	public Integer getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(Integer showFlag) {
		this.showFlag = showFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrderby() {
		if(orderby==null){
			return 0;
		}
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
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
