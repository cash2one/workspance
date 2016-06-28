/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-28.
 */
package com.ast.ast1949.dto.information;


/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class ChartDataForIndexDTO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private Float value;
	private Float diff;
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public Float getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Float value) {
		this.value = value;
	}
	/**
	 * @return the diff
	 */
	public Float getDiff() {
		return diff;
	}
	/**
	 * @param diff the diff to set
	 */
	public void setDiff(Float diff) {
		this.diff = diff;
	}
}
