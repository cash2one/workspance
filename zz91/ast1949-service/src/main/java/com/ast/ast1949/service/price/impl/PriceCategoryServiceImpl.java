/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-25
 */
package com.ast.ast1949.service.price.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.price.PriceCategoryDTO;
import com.ast.ast1949.dto.price.PriceCategoryMinDto;
import com.ast.ast1949.persist.price.PriceCategoryDAO;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 * 
 */

@Component("priceCategoryService")
public class PriceCategoryServiceImpl implements PriceCategoryService {

	@Autowired
	private PriceCategoryDAO priceCategoryDAO;
//	@Autowired
//	private PriceDAO priceDAO;

//	public Integer batchDeletePriceCategoryById(int[] entities) {
//		Assert.notNull(entities, "The entities must not be null.");
//		return priceCategoryDAO.batchDeletePriceCategoryById(entities);
//	}
	public String queryTypeNameByTypeId(Integer id) {
		return priceCategoryDAO.queryTagNameByTypeId(id);
	};
	public Integer deletePriceCategoryById(Integer id) throws IllegalArgumentException {
		Assert.notNull(id, "The id must not be null.");
		return priceCategoryDAO.deletePriceCategoryById(id);
	}

//	public Integer getPriceCategoryRecordConutByCondition(PriceCategoryDTO newsCategoryDto) {
//		return priceCategoryDAO.getPriceCategoryRecordConutByCondition(newsCategoryDto);
//	}

	public Integer insertPriceCategory(PriceCategoryDO newsCategory)
			throws IllegalArgumentException {
		Assert.notNull(newsCategory, "The Object of NewsCategory must not be null.");
		Assert.notNull(newsCategory.getName(), "The NewsCategory name must not be null.");
		if (newsCategory.getParentId() == null) {
			newsCategory.setParentId(0);
		}
		if (newsCategory.getIsDelete() == null) {
			newsCategory.setIsDelete(false);
		}
		newsCategory.setGmtCreated(new Date());
		newsCategory.setGmtModified(new Date());

		return priceCategoryDAO.insertPriceCategory(newsCategory);
	}

//	public List<PriceCategoryDO> queryPriceCategoryByCondition(PriceCategoryDTO newsCategoryDto) {
//		return priceCategoryDAO.queryPriceCategoryByCondition(newsCategoryDto);
//	}

	public PriceCategoryDO queryPriceCategoryById(Integer id) throws IllegalArgumentException {
		Assert.notNull(id, "The id must not be null.");
		return priceCategoryDAO.queryPriceCategoryById(id);
	}

	public Integer updatePriceCategoryById(PriceCategoryDO newsCategory)
			throws IllegalArgumentException {
		Assert.notNull(newsCategory, "The object of news must not be null.");
		newsCategory.setGmtModified(new Date());
		if (newsCategory.getParentId() == null) {
			newsCategory.setParentId(0);
		}
		if (newsCategory.getIsDelete() == null) {
			newsCategory.setIsDelete(false);
		}
		return priceCategoryDAO.updatePriceCategoryById(newsCategory);
	}

//	public Integer updatePriceCategoryIsDeleteById(Integer id, short isDelete)
//			throws IllegalArgumentException {
//		return null;
//	}

	public List<PriceCategoryDO> queryPriceCategoryByParentId(Integer id)
			throws IllegalArgumentException {
		Assert.notNull(id, "The parent id must not be null.");
		return priceCategoryDAO.queryPriceCategoryByParentId(id);
	}

	public List<ExtTreeDto> queryExtTreeChildNodeByParentId(Integer id)
			throws IllegalArgumentException {
		Assert.notNull(id, "The parent id must not be null.");
		List<PriceCategoryDO> newsCategory = priceCategoryDAO.queryPriceCategoryByParentId(id);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		for (PriceCategoryDO n : newsCategory) {
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-" + String.valueOf(n.getId()));
			
			node.setText(n.getName()+"("+n.getId()+")");
			node.setData(n.getId().toString());
			treeList.add(node);
			
			//判断有无子节点
			Integer num=priceCategoryDAO.countChild(n.getId());
			if(num!=null && num.intValue()>0){
				node.setLeaf(false);
			}else{
				node.setLeaf(true);
			}
		}
		return treeList;
	}

	@Override
	public List<PriceCategoryMinDto> queryPriceCategoryByParentIdOrderList(Integer parentId) {
		Assert.notNull(parentId, "the parentId must not be null");
		return priceCategoryDAO.queryPriceCategoryByParentIdOrderList(parentId);
	}
	
//	public List<PriceCategoryDTO> getPriceCategoryByParentId(Integer id)
//			throws IllegalArgumentException {
//		List<PriceCategoryDO> list = priceCategoryDAO.queryPriceCategoryByParentId(id);
//		List<PriceCategoryDTO> listDTO = new ArrayList<PriceCategoryDTO>();
//
//		for (PriceCategoryDO priceCategoryDO : list) {
//			PriceCategoryDTO priceCategoryDTO = new PriceCategoryDTO();
//			priceCategoryDTO.setPriceCategoryDO(priceCategoryDO);
//
//			List<PriceCategoryDO> list2 = priceCategoryDAO
//					.queryPriceCategoryByParentId(priceCategoryDO.getId());
//			priceCategoryDTO.setChild(list2);
//
//			PriceDTO priceDTO = new PriceDTO();
//			priceDTO.setParentId(priceCategoryDO.getId());
//			priceDTO.setLimitSize(3);
//			List<ForPriceDTO> aDtos = priceDAO.queryPriceByParentId(priceDTO);
//
//			priceCategoryDTO.setPriceChild(aDtos);
//
//			listDTO.add(priceCategoryDTO);
//
//		}
//
//		return listDTO;
//	}

