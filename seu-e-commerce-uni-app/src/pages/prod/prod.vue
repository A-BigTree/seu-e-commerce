<template>
  <!-- 商品详情 -->
  <view class="container">
    <!-- 轮播图 -->
    <swiper
        :indicator-dots="indicatorDots"
        :autoplay="autoplay"
        :indicator-color="indicatorColor"
        :interval="interval"
        :duration="duration"
        :indicator-active-color="indicatorActiveColor"
    >
      <block
          v-for="(item, index) in imgs"
          :key="index"
      >
        <swiper-item>
          <image :src="picDomain + item"/>
        </swiper-item>
      </block>
    </swiper>
    <!-- end  轮播图 -->
    <!-- 商品信息 -->
    <view class="prod-info">
      <view class="tit-wrap">
        <view class="prod-tit">
          {{ prodName }}
        </view>
        <view
            class="col"
            @tap="addOrCancelCollection"
        >
          <image
              v-if="!isCollection"
              src="@/static/images/icon/prod-col.png"
          />
          <image
              v-if="isCollection"
              src="@/static/images/icon/prod-col-red.png"
          />
          收藏
        </view>
      </view>
      <view class="sales-p">
        {{ brief }}
      </view>
      <view class="prod-price">
        <text
            v-if="defaultSku && defaultSku.price"
            class="price"
        >
          ￥
          <text class="price-num">
            {{ getDisplayPrice(defaultSku.price) }}
          </text>
        </text>
        <text
            v-if="defaultSku && defaultSku.originPrice"
            class="ori-price"
        >
          ￥{{ getDisplayPrice(defaultSku.originPrice) }}
        </text>
        <text class="sales"/>
      </view>
    </view>
    <!-- 已选规格 -->
    <view
        class="sku"
        @tap="showSku"
    >
      <view class="sku-tit">
        已选
      </view>
      <view class="sku-con">
        {{ selectedProp.length > 0 ? selectedProp + '，' : '' }}{{ prodNum }}件
      </view>
      <view class="more">
        ...
      </view>
    </view>
    <!-- 评价 -->
    <view class="cmt-wrap">
      <view
          class="cmt-tit"
          @tap="showComment"
      >
        <view class="cmt-t">
          评价
          <text class="cmt-good">
            好评{{ prodCommData.positiveRating }}%
          </text>
        </view>
        <view class="cmt-count">
          共{{ prodCommData.number }}条
          <text class="cmt-more"/>
        </view>
      </view>
      <view class="cmt-cont">
        <view
            class="cmt-tag"
            @tap="showComment"
        >
          <text>全部({{ prodCommData.number }})</text>
          <text>好评({{ prodCommData.praiseNumber }})</text>
          <text>中评({{ prodCommData.secondaryNumber }})</text>
          <text>差评({{ prodCommData.negativeNumber }})</text>
        </view>
        <view class="cmt-items">
          <view
              v-for="(item, index) in littleCommPage"
              :key="index"
              class="cmt-item"
          >
            <view class="cmt-user">
              <text class="date">
                {{ item.recTime }}
              </text>
              <view class="cmt-user-info">
                <image
                    class="user-img"
                    :src="item.pic"
                />
                <view class="nickname">
                  {{ item.nickName }}
                </view>
              </view>
            </view>
            <view class="cmt-cnt">
              {{ item.content }}
            </view>
            <scroll-view
                v-if="item.pics.length"
                class="cmt-attr"
                scroll-x="true"
            >
              <image
                  v-for="(commPic, index2) in item.pics"
                  :key="index2"
                  :src="commPic"
              />
            </scroll-view>
          </view>
        </view>
        <view
            v-if="prodCommPage.records.length > 2"
            class="cmt-more-v"
        >
          <text @tap="showComment">
            查看全部评价
          </text>
        </view>
      </view>
    </view>
    <!-- 商品详情 -->
    <view class="prod-detail">
      <view>
        <rich-text :nodes="content"/>
      </view>
    </view>
    <!-- end 商品详情 -->

    <!-- 底部按钮 -->
    <view class="cart-footer">
      <view
          class="btn icon"
          @tap="toHomePage"
      >
        <image src="@/static/images/tabbar/homepage.png"/>
        首页
      </view>
      <view
          class="btn icon"
          @tap="toCartPage"
      >
        <image src="@/static/images/tabbar/basket.png"/>
        购物车
        <view
            v-if="totalCartNum>0"
            class="badge badge-1"
        >
          {{ totalCartNum }}
        </view>
      </view>
      <view
          class="btn cart"
          @tap="showSku"
      >
        <text>加入购物车</text>
      </view>
      <view
          class="btn buy"
          @tap="showSku"
      >
        <text>立即购买</text>
      </view>
    </view>
    <!-- end 底部按钮 -->

    <!-- 规格弹窗 -->
    <view
        v-if="skuShow"
        class="pup-sku"
    >
      <view class="pup-sku-main">
        <view class="pup-sku-header">
          <image
              class="pup-sku-img"
              :src="picDomain + (defaultSku.pic ? defaultSku.pic:pic)"
          />
          <view class="pup-sku-price">
            ￥
            <text
                v-if="defaultSku && defaultSku.price"
                class="pup-sku-price-int"
            >
              {{ getDisplayPrice(defaultSku.price) }}
            </text>
          </view>
          <view class="pup-sku-prop">
            <text>已选</text>
            {{ selectedProp.length > 0 ? selectedProp + '，' : '' }}{{ prodNum }}件
          </view>
          <view
              class="close"
              @tap="closePopup"
          />
        </view>
        <view class="pup-sku-body">
          <view class="pup-sku-area">
            <view
                v-if="skuList.length"
                class="sku-box"
            >
              <block
                  v-for="(skuGroupItem, skuGroupItemIndex) in skuGroupList"
                  :key="skuGroupItemIndex"
              >
                <view
                    class="items sku-text"
                >
                  <text class="sku-kind">
                    {{ skuGroupItem.key }}
                  </text>
                  <view class="con">
                    <text
                        v-for="skuLineItem in skuGroupItem.skuLine"
                        :key="skuLineItem"
                        class="sku-choose-item"
                        :class="[selectedPropList.indexOf(skuGroupItem.key + ':' + skuLineItem) !== -1?'active':'']"
                        @click="toChooseItem(skuGroupItemIndex, skuLineItem, skuGroupItemIndex)"
                    >
                      {{ skuLineItem }}
                    </text>
                  </view>
                </view>
              </block>
            </view>
          </view>
          <view class="pup-sku-count">
            <view class="num-wrap">
              <view
                  class="minus"
                  @tap="onCountMinus"
              >
                <text class="row"/>
              </view>
              <view class="text-wrap">
                <input
                    type="number"
                    :value="prodNum"
                    disabled
                >
              </view>
              <view
                  class="plus"
                  @tap="onCountPlus"
              >
                <text class="row"/>
                <text class="col"/>
              </view>
            </view>
            <view class="count-name">
              数量
            </view>
          </view>
        </view>
        <view class="pup-sku-footer">
          <view
              class="btn cart"
              @tap="addToCart"
          >
            加入购物车
          </view>
          <view
              class="btn buy"
              @tap="buyNow"
          >
            立即购买
          </view>
        </view>
      </view>
    </view>
    <!-- 评价弹窗 -->
    <view
        v-if="commentShow"
        class="cmt-popup"
    >
      <view class="cmt-tit">
        <view class="cmt-t">
          商品评价
          <text class="cmt-good">
            好评度{{ prodCommData.positiveRating }}%
          </text>
        </view>
        <text
            class="close"
            @tap="closePopup"
        />
      </view>
      <view class="cmt-cont">
        <view class="cmt-tag">
          <text
              data-evaluate="-1"
              :class="evaluate === -1?'selected':''"
              @tap="getProdCommPage"
          >
            全部({{ prodCommData.number }})
          </text>
          <text
              data-evaluate="0"
              :class="evaluate === 1?'selected':''"
              @tap="getProdCommPage"
          >
            好评({{ prodCommData.praiseNumber }})
          </text>
          <text
              data-evaluate="1"
              :class="evaluate === 2?'selected':''"
              @tap="getProdCommPage"
          >
            中评({{ prodCommData.secondaryNumber }})
          </text>
          <text
              data-evaluate="2"
              :class="evaluate===3?'selected':''"
              @tap="getProdCommPage"
          >
            差评({{ prodCommData.negativeNumber }})
          </text>
        </view>
        <view class="cmt-items">
          <block v-if="prodCommPage.records.length">
            <view
                v-for="(item, index) in prodCommPage.records"
                :key="index"
                class="cmt-item"
            >
              <view class="cmt-user">
                <text class="date">
                  {{ item.recTime }}
                </text>
                <view class="cmt-user-info">
                  <image
                      class="user-img"
                      :src="item.pic"
                  />
                  <view class="nickname">
                    {{ item.nickName }}
                  </view>
                </view>
              </view>
              <view class="cmt-cnt">
                {{ item.content }}
              </view>
              <scroll-view
                  v-if="item.pics.length"
                  class="cmt-attr"
                  scroll-x="true"
              >
                <image
                    v-for="(commPic, index2) in item.pics"
                    :key="index2"
                    :src="commPic"
                />
              </scroll-view>
              <view
                  v-if="item.replyContent"
                  class="cmt-reply"
              >
                <text class="reply-tit">
                  店铺回复：
                </text>
                {{ item.replyContent }}
              </view>
            </view>
          </block>
          <view
              v-if="!prodCommPage.records.length"
              class="empty"
          >
            暂无评价
          </view>
        </view>
        <view
            v-if="prodCommPage.pages > prodCommPage.current"
            class="load-more"
        >
          <text @tap="getMoreCommPage">
            点击加载更多
          </text>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import {picDomain} from "../../utils/config";
