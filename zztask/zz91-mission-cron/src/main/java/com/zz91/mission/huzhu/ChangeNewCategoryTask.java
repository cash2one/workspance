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

public class ChangeNewCategoryTask implements ZZTask {

	public static void main(String[] args) {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		ChangeNewCategoryTask task = new ChangeNewCategoryTask();
		try {
			task.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		List<Map<String,Integer>> list=getBbsPost();
		Map<Integer,String> map=getMap();
		for(Map<String,Integer> li:list){
			if(map.keySet().contains(li.get("bbs_post_category_id"))){
				String date=map.get(li.get("bbs_post_category_id"));
				String[] str=date.split("-");
				update(Integer.valueOf(li.get("id")),Integer.valueOf(str[0]),Integer.valueOf(str[1]),str[2]);
			}
		}
		return true;
	}

	/**
	 * 检索出所有的bbsPost
	 * 
	 * @return
	 */
	public List<Map<String, Integer>> getBbsPost() {
		String sql = "select id,bbs_post_category_id from bbs_post order by gmt_created asc ";
		final List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Map<String, Integer> map = new HashMap<String, Integer>();
					map.put("id", rs.getInt(1));
					map.put("bbs_post_category_id", rs.getInt(2));
					list.add(map);
				}
			}

		});
		return list;
	}

	/**
	 * 新旧类别转换
	 * 
	 * @param bbsPostCategoryId
	 * @return
	 */
	public Map<Integer, String> getMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		// 废料动态
		map.put(1, "2-4-废料动态,社区");
		// 行业知识
		map.put(2, "3-11-行业知识,废料学院");
		// 江湖风云
		map.put(3, "2-7-江湖风云,社区");
		// 骗子曝光台
		map.put(16, "2-6-骗子曝光台,社区");
		// 站务管理
		map.put(4, "2-10-站务管理,社区");
		// 社区公告
		map.put(12, "2-20-社区公告,社区");
		// AQSIQ
		map.put(17, "3-12-AQSIQ,废料学院");
		// 新手指南
		map.put(15, "3-16-新手指南,废料生意经,废料学院");
		//废料问答
		map.put(11, "1-0-废料问答");
		return map;
	}

	public void update(Integer id, Integer categoryId, Integer assistId, String category) {
		String sql = "update bbs_post set bbs_post_category_id=" + categoryId
				+ ",bbs_post_assist_id=" + assistId + ",category='" + category
				+ "' where id=" + id + "";
		DBUtils.insertUpdate("ast", sql);
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
