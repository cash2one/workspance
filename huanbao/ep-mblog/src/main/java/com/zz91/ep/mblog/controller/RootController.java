package com.zz91.ep.mblog.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.domain.common.Face;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.domain.mblog.MBlogComment;
import com.zz91.ep.domain.mblog.MBlogFollow;
import com.zz91.ep.domain.mblog.MBlogGroup;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.domain.mblog.MBlogSent;
import com.zz91.ep.domain.mblog.MBlogSystem;
import com.zz91.ep.domain.sys.SysArea;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogCommentDto;
import com.zz91.ep.dto.mblog.MBlogDto;
import com.zz91.ep.dto.mblog.MBlogInfoDto;
import com.zz91.ep.dto.mblog.MBlogSystemDto;
import com.zz91.ep.service.common.SysAreaService;
import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.mblog.MBlogCommentService;
import com.zz91.ep.service.mblog.MBlogFollowService;
import com.zz91.ep.service.mblog.MBlogGroupService;
import com.zz91.ep.service.mblog.MBlogInfoService;
import com.zz91.ep.service.mblog.MBlogSentService;
import com.zz91.ep.service.mblog.MBlogService;
import com.zz91.ep.service.mblog.MBlogSystemService;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.auth.ep.EpAuthUtils;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;
import com.zz91.util.tags.TagsUtils;

@Controller
public class RootController extends BaseController {

	@Resource
	private CompAccountService compAccountService;
	@Resource
	private MBlogFollowService mBlogFollowService;
	@Resource
	private MBlogGroupService mBlogGroupService;
	@Resource
	private MBlogService mBlogService;
	@Resource
	private MBlogSystemService mBlogSystemService;
	@Resource
	private MBlogCommentService mBlogCommentService;
	@Resource
	private MBlogInfoService mBlogInfoService;
	@Resource
	private CompProfileService compProfileService;
	@Resource
	private MBlogSentService mBlogSentService;
	@Resource
	private SysAreaService sysAreaService;

	@RequestMapping
	public ModelAndView index(Map<String, Object> out,
			HttpServletRequest request, String groupType, Integer groupId,
			String type, String showtype,PageDto<MBlogDto> page) {
		page.setLimit(15);
		// 读取用户信息
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		} else {
			out.put("info", info);

			out.put("showtype", showtype);
			// 查询粉丝数
			out.put("fansCount", mBlogFollowService.queryFansCountByConditions(
					info.getId(), null));
			// 查询关注数
			out.put("followCount", mBlogFollowService
					.queryFollowCountByConditions(info.getId(), null,null));
			// 查询微博数
			out.put("mblogCount", mBlogService.queryAllCountMBlogById(info
					.getId()));

			// 可能感兴趣的人 先查询出自己的信息
			List<MBlogInfo> infoList = new ArrayList<MBlogInfo>();
			CompProfile compProfile = compProfileService
					.queryCompProfileById(user.getCid());
			// 查询匹配地区
			List<MBlogInfo> uinfoList = mBlogInfoService.queryInfobyAreaCode(
					info.getAreaCode(), info.getProvinceCode());
			// 匹配地区
			// 如果类别不为空
			for (MBlogInfo mBlogInfo : uinfoList) {
				// 查看我是否已经关注
				MBlogFollow notFollowInfo = mBlogFollowService
						.queryByIdAndTargetId(info.getId(), mBlogInfo.getId(),
								"1");
				// 如果灭有关注9
				if (notFollowInfo == null
						&& !mBlogInfo.getId().equals(info.getId())) {
					// 根据公司id去查询对应的相关类别
					CompProfile ucompProfile = compProfileService.queryCompProfileById(mBlogInfo.getCid());
					// 根据类别去匹配
					if (StringUtils.isNotEmpty(compProfile.getIndustryCode())) {
						if (StringUtils.isNotEmpty(ucompProfile.getIndustryCode())&& ucompProfile.getIndustryCode().equals(compProfile.getIndustryCode())) {
							if (infoList.size() == 6) {
								break;
							} else {
								infoList.add(mBlogInfo);
							}
						} else {
							if (infoList.size() == 6) {
								break;
							} else {
								infoList.add(mBlogInfo);
							}
						}
					} else {
						if (infoList.size() == 6) {
							break;
						} else {
							infoList.add(mBlogInfo);
						}
					}
				}
			}
			out.put("infoList", infoList);
		}

		if (info != null) {
			List<MBlogGroup> groupList = mBlogGroupService.queryById(info
					.getId());
			out.put("groupList", groupList);
			// 查询是否有特别关注分组
			MBlogGroup infogroup = mBlogGroupService.queryOneByConditions(info
					.getId(), "特别关注", null);
			if (infogroup != null) {
				out.put("infogroupId", infogroup.getId());
				// 判断页面再次刷新
				out.put("groupType", groupType);
			}
			// 如故他点击特别关注但是没有分组
			if (infogroup == null && StringUtils.isNotEmpty(groupType)
					&& groupType.equals("1")) {
				MBlogGroup group = new MBlogGroup();
				group.setInfoId(info.getId());
				group.setGroupName("特别关注");
				Integer i = mBlogGroupService.insert(group);
				if (i != null && i.intValue() > 0) {
					out.put("i", i);
					out.put("groupType", groupType);
				}
			}

		}

		// 查询热门话题
		List<MBlog> talkBlogList = mBlogService.queryTopicTitle(10);
		out.put("talkBlogList", talkBlogList);
		// 查询我的博友动态
		if (type != null) {
			 page = mBlogService.queryAllBlog(page);
			out.put("page", page);
			out.put("type", type);
		} else {
			out.put("groupId", groupId);
			if (groupId != null && groupId.equals(0)) {
				out.put("defaultName", "默认分组");
			}
			// 查询用户的组
			if (groupId != null) {
				MBlogGroup group = mBlogGroupService.queryOneByConditions(info
						.getId(), null, groupId);
				out.put("group", group);
			}

			MBlogGroup infogroup = mBlogGroupService.queryOneByConditions(info
					.getId(), "特别关注", null);
			// 如果在首次创建用户的分组又在刷新
			if (infogroup != null && StringUtils.isNotEmpty(groupType)
					&& groupType.equals("1")) {
				 page = mBlogService.queryMyFollowBlog(info
						.getId(),infogroup.getId(),page);
				out.put("page", page);
			} else {
				 page = mBlogService.queryMyFollowBlog(info
						.getId(),groupId,page);
				out.put("page", page);
			}
		}

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
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(info.getId(), 5);
		out.put("anteList", anteList);
		
		SeoUtil.getInstance().buildSeo("mblog", out);

		return null;
	}

	// 时间间隔5秒读取时间
	@RequestMapping
	public ModelAndView ajaxQueryMblogByTime(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			String groupId) throws ParseException {
		ExtResult result = new ExtResult();
		do {
			Integer i = mBlogService.queryCountBlogByTime(groupId);
			if (i > 0) {
				result.setSuccess(true);
				result.setData(i);
			}
		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView login(Map<String, Object> out,
			HttpServletRequest request, String error, String refurl) {

		if (StringUtils.isNotEmpty(refurl)) {
			refurl = Jsoup.clean(refurl, Whitelist.none());
			out.put("refurl", refurl);
		}
		out.put("error", error);
		if (StringUtils.isNotEmpty(error)) {
			out.put("errorText", AuthorizeException.getMessage(error));
		}
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie=cookies[i];
				if(cookie.getName().equals("userCookie")){
					String userName=cookie.getValue().split("-")[0];
					String passWord=cookie.getValue().split("-")[1];
					out.put("userName", userName);
					out.put("passWord", passWord);
				}
			}
		}
		SeoUtil.getInstance().buildSeo("login", out);
		return null;
	}

	@RequestMapping
	public ModelAndView doLogin(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response,
			String username, String password, String vcode, String refurl,String checkPassWord) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			out.put("error", "用户名和密码不能为空");
			return new ModelAndView("login");
		}
		
		
		EpAuthUser user = null;
		try {
			user = EpAuthUtils.getInstance().validateUser(response, username,
					password, null);
			
			compAccountService.updateLoginInfo(user.getUid(), null, user
					.getCid());
			EpAuthUtils.getInstance().setEpAuthUser(request, user, null);
			//记住密码
			if(checkPassWord!=null){
			 Cookie userCookie = new Cookie("userCookie",username+"-"+password);
			 userCookie.setMaxAge(172800);
			 response.addCookie(userCookie);
			}
			if (StringUtils.isNotEmpty(refurl)) {
				return new ModelAndView("redirect:" + refurl);
			}
			MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
			if(info!=null && info.getIsDelete().equals("1")){
				out.put("error", "300");
				return new ModelAndView("redirect:login.htm");
			}
		} catch (NoSuchAlgorithmException e) {
			out.put("error", AuthorizeException.ERROR_SERVER);
			out.put("refurl", refurl);
			return new ModelAndView("redirect:login.htm");
		} catch (IOException e) {
			out.put("error", AuthorizeException.ERROR_SERVER);
			out.put("refurl", refurl);
			return new ModelAndView("redirect:login.htm");
		} catch (AuthorizeException e) {
			out.put("error", e.getMessage());
			out.put("refurl", refurl);
			return new ModelAndView("redirect:login.htm");
		} 
		return new ModelAndView("redirect:index.htm");
	}

	@RequestMapping
	public ModelAndView logout(Map<String, Object> out,
			HttpServletRequest request, HttpServletResponse response) {
		EpAuthUtils.getInstance().logout(request, response, null);
		return new ModelAndView("redirect:login.htm");
	}

	// 注册信息
	@RequestMapping
	public ModelAndView editorHead(Map<String, Object> out,
			HttpServletRequest request) {
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
				.getCid());
		// if (info == null) {
		// return new ModelAndView("redirect:register.htm");
		// } else if (info != null && StringUtils.isEmpty(info.getName())) {
		// return new ModelAndView("redirect:register.htm");
		// }
		if (info != null) {
			out.put("info", info);
		}
		return new ModelAndView();
	}

	// 查询信息
	@RequestMapping
	public ModelAndView info(Map<String, Object> out,
			HttpServletRequest request, String msg, String error) {
		// 得到对象id,根据对象id和用户id去查询有没有关注
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		MBlogInfoDto infoDto = mBlogInfoService.queryInfoById(info.getId());
		out.put("infoDto", infoDto);
		if (StringUtils.isNotEmpty(msg)) {
			out.put("msg", msg);
		}
		if (StringUtils.isNotEmpty(error)) {
			out.put("error", error);
		}
		// 查询公司名字
		CompProfile compProfile = compProfileService
				.queryCompProfileById(infoDto.getmBlogInfo().getCid());
		out.put("compProfile", compProfile);
		SeoUtil.getInstance().buildSeo("updateInfo", out);
		return null;
	}

	// 完善注册
	@RequestMapping
	public ModelAndView register(HttpServletRequest request,
			Map<String, Object> out, String error) {
		// 得到对象id,根据对象id和用户id去查询有没有关注
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		if (error != null) {
			out.put("error", error);
		}
		// 查询公司名字
		CompProfile compProfile = compProfileService.queryCompProfileById(user
				.getCid());
		out.put("compProfile", compProfile);
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
				.getCid());
		if (info != null) {
			out.put("info", info);
		}
		SeoUtil.getInstance().buildSeo("updateInfo", out);
		return null;
	}

	// 创建信息
	@RequestMapping
	public ModelAndView doCreateInfo(Map<String, Object> out,
			HttpServletRequest request, MBlogInfo mBlogInfo) {
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
				.getCid());
		// if (info == null) {
		// return new ModelAndView("redirect:register.htm");
		// } else if (info != null && StringUtils.isEmpty(info.getName())) {
		// return new ModelAndView("redirect:register.htm");
		// }
		if (info == null) {
			// mBlogInfoService.query
			MBlogInfo uinfo = mBlogInfoService.queryInfoByInfoByName(mBlogInfo
					.getName().replaceAll(" ", ""));
			if (uinfo != null) {
				out.put("error", "昵称重复，请设置你的独特昵称！");
				return new ModelAndView("register");
			}
			mBlogInfo.setAccount(user.getAccount());
			mBlogInfo.setCid(user.getCid());
			Integer i = mBlogInfoService.insert(mBlogInfo);
			if (i != null && i.intValue() > 0) {
				return new ModelAndView("redirect:index.htm");
			}
		} else if (StringUtils.isEmpty(info.getName())) {
			MBlogInfo uinfo = mBlogInfoService.queryInfoByInfoByName(mBlogInfo
					.getName().replaceAll(" ", ""));
			if (uinfo != null) {
				out.put("error", "昵称重复，请设置你的独特昵称！");
				return new ModelAndView("register");
			}
			mBlogInfo.setId(info.getId());
			mBlogInfo.setAccount(user.getAccount());
			mBlogInfo.setCid(user.getCid());
			if (StringUtils.isNotEmpty(info.getHeadPic())) {
				mBlogInfo.setProvinceCode(info.getHeadPic());
			}
			Integer id = mBlogInfoService.update(mBlogInfo);
			if (id != null && id.intValue() > 0) {
				return new ModelAndView("redirect:index.htm");
			}
		} else {
		//	mBlogInfo.setAccount(user.getAccount());
	//		mBlogInfo.setCid(user.getCid());
			
			mBlogInfoService.update(info);
		}
		return new ModelAndView("redirect:index.htm");
	}

	@RequestMapping
	public ModelAndView doUpdateHeadPic(Map<String, Object> out,
			HttpServletRequest request, String headPic) {
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
				.getCid());
		if (info == null) {
			if (StringUtils.isNotEmpty(headPic)) {
				info.setCid(user.getCid());
				info.setAccount(user.getAccount());
				info.setName("");
				Integer i = mBlogInfoService.insert(info);
				if (i != null && i.intValue() > 0) {
					return new ModelAndView("redirect:register.htm");
				}
			}
		} else {
			if (StringUtils.isNotEmpty(headPic)) {
				info.setHeadPic(headPic);
				mBlogInfoService.update(info);
				return new ModelAndView("redirect:info.htm");
			}
		}
			return new ModelAndView("redirect:info.htm");
	}
	
	@RequestMapping
	public ModelAndView ajaxSaveInfo(Map<String, Object> out,
			HttpServletRequest request, String infoName,String sex,String realName,String provinceCode,String areaCode) {
		ExtResult result =new ExtResult();
 		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user.getCid());
		if(info==null){
			if(StringUtils.isNotEmpty(infoName)){
				//添加到数据库
				MBlogInfo mBlogInfo=new MBlogInfo();
				mBlogInfo.setAccount(user.getAccount());
				mBlogInfo.setName(infoName);
				mBlogInfo.setCid(user.getCid());
				mBlogInfo.setProvinceCode(provinceCode);
				mBlogInfo.setAreaCode(areaCode);
				mBlogInfo.setRealName(realName);
				mBlogInfo.setSex(sex);
				Integer i= mBlogInfoService.insert(mBlogInfo);
				if(i!=null && i.intValue()>0){
					//跳转到头像保存的页面
					result.setSuccess(true);
				}
			}
		}
		result.setSuccess(true);
		return printJson(result, out);
	
	}

	// 更改信息
	@RequestMapping
	public ModelAndView doUpdateInfo(Map<String, Object> out,
			HttpServletRequest request, MBlogInfo mBlogInfo) {
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
				.getCid());
		MBlogInfo uinfo = mBlogInfoService.queryInfoByInfoByName(mBlogInfo
				.getName().replaceAll(" ", ""));
		if (uinfo != null && !uinfo.getId().equals(info.getId())) {
			out.put("error", "000");
			return new ModelAndView("redirect:info.htm");
		}

		Integer i = mBlogInfoService.update(mBlogInfo);
		if (i != null && i.intValue() > 0) {
			out.put("msg", "1");
		}
		return new ModelAndView("redirect:info.htm");
	}

	// 根据用户分组查询
