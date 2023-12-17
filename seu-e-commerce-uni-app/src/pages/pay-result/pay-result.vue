<template>
  <view class="container">
    <view v-if="sts === 1">
      <view class="pay-sts wait">
        等待支付
      </view>
      <view class="tips">
        请在
        <text class="warn">
          30分钟
        </text>内完成付款
      </view>
      <view class="tips">
        否则订单会被系统取消
      </view>
      <view class="btns">
        <text
          class="button checkorder"
          @tap="toOrderList"
        >
          查看订单
        </text>
        <text
          class="button payagain"
          @tap="payAgain"
        >
          确定支付
        </text>
      </view>
    </view>

    <view v-if="sts === 2">
      <view class="pay-sts succ">
        支付成功
      </view>
      <view class="tips">
        感谢您的购买
      </view>
      <view class="btns">
        <text
          class="button checkorder"
          @tap="toOrderList"
        >
          查看订单
        </text>
        <text
          class="button shopcontinue"
          @tap="toIndex"
        >
          继续购物
        </text>
      </view>
    </view>

    <view v-if="sts === 5">
      <view class="pay-sts fail">
        订单已取消
      </view>
      <view class="tips">
        您可以重新下单
      </view>
      <view class="btns">
        <text
          class="button checkorder"
          @tap="toOrderList"
        >
          查看订单
        </text>
        <text
          class="button shopcontinue"
          @tap="toIndex"
        >
          继续购物
        </text>
      </view>
    </view>
  </view>
</template>

<script setup>
import {onLoad, onShow} from "@dcloudio/uni-app";
import {ref} from "vue";

const sts = ref(0)
const orderIds = ref([])
const orders = ref([])
/**
 * 生命周期函数--监听页面加载
 */
onShow(() => {
  sts.value = 0;
  orderIds.value = uni.getStorageSync("unpaidOrderIds");

})

const toOrderList = () => {
  uni.navigateTo({
    url: '/pages/orderList/orderList?sts=0'
  })
}
const toIndex = () => {
  uni.switchTab({
    url: '/pages/index/index'
  })
}
const payAgain = () => {

}
</script>

<style scoped lang="scss">
@use './pay-result.scss';
</style>
