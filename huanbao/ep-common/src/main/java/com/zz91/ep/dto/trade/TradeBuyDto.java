/*
 * 文件名称：TradeBuyDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.trade;

import java.io.Serializable;

import com.zz91.ep.domain.trade.TradeBuy;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：求购信息扩展。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class TradeBuyDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TradeBuy tradeBuy;
	private String compName;
	private Integer isDel;// 公司是否删除
	private String memberCodeBlock;// 非正常用户的member_code
	private String provinceName;
	private String areaName;
	private String supplyAreaName;
	private String buyTypeName;
	private String categoryName;
	private Integer rid;
	private String memberCode;

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public TradeBuy getTradeBuy() {
		return tradeBuy;
	}

	public void setTradeBuy(TradeBuy tradeBuy) {
		this.tradeBuy = tradeBuy;
	}

    public String getSupplyAreaName() {
		return supplyAreaName;
	}

	public void setSupplyAreaName(String supplyAreaName) {
		this.supplyAreaName = supplyAreaName;
	}

	public String getBuyTypeName() {
		return buyTypeName;
	}

	public void setBuyTypeName(String buyTypeName) {
		this.buyTypeName = buyTypeName;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getMemberCodeBlock() {
        return memberCodeBlock;
    }

    public void setMemberCodeBlock(String memberCodeBlock) {
        this.memberCodeBlock = memberCodeBlock;
    }

    public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	
}