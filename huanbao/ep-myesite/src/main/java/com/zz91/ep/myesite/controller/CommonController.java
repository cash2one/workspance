package com.zz91.ep.myesite.controller;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.Captcha.Builder;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.gimpy.BlockGimpyRenderer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//import sun.misc.BASE64Decoder;

import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.comp.IndustryChainDto;
import com.zz91.ep.domain.sys.Feedback;
import com.zz91.ep.domain.sys.SysArea;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.service.common.FeedbackService;
import com.zz91.ep.service.common.MyEsiteService;
import com.zz91.ep.service.common.SysAreaService;
import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.comp.IndustryChainService;
import com.zz91.ep.service.trade.TradeCategoryService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.auth.ep.EpAuthUtils;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.file.FileUtils;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.lang.StringUtils;

@Controller
public class CommonController extends BaseController {

	@Resource
	private CompAccountService compAccountService;
	@Resource
	private SysAreaService sysAreaService;
	@Resource
	private TradeCategoryService tradeCategoryService;
	@Resource
	private CompProfileService compProfileService;
	@Resource
	private IndustryChainService industryChainService;
	@Resource
	private FeedbackService feedbackService;
	@Resource
	private MyEsiteService myEsiteService;
	@Resource
	private TradeSupplyService tradeSupplyService;
	
	private static String UPLOAD_URL = "huanbao/resources";  // 上传路径
	
	/**
	 * 验证注册的账号是否可用
	 * */
	@RequestMapping
	public ModelAndView ajaxAccount(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> out, String fieldValue, String fieldId, String extraData){
		Integer id= compAccountService.getAccountIdByAccount(fieldValue);
		boolean result=false;
		if(id==null || id.intValue()<=0){
			//验证不通过
			result=true;
		}
//		myEsiteService.init(out, getCachedUser(request).getCid());
		out.put("json", buildResult(fieldId, result, null));
		return new ModelAndView("json");
	}
	
	/**
	 * 验证注册的邮箱是否可用
	 * */
	@RequestMapping
	public ModelAndView ajaxEmail(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> out, String fieldValue, String fieldId, String extraData){
		Integer count = compAccountService.queryCountCompAcountByEmail(fieldValue);
		
		boolean result=false;
		if(count==null || count.intValue()<=0){
			//验证不通过
			result=true;
		}
//		myEsiteService.init(out, getCachedUser(request).getCid());
		out.put("json", buildResult(fieldId, result, null));
		return new ModelAndView("json");
	}
	
	@RequestMapping
	public ModelAndView ajaxTitle(HttpServletRequest request, HttpServletResponse response, 
			Map<String, Object> out, String fieldValue, String fieldId, String extraData){
		boolean result=false;
		do {
			EpAuthUser epAuthUser = getCachedUser(request);
			if(epAuthUser==null||epAuthUser.getCid()==null){
				break;
			}
			// 是否可以发布
			if(tradeSupplyService.validateTitleRepeat(epAuthUser.getCid(), fieldValue)){
				result = true;
			}
		} while (false);
		out.put("json", buildResult(fieldId, result, null));
		return new ModelAndView("json");
	}
	
