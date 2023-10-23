package cn.seu.cs.eshop.account.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.seu.cs.eshop.account.pojo.db.EmailVerifyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@Mapper
public interface EmailVerifyDao extends MysqlBaseDao<EmailVerifyDO> {
    /**
     * 根据创建时间降序返回
     */
    default List<EmailVerifyDO> selectedByAccount(String account) {
        EmailVerifyDO entity = new EmailVerifyDO();
        entity.setAccount(account);
        QueryWrapper<EmailVerifyDO> wrapper = new QueryWrapper<>(entity);
        wrapper.orderByDesc("create_time");
        return selectList(wrapper);
    }
}
