package com.ast.ast1949.domain.sample;

import java.util.Date;

/**
 * 积分奖品表
 * @author asto
 *
 */
public class WeixinPrize {
    private Integer id;

    private String account;

    private String pic;
    
    private String webpic;

    private String title;

    private Integer score;

    private Integer num;

    private Integer numall;

    private String content;

    private Integer ord;

    private Integer closeflag;

    private Date gmtCreated;
    
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getWebpic() {
		return webpic;
	}

	public void setWebpic(String webpic) {
		this.webpic = webpic;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNumall() {
        return numall;
    }

    public void setNumall(Integer numall) {
        this.numall = numall;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public Integer getCloseflag() {
        return closeflag;
    }

    public void setCloseflag(Integer closeflag) {
        this.closeflag = closeflag;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}