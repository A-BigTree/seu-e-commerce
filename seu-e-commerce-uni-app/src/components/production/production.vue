<template>
  <view
    class="prod-items"
    :data-prodid="item.prodId"
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
      <view
        v-if="sts===6"
        class="prod-info"
      >
        {{ item.prodCommNumber }}评价 {{ item.positiveRating }}%好评
      </view>
      <view class="prod-text-info">
        <view class="price">
          <text class="symbol">
            ￥
          </text>
          <text class="big-num">
            {{item.price}}
          </text>
        </view>
      </view>
    </view>
  </view>
</template>
<script setup>
import {picDomain} from "../../utils/config";

const props = defineProps({
  item: {
    type: Object,
    default: () => {
      return null
    }
  },
  sts: {
    type: Number,
    default: () => {
      return 2
    }
  }
})

const toProdPage = (e) => {
  const prodid = e.currentTarget.dataset.prodid
  uni.navigateTo({
    url: '/pages/prod/prod?prodid=' + prodid
  })
}
</script>

<style scoped lang="scss">
@use './production.scss';
</style>
