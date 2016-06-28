package com.zz91.sms.common;

/**
 * 
 * @author kongsj(kongsj@asto.com.cn)
 * 
 * 短信抽象类<br />
 * sendSMS(String mobile,String content):<br />
 * 发送短信执行方法,mobile:接收方电话号码,content:发送的内容<br />
 * 返回int类型的结果,代表不同的发送状况<br />
 * 
 * getBalance():<br />
 * 查询余额方法,不同网关的短信发送余额(条数)<br />
 * 
 */
public interface ZZSms {
	
	public final static int WAITING=0;
	
	public final static int SENDING=1;
	
	public final static int SUCCESS=2;
	
	public final static int FAILURE=3;
	
	public Integer send(String mobile,String content);
	
	public Object balance();
	
}
