package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.download.DownloadInfo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.download.DownloadInfoService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.site.CategoryService;
import com.ast.ast1949.web.controller.BaseController;
import com.ast.ast1949.web.thread.PdfToSwfThread;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.lang.StringUtils;

/**
 * author:kongsj date:2013-6-9
 */
@Controller
public class XiazaiController extends BaseController {

	@Resource
	private DownloadInfoService downloadInfoService;

	@Resource
	private CategoryService categoryService;

	@RequestMapping
	public void list(Map<String, Object> out) {

	}

	@RequestMapping
	public ModelAndView queryList(Map<String, Object> out,
			DownloadInfo downloadInfo, PageDto<DownloadInfo> page)
			throws IOException {
		page = downloadInfoService.pageList(downloadInfo, page);
		for (DownloadInfo obj : page.getRecords()) {
			obj.setCode(CategoryFacade.getInstance().getValue(obj.getCode()));
		}
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView init(Map<String, Object> out, Integer id,String copy)
			throws IOException {
		do {
			DownloadInfo downloadInfo = downloadInfoService.queryById(id);
			if (downloadInfo == null) {
				break;
			}
			downloadInfo.setLabel(CategoryFacade.getInstance().getValue(
					downloadInfo.getCode()));
			List<DownloadInfo> list = new ArrayList<DownloadInfo>();
			if("1".equals(copy)){
				downloadInfo.setId(0);
			}
			list.add(downloadInfo);
			PageDto<DownloadInfo> page = new PageDto<DownloadInfo>();
			page.setRecords(list);
			return printJson(page, out);
		} while (false);
		return null;
	}

	@RequestMapping
	public ModelAndView child(Map<String, Object> model, String code)
			throws IOException {
		if (StringUtils.isEmpty(code)) {
			code = CategoryService.DOWNLOAD_CENTER;
		}
		List<ExtTreeDto> list = categoryService.child(code);

		return printJson(list, model);
	}

	@RequestMapping
	public void add(Map<String, Object> model, String code, Integer id,Integer cid) {
		do {
			if (id != null) {
				model.put("id", id);
				DownloadInfo obj = downloadInfoService.queryById(id);
				model.put("code", obj.getCode());
				model.put("label", CategoryFacade.getInstance().getValue(obj.getCode()));
				break;
			}
			
			if(cid !=null){
				model.put("cid",cid);
				DownloadInfo obj = downloadInfoService.queryById(cid);
				model.put("code", obj.getCode());
				model.put("label", CategoryFacade.getInstance().getValue(obj.getCode()));
				break;
			}

			if (StringUtils.isNotEmpty(code)) {
				model.put("code", code);
				model.put("label", CategoryFacade.getInstance().getValue(code));
			}
		} while (false);
	}

	@RequestMapping
	public ModelAndView doAdd(HttpServletRequest request,
			Map<String, Object> out, DownloadInfo downloadInfo)
			throws IOException {
		ExtResult result = new ExtResult();
		// 创建
		do {
			if (downloadInfo.getId() != null&&downloadInfo.getId()!=0) {
				break;
			}
			SessionUser user = getCachedUser(request);
			if (user == null) {
				break;
			}
			downloadInfo.setCreatedBy(user.getAccount());
			Integer i = downloadInfoService.insert(downloadInfo);
			if (i > 0) {
				result.setSuccess(true);
			}
		} while (false);
		// 修改
		do {
			// 文件是否更换过
			DownloadInfo obj = downloadInfoService.queryById(downloadInfo.getId());
			if(obj!=null&&StringUtils.isNotEmpty(obj.getFileUrl())&&!obj.getFileUrl().equals(downloadInfo.getFileUrl())){
				PdfToSwfThread.addfile(obj.getFileUrl());
			}
			Integer i = downloadInfoService.update(downloadInfo);
			if (i > 0) {
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView doDel(Map<String, Object> model, Integer id)
			throws IOException {
		ExtResult result = new ExtResult();
		do {
			if (id == null) {
				break;
			}
			Integer i = downloadInfoService.deleteById(id);
			if (i > 0) {
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView doSwf(Map<String, Object> model, Integer id)
			throws IOException {
		ExtResult result = new ExtResult();
		do {
			if (id == null) {
				break;
			}
			result.setSuccess(downloadInfoService.pdfToSwf(id));
		} while (false);
		return printJson(result, model);
	}

}