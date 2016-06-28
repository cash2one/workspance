/**
 * Project name: commons-sms
 * File name: SMSBlackList.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms;

import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-3-23
 */
public class SMSBlackList {
    private static Logger LOG = Logger.getLogger(SMSBlackList.class);

    /**
     * 判断内容里是否有敏感词
     * <ul>
     * <li>如果有，则返回该敏感词</li>
     * <li>如果无，则返回null</li>
     * </ul>
     * 
     * @param content
     * @return
     */
    public static String exists(String content) {
        if (content != null && content.length() > 0) {
            try {
                InputStream is = SMSBlackList.class.getClassLoader().getResourceAsStream("blacklist20110225.xls");
                Workbook book = Workbook.getWorkbook(is);
                // 获得第一个工作表对象
                Sheet sheet = book.getSheet(0);
                // 得到单元格
                for (int j = 0; j < sheet.getRows(); j++) {
                    Cell cell = sheet.getCell(0, j);
                    String word = cell.getContents();
                    if (content.indexOf(word) < 0) {
                        continue;
                    } else {
                        LOG.info("内容有敏感词:" + word);
                        return word;
                    }
                }
                book.close();
            } catch (Exception e) {
                LOG.error(e);
            }
        }
        return null;
    }
}
