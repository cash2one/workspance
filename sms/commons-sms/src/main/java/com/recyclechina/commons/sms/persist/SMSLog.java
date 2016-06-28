/**
 * Project name: commons-sms
 * File name: SMSLog.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms.persist;

import com.recyclechina.commons.sms.common.SMSFlagEnum;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-3-21
 */
public interface SMSLog {
    /**
     * 记录短信发送的详细日志
     * 
     * @param mobiles
     * @param statId
     *            对应那次发送的统计ID
     * @param companyId
     *            对应公司的ID
     * @param flag
     *            标志日志来源.
     * @return 插入的条数
     */
    int recordLogs(String[] mobiles, int statId, Integer[] companyIds, SMSFlagEnum flag);
}
