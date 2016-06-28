package com.ast.ast1949.domain.autocaiji;

import java.io.Serializable;
import java.util.Date;

public class AutoCaiji implements Serializable{

    /**
     * zhouzk
     */
    private static final long serialVersionUID = 1L;
    
    private Integer id ;
    private String title;
    private String type;
    private Integer num;
    private String url;
    private String log;
    private String defaultTime;
    private String earlyTime;       //抓取同一数据最早时间
    private String lateTime;        //抓取同一数据最晚时间
    private Date gmtTarget;         //统计日期
    private Date gmtCreated;
    private Date gmtModified;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getNum() {
        return num;
    }
    public void setNum(Integer num) {
        this.num = num;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getLog() {
        return log;
    }
    public void setLog(String log) {
        this.log = log;
    }
    public String getDefaultTime() {
        return defaultTime;
    }
    public void setDefaultTime(String defaultTime) {
        this.defaultTime = defaultTime;
    }
    public String getEarlyTime() {
        return earlyTime;
    }
    public void setEarlyTime(String earlyTime) {
        this.earlyTime = earlyTime;
    }
    public String getLateTime() {
        return lateTime;
    }
    public void setLateTime(String lateTime) {
        this.lateTime = lateTime;
    }
    public Date getGmtTarget() {
        return gmtTarget;
    }
    public void setGmtTarget(Date gmtTarget) {
        this.gmtTarget = gmtTarget;
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

}
