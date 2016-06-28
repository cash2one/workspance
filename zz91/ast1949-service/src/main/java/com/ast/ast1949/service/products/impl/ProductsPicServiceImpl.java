/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-26
 */
package com.ast.ast1949.service.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.products.ProductsPicDTO;
import com.ast.ast1949.persist.products.ProductsPicDAO;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.util.Assert;
import com.zz91.util.lang.StringUtils;

/**
 * @author yuyonghui
 * 
 */
@Component("productsPicService")
public class ProductsPicServiceImpl implements ProductsPicService {

	@Autowired
	private ProductsPicDAO productsPicDAO;

	public List<ProductsPicDO> queryProductsPicByCondition(
			ProductsPicDTO productsPicDTO) {
		Assert.notNull(productsPicDTO, "productsPicDTO is not null");
		return productsPicDAO.queryProductsPicByCondition(productsPicDTO);
	}

	public int getProductsPicRecordCountByCondition(
			ProductsPicDTO productsPicDTO) {
		Assert.notNull(productsPicDTO, "productsPicDTO is not null");
		return productsPicDAO
				.getProductsPicRecordCountByCondition(productsPicDTO);
	}

	// public List<ProductsPicDO> queryProductPicByproductId(int productId) {
	// Assert.notNull(productId, "productId is not null");
	// return productsPicDAO.queryProductPicByproductId(productId);
	// }
	public int insertProductsPic(ProductsPicDO productsPicDO) {
		Assert.notNull(productsPicDO, "productsPicDO is not null");
		// productsPicDAO.q
		if (StringUtils.isEmpty(productsPicDO.getCheckStatus())) {
			productsPicDO.setCheckStatus(CHECK_STATUS_WAIT);
		}
		if(productsPicDO.getProductId()==null){
			return 0;
		}
		
		if (productsPicDO.getProductId()>0) {
			List<ProductsPicDO> list = productsPicDAO.queryProductPicInfoByProductsId(productsPicDO.getProductId());
			if (list!=null&&list.size()>=5) {
				return 0;
			}
		}
		
		return productsPicDAO.insertProductsPic(productsPicDO);
	}

	public int updateProductsPic(ProductsPicDO productsPicDO) {

		Assert.notNull(productsPicDO, "productsPicDO is not null");
		return productsPicDAO.updateProductsPic(productsPicDO);
	}

	public ProductsPicDTO queryProductPicById(int id) {
		Assert.notNull(id, "id is not null");
		return productsPicDAO.queryProductPicById(id);
	}

	public int batchDeleteProductPicbyId(Integer[] entities) {
		/** 删除服务器上传的图片
		for (Integer id : entities) {

			String file = productsPicDAO.queryPicPath(id);
			if (StringUtils.isNotEmpty(file)) {
				String filepath = MvcUpload.getDestRoot();
				if (file.startsWith("/")) {
					filepath = filepath + file;
				} else {
					filepath = filepath + "/" + file;
				}
				FileUtils.deleteFile(filepath);
			}
		}
		**/
		return productsPicDAO.batchDeleteProductPicbyId(entities);
	}

	public Integer updateProductsPicAddr(ProductsPicDO productsPicDO) {
		Assert.notNull(productsPicDO, "productsPicDO is not null");
		return productsPicDAO.updateProductsPicAddr(productsPicDO);
	}

	public Integer updateProductsPicName(ProductsPicDO productsPicDO) {
		Assert.notNull(productsPicDO, "productsPicDO is not null");
		return productsPicDAO.updateProductsPicName(productsPicDO);
	}

	public Integer updateProductsPicIsDefault(ProductsPicDO productsPicDO) {
		Assert.notNull(productsPicDO, "productsPicDO is not null");
		Assert.notNull(productsPicDO.getProductId(), "productId is not null");
		Assert.notNull(productsPicDO.getId(), "id is not null");
		// 其他图片设置为非默认
		productsPicDAO.updateIsDefaultByProductId(productsPicDO.getProductId(),
				IS_NOT_DEFAULT);
		// 更新图片为默认
		return productsPicDAO.updateProductsPicIsDefault(productsPicDO);
	}

