/**
 * 
 */
package com.ast.ast1949.dto.dataindex;

import java.io.Serializable;

import com.ast.ast1949.domain.dataindex.DataIndexDO;

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
