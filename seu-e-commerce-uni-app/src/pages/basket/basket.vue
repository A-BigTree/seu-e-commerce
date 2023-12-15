<script setup>
import {ref} from "vue";
import {picDomain} from "../../utils/config";
import {onShow} from "@dcloudio/uni-app";
import {request} from "../../utils/http";
import {getDisplayPrice} from "../../utils/util";

const shopCartItemDiscounts = ref([
  {
    shopCartItems: []
  }
]);

onShow(() => {
  finalMoney.value = 0;
  allChecked.value = false;
  listData();
})

const listData = () => {
  request({
    url: "/product/basket/user/list",
    method: "GET",
    callBack: (res) => {
      shopCartItemDiscounts.value = [{
        shopCartItems: []
      }]
      res.data.forEach(data => {
        shopCartItemDiscounts.value[0].shopCartItems.push({
          id: data.id,
          prodId: data.prodId,
          prodName: data.prodName,
          pic: data.pic,
          skuName: data.skuName,
          price: data.price,
          prodCount: data.prodCount,
          status: data.status,
          checked: false
        })
      })
    }
  })
}

const allChecked = ref(false);


const finalMoney = ref(0);

const onSelectedItem = (e) => {
  const index = e.currentTarget.dataset.index;
  const scIndex = e.currentTarget.dataset.scindex;
  const item = shopCartItemDiscounts.value[scIndex].shopCartItems[index];
  item.checked = (!item.checked);
  freshFinalPrice();
}

const onCountMinus = (e) => {
  const index = e.currentTarget.dataset.index;
  const scIndex = e.currentTarget.dataset.scindex;
  const item = shopCartItemDiscounts.value[scIndex].shopCartItems[index];
  if (item.prodCount === 1) {
    return;
  }
  request({
    url: "/product/basket/prod/count/change?count=-1&id=" + item.id,
    callBack: (res) => {
      item.prodCount = parseInt(res.data);
      freshFinalPrice();
    }
  })
}

const onCountPlus = (e) => {
  const index = e.currentTarget.dataset.index;
  const scIndex = e.currentTarget.dataset.scindex;
  const item = shopCartItemDiscounts.value[scIndex].shopCartItems[index];
  request({
    url: "/product/basket/prod/count/change?count=1&id=" + item.id,
    callBack: (res) => {
      item.prodCount = parseInt(res.data);
      freshFinalPrice();
    }
  })
}

const onSelAll = () => {
  allChecked.value = (!allChecked.value);
  const baskets = shopCartItemDiscounts.value[0].shopCartItems;
  baskets.forEach(basket => {
    basket.checked = allChecked.value;
  })
  freshFinalPrice();
}

const onDelBasket = () => {
  const ids = [];
  const items = shopCartItemDiscounts.value[0].shopCartItems;
  items.forEach(item => {
    if (item.checked) {
      ids.push(item.id);
    }
  })
  if (ids.length === 0) {
    uni.showToast({
      title: "请选择商品",
      icon: "none"
    })
  } else {
    uni.showModal({
      title: '',
      content: '确定要删除所选商品吗？',
      confirmColor: '#eb2444',
      success(res) {
        if (res.confirm) {
          request({
            url: "/product/basket/batch/delete",
            data: ids,
            callBack: (res) => {
              uni.showToast({
                title: "删除成功",
                icon: "success"
              });
              listData();
            }
          })
        }
      }
    });
  }
}

const freshFinalPrice = () => {
  finalMoney.value = 0;
  const items = shopCartItemDiscounts.value[0].shopCartItems;
  let temp = true;
  items.forEach(item => {
    if (item.checked) {
      finalMoney.value += (item.price * item.prodCount);
    } else {
      temp = false;
    }
  });
  allChecked.value = temp;
}

const toFirmOrder = () => {
  const ids = []
  const items = shopCartItemDiscounts.value[0].shopCartItems;
  items.forEach(item => {
    if (item.checked) {
      ids.push({
        id: item.id,
        prodId: item.prodId,
        skuId: item.skuId,
        prodCount: item.prodCount
      });
    }
  });
  if (ids.length === 0) {
    uni.showToast({
      title: "请选择商品",
      icon: "none"
    })
    return;
  }

  uni.setStorageSync("orderIds", JSON.stringify(ids));
  uni.navigateTo({
    url: '/pages/submit-order/submit-order?orderEntry=2'
  })
}

</script>


<template>
  <view class="container">
    <view class="prod-list">
      <block
          v-for="(item, scIndex) in shopCartItemDiscounts"
          :key="scIndex"
      >
        <view class="prod-block">
          <block
              v-for="(prod, index) in item.shopCartItems"
              :key="index"
          >
            <view class="item">
              <view class="btn">
                <label>
                  <checkbox
                      :data-scindex="scIndex"
                      :data-index="index"
                      :value="'' + prod.prodId"
                      :checked="prod.checked"
                      color="#105c3e"
                      @tap="onSelectedItem"
                  />
                </label>
              </view>
              <view class="prodinfo">
                <view class="pic">
                  <image :src="picDomain + prod.pic" />
                </view>
                <view class="opt">
                  <view class="prod-name">
                    {{ prod.prodName }}
                  </view>
                  <text :class="'prod-info-text ' + (prod.skuName?'':'empty-n')">
                    {{ prod.skuName }}
                  </text>
                  <view class="price-count">
                    <view class="price">
                      <text class="symbol">
                        ￥
                      </text>
                      <text class="big-num">
                        {{ getDisplayPrice(prod.price) }}
                      </text>
                    </view>
                    <view class="m-numSelector">
                      <view
                          class="minus"
                          :data-scindex="scIndex"
                          :data-index="index"
                          @tap="onCountMinus"
                      />
                      <input
                          type="number"
                          :value="prod.prodCount"
                          disabled
                      >
                      <view
                          class="plus"
                          :data-scindex="scIndex"
                          :data-index="index"
                          @tap="onCountPlus"
                      />
                    </view>
                  </view>
                </view>
              </view>
            </view>
          </block>
        </view>
      </block>
    </view>

    <view
        v-if="!shopCartItemDiscounts.length"
        class="empty"
    >
      <view class="img">
        <image src="@/static/images/tabbar/basket.png" />
      </view>
      <view class="txt">
        您还没有添加任何商品哦~
      </view>
    </view>

    <!-- 底部按钮 -->
    <view
        v-if="shopCartItemDiscounts.length > 0"
        class="cart-footer"
    >
      <view class="btn all">
        <checkbox
            :checked="allChecked"
            color="#f7d731;"
            @tap="onSelAll"
        />
        全选
      </view>
      <view
          class="btn del"
          @tap="onDelBasket"
      >
        <text>删除</text>
      </view>
      <view class="btn total">
        <view class="finally">
          <text>合计:</text>
          <view class="price">
            <text class="symbol">
              ￥
            </text>
            <text class="big-num">
              {{getDisplayPrice(finalMoney)}}
            </text>
          </view>
        </view>
      </view>
      <view
          class="btn settle"
          @tap="toFirmOrder"
      >
        <text>结算</text>
      </view>
    </view>
    <!-- end 底部按钮 -->
  </view>
</template>



<style scoped lang="scss">
@import "./basket.scss";
</style>