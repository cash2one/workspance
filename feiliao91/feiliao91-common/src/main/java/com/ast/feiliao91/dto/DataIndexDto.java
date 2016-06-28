/**
 * 
 */
package com.ast.feiliao91.dto;

import java.io.Serializable;

import com.ast.feiliao91.domain.common.DataIndexDO;

/**
 * @author yuyh
 *
 */
public class DataIndexDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DataIndexDO dataIndexDO;
	private String categoryName;
	
	public DataIndexDO getDataIndexDO() {
		return dataIndexDO;
	}
	public void setDataIndexDO(DataIndexDO dataIndexDO) {
		this.dataIndexDO = dataIndexDO;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
