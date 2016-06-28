package com.zz91.mission.huzhu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.caiji.CaiJiCommonOperate;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.http.HttpUtils;

public class CaijiPostByNewTask implements ZZTask {
	CaiJiCommonOperate operate = new CaiJiCommonOperate();
	 final static Map<String, String> HUZHU = new HashMap<String, String>();
	    static{
	        // httpget 使用的编码 不然会有乱码
	    	HUZHU.put("charset", "gb2312");
	        // 采集地址
	    	HUZHU.put("url", "http://info.21cp.com/Zaisheng/ShowClass.asp?ClassID=208&page=5");
	    	HUZHU.put("listStart", "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">"); // 列表页开头
	    	HUZHU.put("listEnd", "<div class=\"showpage\">"); // 列表页结尾
	    	HUZHU.put("contentStart", " <div class=\"content overflow\">");
	    	HUZHU.put("contentEnd", "<h2>关键字：<strong>");
	    	//文章的url
	    	HUZHU.put("contentUrl", "");
	    	//标签
	    	HUZHU.put("tags", "废塑料,再生料");
	    	//类别
	    	HUZHU.put("category", "废料百科,废料学院");
	    	//辅助类别
	    	HUZHU.put("assistId", "13");
	    	//拆分标志
	    	HUZHU.put("spilt", "</td>");
	        
	    }

	public static void main(String[] args) {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		CaijiPostByNewTask task = new CaijiPostByNewTask();
		try {
			task.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
			System.out.println("123");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String tags = HUZHU.get("tags");
		String category = HUZHU.get("category");
		String resultTitle = "";
		String contentW = HttpUtils.getInstance().httpGet(HUZHU.get("url"), HUZHU.get("charset"));
		contentW = contentW.substring(
				contentW.indexOf(HUZHU.get("listStart")),
				contentW.indexOf(HUZHU.get("listEnd")));
		String[] str = contentW.split(HUZHU.get("spilt"));
		for (String li : str) {
			//li=li.replace("[<a", "").replace("工程塑料</a>]", "");
			if (li.contains("<a")) {
				String url = HUZHU.get("contentUrl");
				String linkStr = operate.getAlink(li);
				String[] alink = linkStr.split("\"");
				url = url + alink[3];
				resultTitle = Jsoup.clean(linkStr, Whitelist.none()).replace("・", "");
				if(hasPost(resultTitle,HUZHU.get("assistId"))){
					continue;
				}
				contentW=HttpUtils.getInstance().httpGet(url, HUZHU.get("charset"));
				Integer start=contentW.indexOf(HUZHU.get("contentStart"));
				if(start==-1){
					start=contentW.indexOf("<div class=content>");
				}
				Integer end=contentW.indexOf(HUZHU.get("contentEnd"));
				if(end==-1){
					end=contentW.indexOf("<div class=\"keyword clearfix\">");
				}
				contentW=contentW.substring(start, end);
				contentW = Jsoup.clean(contentW, Whitelist.none().addTags("p","ul","br","li","table","th","tr","td","img").addAttributes("td","rowspan").addAttributes("img", "width","height","src"));
//				if(contentW.contains("<img")&&!contentW.contains("http://")){
//					contentW=contentW.replaceAll("src=\"", "src=\""+"http://help.zz91.com");
//				}
				contentW=contentW.replaceAll("\'", "\"");
				//判断标签是否存在
				for(String s:tags.split(",")){
					if(isTag(3,s)){
						//存在，更新文章数
						update(s);
					}else{
						//不存在，插入标签
						insert(s);
					}
				}
				//插入新贴
				insertPost(resultTitle,contentW,tags,category,Integer.valueOf(HUZHU.get("assistId")));
			}
		}
		return true;
	}
    public boolean hasPost(String title,String assistId){
    	String sql="select id from bbs_post where title='"+title+"'";
    	final List<String> list=new ArrayList<String>();
    	DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}
    	});
    	if(list.size()>0){
    		return true;
    	}else{
    		return false;
    	}
    }
	/**
	 * 判断该标签是否存在
	 * 
	 * @param categoryId
	 * @param TagName
	 * @return
	 */
	public boolean isTag(Integer categoryId, String TagName) {
		String sql = "select id from bbs_post_tags where category='"+categoryId+"' and tag_name='"
				+ TagName + "'";
		final List<Integer> list=new ArrayList<Integer>();
		DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					list.add(rs.getInt(1));
				}

			}
		});
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 插入标签
	 * 
	 * @param tagName
	 */
	public void insert(String tagName) {
		String sql = "insert into bbs_post_tags (category,tag_name,notice_count,article_count,is_del,gmt_created,gmt_modified)"
				+ "VALUES('3','" + tagName + "','0','1','0',now(),now())";
		DBUtils.insertUpdate("ast", sql);
	}

	/**
	 * 更新标签的文章数
	 * 
	 * @param tagName
	 */
	public void update(String tagName) {
		String sql = "update bbs_post_tags set article_count=article_count+1 where category='3' and tag_name='"
				+ tagName + "'";
		DBUtils.insertUpdate("ast", sql);

	}
    /**
     * 插入新贴
     * @param title
     * @param content
     * @param tags
     * @param category
     */
	public void insertPost(String title, String content, String tags,
			String category,Integer assistId) {
		String sql = "insert into bbs_post (company_id,account,bbs_post_category_id,bbs_user_profiler_id,bbs_post_assist_id,title,content,is_del,check_status,post_time,reply_time,tags,gmt_created,gmt_modified,notice_count,recommend_count,collect_count,category)"
				+ "VALUES('0','leicf','3','0','"+assistId+"','"
				+ title
				+ "','"
				+ content
				+ "','0','0',now(),now(),'"
				+ tags
				+ "',now(),now(),'0','0','0','" + category + "')";
		DBUtils.insertUpdate("ast", sql);
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

}
