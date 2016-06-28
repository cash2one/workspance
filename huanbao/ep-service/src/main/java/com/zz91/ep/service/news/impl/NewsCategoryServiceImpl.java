package com.zz91.ep.service.news.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.news.NewsCategoryDao;
import com.zz91.ep.domain.news.NewsCategory;
import com.zz91.ep.service.news.NewsCategoryService;
import com.zz91.util.Assert;
/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：资讯类别实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-09-03　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("newsCategoryServiceImpl")
public class NewsCategoryServiceImpl implements NewsCategoryService {
	
	@Resource
	private NewsCategoryDao newsCategoryDao;
	
	@Override
	public List<NewsCategory> querySubNewsCategoryByCode(String code,Integer size) {
		Assert.notNull(code, "the code can not be null");
		if(size==null){
			size=3;
		}
		return newsCategoryDao.querySubNewsCategoryByCode(code,size);
	}

}
