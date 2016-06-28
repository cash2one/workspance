package com.zz91.ep.admin.service.news.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zz91.ep.admin.dao.news.NewsZhuantiDao;
import com.zz91.ep.admin.dao.sys.ParamDao;
import com.zz91.ep.admin.service.news.NewsZhuantiService;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.domain.news.Zhuanti;
import com.zz91.ep.dto.ExtTreeDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.NewsDto;
import com.zz91.ep.dto.news.ZhuantiDto;
import com.zz91.util.Assert;
import com.zz91.util.cache.CodeCachedUtil;
import com.zz91.util.domain.Param;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;
/** 
 * 专题service
 * @author 黄怀清 
 * @version 创建时间：2012-9-12 
 */
@Transactional
@Service("zhuantiServiceImpl")
public class NewsZhuantiServiceImpl implements NewsZhuantiService{
	@Resource
	private ParamDao paramDao;
	@Resource
	private NewsZhuantiDao zhuantiDao;
	
	@Override
	public List<ExtTreeDto> queryZhuantiCategory(String type) {
		List<Param> cateList = paramDao.queryParamByType(type);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		for (Param param : cateList) {
			ExtTreeDto node = new ExtTreeDto();
			node.setId(String.valueOf(param.getId()));
			node.setLeaf(true);
			node.setText(param.getName());
			node.setData(param.getKey());
			treeList.add(node);
		}
		return treeList;
	}
	
	@Override
	public PageDto<ZhuantiDto> pageZhuanti(Zhuanti zhuanti,
			PageDto<ZhuantiDto> page) {
		
		if(page.getLimit()==null){
			page.setLimit(20);
		}
		if(page.getSort()==null){
			page.setSort("nz.gmt_publish");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
		page.setRecords(buildZhuantiDto(zhuantiDao.queryZhuanti(zhuanti, page)));
		page.setTotals(zhuantiDao.queryZhuantiCount(zhuanti));
		return page;
	}
	
	public List<ZhuantiDto> buildZhuantiDto(List<Zhuanti> list) {
		if(list==null||list.size()==0){
			return null;
		}
		List<ZhuantiDto> listDto=new ArrayList<ZhuantiDto>();
		for(Zhuanti n:list){
			ZhuantiDto dto=new ZhuantiDto();
			dto.setZhuanti(n);
			
			dto.setCategoryName(ParamUtils.getInstance().getValue("zhuanti_category", n.getCategory()));
			listDto.add(dto);
		}
		return listDto;
	}
	
	@Override
	public Integer create(Zhuanti zhuanti) {
		if(StringUtils.isEmpty(zhuanti.getRecommandStatus())){
			zhuanti.setRecommandStatus(NewsZhuantiDao.RECOMMEND_N);
		}
		if(StringUtils.isEmpty(zhuanti.getAttentionStatus())){
			zhuanti.setAttentionStatus(NewsZhuantiDao.ATTENTION_N);
		}
		if(StringUtils.isEmpty(zhuanti.getTags())){
			zhuanti.setTags(null);
		}
		if(zhuanti.getGmtPublish()==null){
			zhuanti.setGmtPublish(new Date());
		}
		
		return zhuantiDao.insert(zhuanti);
	}
	
	@Override
	public Integer update(Zhuanti zhuanti) {
		if(StringUtils.isEmpty(zhuanti.getRecommandStatus())){
			zhuanti.setRecommandStatus(NewsZhuantiDao.RECOMMEND_N);
		}
		if(StringUtils.isEmpty(zhuanti.getAttentionStatus())){
			zhuanti.setAttentionStatus(NewsZhuantiDao.ATTENTION_N);
		}
		if(StringUtils.isEmpty(zhuanti.getTags())){
			zhuanti.setTags(null);
		}
		if(StringUtils.isEmpty(zhuanti.getPhotoPreview())){
			zhuanti.setPhotoPreview(null);
		}
		if(zhuanti.getGmtPublish()==null){
			zhuanti.setGmtPublish(new Date());
		}
		
		return zhuantiDao.update(zhuanti);
	}
	
	@Override
	public Integer recommend(Zhuanti zhuanti) {
		Assert.notNull(zhuanti.getId(), "the id can not be null");
		Assert.notNull(zhuanti.getRecommandStatus(), "the recommend can not be null");
		return zhuantiDao.updateRecommend(zhuanti);
	}
	
	@Override
	public Integer attention(Zhuanti zhuanti) {
		Assert.notNull(zhuanti.getId(), "the id can not be null");
		Assert.notNull(zhuanti.getAttentionStatus(), "the attention can not be null");
		return zhuantiDao.updateAttention(zhuanti);
	}
	
	@Override
	public Zhuanti queryZhuantiDetail(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return zhuantiDao.queryZhuantiDetail(id);
	}
	
	@Override
	public Integer remove(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return zhuantiDao.delete(id);
	}
}
