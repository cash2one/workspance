/**
 * @author leiteng
 * @email  lvjavapro@163.com
 * @create_time  2013-4-10 上午11:17:32
 */
package com.ast.ast1949.photos.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.CategoryProductsDTO;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.products.CategoryProductsService;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.seo.SeoUtil;

@Controller
public class IndexController extends BaseController {
	@Resource
	private ProductsService productsService;
	@Resource
	private ProductsPicService productsPicService;
	@Resource
	private CategoryProductsService categoryProductsService;
	@Resource
	private CompanyService companyService;
	@RequestMapping
	public ModelAndView index(Map<String, Object> out) {

		// 查询金属类别的图片
		// 金属和塑料的固定图片用productsService中的page搜索引擎
		// 搜索栏也使用搜索引擎
		// 推荐的图片使用trade.zz91.com右边的那种实现方式

		// 搜索的金属的图片
		ProductsDO productsDO = new ProductsDO();
		productsDO.setTitle("金属");
		List<ProductsDto> list = productsService.querypicByKeyWord(productsDO,true, 10);
		out.put("jsList", list);

		// 搜索塑料的图片
		productsDO.setTitle("塑料");
		List<ProductsDto> slList = productsService.querypicByKeyWord(productsDO, true, 10);
		out.put("slList", slList);
		
		// 搜索的金属的图片
		productsDO.setTitle("电子电器");
		List<ProductsDto> dzList = productsService.querypicByKeyWord(productsDO,true, 10);
		out.put("dzList", dzList);

		// 搜索塑料的图片
		productsDO.setTitle("橡胶");
		List<ProductsDto> xjList = productsService.querypicByKeyWord(productsDO, true, 10);
		out.put("xjList", xjList);
		
		// 搜索的金属的图片
		productsDO.setTitle("二手设备");
		List<ProductsDto> esList = productsService.querypicByKeyWord(productsDO,true, 10);
		out.put("esList", esList);

		// 搜索塑料的图片
		productsDO.setTitle("废纸");
		List<ProductsDto> fzList = productsService.querypicByKeyWord(productsDO, true, 10);
		out.put("fzList", fzList);
		
		SeoUtil.getInstance().buildSeo("index", out);
		return new ModelAndView();
	}

