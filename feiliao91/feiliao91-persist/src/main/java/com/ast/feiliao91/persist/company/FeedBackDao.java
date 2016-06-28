package com.ast.feiliao91.persist.company;

import com.ast.feiliao91.domain.company.FeedBack;

public interface FeedBackDao {
	/**
	 * 插入信息
	 * @param feedback表
	 * @return
	 */
    public Integer insert (FeedBack feedback);
    /**
     * 根据ID查询
     */
    public FeedBack selectById(Integer id);
}
