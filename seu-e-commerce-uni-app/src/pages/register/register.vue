<template>
  <view class="register">
    <view class="con">
      <image src="/static/logo.png"></image>
      <!-- 登录 -->
      <view class="login-form">
        <view :class="['item',errorTips===1? 'error':'']">
          <view class="account">
            <text class="input-item">邮箱</text>
            <input type="text" @input="getInputVal" data-type="account" placeholder-class="inp-palcehoder"
                   placeholder="请输入注册邮箱"/>
          </view>
          <view class="error-text" v-if="errorTips===1">
            <text class="warning-icon">!</text>
            请正确输入注册邮箱！
          </view>
        </view>
        <view :class="['item',errorTips===4? 'error':'']">
          <view class="account">
            <input type="text" @input="getInputVal" data-type="verifyCode" placeholder-class="inp-palcehoder"
                   placeholder="验证码"/>
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
                   placeholder="请输入密码"/>
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
                   placeholder="请确认密码"/>
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

<script setup>
import {salt} from "../../utils/config";
import {md5} from "js-md5"
import {request} from "../../utils/http";
import {checkEmail, countDown} from "../../utils/util";
import {ref} from "vue";

const errorTips = ref(0);
const principal = ref("");
const verifyCode = ref("");
const credentials = ref("");
const credentials2 = ref("");
const sendContext = ref("发送验证码");
const isSend = ref(false);

/**
 * 输入框的值
 */
const getInputVal = function (e) {
  const type = e.currentTarget.dataset.type;
  if (type === 'account') {
    principal.value = e.detail.value
  } else if (type === 'password') {
    credentials.value = e.detail.value
  } else if (type === 'password2') {
    credentials2.value = e.detail.value
  } else if (type === 'verifyCode') {
    verifyCode.value = e.detail.value;
  }
}


/**
 * 注册
 */
const toRegister = () => {
  if (principal.value.length === 0 || !checkEmail(principal.value)) {
    errorTips.value = 1
  } else if (credentials.value.length === 0) {
    errorTips.value = 2
  } else if (verifyCode.value.length === 0) {
    errorTips.value = 4
  } else if (credentials2.value.length === 0 || credentials2.value !== credentials.value) {
    errorTips.value = 3
  } else {
    errorTips.value = 0
    const params = {
      url: "/account/user/register",
      data: {
        account: principal.value,
        nickname: "",
        verifyCode: verifyCode.value,
        password: md5(credentials.value + salt),
        roleType: 1
      },
      callBack: function (data) {
        uni.showToast({
          title: "注册成功",
          icon: "success",
          complete: () => {
            setTimeout(() => {
              uni.navigateTo({
                url: "/pages/login/login"
              });
            }, 1000)
          }
        });
      }
    };
    request(params);
  }
}
/**
 * 去登陆
 */
const toLogin = function () {
  uni.navigateTo({
    url: "/pages/login/login"
  });
}

/**
 * 回到首页
 */
const toIndex = function () {
  uni.switchTab({
    url: "/pages/user/user"
  });
}

// 发送验证码
const sendEmail = () => {
  if (principal.value.length === 0 || !checkEmail(principal.value)) {
    errorTips.value = 1
    return;
  }
  errorTips.value = 0
  isSend.value = true
  sendContext.value = "发送验证码"
  const params = {
    url: "/account/send/email/verify",
    data: {
      toEmail: principal.value,
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
  request(params);
  countDown(60, (value) => {
    sendContext.value = value + "s后重试"
    if (value === 0) {
      sendContext.value = "发送验证码"
      isSend.value = false
    }
  });
}
</script>

<style>
@import "register.css";
</style>
