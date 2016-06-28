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


import com.zz91.ep.sync.domain.CompAccount;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IInsertUpdateHandler;
import com.zz91.util.lang.StringUtils;

public class ImptCompAccountServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	final static String DB="ep";
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		doPost(req, res);
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			   throws ServletException, IOException  {
			

				String tradeSupply = request.getParameter("compAccount");
				
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
						if(imptCompAccount(jobj)){
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
			
		private boolean imptCompAccount(JSONObject compAccount){
				final CompAccount account = (CompAccount) JSONObject.toBean(compAccount, CompAccount.class);
				
				//验证ID
				if (account.getId()==null||account.getId()>=10000000) {
					return false;
				}
				//validate gmt_modified
				if(account.getGmtModified()==null){
					return false;
				}
				//insert
				StringBuffer sql= new StringBuffer("insert into comp_account");
				sql.append("(")
				.append("id,")
				.append("cid,")
				.append("account,")
				.append("email,")
				.append("password,")
				.append("password_clear,")
				.append("name,")
				.append("sex,")
				.append("mobile,")
				.append("phone_country,")
				.append("phone_area,")
				.append("phone,")
				.append("fax_country,")
				.append("fax_area,")
				.append("fax,")
				.append("dept,")
				.append("contact,")
				.append("position,")
				.append("login_count,")
				.append("login_ip,")
				.append("gmt_login,")
				.append("gmt_register,")
				.append("gmt_created,")
				.append("gmt_modified");
			sql.append(") values (");
			sql.append("?,?,?,?,?,?,?,?,?,?,?,?,")
				.append("?,?,?,?,?,?,?,?,?,?,?,?)");
							
				DBUtils.insertUpdate(DB, sql.toString(), new IInsertUpdateHandler() {
					
					public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
						ps.setInt(1, account.getId());
						ps.setInt(2, account.getCid());
						ps.setString(3, account.getAccount());
						ps.setString(4, account.getEmail());
						ps.setString(5, account.getPassword());
						ps.setString(6, account.getPasswordClear());
						ps.setString(7, account.getName());
						ps.setShort(8, account.getSex());
						ps.setString(9, account.getMobile());
						ps.setString(10, account.getPhoneCountry());
						ps.setString(11, account.getPhoneArea());
						ps.setString(12, account.getPhone());
						ps.setString(13, account.getFaxCountry());
						ps.setString(14, account.getFaxArea());
						ps.setString(15, account.getFax());
						ps.setString(16, account.getDept());
						ps.setString(17, account.getContact());
						ps.setString(18, account.getPosition());
						ps.setInt(19, account.getLoginCount());
						ps.setString(20, account.getLoginIp());
						ps.setString(21, convertDate(account.getGmtLogin()));
						ps.setString(22, convertDate(account.getGmtRegister()));
						ps.setString(23, convertDate(account.getGmtCreated()));
						ps.setString(24, convertDate(account.getGmtModified()));
						ps.execute();
					}
					
				});
				
				return true;
			}
	
}
