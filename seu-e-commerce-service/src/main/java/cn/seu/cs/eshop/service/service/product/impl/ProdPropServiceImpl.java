package cn.seu.cs.eshop.service.service.product.impl;

import cn.seu.cs.eshop.service.convert.ProductCategoryConvert;
import cn.seu.cs.eshop.service.dao.ProductPropDao;
import cn.seu.cs.eshop.service.dao.ProductPropValueDao;
import cn.seu.cs.eshop.service.manager.product.ProdPropValueManager;
import cn.seu.cs.eshop.service.pojo.db.ProductPropDO;
import cn.seu.cs.eshop.service.pojo.db.ProductPropValueDO;
import cn.seu.cs.eshop.service.sdk.product.dto.ProdPropDTO;
import cn.seu.cs.eshop.service.sdk.product.req.GetProdPropResponse;
import cn.seu.cs.eshop.service.sdk.product.req.ListPageProdPropRequest;
import cn.seu.cs.eshop.service.sdk.product.req.ListPageProdPropResponse;
import cn.seu.cs.eshop.service.sdk.product.req.UpdateProdPropRequest;
import cn.seu.cs.eshop.service.service.AbstractCrudService;
import cn.seu.cs.eshop.service.service.product.ProdPropService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.seu.cs.eshop.common.enums.CrudOperationTypeEnum.DELETE;
import static cn.seu.cs.eshop.common.util.MysqlUtils.buildEffectEntity;
import static cn.seu.cs.eshop.common.util.ResponseBuilderUtils.buildSuccessResponse;
import static cn.seu.cs.eshop.service.convert.ProductCategoryConvert.convertDO;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
@Service
public class ProdPropServiceImpl
        extends AbstractCrudService<ProdPropDTO>
        implements ProdPropService {
    @Resource
    private ProductPropDao productPropDao;
    @Resource
    private ProductPropValueDao productPropValueDao;
    @Resource
    private ProdPropValueManager prodPropValueManager;

    @Override
    public long insert(ProdPropDTO data) {
        ProductPropDO entity = convertDO(data);
        buildEffectEntity(entity);
        productPropDao.insert(entity);
        return entity.getId();
    }

    @Override
    public long delete(ProdPropDTO data) {
        List<ProductPropValueDO> values = productPropValueDao.selectByPropId(data.getId(), data.getShopId());
        List<Long> ids = values.stream()
                .map(ProductPropValueDO::getId)
                .toList();
        if (!CollectionUtils.isEmpty(ids)) {
            productPropValueDao.deleteBatchIds(ids);
        }
        productPropDao.deleteById(data.getId());
        return data.getId();
    }

    @Override
    public long update(ProdPropDTO data) {
        ProductPropDO entity = convertDO(data);
        productPropDao.updateById(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public BaseResponse updateProdProp(UpdateProdPropRequest request) {
        long id = crudOperation(request);
        ProdPropDTO data = request.getData();
        // 更新属性值
        if (request.getAction() != DELETE.getType() && !CollectionUtils.isEmpty(data.getValue())) {
            List<ProductPropValueDO> origins = productPropValueDao.selectByPropId(data.getId(), data.getShopId());
            List<ProductPropValueDO> news = data.getValue().stream()
                    .map(ProductCategoryConvert::convertDO)
                    .toList();
            prodPropValueManager.updateDiffEntities(news, origins);
        }
        return buildSuccessResponse(BaseResponse.class, Long.toString(id));
    }

    @Override
    public ListPageProdPropResponse listPageProdProp(ListPageProdPropRequest request) {
        
        return null;
    }

    @Override
    public GetProdPropResponse getProdProp(Long id, Long shopId) {
        return null;
    }
}
