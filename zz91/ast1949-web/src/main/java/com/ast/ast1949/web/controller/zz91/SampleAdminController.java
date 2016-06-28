package com.ast.ast1949.web.controller.zz91;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.domain.sample.Account;
import com.ast.ast1949.domain.sample.Accountseq;
import com.ast.ast1949.domain.sample.Address;
import com.ast.ast1949.domain.sample.Custominfo;
import com.ast.ast1949.domain.sample.Identity;
import com.ast.ast1949.domain.sample.OrderBill;
import com.ast.ast1949.domain.sample.Sample;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.auth.ParamService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.products.ProductAddPropertiesService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.sample.AccountService;
import com.ast.ast1949.service.sample.AccountseqService;
import com.ast.ast1949.service.sample.AddressService;
import com.ast.ast1949.service.sample.CustominfoService;
import com.ast.ast1949.service.sample.IdentityService;
import com.ast.ast1949.service.sample.OrderBillService;
import com.ast.ast1949.service.sample.RefundService;
import com.ast.ast1949.service.sample.SampleRelateProductService;
import com.ast.ast1949.service.sample.SampleService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.lang.StringUtils;

@Controller
public class SampleAdminController extends BaseController {
	
//	private static Logger log = Logger.getLogger(SampleAdminController.class);
	
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
	private IdentityService identityService;

	@Resource
	private CustominfoService custominfoService;

	@Resource
	private AccountseqService accountseqService;

	@Resource
	private CompanyAccountService companyAccountService;

	@Resource
	private ProductAddPropertiesService productAddPropertiesService;

	/**
	 * 入口
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView sampleAdminindex(HttpServletRequest request, Map<String, Object> out) {
		return new ModelAndView("/zz91/sampleAdmin/index");
	}

	/**
	 * 样品
	 * 
	 * @param request
	 * @param out
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView sampleList(HttpServletRequest request, Map<String, Object> out, PageDto<Sample> page, String state,
			String datefrom, String dateto, Integer companyId, String companyName,String membershipCode,String productsTypeCode,String isVip,String dateType,String checkStatus,String title) throws UnsupportedEncodingException {
		
		if (StringUtils.isNotEmpty(companyName)){
			if (!StringUtils.isContainCNChar(companyName)) {
				companyName = StringUtils.decryptUrlParameter(companyName);
			}
		}
		
		if(StringUtils.isNotEmpty(title)){
			if (!StringUtils.isContainCNChar(title)) {
				title = StringUtils.decryptUrlParameter(title);
			}
		}
		
		if (companyId != null) {
			Company seller = companyService.queryCompanyById(companyId);
			out.put("snapTitle", "");
			if (seller != null)
				out.put("sellerName", seller.getName());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", datefrom);
		map.put("to", dateto);
		map.put("companyName", companyName);
		map.put("membershipCode", membershipCode);
		map.put("productsTypeCode", productsTypeCode);
		map.put("isVip", isVip);
		map.put("dateType",dateType);
		map.put("checkStatus",checkStatus);
		map.put("title",title);

		page.setPageSize(20);
		page = sampleService.queryListByFilter(page, map);
		out.put("page", page);
		out.put("datefrom", datefrom);
		out.put("dateto", dateto);
		out.put("companyId", companyId);
		out.put("companyName", companyName);
		out.put("membershipCode", membershipCode);
		out.put("productsTypeCode", productsTypeCode);
		out.put("isVip", isVip);
		out.put("dateType",dateType);
		out.put("checkStatus",checkStatus);
		out.put("title",title);
		
		return new ModelAndView("/zz91/sampleAdmin/Sample/sampleList");
	}

	/**
	 * 该样品订单
	 * 
	 * @param request
	 * @param out
	 * @param page
	 * @param state
	 * @param datefrom
	 * @param dateto
	 * @param companyId
	 * @return
	 */
	@RequestMapping
	public ModelAndView orderListBySample(HttpServletRequest request, Map<String, Object> out, PageDto<OrderBill> page, String state,
			String datefrom, String dateto, Integer companyId, Integer sampleId) {

		page.setPageSize(20);
		page = orderBillService.queryListBySampleId(companyId, state, page, datefrom, dateto, sampleId,null);
		out.put("page", page);
		out.put("state", state);
		out.put("datefrom", datefrom);
		out.put("dateto", dateto);
		out.put("companyId", companyId);
		out.put("sampleId", sampleId);
		return new ModelAndView("/zz91/sampleAdmin/Sample/orderListBySample");
	}

