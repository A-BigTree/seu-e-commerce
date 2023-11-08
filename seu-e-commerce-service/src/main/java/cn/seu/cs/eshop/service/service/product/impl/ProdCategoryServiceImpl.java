package cn.seu.cs.eshop.service.service.product.impl;

import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.service.cache.ProdCategoryCache;
import cn.seu.cs.eshop.service.convert.ProductCategoryConvert;
import cn.seu.cs.eshop.service.dao.ProductCategoryDao;
import cn.seu.cs.eshop.service.pojo.db.ProductCategoryDO;
import cn.seu.cs.eshop.service.sdk.product.dto.ProdCategoryDTO;
import cn.seu.cs.eshop.service.sdk.product.dto.ProdCategoryListDTO;
import cn.seu.cs.eshop.service.sdk.product.req.GetAllProdCategoryResponse;
import cn.seu.cs.eshop.service.sdk.product.req.ListPageProdCategoryRequest;
import cn.seu.cs.eshop.service.sdk.product.req.ListPageProdCategoryResponse;
import cn.seu.cs.eshop.service.sdk.product.req.UpdateProdCategoryRequest;
import cn.seu.cs.eshop.service.service.AbstractCrudService;
import cn.seu.cs.eshop.service.service.product.ProdCategoryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.seu.cs.eshop.common.constants.CommonConstants.OFFICIAL_ID;
import static cn.seu.cs.eshop.common.util.ResponseBuilderUtils.buildSuccessResponse;
import static cn.seu.cs.eshop.service.convert.ProductCategoryConvert.convertDO;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
@Service
public class ProdCategoryServiceImpl extends AbstractCrudService<ProdCategoryDTO> implements ProdCategoryService {
    @Resource
    private ProductCategoryDao productCategoryDao;
    @Resource
    private ProdCategoryCache prodCategoryCache;

    @Override
    public long insert(ProdCategoryDTO data) {
        ProductCategoryDO entity = convertDO(data);
        ProductCategoryDO newEntity = MysqlUtils.buildEffectEntity(entity);
        productCategoryDao.insert(newEntity);
        return newEntity.getId();
    }

    @Override
    public long delete(ProdCategoryDTO data) {
        Long id = data.getId();
        List<ProductCategoryDO> children = productCategoryDao.selectByParentId(id);
        List<Long> ids = children.stream()
                .map(ProductCategoryDO::getId)
                .toList();
        productCategoryDao.deleteBatchIds(ids);
        productCategoryDao.deleteById(id);
        return id;
    }

    @Override
    public long update(ProdCategoryDTO data) {
        ProductCategoryDO entity = convertDO(data);
        entity.setId(data.getId());
        productCategoryDao.updateById(entity);
        return data.getId();
    }

    @Override
    @Transactional
    public BaseResponse updateProdCategory(UpdateProdCategoryRequest request) {
        long id = crudOperation(request);
        prodCategoryCache.removeCategory(request.getData().getShopId());
        return buildSuccessResponse(BaseResponse.class, Long.toString(id));
    }

    @Override
    @Transactional
    public ListPageProdCategoryResponse listPageProdCategory(ListPageProdCategoryRequest request) {
        IPage<ProductCategoryDO> pageDate = productCategoryDao.selectPageByParentId(request.getParentId(),
                request.getShopId(), request.getPage());
        ProdCategoryListDTO data = MysqlUtils.buildPageData(ProdCategoryListDTO.class,
                pageDate, ProductCategoryConvert::convertDTO);
        return buildSuccessResponse(ListPageProdCategoryResponse.class, data);
    }

    @Override
    public GetAllProdCategoryResponse getAllProdCategory(Long shopId) {
        List<ProductCategoryDO> result = prodCategoryCache.getCategory(shopId);
        Map<Long, List<ProdCategoryDTO>> group = result.stream()
                .map(ProductCategoryConvert::convertDTO)
                .collect(groupingBy(ProdCategoryDTO::getParentId));
        List<ProdCategoryDTO> data = group.getOrDefault(OFFICIAL_ID, new ArrayList<>());
        for (ProdCategoryDTO category: data) {
            category.setChildren(group.getOrDefault(category.getId(), new ArrayList<>()));
        }
        return buildSuccessResponse(GetAllProdCategoryResponse.class, data);
    }
}
