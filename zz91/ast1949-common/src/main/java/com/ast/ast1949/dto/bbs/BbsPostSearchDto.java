package com.ast.ast1949.dto.bbs;

public class BbsPostSearchDto {

	private String account;
	private String checkStatus;
	private String isDel;
	private Integer bbsPostCategoryId;
	private String replyAccount;
	private String title;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getBbsPostCategoryId() {
		return bbsPostCategoryId;
	}

	public void setBbsPostCategoryId(Integer bbsPostCategoryId) {
		this.bbsPostCategoryId = bbsPostCategoryId;
	}

	public String getReplyAccount() {
		return replyAccount;
	}

	public void setReplyAccount(String replyAccount) {
		this.replyAccount = replyAccount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

}
