/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-12 上午09:58:40
 */
package com.ast.ast1949.front.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.ast.ast1949.util.AstConst;
import com.zz91.util.cache.MemcachedUtils;

/**
 * 将页面转换为静态的HTML
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class ConvertToHtmlServlet extends HttpServlet {

	/**
	 * serialVersionUIDlong
	 */
	private static final long serialVersionUID = -4475419178912667236L;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		ServletContext sc = getServletContext();

		String noSuffixFileName = request.getParameter("noSuffixFileName");
		String folderName = request.getParameter("folderName");
		String url = noSuffixFileName + ".htm";// 需要生成页面的文件名
		String htmlPath = folderName + AstConst.HTML_SUFFIX;
		String realRootPath = MemcachedUtils.getInstance().getClient().get(
				"baseConfig.html_folder").toString();
		String absoluteFilePath = realRootPath + htmlPath;
		makeDir(absoluteFilePath);
		RequestDispatcher rd = sc.getRequestDispatcher(url);

		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		final ServletOutputStream stream = new ServletOutputStream() {
			public void write(byte[] data, int offset, int length) {
				os.write(data, offset, length);
			}

			public void write(int b) throws IOException {
				os.write(b);
			}
		};

		final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
		HttpServletResponse rep = new HttpServletResponseWrapper(response) {
			public ServletOutputStream getOutputStream() {
				return stream;
			}

			public PrintWriter getWriter() {
				return pw;
			}
		};
		try {
			rd.include(request, rep);
		} catch (ServletException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		pw.flush();
		String s = os.toString();
		FileOutputStream fos = new FileOutputStream(absoluteFilePath);
		try {
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			out.write(s);
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter pw2;
		try {
			pw2 = response.getWriter();
			pw2.write(s);
			pw2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// request.getRequestDispatcher(absoluteFilePath).forward(request,
		// response);
	}

	/**
	 *
	 * @param fileName
	 */
	private void makeDir(String fileName) {
		Integer i = fileName.lastIndexOf("/");
		String dir = fileName.substring(0, i);
		File f = new File(dir);
		if (!f.exists()) {
			f.mkdirs();
		}
	}
}
