package com.ast.ast1949.service.yuanliao;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.yuanliao.CategoryYuanliao;

/**
 * @date 2015-08-21
 * @author asto
 */
public interface CategoryYuanliaoService {
	
	public Integer insertCategoryYuanliao(CategoryYuanliao categoryYuanliao);
	
	public CategoryYuanliao queryCategoryYuanliaoByCode(String code);
	
	public List<CategoryYuanliao> queryCategoryYuanliaoByParentCode(String parentCode);
	
	public Integer updateCategoryYuanliao(CategoryYuanliao categoryYuanliao);
	
	public List<CategoryYuanliao> queryAllCategoryYuanliao();
	
	public List<Map<String, Object>> queryCategoryYuanliaoByKeyword(String keyword);
	
	public List<CategoryYuanliao> querySimilarCategory(String keyword);

}
