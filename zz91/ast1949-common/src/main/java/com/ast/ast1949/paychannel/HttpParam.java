package com.ast.ast1949.paychannel;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpParam implements Serializable {

	private static final long serialVersionUID = 1L;

	// 渠道编号
	private String channelId;

	// 交易类型--4位
	private String transType;// 交易类型Paybill ；RechargeBILL ；WithDrawBILL

	// 渠道交易类型
	private String channelTransType;

	/*
	 * 支付交易，指的是 Paybill ；RechargeBILL ；WithDrawBILL
	 */
	private Object bizBean;

	private HttpServletRequest request;

	private HttpServletResponse response;

	public Object getBizBean() {
		return bizBean;
	}

	public void setBizBean(Object bizBean) {
		this.bizBean = bizBean;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getChannelTransType() {
		return channelTransType;
	}

	public void setChannelTransType(String channelTransType) {
		this.channelTransType = channelTransType;
	}
}
