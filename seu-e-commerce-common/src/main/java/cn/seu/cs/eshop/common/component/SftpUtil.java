package cn.seu.cs.eshop.common.component;

import cn.seu.cs.eshop.common.configuration.SftpGenericObjectPool;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/26
 */
@Component
public class SftpUtil {
    @Resource
    private SftpGenericObjectPool pool;


    /**
     * 文件是否存在
     */
    public boolean isExist(String filePath) throws Exception {
        ChannelSftp sftp = pool.borrowObject();
        try {
            sftp.lstat(filePath);
        } catch (SftpException se) {
            if (se.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                return false;
            }
        } finally {
            pool.returnObject(sftp);
        }
        return true;
    }

    /**
     * 递归创建多级目录
     *
     * @param dir 多级目录
     */
    private void mkdirs(ChannelSftp sftp, String dir) throws SftpException {
        String[] folders = dir.split("/");
        sftp.cd("/");
        for (String folder : folders) {
            if (!folder.isEmpty()) {
                try {
                    sftp.cd(folder);
                } catch (Exception e) {
                    sftp.mkdir(folder);
                    sftp.cd(folder);
                }
            }
        }
    }

    /**
     * 上传文件
     *
     * @param dir  远程目录
     * @param name 远程文件名
     * @param in   输入流
     */
    public void upload(String dir, String name, InputStream in) throws Exception {
        ChannelSftp sftp = pool.borrowObject();
        try {
            mkdirs(sftp, dir);
            sftp.cd(dir);
            sftp.put(in, name);
        } finally {
            pool.returnObject(sftp);
        }
    }
}
