package com.zz91.log.util;

import java.util.Map;

import org.apache.log4j.Logger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.zz91.util.file.FileUtils;

public class MongoUtil {
	private static MongoUtil _instances = null;
	private static String DEFAULT_PROP="logclient.properties";
	final static Logger LOG= Logger.getLogger("com.zz91.log4z");
	
	public Mongo mongo=null;
	public DB db = null;
	public DBCollection dbc;
	
	public void init(){
		init(DEFAULT_PROP);
	}
	@SuppressWarnings("unchecked")
	public void init(String properties){
		Map<String, String> map =null;
		try {
			map = FileUtils.readPropertyFile(properties, "utf-8");
	        
		} catch (Exception e) {
			LOG.error("read propertyFile Exception:"+e.getMessage());
		}
		try {
			if(map!=null){
				//连接池配置
				MongoOptions options = new MongoOptions(); 
		        options.autoConnectRetry = true; 
		        options.connectionsPerHost = 20; 
		        options.connectTimeout = 0; 
		        options.maxAutoConnectRetryTime = 12000; 
		        options.maxWaitTime = 12000; 
		        options.socketKeepAlive = true; 
		        options.socketTimeout = 0;
				
		        mongo = new Mongo(map.get("mongo.host"));
				db = mongo.getDB(map.get("mongo.db"));
				dbc = db.getCollection(map.get("mongo.collection"));
			}
		} catch (Exception e) {
			LOG.error("MongoDB connection Exception:"+e.getMessage());
		}
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		mongo.close();
	}
	
	public static synchronized MongoUtil getInstance(){
		if (_instances == null) {
			_instances = new MongoUtil();
		}
		return _instances;
	}
}
