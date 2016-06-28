package com.ast.ast1949.sample.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.auth.AuthForgotPassword;
import com.ast.ast1949.domain.auth.AuthUser;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.GetPasswordLog;
import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.sample.Account;
import com.ast.ast1949.domain.sample.Address;
import com.ast.ast1949.domain.sample.Custominfo;
import com.ast.ast1949.domain.sample.Identity;
import com.ast.ast1949.domain.sample.OrderBill;
import com.ast.ast1949.domain.sample.Refund;
import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.domain.sample.SampleMsgset;
import com.ast.ast1949.domain.sample.WeixinPrize;
import com.ast.ast1949.domain.sample.WeixinPrizelog;
import com.ast.ast1949.domain.sample.WeixinScore;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.paychannel.ChannelConst;
import com.ast.ast1949.paychannel.OrderDto;
import com.ast.ast1949.paychannel.OrderState;
import com.ast.ast1949.paychannel.OrderUtil;
import com.ast.ast1949.paychannel.ReturnState;
import com.ast.ast1949.paychannel.TransType;
import com.ast.ast1949.paychannel.exception.BizException;
import com.ast.ast1949.sample.paychannel.alipay.config.AlipayConfig;
import com.ast.ast1949.sample.paychannel.alipay.util.AlipayNotify;
import com.ast.ast1949.sample.paychannel.alipay.util.AlipaySubmit;
import com.ast.ast1949.sample.paychannel.tenpay.RequestHandler;
import com.ast.ast1949.sample.paychannel.tenpay.ResponseHandler;
import com.ast.ast1949.sample.paychannel.tenpay.client.ClientResponseHandler;
import com.ast.ast1949.sample.paychannel.tenpay.client.TenpayHttpClient;
import com.ast.ast1949.sample.paychannel.tenpay.util.TenpayUtil;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.auth.ParamService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.CompanyValidateService;
import com.ast.ast1949.service.company.GetPasswordLogService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.oauth.OauthAccessService;
import com.ast.ast1949.service.products.ProductAddPropertiesService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.sample.AccountService;
import com.ast.ast1949.service.sample.AddressService;
import com.ast.ast1949.service.sample.CustominfoService;
import com.ast.ast1949.service.sample.IdentityService;
import com.ast.ast1949.service.sample.OrderBillService;
import com.ast.ast1949.service.sample.RefundService;
import com.ast.ast1949.service.sample.SampleMsgsetService;
import com.ast.ast1949.service.sample.SampleRelateProductService;
import com.ast.ast1949.service.sample.SampleService;
import com.ast.ast1949.service.sample.WeixinPrizeService;
import com.ast.ast1949.service.sample.WeixinPrizelogService;
import com.ast.ast1949.service.sample.WeixinScoreService;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.domain.Param;
import com.zz91.util.encrypt.MD5;
import com.zz91.util.lang.NumberUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.mail.MailUtil;
import com.zz91.util.sms.SmsUtil;

@Controller
public class MngrController extends BaseController {
	private static Logger log = Logger.getLogger(MngrController.class);
	@Resource
	private SampleService sampleService;
	@Resource
	private ProductsService productsService;
	@Resource
	private SampleRelateProductService sampleRelateProductService;
	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private CompanyService companyService;

	@Resource
	private AccountService accountService;

	@Resource
	private OrderBillService orderBillService;

	@Resource
	private AddressService addressService;

	@Resource
	private ParamService paramService;

	@Resource
	private RefundService refundService;

	@Resource
	private CustominfoService custominfoService;

	@Resource
	private CompanyAccountService companyAccountService;

	@Resource
	private ProductAddPropertiesService productAddPropertiesService;
	
	@Resource
	private IdentityService identityService;
	
	@Resource
	private OauthAccessService oauthAccessService;

	@Resource
	private AuthService authService;
	
	@Resource
	private SampleMsgsetService sampleMsgsetService;
	
	@Resource
	private CompanyValidateService companyValidateService;
	
	@Resource
	private WeixinScoreService weixinScoreService;
	
	@Resource
	private WeixinPrizelogService weixinPrizelogService;
	
	@Resource
	private WeixinPrizeService weixinPrizeService;
	
	@Resource
	private GetPasswordLogService getPasswordLogService;
	/**
	 * 交易管理-会员中心
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView managerIndex(HttpServletRequest request, Map<String, Object> out) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		String account =null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
			account = sessionUser.getAccount();
		}

		// 各状态订单的统计数
		this.getOrderCount(companyId, out);
		
		Account acc = accountService.selectByCompanyId(companyId);
		if (acc == null) {
			acc = new Account(companyId, new BigDecimal(0), "00", new Date());
			acc.setLastupdateTime(new Date());
			accountService.insert(acc);
		}

		PageDto<OrderBill> page = new PageDto<OrderBill>();
		PageDto<OrderBill> page2 = new PageDto<OrderBill>();
		page.setPageSize(1);
		page2.setPageSize(1);
		// 最新采购订单
		PageDto<OrderBill> cgPage = orderBillService.queryBuyListByCompanyId(companyId, null, page, null, null, null);
		// 最新供应订单
		PageDto<OrderBill> gyPage = orderBillService.querySellListByCompanyId(companyId, null, page2, null, null, null);

		
		//图片及公司链接
		Map<Integer, String> map = new HashMap<Integer, String>();
		Map<Integer, String> maps = new HashMap<Integer, String>();
		this.getOrderPicAndLink(page.getRecords(),out,"S",map,maps);
		this.getOrderPicAndLink(page2.getRecords(),out,"B",map,maps);
		out.put("map", map);
		out.put("maps", maps);

		
		// 新样品
		PageDto<ProductsDto> page3 = new PageDto<ProductsDto>();
		ProductsDO allPro = new ProductsDO();
		allPro.setIsYP(true);
		allPro.setProductsTypeCode("10331000");
		page3.setPageSize(2);
		page3 = productsService.pageProductsBySearchEngine(allPro, null, true, page3);

		// 定制求购样品
		ProductsDO products = new ProductsDO();
		PageDto<ProductsDto> page4 = new PageDto<ProductsDto>();
		products.setIsYP(true);
		products.setProductsTypeCode("10331001");
		page4.setPageSize(2);
		page4 = productsService.pageProductsBySearchEngine(products, null, true, page4);

		
		// 验证是否手机绑定
		AuthUser au = authService.queryAuthUserByUsername(account);
		if (au != null) {
			out.put("au", au);
		}
		// 帐号信息
		CompanyAccount myaccount = companyAccountService.queryAccountByAccount(account);
		out.put("myaccount", myaccount);
		//身份认证
		Identity idnt = identityService.queryIdentityByCompanyId(companyId);
		if (idnt != null) {
			out.put("idnt", idnt);
		}
		// 验证是否微信绑定
		OauthAccess oa = oauthAccessService.queryAccessByAccountAndType(account, OauthAccessService.OPEN_TYPE_WEIXIN);
		if (oa != null) {
			out.put("oa", oa);
		}
		
		//安全级别：登陆密码+交易密码+短信+邮箱+实名+微信  6项，分3等级，每等级2项
		int safeLevel = 1; //登陆密码已设置,所以默认为1.
		if(acc !=null && StringUtils.isNotEmpty(acc.getPayPasswd()))
			safeLevel++;
		if(au !=null && StringUtils.isNotEmpty(au.getMobile()))
			safeLevel++;
		if(myaccount !=null && StringUtils.isNotEmpty(myaccount.getEmail()))
			safeLevel++;
		if(idnt !=null && "01".equals(idnt.getState()))
			safeLevel++;
		if(oa !=null)
			safeLevel++;
		out.put("safeLevel", safeLevel);
		
		//可兑换的积分数
		Integer totalScore = weixinScoreService.totalAvailableScore(account); 
		//已兑换的积分数
		Integer totalScoreEx = weixinPrizelogService.totalConvertScore(account); 
		
		out.put("totalScore", totalScore-totalScoreEx);
		
		out.put("gyypList", page3.getRecords());
		out.put("qgypList", page4.getRecords());
		out.put("account", acc);
		out.put("cgPage", cgPage);
		out.put("gyPage", gyPage);
		return new ModelAndView("/mngr/index");
	}

	/**
	 * 获取各状态订单的统计数
	 * 
	 * @param out
	 */
	private void getOrderCount(Integer companyId, Map<String, Object> out) {
		// bc_;bc_00;bc_11;bc_13;
		orderBillService.queryBuyListByCompanyIdCount(companyId, null, null, null, out);
		// sc_;sc_03;sc_06;sc_14;sc_11;sc_13;
		orderBillService.querySellListByCompanyIdCount(companyId, null, null, null, out);
	}
	
	
	/**
	 * 图片及公司链接
	 * @param page
	 * @param out
	 * @param bs
	 */
	private void getOrderPicAndLink(final List<OrderBill> list, Map<String, Object> out, String bs,Map<Integer, String> map ,Map<Integer, String> maps) {
		if (list != null) {
			for (OrderBill ob : list) {
				List<ProductsPicDO> picList = productsPicService.queryProductPicInfoByProductsIdForFront(ob.getSnapProductId());
				if (picList != null && picList.size() > 0) {
					ob.setSnapPic(picList.get(0).getPicAddress());
				}

				Company company = null;
				if ("S".equalsIgnoreCase(bs)) { // 卖家的公司信息
					company = companyService.queryCompanyById(ob.getSellerId());
					if (company != null) {
						map.put(ob.getSellerId(), company.getMembershipCode());
						maps.put(ob.getSellerId(), company.getDomainZz91());
					}
				} else { // 买家的公司信息
					company = companyService.queryCompanyById(ob.getBuyerId());
					if (company != null) {
						map.put(ob.getBuyerId(), company.getMembershipCode());
						maps.put(ob.getBuyerId(), company.getDomainZz91());
					}
				}
			}
		}
	}

	@RequestMapping
	public ModelAndView orderAll(HttpServletRequest request, Map<String, Object> out, PageDto<OrderBill> page, String state,
			String datefrom, String dateto, String keyword) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		// 各状态订单的统计数
		this.getOrderCount(companyId, out);

		page.setPageSize(5);
		page = orderBillService.queryBuyListByCompanyId(companyId, state, page, datefrom, dateto, keyword);
		
		//图片及公司链接
		Map<Integer, String> map = new HashMap<Integer, String>();
		Map<Integer, String> maps = new HashMap<Integer, String>();
		this.getOrderPicAndLink(page.getRecords(),out,"S",map,maps);
		out.put("map", map);
		out.put("maps", maps);
		
