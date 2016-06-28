/**
 * @author shiqp
 * @date 2016-01-09
 */
package com.ast.feiliao91.www.controller;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
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

import com.ast.feiliao91.auth.SsoUtils;
import com.ast.feiliao91.service.company.CompanyValidateService;
import com.ast.feiliao91.util.AstConst;
import com.zz91.util.lang.StringUtils;

@Controller
public class ValidationCodeController extends BaseController {
	@Resource
	private CompanyValidateService companyValidateService;

	private static ConfigurableCaptchaService cs = null;
	private static ColorFactory cf = null;
	private static RandomWordFactory wf = null;
	private static WobbleRippleFilterFactory wrff = null;

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
		if (wrff == null) {
			wrff = new WobbleRippleFilterFactory();
		}
		cs.setWordFactory(wf);
		cs.setColorFactory(cf);
		cs.setWidth(160);
		cs.setHeight(50);

	}

	@RequestMapping
	public void vcode(HttpServletRequest request, Map<String, Object> out, HttpServletResponse response) {
		response.setContentType("image/png");
		init();
		OutputStream os;
		try {
			os = response.getOutputStream();
			cs.setFilterFactory(wrff);

			org.patchca.service.Captcha captcha = cs.getCaptcha();
			SsoUtils.getInstance().setValue(request, response, AstConst.VALIDATE_CODE_KEY, captcha.getChallenge());
			ImageIO.write(captcha.getImage(), "png", os);

			os.flush();
			os.close();

		} catch (IOException e) {
		} finally {
		}
	}

	// 验证验证码
	@RequestMapping
	public ModelAndView validateCode(Map<String, Object> out, HttpServletRequest request, HttpServletResponse response,
			String verifyCode) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		do {
			boolean isTrue = true;
			// 验证验证码，防止机器注册
			String vcode = String.valueOf(SsoUtils.getInstance().getValue(request, response, AstConst.VALIDATE_CODE_KEY));
			if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(verifyCode) || !verifyCode.equalsIgnoreCase(vcode)) {
				isTrue = false;
				break;
			}
			SsoUtils.getInstance().remove(request, AstConst.VALIDATE_CODE_KEY);
			map.put("isTrue", isTrue);
		} while (false);
		return printJson(map, out);
	}
	
	@RequestMapping
	public void sendMobile(HttpServletRequest request, HttpServletResponse response, String mobile){
		companyValidateService.sendCodeByMobile(mobile,"0");
	}

}
