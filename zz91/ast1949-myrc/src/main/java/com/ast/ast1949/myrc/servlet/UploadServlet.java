package com.ast.ast1949.myrc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.zz91.util.domain.UploadDto;
import com.zz91.util.domain.UploadResult;
import com.zz91.util.file.Upload;
import com.zz91.util.param.ParamUtils;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger LOG = Logger.getLogger(UploadServlet.class);

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
		
			config.setTmpFolder(Upload.DEFAULT_TMP_FOLDER);
			config.setRootFolder(Upload.DEFAULT_ROOT_FOLDER);
			config.setUploadFolder(getModel(request)+"/"+Upload.getInstance().getDateFolder());
			config.setAllowFileType(
					String.valueOf(paramMap.get("allow_filetype_"+getAllowFiletype(request))).split(","));
			config.setSizeMax(Integer.valueOf(paramMap.get("max_size_web")));
			
			List<UploadResult> list = Upload.getInstance().upload(request, config);
			result.setSuccess(true);
			result.setData(list);
		} catch (FileUploadException e) {
			LOG.error("there is an error occur when upload file. e:"+e.getMessage());
		}

		PrintWriter pw = response.getWriter();
		pw.write(JSONObject.fromObject(result).toString());
		pw.close();
	}

	private String getModel(HttpServletRequest request){
		if(request.getParameter("model")==null || "".equals(request.getParameter("model"))){
			return "tmp";
		}
		return request.getParameter("model");
	}
	
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
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		URLDecoder.decode("%2C", "utf-8");
	}
	
}
