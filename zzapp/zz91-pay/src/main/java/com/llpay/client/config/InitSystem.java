/**
 * @author kongsj
 * @date 2014年11月13日
 * 
 */
package com.llpay.client.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.domain.Param;
import com.zz91.util.param.ParamUtils;

public class InitSystem implements ServletContextListener{

	final static String WEB_PROP="web.properties";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 缓存
		MemcachedUtils.getInstance().init(WEB_PROP);
		// 初始化开始
		DBPoolFactory.getInstance().init("file:/mnt/tools/config/db/db-zztask-jdbc.properties");
		// 初始化
		ParamUtils.getInstance().init(getParamList(), "memcached");

	}
	
	private List<Param> getParamList(){
		final List<Param> list =new  ArrayList<Param>();
		String sql = "select id,name,types,`key`,value,sort,isuse from param ";
		DBUtils.select("ast", sql, new IReadDataHandler() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Param param = new Param();
					param.setId(rs.getInt(1));
					param.setName(rs.getString(2));
					param.setTypes(rs.getString(3));
					param.setKey(rs.getString(4));
					param.setValue(rs.getString(5));
					param.setSort(rs.getInt(6));
					param.setIsuse(rs.getInt(7));
					list.add(param);
				}
			}

		});
		return list;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// 销毁
	}

}