import {ref} from "vue";
import {onLoad, onShareAppMessage, onShow} from "@dcloudio/uni-app";
import {request} from "../../utils/http";
import {getDisplayPrice} from "../../utils/util";

const indicatorDots = ref(true)
const indicatorColor = ref('#f2f2f2')
const indicatorActiveColor = ref('#eb2444')
const autoplay = ref(true)
const interval = ref(3000)
const duration = ref(1000)
const selectedProp = ref([])
let prodId = 0
/**
 * 生命周期函数--监听页面加载
 */
onLoad((options) => {
  prodId = options.prodId// 加载商品信息
  getProdInfo() // 加载商品数据
  getProdCommData() // 加载评论项
  getLittleProdComm() // 查看用户是否关注
})

const app = getApp()
const totalCartNum = ref(0)

/**
 * 分享设置
 */
onShareAppMessage(() => {
  return {
    title: prodName.value,
    path: '/pages/prod/prod?prodId=' + prodId
  }
})

/**
 * 添加或者取消收藏商品
 */
const addOrCancelCollection = () => {
  if (!isCollection.value) {
    request({
      url: '/product/toc/favorite/prod/status/update?action=1&prodId=' + prodId,
      callBack: (res) => {
        isCollection.value = true;
        collectionId.value = parseInt(res.data);
      }
    })
  } else {
    request({
      url: '/product/toc/favorite/prod/status/update?action=2&prodId=' + prodId + "&id=" + collectionId.value,
      callBack: (res) => {
        isCollection.value = false;
        collectionId.value = 0;
      }
    })
  }
}

