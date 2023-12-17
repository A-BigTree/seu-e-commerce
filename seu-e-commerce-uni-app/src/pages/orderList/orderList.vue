<template>
  <view class="container">
    <!-- 头部菜单 -->
    <view class="order-tit">
      <text
          data-sts="-1"
          :class="sts===-1?'on':''"
          @tap="onStsTap"
      >
        全部
      </text>
      <text
          data-sts="1"
          :class="sts===1?'on':''"
          @tap="onStsTap"
      >
        待支付
      </text>
      <text
          data-sts="2"
          :class="sts===2?'on':''"
          @tap="onStsTap"
      >
        待发货
      </text>
      <text
          data-sts="3"
          :class="sts===3?'on':''"
          @tap="onStsTap"
      >
        待收货
      </text>
      <text
          data-sts="4"
          :class="sts===4?'on':''"
          @tap="onStsTap"
      >
        已完成
      </text>
    </view>
    <!-- end 头部菜单 -->
    <view class="main">
      <view
          v-if="list.length===0"
          class="empty"
      >
        还没有任何相关订单
      </view>
      <!-- 订单列表 -->
      <block
          v-for="(item, index) in list"
          :key="index"
      >
        <view class="prod-item">
          <view class="shop-name">
            {{ item.shopName }}
          </view>
          <view class="order-num">
            <text>订单编号：{{ item.orderNumber }}</text>
            <view class="order-state">
              <text
                  :class="'order-sts  ' + (item.status===1?'red':'') + '  ' + ((item.status===4||item.status===5)?'gray':'')"
              >
                {{
                  item.status === 1 ? '待支付' : (item.status === 2 ? '待发货' : (item.status === 3 ? '待收货' : (item.status === 4 ? '已完成' : '已取消')))
                }}
              </text>

              <view
                  v-if="item.status===4 || item.status===5"
                  class="clear-btn"
              >
                <image
                    src="@/static/images/icon/clear-his.png"
                    class="clear-list-btn"
                    :data-orderid="item.id"
                    @tap="delOrderList"
                />
              </view>
            </view>
          </view>

          <!-- 商品列表 -->
          <view>
            <view
                class="item-cont"
                :data-orderid="item.id"
                @tap="toOrderDetailPage"
            >
              <view class="prod-pic">
                <image :src="picDomain + item.pic"/>
              </view>
              <view class="prod-info">
                <view class="prodname">
                  {{ item.prodName }}
                </view>
              </view>

            </view>
          </view>

          <view class="total-num">
            <text class="prodcount">
              共{{ item.prodCount }}件商品
            </text>
            <view class="prodprice">
              合计：
              <text class="symbol">
                ￥
              </text>
              <text class="big-num">
                {{ getDisplayPrice(item.total) }}
              </text>
            </view>
          </view>
          <!-- end 商品列表 -->
          <view class="prod-foot">
            <view class="btn">
              <text
                  v-if="item.status===1"
                  class="button"
                  :data-orderid="item.id"
                  hover-class="none"
                  @tap="onCancelOrder"
              >
                取消订单
              </text>
              <text
                  v-if="item.status===1"
                  class="button warn"
                  :data-orderid="item.id"
                  hover-class="none"
                  @tap="normalPay"
              >
                付款
              </text>
              <text
                  v-if="item.status===3"
                  class="button warn"
                  :data-orderid="item.id"
                  hover-class="none"
                  @tap="onConfirmReceive"
              >
                确认收货
              </text>
            </view>
          </view>
        </view>
      </block>
    </view>
  </view>
  <!-- end 订单列表 -->
</template>

<script setup>
import {picDomain} from "../../utils/config";
import {onLoad, onReachBottom, onShow} from "@dcloudio/uni-app";
import {ref} from "vue";
import {request} from "../../utils/http";
import {getDisplayPrice} from "../../utils/util";

const sts = ref(-1)

const list = ref([])

const page = ref({
  pageNum: 1,
  pageSize: 10
});

