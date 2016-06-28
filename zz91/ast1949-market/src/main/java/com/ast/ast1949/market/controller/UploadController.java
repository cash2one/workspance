/**
 * 
 */
package com.ast.ast1949.market.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CompanyUploadFileDO;
import com.ast.ast1949.domain.market.MarketPic;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyUploadFileService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.market.MarketPicService;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.file.ScaleImage;
import com.zz91.util.http.HttpUtils;

/**
 * @author mays (mays@zz91.net)
 * 
 *         created by 2011-12-21
 */
@Controller
public class UploadController extends BaseController {

	@Resource
	private CompanyUploadFileService companyUploadFileService;
	@Resource
	CompanyService companyService;
	@Resource
	CrmCompanySvrService crmCompanySvrService;
	@Resource
	private MarketPicService marketPicService;
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	final static String MODEL_CREDIT_FILE = "creditfile";
	final static String MODEL_COMPANY = "myrc";
	final static Integer LIMIT_ZST_PIC = 5;
	final static String LIMIT_ZST_PIC_INFO = "目前系统允许再生通会员一条信息只能上传"
			+ LIMIT_ZST_PIC + "张图片";
	final static String LIMIT_LDB_PIC_INFO = "目前系统允许来电宝会员一条信息只能上传"
			+ LIMIT_ZST_PIC + "张图片";
	final static Integer LIMIT_PT_PIC = 5;
	final static String LIMIT_PT_PIC_INFO = "目前系统允许普通会员一条信息只能上传"
			+ LIMIT_ZST_PIC + "张图片";

	@RequestMapping
	public ModelAndView market(HttpServletRequest request,
			Map<String, Object> out, Integer productId, String destUrl,
			String error, String name, Integer id) {
		if (error == null) {
			error = "";
		}
		if (name == null) {
			name = "";
		}
		try {
			out.put("error", URLDecoder.decode(error, HttpUtils.CHARSET_UTF8));
			out.put("name", URLDecoder.decode(name, HttpUtils.CHARSET_UTF8));
		} catch (UnsupportedEncodingException e) {
		}

		out.put("productId", productId);
		out.put("destUrl", destUrl);
		out.put("id", id);
		return null;
	}

