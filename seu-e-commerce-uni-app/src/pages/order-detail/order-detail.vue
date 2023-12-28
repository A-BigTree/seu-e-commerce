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
          {{ userAddrDto.area.province.areaName }} {{ userAddrDto.area.city.areaName }}
          {{ userAddrDto.area.area.areaName }} {{ userAddrDto.address }}
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
          >
            <view class="prod-pic"
                  :data-prodid="item.prodId"
                  @tap="toProdPage">
              <image :src="picDomain + item.pic"/>
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
                    {{ getDisplayPrice(item.price) }}
                  </text>
                </text>
                <view class="btn-box">
                  <view class="btn" v-if="status === 4 && item.status === 0">
                    <text
                        hover-class="none"
                        @tap="toCommProdPage(item)"
                    >
                      去评论
                    </text>
                  </view>
                </view>
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
              {{payType}}
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
                {{ getDisplayPrice(total) }}
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
                {{ getDisplayPrice(transfee) }}
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
                {{ getDisplayPrice(actualTotal) }}
              </text>
            </view>
          </view>
        </view>
      </view>

      <!--历史信息-->
      <view class="order-history">
        <uni-section title="订单追踪" type="line" padding>
          <uni-steps :options="reviewOptions" :active="active" direction="column"/>
        </uni-section>
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

  <uni-popup ref="popup" type="dialog">
    <uni-popup-dialog title="评论商品" style="width: 650rpx" :before-close="true" @close="popup.close()" @confirm="confirm">
      <template #default>
        <view style="width: 600rpx">
          <uni-forms :modelValue="formData">
            <uni-forms-item label="评分">
              <uni-rate v-model="formData.rateValue"/>
            </uni-forms-item>
            <uni-forms-item label="评语">
              <uni-easyinput type="textarea" v-model="formData.content"/>
            </uni-forms-item>
            <uni-forms-item label="匿名">
              <switch v-model="formData.commType" style="transform:scale(0.7)" @change="testChange"/>
            </uni-forms-item>
          </uni-forms>
        </view>

      </template>

    </uni-popup-dialog>
  </uni-popup>
</template>

<script setup>
import {onLoad} from "@dcloudio/uni-app";
import {ref} from "vue";
import {request} from "../../utils/http";
import {picDomain} from "../../utils/config";
import {getDisplayPrice} from "../../utils/util";

const orderId = ref(0);

const remarks = ref('')
const orderItemDtos = ref([])
const transfee = ref('')
const status = ref(0)
const actualTotal = ref(0)
const userAddrDto = ref(null)
const orderNumber = ref('')
const createTime = ref('')
const total = ref(0) // 商品总额

const payType = ref("微信支付");

const reviews = ref([]);

const reviewOptions = ref([]);

const active = ref(0);

onLoad((options) => {
  orderId.value = parseInt(options.orderId);
  loadOrderDetail();
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


/**
 * 加载订单数据
 */
const loadOrderDetail = () => {
  request({
    url: "/prod/order/info/get?id=" + orderId.value,
    method: "GET",
    callBack: (res) => {
      const data = res.data;
      remarks.value = data.remarks
      orderItemDtos.value = data.orderItems
      transfee.value = data.deliveryCost
      status.value = data.status
      actualTotal.value = data.total
      userAddrDto.value = data.address
      orderNumber.value = data.orderNumber
      createTime.value = data.createTime
      total.value = data.total - data.deliveryCost
      if (data.status !== 1 && data.status !== 5) {
        if (data.payType === 1) {
          payType.value = "微信支付"
        } else if (data.payType === 2) {
          payType.value = "支付宝"
        } else if (data.payType === 3) {
          payType.value = "货到付款"
        } else if (data.payType === 4) {
          payType.value = "银联支付"
        } else if (data.payType === 5) {
          payType.value = "余额支付"
        }
      } else {
        payType.value = "未支付"
      }
    }
  });
  request({
    url: "/prod/order/review/list?id=" + orderId.value,
    method: "GET",
    callBack: (res) => {
      reviews.value = res.data;
      reviewOptions.value = res.data.map(item => {
        return {
          title: item.remark,
          desc: item.createTime
        }
      });
    }
  })
}

/**
 * 删除已完成||已取消的订单
 */
const delOrderList = () => {
  uni.showModal({
    title: '',
    content: '确定要删除此订单吗？',
    confirmColor: '#eb2444',
    success(res) {
      if (res.confirm) {
        request({
          url: "/prod/order/user/update",
          data: {
            action: 2,
            data: {
              id: orderId.value
            }
          },
          callBack: (res) => {
            uni.showToast({
              title: "删除成功",
              icon: "success"
            });
            uni.navigateBack({
              delta: 1
            })
          }
        })
      }
    }
  })
}

const popup = ref(null);
const formData = ref({
  rateValue: 5,
  content: "",
  commType: 0,
})
const currentProd = ref(null);
/**
 * 评论商品
 */
const toCommProdPage = (item) => {
  formData.value = {
    rateValue: 5,
    content: "",
    commType: 0,
  }
  currentProd.value = item;
  popup.value.open();
}

const testChange =(e) => {
  if(e.detail.value) {
    formData.value.commType = 1;
  } else {
    formData.value.commType = 0;
  }
}

const confirm =() => {
  if (formData.value.content === "") {
    uni.showToast({
      title: "请输入评论内容",
      icon: "none"
    })
    return;
  }
  const score = formData.value.rateValue;
  request({
    url: "/prod/comm/user/add",
    data: {
      data: {
        id: 0,
        prodId: currentProd.value.prodId,
        orderItemId: currentProd.value.id,
        content: formData.value.content,
        score: score,
        commType: formData.value.commType,
        evaluate: score >= 4 ? 1 : (score >= 2 ? 2 : 3)
      }
    },
    callBack: (res) => {
      popup.value.close();
      loadOrderDetail();
      uni.showToast({
        title: "评论成功",
        icon: "success"
      })
    }
  })
}
</script>

<style scoped lang="scss">
@use './order-detail.scss';
</style>