		out.put("page", page);
		out.put("state", state);
		out.put("datefrom", datefrom);
		out.put("dateto", dateto);
		out.put("keyword", keyword);
		return new ModelAndView("/mngr/order_all");
	}

	@RequestMapping
	public ModelAndView saleAll(HttpServletRequest request, Map<String, Object> out, PageDto<OrderBill> page, String state,
			String datefrom, String dateto, String keyword) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		// 各状态订单的统计数
		this.getOrderCount(companyId, out);

		page.setPageSize(5);
		page = orderBillService.querySellListByCompanyId(companyId, state, page, datefrom, dateto, keyword);
		
		//图片及公司链接
		Map<Integer, String> map = new HashMap<Integer, String>();
		Map<Integer, String> maps = new HashMap<Integer, String>();
		this.getOrderPicAndLink(page.getRecords(),out,"B",map,maps);
		out.put("map", map);
		out.put("maps", maps);
		
		
		out.put("page", page);
		out.put("state", state);
		out.put("datefrom", datefrom);
		out.put("dateto", dateto);
		out.put("keyword", keyword);
		return new ModelAndView("/mngr/sale_all");
	}

	/**
	 * 备忘
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param flag
	 * @return
	 */
	@RequestMapping
	public ModelAndView bw(HttpServletRequest request, Map<String, Object> out, Integer id, String flag) {
		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		Custominfo custominfo = custominfoService.selectByOrderSeqAndFlagAndType(bill.getOrderid(), flag, "0");

		out.put("custominfo", custominfo);
		out.put("flag", flag);
		out.put("id", id);
		return new ModelAndView("/mngr/bw");
	}

	/**
	 * 备忘
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param flag
	 * @param infoLevel
	 * @param bwContent
	 * @return
	 */
	@RequestMapping
	public ModelAndView bw_suc(HttpServletRequest request, Map<String, Object> out, Integer id, String flag, String infoLevel,
			String bwContent) {
		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		Custominfo info = custominfoService.selectByOrderSeqAndFlagAndType(bill.getOrderid(), flag, "0");

		if (info == null) {
			info = new Custominfo();
			info.setOrderId(id);
			info.setOrderSeq(bill.getOrderid());
			info.setInfoType("0");
			info.setFlag(flag);
			info.setInfoLevel(infoLevel);
			info.setMemo(bwContent);
			info.setCreateTime(new Date());
			info.setUpdateTime(new Date());
			custominfoService.insert(info);
		} else {
			info.setInfoLevel(infoLevel);
			info.setMemo(bwContent);
			info.setUpdateTime(new Date());
			custominfoService.updateByPrimaryKey(info);
		}

		// 更新备忘级别
		if ("B".equals(flag)) {
			bill.setBuyerMemo(infoLevel);
		} else {
			bill.setSellerMemo(infoLevel);
		}
		orderBillService.updateByPrimaryKey(bill);

		return new ModelAndView("/mngr/bw_suc");
	}

	/**
	 * 留言
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param custominfoId
	 * @param flag
	 * @return
	 */
	@RequestMapping
	public ModelAndView leavewords(HttpServletRequest request, Map<String, Object> out, Integer id, Integer custominfoId, String flag) {
		Custominfo info = custominfoService.selectByPrimaryKey(custominfoId);
		out.put("custominfo", info);
		return new ModelAndView("/mngr/leavewords");
	}

	@RequestMapping
	public ModelAndView account(HttpServletRequest request, Map<String, Object> out) {
		return new ModelAndView("/mngr/account");
	}

	/**
	 * 
	 * 财务概况
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView finance(HttpServletRequest request, Map<String, Object> out) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		// 各状态订单的统计数
		this.getOrderCount(companyId, out);

		Account account = accountService.selectByCompanyId(companyId);
		if (account == null) {
			account = new Account(companyId, new BigDecimal(0), "00", new Date());
			account.setLastupdateTime(new Date());
			accountService.insert(account);
		}

		out.put("account", account);
		
		//身份认证
		Identity idnt = identityService.queryIdentityByCompanyId(companyId);
		out.put("idnt", idnt);
		
		return new ModelAndView("/mngr/finance");
	}

	/**
	 * 支付宝修改
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param aplipayAcc2
	 * @return
	 */
	@RequestMapping
	public ModelAndView addalipay(HttpServletRequest request, Map<String, Object> out, Integer id, String aplipayAcc2) {
		Account account = accountService.selectByPrimaryKey(id);
		if (aplipayAcc2 != null && !aplipayAcc2.equals(account.getAplipayAcc())) {
			account.setAplipayAcc(aplipayAcc2);
			accountService.updateByPrimaryKey(account);
			return new ModelAndView("/mngr/alipay_suc");
		}

		out.put("account", account);
		return new ModelAndView("/mngr/addalipay");
	}

	/**
	 * 提现
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param aplipayAcc2
	 * @return
	 */
	@RequestMapping
	public ModelAndView withdraw(HttpServletRequest request, Map<String, Object> out, Integer id) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		// Account account = accountService.selectByPrimaryKey(id);
		Account account = accountService.selectByCompanyId(companyId);

		out.put("account", account);
		out.put("id", id);
		return new ModelAndView("/mngr/withdraw");
	}

	/**
	 * 密码设置pro
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param aplipayAcc2
	 * @return
	 */
	@RequestMapping
	public ModelAndView passwd(HttpServletRequest request, Map<String, Object> out, Integer id) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		Account account = accountService.selectByCompanyId(companyId);
		if (account == null) {
			account = new Account(companyId, new BigDecimal(0), "00", new Date());
			account.setLastupdateTime(new Date());
			accountService.insert(account);
		}

		out.put("account", account);
		return new ModelAndView("/mngr/passwd");
	}

	/**
	 * 密码设置
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param aplipayAcc2
	 * @return
	 */
	@RequestMapping
	public ModelAndView paypasswd(HttpServletRequest request, Map<String, Object> out, Integer id, String passwd, String repasswd,
			String oldpasswd) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		String msg = "提示：您已成功修改平台支付密码！";

		Account account = accountService.selectByCompanyId(companyId);
		do {
			if (account.getPayPasswd() != null) {
				String oldPass = "";
				if (oldpasswd != null) {
					try {
						oldPass = MD5.encode(oldpasswd);
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				if (!account.getPayPasswd().equals(oldPass)) {
					msg = "旧支付密码有误，不能修改密码！";
					break;
				}
			}

			if (passwd != null && !passwd.trim().equals("") && passwd.equals(repasswd)) {
				String newpass = "";
				try {
					newpass = MD5.encode(passwd);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				account.setPayPasswd(newpass);
				accountService.updateByPrimaryKey(account);
			} else {
				msg = "两次密码不一致！";
			}
		} while (false);

		out.put("msg", msg);
		return new ModelAndView("/mngr/resultInfo");
	}

	/**
	 * 提现历史
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param aplipayAcc2
	 * @return
	 */
	@RequestMapping
	public ModelAndView withdrawlist(HttpServletRequest request, Map<String, Object> out, Integer id) {
		Account account = accountService.selectByPrimaryKey(id);

		out.put("account", account);
		out.put("id", id);
		return new ModelAndView("/mngr/withdrawlist");
	}

	/**
	 * 实名认证 --上传证件
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView checkname(HttpServletRequest request, Map<String, Object> out) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId = null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}
		// 各状态订单的统计数
		this.getOrderCount(companyId, out);
		
		Identity idnt = identityService.queryIdentityByCompanyId(companyId);
		if(idnt!=null && "00".equals(idnt.getState())){
			out.put("msg", "您已经提交过认证信息，实名认证审核中，重新提交将会取消之前的申请！");
		}
		if(idnt!=null && "01".equals(idnt.getState())){
			out.put("msg", "您已经实名认证成功，不需要重新申请认证！");
		}
		
		out.put("identity", idnt);
		return new ModelAndView("/mngr/checkname");
	}

	/**
	 * 实名认证 --上传证件
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView checknameDeal(HttpServletRequest request, Map<String, Object> out, String realName, String identityNo) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId = null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		// 各状态订单的统计数
		this.getOrderCount(companyId, out);

		Identity idnt = identityService.queryIdentityByCompanyId(companyId);

		if (idnt == null || StringUtils.isEmpty(idnt.getScanBackImg()) || StringUtils.isEmpty(idnt.getScanFrontImg())) {
			out.put("msg", "身份证正反面扫描图没有上传，请先上传证件扫描图！");
			if(idnt==null) idnt = new Identity();
			idnt.setRealName(realName);
			idnt.setIdentityNo(identityNo);
			out.put("identity",idnt );
			return new ModelAndView("/mngr/checkname");
		}

		idnt.setRealName(realName);
		idnt.setIdentityNo(identityNo);
		idnt.setState("00");
		identityService.updateByPrimaryKeySelective(idnt);
		
		out.put("identity", idnt);
		out.put("msg", "实名认证信息成功提交，实名认证审核中!");
		return new ModelAndView("/mngr/checkname_rst");
	}

	/**
	 * 收支记录
	 * 
	 * @param request
	 * @param out
	 * @param page
	 * @return
	 */
	@RequestMapping
	public ModelAndView record(HttpServletRequest request, Map<String, Object> out, PageDto<OrderBill> page, Integer dat ,String type ) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		// 各状态订单的统计数
		this.getOrderCount(companyId, out);

		// Company curCmy = companyService.queryCompanyById(companyId);
		page.setPageSize(20);
		String from = null;
		String to = null;
		
		 String rangeState = "'"+OrderState.STATE_13+"','"+OrderState.STATE_17+"'";
		 
		 if( StringUtils.isEmpty(type)){
			 type= "0";
		 }
		 
		// 当天的收支记录
		if (dat == null) {
			from = DateUtil.toString(new Date(), "yyyy-MM-dd");
			to = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), 1), "yyyy-MM-dd");
			page = orderBillService.queryListByCompanyId(companyId, null, rangeState, page, from, to, type);
		} else {
			// 7天之内的收支记录
			if (dat == 7) {
				from = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -6), "yyyy-MM-dd");
				to = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), 1), "yyyy-MM-dd");
				page = orderBillService.queryListByCompanyId(companyId, null, rangeState, page, from, to, type);
			}
			// 1个月的收支记录
			if (dat == 1) {
				from = DateUtil.toString(DateUtil.getDateAfterMonths(new Date(), -1), "yyyy-MM-dd");
				to = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), 1), "yyyy-MM-dd");
				page = orderBillService.queryListByCompanyId(companyId, null, rangeState, page, from, to, type);
			}
			// 3个月的收支记录
			if (dat == 3) {
				from = DateUtil.toString(DateUtil.getDateAfterMonths(new Date(), -3), "yyyy-MM-dd");
				to = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), 1), "yyyy-MM-dd");
				page = orderBillService.queryListByCompanyId(companyId, null, rangeState, page, from, to, type);
			}
			out.put("dat", dat);
		}
		// Company curCmy = companyService.queryCompanyById(companyId);
		// page = orderBillService.queryListByCompanyId(companyId,
		// OrderState.STATE_13, page, null, null);

		List<OrderBill> list = page.getRecords();
		BigDecimal amountIn = new BigDecimal(0);
		BigDecimal amountOut = new BigDecimal(0);
		for (OrderBill bill : list) {
			if (OrderState.STATE_17.equals(bill.getState())) {
				//退款不计算
			} else {
				if (companyId.compareTo(bill.getBuyerId()) == 0) {
					amountOut = amountOut.add(bill.getAmount());
				} else {
					amountIn = amountIn.add(bill.getAmount());
				}
			}
		}

		Account account = accountService.selectByCompanyId(companyId);

		out.put("page", page);
		out.put("companyId", companyId);
		out.put("amountIn", amountIn);
		out.put("amountOut", amountOut);
		out.put("account", account);
		out.put("type", type);
		return new ModelAndView("/mngr/record");
	}

	@RequestMapping
	public ModelAndView address(HttpServletRequest request, Map<String, Object> out, String flag) {
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; // TODO
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		// 各状态订单的统计数
		this.getOrderCount(companyId, out);

		List<Address> addressList = addressService.findListByCompanyIdAndFlag(companyId, flag);
		out.put("addressList", addressList);
		out.put("flag", flag);
		return new ModelAndView("/mngr/address");
	}

	@RequestMapping
	public ModelAndView addressAdd(HttpServletRequest request, Map<String, Object> out, Address address, String flag) {
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null;
		String addresses = "";
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}
		String code = address.getAddressCode();
		if (code.length() >= 12) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 12));
		}
		if (code.length() >= 16) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 16));
		}
		if (code.length() >= 20) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 20));
		}
		addresses = addresses + address.getAddress();
		address.setCompanyId(companyId);
		address.setAddress(addresses);
		address.setAddressCode(code);
		if (address.getIsdefault() == null)
			address.setIsdefault("0");
		addressService.insert(address);

		return new ModelAndView("redirect:/mngr/address.htm?flag=" + flag);
	}

	/**
	 * 设置为默认地址--拿样
	 * 
	 * @param request
	 * @param out
	 * @param flag
	 * @return
	 */
	@RequestMapping
	public ModelAndView addressSetDefault(HttpServletRequest request, Map<String, Object> out, Integer addrId, Integer id) {
		Address addr = addressService.selectByPrimaryKey(addrId);
		addr.setIsdefault("1");
		addressService.updateByPrimaryKey(addr);
		return new ModelAndView("redirect:/mngr/createOrderPro.htm?id=" + id);
	}
	
	
	/**
	 * 设置为默认地址--会员中心
	 * 
	 * @param request
	 * @param out
	 * @param flag
	 * @return
	 */
	@RequestMapping
	public ModelAndView addressSetDflt(HttpServletRequest request, Map<String, Object> out, Integer defaultAddrId, String flag) {
		Address addr = addressService.selectByPrimaryKey(defaultAddrId);
		addr.setIsdefault("1");
		addressService.updateByPrimaryKey(addr);
		return new ModelAndView("redirect:/mngr/address.htm?flag=" + flag);
	}
	
	
	/**
	 * 拿样页地址增加
	 * 
	 * @param request
	 * @param out
	 * @param flag
	 * @return
	 */
	@RequestMapping
	public ModelAndView addressfront(HttpServletRequest request, Map<String, Object> out, String flag) {
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; // TODO
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}
		
		List<Address> addressList = addressService.findListByCompanyIdAndFlag(companyId, flag);
		out.put("addressList", addressList);
		out.put("flag", flag);
		return new ModelAndView("/mngr/addressfront");
	}

	@RequestMapping
	public ModelAndView addressfrontAdd(HttpServletRequest request, Map<String, Object> out, Address address, String flag) {
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; // TODO
		String addresses = "";
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}
		String code = address.getAddressCode();
		if (code.length() >= 12) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 12));
		}
		if (code.length() >= 16) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 16));
		}
		if (code.length() >= 20) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 20));
		}
		addresses = addresses + address.getAddress();
		address.setAddress(addresses);
		address.setAddressCode(code);
		address.setCompanyId(companyId);
		if (address.getIsdefault() == null)
			address.setIsdefault("0");
		addressService.insert(address);

		return new ModelAndView("/mngr/addressfront_suc");
	}

	@RequestMapping
	public ModelAndView addressfrntEditPro(HttpServletRequest request, Map<String, Object> out, Integer id, String flag) {
		Address address = addressService.selectByPrimaryKey(id);
		String addresses = "";
		String code = address.getAddressCode();
		if (code.length() >= 12) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 12));
		}
		if (code.length() >= 16) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 16));
		}
		if (code.length() >= 20) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 20));
		}
		address.setAddress(address.getAddress().replace(addresses, ""));
		out.put("flag", flag);
		out.put("obj", address);
		return new ModelAndView("/mngr/addressfrntEdit");
	}

	@RequestMapping
	public ModelAndView addressfrntEdit(HttpServletRequest request, Map<String, Object> out, Address address, String flag) {
		if (address.getIsdefault() == null) {
			address.setIsdefault("0"); // 1:默认 0：备用
		}
		String addresses = "";
		String code = address.getAddressCode();
		if (code.length() >= 12) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 12));
		}
		if (code.length() >= 16) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 16));
		}
		if (code.length() >= 20) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 20));
		}
		addresses = addresses + address.getAddress();
		address.setAddress(addresses);
		
		addressService.updateByPrimaryKeySelective(address);
		return new ModelAndView("/mngr/addressfrntEdit_suc");
	}
	
	@RequestMapping
	public ModelAndView addressDel(HttpServletRequest request, Map<String, Object> out, Integer id, String flag) {
		addressService.deleteByPrimaryKey(id);
		return new ModelAndView("redirect:/mngr/address.htm?flag=" + flag);
	}

	@RequestMapping
	public ModelAndView addressEditPro(HttpServletRequest request, Map<String, Object> out, Integer id, String flag) {
		// addressService.deleteByPrimaryKey(id);
		Address address = addressService.selectByPrimaryKey(id);
		String addresses = "";
		String code = address.getAddressCode();
		if (code.length() >= 12) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 12));
		}
		if (code.length() >= 16) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 16));
		}
		if (code.length() >= 20) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 20));
		}
		address.setAddress(address.getAddress().replace(addresses, ""));
		out.put("flag", flag);
		out.put("obj", address);
		return new ModelAndView("/mngr/editaddress");
	}

	@RequestMapping
	public ModelAndView addressEdit(HttpServletRequest request, Map<String, Object> out, Address address, String flag) {
		if (address.getIsdefault() == null) {
			address.setIsdefault("0"); // 1:默认 0：备用
		}
		
		String addresses = "";
		String code = address.getAddressCode();
		if (code.length() >= 12) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 12));
		}
		if (code.length() >= 16) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 16));
		}
		if (code.length() >= 20) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 20));
		}
		addresses = addresses + address.getAddress();
		address.setAddress(addresses);
		
		addressService.updateByPrimaryKeySelective(address);
		return new ModelAndView("redirect:/mngr/address.htm?flag=" + flag);
	}

	@RequestMapping
	public ModelAndView safe(HttpServletRequest request, Map<String, Object> out) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		String account =null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
			account = sessionUser.getAccount();
		}

		// 各状态订单的统计数
		this.getOrderCount(companyId, out);
		
		Account acc = accountService.selectByCompanyId(companyId);
		if (acc == null) {
			acc = new Account(companyId, new BigDecimal(0), "00", new Date());
			acc.setLastupdateTime(new Date());
			accountService.insert(acc);
		}
		
		// 验证是否手机绑定
		AuthUser au = authService.queryAuthUserByUsername(account);
		if (au != null) {
			out.put("au", au);
		}

		// 帐号信息
		CompanyAccount myaccount = companyAccountService.queryAccountByAccount(account);
		out.put("myaccount", myaccount);
		
		//身份认证
		Identity idnt = identityService.queryIdentityByCompanyId(companyId);
		
		// 验证是否微信绑定
		OauthAccess oa = oauthAccessService.queryAccessByAccountAndType(account, OauthAccessService.OPEN_TYPE_WEIXIN);
		if (oa != null) {
			out.put("oa", oa);
		}
		
		//安全级别：登陆密码+交易密码+短信+邮箱+实名+微信  6项，分3等级，每等级2项
		int safeLevel = 1; //登陆密码已设置,所以默认为1.
		if(acc !=null && StringUtils.isNotEmpty(acc.getPayPasswd()))
			safeLevel++;
		if(au !=null && StringUtils.isNotEmpty(au.getMobile()))
			safeLevel++;
		if(myaccount !=null && StringUtils.isNotEmpty(myaccount.getEmail()))
			safeLevel++;
		if(idnt !=null && "01".equals(idnt.getState()))
			safeLevel++;
		if(oa !=null)
			safeLevel++;
		out.put("safeLevel", safeLevel);
		
		out.put("account", acc);
		out.put("idnt", idnt);
		return new ModelAndView("/mngr/safe");
	}

	/**
	 * 订单详情
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView orderDetail(HttpServletRequest request, Map<String, Object> out, Integer id) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		OrderBill bill = orderBillService.selectByPrimaryKey(id);

		// 留言
		Custominfo custominfo = custominfoService.selectByOrderSeq(bill.getOrderid());
		// 收货地址
		Address buyerAddr =addressService.selectByPrimaryKey(bill.getBuyerAddrId());
		if (buyerAddr==null) {
			buyerAddr = addressService.queryAimAddressByCompanyId(bill.getBuyerId());
		}

		// 备忘
		String flag = "S";
		if (companyId.equals(bill.getBuyerId())) {
			flag = "B";
		}
		Custominfo bw = custominfoService.selectByOrderSeqAndFlagAndType(bill.getOrderid(), flag, "0");

		//公司链接
		Company sellerCompany = companyService.queryCompanyById(bill.getSellerId());
		Company buyerCompany = companyService.queryCompanyById(bill.getBuyerId());
		out.put("sellerCompany", sellerCompany);
		out.put("buyerCompany", buyerCompany);
		
		//图片
		List<ProductsPicDO> picList = productsPicService.queryProductPicInfoByProductsIdForFront(bill.getSnapProductId());
		if (picList != null && picList.size() > 0) {
			out.put("picList", picList.get(0));
		}
		
		out.put("bill", bill);
		out.put("custominfo", custominfo);
		out.put("buyerAddr", buyerAddr);
		out.put("bw", bw);
		return new ModelAndView("/mngr/orderDetail");
	}

	/**
	 * 申请拿样 mngr/createOrderPro.htm?id=${id}
	 * 
	 * @param request
	 * @param out
	 * @param id
	 *            样品ID
	 * @return
	 */
	@RequestMapping
	public ModelAndView createOrderPro(HttpServletRequest request, Map<String, Object> out, Integer id,String orderSeq) {
		SsoUser sessionUser = getCachedUser(request);
		
		Sample sample = new Sample();
		if (id != null) {
			sample = sampleService.queryByIdOrProductId(id, null);
		}
		
		if(id == null || sample==null||sessionUser==null){
			return new ModelAndView("redirect:/index.htm");
		}
		
		// 不能向自己下订单
		if (sample.getCompanyId().equals(sessionUser.getCompanyId())) {
			return new ModelAndView("redirect:/index.htm");
		}
		
		Integer companyId = null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		List<Address> addressList = addressService.findListByCompanyIdAndFlag(companyId, "B");
		Company sellerCompany = companyService.queryCompanyById(sample.getCompanyId());

		CompanyAccount companyAccount = companyAccountService.queryAccountByCompanyId(sellerCompany.getId());
		Integer productId = sampleRelateProductService.queryBySampleIdForProductId(sample.getId());
		ProductsDto dto = productsService.queryProductsDetailsById(productId);
		setProperties(dto, out);

		out.put("dto", dto);
		out.put("addressList", addressList);
		out.put("sample", sample);
		sample.getTakePrice();
		sample.getSendPrice();

		if (sample.getTakePrice() != null)
			out.put("takePrice", NumberUtil.formatCurrency(sample.getTakePrice(), "#####0.00"));
		else
			out.put("takePrice", 0);

		if (sample.getSendPrice() != null)
			out.put("sendPrice", NumberUtil.formatCurrency(sample.getSendPrice(), "#####0.00"));
		else
			out.put("sendPrice", 0);
		List<ProductsPicDO> picList = productsPicService.queryProductPicInfoByProductsIdForFront(productId);

		if (picList != null && picList.size() > 0) {
			out.put("picList", picList.get(0));
		}

		out.put("sellerCompany", sellerCompany);
		out.put("companyAccount", companyAccount);
		Address sellerAddr = addressService.queryAimAddressByCompanyId(companyId);
		out.put("sellerAddr", sellerAddr);
		
		Company buyerCompany = companyService.queryCompanyById(companyId);
		
		// 根据订单号检索订单是否存在，存在则不新建订单
		OrderBill bill = orderBillService.selectByOrderSeq(orderSeq);
		do {
			if(bill!=null){
				out.put("orderSeq", bill.getOrderid());
				break;
			}
			bill = orderBillService.queryOrderExistByUser(companyId, id);
			if (bill!=null) {
				out.put("orderSeq", bill.getOrderid());
				break;
			}
			
			// 打开页面 即 生成订单
			bill = new OrderBill();
			bill.setSrcId(companyId);
			bill.setSampleId(id);
			bill.setBuyerId(companyId);
			if (StringUtils.isNotEmpty(buyerCompany.getName())) {
				bill.setBuyerName(buyerCompany.getName());
			}
			bill.setAmount(new BigDecimal(0f));
			bill.setVirtualAmount(new BigDecimal(0f));
			bill.setChannelAmount(new BigDecimal(0f));
			bill.setSellerId(sample.getCompanyId());
			bill.setSellerName(sellerCompany.getName());
			bill.setSnapProductId(productId);
			bill.setSnapPic(dto.getCoverPicUrl());
			bill.setNumber(0);
			bill.setWeight(0f);
			bill.setUnitPrice(new BigDecimal(sample.getTakePrice()));
			bill.setTrafficFee(new BigDecimal(sample.getSendPrice()));
			bill.setSnapTitle(dto.getProducts().getTitle());
			if (StringUtils.isEmpty(orderSeq)) {
				orderSeq = OrderUtil.orderSeq();
			}
			bill.setOrderid(orderSeq);// 14位订单序号 yy
			bill.setTranType(TransType.Direct_Pay);// 即时到帐支付 PayBillMMddHHmmss+Random(2)
			bill.setState(OrderState.STATE_02);// 未确认
			// 创建新订单
			orderBillService.insert(bill);
			out.put("orderSeq", orderSeq);
		} while (false);
		out.put("billId", bill.getId());
		return new ModelAndView("/mngr/createOrder");
	}

	/**
	 * 保存订单
	 * 
	 * @param request
	 * @param out
	 * @param sampleId
	 * @param addrId
	 * @param custominfo
	 * @param number
	 * @param orderTitle
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping
	public ModelAndView createOrderPayPro(HttpServletRequest request, Map<String, Object> out, Integer sampleId, Integer addrId,
			Custominfo custominfo, Integer qty_item_1, String orderTitle,String orderSeq,Integer billId,Integer isCashDelivery) {
		Integer number = qty_item_1;
		Integer companyId =null; 
		SsoUser sessionUser = getCachedUser(request);
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}
		if (StringUtils.isEmpty(orderSeq)) {
			return new ModelAndView("redirect:/mngr/createOrderPro"+sampleId+"-o"+orderSeq+".htm");
		}

		Account acc = accountService.selectByCompanyId(companyId);
		if (acc == null) {
			acc = new Account(companyId, new BigDecimal(0), "00", new Date());
			acc.setLastupdateTime(new Date());
			accountService.insert(acc);
		}

		if (custominfo != null && custominfo.getLevel() != null && custominfo.getLevel().trim() != "") {
			custominfo.setOrderSeq(orderSeq);
			custominfo.setInfoType("1");
			custominfo.setFlag("B");
			custominfo.setCreateTime(new Date());
			custominfo.setUpdateTime(new Date());
			custominfoService.insert(custominfo);
		}
		custominfo = custominfoService.selectByOrderSeq(orderSeq);

		Sample sample = sampleService.queryByIdOrProductId(sampleId, null);
		
		if (sample.getTakePrice() == null) {
			sample.setTakePrice(0f);
		}

		if (sample.getSendPrice() == null) {
			sample.setSendPrice(0f);
		}

		// 总金额
		BigDecimal amount = new BigDecimal(sample.getTakePrice() * number);
		if(isCashDelivery!=null && isCashDelivery.intValue()==0 ){
			amount = amount.add(new BigDecimal(sample.getSendPrice()));
		}

		Company sellerCompany = companyService.queryCompanyById(sample.getCompanyId());
		Company buyerCompany = companyService.queryCompanyById(companyId);

		//卖家账户初始化
		Account accSeller = accountService.selectByCompanyId(sample.getCompanyId());
		if (accSeller == null) {
			accSeller = new Account(sample.getCompanyId(), new BigDecimal(0), "00", new Date());
			accSeller.setLastupdateTime(new Date());
			accountService.insert(accSeller);
		}
		
		// 支付联系人
		CompanyAccount buyAccount = companyAccountService.queryAccountByCompanyId(companyId);
		out.put("buyAccount", buyAccount);
		
		OrderBill bill = new OrderBill();
		try {
			/**
			 * 保存订单
			 */
			bill.setSrcId(companyId);
			bill.setSampleId(sampleId);
			bill.setBuyerId(companyId);
			bill.setSellerId(sample.getCompanyId());
			bill.setBuyerName(buyerCompany.getName());
			bill.setSellerName(sellerCompany.getName());
			bill.setNumber(number);
			bill.setUnitPrice(new BigDecimal(sample.getTakePrice()));
			bill.setTrafficFee(new BigDecimal(sample.getSendPrice()));
			if (sample.getWeight() != null) {
				bill.setUnitWeight(Float.valueOf(sample.getWeight()));
				bill.setWeight(Float.valueOf(sample.getWeight() * number));
			}

			Integer productId = sampleRelateProductService.queryBySampleIdForProductId(sample.getId());
			ProductsDto dto = productsService.queryProductsDetailsById(productId);
			bill.setSnapProductId(productId);
			bill.setSnapPic(dto.getCoverPicUrl());
			bill.setSnapTitle(dto.getProducts().getTitle());

			// 总金额
			// BigDecimal amount = new BigDecimal(sample.getTakePrice() *
			// number) ;
			// amount = amount.add(new BigDecimal(sample.getSendPrice()) );
			bill.setAmount(amount);

			// 默认不使用平台帐号金额
			bill.setChannelAmount(amount);
			bill.setVirtualAmount(new BigDecimal(0));

			bill.setBuyerAcc(null);
			bill.setSellerAcc(null);

			bill.setTranType(TransType.Direct_Pay);// 即时到帐支付 PayBill

			bill.setOrderid(orderSeq);// 14位订单序号 yyMMddHHmmss+Random(2)
			bill.setOrdernote("");

			bill.setCreateTime(new Date());
			bill.setExpireTime(DateUtil.addDays(new Date(), 3));// 3日之内未付款，系统将取消订单

			bill.setChannelType(null);
			bill.setChannelSendTime(null);
			bill.setChannelRecvTime(null);
			bill.setChannelRecvSn(null);
			bill.setChannelSendSn(null);
			bill.setChannelRtncode(null);
			bill.setChannelRtnnote(null);

			bill.setState(OrderState.STATE_00);// 待支付
			bill.setUpdateTime(new Date());
			bill.setCloseReason(null);
			bill.setSellerMemo(null);
			bill.setBuyerMemo("");
			bill.setBuyerLeavemsg("");

			if (custominfo != null && custominfo.getId() != null){
				bill.setCustominfoId(custominfo.getId());
			}

			// 收发货地址设置
			Address sellerAddr = addressService.selectDefault(sample.getCompanyId(), "S");

			bill.setBuyerAddrId(addrId);
			bill.setBuyerAddr(setAddress(addrId)); //收货地址快照
			
			if (sellerAddr != null) {
				bill.setSellerAddrId(sellerAddr.getId());
			}

			if(isCashDelivery!=null){
				bill.setIsCashDelivery(isCashDelivery);
			}else{
				bill.setIsCashDelivery(0);
			}
			
			OrderBill bill_ = orderBillService.selectByOrderSeq(orderSeq);
			
			// 是否订单成功了，成功则返回 订单总页
			if (OrderState.STATE_03.equals(bill_.getState())) {
				return new ModelAndView("redirect:/mngr/orderAll.htm");
			}
			
			// 返回订单确认页面
			if (sample.getAmount()<number) {
				return new ModelAndView("redirect:/mngr/createOrderPro"+sampleId+"-o"+orderSeq+".htm");
			}
			
			Integer i = null;
			if (bill_ == null) {
				i = orderBillService.insert(bill);
			}else{
				// 更新订单
				bill.setId(bill_.getId());
				bill.setState(OrderState.STATE_01);
				i = orderBillService.updateByPrimaryKey(bill);
			}
			if (i>0) {
				// 减少库存
				sample.setAmount(sample.getAmount() - number);
				sampleService.editSample(sample);
			}

			out.put("orderBill", bill);

		} catch (Exception e) {
			e.printStackTrace();
		}

		out.put("account", acc);
		out.put("amount", NumberUtil.formatCurrency(bill.getAmount().doubleValue(), "#####0.00"));
		out.put("orderSeq", orderSeq);
		out.put("isCashDelivery", isCashDelivery);
		out.put("sendPrice", NumberUtil.formatCurrency(sample.getSendPrice().doubleValue(), "#####0.00"));
		if(acc.getAmount().doubleValue()>=bill.getAmount().doubleValue()){
			out.put("haveMoney", 1);
		}
		if (StringUtils.isNotEmpty(orderSeq)) {
			out.put("orderSeq", orderSeq);
		}
		return new ModelAndView("/mngr/createOrder2");
	}
	
	/**
	 * 订单地址组装
	 * @param addrId
	 * @return
	 */
	private String setAddress(Integer addrId) {
		if (addrId == null) {
			return "";
		}
		Address addr = addressService.selectByPrimaryKey(addrId);
		if (addr == null) {
			return "";
		}
		
		String addresses = "";
		String code = addr.getAddressCode();
		if (code.length() >= 12) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 12));
		}
		if (code.length() >= 16) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 16));
		}
		if (code.length() >= 20) {
			addresses += CategoryFacade.getInstance().getValue(code.substring(0, 20));
		}
		addresses = addresses + addr.getAddress();
		
		if(StringUtils.isNotEmpty(addr.getContact())){
			addresses = addresses + "  收货人："+addr.getContact();
		}
		
		if(StringUtils.isNotEmpty(addr.getMobile())){
			addresses =addresses+"  "+addr.getMobile();
		}
		
		if(StringUtils.isNotEmpty(addr.getTel())){
			addresses =addresses+"  电话："+addr.getTel();
		}
		
		if(StringUtils.isNotEmpty(addr.getAddressZip())){
			addresses =addresses+"  邮编："+addr.getAddressZip();
		}
		return addresses;
	}
	

	/**
	 * 更新订单
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView orderConfirm(HttpServletRequest request, Map<String, Object> out, boolean isVirtualAmt, String orderSeq) {

		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		OrderBill bill = orderBillService.selectByOrderSeq(orderSeq);
		try {
			/**
			 * 更新订单 是否使用平台虚拟金额
			 */

			BigDecimal amount = bill.getAmount();
			// 默认不使用平台帐号金额
			bill.setChannelAmount(amount);
			bill.setVirtualAmount(new BigDecimal(0));

			// 使用平台帐号金额
			if (isVirtualAmt) {
				Account acc = accountService.selectByCompanyId(companyId);
				if (acc != null && acc.getAmount().compareTo(amount) >= 0) {
					bill.setChannelAmount(new BigDecimal(0));
					bill.setVirtualAmount(amount);
				} else if (acc != null && acc.getAmount().compareTo(amount) < 0) {
					bill.setChannelAmount(amount.subtract(acc.getAmount()));
					bill.setVirtualAmount(acc.getAmount());
				}
			}

			if (OrderState.STATE_00.equals(bill.getState()) || OrderState.STATE_01.equals(bill.getState()))
				orderBillService.updateByPrimaryKeySelective(bill);

			out.put("orderBill", bill);

		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * 总交易金额==1，跳转到支付成功
		 */
		if (bill.getAmount().compareTo(new BigDecimal(0)) == 0) {
			if (OrderState.STATE_00.equals(bill.getState()) || OrderState.STATE_01.equals(bill.getState())) {
				this.orderConfirmNotChannel(bill);
			}
			return new ModelAndView("/mngr/orderConfirmNotChannel");
		}

		/**
		 * 总交易金额 ！=0 ；渠道交易金额==1，跳转到非渠道支付
		 */
		if (bill.getChannelAmount().compareTo(new BigDecimal(0)) == 0) {
			Account acc = accountService.selectByCompanyId(bill.getBuyerId());
			out.put("account", acc);
			out.put("amount", bill.getVirtualAmount());
			out.put("leaveAmt", acc.getAmount().subtract(bill.getVirtualAmount()));
			return new ModelAndView("/mngr/orderNotChannel");
		}

		OrderDto orderDto = new OrderDto();
		orderDto.setOrderSeq(orderSeq);
		orderDto.setOrderBill(bill);
		orderDto.setOrderSubject(bill.getSnapTitle());
		orderDto.setTotalAmount(bill.getChannelAmount().toString());

