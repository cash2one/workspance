package com.ast.ast1949.paychannel.exception;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Assertion utility.
 */
public abstract class Assert {
	private static void throwException(BizException e) throws BizException {
		throw e;
	}

	private static void throwException(BizException e, String log) throws BizException {
		throw e;
	}

	public static void isTrue(boolean expression, String message) throws BizException {
		isTrue(expression, null, message);
	}

	public static void isTrue(boolean expression, String errorCode, String message) throws BizException {
		if (!expression) {
			throwException(new BizException(errorCode, message));
		}
	}

	public static void isTrue(boolean expression, String errorCode, String message, String log) throws BizException {
		if (!expression) {
			throwException(new BizException(errorCode, message), log);
		}
	}

	public static void notTrue(boolean expression, String message) throws BizException {
		notTrue(expression, null, message);
	}

	public static void notTrue(boolean expression, String errorCode, String message) throws BizException {
		if (expression) {
			throwException(new BizException(errorCode, message));
		}
	}

	public static void isNull(Object object, String message) throws BizException {
		if (object != null) {
			throwException(new BizException(message));
		}
	}

	public static void notNull(Object object, String message) throws BizException {
		if (object == null) {
			throwException(new BizException(message));
		}
	}

	public static void notNull(Object object, String errorCode, String message, String log) throws BizException {
		if (object == null) {
			throwException(new BizException(errorCode, message), log);
		}
	}

	public static void notNull(Object object, String errorCode, String message) throws BizException {
		if (object == null) {
			throwException(new BizException(errorCode, message));
		}
	}

	public static void isEmpty(String data, String message) throws BizException {
		if (!StringUtils.isEmpty(data)) {
			throwException(new BizException(message));
		}
	}

	public static void notEmpty(String data, String message) throws BizException {
		if (StringUtils.isEmpty(data)) {
			throwException(new BizException(message));
		}
	}

	public static void notEmpty(String data, String errorCode, String message) throws BizException {
		if (StringUtils.isEmpty(data)) {
			throwException(new BizException(errorCode, message));
		}
	}

	public static void isEmpty(Collection data, String message) throws BizException {
		if (!CollectionUtils.isEmpty(data)) {
			throwException(new BizException(message));
		}
	}

	public static void notEmpty(Collection data, String message) throws BizException {
		if (CollectionUtils.isEmpty(data)) {
			throwException(new BizException(message));
		}
	}

	public static void notEmpty(Collection data, String errorCode, String message) throws BizException {
		if (CollectionUtils.isEmpty(data)) {
			throwException(new BizException(errorCode, message));
		}
	}

	public static void notEmpty(Object[] data, String message) throws BizException {
		if (ArrayUtils.isEmpty(data)) {
			throwException(new BizException(message));
		}
	}

	public static void notEmpty(Object[] data, String errorCode, String message) throws BizException {
		if (ArrayUtils.isEmpty(data)) {
			throwException(new BizException(errorCode, message));
		}
	}

	public static void equals(String s1, String s2, String message) throws BizException {
		if (!StringUtils.equals(s1, s2)) {
			throwException(new BizException(message));
		}
	}

	public static void equals(String s1, String s2, String message, String log) throws BizException {
		if (!StringUtils.equals(s1, s2)) {
			throwException(new BizException(message), log);
		}
	}

	public static void notEquals(String s1, String s2, String message) throws BizException {
		if (StringUtils.equals(s1, s2)) {
			throwException(new BizException(message));
		}
	}

	public static void checkLength(String data, String errorCode, String message, int maxLen) throws BizException {
		if (data != null && data.getBytes().length > maxLen) {
			throwException(new BizException(errorCode, message + "的内容[" + data + "]超过" + maxLen + "的最大限制"));
		}
	}

	public static void checkLength(String data, String errorCode, String message, int minLen, int maxLen) throws BizException {
		if (data != null && (data.getBytes().length > maxLen || data.getBytes().length < minLen)) {
			throwException(new BizException(errorCode, message + "的内容[" + data + "]长度应该在[" + minLen + "," + maxLen + "]之间"));
		}
	}

	// 检查是否数字串
	public static void checkNChar(String data, String errorCode, String message) throws BizException {
		if (data != null) {
			Pattern pattern = Pattern.compile("\\d*");
			Matcher isNum = pattern.matcher(data);
			if (!isNum.matches()) {
				throwException(new BizException(errorCode, message + "的内容[" + data + "]包含不合法的数字"));
			}
		}
	}

