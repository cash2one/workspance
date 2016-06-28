package com.ast.ast1949.front.controller.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.dataindex.DataIndexService;

@Controller
public class DataIndexController extends BaseController {

	@Resource
	private DataIndexService dataIndexService;
	
	@RequestMapping
	public ModelAndView indexByCode(HttpServletRequest request, Map<String, Object> out,
			String code, Integer size)throws IOException{
		if(size!=null && size.intValue()>200){
			size=200;
		}
		List<DataIndexDO> list=dataIndexService.queryDataByCode(code, null, size);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list", list);
		return printJson(map, out);
	}
}
