/**
 * @author shiqp
 * @date 2016-01-31
 */
package com.ast.feiliao91.dto.goods;

import java.util.List;
import java.util.Map;

import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.goods.Shopping;

public class ShoppingDto {

	private CompanyInfo info;
	private List<Shopping> list;
	private List<ShoppingBuyDto> dtoList;

	private Integer bzjFlag;
	private Integer sevenDayFlag;
    
	public CompanyInfo getInfo() {
		return info;
	}

	public void setInfo(CompanyInfo info) {
		this.info = info;
	}

	public List<Shopping> getList() {
		return list;
	}

	public void setList(List<Shopping> list) {
		this.list = list;
	}

	public List<ShoppingBuyDto> getDtoList() {
		return dtoList;
	}

	public void setDtoList(List<ShoppingBuyDto> dtoList) {
		this.dtoList = dtoList;
	}

	public Integer getBzjFlag() {
		return bzjFlag;
	}

	public void setBzjFlag(Integer bzjFlag) {
		this.bzjFlag = bzjFlag;
	}

	public Integer getSevenDayFlag() {
		return sevenDayFlag;
	}

	public void setSevenDayFlag(Integer sevenDayFlag) {
		this.sevenDayFlag = sevenDayFlag;
	}
}
