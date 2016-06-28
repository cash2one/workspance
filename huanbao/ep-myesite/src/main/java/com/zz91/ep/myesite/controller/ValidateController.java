/*
 * 文件名称：RootController.java
 * 创建者　：涂灵峰
 * 创建时间：2012-5-23 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.myesite.controller;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.patchca.color.ColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.word.RandomWordFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.utils.EpConst;
import com.zz91.util.auth.ep.EpAuthUtils;

/**
 * ajax 验证，用于验证用户注册信息是否可用
 */
@Controller
public class ValidateController extends BaseController {

	private static ConfigurableCaptchaService cs = null;
	private static ColorFactory cf = null;
	private static RandomWordFactory wf = null;
	// private static Random r = new Random();
	// private static CurvesRippleFilterFactory crff = null;
	// private static MarbleRippleFilterFactory mrff = null;
	// private static DoubleRippleFilterFactory drff = null;
	private static WobbleRippleFilterFactory wrff = null;

	// private static DiffuseRippleFilterFactory dirff = null;

	private void init() {
		if (cs == null) {
			cs = new ConfigurableCaptchaService();
		}
		if (cf == null) {
			cf = new SingleColorFactory(new Color(25, 60, 170));
		}
		if (wf == null) {
			wf = new RandomWordFactory();
		}
		// crff = new CurvesRippleFilterFactory(cs.getColorFactory());
		// drff = new DoubleRippleFilterFactory();
		// dirff = new DiffuseRippleFilterFactory();
		// mrff = new MarbleRippleFilterFactory();

		if (wrff == null) {
			wrff = new WobbleRippleFilterFactory();
		}

		cs.setWordFactory(wf);
		cs.setColorFactory(cf);
		cs.setWidth(160);
		cs.setHeight(50);

	}

	@RequestMapping
	public ModelAndView vcode(HttpServletRequest request,
			Map<String, Object> out, HttpServletResponse response) {

		response.setContentType("image/png");

		init();

		OutputStream os;
		try {

			os = response.getOutputStream();
			cs.setFilterFactory(wrff);

			org.patchca.service.Captcha captcha = cs.getCaptcha();
			EpAuthUtils.getInstance().setValue(request, response,
					EpConst.VALIDATE_CODE_KEY, captcha.getChallenge());
			ImageIO.write(captcha.getImage(), "png", os);

			os.flush();
			os.close();

		} catch (IOException e) {
		} finally {
		}

		return null;
	}

}