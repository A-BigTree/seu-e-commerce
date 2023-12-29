package cn.seu.cs.eshop.im.store;

import cn.seu.cs.eshop.account.sdk.entity.dto.UserInfoDTO;
import cn.seu.cs.eshop.account.sdk.entity.req.GetUserInfoResponse;
import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import cn.seu.cs.eshop.common.kafka.service.EshopKafkaSendService;
import cn.seu.cs.eshop.im.dao.EshopImMessageDao;
import cn.seu.cs.eshop.im.db.EshopImMessageDO;
import cn.seu.cs.eshop.im.dto.EshopImMessageListItemDTO;
import cs.seu.cs.eshop.common.sdk.entity.dto.EshopImMessageDTO;
import jakarta.annotation.Resource;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.*;

import static cn.seu.cs.eshop.common.kafka.KafkaTopicConfEnum.IM_MESSAGE_TOPIC;
import static cn.seu.cs.eshop.im.convert.EshopImConvert.convertToEshopImMessageDTO;
import static cs.seu.cs.eshop.common.sdk.enums.EshopStatusEnum.INVALID;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/18
 */
@Service
public class ImMessageStoreService {
    @Resource
    private EshopImMessageDao eshopImMessageDao;
    @Resource
    private EshopKafkaSendService eshopKafkaSendService;
    @DubboReference(timeout = 5000, retries = 3)
    private EshopAccountService eshopAccountService;

    public List<EshopImMessageListItemDTO> getMessageList(Long userId) {
        List<EshopImMessageDO> messages = eshopImMessageDao.selectAllMessageByUserId(userId);
        if (CollectionUtils.isEmpty(messages)) {
            return new ArrayList<>();
        }
        Map<Long, EshopImMessageListItemDTO> messageMap = new HashMap<>();
        Map<Long, Long> unreadCountMap = new HashMap<>();
        messages.forEach(msg -> {
            Long toUserId = msg.getToUserId().equals(userId) ? msg.getFromUserId() : msg.getToUserId();
            if (!messageMap.containsKey(toUserId)) {
                GetUserInfoResponse userInfo = eshopAccountService.getUserInfo(toUserId);
                UserInfoDTO data = userInfo.getData();
                EshopImMessageListItemDTO messageListItem = EshopImMessageListItemDTO.builder()
                        .toUserId(toUserId)
                        .nickname(data.getNickname())
                        .headImg(data.getImage())
                        .lastMessage(convertToEshopImMessageDTO(msg))
                        .build();
                messageMap.put(toUserId, messageListItem);
            }
            if (Objects.equals(msg.getFromUserId(), toUserId) && msg.getStatus() == INVALID.getStatus()) {
                unreadCountMap.put(toUserId, unreadCountMap.getOrDefault(toUserId, 0L) + 1);
            }
        });
        messageMap.forEach((k, v) -> v.setUnreadCount(unreadCountMap.getOrDefault(k, 0L)));
        return new ArrayList<>(messageMap.values());
    }

    public void sendKafkaMessage(EshopImMessageDTO message) {
        eshopKafkaSendService.sendMessage(IM_MESSAGE_TOPIC, message);
    }
}
