package com.ast.ast1949.web.controller.zz91;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.spot.SpotAuction;
import com.ast.ast1949.domain.spot.SpotAuctionLog;
import com.ast.ast1949.domain.spot.SpotOrder;
import com.ast.ast1949.domain.spot.SpotPromotions;
import com.ast.ast1949.domain.spot.SpotTrust;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotAuctionDto;
import com.ast.ast1949.dto.spot.SpotAuctionLogDto;
import com.ast.ast1949.dto.spot.SpotOrderDto;
import com.ast.ast1949.dto.spot.SpotPromotionsDto;
import com.ast.ast1949.dto.spot.SpotTrustDto;
import com.ast.ast1949.service.spot.SpotAuctionLogService;
import com.ast.ast1949.service.spot.SpotAuctionService;
import com.ast.ast1949.service.spot.SpotOrderService;
import com.ast.ast1949.service.spot.SpotPromotionsService;
import com.ast.ast1949.service.spot.SpotTrustService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.lang.StringUtils;

/**
 * 现货后台管理页面
 *	author:kongsj
 *	date:2013-3-9
 */
@Controller
public class SpotController extends BaseController {
	
	@Resource
	private SpotPromotionsService spotPromotionsService;
	@Resource
	private SpotAuctionService spotAuctionService;
	@Resource
	private SpotAuctionLogService spotAuctionLogService;
	@Resource
	private SpotOrderService spotOrderService;
	@Resource
	private SpotTrustService spotTrustService;
	
	@RequestMapping
	public ModelAndView cuxiao(Map<String,Object>out){
		
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView jingpai(Map<String,Object> out){
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView order(Map<String,Object>out){
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView queryPromotions(PageDto<SpotPromotionsDto> page,Map<String, Object> out,SpotPromotions spotPromotions) throws IOException{
		if(StringUtils.isEmpty(page.getSort())){
			page.setSort("id");
		}
		page = spotPromotionsService.pagePromotions(spotPromotions, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryAuction(PageDto<SpotAuctionDto> page,Map<String, Object> out,SpotAuction spotAuction) throws IOException{
		if(StringUtils.isEmpty(page.getSort())){
			page.setSort("id");
		}
		page = spotAuctionService.pageByCondition(spotAuction, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryOrder(PageDto<SpotOrderDto>page,Map<String, Object>out,SpotOrder spotOrder) throws IOException{
		if(StringUtils.isEmpty(page.getSort())){
			page.setSort("id");
		}
		page = spotOrderService.pageSpotOrder(spotOrder, page);
		return printJson(page,out);
	}
	
	@RequestMapping
	public ModelAndView updateStatusForPromotions(Map<String, Object> out,Integer id,String status) throws IOException{
		ExtResult ext = new ExtResult();
		do{
			if(id==null||StringUtils.isEmpty(status)){
				break;
			}
			Integer i = spotPromotionsService.updateStatusById(id, status);
			if(i>0){
				ext.setSuccess(true);
			}
		}while(false);
		return printJson(ext, out);
	}
	
	@RequestMapping
	public ModelAndView updateStatusForAuction(Map<String, Object> out,Integer id,String status) throws IOException{
		ExtResult ext = new ExtResult();
		do{
			if(id==null||StringUtils.isEmpty(status)){
				break;
			}
			Integer i = spotAuctionService.updateStatusById(id, status);
			if(i>0){
				ext.setSuccess(true);
			}
		}while(false);
		return printJson(ext, out);
	}
	
	@RequestMapping
	public ModelAndView queryAuctionLog(Map<String, Object> out,SpotAuctionLog spotAuctionLog,PageDto<SpotAuctionLogDto> page) throws IOException{
		page.setPageSize(5);
		if(StringUtils.isEmpty(page.getSort())){
			page.setDir("id");
		}
		page = spotAuctionLogService.pageAuctionLog(spotAuctionLog, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView addToPromotion(Map<String, Object> out,
			SpotPromotions spotPromotions) throws IOException {
		ExtResult result = new ExtResult();
		do {
			// 后台推荐 默认通过
			spotPromotions.setCheckStatus(SpotPromotionsService.CHECK_STATUS_PASS);
			Integer i = spotPromotionsService.addOnePromotions(spotPromotions);
			if(i<=0){
				break;
			}
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView addToAuction(Map<String, Object>out,SpotAuction spotAuction) throws IOException{
		ExtResult result = new ExtResult();
		do {
			Integer i = spotAuctionService.recommendByAdmin(spotAuction);
			if(i<=0){
				break;
			}
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView trust(Map<String, Object>out ){
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView queryTrust(Map<String, Object> out,SpotTrustDto spotTrustDto,PageDto<SpotTrustDto> page) throws IOException{
		page.setSort("id");
		page = spotTrustService.pageList(spotTrustDto, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView updateCheckedForTrust(Map<String, Object> out,String isChecked,Integer id) throws IOException{
		ExtResult result = new ExtResult();
		do {
			Integer i = spotTrustService.updateChecked(isChecked, id);
			if(i>0){
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateDeleteForTrust(Map<String, Object> out,String isDelete,Integer id) throws IOException{
		ExtResult result = new ExtResult();
		do {
			Integer i = spotTrustService.updateDelete(isDelete, id);
			if(i>0){
				result.setSuccess(true);
			}
		}while (false);
		return printJson(result, out);
	}
	
	/**
	 * 更新采购委托信息，如果id为0，则添加一条采购委托
	 * @param out
	 * @param spotTrust
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateTrustInfo(Map<String, Object> out,SpotTrust spotTrust) throws IOException{
		ExtResult result = new ExtResult();
		do {
			Integer i = 0;
			if(spotTrust.getId()==null||spotTrust.getId()==0){
				spotTrust.setIsChecked("0");
				i = spotTrustService.insert(spotTrust);
			}else{
				i = spotTrustService.update(spotTrust);
			}
			
			if(i>0){
				result.setSuccess(true);
			}
		} while (false);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryTrustById(Map<String, Object>out,Integer id) throws IOException{
		SpotTrust obj = spotTrustService.queryById(id);
//		SpotTrustDto dto =  new SpotTrustDto();
		List<SpotTrust> list = new ArrayList<SpotTrust>();
		list.add(obj);
//		dto.setSpotTrust(obj);
		return printJson(list, out);
	}

}
