/**
 * 
 */
package com.zz91.top.app.task;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Task;
import com.taobao.api.request.TopatsTradesSoldGetRequest;
import com.taobao.api.response.TopatsTradesSoldGetResponse;
import com.zz91.top.app.config.TopConfig;
import com.zz91.top.app.domain.SysTask;
import com.zz91.top.app.domain.TbShopAccess;
import com.zz91.top.app.persist.SysTaskMapper;
import com.zz91.top.app.persist.TbShopAccessMapper;
import com.zz91.top.app.persist.TbTradeMapper;
import com.zz91.util.datetime.DateUtil;

/**
 * 扫描店铺 => AccessToken
 * 最晚 trade 时间 + 最晚 Task end_time => task start and end time
 * create task
 * @author mays
 *
 */
@Service
public class ScanAccessTokenJob {

	@Resource
	private TbShopAccessMapper tbShopAccessMapper;
	@Resource
	private TbTradeMapper tbTradeMapper;
	@Resource
	private SysTaskMapper sysTaskMapper;
	@Resource
	private TopConfig topConfig;
	

	final static int LIMIT=100;
	final static String METHOD="taobao.topats.trades.sold.get";
	final static String TOP_DATE_FORMAT="yyyyMMdd";
	final static String TOP_FIELDS="tid,num,num_iid,status,title,price,total_fee,created,modified,pay_time,end_time,seller_nick,buyer_nick,trade_from,payment";
	final static String ISV_TASK_DUPLICATE="isv.task-duplicate";
	
	public void doTask() throws ParseException{
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>scan access token: "+DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		//获取 access token
		//sub1.获取最晚的 trade 时间
		//sub2.最晚的task end_time
		//如果都没有则获取三个月数据
		//如果有,则比较最晚的,创建最晚时间第二天到昨天的数据读取任务
		
		int fromId = 0;
		Date now=new Date();
		List<TbShopAccess> list=null;
		TaobaoClient client=new DefaultTaobaoClient(topConfig.getUrl(), topConfig.getAppKey(), topConfig.getAppSecret());
		
		do {
			list = tbShopAccessMapper.queryAccess(fromId, LIMIT);
			
			for (TbShopAccess tbShopAccess: list) {
				fromId=tbShopAccess.getId();
				Date tradeDate=tbTradeMapper.queryLastCreated(tbShopAccess.getTaobaoUserNick());
				tradeDate = compareDate(tradeDate);
				Date taskDate=sysTaskMapper.queryLastEndDate(tbShopAccess.getTaobaoUserNick(), METHOD);
				taskDate = compareDate(taskDate);
				
				Date targetFromDate=tradeDate;
				if(tradeDate.getTime()<taskDate.getTime()){
					targetFromDate = taskDate;
				}
				
				if(DateUtil.getIntervalDays(now, targetFromDate) <=1){
					continue ;
				}
				
				targetFromDate = DateUtil.getDate(DateUtil.getDateAfterDays(targetFromDate, 1), "yyyy-MM-dd");
				Date targetToDate = DateUtil.getDate(DateUtil.getDateAfterDays(now, -1), "yyyy-MM-dd");
				
				TopatsTradesSoldGetRequest req=new TopatsTradesSoldGetRequest();
				req.setFields(TOP_FIELDS);
				req.setStartTime(DateUtil.toString(targetFromDate, TOP_DATE_FORMAT));
				req.setEndTime(DateUtil.toString(targetToDate, TOP_DATE_FORMAT));
				req.setIsAcookie(true);
				TopatsTradesSoldGetResponse response = null;
				try {
					response = client.execute(req , tbShopAccess.getAccessToken());
				} catch (ApiException e) {
					e.printStackTrace();
					continue ;
				}
				
				if(response.getSubCode()!=null 
						&& !response.getSubCode().equals(ISV_TASK_DUPLICATE)){
					continue ;
				}
				
				Task task = response.getTask();
				
				SysTask t=new SysTask();

				String taskID = "";
				if(task==null){
					taskID = response.getSubMsg();
					taskID = taskID.substring(17);
					t.setTaskId(taskID);
				}else{
					t.setTaskId(String.valueOf(task.getTaskId()));
				}
				
				Integer c=sysTaskMapper.countByTaskId(t.getTaskId());
				if(c!=null && c.intValue()>0){
					continue ;
				}
				
				t.setAccessToken(tbShopAccess.getAccessToken());
				t.setGmtReadStart(targetFromDate);
				t.setGmtReadEnd(targetToDate);
				t.setMethod(METHOD);
				t.setGmtSubmit(now);
				t.setNick(tbShopAccess.getTaobaoUserNick());
				t.setReadFields(TOP_FIELDS);
				t.setStatus(2); // new 
				
				sysTaskMapper.insert(t);
				
			}
		} while (list!=null && list.size()>0);
	}
	
	
	private Date compareDate(Date d1){
		if(d1==null){
			d1 = DateUtil.getDateAfterMonths(new Date(), -3);
		}
		return d1;
	}
	
	public static void main(String[] args) throws ParseException {
		String t="相同的任务已经存在：TaskId=3625932";
		System.out.println(t.substring(17));
	}
	
	
}
