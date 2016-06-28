/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-2
 */
package com.ast.ast1949.service.products.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.CategoryProductsDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.CategoryProductsDTO;
import com.ast.ast1949.persist.products.CategoryProductsDAO;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.service.products.CategoryProductsService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.GB2Alpha;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

/**
 * @author yuyonghui
 * 
 */
@Component("categoryProductsService")
public class CategoryProductsServiceImpl implements CategoryProductsService {

	@Autowired
	private CategoryProductsDAO categoryProductsDAO;

	public List<CategoryProductsDO> queryAllCategoryProducts() {
		return categoryProductsDAO.queryAllCategoryProducts();
	}

	public List<CategoryProductsDO> queryCategoryProductsByCode(String code,
			String isAssist) {
		return categoryProductsDAO.queryCategoryProductsByCode(code, isAssist);
	}

	// public List<CategoryProductsDO> queryCategoryProductsByCondition(
	// CategoryProductsDTO categoryProductsDTO) {
	// Assert.notNull(categoryProductsDTO, "categoryProductsDTO is not null");
	// return
	// categoryProductsDAO.queryCategoryProductsByCondition(categoryProductsDTO);
	// }

	public CategoryProductsDO queryCategoryProductsById(int id) {
		Assert.notNull(id, "id is not null");
		return categoryProductsDAO.queryCategoryProductsById(id);
	}

	@Override
	public CategoryProductsDO queryCategoryProductsByLabel(String label) {
		Assert.notNull(label, "lable is not null");
		return categoryProductsDAO.queryCategoryProductsByLabel(label);
	}

