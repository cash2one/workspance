package com.nala.common.utils;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils extends org.apache.commons.lang.StringUtils {

    /**
     * 填充字符串,取右边N位
     * <p/>
     * <pre>
     * StringUtils.fill("1", "0", 2)           = "01"
     * </pre>
     *
     * @param ori
     * @param fill
     * @param length
     * @return
     */
    static public String fill(String ori, String fill, int length) {
        if (ori == null) return null;
        int len = ori.length();
        if (len >= length) {
            ori = org.apache.commons.lang.StringUtils.right(ori, length);
            return ori;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length - len; i++) {
            sb.append(fill);
        }
        sb.append(ori);
        return sb.toString();
    }

    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     *
     * @param c 需要判断的字符
     * @return 返回true, Ascill字符
     */
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }


    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param s 需要得到长度的字符串
     * @return i得到的字符串长度
     */
    public static int lengthgbk(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
     *
     * @param origin 原始字符串
     * @param len    截取长度(一个汉字长度按2算的)
     * @param c      后缀
     * @return 返回的字符串
     */
    public static String substringgbk(String origin, int len, String c) {
        if (origin == null || origin.equals("") || len < 1)
            return "";
        byte[] strByte = new byte[len];
        if (len > lengthgbk(origin)) {
            return origin + c;
        }
        try {
            System.arraycopy(origin.getBytes("GBK"), 0, strByte, 0, len);
            int count = 0;
            for (int i = 0; i < len; i++) {
                int value = (int) strByte[i];
                if (value < 0) {
                    count++;
                }
            }
            if (count % 2 != 0) {
                len = (len == 1) ? ++len : --len;
            }
            return new String(strByte, 0, len, "GBK") + c;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成UUID
     *
     * @return
     */
    public static String genUUID() {
        return UUID.randomUUID().toString();
    }


    /**
     * 获取二级域名
     *
     * @param domain
     * @return static public String getSLD(String domain){
     *         if(StringUtils.equalsIgnoreCase(domain, "www.tuandx.com")||StringUtils.equalsIgnoreCase(domain, "tuandx.com"))
     *         return null;
     *         Pattern pattern = Pattern.compile("(\\w+)?\\.?\\w+\\.com");
     *         Matcher matcher = pattern.matcher(domain);
     *         if(matcher.find()){
     *         return matcher.group(1);
     *         }else
     *         return null;
     *         }
     */

    public static Integer addOne(String i) {
        return Integer.parseInt(i) + 1;
    }

    /**
     * 处理请求字符串
     *
     * @param para
     * @param key
     * @param val
     * @return
     */
    public static String proPara(String url, String para, String key, Object val) {
        StringBuilder sb = new StringBuilder(url);
        String[] arr = para.split("&");
        for (String s : arr) {
            String[] onePara = s.split("=");
            if (onePara[0].equals(key)) {
                sb.append(key).append("=").append(val);
            } else {
                sb.append(s);
            }
            sb.append("&");
        }

        return sb.toString();
    }

    public static String genCodeNo() {
        Date date = new Date();
        return DateUtils.parseToShortFormat(date) + StringUtils.fill(StringUtils.genUUID(), "0", 14);
    }

    public static String genShortCodeNo() {
        return StringUtils.fill(StringUtils.genUUID(), "0", 7);
    }

    public static String genCodeNoByNum(Integer i) {
        return StringUtils.fill(StringUtils.genUUID(), "0", i);
    }

    /*
      * 汉字toUnicode
      */
    public static String toUNICODE(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) > 256) {
                sb.append("\\u").append(Integer.toHexString(s.charAt(i)));
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args){
         System.out.println(toUNICODE("邮箱"));
    }

    /**
     * 图片地址转换
     * eg : 将http://pic.yupoo.com/kauu/BjYMOwe0/h0ZUB.jpg转换成为http://img.nala.com.cn/itempic/kauu_BjYMOwe0_h0ZUB.jpg
     */
    public static String switchImgUrl(String url) {

        String[] url_array = url.split("/");

        String imgUrl = "";

        for (int i = url_array.length - 3; i < url_array.length; i++) {
            imgUrl += "_" + url_array[i];
        }

        return "http://img.nala.com.cn/itempic/" + imgUrl.substring(1, imgUrl.length());

    }

    /**
     * 检查所提供的String是否全是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0,1,2,3,4,5,6,7,8,9,.]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 过滤掉给定字符串中包含的特殊字符
     *
     * @param str
     * @return
     */
    public static String filterSpecialChar(String str) {
        if (str != null && str.indexOf("&") != -1) {
            str = str.replaceAll("&", " ");
        }

        return str;
    }

    /**
     * 过滤掉字符串中html语句
     */

    public static String htmltotext(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    /**
     * 返回传入字符串的最大长度
     * @param str
     * @param length
     * @return
     */
    public static String maxLength(String str, int length){
        if (str!=null && length > 0){            
            if (str.length() > length){
                str = str.substring(0, length) + "...";
            }
        }
        return str;
    }

}
