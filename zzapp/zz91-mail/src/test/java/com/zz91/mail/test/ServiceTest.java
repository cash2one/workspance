/**
 * Project name: zz91-mail
 * File name: ServiceTest.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-11-2
 */
public class ServiceTest {
    //测试模板检索模板内容
    public static void test() throws ClassNotFoundException, SQLException{
        final String url = "jdbc:mysql://192.168.2.10:3306/zzmail";  
        final String user = "root";  
        final String pwd = "zj88friend";  
        Connection cn = null;  
        Statement stm = null;  
        try  
        {  
            Class.forName("org.gjt.mm.mysql.Driver");  
            cn = DriverManager.getConnection(url, user, pwd);  
            stm = cn.createStatement();  
            final String sql = "select * from template where code = '1000'";  
            ResultSet rs = stm.executeQuery(sql);
            if(rs.next()){
                rs.getRow();
                rs.getString("id");
                String str = rs.getString("t_content");
                System.out.print("t_content:"+str);
            }
        }  
        finally  
        {  
            stm.close();  
            cn.close();  
        }  
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        test();
    }
}
