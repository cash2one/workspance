/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-13 上午11:05:58
 */
package com.ast.ast1949.bbs.filters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.ast.ast1949.util.AstConst;

/**
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class ConvertToHtmlFilter implements Filter {

	private String url = "";
	private String expiredSeconds = "";
	private String realRootPath = "";
	private List<String> urlList = new ArrayList<String>();
	private List<String> expiredSecondsList = new ArrayList<String>();
	Integer matchIndex = null;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String servletPath = req.getServletPath(); // 以"/"开头
		String noSuffixFileName = trimSuffix(servletPath);
		String folderName = noSuffixFileName;
		if (noSuffixFileName.equals("/price/priceDetails")) {
			Integer id = Integer.valueOf(req.getParameter("id"));
			String subFolder = String.valueOf((id / 1000));
			folderName = "/price/" + subFolder + "/" + id;
		}
		String htmlPath = folderName + AstConst.HTML_SUFFIX;
		String absoluteFilePath = realRootPath + htmlPath;

		// 判断是否需要生成静态文本
		if (isNeededToConvert(servletPath)) {
			if (isExistedHtmlAndNotExpired(absoluteFilePath)) {
				req.getRequestDispatcher("/" + htmlPath).forward(request, response);

			} else {
				req.getRequestDispatcher(
						"/ConvertToHtml?noSuffixFileName=" + noSuffixFileName + "&folderName=" + folderName
								).forward(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		realRootPath = filterConfig.getServletContext().getRealPath("/");

		url = filterConfig.getInitParameter("url");
		String[] tmp = url.split("\\|");
		for (String s : tmp) {
			urlList.add(s);
		}

		expiredSeconds = filterConfig.getInitParameter("expiredSeconds");
		String[] tmpSeconds = expiredSeconds.split("\\|");
		for (String s : tmpSeconds) {
			expiredSecondsList.add(s);
		}
	}

	/**
	 * 是否已经存在静态文本,并且没有过期
	 *
	 * @param fileName
	 * @return
	 */
	private boolean isExistedHtmlAndNotExpired(String absoluteFilePath) {
		File file = new File(absoluteFilePath);
		if (file.exists()) {
			if (isExpired(file)) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 *
	 * @param file
	 * @return
	 */
	private Boolean isExpired(File file) {
		// 如果静态文本和web服务器不在同一台机器，则存在时间不同步的风险
		Date lastModifiedTime = new Date(file.lastModified());
		// String s=file.getAbsolutePath();
		Date currentTime = new Date();
		Long between = (currentTime.getTime() - lastModifiedTime.getTime()) / 1000;
		Integer expired = 1000;
		if (matchIndex < expiredSecondsList.size()) {
			expired = Integer.valueOf(expiredSecondsList.get(matchIndex));
		}
		if (between < expired) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 去除扩展名
	 *
	 * @param requestUri
	 * @return
	 */
	private String trimSuffix(String requestUri) {
		if (requestUri.endsWith(".htm")) {
			requestUri = requestUri.substring(0, requestUri.length() - 4);
		}
		return requestUri;
	}

	/**
	 * 判断是否需要生成静态文件< /br> 并将匹配正则的索引值获得，以取得对应的失效时间
	 *
	 * @param uri
	 * @return
	 */
	private Boolean isNeededToConvert(String uri) {
		Integer i = 0;
		for (String regex : urlList) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(uri);
			if (m.find()) {
				matchIndex = i;
				return true;
			}
			i++;
		}
		return false;
	}
}