const isOver = ref(false);


/**
 * 生命周期函数--监听页面加载
 */
onLoad((options) => {
  page.value = {
    pageNum: 1,
    pageSize: 10
  }
  isOver.value = false;
  list.value = [];
  if (options.sts) {
    sts.value = parseInt(options.sts)
  } else {
    sts.value = -1;
  }
})

onShow(() => {
  loadOrderData();
})

/**
 * 页面上拉触底事件的处理函数
 */
onReachBottom(() => {
  if (!isOver.value) {
    loadOrderData();
  }
})


/**
 * 加载订单数据
 */
const loadOrderData = () => {
  request({
    url: "/prod/order/page/list",
    data: {
      page: page.value,
      status: sts.value
    },
    callBack: (res) => {
      if (res.data.records.length === 0) {
        isOver.value = true;
        return;
      }
      list.value = list.value.concat(res.data.records);
      page.value.pageNum++;
    }
  })
}

/**
 * 状态点击事件
 */
const onStsTap = (e) => {
  sts.value = parseInt(e.currentTarget.dataset.sts);
  page.value = {
    pageNum: 1,
    pageSize: 10
  }
  isOver.value = false;
  list.value = [];
  loadOrderData()
}

/**
 * 取消订单
 */
const onCancelOrder = (e) => {
  const orderId = e.currentTarget.dataset.orderid
  uni.showModal({
    title: '',
    content: '要取消此订单？',
    confirmColor: '#3e62ad',
    cancelColor: '#3e62ad',
    cancelText: '否',
    confirmText: '是',

    success(res) {
      if (res.confirm) {
        request({
          url : "/prod/order/status/change",
          data: {
            orderId: orderId,
            status: 5,
            param1: 1
          },
          callBack: (res) => {
            page.value = {
              pageNum: 1,
              pageSize: 10
            }
            isOver.value = false;
            list.value = [];
            loadOrderData();
          }
        })
      }
    }
  })
}

/**
 * 模拟支付，直接提交成功
 * @param e
 */
const normalPay = (e) => {
  console.log("付款")
  const orderId = e.currentTarget.dataset.orderid;
  uni.setStorageSync("unPaidOrderIds", JSON.stringify([orderId]));
  uni.navigateTo({
    url: '/pages/pay-result/pay-result'
  })
}

/**
 * 查看订单详情
 */
const toOrderDetailPage = (e) => {
  uni.navigateTo({
    url: '/pages/order-detail/order-detail?orderId=' + e.currentTarget.dataset.orderid
  })
}

/**
 * 确认收货
 */
const onConfirmReceive = (e) => {
  const orderId = e.currentTarget.dataset.orderid;
  uni.showModal({
    title: '',
    content: '我已收到货？',
    confirmColor: '#eb2444',
    success(res) {
      if (res.confirm) {
        request({
          url : "/prod/order/status/change",
          data: {
            orderId: orderId,
            status: 4
          },
          callBack: (res) => {
            page.value = {
              pageNum: 1,
              pageSize: 10
            }
            isOver.value = false;
            list.value = [];
            loadOrderData();
          }
        })
      }
    }
  })
}

/**
 * 删除已完成||已取消的订单
 * @param e
 */
const delOrderList = (e) => {
  uni.showModal({
    title: '',
    content: '确定要删除此订单吗？',
    confirmColor: '#eb2444',
    success(res) {
      if (res.confirm) {
        const orderId = e.currentTarget.dataset.orderid;
        request({
          url: "/prod/order/user/update",
          data: {
            action: 2,
            data: {
              id: orderId
            }
          },
          callBack: (res) => {
            uni.showToast({
              title: "删除成功",
              icon: "success"
            });
            page.value = {
              pageNum: 1,
              pageSize: 10
            }
            isOver.value = false;
            list.value = [];
            loadOrderData();
          }
        })
      }
    }
  })
}

</script>

<style scoped lang="scss">
@use './orderList.scss';
</style>
