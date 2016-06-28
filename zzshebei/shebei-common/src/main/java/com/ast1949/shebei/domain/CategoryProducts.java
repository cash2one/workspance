/**
 * 产品类别表
 * @author 陈庆林
 */
package com.ast1949.shebei.domain;
import java.io.Serializable;
import java.util.Date;

public class CategoryProducts implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String  code;//产品类别
	private String  label;//产品类别名称
	private String  tags;  //标签
	private Short    sort;//排序
	private Date    gmtCreated;//创建时间
	private Date    gmtModified;//最后创建时间
	private Short  leaf;
	public CategoryProducts(){
		
	}
	
	public CategoryProducts(Integer id, String code, String label, String tags,
			Short sort, Date gmtCreated, Date gmtModified) {
		super();
		this.id = id;
		this.code = code;
		this.label = label;
		this.tags = tags;
		this.sort = sort;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Short getLeaf() {
		return leaf;
	}

	public void setLeaf(Short leaf) {
		this.leaf = leaf;
	}

	public Short getSort() {
		return sort;
	}
	public void setSort(Short sort) {
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
}
