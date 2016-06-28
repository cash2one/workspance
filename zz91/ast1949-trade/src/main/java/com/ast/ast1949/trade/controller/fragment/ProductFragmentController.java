/**
 * 
 */
package com.ast.ast1949.trade.controller.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.trade.controller.BaseController;

/**
 * 供应信息有关的页面片段接口
 * @author mays (mays@zz91.net)
 *
 * Created by 2011-10-19
 */
@Controller
public class ProductFragmentController extends BaseController {

	@Resource
	private ProductsService productsService;
	
	@RequestMapping
	public ModelAndView pageSimilarProduct(HttpServletRequest request, Map<String, Object> out, 
			Integer pid, String categoryCode, Integer cid) throws IOException{
		List<Integer> list=productsService.queryProductsIdsOfCompany(cid, categoryCode);
		int max=list.size();
		int start=1;
		
		for(int i=1;i<=list.size();i++){
			if(pid.intValue()==list.get(i-1)){
				max=i+8;
				start=i-8;
				break;
			}
		}
		
		if(max>list.size()){
			max=list.size();
		}
		
		if(start<1){
			start=1;
		}
		
		int pre=1,next=1;
		List<Integer> resultList=new ArrayList<Integer>();
		for(int i=start;i<=max;i++){
			resultList.add(list.get(i-1));
			if(pid.intValue()==list.get(i-1)){
				pre=i-2;
				next=i;
			}
		}
		
		if(pre<0){
			pre=0;
		}
		if(next>=list.size()){
			next=list.size()-1;
		}
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list", resultList);
		map.put("start", start);
		map.put("max", max);
		map.put("pre", list.get(pre));
		map.put("next", list.get(next));
		
		return printJson(map, out);
	}
	
	/**
	 * 最新供求信息
	 * @param out
	 * @param mainCode 供求主类别code
	 * @param typeCode 供求信息类型code
	 * @param size
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView newestTrade(Map<String, Object> out,String mainCode, String typeCode, Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50) {
			size=50;
		}
		if (mainCode==null){
			mainCode=null;
		}
		if (typeCode==null) {
			typeCode=null;
		}
		List<ProductsDO> productsList = productsService.queryNewestProducts(mainCode, typeCode, size);
		map.put("productsList", productsList);
		return printJson(map, out);
	}
	
	/**
	 * 最新供求
	 * @param out
	 * @param mainCode 供求主类别
	 * @param typeCode 供求类型
	 * @param size 显示条数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView trade(Map<String, Object> out, String mainCode,String typeCode,Integer size) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (size>50) {
			size=50;
		}
		if (mainCode==null){
			mainCode=null;
		}
		if (typeCode==null){
			typeCode=null;
		}
		List<ProductsDto> list=productsService.queryProductsByMainCode(mainCode, typeCode, size);
		map.put("list", list);
		return printJson(map, out);
	}
}
