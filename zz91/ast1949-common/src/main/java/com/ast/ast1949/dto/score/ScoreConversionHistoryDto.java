/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-1
 */
package com.ast.ast1949.dto.score;

import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.score.ScoreConversionHistoryDo;
import com.ast.ast1949.domain.score.ScoreGoodsDo;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-3-1
 */
public class ScoreConversionHistoryDto implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ScoreGoodsDo goods;
	private ScoreConversionHistoryDo history;
	private BbsUserProfilerDO profiler;
	private Company company;
	private CompanyAccount contacts;
	private Integer totalScore;
	
	
	/**
	 * @return the goods
	 */
	public ScoreGoodsDo getGoods() {
		return goods;
	}
	/**
	 * @param goods the goods to set
	 */
	public void setGoods(ScoreGoodsDo goods) {
		this.goods = goods;
	}
	/**
	 * @return the history
	 */
	public ScoreConversionHistoryDo getHistory() {
		return history;
	}
	/**
	 * @param history the history to set
	 */
	public void setHistory(ScoreConversionHistoryDo history) {
		this.history = history;
	}
	/**
	 * @return the profiler
	 */
	public BbsUserProfilerDO getProfiler() {
		return profiler;
	}
	/**
	 * @param profiler the profiler to set
	 */
	public void setProfiler(BbsUserProfilerDO profiler) {
		this.profiler = profiler;
	}
	/**
	 * @return the totalScore
	 */
	public Integer getTotalScore() {
		return totalScore;
	}
	/**
	 * @param totalScore the totalScore to set
	 */
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
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
	 * @return the contacts
	 */
	public CompanyAccount getContacts() {
		return contacts;
	}
	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(CompanyAccount contacts) {
		this.contacts = contacts;
	} 
	
	
}
