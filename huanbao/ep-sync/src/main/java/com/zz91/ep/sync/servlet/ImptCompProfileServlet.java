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
import com.zz91.ep.sync.domain.CompProfile;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IInsertUpdateHandler;
import com.zz91.util.lang.StringUtils;

public class ImptCompProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	final static String DB = "ep";

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		doPost(req, res);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String comp = request.getParameter("compProfile");

		List<Integer> failure = new ArrayList<Integer>();
		Integer success = 0;
		do {

			if (StringUtils.isEmpty(comp)
					|| !comp.startsWith("[")) {
				break;
			}

			comp = StringUtils.decryptUrlParameter(comp);

			JSONArray jarry = JSONArray.fromObject(comp);

			for (Object obj : jarry) {
				JSONObject jobj = JSONObject.fromObject(obj);
				if (imptCompProfile(jobj)) {
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

	private String convertDate(Date date) {

		return DateUtil.toString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	
	private boolean imptCompProfile(JSONObject comppro){
		final CompProfile comp = (CompProfile) JSONObject.toBean(comppro, CompProfile.class);
		
		//验证ID
				if (comp.getId()==null||comp.getId()>=50000000) {
					return false;
				}
				//validate gmt_modified
				if(comp.getGmtModified()==null){
					return false;
				}
		//插入数据sql语句
		StringBuffer sql = new StringBuffer("insert into comp_profile");
				sql.append("(")
					.append("id,")
					.append("name,")
					.append("details,")
					.append("industry_code,")
					.append("main_buy,")
					
					.append("main_product_buy,")
					.append("main_supply,")
					.append("main_product_supply,")
					.append("member_code,")
					.append("member_code_block,")
					
					.append("register_code,")
					.append("business_code,")
					.append("area_code,")
					.append("province_code,")
					.append("legal,")
					
					.append("funds,")
					.append("main_brand,")
					.append("address,")
					.append("address_zip,")
					.append("domain,")
					
					.append("domain_two,")
					.append("message_count,")
					.append("view_count,")
					.append("tags,")
					.append("details_query,")
					
					.append("gmt_created,")
					.append("gmt_modified,")
					.append("del_status,")
					.append("process_method,")
					.append("process,")
					
					.append("employee_num,")
					.append("developer_num,")
					.append("plant_area,")
					.append("main_market,")
					.append("main_customer,")
					
					.append("month_output,")
					.append("year_turnover,")
					.append("year_exports,")
					.append("quality_control,")
					.append("register_area,")
					
					.append("enterprise_type,")
					.append("send_time,")
					.append("receive_time,")
					.append("oper_name");
				
				sql.append(") values (");
				sql.append("?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,")
					.append("?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?)");
				//System.out.println(sql.toString());
				DBUtils.insertUpdate(DB, sql.toString(), new IInsertUpdateHandler() {
				
				@Override
				public void handleInsertUpdate(PreparedStatement ps)
						throws SQLException {
					ps.setInt(1, comp.getId());
					ps.setString(2, comp.getName());
					ps.setString(3, comp.getDetails());
					ps.setString(4, comp.getIndustryCode());
					ps.setShort(5, comp.getMainBuy());
					
					
					ps.setString(6, comp.getMainProductBuy());
					ps.setShort(7, comp.getMainSupply());
					ps.setString(8, comp.getMainProductSupply());
					ps.setString(9, comp.getMemberCode());
					ps.setString(10, comp.getMemberCodeBlock());
					
					ps.setString(11, comp.getRegisterCode());
					ps.setString(12, comp.getBusinessCode());
					ps.setString(13, comp.getAreaCode());
					ps.setString(14, comp.getProvinceCode());
					ps.setString(15, comp.getLegal());
					
					
					ps.setString(16, comp.getFunds());
					ps.setString(17, comp.getMainBrand());
					ps.setString(18, comp.getAddress());
					ps.setString(19, comp.getAddressZip());
					ps.setString(20, comp.getDomain());
					
					ps.setString(21, comp.getDomainTwo());
					ps.setInt(22, comp.getMessageCount());
					ps.setInt(23, comp.getViewCount());
					ps.setString(24, comp.getTags());
					ps.setString(25, comp.getDetailsQuery());
					
					ps.setObject(26, convertDate(comp.getGmtCreated()));
					ps.setObject(27, convertDate(comp.getGmtModified()));
					ps.setInt(28, comp.getDelStatus());
					ps.setString(29, comp.getProcessMethod());
					ps.setString(30, comp.getProcess());
					
					ps.setString(31, comp.getEmployeeNum());
					ps.setString(32, comp.getDeveloperNum());
					ps.setString(33, comp.getPlantArea());
					ps.setString(34, comp.getMainMarket());
					ps.setString(35, comp.getMainCustomer());
					
					ps.setString(36, comp.getMonthOutput());
					ps.setString(37, comp.getYearTurnover());
					ps.setString(38, comp.getYearExports());
					ps.setString(39, comp.getQualityControl());
					ps.setString(40, comp.getRegisterArea());
					
					ps.setString(41, comp.getEnterpriseType());
					ps.setString(42, convertDate(comp.getSendTime()));
					ps.setString(43, convertDate(comp.getReceiveTime()));
					ps.setString(44, comp.getOperName());
					ps.execute();
				}
			});
		return true;
	}
}
