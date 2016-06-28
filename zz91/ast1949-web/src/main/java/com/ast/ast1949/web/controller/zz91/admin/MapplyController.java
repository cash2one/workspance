/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-7-16
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.MemberApplyDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.MemberApplyDTO;
import com.ast.ast1949.service.company.MemberApplyService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.SessionUser;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Controller
public class MapplyController extends BaseController {
	
	@Autowired
	private MemberApplyService memberApplyService;
	
	@RequestMapping
	public void view(Map<String, Object> out) {
		
	}
	
	/**
	 * 获取列表
	 * @param model
	 * @param page
	 * @param bbsPost
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView list(Map<String, Object> model,PageDto page,MemberApplyDO memberApply) throws IOException{
		if(page == null){
			page = new PageDto(AstConst.PAGE_SIZE);
		}
		page.setSort("id");
		page.setDir("desc");
		MemberApplyDTO memberApplyDTO =new MemberApplyDTO();
		
		memberApplyDTO.setPage(page);
		memberApplyDTO.setMemberApply(memberApply);
		
		page.setTotalRecords(memberApplyService.countMemberApplyList(memberApplyDTO));
		page.setRecords(memberApplyService.queryMemberApplyList(memberApplyDTO));

		return printJson(page, model);
	}
	
	/**
	 * 更新审核状态
	 * @param model
	 * @param id
	 * @param checkStatus
	 * @param unpassReason
	 * @param checkPerson
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="updateCheckStatus.htm",method = RequestMethod.POST)
	public ModelAndView updateCheckStatus(HttpServletRequest request,Map<String, Object> model, 
			String ids,String processStatus,String remark) throws IOException{
		ExtResult result= new ExtResult();
		
//		AdminUserDO user=getCachedAccount(request);
		SessionUser sessionUser = getCachedUser(request);
		
		String[] entities=ids.split(",");

		int impacted=0;
		for(int ii=0;ii<entities.length;ii++){
			if(StringUtils.isNumber(entities[ii])){
				Integer im=memberApplyService.updateProcessStatusById(processStatus, sessionUser.getAccount(), remark, Integer.valueOf(entities[ii]));//.updateBbsPostReplyCheckStatusById(Integer.valueOf(entities[ii]), checkStatus, unpassReason, user.getUsername());
				if(im.intValue()>0){
					impacted++;
				}
			}
		}

		if(impacted!=entities.length) {
			result.setSuccess(false);
		}else{
			result.setSuccess(true);
		}

		result.setData(impacted);

		return printJson(result, model);
	}
}
