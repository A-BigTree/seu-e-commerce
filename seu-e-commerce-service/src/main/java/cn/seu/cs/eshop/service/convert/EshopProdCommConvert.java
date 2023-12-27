package cn.seu.cs.eshop.service.convert;

import cn.seu.cs.eshop.service.pojo.db.EshopProdCommDO;
import cn.seu.cs.eshop.service.sdk.order.comm.dto.EshopProdCommDTO;
import cs.seu.cs.eshop.common.sdk.util.TimeUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static cs.seu.cs.eshop.common.sdk.enums.EshopStatusEnum.VALID;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/27
 */
public class EshopProdCommConvert {
    public static EshopProdCommDTO convertToEshopProdCommDTO(EshopProdCommDO item) {
        if (item == null) {
            return null;
        }
        return EshopProdCommDTO.builder()
                .id(item.getId())
                .userId(item.getEvaluate() == VALID.getStatus() ? 0 : item.getUserId())
                .userName(item.getEvaluate() == VALID.getStatus() ? "匿名用户" : item.getUserName())
                .prodId(item.getProdId())
                .orderItemId(item.getOrderItemId())
                .content(item.getContent())
                .score(item.getScore())
                .status(item.getStatus())
                .images(StringUtils.isEmpty(item.getImages()) ? null : List.of(item.getImages().split(";")))
                .commType(item.getCommType())
                .evaluate(item.getEvaluate())
                .createTime(TimeUtils.convertString(item.getCreateTime(), TimeUtils.DATE_TIME_FORMAT))
                .build();
    }

    public static EshopProdCommDO convertToEshopProdCommDO(EshopProdCommDTO item) {
        if (item == null) {
            return null;
        }
        EshopProdCommDO result = new EshopProdCommDO();
        result.setUserId(item.getUserId());
        result.setUserName(item.getUserName());
        result.setProdId(item.getProdId());
        result.setOrderItemId(item.getOrderItemId());
        result.setContent(item.getContent());
        result.setScore(item.getScore());
        result.setStatus(item.getStatus());
        result.setImages(String.join(";", item.getImages()));
        result.setCommType(item.getCommType());
        result.setEvaluate(item.getEvaluate());
        result.setId(item.getId() > 0 ? item.getId() : null);
        return result;
    }
}