	/**
	 * 函数名称：registEmail 功能描述：验证邮箱是否唯一。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView registEmail(Map<String, Object> out,
			HttpServletRequest request, String email) {
		do {
			if (StringUtils.isEmpty(email)) {
				out.put("json", false);
				break;
			}
			Integer count = compAccountService
					.queryCountCompAcountByEmail(email);
			if (count != null && count > 0) {
				out.put("json", false);
			} else {
				out.put("json", true);
			}
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView("json");
	}

	/**
	 * 函数名称：registVerifyCode 功能描述：验证码是否正确。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView registVerifyCode(Map<String, Object> out,
			HttpServletRequest request, String verifyCode, String verifyCodeKey) {
		do {
			if (StringUtils.isEmpty(verifyCode)) {
				out.put("json", false);
				break;
			}
			String captcha = String.valueOf(MemcachedUtils.getInstance()
					.getClient().get(verifyCodeKey));

			if (captcha == null) {
				out.put("json", false);
				break;
			}
			if (!captcha.equals(verifyCode)) {
				out.put("json", false);
				break;
			}
			out.put("json", true);
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView("json");
	}

	/**
	 * 函数名称：registEmail 功能描述：验证用户名是否唯一。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView registAccount(Map<String, Object> out,
			HttpServletRequest request, String username) {
		do {
			if (StringUtils.isEmpty(username)) {
				out.put("json", false);
				break;
			}
			Integer count = compAccountService.getAccountIdByAccount(username);
			if (count != null && count > 0) {
				out.put("json", false);
			} else {
				out.put("json", true);
			}
		} while (false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView("json");
	}

	/**
	 * 函数名称：registMathcode 功能描述：取得验证码。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView registMathcode(HttpServletRequest request,
			HttpServletResponse response, String ts) throws IOException {
		if (ts == null) {
			return null;
		}

		Builder builder = new Captcha.Builder(160, 50);
		builder.addBorder();

		builder.addNoise();
		builder.gimp(new BlockGimpyRenderer(3));

		List<Font> fontList = new ArrayList<Font>();
		fontList.add(new Font("Arial", Font.BOLD, 42));
		List<Color> colorList = new ArrayList<Color>();
		colorList.add(Color.red);
		colorList.add(Color.blue);
		colorList.add(Color.darkGray);
		colorList.add(getRandColor(0, 100));
		colorList.add(getRandColor(0, 100));
		colorList.add(getRandColor(0, 100));
		colorList.add(getRandColor(0, 100));
		DefaultWordRenderer dwr = new DefaultWordRenderer(colorList, fontList);

		WordRenderer wr = dwr;

		builder.addText(wr);

		builder.addBackground(new FlatColorBackgroundProducer(getRandColor(100,
				255)));
		builder.gimp(new BlockGimpyRenderer(1));
		Captcha captcha = builder.build();

		MemcachedUtils.getInstance().getClient()
				.set(ts, 300000, captcha.getAnswer());
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

	/**
	 * 函数名称：getAreaCode 功能描述：根据父节点取省/地区 比如：取中国所有省份调用url为
	 * getAreaCode.htm?parentCode=10011000 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map
	 * @param parentCode
	 *            (中国为10011000) 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView getAreaCode(Map<String, Object> out,
			HttpServletRequest request, String parentCode) {
		List<SysArea> sysAreas = sysAreaService.querySysAreasByCode(parentCode);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(sysAreas, out);
	}

	/**
	 * 获取产品类别
	 * 
	 * @param request
	 * @param out
	 * @param parentCode
	 * @return
	 */
	@RequestMapping
	public ModelAndView getTradeCategory(HttpServletRequest request,
			Map<String, Object> out, String parentCode) {
		List<TradeCategory> categorys = tradeCategoryService
				.queryCategoryByParent(parentCode, 0, 0);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(categorys, out);
	}

	/**
	 * 函数名称：loginMini 功能描述：迷你登录(某些操作必须先登录) 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView loginMini(HttpServletRequest request,
			Map<String, Object> out, String destUrl, String error) {
		// 验证码工具制造验证码
		
		out.put("error", error);
		if(StringUtils.isNotEmpty(error)){
			out.put("errorText", AuthorizeException.getMessage(error));
		}
		if(StringUtils.isNotEmpty(destUrl)){
			out.put("destUrl", Jsoup.clean(destUrl, Whitelist.none()));
		}
//		myEsiteService.init(out, getCachedUser(request).getCid());
		return new ModelAndView();
	}

	@RequestMapping
	public ModelAndView doLoginMini(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String destUrl, String account, String password, String vcode){
		EpAuthUser user =null;
		do {
			if(StringUtils.isEmpty(account)){
				break;
			}
			if (StringUtils.isEmpty(password)) {
				break;
			}
			try {
				user = EpAuthUtils.getInstance().validateUser(response,account, password, null);
				compAccountService.updateLoginInfo(user.getUid(), null,user.getCid());
				EpAuthUtils.getInstance().setEpAuthUser(request, user, null);
				out.put("success", true);
				out.put("data", user.getCid());
//				myEsiteService.init(out, getCachedUser(request).getCid());
				
			} catch (NoSuchAlgorithmException e) {
				out.put("error", AuthorizeException.ERROR_SERVER);
				out.put("destUrl", destUrl);
//				myEsiteService.init(out, getCachedUser(request).getCid());
//				return new ModelAndView("redirect:loginMini.htm");
				break;
			} catch (IOException e) {
				out.put("error", AuthorizeException.ERROR_SERVER);
				out.put("destUrl", destUrl);
//				myEsiteService.init(out, getCachedUser(request).getCid());
//				return new ModelAndView("redirect:loginMini.htm");
				break;
			} catch (AuthorizeException e) {
				out.put("error", e.getMessage());
				out.put("destUrl", destUrl);
//		        myEsiteService.init(out, getCachedUser(request).getCid());
		        break;
			}
			if(StringUtils.isEmpty(destUrl)){
				myEsiteService.init(out, getCachedUser(request).getCid());
				return new ModelAndView("redirect:/");
			}else{
				myEsiteService.init(out, user.getCid());
				//myEsiteService.init(out, getCachedUser(request).getCid());
				return new ModelAndView("redirect:" + destUrl);
			}
		} while (false);
		return new ModelAndView("redirect:loginMini.htm");
	}

	/**
	 * 用于检测公司信息是否完善，确定是否需要强制完善信息
	 * */
	@RequestMapping
	public ModelAndView getCompproName(HttpServletRequest request,
			Map<String, Object> out) {
//		boolean incomplete = false;
		CompProfile compProfile = compProfileService.queryShortCompProfileById(getCompanyId(request));
//		if ("".equals(compProfile.getName())) {
//			incomplete = true;
//		}
//		List<Param> list = paramService.queryParamsByType("huanbao_announcement");
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("compProfile", compProfile);
//		m.put("list", list);
//		m.put("incomplete", incomplete);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(compProfile, out);
	}
	
