/**
 * 
 */
package com.ast.ast1949.persist.dataindex;

import java.util.List;

import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.dto.PageDto;

/**
 * @author yuyh
 *
 */
public interface DataIndexDao {

//	public List<DataIndexDto> pageDataIndexByCondition(DataIndexDto dataIndexDto); 
	
//	public Integer countDataIndexByCondition(DataIndexDto dataIndexDto);
	
	
	
//	public List<DataIndexDO> queryDataIndexByCode(String code,Integer size);
	
	public final static int CODE_LENGTH = 4;
	/**
	 * <br />根据父类别查找index信息
	 * <br />path>0 表示查找子类别的深度,否则查找指定code的index信息
	 */
	public List<DataIndexDO> queryDataIndexByParentCode(String parentCode, String path, Integer size);
	
	public List<DataIndexDO> queryDataIndex(DataIndexDO index, PageDto page );
	
	public Integer queryDataIndexCount(DataIndexDO index);
	
	public Integer insertDataIndex(DataIndexDO dataIndexDO);
	
	public Integer updateDataIndex(DataIndexDO dataIndexDO);
	
	public DataIndexDO queryDataIndexById(Integer id);
	
	public Integer deleteDataIndexById(Integer id);
	
	public 	List<DataIndexDO> queryDataIndexByCode(String code,Integer size);
}
