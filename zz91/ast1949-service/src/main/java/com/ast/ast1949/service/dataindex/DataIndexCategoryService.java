/**
 * 
 */
package com.ast.ast1949.service.dataindex;

import java.io.IOException;
import java.util.List;

import com.ast.ast1949.domain.dataindex.DataIndexCategoryDO;
import com.ast.ast1949.dto.ExtTreeDto;

/**
 * @author yuyh
 *
 */
public interface DataIndexCategoryService {

	/**
	 * 添加类别
	 * @param dataIndexCategoryDO
	 * @param preCode 参数code
	 * @return 结果>0 添加成功
	 */
	public Integer insertDataIndexCategory(DataIndexCategoryDO dataIndexCategoryDO,String preCode);
	/**
	 * 修改类别，不更新isdelete
	 * @param dataIndexCategoryDO
	 * @return 
	 */
	public Integer updateDataIndexCategory(DataIndexCategoryDO dataIndexCategoryDO);
	/**
	 * 根据父code 查詢 List<DataIndexCategoryDO>
	 * @param preCode
	 * @return List<DataIndexCategoryDO> 
	 */
//	public List<DataIndexCategoryDO> queryDataIndexCategorybyPreCode(String preCode);
	
	/**
	 * 获取某个类别下的子类别,并且返回Ext tree需要的数据格式
	 * @param code 父结点编号
	 * @return ext 树结点
	 *		ExtTreeDto.id对应dataIndexCategoryDO.id
	 *		ExtTreeDto.text对应dataIndexCategoryDO.label
	 *		ExtTreeDto.data对应dataIndexCategoryDO.data
	 *		ExtTreeDto.leaf = false 表示仍有子节点,true表示无子节点
	 */
	public List<ExtTreeDto> child(String code);
	/**
	 * 更新类别信息 默认按照记录ID更新 当DataIndexCategoryDO.getId()大于0时更新,否则不更新
	 *
	 * @param DataIndexCategoryDO
	 *            待更新类别信息,需要包含ID信息
	 * @param preCode 父类别code
	 * @throws IOException
	 * @return
	 *  1.当preCode为空时,返回根类别的最大Code,4位
	 *  2.当preCode不为空,返回当前父类别下子类别的最大code,如父类别code为1000,当前子类别下有10001002,则返回10001003
	 */
	public String getMaxCode(String preCode);
	/**
	 * 
	 * @param id
	 * @return DataIndexCategoryDO
	 */
//	public DataIndexCategoryDO queryDataIndexCategoryById(Integer id);
	
//	public Integer updateIsDelete(boolean isdelete, Integer id);
	
	public DataIndexCategoryDO queryDataIndexCategoryByCode(String code);
	
	public Integer deleteCategoryByCode(String code);
	
	public List<DataIndexCategoryDO> queryDataIndexCategoryByPreCode(String code);
}
