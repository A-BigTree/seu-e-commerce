package cn.seu.cs.eshop.common.conf;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/13
 */
public interface ConfDataId<T> {
    String getDataId();

    <D extends T> D getDefaultData();
}
