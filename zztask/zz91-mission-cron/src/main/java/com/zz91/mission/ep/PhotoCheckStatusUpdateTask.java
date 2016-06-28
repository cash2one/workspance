package com.zz91.mission.ep;

import java.text.ParseException;
import java.util.Date;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.pool.DBPoolFactory;

/**
 * <br />
 * 任务描述： <br />
 * 1.把之前审核通过的供应信息，图片审核状态都从“未审核”变成“通过”;
 */
public class PhotoCheckStatusUpdateTask implements ZZTask {

	public static String DB = "ep";
	final static String DATE_FORMATE = "yyyy-MM-dd";

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		baseDate = DateUtil.getDate(baseDate, DATE_FORMATE);
		String sql = "update photo,trade_supply  set  photo.check_status = '1'  where  photo.target_id = trade_supply.id  and  trade_supply.check_status = '1' ";
		DBUtils.insertUpdate(DB, sql);
		return true;
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	public static void main(String[] args) {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");

		PhotoCheckStatusUpdateTask task = new PhotoCheckStatusUpdateTask();
		try {
			task.exec(DateUtil.getDate("2014-8-14", "yyyy-MM-dd"));
		} catch (ParseException e) {
		} catch (Exception e) {
		}
	}
}