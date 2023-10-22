package cn.seu.cs.eshop.common.util;

import cn.seu.cs.eshop.common.enums.ResponseStateEnum;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponseInterface;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@Slf4j
public class ResponseBuilderUtils {
    public static <D, T extends BaseResponseInterface<D>> T buildResponse(Class<T> clazz, ResponseStateEnum responseState, D data) {
        T response = null;
        try {
            response = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("build fail. e:", e);
            return response;
        }
        response.setCode(responseState.getCode());
        response.setMsg(responseState.getMsg());
        response.setData(data);
        return response;
    }

    public static <D, T extends BaseResponseInterface<D>> T buildSuccessResponse(Class<T> clazz, D data) {
        return buildResponse(clazz, ResponseStateEnum.OK, data);
    }

    public static <D, T extends BaseResponseInterface<D>> T buildFailResponse(Class<T> clazz, D data) {
        return buildResponse(clazz, ResponseStateEnum.ERROR, data);
    }
}
