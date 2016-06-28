/**
 * Project name: commons-sms
 * File name: SMSApiImpl.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import cn.emay.sdk.client.api.Client;
import cn.emay.sdk.client.api.MO;
import cn.emay.sdk.client.api.StatusReport;

import com.recyclechina.commons.sms.SMSApi;
import com.recyclechina.commons.sms.SMSBlackList;
import com.recyclechina.commons.sms.common.DaemonThreadFactory;
import com.recyclechina.commons.sms.common.SMSFlagEnum;
import com.recyclechina.commons.sms.common.SMSSendReturnValueEnum;
import com.recyclechina.commons.sms.persist.impl.SMSLogImpl;
import com.recyclechina.commons.sms.persist.impl.SMSReplyImpl;
import com.recyclechina.commons.sms.persist.impl.SMSStatImpl;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-3-21
 */
public class SMSApiImpl implements SMSApi, Runnable {
    private static final Logger LOG = Logger.getLogger(SMSApiImpl.class);
    private Client client;
    private String password;
    private static int SMS_PRIORITY = 3;
    private static int BATCH_MAXIMUM = 200;
    private static int RETURN_VALUE_BLACKLIST = -1;
    private static int MOBILE_LENGTH = 11;
    // 最大值为500
    private static int MAX_CONTENT_LENGTH = 500;
    private static Object writeLock = new Object();
    private String[] mobiles;
    private String title;
    private String content;
    private Integer[] companyIds;
    private SMSFlagEnum flag;
    private String typeCode;
    private ThreadPoolExecutor threadPool;

    public SMSApiImpl(String softwareSerialNo, String password, String key) throws Exception {
        this.password = password;
        LOG.info("regist client:" + softwareSerialNo + " key:" + key + " password:" + password);
        client = new Client(softwareSerialNo, key);
        threadPool = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3), new DaemonThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public void sendSMS(String[] mobiles, String title, String content) {
        synchronized (writeLock) {
            if (content.length() > 0) {
                this.mobiles = mobiles;
                this.title = title;
                this.content = content;
                startup();
            }
        }
    }

    public void sendSMS(String[] mobiles, String title, String content, Integer[] companyIds, SMSFlagEnum flag) {
        synchronized (writeLock) {
            if (content.length() > 0) {
                this.mobiles = mobiles;
                this.title = title;
                this.content = content;
                this.companyIds = companyIds;
                this.flag = flag;
                startup();
            }
        }
    }

    // 国际站等其他网站通用api
    public void sendSMS(String[] mobiles, String title, String content, String typeCode) {
        synchronized (writeLock) {
            if (content.length() > 0) {
                this.mobiles = mobiles;
                this.title = title;
                this.content = content;
                this.typeCode = typeCode;
                startup();
            }
        }
    }

    public void sendSMS(String[] mobiles, String title, String content, Integer[] companyIds, SMSFlagEnum flag, String typeCode) {
        synchronized (writeLock) {
            if (content.length() > 0) {
                this.mobiles = mobiles;
                this.title = title;
                this.content = content;
                this.companyIds = companyIds;
                this.flag = flag;
                this.typeCode = typeCode;
                startup();
            }
        }
    }

    public String getBalance() {
    	String balance = "";
        try {
            balance = client.getBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balance;
    }

    public int register() {
        return client.registEx(password);
    }

    public int logout() {
        return client.logout();

    }

    public int updatePwd(String oldPwd, String newPwd) {
        return client.serialPwdUpd(oldPwd, newPwd);
    }

    public void run() {
        synchronized (writeLock) {
            LOG.debug("SMS threads " + Thread.currentThread().getName() + " Start...");
            // 发送不超过两个字符会显示通道签名,所以不超过时直接不于发送
            if (content != null && content.length() > 2) {
                String word = SMSBlackList.exists(content);
                if (word == null) {
                    int successCount = 0;
                    for (String mobile : mobiles) {
                        if (mobile.length() != MOBILE_LENGTH) {
                            return;
                        }
                    }
                    int batchNum = (mobiles.length + BATCH_MAXIMUM - 1) / BATCH_MAXIMUM;
                    for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
                        int beginIndex = currentBatch * BATCH_MAXIMUM;
                        int endIndex = (currentBatch + 1) * BATCH_MAXIMUM;
                        endIndex = endIndex > mobiles.length ? mobiles.length : endIndex;
                        String[] subMobiles = Arrays.copyOfRange(mobiles, beginIndex, endIndex);
                        int count = content.length() % MAX_CONTENT_LENGTH == 0 ? content.length() / MAX_CONTENT_LENGTH : content.length()
                                / MAX_CONTENT_LENGTH + 1;
                        for (int i = 0; i < count; i++) {
                            int len = Math.min(content.length(), MAX_CONTENT_LENGTH * (i + 1));
                            String currentContent = content.substring(MAX_CONTENT_LENGTH * i, len);
                            int result = client.sendSMS(subMobiles, currentContent, SMS_PRIORITY);
                            int statId = new SMSStatImpl().recordStat(title, currentContent, subMobiles.length, typeCode, result);
                            new SMSLogImpl().recordLogs(subMobiles, statId, companyIds, flag);
                            if (result == 0) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("mobiles=" + Arrays.toString(subMobiles) + ";content：" + currentContent);
                                }
                                successCount = successCount + subMobiles.length;
                            } else {
                                LOG.error("error!，return:" + result + ",cause" + SMSSendReturnValueEnum.getEnumByReturnValue(result)
                                        + ";mobiles=" + subMobiles[0] + "content：" + currentContent);
                            }
                        }
                    }
                    LOG.debug("Total of " + successCount + " SMS messages sent");
                } else {
                    int statId = new SMSStatImpl().recordStat(title, content, mobiles.length, typeCode, RETURN_VALUE_BLACKLIST);
                    new SMSLogImpl().recordLogs(mobiles, statId, companyIds, flag);
                    LOG.error("Word of sensitive" + word + ".content:" + content);
                }
            } else {
                return;
            }
        }
    }

    private void startup() {
        threadPool.execute(this);
    }

    public List<StatusReport> getReport() {
        List<StatusReport> list = Collections.emptyList();
        try {
            list = client.getReport();
            if (list == null) {
                list = Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<MO> getReplyMessages() {
        List<MO> list = Collections.emptyList();
        try {
            list = client.getMO();
            if (list == null) {
                list = Collections.emptyList();
            } else {
                new SMSReplyImpl().saveReply(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
