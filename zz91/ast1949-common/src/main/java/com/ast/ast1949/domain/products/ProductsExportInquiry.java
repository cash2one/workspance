package com.ast.ast1949.domain.products;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

public class ProductsExportInquiry extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7349768123900633263L;
	private Integer id;// INT(11) NOT NULL ,
	private Integer productId;// INT(11) NOT NULL COMMENT '被导成询盘的供求id' ,
	private Integer targetId; // INT(11) NOT NULL COMMENT '导出询盘发送目标供求id' ,
	private Integer fromCompanyId;
	private Integer toCompanyId;
	private Date gmtCreated; // DATETIME NULL ,
	private Date gmtModified; // DATETIME NULL ,

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
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

	public Integer getFromCompanyId() {
		return fromCompanyId;
	}

	public void setFromCompanyId(Integer fromCompanyId) {
		this.fromCompanyId = fromCompanyId;
	}

	public Integer getToCompanyId() {
		return toCompanyId;
	}

	public void setToCompanyId(Integer toCompanyId) {
		this.toCompanyId = toCompanyId;
	}

	
	
}
