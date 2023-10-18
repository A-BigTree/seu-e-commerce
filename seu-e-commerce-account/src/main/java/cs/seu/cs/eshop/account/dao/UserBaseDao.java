package cs.seu.cs.eshop.account.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cs.seu.cs.eshop.account.pojo.db.UserBaseDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@Mapper
public interface UserBaseDao extends MysqlBaseDao<UserBaseDO> {

}
