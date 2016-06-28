/**
 * 
 */
package com.ast.ast1949.dto.company;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class ConfigSiteDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
