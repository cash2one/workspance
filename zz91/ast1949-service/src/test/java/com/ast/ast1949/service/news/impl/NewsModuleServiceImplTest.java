/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-3-21.
 */
package com.ast.ast1949.service.news.impl;

import com.ast.ast1949.service.BaseServiceTestCase;


/**
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
public class NewsModuleServiceImplTest extends BaseServiceTestCase {

//	@Autowired
//	private CategoryService categoryService;
//	
//	@Autowired
//	private NewsModuleService newsModuleService;
//	
//	
//	public void test_init(){
//		clean();
//		created("1026", 0);
//	}
//
//	public void created(String code,Integer parentId){
//		List<CategoryDO> list = categoryService.queryCategoriesByPreCode(code);
//		for (CategoryDO category : list) {
//			NewsModuleDO newsModule = new NewsModuleDO();
//			newsModule.setChecked("1");
//			newsModule.setName(category.getLabel());
//			newsModule.setParentId(parentId);
//			Integer id = newsModuleService.insertNewsModule(newsModule);
//			if(id.intValue()>0){
//				created(category.getCode(),id);
//			}
//		}
//	}
//
//	/**
//	 * 清除表中的数据
//	 */
//	protected void clean(){
//		try {
//			connection.prepareStatement("delete from news_module").execute();
//		} catch (SQLException e) {}
//	}
}
