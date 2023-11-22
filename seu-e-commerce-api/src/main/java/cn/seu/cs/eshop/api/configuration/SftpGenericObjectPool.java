package cn.seu.cs.eshop.api.configuration;

import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/26
 */
public class SftpGenericObjectPool {
    private final GenericObjectPool<ChannelSftp> genericObjectPool;

    public SftpGenericObjectPool(SftpFactory sftpFactory,
                                 GenericObjectPoolConfig<ChannelSftp> sftpPoolConfig) {
        this.genericObjectPool = new GenericObjectPool<>(sftpFactory, sftpPoolConfig);
    }

    public ChannelSftp borrowObject() throws Exception {
        return genericObjectPool.borrowObject();
    }

    public void returnObject(ChannelSftp obj) {
        genericObjectPool.returnObject(obj);
    }
}
