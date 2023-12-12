<template>
  <view class="container">
    <view>
      <block
        v-for="(item, index) in prodList"
        :key="index"
      >
        <production :item="item" />
      </block>
      <view
        v-if="!prodList.length"
        class="empty"
      >
        暂无数据
      </view>
    </view>
  </view>
</template>

<script setup>
import Production from "../../components/production/production.vue";
import {ref} from "vue";
import {onLoad, onReachBottom} from "@dcloudio/uni-app";

const sts = ref(0)
const title = ref('')
const current = ref(1)
const size = ref(10)
const pages = ref(0)
const tagId = ref(0)
/**
 * 生命周期函数--监听页面加载
 */
onLoad((options) => {
  current.value = 1
  pages.value = 0
  sts.value = options.sts
  title.value = options.title ? options.title : ''
  tagId.value = options.tagId ? options.tagId : 0

  loadProdData(options)
})

/**
 * 页面上拉触底事件的处理函数
 */
onReachBottom(() => {
  if (current.value < pages.value) {
    current.value = current.value + 1
    loadProdData()
  }
})

/**
 * 加载商品数据
 */
const loadProdData = (options) => {
  const stsParam = sts.value


}

const prodList = ref([])




</script>

<style scoped lang="scss">
@use './prod-classify.scss';
</style>
