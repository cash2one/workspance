/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-5-19
 */
package com.ast.ast1949.dto.company;

import java.io.Serializable;
import java.util.List;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CrmCsLog;
import com.ast.ast1949.domain.company.CrmCsLogAdded;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-5-19
 */
public class CrmCsLogDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Company company;
	private CrmCsLog log;
	private List<CrmCsLogAdded> addedList;
	
	private String csName;
	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	/**
	 * @return the log
	 */
	public CrmCsLog getLog() {
		return log;
	}
	/**
	 * @param log the log to set
	 */
	public void setLog(CrmCsLog log) {
		this.log = log;
	}
	/**
	 * @return the addedList
	 */
	public List<CrmCsLogAdded> getAddedList() {
		return addedList;
	}
	/**
	 * @param addedList the addedList to set
	 */
	public void setAddedList(List<CrmCsLogAdded> addedList) {
		this.addedList = addedList;
	}
	/**
	 * @return the csName
	 */
	public String getCsName() {
		return csName;
	}
	/**
	 * @param csName the csName to set
	 */
	public void setCsName(String csName) {
		this.csName = csName;
	}
	
	
}
