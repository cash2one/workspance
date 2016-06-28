/**
 * Project name: reborn-common
 * File name: DeamonThreadFactory.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms.common;

import java.util.concurrent.ThreadFactory;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-5-5
 */
public class DaemonThreadFactory implements ThreadFactory {

    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }

}
