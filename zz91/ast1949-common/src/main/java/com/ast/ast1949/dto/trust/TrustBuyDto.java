/**
 * @author kongsj
 * @date 2015年5月7日
 * 
 */
package com.ast.ast1949.dto.trust;

import com.ast.ast1949.domain.DomainSupport;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.domain.trust.TrustDealer;

public class TrustBuyDto extends DomainSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5285552417173125152L;
	private TrustBuy trustBuy;
	private TrustDealer trustDealer;
	private Company company;
	private CompanyAccount account;
	private String area;
	private String categoryName;
	private String contact;
	private Integer dealerId;
	private String areaLabel;
	private Integer isPuase;// 0 不暂停发布 1暂停发布
	private String isGong;// 1表示不能，0表示能
	private String cnDate;

	public String getIsGong() {
		return isGong;
	}

	public void setIsGong(String isGong) {
		this.isGong = isGong;
	}

	public TrustBuy getTrustBuy() {
		return trustBuy;
	}

	public void setTrustBuy(TrustBuy trustBuy) {
		this.trustBuy = trustBuy;
	}

	public TrustDealer getTrustDealer() {
		return trustDealer;
	}

	public void setTrustDealer(TrustDealer trustDealer) {
		this.trustDealer = trustDealer;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CompanyAccount getAccount() {
		return account;
	}

	public void setAccount(CompanyAccount account) {
		this.account = account;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Integer getDealerId() {
		return dealerId;
	}

	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}

	public String getAreaLabel() {
		return areaLabel;
	}

	public void setAreaLabel(String areaLabel) {
		this.areaLabel = areaLabel;
	}

	public Integer getIsPuase() {
		return isPuase;
	}

	public void setIsPuase(Integer isPuase) {
		this.isPuase = isPuase;
	}

	public String getCnDate() {
		return cnDate;
	}

	public void setCnDate(String cnDate) {
		this.cnDate = cnDate;
	}

}