	// 搜索栏的方法搜索到数据后进入fzs页面,并且列出相关类别的类别名称.当搜索不到数据时转向empty.vm
	// 废金属和塑料点击更多按钮也可以使用该方法.
	@RequestMapping
	public ModelAndView searchPic( Map<String, Object> out, HttpServletRequest request) throws Exception {
		String searchKey=request.getParameter("searchKey");
		String mainCode=request.getParameter("mainCode");
		PageDto<ProductsDto> page=new PageDto<ProductsDto>();
		String startIndexString=request.getParameter("startIndex");
		if(StringUtils.isNotEmpty(startIndexString)){
			Integer startIndex=Integer.valueOf(startIndexString);
			page.setStartIndex(startIndex);
		}
		// s代表是否搜索出数据
		// 根据搜索兰提交的关键字搜索数据
		ProductsDO products = new ProductsDO();
		page.setPageSize(16);
		if(!StringUtils.isContainCNChar(searchKey)){
			searchKey = StringUtils.decryptUrlParameter(searchKey);
		}

		products.setTitle(searchKey);
		page = productsService.pageProductsBySearchEngine(products, "", true,page);
		out.put("page", page);
		out.put("searchKey", searchKey);

		List<ProductsDto> list = page.getRecords();
		out.put("listall", list);
		//page.setRecords(list);
		List<ProductsDto> listOne =new ArrayList<ProductsDto>();
		List<ProductsDto> listTwo =new ArrayList<ProductsDto>();
		List<ProductsDto> listThree =new ArrayList<ProductsDto>();
		List<ProductsDto> listFour =new ArrayList<ProductsDto>();
		//相关分类类别的组建
		Map<String, String> relativeCategories = new HashMap<String, String>();
		//to do 等接口写好调用
		PageDto<CategoryProductsDTO> caDto = new PageDto<CategoryProductsDTO>();
		// 相关类别
		caDto.setPageSize(15);
		caDto = categoryProductsService.pageMoreOneCategoryProductsBySearchEngine(searchKey, caDto);
		if (caDto!=null) {
			List<CategoryProductsDTO> categoryList = caDto.getRecords();
			//判断是否为空
			if (categoryList.size()>0) {
				for (CategoryProductsDTO categoryProductsDTO : categoryList) {
					String label =categoryProductsDTO.getCategoryProductsDO().getLabel();
					if (StringUtils.isNotEmpty(label)) {
						relativeCategories.put(label, URLEncoder.encode(label.replace("/", "-"), HttpUtils.CHARSET_UTF8));
					}
				}
				out.put("relativeCategories", relativeCategories);
			}
			
		}
		
		if (list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < 4) {
					listOne.add(list.get(i));
				}
				if (i >= 4 && i < 8) {
					listTwo.add(list.get(i));
				}
				if (i >= 8 && i < 12) {
					listThree.add(list.get(i));
				}
				if (i >= 12 && i < 16) {
					listFour.add(list.get(i));
				}
			}
			out.put("listOne", listOne);
			out.put("listTwo", listTwo);
			out.put("listThree", listThree);
			out.put("listFour", listFour);
			// seo 
			SeoUtil.getInstance().buildSeo("search", new String[]{searchKey}, new String[]{searchKey}, new String[]{searchKey}, out);
			return new ModelAndView("searchPic");
		} else {
			SeoUtil.getInstance().buildSeo("empty", new String[]{searchKey}, new String[]{searchKey}, new String[]{searchKey}, out);
			return new ModelAndView("empty");
		}
	}

	// 点击更多按钮时进入的处理方法
	// 根据id产询图片的具体的信息
	@RequestMapping
	public ModelAndView queryPicInfoById(Integer productId,Map<String, Object> out, String string) throws Exception {
		out.put("productId", productId);
		// 根据id查询出productsPicDTO
		ProductsDO productsDO = productsService.queryProductsById(productId);
		// 根据maincode查询出当前位置
		if (productsDO != null) {
			List<String> categoryList = queryCategory(productsDO.getCategoryProductsMainCode());
			Map<String, String> map = new HashMap<String, String>();
			out.put("categoryList", categoryList);
			if (categoryList.size() > 0) {
				for (String stringUrl : categoryList) {
					map.put(stringUrl, URLEncoder.encode(stringUrl,HttpUtils.CHARSET_UTF8));
				}
				out.put("map", map);
				out.put("last", categoryList.get(categoryList.size() - 1));
			}
			// 这是相关类别也是最后一个显示的类别,根据该类别用搜索引擎搜索出相关数据
			String category = CategoryProductsFacade.getInstance().getValue(
					productsDO.getCategoryProductsMainCode());
			out.put("category", category);
			ProductsDO productsDO2 = new ProductsDO();
			productsDO2.setTitle(category);
			List<ProductsDto> relateList = productsService.querypicByKeyWord(productsDO2, true, 7);

			// 添加
			out.put("relateList", relateList);
			// 获取发布products的公司名
			Integer companyId = productsDO.getCompanyId();
			Company company = companyService.queryCompanyById(companyId);
			out.put("company", company);
		}

		// if (StringUtils.isNotEmpty(companyname)) {
		// companyname =new String(companyname.getBytes("iso-8859-1"),"UTF-8");
		// out.put("companyname", companyname);
		// }

		// 根据products的id查询多张属于该id下面的图
		// 根据此id查询
		List<ProductsPicDO> productsPicDOList = productsPicService.queryProductPicInfoByProductsId(productId);
		List<ProductsPicDO> resultList = new ArrayList<ProductsPicDO>();
		// 判断选出是封面的图片
		if (productsPicDOList.size() > 0) {

			for (ProductsPicDO productsPicDO : productsPicDOList) {
				String iscover = productsPicDO.getIsCover();
				if (StringUtils.isNotEmpty(iscover)) {
					if (!iscover.equals("0")) {
						out.put("picUrl", productsPicDO.getPicAddress());
					}
				}
				if(StringUtils.isNotEmpty(productsPicDO.getCheckStatus())&&"1".equals(productsPicDO.getCheckStatus())){
					resultList.add(productsPicDO);
				}
			}
			if (StringUtils.isEmpty((String) out.get("picUrl"))) {
				out.put("picUrl", productsPicDOList.get(0).getPicAddress());
			}
		}

		// for (ProductsPicDO productsPicDO : productsPicDOList) {
		// if (productsPicDO.getPicAddress().equals(picUrl)) {
		// productsPicDOList.remove(productsDO);
		// }
		// }

		// out.put("picUrl", picUrl);
		out.put("productsPicDOList", resultList);
		out.put("products", productsDO);
		
		// seo
		String seoKey ="";
		String title = "";
		do {
			if(productsDO==null){
				break;
			}
			title = productsDO.getTitle();
			seoKey = productsDO.getTitle();
			if(ProductsService.PRODUCTS_TYPE_OFFER.equals(productsDO.getProductsTypeCode())){
				seoKey = "供应" + seoKey;
			}
			if(ProductsService.PRODUCTS_TYPE_BUY.equals(productsDO.getProductsTypeCode())){
				seoKey = "求购" + seoKey;
			}
		} while (false);
		out.put("seoKey", seoKey);
		
		SeoUtil.getInstance().buildSeo("detail", new String[]{seoKey}, new String[]{seoKey}, new String[]{title}, out);
		return new ModelAndView("queryPicInfoById");

	}

	public static List<String> queryCategory(String mainCode) {
		List<String> list = new ArrayList<String>();
		Integer sizInteger = mainCode.length() / 4;
		for (int i = 1; i <= sizInteger; i++) {
			list.add(CategoryProductsFacade.getInstance().getValue(
					mainCode.substring(0, 4 * i)));

		}
		return list;
	}

	public static void main(String[] args) {
		// List<Integer> list = new ArrayList<Integer>();
		// for (int i = 0; i <= 15; i++) {
		// list.add(i);
		// }
		// List<Integer> list1 =list.subList(0, 4);
		// List<Integer> list2 = list.subList(4, 8);
		// List<Integer> list3 = list.subList(8, 12);
		// List<Integer> list4 = list.subList(12, 16);
		// // list4.add(list.get(15));
		// for (Integer integer : list1) {
		// System.out.println("111111111111111========="+integer);
		//			
		//			
		// }
		// for (Integer integer : list2) {
		// System.out.println(integer);
		// }
		// for (Integer integer : list3) {
		// System.out.println("========="+integer);
		//			
		//			
		// }
		// for (Integer integer : list4) {
		// System.out.println(integer);
		// }

		List<String> list6 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		List<String> list3 = new ArrayList<String>();
		List<String> list4 = new ArrayList<String>();
		List<String> list5 = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			list5.add("" + i);
		}
		for (int i = 0; i < list5.size(); i++) {
			if (i < 4) {
				list2.add("" + i);
			}
			if (i >= 4 && i < 8) {
				list3.add("" + i);
			}
			if (i >= 8 && i < 12) {
				list4.add("" + i);
			}
			if (i >= 12 && i < 16) {
				list6.add("" + i);
			}
		}
		for (String string : list2) {
			System.out.println("-------------->" + string);
		}
		for (String string : list3) {
			System.out.println(string);
		}
		for (String string : list4) {

			System.out.println(">>>>>>>" + string);
		}
		for (String string : list6) {

			System.out.println(string);
		}
		//velocity上下文使用操作
		String conString = "#foreach($key in ${out.entrySet()})"+"name=${key}"+"url--------=$!{key.getValue()}"+"#end";
		Map<String,Object> outmapMap = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "leiteng");
		map.put("2", "yuyan");
		map.put("3", "pingwei");
		map.put("2", "haiguo");
		outmapMap.put("out", map);
		StringWriter w = new StringWriter();
		VelocityContext context = new VelocityContext(outmapMap);
		try {
			Velocity.evaluate(context, w, "", conString);
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(w.toString());
	}

	/**
	 * 构建search_label字段数据 使用1次即可
	 * 
	 * @param out
	 * @return
	 */
	@Deprecated
	@RequestMapping
	public ModelAndView buildAllSearchLabel(Map<String, Object> out) {
//		categoryProductsService.buildAllSearchLabel();
		return new ModelAndView("index");
	}

}
