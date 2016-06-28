package com.zz91.mission.zz91;

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
 * 过期高会的供求批量处理
 * 
 * @author kongsj
 * 
 */
public class ExpiredProductsTask implements ZZTask {

	final static String DB = "ast";
	final static String UNPASS_REASON = "供求信息包含联系方式（如QQ，电话，手机，其他网址等）。信息描述仅允许填写您产品的详细情况及您的合作诚意。";
	final static Integer COMPANY_ID = 354857;

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	/**
	 * 1、 判断标题第一个字是否为中文，如果不是中文，该信息不做处理
	 * 2、 标题第一个字为中文，则去除标题所含的出现较多的文字或符号，具体如下：
	 * 1）多少钱 2）厂家 3）型号 4）价格 5）_ 6）/ 7）物美价廉 8）特点 9）供应商 10）性能 11）工作原理 12）制造商
	 * 13）生产商 14）生产基地 15）供应 16）质优价廉 17）通利-供应 18）河南通利-供应 19）- 20）哪个牌子好 21）-河南通利
	 */

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String sql = "select id,title from products where company_id = "
				+ COMPANY_ID;
		final Map<Integer,String> titleMap = new HashMap<Integer,String>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					titleMap.put(rs.getInt(1),rs.getString(2));
				}
			}
		});
		for (Integer id : titleMap.keySet()) {
			String title = titleMap.get(id);
			// 判断标题第一个字是否为中文，如果不是中文，该信息不做处理
			String ftitle = title.substring(0, 1);
			if(!StringUtils.isContainCNChar(ftitle)){
				continue;
			}
			
			// 特殊字处理
			
			title = title.replace("多少钱", "");
			title = title.replace("厂家", "");
			title = title.replace("型号", "");
			title = title.replace("价格", "");
			title = title.replace("物美价廉", "");
			title = title.replace("特点", "");
			title = title.replace("供应商", "");
			title = title.replace("性能", "");
			title = title.replace("工作原理", "");
			title = title.replace("制造商", "");
			title = title.replace("生产商", "");
			title = title.replace("生产基地", "");
			title = title.replace("供应", "");
			title = title.replace("质优价廉", "");
			title = title.replace("通利-供应", "");
			title = title.replace("河南通利-供应", "");
			title = title.replace("-河南通利", "");
			title = title.replace("哪个牌子好", "");
			title = title.replace("_", "");
			title = title.replace("/", "");
			title = title.replace("-", "");
			
			sql = "update products set title = '"+title+"',gmt_modified = now() where id = " + id;
			DBUtils.insertUpdate(DB, sql);
		}
		return true;
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		ExpiredProductsTask obj = new ExpiredProductsTask();
		obj.exec(new Date());
	}
}
