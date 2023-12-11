<script setup>

import {ref} from "vue";
import ImgShow from "../../components/img-show/img-show.vue";
import {picDomain} from "../../utils/config";

const indicatorColor = ref('#d1e5fb')
const indicatorActiveColor = ref('#1b7dec')
const autoplay = ref(true)
const interval = ref(4000)
const duration = ref(2000)
const indexImages = ref([
  {
    imgUrl: '/index/index1.jpg',
    relation: 1
  },
  {
    imgUrl: '/index/index2.jpg',
    relation: 2
  }
])
const tagList = ref([
  {
    style: '2',
    id: 2,
    title: '每日上新',
    prods: [
      {
        prodName: "商品名称",
        pic: "/index/index1.jpg",
        prodId: 1,
        price: 1000
      },
      {
        prodName: "商品名称",
        pic: "/index/index1.jpg",
        prodId: 1,
        price: 1000
      },
      {
        prodName: "商品名称",
        pic: "/index/index1.jpg",
        prodId: 1,
        price: 1000
      }
    ]
  },
  {
    style: '1',
    id: 1,
    title: '商城热卖',
    prods: [
      {
        prodName: "商品名称",
        pic: "/index/index1.jpg",
        prodId: 1,
        price: 1000
      },
      {
        prodName: "商品名称",
        pic: "/index/index1.jpg",
        prodId: 1,
        price: 1000
      },
      {
        prodName: "商品名称",
        pic: "/index/index1.jpg",
        prodId: 1,
        price: 1000
      },
      {
        prodName: "商品名称",
        pic: "/index/index1.jpg",
        prodId: 1,
        price: 1000
      }
    ]
  }
])
const updata = ref(true)


const toSearchPage = () => {
  uni.navigateTo({
    url: '/pages/search-page/search-page'
  })
}

const toProdPage = (e) => {
  const prodId = e.currentTarget.dataset.prodid

  if (prodId) {
    uni.navigateTo({
      url: '/pages/prod/prod?prodId=' + prodId
    })
  }
}

const toClassifyPage = () => {

}

const addToCart = (prod) => {

}

</script>

<template>
  <view class="container">
    <view class="bg-sear">
      <view class="scrolltop">
        <view
            class="section"
            @tap="toSearchPage"
        >
          <image
              src="@/static/images/icon/search.png"
              class="search-img"
          />
          <text class="placeholder">
            搜索
          </text>
        </view>
      </view>
    </view>

    <view class="content">
      <!-- swiper -->
      <swiper
          :autoplay="autoplay"
          :indicator-color="indicatorColor"
          :interval="interval"
          :duration="duration"
          :indicator-active-color="indicatorActiveColor + ' '"
          :circular="true"
          class="pic-swiper"
          indicator-dots
          previous-margin="20rpx"
          next-margin="20rpx"
      >
        <block
            v-for="(item, index) in indexImages"
            :key="index"
        >
          <swiper-item class="banner-item">
            <view class="img-box">
              <image
                  :src="picDomain + item.imgUrl"
                  :data-prodId="item.relation"
                  class="banner"
                  @tap="toProdPage"
              />
            </view>
          </swiper-item>
        </block>
      </swiper>
      <!-- end swiper -->
    </view>

    <view
        v-if="updata"
        class="updata"
    >
      <block
          v-for="(item, index) in tagList"
          :key="index"
      >
        <!-- 每日上新 -->
        <view
            v-if="item.style==='2' && item.prods && item.prods.length"
            class="up-to-date"
        >
          <view class="title">
            <text>{{ item.title }}</text>
            <view
                class="more-prod-cont"
                data-sts="0"
                :data-id="item.id"
                :data-title="item.title"
                @tap="toClassifyPage"
            >
              <text class="more">
                查看更多
              </text>
            </view>
          </view>
          <view class="item-cont">
            <block
                v-for="(prod, index2) in item.prods"
                :key="index2"
            >
              <view
                  class="prod-item"
                  :data-prodId="prod.prodId"
                  @tap="toProdPage"
              >
                <view>
                  <view class="imagecont">
                    <img-show
                        :src="picDomain + prod.pic"
                        :class-list="['prodimg']"
                    />
                  </view>
                  <view class="prod-text">
                    {{ prod.prodName }}
                  </view>
                  <view class="price">
                    <text class="symbol">
                      ￥
                    </text>
                    <text class="big-num">
                      {{prod.price}}
                    </text>
                  </view>
                </view>
              </view>
            </block>
          </view>
        </view>
        <!-- 商城热卖 -->
        <view
            v-if="item.style==='1' && item.prods && item.prods.length"
            class="hot-sale"
        >
          <view class="title">
            <text>{{ item.title }}</text>
            <view
                class="more-prod-cont"
                data-sts="0"
                :data-id="item.id"
                :data-title="item.title"
                @tap="toClassifyPage"
            >
              <text class="more">
                更多
              </text>
              <text class="arrow" />
            </view>
          </view>
          <view class="hotsale-item-cont">
            <block
                v-for="(prod, index2) in item.prods"
                :key="index2"
            >
              <view
                  class="prod-items"
                  :data-prodId="prod.prodId"
                  @tap="toProdPage"
              >
                <view class="hot-imagecont">
                  <img-show
                      :src="picDomain + prod.pic"
                      :class-list="['hotsaleimg']"
                  />
                </view>
                <view class="hot-text">
                  <view class="hotprod-text">
                    {{ prod.prodName }}
                  </view>
                  <view class="prod-info">
                    {{ prod.brief }}
                  </view>
                  <view class="prod-text-info">
                    <view class="price">
                      <text class="symbol">
                        ￥
                      </text>
                      <text class="big-num">
                        {{prod.price}}
                      </text>
                    </view>
                    <image
                        src="@/static/images/tabbar/basket-sel.png"
                        class="basket-img"
                    />
                  </view>
                </view>
              </view>
            </block>
          </view>
        </view>
      </block>
    </view>
  </view>
</template>

<style scoped>
@import "./index.scss";
</style>