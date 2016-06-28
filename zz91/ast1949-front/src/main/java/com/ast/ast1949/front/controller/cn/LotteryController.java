package com.ast.ast1949.front.controller.cn;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyLottery;
import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.company.CompanyLotteryService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.util.PageCacheUtil;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.velocity.AddressTool;
/**
 *  活动截止日期为 2013-3-15
 * @author kongsj
 *
 */
@Controller
public class LotteryController extends BaseController {

	@Resource
	private CompanyLotteryService companyLotteryService;
	@Resource
	private CompanyService companyService;
	
	@RequestMapping
	public ModelAndView index(HttpServletResponse response, HttpServletRequest request,
			Map<String, Object> out) throws ParseException {
		// 中奖榜
		List<CompanyLottery> list = companyLotteryService.queryCompanyLotteryed(20);
		List<CompanyLottery> nlist = new ArrayList<CompanyLottery>();
		for (CompanyLottery obj:list) {
			Company company = companyService.queryCompanyById(obj.getCompanyId());
			if(company!=null&&StringUtils.isNotEmpty(company.getDomainZz91())){
				obj.setLotteryCode(companyService.queryCompanyById(obj.getCompanyId()).getDomainZz91());
			}
			nlist.add(obj);
		}
		out.put("list", nlist);
		
		do{
			// 如果截止日期大于或等于当前时间 活动未结束
//			if(validateIsEnd()<0){
//				break;
//			}
			// 如果登陆，获取抽奖次数
			SsoUser ssoUser = getCachedUser(request);
			if(ssoUser!=null){
				out.put("lotteryCount", companyLotteryService.queryCountLotteryByCompanyId(ssoUser.getCompanyId()));
			}
		}while(false);
		PageCacheUtil.setNoCDNCache(response);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView miniUp(HttpServletResponse response,HttpServletRequest request,Map<String, Object> out){
		PageCacheUtil.setNoCDNCache(response);
		return new ModelAndView();
	}
	
	@RequestMapping
	public ModelAndView getLottery(HttpServletResponse response,HttpServletRequest request,String lotteryCode,Map<String, Object>out,Integer lotteryId) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isEmpty(lotteryCode)){
			return printJson(map, out);
		}
		Integer i = companyLotteryService.updateLottery(lotteryMap.get(lotteryCode), lotteryId);
		if(i>0){
			map.put("result", 1);
		}
		PageCacheUtil.setNoCDNCache(response);
		return printJson(map, out);
	}
	
	/**
	 * 抽奖 -> 更新奖品 -> 更新抽奖记录为已抽奖 -> 返回flash代码
	 * @param request
	 * @param out
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	@RequestMapping
	public ModelAndView doLottery(HttpServletResponse response,HttpServletRequest request,Map<String, Object>out) throws IOException, ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		SsoUser ssoUser = getCachedUser(request);
		if(ssoUser==null){
			return printJson(map, out);
		}
		CompanyLottery companyLottery = companyLotteryService.queryLotteryByCompanyId(ssoUser.getCompanyId());
		if(companyLottery==null){
			return printJson(map, out);
		}
		// 转盘角度
//		new String[]{"0","22","60","90","120","150","180","210","240","270","300","330"};
		String[] arrTurn = companyLottery.getLotteryCode().split(",");
		Random random = new Random();
		int i = random.nextInt(arrTurn.length);
		Integer hudu = 720 + Integer.valueOf(turnMap.get(arrTurn[i]));
		String lottery = lotteryMap.get(String.valueOf(arrTurn[i]));
		String tips = "您抽中的奖品是" + lottery;
		// 活动过期
		if(validateIsEnd()<0){
			hudu = 720;
			tips = "本次大转盘活动已结束，请继续关注ZZ91，更多精彩等着您来参与！";
		}
		String data = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='480' height='480' id='turnplate'>"
				+ "<param name='allowScriptAccess' value='always' />"
				+ "<param name='FlashVars' id='FlashVars' value='fvar=%s&tips=%s'>"
				+ "<param name='movie' value='"+AddressTool.getAddress("img")+"/zz91/zhuanti/prize2013/images/turnplate.swf'>"
				+ "<param name='menu' value='false'>"
				+ "<param name='quality' value='high'>"
				+ "<param name='wmode' value='transparent'>"
				+ "<embed src='"+AddressTool.getAddress("img")+"/zz91/zhuanti/prize2013/images/turnplate.swf' FlashVars='fvar=%s&tips=%s' id='FlashVars'  width='480' height='480'  quality='high' id='turnplate' name='turnplate' wmode='transparent' allowScriptAccess='always'  pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash'>"
				+ "</embed>" + "</object>";
		data = String.format(data, new Object[]{String.valueOf(hudu),tips,String.valueOf(hudu),tips});
		map.put("data", data);
		// 更新抽奖记录状态
		companyLotteryService.sucOneLottery(companyLottery.getId());
		// 更新奖品
		companyLotteryService.updateLottery(lottery, companyLottery.getId());
		PageCacheUtil.setNoCDNCache(response);
		return printJson(map, out);
	}

	final static Map<String,String> lotteryMap = new HashMap<String,String>();
	static {
		lotteryMap.put("1","笔记本");
		lotteryMap.put("2","2013商务大全");
		lotteryMap.put("3","黄金展位广告1个月");
		lotteryMap.put("4","关键字排名一个月");
		lotteryMap.put("5","再生通会员一年");
		lotteryMap.put("6","塑交会展位一个");
		lotteryMap.put("7","独家广告一个月");
		lotteryMap.put("8","首页广告一个月");
		lotteryMap.put("9","再生通服务年限增加一个月");
		lotteryMap.put("10","明星企业采访");
		lotteryMap.put("11","现货商城推广");
		lotteryMap.put("12","冲锋衣");
	}
	
	final static Map<String,String> turnMap = new HashMap<String,String>();
	static {
		turnMap.put("1","350");
		turnMap.put("2","20");
		turnMap.put("3","50");
		turnMap.put("4","75");
		turnMap.put("5","125");
		turnMap.put("6","150");
		turnMap.put("7","180");
		turnMap.put("8","200");
		turnMap.put("9","220");
		turnMap.put("10","270");
		turnMap.put("11","300");
		turnMap.put("12","310");
	}
	
	private int validateIsEnd() throws ParseException{
		String endStr = ParamUtils.getInstance().getValue("website_end_time", "lottery_end_time");
		if(endStr==null){
			endStr = "2013-3-15";
		}
		int i = DateUtil.getIntervalDays(DateUtil.getDate(endStr, "yyyy-MM-dd"), new Date());
		return i;
	}
	
}
