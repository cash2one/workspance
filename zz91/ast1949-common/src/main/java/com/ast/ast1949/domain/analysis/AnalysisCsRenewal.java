package com.ast.ast1949.domain.analysis;

import java.io.Serializable;
import java.util.Date;

public class AnalysisCsRenewal  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String csAccount;//cs帐号
	private Date gmtTarget;//统计日期
	private Date gmtCreated;
	private Date gmtModified;
	private Integer a;//统计过期180天内当月续签客户数(续签时间：当月，到期时间：已过期，过期半年内)
	private Integer b;//统计过期180天外当月续签客户数(续签时间：当月，到期时间：已过期，过期半年前)
	private Integer c;//统计当月到期客户月前续签数(续签时间：月前，到期时间：当月)
	private Integer d;//统计当月到期客户当月续签数(续签时间：当月，到期时间：当月)
	private Integer e;//E=F-C-D
	private Integer f;//统计当月到期客户总数(到期时间：当月)
	private Integer g;//未到期客户当月续签数 提前180天内(续签时间：当月，到期时间：未过期，还有半年不到过期)
	private Integer h;//未到期客户当月续签数： 提前180天外续签(续签时间：当月，到期时间：未过期，还有半年以上过期)
	private Integer i;//当月续签总数(续签时间：当月)
	private Integer j;//统计有效客户数=3+4+5
	private Float k;//续签率=J/F
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCsAccount() {
		return csAccount;
	}
	public void setCsAccount(String csAccount) {
		this.csAccount = csAccount;
	}
	public Date getGmtTarget() {
		return gmtTarget;
	}
	public void setGmtTarget(Date gmtTarget) {
		this.gmtTarget = gmtTarget;
	}
	public Date getGmtCreated() {
		return gmtCreated;
	}
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Integer getA() {
		return a;
	}
	public void setA(Integer a) {
		this.a = a;
	}
	public Integer getB() {
		return b;
	}
	public void setB(Integer b) {
		this.b = b;
	}
	public Integer getC() {
		return c;
	}
	public void setC(Integer c) {
		this.c = c;
	}
	public Integer getD() {
		return d;
	}
	public void setD(Integer d) {
		this.d = d;
	}
	public Integer getE() {
		return e;
	}
	public void setE(Integer e) {
		this.e = e;
	}
	public Integer getF() {
		return f;
	}
	public void setF(Integer f) {
		this.f = f;
	}
	public Integer getG() {
		return g;
	}
	public void setG(Integer g) {
		this.g = g;
	}
	public Integer getH() {
		return h;
	}
	public void setH(Integer h) {
		this.h = h;
	}
	public Integer getI() {
		return i;
	}
	public void setI(Integer i) {
		this.i = i;
	}
	public Integer getJ() {
		return j;
	}
	public void setJ(Integer j) {
		this.j = j;
	}
	public Float getK() {
		return k;
	}
	public void setK(Float k) {
		this.k = k;
	}
	
}
