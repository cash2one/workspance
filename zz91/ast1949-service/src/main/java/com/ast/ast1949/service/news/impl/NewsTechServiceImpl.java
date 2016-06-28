package com.ast.ast1949.service.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.news.NewsTech;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.news.NewsTechDTO;
import com.ast.ast1949.persist.news.NewsTechDao;
import com.ast.ast1949.service.news.NewsTechService;
import com.zz91.util.Assert;
import com.zz91.util.lang.StringUtils;

@Component("newsTechService")
public class NewsTechServiceImpl implements NewsTechService {
	
	@Resource
	private NewsTechDao newsTechDao; 
	
	@Override
	public Integer createOneTech(NewsTech newsTech) {
		newsTech.setIsDel("0");
		newsTech.setViewCount(0);
		return newsTechDao.insert(newsTech);
	}

	@Override
	public PageDto<NewsTech> pageTech(NewsTechDTO newsTechDTO,
			PageDto<NewsTech> page) {
		List<NewsTech> list=newsTechDao.queryByCondition(newsTechDTO, page);
		if(list.size()>0){
			for(NewsTech tech:list){
				String str=tech.getContent();
				if(StringUtils.isNotEmpty(str)){
					str =Jsoup.clean(str, Whitelist.none());
					tech.setContent(str.replace("&nbsp;", "").replace(" ", ""));
				}
				tech.setLabel(TECH_CODE.get(tech.getCategoryCode()));
			}
		}
		page.setRecords(list);
		page.setTotalRecords(newsTechDao.queryCountCondition(newsTechDTO));
		return page;
	}

	@Override
	public NewsTech queryById(Integer id) {
		Assert.notNull(id, "The id must not be null");
		return newsTechDao.queryById(id);
	}

	@Override
	public List<NewsTech> queryTechList(NewsTechDTO newsTechDTO, Integer size) {
		if(size==null||size>100){
			size = 50;
		}
		PageDto<NewsTech> page = new PageDto<NewsTech>();
		page.setPageSize(size);
		List<NewsTech> list=newsTechDao.queryByCondition(newsTechDTO, page);
		if(list.size()>0){
			for(NewsTech tech:list){
				String str=tech.getContent();
				if(StringUtils.isNotEmpty(str)){
					str =Jsoup.clean(str, Whitelist.none());
					tech.setContent(str.replace("&nbsp;", "").replace(" ", ""));
				}
				tech.setLabel(TECH_CODE.get(tech.getCategoryCode()));
			}
		}
		return list;
	}

	@Override
	public Integer updateOneTech(NewsTech newsTech) {
		Assert.notNull(newsTech, "The newsTech must not be null");
		Assert.notNull(newsTech.getId(), "The id must not be null");
		return newsTechDao.update(newsTech);
	}
	
	final static Map<String, String> TECH_CODE = new HashMap<String, String>();
	static {
		TECH_CODE.put("1000", "废金属");
		TECH_CODE.put("1001", "废塑料");
		TECH_CODE.put("10001000", "废铜");
		TECH_CODE.put("10001001", "废铝");
		TECH_CODE.put("10001002", "废钢铁");
		TECH_CODE.put("10001003", "其他废金属");
		TECH_CODE.put("10001004", "行业标准");
		TECH_CODE.put("10001005", "加工工艺");
		TECH_CODE.put("10001006", "产品图库");
		TECH_CODE.put("10011000", "行业知识");
		TECH_CODE.put("10011001", "助剂改性");
		TECH_CODE.put("10011002", "工艺配方");
		TECH_CODE.put("10011003", "机械设备");
		TECH_CODE.put("10011004", "加工技术");
		TECH_CODE.put("10011005", "产品图库");
		TECH_CODE.put("10011006", "故障分析");
		TECH_CODE.put("1002", "废纸与橡胶");
		TECH_CODE.put("10021000", "鉴别");
		TECH_CODE.put("10021001", "知识");
		TECH_CODE.put("10021002", "技术");
		TECH_CODE.put("1003", "其他废料");
		TECH_CODE.put("10031000", "鉴别");
		TECH_CODE.put("10031001", "知识");
		TECH_CODE.put("10031002", "技术");
	}
	@Override
	public NewsTech queryDownNewsTechById(Integer id) {
		return newsTechDao.queryDownNewsTechById(id);
	}

	@Override
	public NewsTech queryOnNewsTechById(Integer id) {
		return newsTechDao.queryOnNewsTechById(id);
	}

	@Override
	public Integer updateViewCount(Integer id,Integer viewCount) {
		return newsTechDao.updateTechViewCount(id,viewCount);
	}

	@Override
	public List<NewsTech> queryTechForIndex(NewsTechDTO newsTechDTO,
			Integer size) {
		if(size==null||size>100){
			size = 50;
		}
		PageDto<NewsTech> page = new PageDto<NewsTech>();
		page.setPageSize(size);
		List<NewsTech> list=newsTechDao.queryTechForIndex(newsTechDTO, page);
		if(list.size()>0){
			for(NewsTech tech:list){
				String str=tech.getContent();
				if(StringUtils.isNotEmpty(str)){
					str =Jsoup.clean(str, Whitelist.none());
					tech.setContent(str.replace("&nbsp;", "").replace(" ", ""));
				}
				tech.setLabel(TECH_CODE.get(tech.getCategoryCode()));
			}
		}
		return list;
	}

	@Override
	public Integer delete(Integer id) {
		return newsTechDao.delete(id);
	}

	@Override
	public Integer updateContent(Integer id, String content) {
		return newsTechDao.updateContent(id, content);
	}

}
