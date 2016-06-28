/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-10-12
 */
package com.ast.ast1949.dto;

import java.util.List;

/**
 * 带复选框(CheckBox)的树
 * @author Rolyer (rolyer.live@gmail.com)
 * 
 */
public class ExtCheckBoxTreeDto implements java.io.Serializable {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	// Fields

	private String id;
	private String text;
	private String cls;
	private boolean leaf;
	private boolean checked;
	private List<ExtCheckBoxTreeDto> children;

	// Constructors

	/** default constructor */
	public ExtCheckBoxTreeDto() {
		
	}

	/** minimal constructor */
	public ExtCheckBoxTreeDto(String id) {
		this.id = id;
	}

	/** full constructor */
	public ExtCheckBoxTreeDto(String id, String text, String cls,
			boolean leaf, boolean checked, List<ExtCheckBoxTreeDto> children) {
		this.id = id;
		this.text = text;
		this.cls = cls;
		this.leaf = leaf;
		this.checked = checked;
		this.children = children;
	}

	// Property accessors

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the cls
	 */
	public String getCls() {
		return cls;
	}

	/**
	 * @param cls
	 *            the cls to set
	 */
	public void setCls(String cls) {
		this.cls = cls;
	}

	/**
	 * @return the leaf
	 */
	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * @param leaf
	 *            the leaf to set
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked
	 *            the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * @return the children
	 */
	public List<ExtCheckBoxTreeDto> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<ExtCheckBoxTreeDto> children) {
		this.children = children;
	}

}
