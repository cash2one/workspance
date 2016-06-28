/**
 * 
 */
package com.zz91.ads.board.controller.ad;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ads.board.controller.BaseController;
import com.zz91.ads.board.domain.ad.AdMaterial;
import com.zz91.ads.board.dto.ExtResult;
import com.zz91.ads.board.service.ad.AdMaterialService;

/**
 * @author Administrator
 *
 */
@Controller
public class MaterialController extends BaseController{
	
	@Resource
	private AdMaterialService adMaterialService;
	
	@RequestMapping
	public ModelAndView queryMaterialOfAd(HttpServletRequest request, Map<String, Object> out, Integer aid){
		
		return printJson(adMaterialService.queryAdMaterialByAdId(aid), out);
	}
	
	@RequestMapping
	public ModelAndView createMaterial(HttpServletRequest request, Map<String, Object> out, AdMaterial material){
		Integer i=adMaterialService.insertAdMaterial(material);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
			result.setData(i);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteMaterial(HttpServletRequest request, Map<String, Object> out, Integer id){
		Integer i=adMaterialService.deleteAdMaterialById(id);
		ExtResult result = new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
			result.setData(i);
		}
		return printJson(result, out);
	}
}
