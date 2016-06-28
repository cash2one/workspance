/**
 * @author shiqp
 * @date 2016-01-09
 */
package com.ast.feiliao91.service.company.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.CompanyValidate;
import com.ast.feiliao91.domain.company.CompanyValidateSearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.CompanyValidateDto;
import com.ast.feiliao91.persist.company.CompanyAccountDao;
import com.ast.feiliao91.persist.company.CompanyInfoDao;
import com.ast.feiliao91.persist.company.CompanyValidateDao;
import com.ast.feiliao91.service.company.CompanyValidateService;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.sms.SmsUtil;
import com.zz91.util.velocity.AddressTool;

@Component("companyValidateService")
public class CompanyValidateServiceImpl implements CompanyValidateService {
	@Resource
	private CompanyValidateDao companyValidateDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private CompanyInfoDao companyInfoDao;

	@Override
	public void sendCodeByMobile(String mobile, String code) {
		// 验证码生成
		String vcode = "";
		for (Integer i = 0; i < 4; i++) {
			int in = (int) (Math.random() * 10);
			if (in == 0) {
				in = 1;
			}
			vcode = vcode + in;
		}
		// 验证信息
		CompanyValidate validate = new CompanyValidate();
		validate.setTargetName(mobile);
		validate.setVcode(vcode);
		validate.setTargetType("1000");// 暂定为手机验证 1000
		// 保存验证信息
		companyValidateDao.insertValidate(validate);
		// 发送短信
		String sms_flag = (String) MemcachedUtils.getInstance().getClient()
				.get("baseConfig.sms.flag");
		if ("true".equals(sms_flag)) {
			if (code.equals("0")) {
				SmsUtil.getInstance().sendSms("sms_taozaisheng_register",
						mobile, null, new Date(), new String[] { vcode });
			} else if (code.equals("1")) {
				SmsUtil.getInstance().sendSms("sms_UnMobile_validate", mobile,
						null, new Date(), new String[] { vcode });
			} else if (code.equals("2")) {
				SmsUtil.getInstance().sendSms("sms_bindMobile_validate",
						mobile, null, new Date(), new String[] { vcode });
			} else if (code.equals("4")) {
				SmsUtil.getInstance().sendSms("sms_set_pwd", mobile, null,
						new Date(), new String[] { vcode });
			} else if (code.equals("5")) {
				SmsUtil.getInstance().sendSms("sms_update_pwd", mobile, null,
						new Date(), new String[] { vcode });
			}
		}
	}

	@Override
	public Integer validateByMobile(String mobile, String vcode) {
		// 认证标志,默认为失败
		Integer flag = 0;
		// 检索最新的短信认证信息
		CompanyValidate validate = companyValidateDao
				.queryValidateByNameAndType(mobile, "1000");
		if (validate != null) {
			if (vcode.equals(validate.getVcode())
					&& validate.getIsValidate() == 0) {
				// 更新验证状态
				Integer iv = companyValidateDao.updateValidateById(validate
						.getId());
				if (iv > 0) {
					// 验证成功
					flag = 1;
				}
			}
		}
		return flag;
	}

	@Override
	public Integer validateByType(String targetName, String vcode, String type) {
		// 认证标志,默认为失败
		Integer flag = 0;
		// 检索最新的短信认证信息
		CompanyValidate validate = companyValidateDao
				.queryValidateByNameAndType(targetName, type);
		if (validate != null) {
			if (vcode.equals(validate.getVcode())
					&& validate.getIsValidate() == 0) {
				// 更新验证状态
				Integer iv = companyValidateDao.updateValidateById(validate
						.getId());
				if (iv > 0) {
					// 验证成功
					flag = 1;
				}
			}
		}
		return flag;
	}

