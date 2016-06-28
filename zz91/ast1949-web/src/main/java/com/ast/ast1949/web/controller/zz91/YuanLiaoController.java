package com.ast.ast1949.web.controller.zz91;

/**
 * @date 2015-09-01
 * @author shiqp
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.domain.yuanliao.CategoryYuanliao;
import com.ast.ast1949.domain.yuanliao.YuanLiaoExportInquiry;
import com.ast.ast1949.domain.yuanliao.YuanLiaoSearch;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.domain.yuanliao.YuanliaoPic;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.persist.yuanliao.CategoryYuanliaoDao;
import com.ast.ast1949.service.company.CompanyPriceService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.yuanliao.CategoryYuanliaoService;
import com.ast.ast1949.service.yuanliao.YuanLiaoExportInquiryService;
import com.ast.ast1949.service.yuanliao.YuanLiaoService;
import com.ast.ast1949.service.yuanliao.YuanliaoPicService;
import com.ast.ast1949.util.DateUtil;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.log.LogUtil;

import net.sourceforge.pinyin4j.PinyinHelper;

@Controller
public class YuanLiaoController extends BaseController {
	@Resource
	private YuanLiaoService yuanLiaoService;
	@Resource
	private YuanliaoPicService yuanliaoPicService;
	@Resource
	private CategoryYuanliaoService categoryYuanliaoService;
	@Resource
	private CategoryYuanliaoDao categoryYuanliaoDao;
	@Resource
	private CompanyPriceService companyPriceService;
	@Resource
	private CompanyService companyService;
	@Resource
	private YuanLiaoExportInquiryService yuanLiaoExportInquiryService;
	
	final static String COMP_PRICE_CHECK_SUCCESS_OPERTION = "check_compprice_success";
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@RequestMapping
	public void index(){
		
	}
	@RequestMapping
	public void list(Integer companyId,Map<String, Object> out){
		if(companyId==null){
			companyId = 0;
		}
		out.put("companyId", companyId);
	}
	@RequestMapping
	public ModelAndView categoryTreeNode(HttpServletRequest request, Map<String, Object> out, String parentCode) throws Exception{
		if(StringUtils.isEmpty(parentCode)){
			parentCode="2009";
		}
		List<ExtTreeDto> nodeList = new ArrayList<ExtTreeDto>();
		List<CategoryYuanliao> list = categoryYuanliaoService.queryCategoryYuanliaoByParentCode(parentCode);
		for(CategoryYuanliao cyl:list){
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-" + String.valueOf(cyl.getId()));
			List<CategoryYuanliao> listc=categoryYuanliaoService.queryCategoryYuanliaoByParentCode(cyl.getCode());
			if(listc.size()==0){
				node.setLeaf(true);
			}else{
				node.setLeaf(false);
			}
			node.setText(cyl.getLabel());
			node.setData(String.valueOf(cyl.getCode()));
			nodeList.add(node);
		}
		return printJson(nodeList, out);
	}
	@RequestMapping
	public ModelAndView querySimpleCategoryById(HttpServletRequest request, Map<String, Object> out, String id) throws Exception{
		CategoryYuanliao cyl = new CategoryYuanliao();
		if(StringUtils.isNotEmpty(id)){
			cyl=categoryYuanliaoService.queryCategoryYuanliaoByCode(id);
		}
		return printJson(cyl, out);
	}
	
	@RequestMapping
	public ModelAndView addCategory(HttpServletRequest request, Map<String, Object> out, CategoryYuanliao categoryYuanliao) throws Exception{
		ExtResult result=new ExtResult();
		Integer i = categoryYuanliaoService.insertCategoryYuanliao(categoryYuanliao);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView updateCategory(HttpServletRequest request, Map<String, Object> out, CategoryYuanliao categoryYuanliao) throws Exception{
		ExtResult result=new ExtResult();
		//类别拼音
		  String convert = "";  
	        for (int j = 0; j < categoryYuanliao.getLabel().length(); j++) {  
	            char word = categoryYuanliao.getLabel().charAt(j);  
	            if(word=='('){
	            	break;
	            }
	            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
	            if (pinyinArray != null) {  
	                convert += pinyinArray[0].charAt(0);  
	            } else {  
	                convert += word;  
	            }  
	        }  
	        Integer flag = 0;
	        Integer fi = 1;
	        do{
	        	Integer isHave = categoryYuanliaoDao.queryPinyin(convert);
	        	if(fi==1&&isHave!=null){
	        		convert = convert + fi;
	        	}
	        	if(isHave!=null){
	        		convert = convert.substring(0, convert.length()-String.valueOf(fi).length());
	        		convert = convert + fi;
	        		fi = fi + 1;
	        	}else{
	        		flag = 1;
	        	}
	        }while(flag!=1);
	        categoryYuanliao.setPinyin(convert);
		Integer i = categoryYuanliaoService.updateCategoryYuanliao(categoryYuanliao);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView deleteCategory(HttpServletRequest request, Map<String, Object> out, String id) throws Exception{
		ExtResult result=new ExtResult();
		CategoryYuanliao categoryYuanliao = new CategoryYuanliao();
		categoryYuanliao.setCode(id);
		categoryYuanliao.setIsDel(1);
		Integer i = categoryYuanliaoService.updateCategoryYuanliao(categoryYuanliao);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView pageYuanliaoByAdmin(HttpServletRequest request, Map<String, Object> out, PageDto<YuanliaoDto> page,YuanLiaoSearch search) throws Exception{
		page.setDir("desc");
		page.setSort("post_time");
		search.setCheckArrays(StringUtils.StringToIntegerArray(search.getCheckArray()));
		page = yuanLiaoService.pageYuanliaoListByAdmin(page, search);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView updateCheckStatus(HttpServletRequest request, Map<String, Object> out, Yuanliao yuanliao) throws Exception{
		SessionUser sessionUser = getCachedUser(request);
		ExtResult result=new ExtResult();
		Yuanliao yl = yuanLiaoService.queryYuanliaoById(yuanliao.getId());
		if(yl!=null){
			yuanliao.setPrice(yl.getPrice());
			yuanliao.setMinPrice(yl.getMinPrice());
			yuanliao.setMaxPrice(yl.getMaxPrice());
			yuanliao.setSendTime(yl.getSendTime());
		}
		yuanliao.setCheckPerson(sessionUser.getAccount());
		yuanliao.setCheckTime(new Date());
		Integer i = yuanLiaoService.updateYuanliao(yuanliao);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	@RequestMapping
	public void edit( Map<String, Object> out, Integer id,String account,Integer companyId) throws Exception{
		Yuanliao yl = yuanLiaoService.queryYuanliaoById(id);
		out.put("yl", yl);
		out.put("id", id);
		out.put("account", account);
		out.put("companyId", companyId);
	}
	
	@RequestMapping
	public ModelAndView queryCategoryYuanliao(Map<String,Object> out,HttpServletRequest request,String code) throws Exception{
		String[] codeArray = code.split(",");
		Map<String,String> mapy = YuanliaoFacade.getInstance().getChild(codeArray[codeArray.length-1]);
		//类别属性(厂家（产地）)
		List<CategoryYuanliao> list = new ArrayList<CategoryYuanliao>();
		if(mapy==null){
			mapy = new HashMap<String,String>();
		}
		for(String cc : mapy.keySet()){
			CategoryYuanliao yl = new CategoryYuanliao();
			yl.setCode(cc);
			yl.setLabel(mapy.get(cc));
			list.add(yl);
		}
		PageDto<CategoryYuanliao> page = new PageDto<CategoryYuanliao>();
		page.setRecords(list);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryCategory(Map<String,Object> out,HttpServletRequest request,String code) throws Exception{
		Map<String,String> mapy = CategoryFacade.getInstance().getChild(code);
		List<CategoryDO> list = new ArrayList<CategoryDO>();
		if(mapy==null){
			mapy = new HashMap<String,String>();
		}
		for(String cc : mapy.keySet()){
			CategoryDO c = new CategoryDO();
			c.setCode(cc);
			c.setLabel(mapy.get(cc));
			list.add(c);
		}
		PageDto<CategoryDO> page = new PageDto<CategoryDO>();
		page.setRecords(list);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView init(Map<String,Object> out,HttpServletRequest request,Integer id) throws Exception{
		YuanliaoDto dto = new YuanliaoDto();
		Yuanliao yl = yuanLiaoService.queryYuanliaoById(id);
		if(StringUtils.isNotEmpty(yl.getTags())){
			String tags = "";
			for(String s:yl.getTags().split(",")){
				if(StringUtils.isNotEmpty(s)){
					if(StringUtils.isNotEmpty(tags)){
						tags = tags + "," + s;
					}else{
						tags = s;
					}
					
				}
			}
			yl.setTags(tags);
		}
		String addr = "";
		if(StringUtils.isNotEmpty(yl.getLocation())&&yl.getLocation().length()>12){
			addr =CategoryFacade.getInstance().getValue(yl.getLocation().substring(0,12))+CategoryFacade.getInstance().getValue(yl.getLocation());
		}else if(StringUtils.isNotEmpty(yl.getLocation())&&yl.getLocation().length()>8){
			addr = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0,8))+CategoryFacade.getInstance().getValue(yl.getLocation());
		}else if(StringUtils.isNotEmpty(yl.getLocation())&&yl.getLocation().length()==8){
			addr = CategoryFacade.getInstance().getValue(yl.getLocation());
		}
		dto.setAddress(addr);
		if(StringUtils.isNumber(yl.getCategoryYuanliaoCode())&&yl.getCategoryYuanliaoCode().length()>=16){
			dto.setCategoryYuanliaoCodeLabel(YuanliaoFacade.getInstance().getValue(yl.getCategoryYuanliaoCode().substring(0, 16)));
		}else{
			yl.setCategoryYuanliaoCode("");
		}
		dto.setYuanliao(yl);
		Integer postlimittime = DateUtil.getIntervalDays(yl.getExpireTime(),yl.getRefreshTime());
		dto.setPostlimittime(postlimittime);
		if(StringUtils.isNotEmpty(yl.getProcessLevel())&&!"null".equals(yl.getProcessLevel())){
			for(String s : yl.getProcessLevel().split(",")){
				if(StringUtils.isNotEmpty(dto.getProcessLabel())){
					dto.setProcessLabel(dto.getProcessLabel()+","+CategoryFacade.getInstance().getValue(s));
				}else{
					dto.setProcessLabel(CategoryFacade.getInstance().getValue(s));
				}
			}
			
		}
		if(StringUtils.isNotEmpty(yl.getCharaLevel())&&!"null".equals(yl.getCharaLevel())){
			for(String s : yl.getCharaLevel().split(",")){
				if(StringUtils.isNotEmpty(dto.getCharaLabel())){
					dto.setCharaLabel(dto.getCharaLabel() + "," + CategoryFacade.getInstance().getValue(s));
				}else{
					dto.setCharaLabel(CategoryFacade.getInstance().getValue(s));
				}
			}
		}
		if(StringUtils.isNotEmpty(yl.getUsefulLevel())&&!"null".equals(yl.getUsefulLevel())){
			for(String s : yl.getUsefulLevel().split(",")){
				if(StringUtils.isNotEmpty(dto.getUsefulLabel())){
					dto.setUsefulLabel(dto.getUsefulLabel() + "," + CategoryFacade.getInstance().getValue(s));
				}else{
					dto.setUsefulLabel(CategoryFacade.getInstance().getValue(s));
				}
			}
		}
		List<YuanliaoDto> list = new ArrayList<YuanliaoDto>();
		list.add(dto);
		PageDto<YuanliaoDto> page=new PageDto<YuanliaoDto>();
		page.setRecords(list);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView editYuanliao(Map<String,Object> out,HttpServletRequest request,Integer postlimittime,Yuanliao yuanliao) throws Exception{
		ExtResult result=new ExtResult();
		Yuanliao yl = yuanLiaoService.queryYuanliaoById(yuanliao.getId());
		Integer day = DateUtil.getIntervalDays(yl.getExpireTime(),yl.getRefreshTime());
		if(postlimittime!=day){
			yuanliao.setExpireTime(DateUtil.getDateAfterDays(yl.getRefreshTime(), postlimittime));
		}
		if(yuanliao.getMinPrice()!=null){
			if(0==yuanliao.getMinPrice()||0==yuanliao.getMaxPrice()){
				yuanliao.setMinPrice(null);
				yuanliao.setMaxPrice(null);
			}
		}
		Integer i = yuanLiaoService.updateYuanliao(yuanliao);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView listYuanLiaoPic(Integer yuanliaoId,Map<String, Object> out) throws IOException {
		PageDto<YuanliaoPic> page = new PageDto<YuanliaoPic>();
		YuanliaoPic pic = new YuanliaoPic();
		pic.setYuanliaoId(yuanliaoId);
		List<YuanliaoPic> list = yuanliaoPicService.queryYuanliaoPicByYuanliaoId(pic, null);
		page.setRecords(list);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView passPic(String ids,Map<String, Object> out,HttpServletRequest request) throws IOException { 
		SessionUser sessionUser = getCachedUser(request);
		ExtResult result=new ExtResult();
		YuanliaoPic pic = new YuanliaoPic();
		pic.setCheckPerson(sessionUser.getAccount());
		pic.setCheckStatus(1);
		pic.setCheckTime(new Date());
		for(String s : ids.split(",")){
			pic.setId(Integer.valueOf(s));
			Integer i = yuanliaoPicService.updateYuanliaoPic(pic);
			if(i > 0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView defaultPic(Integer id,Integer yuanliaoId,Map<String, Object> out) throws IOException { 
		ExtResult result=new ExtResult();
		YuanliaoPic pic = new YuanliaoPic();
		pic.setYuanliaoId(yuanliaoId);
		pic.setIsDefault(0);
		Integer i = yuanliaoPicService.updateYuanliaoPic(pic);
		if(i > 0){
			YuanliaoPic ylPic = new YuanliaoPic();
			ylPic.setId(id);
			ylPic.setIsDefault(1);
			i = yuanliaoPicService.updateYuanliaoPic(ylPic);
		}
		if(i > 0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView refusePic(String ids,String unpassReason,Map<String, Object> out) throws IOException { 
		ExtResult result=new ExtResult();
		YuanliaoPic pic = new YuanliaoPic();
		pic.setCheckStatus(2);
		pic.setUnpassReason(unpassReason);
		for(String s : ids.split(",")){
			pic.setId(Integer.valueOf(s));
			Integer i = yuanliaoPicService.updateYuanliaoPic(pic);
			if(i > 0){
				result.setSuccess(true);
			}
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView refuseAllPic(Integer yuanliaoId,String unpassReason,Map<String, Object> out) throws IOException { 
		ExtResult result=new ExtResult();
		YuanliaoPic pic = new YuanliaoPic();
		pic.setCheckStatus(2);
		pic.setYuanliaoId(yuanliaoId);
		pic.setUnpassReason(unpassReason);
		Integer i = yuanliaoPicService.updateYuanliaoPic(pic);
		if(i > 0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView saveCompanyPrice(Map<String,Object> out,CompanyPriceDO companyPrice,HttpServletRequest request) throws Exception{
		ExtResult result = new ExtResult();
		Yuanliao yuanliao = yuanLiaoService.queryYuanliaoById(companyPrice.getProductId());
		do {
			if (yuanliao == null) {
				break;
			}
			
			//获取对应企业报价的主要类别code
			String categoryLable = "";
			// 获取供求类别对应的企业报价类别
			if(StringUtils.isNotEmpty(yuanliao.getCategoryYuanliaoCode())){
				categoryLable = companyPriceService.getYuanliaoCode(yuanliao.getCategoryYuanliaoCode());
			}else{
				categoryLable = "1004";
			}
			
			// 企业报价推荐 一次即可 不可重复
			CompanyPriceDO pdo = companyPriceService.queryCompanyPriceByProductId(companyPrice.getProductId(),categoryLable);
			if(pdo!=null){
					break;
			}
			companyPrice.setCategoryCompanyPriceCode(categoryLable);
			Company company = companyService.querySimpleCompanyById(yuanliao.getCompanyId());
			if (company == null) {
				break;
			}
			if (StringUtils.isEmpty(company.getAreaCode())){
				break;
			}
			// 地区
			companyPrice.setAreaCode(company.getAreaCode());
			// 公司account
			companyPrice.setAccount(yuanliao.getAccount());
			// 公司Id
			companyPrice.setCompanyId(yuanliao.getCompanyId());
			// 审核状态
			companyPrice.setIsChecked("1");
			//标题
			companyPrice.setTitle(yuanliao.getTitle());
			// 价格单位
			String priceUnit = StringUtils.isNotEmpty(yuanliao.getPriceUnit())?yuanliao.getPriceUnit():"元";
			String quanlityUnit = StringUtils.isNotEmpty(yuanliao.getUnit())?yuanliao.getUnit():"吨";
			companyPrice.setPriceUnit(priceUnit+"/"+quanlityUnit);
			// 供求详细
			companyPrice.setDetails(yuanliao.getDescription());
			// 最小价格
			if (yuanliao.getMinPrice() !=null&&yuanliao.getMinPrice()>0) {
				companyPrice.setMinPrice(yuanliao.getMinPrice()+"");
			}
			// 最大价格
			if (yuanliao.getMaxPrice() != null&&yuanliao.getMaxPrice()>0) {
				companyPrice.setMaxPrice(yuanliao.getMaxPrice()+"");
			}
			//价格
			if(yuanliao.getPrice()!=null && yuanliao.getPrice()>0){
				companyPrice.setPrice(String.valueOf(yuanliao.getPrice()));
			}
			// insert 入 企业报价 company_price表
			Integer i = companyPriceService.insertCompanyPriceByAdmin(companyPrice);
			// 记录日志 推荐供求为企业报价 企业报价通过数 +1
			if(i>0){
				SessionUser sessionUser = getCachedUser(request);
				LogUtil.getInstance().mongo(sessionUser.getAccount(), COMP_PRICE_CHECK_SUCCESS_OPERTION, null, "id:"+i.toString()+",date:"+DateUtil.toString(new Date(), DATE_FORMAT)+",countryCode:"+company.getAreaCode());
				LogUtil.getInstance().log(sessionUser.getAccount(), COMP_PRICE_CHECK_SUCCESS_OPERTION,
						null,"id:"+i.toString()+",date:"+DateUtil.toString(new Date(), DATE_FORMAT)+",countryCode:"+company.getAreaCode());
			}
			result.setSuccess(true);
		} while (false);

		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView refresh(Map<String,Object> out,Integer id) throws IOException{
		ExtResult result = new ExtResult();
		Integer i = yuanLiaoService.updateRefreshTime(id);
		if(i > 0){
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
			result.setData("刷新失败");
		}
		return printJson(result, out);
	}
	
	/**
	 * 列出已上线供求默认页
	 * @author zhujq
	 * @param out
	 * @return
	 */
	@RequestMapping
	public ModelAndView listExportInquiry(Integer companyId ,Map<String, Object> out){
		if(companyId==null){
			companyId = 0;
		}
		out.put("companyId", companyId);
		return new ModelAndView();
	}
	
	/**
	 * 列出已上线供求
	 * @author zhujq
	 * @param request
	 * @param out
	 * @param page
	 * @param search
	 * @param hasMe 是否包含本公司
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView listYuanLiaoExportInquiry(HttpServletRequest request, Map<String, Object> out, PageDto<YuanliaoDto> page,YuanLiaoSearch search)
			throws IOException {
		page.setDir("desc");
		page.setSort("post_time");
		search.setCheckStatus(1);//审核通过的
		search.setIsPause(0);//已发布的
		page = yuanLiaoService.pageYuanliaoListByAdmin(page, search);
		for (YuanliaoDto dto : page.getRecords()) {
			dto.setCountInquiry(yuanLiaoExportInquiryService.countByYuanLiaoId(dto.getYuanliao().getId()));
			List<YuanLiaoExportInquiry> yuanLiaoExportInquiryList =  yuanLiaoExportInquiryService.queryByYuanLiaoId(dto.getYuanliao().getId());
			if(yuanLiaoExportInquiryList.size()>0){
				dto.setGmtInquiryStr(DateUtil.toString(yuanLiaoExportInquiryList.get(yuanLiaoExportInquiryList.size()-1).getGmtCreated(), "yyyy-MM-dd"));
			}
		}
		return printJson(page, out);
	}
	
	/**
	 * 创建讯盘匹配
	 * @author zhujq
	 * @param model
	 * @param id
	 */
	@RequestMapping
	public void createInquiry(Map<String, Object> model, Integer id) {
		Yuanliao yuanliao = yuanLiaoService.queryYuanliaoById(id);
		yuanliao.setDescription(StringUtils.removeHTML(yuanliao.getDescription()));
		model.put("yuanliao", yuanliao);
	}
}
