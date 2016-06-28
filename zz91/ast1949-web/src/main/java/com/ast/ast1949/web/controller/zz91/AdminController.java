/**
 *	Modify By Rolyer 2010.03.23
 *	说明：提取时间格式化字符到AstConst.java中
 */
package com.ast.ast1949.web.controller.zz91;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.util.DateUtil;
import com.ast.ast1949.util.FileUtils;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.ast.ast1949.web.util.WebConst;
import com.zz91.util.auth.AuthMenu;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.cache.MemcachedUtils;

@Controller
public class AdminController extends BaseController{

	private String[] allowFileType = { ".jpg", ".jpeg", ".gif", ".bmp", ".png" }; // 白名单

	/**
	 * 登录首页
	 *
	 * @param out
	 */
	@RequestMapping
	public void index(Map<String, Object> out, HttpServletRequest request) {
		out.put("username", getCachedUser(request).getAccount());
	}


	/**
	 * 后台登录界面
	 * @throws IOException 
	 */
	@RequestMapping(value = "login.htm", method = RequestMethod.GET)
	public void login(Map<String, Object> out, String url) throws IOException {
		out.put("url", url);
	}

	@RequestMapping
	public void noauth(){

	}

	/**
	 * 后台登录提交
	 * @throws IOException
	 */
	@RequestMapping(value = "login.htm", method = RequestMethod.POST)
	public ModelAndView login(Map<String, Object> out, HttpServletRequest request, HttpServletResponse response, String account, String password) throws IOException {
		SessionUser sessionUser = AuthUtils.getInstance().validateUser(response, account, password, WebConst.PROJECT_CODE, WebConst.PROJECT_PASSWORD);
		ExtResult result = new ExtResult();
		if(sessionUser!=null){
			setSessionUser(request, sessionUser);
			result.setSuccess(true);
		}else{
			result.setData("用户名或者密码写错了，检查下大小写是否都正确了，再试一次吧 :)");
		}
		return printJson(result, out);
	}

	@RequestMapping
	public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		AuthUtils.getInstance().logout(request, response, null);
		cleanCachedSession(request);
		response.sendRedirect(request.getContextPath()+"/zz91/admin/login.htm");
	}

