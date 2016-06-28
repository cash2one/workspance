/**
 * Project name: commons-sms
 * File name: SMSStat.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms.persist;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-3-23
 */
public interface SMSStat {
    /**
     * 记录短信的发送统计
     * 
     * @param title
     *            　标题，方便搜索记录用
     * @param content
     * @param totalCount
     * @return 插入的主键
     */
    int recordStat(String title, String content, int totalCount, String typeCode, int returnValue);
}