	/**
	 * 已买订单
	 * 
	 * @param request
	 * @param out
	 * @param page
	 * @param state
	 * @param datefrom
	 * @param dateto
	 * @param companyId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping
	public ModelAndView orderList(HttpServletRequest request, Map<String, Object> out, PageDto<OrderBill> page, String state,
			String datefrom, String dateto, Integer companyId, String buyerName, String snapTitle, String orderid) {

		if (StringUtils.isNotEmpty(snapTitle)) {
			snapTitle = getDecodeInfo(snapTitle);
		}
		if (StringUtils.isNotEmpty(buyerName))
			buyerName = getDecodeInfo(buyerName);

		Map<String, Object> map = new HashMap<String, Object>();
		page.setPageSize(20);
		map.put("buyerId", companyId);
		map.put("buyerName", buyerName);
		map.put("snapTitle", snapTitle);
		map.put("orderid", orderid);
		map.put("state", state);
		map.put("from", datefrom);
		map.put("to", dateto);
		page = orderBillService.queryListByFilter(page, map);

		out.put("page", page);
		out.put("sellerId", companyId);
		out.put("sellerName", buyerName);
		out.put("snapTitle", snapTitle);
		out.put("orderid", orderid);
		out.put("state", state);
		out.put("from", datefrom);
		out.put("to", dateto);
		return new ModelAndView("/zz91/sampleAdmin/Sample/orderList");
	}

	/**
	 * 已卖订单
	 * 
	 * @param request
	 * @param out
	 * @param page
	 * @param state
	 * @param datefrom
	 * @param dateto
	 * @param companyId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView saleList(HttpServletRequest request, Map<String, Object> out, PageDto<OrderBill> page, String state,
			String datefrom, String dateto, Integer companyId, String sellerName, String snapTitle, String orderid,String companyType) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(snapTitle)) {
			if (!StringUtils.isContainCNChar(snapTitle)) {
				snapTitle = getDecodeInfo(snapTitle);
			}
		}
		if (StringUtils.isNotEmpty(sellerName)){
			if (!StringUtils.isContainCNChar(sellerName)) {
				sellerName = StringUtils.decryptUrlParameter(sellerName);
			}
			if ("0".equals(companyType)) {
				map.put("buyerName", sellerName);
			}else{
				map.put("sellerName", sellerName);
			}
			out.put("sellerName", sellerName);
		}
		
		page.setPageSize(20);
		map.put("sellerId", companyId);
		map.put("snapTitle", snapTitle);
		map.put("orderid", orderid);
		map.put("state", state);
		map.put("from", datefrom);
		map.put("to", dateto);
		page = orderBillService.queryListByFilter(page, map);

		out.put("page", page);
		out.put("sellerId", companyId);
		out.put("snapTitle", snapTitle);
		out.put("orderid", orderid);
		out.put("state", state);
		out.put("from", datefrom);
		out.put("to", dateto);
		out.put("companyType",companyType);
		return new ModelAndView("/zz91/sampleAdmin/Sample/saleList");
	}

	/**
	 * 公司账户信息
	 * 
	 * @param request
	 * @param out
	 * @param page
	 * @param state
	 * @param datefrom
	 * @param dateto
	 * @param companyId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView accountList(HttpServletRequest request, Map<String, Object> out, PageDto<Account> page, String state,
			String datefrom, String dateto, Integer companyId, String companyName,String account) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(companyName)) {
			if (!StringUtils.isContainCNChar(companyName)) {
				companyName = StringUtils.decryptUrlParameter(companyName);
			}
			map.put("companyName", companyName);
			out.put("companyName", companyName);
		}
		
		map.put("companyId", companyId);
		map.put("account", account);

		page.setPageSize(20);
		page = accountService.queryListByFilter(page, map);
		out.put("page", page);
		out.put("datefrom", datefrom);
		out.put("dateto", dateto);
		out.put("account", account);
		out.put("companyId", companyId);
		return new ModelAndView("/zz91/sampleAdmin/Sample/accountList");
	}

	/**
	 * 公司账户更新
	 * 
	 * @param request
	 * @param out
	 * @param page
	 * @param id
	 * @return
	 */
	@RequestMapping
	public ModelAndView accountEdit(HttpServletRequest request, Map<String, Object> out, PageDto<Account> page, Integer id) {
		Account account = accountService.selectByPrimaryKey(id);
		out.put("account", account);
		return new ModelAndView("/zz91/sampleAdmin/Sample/accountEdit");
	}

