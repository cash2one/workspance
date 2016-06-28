/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-12 上午10:35:42
 */
package com.ast.ast1949.util;

import java.io.File;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class XmlUtils {
	/**
	 * doc2XmlFile 将Document对象保存为一个xml文件到本地
	 *
	 * @return true:保存成功 flase:失败
	 * @param filename
	 *            保存的文件名
	 * @param document
	 *            需要保存的document对象
	 */
	public static boolean doc2XmlFile(Document document, String filename) {
		boolean flag = true;
		try {
			/* 将document中的内容写入文件中 */
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			MakeDir(filename);
			XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)), format);
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * string2XmlFile 将xml格式的字符串保存为本地文件，如果字符串格式不符合xml规则，则返回失败
	 *
	 * @return true:保存成功 flase:失败
	 * @param filename
	 *            保存的文件名
	 * @param str
	 *            需要保存的字符串
	 */
	public static boolean string2XmlFile(String str, String filename) {
		boolean flag = true;
		try {
			Document doc = DocumentHelper.parseText(str);
			flag = doc2XmlFile(doc, filename);
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	private static void MakeDir(String fileName) {
		Integer i = fileName.lastIndexOf("/");
		String dir = fileName.substring(0, i);
		File f = new File(dir);
		if (!f.exists()) {
			f.mkdirs();
		}
	}

}