const skuList = ref([])
const brief = ref('')
const prodNum = ref(1)
const pic = ref('')
const imgs = ref('')
const prodName = ref('')
const price = ref(0)
const content = ref('')
const isCollection = ref(false)
const collectionId = ref(0);

const defaultSku = ref(null)
const skuGroupList = ref([])
const selectedPropList = ref(null)
/**
 * 获取商品信息
 */
const getProdInfo = () => {
  request({
    url: "/product/toc/prod/info/get?prodId=" + prodId,
    method: 'GET',
    callBack: (res) => {
      const prodInfo = res.data
      skuList.value = prodInfo.skus
      brief.value = prodInfo.brief
      pic.value = prodInfo.pic
      imgs.value = [prodInfo.pic];
      if (prodInfo.images) {
        prodInfo.images.forEach(image => {
          imgs.value.push(image);
        })
      }
      prodName.value = prodInfo.prodName
      price.value = prodInfo.price
      content.value = prodInfo.content
      collectionId.value = parseInt(prodInfo.ext);
      isCollection.value = parseInt(prodInfo.ext) > 0;
      // SKU配置
      defaultSku.value = prodInfo.skus[0];
      selectedProp.value = [];
      skuGroupList.value = [];
      selectedPropList.value = [];
      for (let i = 0; i < prodInfo.skus[0].properties.length; i++) {
        selectedProp.value.push(prodInfo.skus[0].properties[i].value);
        selectedPropList.value.push(prodInfo.skus[0].properties[i].prop + ':' + prodInfo.skus[0].properties[i].value);
        let values = [];
        for(let j = 0; j < prodInfo.skus.length; j++) {
          if (values.indexOf(prodInfo.skus[j].properties[i].value) < 0){
            values.push(prodInfo.skus[j].properties[i].value);
          }

        }
        skuGroupList.value.push({
          key: prodInfo.skus[0].properties[i].prop,
          skuLine: values
        });
      }
    }
  })
}


