const config = require("@/utils/config");
/**
 * 统一请求
 * @param {Object} params 请求参数
 */
const request = function(params) {
	const paramType = Object.prototype.toString.call(params.data);
	if (paramType === "[object Array]") {
		params.data = JSON.stringify(params.data);
	} else if (paramType === "[object Number]") {
		params.data = params.data + "";
	}
	console.log(config.domain);
	uni.request({
		url: (params.domain? params.domain : config.domain) + params.url,
		data: params.data,
		header: {
			"Access-Control-Allow-Origin": "*",
			"Authorization": uni.getStorageSync("token"),
		},
		method: params.method === undefined ? "POST" : params.method,
		dataType: "json",
		success: function (res) {
			const response = res.data;
			// 请求成功
			if (response.code === 200) {
				if (params.callBack) {
					params.callBack(response);
				}
				return;
			}
			// 需要登录认证 or 登录时间过期
			if (response.code === 501 || response.code === 502) {
				uni.removeStorageSync("loginResult");
				uni.removeStorageSync("token");
				uni.showModal({
					title: "提示",
					content: response.data,
					cancelText: "取消",
					confirmText: "确定",
					success: res => {
						if (res.confirm) {
							uni.navigateTo({
								url: "pages/login/login"
							}).then(r=>{
							});
						} else {
							uni.navigateTo({
								url: "pages/index/index"
							}).then(r=>{
							});
						}
					}
				});
				return;
			}
			// 服务器问题
			 if (response.code === 500) {
				 console.error('============== 请求异常 ==============')
				 console.log('接口: ', params.url)
				 console.log('异常信息: ', response)
				 console.error('============== 请求异常 ==============')
				 if (params.errCallBack) {
					 params.errCallBack(response)
					 return;
				 }
				 uni.showToast({
					 title: '服务器出了点小差~',
					 icon: 'none'
				 }).then(r => {
				 });
				 return;
			 }
			 // 用户操作错误
			if (response.code === 503) {
				if (params.errCallBack) {
					params.errCallBack(response);
					return;
				}
				uni.showToast({
					title: response.data || 'Error',
					icon: "none"
				}).then(r => {
				});
				return;
			}
			// 其他错误
			if (response.code === 504) {
				if (params.errCallBack) {
					params.errCallBack(response)
				} else {
					console.log(`接口: ${params.url}`)
					console.log(`返回信息： `, res)
				}
			}

		},
		fail: function (err) {
			uni.hideLoading();
			if (err.errMsg === 'request:fail abort') {
				console.log('请求被取消啦~');
				return;
			}
			setTimeout(() => {
				uni.showToast({
					title: "服务器出了点小差",
					icon: "none"
				}).then(r => {
				});
			}, 1);
		}
	});
}

module.exports = {
	request,
}