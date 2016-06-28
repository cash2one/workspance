/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-26
 */
package com.ast.ast1949.service.products;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.products.ProductsPicDTO;

/**
 * @author yuyonghui
 * 
 */
public interface ProductsPicService {
	final static String CHECK_STATUS_PASS = "1";
	final static String CHECK_STATUS_NO_PASS = "2";
	final static String CHECK_STATUS_WAIT = "0";
	final static String IS_DEFAULT = "1";
	final static String IS_NOT_DEFAULT = "0";

	public final static String UPLOAD_MODEL = "products";
	public final static String UPLOAD_MODEE = "company";

	/**
	 * 按条件查询图片列表查询
	 * 
	 * @param productsPicDTO
	 * @return true 返回查询所有结果集 false 返回 o
	 * 
	 */
	public List<ProductsPicDO> queryProductsPicByCondition(
			ProductsPicDTO productsPicDTO);

	/**
	 * 按Id查询图片
	 * 
	 * @param id
	 * @return 成功返回结果集 失败返回 null
	 */
	public ProductsPicDTO queryProductPicById(int id);

	/**
	 * 查询图片记录总数
	 * 
	 * @param productsPicDTO
	 * @return true 返回查询记录数 false 返回0
	 */
	public int getProductsPicRecordCountByCondition(
			ProductsPicDTO productsPicDTO);

	/**
	 * 按productId 查询
	 * 
	 * @param productId
	 * @return true 返回查询所有结果集 false 返回 o
	 */
	// public List<ProductsPicDO> queryProductPicByproductId(int productId);

	/**
	 * 添加图片
	 * 
	 * @param productsPicDO
	 * @return 成功 返回 返回影响行数 失败 返回 0
	 * 
	 */
	public int insertProductsPic(ProductsPicDO productsPicDO);

	/**
	 * 修改图片
	 * 
	 * @param productsPicDO
	 * @return 成功 返回 返回影响行数 失败 返回 0
	 */
	public int updateProductsPic(ProductsPicDO productsPicDO);

	/**
	 * 批量删除图片信息
	 * 
	 * @param entities
	 * @return
	 */
	public int batchDeleteProductPicbyId(Integer[] entities);

	/**
	 * 修改图片信息地址
	 * 
	 * @param productsPicDO
	 * @return 成功 返回 返回影响行数 失败 返回 0
	 */
	public Integer updateProductsPicAddr(ProductsPicDO productsPicDO);

	/**
	 * 修改图片信息名称
	 * 
	 * @param productsPicDO
	 * @return 成功 返回 返回影响行数 失败 返回 0
	 */
	public Integer updateProductsPicName(ProductsPicDO productsPicDO);

	/**
	 * 修改图片信息是否为默认
	 * 
	 * @param productsPicDO
	 * @return 成功 返回 返回影响行数 失败 返回 0
	 */
	public Integer updateProductsPicIsDefault(ProductsPicDO productsPicDO);

	/**
	 * 查询供求已审核供求信息和图片，并分页查询，size=12
	 * 
	 * @param productsDTO为供求信息查询条件
	 * <br/>
	 *            productsDTO.getProductsDO().getCompanyId()不能为空，否则抛出异常
	 * @return ProductsPicDTO信息集合，可为空
	 */
	// public List<ProductsPicDTO> queryAllProductsPicByTitle(ProductsDTO
	// productsDTO);

	/**
	 * 查询供求已审核供求信息和图片总记录条数
	 * 
	 * @param productsDTO为供求信息查询条件
	 * <br/>
	 *            productsDTO.getProductsDO().getCompanyId()不能为空，否则抛出异常
	 * @return 返回记录条数，没查询到返回为0
	 */
	// public Integer queryAllProductsPicByTitleCount(ProductsDTO productsDTO);

