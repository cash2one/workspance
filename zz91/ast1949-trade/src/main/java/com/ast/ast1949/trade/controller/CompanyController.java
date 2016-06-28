/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-7-6
 */
package com.ast.ast1949.trade.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.domain.phone.PhoneClickLog;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.service.analysis.AnalysisOptNumDailyService;
import com.ast.ast1949.service.company.CompanyAccessViewService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.facade.MemberRuleFacade;
import com.ast.ast1949.service.phone.PhoneClickLogService;
import com.ast.ast1949.service.phone.PhoneService;
import com.ast.ast1949.service.sample.ContactClickLogService;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.cache.MemcachedUtils;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-7-6
 */
@Controller
public class CompanyController extends BaseController {

	@Resource
	private CompanyService companyService;
	@Resource
	private AnalysisOptNumDailyService analysisOptNumDailyService;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private PhoneService phoneService;
	@Resource
	private CompanyAccessViewService companyAccessViewService;
	@Resource
	private PhoneClickLogService phoneClickLogService;
	@Resource
	private ContactClickLogService  contactClickLogService;
	
	@RequestMapping
	public ModelAndView cinfo(HttpServletRequest request, Map<String, Object> out, String email) throws IOException{
		// 根据搜索email的高会 账户
		CompanyDto company=companyService.queryCompanyByAccountEmail(email,null);
		Integer cid = company.getCompany().getId();
		if(crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.LDB_CODE)){
			Phone phone = phoneService.queryByCompanyId(cid);
			if(phone!=null){
				company.getAccount().setTel(phone.getTel());
				company.setIsLDB(true);
			}
		}
		if(company!=null && company.getCompany()!=null){
			String str=company.getCompany().getBusiness();
			if(str!=null){
				str=Jsoup.clean(str, Whitelist.none());
				if(str.length()>40){
					str=str.substring(0, 40);
				}
			}
			company.getCompany().setBusiness(str);
		}
		return printJson(company, out);
	}
		
	@RequestMapping
	public ModelAndView createImageContactInfo(Map<String, Object> out, Integer cid,
			HttpServletRequest request) throws IOException {
		ExtResult result = new ExtResult();

		do {

			SsoUser ssoUser = getCachedUser(request);

			boolean toViewFlag = false, beViewedFlag = false, isWXAccess= false;
			
			// 登录的人是否 有  再生通、来电宝 等服务
			if (ssoUser!=null) {
				toViewFlag=crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), "1000");
				if(!toViewFlag){
					toViewFlag=crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), "1006");
				}
				// 一块来电宝
				if(!toViewFlag){
					toViewFlag=crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), CrmCompanySvrService.LDB_CODE);
				}
				// 五块来电宝
				if(!toViewFlag){
					toViewFlag=crmCompanySvrService.validatePeriod(ssoUser.getCompanyId(), CrmCompanySvrService.LDB_FIVE_CODE);
				}
				if(!toViewFlag){
					PhoneClickLog phoneClickLog = phoneClickLogService.queryById(ssoUser.getCompanyId(),cid);
					if (phoneClickLog!=null) {
						toViewFlag = true;
					}
				}
				if (!toViewFlag && cid != null) {
					//积分兑换查看服务 
					toViewFlag = contactClickLogService.scoreCvtViewContact(ssoUser.getCompanyId(), ssoUser.getAccount(), cid);
				}
			}

			// 被查看的人是否 有 再生通、来电宝 等服务
			beViewedFlag=crmCompanySvrService.validatePeriod(cid, "1000");
			if (!beViewedFlag) {
				beViewedFlag = crmCompanySvrService.validatePeriod(cid, "1006");
			}
			if(!beViewedFlag){
				beViewedFlag=crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.LDB_CODE);
			}
			if(!beViewedFlag){
				beViewedFlag=crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.LDB_FIVE_CODE);
			}
			
			// 检验普会是否微信绑定用户
			if(ssoUser!=null&&"10051000".equals(ssoUser.getMembershipCode())&&companyAccessViewService.validateIsExists(ssoUser.getCompanyId(), cid, ssoUser.getAccount())==1){
				isWXAccess = true;
				companyAccessViewService.insert(ssoUser.getCompanyId(), cid, ssoUser.getAccount());
			}

			if (!(toViewFlag || beViewedFlag ||isWXAccess)) {
				result.setData("memberAuthorFailure");
				break;
			}

			//检查查看次数是否超过（如果被查看的对象是普通会员）
			if (!beViewedFlag && ssoUser!=null) {

				//限制次数
				Integer viewedNum = Integer.valueOf(MemberRuleFacade.getInstance().getValue(ssoUser.getMembershipCode(), "view_member_contacts_num"));

				//今天已查看次数
				Integer viewedTodayNum = analysisOptNumDailyService.queryOptNumByAccountToday(
						AnalysisOptNumDailyService.OPT_VIEW_CONTACT_PAID_FALSE, ssoUser.getAccount());

				if (viewedTodayNum.intValue() >= viewedNum.intValue()) {
					result.setData("overLimit");
					break;
				}

				analysisOptNumDailyService.insertOptNum(AnalysisOptNumDailyService.OPT_VIEW_CONTACT_PAID_FALSE, ssoUser.getAccount(), ssoUser.getCompanyId());
			}

			CompanyDto beViewedCompany=companyService.queryCompanyWithAccountById(cid);
			if (beViewedCompany == null) {
				result.setData("contactInfoBroken");
				break;
			}

			analysisOptNumDailyService.insertOptNum(AnalysisOptNumDailyService.OPT_BE_VIEWED_CONTACT,beViewedCompany.getAccount().getAccount(), cid);
			//将信息放在缓存中
			String tel = "";
			if (StringUtils.isNotEmpty(beViewedCompany.getAccount().getTelCountryCode())) {
				tel = beViewedCompany.getAccount().getTelCountryCode() + "-";
			}
			if (StringUtils.isNotEmpty(beViewedCompany.getAccount().getTelAreaCode())) {
				tel = tel + beViewedCompany.getAccount().getTelAreaCode() + "-";
			}
			if (StringUtils.isNotEmpty(beViewedCompany.getAccount().getTel())) {
				tel = tel + beViewedCompany.getAccount().getTel();
			}
			beViewedCompany.getAccount().setTelAreaCode(null);
			beViewedCompany.getAccount().setTelCountryCode(null);
			if(crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.LDB_CODE)||
				crmCompanySvrService.validatePeriod(cid, CrmCompanySvrService.LDB_FIVE_CODE)){
				Phone phone = phoneService.queryByCompanyId(cid);
				if(phone!=null){
					beViewedCompany.getAccount().setTel(sendInfoToCache(phone.getTel()));
					beViewedCompany.setIsLDB(true);
				}else{
					beViewedCompany.getAccount().setTel(sendInfoToCache(tel));
				}
			}else{
				beViewedCompany.getAccount().setTel(sendInfoToCache(tel));
			}
			//beViewedCompany.getAccount().setContact(sendInfoToCache(beViewedCompany.getAccount().getContact()));
			beViewedCompany.getAccount().setMobile(sendInfoToCache(beViewedCompany.getAccount().getMobile()));
			
			if("1".equals(beViewedCompany.getAccount().getIsUseBackEmail()) && beViewedCompany.getAccount().getBackEmail()!=null){
				beViewedCompany.getAccount().setEmail(sendInfoToCache(beViewedCompany.getAccount().getBackEmail()));
			}else{
				beViewedCompany.getAccount().setEmail(sendInfoToCache(beViewedCompany.getAccount().getEmail()));
			}
				

			result.setSuccess(true);
			result.setData(beViewedCompany);
		} while (false);
		return printJson(result, out);
	}

	final static int INFO_CACHE_TIME = 60;

	String sendInfoToCache(String info) {
		if (StringUtils.isNotEmpty(info)) {
			String key = UUID.randomUUID().toString();
			MemcachedUtils.getInstance().getClient().set(key, INFO_CACHE_TIME, info);
			return key;
		}
		return null;
	}

	/**
	 * <br />
	 * 获取图片联系方式 <br />
	 * 当用户访问公司详细信息页面和供求详细页面，点击了查看联系方式按钮，并成功返 <br />
	 * 回时，系统通过这个请求，分别获取同一公司信息的不同联系方式
	 * 
	 * @param type
	 *            ：联系方式类型，null或空不获取信息
	 * @param key
	 *            ：每个联系方式对应的key，null或空则不获取任何信息
	 * @return
	 */
	@RequestMapping
	public ModelAndView viewContactInfo(Map<String, Object> out, String type, String key,String color,
			HttpServletResponse response) throws IOException {

		String s = (String) MemcachedUtils.getInstance().getClient().get(key);
		if (s == null) {
			return null;
		}
		MemcachedUtils.getInstance().getClient().delete(key);

		int width = s.getBytes().length * 8 + 2;
		int height = 16;

		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, width, height);
		if("red".equals(color)){
			g2.setColor(Color.red);
		}else{
			g2.setColor(Color.black);
		}
		g2.drawString(s, 2, 13);
		ImageIO.write(bi, "jpg", response.getOutputStream());
		return null;
	}
}
