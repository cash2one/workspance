/**
 * Project name: commons-sms
 * File name: DBUtils.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms.persist;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-3-22
 */
public class DBUtils {
    private static final String DBPATH = "/usr/tools/config/db/db-sms.properties";
    private static String username;
    private static String password;
    private static String jdbcUrl;

    static void getProperties() throws FileNotFoundException {
        Properties props = new Properties();
        InputStream input = new FileInputStream(DBPATH);
        try {
            props.load(input);
        } catch (Exception e) {
            System.err.println("不能读取属性文件:sms-db.properties");
        }
        username = props.getProperty("username");
        password = props.getProperty("password");
        jdbcUrl = props.getProperty("jdbcUrl");
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            getProperties();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            // 装载驱动类
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
//            System.out.println("装载驱动异常!");
            e.printStackTrace();
        }
        try {

            conn = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
//            System.out.println("链接数据库异常!");
            e.printStackTrace();
        }
        return conn;
    }
}
