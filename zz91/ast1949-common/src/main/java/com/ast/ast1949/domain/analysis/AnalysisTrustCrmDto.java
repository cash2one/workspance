package com.ast.ast1949.domain.analysis;

import com.ast.ast1949.domain.DomainSupport;

public class AnalysisTrustCrmDto extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1142716527540876750L;

	private String account;
	private Integer num5;
	private Integer num4;
	private Integer num3;
	private Integer num2;
	private Integer num1;
	private Integer numAll;
	private Integer numNew;
	private Integer numLost;
	private Integer numTomorrow;
	private Integer numToday;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getNum5() {
		if (num5==null) {
			return 0;
		}
		return num5;
	}

	public void setNum5(Integer num5) {
		this.num5 = num5;
	}

	public Integer getNum4() {
		if (num4==null) {
			return 0;
		}
		return num4;
	}

	public void setNum4(Integer num4) {
		this.num4 = num4;
	}

	public Integer getNum3() {
		if (num3==null) {
			return 0;
		}
		return num3;
	}

	public void setNum3(Integer num3) {
		this.num3 = num3;
	}

	public Integer getNum2() {
		if (num2==null) {
			return 0;
		}
		return num2;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}

	public Integer getNum1() {
		if (num1==null) {
			return 0;
		}
		return num1;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	public Integer getNumAll() {
		return numAll;
	}

	public void setNumAll(Integer numAll) {
		this.numAll = numAll;
	}

	public Integer getNumNew() {
		return numNew;
	}

	public void setNumNew(Integer numNew) {
		this.numNew = numNew;
	}

	public Integer getNumLost() {
		return numLost;
	}

	public void setNumLost(Integer numLost) {
		this.numLost = numLost;
	}

	public Integer getNumTomorrow() {
		return numTomorrow;
	}

	public void setNumTomorrow(Integer numTomorrow) {
		this.numTomorrow = numTomorrow;
	}

	public Integer getNumToday() {
		return numToday;
	}

	public void setNumToday(Integer numToday) {
		this.numToday = numToday;
	}

}
