package com.ast.ast1949.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.UploadDto;
import com.ast.ast1949.dto.UploadResult;
import com.ast.ast1949.util.Upload;
import com.ast.ast1949.web.controller.zz91.AdminController;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.param.ParamUtils;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger LOG = Logger.getLogger(AdminController.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.info("can not upload file from get method...");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		ExtResult result = new ExtResult();
		try {
			
			UploadDto config = new UploadDto();
			Map<String, String> paramMap = ParamUtils.getInstance().getChild("upload_config");
			
//			config.setTmpFolder(paramMap.get("temp_folder"));
			config.setTmpFolder(String.valueOf(MemcachedUtils.getInstance().getClient().get("baseConfig.upload_folder_temp")));
			config.setRootFolder(paramMap.get("upload_folder"));
			config.setUploadFolder(getModel(request)+"/"+Upload.getInstance().getDateFolder());
			config.setAllowFileType(
					String.valueOf(paramMap.get("allow_filetype_"+getAllowFiletype(request))).split(","));
			config.setSizeMax(Integer.valueOf(paramMap.get("max_size_web")));
			config.setRemoteHostIp(paramMap.get("remote_host_ip"));
			config.setAccount(paramMap.get("remote_account"));
			config.setPassword(paramMap.get("remote_password"));
			config.setShareDocName(paramMap.get("remote_share_folder"));
			
			List<UploadResult> list = Upload.getInstance().upload(request, config);
			result.setSuccess(true);
			result.setData(list);
			
//			u.setTmpFolder(String.valueOf(MemcachedFacade.getInstance().get("baseConfig.upload_folder_temp")));
//			u.setUploadFolder(getUploadFolder(request));
//			u.setSizeMax(getSizeMax());
//			u.setAllowFileType(getAllowUpload(request));
			
//			List<String> uploadFile=u.upload(request);
//			if(uploadFile.size()>0){
//				result.setSuccess(true);
//				result.setData(uploadFile);
//			}
		} catch (FileUploadException e) {
			LOG.error("there is an error occur when upload file. e:"+e.getMessage());
		}

		PrintWriter pw = response.getWriter();
		pw.write(JSONObject.fromObject(result).toString());
		pw.close();
	}

//	private String getUploadFolder(HttpServletRequest request){
//		if("false".equals(request.getParameter("usetype"))){
//			return String.valueOf(MemcachedFacade.getInstance().get("baseConfig.upload_folder"))
//				+"/"+getModel(request)+"/";
//		}
//		return String.valueOf(MemcachedFacade.getInstance().get("baseConfig.upload_folder"))
//			+"/"+getModel(request)+"/"+getFiletype(request)+"/";
//	}

	private String getModel(HttpServletRequest request){
		if(request.getParameter("model")==null || "".equals(request.getParameter("model"))){
			return "tmp";
		}
		return request.getParameter("model");
	}
	
//	private String getDateFolder(){
//		Calendar now = Calendar.getInstance();
//		return now.get(Calendar.YEAR)+"/"+(now.get(Calendar.MONTH)+1)+"/"+now.get(Calendar.DAY_OF_MONTH);
//	}

//	private String getFiletype(HttpServletRequest request){
//		if(request.getParameter("filetype")==null || "".equals(request.getParameter("filetype"))){
//			return "doc";
//		}
//		return request.getParameter("filetype");
//	}

	/**
	 * 支持的文件类型：doc,img,zip,video
	 * @param request
	 * @return
	 */
	private String getAllowFiletype(HttpServletRequest request){
		String filetype = request.getParameter("filetype");
		if(filetype==null || "".equals(request.getParameter("filetype"))){
			filetype = "doc";
		}
		return filetype;
	}

//	private int getSizeMax(){
//		Integer i = Integer.valueOf(String.valueOf(MemcachedFacade.getInstance().get("baseConfig.upload_size_max")));
//		return i.intValue()*1024*1024;
//	}


}
