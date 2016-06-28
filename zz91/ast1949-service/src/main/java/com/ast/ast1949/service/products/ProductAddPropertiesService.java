package com.ast.ast1949.service.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.spot.SpotInfo;

public interface ProductAddPropertiesService {
    
    public Integer addProperties (ProductAddProperties productAddProperties);
    
    public Integer deleteProperties (Integer id);
    
    public List<ProductAddProperties> queryByProductId (Integer pid , String isDel);
    
    public Integer updateIsDelById (String isDel , Integer id);
    
    public ProductAddProperties queryByPidAndProperty (Integer pid , String property);
    
    public Integer updateProperties(ProductAddProperties productAddProperties);
    
    public List<ProductAddProperties> queryByPid(Integer pid);
    
    public void updateOrAddProperty(ProductsDO productsDO,SpotInfo spotInfo);

}
