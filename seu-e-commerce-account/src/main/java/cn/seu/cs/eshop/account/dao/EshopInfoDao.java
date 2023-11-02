package cn.seu.cs.eshop.account.dao;

import cn.seu.cs.eshop.account.pojo.db.EshopInfoDO;
import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
@Mapper
public interface EshopInfoDao extends MysqlBaseDao<EshopInfoDO> {

}
