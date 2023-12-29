package cn.seu.cs.eshop.common.kafka;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
public class KafkaTopicConstants {
    public static final String emailSendTopic = "eshop_email_send_topic";
    public static final String maxwellBinlogTopic = "eshop_maxwell_binlog";
    public static final String prodUserHistoryTopic = "eshop_prod_user_history_topic";
    public static final String orderProdItemTopic = "eshop_order_prod_item_topic";
    public static final String orderUnpaidTimeoutTopic = "eshop_order_unpaid_timeout_topic";
    public static final String imMessageTopic = "eshop_im_message_topic";
}
