/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7
 */
package com.ast.ast1949.front.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-4-7
 */
public class WebBaseDataHelper {
	
	private static WebBaseDataHelper _instance;
	
	final static int EXP=43200;  //超时时间：12小时
	//显示在外网的网站作弊数据
	final static Map<String, Integer> CHEAT=new HashMap<String, Integer>();
	
	static{
	}
	
	synchronized public WebBaseDataHelper getInstance(){
		if(_instance==null){
			_instance=new WebBaseDataHelper();
		}
		return _instance;
	}
	
//	public Integer getData(String cate){
//		Integer integer=(Integer) MemcachedFacade.getInstance().get("webbasedata@"+cate);
//		if(integer==null){
//			
//		}
//	}
}
