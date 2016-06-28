/*
 * 文件名称：ExtResult.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto;

import java.io.Serializable;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：Ajax操作结果。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class ExtResult implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean success;
    private Boolean error;
    private Object data;
    
	public Boolean getSuccess() {
		if(success==null){
			return false;
		}
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}