package com.ast.ast1949.persist.yuanliao;

import java.util.List;

import com.ast.ast1949.domain.yuanliao.CategoryYuanliao;

/**
 * @date 2015-08-21
 * @author shiqp
 */
public interface CategoryYuanliaoDao {
	
	public Integer insertCategoryYuanliao(CategoryYuanliao categoryYuanliao);
	
	public CategoryYuanliao queryCategoryYuanliaoByCode(String code);
	
	public List<CategoryYuanliao> queryCategoryYuanliaoByParentCode(String parentCode);
	
	public Integer updateCategoryYuanliao(CategoryYuanliao categoryYuanliao);
	
	public List<CategoryYuanliao> queryAllCategoryYuanliao();
	
	public List<CategoryYuanliao> queryCategoryYuanliaoByKeyword(String keyword);
	
	public String queryMaxCategoryYuanLiao(String parentCode);
	
	public Integer queryPinyin(String pinyin);
	
	public List<CategoryYuanliao> querySimilarCategory(String keyword);

}
