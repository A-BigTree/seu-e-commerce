<script setup>

import {ref} from "vue";
import ImgShow from "../../components/img-show/img-show.vue";

const indicatorColor = ref('#d1e5fb')
const indicatorActiveColor = ref('#1b7dec')
const autoplay = ref(true)
const interval = ref(2000)
const duration = ref(1000)
const indexImages = ref([])
const tagList = ref([])
const updata = ref(true)


const toSearchPage = () => {

}

const toProdPage = () => {

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
                  :src="item.imgUrl"
                  :data-prodid="item.relation"
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
                  :data-prodid="prod.prodId"
                  @tap="toProdPage"
              >
                <view>
                  <view class="imagecont">
                    <img-show
                        :src="prod.pic"
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
                      {{ }}
                    </text>
                    <text class="small-num">
                      .{{ }}
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
                  :data-prodid="prod.prodId"
                  @tap="toProdPage"
              >
                <view class="hot-imagecont">
                  <img-show
                      :src="prod.pic"
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
                        {{ }}
                      </text>
                      <text class="small-num">
                        .{{ }}
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

        <!-- 更多宝贝 -->
        <view
            v-if="item.style==='0' && item.prods && item.prods.length"
            class="more-prod"
        >
          <view class="title">
            {{ item.title }}
          </view>
          <view class="prod-show">
            <block
                v-for="(prod, index2) in item.prods"
                :key="index2"
            >
              <view
                  class="show-item"
                  :data-prodid="prod.prodId"
                  @tap="toProdPage"
              >
                <view class="more-prod-pic">
                  <img-show
                      :src="prod.pic"
                      :class-list="['more-pic']"
                  />
                </view>
                <view class="prod-text-right">
                  <view class="prod-text more">
                    {{ prod.prodName }}
                  </view>
                  <view class="prod-info">
                    {{ prod.brief }}
                  </view>
                  <view class="b-cart">
                    <view class="price">
                      <text class="symbol">
                        ￥
                      </text>
                      <text class="big-num">
                        {{ }}
                      </text>
                      <text class="small-num">
                        .{{ }}
                      </text>
                    </view>
                    <image
                        src="@/static/images/tabbar/basket-sel.png"
                        class="basket-img"
                        @tap.stop="addToCart(prod)"
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