	/*
	 * x-字符集由以下81个半角字符组成 a b c d e f g h i j k l m n o p q r s t u v w x y z A B
	 * C D E F G H I J K L M N O P Q R S T U V W X Y Z 0 1 2 3 4 5 6 7 8 9 . , -
	 * ( ) / = + ? ! % & * < > ; @ #
	 */
	public static void checkXChar(String data, String errorCode, String message) throws BizException {
		if (data != null) {
			Pattern pattern = Pattern.compile("(\\w|\\s|#|\\.|,|-|\\(|\\)|/|=|\\+|\\?|!|%|\\*|&|<|>|;|@)*");
			Matcher isNum = pattern.matcher(data);
			if (!isNum.matches()) {
				throwException(new BizException(errorCode, message + "的内容[" + data + "]包含不合法的字符"));
			}
		}
	}

	public static void checkContain(String data, List list, String errorCode, String message) throws BizException {
		if (data != null) {
			if (!list.contains(data)) {
				throwException(new BizException(errorCode, message + "的内容[" + data + "]不在正常值范围"));
			}
		}
	}

	// 检查是否是日期格式
	public static void checkDateString(String data, String errorCode, String message) throws BizException {
		if (StringUtils.isEmpty(data)) {
			return;
		}
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			format.setLenient(false);
			format.parse(data);
		} catch (Exception ex) {
			throwException(new BizException(errorCode, message + "的内容[" + data + "]日期格式错误"));
		}
	}

	// 检查是否是日期+时间格式
	public static void checkDateTimeString(String data, String errorCode, String message) throws BizException {
		if (StringUtils.isEmpty(data)) {
			return;
		}
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			format.setLenient(false);
			format.parse(data);
		} catch (Exception ex) {
			throwException(new BizException(errorCode, message + "的内容[" + data + "]日期时间格式错误"));
		}
	}

	// 检查是否是时间格式
	public static void checkTimeString(String data, String errorCode, String message) throws BizException {
		if (StringUtils.isEmpty(data)) {
			return;
		}
		try {
			DateFormat format = new SimpleDateFormat("HHmmss");
			format.setLenient(false);
			format.parse(data);
		} catch (Exception ex) {
			throwException(new BizException(errorCode, message + "的内容[" + data + "]时间格式错误"));
		}
	}

	public static boolean hasChinese(String s) {
		if (StringUtils.isEmpty(s)) {
			return false;
		}

		return s.getBytes().length != s.length();
	}

	// aps-zmc 增加 为了XML 文本报文的字段检查
	// charSet="N"数字 "X"数字/字母 "G"数字/字母/汉字 "D"8位日期 "T"6位时间 "DT"14位日期时间
	// option="O" "M"
	// 先检查option,再int minLen,int maxLen,最后是charSet
	public static void checkData(String fieldValue, String fieldName, int minLen, int maxLen, String charSet, String option)
			throws BizException {
		String fieldDesc = "[" + fieldName + "]域";
		// 检查必须字段
		if (option.equals("O")) {
			if (StringUtils.isEmpty(fieldValue))
				return;
		}
		if (option.equals("M") && minLen > 0) {
			if (StringUtils.isEmpty(fieldValue))
				throw new BizException("9303", fieldDesc + "是必选字段");
		}

		// 检查字段长度
		if (fieldValue.getBytes().length < minLen || fieldValue.getBytes().length > maxLen)
			throw new BizException("9303", fieldDesc + "的长度范围应该在[" + minLen + "," + maxLen + "]之间");

		// 检查类型
		if (charSet.equals("N"))
			Assert.checkNChar(fieldValue, "9303", fieldDesc);
		else if (charSet.equals("X"))
			Assert.checkXChar(fieldValue, "9303", fieldDesc);
		else if (charSet.equals("D"))
			Assert.checkDateString(fieldValue, "9303", fieldDesc);
		else if (charSet.equals("T"))
			Assert.checkTimeString(fieldValue, "9303", fieldDesc);
		else if (charSet.equals("DT"))
			Assert.checkDateTimeString(fieldValue, "9303", fieldDesc);

		return;
	}

	// 机票平台检查金额格式
	public static void checkMoney(String fieldValue, String errorCode, String message, int bitNum, int maxLen) throws BizException {
		fieldValue = fieldValue.trim();

		if (fieldValue.indexOf('.') >= 0) {
			int len = fieldValue.substring(fieldValue.indexOf('.') + 1).length();
			if (len > bitNum) {
				throw new BizException(errorCode, message + "的内容[" + fieldValue + "]小数位至多为" + bitNum + "位");
			}
			if (fieldValue.substring(0, fieldValue.indexOf('.')).length() > maxLen) {
				throw new BizException(errorCode, message + "的内容[" + fieldValue + "]整数位最大长度为" + maxLen + "位");
			}

		} else {
			if (fieldValue.length() > maxLen) {
				throw new BizException(errorCode, message + "的内容[" + fieldValue + "]整数位最大长度为" + maxLen + "位");
			}
		}

	}
}
