package com.ast.ast1949.dto.phone;

import java.io.Serializable;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.phone.Phone;

public class PhoneDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Phone phone;
	private CompanyAccount companyAccount;
	private Company company;
	private String sumCallFee;// 统计消费的费用
	private String sumClickFee;// 统计点击的费用
	private String sumAllFee;// 统计消费+点击的费用
	private Integer pv;// 统计pv
	private String sumEveCallFee;// 统计前一月已接电话的费用
	private String sumCallClickFee;// 统计未接电话点击费用
	private String missCall;// 统计未接电话个数
	private String phoneRate;// 接通率
	private String allPhone;// 总电话量
	private String isOut;// 是否过期
	private String isLave;
	
	private String phoneRateForSevenDay;// 前7天接通率

	public String getIsOut() {
		return isOut;
	}

	public void setIsOut(String isOut) {
		this.isOut = isOut;
	}

	public String getMissCall() {
		return missCall;
	}

	public String getPhoneRate() {
		return phoneRate;
	}

	public void setPhoneRate(String phoneRate) {
		this.phoneRate = phoneRate;
	}

	public void setMissCall(String missCall) {
		this.missCall = missCall;
	}

	public String getAllPhone() {
		return allPhone;
	}

	public void setAllPhone(String allPhone) {
		this.allPhone = allPhone;
	}

	public String getSumCallClickFee() {
		return sumCallClickFee;
	}

	public void setSumCallClickFee(String sumCallClickFee) {
		this.sumCallClickFee = sumCallClickFee;
	}

	public String getSumEveCallFee() {
		return sumEveCallFee;
	}

	public void setSumEveCallFee(String callString) {
		this.sumEveCallFee = callString;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public String getSumAllFee() {
		return sumAllFee;
	}

	public void setSumAllFee(String sumAllFee) {
		this.sumAllFee = sumAllFee;
	}

	public String getSumClickFee() {
		return sumClickFee;
	}

	public void setSumClickFee(String sumClickFee) {
		this.sumClickFee = sumClickFee;
	}

	public String getSumCallFee() {
		return sumCallFee;
	}

	public void setSumCallFee(String sumCallFee) {
		this.sumCallFee = sumCallFee;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Phone getPhone() {
		return phone;
	}

	public CompanyAccount getCompanyAccount() {
		return companyAccount;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public void setCompanyAccount(CompanyAccount companyAccount) {
		this.companyAccount = companyAccount;
	}

	public String getIsLave() {
		return isLave;
	}

	public void setIsLave(String isLave) {
		this.isLave = isLave;
	}

	public String getPhoneRateForSevenDay() {
		return phoneRateForSevenDay;
	}

	public void setPhoneRateForSevenDay(String phoneRateForSevenDay) {
		this.phoneRateForSevenDay = phoneRateForSevenDay;
	}

}
