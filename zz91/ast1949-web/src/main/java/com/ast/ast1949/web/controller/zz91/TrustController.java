package com.ast.ast1949.web.controller.zz91;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.domain.trust.TrustBuyLog;
import com.ast.ast1949.domain.trust.TrustCompanyLog;
import com.ast.ast1949.domain.trust.TrustDealer;
import com.ast.ast1949.domain.trust.TrustPic;
import com.ast.ast1949.domain.trust.TrustRelateDealer;
import com.ast.ast1949.domain.trust.TrustTrade;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.trust.TrustBuyDto;
import com.ast.ast1949.dto.trust.TrustBuySearchDto;
import com.ast.ast1949.dto.trust.TrustSellDto;
import com.ast.ast1949.dto.trust.TrustTradeDto;
import com.ast.ast1949.persist.trust.TrustRelateDealerDao;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.trust.TrustBuyLogService;
import com.ast.ast1949.service.trust.TrustBuyService;
import com.ast.ast1949.service.trust.TrustCompanyLogService;
import com.ast.ast1949.service.trust.TrustDealerService;
import com.ast.ast1949.service.trust.TrustPicService;
import com.ast.ast1949.service.trust.TrustRelateSellService;
import com.ast.ast1949.service.trust.TrustSellService;
import com.ast.ast1949.service.trust.TrustTradeService;
import com.ast.ast1949.web.controller.BaseController;
import com.ast.ast1949.web.util.WebConst;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.file.MvcUpload;
import com.zz91.util.lang.StringUtils;

@Controller
public class TrustController extends BaseController {
	@Resource
	private TrustBuyService trustBuyService;
	@Resource
	private TrustSellService trustSellService;
	@Resource
	private TrustDealerService trustDealerService;
	@Resource
	private TrustRelateDealerDao trustRelateDealerDao;
	@Resource
	private TrustBuyLogService trustBuyLogService;
	@Resource
	private TrustCompanyLogService trustCompanyLogService;
	@Resource
	private TrustTradeService trustTradeService;
	@Resource
	private TrustRelateSellService trustRelateSellService;
	@Resource
	private TrustPicService trustPicService;
	@Resource
	private CompanyService companyService;

	@RequestMapping
	public void index(Map<String, Object> out) {

	}

	@RequestMapping
	public ModelAndView queryChase(Map<String, Object> model,
			HttpServletRequest request, PageDto<TrustBuyDto> page,
			TrustBuySearchDto searchDto) throws IOException, ParseException {
		if (StringUtils.isNotEmpty(searchDto.getFrom())) {
			searchDto.setFrom(DateUtil.toString(DateUtil.getDate(searchDto
					.getFrom().replace("T", " "), "yyyy-MM-dd"), "yyyy-MM-dd"));
		}
		if (StringUtils.isNotEmpty(searchDto.getTo())) {
			searchDto.setTo(DateUtil.toString(DateUtil.getDateAfterDays(DateUtil.getDate(searchDto.getTo().replace("T", " "),"yyyy-MM-dd"), 1), "yyyy-MM-dd"));
		}
		if (searchDto.getDealerId()!=null&&0==searchDto.getDealerId()) {
			searchDto.setDealerId(null);
		}
		page = trustBuyService.page(searchDto, page);
		return printJson(page, model);
	}

	@RequestMapping
	public void edit(Map<String, Object> out, Integer id, Integer companyId) {
		if (id == null) {
			id = 0;
		}
		out.put("id", id);
		if (companyId!=null&&companyId > 0) {
			out.put("companyId", companyId);
		}
	}
	
	/**
	 * 该公司所有小计
	 * @param out
	 * @param id
	 * @param companyId
	 */
	@RequestMapping
	public void myLog(Map<String, Object> out, Integer companyId) {
		if (companyId > 0) {
			out.put("companyId", companyId);
		}
	}

