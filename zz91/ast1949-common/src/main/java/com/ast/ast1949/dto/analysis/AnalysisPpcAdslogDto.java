package com.ast.ast1949.dto.analysis;

import java.util.List;
import java.util.Map;

public class AnalysisPpcAdslogDto {
	private String time;//时间
	private String date;//周几
	private String name;// 广告位
	private Integer showcount;// 展现量
	private Integer checkcount;// 点击量
	private String clickRate;// 点击率
	private Integer telCount;// 电话量
	private String changeRate;// 转化率
	private Map<String,String> mapN;//广告位名字列表集
    private Map<String,String> mapM;//广告位名字对应的id

	public Map<String, String> getMapN() {
		return mapN;
	}

	public void setMapN(Map<String, String> mapN) {
		this.mapN = mapN;
	}

	public Map<String, String> getMapM() {
		return mapM;
	}

	public void setMapM(Map<String, String> mapM) {
		this.mapM = mapM;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getShowcount() {
		return showcount;
	}

	public void setShowcount(Integer showcount) {
		this.showcount = showcount;
	}

	public Integer getCheckcount() {
		return checkcount;
	}

	public void setCheckcount(Integer checkcount) {
		this.checkcount = checkcount;
	}

	public String getClickRate() {
		return clickRate;
	}

	public void setClickRate(String clickRate) {
		this.clickRate = clickRate;
	}

	public Integer getTelCount() {
		return telCount;
	}

	public void setTelCount(Integer telCount) {
		this.telCount = telCount;
	}

	public String getChangeRate() {
		return changeRate;
	}

	public void setChangeRate(String changeRate) {
		this.changeRate = changeRate;
	}
}
