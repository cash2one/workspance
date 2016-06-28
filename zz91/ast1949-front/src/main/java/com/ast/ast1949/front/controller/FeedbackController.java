package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.site.FeedbackDo;
import com.ast.ast1949.service.site.FeedbackService;

@Controller
public class FeedbackController extends BaseController{
	
	@Resource
	private FeedbackService feedbackService;
	
	@RequestMapping
	public ModelAndView doFeedBack(Map<String, Object> out, String name,String tel,String content)
		throws IOException{
		FeedbackDo feedbackDo=new FeedbackDo();
		do{
			if(name==null || name.equals("")){
				break;
			}
			if(tel==null || tel.equals("")){
				break;
			}
			feedbackDo.setCompanyId(0);
			feedbackDo.setAccount("");
			feedbackDo.setEmail("");
			feedbackDo.setCategory(4);
			String common=name+","+tel;
			if(common.length()>100){
				common = common.substring(0, 100);
			}
			feedbackDo.setTitle(common);
			feedbackDo.setContent(content);
			feedbackService.insertFeedbackByMember(feedbackDo);
		}while(false);
		return new ModelAndView("redirect:http://subject.zz91.com/pvb/msgcallback/");
	}
}
