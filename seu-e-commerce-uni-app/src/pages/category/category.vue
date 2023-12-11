<script setup>
import {ref} from "vue";
import {onShow} from "@dcloudio/uni-app";

const categoryList = ref([
  {
    categoryName: '电子电器',
    categoryId: 1
  },
  {
    categoryName: '服装鞋帽',
    categoryId: 2
  }
]);
const subCategoryList = ref([]);
const selIndex = ref(0);

const init = () => {

}

onShow(() => {
  init();
});

const toSearchPage = () => {

}

const onMenuTab = (e) => {
  selIndex.value = parseInt(e.currentTarget.dataset.index);
  console.log(e.currentTarget.dataset.id);
}

</script>

<template>
  <!-- 滚动内容区 -->
  <view class="main">
    <!-- 左侧菜单start -->
    <scroll-view
        scroll-y="true"
        class="leftmenu"
    >
      <block
          v-for="(item, index) in categoryList"
          :key="index"
      >
        <view
            :class="'menu-item ' + (selIndex===index?'active':'') + ' '"
            :data-index="index"
            :data-id="item.categoryId"
            @tap="onMenuTab"
        >
          {{ item.categoryName }}
        </view>
      </block>
      <view
          v-if="!categoryList || !categoryList.length"
          class="ca-empty"
      >
        {{ categoryList && categoryList.length ? '该分类下暂无商品' : '暂无商品' }}
      </view>
    </scroll-view>
  </view>
</template>

<style scoped>
@import "./category.scss";
</style>