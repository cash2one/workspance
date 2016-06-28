package com.ast.ast1949.service.products.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.persist.products.ProductAddPropertiesDAO;
import com.ast.ast1949.service.products.ProductAddPropertiesService;

@Component("productAddPropertiesService")
public class ProductAddPropertiesServiceImpl implements
        ProductAddPropertiesService {

    @Resource
    private ProductAddPropertiesDAO productAddPropertiesDAO;
    @Override
    public Integer addProperties(ProductAddProperties productAddProperties) {
            return productAddPropertiesDAO.insert(productAddProperties);
    }

    @Override
    public Integer deleteProperties(Integer id) {
        // TODO Auto-generated method stub
        return productAddPropertiesDAO.delete(id);
    }

    @Override
    public List<ProductAddProperties> queryByProductId(Integer pid , String isDel) {
        // TODO Auto-generated method stub
        return productAddPropertiesDAO.queryByProductId(pid , isDel);
    }
    @Override
    public List<ProductAddProperties> queryByPid(Integer pid) {
        
        return productAddPropertiesDAO.queryByPid(pid);
    }

    @Override
    public Integer updateIsDelById(String isDel,
            Integer id) {
        // TODO Auto-generated method stub
        return productAddPropertiesDAO.updateIsDelById(isDel, id);
    }

    @Override
    public ProductAddProperties queryByPidAndProperty(Integer pid,
            String property) {
        // TODO Auto-generated method stub
        return productAddPropertiesDAO.queryByPidAndProperty(pid, property);
    }

    @Override
    public Integer updateProperties(ProductAddProperties productAddProperties) {
        return productAddPropertiesDAO.updateById(productAddProperties);
    }

}
