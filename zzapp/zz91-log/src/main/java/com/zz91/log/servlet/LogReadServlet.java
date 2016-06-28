package com.zz91.log.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zz91.log.util.MongoUtil;
/**
 * 项目名称：日志统计
 * 模块描述：读取日志数据servlet
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-08-16　　　黄怀清　　　　　　　1.0.0　　　　　原文本改为mongDB
 */
@SuppressWarnings("serial")
public class LogReadServlet extends HttpServlet {
	

	final static Logger LOG= Logger.getLogger(LogReadServlet.class);

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//result
		resp.setCharacterEncoding("utf-8");
		PrintWriter pw=resp.getWriter();
		
	 	try {
	 		//所有参数
	 		Map<String,Object> search = (Map<String,Object>)JSONSerializer.toJSON(request.getParameter("params"));
	 		search.put("start", request.getParameter("start"));
	 		search.put("limit", request.getParameter("limit"));
	 		//分页参数
	 		JSONObject pageObj = new JSONObject();
	        
	 		//解析并去掉search中分页参数
	 		pageResolve(search, pageObj);
	 		//解析查询条件
	        DBCursor cursor = paramResolve(search);
	        //对查询结果做分页处理
	        pageResult(pageObj, cursor);
        	//解析结果集(PageDto模式)
	        resultResolve(cursor,pageObj);
	        
	        //return result
			pw.print(pageObj.toString());
		} catch (Exception e) {
			pw.print("Exception:"+e.getMessage());
		}finally{
			pw.close();
		}
        
	}
	//解析并从request中去除分页参数
	private void pageResolve(Map<String,Object> search,JSONObject pageObj){
		if(search.get("start")!=null){
			pageObj.put("start", Integer.parseInt(search.get("start").toString()));
			search.remove("start");
		}
		if(search.get("limit")!=null){
			pageObj.put("limit", Integer.parseInt(search.get("limit").toString()));
			search.remove("limit");
		}
		if(search.get("dir")!=null){
			pageObj.put("dir", search.get("dir").toString());
			search.remove("dir");
		}
		if(search.get("sort")!=null){
			pageObj.put("sort", search.get("sort").toString());
			search.remove("sort");
		}
	}
	
	//解析参数并查询
	private DBCursor paramResolve(Map<String,Object> search){
		//columns
		DBObject columns = new BasicDBObject();
		columns.put("_id", 0);
		if(search.get("columns")!=null){
			Map<String,Object> map=(Map<String,Object>)JSONSerializer.toJSON(search.get("columns"));
			columns.putAll(map);
			search.remove("columns");
		}
		if(search.get("or")!=null){
			search.put("$or", JSONArray.fromObject(search.get("or").toString()));
			search.remove("or");
		}
		return MongoUtil.getInstance().dbc.find(new BasicDBObject(search),columns);
	}
	//对查询游标做分页处理
	private DBCursor pageResult(JSONObject pageObj,DBCursor cursor){
		//totals
		pageObj.put("totals",cursor.size());
		//start
		if (pageObj.get("start")!=null) {
			cursor.skip(pageObj.getInt("start"));
		}
		//limit
		if (pageObj.get("limit")!=null) {
			cursor.limit(pageObj.getInt("limit"));
		}else{//默认查询20条
			cursor.limit(20);
		}
		//sort
		if (pageObj.get("sort")!=null) {
			//排序方式,默认升序
			Integer dir=1;
			if(pageObj.get("dir")!=null){
				if(pageObj.getString("dir").equals("desc")){
					dir=-1;
				}
			}
			DBObject sort = new BasicDBObject(pageObj.getString("sort"),dir);
			cursor.sort(sort);
		}
		
		return cursor;
	}
	//查询结果封装,以PageDto为格式
	private JSONObject resultResolve(DBCursor cursor,JSONObject pageObj){
		
        JSONArray records = new JSONArray();
        while(cursor.hasNext()){
        	JSONObject cur=JSONObject.fromObject(cursor.next());
        	records.add(cur);
        }
        pageObj.put("records", records);
        
        return pageObj;
	}
	
}
