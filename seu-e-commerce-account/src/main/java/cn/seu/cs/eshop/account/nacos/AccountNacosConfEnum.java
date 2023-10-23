package cn.seu.cs.eshop.account.nacos;

import cn.seu.cs.eshop.common.constants.ConfigConstants;
import cn.seu.cs.eshop.common.nacos.ConfDataId;
import cn.seu.cs.eshop.common.constants.ApplicationConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/13
 */
public enum AccountNacosConfEnum implements ConfDataId<Object> {
    accountTest("default"),
    fromEmail("xxx@xxx.xx"), //邮件发送配置
    emailContext("默认内容"), //邮件内容配置
    emailVerifyTime(5L), //验证码有效时间
    emailVerifyLength(6), //验证码长度
    emailVerifySymbols("0123456789")
    ;

    private final String dataId;

    private final Object defaultData;

    AccountNacosConfEnum(String dataId, Object defaultData) {
        this.dataId = dataId;
        this.defaultData = defaultData;
    }

    AccountNacosConfEnum(Object defaultData) {
        this.dataId = StringUtils.EMPTY;
        this.defaultData = defaultData;
    }

    AccountNacosConfEnum() {
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
        return ConfigConstants.ACCOUNT_CONFIG;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getDefaultData() {
        return this.defaultData;
    }

}
