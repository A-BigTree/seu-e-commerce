<template>
  <view class="container">
    <view class="main">
      <view
        v-if="addressList.length===0"
        class="empty"
      >
        <view class="img">
          <image src="http://jiales.gz-yami.com/addr.png" />
        </view>
        <view class="txt">
          您还没有收货地址
        </view>
      </view>
      <radio-group class="radio-group">
        <block
          v-for="(item, index) in addressList"
          :key="index"
        >
          <view class="address">
            <view
              class="personal"
              @tap="selAddrToOrder(item)"
            >
              <view class="info-tit">
                <text class="name">
                  {{ item.receiver }}
                </text>
                <text class="tel">
                  {{ item.mobile }}
                </text>
                <image
                  src="@/static/images/icon/revise.png"
                  :data-addrid="item.id"
                  @tap.stop="toEditAddress"
                />
              </view>
              <view class="addr">
                <text class="addr-get">
                  {{ item.area.province.areaName }}{{ item.area.city.areaName }}{{ item.area.area.areaName }}{{ item.address }}
                </text>
              </view>
            </view>
            <view
              class="select-btn"
            >
              <view class="box">
                <radio
                  :value="'' + item.id"
                  :checked="item.defaultAddress === 1"
                  color="#eb2444"
                  :data-addrid="item.id"
                  :data-default="item.defaultAddress"
                  @tap="onDefaultAddr"
                />
                设为默认地址
              </view>
            </view>
          </view>
        </block>
      </radio-group>
    </view>
    <view
      class="footer"
      @tap="onAddAddr"
    >
      <text>新增收货地址</text>
    </view>
  </view>
</template>

<script setup>
import {onLoad, onShow} from "@dcloudio/uni-app";
import {ref} from "vue";
import {request} from "../../utils/http";

const order = ref(-1)
onLoad((option) => {
  if (option.order) {
    order.value = parseInt(option.order)
  }
})

const addressList = ref([])
/**
 * 加载地址列表
 */
onShow(() => {
  onGetList()
})

/**
 * 获取收获列表接口
 */
const onGetList = () => {
  request({
    url: "/order/area/address/list",
    method: "GET",
    callBack: (res) => {
      addressList.value = res.data
    }
  })
}

/**
 * 新增收货地址
 */
const onAddAddr = () => {
  uni.navigateTo({
    url: '/pages/editAddress/editAddress'
  })
}

/**
 * 设置为默认地址
 */
const onDefaultAddr = (e) => {
  const addrId = e.currentTarget.dataset.addrid;
  const defaultAddr = parseInt(e.currentTarget.dataset.default);
  if (defaultAddr === 1) {
    return;
  }
  request({
    url: "/order/area/address/default/change?addressId=" + addrId,
    callBack: (res) => {
      onGetList();
    }
  })
}

/**
 * 修改地址
 */
const toEditAddress = (e) => {
  const addrId = e.currentTarget.dataset.addrid
  uni.navigateTo({
    url: '/pages/editAddress/editAddress?addrId=' + addrId
  })
}

/**
 * 选择地址 跳转回提交订单页
 */
const selAddrToOrder = (item) => {
  if (order.value === 0) {
    const page = getCurrentPages();
    const prevPage = page[page.length - 2];
    prevPage.address = item;
    uni.navigateBack({
      delta: 1
    });
  }
}

</script>

<style scoped lang="scss">
@use './delivery-address.scss';
</style>
