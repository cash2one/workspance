package com.zz91.mission.caiji;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.domain.subscribe.Price;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaiJiFeijsTask implements ZZTask{

    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        CaiJiCommonOperate operate = new CaiJiCommonOperate();
        final Map<String, String> FEIJS_SCRAP_MAP = new HashMap<String, String>();
        // httpget 使用的编码 不然会有乱码
        FEIJS_SCRAP_MAP.put("charset", "gb2312");
        // 采集地址
        FEIJS_SCRAP_MAP.put("url", "http://www.ccmn.cn/fjjs-fltie-prices/");
        //FEIJS_SCRAP_MAP.put("contentUrl", "http://www.feijs.com/news/");
        FEIJS_SCRAP_MAP.put("listStart", "<a href=\"http://www.ccmn.cn/fjjs-fltie-prices/\" ><font  style=\"font-size: 14px; \">"); // 列表页开头
        FEIJS_SCRAP_MAP.put("listEnd", "<div id=\"pagination2\""); // 列表页结尾
        
        FEIJS_SCRAP_MAP.put("contentStart", "<table cellspacing=\"1\">");
        FEIJS_SCRAP_MAP.put("contentEnd", " <div style=\"margin-bottom:10px;\">");
        
        FEIJS_SCRAP_MAP.put("split", "<tr>");
        FEIJS_SCRAP_MAP.put("splitLink", "\"");
        
        //判断自动抓取是否成功
        int flagFailure = 0 ;
        Integer typeId = 328;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        List<String> resultSet =new ArrayList<String>();
        do {
            String content ="";
            try {
                url = FEIJS_SCRAP_MAP.get("url");
                content = operate.httpClientHtml(url, FEIJS_SCRAP_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
                content = null;
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>废金属资讯网-废铁"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijs_scrap.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "废金属资讯网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废铁自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(FEIJS_SCRAP_MAP.get("listStart"));
            Integer end = content.indexOf(FEIJS_SCRAP_MAP.get("listEnd"));
            String result = content.substring(start, end);
            String[] str = result.split(FEIJS_SCRAP_MAP.get("split"));

            // 检查日期是否对应今天
            String formatDate = DateUtil.toString(baseDate, "yyyy-MM-dd");
            String format = DateUtil.toString(baseDate, "MM月dd日");
            List<String> list = new ArrayList<String>();
            
            for (String s : str) {
                if (s.indexOf(formatDate) != -1) {
                    list.add(s);
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>废金属资讯网-废铁"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijs_scrap.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "废金属资讯网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废铁自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 8) {
                flagFailure = 8 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>废金属资讯网-废铁"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "废金属资讯网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            }
            String typeName = operate.queryTypeNameByTypeId(typeId);
            
            String resultContent ="";
            String threeResultContent = "";
            int tag = 0;
            
            for(String aStr:list){
                String linkStr = operate.getAlink(aStr.split("</td>")[1]);
                if(StringUtils.isEmpty(linkStr)){
                    continue;
                }else{
                    String[] alink = linkStr.split(FEIJS_SCRAP_MAP.get("splitLink"));
                    if(alink.length>0){
                        String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                        if (resultTitle.contains("清远")) {
                            resultTitle = format + "广东清远废铁价格";
                        } else if (resultTitle.contains("佛山")) {
                            resultTitle = format + "广东佛山废铁价格";
                        } else if (resultTitle.contains("湖北")) {
                            resultTitle = format + "湖北废铁价格";
                        } else if (resultTitle.contains("山东")) {
                            resultTitle = format + "山东废铁价格";
                        } else if (resultTitle.contains("广西")) {
                            resultTitle = format + "广西废铁价格";
                        } else if (resultTitle.contains("山西")) {
                            resultTitle = format + "山西废铁价格";
                        } else if (resultTitle.contains("江西")) {
                            resultTitle = format + "江西废铁价格";
                        } else if (resultTitle.contains("河南")) {
                            resultTitle = format + "河南废铁价格";
                        } else if (resultTitle.contains("云南")) {
                            resultTitle = format + "云南废铁价格";
                        } else if (resultTitle.contains("安徽")) {
                            resultTitle = format + "安徽废铁价格";
                        } else if(resultTitle.contains("台州")){
                        	resultTitle = format + "台州废铁价格";
                        }else if(resultTitle.contains("江苏")){
                        	resultTitle = format + "江苏废铁价格";
                        }
                        urlmap.put(resultTitle, url);
                        
                        String newUrl = alink[1]; 
                        resultContent = operate.getContent(FEIJS_SCRAP_MAP, newUrl);
                        if (StringUtils.isEmpty(resultContent)) {
                            continue;
                        }
                        String[] strl=resultContent.split("<tr>");
 
                  //内容分成三段，品种，最低价，最高价
                    String oper="";
        			for(String st:strl){
        				if(st.contains("品名")){
        					continue;
        				}
        				String[] ss = st.split("</td>");
        				if(ss.length<2){
        					continue;
        				}
        				oper = oper + "<tr><td>" + Jsoup.clean(ss[0], Whitelist.none())+ "</td><td>" + Jsoup.clean(ss[5], Whitelist.none()) + "</td><td>" + Jsoup.clean(ss[2], Whitelist.none()) + "</td><td>" + Jsoup.clean(ss[4], Whitelist.none()) + "</td>" + "</tr>";
        				
        			}
        			
        			if(resultTitle.contains("台州") || resultTitle.contains("江苏")){
        				threeResultContent+=oper;
        				tag++;
        				if(tag==2){
        					 resultTitle = format + "江浙沪废铁价格";
        					 resultContent="<table><tr><td>品种</td><td>地区</td><td>最低价</td><td>最高价</td><td>涨跌</td></tr>"+threeResultContent+"</table>";
        				}else{
        					continue;
        				}
        			}else{
        				//组合成表
            			resultContent="<table><tr><td>品种</td><td>地区</td><td>最低价</td><td>最高价</td><td>涨跌</td></tr>"+oper+"</table>";
        			}
        				resultContent = resultContent.replace("-", "</td><td>");
                        Price price = new Price();
                        price.setTitle(resultTitle);
                        price.setTypeId(typeId);
                        price.setTags(typeName);
                        price.setContent(resultContent);
                        price.setGmtOrder(baseDate);
                        price.setGmtCreated(baseDate);
                        Integer flg = operate.doInsert(price,false);
                        resultSet.add(price.getTitle()+"--"+price.getTypeId()+"--"+price.getTags()+"--"+price.getContent()+"--"+price.getGmtOrder()+"--"+price.getGmtCreated()+"--"+flg);
                        //执行插入并判断插入是否成功
                        if (flg==0){
                            errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                    + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                            flagFailure++;
                        }
                    }
                }
            }
        } while (false);
        if (flagFailure != 0){
            String content = "废铁"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijs_scrap.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "废金属资讯网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "废铁自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废铁("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "废金属资讯网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "','idSet':'"+resultSet.toString()+"'}");
            return true;
        }
        
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }
    
    public static void main(String[] args) {
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaiJiFeijsTask csLog=new CaiJiFeijsTask();
        try {
            csLog.exec(DateUtil.getDate("2015-11-17", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("1234");

    }

}
