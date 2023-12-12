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
