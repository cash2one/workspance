/**
 * 
 */
package com.ast.feiliao91.persist.common;

import java.util.List;

import com.ast.feiliao91.domain.common.DataIndexCategoryDO;

/**
 * @author yuyh
 * 
 */
public interface DataIndexCategoryDao {

	public List<DataIndexCategoryDO> queryDataIndexCategoryByPreCode(
			String preCode);

	public Integer insertDataIndexCategory(
			DataIndexCategoryDO dataIndexCategoryDO);

	public Integer updateDataIndexCategory(
			DataIndexCategoryDO dataIndexCategoryDO);

	public String selectMaxCodeByPreCode(String preCode);

//	public DataIndexCategoryDO queryDataIndexCategoryById(Integer id);

//	public Integer updateIsDelete(String isDelete, Integer id);

	public List<String> queryDataIndexCode();

	public DataIndexCategoryDO queryDataIndexCategoryByCode(String code);
	
	public Integer deleteCategoryByCode(String code);
}
