/**
 * 
 */
package com.zz91.ec.finance.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mays
 *
 */
public class Test implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String col1;
	private Integer col2;
	private Date col3;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public Integer getCol2() {
		return col2;
	}
	public void setCol2(Integer col2) {
		this.col2 = col2;
	}
	public Date getCol3() {
		return col3;
	}
	public void setCol3(Date col3) {
		this.col3 = col3;
	}
	
	
}
