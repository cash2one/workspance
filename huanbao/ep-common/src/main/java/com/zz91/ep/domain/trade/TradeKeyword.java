/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-8-23 上午11:10:59
 */
package com.zz91.ep.domain.trade;

import java.io.Serializable;
import java.util.Date;

public class TradeKeyword implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer cid;
	private Integer targetId;
	private Date start;
	private Date end;
	private String keywords;
	private Short type;
	private Short status;
	private String cname;
	private String title;
	private String photoCover;
	private String detailsQuery;
	private String areaCode;
	private String provinceCode;
	private Integer priceNum;
	private String priceUnits;
	private String memberCode;
	private Date gmtRegister;
	private Integer creditFile;
	private String domainTwo;
	private Date gmtCreated;
	private Date gmtModified;
	
	private String mainProductSupply;//主营产品
	private String areaName;//地区名称
	private String provinceName;//省份名称
	
	
	public TradeKeyword(){
		
	}
	
	public TradeKeyword(Integer id, Integer cid, Integer targetId, Date start,
			Date end, String keywords, Short type, Short status, String cname,
			String title, String photoCover, String detailsQuery,
			String areaCode, String provinceCode, Integer priceNum,
			String priceUnits, String memberCode, Date gmtRegister,
			Integer creditFile, String domainTwo, Date gmtCreated,
			Date gmtModified) {
		super();
		this.id = id;
		this.cid = cid;
		this.targetId = targetId;
		this.start = start;
		this.end = end;
		this.keywords = keywords;
		this.type = type;
		this.status = status;
		this.cname = cname;
		this.title = title;
		this.photoCover = photoCover;
		this.detailsQuery = detailsQuery;
		this.areaCode = areaCode;
		this.provinceCode = provinceCode;
		this.priceNum = priceNum;
		this.priceUnits = priceUnits;
		this.memberCode = memberCode;
		this.gmtRegister = gmtRegister;
		this.creditFile = creditFile;
		this.domainTwo = domainTwo;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	public String getMainProductSupply() {
		return mainProductSupply;
	}

	public void setMainProductSupply(String mainProductSupply) {
		this.mainProductSupply = mainProductSupply;
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
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhotoCover() {
		return photoCover;
	}
	public void setPhotoCover(String photoCover) {
		this.photoCover = photoCover;
	}
	public String getDetailsQuery() {
		return detailsQuery;
	}
	public void setDetailsQuery(String detailsQuery) {
		this.detailsQuery = detailsQuery;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public Integer getPriceNum() {
		return priceNum;
	}
	public void setPriceNum(Integer priceNum) {
		this.priceNum = priceNum;
	}
	public String getPriceUnits() {
		return priceUnits;
	}
	public void setPriceUnits(String priceUnits) {
		this.priceUnits = priceUnits;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public Date getGmtRegister() {
		return gmtRegister;
	}
	public void setGmtRegister(Date gmtRegister) {
		this.gmtRegister = gmtRegister;
	}
	public Integer getCreditFile() {
		return creditFile;
	}
	public void setCreditFile(Integer creditFile) {
		this.creditFile = creditFile;
	}
	public String getDomainTwo() {
		return domainTwo;
	}
	public void setDomainTwo(String domainTwo) {
		this.domainTwo = domainTwo;
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
	public String getAreaName() {
		return areaName;
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
