package com.zz91.mission.yuanliao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class InitCategoryYuanliao implements ZZTask {
	
	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		InitCategoryYuanliao obj = new InitCategoryYuanliao();
		obj.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Override
	public boolean exec(Date baseDate) throws Exception {
		Map<Integer,String> map = getList();
		String lowercase = "";
		for(Integer in : map.keySet()){
			//将大写字母转成小写字母
			lowercase = StringUtils.lowerCase(map.get(in));
			if(lowercase.contains("/")){
				lowercase = lowercase.replace("/", "-");
			}
			System.out.println(lowercase);
			update(in,lowercase);
		}
		return true;
	}
	//更新
	public void update(Integer id,String lowercase){
		String sql = "update category_yuanliao set pinyin = '"+lowercase+"' where id = '"+id+"'";
		System.out.println(sql);
		DBUtils.insertUpdate("ast", sql);
	}
	
	public Map<Integer,String> getList(){
		String sql = "select id,pinyin from category_yuanliao where length(code)=16";
		final Map<Integer,String> map = new HashMap<Integer,String>();
		DBUtils.select("ast", sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)throws SQLException {
				while(rs.next()){
					map.put(rs.getInt(1), rs.getString(2));
				}
			}
		});
		return map;
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
