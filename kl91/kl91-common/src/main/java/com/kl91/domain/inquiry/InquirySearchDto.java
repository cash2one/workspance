package com.kl91.domain.inquiry;

import com.kl91.domain.DomainSupport;

public class InquirySearchDto extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 219543720934653447L;
	private Integer fromCid;// '发送询盘公司ID
	private Integer toCid;// 接收询盘公司ID
	private Integer trashCid;
	private Integer trashFlag;

	public Integer getFromCid() {
		return fromCid;
	}

	public void setFromCid(Integer fromCid) {
		this.fromCid = fromCid;
	}

	public Integer getToCid() {
		return toCid;
	}

	public void setToCid(Integer toCid) {
		this.toCid = toCid;
	}

	public Integer getTrashCid() {
		return trashCid;
	}

	public void setTrashCid(Integer trashCid) {
		this.trashCid = trashCid;
	}

	public Integer getTrashFlag() {
		return trashFlag;
	}

	public void setTrashFlag(Integer trashFlag) {
		this.trashFlag = trashFlag;
	}
}
