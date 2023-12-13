<template>
  <view
    class="prod-items"
    :data-prodid="item.id"
    @tap="toProdPage"
  >
    <view class="hot-imagecont">
      <image
        :src="picDomain + item.pic"
        class="hotsaleimg"
      />
    </view>
    <view class="hot-text">
      <view class="hotprod-text">
        {{ item.prodName }}
      </view>
      <view class="prod-text-info">
        <view class="price">
          <text class="symbol">
            ￥
          </text>
          <text class="big-num">
            {{getDisplayPrice(item.price)}}
          </text>
          <view class="cate-prod-info">
            {{item.soldNum}}人付款
          </view>
        </view>
      </view>
    </view>
  </view>
</template>
<script setup>
import {picDomain} from "../../utils/config";
import {getDisplayPrice} from "../../utils/util";

const props = defineProps({
  item: {
    type: Object,
    default: () => {
      return null
    }
  }
})

const toProdPage = (e) => {
  const prodId = e.currentTarget.dataset.prodid
  uni.navigateTo({
    url: '/pages/prod/prod?prodId=' + prodId
  })
}
</script>

<style scoped lang="scss">
@use './production.scss';
</style>
