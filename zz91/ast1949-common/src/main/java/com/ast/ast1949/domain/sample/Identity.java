package com.ast.ast1949.domain.sample;

import java.util.Date;

public class Identity {
    private Integer id;

    private Integer companyId;

    private String realName;

    private String identityNo;

    private String scanFrontImg;

    private String scanBackImg;

    private Date createTime;

	private String state;// 状态 00-申请成功  01-审核成功 02-审核失败 03-失效

    private Date updateTime;
    
    private String companyName;
    
    private String account;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo == null ? null : identityNo.trim();
    }

    public String getScanFrontImg() {
        return scanFrontImg;
    }

    public void setScanFrontImg(String scanFrontImg) {
        this.scanFrontImg = scanFrontImg == null ? null : scanFrontImg.trim();
    }

    public String getScanBackImg() {
        return scanBackImg;
    }

    public void setScanBackImg(String scanBackImg) {
        this.scanBackImg = scanBackImg == null ? null : scanBackImg.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}