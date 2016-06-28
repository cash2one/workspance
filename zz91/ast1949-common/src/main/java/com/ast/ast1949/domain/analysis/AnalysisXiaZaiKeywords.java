package com.ast.ast1949.domain.analysis;

import java.io.Serializable;
import java.util.Date;

import com.ast.ast1949.util.DateUtil;

public class AnalysisXiaZaiKeywords implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String kw;
    private Integer num;
    private Date gmtTarget;//统计日期
    private Date gmtCreated;
    private Date gmtModified;
    private String targetDate;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getKw() {
        return kw;
    }
    public void setKw(String kw) {
        this.kw = kw;
    }
    public Integer getNum() {
        return num;
    }
    public void setNum(Integer num) {
        this.num = num;
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
    public String getTargetDate() {
        if(gmtTarget!=null){
            return DateUtil.toString(gmtTarget, "yyyy-MM-dd");
        }
        return targetDate;
    }
    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }
}
