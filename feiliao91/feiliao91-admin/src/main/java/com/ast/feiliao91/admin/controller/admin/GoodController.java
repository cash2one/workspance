/**
 * @author shiqp
 * @date 2016-02-24
 */
package com.ast.feiliao91.admin.controller.admin;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.admin.controller.BaseController;
import com.ast.feiliao91.domain.common.Category;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.GoodsAddProperties;
import com.ast.feiliao91.domain.goods.GoodsDto;
import com.ast.feiliao91.domain.goods.GoodsSearchDto;
import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.ExtTreeDto;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.ast.feiliao91.service.facade.GCategoryFacade;
import com.ast.feiliao91.service.goods.GoodsCategoryService;
import com.ast.feiliao91.service.goods.GoodsService;
import com.ast.feiliao91.service.goods.PictureService;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;

@Controller
public class GoodController extends BaseController{
	@Resource
	private GoodsService goodsService;
	@Resource
	private GoodsCategoryService goodsCategoryService;
	@Resource
	private PictureService pictureService;
	@RequestMapping
	public ModelAndView list(Map<String,Object> out){
		return null;
	}
	@RequestMapping
	public ModelAndView queryList(Map<String,Object> out,PageDto<GoodsDto> page, GoodsSearchDto searchDto) throws IOException{
		if(StringUtils.isEmpty(page.getSort())){
			page.setSort("gmt_modified");
		}
		page = goodsService.pageBySearchAdmin(searchDto, page);
		return printJson(page, out);
	}
//	巧萍写的审核
//	@RequestMapping
//	public ModelAndView updateStatus(HttpServletRequest request,Map<String,Object> out,String ids,Integer checkStatus) throws IOException{
//		ExtResult result = new ExtResult();
//		SessionUser sessionUser = getCachedUser(request);
//		String s = goodsService.updateStatus(ids, sessionUser.getAccount(), checkStatus);
//		if(StringUtils.isNotEmpty(s)){
//			result.setSuccess(false);
//			result.setData("审核失败");
//		}else{
//			result.setSuccess(true);
//			result.setData("审核成功");
//		}
//		return printJson(result, out);
//	}
	/**
	 * 产品编辑页面
	 * @param out
	 * @param goodsId 
	 */
	@RequestMapping
	public void edit(Map<String, Object> out, Integer goodsId,Integer companyId) {
		if (goodsId == null) {
			goodsId = 0;
		}
		out.put("goodsId", goodsId);
		out.put("mainCode", goodsService.queryGoodById(goodsId).getMainCategory());
		if (companyId!=null&&companyId > 0) {
			out.put("companyId", companyId);
		}
	}
	
	@RequestMapping
	public ModelAndView init(Integer id, Map<String, Object> map)throws IOException{
		GoodsDto goodsDto = goodsService.queryGoodInfoByIdAdmin(id);
		PageDto<GoodsDto> page = new PageDto<GoodsDto>();
		List<GoodsDto> list = new ArrayList<GoodsDto>();
		list.add(goodsDto);
		page.setRecords(list);
		return printJson(page, map);
	}
	
	/**
	 * 商品类别树形选择
	 * @param out
	 * @param parentCode
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView goodsChild(Map<String, Object> out, String parentCode) throws IOException {
		if (parentCode == null) {
			parentCode = "";
		} else if (parentCode.equals("0")) {
			parentCode = "";
		}
		List<ExtTreeDto> list = goodsCategoryService.childByAdmin(parentCode);
		return printJson(list, out);
	}
	
	/**
	 * 加载商品的自定义属性
	 * @param out
	 * @param page
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView loadMoreProp(Map<String, Object> out,PageDto<GoodsAddProperties> page,Integer id) throws IOException{
		List<GoodsAddProperties> paps = goodsService.queryByGoodsAddProperties(id);
		page.setRecords(paps);
		return printJson(page, out);
	}
	
	/**
	 * 获得图片
	 * @param goodsId
	 * @param companyId
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView listGoodsPic(Integer goodsId,Integer companyId,Map<String, Object> out) throws IOException {
		PageDto<Picture> page = new PageDto<Picture>();
		List<Picture> plist = pictureService.queryPictureByAdmin(goodsId,companyId);
		page.setRecords(plist);
		return printJson(page, out);
	}
	
	/**
	 * 货物保存修改
	 * @param goods
	 * @param out
	 * @param expireTimeStr过期时间
	 * @return
	 * @throws IOException
	 * @author zhujq
	 * @throws ParseException 
	 */
	@RequestMapping
	public ModelAndView updateGoods(Goods goods,Map<String, Object> out,String expireTimeStr) throws IOException, ParseException {
		ExtResult result = new ExtResult();
		do {
			//我也不知道为什么expireTime的类型不对
			if (StringUtils.isEmpty(expireTimeStr)) {
				break;
			}
			if(StringUtils.isEmpty(goods.getFare())){
				break;
			}
			if("商议后调整".equals(goods.getFare())){
				goods.setFare("-1");
			}else if("包邮".equals(goods.getFare())){
				goods.setFare("0");
			}
			goods.setExpireTime(DateUtil.getDate(expireTimeStr,"yyyy-MM-dd HH:mm:ss"));
			Integer i = goodsService.updateGoods(goods);
			if(Integer.valueOf("1").equals(i)){
				result.setSuccess(true);
				result.setData("保存成功");
			}else{
				result.setSuccess(false);
				result.setData("保存失败");
			}
		} while (false);
		return printJson(result, out);
	}
	
