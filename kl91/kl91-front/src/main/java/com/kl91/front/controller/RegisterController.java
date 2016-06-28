package com.kl91.front.controller;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.Captcha.Builder;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.gimpy.BlockGimpyRenderer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.DefaultTextProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kl91.domain.company.Company;
import com.kl91.domain.dto.ExtResult;
import com.kl91.service.company.CompanyService;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class RegisterController extends BaseController {

	@Resource
	private CompanyService companyService;

	@RequestMapping
	public void registerStep1(Map<String, Object> out) {
		SeoUtil.getInstance().buildSeo("login", out);
	}
	//注册一
	@RequestMapping
	public ModelAndView doRegister(Map<String, Object> out, Company company,
			String verifyCodeKey, String verifyCode)
			throws Exception {
		ExtResult result=new ExtResult();
		String captcha = String.valueOf(MemcachedUtils.getInstance().getClient().gets(verifyCodeKey));		
		MemcachedUtils.getInstance().getClient().delete(verifyCodeKey);

		if (captcha == null) {
			result.setData("验证码获取失败！");
		}

		if (!captcha.equals(verifyCode)) {
			result.setData("您输入的验证码有错误！");
		}
		company.setCompanyName("");
		company.setMembershipCode("10001000");
		company.setDomain("domain");
		company.setGmtLastLogin(new Date());
		company.setNumLogin(1);
		company.setRegistFlag(1);
		// 默认注册 未激活
		company.setIsActive(0);
		
		String enPassword = MD5.encode(company.getPassword(), MD5.LENGTH_32);
		company.setPassword(enPassword);
		Integer id = companyService.createCompany(company);
		if(id>0){
			return new ModelAndView("redirect:registerStep2.htm?id="+id);
		}
		// 注册失败 返回 注册页面1
		return new ModelAndView("redirect:registerStep1.htm");
	}
	
	//注册第二(修改资料)
	@RequestMapping
	public ModelAndView registerStep2(Map<String, Object> out, HttpServletRequest request,Integer id) {
		do{
			if(id==null){
				break;
			}
			out.put("id", id);
			return null;
		}while(false);
		return new ModelAndView("redirect:registerStep1.htm");
	}

	@RequestMapping
	public ModelAndView doRegister2(Map<String, Object> out,HttpServletRequest request,Company company)
		throws Exception {	
		Integer data = companyService.editCompany(company);
		if(data>0){
			out.put("company", company);
			return new ModelAndView("redirect:/zhushou/index.htm");
		}
		// 注册失败 返回 注册页面1
		return new ModelAndView("redirect:registerStep1.htm");
	}
	
	//验证account是否被注册
	@RequestMapping
	public ModelAndView account(HttpServletRequest request,
			Map<String, Object> out, String fieldId, String fieldValue,
			String extraData) {
		boolean result = false;
		if (StringUtils.isNotEmpty(fieldValue)) {
			Integer userCount = companyService.countUserByAccount(fieldValue);
			if (userCount == null || userCount == 0) {
				result = true;
			}
		}
		out.put("json", buildResult(fieldId, result));
		return new ModelAndView("json");
	}
	//验证email是否被注册
	@RequestMapping
	public ModelAndView email(HttpServletRequest request,
			Map<String, Object> out, String fieldId, String fieldValue,
			String extraData) {
		boolean result = false;
		if (StringUtils.isNotEmpty(fieldValue)) {
			Company company = companyService.queryByEmail(fieldValue);
			if (company == null) {
				result = true;
			}
		}
		out.put("json", buildResult(fieldId, result));
		return new ModelAndView("json");
	}
	
	//验证mobile是否被注册
	@RequestMapping
	public ModelAndView validaeMobile(Map<String, Object> out, String fieldId,
			String fieldValue, String extraData) {
		boolean result = false;
		if (StringUtils.isNotEmpty(fieldValue)) {
			Company company = companyService.queryByMobile(fieldValue);
			if (company == null) {
				result = true;
			}
		}
		out.put("json", buildResult(fieldId, result));
		return new ModelAndView("json");
	}

	private String buildResult(String fieldId, boolean result) {
		return "[\"" + fieldId + "\"," + result + ",\"\"]";
	}

	//获取验证码图片
	@RequestMapping
	public ModelAndView mathcode(HttpServletRequest request,HttpServletResponse response, String ts) throws IOException{
		if(ts==null){
			return null;
		}
		
		Builder builder = new Captcha.Builder(120, 30);
		builder.gimp(new BlockGimpyRenderer(3));	
		List<Font> fontList = new ArrayList<Font>();
		fontList.add(new Font("FZJingLeiS-R-GB", Font.ITALIC, 20));
		List<Color> colorList=new ArrayList<Color>();
		colorList.add(Color.red);
		colorList.add(Color.blue);
		colorList.add(Color.darkGray);
		colorList.add(getRandColor(0,100));
		colorList.add(getRandColor(0,100));
		colorList.add(getRandColor(0,100));
		colorList.add(getRandColor(0,100));
		DefaultWordRenderer dwr= new DefaultWordRenderer(colorList,fontList);		
		WordRenderer wr=dwr;		
		//构造随机数字
		int a=(int)(Math.random()*10);
		int b=(int)(Math.random()*10);		
		int math=(int) (Math.random()*3);
		String mathstr="加";
		int result=a+b;
		if(math==1){
			mathstr="减";
			result=a-b;
		}else if(math==2){
			mathstr="乘";
			result=a*b;
		}		
			builder.addText(new DefaultTextProducer(1, String.valueOf(a).toCharArray()), wr);
			builder.addText(new DefaultTextProducer(1, mathstr.toCharArray()), wr);
			builder.addText(new DefaultTextProducer(1, String.valueOf(b).toCharArray()), wr);
			builder.addText(new DefaultTextProducer(1, new char[]{'等'}), wr);
			builder.addText(new DefaultTextProducer(1, new char[]{'于'}), wr);
			builder.addText(new DefaultTextProducer(1, new char[]{'?'}), wr);
		
		builder.addBackground(new FlatColorBackgroundProducer(getRandColor(100, 255)));
		builder.gimp(new BlockGimpyRenderer(1));
		Captcha captcha =  builder.build();
		MemcachedUtils.getInstance().getClient().set(ts, 300000, result);		
		CaptchaServletUtil.writeImage(response, captcha.getImage());		
		return null;
	}

	public Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	@RequestMapping
	public void login(Map<String, Object> map) {

	}

	@RequestMapping
	public void forgetPassword(Map<String, Object> map) {

	}

	@RequestMapping
	public void sendPasswordForEmail(Map<String, Object> map) {

	}
}
