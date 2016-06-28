package com.zz91.mail.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.mail.thread.MailSendThread;

@Controller
public class MailListController extends BaseController {

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out, MailInfoDomain mailInfoDomain) {
		
//		Map map=null;
//		try {
//			map=FileUtils.readPropertyFile(MailSendThread.MAIL_LIST_PROP, HttpUtils.CHARSET_UTF8);
//		} catch (IOException e) {
//		}
//		
		StringBuffer sb=new StringBuffer();
//		if(map!=null){
//			for(Object key:map.keySet()){
//				sb.append(String.valueOf(key)).append("=").append(String.valueOf(map.get(key))).append("\n");
//			}
//		}
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(MailSendThread.MAIL_LIST_PROP.replace("file:", "")));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		
		
		out.put("maillist", sb.toString());
		return null;
	}
	
	@RequestMapping
	public ModelAndView doUpdate(HttpServletRequest request, Map<String, Object> out, MailInfoDomain mailInfoDomain, 
			String mailList) {
//		String[] items=mailList.split("\n");
//		
//		Properties prop = new Properties();
//		try {
//			InputStream fis = new FileInputStream(MailSendThread.MAIL_LIST_PROP);
//		} catch (FileNotFoundException e) {
//		}
//		
//		for(String item:items){
//			
//		}
		
		String[] items=mailList.split("\n");
		BufferedWriter bw=null;
		try {
			bw = new BufferedWriter(new FileWriter(MailSendThread.MAIL_LIST_PROP.replace("file:", "")));
			
			for(String item:items){
				if(!item.contains("=")){
					continue;
				}
				bw.write(item);
				bw.newLine();// 换行
			}
			
			bw.flush();
		} catch (IOException e) {
		}finally{
			if(bw!=null){
				try {
					bw.close();
				} catch (IOException e) {
				}
			}
		}
		
		return new ModelAndView("redirect:index.htm");
	}

}
