package com.ast.ast1949.front.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.other.SubjectBaomingService;
import com.zz91.util.seo.SeoUtil;

@Controller
public class ServiceController extends BaseController{
	@Resource
	private SubjectBaomingService subjectBaomingService;

	@RequestMapping
	public ModelAndView zxsq(Map<String, Object> map,String index) {
		SeoUtil.getInstance().buildSeo("reqhuiyuan", map);
		//设置默认主页
		if(index==""||index==null){
			index="1";
		}
		map.put("index", index);
		return null;
	}

	@RequestMapping
	public ModelAndView ppgg(Map<String, Object> map) {
		SeoUtil.getInstance().buildSeo("guanggao", map);
		return null;
	}

	@RequestMapping
	public ModelAndView hyfw(Map<String, Object> map, HttpServletRequest req) {
		SeoUtil.getInstance().buildSeo("huiyuan", map);
		HttpSession session = req.getSession();
		String ss = (String) session.getAttribute("id");
		if (ss == "" || ss == null) {
			session.setAttribute("id", "0");
		}
		return new ModelAndView("/service/hyfw");
	}

	@RequestMapping
	public ModelAndView index(Map<String, Object> map) {
		SeoUtil.getInstance().buildSeo("shouye", map);
		return null;
	}

	@RequestMapping
	public ModelAndView ppt(Map<String, Object> map) {
		SeoUtil.getInstance().buildSeo("pingpaitong", map);
		return null;
	}

	@RequestMapping
	public ModelAndView yyfw(Map<String, Object> map) {
		SeoUtil.getInstance().buildSeo("yyservice", map);
		return null;
	}

	@RequestMapping
	public ModelAndView zst(Map<String, Object> map) {
		SeoUtil.getInstance().buildSeo("zaishengtong", map);
		return null;
	}

	// 报名
	@RequestMapping
	public ModelAndView pay(HttpServletRequest req, Map<String, Object> map) throws IOException {
		String sq = req.getParameter("sqzst");
		// SeoUtil.getInstance().buildSeo("huiyuan", map);
		ExtResult result = new ExtResult();
		Integer succeed=0;
		// 判断从哪个页面传过来的请求
		if (sq.equals("zstfy")) {
			// 获取请求参数
			String hytpe = req.getParameter("typename");
			String name = req.getParameter("lianxi");
			String sex = req.getParameter("sex");
			// 判断传过来的参数是男或女
			if (sex.equals("m")) {
				sex = "男";
			} else if (sex.equals("f")) {
				sex = "女";
			}
			// 获取请求参数
			String mobile = req.getParameter("mobilename");
			String countries = req.getParameter("countries");
			String code = req.getParameter("code");
			String phone = req.getParameter("phone");
			String email = req.getParameter("email");
			String title = "";
			// 判断传过来的请求服务类型
			if (hytpe.equals("zst")) {
				title = "再生通基础服务";
			} else if (hytpe.equals("jpt")) {
				title = "金牌品牌通";
			} else if (hytpe.equals("zsp")) {
				title = "钻石品牌通";
			}
			// 拼接请求信息内容
			String content = "";
			if (countries != "") {
				content = "申请服务类型:" + title + "<br/>联系人:" + name + "<br/>性别:"
						+ sex + "<br/>联系电话:'" + countries + "-" + code + "-"
						+ phone + "<br/>手机号码:" + mobile + "<br/>邮箱:" + email
						+ "";
			} else {
				content = "申请服务类型:" + title + "<br/>联系人:" + name + "<br/>性别:"
						+ sex + "<br/>联系电话:" + code + "-" + phone
						+ "<br/>手机号码:" + mobile + "<br/>邮箱:" + email + "";
			}
			succeed=subjectBaomingService.createNewBaoming("申请再生通", content);
		} else if (sq.equals("dianhua")) {
			// 获取请求参数
			String name = req.getParameter("lianxi");
			String sex = req.getParameter("sex");
			String mobile = req.getParameter("mobile");
			String countries = req.getParameter("countries");
			String code = req.getParameter("code");
			String phone = req.getParameter("phone");
			if (sex.equals("m")) {
				sex = "男";
			} else if (sex.equals("f")) {
				sex = "女";
			}
			// 拼接请求信息内容
			String content = "";
			if (countries != "") {
				content = "联系人:" + name + "<br/>性别:" + sex + "<br/>联系电话:"
						+ countries + "-" + code + "-" + phone + "<br/>手机号码:"
						+ mobile + "'";
			} else {
				content = "联系人:" + name + "<br/>性别:" + sex + "<br/>联系电话:"
						+ code + "-" + phone + "<br/>手机号码:" + mobile + "";
			}
			succeed=subjectBaomingService.createNewBaoming("申请再生通", content);
		}
		if(succeed==null){
			succeed=0;
		}
		//判断数据插入是否成功
          if(succeed.intValue()>0){
        	  result.setSuccess(true);     	  
          }else{
        	  result.setSuccess(false); 
          }
		return printJson(result, map);
	}
}
