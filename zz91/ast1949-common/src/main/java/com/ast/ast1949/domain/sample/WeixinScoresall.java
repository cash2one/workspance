package com.ast.ast1949.domain.sample;

import java.util.Date;


/**
 * 客户积分汇总表
 * @author asto
 *
 */
public class WeixinScoresall {
    private Integer id;

    private String account;

    private Integer score;

    private Date gmtCreated;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
}