	@RequestMapping
	public ModelAndView accountUpdate(HttpServletRequest request, Map<String, Object> out, PageDto<Account> page, BigDecimal preamount,
			Account account) {

		accountService.updateAccount(account, preamount);
		return new ModelAndView("redirect:accountList.htm");
	}

	/**
	 * 账户变动流水明细
	 * 
	 * @param request
	 * @param out
	 * @param page
	 * @param state
	 * @param datefrom
	 * @param dateto
	 * @param companyId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping
	public ModelAndView accountseqList(HttpServletRequest request, Map<String, Object> out, PageDto<Accountseq> page, String state,
			String datefrom, String dateto, Integer companyId, Integer accountId, String orderid,String changeType,String companyName) throws UnsupportedEncodingException {

		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(companyName)) {
			if (!StringUtils.isContainCNChar(companyName)) {
				companyName = StringUtils.decryptUrlParameter(companyName);
			}
			map.put("companyName", companyName);
			out.put("companyName", companyName);
		}
		
		map.put("orderid", orderid);
		map.put("companyId", companyId);
		map.put("accountId", accountId);
		map.put("from", datefrom);
		map.put("to", dateto);
		map.put("changeType", changeType);

		page.setPageSize(20);
		page = accountseqService.queryListByFilter(page, map);
		out.put("page", page);
		out.put("datefrom", datefrom);
		out.put("dateto", dateto);
		out.put("companyId", companyId);
		out.put("accountId", accountId);
		out.put("orderid", orderid);
		out.put("changeType", changeType);
		return new ModelAndView("/zz91/sampleAdmin/Sample/accountseqList");
	}

	/**
	 * 留言
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @param companyId
	 * @return
	 */
	@RequestMapping
	public ModelAndView leavewords(HttpServletRequest request, Map<String, Object> out, Integer id, Integer companyId) {
		Custominfo info = custominfoService.selectByPrimaryKey(id);
		out.put("custominfo", info);
		out.put("companyId", companyId);
		return new ModelAndView("/zz91/sampleAdmin/Sample/leavewords");
	}

	/**
	 * 订单详情
	 * 
	 * @param request
	 * @param out
	 * @param id
	 * @return
	 
	@RequestMapping
	public ModelAndView orderDetail(HttpServletRequest request, Map<String, Object> out, Integer id) {
		OrderBill bill = orderBillService.selectByPrimaryKey(id);

		// 留言
		Custominfo custominfo = custominfoService.selectByOrderSeq(bill.getOrderid());
		// 收货地址
		Address buyerAddr = addressService.selectByPrimaryKey(bill.getBuyerAddrId());

		// 备忘
		Custominfo bw = custominfoService.selectByOrderSeqAndFlagAndType(bill.getOrderid(), "B", "0");

		out.put("bill", bill);
		out.put("custominfo", custominfo);
		out.put("buyerAddr", buyerAddr);
		out.put("bw", bw);
		return new ModelAndView("/zz91/sampleAdmin/Sample/orderDetail");
	}
	*/
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
		
