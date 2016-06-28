package com.ast.ast1949.dto.company;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SMSPriceDTO implements Serializable {
    private static final long serialVersionUID = -2253980373529525029L;

    private Integer id;
    private Double price;
    private Double maxPrice;
    private Double minPrice;
    private String categoryCode;
    private String categoryName;
    private String categorySMSName;
    private Integer timeNodeId;
	private Integer areaNodeId;
    private Boolean isRange;
    private Date gmtCreated;
    private Date gmtModified;
    private Date gmtPost;
    private Double upDown;
    private Integer unit;
    private Date gmtBegin;
    private Date gmtEnd;
    private Integer beforeDay;
    private Integer downPrice;
    private Integer upPrice;
    private String keyword;
    private String areaNodeName;
    private Boolean isMetal;
    private Boolean isPlastic;
    private Integer priceType;
    private List<?> list;

    public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Integer getTimeNodeId() {
        return timeNodeId;
    }

    public void setTimeNodeId(Integer timeNodeId) {
        this.timeNodeId = timeNodeId;
    }

    public Boolean getIsRange() {
        return isRange;
    }

    public void setIsRange(Boolean isRange) {
        this.isRange = isRange;
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

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
    
    public String getCategorySMSName() {
		return categorySMSName;
	}
    
    public void setGmtPost(Date gmtPost) {
        this.gmtPost = gmtPost;
    }

    public Date getGmtPost() {
        return gmtPost;
    }

    public void setAreaNodeId(Integer areaNodeId) {
        this.areaNodeId = areaNodeId;
    }

    public Integer getAreaNodeId() {
        return areaNodeId;
    }

    public void setCategorySMSName(String categorySMSName) {
        this.categorySMSName = categorySMSName;
    }

    public void setUpDown(Double upDown) {
        this.upDown = upDown;
    }

    public Double getUpDown() {
        return upDown;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Date getGmtBegin() {
        return gmtBegin;
    }

    public void setGmtBegin(Date gmtBegin) {
        this.gmtBegin = gmtBegin;
    }

    public Date getGmtEnd() {
        return gmtEnd;
    }

    public void setGmtEnd(Date gmtEnd) {
        this.gmtEnd = gmtEnd;
    }

    public Integer getBeforeDay() {
        return beforeDay;
    }

    public void setBeforeDay(Integer beforeDay) {
        this.beforeDay = beforeDay;
    }

    public Integer getDownPrice() {
        return downPrice;
    }

    public void setDownPrice(Integer downPrice) {
        this.downPrice = downPrice;
    }

    public Integer getUpPrice() {
        return upPrice;
    }

    public void setUpPrice(Integer upPrice) {
        this.upPrice = upPrice;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAreaNodeName() {
        return areaNodeName;
    }

    public void setAreaNodeName(String areaNodeName) {
        this.areaNodeName = areaNodeName;
    }

    public Boolean getIsMetal() {
        return isMetal;
    }

    public void setIsMetal(Boolean isMetal) {
        this.isMetal = isMetal;
    }

    public Boolean getIsPlastic() {
        return isPlastic;
    }

    public void setIsPlastic(Boolean isPlastic) {
        this.isPlastic = isPlastic;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        switch (priceType) {
        case 1:
            this.isMetal = true;
            this.isPlastic = null;
            break;
        case 2:
            this.isPlastic = true;
            this.isMetal = null;
            break;
        }
        this.priceType = priceType;
    }

}