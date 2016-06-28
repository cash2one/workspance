/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-7
 */
package com.ast.ast1949.myrc.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.gimpy.DropShadowGimpyRenderer;
import nl.captcha.gimpy.RippleGimpyRenderer;
import nl.captcha.text.producer.ChineseTextProducer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.util.cache.MemcachedUtils;

/**
 * @author yuyonghui
 *
 */
@Controller
public class ValidationCodeController extends BaseController{

	@RequestMapping
	public ModelAndView code(HttpServletRequest request,HttpServletResponse response, String ts) throws IOException{
		if(ts==null){
			return null;
		}
		
		Captcha captcha = new Captcha.Builder(130, 50)
			.addText()
			.addBackground(new FlatColorBackgroundProducer(getRandColor(200, 250)))
			.addNoise()
			.gimp(new RippleGimpyRenderer())
			.addBorder()
			.build();
		
		MemcachedUtils.getInstance().getClient().set(ts, 300000, captcha);
		BufferedImage image = captcha.getImage();
		ImageIO.write(image, "JPEG", response.getOutputStream());
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView code1(HttpServletRequest request,HttpServletResponse response, String ts) throws IOException{
		if(ts==null){
			return null;
		}
		
		Captcha captcha = new Captcha.Builder(130, 40)
			.addText(new ChineseTextProducer(5))
			.addBackground(new FlatColorBackgroundProducer())
			.addNoise()
			.gimp(new DropShadowGimpyRenderer())
			.addBorder()
			.build();
		
		MemcachedUtils.getInstance().getClient().set(ts, 60000, captcha);
		BufferedImage image = captcha.getImage();
		ImageIO.write(image, "JPEG", response.getOutputStream());
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView code2(HttpServletRequest request,HttpServletResponse response, String ts) throws IOException{
		if(ts==null){
			return null;
		}
		
		Captcha captcha = new Captcha.Builder(130, 40)
			.addText(new ChineseTextProducer(5))
			.addBackground()
			.addNoise()
			.gimp()
			.addBorder()
			.build();
		
		MemcachedUtils.getInstance().getClient().set(ts, 60000, captcha);
		BufferedImage image = captcha.getImage();
		ImageIO.write(image, "JPEG", response.getOutputStream());
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView code3(HttpServletRequest request,HttpServletResponse response, String ts) throws IOException{
		if(ts==null){
			return null;
		}
		
		Captcha captcha = new Captcha.Builder(130, 40)
			.addText(new ChineseTextProducer(5))
			.addBackground()
			.addNoise()
			.gimp()
			.addBorder()
			.build();
		
		MemcachedUtils.getInstance().getClient().set(ts, 60000, captcha);
		BufferedImage image = captcha.getImage();
		ImageIO.write(image, "JPEG", response.getOutputStream());
		
		return null;
	}
	
//	@RequestMapping
//	public ModelAndView code(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		// 设置页面不缓存
//		response.setHeader("Pragma", "No-cache");
//		response.setHeader("Cache-Control", "no-cache");
//		response.setDateHeader("Expires", 0);
//		
//		// 在内存中创建图象
//		int width = 65, height = 30;
//		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//		
//		// 获取图形上下文
//		Graphics g = image.getGraphics();
//		
//		// 生成随机类
//		Random random = new Random();
//		
//		// 设定背景色
//		g.setColor(getRandColor(200, 250));
//		g.fillRect(0, 0, width, height);
//		
//		// 设定字体
//		g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
//		
//		// 画边框
//		// g.setColor(new Color());
//		// g.drawRect(23,7,width-1,height+5);
//		
//		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
//		g.setColor(getRandColor(160, 200));
//		for (int i = 0; i < 155; i++) {
//			int x = random.nextInt(width);
//			int y = random.nextInt(height);
//			int xl = random.nextInt(12);
//			int yl = random.nextInt(12);
//			g.drawLine(x, y, x + xl, y + yl);
//		}
//		
//		// 取随机产生的认证码(4位数字)
//		String sRand = "";
//		for (int i = 0; i < 4; i++) {
//			String rand = String.valueOf(random.nextInt(10));
//			sRand += rand;
//			// 将认证码显示到图象中
//			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
//			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
//			g.drawString(rand, 13 * i + 6, 22);
//		}
//		
//		// 将认证码存入Mamcached里
//		setCachedSession( request,  response,  FrontConst.SESSION_CODE, sRand) ;
//		// 图象生效
//		g.dispose();
//		
//		// 输出图象到页面
//		ImageIO.write(image, "JPEG", response.getOutputStream());
//		//	JPEGImageEncoder encode = JPEGCodec.createJPEGEncoder(response.getOutputStream());
//		//	encode.encode(image);
//		// 转成JPEG格式 
//		
//		return null;
//	}
//	
	
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
}
