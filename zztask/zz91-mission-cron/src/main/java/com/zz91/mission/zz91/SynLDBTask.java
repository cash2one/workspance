package com.zz91.mission.zz91;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.sms.SmsUtil;

/**
 * 计算每一个用户的余额
 * 
 * @author kongsj
 * 
 */

public class SynLDBTask implements ZZTask {

	final static String DB = "ast";
	final static Map<String, String> COMPANY_AND_TEL = new HashMap<String, String>();
	final static String WEB_PROP="web.properties";

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String to = DateUtil.toString(baseDate, "yyyy-MM-dd");
		String from = DateUtil.toString(
				DateUtil.getDateAfterDays(baseDate, -1), "yyyy-MM-dd");
		// 检索所有来电宝
		String sql = "select tel,call_fee from phone_log where state = '1' and end_time >='"
				+ from + "' and end_time <'" + to + "'";
		final Map<String, Float> telMap = new HashMap<String, Float>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					if (telMap.get(rs.getString(1)) != null) {
						telMap.put(rs.getString(1), telMap.get(rs.getString(1))
								+ rs.getFloat(2));
					} else {
						telMap.put(rs.getString(1), rs.getFloat(2));
					}
				}
			}
		});

		// 根据tel 检索公司company_id
		final Map<String, Float> companyMap = new HashMap<String, Float>();
		for (final String tel : telMap.keySet()) {
			sql = "select company_id from phone where tel = '" + tel + "'";
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						companyMap.put(rs.getString(1), telMap.get(tel));
						COMPANY_AND_TEL.put(rs.getString(1), tel);
					}
				}
			});
		}

		List<Map<String, Object>> mailList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listM = new ArrayList<Map<String, Object>>();
		// 余额map
		for (String companyId : companyMap.keySet()) {

			// 统计消费明细： 公司id ，400电话 ，昨日余额，消费金额，今日余额
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("companyId", companyId);
			resultMap.put("tel", COMPANY_AND_TEL.get(companyId));

			final Map<String, Float> laveMap = new LinkedHashMap<String, Float>();
			sql = "SELECT id,lave FROM phone_cost_service where is_lack = '0' and company_id = "
					+ companyId + " order by id asc";
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						laveMap.put(rs.getString(1), rs.getFloat(2));
					}
				}
			});
			// 计算余额 更新数据
			Float lave = 0f;
			for (String id : laveMap.keySet()) {

				// 昨日余额
				BigDecimal yBD = new BigDecimal(laveMap.get(id));
				yBD = yBD.setScale(2, BigDecimal.ROUND_HALF_UP);
				resultMap.put("yesLave", yBD.toString());
				// 昨日消费
				BigDecimal ycBD = new BigDecimal(companyMap.get(companyId));
				ycBD = ycBD.setScale(2, BigDecimal.ROUND_HALF_UP);
				resultMap.put("cost", ycBD.toString());
				Float f = laveMap.get(id) - companyMap.get(companyId) + lave;
				BigDecimal bd = new BigDecimal(f);
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				if (f > 0) {
					// 今日余额
					resultMap.put("toLave", bd.toString());
					sql = "update phone_cost_service set gmt_modified = now() , lave = "
							+ bd.toString() + "where id = " + id;
					DBUtils.insertUpdate(DB, sql);
					
					break;
				} else if (f == 0) {
					// 今日余额
					resultMap.put("toLave", bd.toString());
					sql = "update phone_cost_service set is_lack = '1' , lave = 0 ,gmt_modified = now() where id = "
							+ id;
					DBUtils.insertUpdate(DB, sql);
					//判断改条充值记录的相减后余额是否小于等于0。为0这插入一条记录到phone_cs_bs 表中
					String costsql="insert into phone_cs_bs (company_id,target_id,gmt_created,gmt_modified) values ("+companyId+","+id+",now(),now())";
					DBUtils.insertUpdate(DB, costsql);
					break;
				} else {
					sql = "update phone_cost_service set is_lack = '1' , lave = 0 ,gmt_modified = now() where id = "
							+ id;
					DBUtils.insertUpdate(DB, sql);
					lave = lave + laveMap.get(id);
					
					//判断改条充值记录的相减后余额是是否小于等于0。为0这插入一条记录到phone_cs_bs 表中
					String costsql="insert into phone_cs_bs (company_id,target_id,gmt_created,gmt_modified) values ("+companyId+","+id+",now(),now())";
					DBUtils.insertUpdate(DB, costsql);
					continue;
				}
			}
			// 装载邮件列表
			mailList.add(resultMap);
			//生成余额日志
			LogUtil.getInstance().log("admin", "log", null,
					resultMap.toString(), "LDB");
			if (mailList.size() > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("list", mailList);
//				MailUtil.getInstance().sendMail("来电宝日消费清单",
//						"zz91.ldb.log@asto.mail", "zz91", "ldb-cost-log", map, 0);
			}
			Map<String,Object> mapC=new HashMap<String,Object>();
			//昨天消费了的费用
			float cost=getCallFee(companyId,from,to)+getClickFee(companyId,from,to)+getCallClick(companyId,from,to);
			//余额
			float bal=Float.valueOf(String.valueOf(getTotal(companyId)))-getCallFee(companyId)-getClickFee(companyId)-getCallClick(companyId);
			//手机号码
			String moblie=getMobile(companyId);
			String dates = DateUtil.toString(baseDate, "yyyy-MM-dd 9:00:00");
			//余额不足100元提醒
			String time=DateUtil.toString(baseDate, "MM-dd").replace("-", "月")+"日";
			if(bal>=50&&bal<100&&bal+cost>=100){
				if(StringUtils.isNotEmpty(moblie)&&moblie.length()==11){
					SmsUtil.getInstance().sendSms(
							"ldb_balance_notice",
							moblie,
							null,
							DateUtil.getDate(dates, "yyyy-MM-dd HH:mm:ss"),
							new String[] {time,"100"});
					mapC.put("companyId", companyId);
					mapC.put("tel", COMPANY_AND_TEL.get(companyId));
					mapC.put("money", 100);
				}
			}
			if(bal>=100&&bal<200&&bal+cost>=200){
				if(StringUtils.isNotEmpty(moblie)&&moblie.length()==11){
					SmsUtil.getInstance().sendSms(
							"ldb_balance_notice",
							moblie,
							null,
							DateUtil.getDate(dates, "yyyy-MM-dd HH:mm:ss"),
							new String[] {time,"200"});
					mapC.put("companyId", companyId);
					mapC.put("tel", COMPANY_AND_TEL.get(companyId));
					mapC.put("money", 200);
				}
			}
			if(bal<50&&bal+cost>=50){
				if(StringUtils.isNotEmpty(moblie)&&moblie.length()==11){
					SmsUtil.getInstance().sendSms(
							"ldb_balance_notice",
							moblie,
							null,
							DateUtil.getDate(dates, "yyyy-MM-dd HH:mm:ss"),
							new String[] {time,"50"});
					mapC.put("companyId", companyId);
					mapC.put("tel", COMPANY_AND_TEL.get(companyId));
					mapC.put("money", 50);
				}
			}
			//月消费短信提醒
			Calendar cal=Calendar.getInstance();  
			cal.setTime(baseDate); 
			if(cal.get(Calendar.DATE)==2){
				String begin=DateUtil.toString(DateUtil.getDateAfterMonths(baseDate, -1),"yyyy-MM-01");
				String end=DateUtil.toString(baseDate,"yyyy-MM-01");
				//本月通话费用
				float callf=getCallFee(companyId,begin,end);
				//上个月月租
				String lastMonthFee=getMonthFee(baseDate,COMPANY_AND_TEL.get(companyId),0);
				if(StringUtils.isNotEmpty(lastMonthFee)){
					callf=callf-Float.valueOf(lastMonthFee);
				}
				BigDecimal bd = new BigDecimal(callf);
				bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
				String callfee=String.valueOf(bd)+"0"; 
				//本月点击查看费用
				float clickf=getClickFee(companyId,begin,end);
				BigDecimal bds = new BigDecimal(clickf);
				bds = bds.setScale(1, BigDecimal.ROUND_HALF_UP);
				String clickfee=String.valueOf(bds)+"0";
				//本月未接查看费用
				float callC=getCallClick(companyId,begin,end);
				BigDecimal bdb = new BigDecimal(callC);
				bdb = bdb.setScale(1, BigDecimal.ROUND_HALF_UP);
				String callClick=String.valueOf(bdb)+"0";
				//余额
				BigDecimal bdbd = new BigDecimal(bal);
				bdbd = bdbd.setScale(1, BigDecimal.ROUND_HALF_UP);
				String balance=String.valueOf(bdbd);
				//本月总消费
				float sumA=callf+clickf+callC;
				//本月月租
				String monthfee=getMonthFee(baseDate,COMPANY_AND_TEL.get(companyId),1);
				if(StringUtils.isNotEmpty(monthfee)){
					sumA=sumA+Float.valueOf(monthfee);
				}else{
					monthfee="0";
				}
				BigDecimal bs = new BigDecimal(sumA);
				bs = bs.setScale(1, BigDecimal.ROUND_HALF_UP);
				String sunAll=String.valueOf(bs);
				String times=DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -2), "MM-dd").replace("-", "月")+"日";
				if(StringUtils.isNotEmpty(moblie)&&moblie.length()==11){
					SmsUtil.getInstance().sendSms(
							"ldb_month_fee",
							moblie,
							null,
							DateUtil.getDate(dates, "yyyy-MM-dd HH:mm:ss"),
							new String[] {times,sunAll,callfee,clickfee,callClick,monthfee,balance});
				}
			}
			if(mapC.keySet().size()>0){
				listM.add(mapC);
			}
		}
		if (listM.size() > 0) {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("list", listM);
			//MailUtil.getInstance().sendMail(null, null, "来电宝余额提醒清单", "shiqiaopinglove@163.com", null,new Date(), "zz91", "ldb_balance_notice", map, MailUtil.PRIORITY_HEIGHT);
			MailUtil.getInstance().sendMail("来电宝余额提醒清单",
					"shiqiaopinglove@163.com", "zz91", "ldb_balance_notice", map, 0);
		}
		return true;
	}
	//该用户的总费用
	public Integer getTotal(String companyId){
		String sql="select amount from phone where company_id='"+companyId+"'";
		final List<Integer> list=new ArrayList<Integer>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getInt(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}
		return 0;
	}
	//该用户通话费用
	public Float getCallFee(String companyId){
		String sql="select sum(call_fee) from phone_log where company_id ='"+companyId+"' and state = '1'";
		final List<Float> list=new ArrayList<Float>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getFloat(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}
		return 0F;
	}
	
	//该用户点击查看费用
	public Float getClickFee(String companyId){
		String sql="select sum(click_fee) from phone_click_log where company_id ='"+companyId+"'";
		final List<Float> list=new ArrayList<Float>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getFloat(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}
		return 0F;
	}
	//该用户未接查看费用
	public Float getCallClick(String companyId){
		String sql="select sum(click_fee) from phone_call_click_fee where company_id ='"+companyId+"'";
		final List<Float> list=new ArrayList<Float>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getFloat(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}
		return 0F;
	}
	//当月该用户通话费用
	public Float getCallFee(String companyId,String from,String to){
		String sql="select sum(call_fee) from phone_log where company_id ='"+companyId+"' and state = '1' and start_time>='"+from+"' and start_time<'"+to+"'";
		final List<Float> list=new ArrayList<Float>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getFloat(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}
		return 0F;
	}
	//当月该用户点击查看费用
	public Float getClickFee(String companyId,String from,String to){
		String sql="select sum(click_fee) from phone_click_log where company_id ='"+companyId+"' and gmt_created>='"+from+"' and gmt_created<'"+to+"'";
		final List<Float> list=new ArrayList<Float>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getFloat(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}
		return 0F;
	}
	//当月该用户未接查看费用
	public Float getCallClick(String companyId,String from,String to){
		String sql="select sum(click_fee) from phone_call_click_fee where company_id ='"+companyId+"' and gmt_created>='"+from+"' and gmt_created<'"+to+"'";
		final List<Float> list=new ArrayList<Float>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getFloat(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}
		return 0F;
	}
   //根据公司id获取绑定的手机号码
	public String getMobile(String companyId){
		String sql="select mobile from phone where company_id='"+companyId+"'";
		final List<String> list=new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	//获取月费
	public String getMonthFee(Date date,String tel,Integer keys){
		Calendar cal=Calendar.getInstance();  
		cal.setTime(date); 
		String key=cal.get(Calendar.MONTH)+"月租费";
		if(keys==0){
			key=cal.get(Calendar.MONTH)-1+"月租费";
		}
		String sql="select call_fee from phone_log where  caller_id ='"+key+"' and tel = '"+tel+"'";
		final List<String> list=new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public boolean init() throws Exception {
		return false;
	}

	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		MailUtil.getInstance().init("web.properties");
		SynLDBTask obj = new SynLDBTask();
		SmsUtil.getInstance().init(WEB_PROP);
		MailUtil.getInstance().init(WEB_PROP);
		obj.exec(DateUtil.getDate("2014-12-05", "yyyy-MM-dd"));
	}

}
