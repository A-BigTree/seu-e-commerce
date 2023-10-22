<template>
  <view class="register">
    <view class="con">
      <image src="../../static/logo.png"></image>
      <!-- 登录 -->
      <view class="login-form">
        <view :class="['item',errorTips===1? 'error':'']">
          <view class="account">
            <text class="input-item">邮箱</text>
            <input type="text" @input="getInputVal" data-type="account" placeholder-class="inp-palcehoder"
                   placeholder="请输入注册邮箱"></input>
          </view>
          <view class="error-text" v-if="errorTips===1">
            <text class="warning-icon">!</text>
            请正确输入注册邮箱！
          </view>
        </view>
        <view :class="['item',errorTips===4? 'error':'']">
          <view class="account">
            <input type="text" @input="getInputVal" data-type="verifyCode" placeholder-class="inp-palcehoder"
                   placeholder="验证码"></input>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button class="verify-bnt" @tap="sendEmail" :disabled="isSend">{{ sendContext }}</button>
          </view>

          <view class="error-text" v-if="errorTips===4">
            <text class="warning-icon">!</text>
            请输入验证码！
          </view>
        </view>
        <view :class="['item',errorTips===2? 'error':'']">
          <view class="account">
            <text class="input-item">密码</text>
            <input type="password" @input="getInputVal" data-type="password" placeholder-class="inp-palcehoder"
                   placeholder="请输入密码"></input>
          </view>
          <view class="error-text" v-if="errorTips===2">
            <text class="warning-icon">!</text>
            请输入密码！
          </view>
        </view>
        <view :class="['item',errorTips===3? 'error':'']">
          <view class="account">
            <text class="input-item">确认密码</text>
            <input type="password" @input="getInputVal" data-type="password2" placeholder-class="inp-palcehoder"
                   placeholder="请确认密码"></input>
          </view>
          <view class="error-text" v-if="errorTips===3">
            <text class="warning-icon">!</text>
            两次密码输入不一致！
          </view>
        </view>
        <view class="operate">
          <view class="to-register" @tap="toLogin">已有账号？
            <text>去登录></text>
          </view>
        </view>
      </view>

      <view>
        <button class="authorized-btn" @tap="toRegister">注册</button>
        <button class="to-idx-btn" @tap="toIndex">回到首页</button>
      </view>

    </view>
  </view>
</template>

<script>
const salt = require("@/utils/config")
const http = require("@/utils/http")
const util = require("@/utils/util")
import {md5} from "js-md5"

export default {
  data() {
    return {
      errorTips: 0, // 输入错误提示:  1账号输入  2密码输入
      principal: '', // 邮箱
      verifyCode: "", // 验证码
      credentials: '', // 密码
      credentials2: '', //确认密码
      sendContext: "发送验证码",
      isSend: false
    };
  },

  components: {},
  props: {},

  computed: {},

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    //头部导航标题
    uni.setNavigationBarTitle({
      title: "用户注册"
    });
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  },
  methods: {
    /**
     * 输入框的值
     */
    getInputVal: function (e) {
      const type = e.currentTarget.dataset.type;
      if (type === 'account') {
        this.setData({
          principal: e.detail.value
        });
      } else if (type === 'password') {
        this.setData({
          credentials: e.detail.value
        });
      } else if (type === 'password2') {
        this.setData({
          credentials2: e.detail.value
        });
      } else if (type === 'verifyCode') {
        this.setData({
          verifyCode: e.detail.value
        });
      }
    },


    /**
     * 注册
     */
    toRegister() {
      if (this.principal.length === 0 || !util.checkEmail(this.principal)) {
        this.setData({
          errorTips: 1
        });
      } else if (this.credentials.length === 0) {
        this.setData({
          errorTips: 2
        });
      } else if(this.verifyCode.length === 0) {
        this.setData({
          errorTips: 4
        })
      } else if (this.credentials2.length === 0 || this.credentials2 !== this.credentials) {
        this.setData({
          errorTips: 3
        });
      } else {
        this.setData({
          errorTips: 0
        })
        // TODO 注册逻辑
      }
    },
    /**
     * 去登陆
     */
    toLogin: function () {
      uni.navigateTo({
        url: "/pages/login/login"
      });
    },

    /**
     * 回到首页
     */
    toIndex: function () {
      uni.switchTab({
        url: '/pages/index/index'
      });
    },
    // 发送验证码
    sendEmail() {
      if (this.principal.length === 0 || !util.checkEmail(this.principal)) {
        console.info(this.principal);
        console.info(util.checkEmail(this.principal))
        this.setData({
          errorTips: 1
        });
        return;
      }
      this.setData({
        errorTips: 0,
        isSend: true,
        sendContext: "发送验证码"
      });
      const params = {
        url: "/account/send/email/verify",
        data: {
          toEmail: this.principal,
          fromEmail: "",
          context: ""
        },
        callBack: function (data) {
          uni.showToast({
            title: "验证码发送成功",
            icon: "success"
          });
        }
      }
      console.info(params);
      http.request(params);
      util.countDown(60, (value) => {
        this.setData({
          sendContext: value + "s后重试"
        });
        if (value === 0) {
          this.setData({
            sendContext: "发送验证码",
            isSend: false
          });
        }
      });
    }
  }
}
</script>
<style>
@import "register.css";
</style>
