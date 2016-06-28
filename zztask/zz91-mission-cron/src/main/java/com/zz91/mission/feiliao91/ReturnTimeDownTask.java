package com.zz91.mission.feiliao91;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;

public class ReturnTimeDownTask implements ZZTask {
	private final static String DB = "feiliao91";

	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		final SimpleDateFormat fomart = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date = DateUtil.toString(
				DateUtil.getDateAfterDays(baseDate, -20), "yyyy-MM-dd");
		String sql = "select gmt_created,detail_all,recode_detail,status,target_id,id,return_price,company_id,order_no from order_return where gmt_created>"
				+ date;
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Date date = rs.getDate(1);
					String detailAll = rs.getString(2);
					String recodeDetail = rs.getString(3);
					Integer status = rs.getInt(4);
					Integer sellCompanyId = rs.getInt(5);
					Integer id = rs.getInt(6);
					Float price = rs.getFloat(7);
					Integer companyId = rs.getInt(8);
					String orderNo = rs.getString(9);
					long oldTime = date.getTime();
					long newTime = System.currentTimeMillis();
					if (newTime > oldTime) {
						if ((newTime - oldTime) >= (7 * 24 * 60 * 60 * 1000)
								&& status == 0) {
							updateStatus(detailAll, recodeDetail, 1,
									sellCompanyId, id, companyId, price,
									orderNo);
						}
					}
					Map<String, Object> json = getMap(recodeDetail);
					if (json != null) {
						Object obj = json.get("agree");
						if (obj != null) {
							JSONArray pp = JSONArray.fromObject(obj);
							if (pp != null) {
								Object cc = pp.get(0);
								JSONArray bb = JSONArray.fromObject(cc);
								if (bb != null) {
									String time = (String) bb.get(0);
									try {
										long oldTime1 = DateUtil
												.getMillis(DateUtil.getDate(
														time,
														"yyyy-MM-dd HH:mm:ss"));
										long NewTime1 = System
												.currentTimeMillis();
										if ((NewTime1 > oldTime1)
												&& (NewTime1 - oldTime1) >= (7 * 24 * 60 * 60 * 1000)
												&& status == 1) {
											updateStatus(detailAll,
													recodeDetail, 99,
													sellCompanyId, id,
													companyId, price, orderNo);
										}
									} catch (ParseException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}

					if (json != null) {
						Object obj = json.get("refused");
						if (obj != null) {
							JSONArray pp = JSONArray.fromObject(obj);
							if (pp != null) {
								Object cc = pp.get(0);
								if (cc != null) {
									JSONArray bb = JSONArray.fromObject(cc);
									if (bb != null) {
										String time = (String) bb.get(0);
										try {
											long oldTime1 = DateUtil.getMillis(DateUtil
													.getDate(time,
															"yyyy-MM-dd HH:mm:ss"));
											long NewTime1 = System
													.currentTimeMillis();
											if ((NewTime1 > oldTime1)
													&& (NewTime1 - oldTime1) >= (7 * 24 * 60 * 60 * 1000)
													&& status == 2) {
												updateStatus(detailAll,
														recodeDetail, 99,
														sellCompanyId, id,
														companyId, price,
														orderNo);
											}
										} catch (ParseException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
					}

					Map<String, Object> json2 = getMap(detailAll);
					if (json2 != null) {
						Object time = json2.get("deliveTime");
						if (time != null) {
							try {
								long oldTime1 = DateUtil.getMillis(DateUtil
										.getDate(time.toString(),
												"yyyy-MM-dd HH:mm:ss"));
								long NewTime1 = System.currentTimeMillis();
								if ((NewTime1 > oldTime1)
										&& (NewTime1 - oldTime1) >= (10 * 24 * 60 * 60 * 1000)
										&& status == 3) {
									updateStatus(detailAll, recodeDetail, 100,
											sellCompanyId, id, companyId,
											price, orderNo);
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

			private void updateStatus(String detail, String recode, Integer i,
					Integer sellCompanyId, Integer id, Integer companyId,
					Float price, String orderNo) {
				Map<String, Object> json = getMap(detail);
				if (json == null) {
					json = new HashMap<String, Object>();
				}
				Map<String, Object> json1 = getMap(recode);
				if (json1 == null) {
					json1 = new HashMap<String, Object>();
				}
				// 7天时间自动同意买家退货申请
				if (i == 1) {
					if (sellCompanyId != null) {
						// 查询公司名称
						Map<String, Object> company = selectCompanyInfo(sellCompanyId);
						// 查询地址
						Map<String, Object> map = selectDefaultAddress(sellCompanyId);
						// 退款地址
						if (map.size() > 0) {
							json.put("areaCode", map.get("areaCode"));
							json.put("address", map.get("address"));
							json.put("name", map.get("name"));
							json.put("mobile", map.get("mobile"));
						} else {
							Map<String, Object> map1 = selectAddress(sellCompanyId);
							json.put("areaCode", map1.get("areaCode"));
							json.put("address", map1.get("address"));
							json.put("name", map1.get("name"));
							json.put("mobile", map1.get("mobile"));
						}
						// 退款说明
						json.put("returnText", null);

						Date date = new Date();
						List<String[]> list = new ArrayList<String[]>();
						String[] ar = new String[5];
						ar[0] = fomart.format(date);
						ar[1] = "【标题】卖家" + company.get("name") + "已经同意了申请";
						ar[2] = "【内容】";
						ar[3] = "退货地址为:" + json.get("name") + ","
								+ json.get("mobile") + ","
								+ json.get("areaCode") + ","
								+ json.get("address");
						// if (false) {
						// ar[4] = "退款说明:" + null;
						// }
						if (map.get("tel") != null) {
							ar[4] = "商家电话:" + map.get("tel");
						}else{
							ar[4] ="";
						}
						list.add(ar);
						json1.put("agree", list);
						// 修改退货说明和协商记录
						updateAll(JSONObject.fromObject(json).toString(),
								JSONObject.fromObject(json1).toString(), i, id);
					}
				}
				if (i == 99) {
					Date date = new Date();
					List<String[]> list = new ArrayList<String[]>();
					String[] ar = new String[4];
					ar[0] = fomart.format(date);
					ar[1] = "【标题】买家逾期未处理退货申请";
					ar[2] = "【内容】";
					ar[3] = "买家逾期未处理退货信息,系统自动关闭退货订单.";
					list.add(ar);
					json1.put("pay", list);
					updateAll(JSONObject.fromObject(json).toString(),
							JSONObject.fromObject(json1).toString(), 99, id);
				}

				if (i == 100) {
					JSONObject js = new JSONObject();
					if (orderNo != null) {
						js.put("orderNo", orderNo);
					}
					Date date = new Date();
					List<String[]> list = new ArrayList<String[]>();
					String[] ar = new String[4];
					ar[0] = fomart.format(date);
					ar[1] = "【标题】卖家付款";
					ar[2] = "【内容】";
					ar[3] = "退款申请达成,退款成功.";
					list.add(ar);
					json1.put("pay", list);
					updateAll(JSONObject.fromObject(json).toString(),
							JSONObject.fromObject(json1).toString(), 99, id);
					// 根据companyId查询钱
					Map<String, Object> map = selectAccount(companyId);
					if (map != null) {
						// 买家加钱
						SetMoney(companyId, (Float) map.get("price") + price);
						insertTradeLog(companyId, price, 1, js.toString());
					}
					Map<String, Object> map2 = selectAccount(sellCompanyId);
					if (map2 != null) {
						// 卖家扣钱
						if ((Float) map2.get("price") >= price) {
							SetMoney(sellCompanyId, (Float) map2.get("price")
									- price);
						} else {
							SetMoney(sellCompanyId, 0f);
						}
						insertTradeLog(sellCompanyId, price, 0, js.toString());
					}
				}
			}

			private Map<String, Object> selectAccount(Integer companyId) {
				String sql = "select sum_money from company_account where company_id="
						+ companyId;
				final Map<String, Object> map = new HashMap<String, Object>();
				DBUtils.select(DB, sql, new IReadDataHandler() {
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while (rs.next()) {
							map.put("price", rs.getFloat(1));
						}
					}
				});
				return map;
			}

			// 钱的流水账信息
			private void insertTradeLog(Integer company, Float money,
					Integer status, String orderNo) {
				String sql = "insert into trade_log(company_id,status,money,gmt_created,gmt_modified,remark) values("
						+ company
						+ ","
						+ status
						+ ","
						+ money
						+ ",now(),now(),'" + orderNo + "')";
				DBUtils.insertUpdate(DB, sql);
			}

			// 付款
			private void SetMoney(Integer sellCompanyId, Float money) {
				String sql4 = "update company_account set gmt_modified=now(),sum_money="
						+ money + "where company_id=" + sellCompanyId;
				DBUtils.insertUpdate(DB, sql4);
			}

			// 更新order_return表 状态
			private void updateAll(String obj, String code, Integer i,
					Integer id) {
				String sql = "update order_return set gmt_modified=now(),status="
						+ i
						+ ",detail_all='"
						+ obj
						+ "',recode_detail='"
						+ code + "' where id=" + id;
				DBUtils.insertUpdate(DB, sql);
			}

			// 查询公司名称
			private Map<String, Object> selectCompanyInfo(Integer companyId) {
				String sql = "select name from company_info where id="
						+ companyId;
				final Map<String, Object> map = new HashMap<String, Object>();
				DBUtils.select(DB, sql, new IReadDataHandler() {
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while (rs.next()) {
							map.put("name", rs.getString(1));
						}
					}
				});
				return map;
			}

			// 查询最早的地址
			private Map<String, Object> selectAddress(Integer companyId) {
				final Map<String, Object> map = new HashMap<String, Object>();
				String sql = "select area_code,address,name,mobile,tel from address where company_id="
						+ companyId
						+ " and is_del=0 and address_type=0 limit 1";
				DBUtils.select(DB, sql, new IReadDataHandler() {
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while (rs.next()) {
							map.put("areaCode", rs.getString(1));
							map.put("address", rs.getString(2));
							map.put("name", rs.getString(3));
							map.put("mobile", rs.getString(4));
							map.put("tel", rs.getString(5));
						}
					}
				});
				return map;
			}

			// 查询默认地址
			private Map<String, Object> selectDefaultAddress(Integer companyId) {
				String sql = "select area_code,address,name,mobile,tel from address where is_default=1 and is_del=0 and address_type=0 and company_id="
						+ companyId;
				final Map<String, Object> map = new HashMap<String, Object>();
				DBUtils.select(DB, sql, new IReadDataHandler() {
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while (rs.next()) {
							map.put("areaCode", rs.getString(1));
							map.put("address", rs.getString(2));
							map.put("name", rs.getString(3));
							map.put("mobile", rs.getString(4));
							map.put("tel", rs.getString(5));
						}
					}
				});
				return map;
			}
		});

		return true;
	}

	// 将json字符串转换为map类型
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator<String> iter = jsonObject.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	public static void main(String[] args) {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		long start = System.currentTimeMillis();

		ReturnTimeDownTask task = new ReturnTimeDownTask();
		try {
			task.exec(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("共耗时：" + (end - start));
	}

}
