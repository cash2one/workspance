/*
 * 文件名称：SearchPropertyDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.search;

import java.io.Serializable;

/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：搜索专业属性。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class SearchPropertyDto implements Serializable {

	private static final long serialVersionUID = 1L;

	// 属性ID
    private Integer id;

    // 属性名称
    private String name;

    // 相应搜索值
    private String[] searchValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String[] searchValue) {
        this.searchValue = searchValue;
    }
}