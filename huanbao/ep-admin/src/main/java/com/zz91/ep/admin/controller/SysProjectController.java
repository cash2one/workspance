package com.zz91.ep.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.service.sys.SysProjectService;
import com.zz91.ep.domain.sys.SysAvatar;
import com.zz91.ep.domain.sys.SysProject;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.util.lang.StringUtils;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-11-16 
 */
@Controller
public class SysProjectController extends BaseController{
	
	@Resource
	private SysProjectService sysProjectService;
	
	final static int PASSWORD_LENGTH = 16;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		out.put("initcode", UUID.randomUUID());
		out.put("initpassword", StringUtils.randomString(PASSWORD_LENGTH));
		return null;
	}
	
	@RequestMapping
	public ModelAndView querySysProject(HttpServletRequest request, Map<String, Object> out,PageDto<SysProject> page, SysProject sysProject){
		page=sysProjectService.pageAllSysProject(sysProject, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView createSysProject(HttpServletRequest request, Map<String, Object> out, SysProject sysProject){
		Integer i = sysProjectService.createSysProject(sysProject);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		result.setData(UUID.randomUUID()+"|"+StringUtils.randomString(PASSWORD_LENGTH));
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateSysProject(HttpServletRequest request, Map<String, Object> out, SysProject sysProject){
		Integer i=sysProjectService.updateSysProject(sysProject);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteSysProject(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i= sysProjectService.deleteSysProjectById(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryOneProject(HttpServletRequest request, Map<String, Object> out, Integer id){
		SysProject project=sysProjectService.querySysProjectById(id);
		PageDto<SysProject> page=new PageDto<SysProject>();
		List<SysProject> list=new ArrayList<SysProject>();
		list.add(project);
		page.setRecords(list);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView avatar(HttpServletRequest request, Map<String, Object> out){
		List<SysAvatar> list=new ArrayList<SysAvatar>();
		list.add(new SysAvatar("/images/bsicon/user-2.png","user-2.png"));
		list.add(new SysAvatar("/images/bsicon/security.png","security.png"));
		list.add(new SysAvatar("/images/bsicon/cd.png","cd.png"));
		list.add(new SysAvatar("/images/bsicon/soccer-ball.png","soccer-ball.png"));
		list.add(new SysAvatar("/images/bsicon/clipboard.png","clipboard.png"));
		list.add(new SysAvatar("/images/bsicon/toilet-paper.png","toilet-paper.png"));
		list.add(new SysAvatar("/images/bsicon/earth.png","earth.png"));
		list.add(new SysAvatar("/images/bsicon/block.png","block.png"));
		list.add(new SysAvatar("/images/bsicon/pen.png","pen.png"));
		list.add(new SysAvatar("/images/bsicon/poo.png","poo.png"));
		list.add(new SysAvatar("/images/bsicon/apple-red.png","apple-red.png"));
		list.add(new SysAvatar("/images/bsicon/xls.png","xls.png"));
		list.add(new SysAvatar("/images/bsicon/print.png","print.png"));
		list.add(new SysAvatar("/images/bsicon/settings.png","settings.png"));
		list.add(new SysAvatar("/images/bsicon/movie.png","movie.png"));
		list.add(new SysAvatar("/images/bsicon/apple-green.png","apple-green.png"));
		list.add(new SysAvatar("/images/bsicon/flag.png","flag.png"));
		list.add(new SysAvatar("/images/bsicon/burn.png","burn.png"));
		list.add(new SysAvatar("/images/bsicon/pack-2.png","pack-2.png"));
		list.add(new SysAvatar("/images/bsicon/music-1.png","music-1.png"));
		list.add(new SysAvatar("/images/bsicon/text.png","text.png"));
		list.add(new SysAvatar("/images/bsicon/tree.png","tree.png"));
		list.add(new SysAvatar("/images/bsicon/pdf.png","pdf.png"));
		list.add(new SysAvatar("/images/bsicon/pack-1.png","pack-1.png"));
		list.add(new SysAvatar("/images/bsicon/photo.png","photo.png"));
		list.add(new SysAvatar("/images/bsicon/credit-card.png","credit-card.png"));
		list.add(new SysAvatar("/images/bsicon/lock.png","lock.png"));
		list.add(new SysAvatar("/images/bsicon/alert.png","alert.png"));
		list.add(new SysAvatar("/images/bsicon/dvd.png","dvd.png"));
		list.add(new SysAvatar("/images/bsicon/music-2.png","music-2.png"));
		list.add(new SysAvatar("/images/bsicon/download.png","download.png"));
		list.add(new SysAvatar("/images/bsicon/toy-2 copy.png","toy-2 copy.png"));
		list.add(new SysAvatar("/images/bsicon/cd-copy.png","cd-copy.png"));
		list.add(new SysAvatar("/images/bsicon/ruler.png","ruler.png"));
		list.add(new SysAvatar("/images/bsicon/coke.png","coke.png"));
		list.add(new SysAvatar("/images/bsicon/basketball.png","basketball.png"));
		list.add(new SysAvatar("/images/bsicon/clock.png","clock.png"));
		list.add(new SysAvatar("/images/bsicon/note.png","note.png"));
		list.add(new SysAvatar("/images/bsicon/skull.png","skull.png"));
		list.add(new SysAvatar("/images/bsicon/search.png","search.png"));
		list.add(new SysAvatar("/images/bsicon/wireless.png","wireless.png"));
		list.add(new SysAvatar("/images/bsicon/color-palette.png","color-palette.png"));
		list.add(new SysAvatar("/images/bsicon/dvd-copy.png","dvd-copy.png"));
		list.add(new SysAvatar("/images/bsicon/tennis-ball.png","tennis-ball.png"));
		list.add(new SysAvatar("/images/bsicon/unlock.png","unlock.png"));
		list.add(new SysAvatar("/images/bsicon/user-1.png","user-1.png"));
		list.add(new SysAvatar("/images/bsicon/8ball.png","8ball.png"));
		list.add(new SysAvatar("/images/bsicon/app.png","app.png"));
		list.add(new SysAvatar("/images/bsicon/bell.png","bell.png"));
		list.add(new SysAvatar("/images/bsicon/lamp.png","lamp.png"));
		list.add(new SysAvatar("/images/bsicon/chat.png","chat.png"));
		list.add(new SysAvatar("/images/bsicon/id-card.png","id-card.png"));
		list.add(new SysAvatar("/images/bsicon/flower.png","flower.png"));
		list.add(new SysAvatar("/images/bsicon/t-shirt.png","t-shirt.png"));
		list.add(new SysAvatar("/images/bsicon/toy.png","toy.png"));
		list.add(new SysAvatar("/images/bsicon/upload.png","upload.png"));
		list.add(new SysAvatar("/images/bsicon/recycle.png","recycle.png"));
		list.add(new SysAvatar("/images/bsicon/date.png","date.png"));
		list.add(new SysAvatar("/images/bsicon/burger.png","burger.png"));
		list.add(new SysAvatar("/images/bsicon/pencil-2.png","pencil-2.png"));
		list.add(new SysAvatar("/images/bsicon/doc.png","doc.png"));
		list.add(new SysAvatar("/images/bsicon/music-3.png","music-3.png"));
		list.add(new SysAvatar("/images/bsicon/edit.png","edit.png"));
		list.add(new SysAvatar("/images/bsicon/note-2.png","note-2.png"));
		list.add(new SysAvatar("/images/bsicon/twitter.png","twitter.png"));
		list.add(new SysAvatar("/images/bsicon/help.png","help.png"));
		return printJson(list, out);
	}
}
