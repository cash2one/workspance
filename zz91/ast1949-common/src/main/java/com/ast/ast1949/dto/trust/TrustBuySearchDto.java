/**
 * @author kongsj
 * @date 2015年5月7日
 * 
 */
package com.ast.ast1949.dto.trust;

import com.ast.ast1949.domain.DomainSupport;

public class TrustBuySearchDto extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1813019815206272714L;
	private String buyNo;
	private Integer companyId;
	private String status;
	private String title;
	private String code;
	private String dateType;// 时间类型
	private String from;
	private String to;
	private Boolean isFront;
	private String keyword;

	private String mobile;
	private String companyName;

	private Integer isLogin;// 检索条件判断是否检索登陆发布的信息,或者有无凭证
	private Integer dealerId; // 交易员id
	private Integer isFirst; // 第一次发布采购

	private Integer loginFlag;
	private Integer noLoginFlag;
	private Integer isPause;

	public String getBuyNo() {
		return buyNo;
	}

	public void setBuyNo(String buyNo) {
		this.buyNo = buyNo;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Boolean getIsFront() {
		return isFront;
	}

	public void setIsFront(Boolean isFront) {
		this.isFront = isFront;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

	public Integer getDealerId() {
		return dealerId;
	}

	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public Integer getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(Integer loginFlag) {
		this.loginFlag = loginFlag;
	}

	public Integer getNoLoginFlag() {
		return noLoginFlag;
	}

	public void setNoLoginFlag(Integer noLoginFlag) {
		this.noLoginFlag = noLoginFlag;
	}

	public Integer getIsPause() {
		return isPause;
	}

	public void setIsPause(Integer isPause) {
		this.isPause = isPause;
	}

}
