package com.zz91.ep.dto.trade;

import java.io.Serializable;

public class PhotoDto implements Serializable {

	/**
	 * [字段中文描述]
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id; //公司ID
	
	private String name;//公司名称
	
	private String path;//图片路径

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
