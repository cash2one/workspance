package com.ast.ast1949.persist.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductAddProperties;

public interface ProductAddPropertiesDAO {
    
    public Integer insert (ProductAddProperties productAddProperties);
    
    public Integer delete (Integer id);
    
    public List<ProductAddProperties> queryByProductId (Integer pid,String isDel);
    
    public Integer updateIsDelById (String isDel , Integer id);
    
    public ProductAddProperties queryByPidAndProperty (Integer pid , String property);
    
    public Integer updateById (ProductAddProperties productAddProperties);
    
    public List<ProductAddProperties> queryByPid(Integer pid);

}
