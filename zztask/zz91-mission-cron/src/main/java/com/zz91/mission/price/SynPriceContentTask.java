package com.zz91.mission.price;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.task.common.ZZTask;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;

/**
 * 更新报价索引正文
 * @author kongsj
 *
 */
public class SynPriceContentTask implements ZZTask {

	final static String DB = "ast";

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		Integer i= 10;
		do {
			String sql = "select id,content from price where content_query is null order by id desc limit 1000";
			final Map<String, String> map = new HashMap<String, String>();
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						if(StringUtils.isNotEmpty(rs.getString(2))){
							map.put(rs.getString(1), rs.getString(2));
						}
					}
				}
			});

			if(map==null||map.size()<1){
				break;
			}

			// 更新资讯正文
			for (String id : map.keySet()) {
				String contentQuery = map.get(id);
				// 上下版 文本转换
				if(contentQuery.indexOf("<div class='p4'>")!=-1){
					contentQuery = contentQuery.substring(0,contentQuery.indexOf("<div class='p4'>"));
				}
				contentQuery = Jsoup.clean(contentQuery, Whitelist.none());
				contentQuery = contentQuery.replace("'", "");
				contentQuery = contentQuery.replace("\'", "");
				contentQuery = contentQuery.replace("\"", "");
				contentQuery = contentQuery.replace("\\","");
				if(StringUtils.isNotEmpty(contentQuery)&&contentQuery.indexOf("相关文章：")!=-1){
					contentQuery = contentQuery.substring(0,contentQuery.indexOf("相关文章："));
				}
				sql = "update price set content_query = '" + contentQuery + "' where id =" + id;
				try {
					DBUtils.insertUpdate(DB, sql);
				} catch (Exception e) {
					System.out.println(id);
				}
			}
			i--;
		} while (i>0);

		return true;
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		SynPriceContentTask obj = new SynPriceContentTask();
		obj.exec(new Date());
	}
	
}
