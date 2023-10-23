<template>
	<view class="con">
		<image src="/static/logo.png"></image>
		<!-- 登录 -->
		<view class="login-form">
			<view :class="['item',errorTips===1? 'error':'']">
				<view class="account">
					<text class="input-item">邮箱</text>
					<input type="text" @input="getInputVal" data-type="account" placeholder-class="inp-palcehoder" placeholder="请输入用户名"></input>
				</view>
				<view class="error-text" v-if="errorTips===1"><text class="warning-icon">!</text>请输入正确的邮箱！</view>
			</view>
			<view :class="['item',errorTips===2? 'error':'']">
				<view class="account">
					<text class="input-item">密码</text>
					<input type="password" @input="getInputVal" data-type="password" placeholder-class="inp-palcehoder" placeholder="请输入密码"></input>
				</view>
				<view class="error-text" v-if="errorTips===2"><text class="warning-icon">!</text>请输入密码！</view>
			</view>
			<view class="operate">
				<view class="to-register" @tap="toRegister">还没有账号？<text>去注册></text></view>
			</view>
		</view>

		<view>
			<button class="authorized-btn" @tap="login">登录</button>
			<button class="to-idx-btn" @tap="toIndex">回到首页</button>
		</view>

	</view>
</template>

<script>
const config = require("@/utils/config")
const http = require("@/utils/http")
const util = require("@/utils/util")
import {md5} from "js-md5"
  export default {
		data() {
			return {
				principal: '', // 账号
				credentials: '', // 密码
				errorTips: 0, //错误提示

			};
		},

		components: {},
		props: {},

		computed: {},

		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			if (options.isPersonalCenter) {
				this.isPersonalCenter = options.isPersonalCenter
			}
		},

		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {},

		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			//头部导航标题
			uni.setNavigationBarTitle({
				title: "用户登录"
			});
		},

		/**
		 * 生命周期函数--监听页面隐藏
		 */
		onHide: function() {},

		/**
		 * 生命周期函数--监听页面卸载
		 */
		onUnload: function() {},

		/**
		 * 页面相关事件处理函数--监听用户下拉动作
		 */
		onPullDownRefresh: function() {},

		/**
		 * 页面上拉触底事件的处理函数
		 */
		onReachBottom: function() {},

		/**
		 * 用户点击右上角分享
		 */
		onShareAppMessage: function() {},

		methods: {
			/**
			 * 输入框的值
			 */
			getInputVal: function(e) {
				const type = e.currentTarget.dataset.type;
				if (type === 'account') {
					this.setData({
						principal: e.detail.value
					});
				} else if (type === 'password') {
					this.setData({
						credentials: e.detail.value
					});
				}
			},

			/**
			 * 登录
			 */
			login() {
				if (this.principal.length === 0) {
					this.setData({
						errorTips: 1
					})
				} else if (this.credentials.length === 0) {
					this.setData({
						errorTips: 2
					});
				} else {
          this.setData({
            errorTips: 0
          });
          const params = {
            url: "/account/user/login",
            data: {
              account: this.principal,
              password: md5(this.credentials + config.salt),
              roleType: 1
            },
            callBack: function (res) {
              const session = res.data;
              uni.setStorageSync("userInfo", session.user);
              uni.setStorageSync("token", session.token);
              uni.showToast({
                title: "登录成功",
                icon: "success",
                complete: () => {
                  setTimeout(() => {
                    uni.switchTab({
                      url: "/pages/user/user"
                    });
                  }, 500)
                }
              });
            },
            errCallBack: (res) => {
              if (res.code === 503) {
                uni.showToast({
                  title: "账号或密码不正确",
                  icon: "none",
                });
              } else {
                uni.showToast({
                  title: "服务器出了点小差~",
                  icon: "none"
                });
              }
            }
          };
          http.request(params);
        }
			},

			/**
			 * 去注册
			 */
			toRegister() {
				uni.navigateTo({
					url: "/pages/register/register"
				})
			},

			/**
			 * 回到首页
			 */
			toIndex() {
        uni.switchTab({
          url: "/pages/user/user"
        });
			}

		}
	};
</script>
<style>
	@import "login.css";
</style>
