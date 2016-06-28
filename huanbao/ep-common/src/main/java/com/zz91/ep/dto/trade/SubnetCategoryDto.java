package com.zz91.ep.dto.trade;

import java.io.Serializable;
import java.util.List;

import com.zz91.ep.domain.trade.SubnetCategory;

public class SubnetCategoryDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code;

	private String name; 

	private List<SubnetCategory> child;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SubnetCategory> getChild() {
		return child;
	}

	public void setChild(List<SubnetCategory> child) {
		this.child = child;
	}

}
