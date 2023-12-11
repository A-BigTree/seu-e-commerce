<template>
  <view class="container">
    <view class="order-detail">
      <view
        v-if="userAddrDto"
        class="delivery-addr"
      >
        <view class="user-info">
          <text class="item">
            {{ userAddrDto.receiver }}
          </text>
          <text class="item">
            {{ userAddrDto.mobile }}
          </text>
        </view>
        <view class="addr">
          {{ userAddrDto.province }}{{ userAddrDto.city }}{{ userAddrDto.area }}{{
            userAddrDto.area
          }}{{ userAddrDto.addr }}
        </view>
      </view>

      <!-- 商品信息 -->
      <view
        v-if="orderItemDtos"
        class="prod-item"
      >
        <block
          v-for="(item, index) in orderItemDtos"
          :key="index"
        >
          <view
            class="item-cont"
            :data-prodid="item.prodId"
            @tap="toProdPage"
          >
            <view class="prod-pic">
              <image :src="item.pic" />
            </view>
            <view class="prod-info">
              <view class="prodname">
                {{ item.prodName }}
              </view>
              <view class="prod-info-cont">
                <text class="number">
                  数量：{{ item.prodCount }}
                </text>
                <text class="info-item">
                  {{ item.skuName }}
                </text>
              </view>
              <view class="price-nums clearfix">
                <text class="prodprice">
                  <text class="symbol">
                    ￥
                  </text>
                  <text class="big-num">
                    {{ wxs.parsePrice(item.price)[0] }}
                  </text>
                  <text class="small-num">
                    .{{ wxs.parsePrice(item.price)[1] }}
                  </text>
                </text>
                <view class="btn-box" />
              </view>
            </view>
          </view>
        </block>
      </view>

      <!-- 订单信息 -->
      <view class="order-msg">
        <view class="msg-item">
          <view class="item">
            <text class="item-tit">
              订单编号：
            </text>
            <text class="item-txt">
              {{ orderNumber }}
            </text>
          </view>
          <view class="item">
            <text class="item-tit">
              下单时间：
            </text>
            <text class="item-txt">
              {{ createTime }}
            </text>
          </view>
        </view>
        <view class="msg-item">
          <view class="item">
            <text class="item-tit">
              支付方式：
            </text>
            <text class="item-txt">
              微信支付
            </text>
          </view>
          <view class="item">
            <text class="item-tit">
              配送方式：
            </text>
            <text class="item-txt">
              普通配送
            </text>
          </view>
          <view class="item">
            <text
              v-if="!!remarks"
              class="item-tit"
            >
              订单备注：
            </text>
            <text class="item-txt remarks">
              {{ remarks }}
            </text>
          </view>
        </view>
      </view>

      <view class="order-msg">
        <view class="msg-item">
          <view class="item">
            <view class="item-tit">
              订单总额：
            </view>
            <view class="item-txt price">
              <text class="symbol">
                ￥
              </text>
              <text class="big-num">
                {{total}}
              </text>
            </view>
          </view>
          <view class="item">
            <view class="item-tit">
              运费：
            </view>
            <view class="item-txt price">
              <text class="symbol">
                ￥
              </text>
              <text class="big-num">
                {{transfee}}
              </text>
            </view>
          </view>
          <view class="item payment">
            <view class="item-txt price">
              实付款：
              <text class="symbol">
                ￥
              </text>
              <text class="big-num">
                {{actualTotal}}
              </text>
            </view>
          </view>
        </view>
      </view>

      <!-- 底部栏 -->
      <view
        v-if="status===5||status===6"
        class="order-detail-footer"
      >
        <text
          v-if="status===5||status===6"
          class="dele-order"
          @tap="delOrderList"
        >
          删除订单
        </text>
      </view>
    </view>
  </view>
</template>

<script setup>
import {onLoad} from "@dcloudio/uni-app";
import {ref} from "vue";

onLoad((options) => {
  loadOrderDetail(options.orderNum)
})

/**
 * 跳转商品详情页
 * @param e
 */
const toProdPage = (e) => {
  const prodId = e.currentTarget.dataset.prodid
  uni.navigateTo({
    url: '/pages/prod/prod?prodId=' + prodId
  })
}

const remarks = ref('')
const orderItemDtos = ref([])
const reduceAmount = ref('')
const transfee = ref('')
const status = ref(0)
const actualTotal = ref(0)
const userAddrDto = ref(null)
const orderNumber = ref('')
const createTime = ref('')
const total = ref(0) // 商品总额
/**
 * 加载订单数据
 */
const loadOrderDetail = (orderNum) => {

}

/**
 * 删除已完成||已取消的订单
 */
const delOrderList = () => {
  uni.showModal({
    title: '',
    content: '确定要删除此订单吗？',
    confirmColor: '#eb2444',
    success (res) {
      if (res.confirm) {

      }
    }
  })
}
</script>

<style scoped lang="scss">
@use './order-detail.scss';
</style>
