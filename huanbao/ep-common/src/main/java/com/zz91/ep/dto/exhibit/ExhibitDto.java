/*
 * 文件名称：ExhibitDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.exhibit;

import java.io.Serializable;

import com.zz91.ep.domain.exhibit.Exhibit;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：展会信息扩展。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class ExhibitDto implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private Exhibit exhibit;
	
	private String industryCategoryName;//行业名称
	
	private String plateCategoryName;//模块类别名称
	
	private String areaName;//城市名称
	
	private String proviceName;//省份名称
	
	private Integer rid;

	private String photoUrl; //图片
	
	private String orPhoto;
	
	
	public String getOrPhoto() {
		return orPhoto;
	}

	public void setOrPhoto(String orPhoto) {
		this.orPhoto = orPhoto;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getIndustryCategoryName() {
		return industryCategoryName;
	}

	public void setIndustryCategoryName(String industryCategoryName) {
		this.industryCategoryName = industryCategoryName;
	}

	public String getPlateCategoryName() {
		return plateCategoryName;
	}

	public void setPlateCategoryName(String plateCategoryName) {
		this.plateCategoryName = plateCategoryName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getProviceName() {
		return proviceName;
	}

	public void setProviceName(String proviceName) {
		this.proviceName = proviceName;
	}

	public void setExhibit(Exhibit exhibit) {
		this.exhibit = exhibit;
	}

	public Exhibit getExhibit() {
		return exhibit;
	}
	
}