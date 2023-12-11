package cn.seu.cs.eshop.service.cache.product;

import cn.seu.cs.eshop.common.component.EshopConfService;
import cn.seu.cs.eshop.common.component.EshopRedisService;
import cn.seu.cs.eshop.service.convert.EshopProductConvert;
import cn.seu.cs.eshop.service.es.EsProductSearchService;
import cn.seu.cs.eshop.service.pojo.bo.EshopIndexProdConfBO;
import cn.seu.cs.eshop.service.pojo.es.ProductEsIndex;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopIndexProdListDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProductDTO;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static cn.seu.cs.eshop.common.constants.CommonConstants.OFFICIAL_ID;
import static cn.seu.cs.eshop.service.nacos.ServiceNacosConfEnum.eshopIndexProdConf;
import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.prodIndexToCCache;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
@Component
public class ProdIndexToCCache {
    @Resource
    EshopConfService eshopConfService;
    @Resource
    EsProductSearchService esProductSearchService;
    @Resource
    EshopRedisService eshopRedisService;

    @PostConstruct
    public void init() throws NacosException {
        EshopConfService.configService.addListener(
                EshopConfService.getDataId(eshopIndexProdConf.getDataId(), eshopIndexProdConf.getApplication()),
                EshopConfService.getGroup(),
                new Listener() {
                    @Override
                    public Executor getExecutor() {
                        return null;
                    }

                    @Override
                    public void receiveConfigInfo(String s) {
                        eshopRedisService.removeValue(String.valueOf(OFFICIAL_ID), prodIndexToCCache);
                    }
                });
    }

    public List<EshopIndexProdListDTO> getProdIndex() {
        List<EshopIndexProdListDTO> value = eshopRedisService.
                getListValue(String.valueOf(OFFICIAL_ID), prodIndexToCCache, EshopIndexProdListDTO.class);
        if (CollectionUtils.isEmpty(value)) {
            List<EshopIndexProdConfBO> configs =
                    eshopConfService.getConfigList(eshopIndexProdConf, EshopIndexProdConfBO.class);
            List<EshopIndexProdListDTO> res =
                    configs.stream()
                            .map(conf -> {
                                List<ProductEsIndex> indices =
                                        esProductSearchService.searchByConditions(
                                                StringUtils.EMPTY,
                                                conf.getOrderBy(),
                                                conf.getOrder(),
                                                new PageDTO((long) 1, (long) conf.getLimit(), 0L, 0L));
                                List<EshopProductDTO> prods = indices.stream()
                                        .map(EshopProductConvert::convertToEshopProductDTO)
                                        .toList();
                                return new EshopIndexProdListDTO(conf.getStyle(), conf.getId(), conf.getTitle(), prods);
                            })
                            .toList();
            if (!CollectionUtils.isEmpty(res)) {
                eshopRedisService.setObjectValue(String.valueOf(OFFICIAL_ID), res, prodIndexToCCache);
            }
            return res;
        }
        return value;
    }

    public void removeProdIndex() {
        eshopRedisService.removeValue(String.valueOf(OFFICIAL_ID), prodIndexToCCache);
    }
}
