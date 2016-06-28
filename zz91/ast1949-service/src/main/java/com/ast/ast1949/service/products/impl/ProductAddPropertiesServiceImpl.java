package com.ast.ast1949.service.products.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductAddProperties;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.spot.SpotInfo;
import com.ast.ast1949.persist.products.ProductAddPropertiesDAO;
import com.ast.ast1949.service.products.ProductAddPropertiesService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.util.Assert;
import com.zz91.util.lang.StringUtils;

@Component("productAddPropertiesService")
public class ProductAddPropertiesServiceImpl implements
        ProductAddPropertiesService {

    @Resource
    private ProductAddPropertiesDAO productAddPropertiesDAO;
    @Resource
    private ProductsService productsService;
    
    @Override
    public Integer addProperties(ProductAddProperties productAddProperties) {
        Assert.notNull(productAddProperties.getPid(), "pid must not be null");
        if (StringUtils.isEmpty(productAddProperties.getContent())||StringUtils.isEmpty(productAddProperties.getProperty())) {
			return 0;
		}
        
        if ("属性".equals(productAddProperties.getProperty())||"属性值".equals(productAddProperties.getContent())) {
			return 0;
		}
        
        ProductAddProperties pap = productAddPropertiesDAO.queryByPidAndProperty(productAddProperties.getPid(), productAddProperties.getProperty());
        if (pap != null) {
            if (pap.getIsDel()) {
                productAddPropertiesDAO.updateIsDelById("0", pap.getId());
            }
            return productAddPropertiesDAO.updateById(productAddProperties);
        } else {
            return productAddPropertiesDAO.insert(productAddProperties);
        }
    }

    @Override
    public Integer deleteProperties(Integer id) {
        Assert.notNull(id, "the id must not be null");
        return productAddPropertiesDAO.delete(id);
    }

    @Override
    public List<ProductAddProperties> queryByProductId(Integer pid , String isDel) {
        Assert.notNull(pid, "pid must not be null");
        return productAddPropertiesDAO.queryByProductId(pid , isDel);
    }
    @Override
    public List<ProductAddProperties> queryByPid(Integer pid) {
        Assert.notNull(pid, "pid must not be null");
        return productAddPropertiesDAO.queryByPid(pid);
    }

    @Override
    public Integer updateIsDelById(String isDel,
            Integer id) {
        Assert.notNull(id, "id must not be null");
        return productAddPropertiesDAO.updateIsDelById(isDel, id);
    }

    @Override
    public ProductAddProperties queryByPidAndProperty(Integer pid,
            String property) {
        Assert.notNull(pid, "pid must not be null");
        Assert.notNull(property, "property must not be null");
        return productAddPropertiesDAO.queryByPidAndProperty(pid, property);
    }

    @Override
    public Integer updateProperties(ProductAddProperties productAddProperties) {
        Assert.notNull(productAddProperties, "productAddProperties must not be null");
        return productAddPropertiesDAO.updateById(productAddProperties);
    }

    @Override
    public void updateOrAddProperty(ProductsDO productsDO, SpotInfo spotInfo) {
        int productId = productsDO.getId();
        String code = productsDO.getCategoryProductsMainCode().substring(0, 4);
        //判断是否现货
        String typeCode = productsDO.getGoodsTypeCode();
        String prop[] = {"交易说明","形态","级别"};
        String cont[] = {spotInfo.getTransaction(),spotInfo.getShape(),spotInfo.getLevel()};
        
        for (int i = 0 ; i < prop.length; i++) {
            ProductAddProperties pap = queryByPidAndProperty(productsDO.getId(), prop[i]);
            if (ProductsService.GOOD_TYPE_CODE_SPOT.equals(typeCode)) {
                if (pap != null) {
                    updateIsDelById("1",pap.getId());
                }
            } else {
                if (pap != null) {
                    if (code.equals("1000")) {
                        if (prop[i].equals("级别")) {
                            updateIsDelById("1",pap.getId());
                            break;
                        }
                    } else if (!code.equals("1001")) {
                        if (!prop[i].equals("交易说明")) {
                            updateIsDelById("1",pap.getId());
                            continue;
                        }
                    }
                    pap.setContent(cont[i]);
                    updateProperties(pap);
                    if (pap.getIsDel()) {
                        updateIsDelById("0",pap.getId());
                    }
                } else {
                    pap = new ProductAddProperties();
                    if (code.equals("1000")) {
                        if (prop[i].equals("级别")) {
                            break;
                        }
                    } else if (!code.equals("1001")) {
                        if (prop[i].equals("形态")) {
                            break;
                        }
                    }
                    pap.setPid(productId);
                    pap.setProperty(prop[i]);
                    pap.setContent(cont[i]);
                    addProperties(pap);
                }
            }
            
        }
    }
}
