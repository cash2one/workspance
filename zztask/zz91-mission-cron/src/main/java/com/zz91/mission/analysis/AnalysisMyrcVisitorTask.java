package com.zz91.mission.analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class AnalysisMyrcVisitorTask implements ZZTask {
	private final static String DB = "ast";
	private static String LOG_FILE = "/usr/data/log4z/zz91Analysis/run.";
	private final static String LOG_DATE_FORMAT = "yyyy-MM-dd";
	@SuppressWarnings("resource")
	@Override
	public boolean exec(Date baseDate) throws Exception {
		String targetDate = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), LOG_DATE_FORMAT);
		 BufferedReader br = new BufferedReader(new FileReader(LOG_FILE+ targetDate));
		 String line;
		 Map<String,String> maps=new HashMap<String,String>();
		 List<Map<String,String>> listM=new ArrayList<Map<String,String>>();
	    while ((line = br.readLine()) != null) {
	       	JSONObject job=JSONObject.fromObject(JSONObject.fromObject(line).getString("data"));
	       	JSONObject jobs=JSONObject.fromObject(JSONObject.fromObject(line));
	       	String str=job.getString("url");
	       	String ip=jobs.getString("ip");
	       	if(!"61.234.184.252".equals(ip)&&!"115.236.188.99".equals(ip)&&!"115.236.188.98".equals(ip)){
	       		Map<String,String> map=new HashMap<String,String>();
	       		//交易中心最终页
	       		if(str.contains("http://trade.zz91.com/productDetails")){
	       			//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
	       			//标题
	       			map.put("title", null);
	       			//被访问的公司id
	       			String id=str.replace("http://trade.zz91.com/productDetails", "").replace(".htm", "");
		       		String targetId=getTargetId(id);
		       		map.put("targetId", targetId);
	       			map.put("address", maps.get(ip));
	       			listM.add(map);
	       		}
	       		// 样品最终页
		       	if(str.contains("http://yang.zz91.com/sale/")){
		       		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//链接
	       			map.put("url", str);
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
		       		//标题
		       		String id=str.replace("http://yang.zz91.com/sale/", "").replace(".htm", "");
		       		String targetId=getTargetId(id);
		       		String title=products(id).get("title");
		       		map.put("title",title+"样品申请_"+title+"交易平台-zz91再生网");
		       		map.put("address", maps.get(ip));
		       		//被访问公司id
		       		map.put("targetId", targetId);
	       			listM.add(map);
		       	}
		       	//来电宝商铺首页,index后面是公司id
		    	if(str.contains("http://www.zz91.com/ppc/index")){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
	       		    //被访问公司id
	       			if(job.keySet().contains("companyId")){
	       				map.put("targetId", job.getString("companyId"));
	       			}else{
	       				String targetId=str.replace("http://www.zz91.com/ppc/index", "");
	       				Integer j=0;
	       				for(int i=0;i<targetId.length();i++){
	       					if(targetId.charAt(i)<48 || targetId.charAt(i)>57){
	       						j=i;
	       					}else{
	       						break;
	       					}
	       				}
	       				targetId=targetId.substring(0, j);
	       				map.put("targetId",targetId);
	       			}
	       			//标题
	       			if(StringUtils.isNotEmpty(getCompany(map.get("targetId")).get("business"))){
	       				map.put("title", getCompany(map.get("targetId")).get("business")+"-"+getCompany(map.get("targetId")).get("name"));
	       			}else{
	       				map.put("title",getCompany(map.get("targetId")).get("name"));
	       			}
		    	
		    		map.put("address", maps.get(ip));
	       			listM.add(map);
		       	}
		    	//来电宝供求信息，zxgq后面是公司id
		    	if(str.contains("http://www.zz91.com/ppc/zxgq")){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
		    		//标题
		    		map.put("title","供求列表-"+getCompany(job.getString("companyId")).get("name"));
		    		map.put("address", maps.get(ip));
		    		//被访问公司id
		       		map.put("targetId", job.getString("companyId"));
	       			listM.add(map);
		       	}
		    	//来电宝公司动态，gsdt后面是公司id
		    	if(str.contains("http://www.zz91.com/ppc/gsdt")){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
		    		//标题
		    		map.put("title","公司动态-"+getCompany(job.getString("companyId")).get("name"));
		    		map.put("address", maps.get(ip));
		    		//被访问公司id
		       		map.put("targetId", job.getString("companyId"));
	       			listM.add(map);
		       	}
		    	//来电宝公司介绍，gsjs后面是公司id
		    	if(str.contains("http://www.zz91.com/ppc/gsjs")){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
		    		//标题
		    		map.put("title","企业介绍-"+getCompany(job.getString("companyId")).get("name"));
		    		map.put("address", maps.get(ip));
		    		//被访问公司id
		       		map.put("targetId", job.getString("companyId"));
	       			listM.add(map);
		       	}
		    	//来电宝联系方式，contact后面是公司id
		    	if(str.contains("http://www.zz91.com/ppc/contact")){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
		    		//标题
	       			if(StringUtils.isNotEmpty(getCompany(job.getString("companyId")).get("business"))){
	       				map.put("title",getCompany(job.getString("companyId")).get("name"));
	       			}
		    		map.put("address", maps.get(ip));
		    		//被访问公司id
		       		map.put("targetId", job.getString("companyId"));
	       			listM.add(map);
		       	}
		    	//来电宝供求信息最终页，productdetail后面是产品id
		    	if(str.contains("http://www.zz91.com/ppc/productdetail")){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
		    		//标题
	       			if(job.keySet().contains("companyId")){
	       				map.put("targetId", job.getString("companyId"));
	       			}else{
	       				map.put("targetId", products(str.replace("http://www.zz91.com/ppc/productdetail", "").replace(".htm", "")).get("company_id"));
	       			}
		    		map.put("title",products(str.replace("http://www.zz91.com/ppc/productdetail", "").replace(".htm", "")).get("title")+"-"+getCompany(map.get("targetId")).get("name"));
		    		map.put("address", maps.get(ip));
	       			listM.add(map);
		       	}
	       		//获取域名
	       		String[] string=str.split("[.]");
		    	String result=null;
		    	if(string[0].contains("http")){
		    	    result=string[0].substring(7);
		    	}
		    	//高会公司介绍
		    	if(str.contains("gsjs.htm")){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
		    		//标题
		    	    String targetId=getTarget(result);
		    		if(isExit(result)){
		    			Map<String,String> company=getCompany(targetId);
		    			if(StringUtils.isNotEmpty(getCompany(targetId).get("tags"))){
		    				map.put("title", "公司介绍_"+company.get("tags")+"-"+company.get("name"));
		    			}else{
		    				map.put("title", "公司介绍-"+company.get("name"));
		    			}
		    		}
		    		map.put("address", maps.get(ip));
		    		//被访问公司id
		       		map.put("targetId", targetId);
	       			listM.add(map);
		       	}
		    	//高会最新供求
		    	if(str.contains("zxgq.htm")){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
		    		//标题
		    		String targetId=getTarget(result);
		    		if(isExit(result)){
		    			Map<String,String> company=getCompany(targetId);
		    			if(StringUtils.isNotEmpty(getCompany(targetId).get("tags"))){
		    				map.put("title", "最新供求_"+company.get("tags")+"-"+company.get("name"));
		    			}else{
		    				map.put("title", "最新供求-"+company.get("name"));
		    			}
		    		}
		    		map.put("address", maps.get(ip));
		    		//被访问公司id
		       		map.put("targetId", targetId);
	       			listM.add(map);
		       	}
		    	//高会公司动态
		    	if(str.contains("gsdt.htm")){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
	       			//标题
		    		String targetId=getTarget(result);
		    		if(isExit(result)){
		    			Map<String,String> company=getCompany(targetId);
		    			if(StringUtils.isNotEmpty(getCompany(targetId).get("tags"))){
		    				map.put("title", "公司动态_"+company.get("tags")+"-"+company.get("name"));
		    			}else{
		    				map.put("title", "公司动态-"+company.get("name"));
		    			}
		    		}
		    		map.put("address", maps.get(ip));
		    		//被访问公司id
		       		map.put("targetId", targetId);
	       			listM.add(map);
		       	}
		    	//高会诚信档案
		    	if(str.contains("cxda.htm")){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
	       			//标题
		    		String targetId=getTarget(result);
		    		if(isExit(result)){
		    			Map<String,String> company=getCompany(targetId);
		    			if(StringUtils.isNotEmpty(getCompany(targetId).get("tags"))){
		    				map.put("title", "诚信档案_"+company.get("tags")+"-"+company.get("name"));
		    			}else{
		    				map.put("title", "诚信档案-"+company.get("name"));
		    			}
		    		}
		    		map.put("address", maps.get(ip));
		    		//被访问公司id
		       		map.put("targetId", targetId);
	       			listM.add(map);
		       	}
		    	//高会在线留言
		    	if(str.contains("zxly.htm")){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
	       			//标题
		    		String targetId=getTarget(result);
		    		if(isExit(result)){
		    			Map<String,String> company=getCompany(targetId);
		    			if(StringUtils.isNotEmpty(getCompany(targetId).get("tags"))){
		    				map.put("title", "在线留言_"+company.get("tags")+"-"+company.get("name"));
		    			}else{
		    				map.put("title", "在线留言-"+company.get("name"));
		    			}
		    		}
		    		map.put("address", maps.get(ip));
		    		//被访问公司id
		       		map.put("targetId", targetId);
	       			listM.add(map);
		       	}
		    	//高会联系方式
		    	if(str.contains("lxfs.htm")){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
	       			//标题
		    		String targetId=getTarget(result);
		    		if(isExit(result)){
		    			Map<String,String> company=getCompany(targetId);
		    			if(StringUtils.isNotEmpty(getCompany(targetId).get("tags"))){
		    				map.put("title", "联系方式_"+company.get("tags")+"-"+company.get("name"));
		    			}else{
		    				map.put("title", "联系方式-"+company.get("name"));
		    			}
		    		}
		    		map.put("address", maps.get(ip));
		    		//被访问公司id
		       		map.put("targetId", targetId);
	       			listM.add(map);
		       	}
		    	//高会供求最终页
		    	if(StringUtils.isNotEmpty(result)&&str.contains("products")){
		    		if(isExit(result)){
		    		//来访时间
	       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
	       			//来访用户
	       			if(StringUtils.isNotEmpty(job.getString("account"))){
	       				map.put("companyId", getCompanyId(job.getString("account")));
	       				map.put("contact", getContact(map.get("companyId")));
	       				map.put("business", getCompany(map.get("companyId")).get("business"));
	       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
	       			}
	       			if(StringUtils.isEmpty(map.get("companyId"))){
	       				map.put("companyId", "0");
	       				map.put("contact", "游客");
	       				map.put("business", "--");
	       				map.put("companyName", "--");
	       			}
	       			//来访用户所在的地区
	       			if(!maps.keySet().contains(ip)){
	       				maps.put(ip, getAddress(ip));
	       			}
	       			//链接
	       			map.put("url", str);
		    		//标题
		    		String targetId=getTarget(result);
		    		Map<String,String> products=new HashMap<String,String>();
		    		if(str.contains("/static/")){
		    			products=products(str.replace("http://"+result+".zz91.com/static/products.htm?id=", ""));
		    		}else{
		    			products=products(str.replace("http://"+result+".zz91.com/products", "").replace(".htm", ""));
		    		}
		    		if("10331000".equals(products.get("productsType"))){
		    			map.put("title", "供应"+products.get("title"));
		    		}else if("10331001".equals(products.get("productsType"))){
		    			map.put("title", "求购"+products.get("title"));
		    		}else{
		    			map.put("title", "合作"+products.get("title"));
		    		}
		    		map.put("address", maps.get(ip));
		    		//被访问公司id
		       		map.put("targetId", targetId);
	       			listM.add(map);
		    	}
		     }
		    	//高会首页
		    	if(StringUtils.isNotEmpty(result)&&(result+".zz91.com").equals(str)){
		    		if(isExit(result)){
		    			//来访时间
		       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
		       			//来访用户
		       			if(StringUtils.isNotEmpty(job.getString("account"))){
		       				map.put("companyId", getCompanyId(job.getString("account")));
		       				map.put("contact", getContact(map.get("companyId")));
		       				map.put("business", getCompany(map.get("companyId")).get("business"));
		       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
		       			}
		       			if(StringUtils.isEmpty(map.get("companyId"))){
		       				map.put("companyId", "0");
		       				map.put("contact", "游客");
		       				map.put("business", "--");
		       				map.put("companyName", "--");
		       			}
		       			//来访用户所在的地区
		       			if(!maps.keySet().contains(ip)){
		       				maps.put(ip, getAddress(ip));
		       			}
		       			//链接
		       			map.put("url", str);
		    		    //标题
		    			String targetId=getTarget(result);
		    			Map<String,String> company=getCompany(targetId);
		    			if(StringUtils.isNotEmpty(company.get("saleDetails"))){
		    				map.put("title", company.get("saleDetails"));
		    			}else if(StringUtils.isNotEmpty(company.get("buyDetails"))){
		    				map.put("title", company.get("buyDetails"));
		    			}else{
		    				String contact=getContact(targetId);
		    				if(StringUtils.isNotEmpty(contact)){
		    					map.put("title", company.get("name")+","+contact);
		    				}else{
		    					map.put("title", company.get("name"));
		    				}
		    			}
		    			map.put("address", maps.get(ip));
		    			//被访问公司id
			       		map.put("targetId", targetId);
		       			listM.add(map);
		    		}
		       	}
		    	if(StringUtils.isNotEmpty(result)&&str.contains("zz91.com/news")){
		    		if(isExit(result)){
		    			//来访时间
		       			map.put("gmtTarget", DateUtil.toString(new Date(job.getLong("date")), "yyyy-MM-dd HH:mm:ss"));
		       			//来访用户
		       			if(StringUtils.isNotEmpty(job.getString("account"))){
		       				map.put("companyId", getCompanyId(job.getString("account")));
		       				map.put("contact", getContact(map.get("companyId")));
		       				map.put("business", getCompany(map.get("companyId")).get("business"));
		       				map.put("companyName", getCompany(map.get("companyId")).get("name"));
		       			}
		       			if(StringUtils.isEmpty(map.get("companyId"))){
		       				map.put("companyId", "0");
		       				map.put("contact", "游客");
		       				map.put("business", "--");
		       				map.put("companyName", "--");
		       			}
		       			//来访用户所在的地区
		       			if(!maps.keySet().contains(ip)){
		       				maps.put(ip, getAddress(ip));
		       			}
		       			//链接
		       			map.put("url", str);
		       			//标题
		       			String targetId=getTarget(result);
		       			String id=str.replace(result+".zz91.com/news", "").replace(".htm", "");
		       			map.put("title", getNewsName(id)+getCompany(targetId).get("name"));
		       			map.put("address", maps.get(ip));
		    			//被访问公司id
			       		map.put("targetId", targetId);
		       			listM.add(map);
		    		}
		    		
		    	}
	       	}
	    }
	    for(Map<String,String> li:listM){
	    	if(StringUtils.isEmpty(li.get("targetId"))){
	    		li.put("targetId", "0");
	    	}
	    	insert(li);
	    }
		return true;
	}
	//获取公司动态的标题
	public String getNewsName(String id){
		String sql="select title from esite_news where id='"+id+"'";
		final List<String> list=new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}});
		if(list.size()>0){
			return list.get(0)+"-";
		}else{
		   return "";
		}
	}
	//插入数据库
	public void insert(Map<String,String> map){
		String sql = "insert into analysis_myrc_visitor (company_id,target_id,title,url,address,business,contact,company_name,gmt_target,gmt_created,gmt_modified)"
                + "values" + "('"+map.get("companyId")+"','"+map.get("targetId")+"','"+map.get("title")+"','"+map.get("url")+"','"+map.get("address")+"','"+map.get("business")+"','"+map.get("contact")+"','"+map.get("companyName")+"','"+map.get("gmtTarget")+"',now(),now())";
		DBUtils.insertUpdate(DB, sql);
	}
	//根据公司id获取联系人
	public String getContact(String companyId){
		String sql="select contact from company_account where company_id='"+companyId+"'";
		final List<String> list=new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}});
		if(list.size()>0){
			return list.get(0);
		}else{
			return "";
		}
	}
	//根据域名获取公司id
	public String getTarget(String yuming){
		String sql="select id from company where domain_zz91='"+yuming+"'";
		final List<String> list=new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}});
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	//根据产品id获取compayId
	public String getTargetId(String id){
		String sql="select company_id from products where id='"+id+"'";
		final List<String> list=new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}});
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	//获取产品信息
	public Map<String,String> products(String id){
		final Map<String,String> map=new HashMap<String,String>();
		String sql="select products_type_code,title,company_id from products where id='"+id+"'";
		DBUtils.select(DB, sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					map.put("productsType", rs.getString(1));
					map.put("title", rs.getString(2));
					map.put("company_id", rs.getString(3));
				}
			}});
			return map;
	}
	//获取公司信息
	public Map<String,String> getCompany(String targetId){
		final Map<String,String> map=new HashMap<String,String>();
		String sql="select tags,name,sale_details,buy_details,business from company where id='"+targetId+"'";
		DBUtils.select(DB, sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					map.put("tags", rs.getString(1));
					map.put("name", rs.getString(2));
					map.put("saleDetails", rs.getString(3));
					map.put("buyDetails", rs.getString(4));
					map.put("business", rs.getString(5));
				}
			}});
		return map;
	}
	//根据ip获取地址
	public String getAddress(String ip){
		String sql="select area,ip,ip2 from ip_area where ip<='"+ip+"' and ip2>='"+ip+"' ";
		final List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		DBUtils.select("zzother", sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					Map<String,String> map=new HashMap<String,String>();
					map.put("area", rs.getString(1));
					map.put("ip", rs.getString(2));
					map.put("ip2", rs.getString(3));
					list.add(map);
				}
			}});
		//地址
		String address="";
		if(list.size()>1){
			for(int i=0;i<list.size();i++){
				//存放ip
				String[] in=ip.split("[.]");
				//存放数据库ip
				String[] ip1=list.get(i).get("ip").split("[.]");
				//存放数据库ip2
				String[] ip2=list.get(i).get("ip2").split("[.]");
				//比较每个段的大小
				//标记,0表示有意义，1表示要除去的
				list.get(i).put("flag", "0");
				for(int j=0;j<4;j++){
					if(Integer.valueOf(in[j])<Integer.valueOf(ip1[j]) || Integer.valueOf(in[j])>Integer.valueOf(ip2[j])){
						//只要有满足条件的就停止
						list.get(i).put("flag", "1");
						break;
					}
				}
			}
			//装载地址
			for(Map<String,String> map:list){
				if("0".equals(map.get("flag"))){
					address=map.get("area");
				}
			}
			//如果没有满足条件的取最后一个
			if(StringUtils.isEmpty(address)){
				address=list.get(list.size()-1).get("area");
			}
		}else if(list.size()==1){
			address=list.get(0).get("area");
		}else{
			address="未知地区";
		}
		return address;
	}
	//根据帐号获取公司id
	public String getCompanyId(String account){
		String sql="select company_id from company_account where account='"+account+"'";
		final List<String> list=new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}});
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
    //该域名是否存在
	public boolean isExit(String yuming){
		String sql="select count(0) from company where domain_zz91='"+yuming+"'";
		final List<Integer> list=new ArrayList<Integer>();
		DBUtils.select(DB, sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					list.add(rs.getInt(1));
				}
			}});
		if(list.size()>0&&list.get(0)>0){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) throws ParseException, Exception {
		 DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
	       long start = System.currentTimeMillis();
	       AnalysisMyrcVisitorTask analysisMyrcVisitor=new AnalysisMyrcVisitorTask();
	       analysisMyrcVisitor.exec(DateUtil.getDate("2014-11-01", "yyyy-MM-dd"));
	       long end = System.currentTimeMillis();
	       System.out.println("共耗时：" + (end - start));
	}

}
