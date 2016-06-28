package com.ast.ast1949.persist.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.ProductAddPropertiesDAO;

@SuppressWarnings("unchecked")
@Component("productAddPropertiesDAO")
public class ProductAddPropertiesDAOImpl extends BaseDaoSupport implements ProductAddPropertiesDAO{

    final static String SQL_FIX = "productAddProperties";
    @Override
    public Integer insert(ProductAddProperties productAddProperties) {
        return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_FIX, "insert"), productAddProperties);
    }

    @Override
    public Integer delete(Integer id) {
        return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_FIX, "delete"), id);
    }

    @Override
    public List<ProductAddProperties> queryByProductId(Integer pid , String isDel) {
        Map<String , Object> map = new HashMap<String, Object>();
        map.put("pid", pid);
        map.put("isDel", isDel);
        return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByProductId"), map);
    } 
    @Override
    public List<ProductAddProperties> queryByPid(Integer pid) {
        
        return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_FIX, "queryByPid"), pid);
    }

    @Override
    public Integer updateIsDelById(String isDel,Integer id) {
        Map<String ,Object> map = new HashMap<String, Object>();
        map.put("isDel", isDel);
        map.put("id", id);
        return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateIsDelById"), map);
    }

    @Override
    public ProductAddProperties queryByPidAndProperty(Integer pid,
            String property) {
        Map<String , Object> map = new HashMap<String, Object>();
        map.put("pid", pid);
        map.put("property", property);
        return (ProductAddProperties) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_FIX, "queryByPidAndProperty"), map);
    }
    
    @Override
    public Integer updateById (ProductAddProperties productAddProperties) {
        return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_FIX, "updateById"), productAddProperties);
    }

}
