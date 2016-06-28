/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-1 下午07:11:20
 */
package com.ast.ast1949.util;

/**
 * 常用的一些转化工具方法
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class ConvertUtils {
	/**
	 * 将字符串数组转化为整型数组
	 *
	 * @param stringArray
	 *            字符串数组
	 * @return 转化后的整型数组
	 * @throws NumberFormatException
	 *             转化失败,则抛出转换异常
	 */
	public static Integer[] stringArrayToIntegerArray(String[] stringArray)
			throws NumberFormatException {
		Integer[] integerArray = new Integer[stringArray.length];
		for (Integer i = 0; i < stringArray.length; i++) {
			integerArray[i] = Integer.valueOf(stringArray[i]);
		}
		return integerArray;
	}
}
