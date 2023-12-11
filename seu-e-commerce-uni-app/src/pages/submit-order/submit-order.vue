<template>
  <view>
    <view class="container">
      <view class="submit-order">
        <!-- 收货地址 -->
        <view
          class="delivery-addr "
          @tap="toAddrListPage"
        >
          <view
            v-if="!userAddr"
            class="addr-bg "
          >
            <view class="add-addr">
              <view class="plus-sign-img">
                <image src="@/static/images/icon/plus-sign.png" />
              </view>
              <text>新增收货地址</text>
            </view>
            <view class="arrow empty" />
          </view>
          <view
            v-if="userAddr"
            class="addr-bg whole"
          >
            <view class="addr-icon">
              <image src="@/static/images/icon/addr.png" />
            </view>
            <view class="user-info">
              <text class="item">
                {{ userAddr.receiver }}
              </text>
              <text class="item">
                {{ userAddr.mobile }}
              </text>
            </view>
            <view class="addr">
              {{ userAddr.province }}{{ userAddr.city }}{{ userAddr.area }}{{ userAddr.addr }}
            </view>
            <view class="arrow" />
          </view>
        </view>

        <!-- 商品详情 -->
        <view class="prod-item">
          <block
            v-for="(item, index) in orderItems"
            :key="index"
          >
            <view
              class="item-cont"
              :data-ordernum="item.primaryOrderNo"
              @tap="toOrderDetailPage"
            >
              <view class="prod-pic">
                <image :src="picDomain + item.pic" />
              </view>
              <view class="prod-info">
                <view class="prodname">
                  {{ item.prodName }}
                </view>
                <view class="prod-info-cont">
                  {{ item.skuName }}
                </view>
                <view class="price-nums">
                  <text class="prodprice">
                    <text class="symbol">
                      ￥
                    </text>
                    <text class="big-num">
                      {{item.price}}
                    </text>
                  </text>
                  <text class="prodcount">
                    x{{ item.prodCount }}
                  </text>
                </view>
              </view>
            </view>
          </block>

          <view class="total-num">
            <text class="prodcount">
              共{{ totalCount }}件商品
            </text>
            <view class="prodprice">
              合计：
              <text class="symbol">
                ￥
              </text>
              <text class="big-num">
                {{total}}
              </text>
            </view>
          </view>
        </view>

        <!-- 订单详情 -->
        <view class="order-msg">
          <view class="msg-item">
            <view class="item">
              <text>买家留言：</text>
              <input
                v-model="remarks"
                placeholder="给卖家留言"
              >
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
                  {{total}}
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
            <view class="item">
              <view class="item-tit">
                优惠金额：
              </view>
              <view class="item-txt price">
                <text class="symbol">
                  -￥
                </text>
                <text class="big-num">
                  {{shopReduce}}
                </text>
              </view>
            </view>
            <view class="item payment">
              <view class="item-txt price">
                小计：
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
      </view>

      <!-- 底部栏 -->
      <view class="submit-order-footer">
        <view class="sub-order">
          <view class="item-txt">
            合计：
            <view class="price">
              <text class="symbol">
                ￥
              </text>
              <text class="big-num">
                {{actualTotal}}
              </text>
              <text class="small-num">
                .{{actualTotal}}
              </text>
            </view>
          </view>
        </view>
        <view
          class="footer-box"
          @tap="toPay"
        >
          提交订单
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import {picDomain} from "../../utils/config";
import {onLoad, onShow} from "@dcloudio/uni-app";
import {ref} from "vue";


let orderEntry = '0' // 订单入口 0购物车 1立即购买
/**
 * 生命周期函数--监听页面加载
 */
onLoad((options) => {
  orderEntry = options.orderEntry
})

const userAddr = ref(null)
/**
 * 生命周期函数--监听页面显示
 */
onShow(() => {
  const pages = getCurrentPages()
  const currPage = pages[pages.length - 1]
  if (currPage.selAddress === 'yes') {
    // 将携带的参数赋值
    userAddr.value = currPage.item
  }
  // 获取订单数据
  loadOrderData()
})

const total = ref(0)
const actualTotal = ref(0)
const orderItems = ref([])
const totalCount = ref(0)
const transfee = ref(0)
const shopReduce = ref('')
/**
 * 加载订单数据
 */
const loadOrderData = () => {
  let addrId = 0
  if (userAddr.value != null) {
    addrId = userAddr.value.addrId
  }

}


/**
 * 提交订单
 */
const toPay = () => {
  if (!userAddr.value) {
    uni.showToast({
      title: '请选择地址',
      icon: 'none'
    })
    return
  }
  submitOrder()
}

const remarks = ref('')

const submitOrder = () => {

}


/**
 * 去地址页面
 */
const toAddrListPage = () => {
  uni.navigateTo({
    url: '/pages/delivery-address/delivery-address?order=0'
  })
}

</script>

<style scoped lang="scss">
@use './submit-order.scss';
</style>