const prodCommData = ref({
  number: 0,
  positiveRating: 0,
  praiseNumber: 0,
  secondaryNumber: 0,
  negativeNumber: 0
})
const getProdCommData = () => {

}

const prodCommPage = ref({
  current: 0,
  pages: 0,
  records: []
});

/**
 * 获取部分评论
 */
const getLittleProdComm = () => {
  if (prodCommPage.value.records.length) {
    return
  }
  getProdCommPage()
}

const getMoreCommPage = () => {
  getProdCommPage()
}

const littleCommPage = ref([])
const evaluate = ref(-1)
/**
 * 获取分页获取评论
 */
const getProdCommPage = (e) => {
  if (e) {
    if (e.currentTarget.dataset.evaluate === evaluate.value) {
      return
    }
    prodCommPage.value = {
      current: 0,
      pages: 0,
      records: []
    }
    evaluate.value = e.currentTarget.dataset.evaluate
  }
}


/**
 * 规格点击事件
 */
const toChooseItem = (skuGroupItemIndex, skuLineItem, index) => {
  selectedProp.value[index] = skuLineItem;
  selectedPropList.value[index] = skuGroupList.value[index].key + ':' + skuLineItem;
  skuList.value.forEach(sku => {
    let flag = true;
    for (let i = 0; i < selectedPropList.value.length; i++) {
      if (sku.properties[i].prop + ':' + sku.properties[i].value !== selectedPropList.value[i]) {
        flag = false;
        break;
      }
    }
    if (flag) {
      defaultSku.value = sku;
    }
  });
}

/**
 * 跳转到首页
 */
const toHomePage = () => {
  uni.switchTab({
    url: '/pages/index/index'
  })
}

/**
 * 跳转到购物车
 */
const toCartPage = () => {
  uni.switchTab({
    url: '/pages/basket/basket'
  })
}

const shopId = 1
/**
 * 加入购物车
 */
const addToCart = () => {

}

/**
 * 立即购买
 */
const buyNow = () => {

}

/**
 * 减数量
 */
const onCountMinus = () => {
  if (prodNum.value > 1) {
    prodNum.value = prodNum.value - 1
  }
}

/**
 * 加数量
 */
const onCountPlus = () => {
  if (prodNum.value < 1000) {
    prodNum.value = prodNum.value + 1
  }
}

const skuShow = ref(false)
const showSku = () => {
  skuShow.value = true
}

const commentShow = ref(false)
const showComment = () => {
  commentShow.value = true
}

const closePopup = () => {
  skuShow.value = false
  commentShow.value = false
}
</script>

<style scoped lang="scss">
@use './prod.scss';
</style>
