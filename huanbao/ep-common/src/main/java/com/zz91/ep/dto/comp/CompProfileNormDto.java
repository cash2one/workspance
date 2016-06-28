/**
 * 
 */
package com.zz91.ep.dto.comp;

import java.io.Serializable;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;

/**
 * @author mays (mays@asto.com.cn)
 *
 * Created at 2012-11-27
 */
public class CompProfileNormDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4006508653427143122L;
	
	private CompProfile comp;
	private CompAccount account;
	public CompProfile getComp() {
		return comp;
	}
	public void setComp(CompProfile comp) {
		this.comp = comp;
	}
	public CompAccount getAccount() {
		return account;
	}
	public void setAccount(CompAccount account) {
		this.account = account;
	}
	
	

}
