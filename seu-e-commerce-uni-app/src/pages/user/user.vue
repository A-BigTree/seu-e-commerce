<template>
  <view class="container">
    <view class="userinfo" v-if="isAuthInfo">
      <view class="userinfo-con">
        <view class="userinfo-avatar" @click="selectImage">
          <image :src="loginResult.image ? picDomain1 + loginResult.image: '/static/images/icon/head04.png'"></image>
        </view>
        <view class="userinfo-name" @click="toUpdatePage">
          <view>{{ loginResult.nickname ? loginResult.nickname : "用户昵称" }}</view>
        </view>
      </view>
    </view>

    <view class="userinfo-none" v-if="!isAuthInfo">
      <view class="default-pic" @tap="toLogin">
        <image src="../../static/images/icon/head04.png"></image>
      </view>
      <view class="none-login" @tap="toLogin">
        <button class="unlogin">未登录</button>
        <button class="click-login">点击登录账号</button>
      </view>
    </view>
    <!-- end 用户信息 -->

    <view class="list-cont">

      <!-- 订单状态 -->
      <view class="total-order">
        <view class="order-tit">
          <text style="font-weight:bold">我的订单</text>
          <view class="checkmore" @tap="toOrderListPage" data-sts="0">
            <text>查看全部</text>
            <text class="arrowhead"></text>
          </view>
        </view>
        <!--
				<view class="procedure">
					<view class="items" @tap="toOrderListPage" data-sts="1">
						<image src="/static/images/icon/toPay.png"></image>
						<text>待支付</text>
						<text class="num-badge" v-if="orderAmount.unPay>0">{{orderAmount.unPay}}</text>
					</view>
					<view class="items" @tap="toOrderListPage" data-sts="2">
						<image src="/static/images/icon/toDelivery.png"></image>
						<text>待发货</text>
						<text class="num-badge" v-if="orderAmount.payed>0">{{orderAmount.payed}}</text>
					</view>
					<view class="items" @tap="toOrderListPage" data-sts="3">
						<image src="/static/images/icon/toTake.png"></image>
						<text>待收货</text>
						<text class="num-badge" v-if="orderAmount.consignment>0">{{orderAmount.consignment}}</text>
					</view>
					<view class="items" @tap="toOrderListPage" data-sts="5">
						<image src="/static/images/icon/toComment.png"></image>
						<text>已完成</text>
					</view>
				</view>-->
      </view>
      <!--end 订单状态 -->

      <view class="prod-col">
        <view class="col-item" @tap="myCollectionHandle">
          <view v-if="loginResult" class="num">{{ collectionCount }}</view>
          <view v-else class="num">--</view>
          <view class="tit">我的收藏</view>
        </view>
        <view class="col-item" @tap="handleTips">
          <view v-if="loginResult" class="num">5</view>
          <view v-else class="num">--</view>
          <view class="tit">我的消息</view>
        </view>
        <view class="col-item" @tap="handleTips">
          <view v-if="loginResult" class="num">3</view>
          <view v-else class="num">--</view>
          <view class="tit">我的足迹</view>
        </view>
      </view>

      <view class="my-menu">
        <view class="memu-item" @tap="toAddressList">
          <view class="i-name">
            <image src="/static/images/icon/myAddr.png"></image>
            <text>收货地址</text>
          </view>
          <view class="arrowhead"></view>
        </view>
      </view>
      <!--end 列表项 -->

      <view class="log-out" @tap="logout" v-if="isAuthInfo">
        <view class="log-out-n">
          <text>退出登录</text>
        </view>
      </view>
    </view>

  </view>
</template>

<script setup>

import {ref} from "vue";
import {request} from "@/utils/http"
import {domain, picDomain} from "@/utils/config"
import {onShow} from "@dcloudio/uni-app";

const orderAmount = ref("");
const sts = ref("");
const collectionCount = ref(0);
const isAuthInfo = ref(false);
const loginResult = ref("");
const picDomain1 = ref("");

const init = () => {
  const params = {
    url: "/account/user/info/get",
    method: "GET",
    callBack: (response) => {
      const user = response.data;
      uni.setStorageSync("userInfo", user);
    }
  };
  request(params);
    loginResult.value = uni.getStorageSync("userInfo")
  if (loginResult.value) {
      isAuthInfo.value = true,
      picDomain1.value = picDomain

  } else {
      isAuthInfo.value = false
  }
  if (isAuthInfo.value) {
    // TODO 个人订单信息
  }
}

onShow(() => {
  init();
})

const selectImage = () => {
  uni.chooseImage({
    count: 1,
    success: function (result) {
      uni.showLoading({
        title: "Loading",
        mask: true
      }).then(r => {
      });
      const paths = result.tempFilePaths;
      uni.uploadFile({
        url: domain + "/account/user/head/load",
        filePath: paths[0],
        name: "photo",
        header: {
          "Access-Control-Allow-Origin": "*",
          "Authorization": uni.getStorageSync("token"),
        },
        success: (res) => {
          uni.hideLoading();
          console.log(res);
          const data = JSON.parse(res.data);
          console.log(data);
          if (data.code === 200) {
            uni.showToast({
              title: "头像修改成功~",
              icon: "success"
            });
            uni.reLaunch({
              url: "/pages/user/user"
            });
          } else {
            uni.showToast({
              title: "头像修改失败",
              icon: "none"
            });
          }
        }
      });
      uni.hideLoading();
    }
  });

}

const toUpdatePage = function () {
  console.log("点击")
}

const handleTips = function () {

}

const toAddressList = function () {
  uni.navigateTo({
    url: '/pages/delivery-address/delivery-address'
  });
}

const toOrderListPage = function (e) {

}

/**
 * 查询所有的收藏量
 */
const showCollectionCount = function () {

}

/**
 * 我的收藏跳转
 */
const myCollectionHandle = function () {

}

/**
 * 去登陆
 */
const toLogin = function () {
  uni.navigateTo({
    url: "/pages/login/login"
  })
}

/**
 * 退出登录
 */
const logout = function () {
  const params = {
    url: "/account/user/logout",
    callBack: (res) => {
      uni.removeStorageSync("userInfo");
      uni.removeStorageSync("token");
      this.setData({
        isAuthInfo: false
      });
    }
  };
  request(params)
}
</script>

<style scoped>
@import "./user.css";
</style>
