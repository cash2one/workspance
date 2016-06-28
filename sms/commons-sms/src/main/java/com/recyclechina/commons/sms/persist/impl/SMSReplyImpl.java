/**
 * Project name: commons-sms
 * File name: SMSReplyImpl.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms.persist.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.emay.sdk.client.api.MO;

import com.recyclechina.commons.sms.persist.DBUtils;
import com.recyclechina.commons.sms.persist.SMSReply;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-4-12
 */
public class SMSReplyImpl implements SMSReply {
    private static final DateFormat dtLong = new SimpleDateFormat("yyyyMMddHHmmSS");

    public int saveReply(List<MO> moList) {
        int i = 0;
        Connection conn = DBUtils.getConnection();
        try {
            conn.setAutoCommit(false);
            String sql = "INSERT into sms_reply(mobile,content,gmt_send,add_serial,add_serial_rev,channel_number,gmt_created,gmt_modified,reply_handle_id) "
                    + "VALUES(?,?,?,?,?,?,now(),now(),0)";
            PreparedStatement prest = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (MO mo : moList) {
                prest.setString(1, mo.getMobileNumber());
                prest.setString(2, mo.getSmsContent());
                Timestamp gmtSend = new Timestamp(dtLong.parse(mo.getSentTime()).getTime());
                prest.setTimestamp(3, gmtSend);
                prest.setString(4, mo.getAddSerial());
                prest.setString(5, mo.getAddSerialRev());
                prest.setString(6, mo.getChannelnumber());
                prest.addBatch();
                i++;
            }
            prest.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            // 预防性关闭连接（避免异常发生时在try语句块关闭连接没有执行）
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

}
