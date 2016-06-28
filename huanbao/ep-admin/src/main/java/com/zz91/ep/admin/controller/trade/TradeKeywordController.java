/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-8-24 下午06:50:37
 */
package com.zz91.ep.admin.controller.trade;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.dto.ExtResult;

public class TradeKeywordController extends BaseController {

	@RequestMapping
	public ModelAndView createTradeKeyword(Map<String, Object> out,HttpServletRequest request ){
		ExtResult result = new ExtResult();
		
		return printJson(result, out);
	}
}
