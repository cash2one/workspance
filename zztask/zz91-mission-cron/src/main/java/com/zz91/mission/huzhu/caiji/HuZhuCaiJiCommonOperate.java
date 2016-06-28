package com.zz91.mission.huzhu.caiji;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.domain.huzhu.BbsPostCaiJi;
import com.zz91.util.db.DBUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

public class HuZhuCaiJiCommonOperate {
	  public static String DB="ast";
	    public static String file = "file:/usr/tools/config/address/zz91.properties";

	    /**
	     * 插入互助 通用接口
	     * @param priceDO
	     * @return
	     * @throws ParseException
	     * @throws IOException 
	     * @throws URISyntaxException 
	     * @throws ClientProtocolException 
	     */
	    
	   
	    public String getListContent(Map<String, String>map,Integer typeId){
	        String content = "";
	        String url = map.get("url")+ map.get(""+typeId);
			content = HttpUtils.getInstance().httpGet(url,map.get("charset"));
	        if (content == null) {
	            return null;
	        }
	        Integer start = content.indexOf(map.get("listStart"));
	        Integer end = content.indexOf(map.get("listEnd"));
	        String result = subContent(content, start, end);
	        if(StringUtils.isEmpty(result)){
	            return null;
	        }
	        return result;
	    }
	    
	    public String getContent(Map<String, String>map,String url){
	        String content = "";
	        content = HttpUtils.getInstance().httpGet(url,map.get("charset"));
	        if (content == null) {
	            return null;
	        }
	        System.out.println(content);
	        Integer start = content.indexOf(map.get("contentStart"));
	        Integer end = content.indexOf(map.get("contentEnd"));
	        content = subContent(content,start, end);
	        if(StringUtils.isEmpty(content)){
	            return null;
	        }
	        return content;
	    }
	    
	    public String subContent(String content,Integer start,Integer end){
	        if(StringUtils.isEmpty(content)||start>=end){
	            return null;
	        }
	        String result = content.substring(start, end);
	        return result;
	    }
	    
	    public String getAlink(String str){
	        str = str.trim();
	        Integer start = str.indexOf("<a");
	        Integer end = str.indexOf("</a>");
	        str = subContent(str, start, end+4);
	        if(StringUtils.isEmpty(str)){
	            return null;
	        }
	        return str;
	    }
	    
