package com.ast.ast1949.dto.yuanliao;

/**
 * @date 2015-08-27
 * @author shiqp
 *
 */
import java.util.List;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.util.StringUtils;

public class YuanliaoDto {
	private Yuanliao yuanliao;
	private Company company;
	private List<YuanliaoDto> list;// 该公司的最新供求
	private Integer picNum;// 原料供求图片数
	private Integer isExpire;// 是否过期
	private Integer expire;// 过期时间
	private String picAddress;// 图片地址
	private String categoryYuanliaoCodeLabel;// 类别名称
	private String address;// 货物所在地名称
	private Integer postlimittime;// 有效期
	private String categoryMainLabel;// 厂家（产地）名称
	private String processLabel;// 加工级别名称
	private String charaLabel;// 特性级别名称
	private String usefulLabel;// 用途级别名称
	public String typeLabel;// 类型名称
	private String location;// 公司注册地
	private String qq;// 用户的qq
	private String gmtSend;// 发货时间
	private Integer countInquiry;// 询盘数
	private String gmtInquiryStr;// 询盘导出时间
	private String fromTitle;
	private String MembershipCode;
	

	public String getMembershipCode() {
		return MembershipCode;
	}

	public void setMembershipCode(String membershipCode) {
		MembershipCode = membershipCode;
	}

	public Integer getPostlimittime() {
		return postlimittime;
	}

	public void setPostlimittime(Integer postlimittime) {
		this.postlimittime = postlimittime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategoryYuanliaoCodeLabel() {
		return categoryYuanliaoCodeLabel;
	}

	public void setCategoryYuanliaoCodeLabel(String categoryYuanliaoCodeLabel) {
		this.categoryYuanliaoCodeLabel = categoryYuanliaoCodeLabel;
	}

	public Integer getIsExpire() {
		return isExpire;
	}

	public void setIsExpire(Integer isExpire) {
		this.isExpire = isExpire;
	}

	public Yuanliao getYuanliao() {
		return yuanliao;
	}

	public void setYuanliao(Yuanliao yuanliao) {
		this.yuanliao = yuanliao;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getPicNum() {
		return picNum;
	}

	public void setPicNum(Integer picNum) {
		this.picNum = picNum;
	}

	public Integer getExpire() {
		return expire;
	}

	public List<YuanliaoDto> getList() {
		return list;
	}

	public void setList(List<YuanliaoDto> list) {
		this.list = list;
	}

	public void setExpire(Integer expire) {
		this.expire = expire;
	}

	public String getPicAddress() {
		return picAddress;
	}

	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}

	public String getCategoryMainLabel() {
		return categoryMainLabel;
	}

	public void setCategoryMainLabel(String categoryMainLabel) {
		this.categoryMainLabel = categoryMainLabel;
	}

	public String getProcessLabel() {
		return processLabel;
	}

	public void setProcessLabel(String processLabel) {
		this.processLabel = processLabel;
	}

	public String getCharaLabel() {
		return charaLabel;
	}

	public void setCharaLabel(String charaLabel) {
		this.charaLabel = charaLabel;
	}

	public String getUsefulLabel() {
		return usefulLabel;
	}

	public void setUsefulLabel(String usefulLabel) {
		this.usefulLabel = usefulLabel;
	}

	public String getTypeLabel() {
		return typeLabel;
	}

	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getQq() {
		if (StringUtils.isNumber(qq)) {
			return qq;
		}
		return "";
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getGmtSend() {
		return gmtSend;
	}

	public void setGmtSend(String gmtSend) {
		this.gmtSend = gmtSend;
	}

	public String getGmtInquiryStr() {
		return gmtInquiryStr;
	}

	public void setGmtInquiryStr(String gmtInquiryStr) {
		this.gmtInquiryStr = gmtInquiryStr;
	}

	public Integer getCountInquiry() {
		return countInquiry;
	}

	public void setCountInquiry(Integer countInquiry) {
		this.countInquiry = countInquiry;
	}

	public String getFromTitle() {
		return fromTitle;
	}

	public void setFromTitle(String fromTitle) {
		this.fromTitle = fromTitle;
	}

}
