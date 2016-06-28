package com.zz91.mission.feiliao91;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.zz91.task.common.ZZTask;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;

public class UpdateLogisticsInfoTask implements ZZTask{

	/**
	 * 每日自动更新物流信息（淘再生）
	 * 如果物流状态为0，则更新，并判断判断是否到货，
	 * 若到货则更新状态为1，并在order表的detail字段加一个delive_time的到货时间键
	 * @author zhujq
	 * @throws Exception 
	 * @throws ParseException 
	 *
	 */
//	private final static String DB = "ast";
	
	public static void main(String[] args) throws ParseException, Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		long start = System.currentTimeMillis();
		UpdateLogisticsInfoTask updateLogisticsInfoTask = new UpdateLogisticsInfoTask();
		updateLogisticsInfoTask.exec(new Date());
		System.out.println("物流信息更新已完成！");
		long end = System.currentTimeMillis();
		System.out.println("共耗时：" + (end - start));
	}
	
	@Override
	public boolean exec(Date baseDate) throws Exception {
		//找出所有的物流状态为0的物流
		String sql="select logistics_no,logistics_code from logistics where logistics_status=0";
		DBUtils.select("feiliao91", sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					String logisticsNo=rs.getString(1);
					String logisticsCode=rs.getString(2);
					if (StringUtils.isEmpty(logisticsNo)||StringUtils.isEmpty(logisticsCode)) {
						continue;
					}
					//更新
					getLastestLogistics(logisticsNo,logisticsCode);
				}
			}
		});
		return true;
	}
	
	
	//快递100接口实现,并插入将返回的数据更新至logistics
		//参数为物流单号和物流公司
	@SuppressWarnings("static-access")
	public void getLastestLogistics(String LogisticsNo,String wuliu_com){
		try
		{	
			//身份授权key,(大小写敏感）
			String id = "21401df38d569f15";
			//要查询的快递公司代码，不支持中文,如debangwuliu对应徳邦物流
			String com = wuliu_com;
			//要查询的快递单号，请勿带特殊符号，不支持中文（大小写不敏感）
			String nu = LogisticsNo;
			//返回类型： 0：返回json字符串， 1：返回xml对象，2：返回html对象， 3：返回text文本。如果不填，默认返回json字符串。
//				String show = "0";
			//返回信息数量： 1:返回多行完整的信息， 0:只返回一行信息。 不填默认返回多行。 
//				String muti = "1";
			//排序： desc：按时间由新到旧排列， asc：按时间由旧到新排列。 不填默认返回倒序（大小写不敏感）
//				String order = "desc";
			
			String req="http://api.kuaidi100.com/api?id="+id+"&com="+com+"&nu="+nu+"&show=0&muti=1&order=desc";
//				URL url= new URL("http://api.kuaidi100.com/api?id=21401df38d569f15&com=debangwuliu&nu=325498582&show=0&muti=1&order=desc");
			URL url= new URL(req);
			URLConnection con=url.openConnection();
			con.setAllowUserInteraction(false);
			InputStream urlStream = url.openStream();
			String type = con.guessContentTypeFromStream(urlStream);
			String charSet=null;
			if (type == null)
			type = con.getContentType();

			if (type == null || type.trim().length() == 0 || type.trim().indexOf("text/html") < 0)
				return ;

			if(type.indexOf("charset=") > 0)
				charSet = type.substring(type.indexOf("charset=") + 8);

			byte b[] = new byte[10000];
			int numRead = urlStream.read(b);
			String content = new String(b, 0, numRead);
			while (numRead != -1) {
				numRead = urlStream.read(b);
				if (numRead != -1) {
				//String newContent = new String(b, 0, numRead);
				String newContent = new String(b, 0, numRead, charSet);
				content += newContent;
				}
			}
			System.out.println(content);
			//更新logistics_info
			updateLogisticsByCode(LogisticsNo,content);
			urlStream.close();
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//更新物流信息
	public void updateLogisticsByCode(String LogisticsNo,String content){
		//解析content，若state键值为3，则已签收，设置Logistics的status为1，否则只更新content
		JSONObject obj = JSONObject.fromObject(content);
		String status=(String) obj.get("status");
		if(StringUtils.isEmpty(status)||("3").equals(status)){
			//只更新logistics_info
			String sql1= "update logistics set gmt_modified=now(),logistics_info='"+content+"'where logistics_no = "+LogisticsNo;
			DBUtils.insertUpdate("feiliao91", sql1);
		}else{
			//更新logistics_info,并设置status=1
			String sql2= "update logistics set gmt_modified = now(),logistics_info='"+content+"',logistics_status=1 where logistics_no = "+LogisticsNo;
			DBUtils.insertUpdate("feiliao91", sql2);
			//取出deliveTime
			
			String deliveTime=getdeliveTime(obj.get("data"));
			//更新至订单表或者退货订单表
			updateOrders(LogisticsNo,deliveTime);
		}
	}
	
	private String getdeliveTime(Object obj) {
		String deliveTime ="";
		try {
			@SuppressWarnings("unchecked")
			List<Map<String,String>> data = (List<Map<String,String>>) obj;
			deliveTime=data.get(0).get("time");
		} catch (Exception e) {
//			System.err.println("data error");
			deliveTime="";
		}
		return deliveTime;
	}

	
	//更新至订单表或者退货订单表
	public void updateOrders(String LogisticsNo,String deliveTime){
		String sql1="select id from orders where logistics_no="+LogisticsNo;
		String sql2="select id from order_return where logistics_no="+LogisticsNo;
		final List<Integer> list1=new ArrayList<Integer>();
		final List<Integer> list2=new ArrayList<Integer>();
		DBUtils.select("feiliao91", sql1, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list1.add(rs.getInt(1));
				}
			}
		});
		DBUtils.select("feiliao91", sql2, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list2.add(rs.getInt(1));
				}
			}
		});
		if(list1.size()<=0&&list2.size()<=0){
			//什么都不干
			return;
		}else if (list1.size()>0&&list2.size()<=0) {
			//往orders表更新
			String o_table ="orders";
			List<Map<Integer, String>> l=doSelectDetails(list1,o_table,deliveTime);
			doUpdateDetails(l,o_table);
		}else {
			//往orders_return表更新
			String o_table ="orders_return";
			List<Map<Integer, String>> l=doSelectDetails(list2,o_table,deliveTime);
			doUpdateDetails(l,o_table);
		}
	}
	
	//取出订单id和detail字段的列表
	public List<Map<Integer, String>> doSelectDetails(List<Integer> list,String o_table,final String deliveTime){
		final List<Map<Integer, String>> bigList = new ArrayList<Map<Integer, String>>();
		for (Integer id : list) {
			if(("orders").equals(o_table)){
				final Map<Integer, String> detailsMap = new HashMap<Integer, String>();
				//取出orders表中的detail
				String sql="select id,details from orders where id = "+id;
//				System.out.println(sql);
				DBUtils.select("feiliao91", sql, new IReadDataHandler() {
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while (rs.next()) {
							JSONObject obj = JSONObject.fromObject(rs.getString(2));
							JSONObject newObj=changeJsonFormat(obj,deliveTime);
							String dt=newObj.toString();
							detailsMap.put(rs.getInt(1),dt);
							bigList.add(detailsMap);
						}
					}
				});
				
			}else{
				final Map<Integer, String> detailsMap = new HashMap<Integer, String>();
				//取出orders_return表中的detail_all
				String sql="select id,detail_all from order_return where id= "+id;
				DBUtils.select("feiliao91", sql, new IReadDataHandler() {
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while (rs.next()) {
							JSONObject obj = JSONObject.fromObject(rs.getString(2));
							JSONObject newObj=changeJsonFormat(obj,deliveTime);
							
							String dt=newObj.toString();
							detailsMap.put(rs.getInt(1),dt);
							bigList.add(detailsMap);
						}
					}
				});
			}
		}
		return bigList;
	}
	
	//更新detail字段
	public void doUpdateDetails(List<Map<Integer, String>> list,String o_table){
		for (Map<Integer, String> map : list) {
			if(("orders").equals(o_table)){
				for(Integer i:map.keySet()){
					String sql="update orders set gmt_modified = now(),details = '"+map.get(i)+"' where id= "+i;
					DBUtils.insertUpdate("feiliao91", sql);
				}
			}else{
				for(Integer i:map.keySet()){
					String sql="update order_return set gmt_modified = now(),detail_all = '"+map.get(i)+"' where id= "+i;
					DBUtils.insertUpdate("feiliao91", sql);
				}
			}
		}
	}
	
	public JSONObject changeJsonFormat(JSONObject obj,String deliveTime){
		do {
			//order表里错误格式格式化
			if(obj.get("sellCompany")==null || obj.get("buyCompany")==null){
				break;
			}
			String tr=(String) JSONObject.fromObject(obj.get("sellCompany")).get("introduce");
			String br="";
			String tr2=(String) JSONObject.fromObject(obj.get("buyCompany")).get("introduce");
			String br2="";
			if(tr.contains("\r\n")){
				br=tr.replaceAll("\r\n", "").replaceAll("\t", "");
			}else{
				br=tr;
			}
			if(tr2.contains("\r\n")){
				br2=tr2.replaceAll("\r\n", "").replaceAll("\t", "");
			}else{
				br2=tr2;
			}
			JSONObject obj2 = JSONObject.fromObject(obj.get("sellCompany"));
			obj2.put("introduce", br);
			obj.put("sellCompany", obj2.toString());
			JSONObject obj3 = JSONObject.fromObject(obj.get("buyCompany"));
			obj3.put("introduce", br2);
			obj.put("buyCompany", obj3.toString());
		} while (false);
		
		obj.put("deliveTime",deliveTime);
		return obj;
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
	
}
