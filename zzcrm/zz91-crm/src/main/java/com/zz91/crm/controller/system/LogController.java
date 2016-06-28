package com.zz91.crm.controller.system;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.zz91.crm.controller.BaseController;
import com.zz91.crm.domain.SysLog;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.service.SysLogService;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-1-12 
 */
@Controller
public class LogController extends BaseController{
	
	@Resource
	private SysLogService sysLogService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out,HttpServletRequest request,Integer id){
		out.put("id", id);
		return null;
	}
	
	@RequestMapping
	public ModelAndView querySysLog(Map<String, Object> out,
			HttpServletRequest request, Integer targetId, String saleName,String operation,PageDto<SysLog> page) {
		page = sysLogService.pageByTargetId(targetId,saleName,operation, page);
		return printJson(page, out);
	}
}
