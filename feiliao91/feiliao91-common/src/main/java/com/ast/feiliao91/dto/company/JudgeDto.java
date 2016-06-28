/**
 * @author shiqp
 * @date 2016-01-29
 */
package com.ast.feiliao91.dto.company;

import java.util.List;

import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.company.Judge;
import com.ast.feiliao91.domain.goods.Goods;

public class JudgeDto {
	private Judge judge;  //评论信息
	private List<String> picAddress;  //地址
	private String quality;  //购买的数量
	private String nature;  //属性，颜色之类的
	private CompanyInfo info; //公司信息
	private String postTime;//发布时间
	private CompanyInfo buyInfo;//买家家公司信息
	private Goods goods;//产品信息
	private Float price;//单价
	private Integer buyCred;//买家信誉
	private Integer sellCred;//卖家信誉
	public Judge getJudge() {
		return judge;
	}
	public void setJudge(Judge judge) {
		this.judge = judge;
	}
	public List<String> getPicAddress() {
		return picAddress;
	}
	public void setPicAddress(List<String> picAddress) {
		this.picAddress = picAddress;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public CompanyInfo getInfo() {
		return info;
	}
	public void setInfo(CompanyInfo info) {
		this.info = info;
	}
	public String getPostTime() {
		return postTime;
	}
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}
	public CompanyInfo getBuyInfo() {
		return buyInfo;
	}
	public void setBuyInfo(CompanyInfo buyInfo) {
		this.buyInfo = buyInfo;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getBuyCred() {
		return buyCred;
	}
	public void setBuyCred(Integer buyCred) {
		this.buyCred = buyCred;
	}
	public Integer getSellCred() {
		return sellCred;
	}
	public void setSellCred(Integer sellCred) {
		this.sellCred = sellCred;
	}
    
}