	/**
	 * 获取产业链信息
	 * */
	@RequestMapping
	public ModelAndView getIndustryChains(HttpServletRequest request,
			Map<String, Object> out,String areaCode){
		List<IndustryChainDto> list = null;
		do{
			if(StringUtils.isEmpty(areaCode)){
				break;
			}
			list = industryChainService.queryIndustryChainByCid(getCompanyId(request), areaCode);
		}while(false);
		myEsiteService.init(out, getCachedUser(request).getCid());
		return printJson(list, out);
	}
	
	private String buildResult(String fieldId, boolean result, String message){
		if(message==null){
			message="";
		}
		return "[\""+fieldId+"\","+result+",\""+message+"\"]";
	}
	
	@RequestMapping
	public ModelAndView feedback(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, Feedback feedback, String screenBase64) {

//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			// Base64解码
//			byte[] b = decoder.decodeBuffer(screenBase64
//					.substring("data:image/png;base64,".length()));
//			for (int i = 0; i < b.length; ++i) {
//				if (b[i] < 0) {// 调整异常数据
//					b[i] += 256;
//				}
//			}
//			// 生成jpeg图片
//			String filename=UUID.randomUUID().toString();
//			String imgFilePath = "/feedback/"+MvcUpload.getDateFolder();// 新生成的图片
//			FileUtils.makedir(MvcUpload.getDestRootByServer(UPLOAD_URL)+imgFilePath);
//			imgFilePath=imgFilePath+"/"+filename+".png";
//			OutputStream o = new FileOutputStream(MvcUpload.getDestRootByServer(UPLOAD_URL)+imgFilePath);
//			o.write(b);
//			o.flush();
//			o.close();
//			feedback.setScreenshot(imgFilePath);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		EpAuthUser cachedUser = getCachedUser(request);
		if(cachedUser!=null){
			feedback.setCid(cachedUser.getCid());
			feedback.setUid(cachedUser.getUid());
		}
		myEsiteService.init(out, getCachedUser(request).getCid());
		Integer i = feedbackService.create(feedback);
		ExtResult result=new ExtResult();
		result.setData(i);
		result.setSuccess(true);
		return printJson( result, out);
	}
	
//	public static String getImageStr()  
//    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
//        String imgFile = "/home/mays/a.png";//待处理的图片  
//        InputStream in = null;  
//        byte[] data = null;  
//        //读取图片字节数组  
//        try   
//        {  
//            in = new FileInputStream(imgFile);          
//            data = new byte[in.available()];  
//            in.read(data);  
//            in.close();  
//        }   
//        catch (IOException e)   
//        {  
//            e.printStackTrace();  
//        }  
//        //对字节数组Base64编码  
//        BASE64Encoder encoder = new BASE64Encoder();  
//        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
//    }  
}
