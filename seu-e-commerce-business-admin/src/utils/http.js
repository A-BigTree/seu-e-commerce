import axios from "axios";
import {getToken, removeCookies} from "@/utils/cookies";
import router from "@/router";
import {ElMessage, ElLoading, ElMessageBox} from "element-plus";
import {API_URL, TIMEOUT} from "@/utils/config";
import {loadingConfig} from "@/utils/index";

const http = function (params) {
    let loading = params.closeLoading ? null : ElLoading.service(loadingConfig(params.loadingText ? params.loadingText : 'loading'));
    axios.request({
        baseURL: params.domain ? params.domain : API_URL,
        url: params.url,
        method: params.method ? params.method : 'post',
        headers: {
            "Access-Control-Allow-Origin": "*",
            "Authorization": getToken(),
            "Content-Type": params.isFile ?
                'multipart/form-data' : "application/json",
        },
        timeout: TIMEOUT,
        data: params.data,
    }).then((response) => {
        if (response.status === 200) {
            const data = response.data;
            // 请求成功
            if (data.code === 200) {
                if (params.callBack) {
                    params.callBack(data);
                }
                return;
            }
            // 认证失败
            if (data.code === 501 || data.code === 502) {
                removeCookies('token');
                ElMessageBox.confirm(data.msg,
                    'warning',
                    {
                        confirmButtonText: '确认',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }
                ).then(() => {
                    router.push({
                        name: 'login'
                    }).then();
                });
                return;
            }
            // 用户操作错误
            if (data.code === 503) {
                if (params.errCallBack) {
                    params.errCallBack(data);
                    return;
                }
                ElMessage({
                    message: data.data || data.msg || 'Error',
                    type: 'error'
                });
                return;
            }
            // 权限错误
            if (data.code === 504) {
                if (params.errCallBack) {
                    params.errCallBack(data);
                    return;
                }
                ElMessage({
                    message: data.msg || '权限错误',
                    type: 'error'
                });
                return;
            }
            // 服务器错误
            if (data.code === 500) {
                console.error('============== 请求异常 ==============')
                console.log('接口: ', params.url)
                console.log('异常信息: ', response)
                console.error('============== 请求异常 ==============')
                if (params.errCallBack) {
                    params.errCallBack(response)
                    return;
                }
                ElMessage({
                    message: '服务器出了点小差~',
                    type: 'error'
                });
                return;
            }
            // 其他错误
            if (data.code === 600) {
                if (params.errCallBack) {
                    params.errCallBack(data)
                } else {
                    console.log(`接口: ${params.url}`)
                    console.log(`返回信息: ${response}`);
                }
            }
        } else {
            ElMessage({
                message: '服务器出了点小差~',
                type: 'error'
            });
        }
    }).catch((error) => {
        ElMessage({
            message: '服务器出了点小差~' + error,
            type: 'error'
        });
    });
    if (loading !== null) {
        loading.close();
    }
}

export {
    http,
}