//	@RequestMapping
//	public ModelAndView changePassword(String pwd, String newpwd, String newpwd2,
//			HttpServletRequest request, Map<String, Object> model) throws IOException{
//		ExtResult result=new ExtResult();
//		AuthUser user = getCachedAuthUser(request);
//		do {
//			if(user==null){
//				result.setData("用户账户不存在!");
//				break;
//			}
//
////			try {
////				authService.changePassword(user.getUsername(), pwd, newpwd, newpwd2);
////				result.setSuccess(true);
////			} catch (AuthorizeException e) {
////				result.setData(e.getMessage());
////			} catch (NoSuchAlgorithmException e) {
////				e.printStackTrace();
////			}
//
//		} while (false);
//		return printJson(result, model);
//	}

	/**
	 *
	 */
	@RequestMapping
	public void welcome() {

	}

	@RequestMapping
	public ModelAndView mymenu(Map<String, Object> out, HttpServletRequest request, String parentCode) throws IOException{
		if(parentCode==null){
			parentCode="";
		}
		SessionUser sessionUser = getCachedUser(request);
		List<AuthMenu> list = AuthUtils.getInstance().queryMenuByParent(parentCode, WebConst.PROJECT_CODE, sessionUser.getAccount());
		return printJson(list, out);
	}

	/**
	 * 上传图片:初始化页面
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "simpleAjaxUpload.htm", method = RequestMethod.GET)
	public ModelAndView simpleAjaxUpload(Map<String, Object> out, String id) {
		if (StringUtils.isEmpty(id)) {
			id = "0";
		}
		out.put("id", id);
		return null;
	}

	/**
	 * 上传图片（由jQuery+Ajax实现） 注：在使用页面
	 *
	 * @param out
	 * @param request
	 * @param response
	 * @param id
	 *            控件的id值
	 * @param uploadModel
	 *            文件所属模块,这个值将影响到文件按的存放目录，默认值：default<br/>
	 *            在使用页面请设置方法：<br/>
	 *            ①在你所有使用的页面的Controller中 输入如下代码：model.put("uploadFileType",
	 *            AstConst.UPLOAD_FILETYPE_IMG);<br/>
	 *            ②iframe的属性 src=
	 *            "$!{address.server}/simpleAjaxUpload.htm?id=upfile&uploadModel=$!{uploadModel}"
	 *            id="upfile" <br/>
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "simpleAjaxUpload.htm", method = RequestMethod.POST)
	public ModelAndView simpleAjaxUpload(Map<String, Object> out, HttpServletRequest request,
			HttpServletResponse response, String id, String uploadModel,String uploadProject) throws Exception {

		MultipartHttpServletRequest mhs = (MultipartHttpServletRequest) request;
		// 从表单中获取文件
		MultipartFile file = mhs.getFile("file" + id);
		// 创建临时目录
		String uploadFolder = (String) MemcachedUtils.getInstance().getClient().get(
				"baseConfig.upload_folder");
		if (StringUtils.isEmpty(uploadModel)) {
			uploadModel = "default";
		}
		if(StringUtils.isEmpty(uploadProject)){
			uploadProject="front";
		}
		String path = uploadFolder + "/" + uploadProject + "/" + uploadModel;
		FileUtils.makedir(path);

		try {
			// 判断文件是否为空
			if (file == null || file.isEmpty() || file.getSize() <= 0) {
				//return printJs("parent.uploaderror('" + id + "','上传失败：文件为空。');", out);
				return printJs("alert('上传失败:请选择要上传的文件。');history.go(-1);", out);
			}
			// 判断文件大小
			if (file.getSize() > 10000000) {
				//return printJs("parent.uploaderror('" + id + "','上传失败：文件大小不能超过10M。');", out);
				return printJs("alert('上传失败：文件大小不能超过10M。');history.go(-1);", out);
			}

			String filename = file.getOriginalFilename();// 获取文件的原始名称和后缀。
			String fileType = "";
			fileType = filename.substring(filename.lastIndexOf("."));// 截取扩展名

			if (!StringUtils.isEmpty(fileType)) {
				boolean result = false;
				String[] confirmType = allowFileType; // 白名单
				// 判断文件是否合法
				for (int temp = 0; temp < confirmType.length; temp++) {
					if (fileType.equalsIgnoreCase(confirmType[temp])) {
						result = true;
					}
				}
				if (result) {
					// 上传合法文件
					try {
						// 重命名（以上传时间命名）
						String time = DateUtil.toString(new Date(), "yyyyMMddHHmmssSSS");
						String newname = time + fileType;// UUID.randomUUID().toString()
						// 保存文件
						FileUtils.SaveFileFromInputStream(file.getInputStream(), path, newname);

//						return printJs("parent.setSimpleAjaxUploadValue('" + newname
//								+ "');parent.uploadsuccess('" + id + "','" + newname + "');", out);
						return printJs("parent.ajaxUpImgPath('"+newname+"')",out);//("alert('"+newname+"')", out);
					} catch (IOException e) {
						// 上传失败
//						return printJs("parent.uploaderror('" + id + "','上传失败：出异常了！');", out);
						return printJs("alert('上传失败：出异常了！');history.go(-1);", out);
					}
				} else {
					// 文件类型错误（扩展名）
//					return printJs("parent.uploaderror('" + id + "','上传失败：文件类型错误。');", out);
					return printJs("alert('上传失败：文件类型错误。');history.go(-1);", out);
				}
			} else {
				// 文件类型错误（扩展名）
//				return printJs("parent.uploaderror('" + id + "','上传失败：文件类型错误。');", out);
				return printJs("alert('上传失败：文件类型错误。');history.go(-1);", out);
			}
		} catch (Exception ex) {
			// 出现异常
//			return printJs("parent.uploaderror('" + id + "','上传失败：出异常了！');", out);
			return printJs("alert('上传失败：出异常了！');history.go(-1);", out);
		}
	}
}
