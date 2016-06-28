/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-9
 */
package com.zz91.tags.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.tags.domain.Tags;
import com.zz91.tags.dto.ExtResult;
import com.zz91.tags.service.TagsCategoryCollectionService;
import com.zz91.tags.service.TagsService;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.tags.TagsUtils;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-9-9
 */
@Controller
public class ApiController extends BaseController{
	
	@Resource
	private TagsService tagsService;
	@Resource
	private TagsCategoryCollectionService tagsCategoryCollectionService;

	final static String URL_ENCODE="utf-8";
	
	@Deprecated
	@RequestMapping
	public ModelAndView createTags(HttpServletRequest request, Map<String, Object> out, String t){
		try {
			t=StringUtils.decryptUrlParameter(t);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String, String> tagsMap=TagsUtils.getInstance().encodeTags(t, URL_ENCODE);
		for(String k:tagsMap.keySet()){
			Tags tags=new Tags();
			tags.setTags(k);
			tagsService.insertTags(tags);
		}
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	@Deprecated
	@RequestMapping
	public ModelAndView clickTags(HttpServletRequest request, Map<String, Object> out, String t){
		try {
			t=StringUtils.decryptUrlParameter(t);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		tagsService.updateClickCount(t);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	@Deprecated
	@RequestMapping
	public ModelAndView searchTags(HttpServletRequest request, Map<String, Object> out, String t){
		try {
			t=StringUtils.decryptUrlParameter(t);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		tagsService.updateSearchCount(t);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView create(HttpServletRequest request, Map<String, Object> out, String t){
//		try {
//			t=URLDecoder.decode(t, HttpUtils.CHARSET_UTF8);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		Map<String, String> tagsMap=TagsUtils.getInstance().encodeTags(t, HttpUtils.CHARSET_UTF8);
		for(String k:tagsMap.keySet()){
			Tags tags=new Tags();
			tags.setTags(k);
			tagsService.insertTags(tags);
		}
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView click(HttpServletRequest request, Map<String, Object> out, String t){
//		try {
//			t=StringUtils.decryptUrlParameter(t);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		tagsService.updateClickCount(t);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView search(HttpServletRequest request, Map<String, Object> out, String t){
//		try {
//			t=StringUtils.decryptUrlParameter(t);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		tagsService.updateSearchCount(t);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView queryByIndex(HttpServletRequest request, Map<String, Object> out, String ik, Integer l){
		Map<String, String> map=tagsCategoryCollectionService.queryByIndexKey(ik, l);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView queryByCode(HttpServletRequest request, Map<String, Object> out, String c, Integer d, Integer l){
		Map<String, String> map=tagsCategoryCollectionService.queryByCode(c, d, l);
		return printJson(map, out);
	}
	
	@RequestMapping
	public ModelAndView queryTagsByKw(HttpServletRequest request, Map<String, Object> out, String k, String s, Integer l){
		try {
			k=StringUtils.decryptUrlParameter(k);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String, String> map=tagsService.queryTagsByKw(k, s, l);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView queryTagsByTag(HttpServletRequest request, Map<String, Object> out, String t, String s, Integer l){
		try {
			t=StringUtils.decryptUrlParameter(t);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String, String> map=tagsService.queryTagsByTag(t, s, l);
		return printJson(map, out);
	}

	@RequestMapping
	public ModelAndView queryTagsByHot(HttpServletRequest request, Map<String, Object> out,Integer l,Integer d){
		Map<String,String> map = tagsService.queryTagsForHot(l,d);
		return printJson(map, out);
	}

	public static void main(String[] args) {
//		TagsUtils.getInstance().init("web.properties");
//		DBPoolFactory.getInstance().init(
//				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		
//		DBUtils.select("ast", "select name from bbs_tags limit 1000", new IReadDataHandler() {
//
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				TagsUtils.getInstance().createTags(rs.getString(1));
//			}
//			
//		});
//		System.out.println("已添加互助标签");
		
//		for(int i=0;i<50;i++){
//			DBUtils.select("zztags", "select tags from tags limit "+(i*2000)+",2000", new IReadDataHandler() {
//
//				@Override
//				public void handleRead(ResultSet rs) throws SQLException {
//					while (rs.next()) {
//						TagsUtils.getInstance().createTags(rs.getString(1));
//					}
//				}
//
//			});
//			System.out.println("已添加标签：从"+(i*2000)+"到"+((i+1)*2000));
//		}
		
//		TagsUtils.getInstance().createTags("中国，美国，英国，法国，德国");
//		TagsUtils.getInstance().clickTags("中国");
//		TagsUtils.getInstance().clickTags("中国");
//		TagsUtils.getInstance().clickTags("美国");
//		
//		TagsUtils.getInstance().searchTags("中国");
//		TagsUtils.getInstance().searchTags("美国");
		
//		Map<String, String> map1=TagsUtils.getInstance().queryTagsByCode("10001000", 0, 10);
//		System.out.println("map1：根据类别查本类别下的信息");
//		for(String k:map1.keySet()){
//			System.out.println(k+":"+map1.get(k));
//		}
//		
//		Map<String, String> map2=TagsUtils.getInstance().queryTagsByCode("10001000", 1, 10);
//		System.out.println("map2：根据类别查子类别的信息");
//		for(String k:map2.keySet()){
//			System.out.println(k+":"+map2.get(k));
//		}
//		
//		Map<String, String> map3=TagsUtils.getInstance().queryTagsByCode("10001000", 2, 10);
//		System.out.println("map3：根据类别查本类及全部子类的信息");
//		for(String k:map3.keySet()){
//			System.out.println(k+":"+map3.get(k));
//		}
//		
//		Map<String, String> map4=TagsUtils.getInstance().queryTagsByIndex("hot", 2);
//		System.out.println("map4：根据indexkey查询");
//		for(String k:map4.keySet()){
//			System.out.println(k+":"+map4.get(k));
//		}
		
//		Map<String, String> map5=TagsUtils.getInstance().queryTagsByTag("清洗", TagsUtils.ORDER_SEARCH, 10);
//		System.out.println("map5：根据tag查询");
//		for(String k:map5.keySet()){
//			System.out.println(k+":"+map5.get(k));
//		}
//		
//		Map<String, String> map6=TagsUtils.getInstance().queryTagsByKey("杭州", TagsUtils.ORDER_SEARCH, 10);
//		System.out.println("map6：根据keywords查询");
//		for(String k:map6.keySet()){
//			System.out.println(k+":"+map6.get(k));
//		}
	}
	
}
