/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-26 下午05:59:55
 */
package com.zz91.ep.domain.comp;

import java.io.Serializable;
import java.util.Date;

public class SubnetCompRecommend implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String subnetCategory;
	private Integer cid;
	private Date gmtCreated;
	private Date gmtModified;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSubnetCategory() {
		return subnetCategory;
	}
	public void setSubnetCategory(String subnetCategory) {
		this.subnetCategory = subnetCategory;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
}
