package com.zz91.ep.admin.controller.mblog;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.comp.CompProfileService;
import com.zz91.ep.admin.service.mblog.MBlogInfoService;
import com.zz91.ep.admin.service.sys.SysAreaService;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogInfoDto;
import com.zz91.util.lang.StringUtils;


@Controller
public class InfoController extends BaseController{
	@Resource
	private MBlogInfoService mBlogInfoService;
	@Resource
	private SysAreaService sysAreaService;
	@Resource
	private CompProfileService compProfileService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request,
			Map<String, Object> out){
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryAllMblogInfo(HttpServletRequest request,
			Map<String, Object> out,PageDto<MBlogInfoDto> page,MBlogInfo mBlogInfo,String compName,String address){
		CompProfile compProfile=new CompProfile();
		if(StringUtils.isNotEmpty(compName)){
			compProfile.setName(compName);
		}
		String code=sysAreaService.queryProvinceCodeByProvinceName(address);
		//截取出code
		if(StringUtils.isNotEmpty(code)){
			if(code.length()>12){
				String provinceCode = code.substring(0, 12);
				if(StringUtils.isNotEmpty(provinceCode)){
					mBlogInfo.setProvinceCode(provinceCode);
				}
				mBlogInfo.setAreaCode(code);
			}else if(code.length()==12){
				mBlogInfo.setProvinceCode(code);
			}
			
		}
		page.setLimit(20);
		page=mBlogInfoService.queryAllMblogInfo(mBlogInfo, compProfile, page);
		return printJson(page, out);
	}
    //冻结帐号
	@RequestMapping
	public ModelAndView updateCompCodeAndInfoCode(HttpServletRequest request,
			Map<String, Object> out,String codeBlock,Integer id,Integer infoId){
		
		ExtResult result = new ExtResult();
		//公司帐号冻结调
		Integer i=compProfileService.updateMemberCodeBlock(id, codeBlock);
		if (i != null && i.intValue() > 0) {
			Integer ids=mBlogInfoService.updateIsDeleteStatus(infoId, "1");
			if(ids!=null && ids.intValue()>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	//解除帐号
	@RequestMapping
	public ModelAndView updateInfoDeleteStatus(HttpServletRequest request,
			Map<String, Object> out,String codeBlock,Integer id,Integer infoId){
		
		ExtResult result = new ExtResult();
		//公司帐号冻结调
		Integer i=compProfileService.updateMemberCodeBlock(id, "");
		Integer j=compProfileService.updateDelStatus(id, 0);
		if (i!=null && i.intValue()>0 && j != null && j.intValue()>0) {
			Integer ids=mBlogInfoService.updateIsDeleteStatus(infoId, "0");
			if(ids!=null && ids.intValue()>0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
}
