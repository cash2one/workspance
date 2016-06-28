package com.ast.ast1949.persist.yuanliao.impl;
/**
 * @date 2015-08-22
 * @author shiqp
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.yuanliao.YuanliaoPic;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.yuanliao.YuanliaoPicDao;
@Component("yuanliaoPicDao")
public class YuanliaoPicDaoImpl extends BaseDaoSupport implements YuanliaoPicDao {
	final static String SQL_FIX = "yuanliaoPic";

	@Override
	public Integer insertYuanliaoPic(YuanliaoPic yuanliaoPic) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insertYuanliaoPic"), yuanliaoPic);
	}

	@Override
	public Integer updateYuanliaoPic(YuanliaoPic yuanliaoPic) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateYuanliaoPic"), yuanliaoPic);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<YuanliaoPic> queryYuanliaoPicByYuanliaoId(YuanliaoPic yuanliaoPic, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("yuanliaoPic", yuanliaoPic);
		map.put("size", size);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryYuanliaoPicByYuanliaoId"), map);
	}

}
