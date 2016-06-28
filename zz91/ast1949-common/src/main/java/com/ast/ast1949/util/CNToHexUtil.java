package com.ast.ast1949.util;

import java.io.ByteArrayOutputStream;

/**
 * 中文转码
 * 
 * @author sj
 * 
 */
public class CNToHexUtil {
	private static String hexString = "0123456789ABCDEFabcdef";

	private static CNToHexUtil _intance;

	public synchronized static CNToHexUtil getInstance() {
		if (_intance == null) {
			return new CNToHexUtil();
		}
		return _intance;
	}

	// private static String hexString = "0123456789abcdefABCDEF";

	public String encode(String msg) {
		byte[] bytes = msg.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 转换hex编码
		for (byte b : bytes) {
			sb.append(Integer.toHexString(b + 0x800).substring(1));
		}

		// 转换后的代码为c7d7a3acc4e3bac3
		return sb.toString();
	}

	// 把hex编码转换为string

	public String decode(String bytes) {
		bytes = bytes.toUpperCase();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CNToHexUtil a = new CNToHexUtil();
		String msg = a.encode("废金属");
		System.out.println(msg);
		String res = a.encode(msg);
		System.out.println(res);

		// 再次转换为string
		System.out.println(a.decode("e5ba9fe992a2"));
	}
}
