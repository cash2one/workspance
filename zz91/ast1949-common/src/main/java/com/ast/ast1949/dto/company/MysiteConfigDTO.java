/**
 * 
 */
package com.ast.ast1949.dto.company;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class MysiteConfigDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Object> headerConfig;
	private List<Object> sideBarConfig;
	private List<Object> mainConfig;
	public List<Object> getHeaderConfig() {
		return headerConfig;
	}
	public void setHeaderConfig(List<Object> headerConfig) {
		this.headerConfig = headerConfig;
	}
	public List<Object> getSideBarConfig() {
		return sideBarConfig;
	}
	public void setSideBarConfig(List<Object> sideBarConfig) {
		this.sideBarConfig = sideBarConfig;
	}
	public List<Object> getMainConfig() {
		return mainConfig;
	}
	public void setMainConfig(List<Object> mainConfig) {
		this.mainConfig = mainConfig;
	}
}
