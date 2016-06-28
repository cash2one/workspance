package com.zz91.mission.domain.subscribe;

import java.io.Serializable;
import java.util.Date;

/**
 *  author:zhouzk
 *  date:2013-10-24
 */
public class PriceTemp implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer priceId;
    private Date gmtCreated;
    private Date gmtModified;
    public Integer getId() {
        return id;
    }
    public Integer getPriceId() {
        return priceId;
    }
    public Date getGmtCreated() {
        return gmtCreated;
    }
    public Date getGmtModified() {
        return gmtModified;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
    
}