	@Override
	public List<Map<String, Object>> queryAllCategoryProductsByLabel(
			String label) {
		Assert.notNull(label, "lable is not null");
		List<Map<String, Object>> listMapS = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listMapE = new ArrayList<Map<String, Object>>();
		List<CategoryProductsDO> list = categoryProductsDAO.queryAllCategoryProductsByLabel(label);
		for (CategoryProductsDO obj : list) {
			//四级目录
			do{
				if (obj.getCode().length() == 16) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("code", obj.getCode());
					map.put("name", CategoryProductsFacade.getInstance().getValue(
							obj.getCode()));
					map.put("fName", CategoryProductsFacade.getInstance().getValue(
							obj.getCode().substring(0, 4)));
					map.put("sName", CategoryProductsFacade.getInstance().getValue(
							obj.getCode().substring(0, 8)));
					map.put("tName", CategoryProductsFacade.getInstance().getValue(
							obj.getCode().substring(0, 12)));
					map.put("foName", CategoryProductsFacade.getInstance().getValue(
							obj.getCode().substring(0, 16)));
					if(map.get("name")==null ||map.get("name").equals("")){
						break;
					}
					listMapS.add(map);
				}
			}while(false);
			
			// 三级类目统计
			if (obj.getCode().length() == 12) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("code", obj.getCode());
				map.put("name", CategoryProductsFacade.getInstance().getValue(
						obj.getCode()));
				map.put("fName", CategoryProductsFacade.getInstance().getValue(
						obj.getCode().substring(0, 4)));
				map.put("sName", CategoryProductsFacade.getInstance().getValue(
						obj.getCode().substring(0, 8)));
				listMapS.add(map);
			}
			// 二级类目统计
			if (obj.getCode().length() == 8) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("code", obj.getCode());
				map.put("name", CategoryProductsFacade.getInstance().getValue(
						obj.getCode()));
				map.put("fName", CategoryProductsFacade.getInstance().getValue(
						obj.getCode().substring(0, 4)));
				listMapE.add(map);
			}
		}
		if (listMapS.size() > 0) {
			return listMapS;
		} else if (listMapE.size() > 0) {
			return listMapE;
		} else {
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> queryHistoryCategoryByCompanyId(
			Integer companyId) {
		Assert.notNull(companyId, "companyId is not null");
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		List<CategoryProductsDO> list = categoryProductsDAO
				.queryHistoryCategoryByCompanyId(companyId);
		for (CategoryProductsDO obj : list) {
			// 三级类目统计
			if (obj.getCode().length() == 12) {
				Map<String, Object> map = new HashMap<String, Object>();
				buildMap(map, obj.getCode(), CategoryProductsFacade
						.getInstance().getValue(obj.getCode()),
						CategoryProductsFacade.getInstance().getValue(
								obj.getCode().substring(0, 8)),
						CategoryProductsFacade.getInstance().getValue(
								obj.getCode().substring(0, 4)));
				listMap.add(map);
			}
			// 二级类目统计
			if (obj.getCode().length() == 8) {
				Map<String, Object> map = new HashMap<String, Object>();
				buildMap(map, obj.getCode(), CategoryProductsFacade
						.getInstance().getValue(obj.getCode()), null,
						CategoryProductsFacade.getInstance().getValue(
								obj.getCode().substring(0, 4)));
				listMap.add(map);
			}
			// 一级类目统计
			if (obj.getCode().length() == 4) {
				Map<String, Object> map = new HashMap<String, Object>();
				buildMap(map, obj.getCode(), CategoryProductsFacade
						.getInstance().getValue(obj.getCode()), null, null);
				listMap.add(map);
			}
		}
		if (listMap.size() > 0) {
			return listMap;
		} else {
			return null;
		}
	}

	private void buildMap(Map<String, Object> out, String code, String name,
			String sName, String fName) {
		if (com.zz91.util.lang.StringUtils.isNotEmpty(code)) {
			out.put("code", code);
		}
		if (com.zz91.util.lang.StringUtils.isNotEmpty(name)) {
			out.put("name", name);
		}
		if (com.zz91.util.lang.StringUtils.isNotEmpty(sName)) {
			out.put("sName", sName);
		}
		if (com.zz91.util.lang.StringUtils.isNotEmpty(fName)) {
			out.put("fName", fName);
		}
	}

	public String queryMaxCodeBypreCode(String preCode) {
		String code = categoryProductsDAO.queryMaxCodeBypreCode(preCode);
		if (code == null || code.length() == 0) {
			if (preCode == null) {
				preCode = "";
			}
			code = preCode + "1000";
		}// Code值的长度为四时10000001+1
		else if (code.length() == 4) {
			code = String.valueOf(Integer.valueOf(code) + 1);
		} else if (code.length() == 8) {
			code = String.valueOf(Integer.valueOf(code) + 1);
		}
		// string类型 存值有限 必须 先 截取后四位+1 然后赋值
		else {
			String code3 = code.substring(code.length() - 4, code.length());
			String code4 = code.substring(0, code.length() - 4);
			code = code4 + String.valueOf(Integer.valueOf(code3) + 1);
		}
		return code;
		// return ;
	}

	// public int deleteCategoryProductsById(CategoryProductsDO
	// categoryProductsDO) {
	// Assert.notNull(categoryProductsDO, "categoryProductsDO is not null");
	// return
	// categoryProductsDAO.deleteCategoryProductsById(categoryProductsDO);
	// }

	public int insertCategoryProducts(CategoryProductsDO categoryProductsDO,
			String preCode) {
		Assert.notNull(categoryProductsDO, "categoryProductsDO is not null");
		String code = queryMaxCodeBypreCode(preCode);
		categoryProductsDO.setCode(code);
		if (StringUtils.isEmpty(categoryProductsDO.getCnspell())) {
			GB2Alpha gbAlpha = new GB2Alpha();
			categoryProductsDO.setCnspell(gbAlpha
					.String2Alpha(categoryProductsDO.getLabel()));
		}
		return categoryProductsDAO.insertCategoryProducts(categoryProductsDO);
	}

	public Integer updatecategoryProducts(CategoryProductsDO categoryProductsDO) {
		Assert.notNull(categoryProductsDO, "categoryProductsDO is not null");
		if (StringUtils.isEmpty(categoryProductsDO.getCnspell())) {
			GB2Alpha gbAlpha = new GB2Alpha();
			categoryProductsDO.setCnspell(gbAlpha
					.String2Alpha(categoryProductsDO.getLabel()));
		}
		return categoryProductsDAO.updatecategoryProducts(categoryProductsDO);
	}

	public List<ExtTreeDto> child(String code, String isAssist) {

		List<CategoryProductsDO> categoryProductsDOs = categoryProductsDAO
				.queryCategoryProductsByCode(code, isAssist);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		for (CategoryProductsDO m : categoryProductsDOs) {
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-" + String.valueOf(m.getId()));
			node.setLeaf(false);
			node.setText(m.getLabel());
			node.setData(m.getCode());
			treeList.add(node);
		}
		return treeList;
	}

	// public CategoryProductsDO queryCategoryNameByCode(String code) {
	//
	// return categoryProductsDAO.queryCategoryNameByCode(code);
	// }

	// public List<CategoryProductsDO> queryCategoryProductsFront() {
	// return categoryProductsDAO.queryCategoryProductsFront();
	// }

	public Integer deleteCategoryProductsAndChildById(Integer id) {
		Assert.notNull(id, "parent code can not be null");
		CategoryProductsDO c = categoryProductsDAO
				.queryCategoryProductsById(id);
		if (c == null) {
			return 0;
		}
		return categoryProductsDAO.deleteCategoryProductsAndChild(c.getCode());
	}

	public List<CategoryProductsDO> queryCategoryProductsByCnspell(String pingy) {
		if (StringUtils.isEmpty(pingy)) {
			pingy = DEFAULT_PINGY;
		}
		return categoryProductsDAO.queryCategoryProductsByCnspell(pingy);
	}

	@Override
	public List<CategoryProductsDO> queryCategoryByTags(String keywords,
			Integer size) {
		if (size == null) {
			size = 25;
		}
		return categoryProductsDAO.queryCategoryByTags(keywords, size);
	}

	@Override
	public CategoryProductsDO queryCategoryProductsByKey(String label) {
		Assert.notNull(label, "lable is not null");
		return categoryProductsDAO.queryCategoryProductsByKey(label);
	}

	@Override
	public String queryNameByCode(String code) {
		return categoryProductsDAO.queryNameByCode(code);
	}
	
	@Override
	public void buildAllSearchLabel(){
		List<CategoryProductsDO> list = categoryProductsDAO.queryAllCategoryProducts();
		for(CategoryProductsDO obj :list){
			String code = obj.getCode();
			if(StringUtils.isNotEmpty(code)){
				String searchLabel ="";
				while (true){
					searchLabel = CategoryProductsFacade.getInstance().getValue(code)+ "--" + searchLabel;
					if(code.length()<=4){
						break;
					}
					code = code.substring(0, code.length()-4);
				}
				categoryProductsDAO.updateSearchLabelById(obj.getId(),searchLabel);
			}
		}
	}
	
	@Override
	public PageDto<CategoryProductsDTO> pageMoreOneCategoryProductsBySearchEngine(String title,PageDto<CategoryProductsDTO> page){
		page = pageCategoryProductsBySearchEngine(title,page);
		List<CategoryProductsDTO> categoryList = page.getRecords(); 
		// 类别数为一个的时候 重新读取父类别 以父类别中文作为关键字搜索 搜索引擎
		PageDto<CategoryProductsDTO> npage = new PageDto<CategoryProductsDTO>();
		if(categoryList.size()==1){
			String code = categoryList.get(0).getCategoryProductsDO().getCode();
			String key = CategoryProductsFacade.getInstance().getValue(code.substring(0, code.length()-4));
			npage = pageCategoryProductsBySearchEngine(key, page);
		}else{
			npage = page;
		}
		return npage;
	}
	
	@Override
	public PageDto<CategoryProductsDTO> pageCategoryProductsBySearchEngine(String title,PageDto<CategoryProductsDTO> page) {
		if(page.getPageSize()==null){
			page.setPageSize(10);
		}
		
		if(page.getStartIndex()!=null && page.getStartIndex()>=7500){
			page.setStartIndex(7500);
		}
		StringBuffer sb=new StringBuffer();
		SphinxClient cl=SearchEngineUtils.getInstance().getClient();
		
		List<CategoryProductsDTO> list=new ArrayList<CategoryProductsDTO>();
		try {
			if(StringUtils.isNotEmpty(title)){
				sb.append("@(label,search_label) ").append(title);
			}
			
			cl.SetMatchMode (SphinxClient.SPH_MATCH_BOOLEAN);
			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 10000);
			cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "id desc");
			
			SphinxResult res=cl.Query(sb.toString(), "category_products_new");
			
			if(res==null){
				page.setTotalRecords(0);
			}else{
				page.setTotalRecords(res.totalFound);
				for ( int i=0; i<res.matches.length; i++ ){
					SphinxMatch info = res.matches[i];
//					Integer id = Integer.valueOf(""+info.docId);
					CategoryProductsDTO dto = new CategoryProductsDTO();
					CategoryProductsDO obj = new CategoryProductsDO();
					obj.setLabel(String.valueOf(info.attrValues.get(1)));
					obj.setCode(String.valueOf(info.attrValues.get(0)));
					dto.setCategoryProductsDO(obj);
					list.add(dto);
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}
		
		return page;
	}

}
