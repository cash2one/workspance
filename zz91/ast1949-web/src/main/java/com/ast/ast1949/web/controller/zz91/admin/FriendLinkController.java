/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-1 by yuyonghui.
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.site.FriendLinkDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.site.FriendLinkDTO;
import com.ast.ast1949.service.site.FriendLinkService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.DateUtil;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.cache.MemcachedUtils;

/**
 * @author yuyonghui
 *
 */
@Controller
public class FriendLinkController extends BaseController {

	@Autowired
	private FriendLinkService friendLinkService;

	@RequestMapping(value = "list.htm")
	public void FriendLinkList(Map<String, Object> map) {
		map.put("resourceUrl", MemcachedUtils.getInstance().getClient().get("baseConfig.resource_url"));
	}

	@RequestMapping(value = "query.htm")
	public ModelAndView query(FriendLinkDTO friendLinkDTO, Integer showIndex,
			PageDto page, Map<String, Object> out) throws IOException {
		if (page == null) {
			page = new PageDto(AstConst.ADMIN_PAGE_SIZE);
		}
		friendLinkDTO.setPageDto(page);

		friendLinkDTO.setShowIndex(showIndex);
		page.setTotalRecords(friendLinkService
				.getFriendLinkRecordCountByCondition(friendLinkDTO));
		page.setRecords(friendLinkService
				.queryFriendLinkByCondition(friendLinkDTO));
		return printJson(page, out);

	}

	@RequestMapping(value = "init.htm")
	public ModelAndView init(Integer id, Map<String, Object> out)
			throws IOException {
		out.put("resourceUrl", MemcachedUtils.getInstance().getClient().get("baseConfig.resource_url"));
		FriendLinkDO friendLinkDO = friendLinkService.queryFriendLinkById(id);
		List<FriendLinkDO> list = new ArrayList<FriendLinkDO>();
		list.add(friendLinkDO);

		PageDto page = new PageDto();
		page.setRecords(list);
		return printJson(page, out);

	}

	@RequestMapping(value = "update.htm")
	public ModelAndView update(FriendLinkDO friendLinkDO, String myaddTime,
			Map<String, Object> out) throws IOException, ParseException {
		ExtResult result = new ExtResult();
		if (myaddTime != null) {
			friendLinkDO.setAddTime(DateUtil.getDate(myaddTime, "yyyy-mm-dd"));
		}

		friendLinkDO.setAddTime(new Date());
		friendLinkDO.setGmtModified(new Date());
		int i = friendLinkService.updateFriendLink(friendLinkDO);
		if (i > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}

	@RequestMapping(value = "add.htm")
	public ModelAndView add(FriendLinkDO friendLinkDO, String myaddTime,
			Map<String, Object> out) throws IOException, ParseException {

//		if (myaddTime != null) {
//			friendLinkDO.setAddTime(DateUtil.getDate(myaddTime, "yyyy-mm-dd"));
//		}
		ExtResult result = new ExtResult();
		friendLinkDO.setAddTime(new Date());
		friendLinkDO.setGmtCreated(new Date());
		friendLinkDO.setGmtModified(new Date());
		int i = friendLinkService.insertFriendLink(friendLinkDO);
		if (i > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, out);
	}

	@RequestMapping(value = "delete.htm")
	public ModelAndView delete(String ids, Map<String, Object> out)
			throws IOException {

		ExtResult result = new ExtResult();
		String[] entities = ids.split(",");
		int[] i = new int[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		int impacted = friendLinkService.batchDeleteFriendLinkById(i);
		if (impacted != 1) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
		}

		return printJson(result, out);
	}
	@RequestMapping(value = "checked.htm")
	public ModelAndView checked(String ids, Map<String, Object> out)
			throws IOException {

		ExtResult result = new ExtResult();
		String[] entities = ids.split(",");
		int[] i = new int[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		int impacted = friendLinkService.batchCheckedFriendLinkById(i);
		if (impacted != 1) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
		}

		return printJson(result, out);
	}
	@RequestMapping(value = "cancelChecked.htm")
	public ModelAndView cancelChecked(String ids, Map<String, Object> out)
			throws IOException {

		ExtResult result = new ExtResult();
		String[] entities = ids.split(",");
		int[] i = new int[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		int impacted = friendLinkService.batchCancelCheckedFriendLinkById(i);
		if (impacted != 1) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
		}

		return printJson(result, out);
	}
}
