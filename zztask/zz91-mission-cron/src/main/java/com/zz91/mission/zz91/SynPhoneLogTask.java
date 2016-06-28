package com.zz91.mission.zz91;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.sms.SmsUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 来电宝 用户通话清单拉取任务 author:kongsj date:2013-7-15
 */
public class SynPhoneLogTask implements ZZTask {

	// final static String ADMIN_URL =
	// "http://192.168.110.130:8017/web/zz91/phone/getBill.htm";

	final static String DB = "ast";
	// final static String DB_TEST = "astTest";
	// 接口私钥：
	final static String svrCode = "1009";
	// 未接来电短信提醒服务代码
	private static String apiKey = "a16d751a0e879ca533aeba5f0e985c5e";
	// 用户ID：userkey
	private static String userKey = "11683e186d";
	// 组装string 的map
	private static Map<String, String> map = new TreeMap<String, String>();

	static {
		map.put("userkey", userKey);
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		// Date date = new Date();
		String from = DateUtil.toString(baseDate, "yyyy-MM-01");
		String to = DateUtil.toString(DateUtil.getDateAfterDays(
				DateUtil.getDate(DateUtil.getDateAfterMonths(baseDate, 1),
						"yyyy-MM-01"), -1), "yyyy-MM-dd");
		String sign = getSign(DateUtil.getDate(from, "yyyy-MM-dd"),
				DateUtil.getDate(to, "yyyy-MM-dd"), null, null);
		String str = "&start_time=" + from + " 00:00:00&end_time=" + to
				+ " 23:59:59&sign=" + sign;
		String url = "http://api.maxvox.com.cn/api/pci_pull/getCallByDateTime?userkey="
				+ userKey + str.replaceAll(" ", "%20");
		String result = HttpUtils.getInstance().httpGet(url,
				HttpUtils.CHARSET_UTF8);

		//
		// Date date1 = new Date();

		// NameValuePair[] data = new NameValuePair[]{
		// new NameValuePair("start_time",from+"%2000:00:00"),
		// new NameValuePair("end_time",to+"%2000:00:00"),
		// new NameValuePair("userkey",userKey),
		// new NameValuePair("sign",sign)
		// };
		// String result =
		// HttpUtils.getInstance().httpPost("http://api.maxvox.com.cn/api/pci_pull/getCallByDateTime",
		// data, HttpUtils.CHARSET_UTF8);

		JSONObject js = JSONObject.fromObject(result);
		JSONArray jsa = JSONArray.fromObject(js.get("result"));
		// Map<String, Map<String, String>> mapS = new HashMap<String,
		// Map<String, String>>();
		Map<String, String> insertOrUpdateMap = new HashMap<String, String>();
		Map<String, JSONObject> mapI = new HashMap<String, JSONObject>();
		for (int i = 0; i < jsa.size(); i++) {
			JSONObject obj = JSONObject.fromObject(jsa.get(i));
			if (obj != null) {
				do {

					// 先判断是否存在
					String sql = "select id from phone_log where call_sn = '"
							+ obj.getString("call_sn") + "' limit 1";
					final String[] id = new String[] { "" };
					DBUtils.select(DB, sql, new IReadDataHandler() {

						@Override
						public void handleRead(ResultSet rs)
								throws SQLException {
							while (rs.next()) {
								id[0] = rs.getString(1);
							}
						}
					});

					// tel 号码
					String tel = obj.getString("tel");
					String ext = obj.getString("ext");
					if (StringUtils.isNotEmpty(ext)) {
						tel = tel + "-" + ext;
					}

					// 订单已经存在 不再重新提交 做更新操作
					if (StringUtils.isNotEmpty(id[0])) {
						sql = "UPDATE phone_log SET " + "caller_id = '"
								+ obj.getString("call_passid") + "'," + "start_time='"
								+ obj.getString("answer_time") + "',"
								+ "end_time='" + obj.getString("end_time")
								+ "'" + "WHERE id = " + id[0];
						DBUtils.insertUpdate(DB, sql);
						break;
					}
                    //获取公司id
					String compan=getCompanyId(tel);
					Integer companyI=0;
					if(compan!=""){
						companyI=Integer.valueOf(compan);
					}
					// 获得mobile
					String mobile = getMobile(getCompanyId(obj.getString("tel")));
					// 构造call_sn
					String callSn = DateUtil.toString(DateUtil.getDate(
							obj.getString("end_time"), "yyyy-MM-dd"),
							"yyyy-MM-dd")
							+ "_" + obj.getString("call_passid");
					if ("0".equals(obj.get("state"))) {
						boolean mpmt = missedPhoneMessageService(getCompanyId(obj
								.getString("tel")));
						if (mpmt) {
							obj.accumulate("mobile", mobile);
							sendSMS(obj);
							obj.accumulate("callSn", callSn);
							mapI.put(callSn + "_" + obj.getString("tel"), obj);
							// mapS.put(obj.getString("call_sn"), maps);
						}
					}
					//有按通计费的服务
					if(isSvrTong(companyI)){
						if(hasTongFee(tel,obj.getString("call_passid"),DateUtil.getDate(obj.getString("end_time"), "yyyy-MM-dd"))){
							//今天已经扣过费
							obj.put("call_fee", 0.0);
						}else{
							//今天没有扣过费
							obj.put("call_fee", 10.0);
							Map<String,String> jingYan=getJingYan(companyI);
							if(Float.valueOf(obj.getString("call_fee"))!=0.0&&companyI>0){
								if(jingYan.size()!=0){
									double jinyan=Double.valueOf(jingYan.get("phone_cost"))+Float.valueOf(obj.getString("call_fee"));
									BigDecimal jy = new BigDecimal(jinyan);
									jy = jy.setScale(2, BigDecimal.ROUND_HALF_UP);
									if(jinyan>=Math.pow(2,(Integer.valueOf(jingYan.get("level"))+1))*1000){
										updateJingYan(companyI,String.valueOf(jy),Integer.valueOf(jingYan.get("level"))+1);
									}else{
										updateJingYan(companyI,String.valueOf(jy),Integer.valueOf(jingYan.get("level")));
									}
								}else{
									inseryJingyan(companyI,String.valueOf(Float.valueOf(obj.getString("call_fee"))));
								}
							}
						}
						sql = "INSERT INTO phone_log (caller_id,company_id,province,city,tel,call_fee,start_time,gmt_created,gmt_modified,end_time,call_sn,state)"
								+ "VALUES ('"
								+ obj.getString("call_passid")
								+ "','"
								+ companyI
								+ "','"
								+ obj.getString("province")
								+ "','"
								+ obj.getString("city")
								+ "','"
								+ tel
								+ "','"
								+ Float.valueOf(obj.getString("call_fee"))
								+ "','"
								+ obj.getString("answer_time")
								+ "',now(),now(),'"
								+ obj.getString("end_time")
								+ "','" + obj.getString("call_sn") + "','0')";
						DBUtils.insertUpdate(DB, sql);
						break;
					}
					// 如果是未接，按照成本扣费记录保存
					if ("0".equals(obj.get("state"))) {
						// 插入日志
						sql = "INSERT INTO phone_log (caller_id,company_id,province,city,tel,call_fee,start_time,gmt_created,gmt_modified,end_time,call_sn,state)"
								+ "VALUES ('"
								+ obj.getString("call_passid")
								+ "','"
								+ companyI
								+ "','"
								+ obj.getString("province")
								+ "','"
								+ obj.getString("city")
								+ "','"
								+ tel
								+ "','"
								+ Float.valueOf(obj.getString("call_fee"))
								+ "','"
								+ obj.getString("answer_time")
								+ "',now(),now(),'"
								+ obj.getString("end_time")
								+ "','" + obj.getString("call_sn") + "','0')";
						// DBUtils.insertUpdate(DB, sql);
						insertOrUpdateMap.put(obj.getString("call_sn"), sql);
						Map<String,String> jingYan=getJingYan(companyI);
						if(Float.valueOf(obj.getString("call_fee"))!=0.0&&companyI>0){
							if(jingYan.size()!=0){
								double jinyan=Double.valueOf(jingYan.get("phone_cost"))+Float.valueOf(obj.getString("call_fee"));
								BigDecimal jy = new BigDecimal(jinyan);
								jy = jy.setScale(2, BigDecimal.ROUND_HALF_UP);
								if(jinyan>=Math.pow(2,(Integer.valueOf(jingYan.get("level"))+1))*1000){
									updateJingYan(companyI,String.valueOf(jy),Integer.valueOf(jingYan.get("level"))+1);
								}else{
									updateJingYan(companyI,String.valueOf(jy),Integer.valueOf(jingYan.get("level")));
								}
							}else{
								inseryJingyan(companyI,String.valueOf(Float.valueOf(obj.getString("call_fee"))));
							}
						}
						break;
					}

					// 检索company_id
					sql = "select company_id FROM phone where tel = '" + tel
							+ "' limit 1";
					final String[] companyId = new String[] { "" };
					DBUtils.select(DB, sql, new IReadDataHandler() {

						@Override
						public void handleRead(ResultSet rs)
								throws SQLException {
							while (rs.next()) {
								companyId[0] = rs.getString(1);
							}
						}
					});

					// 公司id不存在
					if (StringUtils.isEmpty(companyId[0])) {
						break;
					}

					final Float[] telFee = new Float[] { 0f };
					final Float[] lave = new Float[] { 0f };
					sql = "SELECT tel_fee,lave FROM phone_cost_service where is_lack = '0' and company_id = "
							+ companyId[0] + " order by id asc limit 1";
					DBUtils.select(DB, sql, new IReadDataHandler() {
						@Override
						public void handleRead(ResultSet rs)
								throws SQLException {
							while (rs.next()) {
								telFee[0] = rs.getFloat(1);
								lave[0] = rs.getFloat(2);
							}
						}
					});

					String startStr = obj.getString("answer_time");
					String endStr = obj.getString("end_time");
					Date start = DateUtil.getDate(startStr,
							"yyyy-MM-dd HH:mm:ss");
					Date end = DateUtil.getDate(endStr, "yyyy-MM-dd HH:mm:ss");
					long t = end.getTime() - start.getTime();
					Integer timeS = (int) (t / 1000);
					if (timeS % 60 == 0) {
						timeS = timeS / 60;
					} else {
						timeS = timeS / 60 + 1;
					}
					Double f = Double.valueOf(timeS);

					// Double f = obj.getDouble("call_fee") / 0.12;
					Double fdd = 0d;
					if(telFee[0]>0f){
						fdd = f * telFee[0];
					}else{
						fdd = f * 4.9;
					}
					BigDecimal bd = new BigDecimal(fdd);
					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

					// 余额不足 临界值 使用第二个扣款 规则
					if (fdd > lave[0] && lave[0]>0f) {
						// 取最后一个服务
						sql = "SELECT tel_fee,lave FROM phone_cost_service where is_lack = '0' and company_id = "
								+ companyId[0] + " order by id asc limit 2";
						DBUtils.select(DB, sql, new IReadDataHandler() {
							@Override
							public void handleRead(ResultSet rs)
									throws SQLException {
								while (rs.next()) {
									telFee[0] = rs.getFloat(1);
									lave[0] = rs.getFloat(2);
								}
							}
						});
						f = obj.getDouble("call_fee") / 0.12;
						fdd = f * telFee[0];
						bd = new BigDecimal(fdd);
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					}

					// phoneLog.setCallFee(bd.toString());
					// phoneLog.setCallSn(obj.getString("call_sn"));
					// phoneLog.setEndTime(DateUtil.getDate(obj.getString("end_time"),
					// "yyyy-MM-dd HH:mm:ss"));
					// phoneLog.setCallerId(obj.getString("call_passid"));
					// phoneLog.setStartTime(DateUtil.getDate(obj.getString("start_time"),
					// "yyyy-MM-dd HH:mm:ss"));
					// phoneLog.setTel(obj.getString("tel"));

					// 插入日志
					sql = "INSERT INTO phone_log (caller_id,company_id,province,city,tel,call_fee,start_time,gmt_created,gmt_modified,end_time,call_sn,state)"
							+ "VALUES ('"
							+ obj.getString("call_passid")
							+ "','"
							+ companyI
							+ "','"
							+ obj.getString("province")
							+ "','"
							+ obj.getString("city")
							+ "','"
							+ tel
							+ "','"
							+ bd.floatValue()
							+ "','"
							+ obj.getString("answer_time")
							+ "',now(),now(),'"
							+ obj.getString("end_time")
							+ "','"
							+ obj.getString("call_sn") + "','1')";
					// DBUtils.insertUpdate(DB, sql);

					insertOrUpdateMap.put(obj.getString("call_sn"), sql);
					Map<String,String> jingYan=getJingYan(companyI);
                	if(jingYan.size()>0&&companyI>0){
                		double jinyan=Double.valueOf(jingYan.get("phone_cost"))+bd.floatValue();
                		BigDecimal jy = new BigDecimal(jinyan);
                		jy = jy.setScale(2, BigDecimal.ROUND_HALF_UP);
                		if(jinyan>=Math.pow(2,(Integer.valueOf(jingYan.get("level"))+1)*1000)){
                			updateJingYan(companyI,String.valueOf(jy),Integer.valueOf(jingYan.get("level"))+1);
                		}else{
                			updateJingYan(companyI,String.valueOf(jy),Integer.valueOf(jingYan.get("level")));
                		}
                	}else if(companyI>0){
                		inseryJingyan(companyI,String.valueOf(bd.floatValue()));
                	}
				} while (false);
			}
		}
		// 短信消费记录批量insert
		for (String key : mapI.keySet()) {
			// 获取短信费用
			Float fee = Float.valueOf(getFee(mapI.get(key).getString("tel")));
			insertLog(mapI.get(key).getString("province"), mapI.get(key).getString("city"), mapI.get(key).getString("tel"),fee,
					mapI.get(key).getString("callSn"),
					mapI.get(key).getString("start_time"), mapI.get(key)
							.getString("end_time"));
			// System.out.println(mapI.get(key));
		}

		// 订单批量insert
		for (String key : insertOrUpdateMap.keySet()) {
			DBUtils.insertUpdate(DB, insertOrUpdateMap.get(key));
		}
		// 去掉重复的数据
		// int p = 0;
		if (record().size() > 0) {
			for (String key : record().keySet()) {
				List<Integer> list = getId(record().get(key).get("call_sn"));
				for (int i = 1; i < list.size(); i++) {
					delete(list.get(i));
					// p++;
				}
			}
		}
		return true;
	}
	//新的扣费方式，按通计费
		//判断需不需要扣钱
		public boolean hasTongFee(String tel, String callerId,Date endTime){
				String end = DateUtil.toString(endTime, "yyyy-MM-dd");
				String sql = "select id from phone_log where caller_id='"+callerId+"' and tel='"+tel+"' and call_fee>1 and end_time>'"+end+"'";
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

	// 已知call_sn，搜索id
	private List<Integer> getId(String callsn) {
		String sql = "select id from phone_log where call_sn='" + callsn + "'";
		final List<Integer> list = new ArrayList<Integer>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getInt(1));
				}

			}
		});
		// System.out.println(list);
		return list;
	}

	// 查找记录的条数及其call_sn
	private Map<String, Map<String, String>> record() {
		String sql = "select call_sn,count(*) from phone_log where call_sn!='0' group by call_sn,tel";
		// final Map<String, String> map = new HashMap<String, String>();
		final Map<String, Map<String, String>> list = new HashMap<String, Map<String, String>>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				// int i = 0;
				while (rs.next()) {
					if (rs.getInt(2) > 1) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("count", rs.getString(2));
						map.put("call_sn", rs.getString(1));
						list.put(map.get("call_sn"), map);
						// i++;
					}
				}
				// System.out.println(i);
			}
		});
		return list;
	}

	// 删除重复的记录
	private void delete(Integer id) {
		String sql = "delete from phone_log where id='" + id + "'";
		DBUtils.insertUpdate(DB, sql);
		// System.out.println(sql);
	}

	// 发送短信
	private void sendSMS(JSONObject obj) {
		String addr;
		try {
			addr = getAddress(obj.get("call_passid").toString());
		} catch (HttpException e) {
			addr = null;
		} catch (IOException e) {
			addr = null;
		} catch (ParserConfigurationException e) {
			addr = null;
		} catch (SAXException e) {
			addr = null;
		}
		if (addr != null) {
			// System.out.println(content);
			SmsUtil.getInstance().sendSms(
					"sms_message",
					obj.getString("mobile"),
					null,
					null,
					new String[] { obj.getString("tel"),
							obj.getString("end_time"), addr,
							obj.getString("call_passid") });
		} else {
			// System.out.println(contents);
			SmsUtil.getInstance().sendSms(
					"sms_message",
					obj.getString("mobile"),
					null,
					null,
					new String[] { obj.getString("tel"),
							obj.getString("end_time"),
							obj.getString("call_passid") });
		}
	}

	/**
	 * 
	 * userkey=11683e186d tel=4008676666 ext= start_time=2012-08-01 00:00:00
	 * end_time=2012-08-01 00:02:00
	 */
	private String getSign(Date from, Date to, String tel, String ext) {
		String result = "";
		if (StringUtils.isNotEmpty(tel)) {
			map.put("tel", tel);
		}
		if (StringUtils.isNotEmpty(ext)) {
			map.put("ext", ext);
		}
		map.put("start_time", DateUtil.toString(from, "yyyy-MM-dd 00:00:00"));
		map.put("end_time", DateUtil.toString(to, "yyyy-MM-dd 23:59:59"));
		for (String key : map.keySet()) {
			result = result + key + "=" + map.get(key);
		}
		result += apiKey;
		try {
			result = MD5.encode(result, MD5.LENGTH_32);
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return result;
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		SynPhoneLogTask systemCPPTask = new SynPhoneLogTask();
		map.put("userkey", userKey);
		systemCPPTask.exec(DateUtil.getDate("2015-11-25", "yyyy-MM-dd"));
		System.out.println(1);
	}

	/*
	 * 验证该用户有无未接来电短信服务,返回0,表示该用户没有该服务； 返回1，表示该用户有该服务
	 */
	public boolean missedPhoneMessageService(String companyId) {
		String sql = "select count(*) from crm_company_service where company_id='"
				+ companyId
				+ "'and crm_service_code='"
				+ svrCode
				+ "' and apply_status='1'";
		final int[] num = new int[] { 0 };
		DBUtils.select(DB, sql, new IReadDataHandler() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					num[0] = rs.getInt(1);
				}
			}
		});
		if (num[0] == 0) {
			return false;
		} else {
			return true;
		}
	}

	/* 获取companyId */
	public String getCompanyId(String tel) {
		String sql = "select company_id from phone where tel='" + tel + "' order by gmt_created asc";
		final String[] str = new String[] { "" };
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					str[0] = rs.getString(1);
				}
			}
		});
		return str[0];
	}

	/* 根据公司id搜索该公司的手机号码mobile */
	public String getMobile(String companyId) {
		String sql = "select mobile from company_account where company_id='"
				+ companyId + "'";
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

	/* 根据手机号码获取地址 */
	public String getAddress(String tel) throws HttpException, IOException,
			ParserConfigurationException, SAXException {
		String telN;
		if (tel.length() == 12) {
			telN = tel.substring(1);
		} else {
			telN = tel;
		}
		Pattern pattern = Pattern.compile("1\\d{10}");
		if (StringUtils.isNotEmpty(tel) && StringUtils.isNumber(telN)) {
			Matcher matcher = pattern.matcher(telN);
			if (matcher.matches()) {
				String url = "http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi?chgmobile="
						+ telN;
				String result = HttpUtils.getInstance().httpGet(url, "GBK");
				StringReader stringReader = new StringReader(result);
				InputSource inputSource = new InputSource(stringReader);
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory
						.newDocumentBuilder();
				Document document = documentBuilder.parse(inputSource);
				String retmsg = document.getElementsByTagName("retmsg").item(0)
						.getFirstChild().getNodeValue();
				if (retmsg.equals("OK")) {
					String city = document.getElementsByTagName("city").item(0)
							.getFirstChild().getNodeValue().trim();
					return city;
				} else {
					return null;
				}
			}
		}
		return null;
	}

	/* 插入短信日志 */
	public void insertLog(String province, String city, String tel, Float fee,
			String callSn, String ans, String endT) {
		Integer comp=Integer.valueOf(getCompanyId(tel));
		String cal = "zz91-sms";
		// System.out.println(new Date().getTime());
		String sql = "INSERT INTO phone_log (caller_id,company_id,province,city,tel,call_fee,start_time,gmt_created,gmt_modified,end_time,call_sn,state)"
				+ "VALUES ('"
				+ cal
				+ "','"
				+ comp
				+ "','"
				+ province
				+ "','"
				+ city
				+ "','"
				+ tel
				+ "','"
				+ fee
				+ "','"
				+ ans + "',now(),now(),'" + endT + "','" + callSn + "','0')";
		DBUtils.insertUpdate(DB, sql);
		// System.out.println(sql);
	}

	/* 获取该公司发送短信的费用 */
	public String getFee(String tel) {
		String sql = "select sms_fee from phone where tel='" + tel + "'";
		final String[] fee = new String[] { "0" };
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					fee[0] = rs.getString(1);
				}
			}
		});
		return fee[0];
	}
}
