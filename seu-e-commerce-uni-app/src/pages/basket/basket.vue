<script setup>
import {ref} from "vue";
import {picDomain} from "../../utils/config";

const shopCartItemDiscounts = ref([
  {
    shopCartItems: [
      {
        prodId: 1,
        prodName: "商品名称",
        pic: "/index/index1.jpg",
        skuName: "规格名称",
        price: 1000,
        prodCount: 1,
        checked: false
      },
      {
        prodId: 1,
        prodName: "商品名称",
        pic: "/index/index1.jpg",
        skuName: "规格名称",
        price: 1000,
        prodCount: 1,
        checked: false
      }
    ]
  }
]);

const allChecked = ref(false);

const totalMoney = ref(0);

const subtractMoney = ref(0);

const finalMoney = ref(0);

const onSelectedItem = (e) => {
  const index = e.currentTarget.dataset.index;
  const scIndex = e.currentTarget.dataset.scindex;
}

const onCountMinus = (e) => {
  const index = e.currentTarget.dataset.index;
  const scIndex = e.currentTarget.dataset.scindex;
}

const onCountPlus = (e) => {
  const index = e.currentTarget.dataset.index;
  const scIndex = e.currentTarget.dataset.scindex;
}

const onSelAll = () => {

}

const onDelBasket = () => {

}

const toFirmOrder = () => {

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
                      :value="prod.prodId"
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
                        {{ prod.price }}
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
              {{finalMoney}}
            </text>
          </view>
        </view>
        <view
            v-if="subtractMoney > 0"
            class="total-msg"
        >
          总额:￥{{totalMoney}} 立减:￥{{subtractMoney}}
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