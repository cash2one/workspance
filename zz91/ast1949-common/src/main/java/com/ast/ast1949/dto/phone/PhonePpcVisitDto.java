package com.ast.ast1949.dto.phone;

import com.ast.ast1949.domain.phone.PhonePpcVisit;

public class PhonePpcVisitDto {
	private PhonePpcVisit phonePpcVisit;
	private Integer number;// 次数

	public PhonePpcVisit getPhonePpcVisit() {
		return phonePpcVisit;
	}

	public void setPhonePpcVisit(PhonePpcVisit phonePpcVisit) {
		this.phonePpcVisit = phonePpcVisit;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}
