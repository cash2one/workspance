package com.ast.ast1949.domain.analysis;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author kongsj
 * @date 2012-9-11
 */
public class AnalysisOperate extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3597909576822413945L;

	private Integer id;
	private Integer bbsAdminPost; // 互助 发布帖子
	private Integer bbsClientPostFailure; // 互助 审核 帖子 退回
	private Integer bbsClientPostSuccess; // 互助 审核 帖子 成功
	private Integer bbsReplyFailure; // 互助 审核 回帖 退回
	private Integer bbsReplySuccess; // 互助 审核 回帖 成功
	private Integer checkComppriceFailure; // 企业报价 审核 退回
	private Integer checkComppriceSuccess; // 企业报价 审核 成功
	private Integer checkProductsFailure; // 供求 审核 退回
	private Integer checkProductsSuccess; // 供求 审核 成功
	private Integer importToProducts; // 询盘导出供求
	private Integer postPrice; // 报价资讯 发布报价
	private Integer postPriceText; // 报价资讯 发布报价
	private Date gmtCreated;
	private Date gmtModified;
	private String operator; // 运营人员帐号

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBbsAdminPost() {
		return bbsAdminPost;
	}

	public void setBbsAdminPost(Integer bbsAdminPost) {
		this.bbsAdminPost = bbsAdminPost;
	}

	public Integer getBbsClientPostFailure() {
		return bbsClientPostFailure;
	}

	public void setBbsClientPostFailure(Integer bbsClientPostFailure) {
		this.bbsClientPostFailure = bbsClientPostFailure;
	}

	public Integer getBbsClientPostSuccess() {
		return bbsClientPostSuccess;
	}

	public void setBbsClientPostSuccess(Integer bbsClientPostSuccess) {
		this.bbsClientPostSuccess = bbsClientPostSuccess;
	}

	public Integer getBbsReplyFailure() {
		return bbsReplyFailure;
	}

	public void setBbsReplyFailure(Integer bbsReplyFailure) {
		this.bbsReplyFailure = bbsReplyFailure;
	}

	public Integer getBbsReplySuccess() {
		return bbsReplySuccess;
	}

	public void setBbsReplySuccess(Integer bbsReplySuccess) {
		this.bbsReplySuccess = bbsReplySuccess;
	}

	public Integer getCheckComppriceFailure() {
		return checkComppriceFailure;
	}

	public void setCheckComppriceFailure(Integer checkComppriceFailure) {
		this.checkComppriceFailure = checkComppriceFailure;
	}

	public Integer getCheckComppriceSuccess() {
		return checkComppriceSuccess;
	}

	public void setCheckComppriceSuccess(Integer checkComppriceSuccess) {
		this.checkComppriceSuccess = checkComppriceSuccess;
	}

	public Integer getCheckProductsFailure() {
		return checkProductsFailure;
	}

	public void setCheckProductsFailure(Integer checkProductsFailure) {
		this.checkProductsFailure = checkProductsFailure;
	}

	public Integer getCheckProductsSuccess() {
		return checkProductsSuccess;
	}

	public void setCheckProductsSuccess(Integer checkProductsSuccess) {
		this.checkProductsSuccess = checkProductsSuccess;
	}

	public Integer getImportToProducts() {
		return importToProducts;
	}

	public void setImportToProducts(Integer importToProducts) {
		this.importToProducts = importToProducts;
	}

	public Integer getPostPrice() {
		return postPrice;
	}

	public void setPostPrice(Integer postPrice) {
		this.postPrice = postPrice;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getPostPriceText() {
		return postPriceText;
	}

	public void setPostPriceText(Integer postPriceText) {
		this.postPriceText = postPriceText;
	}
	

}
