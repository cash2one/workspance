/**
 * 
 */
package com.ast.ast1949.domain.dataindex;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuyh
 *
 */
public class DataIndexDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String title;
	private String categoryCode;
	private String pic;
	private String style;
	private String link;
	private String isChecked;
	private Integer sort;
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
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
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
	public DataIndexDO() {
		// TODO Auto-generated constructor stub
	}
	public DataIndexDO(Integer id, String title, String categoryCode,
			String pic, String style, String link, String isChecked,
			Integer sort, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.title = title;
		this.categoryCode = categoryCode;
		this.pic = pic;
		this.style = style;
		this.link = link;
		this.isChecked = isChecked;
		this.sort = sort;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	
}
