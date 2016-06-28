/**
 * 
 */
package com.zz91.top.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradesSoldGetResponse;
import com.zz91.top.app.config.TopConfig;
import com.zz91.top.app.service.TbTradeService;

/**
 * @author mays
 *
 */
@Controller
public class TradeController extends BaseController {

	@Resource
	private TopConfig topConfig;
	@Resource
	private TbTradeService tbTradeService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		TaobaoClient client=new DefaultTaobaoClient(topConfig.getUrl(), topConfig.getAppKey(), topConfig.getAppSecret());
		TradesSoldGetRequest req=new TradesSoldGetRequest();
		req.setFields("tid,num,num_iid,status,title,price,total_fee,created,modified,pay_time,end_time,seller_nick,buyer_nick,trade_from,payment");
		try {
			Date from = SimpleDateFormat.getDateTimeInstance().parse("2013-11-01 00:00:00");
			req.setStartCreated(from);
			Date to = SimpleDateFormat.getDateTimeInstance().parse("2013-11-05 23:59:59");
			req.setEndCreated(to);
			req.setStatus("ALL_WAIT_PAY");
//			req.setBuyerNick("sandbox_hunmei");
//		req.setType("game_equipment");
//		req.setExtType("service");
//		req.setRateStatus("RATE_UNBUYER");
//		req.setTag("time_card");
			req.setPageNo(1L);
			req.setPageSize(40L);
			req.setUseHasNext(true);
			req.setIsAcookie(false);
			TradesSoldGetResponse response = client.execute(req , "6201b04a0baegi638a1a379ee360da4f7792c65117c54113611761560");
			
			List<Trade> list=response.getTrades();
			if(list!=null){
				for(Trade trade: list){
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>:::"+trade.getTitle()+","+trade.getBuyerNick());
					tbTradeService.createFromTaobao(trade);
				}
			}
				
			
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView sync(HttpServletRequest request, Map<String, Object> out){
		
//		TaobaoClient client=new DefaultTaobaoClient(topConfig.getUrl(), 
//				topConfig.getAppKey(), topConfig.getAppSecret());
//		
//		TopatsTradesSoldGetRequest req=new TopatsTradesSoldGetRequest();
//		req.setFields("tid,seller_nick,buyer_nick,payment");
//		//计算开始时间 从数据库中获取最后一条的时间，如果没有则三个月前的时间点
//		req.setStartTime("20131001");
//		req.setEndTime("20131030");
//		req.setIsAcookie(true);
//		
//		SessionUser sessionUser = getSessionUser(request);
//		try {
//			TopatsTradesSoldGetResponse response = client.execute(req , sessionUser.getAccessToken());
//			Task task=response.getTask();
//			System.out.println("==================> task ID:"+task.getTaskId());
//			
////			TradeFullinfoGetResponse rsp = TaobaoUtils.parseResponse("json", TradeFullinfoGetResponse.class);
//			
//			
//		} catch (ApiException e) {
//			e.printStackTrace();
//		}
		
		
		
//		TopatsResultGetRequest resp=new TopatsResultGetRequest();
//		resp.setTaskId(3625780L);
//		try {
//			TopatsResultGetResponse rsp = client.execute(resp);
//			
//			if (rsp.isSuccess() && rsp.getTask().getStatus().equals("done")) {
//
//		         Task task = rsp.getTask();
//
//		         String url = task.getDownloadUrl();
//
//		         File taskFile = AtsUtils.download(url, new File("/tmp/topats/result")); // 下载文件到本地
//
//		         File resultFile = new File("/tmp/topats/unzip", task.getTaskId() + ""); // 解压后的结果文件夹
//
//		         List<File> files = AtsUtils.unzip(taskFile, resultFile); // 解压缩并写入到指定的文件夹
//
//		         for(File file:files){
//		        	 System.out.println(file.getName());
//		         }
//
//			} else {
//	
//			         // TODO 处理错误信息
//	
//			}
//		} catch (ApiException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
		return null;
	}
}
