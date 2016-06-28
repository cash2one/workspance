package com.zz91.mission.price;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;

/**
 * zz91报价-已发布的废纸市场动态正文内容中无效内容删除
 * 
 */
public class ContentDelInvalidTask implements ZZTask {

	final static String DB = "ast";

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		do {
			String sql = "SELECT  id,content  FROM price  where  type_id = 23   and  content   like  '%Feijiu网官方微信%'   limit 100";
			final Map<String, String> map = new HashMap<String, String>();
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						if (StringUtils.isNotEmpty(rs.getString(2))) {
							map.put(rs.getString(1), rs.getString(2));
						}
					}
				}
			});

			if (map == null || map.size() < 1) {
				break;
			}

			// 更新正文
			for (String id : map.keySet()) {
				String ct = map.get(id);

				Integer start = ct.indexOf("Feijiu网官方微信");
				Integer end = ct.indexOf("最新报价请登录官方微信平台查看。");
				String result = "";

				if (end < start) {
					end = ct.indexOf("获取独家信息。");
					result = ct.substring(start, end + 7);
				} else{
					result = ct.substring(start, end + 16);
				}

				ct = ct.replace(result, "");

				sql = "update price set content = '" + ct + "' where id =" + id;
				try {
					DBUtils.insertUpdate(DB, sql);
				} catch (Exception e) {
					System.out.println(id);
				}
			}
		} while (false);

		return true;
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		ContentDelInvalidTask obj = new ContentDelInvalidTask();
		obj.exec(new Date());
	}

}