		OrderBill bill = orderBillService.selectByPrimaryKey(id);

		// 留言
		Custominfo custominfo = custominfoService.selectByOrderSeq(bill.getOrderid());
		// 收货地址
		Address buyerAddr =addressService.selectByPrimaryKey(bill.getBuyerAddrId());
		if (buyerAddr==null) {
			buyerAddr = addressService.queryAimAddressByCompanyId(bill.getBuyerId());
		}

		// 备忘
		List<Custominfo> list = custominfoService.selectByOrderSeqAndType(bill.getOrderid(), "0");
		if(list!=null){
			for (Custominfo bw :list){
				if(bw!=null&&bw.getFlag()!=null&&bw.getMemo()!=null){
					//卖家备忘
					if("S".equals(bw.getFlag())){
						String bws=bw.getMemo();
						out.put("bws", bws);
					}
					//买家备忘
					if("B".equals(bw.getFlag())){
						String bwb=bw.getMemo();
						out.put("bwb", bwb);
					}
				}
			}
		}
		
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
		return new ModelAndView("/zz91/sampleAdmin/Sample/orderDetail");
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	private String getDecodeInfo(String str) {
		String desStr = "";
		if (null != str && !"".equals(str)) {
			try {
				desStr = URLDecoder.decode(str, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return desStr;
	}
	
	
	@RequestMapping
	public ModelAndView identityList(HttpServletRequest request, Map<String, Object> out, PageDto<Identity> page, String state,
			String datefrom, String dateto, String account, String companyName, String realName,String identityNo) throws UnsupportedEncodingException {

		Map<String, Object> map = new HashMap<String, Object>();
		
		if (StringUtils.isNotEmpty(realName)) {
			if (!StringUtils.isContainCNChar(realName)) {
				realName = StringUtils.decryptUrlParameter(realName);
			}
			map.put("realName", realName);
		}
		
		if (StringUtils.isNotEmpty(companyName)) {
			if (!StringUtils.isContainCNChar(companyName)) {
				companyName = StringUtils.decryptUrlParameter(companyName);
			}
			map.put("companyName", companyName);
		}
		
		if (StringUtils.isNotEmpty(account)) {
			map.put("account", account);
		}
		
		if (StringUtils.isNotEmpty(identityNo)) {
			map.put("identityNo", identityNo);
		}
		
		if (StringUtils.isNotEmpty(state)) {
			map.put("state", state);
		}
		
		if (StringUtils.isNotEmpty(datefrom)) {
			map.put("from", datefrom);
		}
		
		if (StringUtils.isNotEmpty(dateto)) {
			map.put("to", dateto);
		}

		page.setPageSize(20);
		page = identityService.queryListByFilter(page, map);
		out.put("page", page);
		out.put("datefrom", datefrom);
		out.put("dateto", dateto);
		out.put("identityNo", identityNo);
		out.put("account", account);
		out.put("companyName", companyName);
		out.put("realName", realName);
		out.put("state", state);
		return new ModelAndView("/zz91/sampleAdmin/Sample/identityList");
	}
	
	@RequestMapping
	public ModelAndView identityEdit(HttpServletRequest request, Map<String, Object> out, PageDto<Identity> page, Integer id,String msg) {
		Identity identity = identityService.selectByPrimaryKey(id);
		out.put("identity", identity);
		out.put("msg", msg);
		return new ModelAndView("/zz91/sampleAdmin/Sample/identityEdit");
	}

	@RequestMapping
	public ModelAndView identityUpdate(HttpServletRequest request, Map<String, Object> out, PageDto<Account> page, Identity identity) {
		Integer i = identityService.updateByPrimaryKeySelective(identity);
		out.put("identity", identity);
		if (i>0) {
			return new ModelAndView("redirect:/zz91/sampleadmin/identityEdit.htm?msg=1&id="+identity.getId());
		}else{
			return new ModelAndView("redirect:/zz91/sampleadmin/identityEdit.htm?id="+identity.getId());
		}
	}
}
