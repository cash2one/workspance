/**
 * Project name: commons-sms
 * File name: SMSStatImpl.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms.persist.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.recyclechina.commons.sms.persist.DBUtils;
import com.recyclechina.commons.sms.persist.SMSStat;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-3-23
 */
public class SMSStatImpl implements SMSStat {

    public int recordStat(String title, String content, int totalCount, String typeCode, int returnValue) {
        int id = 0;
        Connection conn = DBUtils.getConnection();
        try {
            String sql = "insert into sms_stat(title,content,total_count,type_code,return_value,number,gmt_created) VALUES(?,?,?,?,?,?,now())";
            PreparedStatement sm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            sm.setString(1, title);
            sm.setString(2, content);
            sm.setInt(3, totalCount);
            if (typeCode == null) {
                sm.setNull(4, java.sql.Types.VARCHAR);
            } else {
                sm.setString(4, typeCode);
            }
            sm.setInt(5, returnValue);
            int i;
            if (content.length() % 67 == 0) {
                i = content.length() / 67;
            } else {
                i = content.length() / 67 + 1;
            }
            sm.setInt(6, i);
            sm.executeUpdate();
            ResultSet rs = sm.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            sm.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 预防性关闭连接（避免异常发生时在try语句块关闭连接没有执行）
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
//                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return id;
    }
}
