package cn.seu.cs.eshop.account.service.impl;

import cn.seu.cs.eshop.account.convert.AccountConvert;
import cn.seu.cs.eshop.account.dao.UserInfoDao;
import cn.seu.cs.eshop.account.pojo.db.UserInfoDO;
import cn.seu.cs.eshop.account.sdk.entity.dto.RegisterUserListDTO;
import cn.seu.cs.eshop.account.sdk.entity.req.ListRegisterInfoRequest;
import cn.seu.cs.eshop.account.sdk.entity.req.ListRegisterInfoResponse;
import cn.seu.cs.eshop.account.service.UserInfoService;
import cn.seu.cs.eshop.common.enums.RegisterStateEnum;
import cn.seu.cs.eshop.common.enums.UserRoleEnum;
import cn.seu.cs.eshop.common.util.ResponseBuilderUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static cn.seu.cs.eshop.account.convert.AccountConvert.convert;
import static cn.seu.cs.eshop.common.util.MysqlUtils.buildPageData;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    UserInfoDao userInfoDao;

    @Override
    public ListRegisterInfoResponse listRegisterInfo(ListRegisterInfoRequest request) {
        UserInfoDO entity = new UserInfoDO();
        if (request.getId() != null && request.getId() > 0) {
            entity.setId(request.getId());
        }
        if (!StringUtils.isEmpty(request.getEmail())) {
            entity.setAccount(request.getEmail());
        }
        if (request.getRegisterState() != RegisterStateEnum.DEFAULT.getState()) {
            entity.setState(request.getRegisterState());
        }
        if (request.getRoleType() != UserRoleEnum.DEFAULT.getValue()) {
            entity.setRoleType(request.getRoleType());
        }
        IPage<UserInfoDO> result = userInfoDao.selectPagesByConditions(entity, request.getNickname(), request.getPage());
        RegisterUserListDTO data = buildPageData(RegisterUserListDTO.class, result, AccountConvert::convert);
        return ResponseBuilderUtils.buildSuccessResponse(ListRegisterInfoResponse.class, data);
    }
}
