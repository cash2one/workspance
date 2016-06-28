/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-13 上午11:05:58
 */
package com.ast.ast1949.price.filters;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import com.ast.ast1949.util.AlgorithmUtils;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.RemoteFileUtil;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.param.ParamUtils;

/**
 *
 * @author Ryan(rxm1025@gmail.com)
 * updated mays@zz91.net 2010-10-08
 */
public class ConvertToHtmlFilter implements Filter {
	
	private Map<String, Long> fileCacheMap;
	private Map<String, String> fileCacheSubfolderMap;
	private Map<String, Long> memoryCacheMap;
	private RemoteFileUtil remoteFileUtil;
	private Map<String, String> paramMap;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		String fileCache = filterConfig.getInitParameter("fileCache");
		String memoryCache = filterConfig.getInitParameter("memoryCache");
		
		String[] tmpFile = fileCache.split(",");
		String[] tmpMemory = memoryCache.split(",");
		
		fileCacheMap = new HashMap<String, Long>();
		memoryCacheMap = new HashMap<String, Long>();
		fileCacheSubfolderMap = new HashMap<String, String>();
		
		for(String s:tmpFile){
			String[] tmp = s.split("\\|");
			fileCacheMap.put(tmp[0].replaceAll("\\s*|\t|\r|\n",""), Long.valueOf(tmp[1].replaceAll("\\s*|\t|\r|\n","")));
			if(tmp.length>2){
				fileCacheSubfolderMap.put(tmp[0].replaceAll("\\s*|\t|\r|\n",""), tmp[2].replaceAll("\\s*|\t|\r|\n",""));
			}
		}
		
