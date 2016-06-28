package com.ast.ast1949.domain.yuanliao;

/**
 * @date 2015-09-02
 * @author shiqp
 * 
 */
public class YuanLiaoSearch {
	private Integer isVip;
	private String startTime;
	private String endTime;
	private String keyword;
	private Integer isExpire;//是否过期
	private Integer sourceTypeCode;// 发布来源
	private Integer checkStatus;// 审核状态
	private Integer isDel;// 删除状态
	private Integer isPause;// 发布状态
	private String yuanliaoTypeCode;// 供求类型
	private String categoryYuanliaoCode;//类别
	private String checkArray;//审核状态集
	private String sort; //要排序字段
	private Integer[] checkArrays;
	private Integer companyId;
	private Integer hasPic;//是否有图片
	private Integer noCompanyId;//不是该公司的原料供求
	private String province;//省份 列表页or搜索页
	private String code;//类别 列表页
	private String type;//供求类型
	private String dir;//排序
	private Integer limit;//发布时间
	
	

	public Integer getHasPic() {
		return hasPic;
	}

	public void setHasPic(Integer hasPic) {
		this.hasPic = hasPic;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer[] getCheckArrays() {
		return checkArrays;
	}

	public void setCheckArrays(Integer[] checkArrays) {
		this.checkArrays = checkArrays;
	}

	public String getCheckArray() {
		return checkArray;
	}

	public void setCheckArray(String checkArray) {
		this.checkArray = checkArray;
	}

	public String getCategoryYuanliaoCode() {
		return categoryYuanliaoCode;
	}

	public void setCategoryYuanliaoCode(String categoryYuanliaoCode) {
		this.categoryYuanliaoCode = categoryYuanliaoCode;
	}

	public Integer getSourceTypeCode() {
		return sourceTypeCode;
	}

	public void setSourceTypeCode(Integer sourceTypeCode) {
		this.sourceTypeCode = sourceTypeCode;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsPause() {
		return isPause;
	}

	public void setIsPause(Integer isPause) {
		this.isPause = isPause;
	}

	public String getYuanliaoTypeCode() {
		return yuanliaoTypeCode;
	}

	public void setYuanliaoTypeCode(String yuanliaoTypeCode) {
		this.yuanliaoTypeCode = yuanliaoTypeCode;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getIsExpire() {
		return isExpire;
	}

	public void setIsExpire(Integer isExpire) {
		this.isExpire = isExpire;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getNoCompanyId() {
		return noCompanyId;
	}

	public void setNoCompanyId(Integer noCompanyId) {
		this.noCompanyId = noCompanyId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
