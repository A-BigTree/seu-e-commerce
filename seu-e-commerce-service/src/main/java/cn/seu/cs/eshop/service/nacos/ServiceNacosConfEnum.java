package cn.seu.cs.eshop.service.nacos;

import cn.seu.cs.eshop.common.constants.ConfigConstants;
import cn.seu.cs.eshop.common.nacos.ConfDataId;
import cn.seu.cs.eshop.service.bo.ProdReviewEmailBO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/6
 */
public enum ServiceNacosConfEnum implements ConfDataId<Object> {
    emailReviewContext(new ProdReviewEmailBO()),

    ;

    private final String dataId;

    private final Object defaultData;

    ServiceNacosConfEnum(String dataId, Object defaultData) {
        this.dataId = dataId;
        this.defaultData = defaultData;
    }

    ServiceNacosConfEnum(Object defaultData) {
        this.dataId = StringUtils.EMPTY;
        this.defaultData = defaultData;
    }

    ServiceNacosConfEnum() {
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
        return ConfigConstants.SERVICE_CONFIG;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getDefaultData() {
        return this.defaultData;
    }
}
