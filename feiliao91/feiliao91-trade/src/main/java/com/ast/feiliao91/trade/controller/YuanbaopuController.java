package com.ast.feiliao91.trade.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.dto.ExtResult;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;

@Controller
public class YuanbaopuController extends BaseController{
	
	final static String OPEN_MAIN_URL  ="http://qbtest.yuanbaopu.com:8081";
	final static String OPEN_REGIST_URL  ="/application/quickRegist";

	/**
	 * 手机快速注册
	 * @return
	 * real_name String 用户真实姓名
	cert_no String 证件编号
	logon_id String 帐户号:手机号或邮箱(邮箱	暂不支持)
	login_password String 登录密码,8 到 16 位
	register_from String 注册信息来源地:A1
	 * @throws IOException 
	 * @throws HttpException 
	 */
	@RequestMapping
	public ModelAndView register(HttpServletRequest request,Map<String, Object> out) throws IOException, HttpException{
		ExtResult result =new ExtResult();
//		Map<String, String> map =new HashMap<String, String>();
		NameValuePair [] data =new NameValuePair[]{
			new BasicNameValuePair("real_name", "孔尚杰"),
			new BasicNameValuePair("cert_no", "330327198603018795"),
			new BasicNameValuePair("logon_id", "18069890163"),
			new BasicNameValuePair("login_password", "123456"),
			new BasicNameValuePair("register_from", "yuanbaopu"),
			new BasicNameValuePair("send_active_key", "true"),
		}; 
//		map.put("real_name", StringUtils.encryptUrlParameter("孔尚杰"));
//		map.put("cert_no", "330327198603018795");
//		map.put("logon_id", "13738194812");
//		map.put("login_password", "123456");
//		map.put("register_from", "A1");
//		map.put("send_active_key", "true");
//		String sign = getSign(map);
		String resultMsg = HttpUtils.getInstance().httpPost(OPEN_MAIN_URL+OPEN_REGIST_URL, data, HttpUtils.CHARSET_UTF8);
//		System.out.println(resultMsg);
		result.setData(resultMsg);
		return printJson(result, out);
	}
	
	/**
	 * v	string	Y	API协议版本，可选值：1.1
		method	string	Y	API接口名称
		timestamp	string	Y	时间戳，格式为yyyy-MM-dd HH:mm:ss，例如：2013-08-12 15:51:05。API服务端允许客户端请求时间误差为10分钟
		format	string	N	可选，指定响应格式。默认xml，目前支持：xml、json
		app_key	string	Y	系统分配给应用的AppKey
		sign_method	string	Y	参数的加密方法选择，可选值：md5、hmac
		access_token	string	Y	开放平台分配给用户的access_token，通过oauth协议登陆授权获取。某个API是否需要传入access_token参数，需参考此API的API用户授权类型
		sign	string	Y	对 API 输入参数进行 md5或hmac 加密获得，详细参考如下 3、签名sign
	 * @return
	 */
//	private String getData(){
//		
//	}
	
	/**
	 *  1）输入参数为：
        method= yumei.mobilerecharge.get
        timestamp=2013-09-02 09:12:05
        format=xml
        app_key=test
        v=1.1
        mobile=186581260XX
        sign_method=md5
        access_token=test
    2）按照参数名称升序排列：
        access_token=test
        app_key=test
        format=xml
        method=yumei.mobilerecharge.get
        mobile=186581260XX
        sign_method=md5
        timestamp=2013-09-02 09:12:05
        v=1.1
    3）连接字符串
        连接参数名与参数值,并在首尾加上secret，如下：
        app_secretaccess_tokentestapp_keytestformatxmlmethodyumei.mobilerecharge.getmobile186581260XXsign_methodmd5timestamp2013-09-02 09:12:05v1.1app_secret
    4）生成签名：
        32位大写MD5值-> 7A912E9D6E97AC6A4D1CD3D97CD3ADCA
    5）拼装HTTP请求
	 */
	private String getSign(Map<String, String> map){
		String sign = "";
		TreeMap<String, String> tMap = new TreeMap<String, String>();
		for (String key:map.keySet()) {
			tMap.put(key, map.get(key));
		}
		// 序列化拼装
		for (String key:tMap.keySet()) {
			sign = sign + key + "=" + tMap.get(key);
		}
		// 加上头尾 secret
		sign = "app_secret"+ sign + "app_secret";
		// 生成md5
		try {
			sign = MD5.encode(sign, MD5.LENGTH_32);
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		// 英文转大写
		sign = sign.toUpperCase();
		return sign;
	}
}