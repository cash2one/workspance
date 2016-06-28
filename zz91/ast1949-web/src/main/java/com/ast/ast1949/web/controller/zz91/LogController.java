package com.ast.ast1949.web.controller.zz91;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.log.LogOperation;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.log.LogOperationService;
import com.ast.ast1949.web.controller.BaseController;

@Controller
public class LogController extends BaseController {
	@Resource
	private LogOperationService logOperationService;

	@RequestMapping
	public ModelAndView logOperationForBlacklist(HttpServletRequest request,
			Map<String, Object> out,Integer companyId,String isBlock,
			PageDto<LogOperation> page) throws IOException {
		page = logOperationService.pageLogOperationByTargetIdAndOperation(companyId, LogOperationService.BLACK_OPERATION, page);
		return printJson(page, out);
	}
}
