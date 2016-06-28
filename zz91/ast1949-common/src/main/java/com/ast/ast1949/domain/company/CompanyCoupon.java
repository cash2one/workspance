/**
 * @author kongsj
 * @date 2014年11月4日
 * 
 */
package com.ast.ast1949.domain.company;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class CompanyCoupon extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5606707475151135501L;

	public Integer id;
	public Integer companyId; // INT(11) NOT NULL COMMENT '公司id',
	public String code; // VARCHAR(200) NOT NULL COMMENT '优惠券编码',
	public Integer type; // INT(1) NOT NULL COMMENT '优惠券类别:1 会员服务 2 广告',
	public Integer status; // INT NOT NULL COMMENT '优惠券状态:1激活 2失效',
	public Date gmtStart;// DATETIME NOT NULL COMMENT '开始开始',
	public Date gmtEnd; // gmt_end DATETIME NOT NULL COMMENT '结束时间',
	public String serviceName; // 服务名
	public Integer price; //服务原价
	public Integer reducePrice; // 优惠价格
	public Date gmtCreated; // DATETIME NULL,
	public Date gmtModified; // DATETIME NULL,

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getReducePrice() {
		return reducePrice;
	}

	public void setReducePrice(Integer reducePrice) {
		this.reducePrice = reducePrice;
	}

}
