/**
 * Project name: commons-sms
 * File name: SMSLogImpl.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms.persist.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.recyclechina.commons.sms.common.SMSFlagEnum;
import com.recyclechina.commons.sms.persist.DBUtils;
import com.recyclechina.commons.sms.persist.SMSLog;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-3-22
 */
public class SMSLogImpl implements SMSLog {

    public int recordLogs(String[] mobiles, int statId, Integer[] companyIds, SMSFlagEnum flag) {
        int i = 0;
        Connection conn = DBUtils.getConnection();
        try {
            conn.setAutoCommit(false);
            String sql = "INSERT into sms_log(mobile,stat_id,company_id,flag,gmt_created) VALUES(?,?,?,?,now())";
            PreparedStatement prest = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (String mobile : mobiles) {
                prest.setString(1, mobile);
                prest.setInt(2, statId);
                if (companyIds == null) {
                    prest.setNull(3, java.sql.Types.INTEGER);
                } else {
                    prest.setInt(3, companyIds[i]);
                }
                if (flag == null) {
                    prest.setNull(4, java.sql.Types.INTEGER);
                } else {
                    prest.setInt(4, flag.getFlag());
                }

                prest.addBatch();
                i++;
            }
            prest.executeBatch();
            conn.commit();
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
        return i;
    }

}
