package cn.seu.cs.eshop.service.service.product.impl;

import cn.seu.cs.eshop.service.sdk.product.dto.ProdPropDTO;
import cn.seu.cs.eshop.service.sdk.product.req.GetProdPropResponse;
import cn.seu.cs.eshop.service.sdk.product.req.ListPageProdPropRequest;
import cn.seu.cs.eshop.service.sdk.product.req.ListPageProdPropResponse;
import cn.seu.cs.eshop.service.sdk.product.req.UpdateProdPropRequest;
import cn.seu.cs.eshop.service.service.AbstractCrudService;
import cn.seu.cs.eshop.service.service.product.ProdPropService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
public class ProdPropServiceImpl extends AbstractCrudService<ProdPropDTO> implements ProdPropService {
    @Override
    public long insert(ProdPropDTO data) {
        return 0;
    }

    @Override
    public long delete(ProdPropDTO data) {
        return 0;
    }

    @Override
    public long update(ProdPropDTO data) {
        return 0;
    }

    @Override
    public BaseResponse updateProdProp(UpdateProdPropRequest request) {
        return null;
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
