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
public class DataIndexCategoryDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String code;
	private String link;
	private String label;
	private String isDelete;
	private Date gmtCreated;
	private Date gmtModified;
	
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
	public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getLabel() {
		return label;
	}
    public void setLabel(String label) {
		this.label = label;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
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
	public DataIndexCategoryDO(Integer id, String code, String label,
			String isDelete, Date gmtCreated, Date gmtModified) {

		this.id = id;
		this.code = code;
		this.label = label;
		this.isDelete = isDelete;
		this.gmtCreated = gmtCreated;
		this.gmtModified = gmtModified;
	}
	public DataIndexCategoryDO() {
		
	}
	
	
}
