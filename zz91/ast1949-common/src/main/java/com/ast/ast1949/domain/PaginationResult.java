package com.ast.ast1949.domain;

import java.util.List;

public class PaginationResult {

	private List<DomainSupport> resultList;
	private int resultTotal;
	public List<DomainSupport> getResultList() {
		return resultList;
	}
	public void setResultList(List<DomainSupport> resultList) {
		this.resultList = resultList;
	}
	public int getResultTotal() {
		return resultTotal;
	}
	public void setResultTotal(int resultTotal) {
		this.resultTotal = resultTotal;
	}
	
}
