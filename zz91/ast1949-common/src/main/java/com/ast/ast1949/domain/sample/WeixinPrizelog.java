package com.ast.ast1949.domain.sample;

import java.util.Date;

/**
 * 积分支出表
 * @author asto
 *
 */
public class WeixinPrizelog {
    private Integer id;

    private String account;

    private Integer prizeid;

    private Integer score;

    private Integer ischeck;

    private Date gmtCreated;
    
    private String scoreGoodsTitle;
    
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Integer getPrizeid() {
        return prizeid;
    }

    public void setPrizeid(Integer prizeid) {
        this.prizeid = prizeid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getIscheck() {
        return ischeck;
    }

    public void setIscheck(Integer ischeck) {
        this.ischeck = ischeck;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

	public String getScoreGoodsTitle() {
		return scoreGoodsTitle;
	}

	public void setScoreGoodsTitle(String scoreGoodsTitle) {
		this.scoreGoodsTitle = scoreGoodsTitle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}