package com.zz91.ep.sync.servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.zz91.util.db.pool.DBPoolFactory;


public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1315545405117443146L;

	public void init() throws ServletException {


		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");

	}

	public void destroy() {

	}
}
