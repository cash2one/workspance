package com.zz91.ep.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ResultSolrDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Integer>  idList;
	
	private Map<String, Map<String, List<String>>> highMap;
	
	private Integer totals;
	
	public Integer getTotals() {
		return totals;
	}

	public void setTotals(Integer totals) {
		this.totals = totals;
	}

	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}

	public Map<String, Map<String, List<String>>> getHighMap() {
		return highMap;
	}

	public void setHighMap(Map<String, Map<String, List<String>>> highMap) {
		this.highMap = highMap;
	}



	

}
