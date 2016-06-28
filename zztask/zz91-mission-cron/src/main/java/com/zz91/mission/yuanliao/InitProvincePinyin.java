package com.zz91.mission.yuanliao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class InitProvincePinyin implements ZZTask {
	
	public static void main(String[] args) throws Exception{
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		InitProvincePinyin obj = new InitProvincePinyin();
		obj.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Override
	public boolean exec(Date baseDate) throws Exception {
		Map<String,String> map = getProvinceMap();
		for(String s : map.keySet()){
			System.out.println(s);
			System.out.println(getPinyin(map.get(s)));
			insert(s,getPinyin(map.get(s)));
		}
		return true;
	}
	//将数据更新进数据库中
	public void insert(String code,String pinyin){
		String sql = "insert into province_pinyin(code,pinyin,gmt_created,gmt_modified)"+"VALUES('"+code+"','"+pinyin+"',now(),now())";
		DBUtils.insertUpdate("ast", sql);
	}
	//获取省份列表
    public Map<String,String> getProvinceMap(){
    	String sql = "select code,label from category where parent_code='"+"10011000"+"' order by id asc";
    	final Map<String,String> map = new LinkedHashMap<String,String>();
    	DBUtils.select("ast", sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)throws SQLException {
				while(rs.next()){
					map.put(rs.getString(1), rs.getString(2));
				}
			}});
    	return map;
    }
    //判断该名称是否存在
    public boolean isHave(String pinyin){
    	String sql = "select id from province_pinyin where pinyin = '"+pinyin+"'";
    	final List<Integer> list = new ArrayList<Integer>();
    	DBUtils.select("ast", sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)throws SQLException {
				while(rs.next()){
					list.add(rs.getInt(1));
				}
			}});
    	if(list.size()<1){
    		return false;
    	}
    	return true;
    }
    //将中文转成拼音首字母缩写
    public String getPinyin(String province){
    	String convert = "";  
	    for (int j = 0; j < province.length(); j++) {  
	        char word =province.charAt(j);  
	        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
	        if (pinyinArray != null) {  
	             convert += pinyinArray[0].charAt(0);  
	        } else {  
	             convert += word;  
	        }  
	   }  
	   Integer flag = 0;
	   Integer fi = 0;
	   do{
	      if(isHave(convert)){
	        if(fi>0){
	        	convert = convert.replace(String.valueOf(fi),"");
	        }
	        fi = fi + 1;
	        convert = convert + String.valueOf(fi);
	      }else{
	        flag = 1;
	      }
	   }while(flag!=1);
    return convert;
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
