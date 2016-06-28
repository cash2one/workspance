package com.zz91.mission.caiji;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.set.SynchronizedSortedSet;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.domain.subscribe.Price;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiColorJstwoTask implements ZZTask {
	final static String PRICE_OPERTION = "price_caiji";
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11, 16);
		CaiJiCommonOperate oprate = new CaiJiCommonOperate();
		final Map<String, String> feijs_map = new HashMap<String, String>();
		feijs_map.put("charset", "utf-8");
		feijs_map.put("url", "http://www.511535.cn/Home/Search?keyword=%E3%80%90LME%E8%AF%84%E8%AE%BA%E3%80%91");

		feijs_map.put("listStart", "<div class=\"n8_5\">"); // 列表页开头
		feijs_map.put("listEnd", " <div class=\"fy1 ys_fy1\">");// 列表页结尾

		feijs_map.put("twoStart", "13086.html"); // 列表页开头
		feijs_map.put("twoEnd", "\" target=\"_blank\">");// 列表页结尾

		feijs_map.put("contentStart", "<p style=\"text-indent:2em;\">");
		feijs_map.put("contentEnd", "<div class=\"sm_nr\">");

		feijs_map.put("split", "<li>");
		feijs_map.put("splittwo", "<span style=");

		Integer typeId = 216;
		String url = "";
		String errContentUrl = "";
		String content = "";
		do{
		url = feijs_map.get("url");
		content = HttpUtils.getInstance().httpGet(url, feijs_map.get("charset"));
		if (content == null) {
			errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" + url + "'>"
					+ "<font color='#FF0000'>上海有色金属交易中心-国际贵金属" + "(" + typeId + ")" + "</font></a>查看来源网站是否更改地址！<br />"
					+ "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijs_scrap.htm?typeId="
					+ typeId + "'>" + "<font color='#FF0000'>抓取</font></a>！";

			LogUtil.getInstance().log("caiji-auto", PRICE_OPERTION, null,
					"{'title':'国际贵金属(" + typeId + ")','type':'failure','url':'<a href='" + url + "' target='_blank'>"
							+ "上海有色金属交易中心</a>','defaultTime':'" + defaultTime + "','date':'"
							+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", errContentUrl);
			MailUtil.getInstance().sendMail("国际贵金属自动抓取报错", "785395338@qq.com", null, null, "zz91", "blank", map,
					MailUtil.PRIORITY_DEFAULT);
			return false;
		}
		Integer start = content.indexOf(feijs_map.get("listStart"));
		Integer end = content.indexOf(feijs_map.get("listEnd"));
		String result = content.substring(start, end);
		String[] str = result.split(feijs_map.get("split"));

		// 检查日期是否对应今天
		String formatDate = DateUtil.toString(baseDate, "yyyy-M-d");

		String format = DateUtil.toString(baseDate, "MM月dd日");
		List<String> list = new ArrayList<String>();
		for(String string:str){
		if (string.indexOf(format) != -1) {
			list.add(string);
		}
		}

		if (list.size() < 1) {
			errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" + url + "'>"
					+ "<font color='#FF0000'>上海有色金属交易中心-国际贵金属" + "(" + typeId + ")" + "</font></a>查看来源网站<br />"
					+ "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijs_scrap.htm?typeId="
					+ typeId + "'>" + "<font color='#FF0000'>抓取</font></a>！";
			LogUtil.getInstance().log("caiji-auto", PRICE_OPERTION, null,
					"{'title':'国际贵金属(" + typeId + ")','type':'failure','url':'<a href='" + url + "' target='_blank'>"
							+ "上海有色金属交易中心</a>','defaultTime':'" + defaultTime + "','date':'"
							+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", errContentUrl);
			MailUtil.getInstance().sendMail("国际贵金属自动抓取报错", "zz91.price.caiji.auto@asto.mail", null, null, "zz91",
					"blank", map, MailUtil.PRIORITY_DEFAULT);
			return false;
		}
		String cont = list.get(0);
		String urls = cont.substring(cont.indexOf(feijs_map.get("twoStart")), cont.indexOf(feijs_map.get("twoEnd")));
		url = "http://www.511535.cn/rd/" + urls;
		content = HttpUtils.getInstance().httpGet(url, feijs_map.get("charset"));
		Map<String, Object> map = new HashMap<String, Object>();
		if (content == null) {
			errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" + url + "'>"
					+ "<font color='#FF0000'>上海有色金属交易中心-国际贵金属" + "(" + typeId + ")" + "</font></a>查看来源网站是否更改地址！<br />"
					+ "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijs_scrap.htm?typeId="
					+ typeId + "'>" + "<font color='#FF0000'>抓取</font></a>！";

			LogUtil.getInstance().log("caiji-auto", PRICE_OPERTION, null,
					"{'title':'国际贵金属(" + typeId + ")','type':'failure','url':'<a href='" + url + "' target='_blank'>"
							+ "上海有色金属交易中心</a>','defaultTime':'" + defaultTime + "','date':'"
							+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
					map.put("content", errContentUrl);
			MailUtil.getInstance().sendMail("国际贵金属自动抓取报错", "785395338@qq.com", null, null, "zz91", "blank", map,
					MailUtil.PRIORITY_DEFAULT);
			return false;
		}
		Map <String,Object> listtwo = new HashMap<String,Object>();
		start =content.indexOf(feijs_map.get("contentStart"));
		end=content.indexOf(feijs_map.get("contentEnd"));
		cont = content.substring(start, end);
		String [] stri=cont.split(feijs_map.get("splittwo"));
		   String need=stri[1];
		   String title=need.substring(need.indexOf("\"color:#000000;\">")+17, need.indexOf("</span>"));
		   listtwo.put("title", title);
		 url="http://www.gmfutures.com/news/gb/work.asp?bigclassid=15";
		 content=HttpUtils.getInstance().httpGet(url, "GBK");
		 cont=content.substring(content.indexOf(" <td align=\"center\" valign=\"top\""),content.indexOf("align=\"center\" style=\"color:#FFF\">"));
		 String [] srits=cont.split("<table width=");
		 for(String string:srits){
			 if(string.contains("行情回顾及早评")&&string.contains(format)){
				 url= "http://www.gmfutures.com/news/gb/"+string.substring(string.indexOf("<a href=\"")+9, string.indexOf("\" >"));						 
			 }
		 }
		content = HttpUtils.getInstance().httpGet(url, "GBK");
		  cont=content.substring(content.indexOf("<td align=\"center\"><table width="), content.indexOf("align=\"center\" style=\"color:#FFF\">"));
		  String [] sritst=cont.split("<p><strong>");
		  String strts="";
		  
		  for(String string:sritst){
			  if(string.contains("全球金市")){
				String  tring =string.substring(string.indexOf("</span></strong></p><p>"));
				   strts="<p>"+Jsoup.clean(tring, Whitelist.none())+"</p>";			  
			  }
		  }
		  String typeName = oprate.queryTypeNameByTypeId(typeId);
		  String resultContent="<p>"+listtwo.get("title")+"</p>"+strts;
		  Price price = new Price();
          price.setTitle(format+"国际贵金属市场行情");
          price.setTypeId(typeId);
          price.setTags(typeName);
          price.setContent(resultContent);
          price.setGmtOrder(baseDate);

          Integer flg = oprate.doInsert(price,false);
          if (flg == null){
              errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者!";
              map.put("content", errContentUrl);
          }
		}while(false);
		
		 
		return true;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) {
		DBPoolFactory.getInstance().init("file:C:\\properties\\db-zztask-jdbc.properties");
		CaijiColorJstwoTask ts = new CaijiColorJstwoTask();
		Date baseDate = new Date();
		try {
			ts.exec(baseDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
