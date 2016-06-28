/**
 * @author shiqp
 * @date 2016-01-19
 */
package com.ast.feiliao91.persist.goods.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.goods.PictureDao;
@Component("pictureDao")
public class PictureDaoImpl extends BaseDaoSupport implements PictureDao {
	final static String SQL_PREFIX="picture";

	@Override
	public Integer insertPicture(Picture picture) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertPicture"), picture);
	}

	@Override
	public Integer updateTargetId(Integer id, Integer targetId,String targetType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("targetId", targetId);
		map.put("id", id);
		map.put("targetType", targetType);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateTargetId"), map);
	}

	@Override
	public Picture queryById(Integer id) {
		return (Picture) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryById"),id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Picture> queryPicList(PageDto<Picture> page, Integer companyId, String targetType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("companyId", companyId);
		map.put("targetType", targetType);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPicList"), map);
	}

	@Override
	public Integer countPicList(Integer companyId, String targetType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("targetType", targetType);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countPicList"), map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Picture> queryPictureByCondition(Integer targetId, String targetType, Integer companyId, Integer size) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("targetId", targetId);
		map.put("targetType", targetType);
		map.put("companyId", companyId);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPictureByCondition"), map);
	}

	@Override
	public Integer deletePic(Integer id) {
		
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "deletePic"),id);
	}

	@Override
	public Integer selectByAddr(String arr) {
		return (Integer)getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX,"selectByAddr"),arr);
	}
	
	
	@Override
	public Integer deleteAllPicInThisGoods(Integer targetId){
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "deleteAllPicInThisGoods"),targetId);
	}
	
	@Override
	public Integer updateSellPostGoodsPic(Integer picId,Integer oId){
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("id", picId);
		map.put("targetId", oId);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateSellPostGoodsPic"),map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Picture> queryPictureByAdmin(Integer goodsId,Integer companyId){
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("targetId", goodsId);
		map.put("companyId", companyId);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPictureByAdmin"), map);
	}
	@Override
	public Integer batchUpdatePicStatus(Integer id,
			String checkPerson, Integer status){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("checkPerson", checkPerson);
		map.put("status", status);
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "batchUpdatePicStatus"),map);
	}
}
