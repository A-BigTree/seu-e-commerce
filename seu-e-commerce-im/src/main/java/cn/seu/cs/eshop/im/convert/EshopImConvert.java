package cn.seu.cs.eshop.im.convert;

import cn.seu.cs.eshop.im.db.EshopImMessageDO;
import cs.seu.cs.eshop.common.sdk.entity.dto.EshopImMessageDTO;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/29
 */
public class EshopImConvert {
    public static EshopImMessageDO convertToEshopImMessageDO(EshopImMessageDTO item) {
        if (item == null) {
            return null;
        }
        EshopImMessageDO result = new EshopImMessageDO();
        result.setMsgType(item.getMsgType());
        result.setFromUserId(item.getFromUserId());
        result.setToUserId(item.getToUserId());
        result.setStatus(item.getStatus());
        result.setContent(item.getContent());
        result.setId(item.getId() != null && item.getId() > 0 ? item.getId() : null);
        return result;
    }

    public static EshopImMessageDTO convertToEshopImMessageDTO(EshopImMessageDO item) {
        if (item == null) {
            return null;
        }
        return EshopImMessageDTO.builder()
                .id(item.getId())
                .msgType(item.getMsgType())
                .fromUserId(item.getFromUserId())
                .toUserId(item.getToUserId())
                .status(item.getStatus())
                .content(item.getContent())
                .createTime(item.getCreateTime())
                .build();
    }
}
