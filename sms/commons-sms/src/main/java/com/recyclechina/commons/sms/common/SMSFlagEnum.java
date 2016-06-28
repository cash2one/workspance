/**
 * Project name: commons-sms
 * File name: SMSFlagEmum.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms.common;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-3-25
 */
public enum SMSFlagEnum {
    ZZ91(0), RECYCLE(1);
    private int flag;

    private SMSFlagEnum(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }
}
