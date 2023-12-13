<script setup>
import {ref} from "vue";
import {onShow} from "@dcloudio/uni-app";
import {request} from "../../utils/http";

const categoryList = ref([
  {
    categoryName: '电子电器',
    id: 1
  }
]);
const subCategoryList = ref([]);
const selIndex = ref(0);

const init = () => {
  categoryList.value = [];
  request({
    url: "/product/category/all/get",
    method: "GET",
    callBack: (res) => {
      categoryList.value = res.data;
      if (categoryList.value && categoryList.value.length) {
        selIndex.value = 0;
        subCategoryList.value = categoryList.value[0].children;
      }
    }
  });
}

onShow(() => {
  init();
});

const onMenuTab = (e) => {
  const index = e.currentTarget.dataset.index;
  selIndex.value = index;
  subCategoryList.value = categoryList.value[index].children;
}

const toCatePage = (e) => {
  const categoryId = e.currentTarget.dataset.categoryid;
  const title = e.currentTarget.dataset.title;
  uni.navigateTo({
    url: '/pages/prod-classify/prod-classify?tagId=' + categoryId + '&sts=2&title=' + title
  })
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
            :data-id="item.id"
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
    <!-- 左侧菜单end -->

    <!-- 右侧内容start -->
    <scroll-view
        scroll-y="true"
        class="rightcontent"
    >
      <!-- 子分类 -->
      <view
          v-if="subCategoryList.length"
          class="th-cate-con"
      >
        <block
            v-for="(thCateItem, index) in subCategoryList"
            :key="index"
        >
          <view class="sub-category">
            <view
                class="sub-category-item"
                :data-categoryid="thCateItem.id"
                :data-title="thCateItem.categoryName"
                @tap="toCatePage"
            >
              <text>{{ thCateItem.categoryName }}</text>
            </view>
          </view>
        </block>
      </view>
      <view
          v-else
          class="cont-item empty"
      >
        该分类下暂无子分类~
      </view>
    </scroll-view>
    <!-- 右侧内容end -->
  </view>
</template>

<style scoped>
@import "./category.scss";
</style>