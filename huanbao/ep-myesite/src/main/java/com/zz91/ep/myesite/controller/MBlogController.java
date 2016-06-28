package com.zz91.ep.myesite.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.domain.mblog.MBlogSystem;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.service.common.MyEsiteService;
import com.zz91.ep.service.mblog.MBlogFollowService;
import com.zz91.ep.service.mblog.MBlogInfoService;
import com.zz91.ep.service.mblog.MBlogService;
import com.zz91.ep.service.mblog.MBlogSystemService;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.lang.StringUtils;

@Controller
public class MBlogController extends BaseController {
	@Resource
	private MyEsiteService myEsiteService;
	@Resource
	private MBlogInfoService mBlogInfoService;
	@Resource
	private MBlogFollowService mBlogFollowService;
	@Resource
	private MBlogService mBlogService;
	@Resource
	private MBlogSystemService mBlogSystemService;
	@RequestMapping 
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request){
		// 读取用户信息
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return null;
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			return null;
		}
		if (info == null) {
			return null;
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return null;
		}
		
		out.put("info", info);
		// 查询出统计没有读的回复和评论
		Integer commentCount = mBlogSystemService.queryCountById(info.getId(),
				"3", "0");
		out.put("commentCount", commentCount);
		//查询出新粉丝的count
		out.put("newFansCount", mBlogFollowService.queryFansCountByConditions(
				info.getId(), "0"));
		//读取出有人@我的人
		Integer anteCount = mBlogSystemService.queryCountById(info.getId(), "2", "0");
		out.put("anteCount", anteCount);
		//读取出系统消息
		Integer messageCount = mBlogSystemService.queryCountById(info.getId(), "4", "0");
		out.put("messageCount", messageCount);

		// 查询热门话题
		List<MBlog> talkBlogList = mBlogService.queryTopicTitle(10);
		out.put("talkBlogList", talkBlogList);		
		
		myEsiteService.init(out, getCachedUser(request).getCid());
		return null;
		
	}
	
	@RequestMapping
	public ModelAndView ajaxSend(Map<String, Object> out,
			HttpServletRequest request, String contents, String photos){
		ExtResult result=new ExtResult();
		do {
			// 存入数据库去空格
			String content = "";
			contents = contents.trim().replaceAll("：", ":");
			for (int i = 0; i < contents.length(); i++) {
				if (i != 0 && contents.charAt(i - 1) == ' '
						&& contents.charAt(i) == ' ') {
					continue;
				} else {
					content += contents.charAt(i);
				}
			}
			content = content.replace("\n", "");
			EpAuthUser user = getCachedUser(request);
			MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
					.getCid());
			if (info != null) {
				// 判断是否是话题 截取##号之间的值
				String title = "";
				// 首先截取第一个#号
				if (content.indexOf("#") != -1) {
					Integer idx = content.indexOf("#");
					Integer count = 0;// 用于标记是否是当前的#
					for (int i = idx; i < contents.length() - 1; i++) {
						// 如果当前#的下一个是#就让他继续
						if (contents.charAt(i) == '#'
								&& content.charAt(i + 1) == '#') {
							continue;
						} else {
							title += content.charAt(i);
							count++;
							// 判断是否是最后一个字符
							if (i + 1 == content.length() - 1
									&& content.charAt(i + 1) != '#') {
								title = "";
								break;
							}
							if (count != 1 && content.charAt(i + 1) == '#') {
								title = title + content.charAt(i + 1);
								break;
							}
						}
					}
				}
				// 添加
				MBlog mBlog = new MBlog();
				if (!title.equals("")) {
					mBlog.setTitle(title);
				}
				mBlog.setInfoId(info.getId());
				mBlog.setContent(content);
				mBlog.setPhotoPath(photos);
				if (!title.equals("")) {
					mBlog.setType("2");// 代表话题
				} else {
					mBlog.setType("1");
				}
				Integer i = mBlogService.insert(mBlog);
				if(i != null && i.intValue() > 0){
					result.setSuccess(true);
					//添加到系统表
					if (content.indexOf("@") != -1) {
						Map<String, Object> maps = mBlogService.replceMblogAnte(content);
						for (String mapKey : maps.keySet()) {
							// 查询出对应的人的id
							MBlogInfo mBlogInfo = mBlogInfoService.queryInfoByInfoByName(mapKey);
							if (mBlogInfo != null) {
								if(!mBlogInfo.getId().equals(info.getId())){
									// 添加到系统表
									MBlogSystem system = new MBlogSystem();
									system.setFromId(info.getId());
									system.setToId(mBlogInfo.getId());
									system.setContent(String.valueOf(i));
									system.setType("2");
									mBlogSystemService.insert(system);
								}
							} 
						}
					}
				}
				
			}
			
		} while (false);
		
		return printJson(result, out);
	}	
	
}
