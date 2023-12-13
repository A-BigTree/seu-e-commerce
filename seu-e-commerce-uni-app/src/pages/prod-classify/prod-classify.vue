<template>
  <view class="container">
    <view class="prod-list">
      <view class="cont-item">
        <block
            v-for="(item, index) in prodList"
            :key="index"
        >
          <view
              class="show-item"
              :data-prodid="item.id"
              @tap="toProdPage"
          >
            <view class="more-prod-pic">
              <image
                  :src="picDomain + item.pic"
                  class="more-pic"
              />
            </view>
            <view class="prod-text-right">
              <view class="prod-text more">
                {{ item.prodName }}
              </view>
              <view class="cate-prod-info">
                {{ item.brief }}
              </view>
              <view class="prod-price more">
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
        </block>
      </view>

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
import {request} from "../../utils/http";
import {picDomain} from "../../utils/config";
import {getDisplayPrice} from "../../utils/util";

const sts = ref(0);
const title = ref('');
const tagId = ref(0);
const isOver = ref(false);
const prodList = ref([]);
const page = ref({
  pageNum: 1,
  pageSize: 10
})

/**
 * 生命周期函数--监听页面加载
 */
onLoad((options) => {
  page.value.pageNum = 1;
  sts.value = options.sts
  title.value = options.title ? options.title : ''
  tagId.value = options.tagId ? options.tagId : 0;
  isOver.value = false;
  uni.setNavigationBarTitle({
    title: title.value
  })
  loadProdData(options)
})

/**
 * 页面上拉触底事件的处理函数
 */
onReachBottom(() => {
  if (!isOver.value) {
    page.value.pageNum = page.value.pageNum + 1;
    loadProdData()
  }
})

/**
 * 加载商品数据
 */
const loadProdData = (options) => {
  const status = parseInt(sts.value);
  switch (status) {
      case 1:
        listStyleData();
        break;
      case 2:
        listCategoryData();
        break;
      case 3:
        listActivityData();
        break;
      default:
        break;
  }

}

const callBack = (res) => {
  const data = res.data;
  if (data.records.length === 0) {
    isOver.value = true;
  }
  if (data.page.pageNum === 1) {
    prodList.value = data.records;
  } else {
    let list = prodList.value;
    prodList.value = list.concat(data.records);
  }
}

// 首页信息
const listStyleData = () => {
  request({
    url: "/product/toc/style/prod/list",
    data: {
      style: tagId.value,
      page: page.value
    },
    callBack: callBack
  })
}

// 分类信息
const listCategoryData = () => {
request({
    url: "/product/toc/category/prod/list",
    data: {
      categoryId: tagId.value,
      page: page.value
    },
    callBack: callBack
  })
}

// 活动信息
const listActivityData = () => {
  request({
    url: "/product/toc/user/prod/list",
    data: {
      type: tagId.value,
      page: page.value
    },
    callBack: callBack
  })
}

const toProdPage = (e) => {
  const prodId = e.currentTarget.dataset.prodid
  uni.navigateTo({
    url: '/pages/prod/prod?prodId=' + prodId
  })
}




</script>

<style scoped lang="scss">
@use './prod-classify.scss';
</style>
