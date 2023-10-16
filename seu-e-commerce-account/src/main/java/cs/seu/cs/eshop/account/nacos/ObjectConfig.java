package cs.seu.cs.eshop.account.nacos;

import cn.seu.cs.eshop.common.conf.ConfDataId;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/13
 */
public enum ObjectConfig implements ConfDataId<Object> {
    accountTest("default"),


    ;

    private final String dataId;

    private final Object defaultData;

    ObjectConfig(String dataId, Object defaultData) {
        this.dataId = dataId;
        this.defaultData = defaultData;
    }

    ObjectConfig(Object defaultData) {
        this.dataId = StringUtils.EMPTY;
        this.defaultData = defaultData;
    }

    ObjectConfig() {
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

    @SuppressWarnings("unchecked")
    @Override
    public Object getDefaultData() {
        return this.defaultData;
    }

}
