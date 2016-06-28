/**
 * @author shiqp
 * @date 2016-01-18
 */
package com.ast.feiliao91.www.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.auth.SsoUser;
import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.service.goods.PictureService;
import com.zz91.util.file.MvcUpload;

@Controller
public class UploadController extends BaseController {
	@Resource
	private PictureService pictureService;

	@RequestMapping
	public ModelAndView judgeUpload(HttpServletRequest request,
			Map<String, Object> out) throws IOException {
		String targetType = request.getParameter("targetType"); // 检测报告1 产品图片2
																// 评价图片3
		SsoUser user = getCachedUser(request);
		Map<String, String> map = new HashMap<String, String>();
		String filename = UUID.randomUUID().toString();
		String path = MvcUpload.getModalPath("feiliao91");
		String finalname = "";
		try {
			finalname = MvcUpload.localUpload(request, path, filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 保存在数据库
		Picture picture = new Picture();
		picture.setCompanyId(user.getCompanyId());
		String picAddress = "/feiliao91/" + MvcUpload.getDateFolder();
		picture.setPicAddress(picAddress + "/" + finalname);
		picture.setTargetType(targetType);
		picture.setTargetId(0);
		Integer i =pictureService.createPicture(picture);
		map.put("url", "/feiliao91/" + MvcUpload.getDateFolder() + "/"
				+ finalname);
		map.put("id", i.toString());
		return printJson(map, out);
	}

}
