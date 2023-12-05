<template>
  <view class="con">
    <image src="/static/logo.png"></image>
    <!-- 登录 -->
    <view class="login-form">
      <view :class="['item',errorTips===1? 'error':'']">
        <view class="account">
          <text class="input-item">邮箱</text>
          <input type="text" @input="getInputVal" data-type="account" placeholder-class="inp-palcehoder"
                 placeholder="请输入用户名"/>
        </view>
        <view class="error-text" v-if="errorTips===1">
          <text class="warning-icon">!</text>
          请输入正确的邮箱！
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
      <view class="operate">
        <view class="to-register" @tap="toRegister">还没有账号？
          <text>去注册></text>
        </view>
      </view>
    </view>

    <view>
      <button class="authorized-btn" @tap="login">登录</button>
      <button class="to-idx-btn" @tap="toIndex">回到首页</button>
    </view>

  </view>
</template>

<script setup>
import {salt} from "../../utils/config";
import {request} from "../../utils/http"
import {md5} from "js-md5"
import {ref} from "vue";

const principal = ref("");
const credentials = ref("");
const errorTips = ref(0);


const getInputVal = function (e) {
  const type = e.currentTarget.dataset.type;
  if (type === 'account') {
      principal.value = e.detail.value;
  } else if (type === 'password') {
      credentials.value = e.detail.value;
  }
}

/**
 * 登录
 */
const login = () => {
  if (principal.value.length === 0) {
      errorTips.value = 1

  } else if (credentials.value.length === 0) {
      errorTips.value = 2
  } else {
      errorTips.value = 0
    const params = {
      url: "/account/user/login",
      data: {
        account: principal.value,
        password: md5(credentials.value + salt),
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
    request(params);
  }
}

/**
 * 去注册
 */
const toRegister = () => {
  uni.navigateTo({
    url: "/pages/register/register"
  })
}

/**
 * 回到首页
 */
const toIndex = () => {
  uni.switchTab({
    url: "/pages/user/user"
  });
}

</script>

<style scoped>
@import "login.css";
</style>
