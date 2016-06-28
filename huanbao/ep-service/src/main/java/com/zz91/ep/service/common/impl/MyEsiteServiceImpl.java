/**
 * @author leiteng
 * @email  lvjavapro@163.com
 * @create_time  2013-4-25 下午04:16:59
 */
package com.zz91.ep.service.common.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.comp.CompNewsDao;
import com.zz91.ep.dao.mblog.MBlogFollowDao;
import com.zz91.ep.dao.mblog.MBlogInfoDao;
import com.zz91.ep.dao.mblog.MBlogSystemDao;
import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.domain.mblog.MBlogInfo;
import com.zz91.ep.service.common.MyEsiteService;
import com.zz91.util.lang.StringUtils;
@Service("MyEsiteService")
public class MyEsiteServiceImpl implements MyEsiteService {
	@Resource
	private CompNewsDao compNewsDao;
	@Resource
	private MBlogSystemDao mBlogSystemDao;
	@Resource
	private MBlogFollowDao mBlogFollowDao;
	@Resource 
	private MBlogInfoDao mBlogInfoDao;
	@Override
	public void init(Map<String, Object> out, Integer cid) {
		Integer count = compNewsDao.queryWtgshCount(cid);
		out.put("count", count);
		
	    List<CompNews> compList=compNewsDao.queryCompNewsByCid(cid, null, null, null, (short)2, null, null);
		for (CompNews compNews : compList) {
			if(compNews.getCategoryCode().equals("1000")){
				 out.put("type0", compNews.getCategoryCode());
			}
			if(compNews.getCategoryCode().equals("1001")){
				out.put("type1", compNews.getCategoryCode());
			}
			if(compNews.getCategoryCode().equals("1002")){
				out.put("type2", compNews.getCategoryCode());
			}
		}
	   //查询出
		MBlogInfo info =mBlogInfoDao.queryInfoByCid(cid);
		
		if(info!=null && StringUtils.isNotEmpty(info.getName()) && info.getIsDelete().equals("0")){
			// 查询出统计没有读的回复和评论
			Integer commentCount = mBlogSystemDao.queryCountById(info.getId(),
					"3", "0");
			out.put("commentCount", commentCount);
			//查询出新粉丝的count
			out.put("newFansCount", mBlogFollowDao.queryFansCountByConditions(
					info.getId(), "0"));
			//读取出有人@我的人
			Integer anteCount = mBlogSystemDao.queryCountById(info.getId(), "2", "0");
			out.put("anteCount", anteCount);
			//读取出系统消息
			Integer messageCount = mBlogSystemDao.queryCountById(info.getId(), "4", "0");
			out.put("messageCount", messageCount);

		}
		
	}

}
