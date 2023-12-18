package cn.seu.cs.eshop.task.nacos;

import cn.seu.cs.eshop.common.constants.ConfigConstants;
import cn.seu.cs.eshop.common.nacos.ConfDataId;
import cn.seu.cs.eshop.task.bo.EmailSendClientBO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
public enum TaskNacosConfEnum implements ConfDataId<Object> {
    emailSendClientConf(new EmailSendClientBO()),
    unpaidOrderTimeout(900000L),

    ;

    private final String dataId;

    private final Object defaultData;

    TaskNacosConfEnum(String dataId, Object defaultData) {
        this.dataId = dataId;
        this.defaultData = defaultData;
    }

    TaskNacosConfEnum(Object defaultData) {
        this.dataId = StringUtils.EMPTY;
        this.defaultData = defaultData;
    }

    TaskNacosConfEnum() {
        this.dataId = StringUtils.EMPTY;
        this.defaultData = null;
    }


    @Override
    public String getDataId() {
        if (StringUtils.isEmpty(dataId)) {
            return this.name();
        }
        return dataId;
    }

    @Override
    public String getApplication() {
        return ConfigConstants.TASK_CONFIG;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getDefaultData() {
        return this.defaultData;
    }
}
