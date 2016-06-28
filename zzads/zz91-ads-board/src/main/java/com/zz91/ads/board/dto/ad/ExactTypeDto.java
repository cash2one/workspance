/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-16
 */
package com.zz91.ads.board.dto.ad;

import com.zz91.ads.board.domain.ad.AdExactType;
import com.zz91.ads.board.domain.ad.ExactType;
import com.zz91.ads.board.domain.ad.PositionExactType;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-6-16
 */
public class ExactTypeDto {

	private ExactType exact;
	private AdExactType adExact;
	private PositionExactType positionExact;

	/**
	 * @return the exact
	 */
	public ExactType getExact() {
		return exact;
	}

	/**
	 * @param exact
	 *            the exact to set
	 */
	public void setExact(ExactType exact) {
		this.exact = exact;
	}

	/**
	 * @return the adExact
	 */
	public AdExactType getAdExact() {
		return adExact;
	}

	/**
	 * @param adExact
	 *            the adExact to set
	 */
	public void setAdExact(AdExactType adExact) {
		this.adExact = adExact;
	}

	/**
	 * @return the positionExact
	 */
	public PositionExactType getPositionExact() {
		return positionExact;
	}

	/**
	 * @param positionExact
	 *            the positionExact to set
	 */
	public void setPositionExact(PositionExactType positionExact) {
		this.positionExact = positionExact;
	}

}
