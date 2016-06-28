/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dto;

import java.io.Serializable;

/**
 * @author totly created on 2011-12-10
 */
public class SaleCompanyDataDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String saleAccount;//销售帐号
    private String saleName;//销售名字
    private String saleDept;//销售部门
    private Integer start1;//1星客户数
    private Integer start2;//2星客户数
    private Integer start3;//3星客户数
    private Integer start4;//4星客户数
    private Integer start5;//5星客户数
    private Integer uncontact;//未联系客户数
    private Integer tomContact;//明天安排联系客户数
    private Integer todContact;//今天安排联系客户数
    private Integer totalsComp;//客户数合计
    private Integer newUnContact;//新分配未联系数
    private Integer seaUnContact;//公海挑入未联系数
    private Integer lostComp;//跟丢客户数
    private Integer dragDestroy;//拖/毁单客户数
    
	public String getSaleAccount() {
		return saleAccount;
	}
	public void setSaleAccount(String saleAccount) {
		this.saleAccount = saleAccount;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getSaleDept() {
		return saleDept;
	}
	public void setSaleDept(String saleDept) {
		this.saleDept = saleDept;
	}
	public Integer getStart1() {
		return start1;
	}
	public void setStart1(Integer start1) {
		this.start1 = start1;
	}
	public Integer getStart2() {
		return start2;
	}
	public void setStart2(Integer start2) {
		this.start2 = start2;
	}
	public Integer getStart3() {
		return start3;
	}
	public void setStart3(Integer start3) {
		this.start3 = start3;
	}
	public Integer getStart4() {
		return start4;
	}
	public void setStart4(Integer start4) {
		this.start4 = start4;
	}
	public Integer getStart5() {
		return start5;
	}
	public void setStart5(Integer start5) {
		this.start5 = start5;
	}
	public Integer getUncontact() {
		return uncontact;
	}
	public void setUncontact(Integer uncontact) {
		this.uncontact = uncontact;
	}
	public Integer getTomContact() {
		return tomContact;
	}
	public void setTomContact(Integer tomContact) {
		this.tomContact = tomContact;
	}
	public void setTodContact(Integer todContact) {
		this.todContact = todContact;
	}
	public Integer getTodContact() {
		return todContact;
	}
	public void setTotalsComp(Integer totalsComp) {
		this.totalsComp = totalsComp;
	}
	public Integer getTotalsComp() {
		return totalsComp;
	}
	public void setNewUnContact(Integer newUnContact) {
		this.newUnContact = newUnContact;
	}
	public Integer getNewUnContact() {
		return newUnContact;
	}
	public void setSeaUnContact(Integer seaUnContact) {
		this.seaUnContact = seaUnContact;
	}
	public Integer getSeaUnContact() {
		return seaUnContact;
	}
	public void setLostComp(Integer lostComp) {
		this.lostComp = lostComp;
	}
	public Integer getLostComp() {
		return lostComp;
	}
	public void setDragDestroy(Integer dragDestroy) {
		this.dragDestroy = dragDestroy;
	}
	public Integer getDragDestroy() {
		return dragDestroy;
	}
}