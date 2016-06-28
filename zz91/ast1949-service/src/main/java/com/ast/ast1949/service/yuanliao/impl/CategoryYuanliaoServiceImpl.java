package com.ast.ast1949.service.yuanliao.impl;
/**
 * @date 2015-08-21
 * @author shiqp
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.yuanliao.CategoryYuanliao;
import com.ast.ast1949.persist.yuanliao.CategoryYuanliaoDao;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.yuanliao.CategoryYuanliaoService;
@Component("categoryYuanliaoService")
public class CategoryYuanliaoServiceImpl implements CategoryYuanliaoService {
	@Resource
	private CategoryYuanliaoDao categoryYuanliaoDao;

	@Override
	public Integer insertCategoryYuanliao(CategoryYuanliao categoryYuanliao) {
		String code = categoryYuanliaoDao.queryMaxCategoryYuanLiao(categoryYuanliao.getParentCode());
		if(code==null||code.length()==0){
			code = categoryYuanliao.getParentCode() + "1000";
		}else{
			String code3 = code.substring(code.length() - 4, code.length());
			String code4 = code.substring(0, code.length() - 4);
			code = code4 + String.valueOf(Integer.valueOf(code3) + 1);
		}
		categoryYuanliao.setCode(code);
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
		return categoryYuanliaoDao.insertCategoryYuanliao(categoryYuanliao);
	}

	@Override
	public CategoryYuanliao queryCategoryYuanliaoByCode(String code) {
		return categoryYuanliaoDao.queryCategoryYuanliaoByCode(code);
	}

	@Override
	public List<CategoryYuanliao> queryCategoryYuanliaoByParentCode(String parentCode) {
		return categoryYuanliaoDao.queryCategoryYuanliaoByParentCode(parentCode);
	}

	@Override
	public Integer updateCategoryYuanliao(CategoryYuanliao categoryYuanliao) {
		return categoryYuanliaoDao.updateCategoryYuanliao(categoryYuanliao);
	}

	@Override
	public List<CategoryYuanliao> queryAllCategoryYuanliao() {
		return categoryYuanliaoDao.queryAllCategoryYuanliao();
	}

	@Override
	public List<Map<String, Object>> queryCategoryYuanliaoByKeyword(String keyword) {
		List<CategoryYuanliao> list = categoryYuanliaoDao.queryCategoryYuanliaoByKeyword(keyword);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for(CategoryYuanliao sy : list){
			if(sy.getCode().length()==16){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("first", YuanliaoFacade.getInstance().getValue(sy.getCode().substring(0, 8)));
				map.put("second", YuanliaoFacade.getInstance().getValue(sy.getCode().substring(0, 12)));
				map.put("third", YuanliaoFacade.getInstance().getValue(sy.getCode()));
				map.put("code", sy.getCode());
				listMap.add(map);
			}
			if(listMap.size()==5){
				break;
			}
		}
		return listMap;
	}

	@Override
	public List<CategoryYuanliao> querySimilarCategory(String keyword) {
		return categoryYuanliaoDao.querySimilarCategory(keyword);
	}

}