	    public String httpClientHtml(String url , String strCharset) throws URISyntaxException, ClientProtocolException, IOException{
	        DefaultHttpClient httpclient = new DefaultHttpClient();
	        HttpProtocolParams.setUserAgent(httpclient.getParams(),"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
	        String charset = strCharset;
//	      if (null != params && params.length >= 1) {
//	          charset = params[0];
//	      }
	        HttpGet httpget = new HttpGet();
	        String content = "";
	        httpget.setURI(new java.net.URI(url));
	        HttpResponse response = httpclient.execute(httpget);
	        HttpEntity entity = response.getEntity();
	        if (entity != null) {
	            // 使用EntityUtils的toString方法，传递默认编码，在EntityUtils中的默认编码是ISO-8859-1
	            content = EntityUtils.toString(entity, charset);
	            httpget.abort();
	            httpclient.getConnectionManager().shutdown();
	        }
	        return content;
	    }
	    //读取网页中list全部的内容
	    public Map<String,String> httpClientAllContent(Map<String, String>map , String formatDate , String tagsTitle) throws URISyntaxException, ClientProtocolException, IOException{
	        DefaultHttpClient httpclient = new DefaultHttpClient();
	        Map<String , String>  mapContent = new HashMap<String, String>();
	        HttpProtocolParams.setUserAgent(httpclient.getParams(),"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
	        String charset = map.get("charset");
//	      if (null != params && params.length >= 1) {
//	          charset = params[0];
//	      }
	        HttpGet httpget = new HttpGet();
	        String content = "";
	        
	        List<String> list = new ArrayList<String>();
	        
	        httpget.setURI(new java.net.URI(map.get("url")));
	        HttpResponse response = httpclient.execute(httpget);
	        HttpEntity entity = response.getEntity();
	        if (entity != null) {
	            // 使用EntityUtils的toString方法，传递默认编码，在EntityUtils中的默认编码是ISO-8859-1
	            content = EntityUtils.toString(entity, charset);
	            httpget.abort();
	            httpclient.getConnectionManager().shutdown();
	        }
	        Integer start = content.indexOf(map.get("listStart"));
	        Integer end = content.indexOf(map.get("listEnd"));
	        String result = content.substring(start, end);
	        String [] str = result.split(map.get("split"));
	        
	        for (String s:str) {
	            if(s.indexOf(formatDate)!=-1){
	                list.add(s);
	            }
	        }
	        if(list.size()<1){
	            return null;
	        }
	        String resultContent ="";
	        for (String link:list) {
	            String linkStr = getAlink(link);
	            String[] alink = linkStr.split(map.get("splitLink"));
	            if(alink.length>0){
	                String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
	                String contentLink = "";
	                for (String trueLink :alink) {
	                    if (trueLink.contains("</a>")) {
	                        trueLink = trueLink.replace("</a>", "");
	                    }
	                    if (trueLink.contains("/")) {
	                        contentLink = trueLink;
	                    }
	                }
	                String resultLink = urlFormat(map.get("contentLink") + contentLink);
	                
	                try {
	                    resultContent = httpClientHtml(resultLink, map.get("charset"));
	                } catch (IOException e) {
	                }
	                Integer cStart = resultContent.indexOf(map.get("contentStart"));
	                Integer cEnd = resultContent.indexOf(map.get("contentEnd"));
	                resultContent = resultContent.substring(cStart, cEnd);
	                if (resultContent.contains(tagsTitle)) {
	                    mapContent.put(resultTitle, resultContent);
	                    mapContent.put("resultTitle", resultTitle);
	                    return mapContent;
	                }
	            }
	        }
	        return null;
	    }
	    public String getDateFormatTitle(String title){
	        do {
	            if(title.indexOf("日")==-1||title.indexOf("月")==-1){
	                break;
	            }
	            String strSplit = "";
	            for (int k = 1 ; k < title.length() ; k++) {
	                if (title.charAt(k)=='日' && StringUtils.isNumber(String.valueOf(title.charAt(k-1)))) {
	                    strSplit = String.valueOf(title.charAt(k-1))+"日";
	                    break;
	                }
	            }
	            if (StringUtils.isEmail(strSplit)) {
	                break;
	            }
	            String[] strArray = title.split(strSplit);
	            strArray[0] += strSplit.substring(0,1);
	            String[] resultArray = strArray[0].split("月");
	            for (int i=0;i<resultArray.length;i++) {
	                if(resultArray[i].length()==1){
	                    resultArray[i] = "0"+resultArray[i];
	                } else  if (resultArray[0].length() > 6 && !resultArray[0].contains("【")) {   //判断如果日期在后面则直接返回title。开发者：周宗坤 修改日期：2013-09-11
	                	String months = resultArray[i].substring(resultArray[i].length()-2, resultArray[i].length());
	                	String day = resultArray[i+1];
	                	if(resultArray[i+1].length()==1) {
	                		day = "0"+resultArray[i+1];
	                	}
	                	if (!StringUtils.isNumber(months)) {
	                		months = "0" + resultArray[i].substring(resultArray[i].length()-1, resultArray[i].length());
	                		title = resultArray[i].substring(0, resultArray[i].length()-1) + months +"月" + day + "日";
	                		return title;
	                	}
	                	return resultArray[i] +"月" + day + "日";
	                }else{
	                	String dateStr = resultArray[i];
	                	if (!StringUtils.isNumber(dateStr)) {
	                		if (!StringUtils.isNumber(dateStr.substring(dateStr.length()-2, dateStr.length()))) {
								resultArray[i] = dateStr.substring(0, dateStr.length()-1) + "0" + dateStr.substring(dateStr.length()-1,dateStr.length());
							}
						}
	                }
	            }
	            String result = "";
	            if(resultArray.length==2&&strArray.length>=2){
	                result = resultArray[0]+"月"+resultArray[1]+"日"+strArray[1];
	            }
	            if(strArray.length>2){
	                int i=0;
	                do {
	                    if(i>=strArray.length){
	                        break;
	                    }
	                    if(i>1){
	                        result = result + "日"+strArray[i];
	                    }
	                    i++;
	                } while (true);
	            }
	            return result;
	        } while (false);
	        return title;
	    }
	    private String urlFormat (String url) {
	        String newUrl = url;
	        if (newUrl.contains("/./")){
	            newUrl = newUrl.replace("/./", "");
	        }
	        return newUrl;
	    }
	    public String dateFormat(String title) {
	        do {
	            if(title.indexOf("日")==-1 || title.indexOf("月")==-1){
	                break;
	            }
	           int monthIndex = title.indexOf("月");
	           int dayIndex = title.indexOf("日");
	           String month = title.substring(monthIndex-2, monthIndex);
	           String day = title.substring(dayIndex-2,dayIndex);
	           if (!month.matches("-?\\d+\\.?\\d*")) {
	               month = title.substring(monthIndex-1, monthIndex);
	           }
	           if (!day.matches("-?\\d+\\.?\\d*")) {
	               day ="0" + title.substring(dayIndex-1, dayIndex);
	           }
	           String result = month + "月" + day + "日";
	           return result;
	        } while (false);
	        return title;
	    }
	    
//	    private String readPropertiesForUrl (String properties) {
//	        InputStream is =null;
//	        if(properties.startsWith("file:"))
//	            try {
//	                is=new FileInputStream(new File(properties.substring(5)));
//	            } catch (FileNotFoundException e1) {
//	                e1.printStackTrace();
//	            }
//	        else
//	            is=FileUtils.class.getClassLoader().getResourceAsStream(properties);
//	        Properties p = new Properties();
//	        try {
//	            p.load(is);
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	        String host = p.getProperty("price-caiji");
//	        return host;
//	    }
	    
	    //判断字符串中是否含有英文
	    public boolean hasLetter(String str){ 
	              Pattern pattern = Pattern.compile("[a-zA-Z]+"); 
	              Matcher m = pattern.matcher(str);
	              return m.find();     
	     }
	    //将标题中的小写字母转成大写字母
	    public String changeLetter(String str) {
	        return str.toUpperCase();
	    }
	    //截取标题中最后出现的数字或字母,并替换标题内容。注：截取数字因为“生意社”的CaijiSocialBusinessTask任务的标题塑料后带有数字
	    public String splitTitle(String title , String repTitle) {
	        StringBuilder sb = new StringBuilder(title);
	        sb = sb.reverse();
	        Pattern pattern = Pattern.compile("[a-zA-Z0-9]");
	        Matcher matcher = pattern.matcher(sb);
	        if (matcher.find()) {
	          return title.replace(title.substring(title.lastIndexOf(matcher.group())+1), repTitle);
	        }
	        return null;
	    }
	    
	    //插入互助 bbs_post_caij 表
	    public boolean insert(BbsPostCaiJi bpc){
	    	
	    	String sql="insert into bbs_post_caiji (bbs_post_category_id,bbs_post_assist_id,title,content,check_status,tags,gmt_created,category,gmt_modified) values "
	    			+ "("+bpc.getBbsPostCategoryId()+","+bpc.getBbsPostAssistId()+",'"+bpc.getTitle()+"',"
	    					+ "'"+bpc.getContent()+"','1','"+bpc.getTags()+"'"
	    					+",now(),'"+bpc.getCategory()+"',now())";
			 Boolean b=  DBUtils.insertUpdate(DB, sql);
			 return b;
	    }
}