	/**
	 * 审核
	 * @param goodsId
	 * @param checkStatus
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateCheckStatus(HttpServletRequest request,String ids,Integer checkStatus,Map<String, Object> out) throws IOException {
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);
		do {
			if(StringUtils.isEmpty(ids) || checkStatus == null){
				break;
			}
			String s = goodsService.updateStatus(ids,sessionUser.getAccount(),checkStatus);
			if(StringUtils.isEmpty(s)){
				result.setSuccess(false);
				result.setData("审核失败");
			}else{
				result.setSuccess(true);
				result.setData("审核成功");
			}
		} while (false);
		return printJson(result, out);
	}
	/**
	 * 该公司的产品信息页面
	 * @param request
	 * @param out
	 * @param companyId
	 * @return
	 */
	@RequestMapping
	public ModelAndView listOfCompany(HttpServletRequest request,
			Map<String, Object> out, Integer companyId) {
		out.put("companyId", companyId);
		return null;
	}
	
	/**
	 * 获得三级属性
	 * @param goodsId
	 * @param out
	 * @param type
	 * @paam parentCode 若用户没添属性类别，则传入parentCode
	 * @return
	 * @throws IOException
	 */
//	@RequestMapping
//	public ModelAndView getThreeLevelProp(Integer goodsId,Map<String, Object> out,Integer type,String parentCode) throws IOException{
//		List<Category> list=new ArrayList<Category>();
//		PageDto<Category> page=new PageDto<Category>();
//		Goods goods = goodsService.queryGoodById(goodsId);
//		if (StringUtils.isEmpty(parentCode)) {
//			do {
//				if(type.equals(1)){
//					String levelCode = goods.getUseLevel();
//					if(StringUtils.isNotEmpty(levelCode) && levelCode.length()>=12){
//						String a = levelCode.substring(0, 12) ;
//						// 用途级别
//						Map<String, String> umap = CategoryFacade.getInstance().getChild(a);
//						if (umap != null) {
//							for(String s : umap.keySet()){
//								Category c = new Category();
//								c.setCode(s);
//								c.setLabel(umap.get(s));
//								list.add(c);
//							}
//						}
//						page.setRecords(list);
//						return printJson(page,out);
//					}else {
//						break;
//					}
//				}
//				if(type.equals(2)){
//					String levelCode = goods.getProcessLevel();
//					if(StringUtils.isNotEmpty(levelCode) && levelCode.length()>=12){
//						String a = levelCode.substring(0, 12);
//						// 加工级别
//						Map<String, String> pmap = CategoryFacade.getInstance().getChild(a);
//						if (pmap != null) {
//							for(String s : pmap.keySet()){
//								Category c = new Category();
//								c.setCode(s);
//								c.setLabel(pmap.get(s));
//								list.add(c);
//							}
//						}
//						page.setRecords(list);
//						return printJson(page,out);
//					}else {
//						break;
//					}
//				}
//				if(type.equals(3)){
//					String levelCode = goods.getCharLevel();
//					if(StringUtils.isNotEmpty(levelCode) && levelCode.length()>=12){
//						String a = levelCode.substring(0, 12);
//						// texing级别
//						Map<String, String> cmap = CategoryFacade.getInstance().getChild(a);
//						if (cmap != null) {
//							for(String s : cmap.keySet()){
//								Category c = new Category();
//								c.setCode(s);
//								c.setLabel(cmap.get(s));
//								list.add(c);
//							}
//						}
//						page.setRecords(list);
//						return printJson(page,out);
//					}else {
//						break;
//					}
//				}
//			} while (false);
//		}else {
//			Map<String, String> category = CategoryFacade.getInstance().getChild("1002");
//			Map<String, String> label = new HashMap<String, String>();
//			for (String s : category.keySet()) {
//				label.put(category.get(s), s);
//			}
//			if (parentCode.length()>=12) {
//				String lcode = label.get(GCategoryFacade.getInstance().getValue(parentCode.substring(0, 12)));
//				String a = lcode + "1001";
//				Map<String, String> umap = CategoryFacade.getInstance().getChild(a);
//				if (umap != null) {
//					for(String s : umap.keySet()){
//						Category c = new Category();
//						c.setCode(s);
//						c.setLabel(umap.get(s));
//						list.add(c);
//					}
//				}
//				page.setRecords(list);
//				return printJson(page,out);
//			}
//		}	
//		return printJson(page,out);
//	}
	
