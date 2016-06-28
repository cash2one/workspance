package com.ast.ast1949.front.controller.cn;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.market.MarketDto;
import com.ast.ast1949.front.controller.BaseController;
import com.ast.ast1949.service.market.ProductMarketService;
import com.ast.ast1949.util.StringUtils;

@Controller
public class MarketController extends BaseController{
	@Resource
	private ProductMarketService productMarketService;
	@RequestMapping
	public ModelAndView index(Map<String, Object> out,PageDto<MarketDto> page,String industry,Integer index) {
		if(StringUtils.isEmpty(industry)){
			industry="1001";
		}
		if(index==null){
			index=1;
		}
		page.setPageSize(8);
		page = productMarketService.pageSearchProductsForZhuanTi(page, "100110001010", industry, 1, null, index);
		page.setCurrentPage(index);
		if(index==1){
			out.put("left",1);
		}else{
			out.put("left",index-1);
		}
		if(index>=page.getTotalPages()){
			out.put("right", page.getTotalPages());
		}else{
			out.put("right", index+1);
		}
		Integer start=0,end=0;
		if(index-5>0){
			start=index-5;
		}else{
			start=1;
		}
		if(index+5<page.getTotalPages()){
			end = index+5;
		}else{
			end = page.getTotalPages();
		}
		out.put("industry", industry);
		out.put("index", index);
		out.put("start", start);
		out.put("end", end);
		out.put("page", page);
		return null;
	}
	@RequestMapping
	public ModelAndView august(Map<String, Object> out,PageDto<MarketDto> page,String industry,Integer index) {
		if(StringUtils.isEmpty(industry)){
			industry="1001";
		}
		if(index==null){
			index=1;
		}
		page.setPageSize(8);
		page = productMarketService.pageSearchProductsForZhuanTi(page, "100110001018", industry, 1, null, index);
		page.setCurrentPage(index);
		if(index==1){
			out.put("left",1);
		}else{
			out.put("left",index-1);
		}
		if(index>=page.getTotalPages()){
			out.put("right", page.getTotalPages());
		}else{
			out.put("right", index+1);
		}
		Integer start=0,end=0;
		if(index-5>0){
			start=index-5;
		}else{
			start=1;
		}
		if(index+5<page.getTotalPages()){
			end = index+5;
		}else{
			end = page.getTotalPages();
		}
		out.put("industry", industry);
		out.put("index", index);
		out.put("start", start);
		out.put("end", end);
		out.put("page", page);
		return null;
	}
	
	@RequestMapping
	public ModelAndView jiuyue(Map<String, Object> out,PageDto<MarketDto> page,String industry,Integer index) {
		if(StringUtils.isEmpty(industry)){
			industry="1001";
		}
		if(index==null){
			index=1;
		}
		page.setPageSize(8);
		page = productMarketService.pageSearchProductsForZhuanTi(page, "100110001009", industry, 1, null, index);
		page.setCurrentPage(index);
		if(index==1){
			out.put("left",1);
		}else{
			out.put("left",index-1);
		}
		if(index>=page.getTotalPages()){
			out.put("right", page.getTotalPages());
		}else{
			out.put("right", index+1);
		}
		Integer start=0,end=0;
		if(index-5>0){
			start=index-5;
		}else{
			start=1;
		}
		if(index+5<page.getTotalPages()){
			end = index+5;
		}else{
			end = page.getTotalPages();
		}
		if(end==0){
			end=1;
		}
		out.put("industry", industry);
		out.put("index", index);
		out.put("start", start);
		out.put("end", end);
		out.put("page", page);
		return null;
	}
	
	@RequestMapping
	public ModelAndView shandong(Map<String, Object> out,PageDto<MarketDto> page,String industry,Integer index) {
		if(StringUtils.isEmpty(industry)){
			industry="1001";
		}
		if(index==null){
			index=1;
		}
		page.setPageSize(8);
		page = productMarketService.pageSearchProductsForZhuanTi(page, "100110001014", industry, 1, null, index);
		page.setCurrentPage(index);
		if(index==1){
			out.put("left",1);
		}else{
			out.put("left",index-1);
		}
		if(index>=page.getTotalPages()){
			out.put("right", page.getTotalPages());
		}else{
			out.put("right", index+1);
		}
		Integer start=0,end=0;
		if(index-5>0){
			start=index-5;
		}else{
			start=1;
		}
		if(index+5<page.getTotalPages()){
			end = index+5;
		}else{
			end = page.getTotalPages();
		}
		if(end==0){
			end=1;
		}
		out.put("industry", industry);
		out.put("index", index);
		out.put("start", start);
		out.put("end", end);
		out.put("page", page);
		return null;
	}
	
	@RequestMapping
	public ModelAndView hebei(Map<String, Object> out,PageDto<MarketDto> page,String industry,Integer index) {
		if(StringUtils.isEmpty(industry)){
			industry="1001";
		}
		if(index==null){
			index=1;
		}
		page.setPageSize(8);
		page = productMarketService.pageSearchProductsForZhuanTi(page, "100110001002", industry, 1, null, index);
		page.setCurrentPage(index);
		if(index==1){
			out.put("left",1);
		}else{
			out.put("left",index-1);
		}
		if(index>=page.getTotalPages()){
			out.put("right", page.getTotalPages());
		}else{
			out.put("right", index+1);
		}
		Integer start=0,end=0;
		if(index-5>0){
			start=index-5;
		}else{
			start=1;
		}
		if(index+5<page.getTotalPages()){
			end = index+5;
		}else{
			end = page.getTotalPages();
		}
		if(end==0){
			end=1;
		}
		out.put("industry", industry);
		out.put("index", index);
		out.put("start", start);
		out.put("end", end);
		out.put("page", page);
		return null;
	}

}
