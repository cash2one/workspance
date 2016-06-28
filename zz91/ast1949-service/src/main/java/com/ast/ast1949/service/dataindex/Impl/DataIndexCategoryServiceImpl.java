/**
 * 
 */
package com.ast.ast1949.service.dataindex.Impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.dataindex.DataIndexCategoryDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.persist.dataindex.DataIndexCategoryDao;
import com.ast.ast1949.service.dataindex.DataIndexCategoryService;
import com.ast.ast1949.util.Assert;


/**
 * @author yuyh
 *
 */
@Component("dataIndexCategory")
public class DataIndexCategoryServiceImpl implements DataIndexCategoryService{

	@Autowired
	private DataIndexCategoryDao dataIndexCategoryDao;
	
	public List<ExtTreeDto> child(String code) {
		List<String> codeList=dataIndexCategoryDao.queryDataIndexCode();
		Map<String,String> parentCode=new HashMap<String, String>();
		for(String s: codeList){
			if(s!=null && s.length()>4){
				parentCode.put(s.substring(0,s.length()-4), "");
			}
		}
		
		List<DataIndexCategoryDO> categoryDOs =dataIndexCategoryDao.queryDataIndexCategoryByPreCode(code);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		for (DataIndexCategoryDO m : categoryDOs) {
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-" + String.valueOf(m.getId()));
			if(parentCode.get(m.getCode())!=null){
				node.setLeaf(false);
			}else{
				node.setLeaf(true);
			}
			node.setText(m.getLabel());
			node.setData(m.getCode());
			treeList.add(node);
		}
		return treeList;
	}

	public Integer insertDataIndexCategory(
			DataIndexCategoryDO dataIndexCategoryDO, String preCode) {
		Assert.notNull(dataIndexCategoryDO, "dataIndexCategoryDO is not null");
		Assert.notNull(preCode, "preCode is not null");
		preCode=getMaxCode(preCode);
		dataIndexCategoryDO.setCode(preCode);
		return dataIndexCategoryDao.insertDataIndexCategory(dataIndexCategoryDO);
	}

//	public List<DataIndexCategoryDO> queryDataIndexCategorybyPreCode(
//			String preCode) {
//		Assert.notNull(preCode, "preCode is not null");
//		return dataIndexCategoryDao.queryDataIndexCategoryByPreCode(preCode);
//	}

	public Integer updateDataIndexCategory(
			DataIndexCategoryDO dataIndexCategoryDO) {
		Assert.notNull(dataIndexCategoryDO, "dataIndexCategoryDO is not null");
		return dataIndexCategoryDao.updateDataIndexCategory(dataIndexCategoryDO);
	}

	public String getMaxCode(String preCode) {
		Assert.notNull(preCode, "preCode is not null");
		String code = dataIndexCategoryDao.selectMaxCodeByPreCode(preCode);
		
		if (code == null || code.length() == 0) {
			code = preCode + "1000";
		}else {
		    //long i = Long.valueOf(code);
		    BigInteger bi1=new BigInteger(code);
		    BigInteger bi2=new BigInteger("1");
		    bi1 =bi1.add(bi2);
			code = String.valueOf(bi1);
		}
		return code;
	}

//	public DataIndexCategoryDO queryDataIndexCategoryById(Integer id) {
//		Assert.notNull(id, "id is not null");
//		return dataIndexCategoryDao.queryDataIndexCategoryById(id);
//	}

//	@Override
//	public Integer updateIsDelete(boolean isdelete, Integer id) {
//		if(isdelete){
//			return dataIndexCategoryDao.updateIsDelete("1", id);
//		}
//		return dataIndexCategoryDao.updateIsDelete("0", id);
//	}

	@Override
	public DataIndexCategoryDO queryDataIndexCategoryByCode(String code) {
		return dataIndexCategoryDao.queryDataIndexCategoryByCode(code);
	}

	@Override
	public Integer deleteCategoryByCode(String code) {
		return dataIndexCategoryDao.deleteCategoryByCode(code);
	}

	@Override
	public List<DataIndexCategoryDO> queryDataIndexCategoryByPreCode(String code) {
		return dataIndexCategoryDao.queryDataIndexCategoryByPreCode(code);
	}

}
