package com.zz91.ep.dao.news;

import java.util.List;

import com.zz91.ep.domain.news.NewsCategory;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：资讯信息类别接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-09-03　　　黄怀清　　　　　　　1.0.0　　　　　创建类文件
 */
public interface NewsCategoryDao {
	/**
	 * 函数名称：queryNewsCategoryByCode
	 * 功能描述：根据CODE查询子资讯类别（页面片段缓存）
	 * 输入参数：
	 *        @param code String 资讯类别
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/09/03　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<NewsCategory> querySubNewsCategoryByCode(String code,Integer size);
}
