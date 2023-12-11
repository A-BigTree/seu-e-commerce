package cn.seu.cs.eshop.api.controller.product;

import cn.seu.cs.eshop.api.annotation.ApiMonitor;
import cn.seu.cs.eshop.service.sdk.product.prod.req.GetEshopIndexProdsResponse;
import cn.seu.cs.eshop.service.sdk.product.rpc.EshopProdToCService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
@RestController
@RequestMapping("/product/toc")
public class ProductToCController {
    @DubboReference(timeout = 4000, retries = 0)
    EshopProdToCService eshopProdToCService;

    @ApiMonitor(isAuthor = false)
    @CrossOrigin
    @GetMapping("/shop/index/prod/get")
    public GetEshopIndexProdsResponse getEshopIndexProds() {
        return eshopProdToCService.getEshopIndexProds();
    }
}
