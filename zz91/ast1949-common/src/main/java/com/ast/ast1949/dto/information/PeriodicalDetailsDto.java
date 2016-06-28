/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25
 */
package com.ast.ast1949.dto.information;

import java.io.Serializable;

import com.ast.ast1949.domain.information.PeriodicalDetails;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public class PeriodicalDetailsDto implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private PeriodicalDetails periodicalDetails;
	private String periodicalName;


	/**
	 * @return the periodicalDetails
	 */
	public PeriodicalDetails getPeriodicalDetails() {
		return periodicalDetails;
	}

	/**
	 * @param periodicalDetails the periodicalDetails to set
	 */
	public void setPeriodicalDetails(PeriodicalDetails periodicalDetails) {
		this.periodicalDetails = periodicalDetails;
	}

	/**
	 * @return the periodicalName
	 */
	public String getPeriodicalName() {
		return periodicalName;
	}

	/**
	 * @param periodicalName the periodicalName to set
	 */
	public void setPeriodicalName(String periodicalName) {
		this.periodicalName = periodicalName;
	}


}