		for(String s:tmpMemory){
			memoryCacheMap.put(s.split("\\|")[0].replaceAll("\\s*|\t|\r|\n",""),
					Long.valueOf(s.split("\\|")[1].replaceAll("\\s*|\t|\r|\n","")));
		}
	}

	public void destroy() {
	}
	
	public void doFilter(ServletRequest rq, ServletResponse rp, FilterChain chain)
			throws IOException, ServletException {
		//判断哪些URL需要生成静态页面
		//判断静态页面是否存在且未过期；如果存在，同时更新过期时间
		//直接读取静态页面或重新生成静态页面
		HttpServletRequest request= (HttpServletRequest) rq;
		HttpServletResponse response = (HttpServletResponse) rp;
		
		if(paramMap==null){
			paramMap = ParamUtils.getInstance().getChild("upload_config");
		}
		
		String servletPath = request.getServletPath();
		
		String absoluteFilePath = "";

		do{
			Long exp = memoryCacheMap.get(servletPath);
			if(exp!=null && exp.longValue()>0){ //需要使用级存
				absoluteFilePath = createPath(request, servletPath);
				String cachedString = (String) MemcachedUtils.getInstance().getClient().get(absoluteFilePath);
				if(cachedString==null){ //数据没有被缓存
					ByteArrayOutputStream os = bos(request, response, servletPath);
					try {
						MemcachedUtils.getInstance().getClient().set(absoluteFilePath, Integer.valueOf(exp+""), os.toString("utf-8"));
					} catch (UnsupportedEncodingException e) {
					}
					cachedString = os.toString("utf-8");
					os.close();
				}
				// TODO 读取输出
				printwrite(response, cachedString);
				break;
			}
			
			exp = fileCacheMap.get(servletPath);
			if(exp!=null && exp.longValue()>0){ //需要使用静态文件
				
				if(remoteFileUtil==null){
					remoteFileUtil = new RemoteFileUtil(paramMap.get("remote_host_ip"), 
							paramMap.get("remote_account"), 
							paramMap.get("remote_password"), 
							paramMap.get("remote_share_folder"));
				}
				absoluteFilePath = createPath(request, servletPath);
				remoteFileUtil.writeFolder(absoluteFilePath.substring(0,absoluteFilePath.lastIndexOf("/")));
				SmbFile file = remoteFileUtil.smbFile(absoluteFilePath);
				long now = System.currentTimeMillis();
				if(!file.exists() || (now-file.getLastModified())>exp){ //数据没有缓存或已过期
					ByteArrayOutputStream os = bos(request, response, servletPath);
					os.writeTo(file.getOutputStream());
					printwrite(response, os.toString("utf-8"));
					os.close();
					break;
				}
				// TODO 读取输出
//				List<String> fileList = remoteFileUtil.readFile(absoluteFilePath);
				StringBuffer sb = new StringBuffer();
//				for(String s:fileList){
//					sb.append(s).append("\n");
//				}
				InputStreamReader isr = null;
				BufferedReader reader = null;
				try {
					isr = new InputStreamReader(new SmbFileInputStream(file));
					reader = new BufferedReader(isr);
					String line = null;
					line = reader.readLine();
					while (line != null) {
		                sb.append(line).append("\n");
		                line = reader.readLine();
		            }
				}finally{
					if(isr!=null){
						isr.close();
					}
					if(reader!=null){
						reader.close();
					}
				}
				
				printwrite(response, sb.toString());
				break;
			}
			chain.doFilter(request, response);
		}while(false);
		
		return ;
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
	
	private ByteArrayOutputStream bos(HttpServletRequest request, HttpServletResponse response, String url){
		RequestDispatcher rd = request.getRequestDispatcher(url);
		final ByteArrayOutputStream os=new ByteArrayOutputStream();
		final ServletOutputStream stream = new ServletOutputStream() {
			public void write(byte[] data, int offset, int length){
				os.write(data,offset,length);
			}
			
			public void write(int b) throws IOException {
				os.write(b);
			}
		};
		
		final PrintWriter pw =new PrintWriter(new OutputStreamWriter(os));
		HttpServletResponse rp = new HttpServletResponseWrapper(response){
			public ServletOutputStream getOutputStream(){
				return stream;
			}
			
			public PrintWriter getWriter(){
				return pw;
			}
		};
		try {
			rd.include(request, rp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			pw.flush();
		} catch (Exception e) {
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
		return os;
	}
	
	private void printwrite(HttpServletResponse response, String s){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(s);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
	}
	
	final static int SUB_FOLDER_SPERATE = 10000;
	private String createSubFolder(HttpServletRequest request, String servletPath){
		String paramName = fileCacheSubfolderMap.get(servletPath);
		if(StringUtils.isEmpty(paramName)){
			return "";
		}
		paramName = request.getParameter(paramName);
		String subfolder = "";
		if(paramName!=null){
			if(StringUtils.isNumber(paramName)){
				subfolder = "/" + (Integer.valueOf(paramName) / SUB_FOLDER_SPERATE);
			}else{
				subfolder = "/" + paramName.substring(0,1);
			}
		}
		return subfolder;
	}
	
	private String createPath(HttpServletRequest request, String servletPath){
		String htmlRootFolder = paramMap.get("html_folder");
		String folderName = trimSuffix(servletPath);
		
		Map<String, String[]> params = request.getParameterMap();
		StringBuffer psb = new StringBuffer();
		for(String s:params.keySet()){
			for(String sv:params.get(s)){
				psb.append(s).append("=").append(sv).append(",");
			}
		}
		
		String htmlPath = null;
		if(psb.length()>0){
			try {
				htmlPath = folderName.substring(0, folderName.lastIndexOf("/")) +
					createSubFolder(request, servletPath) + 
					folderName.substring(folderName.lastIndexOf("/"),folderName.length()) +
					AlgorithmUtils.MD5(psb.toString(), AlgorithmUtils.LENGTH) + 
					AstConst.HTML_SUFFIX;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else {
			htmlPath = folderName + AstConst.HTML_SUFFIX;
		}
		return htmlRootFolder + htmlPath;
	}
	
}
