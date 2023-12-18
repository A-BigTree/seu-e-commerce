<template>
  <view class="container">
    <view v-if="sts === 1">
      <view class="pay-sts wait">
        等待支付
      </view>
      <view class="tips">
        请在
        <text class="warn">
          {{getDisplayTime(payTimeout)}}
        </text>内完成付款
      </view>
      <view class="tips">
        否则订单会被系统取消
      </view>
      <view class="pay-number">
        支付金额: ￥{{getDisplayPrice(payTotal)}}
      </view>
      <view class="pay-type">
        <radio-group @change="payTypeCheck">
          <radio value = "1" :checked="payType === '1'" class="pay-type-item">微信支付</radio>
          <radio value = "2" :checked="payType === '2'" class="pay-type-item">支付宝</radio>
          <radio value="3" :checked="payType === '3'" class="pay-type-item">银联支付</radio>
          <radio value="4" :checked="payType === '4'" class="pay-type-item">余额支付</radio>
        </radio-group>
      </view>
      <view class="btns">
        <text
            class="button payagain"
            @tap="toPay"
        >
          确定支付
        </text>

        <text
          class="button checkorder"
          @tap="toOrderList"
        >
          查看订单
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
        {{getCancelText(closeType)}}
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
import {ref, watch} from "vue";
import {countDown, getDisplayPrice} from "../../utils/util";
import {request} from "../../utils/http";

const sts = ref(1)
const orderIds = ref([])
const orders = ref([])

const initComplete = ref([]);
const paidComplete = ref([]);

const payType = ref("1");

const payTotal = ref(0);

const payTimeout = ref(0);

const closeType = ref(1);

// 初始信息加载完成
watch(initComplete, () => {
  if (initComplete.value.length > 0 && initComplete.value.length === orders.value.length) {
    payTimeout.value = 15 * 60;
    let time = 15 * 60;
    initComplete.value.forEach(data => {
      if (data.status === 5) {
        sts.value = 5;
        closeType.value = data.closeType;
        return;
      }
      const timeout = parseInt((900000 - (Date.now() - data.submitTime)) / 1000);
      if (payTimeout.value > timeout) {
        payTimeout.value = timeout;
        time = timeout;
      }
    });
    if (sts.value !== 5) {
      countDown(time, (value) => {
        payTimeout.value = value;
      })
    }
  }
}, {deep: true});

// 支付完成
watch(paidComplete, () => {
  if (paidComplete.value.length === orders.value.length) {
    uni.showToast({
      title: "支付成功",
      icon: "success"
    });

    sts.value = 2;
  }
}, {deep: true});

/**
 * 生命周期函数--监听页面加载
 */
onShow(() => {
  sts.value = 0;
  orderIds.value = JSON.parse(uni.getStorageSync("unPaidOrderIds"));
  payType.value = "1";
  initComplete.value = []
  if (orderIds.value.length === 0) {
    uni.showToast({
      title: "订单不存在",
      icon: "none"
    })
    return;
  }
  orderIds.value.forEach(orderId => {
    request({
      url: "/prod/order/info/get?id=" + orderId,
      method: "GET",
      callBack: (res) => {
        const data = res.data;
        orders.value.push(data);
        payTotal.value += data.total;
        sts.value = data.status;
        closeType.value = data.closeType;
        initComplete.value.push({
          status: data.status,
          submitTime: data.submitTime,
          closeType: data.closeType
        });
      }
    })
  })
})



const toOrderList = () => {
  uni.navigateTo({
    url: '/pages/order-detail/order-detail?orderId=' + orders.value[0].id
  })
}
const toIndex = () => {
  uni.switchTab({
    url: '/pages/index/index'
  })
}
const toPay = () => {
  orders.value.forEach(order => {
    if (order.status === 1) {
      request({
        url : "/prod/order/status/change",
        data: {
          orderId: order.id,
          orderNumber: order.orderNumber,
          status: 2,
          param1: payType.value,
          param2: payTotal.value
        },
        callBack: (res) => {
          paidComplete.value.push(parseInt(res.data));
        }
      })
    }
  })
}

const getDisplayTime = (sec) => {
  const min = parseInt(sec / 60);
  const s = sec % 60;
  if (min === 0) {
    return s + "秒";
  }
  return min + "分" + s + "秒";
}

const payTypeCheck = (e) => {
  payType.value = e.detail.value;
  console.log(payType.value);
}

const getCancelText = (type) => {
  if (type === 1) {
    return "买家已取消";
  }
  if (type === 2) {
    return "订单已超时";
  }
  if (type === 3) {
    return "卖家已取消";
  }
}
</script>

<style scoped lang="scss">
@use './pay-result.scss';
</style>
