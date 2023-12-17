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
                <image src="@/static/images/icon/plus-sign.png"/>
              </view>
              <text>新增收货地址</text>
            </view>
            <view class="arrow empty"/>
          </view>
          <view
              v-if="userAddr"
              class="addr-bg whole"
          >
            <view class="addr-icon">
              <image src="@/static/images/icon/addr.png"/>
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
              {{ userAddr.area.province.areaName }}
              {{ userAddr.area.city.areaName }}
              {{ userAddr.area.area.areaName }}
              {{ userAddr.address }}
            </view>
            <view class="arrow"/>
          </view>
        </view>

        <!-- 商品详情 -->
        <view
            v-for="(orderItem, index) in orderItems"
            :key="index"
            class="prod-item">
          <view class="shopName">
            {{ orderItem.shopName }}
          </view>
          <block
              v-for="(item, index) in orderItem.items"
              :key="index"
          >
            <view
                class="item-cont"
            >
              <view class="prod-pic">
                <image :src="picDomain + item.pic"/>
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
                      {{ getDisplayPrice(item.price) }}
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
              共{{ orderItem.totalCount }}件商品
            </text>
            <view class="prodprice">
              合计：
              <text class="symbol">
                ￥
              </text>
              <text class="big-num">
                {{ getDisplayPrice(orderItem.total) }}
              </text>
            </view>
          </view>
          <!-- 订单详情 -->
          <view class="order-msg">
            <view class="msg-item">
              <view class="item">
                <text>买家留言：</text>
                <input
                    v-model="orderItem.remarks"
                    placeholder="给卖家留言"
                >
              </view>
            </view>
          </view>
          <!--订单小计-->
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
                  {{ getDisplayPrice(orderItem.total) }}
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
                    {{ getDisplayPrice(orderItem.deliveryCost) }}
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
                    {{ getDisplayPrice(shopReduce) }}
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
                    {{ getDisplayPrice(orderItem.actualTotal) }}
                  </text>
                </view>
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
                {{ getDisplayPrice(actualTotal) }}
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
import {ref, watch} from "vue";
import {request} from "../../utils/http";
import {getDisplayPrice} from "../../utils/util";


let orderEntry = '1' // 订单入口 1立即购买 2购物车
/**
 * 生命周期函数--监听页面加载
 */
onLoad((options) => {
  orderEntry = options.orderEntry;
  request({
    url: "/order/area/address/default/get",
    method: "GET",
    callBack: (res) => {
      if (res.data) {
        userAddr.value = res.data;
      }
    }
  })
})

const userAddr = ref(null)
/**
 * 生命周期函数--监听页面显示
 */
onShow(() => {
  const pages = getCurrentPages()
  const currPage = pages[pages.length - 1]
  if (currPage.address) {
    // 将携带的参数赋值
    userAddr.value = currPage.address
  }
  // 获取订单数据
  loadOrderData()
})

const actualTotal = ref(0)
const orderItems = ref([])
const shopReduce = ref('')
/**
 * 加载订单数据
 */
const loadOrderData = () => {
  const orderIds = JSON.parse(uni.getStorageSync('orderIds'));
  request({
    url: "/prod/order/init/list",
    data: {
      orderIds: orderIds
    },
    callBack: (res) => {
      orderItems.value = [];
      actualTotal.value = 0;
      const items = res.data;
      items.forEach(item => {
        let index = 0;
        let flag = false;
        orderItems.value.forEach(orderItem => {
          if (orderItem.shopId === item.shopId) {
            flag = true;
            return;
          }
          index++;
        });
        if (flag) {
          orderItems.value[index].items.push(item);
          orderItems.value[index].totalCount += item.prodCount;
          orderItems.value[index].total += item.price * item.prodCount;
          orderItems.value[index].actualTotal += item.price * item.prodCount + item.deliveryCost;
          orderItems.value[index].deliveryCost += item.deliveryCost;
        } else {
          orderItems.value.push({
            shopId: item.shopId,
            shopName: item.ext,
            items: [item],
            totalCount: item.prodCount,
            total: item.price * item.prodCount,
            remarks: '',
            actualTotal: item.price * item.prodCount + item.deliveryCost,
            deliveryCost: item.deliveryCost
          });
        }
      });
      orderItems.value.forEach(orderItem => {
        actualTotal.value += orderItem.actualTotal;
      });
    }

  })

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

const orderComplete = ref([]);

watch(() => orderComplete, () => {
  if (orderComplete.value.length === orderItems.value.length) {
    uni.showModal({
      title: '',
      content: '订单提交成功~现在去支付？',
      confirmColor: '#eb2444',
      success(res) {
        if (res.confirm) {
          uni.setStorageSync("unPaidOrderIds", JSON.stringify(orderComplete.value));
          uni.navigateTo({
            url: '/pages/pay-result/pay-result'
          })
        }
      }
    })
  }
}, {deep: true});

const submitOrder = () => {
  orderComplete.value = [];
  orderItems.value.forEach(order => {
    request({
      url: "/prod/order/user/update",
      data: {
        action: 1,
        data: {
          id: 0,
          shopId: order.shopId,
          shopName: order.shopName,
          address: {
            id: userAddr.value.id
          },
          prodName: order.items.map(item => {
            return item.prodName;
          }).join(','),
          pic: order.items[0].pic,
          remarks: order.remarks,
          prodCount: order.totalCount,
          deliveryCost: order.deliveryCost,
          orderType: parseInt(orderEntry),
          total: order.actualTotal,
          orderItems: order.items.map(item => {
            return {
              id: 0,
              prodId: item.prodId,
              prodName: item.prodName,
              skuId: item.skuId,
              skuName: item.skuName,
              price: item.price,
              shopId: order.shopId,
              prodCount: item.prodCount,
              pic: item.pic,
              status: 0,
            }
          })
        }
      },
      callBack: (res) => {
        orderComplete.value.push(parseInt(res.data));
      }
    })
  })
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