	@Override
	public void sendCodeByEmail(String email, String account, String code) {
		// 验证码生成
		String vcode = "";
		for (Integer i = 0; i < 4; i++) {
			int in = (int) (Math.random() * 10);
			if (in == 0) {
				in = 1;
			}
			vcode = vcode + in;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("vcode", vcode);
		map.put("email", email);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("date", format.format(new Date()));
		// 验证信息
		CompanyValidate validate = new CompanyValidate();
		validate.setTargetName(email);
		validate.setVcode(vcode);
		validate.setTargetType(TYPE_EMAIL);// 邮箱验证
		// 保存验证信息
		companyValidateDao.insertValidate(validate);
		String key = "";
		try {
			key = MD5.encode(account + email + vcode);
			map.put("key", key);
		} catch (Exception e) {
			return;
		}
		// 发送邮件
		String sms_flag = (String) MemcachedUtils.getInstance().getClient()
				.get("baseConfig.sms.flag");
		if ("true".equals(sms_flag)) {
			if (code.equals("0")) {
				map.put("address", AddressTool.getAddress("www")
						+ "/doValidEmail.htm?key=" + key + "&account" + account
						+ "&vcode=" + vcode);
				MailUtil.getInstance().sendMail("邮件密码找回", email,
						"feiliao91-pwd-mail", map, 0);
			} else if (code.equals("1")) {
				map.put("address", AddressTool.getAddress("trade")
						+ "/security/unValidEmail.htm?key=" + key + "&account="
						+ account + "&vcode=" + vcode + "&email=" + email);
				MailUtil.getInstance().sendMail("邮件解绑", email,
						"feiliao91-email-mailUnbind", map, 0);
			} else if (code.equals("2")) {
				map.put("address", AddressTool.getAddress("trade")
						+ "/security/doValidEmail.htm?key=" + key + "&account="
						+ account + "&vcode=" + vcode + "&email=" + email);
				MailUtil.getInstance().sendMail("邮件绑定", email,
						"feiliao91-email-mailBind", map, 0);
			}
		} else {
			// 本地打印信息
			System.out.println(map);
		}

	}

	@Override
	public void sendAllMobile(String mobile, Map<String, Object> map) {
		// 验证码生成
		String vcode = "";
		for (Integer i = 0; i < 4; i++) {
			int in = (int) (Math.random() * 10);
			if (in == 0) {
				in = 1;
			}
			vcode = vcode + in;
		}
		// 验证信息
		CompanyValidate validate = new CompanyValidate();
		validate.setTargetName(mobile);
		validate.setVcode(vcode);
		validate.setTargetType("1000");// 暂定为手机验证 1000
		Float pri = (Float) map.get("price");
		java.text.DecimalFormat df = new java.text.DecimalFormat("#######0.0");
		String price = df.format(pri);
		// 保存验证信息
		companyValidateDao.insertValidate(validate);
		// 发送短信
		String sms_flag = (String) MemcachedUtils.getInstance().getClient()
				.get("baseConfig.sms.flag");
		if ("true".equals(sms_flag)) {
			SmsUtil.getInstance().sendSms("sms_pay_validate", mobile, null,
					new Date(), new String[] { vcode, price });

		}

	}
	
	@Override
	public PageDto<CompanyValidateDto> pageBySearchByAdmin(
			PageDto<CompanyValidateDto> page, CompanyValidateSearch searchDto){
		List<CompanyValidate> list = companyValidateDao.querycompanyValidateByAdmin(page, searchDto);
		List<CompanyValidateDto> resultList = new ArrayList<CompanyValidateDto>();
		for (CompanyValidate companyValidate : list) {
			CompanyValidateDto dto = new CompanyValidateDto();
			dto.setCompanyValidate(companyValidate);
			//判断是否注册成功
			CompanyAccount companyAccount = companyAccountDao.queryByAccount(companyValidate.getTargetName());
			if(companyAccount != null){
				dto.setIsRegisterSucc(1);
				dto.setRegisterSuccTime(companyAccount.getGmtCreated());
				int totalSeconds = getSeconds(companyValidate.getGmtCreated(),companyAccount.getGmtCreated());
				dto.setRegisterUsedTime(Integer.valueOf(totalSeconds));
				dto.setCompanyName(companyInfoDao.queryById(companyAccount.getCompanyId()).getName());
			}else{
				dto.setIsRegisterSucc(0);
			}
			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(companyValidateDao.countcompanyValidateByAdmin(searchDto));
		return page;
	}
	/**
	 * 获得时间差
	 * @param startdate
	 * @param enddate
	 * @return 秒数
	 */
	public int getSeconds(Date startdate, Date enddate) {
		long time = enddate.getTime() - startdate.getTime();
		int totalS = new Long(time / 1000).intValue();
		return totalS;
		}
}
