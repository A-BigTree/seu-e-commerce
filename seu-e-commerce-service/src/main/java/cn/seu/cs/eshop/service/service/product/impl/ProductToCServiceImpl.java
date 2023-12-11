package cn.seu.cs.eshop.service.service.product.impl;

import cn.seu.cs.eshop.common.component.EshopConfService;
import cn.seu.cs.eshop.service.cache.product.ProdIndexToCCache;
import cn.seu.cs.eshop.service.es.EsProductSearchService;
import cn.seu.cs.eshop.service.pojo.bo.EshopIndexProdConfBO;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopIndexProdListDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.req.GetEshopIndexProdsResponse;
import cn.seu.cs.eshop.service.service.product.ProductToCService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executor;

import static cn.seu.cs.eshop.service.nacos.ServiceNacosConfEnum.eshopIndexProdConf;
import static cs.seu.cs.eshop.common.sdk.util.ResponseBuilderUtils.buildSuccessResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
@Service
public class ProductToCServiceImpl implements ProductToCService {
    @Resource
    ProdIndexToCCache prodIndexToCCache;


    @Override
    public GetEshopIndexProdsResponse getEshopIndexProds() {
        List<EshopIndexProdListDTO> data = prodIndexToCCache.getProdIndex();
        return buildSuccessResponse(GetEshopIndexProdsResponse.class, data);
    }
}