	public List<PriceCategoryDO> getAllParentPriceCategoryByParentId(Integer parentId)
			throws IllegalArgumentException {
		List<PriceCategoryDO> list = new ArrayList<PriceCategoryDO>();
		PriceCategoryDO entity = priceCategoryDAO.queryPriceCategoryById(parentId);
		if (entity != null) {
			// 1为主类别，2为辅助类别
			if ((!entity.getId().equals(1) && !entity.getId().equals(2))) {
				list.addAll(getAllParentPriceCategoryByParentId(entity.getParentId()));
				list.add(entity);
			}
		}
		return list;

	}

//	@Override
//	public List<PriceCategoryLinkDO> queryPriceCategoryLink(
//			PriceCategoryLinkDO priceCategoryLinkDO) {
//		Assert.notNull(priceCategoryLinkDO, "priceCategoryLinkDO is not null");
//		return priceCategoryDAO.queryPriceCategoryLink(priceCategoryLinkDO);
//	}

	@Override
	public PriceCategoryDTO queryPriceCategoryDtoById(Integer id) {
		Assert.notNull(id, "The id must not be null.");
		return priceCategoryDAO.queryPriceCategoryDtoById(id);
	}

	@Override
	public List<PriceCategoryDO> queryAssistPriceCategoryByTypeId(Integer id) {
		Assert.notNull(id, "The id must not be null.");
		return priceCategoryDAO.queryAssistPriceCategoryByTypeId(id);
	}

//	@Override
//	public Integer countPriceCategoryByCondition(PriceCategoryDTO priceCategoryDto) {
//		return priceCategoryDAO.countPriceCategoryByCondition(priceCategoryDto);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<ExtCheckBoxTreeDto> queryExtCheckBoxTreeChildNodeByParentId(Integer parentId,
//			String ids) {
//		Assert.notNull(parentId, "The parent id must not be null.");
//		ArrayList al=new ArrayList();
//		if(ids!=null&&StringUtils.isNotEmpty(ids)){
//			String[] entities=ids.split(",");
//			for (String s : entities) {
//				al.add(s);
//			}
//		} else {
//			List<Integer> list =queryAssistPriceCategoryIdByTypeId(parentId);
//			for (Integer i : list) {
//				al.add(i);
//			}
////			for (PriceCategoryDO priceCategoryDO : list) {
////				al.add(priceCategoryDO.getId());
////			}
//		}
//		
//		List<PriceCategoryDO> priceCategoryDO = queryPriceCategoryByParentId(parentId);
//		List<ExtCheckBoxTreeDto> treeList = new ArrayList<ExtCheckBoxTreeDto>();
//		for(PriceCategoryDO p:priceCategoryDO){
//			ExtCheckBoxTreeDto node = new ExtCheckBoxTreeDto();
//			node.setId(String.valueOf(p.getId()));
//			node.setText(p.getName());
//			Integer conut= countPriceCategoryByParentId(p.getId());
//			node.setCls(conut.intValue()==0?"folder":"file");
//			node.setLeaf(conut.intValue()==0?true:false);
//			
//			//设置选中
//			if(al.size()>0){
//				for(int ii=0;ii<al.size();ii++){
//					if(StringUtils.isNumber(al.get(ii).toString())){
//						if(Integer.valueOf(al.get(ii).toString()).intValue()==p.getId().intValue()){
//							node.setChecked(true);
//						}
//					}
//				}
//			} else {
//				node.setChecked(false);
//			}
//			
//			//设置子节点
//			List<ExtCheckBoxTreeDto> children = queryExtCheckBoxTreeChildNodeByParentId(p.getId(),ids);
//			node.setChildren(children);
//			treeList.add(node);
//		}
//		
//		return treeList;
//	}

//	@Override
//	public List<Integer> queryAssistPriceCategoryIdByTypeId(Integer id) {
//		Assert.notNull(id, "The id must not be null.");
//		return priceCategoryDAO.queryAssistPriceCategoryIdByTypeId(id);
//	}

//	@Override
//	public Integer countPriceCategoryByParentId(Integer id) {
//		Assert.notNull(id, "The id must not be null.");
//		return priceCategoryDAO.countPriceCategoryByParentId(id);
//	}

	@Override
	public Integer queryParentIdById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return priceCategoryDAO.queryParentIdById(id);
	}
}
