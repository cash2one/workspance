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

public class AnalysisNoticeAccountTask implements ZZTask {
	public static void main(String[] args) {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		AnalysisNoticeAccountTask task = new AnalysisNoticeAccountTask();
		try {
			task.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		List<Map<String,Integer>> list=getMap();
		for(Map<String,Integer> map:list){
			update(map);
		}
		return true;
	}

	// 被关注过的内容id，及数量
	public List<Map<String, Integer>> getMap() {
		final List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		String sql = "select distinct(content_id),count(content_id) from bbs_post_notice_recommend where type='1' and (category<2 or category='4') group by content_id";
		DBUtils.select("ast", sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Map<String, Integer> map = new HashMap<String, Integer>();
					map.put("id", rs.getInt(1));
					map.put("number", rs.getInt(2));
					list.add(map);
				}
			}
		});
		return list;
	}
    //更新进bbs_post的notice_count
	public void update(Map<String,Integer> map){
		String sql="update bbs_post set notice_count="+map.get("number")+",gmt_modified=now() where id="+map.get("id")+"";
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
