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

import com.zz91.ep.sync.domain.TradeBuy;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IInsertUpdateHandler;
import com.zz91.util.lang.StringUtils;

public class ImptTradeBuyServlet extends HttpServlet {

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
			throws ServletException, IOException {
		String tradeBuy = request.getParameter("tradeBuy");

		List<Integer> failure = new ArrayList<Integer>();
		Integer success = 0;
		do {

			if (StringUtils.isEmpty(tradeBuy)
					|| !tradeBuy.startsWith("[")) {
				break;
			}

			tradeBuy = StringUtils.decryptUrlParameter(tradeBuy);

			JSONArray jarry = JSONArray.fromObject(tradeBuy);

			for (Object obj : jarry) {
				JSONObject jobj = JSONObject.fromObject(obj);
				if (imptBuy(jobj)) {
					success++;
				} else {
					failure.add(jobj.getInt("id"));
				}
			}

		} while (false);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", success);
		resultMap.put("failure", failure);

		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(JSONObject.fromObject(resultMap).toString());
		pw.close();

	}
	
	/**
	 * 处理时间
	 * @param date
	 * @return
	 */
	private String convertDate(Date date){
		
		return DateUtil.toString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 插入数据
	 * @param tradeBuy
	 * @return
	 */
	private boolean imptBuy(JSONObject tradeBuy){
		final TradeBuy buy = (TradeBuy) JSONObject.toBean(tradeBuy, TradeBuy.class);
		
		//验证ID
		if (buy.getId()==null||buy.getId()>=10000000) {
			return false;
		}
		//validate gmt_modified
		if(buy.getGmtModified()==null){
			return false;
		}
		//insert
		StringBuffer sql= new StringBuffer();
		sql.append("insert into trade_buy(");
		sql.append("id,uid,cid,title,details,category_code,photo_cover,province_code,")
		.append("area_code,buy_type,quantity,quantity_year,quantity_untis,supply_area_code,use_to,")
		.append("gmt_confirm,gmt_receive,gmt_publish,gmt_refresh,valid_days,gmt_expired,")
		.append("tags_sys,details_query,message_count,view_count,favorite_count,")
		.append("plus_count,html_path,del_status,pause_status,check_status,check_admin,check_refuse,")
		.append("gmt_check,gmt_created,gmt_modified) ")
		.append("values(");
		sql.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?")
			.append(",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		DBUtils.insertUpdate(DB, sql.toString(), new IInsertUpdateHandler() {
			
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, buy.getId());
				ps.setInt(2, buy.getUid());
				ps.setInt(3, buy.getCid());
				ps.setString(4, buy.getTitle());
				ps.setString(5, buy.getDetails());
				ps.setString(6, buy.getCategoryCode());
				ps.setString(7, buy.getPhotoCover());
				ps.setString(8, buy.getProvinceCode());
				ps.setString(9, buy.getAreaCode());
				ps.setShort(10, buy.getBuyType());
				ps.setInt(11, buy.getQuantity());
				ps.setInt(12, buy.getQuantityYear());
				ps.setString(13, buy.getQuantityUntis());
				ps.setString(14, buy.getSupplyAreaCode());
				ps.setString(15, buy.getUseTo());
				ps.setString(16, convertDate(buy.getGmtConfirm()));
				ps.setString(17, convertDate(buy.getGmtReceive()));
				ps.setString(18,convertDate(buy.getGmtPublish()));
				ps.setString(19, convertDate(buy.getGmtRefresh()));
				ps.setShort(20,buy.getValidDays());
				ps.setString(21, convertDate(buy.getGmtExpired()));
				ps.setString(22, buy.getTagsSys());
				ps.setString(23, buy.getDetailsQuery());
				ps.setInt(24, buy.getMessageCount());
				ps.setInt(25, buy.getViewCount());
				ps.setInt(26, buy.getFavoriteCount());
				ps.setInt(27, buy.getPlusCount());
				ps.setString(28, buy.getHtmlPath());
				ps.setShort(29, buy.getDelStatus());
				ps.setShort(30, buy.getPauseStatus());
				ps.setShort(31, buy.getCheckStatus());
				ps.setString(32,buy.getCheckAdmin());
				ps.setString(33,buy.getCheckRefuse());
				ps.setString(34, convertDate(buy.getGmtCheck()));
				ps.setString(35, convertDate(buy.getGmtCreated()));
				ps.setString(36, convertDate(buy.getGmtModified()));
				ps.execute();
				
			}
			
		});
		
		return true;
	}
	
	
	
}
