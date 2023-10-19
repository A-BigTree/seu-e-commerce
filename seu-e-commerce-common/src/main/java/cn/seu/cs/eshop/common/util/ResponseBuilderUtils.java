package cn.seu.cs.eshop.common.util;

import cn.seu.cs.eshop.common.enums.ResponseStateEnum;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@Slf4j
public class ResponseBuilderUtils {
    public static <T extends BaseResponse> T buildResponse(Class<T> clazz, ResponseStateEnum responseState) {
        T response = null;
        try {
            response = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("build fail. e:", e);
            return response;
        }
        response.setCode(responseState.getCode());
        response.setMsg(responseState.getMsg());
        return response;
    }

    public static <T extends BaseResponse> T buildSuccessResponse(Class<T> clazz) {
        return buildResponse(clazz, ResponseStateEnum.OK);
    }

    public static <T extends BaseResponse> T buildFailResponse(Class<T> clazz) {
        return buildResponse(clazz, ResponseStateEnum.ERROR);
    }
}
