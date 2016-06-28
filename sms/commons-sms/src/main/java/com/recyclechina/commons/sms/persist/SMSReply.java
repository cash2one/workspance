/**
 * Project name: commons-sms
 * File name: SMSReply.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms.persist;

import java.util.List;

import cn.emay.sdk.client.api.MO;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-4-12
 */
public interface SMSReply {
    int saveReply(List<MO> moList);
}
