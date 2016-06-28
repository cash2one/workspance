/**
 * Project name: commons-sms
 * File name: SMSSendReturnValueEnum.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms.common;


/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-4-12
 */
public enum SMSSendReturnValueEnum {
    BLACKLIST(-1,"有敏感词"),//非第三方API返回的值，自定义的标志
    SUCCESS(0, "成功"),
    FAIL(17,"失败"),
    ERROR(101,"客户端网络故障"),
    NET_ERROR(303,"客户端网络故障"),
    RETURN_ERROR(305,"服务器端返回错误，错误的返回值（返回值不是数字字符串）"),
    FORMAT_ERROR(307,"目标电话号码不符合规则，电话号码必须是以0、1开头"),
    TIMEOUT(997,"平台返回找不到超时的短信，该信息是否成功无法确定"),
    NETWORK_ERROR(998,"由于客户端网络问题导致信息发送超时，该信息是否成功下发无法确定");
    
    private int returnValue;
    private String description;

    private SMSSendReturnValueEnum(int returnValue, String description) {
        this.returnValue = returnValue;
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public int getReturnValue() {
        return returnValue;
    }

    public static SMSSendReturnValueEnum getEnumByReturnValue(int returnValue) {
        for (SMSSendReturnValueEnum r : SMSSendReturnValueEnum.values()) {
            if (r.getReturnValue() == returnValue) {
                return r;
            }
        }
        return null;
    }
}
