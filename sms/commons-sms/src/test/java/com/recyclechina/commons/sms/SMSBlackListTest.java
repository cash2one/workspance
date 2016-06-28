/**
 * Project name: commons-sms
 * File name: SMSBlackListTest.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-3-23
 */
public class SMSBlackListTest {

    @Test
    public void testExists() {
        String content = "短信内容不能有移动";
        Assert.assertEquals(SMSBlackList.exists(content), "移动");
        content = "沪市提醒：即日起沪市短信报价以上海期货交易量最大产品合约10年07月16日到11年07月15日为准（例：沪铜即沪铜1107，沪铝即沪铝1107），以后报价合约名变更我们将以短信的形式提醒您，祝您生意兴隆【ZZ91再生网】";
        Assert.assertNull(SMSBlackList.exists(content));
    }

}
