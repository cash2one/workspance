package com.ast.ast1949.web.controller.zz91.admin;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SimpleTimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.http.HttpUtils;

import jodd.util.Base64;

@Controller
public class CdnController {
	private static final String HMAC_SHA1 = "HmacSHA1";
	private String getUrl = "";
    @RequestMapping
	public ModelAndView fresh(){
	   return null;
	}
	
	@RequestMapping
	public ModelAndView refresh(HttpServletRequest req, Map<String, String> map) throws Exception {
		String urlto = req.getParameter("text");
		String files=req.getParameter("wenjian");
		if (urlto == null) {
			map.put("message", "请求不能为空");
			return null;
		}
		//自动补全文件结尾
		if(files.equals("Directory")){
			if(!urlto.substring(urlto.length()-1).equals("/")){
				urlto=urlto+"/";
			}
		}
		//自动补全开头http://开头
		String br=urlto.substring(0, 7);
		if(!br.equals("http://")){
			urlto="http://"+urlto;
		}
        //GMT时间格式请求
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		format.setTimeZone(new SimpleTimeZone(0, "GMT"));
		String yeas = format.format(date);
		long suiji = System.currentTimeMillis();
		// 参数
		String Action = URLEncoder.encode("Action", "UTF-8");
		String ActionValues = URLEncoder.encode("RefreshObjectCaches", "UTF-8");

		String ObjectPath = URLEncoder.encode("ObjectPath", "UTF-8");
		String ObjectPathValues = URLEncoder.encode(urlto, "UTF-8");

		String ObjectType = URLEncoder.encode("ObjectType", "UTF-8");
		String ObjectTypeValues = URLEncoder.encode(files, "UTF-8");

		String Format = URLEncoder.encode("Format", "UTF-8");
		String FormatValues = URLEncoder.encode("JSON", "UTF-8");

		String Version = URLEncoder.encode("Version", "UTF-8");
		String VersionValues = URLEncoder.encode("2014-11-11", "UTF-8");

		String AccessKeyId = URLEncoder.encode("AccessKeyId", "UTF-8");
		String AccessKeyIdValues = URLEncoder.encode("vkryNziM4uWV4Jhc", "UTF-8");

		String SignatureMethod = URLEncoder.encode("SignatureMethod", "UTF-8");
		String SignatureMethodValues = URLEncoder.encode("HMAC-SHA1", "UTF-8");

		String Timestamp = URLEncoder.encode("Timestamp", "UTF-8");
		String TimestampValues = URLEncoder.encode(yeas, "UTF-8");

		String SignatureVersion = URLEncoder.encode("SignatureVersion", "UTF-8");
		String SignatureVersionValues = URLEncoder.encode("1.0", "UTF-8");

		String SignatureNonce = URLEncoder.encode("SignatureNonce", "UTF-8");
		String SignatureNonceValues = URLEncoder.encode(String.valueOf(suiji), "UTF-8");


			String guifan = AccessKeyId + "=" + AccessKeyIdValues + "&" + Action + "=" + ActionValues + "&" + Format + "="
					+ FormatValues + "&" + ObjectPath + "=" + ObjectPathValues + "&" + ObjectType + "=" + ObjectTypeValues
					+ "&" + SignatureMethod + "=" + SignatureMethodValues + "&" + SignatureNonce + "="
					+ SignatureNonceValues + "&" + SignatureVersion + "=" + SignatureVersionValues + "&" + Timestamp + "="
					+ TimestampValues + "&" + Version + "=" + VersionValues;
            //请求签名转换
			String ToSign = "GET" + "&" + URLEncoder.encode("/", "UTF-8") + "&" + URLEncoder.encode(guifan, "UTF-8");

			String key = "1sD6OqkY1jTOZfYn2sw2D7oreuC83a&";
			byte[] key1 = key.getBytes("US-ASCII");
			SecretKeySpec signingKey = new SecretKeySpec(key1, HMAC_SHA1);
			Mac mac = Mac.getInstance(HMAC_SHA1);
			mac.init(signingKey);
			byte[] text = ToSign.toString().getBytes("US-ASCII");
			byte[] rawHmac = mac.doFinal(text);
			String base64Text = Base64.encodeToString(rawHmac);
			getUrl = "http://cdn.aliyuncs.com/?" + guifan + "&" + "Signature"
					+ "=" + URLEncoder.encode(base64Text, "UTF-8");
			String box = HttpUtils.getInstance().httpGet(getUrl, "UTF-8");
			if(box!=""){
				map.put("message", "刷新成功");
				map.put("message2", box);
			}else{
			map.put("message", "刷新失败");
			}
		

		return null;
	}
}
