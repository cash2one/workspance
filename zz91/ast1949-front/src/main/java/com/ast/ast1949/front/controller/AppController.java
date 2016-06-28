/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2013-3-5 上午10:39:18
 */
package com.ast.ast1949.front.controller;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.ExtResult;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.SensitiveUtils;
import com.zz91.util.lang.StringUtils;

/**
 * 敏感词过滤工具 需要服务器本地有敏感词词库文件 "/usr/data/keylimit/limit"
 * 
 * @author LeiTeng
 * @date 2013-03-7
 * 
 */
@Controller
public class AppController extends BaseController {
	@RequestMapping
	public ModelAndView doSentive(HttpServletResponse response,HttpServletRequest request,
		Map<String, Object> out,String pasString) throws Exception {
		//返回ajax数据结果
		ExtResult result = new ExtResult();
		//获取传递的参数,通过URLDecoder类的decode方法解决中文乱码问题
		pasString = URLDecoder.decode(pasString, HttpUtils.CHARSET_UTF8);
		//定义过滤后的字符串
		//String varifiedString =pasString;
		
		do {
			//判断参数如果为空,直接跳出循环
			if (StringUtils.isEmpty(pasString)) {
				break;
			}
			//对非空字符串进行替换
			pasString =SensitiveUtils.getSensitiveValue(pasString,"*");
			result.setData(pasString);
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}

}