	/**
	 * 获得三级属性
	 * @param out
	 * @param code
	 * @param type 用途级别=1，加工级别=2，特性级别=3
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView getThreeLevelProp(Map<String, Object> out, String code,Integer type)throws IOException{
		List<Category> list=new ArrayList<Category>();
		PageDto<Category> page=new PageDto<Category>();
		
		Map<String, String> category = CategoryFacade.getInstance().getChild("1002");
		Map<String, String> label = new HashMap<String, String>();
		for (String s : category.keySet()) {
			label.put(category.get(s), s);
		}
		if (code.length()>=12) {
			String lcode = label.get(GCategoryFacade.getInstance().getValue(code.substring(0, 12)));
			if (type.intValue()==1) {
				//用途级别
				String a = lcode + "1001";
				Map<String, String> umap = CategoryFacade.getInstance().getChild(a);
				if (umap != null) {
					for(String s : umap.keySet()){
						Category c = new Category();
						c.setCode(s);
						c.setLabel(umap.get(s));
						list.add(c);
					}
				}
				page.setRecords(list);
				return printJson(page,out);
			}
			if(type.intValue()==2){
				//加工级别
				String a = lcode + "1000";
				Map<String, String> pmap = CategoryFacade.getInstance().getChild(a);
				if (pmap != null) {
					for(String s : pmap.keySet()){
						Category c = new Category();
						c.setCode(s);
						c.setLabel(pmap.get(s));
						list.add(c);
					}
				}
				page.setRecords(list);
				return printJson(page,out);
			}
			if (type.intValue()==3) {
				//加工级别
				String a = lcode + "1002";
				Map<String, String> cmap = CategoryFacade.getInstance().getChild(a);
				if (cmap != null) {
					for(String s : cmap.keySet()){
						Category c = new Category();
						c.setCode(s);
						c.setLabel(cmap.get(s));
						list.add(c);
					}
				}
				page.setRecords(list);
				return printJson(page,out);
			}
		}
		return printJson(page,out);
	}
	/**
	 * 图片审核通过
	 * @param request
	 * @param ids
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView passPic(HttpServletRequest request,String idArrayStr,Map<String, Object> out,Integer status) throws IOException{
		ExtResult result = new ExtResult();
		SessionUser sessionUser = getCachedUser(request);
		do {
			if(StringUtils.isEmpty(idArrayStr)){
				break;
			}
			String s = pictureService.batchUpdatePicStatus(idArrayStr, sessionUser.getAccount(),status);
			if(StringUtils.isEmpty(s)){
				result.setSuccess(false);
				result.setData("审核失败");
			}else{
				result.setSuccess(true);
				result.setData("审核成功");
			}
		}while(false);
		return printJson(result, out);
	}
	
	/**
	 * 编辑更新自定义属性
	 * @author zhujq
	 * @param request
	 * @param goodsId
	 * @param row
	 * @param column
	 * @param value
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView updateProp(HttpServletRequest request,Integer goodsId,Integer row,Integer column,String value,Map<String, Object> out) throws IOException{
		ExtResult result = new ExtResult();
		do {
			Goods goods = goodsService.queryGoodById(goodsId);
			List<GoodsAddProperties> GoodsAddPropertiesList = goodsService.queryByGoodsAddProperties(goodsId);
			GoodsAddProperties GoodsProp = GoodsAddPropertiesList.get(row);//获得要修改的属性
			if (column.intValue()==2) {
				GoodsProp.setProperty(value);
			}else if(column.intValue()==3){
				GoodsProp.setContent(value);
			}else{
				break;
			}
			GoodsAddPropertiesList.set(row,GoodsProp);//更新
			String newProp = changePropertiesListToString(GoodsAddPropertiesList);
			goods.setGoodAttribute(newProp);
			Integer i = goodsService.updateGoods(goods);
			if(i.intValue()==1){
				result.setSuccess(true);
				result.setData("审核成功");
			}else{
				result.setSuccess(false);
				result.setData("审核失败");
			}
		} while (false);
		return printJson(result, out);
	}
	/**
	 * 删除自定义属性
	 * @author zhujq
	 * @param request
	 * @param goodsId
	 * @param property
	 * @param content
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView delProp(HttpServletRequest request,Integer goodsId,String property,String content,Map<String, Object> out) throws IOException{
		ExtResult result = new ExtResult();
		do {
			Goods goods = goodsService.queryGoodById(goodsId);
			List<GoodsAddProperties> GoodsAddPropertiesList = goodsService.queryByGoodsAddProperties(goodsId);
			int index = -1;//定义列表的索引位置
			for (GoodsAddProperties goodsAddProperties : GoodsAddPropertiesList) {
				if (goodsAddProperties.getProperty().equals(property) && goodsAddProperties.getContent().equals(content)) {
					index = GoodsAddPropertiesList.indexOf(goodsAddProperties);
					break;
				}
			}
			if(index==-1){
				break;//若没变化则跳出循环
			}
			GoodsAddPropertiesList.remove(index);//删除索引处元素
			String newProp = changePropertiesListToString(GoodsAddPropertiesList);
			goods.setGoodAttribute(newProp);
			Integer i = goodsService.updateGoods(goods);
			if(i.intValue()==1){
				result.setSuccess(true);
				result.setData("审核成功");
			}else{
				result.setSuccess(false);
				result.setData("审核失败");
			}
		} while (false);
		return printJson(result, out);
	}
	/**
	 * 增加属性
	 * @author zhujq
	 * @param request
	 * @param goodsAddProperties
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView addProp(HttpServletRequest request,GoodsAddProperties goodsAddProperties,Map<String, Object> out) throws IOException{
		ExtResult result = new ExtResult();
		do {
			if(goodsAddProperties==null || goodsAddProperties.getProperty()==null || goodsAddProperties.getContent()==null){
				break;
			}
			Integer goodsId = goodsAddProperties.getGid();
			Goods goods = goodsService.queryGoodById(goodsAddProperties.getGid());
			List<GoodsAddProperties> GoodsAddPropertiesList = goodsService.queryByGoodsAddProperties(goodsId);
			if (GoodsAddPropertiesList==null) {
				GoodsAddPropertiesList = new ArrayList <GoodsAddProperties>();
			}
			GoodsAddPropertiesList.add(goodsAddProperties);
			String newProp = changePropertiesListToString(GoodsAddPropertiesList);
			goods.setGoodAttribute(newProp);
			Integer i = goodsService.updateGoods(goods);
			if(i.intValue()==1){
				result.setSuccess(true);
				result.setData("审核成功");
			}else{
				result.setSuccess(false);
				result.setData("审核失败");
			}
		} while (false);
		return printJson(result, out);
	}
	/**
	 * 重新组装自定义属性
	 * @author zhujq
	 * @param goodsAddPropertiesList
	 * @return
	 */
	private String changePropertiesListToString(
			List<GoodsAddProperties> goodsAddPropertiesList) {
		String prop = "";
		for (GoodsAddProperties goodsAddProperties : goodsAddPropertiesList) {
			String p = "|"+goodsAddProperties.getProperty()+"："+goodsAddProperties.getContent();
			prop=prop+p;
		}
		return prop;
	}
	/**
	 * 
	 * @author zhujq
	 * @param request
	 * @param goodsId
	 * @param propName
	 * @param out
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView isSystemProp(HttpServletRequest request,Integer goodsId,String propName,Map<String, Object> out)throws IOException{
		ExtResult result = new ExtResult();
		do {
			if(goodsId == null || StringUtils.isEmpty(propName)){
				break;
			}
			String processLevel = goodsService.queryGoodById(goodsId).getProcessLevel();
			if (StringUtils.isEmpty(processLevel)) {
				result.setSuccess(false);
				result.setData("该产品未填写加工级别！");
				break;
			}
			Map<String, String> pmap = CategoryFacade.getInstance().getChild(processLevel);
			for (Map.Entry<String, String> entry : pmap.entrySet()) {
				if(entry.getValue().equals(propName)){
					result.setSuccess(false);
					break;
				}
				result.setSuccess(true);
			}
			if(result.isSuccess()==true){
				result.setData("1");
			}else {
				result.setData("系统属性名不能更改");
			}
		} while (false);
		return printJson(result, out);
	}
}
