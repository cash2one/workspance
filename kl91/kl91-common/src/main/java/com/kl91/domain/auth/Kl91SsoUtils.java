package com.kl91.domain.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;


public class Kl91SsoUtils{

	private static Kl91SsoUtils _instance=null;
	
	private Kl91SsoUtils(){
		
	}
	
	synchronized public static Kl91SsoUtils getInstance(){
		if(_instance==null){
			_instance = new Kl91SsoUtils();
		}
		return _instance;
	}
	
	private final static int TIMEOUT=100000;  //TODO 发布后改为10秒＝10000
	
	public static void main(String[] args) {
		try {
			System.out.println(MD5.encode("admin"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送消息包含：account，password，projectCode
	 * ticket=account+password+projectCode+projectPassword+key
	 * 返回信息包含：sessionUser，ticket，key
	 */
	public Kl91SsoUser validateUser(HttpServletResponse response, String account, String password, Integer expired){
		String encodePwd="";
		try {
			encodePwd = MD5.encode(password,MD5.LENGTH_32);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return validateUserByEncodePwd(response,account,encodePwd,expired);
	}
	
	public Kl91SsoUser validateUserByEncodePwd(HttpServletResponse response, String account, String password, Integer expired){
		Kl91SsoUser ssoUser = null;
			try {
				String encodeAccount = URLEncoder.encode(account, "utf-8");
				//提交给服务器验证
				URL url = new URL(Kl91SsoConst.API_HOST+"/ssoUser.htm?a="+encodeAccount+"&p="+password);
				Document doc = Jsoup.parse(url, TIMEOUT);
				//验证返回结果是否正确
				String key=doc.select("#key").val();
				String ticket=doc.select("#ticket").val();
				if(StringUtils.isEmpty(key) || StringUtils.isEmpty(ticket)){
					return null;
				}
				
				String validateTicket=MD5.encode(account+password+key);
				if(!ticket.equals(validateTicket)){
					return null;
				}
				
				ssoUser = new Kl91SsoUser();
				ssoUser.setAccount(doc.select("#account").val());
				ssoUser.setCompanyId(Integer.valueOf(doc.select("#companyId").val()));
				ssoUser.setEmail(doc.select("#email").val());
				ssoUser.setDomain(doc.select("#domain").val());
				ssoUser.setKey(key);
				ssoUser.setTicket(ticket);
				
				HttpUtils.getInstance().setCookie(response, Kl91SsoConst.TICKET_KEY, ticket, Kl91SsoConst.SSO_DOMAIN, null);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		
		return ssoUser;
	}
	
	/**
	 * 发送信息包含：ticket，projectCode
	 * vticket=projectCode+projectPassword+key
	 * 返回信息包含：SessionUser，vticket，key
	 */
	public Kl91SsoUser validateTicket(HttpServletRequest request){
		URL url;
		Kl91SsoUser ssoUser = null;
		String ticket=HttpUtils.getInstance().getCookie(request, Kl91SsoConst.TICKET_KEY, Kl91SsoConst.SSO_DOMAIN);
		if(StringUtils.isEmpty(ticket)){
			return null;
		}
		try {
			url = new URL(Kl91SsoConst.API_HOST+"/ssoTicket.htm?t="+ticket);
			Document doc = Jsoup.parse(url, TIMEOUT);
			String key = doc.select("#key").val();
			if(StringUtils.isEmpty(key)){   //|| StringUtils.isEmpty(vticket)
				return null;
			}
			
			ssoUser = new Kl91SsoUser();
			ssoUser.setAccount(doc.select("#account").val());
			ssoUser.setCompanyId(Integer.valueOf(doc.select("#companyId").val()));
			ssoUser.setEmail(doc.select("#email").val());
			ssoUser.setDomain(doc.select("#domain").val());
			ssoUser.setKey(key);
			ssoUser.setTicket(ticket);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ssoUser;
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response, String sessionid){
		//得到票据，重置cookie
		String ticket=HttpUtils.getInstance().getCookie(request, Kl91SsoConst.TICKET_KEY, Kl91SsoConst.SSO_DOMAIN);
		URL url;
		try {
			url = new URL(Kl91SsoConst.API_HOST+"/ssoLogout.htm?t="+ticket);
			Jsoup.parse(url, TIMEOUT);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		clearnSessionUser(request, sessionid);
		HttpUtils.getInstance().setCookie(response, Kl91SsoConst.TICKET_KEY, null, Kl91SsoConst.SSO_DOMAIN, 0);
	}
	
	public Kl91SsoUser getSessionUser(HttpServletRequest request, String sessionid){
		String tickkey=HttpUtils.getInstance().getCookie(request, Kl91SsoConst.TICKET_KEY, Kl91SsoConst.SSO_DOMAIN);
		if(StringUtils.isEmpty(tickkey)){
			clearnSessionUser(request, sessionid);
			return null;
		}
		Kl91SsoUser ssoUser = null;
		if(sessionid==null){
			// TODO 使用session实现
			ssoUser = (Kl91SsoUser) request.getSession().getAttribute(Kl91SsoUser.SESSION_KEY);
		}else{
			// TODO 使用memcached实现
		}
		return ssoUser;
	}
	
	public void setSessionUser(HttpServletRequest request, Kl91SsoUser ssoUser, String sessionid){
		if(sessionid==null){
			// TODO 使用session实现
			request.getSession().setAttribute(Kl91SsoUser.SESSION_KEY, ssoUser);
		}else{
			// TODO 使用memcached实现
		}
	}
	
	public void clearnSessionUser(HttpServletRequest request, String sessionid){
		if(sessionid==null){
			// TODO 使用session实现
			request.getSession().removeAttribute(Kl91SsoUser.SESSION_KEY);
		}else{
			// TODO 使用memcached实现
		}
	}
}