	// public List<ProductsPicDTO> queryAllProductsPicByTitle(
	// ProductsDTO productsDTO) {
	// Assert.notNull(productsDTO.getProductsDO().getCompanyId(),
	// "The companyId must not be null");
	// return productsPicDAO.queryAllProductsPicByTitle(productsDTO);
	// }
	// public Integer queryAllProductsPicByTitleCount(ProductsDTO productsDTO) {
	// Assert.notNull(productsDTO.getProductsDO().getCompanyId(),
	// "The companyId must not be null");
	// return productsPicDAO.queryAllProductsPicByTitleCount(productsDTO);
	// }
	// public List<ProductsPicDTO> queryforthProductsPic(
	// ProductsDO productsDO) {
	// Assert.notNull(productsDO, "The productsDTO must not be null");
	// return productsPicDAO.queryforthProductsPic(productsDO);
	// }
	public ProductsPicDTO queryProductsPicDetails(Integer id) {
		Assert.notNull(id, "The id must not be null");
		ProductsPicDTO picdto = productsPicDAO.queryProductsPicDetails(id);
		if (picdto.getProductsPicDO() == null) {
			picdto.setProductsPicDO(new ProductsPicDO());
		}
		return picdto;
	}

	// public List<ProductsPicDTO> queryAllProductsPicByHadSubSeries(
	// ProductsDTO productsDTO) {
	// Assert.notNull(productsDTO.getProductsDO().getCompanyId(),
	// "The companyId must not be null");
	// Assert.notNull(productsDTO.getSeriesId(),
	// "The seriesId must not be null");
	// return productsPicDAO.queryAllProductsPicByHadSubSeries(productsDTO);
	// }
	// public Integer queryAllProductsPicByHadSubSeriesCount(
	// ProductsDTO productsDTO) {
	// Assert.notNull(productsDTO.getProductsDO().getCompanyId(),
	// "The companyId must not be null");
	// Assert.notNull(productsDTO.getSeriesId(),
	// "The seriesId must not be null");
	// return
	// productsPicDAO.queryAllProductsPicByHadSubSeriesCount(productsDTO);
	// }
	// public List<ProductsPicDTO> queryAllProductsPicByNoHadSubSeries(
	// ProductsDTO productsDTO) {
	// Assert.notNull(productsDTO.getProductsDO().getCompanyId(),
	// "The companyId must not be null");
	// return productsPicDAO.queryAllProductsPicByNoHadSubSeries(productsDTO);
	// }
	// public Integer queryAllProductsPicByNoHadSubSeriesCount(
	// ProductsDTO productsDTO) {
	// Assert.notNull(productsDTO.getProductsDO().getCompanyId(),
	// "The companyId must not be null");
	// return
	// productsPicDAO.queryAllProductsPicByNoHadSubSeriesCount(productsDTO);
	// }
	public List<ProductsPicDO> queryProductPicInfoByProductsId(Integer productId) {
		Assert.notNull(productId, "The productId can not be null");
		if (productId > 0) {
			return productsPicDAO.queryProductPicInfoByProductsId(productId);
		} else {
			return null;
		}
	}

	public List<ProductsPicDO> queryProductPicInfoByProductsIdForFront(
			Integer productId) {
		Assert.notNull(productId, "The productId can not be null");
		return productsPicDAO
				.queryProductPicInfoByProductsIdForFront(productId);
	}

	@Override
	public Integer countProductPicByProductId(Integer productId) {
		return productsPicDAO.countProductPicByProductId(productId);
	}

	@Override
	public String queryPicByProductsId(Integer id) {
		return productsPicDAO.queryPicByProId(id);
	}

	@Override
	public Map<Integer, Integer> countProductsPicByProductsId(
			List<ProductsDO> list) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Integer i = 0;
		for (ProductsDO obj : list) {
			if (obj != null && obj.getId() != null) {
				i = productsPicDAO.countProductPicByProductId(obj.getId());
				map.put(obj.getId(), i);
			}
		}
		return map;
	}

	@Override
	public Integer batchUpdatePicStatus(String ids, String unpassReason,
			String checkStatus) {
		Assert.notNull(ids, "id must not be null");
		return productsPicDAO.batchUpdatePicStatus(
				StringUtils.StringToIntegerArray(ids), checkStatus,
				unpassReason);
	}
	

	/**
	 * 使用productId 批量审核图片
	 */
	@Override
	public Integer batchUpdatePicStatusByProductId(Integer productId,
			String unpassReason, String checkStatus) {
		List<ProductsPicDO> list = productsPicDAO
				.queryProductPicInfoByProductsId(productId);
		String ids = "";
		for (ProductsPicDO obj : list) {
			ids = ids + obj.getId() + ",";
		}
		return batchUpdatePicStatus(ids, unpassReason, checkStatus);
	}

	/** 根据图片id更新isdefault，完成图片是否置顶 */
	public Integer updateProductsPicIsDefaultById(Integer id,String isDefault) {
		return productsPicDAO.updateProductsPicIsDefaultById(id,isDefault);

	}


}
