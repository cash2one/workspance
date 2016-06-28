package com.zz91.mission.caiji;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.domain.subscribe.Price;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiYuYaoSlMarketTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> YUYAOPLASTICS_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        YUYAOPLASTICS_MAP.put("charset", "gbk");
        // 采集地址
        YUYAOPLASTICS_MAP.put("url", "http://info.1688.com/tags_list/v5003220-l15736.html");
        YUYAOPLASTICS_MAP.put("contentUrl", "http://info.1688.com");
        YUYAOPLASTICS_MAP.put("listStart", "<dl class=\"li23 listcontent1\">"); // 列表页开头
        YUYAOPLASTICS_MAP.put("listEnd", "<div class=\"cl\"></div>"); // 列表页结尾
        
        YUYAOPLASTICS_MAP.put("contentStart", "<div class=\"d-content\">");
        YUYAOPLASTICS_MAP.put("contentEnd", "(责任编辑：");
        
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 110;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content ="";
            url = YUYAOPLASTICS_MAP.get("url");
			content = HttpUtils.getInstance().httpGet(url, YUYAOPLASTICS_MAP.get("charset"));
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>阿里巴巴-余姚塑料市场"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb_yuyaoplastics.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'余姚塑料市场("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "余姚塑料市场自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(YUYAOPLASTICS_MAP.get("listStart"));
            Integer end = content.indexOf(YUYAOPLASTICS_MAP.get("listEnd"));
            String result = content.substring(start, end);
            String[] str = result.split("<li>");
            String formatDate=null;
            //判断今天是不是该星期的第一天
            if(DateUtil.toString(DateUtil.getFirstDateThisWeek(), "yyyy-MM-dd").equals(DateUtil.toString(baseDate, "yyyy-MM-dd"))){
            	formatDate = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -3), "yyyy-MM-dd");
            }else{
            	formatDate = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), "yyyy-MM-dd");
            }
            // 检查日期是否对应今天
           // String format = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), "yyyy-MM-dd");
            List<String> list = new ArrayList<String>();
            for (String s : str) {
                if (s.indexOf(formatDate) != -1) {
                    if(s.contains("TPU最新报价") || s.contains("PVC最新报价") || s.contains("ABS最新报价")
                            || s.contains("PC最新报价") || s.contains("PMMA最新报价") || s.contains("EVA最新报价")
                            || s.contains("PP最新报价") || s.contains("HIPS最新报价") || s.contains("PET最新报价")
                            || s.contains("GPPS最新报价") || s.contains("LDPE最新报价") || s.contains("LLDPE最新报价") || s.contains("HDPE最新报价")) {
                        list.add(s);
                    }
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>阿里巴巴-余姚塑料市场"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb_yuyaoplastics.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'余姚塑料市场("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "余姚塑料市场自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 13) {
                flagFailure = 13 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>阿里巴巴-余姚塑料市场"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
            }
            
            String typeName = operate.queryTypeNameByTypeId(typeId);
            
            for(String aStr:list){
                String linkStr = operate.getAlink(aStr);
                if(StringUtils.isEmpty(linkStr)){
                    continue;
                }else{
                    String[] alink = linkStr.split("\"");
                    if(alink.length>0){
                        String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                        if (resultTitle.contains("工程塑料:")) {
                            resultTitle = resultTitle.replace("工程塑料:", "");
                        }
                        String resultContent ="";
                        String newUrl = YUYAOPLASTICS_MAP.get("contentUrl") + alink[1]; 
                        resultContent = operate.getContent(YUYAOPLASTICS_MAP, newUrl);
                        // 获取内容
//                      resultContent = resultContent.toLowerCase();
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("ul","br","li","table","th","tr","td").addAttributes("td","rowspan"));
                        resultContent = resultContent.replace("<td>趋势图</td>", "");
                        
                        Price price = new Price();
                        price.setTitle(resultTitle);
                        price.setTypeId(typeId);
                        price.setTags(typeName);
                        price.setContent(resultContent);
                        if(DateUtil.toString(DateUtil.getFirstDateThisWeek(), "yyyy-MM-dd").equals(DateUtil.toString(baseDate, "yyyy-MM-dd"))){
                        	price.setGmtOrder(DateUtil.getDateAfterDays(baseDate, -3));
                        }else{
                        	price.setGmtOrder(DateUtil.getDateAfterDays(baseDate, -1));
                        }
                        price.setGmtCreated(baseDate);
                        urlmap.put(resultTitle, url);

                        Integer flg = operate.doInsert(price,false);
                       //执行插入并判断插入是否成功
                       /*if (flg != null && flg > 0) {
                           LogUtil.getInstance().log(
                                   "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                           + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                       } else*/ if (flg == null){
                           errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                   + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                           /*LogUtil.getInstance().log(
                                   "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                           + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                           flagFailure++;
                       }
                    }
                }
            }
            
        } while (false);
        if (flagFailure != 0){
            String content = "余姚塑料市场"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb_yuyaoplastics.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'余姚塑料市场("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "余姚塑料市场自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'余姚塑料市场("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiYuYaoSlMarketTask js=new CaijiYuYaoSlMarketTask();
        try {
            js.exec(DateUtil.getDate("2014-10-27", "yyyy-MM-dd"));
            //System.out.println("123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