	@RequestMapping
	public void chase(Map<String, Object> out, Integer id,Integer companyId) throws IOException {
		out.put("id", id);
		if (companyId!=null&&companyId>0) {
			out.put("companyId", companyId);
		}else{
			out.put("companyId", 0);
		}
	}
	
	@RequestMapping
	public void mytrustlog(Map<String, Object> out, Integer companyId) throws IOException {
		out.put("companyId", companyId);
	}
	@RequestMapping
	public void myCompanylog(Map<String, Object> out, Integer companyId) throws IOException {
		out.put("companyId", companyId);
	}
	
	@RequestMapping
	public void add(Map<String, Object> out, Integer id){
	}
	
	/**
	 * 在后台新建一条采购信息
	 * @param out
	 * @param buyDto
	 * @param trustBuy
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView doAdd(Map<String, Object> out,TrustBuyDto buyDto, TrustBuy trustBuy) throws IOException {
		ExtResult result = new ExtResult();
		Integer i = 0;
		if (buyDto.getDealerId() != null) {
			i = trustBuyService.createBuyByAdmin(trustBuy);
			Integer did = trustRelateDealerDao.queryRelateDealerByBuyNo(trustBuy.getBuyNo());
			if (did != null && !did.equals(buyDto.getDealerId())&& buyDto.getDealerId() != 0) {
				i = trustRelateDealerDao.updateRelateDealer(buyDto.getDealerId(), trustBuy.getBuyNo());
			}
			if (did == null && buyDto.getDealerId() != 0) {
				TrustRelateDealer dealer = new TrustRelateDealer();
				dealer.setBuyNo(trustBuy.getBuyNo());
				dealer.setDealerId(buyDto.getDealerId());
				i = trustRelateDealerDao.insertRelateDealer(dealer);
			}
		}
		if (i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView queryDetail(Map<String, Object> out, Integer id)
			throws IOException {
		TrustBuyDto dto = new TrustBuyDto();
		if (id == null) {
			id = 0;
		} else {
			dto = trustBuyService.showTrustBuy(id);
		}
		out.put("id", id);
		Integer dealId = trustRelateDealerDao.queryRelateDealerByBuyNo(dto
				.getTrustBuy().getBuyNo());
		if (dealId != null) {
			dto.setDealerId(dealId);
		}
		if (StringUtils.isNotEmpty(dto.getTrustBuy().getAreaCode())) {
			dto.setAreaLabel(CategoryFacade.getInstance().getValue(
					dto.getTrustBuy().getAreaCode()));
		}
		List<TrustBuyDto> list = new ArrayList<TrustBuyDto>();
		list.add(dto);
		return printJson(list, out);
	}

	@RequestMapping
	public void mytrust(Map<String, Object> out, Integer companyId) {
		out.put("companyId", companyId);
	}

	@RequestMapping
	public void mysupply(Map<String, Object> out, Integer companyId) {
		out.put("companyId", companyId);
	}

	@RequestMapping
	public ModelAndView querySupply(Map<String, Object> model,
			HttpServletRequest request, PageDto<TrustSellDto> page,
			TrustBuySearchDto searchDto) throws IOException, ParseException {
		if (StringUtils.isNotEmpty(searchDto.getFrom())) {
			searchDto.setFrom(DateUtil.toString(DateUtil.getDate(searchDto
					.getFrom().replace("T", " "), "yyyy-MM-dd"), "yyyy-MM-dd"));
		}
		if (StringUtils.isNotEmpty(searchDto.getTo())) {
			searchDto.setTo(DateUtil.toString(DateUtil.getDateAfterDays(
					DateUtil.getDate(searchDto.getTo().replace("T", " "),
							"yyyy-MM-dd"), 1), "yyyy-MM-dd"));
		}
		if (searchDto.getDealerId()!=null&&0==searchDto.getDealerId()) {
			searchDto.setDealerId(null);
		}
		page = trustSellService.pageByCondition(searchDto, page);
		return printJson(page, model);
	}

	/**
	 * 更改供货状态
	 * @param model
	 * @param id
	 * @param status
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateStatus(Map<String, Object> model, Integer id,
			String status) throws IOException {
		ExtResult result = new ExtResult();
		Integer mm = trustSellService.editByStatus(id, status);
		if (mm.intValue() > 0) {
			result.setSuccess(true);
		}
		result.setData(mm);
		return printJson(result, model);
	}

	@RequestMapping
	public ModelAndView queryDealer(Map<String, Object> model)
			throws IOException {
		List<TrustDealer> list = new ArrayList<TrustDealer>();
		list = trustDealerService.queryAllDealer();
		return printJson(list, model);
	}
	
	@RequestMapping
	public ModelAndView queryOffer(Map<String, Object> model,Integer buyId,Integer companyId) throws IOException{
		List<Company> list = trustRelateSellService.queryCompanyByBuyId(buyId,companyId);
		return printJson(list, model);
	}
	
	@RequestMapping
	public ModelAndView queryBuyNo(Map<String, Object> model,Integer companyId) throws IOException{
		List<TrustBuy> list = trustBuyService.queryTrustByCompanyId(companyId);
		return printJson(list, model);
	}

	/**
	 * 在后台，修改采购信息，并更新交易员信息
	 * @param model
	 * @param request
	 * @param buyDto
	 * @param trustBuy
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView saveEditOfBuy(Map<String, Object> model,
			HttpServletRequest request, TrustBuyDto buyDto, TrustBuy trustBuy)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer i = 0;
		// 更新采购的信息
		i = trustBuyService.editTrustByAdmin(trustBuy);
		if (buyDto.getDealerId() != null) {
			Integer did = trustRelateDealerDao
					.queryRelateDealerByBuyNo(trustBuy.getBuyNo());
			if (did != null && !did.equals(buyDto.getDealerId())
					&& buyDto.getDealerId() != 0) {
				i = trustRelateDealerDao.updateRelateDealer(
						buyDto.getDealerId(), trustBuy.getBuyNo());
			}
			if (did == null && buyDto.getDealerId() != 0) {
				TrustRelateDealer dealer = new TrustRelateDealer();
				dealer.setBuyNo(trustBuy.getBuyNo());
				dealer.setDealerId(buyDto.getDealerId());
				i = trustRelateDealerDao.insertRelateDealer(dealer);
			}
		}
		if (i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping
	public void deallist(Map<String, Object> out) {

	}

	@RequestMapping
	public ModelAndView queryAllDealers(Map<String, Object> out,
			PageDto<TrustDealer> page) throws IOException {
		page.setSort("gmt_created");
		page.setDir("desc");
		page = trustDealerService.pageByCondition(null, page);
		return printJson(page, out);
	}

	@RequestMapping
	public ModelAndView addDealer(Map<String, Object> out, TrustDealer dealer)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer i = trustDealerService.createOneDealer(dealer.getName(),
				dealer.getTel(), dealer.getQq());
		if (i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView delDealer(Map<String, Object> out, Integer id)
			throws IOException {
		ExtResult result = new ExtResult();
		Integer i = trustDealerService.deleteDealer(id);
		if (i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView batchRefresh(Map<String, Object> out,String ids) throws IOException{
		ExtResult result = new ExtResult();
		int i = trustBuyService.batchRefresh(ids);
		if (i>0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryBuyLog(Map<String, Object>out,TrustBuyLog trustBuyLog,PageDto<TrustBuyLog> page) throws IOException{
		
		page.setSort("id");
		page.setDir("desc");
		page= trustBuyLogService.pageLog(trustBuyLog, page);
		
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryCompanyLog(Map<String, Object>out,TrustCompanyLog trustCompanyLog,PageDto<TrustCompanyLog> page) throws IOException{
		
		page.setSort("id");
		page.setDir("desc");
		page= trustCompanyLogService.pageLog(trustCompanyLog, page);
		
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView saveLog(Map<String, Object>out,TrustBuyLog trustBuyLog,HttpServletRequest request) throws IOException{
		ExtResult result = new ExtResult();
		SessionUser ssoUser = getCachedUser(request);
		if (ssoUser!=null) {
			trustBuyLog.setTrustAccount(ssoUser.getName()+"("+ssoUser.getAccount()+")");
		}
		Integer i = trustBuyLogService.createLog(trustBuyLog);
		if (i>0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView saveCompanyLog(Map<String, Object>out,TrustCompanyLog trustCompanyLog,HttpServletRequest request) throws IOException{
		ExtResult result = new ExtResult();
		SessionUser ssoUser = getCachedUser(request);
		if (ssoUser!=null) {
			trustCompanyLog.setTrustAccount(ssoUser.getName()+"("+ssoUser.getAccount()+")");
		}
		Integer i = trustCompanyLogService.createLog(trustCompanyLog);
		if (i>0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	
	@RequestMapping
	public ModelAndView deleteById(Map<String, Object> out,Integer id) throws IOException{
		ExtResult result = new ExtResult();
		int i = trustBuyService.deleteById(id);
		if (i>0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryAllDealer(String preCode, Map<String, Object> out) throws IOException {
		List<TrustDealer> list=trustDealerService.queryAllDealer();
		TrustDealer obj = new TrustDealer();
		obj.setName("全部");
		obj.setId(null);
		list.add(obj);
		PageDto<TrustDealer> page=new PageDto<TrustDealer>();
		page.setRecords(list);
		return printJson(page,out);
	}
	
	@RequestMapping
	public ModelAndView addTrustTrade(HttpServletRequest request,HttpServletResponse reponse,Map<String, Object> out,TrustTrade trade) throws Exception{
		ExtResult result = new ExtResult();
		Integer i = 0;
		if(StringUtils.isNotEmpty(trade.getCompanyName())&&StringUtils.isNumber(trade.getCompanyName())){
			trade.setCompanyId(Integer.valueOf(trade.getCompanyName()));
		}
		TrustTrade tt = trustTradeService.queryOneTrade(trade.getBuyId());
		if(tt == null){
			i = trustTradeService.createTrustTrade(trade);
		}else{
			trade.setId(tt.getId());
			i = trustTradeService.updateTrustTradeInfo(trade);
		}
		if(i>0){
			trustPicService.updateTradeIdByPicAddress(trade.getId(), trade.getPicAddress());
			result.setSuccess(true);
		}
		return printJson(result,out);
	}
	
	@RequestMapping
	public ModelAndView queryCompanyByBuyId(Integer buyId, Map<String, Object> out) throws IOException {
		List<Company> list=trustRelateSellService.queryCompanyByBuyId(buyId,null);
		if(list.size()==0){
			Company obj = new Company();
			obj.setName("无");
			obj.setId(null);
			list.add(obj);
		}
		return printJson(list,out);
	}
	
	@RequestMapping
	public ModelAndView trade(){
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryTrade(Map<String, Object> model,HttpServletRequest request, PageDto<TrustTradeDto> page,TrustBuySearchDto searchDto) throws IOException, ParseException {
		if (StringUtils.isNotEmpty(searchDto.getFrom())) {
			searchDto.setFrom(DateUtil.toString(DateUtil.getDate(searchDto
					.getFrom().replace("T", " "), "yyyy-MM-dd"), "yyyy-MM-dd"));
		}
		if (StringUtils.isNotEmpty(searchDto.getTo())) {
			searchDto.setTo(DateUtil.toString(DateUtil.getDateAfterDays(
					DateUtil.getDate(searchDto.getTo().replace("T", " "),
							"yyyy-MM-dd"), 1), "yyyy-MM-dd"));
		}
		if (searchDto.getDealerId()!=null&&0==searchDto.getDealerId()) {
			searchDto.setDealerId(null);
		}
		page = trustTradeService.queryTradeList(0, page, searchDto);
		return printJson(page, model);
	}
	
	@RequestMapping
	public ModelAndView doUpload(HttpServletRequest request, Map<String, Object> out,TrustPic pic) 
			throws IOException{
		
		String path=MvcUpload.getModalPath(WebConst.UPLOAD_MODEL_DEFAULT);
		String filename=UUID.randomUUID().toString();
		ExtResult result=new ExtResult();
		try {
			String finalname=MvcUpload.localUpload(request, path, filename);
			pic.setPicAddress(path.replace(MvcUpload.getDestRoot(), "")+"/"+finalname);
			if(pic.getId()!=null){
				trustPicService.updateTradeInfo(pic);
			}else{
				if(pic.getTradeId()==null){
					pic.setTradeId(0);
				}
				trustPicService.createTradePic(pic);
			}
			result.setSuccess(true);
			result.setData(path.replace(MvcUpload.getDestRoot(), "")+"/"+finalname);
		} catch (Exception e) {
			result.setData(MvcUpload.getErrorMessage(e.getMessage()));
		}
		return printJson(result, out);
	}
	@RequestMapping
	public ModelAndView queryTrustPic(HttpServletRequest request, Map<String, Object> out,PageDto<TrustPic> page,Integer tradeId) throws Exception {
		page = trustPicService.pageTradePicInfo(page, tradeId);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView updateDelete(Map<String, Object> out,TrustPic pic) throws Exception{
		ExtResult result=new ExtResult();
		Integer i = trustPicService.updateTradeInfo(pic);
		if(i>0){
			result.setSuccess(true);
			if(pic!=null && pic.getIsDefault()!=null){
				if(pic.getIsDefault()==1){
					result.setData("图片已置顶");
				}else if(pic.getIsDefault()==0){
					result.setData("图片已取消置顶");
				}
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateTradeInfo(Map<String, Object> out,TrustTrade trade) throws Exception{
		ExtResult result=new ExtResult();
		Integer i = trustTradeService.updateTrustTradeInfo(trade);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryTradeById(Map<String, Object> out,Integer buyId) throws Exception{
		List<TrustTrade> list=new ArrayList<TrustTrade>();
		TrustTrade trade = trustTradeService.queryOneTrade(buyId);
		if(trade != null){
			if(trade.getCompanyId() != 0){
				Company c = companyService.queryCompanyById(trade.getCompanyId());
				if(c != null){
					trade.setCompanyName(c.getName());
				}
			}
			TrustPic pic = trustPicService.queryOnePic(trade.getId());
			if(pic != null){
				trade.setPicAddress(pic.getPicAddress());
			}
		}
		list.add(trade);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryPicById(Map<String, Object> out, Integer id) throws IOException{
		List<TrustPic> list=new ArrayList<TrustPic>();
		TrustPic pic = trustPicService.queryById(id);
		list.add(pic);
		return printJson(list, out);
	}
	
	/**
	 * 获取公司id页面
	 * @param request
	 * @param out
	 * @param companyId
	 * @return
	 */
	@RequestMapping
	public ModelAndView compInfo(HttpServletRequest request,
			Map<String, Object> out, Integer companyId) {
		out.put("companyId", companyId);
		return null;
	}
	
	@RequestMapping
	public ModelAndView pubSupplyByBuyId(String account,Integer buyId,String content,Map<String, Object> out) throws IOException{
		ExtResult result  = new ExtResult();
		Integer i = trustSellService.publishTrustSellByAccount(account, buyId, content);
		if (i>0) {
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView pauseBuy(Map<String, Object> out,String ids) throws IOException{
		ExtResult result  = new ExtResult();
		do {
			Integer i = trustBuyService.pauseBuy(ids);
			if (i>0) {
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView pubBuy(Map<String, Object> out,String ids) throws IOException{
		ExtResult result  = new ExtResult();
		do {
			Integer i = trustBuyService.pubBuy(ids);
			if (i>0) {
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}
}
