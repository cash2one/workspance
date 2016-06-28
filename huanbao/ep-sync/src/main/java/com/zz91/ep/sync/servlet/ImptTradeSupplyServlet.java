package com.zz91.ep.sync.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zz91.ep.sync.domain.TradeSupply;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IInsertUpdateHandler;
import com.zz91.util.lang.StringUtils;


public class ImptTradeSupplyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final static String DB="ep";
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		doPost(req, res);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	   throws ServletException, IOException  {
	

		String tradeSupply = request.getParameter("tradeSupply");
		
		List<Integer> failure=new ArrayList<Integer>();
		Integer success=0;
		do{
			
			if(StringUtils.isEmpty(tradeSupply) || !tradeSupply.startsWith("[")){
				break;
			}
			
			tradeSupply = StringUtils.decryptUrlParameter(tradeSupply);
			
			JSONArray jarry=JSONArray.fromObject(tradeSupply);
			
			for(Object obj:jarry){
				JSONObject jobj = JSONObject.fromObject(obj);
				if(imptSupply(jobj)){
					success++;
				}else{
					failure.add(jobj.getInt("id"));
				}
			}
			
			
			
		}while(false);
		

		Map<String, Object> resultMap=new HashMap<String, Object>();
		resultMap.put("success", success);
		resultMap.put("failure", failure);
		
		response.setCharacterEncoding("utf-8");
		PrintWriter pw=response.getWriter();
		pw.print(JSONObject.fromObject(resultMap).toString());
		pw.close();

	}
	
	private String convertDate(Date date){
		
		return DateUtil.toString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	private boolean imptSupply(JSONObject tradeSupply){
		final TradeSupply supply = (TradeSupply) JSONObject.toBean(tradeSupply, TradeSupply.class);
		
		//验证ID
		if (supply.getId()==null||supply.getId()>=10000000) {
			return false;
		}
		//validate gmt_modified
		if(supply.getGmtModified()==null){
			return false;
		}
		//insert
		StringBuffer sql= new StringBuffer("insert into trade_supply(");
		sql.append("id,");
		sql.append("uid,");
		sql.append("cid,");
		sql.append("title,");
		sql.append("details,");
		sql.append("category_code,");
		sql.append("group_id,");
		sql.append("photo_cover,");
		sql.append("province_code,");
		sql.append("area_code,");
		sql.append("total_num,");
		sql.append("total_units,");
		sql.append("price_num,");
		sql.append("price_units,");
		sql.append("price_from,");
		sql.append("price_to,");
		sql.append("use_to,");
		sql.append("used_product,");
		sql.append("tags,");
		sql.append("tags_sys,");
		sql.append("details_query,");
		sql.append("property_query,");
		sql.append("message_count,");
		sql.append("view_count,");
		sql.append("favorite_count,");
		sql.append("plus_count,");
		sql.append("html_path,");
		sql.append("integrity,");
		sql.append("gmt_publish,");
		sql.append("gmt_refresh,");
		sql.append("valid_days,");
		sql.append("gmt_expired,");
		sql.append("del_status,");
		sql.append("pause_status,");
		sql.append("check_status,");
		sql.append("check_admin,");
		sql.append("check_refuse,");
		sql.append("gmt_created,");
		sql.append("gmt_modified,");
		sql.append("info_come_from) values(");
		sql.append(supply.getId()).append(",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,");
		sql.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?");
		sql.append(")");
		
					
		DBUtils.insertUpdate(DB, sql.toString(), new IInsertUpdateHandler() {
			
			public void handleInsertUpdate(PreparedStatement stat) throws SQLException {
				
				stat.setInt(1,supply.getUid());
				stat.setInt(2,supply.getCid());
				stat.setString(3,supply.getTitle());
				stat.setString(4,supply.getDetails());
				stat.setString(5,supply.getCategoryCode());
				stat.setInt(6,supply.getGroupId());
				stat.setString(7,supply.getPhotoCover());
				stat.setString(8,supply.getProvinceCode());
				stat.setString(9,supply.getAreaCode());
				stat.setInt(10,supply.getTotalNum());
				stat.setString(11,supply.getTotalUnits());
				stat.setInt(12,supply.getPriceNum());
				stat.setString(13,supply.getPriceUnits());
				stat.setInt(14,supply.getPriceFrom());
				stat.setInt(15,supply.getPriceTo());
				stat.setString(16,supply.getUseTo());
				stat.setInt(17,supply.getUsedProduct());
				stat.setString(18,supply.getTags());
				stat.setString(19,supply.getTagsSys());
				stat.setString(20,supply.getDetailsQuery());
				stat.setString(21,supply.getPropertyQuery());
				stat.setInt(22,supply.getMessageCount());
				stat.setInt(23,supply.getViewCount());
				stat.setInt(24,supply.getFavoriteCount());
				stat.setInt(25,supply.getPlusCount());
				stat.setString(26,supply.getHtmlPath());
				stat.setInt(27,supply.getIntegrity());
				stat.setString(28,convertDate(supply.getGmtPublish()));
				stat.setString(29,convertDate(supply.getGmtRefresh()));
				stat.setInt(30,supply.getValidDays());
				stat.setString(31,convertDate(supply.getGmtExpired()));
				stat.setInt(32,supply.getDelStatus());
				stat.setInt(33,supply.getPauseStatus());
				stat.setInt(34,supply.getCheckStatus());
				stat.setString(35,supply.getCheckAdmin());
				stat.setString(36,supply.getCheckRefuse());
				stat.setString(37,convertDate(supply.getGmtCreated()));
				stat.setString(38,convertDate(supply.getGmtModified()));
				stat.setInt(39,supply.getInfoComeFrom());
				
				stat.execute();
				
			}
			
		});
		
		return true;
	}

}
