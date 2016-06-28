package com.zz91.ep.sync.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;


public class SeedServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final static String DB="ep";
	
	final static Map<String, Integer> MAX_UID=new HashMap<String, Integer>();
	
	static {
		MAX_UID.put("trade_supply", 10000000);
		MAX_UID.put("trade_buy", 10000000);
		MAX_UID.put("comp_profile",50000000);
		MAX_UID.put("comp_account", 10000000);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		doPost(req, res);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException  {
	
		String table = request.getParameter("table");
		
		String sql="select max(id) from "+table+" where id<" + MAX_UID.get(table);
		
		final Map<String, Object> map=new HashMap<String, Object>();
		//maxid
		DBUtils.select(DB, sql, new IReadDataHandler() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					map.put("maxId", rs.getString(1));
				}
			}
		});
		
		String sqlGmt="SELECT UNIX_TIMESTAMP(max(gmt_modified)) FROM "+table;
		//modified
		DBUtils.select(DB, sqlGmt, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					Long time=rs.getLong(1);
					map.put("maxGmtModified", time*1000);
				}
			}
		});
		
		
		response.setCharacterEncoding("utf-8");
		PrintWriter pw=response.getWriter();
		pw.print(JSONObject.fromObject(map).toString());
		pw.close();

	}
	
	
	public void setParams(PreparedStatement paramPreparedStatement) throws SQLException {
	}

//	public static void main(String[] args) {
//		System.out.println(DateUtil.toString(new Date(1343626014000l), "yyyy-MM-dd HH:mm:ss"));
//	}
}
