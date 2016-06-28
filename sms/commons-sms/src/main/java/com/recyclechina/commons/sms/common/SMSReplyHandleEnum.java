/**
 * Project name: commons-sms
 * File name: SMSReplyHandleEnum.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms.common;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-5-5
 */
public enum SMSReplyHandleEnum {
    UNHANDLE(0, "未处理"), NO_ANSWER(1, "已处理电话未打通"), OPENED(2, "已处理开通试用"), NOT_OPENED(4, "已处理不要试用"), OTHERS(3, "其它");
    private int id;
    private String message;

    private SMSReplyHandleEnum(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return message;
    }
}