//	@RequestMapping
//	public ModelAndView myGroups(Map<String, Object> out,
//			HttpServletRequest request, Integer groupId,
//			PageDto<MBlogFollow> page) {
//		page.setLimit(12);
//		EpAuthUser user = getCachedUser(request);
//		if (user == null) {
//			return new ModelAndView("redirect:login.htm");
//		}
//		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
//				.getCid());
//		page = mBlogFollowService.queryFollowByConditions(info.getId(),
//				groupId, page);
//		out.put("page", page);
//		return null;
//	}

	// 查询我的粉丝
	@RequestMapping
	public ModelAndView myfans(Map<String, Object> out,
			HttpServletRequest request, PageDto<MBlogInfoDto> page) {
		page.setLimit(5);
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		List<MBlogFollow> followList = mBlogFollowService.queryFansByTargetId(
				info.getId(), null, page.getStart(), page.getLimit());

		List<MBlogInfoDto> dtolist = new ArrayList<MBlogInfoDto>();
		for (MBlogFollow mBlogFollow : followList) {
			MBlogInfoDto dto = mBlogInfoService.queryInfoById(mBlogFollow.getInfoId());
			if(dto.getmBlogInfo()==null){
				continue;
			}
			// 查询公司信息
			CompProfile compProfile = compProfileService
					.queryCompProfileById(dto.getmBlogInfo().getCid());
			dto.setCompProfile(compProfile);
			if (mBlogFollow.getType().equals("1")) {
				dto.setType("1");
			} else {
				dto.setType("0");
			}
			if (dto != null) {
				// 添加粉丝数
				dto.setFansCount(mBlogFollowService.queryFansCountByConditions(
						mBlogFollow.getInfoId(), null));
				// 查询关注数
				dto.setFollowCount(mBlogFollowService
						.queryFollowCountByConditions(mBlogFollow.getInfoId(),
								null,null));
				// 查询微博数
				dto.setMblogCount(mBlogService
						.queryAllCountMBlogById(mBlogFollow.getInfoId()));
				dtolist.add(dto);
			}
		}
		page.setRecords(dtolist);
		page.setTotals(mBlogFollowService.queryFansCountByConditions(info
				.getId(), null));
		out.put("page", page);
		out.put("fansCount", mBlogFollowService.queryFansCountByConditions(info
				.getId(), null));
		
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
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(info.getId(), 5);
		out.put("anteList", anteList);
		
		
		
		// 查询分组
		List<MBlogGroup> groupList = mBlogGroupService.queryById(info.getId());
		out.put("groupList", groupList);
		//查询是否有特别关注
		MBlogGroup group=mBlogGroupService.queryOneByConditions(info.getId(), "特别关注", null);
		out.put("group", group);
		
		SeoUtil.getInstance().buildSeo("myfans", out);
		return null;
	}

	// 添加分组
	@RequestMapping
	public ModelAndView creatGroup(Map<String, Object> out,
			HttpServletRequest request, MBlogGroup group) {
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		Integer i = mBlogGroupService.insert(group);
		if (i != null && i.intValue() > 0) {
			out.put("msg", 1);
		}
		return null;
	}

	@RequestMapping
	public ModelAndView ajaxFollowStatus(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, Integer id) {
		ExtResult result = new ExtResult();
		do {
			EpAuthUser user = getCachedUser(request);
			MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
					.getCid());
			String type = "1";
			MBlogFollow follow = new MBlogFollow();
			// 去查询目标有没有关注自己
			MBlogFollow uFollow = mBlogFollowService.queryByIdAndTargetId(id,
					info.getId(), null);
			if (uFollow != null && uFollow.getType().equals("1")) {
				result.setData("1");// 表示互相关注，用于js判断
				return printJson(result, out);
			}
			if (uFollow != null) {
				
				// 查看之前我有没有关注他
				MBlogFollow myFollow = mBlogFollowService.queryByIdAndTargetId(
						info.getId(), id, null);
				if(myFollow==null){
					// 如果有关注那么更新状态为互相关注
					mBlogFollowService.updateTypeById(uFollow.getId(), type);
					follow.setInfoId(info.getId());
					follow.setTargetId(id);
					follow.setFollowStatus("1");
					follow.setType("1");
					Integer i = mBlogFollowService.insert(follow);
					if (i != null && i.intValue() > 0) {
						mBlogFollowService.updateTypeById(i, type);
						result.setSuccess(true);
						result.setData("1");// 表示互相关注，用于js判断
					}
				}else if(myFollow != null && myFollow.getFollowStatus().equals("0")){
					// 更新他关注我的状态状态
					mBlogFollowService.updateTypeById(uFollow.getId(), type);
					//更新我关注他的状态状态
					Integer i = mBlogFollowService.updateFollowStatus(type, "1", info.getId(), id, myFollow.getGroupId());
					if (i != null && i.intValue() > 0) {
						result.setSuccess(true);
						result.setData("1");// 表示我关注了他，用于js判断
					}
				}
				

			} else {
				// 查看之前我有没有关注他
				MBlogFollow myFollow = mBlogFollowService.queryByIdAndTargetId(
						info.getId(), id, null);
				if (myFollow != null && myFollow.getFollowStatus().equals("0")) {
					Integer i = mBlogFollowService.updateFollowStatus(myFollow
							.getType(), "1", info.getId(), id, myFollow
							.getGroupId());
					if (i != null && i.intValue() > 0) {
						result.setSuccess(true);
						result.setData("2");// 表示我关注了他，用于js判断
					}
				} else {
					follow.setInfoId(info.getId());
					follow.setTargetId(id);
					follow.setFollowStatus("1");
					Integer i = mBlogFollowService.insert(follow);
					if (i != null && i.intValue() > 0) {
						result.setSuccess(true);
						result.setData("2");// 表示我关注了他，用于js判断
					}
				}
			}

		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView ajaxUpdateFollowStatus(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out, Integer id,
			Integer type) {
		ExtResult result = new ExtResult();
		do {
			EpAuthUser user = getCachedUser(request);
			MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
					.getCid());
			if (type == 1) {
				// 查询是否是互相关注
				MBlogFollow follow = mBlogFollowService.queryByIdAndTargetId(
						info.getId(), id, null);
				Integer i = mBlogFollowService.updateFollowStatus("0", "0", id,
						info.getId(), 0);
				if (i != null && i.intValue() > 0) {
					result.setSuccess(true);
					mBlogFollowService.updateTypeById(follow.getId(), "0");
				}
			} else {
				Integer i = mBlogFollowService.updateFollowStatus("0", "0", id,
						info.getId(), 0);
				if (i != null && i.intValue() > 0) {
					result.setSuccess(true);
				}
			}
		} while (false);
		return printJson(result, out);
	}

	// 查询刚刚请求的粉丝
	@RequestMapping
	public ModelAndView newfans(Map<String, Object> out,
			HttpServletRequest request, PageDto<MBlogInfoDto> page) {
		page.setLimit(5);
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		List<MBlogInfoDto> dtolist = new ArrayList<MBlogInfoDto>();
		// 查看没有互相关注的粉丝
		List<MBlogFollow> followList = mBlogFollowService.queryFansByTargetId(
				info.getId(), "0", page.getStart(), page.getLimit());
		for (MBlogFollow mBlogFollow : followList) {
			MBlogInfoDto dto = mBlogInfoService.queryInfoById(mBlogFollow
					.getInfoId());
			// 查询公司信息
			CompProfile compProfile = compProfileService.queryCompProfileById(dto.getmBlogInfo().getCid());
			dto.setCompProfile(compProfile);
			if (mBlogFollow.getType().equals("1")) {
				dto.setType("1");
			} else {
				dto.setType("0");
			}
			if (dto != null) {
				// 添加粉丝数
				dto.setFansCount(mBlogFollowService.queryFansCountByConditions(
						mBlogFollow.getInfoId(), null));
				// 查询关注数
				dto.setFollowCount(mBlogFollowService
						.queryFollowCountByConditions(mBlogFollow.getInfoId(),
								null,null));
				// 查询微博数
				dto.setMblogCount(mBlogService
						.queryAllCountMBlogById(mBlogFollow.getInfoId()));
				dtolist.add(dto);
			}
		}

		page.setRecords(dtolist);
		page.setTotals(mBlogFollowService.queryFansCountByConditions(info
				.getId(), "0"));
		out.put("page", page);
		out.put("newFansCount", mBlogFollowService.queryFansCountByConditions(
				info.getId(), "0"));
		
		
		
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
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(info.getId(), 5);
        out.put("anteList", anteList); 
		
		SeoUtil.getInstance().buildSeo("newfans", out);
		return null;
	}

	// 查询我的微博
	@RequestMapping
	public ModelAndView mblog(Map<String, Object> out,
			HttpServletRequest request, PageDto<MBlogDto> page, Integer infoId,String type) {
		page.setLimit(5);
		// 查询我的微博
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		
		if(StringUtils.isNotEmpty(type)){
			out.put("type", type);
		}
		page = mBlogService.queryAllmBlogById(info.getId(),type,page);
		out.put("page", page);
		// 查询信息
		MBlogInfoDto dto = mBlogInfoService.queryInfoById(info.getId());
		// 查询粉丝数
		out.put("fansCount", mBlogFollowService.queryFansCountByConditions(info
				.getId(), null));
		// 查询关注数
		out.put("followCout", mBlogFollowService.queryFollowCountByConditions(
				info.getId(), null,null));
		// 查询微博数
		out.put("mblogCount", mBlogService.queryAllCountMBlogById(info
						.getId()));
		out.put("info", info);
		// 查询公司信息
		CompProfile compProfile = compProfileService.queryCompProfileById(info
				.getCid());
		out.put("compProfile", compProfile);
		out.put("dto", dto);
		// 查询粉丝
		List<MBlogInfo> infoFansList = new ArrayList<MBlogInfo>();
		List<MBlogFollow> fansList = mBlogFollowService.queryFansByTargetId(
				info.getId(), null, 0, 4);
		for (MBlogFollow mBlogFollow : fansList) {
			MBlogInfo infoFans = mBlogInfoService.queryInfoByInfoIdorCid(
					mBlogFollow.getInfoId(), null);
			infoFansList.add(infoFans);
		}
		out.put("infoFansList", infoFansList);
		// 查询关注
		List<MBlogInfo> infoFollowList = new ArrayList<MBlogInfo>();
		List<MBlogFollow> followList = mBlogFollowService
				.queryByInfoIdOrGroupId(info.getId(), null);
		for (MBlogFollow mBlogFollow : followList) {
			MBlogInfo infoFollow = mBlogInfoService.queryInfoByInfoIdorCid(
					mBlogFollow.getTargetId(), null);
			infoFollowList.add(infoFollow);

		}
		out.put("infoFollowList", infoFollowList);
		// 查询热门话题
		List<MBlog> talkBlogList = mBlogService.queryTopicTitle(10);
		out.put("talkBlogList", talkBlogList);
		
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
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(info.getId(), 5);
		out.put("anteList", anteList);

		SeoUtil.getInstance().buildSeo("mblog", out);
		return null;
	}
    @RequestMapping
    public ModelAndView ajaxRemoveMblog(Map<String, Object> out,
			HttpServletRequest request,HttpServletResponse response,Integer mblogId){
    	ExtResult result=new ExtResult();
    	do {
			if(mblogId!=null){
				//更新博文状态
				Integer i=mBlogService.updateMblogIsDeleteStatus(mblogId, "1");
				if(i!=null && i.intValue()>0){
					result.setSuccess(true);
				}
			}
		} while (false);
    	return printJson(result, out);
    } 
	// 首页的评论 sentType转发标记
	@RequestMapping
	public ModelAndView reply(Map<String, Object> out,
			HttpServletRequest request, String textContent, Integer mblogId,
			String type, Integer sentType, Integer tragetInfoId,
			Integer tragetId) throws UnsupportedEncodingException {
		
	     getReply(out, request, textContent, mblogId, type, sentType, tragetInfoId, tragetId);
		
		return null;
	}

	// 读取评论人的信息
	@RequestMapping
	public ModelAndView reply1(Map<String, Object> out,
			HttpServletRequest request, Integer mblogId) {
		
		getQueryreply(out, request, mblogId);
		
		return null;
	}

	// 读取评论的人的信息以及和同时转发的功能
	@RequestMapping
	public ModelAndView reply2(Map<String, Object> out,
			HttpServletRequest request, String content, Integer tragetInfoId,
			Integer tragetId, Integer mblogId, String type, Integer sentType)
			throws UnsupportedEncodingException {
		

		String contents = content;

		// 存入数据库去空格
		String newcontent = "";
		contents = contents.trim().replaceAll("：", ":");
		for (int i = 0; i < contents.length(); i++) {
			if (i != 0 && contents.charAt(i - 1) == ' '
					&& contents.charAt(i) == ' ') {
				continue;
			} else {
				newcontent += contents.charAt(i);
			}
		}
		newcontent = newcontent.replace("\n", "");

		// String contents=URLDecoder.decode(content, "utf-8");
		EpAuthUser user = getCachedUser(request);
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
				.getCid());
		if (tragetInfoId != null) {
			MBlogInfo tragetInfo = mBlogInfoService.queryInfoByInfoIdorCid(
					tragetInfoId, null);
			out.put("tragetInfo", tragetInfo);
		}
		out.put("info", info);

		String commentContent = Face.getFace(newcontent);

		if (commentContent.indexOf("@") != -1) {
			Map<String, Object> maps = mBlogService
					.replceMblogAnte(commentContent);
			for (String mapKey : maps.keySet()) {
				if(StringUtils.isEmpty(mapKey)){
					continue;
				}
				// 查询出对应的人的id
				MBlogInfo mBlogInfo = mBlogInfoService
						.queryInfoByInfoByName(mapKey);
				if (mBlogInfo != null) {
					commentContent = commentContent.replace("@" + mapKey,
							"<a href=\"ublog" + mBlogInfo.getId()+".htm\" style=\"color:#006633;\">@"+ mapKey + "</a>");
					// 添加到系统表
				} else {
					commentContent = commentContent.replace("@" + mapKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+ mapKey + "</a>");
				}
			}
		}

		out.put("content", commentContent);
		out.put("mblogId", mblogId);

		// 添加到评论表
		if (content != null) {
			MBlogComment comment = new MBlogComment();
			comment.setInfoId(info.getId());
			comment.setMblogId(mblogId);
			comment.setTargetType(type);
			if (tragetId != null) {
				comment.setTargetId(tragetId);
			} else {
				comment.setTargetId(mblogId);
			}
			comment.setContent(newcontent);
			Integer i = mBlogCommentService.sentComment(comment);
			if (i != null && i.intValue() > 0) {
				mBlogService.updateDiscussCount(mblogId);
				out.put("tragetId", i);
				// 添加到系统表
				// 查询出id
				MBlog mBlog = mBlogService.queryOneById(mblogId);
				if (!mBlog.getInfoId().equals(info.getId())) {
					MBlogSystem system = new MBlogSystem();
					system.setFromId(info.getId());
					system.setToId(mBlog.getInfoId());
					system.setType("3");
					// 先用于存储信息
					system.setContent(String.valueOf(i));
					mBlogSystemService.insert(system);
				} else if (tragetId != null && type.equals("2")) {
					// 查询出我回复的是否是我自己
					MBlogComment comment2 = mBlogCommentService
							.queryOneCommentById(tragetId);
					if (!comment2.getInfoId().equals(info.getId())) {
						MBlogSystem system = new MBlogSystem();
						system.setFromId(info.getId());
						system.setToId(comment2.getInfoId());
						system.setType("3");
						// 先用于存储信息
						system.setContent(String.valueOf(i));
						mBlogSystemService.insert(system);
					}
				}

			}
			// 说明还要转发
			if (sentType != null && sentType.equals(1)) {
				MBlog mBlog = new MBlog();
				mBlog.setInfoId(info.getId());
				mBlog.setContent(newcontent);
				mBlog.setType("1");
				Integer sid = mBlogService.insert(mBlog);
				if (sid != null && sid.intValue() > 0) {
					//查询出是否是自己发布的
					MBlog toMBlog = mBlogService.queryOneById(mblogId);
					if(!info.getId().equals(toMBlog.getInfoId())){
						//转发的添加到系统表
						MBlogSystem tragetSystem=new MBlogSystem();
						tragetSystem.setFromId(info.getId());
						tragetSystem.setToId(toMBlog.getInfoId());
						tragetSystem.setContent(String.valueOf(sid));
						tragetSystem.setType("2");
						mBlogSystemService.insert(tragetSystem);
					}
					// 更新转发数
					mBlogService.updateSentCount(mblogId);
					// 插入转发关系表
					MBlogSent mBlogSent = new MBlogSent();
					mBlogSent.setMblogId(sid);
					if (tragetId != null) {
						mBlogSent.setTargetId(tragetId);
					} else {
						mBlogSent.setTargetId(mblogId);
					}
					mBlogSent.setTopId(mblogId);
					mBlogSentService.insert(mBlogSent);
				}
			}
			if (sentType != null && sentType.equals(2)) {// 用于转发博文下评论的信息的标记
				// 获取评论的信息
				MBlogComment comments = mBlogCommentService
						.queryOneCommentById(tragetId);
				MBlogInfo tragetInfo = mBlogInfoService.queryInfoByInfoIdorCid(
						tragetInfoId, null);
				// 添加到动态表
				MBlog mBlog = new MBlog();
				mBlog.setInfoId(info.getId());
				mBlog.setContent("回复@" + tragetInfo.getName() + ":"
						+ newcontent + "//@" + tragetInfo.getName() + ":"
						+ comments.getContent());
				mBlog.setType("1");
				Integer sid = mBlogService.insert(mBlog);
				if (sid != null && sid.intValue() > 0) {
					//转发添加到系统表
					//查询出是否是回复自己的
					if(!comments.getInfoId().equals(info.getId())){
						MBlogSystem system=new MBlogSystem();
						system.setFromId(info.getId());
						system.setToId(comments.getInfoId());
						system.setType("2");
						system.setContent(String.valueOf(sid));
						mBlogSystemService.insert(system);
					}
					
					// 更新转发数
					mBlogService.updateSentCount(mblogId);
					// 插入转发关系表
					MBlogSent mBlogSent = new MBlogSent();
					mBlogSent.setMblogId(sid);
					if (tragetId != null) {
						mBlogSent.setTargetId(tragetId);
					} else {
						mBlogSent.setTargetId(mblogId);
					}
					mBlogSent.setTopId(mblogId);
					mBlogSentService.insert(mBlogSent);
				}
			}
		}
		return null;
	}

	@RequestMapping
	public ModelAndView reply3(Map<String, Object> out,
			HttpServletRequest request, Integer mblogId) {
		
		getQueryreply(out, request, mblogId);
		
		return null;
	}

	@RequestMapping
	public ModelAndView replyCommments1(Map<String, Object> out,
			HttpServletRequest request, Integer mblogId) {
		
		getQueryreply(out, request, mblogId);
	
		return null;
	}
	
	@RequestMapping
	public ModelAndView replyCommments(Map<String, Object> out,
			HttpServletRequest request, String textContent, Integer mblogId,
			String type, Integer sentType, Integer tragetInfoId,
			Integer tragetId) throws UnsupportedEncodingException {
		
		getReply(out, request, textContent, mblogId, type, sentType, tragetInfoId, tragetId);
		
		return null;
	}
	
	// 一条微博的评论页面
	@RequestMapping
	public ModelAndView originalMblog(Map<String, Object> out,
			HttpServletRequest request, Integer mblogId,
			PageDto<MBlogCommentDto> page, Integer infoId) {
		page.setLimit(10);
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		
		// 根据博文id查询出博文
		// MBlog mBlog=mBlogService.queryOneById(mblogId);
		MBlogDto mBlogDto = mBlogService.queryOneBymblogId(mblogId);
		
		MBlogFollow follow=mBlogFollowService.queryByIdAndTargetId(info.getId(),mBlogDto.getmBlog().getInfoId(), null);
		out.put("follow", follow);
		
		if (StringUtils.isNotEmpty(mBlogDto.getmBlog().getPhotoPath())) {
			// 查看是否有图片
			if (StringUtils.isNotEmpty(mBlogDto.getmBlog().getPhotoPath())) {
				// 如果有截取出图片
				List list = mBlogService.queryMblogPhoto(mBlogDto.getmBlog()
						.getPhotoPath());
				out.put("list", list);
			}
		}

		out.put("mBlogDto", mBlogDto);
		// 查询出博文的信息
		MBlogInfoDto mblogInfoDto = mBlogInfoService.queryInfoById(mBlogDto
				.getmBlog().getInfoId());
		out.put("mblogInfoDto", mblogInfoDto);
		// 查询出公司信息
		CompProfile compProfile = compProfileService
				.queryCompProfileById(mblogInfoDto.getmBlogInfo().getCid());
		out.put("compProfile", compProfile);
		// 查询所有评论的信息
		// 查询博文评论
		page = mBlogCommentService.queryCommentBymblogId(mblogId, page);
		for (MBlogCommentDto mBlogCommentDto : page.getRecords()) {

			if (mBlogCommentDto.getComment().getContent().indexOf("@") != -1) {
				Map<String, Object> maps = mBlogService
						.replceMblogAnte(mBlogCommentDto.getComment()
								.getContent());
				for (String mapKey : maps.keySet()) {
					if(StringUtils.isEmpty(mapKey)){
						continue;
					}
					// 查询出对应的人的id
					MBlogInfo mBlogInfo = mBlogInfoService
							.queryInfoByInfoByName(mapKey);
					if (mBlogInfo != null) {
						mBlogCommentDto.getComment().setContent(mBlogCommentDto.getComment().getContent().replace("@" + mapKey,"<a href=\"ublog"
				        + mBlogInfo.getId()+ ".htm\" style=\"color:#006633;\">@"+ mapKey+ "</a>"));
						// 添加到系统表
					} else {
						mBlogCommentDto.getComment().setContent(mBlogCommentDto.getComment().getContent().replace("@" + mapKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"
						+ mapKey + "</a>"));
					}
				}
			}

			MBlogInfo mBlogInfo = mBlogInfoService.queryInfoByInfoIdorCid(
					mBlogCommentDto.getComment().getInfoId(), null);
			mBlogCommentDto.setInfo(mBlogInfo);
			// 查询目标id对应的人
			if (mBlogCommentDto.getComment().getTargetType().equals("2")) {
				mBlogCommentDto.getComment()
						.setContent(
								Face.getFace(mBlogCommentDto.getComment()
										.getContent()));
				MBlogComment comment = mBlogCommentService
						.queryOneCommentById(mBlogCommentDto.getComment()
								.getTargetId());
				comment.setContent(Face.getFace(comment.getContent()));
				// 查询目标评论的人
				MBlogInfo tragetInfo = mBlogInfoService.queryInfoByInfoIdorCid(
						comment.getInfoId(), null);
				mBlogCommentDto.setmBlogInfo(tragetInfo);
			} else {
				mBlogCommentDto.getComment()
						.setContent(
								Face.getFace(mBlogCommentDto.getComment()
										.getContent()));
			}
		}
		out.put("page", page);

		// 查询关注
		List<MBlogInfo> infoFollowList = new ArrayList<MBlogInfo>();
		List<MBlogFollow> followList = mBlogFollowService
				.queryByInfoIdOrGroupId(mblogInfoDto.getmBlogInfo().getId(),
						null);
		for (MBlogFollow mBlogFollow : followList) {
			MBlogInfo infoFollow = mBlogInfoService.queryInfoByInfoIdorCid(
					mBlogFollow.getTargetId(), null);
			infoFollowList.add(infoFollow);

		}
		out.put("infoFollowList", infoFollowList);
		// 查询粉丝
		List<MBlogInfo> infoFansList = new ArrayList<MBlogInfo>();
		List<MBlogFollow> fansList = mBlogFollowService.queryFansByTargetId(
				mblogInfoDto.getmBlogInfo().getId(), null, 0, 4);
		for (MBlogFollow mBlogFollow : fansList) {
			MBlogInfo infoFans = mBlogInfoService.queryInfoByInfoIdorCid(
					mBlogFollow.getInfoId(), null);
			infoFansList.add(infoFans);
		}
		out.put("infoFansList", infoFansList);
		// 查询热门话题
		List<MBlog> talkBlogList = mBlogService.queryTopicTitle(10);
		out.put("talkBlogList", talkBlogList);
		// 查询粉丝数
		out.put("fansCount", mBlogFollowService.queryFansCountByConditions(
				mblogInfoDto.getmBlogInfo().getId(), null));
		// 查询关注数
		out.put("followCout", mBlogFollowService.queryFollowCountByConditions(
				mblogInfoDto.getmBlogInfo().getId(), null,null));
		// 查询微博数
		out.put("mblogCount", mBlogService.queryAllCountMBlogById(mblogInfoDto
				.getmBlogInfo().getId()));

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
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(info.getId(), 5);
        out.put("anteList", anteList); 
		SeoUtil.getInstance().buildSeo("originalMblog", out);
		return null;
	}


	@RequestMapping
	public ModelAndView ublog(Map<String, Object> out,
			HttpServletRequest request, PageDto<MBlogDto> page, Integer infoId,String type) {
		page.setLimit(5);
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo uinfo =mBlogInfoService.queryInfoByCid(user.getCid());
		if(uinfo!=null && uinfo.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (uinfo == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (uinfo != null && StringUtils.isEmpty(uinfo.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		if (uinfo.getId().equals(infoId)) {
			return new ModelAndView("redirect:mblog.htm");
		}
		// 查询个人信息
		MBlogInfoDto dto = mBlogInfoService.queryInfoById(infoId);
		out.put("dto", dto);
		MBlogFollow follow=mBlogFollowService.queryByIdAndTargetId(uinfo.getId(),infoId, null);
		out.put("follow", follow);
		
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(infoId, null);
		out.put("info", info);
		
		if(StringUtils.isNotEmpty(type)){
			out.put("type", type);
		} 
		page = mBlogService.queryAllmBlogById(infoId,type, page);
		out.put("page", page);
		// 查询粉丝数
		out.put("fansCount", mBlogFollowService.queryFansCountByConditions(
				infoId, null));
		// 查询关注数
		out.put("followCout", mBlogFollowService.queryFollowCountByConditions(
				infoId, null,null));
		// 查询微博数
		out.put("mblogCount", mBlogService.queryAllCountMBlogById(infoId));

		// 查询公司信息
		CompProfile compProfile = compProfileService.queryCompProfileById(info
				.getCid());
		out.put("compProfile", compProfile);
		out.put("dto", dto);
		// 查询粉丝
		List<MBlogInfo> infoFansList = new ArrayList<MBlogInfo>();
		List<MBlogFollow> fansList = mBlogFollowService.queryFansByTargetId(
				infoId, null, 0, 4);
		for (MBlogFollow mBlogFollow : fansList) {
			MBlogInfo infoFans = mBlogInfoService.queryInfoByInfoIdorCid(
					mBlogFollow.getInfoId(), null);
			infoFansList.add(infoFans);
		}
		out.put("infoFansList", infoFansList);
		// 查询关注
		List<MBlogInfo> infoFollowList = new ArrayList<MBlogInfo>();
		List<MBlogFollow> followList = mBlogFollowService
				.queryByInfoIdOrGroupId(infoId, null);
		for (MBlogFollow mBlogFollow : followList) {
			MBlogInfo infoFollow = mBlogInfoService.queryInfoByInfoIdorCid(
					mBlogFollow.getTargetId(), null);
			infoFollowList.add(infoFollow);

		}
		out.put("infoFollowList", infoFollowList);
		// 查询热门话题

		List<MBlog> talkBlogList = mBlogService.queryTopicTitle(10);
		out.put("talkBlogList", talkBlogList);
		
		// 查询出统计没有读的回复和评论
		Integer commentCount = mBlogSystemService.queryCountById(uinfo.getId(),
				"3", "0");
		out.put("commentCount", commentCount);
		//查询出新粉丝的count
		out.put("newFansCount", mBlogFollowService.queryFansCountByConditions(
				uinfo.getId(), "0"));
		//读取出有人@我的人
		Integer anteCount = mBlogSystemService.queryCountById(uinfo.getId(), "2", "0");
		out.put("anteCount", anteCount);
		
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(uinfo.getId(), 5);
		out.put("anteList", anteList);
		SeoUtil.getInstance().buildSeo("mblog", out);

		return null;
	}

	// 查询我的粉丝
	@RequestMapping
	public ModelAndView ufans(Map<String, Object> out,
			HttpServletRequest request, PageDto<MBlogInfoDto> page,
			Integer infoId) {
		page.setLimit(5);
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo uinfo =mBlogInfoService.queryInfoByCid(user.getCid());
		if(uinfo!=null && uinfo.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (uinfo == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (uinfo != null && StringUtils.isEmpty(uinfo.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		if (uinfo.getId().equals(infoId)) {
			return new ModelAndView("redirect:myfans.htm");
		}
		out.put("uinfo", uinfo);
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(infoId, null);
		out.put("info", info);
		List<MBlogFollow> followList = mBlogFollowService.queryFansByTargetId(
				infoId, null, page.getStart(), page.getLimit());

		List<MBlogInfoDto> dtolist = new ArrayList<MBlogInfoDto>();
		for (MBlogFollow mBlogFollow : followList) {
			MBlogInfoDto dto = mBlogInfoService.queryInfoById(mBlogFollow
					.getInfoId());
			// 查询公司信息
			CompProfile compProfile = compProfileService
					.queryCompProfileById(dto.getmBlogInfo().getCid());
			dto.setCompProfile(compProfile);
			// 查看我有没有关注
			MBlogFollow mFollow = mBlogFollowService.queryByIdAndTargetId(uinfo
					.getId(), mBlogFollow.getInfoId(), null);
			if (mFollow != null && mFollow.getType().equals("1")) {
				dto.setType("1");// 表示互相关注
			} else if (mFollow != null && mFollow.getFollowStatus().equals("1")) {
				dto.setType("2");// 表示一关注
			} else {
				dto.setType("0");
			}
			if (dto != null) {
				// 添加粉丝数
				dto.setFansCount(mBlogFollowService.queryFansCountByConditions(
						mBlogFollow.getInfoId(), null));
				// 查询关注数
				dto.setFollowCount(mBlogFollowService
						.queryFollowCountByConditions(mBlogFollow.getInfoId(),
								null,null));
				// 查询微博数
				dto.setMblogCount(mBlogService
						.queryAllCountMBlogById(mBlogFollow.getInfoId()));
				dtolist.add(dto);
			}
		}
		page.setRecords(dtolist);
		page.setTotals(mBlogFollowService.queryFansCountByConditions(infoId,
				null));
		out.put("page", page);
		out.put("fansCount", mBlogFollowService.queryFansCountByConditions(
				infoId, null));
		
		
		// 查询出统计没有读的回复和评论
		Integer commentCount = mBlogSystemService.queryCountById(uinfo.getId(),
				"3", "0");
		out.put("commentCount", commentCount);
		//查询出新粉丝的count
		out.put("newFansCount", mBlogFollowService.queryFansCountByConditions(
				uinfo.getId(), "0"));
		//读取出有人@我的人
		Integer anteCount = mBlogSystemService.queryCountById(uinfo.getId(), "2", "0");
		out.put("anteCount", anteCount);
		
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(uinfo.getId(), 5);
		out.put("anteList", anteList);
		SeoUtil.getInstance().buildSeo("myfans", out);
		return null;
	}

	@RequestMapping
	public ModelAndView dynamic(Map<String, Object> out,
			HttpServletRequest request, String contents, String photos)
			throws UnsupportedEncodingException {
		// String contents=StringUtils.decryptUrlParameter(content);

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
			if (i != null && i.intValue() > 0) {
				MBlog newMBlog = mBlogService.queryOneById(i);
				
				if (newMBlog.getContent().indexOf("@") != -1) {
					Map<String, Object> maps = mBlogService.replceMblogAnte(newMBlog.getContent());
					for (String mapKey : maps.keySet()) {
						if(StringUtils.isEmpty(mapKey)){
							continue;
						}
						// 查询出对应的人的id
						MBlogInfo mBlogInfo = mBlogInfoService.queryInfoByInfoByName(mapKey);
						if (mBlogInfo != null) {
							newMBlog.setContent(newMBlog.getContent().replace("@" + mapKey,"<a href=\"ublog"+ mBlogInfo.getId()+ ".htm\" style=\"color:#006633;\">@"+ mapKey + "</a>"));
						
							if(!mBlogInfo.getId().equals(info.getId())){
								// 添加到系统表
								MBlogSystem system = new MBlogSystem();
								system.setFromId(info.getId());
								system.setToId(mBlogInfo.getId());
								system.setContent(String.valueOf(i));
								system.setType("2");
								mBlogSystemService.insert(system);
							}
						} else {
							newMBlog.setContent(newMBlog.getContent().replace("@" + mapKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+ mapKey + "</a>"));
						}
					}
				}
				out.put("newMBlog", newMBlog);
				if (StringUtils.isNotEmpty(newMBlog.getPhotoPath())) {
					List<Object> objList = new ArrayList<Object>();
					String[] photoArray = newMBlog.getPhotoPath().split(",");
					for (int j = 0; j < photoArray.length; j++) {
						MBlog mBlog1 = new MBlog();
						if (StringUtils.isNotEmpty(photoArray[j])) {
							mBlog1.setPhotoPath(photoArray[j]);
							objList.add(mBlog1);
						}
					}
					out.put("objList", objList);
				}

			}

			out.put("info", info);
			// out.put("contents", con);
		}
		return null;
	}

	@RequestMapping
	public ModelAndView follow(Map<String, Object> out,
			HttpServletRequest request, Integer groupId,
			PageDto<MBlogInfoDto> page, String groupType,String type,String keywords) {
		page.setLimit(12);
		// 用户信息
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		out.put("info", info);
		out.put("groupId", groupId);
		if (groupId != null && groupId != 0) {
			MBlogGroup group = mBlogGroupService.queryOneByConditions(null,
					null, groupId);
			out.put("group", group);
		} else if (groupId != null && groupId == 0) {
			out.put("group", "默认分组");
		}
		if (StringUtils.isNotEmpty(groupType)) {
			out.put("groupType", groupType);
		}
		// 查询是否有特别关注分组
		MBlogGroup infogroup = mBlogGroupService.queryOneByConditions(info
				.getId(), "特别关注", null);
		if (infogroup != null) {
			out.put("infogroupId", infogroup.getId());
			if (groupId != null && groupId.equals(infogroup.getId())) {
				// 用于刷新再次传值的标记
				out.put("dgroup", "特别关注");
			}
		}
		
		if(StringUtils.isNotEmpty(keywords)){
			page=mBlogFollowService.pageQueryForkeywords(info.getId(), keywords, page);
			out.put("page", page);
		}else{
			//创建特别关注
			if (StringUtils.isNotEmpty(groupType) && groupType.equals("1")
					&& infogroup == null) {
				out.put("groupType", groupType);
				MBlogGroup group = new MBlogGroup();
				group.setInfoId(info.getId());
				group.setGroupName("特别关注");
				Integer i = mBlogGroupService.insert(group);
				if (i != null && i.intValue() > 0) {
					out.put("i", i);
				}
				// 去查询这个组的分组
				List<MBlogFollow> followList = mBlogFollowService
						.queryFollowByConditions(info.getId(), i,type, page);
				if (followList.size() == 0) {
					List<MBlogInfoDto> dtolist = new ArrayList<MBlogInfoDto>();
					page.setRecords(dtolist);
					page.setTotals(mBlogFollowService.queryFollowCountByConditions(
							info.getId(), i,null));
					out.put("page", page);
					out.put("followCount", mBlogFollowService
							.queryFollowCountByConditions(info.getId(), i,null));
				}
			} else {
				// 页面刷新状态 groupType是特别关注的分组
				if (groupType != null && infogroup != null) {
					groupId = infogroup.getId();
				}
				//互相关注
				if(type!=null && type.equals("1")){
					out.put("type",type);
				}
				// 查询我关注的人
				List<MBlogFollow> followList = mBlogFollowService
						.queryFollowByConditions(info.getId(), groupId,type, page);
				List<MBlogInfoDto> dtolist = new ArrayList<MBlogInfoDto>();
		
				for (MBlogFollow mBlogFollow : followList) {
					MBlogInfoDto dto = mBlogInfoService.queryInfoById(mBlogFollow
							.getTargetId());
					if(dto.getmBlogInfo()==null){
						continue;
					}
					// 查询公司信息
					CompProfile compProfile = compProfileService
							.queryCompProfileById(dto.getmBlogInfo().getCid());
					dto.setCompProfile(compProfile);
					if (mBlogFollow.getType().equals("1")) {
						dto.setType("1");
					} else {
						dto.setType("0");
					}
					if(StringUtils.isNotEmpty(mBlogFollow.getNoteName()) ){
						dto.setNoteName(mBlogFollow.getNoteName());
					}
					if (dto != null) {
						// 添加粉丝数
						dto.setFansCount(mBlogFollowService.queryFansCountByConditions(mBlogFollow.getTargetId(), null));
						// 查询关注数
						dto.setFollowCount(mBlogFollowService.queryFollowCountByConditions(mBlogFollow
										.getTargetId(), null,null));
						// 查询微博数
						dto.setMblogCount(mBlogService.queryAllCountMBlogById(mBlogFollow.getTargetId()));
						dtolist.add(dto);
					}
				}
				page.setRecords(dtolist);
				page.setTotals(mBlogFollowService.queryFollowCountByConditions(info
						.getId(), groupId,type));
				out.put("page", page);
				out.put("followCount", mBlogFollowService
						.queryFollowCountByConditions(info.getId(), groupId,type));
		
			}
		}	
		// 查询粉丝数
		out.put("fansCount", mBlogFollowService.queryFansCountByConditions(info
				.getId(), null));
		// 查询分组
		List<MBlogGroup> groupList = mBlogGroupService.queryById(info.getId());
		out.put("groupList", groupList);
		
		
		
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
		
		SeoUtil.getInstance().buildSeo("follow", out);
		return null;
	}

	@RequestMapping
	public ModelAndView group(Map<String, Object> out,
			HttpServletRequest request, Integer infoId) {
		EpAuthUser user = getCachedUser(request);
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
				.getCid());
		List<MBlogGroup> groupList = mBlogGroupService.queryById(info.getId());
		out.put("groupList", groupList);
		// 查询要分组的人
		MBlogInfo tragetinfo = mBlogInfoService.queryInfoByInfoIdorCid(infoId,
				null);
		out.put("tragetinfo", tragetinfo);

		return null;
	}

	@RequestMapping
	public ModelAndView ajaxDoGroup(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String groupName) {
		ExtResult result = new ExtResult();
		MBlogGroup group = new MBlogGroup();
		do {
			EpAuthUser user = getCachedUser(request);
			MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
					.getCid());
			// 根据名字去查询有没有创建这个组
			MBlogGroup group2 = mBlogGroupService.queryOneByConditions(info
					.getId(), groupName, null);
			// 如故有则是修改
			if (group2 == null) {
				group.setInfoId(info.getId());
				group.setGroupName(groupName);
				Integer i = mBlogGroupService.insert(group);
				if (i != null && i.intValue() > 0) {
					result.setSuccess(true);
					result.setData(i);
				}
			} else {
				result.setSuccess(false);
			}

		} while (false);
		return printJson(result, out);
	}

	// 为关注的人分组
	@RequestMapping
	public ModelAndView doCreateGroupForInfo(HttpServletRequest request,
			Map<String, Object> out, Integer tragetId, String groupId,String noteName) {
		
		EpAuthUser user = getCachedUser(request);
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
				.getCid());
		// 如故他选特别关注。就该他创建组
		if (StringUtils.isNotEmpty(groupId) && groupId.equals("特别关注")) {
			MBlogGroup mBlogGroup = mBlogGroupService.queryOneByConditions(info
					.getId(), groupId, null);
			if (mBlogGroup == null) {
				MBlogGroup group = new MBlogGroup();
				group.setInfoId(info.getId());
				group.setGroupName(groupId);
				Integer i = mBlogGroupService.insert(group);
				if (i != null && i.intValue() > 0) {
					mBlogFollowService.updateFollowGroup(info.getId(),
							tragetId, i);
				}
			} else {
				mBlogFollowService.updateFollowGroup(info.getId(), tragetId,
						mBlogGroup.getId());
			}
			// 如果他选默认关注
		} else if (StringUtils.isNotEmpty(groupId) && groupId.equals("0")) {
			mBlogFollowService.updateFollowGroup(info.getId(), tragetId, 0);
		} else if (groupId != null) {
			mBlogFollowService.updateFollowGroup(info.getId(), tragetId,
					Integer.valueOf(groupId));
		}
		if(StringUtils.isNotEmpty(noteName)){
			//更新名字
		    mBlogFollowService.updateNoteNameById(info.getId(), tragetId, noteName);
		}
		return new ModelAndView("mblog");
	}
	@RequestMapping
	public ModelAndView ajaxCreatGroupForInfo(HttpServletRequest request,
			Map<String, Object> out, Integer tragetId, String groupId,String noteName) {
		ExtResult result = new ExtResult();
		EpAuthUser user = getCachedUser(request);
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
				.getCid());
		// 如故他选特别关注。就该他创建组
		if (StringUtils.isNotEmpty(groupId) && groupId.equals("特别关注")) {
			MBlogGroup mBlogGroup = mBlogGroupService.queryOneByConditions(info
					.getId(), groupId, null);
			if (mBlogGroup == null) {
				MBlogGroup group = new MBlogGroup();
				group.setInfoId(info.getId());
				group.setGroupName(groupId);
				Integer i = mBlogGroupService.insert(group);
				if (i != null && i.intValue() > 0) {
					mBlogFollowService.updateFollowGroup(info.getId(),
							tragetId, i);
				}
			} else {
				mBlogFollowService.updateFollowGroup(info.getId(), tragetId,
						mBlogGroup.getId());
			}
			// 如果他选默认关注
		} else if (StringUtils.isNotEmpty(groupId) && groupId.equals("0")) {
			mBlogFollowService.updateFollowGroup(info.getId(), tragetId, 0);
		} else if (groupId != null) {
			mBlogFollowService.updateFollowGroup(info.getId(), tragetId,
					Integer.valueOf(groupId));
		}
		if(StringUtils.isNotEmpty(noteName)){
			//更新名字
		    mBlogFollowService.updateNoteNameById(info.getId(), tragetId, noteName);
		}
		
		return printJson(result, out);
	}
	// 转发功能
	@RequestMapping
	public ModelAndView ajaxSentMblog(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			Integer mblogId, Integer topId) {
		ExtResult result = new ExtResult();
		do {

			EpAuthUser user = getCachedUser(request);
			MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
					.getCid());
			MBlog mblog = mBlogService.queryOneById(mblogId);

			if (mblog != null) {
				// 查出对应的人
				MBlogInfo targetInfo = mBlogInfoService.queryInfoByInfoIdorCid(
						mblog.getInfoId(), null);
				MBlog umblog = new MBlog();
				umblog.setInfoId(info.getId());
				umblog.setContent("转发微博");
				if (topId != null) {
					umblog.setContent("//@" + targetInfo.getName() + ":"
							+ mblog.getContent());
				}
				umblog.setType("1");
				// 标记转发
				Integer i = mBlogService.insert(umblog);
				if (i != null && i.intValue() > 0) {
					//插入的系统表转发关系对应的是@类型
					if(!info.getId().equals(mblog.getInfoId())){
						MBlogSystem system=new MBlogSystem();
						system.setFromId(info.getId());
						system.setToId(mblog.getInfoId());
						system.setType("2");
						system.setContent(String.valueOf(i));
						mBlogSystemService.insert(system);
					}
					
					mBlogService.updateSentCount(mblog.getId());
					MBlogSent sent = new MBlogSent();
					sent.setMblogId(i);
					sent.setTargetId(mblogId);
					if (topId != null) {
						mBlogService.updateSentCount(topId);
						sent.setTopId(topId);
					} else {
						sent.setTopId(mblogId);
					}
					Integer sentid = mBlogSentService.insert(sent);
					if (sentid != null && sentid.intValue() > 0) {
						result.setSuccess(true);
					}
				}
			}

		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView ajaxQueryFollowByType(Map<String, Object> out,
			HttpServletRequest request,String type){
		return null;
	}
	
	@RequestMapping
	public ModelAndView focus(HttpServletRequest request,
			Map<String, Object> out) {

		return null;
	}

	@RequestMapping
	public ModelAndView getAreaCode(Map<String, Object> out,
			HttpServletRequest request, String parentCode, String provinceCode,
			String areaCode) {

		if (StringUtils.isNotEmpty(provinceCode)
				&& StringUtils.isNotEmpty(areaCode)) {
			String codeName = CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, provinceCode);
			String areaName = CodeCachedUtil.getValue(
					CodeCachedUtil.CACHE_TYPE_AREA, areaCode);
			out.put("codeName", codeName);
			out.put("areaName", areaName);
		}

		List<SysArea> sysAreas = sysAreaService.querySysAreasByCode(parentCode);
		return printJson(sysAreas, out);
	}

	@RequestMapping
	public ModelAndView ajaxRemoveFollow(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String arrayInfo) {
		ExtResult result = new ExtResult();
		do {
			EpAuthUser user = getCachedUser(request);
			MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
					.getCid());
			String[] followInfo = arrayInfo.split(",");
			for (int i = 0; i < followInfo.length; i++) {
				if (StringUtils.isNotEmpty(followInfo[i])) {
					// 查询有没有互相关注
					MBlogFollow follow = mBlogFollowService
							.queryByIdAndTargetId(Integer
									.valueOf(followInfo[i]), info.getId(), null);
					if (follow != null && follow.getType().equals("1")) {
						// 那么取消我对他的关注互相关注
						mBlogFollowService.updateFollowStatus("0", "0", info
								.getId(), Integer.valueOf(followInfo[i]), 0);
						// 取消互相关注的状态
						mBlogFollowService.updateTypeById(follow.getId(), "0");
					} else {
						// 取消我对目标的关注
						mBlogFollowService.updateFollowStatus("0", "0", info
								.getId(), Integer.valueOf(followInfo[i]), 0);

					}
				}
			}
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}

	// ajax方法，为用户创建分组
	@RequestMapping
	public ModelAndView ajaxCreateGroupForInfo(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String infoIds, String groupId) {
		ExtResult result = new ExtResult();
		do {
			EpAuthUser user = getCachedUser(request);
			MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
					.getCid());
			if (groupId.equals("特别关注")) {
				MBlogGroup mBlogGroup = mBlogGroupService.queryOneByConditions(
						info.getId(), groupId, null);
				if (mBlogGroup == null) {
					MBlogGroup group = new MBlogGroup();
					group.setInfoId(info.getId());
					group.setGroupName(groupId);
					Integer id = mBlogGroupService.insert(group);
					if (id != null && id.intValue() > 0) {
						String[] mblogInfos = infoIds.split(",");
						for (int i = 0; i < mblogInfos.length; i++) {
							if (StringUtils.isNotEmpty(mblogInfos[i])) {
								// 为用户创建分组
								mBlogFollowService.updateFollowGroup(info.getId(), Integer.valueOf(mblogInfos[i]), id);
							}
						}
					}
				} else {
					String[] mblogInfos = infoIds.split(",");
					for (int i = 0; i < mblogInfos.length; i++) {
						if (StringUtils.isNotEmpty(mblogInfos[i])) {
							// 为用户创建分组
							mBlogFollowService.updateFollowGroup(info.getId(),Integer.valueOf(mblogInfos[i]), mBlogGroup.getId());
						}
					}
				}
			} else {
				String[] mblogInfos = infoIds.split(",");
				for (int i = 0; i < mblogInfos.length; i++) {
					if (StringUtils.isNotEmpty(mblogInfos[i])) {
						// 为用户创建分组
						mBlogFollowService.updateFollowGroup(info.getId(),Integer.valueOf(mblogInfos[i]), Integer.valueOf(groupId));
					}
				}
			}
			result.setSuccess(true);

		} while (false);
		return printJson(result, out);
	}

	@RequestMapping
	public ModelAndView ufollow(Map<String, Object> out,
			HttpServletRequest request, PageDto<MBlogInfoDto> page,
			Integer infoId) {
		page.setLimit(5);
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo uinfo =mBlogInfoService.queryInfoByCid(user.getCid());
		if(uinfo!=null && uinfo.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (uinfo == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (uinfo != null && StringUtils.isEmpty(uinfo.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		if (uinfo.getId().equals(infoId)) {
			return new ModelAndView("redirect:mblog.htm");
		}
		out.put("uinfo", uinfo);
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(infoId, null);
		out.put("info", info);

		List<MBlogFollow> followList = mBlogFollowService
				.queryFollowByConditions(info.getId(), null,null, page);
		List<MBlogInfoDto> dtolist = new ArrayList<MBlogInfoDto>();

		// List<MBlogFollow>
		// followList=mBlogFollowService.queryFansByTargetId(infoId,null,page.getStart(),page.getLimit());

		for (MBlogFollow mBlogFollow : followList) {
			MBlogInfoDto dto = mBlogInfoService.queryInfoById(mBlogFollow
					.getTargetId());
			if(dto.getmBlogInfo()==null){
				continue;
			}
			// 查询公司信息
			CompProfile compProfile = compProfileService.queryCompProfileById(dto.getmBlogInfo().getCid());
			if(compProfile!=null){
				dto.setCompProfile(compProfile);
			}
			// 查看我有没有关注
			MBlogFollow mFollow = mBlogFollowService.queryByIdAndTargetId(uinfo
					.getId(), mBlogFollow.getTargetId(), null);
			if (mFollow != null && mFollow.getType().equals("1")) {
				dto.setType("1");// 表示互相关注
			} else if (mFollow != null && mFollow.getFollowStatus().equals("1")) {
				dto.setType("2");// 表示一关注
			} else {
				dto.setType("0");
			}
			if (dto != null) {
				// 查询粉丝数
				dto.setFansCount(mBlogFollowService.queryFansCountByConditions(
						mBlogFollow.getTargetId(), null));
				// 查询关注数
				dto.setFollowCount(mBlogFollowService
						.queryFollowCountByConditions(
								mBlogFollow.getTargetId(), null,null));
				// 查询微博数
				dto.setMblogCount(mBlogService
						.queryAllCountMBlogById(mBlogFollow.getTargetId()));
				dtolist.add(dto);
			}
		}
		page.setRecords(dtolist);
		page.setTotals(mBlogFollowService.queryFollowCountByConditions(infoId,
				null,null));
		out.put("page", page);
		out.put("followCount", mBlogFollowService.queryFollowCountByConditions(
				infoId, null,null));
		
		// 查询出统计没有读的回复和评论
		Integer commentCount = mBlogSystemService.queryCountById(uinfo.getId(),
				"3", "0");
		out.put("commentCount", commentCount);
		//查询出新粉丝的count
		out.put("newFansCount", mBlogFollowService.queryFansCountByConditions(
				uinfo.getId(), "0"));
		//读取出有人@我的人
		Integer anteCount = mBlogSystemService.queryCountById(uinfo.getId(), "2", "0");
		out.put("anteCount", anteCount);
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(uinfo.getId(), 5);
		out.put("anteList", anteList);
		SeoUtil.getInstance().buildSeo("follow", out);
		return null;

	}

	@RequestMapping
	public ModelAndView ante(Map<String, Object> out, HttpServletRequest request,PageDto<MBlogSystemDto> page) {
		EpAuthUser user = getCachedUser(request);
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		
		//查询出@我的信息
		
		page=mBlogSystemService.queryAnteSystemById(info.getId(), "2", null, page);
		out.put("page", page);
		
		// 更新ante所有的状态为1
		List<MBlogSystem> systemList = mBlogSystemService
				.querySystemByisReadAndType(info.getId(), "2", "0");
		for (MBlogSystem mBlogSystem : systemList) {
			mBlogSystemService.updateIsReadStatus(mBlogSystem.getId());
		}
		
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
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(info.getId(), 5);
		out.put("anteList", anteList);
		
		SeoUtil.getInstance().buildSeo("mblog", out);
		return null;
	}

	@RequestMapping
	public ModelAndView comments(Map<String, Object> out,
			HttpServletRequest request, PageDto<MBlogSystemDto> page) {
		page.setLimit(5);
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		}

		// 分页查询 和更新状态
		page = mBlogSystemService.querySystemById(info.getId(), "3", null, page);
		out.put("page", page);

		// 更新所有的状态为1
		List<MBlogSystem> systemList = mBlogSystemService
				.querySystemByisReadAndType(info.getId(), "3", "0");
		for (MBlogSystem mBlogSystem : systemList) {
			mBlogSystemService.updateIsReadStatus(mBlogSystem.getId());
		}
		
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
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(info.getId(), 5);
		out.put("anteList", anteList);
		
		SeoUtil.getInstance().buildSeo("mblog", out);

		return null;
	}
	
	@RequestMapping
	public ModelAndView message(Map<String, Object> out,
			HttpServletRequest request, PageDto<MBlogSystem> page) {
		page.setLimit(10);
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		// 分页查询 和更新状态
		page = mBlogSystemService.queryMessageByInfoId(info.getId(), "4", null, page);
		out.put("page", page);

		// 更新所有的状态为1
		List<MBlogSystem> systemList = mBlogSystemService
				.querySystemByisReadAndType(info.getId(), "4", "0");
		for (MBlogSystem mBlogSystem : systemList) {
			mBlogSystemService.updateIsReadStatus(mBlogSystem.getId());
		}
		
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
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(info.getId(), 5);
		out.put("anteList", anteList);
		
		SeoUtil.getInstance().buildSeo("mblog", out);

		return null;
	}

	@RequestMapping
	public ModelAndView headimg(Map<String, Object> out,
			HttpServletRequest request, Integer targetInfoId) {
		EpAuthUser user = getCachedUser(request);
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
				.getCid());
		out.put("info", info);
		// 查询该用户信息
		MBlogInfoDto infoDto = mBlogInfoService.queryInfoById(targetInfoId);
		// 查询粉丝数
		infoDto.setFansCount(mBlogFollowService.queryFansCountByConditions(
				targetInfoId, null));
		// 查询关注数
		infoDto.setFollowCount(mBlogFollowService.queryFollowCountByConditions(
				targetInfoId, null,null));
		// 查询微博数
		infoDto
				.setMblogCount(mBlogService
						.queryAllCountMBlogById(targetInfoId));
		// 查询公司信息
		CompProfile compProfile = compProfileService
				.queryCompProfileById(infoDto.getmBlogInfo().getCid());
		// 查询我关注他的状态
		MBlogFollow follow = mBlogFollowService.queryByIdAndTargetId(info
				.getId(), targetInfoId, "1");
		if (follow != null) {
			out.put("follow", follow);
		}
		out.put("compProfile", compProfile);
		out.put("infoDto", infoDto);

		return null;
	}


	@RequestMapping
	public ModelAndView replyPersonal(Map<String, Object> out,
			HttpServletRequest request) {
		return null;
	}

	
	@RequestMapping
	public ModelAndView replySearch(Map<String, Object> out,
			HttpServletRequest request, String textContent, Integer mblogId,
			String type, Integer sentType, Integer tragetInfoId,
			Integer tragetId) throws UnsupportedEncodingException {
		
		getReply(out, request, textContent, mblogId, type, sentType, tragetInfoId, tragetId);
		
		return null;
	}
	
	
	// 读取评论人的信息
	@RequestMapping
	public ModelAndView replySearch1(Map<String, Object> out,
			HttpServletRequest request, Integer mblogId) {
		getQueryreply(out, request, mblogId);
		return null;
	}
	
	
	
	@RequestMapping
	public ModelAndView forwardReplySm1(Map<String, Object> out,
			HttpServletRequest request, Integer mblogId){
		
		getQueryreply(out, request, mblogId);
		
		return null;
	}

	@RequestMapping
	public ModelAndView forwardReplySm(Map<String, Object> out,
			HttpServletRequest request, String textContent, Integer mblogId,
			String type, Integer sentType, Integer tragetInfoId,
			Integer tragetId) throws UnsupportedEncodingException {
		
		getReply(out, request, textContent, mblogId, type, sentType, tragetInfoId, tragetId);
		
		return null;
	}
	
	
	
	@RequestMapping
	public ModelAndView searchFind(Map<String, Object> out,
			HttpServletRequest request,String keywords,PageDto<MBlogInfoDto> page) {
		// 读取用户信息
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		}

		// 查询感兴趣的人
		List<MBlogInfo> infoList = new ArrayList<MBlogInfo>();
		// 查询匹配地区
		List<MBlogInfo> uinfoList = mBlogInfoService.queryInfobyAreaCode(info
				.getAreaCode(), info.getProvinceCode());

		// 查询公司的信息
		CompProfile compProfile = compProfileService.queryCompProfileById(info
				.getCid());
		// 如果类别不为空
		for (MBlogInfo mBlogInfo : uinfoList) {
			// 查看我是否已经关注
			MBlogFollow notFollowInfo = mBlogFollowService
					.queryByIdAndTargetId(info.getId(), mBlogInfo.getId(), "1");
			// 如果灭有关注9
			if (notFollowInfo == null
					&& !mBlogInfo.getId().equals(info.getId())) {
				// 根据公司id去查询对应的相关类别
				CompProfile ucompProfile = compProfileService
						.queryCompProfileById(mBlogInfo.getCid());
				// 根据类别去匹配
				if (StringUtils.isNotEmpty(compProfile.getIndustryCode())) {
					if (StringUtils.isNotEmpty(ucompProfile.getIndustryCode())&& ucompProfile.getIndustryCode().equals(compProfile.getIndustryCode())) {
						if (infoList.size() == 6) {
							break;
						} else {
							infoList.add(mBlogInfo);
						}
					} else {
						if (infoList.size() == 6) {
							break;
						} else {
							infoList.add(mBlogInfo);
						}
					}
				} else {
					if (infoList.size() == 6) {
						break;
					} else {
						infoList.add(mBlogInfo);
					}
				}
			}
		}
		out.put("infoList", infoList);

		//搜索引擎
		out.put("keywords", keywords);
		page.setLimit(10);
		page=mBlogInfoService.pageBySearchEngine(keywords, page, info.getId());
		out.put("page", page);
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
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(info.getId(), 5);
		out.put("anteList", anteList);
		
		SeoUtil.getInstance().buildSeo("search", out);
		return null;
	}

	@RequestMapping
	public ModelAndView searchMblog(Map<String, Object> out,
			HttpServletRequest request,String mBlogKeywords,String keywords,PageDto<MBlogDto> page) {
		// 读取用户信息
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		
		//搜索热搜词
		Map<String, String>	maps=TagsUtils.getInstance().queryTagsByCode("100110021000", null, 10);
		out.put("maps", maps);
		page.setLimit(10);
		//搜索引擎
		if(StringUtils.isNotEmpty(mBlogKeywords)){
			page=mBlogService.pageBySearchEngine(mBlogKeywords, page);
			out.put("keywords", mBlogKeywords);
		}else{
			page=mBlogService.pageBySearchEngine(keywords, page);
			out.put("keywords", keywords);
		}
		   out.put("page", page);		
		
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
		//查询出@最多的人
		List<MBlogInfo> anteList=mBlogInfoService.queryAnetInfo(info.getId(), 5);
		out.put("anteList", anteList);
		
		SeoUtil.getInstance().buildSeo("searchMblog", out);

		return null;
	}

	@RequestMapping
	public ModelAndView topic(Map<String, Object> out,
			HttpServletRequest request, Integer mblogId) {
		// 用户自己的信息
		EpAuthUser user = getCachedUser(request);
		if (user == null) {
			return new ModelAndView("redirect:login.htm");
		}
		MBlogInfo info =mBlogInfoService.queryInfoByCid(user.getCid());
		if(info!=null && info.getIsDelete().equals("1")){
			out.put("error","300");
			return new ModelAndView("redirect:login.htm");
		}
		if (info == null) {
			return new ModelAndView("redirect:register.htm");
		} else if (info != null && StringUtils.isEmpty(info.getName())) {
			return new ModelAndView("redirect:register.htm");
		}
		out.put("info", info);

		// 根据id查询出博文内容
		MBlog topicMBlog = mBlogService.queryOneById(mblogId);
		if(topicMBlog==null){
			return new ModelAndView("redirect:resourceNotFound.htm");
		}
		out.put("topicMBlog", topicMBlog);
		// 如果有图片就选取选取第一张图片
		if (StringUtils.isNotEmpty(topicMBlog.getPhotoPath())) {
			String[] topicPhoto = topicMBlog.getPhotoPath().split(",");
			String photoPath = topicPhoto[0];
			out.put("photoPath", photoPath);

		}
		// 根据博文id查出对应的人
		MBlogInfo topicInfo = mBlogInfoService.queryInfoByInfoIdorCid(
				topicMBlog.getInfoId(), null);
		out.put("topicInfo", topicInfo);
		// 根据用户查出对应的简介信息
		CompProfile compProfile = compProfileService
				.queryCompProfileById(topicInfo.getCid());
		if (compProfile != null) {
			out.put("compProfile", compProfile);
		}
		// 查询出我是否是关注他的状态
		MBlogFollow follow = mBlogFollowService.queryByIdAndTargetId(info
				.getId(), topicInfo.getId(), "1");
		if (follow != null) {
			out.put("follow", follow);
		}

		// 查询出最新用户的的参与
		List<MBlogDto> dtoList = mBlogService.querytopicbyInfo(topicMBlog
				.getTitle(), 20);
		out.put("dtoList", dtoList);
		// 查询出讨论数
		Integer talkCount = mBlogService.querytopicCountByInfo(topicMBlog
				.getTitle());
		out.put("talkCount", talkCount);
		List<MBlogInfo> infoList = mBlogInfoService.queryInfoBytopIcTitle(
				topicMBlog.getTitle(), 16);
		out.put("infoList", infoList);
		SeoUtil.getInstance().buildSeo("mblog", out);
		return null;
	}

	@RequestMapping
	public ModelAndView dynamicTopic(Map<String, Object> out,
			HttpServletRequest request, String contents, String photos,
			String topicTitle) {
		EpAuthUser user = getCachedUser(request);
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
				.getCid());
		if (info != null) {
			// 判断他发的话题被删除强加
			if (contents.indexOf(topicTitle) == -1) {
				contents = topicTitle + contents;
			}
			// 添加
			MBlog mBlog = new MBlog();
			mBlog.setTitle(topicTitle);
			mBlog.setInfoId(info.getId());
			mBlog.setContent(contents);
			mBlog.setPhotoPath(photos);
			mBlog.setType("2");// 代表话题

			Integer i = mBlogService.insert(mBlog);
			if (i != null && i.intValue() > 0) {
				MBlog newMBlog = mBlogService.queryOneById(i);

				// 查询出最早发布的人的标题
				if (StringUtils.isNotEmpty(topicTitle)) {
					MBlog tailkMbBlog = mBlogService
							.querymblogByTitle(topicTitle);
					newMBlog.setContent(newMBlog.getContent().replace(topicTitle,"<a href=\"topic" + tailkMbBlog.getId()+ ".htm\" style= \"color:#0078b6\">"+ topicTitle + " </a>"));
				}
				newMBlog.setContent(Face.getFace(newMBlog.getContent()));
				out.put("newMBlog", newMBlog);
				if (StringUtils.isNotEmpty(newMBlog.getPhotoPath())) {
					List<Object> objList = new ArrayList<Object>();
					String[] photoArray = newMBlog.getPhotoPath().split(",");
					for (int j = 0; j < photoArray.length; j++) {
						MBlog mBlog1 = new MBlog();
						if (StringUtils.isNotEmpty(photoArray[j])) {
							mBlog1.setPhotoPath(photoArray[j]);
							objList.add(mBlog1);
						}
					}
					out.put("objList", objList);
				}

			}
			out.put("info", info);
			out.put("contents", contents);
		}
		return null;
	}

	@RequestMapping
	public ModelAndView ajaxReplyComment(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> out,
			String content, Integer commentId, Integer mBlogId,
			Integer tragetInfoId, Integer sentType) {
		ExtResult result = new ExtResult();
		do {
			EpAuthUser user = getCachedUser(request);
			MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
					.getCid());

			// 添加到回复表和系统表
			MBlogComment comment = new MBlogComment();
			comment.setInfoId(info.getId());
			comment.setTargetId(commentId);
			comment.setContent(content);
			// comment.setContent("回复@"+tragetInfo.getName()+":"+content+"//@"+tragetInfo.getName()+":"+comments.getContent());
			// 根据这条id查询出博文的id
			comment.setMblogId(mBlogId);
			comment.setTargetType("2");
			Integer i = mBlogCommentService.sentComment(comment);
			if (i != null && i.intValue() > 0) {
				//更新文章的评论数
				mBlogService.updateDiscussCount(mBlogId);
				if(!tragetInfoId.equals(info.getId())){
					// 更新到系统表
					MBlogSystem system = new MBlogSystem();
					system.setFromId(info.getId());
					system.setToId(tragetInfoId);
					system.setType("3");
					system.setContent(String.valueOf(i));
					Integer id = mBlogSystemService.insert(system);
					if (id != null && id.intValue() > 0) {
						result.setSuccess(true);
					}
				}
			}

			if (sentType.equals(2)) {// 用于转发博文下评论的信息的标记
				// 获取评论的信息
				MBlogComment comments = mBlogCommentService
						.queryOneCommentById(commentId);
				MBlogInfo tragetInfo = mBlogInfoService.queryInfoByInfoIdorCid(
						tragetInfoId, null);
				// 添加到动态表
				MBlog mBlog = new MBlog();
				mBlog.setInfoId(info.getId());
				mBlog.setContent("回复@" + tragetInfo.getName() + ":" + content+ "//@" + tragetInfo.getName() + ":"+ comments.getContent());
				mBlog.setType("1");
				Integer sid = mBlogService.insert(mBlog);
				if (sid != null && sid.intValue() > 0) {
					//添加到系统表
					if(!comments.getInfoId().equals(info.getId())){
						MBlogSystem system=new MBlogSystem();
						system.setFromId(info.getId());
						system.setToId(comments.getInfoId());
						system.setType("2");
						system.setContent(String.valueOf(sid));
						mBlogSystemService.insert(system);
					}
					// 更新转发数
					mBlogService.updateSentCount(mBlogId);
					// 插入转发关系表
					MBlogSent mBlogSent = new MBlogSent();
					mBlogSent.setMblogId(sid);
					mBlogSent.setTargetId(mBlogId);
					mBlogSent.setTopId(mBlogId);
					mBlogSentService.insert(mBlogSent);
				}
			}
		} while (false);
		return printJson(result, out);

	}

	/**
	 * 函数名称：resourceNotFound 功能描述：发生404错误页面。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView resourceNotFound(HttpServletRequest request,
			Map<String, Object> out) {
		// SEO初始化
		SeoUtil.getInstance().buildSeo("", null, null, null, out);
		return null;
	}

	/**
	 * 函数名称：uncaughtException 功能描述：发生异常错误页面。 输入参数：
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param out
	 *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 *            　　　　　2012/05/23　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@RequestMapping
	public ModelAndView uncaughtException(HttpServletRequest request,
			Map<String, Object> out) {
		// SEO初始化
		SeoUtil.getInstance().buildSeo("", null, null, null, out);
		return null;
	}

	
	
	@RequestMapping
	public void getReply(Map<String, Object> out,
			HttpServletRequest request, String textContent, Integer mblogId,
			String type, Integer sentType, Integer tragetInfoId,
			Integer tragetId) throws UnsupportedEncodingException {
		
		String content = textContent;

		// 存入数据库去空格
		String contents = "";
		content = content.trim().replaceAll("：", ":");
		for (int i = 0; i < content.length(); i++) {
			if (i != 0 && content.charAt(i - 1) == ' '&& content.charAt(i) == ' ') {
				continue;
			} else {
				contents += content.charAt(i);
			}
		}
		contents = contents.replace("\n", "");
		EpAuthUser user = getCachedUser(request);
		MBlogInfo info = mBlogInfoService.queryInfoByInfoIdorCid(null, user
				.getCid());
		out.put("info", info);
		// 查询我回复的人的信息
		if (tragetInfoId != null) {
			MBlogInfo tragetInfo = mBlogInfoService.queryInfoByInfoIdorCid(
					tragetInfoId, null);
			out.put("tragetInfo", tragetInfo);
		}
		
		
		// 添加到评论表
		if (content != null) {

			out.put("mblogId", mblogId);
			MBlogComment comment = new MBlogComment();
			comment.setInfoId(info.getId());
			comment.setMblogId(mblogId);
			comment.setTargetType(type);
			if (tragetId != null) {
				comment.setTargetId(tragetId);
			} else {
				comment.setTargetId(mblogId);
			}
			comment.setContent(contents);
			Integer i = mBlogCommentService.sentComment(comment);
			if (i != null && i.intValue() > 0) {

				String newContent = Face.getFace(contents);
				if (newContent.indexOf("@") != -1) {
					Map<String, Object> maps = mBlogService
							.replceMblogAnte(newContent);
					for (String mapKey : maps.keySet()) {
						if(StringUtils.isEmpty(mapKey)){
							continue;
						}
						// 查询出对应的人的id
						MBlogInfo mBlogInfo = mBlogInfoService.queryInfoByInfoByName(mapKey);
						if (mBlogInfo != null) {
							newContent = newContent.replace("@" + mapKey,"<a href=\"ublog"+ mBlogInfo.getId()+ ".htm\" style=\"color:#006633;\">@"+ mapKey + "</a>");
							// 添加到系统表
						} else {
							newContent = newContent.replace("@" + mapKey,"<a href=\"resourceNotFound.htm\" style=\"color:#006633;\">@"+ mapKey + "</a>");
						}
					}
				}

				out.put("content", newContent);

				mBlogService.updateDiscussCount(mblogId);
				out.put("tragetId", i);
				// 添加到系统表
				// 查询出博文的id
				MBlog mBlog = mBlogService.queryOneById(mblogId);
				if (!mBlog.getInfoId().equals(info.getId())) {
					// 插入到系统表
					MBlogSystem system = new MBlogSystem();
					system.setFromId(info.getId());
					system.setToId(mBlog.getInfoId());
					system.setType("3");
					system.setContent(String.valueOf(i));
					mBlogSystemService.insert(system);
				} else if (tragetId != null && type.equals("2")) {
					// 查询出我回复的是否是我自己
					MBlogComment comment2 = mBlogCommentService
							.queryOneCommentById(tragetId);
					if (!comment2.getInfoId().equals(info.getId())) {
						MBlogSystem system = new MBlogSystem();
						system.setFromId(info.getId());
						system.setToId(comment2.getInfoId());
						system.setType("3");
						// 先用于存储信息
						system.setContent(String.valueOf(i));
						mBlogSystemService.insert(system);
					}
				}
			}
		}
		// 说明还要转发
		if (sentType != null && sentType.equals(1)) {
			MBlog mBlog = new MBlog();
			mBlog.setInfoId(info.getId());
			mBlog.setContent(contents);
			mBlog.setType("1");
			Integer sid = mBlogService.insert(mBlog);
			if (sid != null && sid.intValue() > 0) {
				
				//查询出是否是自己发布的
				MBlog toMBlog = mBlogService.queryOneById(mblogId);
				if(!info.getId().equals(toMBlog.getInfoId())){
					//转发的添加到系统表
					MBlogSystem tragetSystem=new MBlogSystem();
					tragetSystem.setFromId(info.getId());
					tragetSystem.setToId(toMBlog.getInfoId());
					tragetSystem.setContent(String.valueOf(sid));
					tragetSystem.setType("2");
					mBlogSystemService.insert(tragetSystem);
				}
				//转发的评论里有@就添加到到系统表
				if (contents.indexOf("@") != -1) {
					Map<String, Object> maps = mBlogService.replceMblogAnte(contents);
					for (String mapKey : maps.keySet()) {
						// 查询出对应的人的id
						MBlogInfo mBlogInfo = mBlogInfoService.queryInfoByInfoByName(mapKey);
						if (mBlogInfo != null) {
							if(!mBlogInfo.getId().equals(info.getId())){
								// 添加到系统表
								MBlogSystem system = new MBlogSystem();
								system.setFromId(info.getId());
								system.setToId(mBlogInfo.getId());
								system.setContent(String.valueOf(sid));
								system.setType("2");
								mBlogSystemService.insert(system);
							}
						}
					}
				}
				
				// 更新转发数
				mBlogService.updateSentCount(mblogId);
				// 插入转发关系表
				MBlogSent mBlogSent = new MBlogSent();
				mBlogSent.setMblogId(sid);
				mBlogSent.setTargetId(mblogId);
				mBlogSent.setTopId(mblogId);
				mBlogSentService.insert(mBlogSent);
			}
		}
		if (sentType != null && sentType.equals(2)) {// 用于转发博文下评论的信息的标记
			// 获取评论的信息
			MBlogComment comments = mBlogCommentService
					.queryOneCommentById(tragetId);
			MBlogInfo tragetInfo = mBlogInfoService.queryInfoByInfoIdorCid(
					tragetInfoId, null);
			// 添加到动态表
			MBlog mBlog = new MBlog();
			mBlog.setInfoId(info.getId());
			mBlog.setContent("回复@" + tragetInfo.getName() + ":" + contents
					+ "//@" + tragetInfo.getName() + ":"
					+ comments.getContent());
			mBlog.setType("1");
			Integer sid = mBlogService.insert(mBlog);
			if (sid != null && sid.intValue() > 0) {
				//转发添加到系统表
				//查询出是否是回复自己的
				if(!comments.getInfoId().equals(info.getId())){
					MBlogSystem system=new MBlogSystem();
					system.setFromId(info.getId());
					system.setToId(comments.getInfoId());
					system.setType("2");
					system.setContent(String.valueOf(sid));
					mBlogSystemService.insert(system);
				}
				
				// 更新转发数
				mBlogService.updateSentCount(mblogId);
				// 插入转发关系表
				MBlogSent mBlogSent = new MBlogSent();
				mBlogSent.setMblogId(sid);
				if (tragetId != null) {
					mBlogSent.setTargetId(tragetId);
				} else {
					mBlogSent.setTargetId(mblogId);
				}
				mBlogSent.setTopId(mblogId);
				mBlogSentService.insert(mBlogSent);
			}
		}
		
	}
	
	
	// 读取评论人的信息
	@RequestMapping
	public void getQueryreply(Map<String, Object> out,
			HttpServletRequest request, Integer mblogId) {

		List<MBlogCommentDto> dtoList = mBlogCommentService
				.queryMblogCommentBymblogId(mblogId);

		out.put("dtoList", dtoList);
	}
	
	
	
}