	/**
	 * 查询供求前4条没有当前记录的已审核供求信息和图片
	 * 
	 * @param productsDO为供求信息
	 *            ，不能为空，否则抛出异常
	 * @return ProductsPicDTO信息集合，可为空
	 */
	// public List<ProductsPicDTO> queryforthProductsPic(ProductsDO productsDO);

	/**
	 * 查询供求信息和图片的详细信息
	 * 
	 * @param id为供求信息主键值
	 *            ，不能为空，否则抛出异常
	 * @return ProductsPicDTO信息，可为空
	 */
	public ProductsPicDTO queryProductsPicDetails(Integer id);

	/**
	 * 查询已分类别供求已审核供求信息和图片总记录条数
	 * productsDTO.getProductsDO().getCompanyId()不能为空，否则抛出异常<br/>
	 * productsDTO.getSeriesId()不能为空，否则抛出异常
	 * 
	 * @return 返回记录条数，没查询到返回为0
	 */
	// public Integer queryAllProductsPicByHadSubSeriesCount(ProductsDTO
	// productsDTO);

	/**
	 * 查询已分类别供求已审核供求信息和图片，并分页查询，size=12
	 * 
	 * @param productsDTO为供求信息查询条件
	 * <br/>
	 *            productsDTO.getProductsDO().getCompanyId()不能为空，否则抛出异常<br/>
	 *            productsDTO.getSeriesId()不能为空，否则抛出异常
	 * @return ProductsPicDTO信息集合，可为空
	 */
	// public List<ProductsPicDTO> queryAllProductsPicByHadSubSeries(ProductsDTO
	// productsDTO);

	/**
	 * 查询未分类别供求已审核供求信息和图片总记录条数
	 * 
	 * @param productsDTO为供求信息查询条件
	 * <br/>
	 *            productsDTO.getProductsDO().getCompanyId()不能为空，否则抛出异常
	 * @return 返回记录条数，没查询到返回为0
	 */
	// public Integer queryAllProductsPicByNoHadSubSeriesCount(ProductsDTO
	// productsDTO);

	/**
	 * 查询未分类别供求已审核供求信息和图片，并分页查询，size=12
	 * 
	 * @param productsDTO为供求信息查询条件
	 * <br/>
	 *            productsDTO.getProductsDO().getCompanyId()不能为空，否则抛出异常
	 * @return ProductsPicDTO信息集合，可为空
	 */
	// public List<ProductsPicDTO>
	// queryAllProductsPicByNoHadSubSeries(ProductsDTO productsDTO);

	/**
	 * 根据供求ID查询供求图片信息
	 * 
	 * @param productId为供求id
	 *            ，不能为空
	 * @return ProductsPicDO信息集合，可为空
	 */
	public List<ProductsPicDO> queryProductPicInfoByProductsId(Integer productId);

	/**
	 * 根据供求id查询 通过审核 的图片信息
	 * 
	 * @param productId
	 * @return
	 */
	public List<ProductsPicDO> queryProductPicInfoByProductsIdForFront(
			Integer productId);

	/**
	 * 搜索有图片的供求
	 * 
	 * @param productId
	 * @return
	 */

	public Integer countProductPicByProductId(Integer productId);

	/**
	 * 传入供求列表，根据id搜索供求所上传的图片数量 使用于，生意管家 - 供求管理 页面 查看图片上传数量
	 * 
	 * @param list
	 * @return
	 */
	public Map<Integer, Integer> countProductsPicByProductsId(
			List<ProductsDO> list);

	/**
	 * 根据供求ID查询供求图片地址(多ID)
	 * 
	 * @param list
	 * @return
	 */
	// public List<ProductsDto> queryProductPicUrl(List<ProductsDto> list);

	public String queryPicByProductsId(Integer id);

	public Integer batchUpdatePicStatus(String ids, String unpassReason,
			String checkStatus);
	
	public Integer batchUpdatePicStatusByProductId(Integer productId,
			String unpassReason, String checkStatus);

	public Integer updateProductsPicIsDefaultById(Integer id,String isDefault);

}
