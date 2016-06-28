/**
 * Project name: commons-sms
 * File name: SMSApi.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms;

import java.util.List;

import cn.emay.sdk.client.api.MO;
import cn.emay.sdk.client.api.StatusReport;

import com.recyclechina.commons.sms.common.SMSFlagEnum;

/**
 * 短信使用接口
 * 
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-3-21
 */
public interface SMSApi {
    /**
     * 批量发送短信
     * <p>
     * smsPriority默认为3，并且同时保存日志
     * </p>
     * 
     * @param mobiles
     * @param title
     *            起到一个标注的作用，方便查询
     * @param content
     */
    void sendSMS(String[] mobiles, String title, String content);

    /**
     * 批量发送短信
     * 
     * @param mobiles
     * @param title
     * @param content
     * @param companyIds
     *            对应的公司ID
     * @param flag
     */
    void sendSMS(String[] mobiles, String title, String content, Integer[] companyIds, SMSFlagEnum flag);

    /**
     * 批量发送短信
     * 
     * @param mobiles
     *            号码
     * @param title
     *            标题
     * @param content
     *            内容
     * @param typeCode
     *            发送的类型
     */
    void sendSMS(String[] mobiles, String title, String content, String typeCode);
    
    /**
     * 批量发送短信
     * 
     * @param mobiles
     * @param title
     * @param content
     * @param companyIds
     *            对应的公司ID
     * @param typeCode
     *            发送的类型
     * @param flag
     */
    void sendSMS(String[] mobiles, String title, String content, Integer[] companyIds, SMSFlagEnum flag, String typeCode);

    /**
     * 查询余额
     * 
     * @return
     */
    String getBalance();

    /**
     * 注册激活，激活只在第一次使用时执行
     * 
     * @param password
     * @return
     */
    int register();

    /**
     * 注销
     */
    int logout();

    /**
     * 修改密码
     * 
     * @param oldPwd
     * @param newPwd
     * @return
     */
    int updatePwd(String oldPwd, String newPwd);

    /**
     * 返回状态报告。不过现在还不能使用，需要独享通道才有此功能
     * 
     * @return 如果无报告或抛异常，则返回空列表
     */
    List<StatusReport> getReport();

    /**
     * 从EUCP平台接收手机用户上行的短信.如果有，则持久化
     * 
     * @return 如果平台暂时还没有获取到上行信息供客户查询，则返回空列表
     */
    List<MO> getReplyMessages();
}
