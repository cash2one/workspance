/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-25
 */
package com.zz91.mission.domain.subscribe;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-3-25
 */
public class Price implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String title;
	private Integer typeId;
	private String typeName;
    private String tags;                   //标签
    private String content;                    //资讯内容
    private Date gmtCreated;                //创建时间
    private Date gmtOrder;                  //排序时间
    private String isChecked;               //是否审核(0 否 1 是)
    private String isIssue;             //是否直接发布(0 否 1 是)
    private Integer assistTypeId;           //辅助类别
	private String gmtTime;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the typeId
	 */
	public Integer getTypeId() {
		return typeId;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getGmtCreated() {
        return gmtCreated;
    }
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
    public Date getGmtOrder() {
        return gmtOrder;
    }
    public void setGmtOrder(Date gmtOrder) {
        this.gmtOrder = gmtOrder;
    }
    public String getIsChecked() {
        return isChecked;
    }
    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
    public String getIsIssue() {
        return isIssue;
    }
    public void setIsIssue(String isIssue) {
        this.isIssue = isIssue;
    }
    public Integer getAssistTypeId() {
        return assistTypeId;
    }
    public void setAssistTypeId(Integer assistTypeId) {
        this.assistTypeId = assistTypeId;
    }
    /**
	 * @return the gmtTime
	 */
	public String getGmtTime() {
		return gmtTime;
	}
	/**
	 * @param gmtTime the gmtTime to set
	 */
	public void setGmtTime(String gmtTime) {
		this.gmtTime = gmtTime;
	}
	
	
	
}
