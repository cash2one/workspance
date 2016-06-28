package com.zz91.mission.huzhu.caiji.metal;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.domain.huzhu.BbsPostCaiJi;
import com.zz91.mission.huzhu.caiji.HuZhuCaiJiCommonOperate;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;
/**
 * 互助抓取 长江有色金属-废旧金属 -废旧金属  对应bbs_post 中的类别:废料学院-行业知识-废金属
 * 采集地址:http://fjjs.ccmn.cn/fjjs-knowledge/
 * @author root
 *
 */
public class CaijiccmnMetalknowledgeTask implements ZZTask{
	final static String PRICE_OPERTION = "huzhu_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    HuZhuCaiJiCommonOperate operate=new HuZhuCaiJiCommonOperate();
    final String DB="ast";
    final static Map<String, String> QQZSS_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        QQZSS_MAP.put("charset", "gb2312");
        
        //采集地址 正式
        QQZSS_MAP.put("url", "http://fjjs.ccmn.cn/fjjs-knowledge/");
        QQZSS_MAP.put("contentUrl", "");
        QQZSS_MAP. put("listStart", "<div id=\"PriceList\""); // 列表页开头
        QQZSS_MAP.put("listEnd", "<div id=\"pagination2\""); // 列表页结尾
        QQZSS_MAP.put("split", "<tr>");
        
        QQZSS_MAP.put("contentStart", "<div class=\"text\"");
        QQZSS_MAP.put("contentEnd", "<br/>本文Tags:" );
        
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 23;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
        	for(Integer i=1;i<4;i++){
        		String content ="";
        		try {
        			
        			url="http://fjjs.ccmn.cn/fjjs-knowledge/?offset="+20*(i-1);
        			content = operate.httpClientHtml(url, QQZSS_MAP.get("charset"));
        		} catch (IOException e) {
        			content = null;
        		}
        	
        		Integer start = content.indexOf(QQZSS_MAP.get("listStart"));
        		if(start==-1){
        			sendEmail(url,typeId,defaultTime);
        			continue;
        		}
        		Integer end = content.indexOf(QQZSS_MAP.get("listEnd"));
        		if(end==-1){
        			sendEmail(url,typeId,defaultTime);
        			continue;
        		}
        		String result = content.substring(start, end);
        		String[] str = result.split(QQZSS_MAP.get("split"));

        		// 检查日期是否对应今天
        		//String formatDate = DateUtil.toString(baseDate, "yyyy-MM-dd");
        		List<String> list = new ArrayList<String>();
           
        		for (String s : str) {
        			if (s.indexOf(".html") != -1) {
        				list.add(s);
        			}
        		}
           
        		if(list.size()<1){
        			sendEmail(url,typeId,defaultTime);
        			return false;
        		}
        		
        		for(String aStr:list){
        			
        		
        			if(aStr!=null){
        				String[] alink=aStr.split("</td>");
        				if (alink.length>=2){
        					String linkStr="";
        					linkStr = operate.getAlink(alink[1]);
        					if(StringUtils.isEmpty(linkStr)){
        						sendEmail(url,typeId,defaultTime);
        						continue;
        					}else{
            						String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
            						Integer starts = linkStr.indexOf("http");
            						Integer ends = linkStr.indexOf(".html");
            						linkStr = linkStr.substring(starts,(ends+5));
            						String resultContent ="";
            						try {
            							resultContent = operate.httpClientHtml(linkStr, QQZSS_MAP.get("charset"));
            						} catch (IOException e) {
            							resultContent = null;
            						}
            						
            						Integer contentstart = resultContent.indexOf(QQZSS_MAP.get("contentStart"));
            						
            						if (contentstart==-1) {
            							sendEmail(url,typeId,defaultTime);
            							continue;
            						}
            						
            						Integer contentend= resultContent.indexOf(QQZSS_MAP.get("contentEnd"));
				
            						if(contentend==-1)	{
            							sendEmail(url,typeId,defaultTime);
            							continue;
            						}
            						resultContent = resultContent.substring(contentstart, contentend);
            						resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","ul","br","li","table","th","tr","td").addAttributes("td","colspan"));
            					
            						String tags="废金属,基础知识";
            						BbsPostCaiJi bbsPostCaiJi=new BbsPostCaiJi();
            						bbsPostCaiJi.setBbsPostCategoryId(3);
            						bbsPostCaiJi.setBbsPostAssistId(23);
            						bbsPostCaiJi.setCategory("废金属,行业知识,废料学院");
            						bbsPostCaiJi.setContent(resultContent);
            						bbsPostCaiJi.setTitle(resultTitle);
            						bbsPostCaiJi.setTags(tags);
            				
            						boolean flg = operate.insert(bbsPostCaiJi);
            						//执行插入并判断插入是否成功
            						if (!flg){
            							errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
            									+ "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
            							flagFailure++;
            						}
        					}
        				}
        			}
        		}
        		
        	}
           
            
        } while (false);
        if (flagFailure != 0){
        	sendEmail(url,typeId,defaultTime);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'长江有色金属-废旧金属 -废旧金属知识("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "长江有色金属</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }
    public void sendEmail(String url,Integer typeId,String defaultTime) throws ParseException{
    	String errContentUrl =" 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                + "<font color='#FF0000'>长江有色金属-废旧金属 -废旧金属"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                        + "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiQqzsslPlasticTask&start="+DateUtil.getDate(new Date(), "yyyy-MM-dd")+" 00:00:00'>"
                    + "<font color='#FF0000'>抓取</font></a>！";
			LogUtil.getInstance().log(
                "caiji-auto", PRICE_OPERTION, null, "{'title':'废旧金属 -废旧金属("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                        + "长江有色金属</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", errContentUrl);
			MailUtil.getInstance().sendMail(
                "互助废料学院-行业知识-机械自动抓取报错", 
                "zz91.price.caiji.auto@asto.mail", null,
                null, "zz91", "blank",
                map, MailUtil.PRIORITY_DEFAULT);
			
    }
    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        long start=System.currentTimeMillis(); 
        CaijiccmnMetalknowledgeTask js=new CaijiccmnMetalknowledgeTask();
        try {
            js.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end=System.currentTimeMillis(); 
        System.out.println(end-start);
    }
}