//		orderjumpAlipay(orderDto, out);
		return orderjumpAlipay(orderDto, out);
	}
	
	/**
	 * zz91支付页面成功回调页面
	 * @return
	 */
	@RequestMapping
	public ModelAndView zz91PayReturnUrl(String orderSeq,Map<String, Object> out,HttpServletRequest request){
		do {
			if (request.getParameter("r6_Order")==null) {
				break;
			}
			if (request.getParameter("r1_Code")==null) {
				break;
			}
			String orderBillId = request.getParameter("r6_Order").toString();
			OrderBill bill = orderBillService.selectByOrderSeq(orderBillId);
			String isSuccess = request.getParameter("r1_Code").toString();
			if ("1".equals(isSuccess)) {
				/**
				 * 总交易金额==1，跳转到支付成功
				 */
				if (OrderState.STATE_00.equals(bill.getState()) || OrderState.STATE_01.equals(bill.getState())) {
					bill.setState(OrderState.STATE_03);
					bill.setUpdateTime(new Date());
					orderBillService.updateByPrimaryKey(bill);
					
					/**
					 * 发送短信信息
					 */
					String str = "已付款，请您尽快安排发货";

					CompanyAccount ca = companyAccountService.queryAccountByCompanyId(bill.getSellerId());
					do {
						if (ca==null) {
							break;
						}
						SampleMsgset sampleMsgset= sampleMsgsetService.queryByCompanyId(bill.getSellerId());
						if(sampleMsgset ==null || sampleMsgset.getSms()==1)	{
							if (ca != null && StringUtils.isNotEmpty(ca.getMobile())) {
								String orderTitle = org.apache.commons.lang.StringUtils.substring(bill.getSnapTitle(), 0, 8);
								String orderId = bill.getOrderid();
								CompanyAccount caBuy = companyAccountService.queryAccountByCompanyId(bill.getBuyerId());
								String contact = caBuy.getContact();
								
								// 发送短信
								// s1.您有一笔来自买家康先生的样品订单（PP再生颗粒，订单号：14072314124676）买家已付款，请您尽快安排发货
								//您有一笔来自买家{0}的样品订单（{1}，订单号：{2}）买家{3}
								SmsUtil.getInstance().sendSms("sms_sample_seller", ca.getMobile(), null, null, new String[] {contact, orderTitle, orderId, str });
							}
						}
						
						// * 发送邮件提醒信息 link-s1
						if(sampleMsgset ==null || 0 == sampleMsgset.getEmail()){
							if (StringUtils.isNotEmpty(ca.getUseEmail())) {
								Map<String, Object> paramMap = new HashMap<String, Object>();
								paramMap.put("bill", bill);
								paramMap.put("createTime", DateUtil.toString( bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
								paramMap.put("stateName", "买家已付款，等待卖家发货");
								paramMap.put("stateAndDeal", "买家已付款，请您尽快安排发货");
								MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒", ca.getUseEmail(), null, new Date(), "zz91","sample_order_seller", paramMap, MailUtil.PRIORITY_HEIGHT);
							}
						}
					} while (false);
					
					/**
					 * 付款成功，增加积分
					 */
					CompanyAccount caBuy = companyAccountService.queryAccountByCompanyId(bill.getBuyerId());
					orderBillService.increaseScore(caBuy);
				}
				return new ModelAndView("/mngr/orderConfirmNotChannel");
			}
		} while (false);
		OrderBill obj = orderBillService.selectByOrderSeq(orderSeq);
		if (obj!=null) {
			Integer sampleId = obj.getSampleId();
			return new ModelAndView("redirect:/mngr/createOrderPro"+sampleId+"-o"+orderSeq+".htm");
		}else{
			return new ModelAndView();
		}
	}

	@RequestMapping
	public ModelAndView orderConfirmNotChannel(HttpServletRequest request, Map<String, Object> out, String orderid, String payPasswd) {
		OrderBill bill = orderBillService.selectByOrderSeq(orderid);
		Account acc = accountService.selectByCompanyId(bill.getBuyerId());

		String encodepwd = "";
		if (payPasswd != null && !payPasswd.trim().equals("")) {
			try {
				encodepwd = MD5.encode(payPasswd);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if (payPasswd == null || payPasswd.trim().equals("") || !encodepwd.equals(acc.getPayPasswd())) {
			out.put("account", acc);
			out.put("orderBill", bill);
			out.put("amount", bill.getVirtualAmount());
			out.put("leaveAmt", acc.getAmount().subtract(bill.getVirtualAmount()));
			out.put("msg", "交易密码错误,请重新输入！");
			return new ModelAndView("/mngr/orderNotChannel");
		}

		if (OrderState.STATE_00.equals(bill.getState()) || OrderState.STATE_01.equals(bill.getState())) {
			this.orderConfirmNotChannel(bill); // 更新订单
		}

		out.put("orderBill", bill);
		out.put("account", acc);
		out.put("amount", bill.getVirtualAmount());
		return new ModelAndView("/mngr/orderConfirmNotChannel");
	}

	/**
	 * 非渠道支付 (1.虚拟账户支付 2.免费支付)
	 * 
	 * @param bill
	 */
	public void orderConfirmNotChannel(OrderBill bill) {
		// 1.全部虚拟账户支付
		if (bill.getChannelAmount().compareTo(new BigDecimal(0)) == 0 && bill.getVirtualAmount().compareTo(new BigDecimal(0)) == 1) {
			try {
				orderBillService.dealTradeNotChannel(bill);
			} catch (BizException e) {
				e.printStackTrace();
			}
		}

		// 2.免费支付
		if (bill.getChannelAmount().compareTo(new BigDecimal(0)) == 0 && bill.getVirtualAmount().compareTo(new BigDecimal(0)) == 0) {
			bill.setState(OrderState.STATE_03);
			bill.setUpdateTime(new Date());
			orderBillService.updateByPrimaryKey(bill);
			
			/**
			 * 发送短信信息
			 */
			String str = "已付款，请您尽快安排发货";

			CompanyAccount ca = companyAccountService.queryAccountByCompanyId(bill.getSellerId());
			do {
				if (ca==null) {
					break;
				}
				SampleMsgset sampleMsgset= sampleMsgsetService.queryByCompanyId(bill.getSellerId());
				if(sampleMsgset ==null || sampleMsgset.getSms()==1)	{
					if (ca != null && StringUtils.isNotEmpty(ca.getMobile())) {
						String orderTitle = org.apache.commons.lang.StringUtils.substring(bill.getSnapTitle(), 0, 8);
						String orderId = bill.getOrderid();
						CompanyAccount caBuy = companyAccountService.queryAccountByCompanyId(bill.getBuyerId());
						String contact = caBuy.getContact();
						
						// 发送短信
						// s1.您有一笔来自买家康先生的样品订单（PP再生颗粒，订单号：14072314124676）买家已付款，请您尽快安排发货
						//您有一笔来自买家{0}的样品订单（{1}，订单号：{2}）买家{3}
						SmsUtil.getInstance().sendSms("sms_sample_seller", ca.getMobile(), null, null, new String[] {contact, orderTitle, orderId, str });
					}
				}
				
				// * 发送邮件提醒信息 link-s1
				if(sampleMsgset ==null || 0 == sampleMsgset.getEmail()){
					if (StringUtils.isNotEmpty(ca.getUseEmail())) {
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("bill", bill);
						paramMap.put("createTime", DateUtil.toString( bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
						paramMap.put("stateName", "买家已付款，等待卖家发货");
						paramMap.put("stateAndDeal", "买家已付款，请您尽快安排发货");
						MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒", ca.getUseEmail(), null, new Date(), "zz91","sample_order_seller", paramMap, MailUtil.PRIORITY_HEIGHT);
					}
				}
			} while (false);
			
			/**
			 * 付款成功，增加积分
			 */
			CompanyAccount caBuy = companyAccountService.queryAccountByCompanyId(bill.getBuyerId());
			orderBillService.increaseScore(caBuy);
			
		}
	}

	/**
	 * 
	 * @param orderDto
	 * @param channelType
	 * @return
	 */
	public OrderDto putChannelparam(OrderDto dto, String channelType) {
		if (dto == null)
			dto = new OrderDto();

		if (ChannelConst.CHANNEL_TENPAY.equals(channelType)) {
			Map<String, String> paramMap = this.queryParamsMapByTypes("tenpay");
			// 接受回执URL
			String notifyUrl = paramMap.get("notifyUrl");
			// 返回跳转URL
			String returnUrl = paramMap.get("returnUrl");
			// 收款方
			String spname = paramMap.get("spname");
			// 商户号
			String partner = paramMap.get("partner");
			// 密钥
			String key = paramMap.get("key");

			String astoAccount = paramMap.get("astoAccount");

			dto.setNotifyUrl(notifyUrl);
			dto.setReturnUrl(returnUrl);
			dto.setSpname(spname);
			dto.setPartner(partner);
			dto.setKey(key);
			dto.setAstoAccount(astoAccount);
		} else if (ChannelConst.CHANNEL_ALIPAY.equals(channelType)) {
			Map<String, String> paramMap = this.queryParamsMapByTypes("alipay");
			// 接受回执URL
			String notifyUrl = paramMap.get("notifyUrl");
			if (notifyUrl == null || notifyUrl.trim().equals("")) {
				notifyUrl = "http://test_apps.zz91.com/yang/alipayNotifyUrl.htm";
			}

			// 返回跳转URL
			String returnUrl = paramMap.get("returnUrl");
			if (returnUrl == null || returnUrl.trim().equals("")) {
				returnUrl = "http://test_apps.zz91.com/yang/mngr/alipayReturnUrl.htm";
			}

			// 合作身份者ID，以2088开头由16位纯数字组成的字符串
			String partner = paramMap.get("partner");
			// 商户的私钥
			String key = paramMap.get("key");

			String astoAccount = paramMap.get("astoAccount");
			if (astoAccount == null || astoAccount.trim().equals("")) {
				astoAccount = "zhifu@asto-inc.com";
			}

			dto.setNotifyUrl(notifyUrl);
			dto.setReturnUrl(returnUrl);
			dto.setPartner(partner);
			dto.setKey(key);
			dto.setAstoAccount(astoAccount);
		}
		return dto;
	}

	/**
	 * 订单提交到支付渠道
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView orderSubmitToChannel(HttpServletRequest request, Map<String, Object> out, OrderBill orderbill, String channelType) {

		OrderDto dto = new OrderDto();
		putChannelparam(dto, channelType);
		dto.setOrderSeq(orderbill.getOrderid());
		dto.setOrderDes(orderbill.getOrdernote());
		dto.setOrderSubject("");
		dto.setShowUrl(null);

		dto.setOrderBill(orderbill);

		out.put("orderDto", dto);
		// TODO 通过channelType 来跳转具体渠道
		return new ModelAndView();
	}

	/**
	 * 
	 * @param types
	 * @return
	 */
	public Map<String, String> queryParamsMapByTypes(final String types) {
		final List<Param> retList = paramService.listParamByTypes(types);
		final Map<String, String> sampleParam = new HashMap<String, String>(retList.size());
		for (final Param param : retList) {
			sampleParam.put(param.getKey(), param.getValue());
		}
		return sampleParam;
	}

	/**
	 * 支付宝 导向界面
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView orderAlipay(HttpServletRequest request, Map<String, Object> out) {
		return new ModelAndView("/mngr/orderAlipay");
	}

	/**
	 * 支付宝 跳转页
	 * 
	 * @param request
	 * @param orderDto
	 * @param out
	 * @return
	 */
	public ModelAndView orderjumpAlipay(OrderDto orderDto, Map<String, Object> out) {

		this.putChannelparam(orderDto, ChannelConst.CHANNEL_ALIPAY);

		// 支付类型
		String payment_type = "1";

		// 防钓鱼时间戳
		String anti_phishing_key = "";
		// 若要使用请调用类文件submit中的query_timestamp函数

		// 客户端的IP地址
		String exter_invoke_ip = "";
		// 非局域网的外网IP地址，如：221.0.0.1

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", orderDto.getNotifyUrl());
		sParaTemp.put("return_url", orderDto.getReturnUrl());
		sParaTemp.put("seller_email", orderDto.getAstoAccount());
		sParaTemp.put("out_trade_no", orderDto.getOrderSeq());
		sParaTemp.put("subject", orderDto.getOrderSubject());
		sParaTemp.put("total_fee", orderDto.getTotalAmount());
		sParaTemp.put("body", orderDto.getOrderDes());
		sParaTemp.put("show_url", orderDto.getShowUrl());
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		out.put("sHtmlText", sHtmlText);

		/**
		 * 更新渠道流水
		 */
		// updateOrderStateForSending( orderDto,ChannelConst.CHANNEL_ALIPAY) ;

		return new ModelAndView("/mngr/orderjumpAlipay");
	}

	/**
	 * 跳转到支付渠道时，更新订单状态
	 */
	private void updateOrderStateForSending(OrderDto orderDto, String channelType) {
		/**
		 * 更新渠道流水
		 */
		OrderBill bill = orderDto.getOrderBill();

		bill.setChannelType(channelType);
		bill.setChannelSendTime(new Date());
		bill.setChannelRecvTime(null);
		bill.setChannelRecvSn(null);
		bill.setChannelSendSn(bill.getOrderid());
		bill.setChannelRtncode(null);
		bill.setChannelRtnnote(null);

		bill.setState(OrderState.STATE_01);// 支付中
		bill.setUpdateTime(new Date());
		bill.setCloseReason(null);
		bill.setSellerMemo(null);
		bill.setBuyerMemo(orderDto.getOrderDes());
		bill.setBuyerLeavemsg("");
		orderBillService.updateByPrimaryKey(bill);
	}

	/**
	 * 财付通 导向界面
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView orderTenpay(HttpServletRequest request, Map<String, Object> out) {

		/**
		 * 订单序号
		 */
		String strReq = OrderUtil.orderSeq();

		// 收款方
		String spname = "财付通双接口测试";
		out.put("strReq", strReq);
		out.put("spname", spname);
		return new ModelAndView("/mngr/orderTenpay");
	}

	/**
	 * 财付通 跳转
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView orderjumpTenpay(HttpServletRequest request, HttpServletResponse response, OrderDto dto, Map<String, Object> out) {

		this.putChannelparam(dto, ChannelConst.CHANNEL_TENPAY);

		/**
		 * 收款方信息
		 */
		// 收款方
		//String spname = dto.getSpname();
		// 商户号
		String partner = dto.getPartner();
		// 密钥
		String key = dto.getKey();
		// 交易完成后跳转的URL
		String return_url = dto.getReturnUrl();
		// 接收财付通通知的URL
		String notify_url = dto.getNotifyUrl();

		// ---------------------------------------------------------
		// 财付通网关支付请求示例，商户按照此文档进行开发即可
		// ---------------------------------------------------------
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 获取提交的商品价格
		String order_price = request.getParameter("order_price");
		/* 商品价格（包含运费），以分为单位 */
		double total_fee = (Double.valueOf(order_price) * 100);
		int fee = (int) total_fee;

		// 获取提交的商品名称
		String product_name = request.getParameter("product_name");

		// 获取提交的备注信息
		String remarkexplain = request.getParameter("remarkexplain");

		String desc = "商品：" + product_name + ",备注：" + remarkexplain;

		// 获取提交的订单号
		String out_trade_no = request.getParameter("order_no");

		// 支付方式
		String trade_mode = "1";

		// ---------------生成订单号 开始------------------------
		// 当前时间 yyyyMMddHHmmss
		// String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		// String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		// String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		// String strReq = strTime + strRandom;
		// 订单号，此处用时间加随机数生成，商户根据自己情况调整，只要保持全局唯一就行
		// String out_trade_no = strReq;
		// ---------------生成订单号 结束------------------------

		String currTime = TenpayUtil.getCurrTime();
		// 创建支付请求对象
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init();
		// 设置密钥
		reqHandler.setKey(key);
		// 设置支付网关
		reqHandler.setGateUrl("https://gw.tenpay.com/gateway/pay.htm");

		// -----------------------------
		// 设置支付参数
		// -----------------------------
		reqHandler.setParameter("partner", partner); // 商户号
		reqHandler.setParameter("out_trade_no", out_trade_no); // 商家订单号
		reqHandler.setParameter("total_fee", String.valueOf(fee)); // 商品金额,以分为单位
		reqHandler.setParameter("return_url", return_url); // 交易完成后跳转的URL
		reqHandler.setParameter("notify_url", notify_url); // 接收财付通通知的URL
		reqHandler.setParameter("body", desc); // 商品描述
		reqHandler.setParameter("bank_type", "DEFAULT"); // 银行类型(中介担保时此参数无效)
		reqHandler.setParameter("spbill_create_ip", request.getRemoteAddr()); // 用户的公网ip，不是商户服务器IP
		reqHandler.setParameter("fee_type", "1"); // 币种，1人民币
		reqHandler.setParameter("subject", desc); // 商品名称(中介交易时必填)

		// 系统可选参数
		reqHandler.setParameter("sign_type", "MD5"); // 签名类型,默认：MD5
		reqHandler.setParameter("service_version", "1.0"); // 版本号，默认为1.0
		reqHandler.setParameter("input_charset", "UTF-8"); // 字符编码
		reqHandler.setParameter("sign_key_index", "1"); // 密钥序号

		// 业务可选参数
		reqHandler.setParameter("attach", ""); // 附加数据，原样返回
		reqHandler.setParameter("product_fee", ""); // 商品费用，必须保证transport_fee +
													// product_fee=total_fee
		reqHandler.setParameter("transport_fee", "0"); // 物流费用，必须保证transport_fee
														// +
														// product_fee=total_fee
		reqHandler.setParameter("time_start", currTime); // 订单生成时间，格式为yyyymmddhhmmss
		reqHandler.setParameter("time_expire", ""); // 订单失效时间，格式为yyyymmddhhmmss
		reqHandler.setParameter("buyer_id", ""); // 买方财付通账号
		reqHandler.setParameter("goods_tag", ""); // 商品标记
		reqHandler.setParameter("trade_mode", trade_mode); // 交易模式，1即时到账(默认)，2中介担保，3后台选择（买家进支付中心列表选择）
		reqHandler.setParameter("transport_desc", ""); // 物流说明
		reqHandler.setParameter("trans_type", "1"); // 交易类型，1实物交易，2虚拟交易
		reqHandler.setParameter("agentid", ""); // 平台ID
		reqHandler.setParameter("agent_type", ""); // 代理模式，0无代理(默认)，1表示卡易售模式，2表示网店模式
		reqHandler.setParameter("seller_id", ""); // 卖家商户号，为空则等同于partner

		// 请求的url
		String requestUrl = "";
		try {
			requestUrl = reqHandler.getRequestURL();
		} catch (UnsupportedEncodingException e) {
		}

		// 获取debug信息,建议把请求和debug信息写入日志，方便定位问题
//		String debuginfo = reqHandler.getDebugInfo();
//		System.out.println("requestUrl:  " + requestUrl);
//		System.out.println("sign_String:  " + debuginfo);
		out.put("requestUrl", requestUrl);

		/**
		 * 更新渠道流水
		 */
		updateOrderStateForSending(dto, ChannelConst.CHANNEL_TENPAY);

		return new ModelAndView("/mngr/orderjumpTenpay");
	}

	/**
	 * 交易完成后 处理回调
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView alipayReturnUrl(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out)
			throws UnsupportedEncodingException {
		log.info("===========alipayReturnUrl=====start===========");

//		OrderDto dto = this.putChannelparam(null, ChannelConst.CHANNEL_ALIPAY);
		String msg = "";

		// 获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
			log.info("===========" + name + "=" + valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

		// 支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

		// 交易金额
		String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");

		// 卖方支付宝账户
		String seller_email = new String(request.getParameter("seller_email").getBytes("ISO-8859-1"), "UTF-8");

		// 买方支付宝账户
		String buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"), "UTF-8");

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);

		if (verify_result) {// 验证成功
			// ////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				ReturnState rs = new ReturnState();
				rs.setOrderSeq(out_trade_no);
				rs.setChannelRecvSn(trade_no);
				rs.setChannelType(ChannelConst.CHANNEL_ALIPAY);
				rs.setRelTranAmount(Double.parseDouble(total_fee));
				rs.setChannelRetCode("0000");

				rs.setBuyerAcc(buyer_email);
				rs.setSellerAcc(seller_email);

				try {
					orderBillService.dealCallback(rs);
				} catch (BizException e) {
					e.printStackTrace();
					msg = e.getMessage();
				}

			}

			// 该页面可做页面美工编辑
			msg = "验证成功" + " " + msg;
			// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

			// ////////////////////////////////////////////////////////////////////////////////////////
		} else {
			// 该页面可做页面美工编辑
			msg = "验证失败";
		}

		out.put("msg", msg);

		log.info("===========alipayReturnUrl=====end===========");
		return new ModelAndView("/mngr/orderConfirmNotChannel");
	}



	/**
	 * 交易完成后 处理回调
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView tenpayReturnUrl(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out) {
		OrderDto dto = this.putChannelparam(null, ChannelConst.CHANNEL_TENPAY);

		// 密钥
		String key = dto.getKey();

		String msg = "";

		// ---------------------------------------------------------
		// 财付通支付应答（处理回调）示例，商户按照此文档进行开发即可
		// ---------------------------------------------------------

		// 创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey(key);

//		System.out.println("前台回调返回参数:" + resHandler.getAllParameters());

		// 判断签名
		if (resHandler.isTenpaySign()) {

			// 通知id
//			String notify_id = resHandler.getParameter("notify_id");
			// 商户订单号
			String out_trade_no = resHandler.getParameter("out_trade_no");
			// 财付通订单号
//			String transaction_id = resHandler.getParameter("transaction_id");
			// 金额,以分为单位
			String total_fee = resHandler.getParameter("total_fee");
			// 如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
			String discount = resHandler.getParameter("discount");
			// 支付结果
			String trade_state = resHandler.getParameter("trade_state");
			// 交易模式，1即时到账，2中介担保
			String trade_mode = resHandler.getParameter("trade_mode");

			if ("1".equals(trade_mode)) { // 即时到账
				if ("0".equals(trade_state)) { // 前台回调返回参数
					// ------------------------------
					// 即时到账处理业务开始
					// ------------------------------

					ReturnState rs = new ReturnState();
					rs.setOrderSeq(out_trade_no);
					rs.setRelTranAmount((Double.parseDouble(total_fee) + Double.parseDouble(discount)) / 100);
					rs.setChannelRetCode("0000");
					try {
						orderBillService.dealCallback(rs);
					} catch (BizException e) {
						e.printStackTrace();
					}

					// 注意交易单不要重复处理
					// 注意判断返回金额

					// ------------------------------
					// 即时到账处理业务完毕
					// ------------------------------
					msg = "即时到帐付款成功";
//					System.out.println("即时到帐付款成功");
				} else {
					msg = "即时到帐付款失败";
//					System.out.println("即时到帐付款失败");
				}
			} else if ("2".equals(trade_mode)) { // 中介担保
				if ("0".equals(trade_state)) {
					// ------------------------------
					// 中介担保处理业务开始
					// ------------------------------

					// 注意交易单不要重复处理
					// 注意判断返回金额

					// ------------------------------
					// 中介担保处理业务完毕
					// ------------------------------

					msg = "中介担保付款成功";
//					System.out.println("中介担保付款成功");
				} else {
					msg = "trade_state=" + trade_state;
				}
			}
		} else {
			msg = "认证签名失败";
		}

		// 获取debug信息,建议把debug信息写入日志，方便定位问题
		String debuginfo = resHandler.getDebugInfo();
//		System.out.println("debuginfo:" + debuginfo);
		// out.print("sign_String:  " + debuginfo + "<br><br>");

		out.put("debuginfo", debuginfo);
		out.put("msg", msg);
		return new ModelAndView();
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView tenpayNotifyUrl(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out) throws Exception {
		OrderDto dto = this.putChannelparam(null, ChannelConst.CHANNEL_TENPAY);
		// 商户号
		String partner = dto.getPartner();
		// 密钥
		String key = dto.getKey();

//		String msg = "";

		// ---------------------------------------------------------
		// 财付通支付通知（后台通知）示例，商户按照此文档进行开发即可
		// ---------------------------------------------------------

		// 创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey(key);

		out.put("m1", "后台回调返回参数:" + resHandler.getAllParameters());
//		System.out.println("后台回调返回参数:" + resHandler.getAllParameters());

		// 判断签名
		if (resHandler.isTenpaySign()) {

			// 通知id
			String notify_id = resHandler.getParameter("notify_id");

			// 创建请求对象
			RequestHandler queryReq = new RequestHandler(null, null);
			// 通信对象
			TenpayHttpClient httpClient = new TenpayHttpClient();
			// 应答对象
			ClientResponseHandler queryRes = new ClientResponseHandler();

			// 通过通知ID查询，确保通知来至财付通
			queryReq.init();
			queryReq.setKey(key);
			queryReq.setGateUrl("https://gw.tenpay.com/gateway/simpleverifynotifyid.xml");
			queryReq.setParameter("partner", partner);
			queryReq.setParameter("notify_id", notify_id);

			// 通信对象
			httpClient.setTimeOut(5);
			// 设置请求内容
			httpClient.setReqContent(queryReq.getRequestURL());
//			System.out.println("验证ID请求字符串:" + queryReq.getRequestURL());
			out.put("m2", "验证ID请求字符串:" + queryReq.getRequestURL());

			// 后台调用
			if (httpClient.call()) {
				// 设置结果参数
				queryRes.setContent(httpClient.getResContent());
//				System.out.println("验证ID返回字符串:" + httpClient.getResContent());
				out.put("m3", "验证ID返回字符串:" + httpClient.getResContent());
				queryRes.setKey(key);

				// 获取id验证返回状态码，0表示此通知id是财付通发起
				String retcode = queryRes.getParameter("retcode");

				// 商户订单号
//				String out_trade_no = resHandler.getParameter("out_trade_no");
				// 财付通订单号
//				String transaction_id = resHandler.getParameter("transaction_id");
				// 金额,以分为单位
//				String total_fee = resHandler.getParameter("total_fee");
				// 如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
//				String discount = resHandler.getParameter("discount");
				// 支付结果
				String trade_state = resHandler.getParameter("trade_state");
				// 交易模式，1即时到账，2中介担保
				String trade_mode = resHandler.getParameter("trade_mode");

				// 判断签名及结果
				if (queryRes.isTenpaySign() && "0".equals(retcode)) {
//					System.out.println("id验证成功");
					out.put("m4", "id验证成功");

					if ("1".equals(trade_mode)) { // 即时到账
						if ("0".equals(trade_state)) {
							// ------------------------------
							// 即时到账处理业务开始
							// ------------------------------

							// 处理数据库逻辑
							// 注意交易单不要重复处理
							// 注意判断返回金额

							// ------------------------------
							// 即时到账处理业务完毕
							// ------------------------------
							out.put("m5", "即时到账支付成功");
//							System.out.println("即时到账支付成功");
							// 给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
							resHandler.sendToCFT("success");

						} else {
							out.put("m6", "即时到账支付失败");
//							System.out.println("即时到账支付失败");
							resHandler.sendToCFT("fail");
						}
					} else if ("2".equals(trade_mode)) { // 中介担保
						// ------------------------------
						// 中介担保处理业务开始
						// ------------------------------

						// 处理数据库逻辑
						// 注意交易单不要重复处理
						// 注意判断返回金额

						int iStatus = TenpayUtil.toInt(trade_state);
						switch (iStatus) {
						case 0: // 付款成功

							break;
						case 1: // 交易创建

							break;
						case 2: // 收获地址填写完毕

							break;
						case 4: // 卖家发货成功

							break;
						case 5: // 买家收货确认，交易成功

							break;
						case 6: // 交易关闭，未完成超时关闭

							break;
						case 7: // 修改交易价格成功

							break;
						case 8: // 买家发起退款

							break;
						case 9: // 退款成功

							break;
						case 10: // 退款关闭

							break;
						default:
						}

						// ------------------------------
						// 中介担保处理业务完毕
						// ------------------------------

						out.put("m7", "trade_state = " + trade_state);
//						System.out.println("trade_state = " + trade_state);
						// 给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
						resHandler.sendToCFT("success");
					}
				} else {
					// 错误时，返回结果未签名，记录retcode、retmsg看失败详情。
					out.put("m8", "查询验证签名失败或id验证失败" + ",retcode:" + queryRes.getParameter("retcode"));
//					System.out.println("查询验证签名失败或id验证失败" + ",retcode:" + queryRes.getParameter("retcode"));
				}
			} else {
				out.put("m9", "后台调用通信失败");
				out.put("m10", httpClient.getResponseCode());
				out.put("m11", httpClient.getErrInfo());

//				System.out.println("后台调用通信失败");
//				System.out.println(httpClient.getResponseCode());
//				System.out.println(httpClient.getErrInfo());
				// 有可能因为网络原因，请求已经处理，但未收到应答。
			}
		} else {
			out.put("m11", "通知签名验证失败");
//			System.out.println("通知签名验证失败");
		}

		return new ModelAndView();
	}

	/**
	 * 未付款之前，买家订单取消
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView cancelOrderBybuyerPro(HttpServletRequest request, Map<String, Object> out, Integer id) {
		out.put("id", id);
		return new ModelAndView("/mngr/cancelOrder");
	}

	/**
	 * 未付款之前，买家订单取消
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView cancelOrderBybuyer(HttpServletRequest request, Map<String, Object> out, Integer id, String cancelReson, String memo) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		String msg = "取消订单成功！";

		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		if (bill.getBuyerId().equals(companyId)) {
			try {
				this.cancelOrderBybuyer(bill, cancelReson +  ("" != memo ? (" 备注:" + memo) : ""));
				
				/**
				 * 发送短信信息
				 */
				String str = "已取消订单（"+bill.getCloseReason()+"）";

				CompanyAccount ca = companyAccountService.queryAccountByCompanyId(bill.getSellerId());
				do {
					if (ca==null) {
						break;
					}
					
					SampleMsgset sampleMsgset= sampleMsgsetService.queryByCompanyId(bill.getSellerId());
					if(sampleMsgset ==null || sampleMsgset.getSms()==1)	{
						if (StringUtils.isNotEmpty(ca.getMobile())) {
							String orderTitle = org.apache.commons.lang.StringUtils.substring(bill.getSnapTitle(), 0, 8);
							String orderId = bill.getOrderid();
							CompanyAccount caBuy = companyAccountService.queryAccountByCompanyId(bill.getBuyerId());
							String contact = caBuy.getContact();
							// 发送短信
							//   s3.您有一笔来自买家康先生的样品订单（PP再生颗粒，订单号：14072314124676）买家已取消订单（理由：买家取消订单）
							//您有一笔来自买家{0}的样品订单（{1}，订单号：{2}）买家{3}
							SmsUtil.getInstance().sendSms("sms_sample_seller", ca.getMobile(), null, null, new String[] {contact, orderTitle, orderId, str });
						}
					}
					
					/**
					 * 发送邮件提醒信息 link-s3
					 */
					if(sampleMsgset==null || 1==sampleMsgset.getEmail()){
						if (StringUtils.isNotEmpty(ca.getUseEmail())) {
							Map<String, Object> paramMap = new HashMap<String, Object>();
							paramMap.put("bill", bill);
							paramMap.put("createTime", DateUtil.toString( bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
							paramMap.put("stateName", str);
							paramMap.put("stateAndDeal", "买家已取消订单");
							MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒", ca.getUseEmail(), null, new Date(), "zz91","sample_order_seller", paramMap, MailUtil.PRIORITY_HEIGHT);
						}
					}
					
				} while (false);
				
			} catch (BizException e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		} else {
			msg = "无权限操作！";
		}

		out.put("msg", msg);
		return new ModelAndView("/mngr/resultInfo");
	}

	/**
	 * 未付款之前，买家订单取消
	 * 
	 * @throws BizException
	 */
	private void cancelOrderBybuyer(OrderBill bill, String cancelInfo) throws BizException {
		if (OrderState.STATE_00.equals(bill.getState())) {
			bill.setState(OrderState.STATE_20);
			bill.setUpdateTime(new Date());
			bill.setCloseReason(cancelInfo);
			orderBillService.updateByPrimaryKey(bill);

			// 库存的增加
			Sample sample = sampleService.queryByIdOrProductId(bill.getSampleId(), null);
			if(bill.getNumber()!=null){
				sample.setAmount(sample.getAmount() + bill.getNumber());
				sampleService.editSample(sample);
			}
		} else {
			throw new BizException("当前订单状态不可取消订单，请不要重复操作！");
		}
	}

	/**
	 * 已经付款订单，卖家主动关闭交易
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView closeOrderBysellerPro(HttpServletRequest request, Map<String, Object> out, Integer id) {
		out.put("id", id);
		return new ModelAndView("/mngr/closeOrder");
	}

	/**
	 * 已经付款订单，卖家主动关闭交易
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView closeOrderByseller(HttpServletRequest request, Map<String, Object> out, Integer id, String cancelReson, String memo) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		String msg = "关闭订单成功！";

		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		if (bill.getSellerId().equals(companyId)) {
			try {
				
				orderBillService.closeOrderByseller(bill, cancelReson + ("" != memo ? (" 备注:" + memo) : ""));
				
				/**
				 * 发送短信信息
				 */
				String str = "已关闭订单（"+bill.getCloseReason()+"）";
				
				CompanyAccount ca = companyAccountService.queryAccountByCompanyId(bill.getBuyerId());
				do {
					
					if (ca==null) {
						break;
					}
					SampleMsgset sampleMsgset= sampleMsgsetService.queryByCompanyId(bill.getBuyerId());
					if(sampleMsgset ==null || sampleMsgset.getSms()==1)	{
						if (StringUtils.isNotEmpty(ca.getMobile())) {
							String orderTitle = org.apache.commons.lang.StringUtils.substring(bill.getSnapTitle(), 0, 8);
							String orderId = bill.getOrderid();
							// 发送短信
							// 3.您在ZZ91再生网的样品订单（PP再生颗粒，订单号：14072314124676）卖家已关闭订单（理由：买家资质不全）
							SmsUtil.getInstance().sendSms("sms_sample_buyer", ca.getMobile(), null, null, new String[] { orderTitle, orderId, str });
						}
					}
					
					//* 发送邮件提醒信息 link-b3
					if(sampleMsgset==null || 1==sampleMsgset.getEmail()){
						if (StringUtils.isNotEmpty(ca.getUseEmail())) {
							Map<String, Object> paramMap = new HashMap<String, Object>();
							paramMap.put("bill", bill);
							paramMap.put("createTime", DateUtil.toString( bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
							paramMap.put("stateName", str);
							paramMap.put("stateAndDeal", "卖家已关闭订单");
							MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒", ca.getUseEmail(), null, new Date(), "zz91","sample_order_buyer", paramMap, MailUtil.PRIORITY_HEIGHT);
						}
					}
					
				} while (false);
				
			} catch (BizException e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		} else {
			msg = "无权限操作！";
		}

		out.put("msg", msg);
		return new ModelAndView("/mngr/resultInfo");
	}

	/**
	 * 提醒卖家发货
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView remindSendGoods(HttpServletRequest request, Map<String, Object> out, Integer id) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		String msg = "已提醒卖家发货！";

		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		if (bill.getBuyerId().equals(companyId)) {
			if (OrderState.STATE_03.equals(bill.getState())) {
				bill.setState(OrderState.STATE_06);
				bill.setUpdateTime(new Date());
				orderBillService.updateByPrimaryKey(bill);
				
				/**
				 * 发送短信信息
				 */
				String str = "已付款并提醒发货，请您尽快安排发货";

				CompanyAccount ca = companyAccountService.queryAccountByCompanyId(bill.getSellerId());
				do {
					if (ca==null) {
						break;
					}
					
					SampleMsgset sampleMsgset= sampleMsgsetService.queryByCompanyId(bill.getSellerId());
					if(sampleMsgset ==null || sampleMsgset.getSms()==1){
						if (StringUtils.isNotEmpty(ca.getMobile())) {
							String orderTitle = org.apache.commons.lang.StringUtils.substring(bill.getSnapTitle(), 0, 8);
							String orderId = bill.getOrderid();
							CompanyAccount caBuy = companyAccountService.queryAccountByCompanyId(bill.getBuyerId());
							String contact = caBuy.getContact();
							// 发送短信
							//  s2.您有一笔来自买家康先生的样品订单（PP再生颗粒，订单号：14072314124676）买家已付款并提醒发货，请您尽快安排发货
							//您有一笔来自买家{0}的样品订单（{1}，订单号：{2}）买家{3}
							SmsUtil.getInstance().sendSms("sms_sample_seller", ca.getMobile(), null, null, new String[] {contact, orderTitle, orderId, str });
						}
					}
					/**
					 * 发送邮件提醒信息 link-s2
					 */
					if(sampleMsgset==null || 1==sampleMsgset.getEmail()){
						if (StringUtils.isNotEmpty(ca.getUseEmail())) {
							Map<String, Object> paramMap = new HashMap<String, Object>();
							paramMap.put("bill", bill);
							paramMap.put("createTime", DateUtil.toString( bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
							paramMap.put("stateName", "买家提醒发货");
							paramMap.put("stateAndDeal", "买家已付款并提醒发货，请您尽快安排发货");
							MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒", ca.getUseEmail(), null, new Date(), "zz91","sample_order_seller", paramMap, MailUtil.PRIORITY_HEIGHT);
						}
					}
				
				} while (false);
				
			} else {
				msg = "当前订单状态不可做确认发货操作！";
			}
		} else {
			msg = "无权限操作！";
		}

		out.put("msg", msg);
		return new ModelAndView("/mngr/resultInfo");
	}

	/**
	 * 付款
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView payToChannel(HttpServletRequest request, Map<String, Object> out, String orderid) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		Account acc = accountService.selectByCompanyId(companyId);

		OrderBill bill = orderBillService.selectByOrderSeq(orderid);
		
		if (bill.getNumber()==null||bill.getNumber()<1) {
			return new ModelAndView("redirect:/mngr/createOrderPro"+bill.getSampleId()+"-o"+orderid+".htm");
		}

		out.put("account", acc);
		out.put("orderSeq", orderid);
		out.put("amount", NumberUtil.formatCurrency(bill.getAmount().doubleValue(), "#####0.00"));
		out.put("isCashDelivery", bill.getIsCashDelivery());
		out.put("sendPrice", NumberUtil.formatCurrency(bill.getTrafficFee().doubleValue(), "#####0.00"));
		
		return new ModelAndView("/mngr/createOrder2");
	}

	/**
	 * 卖家确认发货
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView confirmSendGoods(HttpServletRequest request, Map<String, Object> out, Integer id) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		String msg = "确认发货成功！";

		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		if (bill.getSellerId().equals(companyId)) {
			try {
				this.confirmSendGoodsByseller(bill);
			} catch (BizException e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		} else {
			msg = "无权限操作！";
		}

		out.put("msg", msg);
		return new ModelAndView("/mngr/resultInfo");
	}

	/**
	 * 卖家确认发货
	 * 
	 * @throws BizException
	 */
	private void confirmSendGoodsByseller(OrderBill bill) throws BizException {
		if (OrderState.STATE_03.equals(bill.getState()) || OrderState.STATE_06.equals(bill.getState())) {
			bill.setState(OrderState.STATE_11);
			bill.setUpdateTime(new Date());
			orderBillService.updateByPrimaryKey(bill);
			
			/**
			 * 发送短信信息
			 */
			String str = "已发货，请注意查收并确认收货";
			
			CompanyAccount ca = companyAccountService.queryAccountByCompanyId(bill.getBuyerId());
			do {
				if (ca==null) {
					break;
				}
				
				SampleMsgset sampleMsgset= sampleMsgsetService.queryByCompanyId(bill.getBuyerId());
				if(sampleMsgset ==null || sampleMsgset.getSms()==1)			
				if (StringUtils.isNotEmpty(ca.getMobile())) {
					String orderTitle = bill.getSnapTitle().substring(0, 8);
					String orderId = bill.getOrderid();
					// 发送短信
					// 您在ZZ91再生网的样品订单（PP再生颗粒，订单号：14072314124676）卖家已发货，请注意查收并确认收货
					SmsUtil.getInstance().sendSms("sms_sample_buyer", ca.getMobile(), null, null, new String[] { orderTitle, orderId, str });
				}
				
				// * 发送邮件提醒信息 link-b2
				if(sampleMsgset==null || 1==sampleMsgset.getEmail()){
					if (StringUtils.isNotEmpty(ca.getUseEmail())) {
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("bill", bill);
						paramMap.put("createTime", DateUtil.toString( bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
						paramMap.put("stateName", "卖家已发货");
						paramMap.put("stateAndDeal", "卖家已发货，请注意查收并确认收货");
						MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒", ca.getUseEmail(), null, new Date(), "zz91","sample_order_buyer", paramMap, MailUtil.PRIORITY_HEIGHT);
					}
				}
			} while (false);
			
		} else {
			throw new BizException("当前订单状态不可做确认发货操作！");
		}
	}

	/**
	 * 买家确认收获
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView confirmRecvGoods(HttpServletRequest request, Map<String, Object> out, Integer id) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		String havePwd = "1";
		if (bill.getAmount().compareTo(new BigDecimal(0)) == 0) {
			havePwd = "0";
		}

		Account acc = accountService.selectByCompanyId(companyId);
		if (acc == null) {
			acc = new Account(companyId, new BigDecimal(0), "00", new Date());
			acc.setLastupdateTime(new Date());
			accountService.insert(acc);
		}

		out.put("account", acc);
		out.put("id", id);
		out.put("havePwd", havePwd);
		return new ModelAndView("/mngr/confirmRecvGoods");
	}

	@RequestMapping
	public ModelAndView confirmRecvGoodsDeal(HttpServletRequest request, Map<String, Object> out, Integer id, String payPasswd) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		String msg = "确认收货成功！";

		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		if (bill.getBuyerId().equals(companyId)) {
			try {
				OrderDto dto = new OrderDto(bill);
				this.confirmReceiveGoodsBybuyer(dto, payPasswd, companyId);
			} catch (BizException e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		} else {
			msg = "无权限操作！";
		}

		out.put("msg", msg);
		return new ModelAndView("/mngr/resultInfo");
	}

	/**
	 * 买家确认收货 存在资金的转移
	 * 
	 * @param dto
	 * @param confirmPasswd
	 * @throws BizException
	 */
	private void confirmReceiveGoodsBybuyer(OrderDto dto, String payPasswd, Integer companyId) throws BizException {

		/**
		 * 总交易金额 !=0,密码验证
		 */
		if (dto.getOrderBill().getAmount().compareTo(new BigDecimal(0)) == 1) {
			Account acc = accountService.selectByCompanyId(companyId);
			String encodepwd = "";
			if (payPasswd != null && !payPasswd.trim().equals("")) {
				try {
					encodepwd = MD5.encode(payPasswd);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				throw new BizException("交易密码不能为空！请到安全中心设置！");
			}

			if (!encodepwd.equals(acc.getPayPasswd())) {
				throw new BizException("交易密码错误，请重新输入！");
			}
		}

		orderBillService.confirmReceiveGoodsBybuyer(dto, payPasswd);
	}

	/**
	 * 买家申请退货
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView applayRefundPro(HttpServletRequest request, Map<String, Object> out, Integer id) {
		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		out.put("id", id);
		out.put("bill", bill);
		return new ModelAndView("/mngr/applayRefund");
	}

	/**
	 * 买家申请退货
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param refundAmount
	 * @param refundType
	 * @param isflag
	 * @param refundReson
	 * @param refundDes
	 * @param refundNum
	 * @return
	 */
	@RequestMapping
	public ModelAndView applayRefund(HttpServletRequest request, Map<String, Object> out, Integer id, String refundAmount,
			String refundType, String isflag, String refundReson, String refundDes, Integer refundNum) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		String msg = "申请退货成功，等待卖家处理退货！";
		OrderBill bill = orderBillService.selectByPrimaryKey(id);

		if (bill.getBuyerId().equals(companyId)) {
			try {
				if (bill.getAmount().compareTo(new BigDecimal(0)) == 0) {
					throw new BizException("免费订单不能做退货操作！");
				}

				orderBillService.applayRefundBybuyer(bill, companyId, refundAmount, refundType, isflag, refundReson, refundDes, refundNum);
			
				
				/**
				 * 发送短信信息
				 */
				String str = "申请退货（"+refundReson+"）";

				CompanyAccount ca = companyAccountService.queryAccountByCompanyId(bill.getSellerId());
				do {
					
					if (ca==null) {
						break;
					}
					
					SampleMsgset sampleMsgset= sampleMsgsetService.queryByCompanyId(bill.getSellerId());
					if(sampleMsgset ==null || sampleMsgset.getSms()==1)				
					if (StringUtils.isNotEmpty(ca.getMobile())) {
						String orderTitle = org.apache.commons.lang.StringUtils.substring(bill.getSnapTitle(), 0, 8);
						String orderId = bill.getOrderid();
						CompanyAccount caBuy = companyAccountService.queryAccountByCompanyId(bill.getBuyerId());
						String contact = caBuy.getContact();
						// 发送短信
						// s5.您有一笔来自买家康先生的样品订单（PP再生颗粒，订单号：14072314124676）买家申请退货（理由：虚假发货）
						//您有一笔来自买家{0}的样品订单（{1}，订单号：{2}）买家{3}
						SmsUtil.getInstance().sendSms("sms_sample_seller", ca.getMobile(), null, null, new String[] {contact, orderTitle, orderId, str });
					}
					
					// * 发送邮件提醒信息 link-s5
					if(sampleMsgset==null || 1==sampleMsgset.getEmail()){
						if (StringUtils.isNotEmpty(ca.getUseEmail())) {
							Map<String, Object> paramMap = new HashMap<String, Object>();
							paramMap.put("bill", bill);
							paramMap.put("createTime", DateUtil.toString( bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
							paramMap.put("stateName",str);
							paramMap.put("stateAndDeal", "买家申请退货");
							MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒", ca.getUseEmail(), null, new Date(), "zz91","sample_order_seller", paramMap, MailUtil.PRIORITY_HEIGHT);
						}
					}
				} while (false);
				
			} catch (BizException e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		} else {
			msg = "无权限操作！";
		}

		out.put("msg", msg);
		return new ModelAndView("/mngr/resultInfo");
	}

	/**
	 * 卖家是否同意退货
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView applayRefundAuditPro(HttpServletRequest request, Map<String, Object> out, Integer id) {
		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		Refund refund = refundService.selectByOrderBillId(bill.getId());

		out.put("id", id);
		out.put("bill", bill);
		out.put("refund", refund);
		return new ModelAndView("/mngr/applayRefundAudit");
	}

	/**
	 * 卖家是否同意退货
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param refundAmount
	 * @param refundType
	 * @param isflag
	 * @param refundReson
	 * @param refundDes
	 * @param refundNum
	 * @return
	 */
	@RequestMapping
	public ModelAndView applayRefundAudit(HttpServletRequest request, Map<String, Object> out, Integer id, Integer refundId,
			String isAgree, Integer addressId) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		String msg = "处理退货成功！";
		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		Refund refund = refundService.selectByPrimaryKey(refundId);

		if (bill.getSellerId().equals(companyId)) {
			try {
				orderBillService.agreeRefundByseller(bill, refund, isAgree, addressId);
			} catch (BizException e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		} else {
			msg = "无权限操作！";
		}

		out.put("msg", msg);
		return new ModelAndView("/mngr/resultInfo");
	}

	/**
	 * 买家确认退货发货
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView confirmRefundSendGoods(HttpServletRequest request, Map<String, Object> out, Integer id) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		String msg = "确认退货发货成功！";

		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		Refund refund = refundService.selectByOrderBillId(bill.getId());

		if (bill.getBuyerId().equals(companyId)) {
			try {
				orderBillService.refundGoodSentBybuyer(bill, refund);
				
				/**
				 * 发送短信信息
				 */
				String str = "已发货，请注意查收并确认收货";

				CompanyAccount ca = companyAccountService.queryAccountByCompanyId(bill.getSellerId());
				
				do {
					
				
				SampleMsgset sampleMsgset= sampleMsgsetService.queryByCompanyId(bill.getSellerId());
				if(sampleMsgset ==null || sampleMsgset.getSms()==1)
				if (StringUtils.isNotEmpty(ca.getMobile())) {
					String orderTitle = org.apache.commons.lang.StringUtils.substring(bill.getSnapTitle(), 0, 8);
					String orderId = bill.getOrderid();
					CompanyAccount caBuy = companyAccountService.queryAccountByCompanyId(bill.getBuyerId());
					String contact = caBuy.getContact();
					// 发送短信
					//  s6.您有一笔来自买家康先生的样品订单（PP再生颗粒，订单号：14072314124676）买家已发货，请注意查收并确认收货
					//您有一笔来自买家{0}的样品订单（{1}，订单号：{2}）买家{3}
					SmsUtil.getInstance().sendSms("sms_sample_seller", ca.getMobile(), null, null, new String[] {contact, orderTitle, orderId, str });
				}
				
				// * 发送邮件提醒信息 link-s6
				if(sampleMsgset==null || 0 ==sampleMsgset.getEmail()){
					if (StringUtils.isNotEmpty(ca.getUseEmail())) {
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("bill", bill);
						paramMap.put("createTime", DateUtil.toString( bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
						paramMap.put("stateName", "等待卖家收货");
						paramMap.put("stateAndDeal", "买家已发货，请注意查收并确认收货");
						MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒", ca.getUseEmail(), null, new Date(), "zz91","sample_order_seller", paramMap, MailUtil.PRIORITY_HEIGHT);
					}
				}
				
				} while (false);
				
			} catch (BizException e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		} else {
			msg = "无权限操作！";
		}

		out.put("msg", msg);
		return new ModelAndView("/mngr/resultInfo");
	}

	/**
	 * 卖家确认收到退货
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView refundGoodRecvBysellerPro(HttpServletRequest request, Map<String, Object> out, Integer id) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		String havePwd = "1";
		if (bill.getAmount().compareTo(new BigDecimal(0)) == 0) {
			havePwd = "0";
		}

		Account acc = accountService.selectByCompanyId(companyId);
		if (acc == null) {
			acc = new Account(companyId, new BigDecimal(0), "00", new Date());
			acc.setLastupdateTime(new Date());
			accountService.insert(acc);
		}

		out.put("account", acc);
		out.put("id", id);
		out.put("havePwd", havePwd);
		return new ModelAndView("/mngr/refundGoodRecvByseller");
	}

	@RequestMapping
	public ModelAndView refundGoodRecvByseller(HttpServletRequest request, Map<String, Object> out, Integer id, String payPasswd) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		String msg = "确认收货成功！";

		OrderBill bill = orderBillService.selectByPrimaryKey(id);
		Refund refund = refundService.selectByOrderBillId(bill.getId());

		if (bill.getSellerId().equals(companyId)) {
			try {
				this.refundGoodRecvByseller(refund, payPasswd, companyId);
			} catch (BizException e) {
				e.printStackTrace();
				msg = e.getMessage();
			}
		} else {
			msg = "无权限操作！";
		}

		out.put("msg", msg);
		return new ModelAndView("/mngr/resultInfo");
	}

	/**
	 * 卖家确认收到退货 存在账务处理
	 * 
	 * @throws BizException
	 */
	public void refundGoodRecvByseller(Refund refund, String confirmPasswd, Integer companyId) throws BizException {
		Account acc = accountService.selectByCompanyId(companyId);

		String encodepwd = "";
		if (confirmPasswd != null && !confirmPasswd.trim().equals("")) {
			try {
				encodepwd = MD5.encode(confirmPasswd);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			throw new BizException("交易密码不能为空！请到安全中心设置！");
		}

		if (!encodepwd.equals(acc.getPayPasswd())) {
			throw new BizException("交易密码错误，请重新输入！");
		}

		orderBillService.confirmRefundGoodRecvByseller(refund, confirmPasswd);
		
		OrderBill bill = orderBillService.selectByPrimaryKey(refund.getOrderbillId());
		
		/**
		 * 发送短信信息
		 */
		String str = "已发货，请注意查收并确认收货";

		CompanyAccount ca = companyAccountService.queryAccountByCompanyId(bill.getBuyerId());
		do {
			if(ca==null){
				break;
			}
			
			SampleMsgset sampleMsgset= sampleMsgsetService.queryByCompanyId(bill.getBuyerId());
			if(sampleMsgset ==null || sampleMsgset.getSms()==1)
			if (StringUtils.isNotEmpty(ca.getMobile())) {
				String orderTitle = org.apache.commons.lang.StringUtils.substring(bill.getSnapTitle(), 0, 8);
				String orderId = bill.getOrderid();
				// 发送短信
				//  5.您在ZZ91再生网的样品订单（PP再生颗粒，订单号：14072314124676）卖家已确认收货，您已成功退货
				SmsUtil.getInstance().sendSms("sms_sample_buyer", ca.getMobile(), null, null, new String[] { orderTitle, orderId, str });
			}
			
			/**
			 * 发送邮件提醒信息 link-b5
			 */
			if( sampleMsgset == null || 1==sampleMsgset.getEmail()){
				if (StringUtils.isNotEmpty(ca.getUseEmail())) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("bill", bill);
					paramMap.put("createTime", DateUtil.toString( bill.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
					paramMap.put("stateName", "退货成功");
					paramMap.put("stateAndDeal", "卖家已确认收货，您已成功退货");
					MailUtil.getInstance().sendMail(null, null, "ZZ91样品交易订单提醒", ca.getUseEmail(), null, new Date(), "zz91","sample_order_buyer", paramMap, MailUtil.PRIORITY_HEIGHT);
				}
			}
		} while (false);
		
	}
	
	private void setProperties(ProductsDto dto, Map<String, Object> out) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 颜色
		if (StringUtils.isNotEmpty(dto.getProducts().getColor())) {
			map.put("颜色", dto.getProducts().getColor());
		}
		// 形态
		ProductAddProperties shape = productAddPropertiesService.queryByPidAndProperty(dto.getProducts().getId(), "形态");
		if (shape != null && StringUtils.isNotEmpty(shape.getContent())) {
			map.put("形态", shape.getContent());
		}

		// 级别
		ProductAddProperties level = productAddPropertiesService.queryByPidAndProperty(dto.getProducts().getId(), "级别");
		if (level != null && StringUtils.isNotEmpty(level.getContent())) {
			map.put("级别", level.getContent());
		}

		// 外观
		if (StringUtils.isNotEmpty(dto.getProducts().getAppearance())) {
			map.put("外观", dto.getProducts().getAppearance());
		}
		// 货源地
		if (StringUtils.isNotEmpty(dto.getProducts().getSource())) {
			map.put("货源地", dto.getProducts().getSource());
		}
		// 来源产品：
		if (StringUtils.isNotEmpty(dto.getProducts().getOrigin())) {
			map.put("来源产品", dto.getProducts().getOrigin());
		}
		// 产品规格
		if (StringUtils.isNotEmpty(dto.getProducts().getSpecification())) {
			map.put("产品规格", dto.getProducts().getSpecification());
		}
		// 此废料可用于
		if (StringUtils.isNotEmpty(dto.getProducts().getUseful())) {
			map.put("此废料可用于", dto.getProducts().getUseful());
		}
		// 杂质（杂物）含量： （请写明具体杂质及其含量，如：含镍3%）
		if (StringUtils.isNotEmpty(dto.getProducts().getImpurity())) {
			map.put("杂质（杂物）含量", dto.getProducts().getImpurity());
		}
		// 加工说明
		if (StringUtils.isNotEmpty(dto.getProducts().getManufacture())) {
			map.put("加工说明", CategoryFacade.getInstance().getValue(dto.getProducts().getManufacture()));
		}

		out.put("properties", map);
	}
	
	/**
	 * 安全中心 --手机验证
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView checkmobile(HttpServletRequest request, Map<String, Object> out) {
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		String account =null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
			account = sessionUser.getAccount();
		}
		
		// 各状态订单的统计数
		this.getOrderCount(companyId, out);
		
		// 验证是否手机绑定
		AuthUser au = authService.queryAuthUserByUsername(account);
		if (au != null) {
			out.put("au", au);
		}
		
		//Identity idnt = identityService.queryIdentityByCompanyId(companyId);
		//out.put("identity", idnt);
		//out.put("msg", "您已经提交过认证信息，实名认证审核中，重新提交将会取消之前的申请！");
		
		return new ModelAndView("/mngr/checkmobile");
	}	
	
	@RequestMapping
	public ModelAndView getMobileCode(Map<String, Object> out,HttpServletRequest request,String mobile) throws IOException{
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		String account =null;
		if (sessionUser != null) {
			account = sessionUser.getAccount();
		}
		
		ExtResult result = new ExtResult();
		do {
			// 未登陆
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null){
				//break;
			}
			// 手机号未空
			if (StringUtils.isEmpty(mobile)) {
				break;
			}
			// 手机号是否已经存在于其他账户
			Integer i = 0;
			if (authService.validateAuthUserByMobile(account, mobile)) {
				i = oauthAccessService.addOneMobileAccess(mobile, OauthAccessService.OPEN_TYPE_MOBILE, account);
			}
			if(i>0){
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView doMobile(Map<String, Object>out,HttpServletRequest request,String mobile,String code,String destUrl){
		SsoUser sessionUser = getCachedUser(request);
		String account =null;
		if (sessionUser != null) {
			account = sessionUser.getAccount();
		}
		
		out.put("destUrl",destUrl);
		do {
			// 
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser==null){
				//break;
			}
			
			if(StringUtils.isEmpty(code)||StringUtils.isEmpty(mobile)){
				break;
			}
			// 获取验证信息
			OauthAccess oa = oauthAccessService.queryAccessByOpenIdAndType(mobile, OauthAccessService.OPEN_TYPE_MOBILE);
			//验证信息是否存在
			if(oa==null){
				break;
			}
			// 编码是否一致
			if(!code.equals(oa.getCode())){
				break;
			}
			// 编码是否过期 30分钟
			Date date = new Date();
			if(date.getTime()-oa.getGmtCreated().getTime()>30*60*1000){
				break;
			}
			
			// 更新手机帐号登陆
			authService.updateMobile(account, mobile);
			return new ModelAndView("redirect:/mngr/safe.htm");
		} while (false);
		out.put("result", 1);
		return new ModelAndView("/mngr/checkmobile");
	}
	
	@RequestMapping
	public ModelAndView msgset(HttpServletRequest request, Map<String, Object> out,String msg) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		String account =null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
			account = sessionUser.getAccount();
		}

		// 各状态订单的统计数
		this.getOrderCount(companyId, out);
		
		// 验证是否手机绑定
		AuthUser au = authService.queryAuthUserByUsername(account);
		if (au != null) {
			out.put("au", au);
		}
		
		// 验证是否微信绑定
		OauthAccess oa = oauthAccessService.queryAccessByAccountAndType(account, OauthAccessService.OPEN_TYPE_WEIXIN);
		if (oa != null) {
			out.put("oa", oa);
		}

		// 帐号信息
		CompanyAccount myaccount = companyAccountService.queryAccountByAccount(account);
		out.put("myaccount", myaccount);
		
		SampleMsgset msgset = sampleMsgsetService.queryByCompanyId(companyId);
		if (msgset != null) {
			out.put("msgset", msgset);
		}
		
		if (msg != null) {
			out.put("msg", msg);
		}
		
		return new ModelAndView("/mngr/msgset");
	}

	@RequestMapping
	public ModelAndView msgsetUpdate(HttpServletRequest request, Map<String, Object> out,SampleMsgset msgset) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId = null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}
		sampleMsgsetService.openMsgset(companyId, msgset.getEmail(), msgset.getSms(), msgset.getWechat());
		return new ModelAndView("redirect:/mngr/msgset.htm?msg=1");
	}
	
	/**
	 * 邮件验证
	 */
	@RequestMapping
	public ModelAndView checkemail(HttpServletRequest request,Map<String, Object> out, String email,String result) {
		SsoUser sessionUser = getCachedUser(request);
		String account =null;
		if (sessionUser != null) {
			account = sessionUser.getAccount();
		}
		
		// 帐号信息
		CompanyAccount myaccount = companyAccountService.queryAccountByAccount(account);
		out.put("myaccount", myaccount);

		if (result != null) {
			out.put("result", result);
		}
		
		return new ModelAndView("/mngr/checkemail");
	}
	
	/**
	 * 邮件验证处理
	 */
	@RequestMapping
	public ModelAndView checkemaildeal(HttpServletRequest request,Map<String, Object> out, String email,String backEmail,String isUseBackEmail) {
		SsoUser sessionUser = getCachedUser(request);
		String account =null;
		if (sessionUser != null) {
			account = sessionUser.getAccount();
		}
		
		
		int result = 0;
		try {
			// 帐号信息
			CompanyAccount myaccount = companyAccountService.queryAccountByAccount(account);

			if (myaccount != null && StringUtils.isEmpty(myaccount.getEmail())) {
				myaccount.setEmail(email);
				companyAccountService.updateAccountByUser(myaccount);
				
				companyValidateService.sendValidateByEmail(account, email);
				result = 1;
			} else {
				myaccount.setBackEmail(backEmail);
				myaccount.setIsUseBackEmail(isUseBackEmail);
				companyAccountService.updateAccountByUser(myaccount);
				result = 2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = 3;
		}
		
		return new ModelAndView("redirect:/mngr/checkemail.htm?result="+result);
	}
	
	/**
	 * 积分收入
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView scoreIncome(HttpServletRequest request, Map<String, Object> out,PageDto<WeixinScore> page) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		String account =null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
			account = sessionUser.getAccount();
		}

		// 各状态订单的统计数
		this.getOrderCount(companyId, out);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account",account);
		page.setPageSize(10);
		page = weixinScoreService.queryListByFilter(page, map);
		
		//可兑换的积分数
		Integer totalScore = weixinScoreService.totalAvailableScore(account); 
		//已兑换的积分数
		Integer totalScoreEx = weixinPrizelogService.totalConvertScore(account); 
		
		out.put("page", page);
		out.put("totalScore", totalScore-totalScoreEx);
		return new ModelAndView("/mngr/scoreIncome");
	}
	
	/**
	 * 积分支出
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView scoreConvert(HttpServletRequest request, Map<String, Object> out,PageDto<WeixinPrizelog> page) {
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		String account =null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
			account = sessionUser.getAccount();
		}

		// 各状态订单的统计数
		this.getOrderCount(companyId, out);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account",account);
		page.setPageSize(10);
		page = weixinPrizelogService.queryListByFilter(page, map);
		
		WeixinPrize wxp = null;
		for(WeixinPrizelog ssc:page.getRecords()){
			wxp = weixinPrizeService.selectByPrimaryKey(ssc.getPrizeid());
			if (wxp != null)
				ssc.setScoreGoodsTitle(wxp.getTitle());
		}
		
		//已兑换的积分数
		Integer totalScore = weixinPrizelogService.totalConvertScore(account); 
		
		out.put("page", page);
		out.put("totalScore", totalScore);
		return new ModelAndView("/mngr/scoreConvert");
	}
	
	
	/**
	 * 找回交易密码 根据手机号码
	 * @param out
	 * @param request
	 * @param username
	 * @return
	 */
    @RequestMapping
    public ModelAndView mobileForPasswd(Map<String, Object> out , HttpServletRequest request){
		// 从session获取用户信息
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId =null; 
		String account =null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
			account = sessionUser.getAccount();
		}
		
		// 各状态订单的统计数
		this.getOrderCount(companyId, out);
		
		Account acc = accountService.selectByCompanyId(companyId);
		if (acc == null) {
			acc = new Account(companyId, new BigDecimal(0), "00", new Date());
			acc.setLastupdateTime(new Date());
			accountService.insert(acc);
		}
		
		// 验证是否手机绑定
		AuthUser au = authService.queryAuthUserByUsername(account);
		if (au != null) {
			out.put("au", au);
		}

		// 帐号信息
		CompanyAccount myaccount = companyAccountService.queryAccountByAccount(account);
		out.put("myaccount", myaccount);
		
		//身份认证
		Identity idnt = identityService.queryIdentityByCompanyId(companyId);
		
		// 验证是否微信绑定
		OauthAccess oa = oauthAccessService.queryAccessByAccountAndType(account, OauthAccessService.OPEN_TYPE_WEIXIN);
		if (oa != null) {
			out.put("oa", oa);
		}
		
		//安全级别：登陆密码+交易密码+短信+邮箱+实名+微信  6项，分3等级，每等级2项
		int safeLevel = 1; //登陆密码已设置,所以默认为1.
		if(acc !=null && StringUtils.isNotEmpty(acc.getPayPasswd()))
			safeLevel++;
		if(au !=null && StringUtils.isNotEmpty(au.getMobile()))
			safeLevel++;
		if(myaccount !=null && StringUtils.isNotEmpty(myaccount.getEmail()))
			safeLevel++;
		if(idnt !=null && "01".equals(idnt.getState()))
			safeLevel++;
		if(oa !=null)
			safeLevel++;
		out.put("safeLevel", safeLevel);
		
		
        CompanyAccount companyAccount = companyAccountService.queryAccountByAccount(account);
        String mobile = "********" + companyAccount.getMobile().substring(companyAccount.getMobile().length()-3,companyAccount.getMobile().length());
        out.put("mobile", mobile);
        out.put("allMobile", companyAccount.getMobile());
        out.put("username", account);
        return new ModelAndView();
    }
	
	/**
	 *  交易密码找回
	 * @param model
	 * @param password
	 * @param username
	 */
    @RequestMapping
	public ModelAndView resetPasswordForPasswd(Map<String, Object> model, String password, HttpServletRequest request) throws IOException,
			NoSuchAlgorithmException {
		SsoUser sessionUser = getCachedUser(request);
		Integer companyId = null;
		if (sessionUser != null) {
			companyId = sessionUser.getCompanyId();
		}

		Account account = accountService.selectByCompanyId(companyId);
		if (account != null) {
			account.setPayPasswd(MD5.encode(password));
			int i = accountService.updateByPrimaryKey(account);
			if (i > 0) {
				return new ModelAndView("/mngr/resetPwSuccess");
			}
		}

		return new ModelAndView();
	}
    
    
	final static public String MOBILE_TYPE = "1";
	
    @RequestMapping
    public ModelAndView SendCheckCode(Map<String, Object> model, String mobile, HttpServletRequest request)
        throws IOException, NoSuchAlgorithmException{
        Integer companyId = companyAccountService.queryComapnyIdByMobile(mobile);
        CompanyAccount companyAccount = companyAccountService.queryAccountByCompanyId(companyId);
        String email = companyAccount.getEmail();
        String userName  = companyAccount.getAccount();
        ExtResult result = new ExtResult();
        if (companyId != null) {
            if (getPasswordLogService.numOfType(companyId, MOBILE_TYPE) < 5) {
                GetPasswordLog getPasswordLog = new GetPasswordLog();
                getPasswordLog.setCompanyId(companyId);
                getPasswordLog.setType(MOBILE_TYPE);
                String key = authService.generateForgotPasswordMobileKey(email,userName);
                String content  ="您正在取回密码，验证码：" + key + "，该验证码1小时内有效。工作人员不会向您索取验证码，切勿泄露给他人【ZZ91再生网】";
                if(key!=null){
                    SmsUtil.getInstance().sendSms(mobile, content,"yuexin");
                    getPasswordLogService.insertPasswordLog(getPasswordLog);
                    result.setSuccess(true);
                }
            } else {
                result.setData("num");
            }
        }
        return printJson(result, model);
    }
    
    /*
     * 手机验证：当客户发送了手机验证后，接着客户又发送email验证，则手机验证的key会失效
     * 原因： email的key和手机的key是放在auth_forgot_password这同一张表中，而手机key查找是按用户最新的key。
     */
    @RequestMapping
    public ModelAndView verifyCheckCode(Map<String, Object> out, HttpServletRequest request, String smscomfirmcode) 
            throws IOException, NoSuchAlgorithmException{
        ExtResult result = new ExtResult();
        do {
                AuthForgotPassword o= authService.listAuthForgotPasswordByKey(smscomfirmcode);
                if(o==null){
                    break;
                }
                long start=DateUtil.getMillis(o.getGmtCreated());
                long now=DateUtil.getMillis(new Date());
                long twoday=60*60*1000;
                if((now-start)>twoday){
                    result.setData("验证码已过期！");
                } else if (smscomfirmcode.equals(o.getAuthKey())){
                    result.setSuccess(true);
                } else {
                    result.setSuccess(false);
                }
        } while (false);
        
        return printJson(result, out);
    }
	
}
