package cn.seu.cs.eshop.common.configuration.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/26
 */
public class SftpFactory extends BasePooledObjectFactory<ChannelSftp> {
    @Value("${sftp.host}")
    private String host;

    @Value("${sftp.port}")
    private int port;

    @Value("${sftp.port}")
    private String username;

    @Value("${sftp.password}")
    private String password;

    @Override
    public ChannelSftp create() throws Exception {
        JSch jsch = new JSch();
        Session sshSession = jsch.getSession(username, host, port);
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        ChannelSftp channel = (ChannelSftp) sshSession.openChannel("sftp");
        channel.connect();
        return channel;
    }

    @Override
    public PooledObject<ChannelSftp> wrap(ChannelSftp channelSftp) {
        return new DefaultPooledObject<>(channelSftp);
    }

    @Override
    public void destroyObject(PooledObject<ChannelSftp> p) {
        ChannelSftp channelSftp = p.getObject();
        channelSftp.disconnect();
    }

    @Override
    public void activateObject(PooledObject<ChannelSftp> p) throws Exception {
        ChannelSftp channelSftp = p.getObject();
        if (!channelSftp.isConnected()) {
            channelSftp.connect();
        }
    }
}
