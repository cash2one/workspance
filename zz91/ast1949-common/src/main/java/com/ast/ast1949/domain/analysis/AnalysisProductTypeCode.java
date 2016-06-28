package com.ast.ast1949.domain.analysis;

import java.util.Date;

import com.ast.ast1949.domain.DomainSupport;

/**
 * @author kongsj
 * @date 2012-9-11
 */
public class AnalysisProductTypeCode extends DomainSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3510523348689998671L;
	private Integer id;
	private Integer boliN; // 玻璃 退回
	private Integer boliY; // 玻璃 通过
	private Integer dianzidianqiN; // 电子电器 退回
	private Integer dianzidianqiY; // 电子电器 通过
	private Integer ershoushebeiN; // 二手设备 退回
	private Integer ershoushebeiY; // 二手设备 通过
	private Integer fangzhipinN; // 纺织品 皮革 退回
	private Integer fangzhipinY; // 纺织品 皮革 通过
	private Integer fuwuN; // 服务 退回
	private Integer fuwuY; // 服务 通过
	private Integer jinshuN; // 金属 退回
	private Integer jinshuY; // 金属 通过
	private Integer xiangjiaoN; // 轮胎 橡胶 退回
	private Integer xiangjiaoY; // 轮胎 橡胶 通过
	private Integer qitafeiliaoN; // 其他废料 退回
	private Integer qitafeiliaoY; // 其他废料 通过
	private Integer suliaoN; // 塑料 退回
	private Integer suliaoY; // 塑料 通过
	private Integer zhiN; // 废纸 退回
	private Integer zhiY; // 废纸 通过
	private Integer pigeN; //皮革 退回
	private Integer pigeY; //皮革 通过 
	private Integer luntaiN; //废轮胎 退回
	private Integer luntaiY; //废轮胎 通过
	private Date gmtCreated;
	private Date gmtModified;
	private String operator; // 运营人员帐号
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBoliN() {
		return boliN;
	}
	public void setBoliN(Integer boliN) {
		this.boliN = boliN;
	}
	public Integer getBoliY() {
		return boliY;
	}
	public void setBoliY(Integer boliY) {
		this.boliY = boliY;
	}
	public Integer getDianzidianqiN() {
		return dianzidianqiN;
	}
	public void setDianzidianqiN(Integer dianzidianqiN) {
		this.dianzidianqiN = dianzidianqiN;
	}
	public Integer getDianzidianqiY() {
		return dianzidianqiY;
	}
	public void setDianzidianqiY(Integer dianzidianqiY) {
		this.dianzidianqiY = dianzidianqiY;
	}
	public Integer getErshoushebeiN() {
		return ershoushebeiN;
	}
	public void setErshoushebeiN(Integer ershoushebeiN) {
		this.ershoushebeiN = ershoushebeiN;
	}
	public Integer getErshoushebeiY() {
		return ershoushebeiY;
	}
	public void setErshoushebeiY(Integer ershoushebeiY) {
		this.ershoushebeiY = ershoushebeiY;
	}
	public Integer getFangzhipinN() {
		return fangzhipinN;
	}
	public void setFangzhipinN(Integer fangzhipinN) {
		this.fangzhipinN = fangzhipinN;
	}
	public Integer getFangzhipinY() {
		return fangzhipinY;
	}
	public void setFangzhipinY(Integer fangzhipinY) {
		this.fangzhipinY = fangzhipinY;
	}
	public Integer getFuwuN() {
		return fuwuN;
	}
	public void setFuwuN(Integer fuwuN) {
		this.fuwuN = fuwuN;
	}
	public Integer getFuwuY() {
		return fuwuY;
	}
	public void setFuwuY(Integer fuwuY) {
		this.fuwuY = fuwuY;
	}
	public Integer getJinshuN() {
		return jinshuN;
	}
	public void setJinshuN(Integer jinshuN) {
		this.jinshuN = jinshuN;
	}
	public Integer getJinshuY() {
		return jinshuY;
	}
	public void setJinshuY(Integer jinshuY) {
		this.jinshuY = jinshuY;
	}
	public Integer getXiangjiaoN() {
		return xiangjiaoN;
	}
	public void setXiangjiaoN(Integer xiangjiaoN) {
		this.xiangjiaoN = xiangjiaoN;
	}
	public Integer getXiangjiaoY() {
		return xiangjiaoY;
	}
	public void setXiangjiaoY(Integer xiangjiaoY) {
		this.xiangjiaoY = xiangjiaoY;
	}
	public Integer getQitafeiliaoN() {
		return qitafeiliaoN;
	}
	public void setQitafeiliaoN(Integer qitafeiliaoN) {
		this.qitafeiliaoN = qitafeiliaoN;
	}
	public Integer getQitafeiliaoY() {
		return qitafeiliaoY;
	}
	public void setQitafeiliaoY(Integer qitafeiliaoY) {
		this.qitafeiliaoY = qitafeiliaoY;
	}
	public Integer getSuliaoN() {
		return suliaoN;
	}
	public void setSuliaoN(Integer suliaoN) {
		this.suliaoN = suliaoN;
	}
	public Integer getSuliaoY() {
		return suliaoY;
	}
	public void setSuliaoY(Integer suliaoY) {
		this.suliaoY = suliaoY;
	}
	public Integer getZhiN() {
		return zhiN;
	}
	public void setZhiN(Integer zhiN) {
		this.zhiN = zhiN;
	}
	public Integer getZhiY() {
		return zhiY;
	}
	public void setZhiY(Integer zhiY) {
		this.zhiY = zhiY;
	}
	public Integer getPigeN() {
		return pigeN;
	}
	public void setPigeN(Integer pigeN) {
		this.pigeN = pigeN;
	}
	public Integer getPigeY() {
		return pigeY;
	}
	public void setPigeY(Integer pigeY) {
		this.pigeY = pigeY;
	}
	public Integer getLuntaiN() {
		return luntaiN;
	}
	public void setLuntaiN(Integer luntaiN) {
		this.luntaiN = luntaiN;
	}
	public Integer getLuntaiY() {
		return luntaiY;
	}
	public void setLuntaiY(Integer luntaiY) {
		this.luntaiY = luntaiY;
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}

	
}
