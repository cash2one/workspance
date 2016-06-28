/**
 * 
 */
package com.ast.ast1949.service.dataindex.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.dataindex.DataIndexDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.dataindex.DataIndexDao;
import com.ast.ast1949.service.dataindex.DataIndexService;
import com.ast.ast1949.util.Assert;
import com.zz91.util.lang.StringUtils;

/**
 * @author yuyh
 * 
 */
@Component("dataIndexService")
public class DataIndexServiceImpl implements DataIndexService {

	@Autowired
	private DataIndexDao dataIndexDao;

	public Integer deleteDataIndex(Integer id) {
		Assert.notNull(id, "id is not null");
		return dataIndexDao.deleteDataIndexById(id);
	}

	public Integer insertDataIndex(DataIndexDO dataIndexDO) {
		Assert.notNull(dataIndexDO, "dataIndexDO is not null");
		return dataIndexDao.insertDataIndex(dataIndexDO);
	}

	@Override
	public DataIndexDO queryDataIndexById(Integer id) {

		return dataIndexDao.queryDataIndexById(id);
	}

	@Override
	public Integer updateDataIndex(DataIndexDO dataIndexDO) {

		return dataIndexDao.updateDataIndex(dataIndexDO);
	}

//	@Override
//	public Map<String, List<DataIndexDO>> queryDataIndexOrderCategory(String parentCode, Integer path) {
//		
//		List<DataIndexDO> list = dataIndexDao.queryDataIndexByParentCode(parentCode,buildupPath(path));
//		Map<String, List<DataIndexDO> > map = new HashMap<String, List<DataIndexDO>>();
//		for(DataIndexDO dataIndex: list){
//			List<DataIndexDO> dataList = map.get(dataIndex.getCategoryCode());
//			if(dataList==null){
//				dataList = new ArrayList<DataIndexDO>();
//			}
//			dataList.add(dataIndex);
//			map.put(dataIndex.getCategoryCode(), dataList);
//		}
//		return map;
//	}
	
	private String buildupPath(Integer path){
		if(path==null || path.intValue()<=0){
			return "%";
		}

		StringBuilder sb=new StringBuilder();
		for(int i=0;i<(path*DataIndexDao.CODE_LENGTH);i++){
			sb.append("_");
		}
		return sb.toString();
	}

	@Override
	public PageDto<DataIndexDO> pageDataIndex(DataIndexDO index,
			PageDto<DataIndexDO> page) {
		if(page.getSort()==null){
			page.setSort("id");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
		page.setTotalRecords(dataIndexDao.queryDataIndexCount(index));
		page.setRecords(dataIndexDao.queryDataIndex(index, page));
		return page;
	}

	@Override
	public List<DataIndexDO> queryDataByCode(String code, Integer path, Integer size) {
		if(StringUtils.isEmpty(code)){
			return null;
		}
		if(size==null){
			size=100;
		}
		return dataIndexDao.queryDataIndexByParentCode(code, buildupPath(path), size);
	}

	@Override
	public List<DataIndexDO> queryDataIndexByCode(String code,Integer size) {
		return dataIndexDao.queryDataIndexByCode(code,size);
	}
	
}
