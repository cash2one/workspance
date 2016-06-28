package com.ast.ast1949.dto.phone;

import java.io.Serializable;

import com.ast.ast1949.domain.phone.PhoneLog;

public class PhoneLogDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private PhoneLog phoneLog;
	private String diffMinute;// 通话时间差.单位,分钟
	private String countDiffMinute;// 统计出通话时间
	private String countcallFee;// 统计出所有的通话费用
	private Integer countPhone;// 统计出电话个数
	private String allFee;// 统计所有的明细.不包括月租
	private String allMFee;// 统计所有的月租
	private String stringFellName;//表示文字说明
	private Integer isBlack;//表示来电是否被拉黑
	
	public String getStringFellName() {
		return stringFellName;
	}

	public void setStringFellName(String stringFellName) {
		this.stringFellName = stringFellName;
	}

	public String getAllFee() {
		return allFee;
	}

	public String getAllMFee() {
		return allMFee;
	}

	public void setAllFee(String allFee) {
		this.allFee = allFee;
	}

	public void setAllMFee(String allMFee) {
		this.allMFee = allMFee;
	}

	public Integer getCountPhone() {
		return countPhone;
	}

	public void setCountPhone(Integer countPhone) {
		this.countPhone = countPhone;
	}

	public PhoneLog getPhoneLog() {
		return phoneLog;
	}

	public String getDiffMinute() {
		return diffMinute;
	}

	public String getCountDiffMinute() {
		return countDiffMinute;
	}

	public String getCountcallFee() {
		return countcallFee;
	}

	public void setPhoneLog(PhoneLog phoneLog) {
		this.phoneLog = phoneLog;
	}

	public void setDiffMinute(String diffMinute) {
		this.diffMinute = diffMinute;
	}

	public void setCountDiffMinute(String countDiffMinute) {
		this.countDiffMinute = countDiffMinute;
	}

	public void setCountcallFee(String countcallFee) {
		this.countcallFee = countcallFee;
	}

	public Integer getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Integer isBlack) {
		this.isBlack = isBlack;
	}

}
