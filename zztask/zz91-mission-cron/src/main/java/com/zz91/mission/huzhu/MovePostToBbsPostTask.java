package com.zz91.mission.huzhu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class MovePostToBbsPostTask implements ZZTask {
	public static void main(String[] args) {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		MovePostToBbsPostTask postTask = new MovePostToBbsPostTask();
		try {
			postTask.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		List<Map<String, Object>> list = getBbsPost();
		for (Map<String, Object> map : list) {
				if(isHave(String.valueOf(map.get("title")),Integer.valueOf(String.valueOf(map.get("bbs_post_assist_id"))))==0){
					dealTags(String.valueOf(map.get("tags")));
					insert(map);
				}else{
					continue;
				}
		}
		return true;
	}

	// 搜出所有的废料学院记录
	public List<Map<String, Object>> getBbsPost() {
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select bbs_post_category_id,bbs_post_assist_id,title,content,tags,category from bbs_post_caiji";
		DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("bbs_post_category_id", rs.getInt(1));
					map.put("bbs_post_assist_id", rs.getInt(2));
					map.put("title", rs.getString(3));
					map.put("content", rs.getString(4));
					map.put("tags", rs.getString(5));
					map.put("category", rs.getString(6));
					list.add(map);
				}
			}
		});
		return list;
	}

	// 更新进bbs_post表中
	public void insert(Map<String, Object> map) {
		String sql = "insert into `bbs_post` (`company_id`,`account`,`bbs_user_profiler_id`,`bbs_post_category_id`,`bbs_post_assist_id`,`title`,`content`,`content_query`,`tags`,`category`,`is_del`,`check_status`,`post_time`,`reply_time`,`gmt_created`,`gmt_modified`,`check_time`) "
				+ "values('0','leicf','0',"
				+ map.get("bbs_post_category_id")
				+ ","
				+ map.get("bbs_post_assist_id")
				+ ",'"
				+ map.get("title")
				+ "','"
				+ map.get("content")
				+ "','"
				+ map.get("content")
				+ "','"
				+ map.get("tags")
				+ "','"
				+ map.get("category")
				+ "','0','1',now(),now(),now(),now(),now())";
		// System.out.println(sql);
		DBUtils.insertUpdate("ast", sql);
	}

	// 处理标签
	public void dealTags(String tags) {
		String[] listTag = tags.split(",");
		for (String str : listTag) {
			if (isExist(str)) {
				// 更新文章数
				updateTag(str);
			} else {
				// 插入新标签
				insertTag(str);
			}
		}
	}

	// 判断该标签是否已经存在
	public boolean isExist(String tagName) {
		String sql = "select count(0) from bbs_post_tags where category='3' and tag_name='"
				+ tagName + "'";
		final Integer[] in = new Integer[] { 0 };
		DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					in[0] = rs.getInt(1);
				}
			}
		});
		if (in[0] > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 标签插入标签库
	public void insertTag(String tagName) {
		String sql = "insert into `bbs_post_tags` (`category`,`tag_name`,`notice_count`,`article_count`,`is_del`,`gmt_created`,`gmt_modified`)"
				+ "values('3','" + tagName + "','0','1','0',now(),now())";
		DBUtils.insertUpdate("ast", sql);
	}

	// 更新文章数
	public void updateTag(String tagName) {
		String sql = "update bbs_post_tags set article_count=article_count+1 where category='3' and tag_name='"
				+ tagName + "'";
		DBUtils.insertUpdate("ast", sql);
	}

	// 判断是否有该标题的内容
	public Integer isHave(String title, Integer assistId) {
		String sql = "select count(0) from bbs_post where title='" + title
				+ "' and bbs_post_assist_id=" + assistId + "";
		final Integer[] in = new Integer[] { 0 };
		DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					in[0] = rs.getInt(1);
				}
			}
		});
		return in[0];
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
