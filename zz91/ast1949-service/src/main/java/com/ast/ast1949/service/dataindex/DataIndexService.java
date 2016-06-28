/**
 * 
 */
package com.ast.ast1949.service.dataindex;

import java.util.List;

import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author yuyh
 *
 */
public interface DataIndexService {

	/**
	 * <br />查找某节点下的所有index信息
	 * <br />path表示第几级子类，如1表示第1级子类，2表示第二级子类，
	 * <br />当path＝2时，无法获取第一级子类的信息
	 */
//	public Map<String, List<DataIndexDO>> queryDataIndexOrderCategory(String parentCode, Integer path);
	
	public PageDto<DataIndexDO> pageDataIndex(DataIndexDO index,PageDto<DataIndexDO> page);
	
	public Integer insertDataIndex(DataIndexDO dataIndexDO) ;
	
	public Integer deleteDataIndex(Integer id);
	
	public Integer updateDataIndex(DataIndexDO dataIndexDO);
	
	public DataIndexDO queryDataIndexById(Integer id);
	
	/**
	 * <br />查找某节点下的所有index信息
	 * <br />path表示第几级子类，如1表示第1级子类，2表示第二级子类，
	 * <br />当path＝2时，无法获取第一级子类的信息 
	 */
	public List<DataIndexDO> queryDataByCode(String code, Integer path, Integer size);
	
	public List<DataIndexDO> queryDataIndexByCode(String code,Integer size);
	
}
