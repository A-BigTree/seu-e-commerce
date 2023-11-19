package cn.seu.cs.eshop.service.service.product.impl;

import cn.seu.cs.eshop.common.exception.EshopException;
import cn.seu.cs.eshop.service.dao.ProductCategoryPropDao;
import cn.seu.cs.eshop.service.dao.ProductPropDao;
import cn.seu.cs.eshop.service.dao.ProductPropValueDao;
import cn.seu.cs.eshop.service.manager.product.ProdCategoryPropManager;
import cn.seu.cs.eshop.service.pojo.db.ProductCategoryPropDO;
import cn.seu.cs.eshop.service.pojo.db.ProductPropDO;
import cn.seu.cs.eshop.service.pojo.db.ProductPropValueDO;
import cn.seu.cs.eshop.service.sdk.product.category.dto.ProdCategoryPropDTO;
import cn.seu.cs.eshop.service.sdk.product.category.dto.ProdPropDTO;
import cn.seu.cs.eshop.service.sdk.product.category.req.GetProdCategoryPropResponse;
import cn.seu.cs.eshop.service.sdk.product.category.req.UpdateProdCategoryPropRequest;
import cn.seu.cs.eshop.service.service.product.ProdCategoryPropService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static cn.seu.cs.eshop.common.util.ResponseBuilderUtils.buildSuccessResponse;
import static cn.seu.cs.eshop.service.convert.ProductCategoryConvert.convertDO;
import static cn.seu.cs.eshop.service.convert.ProductCategoryConvert.convertDTO;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/18
 */
@Service
public class ProdCategoryPropServiceImpl implements ProdCategoryPropService {
    @Resource
    ProductCategoryPropDao productCategoryPropDao;
    @Resource
    ProductPropDao productPropDao;
    @Resource
    ProductPropValueDao productPropValueDao;
    @Resource
    ProdCategoryPropManager prodCategoryPropManager;

    @Override
    @Transactional
    public GetProdCategoryPropResponse getProdCategoryProp(Long id, Long shopId, Integer propType) {
        // 获取 类目-属性 关联关系
        List<ProductCategoryPropDO> categoryProps = productCategoryPropDao.selectByCategoryId(id, shopId, propType);
        if (CollectionUtils.isEmpty(categoryProps)) {
            return buildSuccessResponse(GetProdCategoryPropResponse.class, new ArrayList<>());
        }
        List<Long> propIds = categoryProps.stream()
                .map(ProductCategoryPropDO::getPropId)
                .toList();
        // 获取属性详细信息
        List<ProductPropDO> props = productPropDao.selectBatchIds(propIds);
        if (CollectionUtils.isEmpty(props)) {
            throw new EshopException("存在脏数据");
        }
        // 获取属性下值信息
        List<ProductPropValueDO> propValues = productPropValueDao.selectByBatchPropIds(propIds, shopId);
        // propId -> list(value)
        Map<Long, List<ProductPropValueDO>> mapValues = propValues.stream()
                .collect(groupingBy(ProductPropValueDO::getPropId));
        // propId -> prop
        Map<Long, ProdPropDTO> propMap = new HashMap<>();
        props.stream()
                .map(prop -> convertDTO(prop, mapValues.getOrDefault(prop.getId(), null)))
                .forEach(value -> propMap.put(value.getId(), value));
        // 构建结果
        List<ProdCategoryPropDTO> data = categoryProps.stream()
                .map(value -> convertDTO(value, propMap.getOrDefault(value.getPropId(), null)))
                .toList();
        return buildSuccessResponse(GetProdCategoryPropResponse.class, data);
    }

    @Override
    @Transactional
    public BaseResponse updateProdCategoryProp(UpdateProdCategoryPropRequest request) {

        List<ProductCategoryPropDO> news = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getProps())) {
            checkDuplication(request.getProps());
            news = request.getProps().stream()
                    .map(value -> convertDO(value, request.getShopId()))
                    .toList();
        }
        List<ProductCategoryPropDO> origins = productCategoryPropDao.selectByCategoryId(request.getCategoryId(),
                request.getShopId(), request.getPropType());
        prodCategoryPropManager.updateDiffEntities(news, origins);
        return buildSuccessResponse(BaseResponse.class, "OK");
    }

    private void checkDuplication(List<ProdCategoryPropDTO> data) {
        HashSet<Long> set = new HashSet<>();
        data.forEach(value -> {
            if (set.contains(value.getPropId())) {
                throw new EshopException("绑定属性存在重复请重试");
            }
            set.add(value.getPropId());
        });
    }
}
