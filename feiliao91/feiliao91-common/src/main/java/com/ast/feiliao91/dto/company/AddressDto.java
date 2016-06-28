/**
 * @author shiqp
 * @date 2016-02-01
 */
package com.ast.feiliao91.dto.company;

import com.ast.feiliao91.domain.company.Address;

public class AddressDto {
	private Address address;
	private String areaLabel;//显示内容为  省 + 市
	private String preTel;//区号
	private String midTel;//电话号码
	private String postTel;//分机

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getAreaLabel() {
		return areaLabel;
	}

	public void setAreaLabel(String areaLabel) {
		this.areaLabel = areaLabel;
	}

	public String getPreTel() {
		return preTel;
	}

	public void setPreTel(String preTel) {
		this.preTel = preTel;
	}

	public String getMidTel() {
		return midTel;
	}

	public void setMidTel(String midTel) {
		this.midTel = midTel;
	}

	public String getPostTel() {
		return postTel;
	}

	public void setPostTel(String postTel) {
		this.postTel = postTel;
	}

}
