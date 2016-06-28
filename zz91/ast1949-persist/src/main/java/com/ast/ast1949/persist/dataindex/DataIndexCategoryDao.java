/**
 * 
 */
package com.ast.ast1949.persist.dataindex;

import java.util.List;

import com.ast.ast1949.domain.dataindex.DataIndexCategoryDO;

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
