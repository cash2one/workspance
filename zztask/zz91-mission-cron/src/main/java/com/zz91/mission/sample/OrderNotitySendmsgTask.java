package com.zz91.mission.sample;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.sms.SmsUtil;

/**
 * <br />
 * 任务描述： <br />
 * 1.发送规则：买家提交订单未付款时长达到48小时，系统发送该条信息提醒买家付款。 created on 2014-7-29
 */
public class OrderNotitySendmsgTask implements ZZTask {

	public static String DB = "ast";
	final static String DATE_FORMATE = "yyyy-MM-dd";

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {

		/**
		 * 发送规则：买家提交订单未付款时长达到48小时，系统发送该条信息提醒买家付款。
		 */
		String from = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -2), "yyyy-MM-dd HH:mm:ss");
		String to = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -1), "yyyy-MM-dd HH:mm:ss");
		final Set<Map<String, Object>> expiredOrder2 = new HashSet<Map<String, Object>>();
		String sql2 = "select  id,buyer_id,seller_id,amount,orderid,snap_title,seller_name, gmt_created  from sample_orderbill where (state = '00' or state = '01') and  '" + to + "' > gmt_created and gmt_created < '"+from +"'";
		queryCid2(sql2, expiredOrder2);
		// 更新所有过期订单
		for (Map<String, Object> data : expiredOrder2) {
			BigDecimal changeAmount = (BigDecimal) data.get("amount");// 订单金额
			//Integer seller_id = (Integer) data.get("seller_id");// 卖方公司ID
			Integer buyer_id = (Integer) data.get("buyer_id");// 卖方公司ID
			//Integer id = (Integer) data.get("id");// 订单ID
			String orderid = (String) data.get("orderid");// 订单编号
			
			String snapTitle = (String) data.get("snap_title"); // 订单标题
			snapTitle = org.apache.commons.lang.StringUtils.substring(snapTitle, 0, 8);
			
			String sellerName = (String) data.get("seller_name");// 订单编号
			Date createTime =  (Date) data.get("gmt_created");// 订单编号
			 
			String mobile = getMobile(buyer_id);
			
			//您在ZZ91再生网的样品订单（{0}，订单号：{1}）{2}（实付款：{3}元）。
			//您在ZZ91再生网的样品订单（PP再生颗粒，订单号：14072314124676）等待付款（实付款：10.00元）。
			if(StringUtils.isNotEmpty(mobile)){
				SmsUtil.getInstance().sendSms("sms_sample_order", mobile, null, null,	new String[] { snapTitle, orderid, "等待付款", changeAmount.toString()});
			}
			
			//邮件提醒
			String email = getEmail(buyer_id);
			if(StringUtils.isNotEmpty(email)){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("sellerName", sellerName);
				paramMap.put("snapTitle", snapTitle);
				paramMap.put("orderid", orderid);
				paramMap.put("createTime", createTime);
				paramMap.put("changeAmount", changeAmount);
				paramMap.put("stateName", "等待买家付款");
				MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒", email, null, new Date(), "zz91","sample_order_nopay", paramMap, MailUtil.PRIORITY_HEIGHT);
			}
		}

		return true;
	}
	
	
	/* 根据公司id搜索该公司的手机号码mobile */
	public String getMobile(int companyId) {
		String sql = "select mobile from company_account where company_id="+ companyId ;
		final List<String> list = new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		});
		String mobile = null;
		if (list.size() > 0) {
			mobile = list.get(0);
		}
		return mobile;
	}
	
	/* 根据公司id搜索该公司的手机号码email */
	public String getEmail(int companyId) {
		String sql = "select email  from company_account where company_id="+ companyId ;
		final List<String> list = new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		});
		String email = null;
		if (list.size() > 0) {
			email = list.get(0);
		}
		return email;
	}
	

	private void queryCid2(String sql, final Set<Map<String, Object>> set) {
		DBUtils.select(DB, sql, new IReadDataHandler() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					//id,buyer_id,seller_id,amount,orderid,snap_title  
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", rs.getInt(1));
					map.put("buyer_id", rs.getInt(2));
					map.put("seller_id", rs.getInt(3));
					map.put("amount", rs.getBigDecimal(4));
					map.put("orderid", rs.getString(5));
					map.put("snap_title", rs.getString(6));
					map.put("seller_name", rs.getString(7));
					map.put("gmt_created", rs.getTimestamp(8));
					set.add(map);
				}
			}
		});
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	public static void main(String[] args) {
		
		SmsUtil.getInstance().init("web.properties");
		MailUtil.getInstance().init("web.properties");
		
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");

		OrderNotitySendmsgTask task = new OrderNotitySendmsgTask();
		try {
			task.exec(DateUtil.getDate("2014-7-24", "yyyy-MM-dd"));
		} catch (ParseException e) {
		} catch (Exception e) {
		}
	}
}