	@RequestMapping
	public ModelAndView doMarket(HttpServletRequest request,
			Map<String, Object> out, String destUrl, Integer marketId,
			String name, Integer id) throws ParseException {
		if (marketId == null) {
			marketId = 0;
		}

		if (destUrl == null) {
			destUrl = "";
		}
		String finalname = null;
		String error = null;
		MarketPic pic = new MarketPic();
		do {
			// 图片上传 图片表更新
			try {
				String filename = UUID.randomUUID().toString();
				String path = MvcUpload
						.getModalPath("zz91admin");
				finalname = MvcUpload.localUpload(request, path, filename);

				// 改为缩略图
				ScaleImage is = new ScaleImage();
				is.saveImageAsJpg(path + "/" + finalname, path + "/"
						+ finalname, 800, 800);
				pic.setName(name);
				pic.setPicAddress(path.replace(MvcUpload.getDestRoot() + "/",
						"") + "/" + finalname);
				pic.setMarketId(marketId);
				pic.setPicSource(2);
				pic.setIsDefault("0");
				pic.setCheckStatus(0);
				id = marketPicService.insert(pic);
				pic.setId(id);
			} catch (Exception e) {
				error = MvcUpload.getErrorMessage(e.getMessage());
			}
		} while (false);

		if (error != null) {
			out.put("marketId", marketId);
			out.put("destUrl", destUrl);
			try {
				out.put("name", URLEncoder.encode(name, HttpUtils.CHARSET_UTF8));
				out.put("error",
						URLEncoder.encode(error, HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
			return new ModelAndView("redirect:market.htm");
		} else {
			out.put("success", "1");
			out.put("data",
					"{id:" + pic.getId() + ",picAddress:\""
							+ pic.getPicAddress() + "\"}");
			out.put("destUrl", destUrl);
			return null;
		}

	}

	@RequestMapping
	public ModelAndView company(HttpServletRequest request,
			Map<String, Object> out, String destUrl, String error, String remark) {
		if (error == null) {
			error = "";
		}
		if (remark == null) {
			remark = "";
		}
		try {
			out.put("error", URLDecoder.decode(error, HttpUtils.CHARSET_UTF8));
			out.put("name", URLDecoder.decode(remark, HttpUtils.CHARSET_UTF8));
		} catch (UnsupportedEncodingException e) {
		}

		out.put("destUrl", destUrl);
		return null;
	}

	@RequestMapping
	public ModelAndView doCompany(HttpServletRequest request,
			Map<String, Object> out, String destUrl, String remark) {
		if (destUrl == null) {
			destUrl = "";
		}

		String finalname = null;
		String error = null;
		CompanyUploadFileDO pic = new CompanyUploadFileDO();
		try {
			String filename = UUID.randomUUID().toString();
			String path = MODEL_COMPANY + "/" + MvcUpload.getDateFolder();
			finalname = MvcUpload.localUpload(request, MvcUpload.getDestRoot()
					+ "/" + path, filename);

			// 改为缩略图
			ScaleImage is = new ScaleImage();
			is.saveImageAsJpg(MvcUpload.getDestRoot() + "/" + path + "/"
					+ finalname, MvcUpload.getDestRoot() + "/" + path + "/"
					+ finalname, 800, 800);
			// 保存图片信息
			// String suffix=name.substring(finalname.lastIndexOf("."),
			// finalname.length());

			pic.setRemark(remark);
			pic.setFilename(finalname);
			pic.setFilepath(path + "/");
			pic.setCompanyId(getCachedUser(request).getCompanyId());
			pic.setFiletype(pic.getFilename().split("\\.")[1]);

			Integer id = companyUploadFileService.insertCompanyUploadFile(pic);

			pic.setId(id);
		} catch (Exception e) {
			error = MvcUpload.getErrorMessage(e.getMessage());
		}

		if (error != null) {
			out.put("destUrl", destUrl);
			try {
				out.put("error",
						URLEncoder.encode(error, HttpUtils.CHARSET_UTF8));
				out.put("remark",
						URLEncoder.encode(remark, HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
			return new ModelAndView("redirect:company.htm");
		} else {
			out.put("success", "1");
			out.put("data",
					"{id:" + pic.getId() + ",picAddress:\"" + pic.getFilepath()
							+ "/" + pic.getFilename() + "\"}");
			out.put("destUrl", destUrl);
			return null;
		}

	}

	@RequestMapping
	public ModelAndView creditFile(HttpServletRequest request,
			Map<String, Object> out, String destUrl, String error) {
		if (error == null) {
			error = "";
		}
		try {
			out.put("error", URLDecoder.decode(error, HttpUtils.CHARSET_UTF8));
		} catch (UnsupportedEncodingException e) {
		}

		out.put("destUrl", destUrl);
		return null;
	}

	@RequestMapping
	public ModelAndView doCreditFile(HttpServletRequest request,
			Map<String, Object> out, String destUrl) {
		if (destUrl == null) {
			destUrl = "";
		}

		String finalname = null;
		String error = null;
		String finalFilePath = "";
		// CompanyUploadFileDO pic=new CompanyUploadFileDO();
		try {
			String filename = UUID.randomUUID().toString();
			String path = MODEL_CREDIT_FILE + "/" + MvcUpload.getDateFolder();
			finalname = MvcUpload.localUpload(request, MvcUpload.getDestRoot()
					+ "/" + path, filename);

			finalFilePath = path + "/" + finalname;

			// 改为缩略图
			ScaleImage is = new ScaleImage();
			is.saveImageAsJpg(MvcUpload.getDestRoot() + "/" + path + "/"
					+ finalname, MvcUpload.getDestRoot() + "/" + path + "/"
					+ finalname, 800, 800);
			// 保存图片信息
			// String suffix=name.substring(finalname.lastIndexOf("."),
			// finalname.length());

			// pic.setFilename(finalname);
			// pic.setFilepath(path+"/");
			// pic.setCompanyId(getCachedUser(request).getCompanyId());
			// pic.setFiletype(pic.getFilename().split("\\.")[1]);
			//
			// Integer id=companyUploadFileService.insertCompanyUploadFile(pic);
			//
			// pic.setId(id);
		} catch (Exception e) {
			error = MvcUpload.getErrorMessage(e.getMessage());
		}

		if (error != null) {
			out.put("destUrl", destUrl);
			try {
				out.put("error",
						URLEncoder.encode(error, HttpUtils.CHARSET_UTF8));
				// out.put("remark", URLEncoder.encode(remark,
				// HttpUtils.CHARSET_UTF8));
			} catch (UnsupportedEncodingException e) {
			}
			return new ModelAndView("redirect:creditFile.htm");
		} else {
			out.put("success", "1");
			out.put("data", "{picAddress:\"" + finalFilePath + "\"}");
			out.put("destUrl", destUrl);
			return null;
		}

	}
}
