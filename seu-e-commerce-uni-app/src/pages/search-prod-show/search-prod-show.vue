<template>
  <view class="container">
    <!-- 搜索框 -->
    <view class="fixed-box">
      <view class="search-bar">
        <view class="search-box">
          <input
            placeholder="输入关键字搜索"
            class="sear-input"
            :value="prodName"
            confirm-type="search"
            @input="getSearchContent"
            @confirm="toSearchConfirm"
          >
          <image
            src="@/static/images/icon/search.png"
            class="search-img"
          />
        </view>
        <view
          class="search-list-img"
          @tap="changeShowType"
        >
          <image
            v-if="showType===1"
            src="@/static/images/icon/search-col.png"
          />
          <image
            v-if="showType===2"
            src="@/static/images/icon/search-col2.png"
          />
        </view>
      </view>
      <view class="tabs">
        <text
          :class="'tab-item complete ' + (sts===0?'on':'')"
          data-sts="0"
          @tap="onStsTap"
        >
          综合
        </text>
        <text
          :class="'tab-item ' + (sts===1?'on':'')"
          data-sts="1"
          @tap="onStsTap"
        >
          销量
        </text>
        <text
          :class="'tab-item ' + (sts===2?'on':'')"
          data-sts="2"
          @tap="onStsTap"
        >
          价格
        </text>
      </view>
    </view>

    <!-- 商品列表 -->
    <view class="prod-list">
      <!-- 横向列表 -->
      <view
        v-if="showType===1"
        class="prod-show"
      >
        <view class="hotsale-item-cont">
          <block
            v-for="(item, index) in searchProdList"
            :key="index"
          >
            <production
              :item="item"
            />
          </block>
        </view>
      </view>

      <!-- 纵向列表 -->
      <view
        v-if="showType===2"
        class="cont-item"
      >
        <block
          v-for="(item, index) in searchProdList"
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
                {{ item.brief}}
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

      <!-- 空占位 -->
      <view
        v-if="searchProdList.length === 0"
        :class="['empty','empty-top']"
      >
        暂无结果
      </view>
    </view>
  </view>
</template>

<script setup>
import Production from "../../components/production/production.vue";
import {picDomain} from "../../utils/config";
import {onLoad, onReachBottom, onShow} from "@dcloudio/uni-app";
import {ref} from "vue";
import {request} from "../../utils/http";
import {getDisplayPrice} from "../../utils/util";


const prodName = ref('')
const sts = ref(0)
const searchProdList = ref([])
const isOver = ref(false);
const page = ref({
  pageNum: 1,
  pageSize: 10
})

/**
 * 生命周期函数--监听页面加载
 */
onLoad((options) => {
  prodName.value = options.prodName
})

/**
 * 生命周期函数--监听页面显示
 */
onShow(() => {
  toLoadData()
})

const showType = ref(2)

const changeShowType = () => {
  if (showType.value === 1) {
    showType.value = 2
  } else {
    showType.value = 1
  }
}

/**
 * 输入商品获取数据
 * @param e
 */
const getSearchContent = (e) => {
  prodName.value = e.detail.value
}

/**
 *  请求热门搜索商品接口
 */
const toLoadData = () => {
  request({
    url: "/product/toc/search/prod/list",
    data: {
      keyword: prodName.value,
      type: sts.value,
      page: page.value
    },
    callBack: (res) => {
      const data = res.data;
      if (data.records.length === 0) {
        isOver.value = true;
      }
      if (data.page.pageNum === 1) {
        searchProdList.value = data.records;
      } else {
        let list = searchProdList.value;
        searchProdList.value = list.concat(data.records);
      }
    }
  })
}

/**
 * 当前搜索页二次搜索商品
 */
const toSearchConfirm = (e) => {
  if (e.detail.value) {
    let recentSearch = uni.getStorageSync('recentSearch') || []
    recentSearch = recentSearch.filter(item => item !== prodName.value)
    recentSearch.unshift(prodName.value)
    if (recentSearch.length > 10) {
      recentSearch.pop()
    }
    uni.setStorageSync('recentSearch', recentSearch)
  }
  uni.redirectTo({
    url: '/pages/search-prod-show/search-prod-show?prodName=' + e.detail.value
  })
}

/**
 * 状态点击事件
 */
const onStsTap = (e) => {
  sts.value = parseInt(e.currentTarget.dataset.sts);
  toLoadData()
}

const toProdPage = (e) => {
  uni.navigateTo({
    url: '/pages/prod/prod?prodId=' + e.currentTarget.dataset.prodid
  })
}

/**
 * 页面上拉触底事件的处理函数
 */
onReachBottom(() => {
  if (!isOver.value) {
    page.value.pageNum = page.value.pageNum + 1;
    toLoadData();
  }
})
</script>

<style scoped lang="scss">
@use './search-prod-show.scss';
</style>
