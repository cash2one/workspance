package com.zz91.mission.zz91;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;

/**
 * 来电宝铁通号码拉清单任务 (每日)
 *
 */

public class SynNewLDBDaylyTask implements ZZTask{
	final static String DB = "ivr";
	final static String DBS = "ast";
	final static String DBSS = "zzother";

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String to = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String from =DateUtil.toString(baseDate, "yyyy-MM-dd");
		// 搜出时间段的所有记录
		List<Map<String,String>> list=getRecord(from,to);
		double jinyan=0;
		for(Map<String,String> map:list){
			Date time=DateUtil.getDate(map.get("startDate"), "yyyy-MM-dd HH:mm:ss");
			map.put("id", map.get("caller")+String.valueOf(time.getTime()));
			//标识接听状态
			if("ANSWERED".equals(map.get("disPosition"))){
				map.put("disPosition", "1");
			}else{
				map.put("disPosition", "0");
			}
			//通话结束时间为空的话，补接听
			if(StringUtils.isEmpty(map.get("endDate"))){
				map.put("endDate", map.get("startDate"));
			}
			//通话开始时间，未接用发起通话的开始时间（startDate），已接用应答时间（answerDate）
			if(StringUtils.isNotEmpty(map.get("answerDate"))){
				map.put("startDate", map.get("answerDate"));
			}
			boolean boo=isHave(map.get("id"));
			if(boo){
				//更新日志
				modified(map.get("id"));
			}else{
				//检索400号码
				String tel=getTel(map.get("exten"));
				map.put("exten",tel);
				//tel不为空
				if(StringUtils.isNotEmpty(tel)){
					//获取公司id
					Integer companyId=getCompanyId(tel);
					if(companyId==null){
						companyId=0;
					}
					//已接电话，通话时间
					Date start = DateUtil.getDate(map.get("startDate"),"yyyy-MM-dd HH:mm:ss");
					Date end = DateUtil.getDate(map.get("endDate"), "yyyy-MM-dd HH:mm:ss");
					double callFee=0;
					//没有按通计费这个服务
					if(!isSvrTong(companyId)){
						//获取每分钟的话费，并算话费
						String fee=getTelFee(companyId);
						//单位分
						long t = end.getTime() - start.getTime();
						Integer timeS = (int) (t / 1000);
						if (timeS % 60 == 0) {
							timeS = timeS / 60;
						} else {
							timeS = timeS / 60 + 1;
						}
						Double f = Double.valueOf(timeS);
						if("1".equals(map.get("disPosition"))){
							if(StringUtils.isNotEmpty(fee)){
							    callFee=Double.valueOf(fee)*f;
							}else{
								callFee=4.9*f;
							}
							if(callFee==0){
								map.put("disPosition", "0");
							}
						}else{
							callFee=0;
						}
					}else{
						//有按通计费方式
						if(hasTongFee(tel,map.get("caller"),end)){
							//今天已经计过费了
							callFee = 0.0;
						}else{
							//今天没有计过费
							callFee = 10.0;
						}
					}
					BigDecimal db = new BigDecimal(callFee);
					db = db.setScale(2, BigDecimal.ROUND_HALF_UP);
					map.put("billable", String.valueOf(db));
					//重新计算经验
	                if(companyId>0){
	                	Map<String,String> jingYan=getJingYan(companyId);
	                	if(jingYan.size()>0){
	                		jinyan=Double.valueOf(jingYan.get("phone_cost"))+callFee;
	                		BigDecimal jy = new BigDecimal(jinyan);
	                		jy = jy.setScale(2, BigDecimal.ROUND_HALF_UP);
	                		if(jinyan>=Math.pow(2,(Integer.valueOf(jingYan.get("level")))+1)*1000){
	                			updateJingYan(companyId,String.valueOf(jy),Integer.valueOf(jingYan.get("level"))+1);
	                		}else{
	                			updateJingYan(companyId,String.valueOf(jy),Integer.valueOf(jingYan.get("level")));
	                		}
	                	}else{
	                		inseryJingyan(companyId,String.valueOf(db));
	                	}
	                }
					// 根据手机号码或电话号码获取省，市
					Map<String, String> mapAddr = new HashMap<String, String>();
					if (!map.get("caller").startsWith("010") && (map.get("caller").startsWith("1") || map.get("caller").startsWith("01"))) {
						mapAddr = getAddressByPhone(map.get("caller"));
					} else {
						mapAddr = getAddressByTel(map.get("caller"));
					}
					map.put("province", mapAddr.get("province"));
					map.put("city", mapAddr.get("city"));
					//对400拨打的手机号码进行处理，以0来头，0要去掉
					if(map.get("caller").length()==12 && map.get("caller").startsWith("01")){
						map.put("caller", map.get("caller").substring(1, 12));
					}
					//插入日志
					insert(map,companyId);
					// 计算余额
					countCount(companyId,Double.valueOf(callFee).floatValue());
				}
			}
		}
		return true;
	}
	//新的扣费方式，按通计费
		//判断需不需要扣钱
		public boolean hasTongFee(String tel, String callerId,Date endTime){
				String end = DateUtil.toString(endTime, "yyyy-MM-dd");
				String sql = "select id from phone_log where caller_id='"+callerId+"' and tel='"+tel+"' and call_fee>0 and end_time>'"+end+"'";
				final List<Integer> list = new ArrayList<Integer>();
				DBUtils.select("ast", sql, new IReadDataHandler(){
					@Override
					public void handleRead(ResultSet rs)throws SQLException {
						while(rs.next()){
							list.add(rs.getInt(1));
						}
					}
				});
				if(list.size()>0){
					return true;
				}
				return false;
		}
		//判断该用户有没有按通计费的服务
		public boolean isSvrTong(Integer companyId){
				String sql = "select id from crm_company_service where company_id='"+companyId+"' and apply_status = '1' and crm_service_code='"+"1011"+"'";
				final List<Integer> list = new ArrayList<Integer>();
				DBUtils.select("ast", sql, new IReadDataHandler(){
					@Override
					public void handleRead(ResultSet rs)throws SQLException {
						while(rs.next()){
							list.add(rs.getInt(1));
						}
					}});
				if(list.size()==0){
					return false;
				}
				return true;
		}
	//插入新的来电宝经验信息
	public void inseryJingyan(Integer companyId,String cost){
		String sql = "insert into ldb_level(company_id,level,phone_cost,gmt_created,gmt_modified)"
				+ "VALUES('"+companyId+"','1','" + cost + "',now(),now())";
		DBUtils.insertUpdate("ast", sql);
		
	}
	//更新来电宝经验信息
	public void updateJingYan(Integer companyId,String cost,Integer level){
		String sql="update ldb_level set phone_cost='"+cost+"',level='"+level+"',gmt_modified=now() where company_id='"+companyId+"'";
		DBUtils.insertUpdate("ast", sql);
	}
	//获取来电宝经验信息
	public Map<String,String> getJingYan(Integer companyId){
		String sql="select phone_cost,level from ldb_level where company_id='"+companyId+"'";
		final Map<String,String> map=new HashMap<String,String>();
		DBUtils.select("ast", sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)throws SQLException {
				while(rs.next()){
					map.put("phone_cost", rs.getString(1));
					map.put("level", rs.getString(2));
				}
			}
			
		});
		return map ;
	}
	//获取每分钟的话费
	public String getTelFee(Integer companyId){
		String sql="select tel_fee from phone_cost_service where company_id="+companyId+" and is_lack = '0' order by id asc limit 1";
		final List<String> list=new ArrayList<String>();
		DBUtils.select(DBS, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	// 搜出时间段的所有记录
	public List<Map<String, String>> getRecord(String from, String to) {
		String sql = "select id,billable,billableMinutes,called,caller,startDate,endDate,disPosition,amaNumber,answerDate from T_CDR where startDate>='"
				+ from + "' and startDate<='" + to + "'";
		final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", rs.getString(1));
					map.put("billable", rs.getString(2));
					map.put("billableMinutes", rs.getString(3));
					map.put("called", rs.getString(4));
					map.put("caller", rs.getString(5));
					map.put("startDate", rs.getString(6));
					map.put("endDate", rs.getString(7));
					map.put("disPosition", rs.getString(8));
					map.put("exten", rs.getString(9));
					map.put("answerDate", rs.getString(10));
					list.add(map);
				}
			}
		});
		for(int i=0;i<list.size();i++){
			if(StringUtils.isEmpty(list.get(i).get("called"))){
				list.remove(i);
			}
		}
		return list;
	}

	// 判断是否存在于phone_log表单中
	public boolean isHave(String key) {
		String sql = "select count(0) from phone_log where call_sn='" + key
				+ "'";
		final Integer[] in = new Integer[] { 0 };
		DBUtils.select(DBS, sql, new IReadDataHandler() {
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

	// 更新
	public void modified(String key) {
		String sql = "update phone_log set gmt_modified = now() where call_sn='"
				+ key + "' ";
		DBUtils.insertUpdate(DBS, sql);
	}

	// 插入
	public void insert(Map<String, String> map, Integer companyId) {
		String sql = "INSERT INTO phone_log (caller_id,company_id,tel,call_fee,start_time,gmt_created,gmt_modified,end_time,call_sn,state,province,city)"
					+ "VALUES ('"
					+ map.get("caller")
					+ "','"
					+ companyId
					+ "','"
					+ map.get("exten")
					+ "','"
					+ map.get("billable")
					+ "','"
					+ map.get("startDate")
					+ "',now(),now(),'"
					+ map.get("endDate")
					+ "','"
					+ map.get("id")
					+ "','"
					+ map.get("disPosition")
					+ "','" + map.get("province") + "','" + map.get("city") + "')";
			DBUtils.insertUpdate(DBS, sql);
		}

    //根据called检索400号码
	public String getTel(String called){
		String sql="select tel from phone_library where called="+called+"";
		final List<String> list=new ArrayList<String>();
		DBUtils.select(DBS, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	//根据400号码检索公司id
	public Integer getCompanyId(String tel){
		String sql="select company_id from phone where expire_flag = 0 and tel='"+tel+"'";
		final List<Integer> list=new ArrayList<Integer>();
		DBUtils.select(DBS, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs)
					throws SQLException {
				while (rs.next()) {
					list.add(rs.getInt(1));
				}
			}
		});
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	// 手机号码获取来电地区
	public Map<String, String> getAddressByPhone(String mobile) {
		if (mobile.startsWith("0")) {
			mobile = mobile.substring(1);
		}
		mobile = mobile.substring(0, 7);
		String sql = "select province,city from mobile_number where numb="
				+ mobile + "";
		final Map<String, String> map = new HashMap<String, String>();
		DBUtils.select(DBSS, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					map.put("province", rs.getString(1));
					map.put("city", rs.getString(2));
				}

			}
		});
		if (map.keySet().size() == 0) {
			map.put("province", "未知");
			map.put("city", "未知");
		}
		return map;
	}

	// 电话号码获取获取来电地区
	public Map<String, String> getAddressByTel(String tel) {
		final Map<String, String> map = new HashMap<String, String>();
		do {
			if (!StringUtils.isNumber(tel)) {
				break;
			}
			if (tel.startsWith("010") || tel.startsWith("02")) {
				tel = tel.substring(0, 3);
			} else {
				if(tel.length()>4){
					tel = tel.substring(0, 4);
				}else{
					break;
				}
			}
			String sql = "select province,city from tel_guoneinumb where number="+tel;
			DBUtils.select(DBSS, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						map.put("province", rs.getString(1));
						map.put("city", rs.getString(2));
						break;
					}
				}
			});
		} while (false);
		if (map.keySet().size() == 0) {
			map.put("province", "未知");
			map.put("city", "未知");
		}
		return map;
	}
	
	private void countCount(Integer companyId,float costFee){
		// 统计消费明细： 公司id ，400电话 ，昨日余额，消费金额，今日余额
		Map<String, Object> resultMap = new HashMap<String, Object>();
		final Map<String, Float> laveMap = new LinkedHashMap<String, Float>();
		String sql = "SELECT id,lave FROM phone_cost_service where is_lack = '0' and company_id = " + companyId + " order by id asc";
		DBUtils.select(DBS, sql, new IReadDataHandler() {
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
			BigDecimal ycBD = new BigDecimal(costFee);
			ycBD = ycBD.setScale(2, BigDecimal.ROUND_HALF_UP);
			resultMap.put("cost", ycBD.toString());
			Float f = laveMap.get(id) - costFee + lave;
			BigDecimal bd = new BigDecimal(f);
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			if (f > 0) {
				// 今日余额
				resultMap.put("toLave", bd.toString());
				sql = "update phone_cost_service set gmt_modified = now() , lave = "
						+ bd.toString() + "where id = " + id;
				DBUtils.insertUpdate(DBS, sql);
				
				break;
			} else if (f == 0) {
				// 今日余额
				resultMap.put("toLave", bd.toString());
				sql = "update phone_cost_service set is_lack = '1' , lave = 0 ,gmt_modified = now() where id = "
						+ id;
				DBUtils.insertUpdate(DBS, sql);
				//判断改条充值记录的相减后余额是否小于等于0。为0这插入一条记录到phone_cs_bs 表中
				String costsql="insert into phone_cs_bs (company_id,target_id,gmt_created,gmt_modified) values ("+companyId+","+id+",now(),now())";
				DBUtils.insertUpdate(DBS, costsql);
				break;
			} else {
				sql = "update phone_cost_service set is_lack = '1' , lave = 0 ,gmt_modified = now() where id = "
						+ id;
				DBUtils.insertUpdate(DBS, sql);
				lave = lave + laveMap.get(id);
				
				//判断改条充值记录的相减后余额是是否小于等于0。为0这插入一条记录到phone_cs_bs 表中
				String costsql="insert into phone_cs_bs (company_id,target_id,gmt_created,gmt_modified) values ("+companyId+","+id+",now(),now())";
				DBUtils.insertUpdate(DBS, costsql);
				continue;
			}
		}
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
	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		SynNewLDBDaylyTask obj = new SynNewLDBDaylyTask();
		obj.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(new Date());
	}

}
