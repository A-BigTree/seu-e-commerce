<template>
  <view class="container">


    <view class="userinfo" v-if="isAuthInfo">
      <view class="userinfo-con">
        <view class="userinfo-avatar">
          <image :src="loginResult.image ? (loginResult.image.indexOf('http') === -1 ? picDomain + loginResult.image : loginResult.pic) : '/static/images/icon/head04.png'"></image>
        </view>
        <view class="userinfo-name">
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
        <view class="memu-item" @tap="toDistCenter">
          <view class="i-name">
            <image src="/static/images/icon/promotion.png"></image>
            <text>分销中心</text>
          </view>
          <view class="arrowhead"></view>
        </view>
        <view class="memu-item" @tap="toCouponCenter">
          <view class="i-name">
            <image src="/static/images/icon/getCoupon.png"></image>
            <text>领券中心</text>
          </view>
          <view class="arrowhead"></view>
        </view>
        <view class="memu-item" @tap="toMyCouponPage">
          <view class="i-name">
            <image src="/static/images/icon/myCoupon.png"></image>
            <text>我的优惠券</text>
          </view>
          <view class="arrowhead"></view>
        </view>
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

<script>
const http = require("@/utils/http")
export default {
  data() {
    return {
      orderAmount: '',
      sts: '',
      collectionCount: 0,
      isAuthInfo: false,
      loginResult: '',
      picDomain: ''
    };
  },

  components: {},
  props: {},

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
    uni.setNavigationBarTitle({
      title: "个人中心"
    });
    this.setData({
      loginResult: uni.getStorageSync("userInfo")
    });
    if (this.loginResult) {
      this.setData({
        isAuthInfo: true
      });
    } else {
      this.setData({
        isAuthInfo: false
      });
    }
    if (this.isAuthInfo) {
      // TODO 个人订单信息
    }
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
    toDistCenter: function () {

    },
    toCouponCenter: function () {

    },
    toMyCouponPage: function () {

    },
    handleTips: function () {

    },
    toAddressList: function () {

    },
    toOrderListPage: function (e) {

    },

    /**
     * 查询所有的收藏量
     */
    showCollectionCount: function () {

    },

    /**
     * 我的收藏跳转
     */
    myCollectionHandle: function () {

    },

    /**
     * 去登陆
     */
    toLogin: function () {
      uni.navigateTo({
        url: "/pages/login/login"
      })
    },

    /**
     * 退出登录
     */
    logout: function () {
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
      http.request(params)
    },
  }

};
</script>
<style>
@import "./user.css";
</style>
