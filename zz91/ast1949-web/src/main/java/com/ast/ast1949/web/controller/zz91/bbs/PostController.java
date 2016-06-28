/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-24
 */
package com.ast.ast1949.web.controller.zz91.bbs;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.score.ScoreChangeDetailsDo;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.PostDto;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.service.score.ScoreChangeDetailsService;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.log.LogUtil;

/**
 * 帖子管理
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
@Controller
public class PostController extends BaseController {
	@Resource
	private BbsPostService bbsPostService;
	@Resource
	private ScoreChangeDetailsService scoreChangeDetailsService;
	@Resource
	private BbsUserProfilerService bbsUserProfilerService;
	
	final static String BBS_CLIENT_SUCCESS_OPERTION = "bbs_client_post_success";
	final static String BBS_CLIENT_FAILURE_OPERTION = "bbs_client_post_failure";
	final static String BBS_ADMIN_POST_OPERTION = "bbs_admin_post";
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	@RequestMapping
	public void index(Map<String, Object> out) {
		
	}
	
	@RequestMapping
	public ModelAndView queryPost(Map<String, Object> model, HttpServletRequest request,
			PageDto<PostDto> page,BbsPostDO post, String companyName) throws IOException{
		if(StringUtils.isNotEmpty(companyName)){
//			Integer id=companyService.queryCompanyIdByName(companyName);
		}
		
		page = bbsPostService.pagePostByAdmin(getCachedUser(request).getAccount(), post, page);

		return printJson(page, model);
	}
	
	@RequestMapping
	public ModelAndView updateCheckStatus(HttpServletRequest request, Map<String, Object> out, 
			Integer id, String checkStatus,String account, Integer companyId) throws IOException, ParseException{
		SessionUser sessionUser = getCachedUser(request);
		Integer im =bbsPostService.updateCheckStatus(id, checkStatus, sessionUser.getAccount());
		ExtResult result = new ExtResult();
		if (im.intValue()>0) {
			result.setSuccess(true);
			bbsUserProfilerService.updatePostNumber(account);
			//日志系统 记录运营审核帖子 
			if("1".equals(checkStatus)){
				scoreChangeDetailsService.saveChangeDetails(new ScoreChangeDetailsDo(companyId, null, "get_post_bbs", null, id, null));
				LogUtil.getInstance().mongo(sessionUser.getAccount(), BBS_CLIENT_SUCCESS_OPERTION, null, "{'id':'"+id+"','companyId':"+companyId+"','date':'"+DateUtil.toString(new Date(), DATE_FORMAT)+"'}");
				LogUtil.getInstance().log(sessionUser.getAccount(), BBS_CLIENT_SUCCESS_OPERTION, null,"{'id':'"+id+"','companyId':"+companyId+"','date':'"+DateUtil.toString(new Date(), DATE_FORMAT)+"'}");
			}else{
				LogUtil.getInstance().mongo(sessionUser.getAccount(), BBS_CLIENT_FAILURE_OPERTION, null, "{'id':'"+id+"','companyId':'"+companyId+"','date':'"+DateUtil.toString(new Date(), DATE_FORMAT)+"'}");
				LogUtil.getInstance().log(sessionUser.getAccount(), BBS_CLIENT_FAILURE_OPERTION, null,"{'id':'"+id+"','companyId':"+companyId+"','date':'"+DateUtil.toString(new Date(), DATE_FORMAT)+"'}");
			}
		}
		result.setData(im);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateDelete(HttpServletRequest request, Map<String, Object> out,
			String isDel, Integer id) throws IOException{
		
		Integer i=bbsPostService.updateIsDel(id, isDel);
		
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView delete(HttpServletRequest request, Map<String, Object> out,
			Integer id) throws IOException{
		Integer i=bbsPostService.deleteBbsPost(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public void edit(Map<String, Object> out,Integer id, Integer companyId) {
		if(id==null){
			id=0;
		}
		out.put("id", id);
		out.put("companyId", companyId);
	}
	
	@RequestMapping
	public ModelAndView queryOnePost(HttpServletRequest request, Map<String, Object> out,
			Integer id) throws IOException{
		
//		List<BbsPostDO> list=new ArrayList<BbsPostDO>();
		BbsPostDO post=bbsPostService.queryPostById(id);
		PostDto dto=new PostDto();
		dto.setPost(post);
		dto.setPostTypeName(bbsPostService.queryPostTypeName(post.getPostType()));
//		list.add(post);
		return printJson(dto, out);
	}
	
	@RequestMapping
	public ModelAndView createPost(HttpServletRequest request, Map<String, Object> out,
			BbsPostDO post, String replyTimeStr, String postTimeStr) throws IOException, ParseException{
//		if(StringUtils.isNotEmpty(postTimeStr)){
//			try {
//				post.setPostTime(DateUtil.getDate(postTimeStr, "yyyy-M-d HH:mm"));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//		if(StringUtils.isNotEmpty(replyTimeStr)){
//			try {
//				post.setReplyTime(DateUtil.getDate(replyTimeStr, "yyyy-M-d HH:mm"));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
		
		SessionUser sessionUser = getCachedUser(request);
		Integer i=bbsPostService.createPostByAdmin(post, sessionUser.getAccount());
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
			result.setData(i);
			// 日志系统 记录 后台人员发布的互助帖子
			LogUtil.getInstance().mongo(sessionUser.getAccount(), BBS_ADMIN_POST_OPERTION, null, "{'id':'"+i+"','date':'"+DateUtil.toString(new Date(), DATE_FORMAT)+"'}");
			LogUtil.getInstance().log(sessionUser.getAccount(), BBS_ADMIN_POST_OPERTION,null,"{'id':'"+i+"','date':'"+DateUtil.toString(new Date(), DATE_FORMAT)+"'}");
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updatePost(HttpServletRequest request, Map<String, Object> out,
			BbsPostDO post, String replyTimeStr, String postTimeStr) throws IOException{
		if(StringUtils.isNotEmpty(postTimeStr)){
			try {
				post.setPostTime(DateUtil.getDate(postTimeStr, "yyyy-M-d HH:mm"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(replyTimeStr)){
			try {
				post.setReplyTime(DateUtil.getDate(replyTimeStr, "yyyy-M-d HH:mm"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Integer i=bbsPostService.updatePostByAdmin(post);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView content(HttpServletRequest request, Map<String, Object> out, Integer id, String success){
		out.put("id", id);
		out.put("content", bbsPostService.queryContent(id));
		out.put("success", success);
		return null;
	}
	
	@RequestMapping
	public ModelAndView doUpdateContent(HttpServletRequest request, Map<String, Object> out, 
			Integer id, String content) throws IOException{
		Integer i=bbsPostService.updateContent(id, content);
		if(i!=null && i.intValue()>0){
			out.put("success", "1");
		}else{
			out.put("success", "0");
		}
		out.put("id", id);
		return new ModelAndView("redirect:content.htm");
	}
	
	@RequestMapping
	public ModelAndView queryTypeChild(HttpServletRequest request, String parentCode, Map<String, Object> out) 
		throws IOException{
		
		List<ExtTreeDto> postType=bbsPostService.queryPostTypechild(parentCode);
		return printJson(postType, out);
	}
	
//	public static void main(String[] args) {
//		long startTime = System.currentTimeMillis();
//		LogUtil.getInstance().init("web.properties");
//		
//		//select test
//		//param
//		Map<String, Object> param=new HashMap<String, Object>();
//		param.put("operator", "kongsj");	//模糊查询
//		param.put("appCode", "dev");
//		param.put("time",LogUtil.getInstance().mgCompare(">=","1340000000000","<=","1360000000000"));	//逻辑运算符查询
//		//param.put("columns", logutil.mgColumns("appCode","time"));		//要查询的列,可不指定
//		//param.put("sort", "time");							//排序字段
//		//param.put("dir", "desc");							//排序 asc,desc
//		Long sdf=new Long("1346747120390");
//		Date date = new Date(sdf);
//		
//		//param.put("data.age", logutil.mgIn(18,22,33,21));	//in查询
////		param.put("or",new JSONObject[]{logutil.mgkv("appCode","news"),
////				logutil.mgkv("appCode", "dev")});	//逻辑or查询,
//		
//		//result		
//		try {
//			
//			JSONObject res=LogUtil.getInstance().readMongo(param, 0, 10);
//			List<JSONObject> list =res.getJSONArray("records"); 
//			for(int i=0;i<list.size();i++){
//				JSONObject j = (JSONObject)JSONSerializer.toJSON(list.get(i));
//				System.out.println(j);
//			}
//			long endTime = System.currentTimeMillis();
//			System.out.println("时间:"+(endTime-startTime));
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//	}
}
