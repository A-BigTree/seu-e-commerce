<template>
  <view class="container">
    <!--input列表 -->
    <view class="input-box">
      <view class="section">
        <text>收 货 人</text>
        <input
            placeholder="姓名"
            type="text"
            maxlength="15"
            :value="receiver"
            @input="onReceiverInput"
        >
      </view>
      <view class="section">
        <text>手机号码</text>
        <input
            placeholder="11位手机号码"
            type="number"
            maxlength="11"
            :value="mobile"
            @input="onMobileInput"
        >
      </view>
      <view
          class="section"
          @tap="translate"
      >
        <text>所在地区</text>
        <view class="pca">
          {{ province }} {{ city }} {{ area }}
        </view>
        <view
            class="animation-element-wrapper"
            :animation="animation"
            :style="'visibility:' + (show ? 'visible':'hidden')"
            @tap.stop="hiddenFloatView"
        >
          <view
              class="animation-element"
          >
            <text
                class="right-bt"
                @tap.stop="hiddenFloatView"
            >
              确定
            </text>
            <view class="line"/>
            <picker-view
                indicator-style="height: 50rpx;"
                :value="valArr"
                @change="bindChange"
            >
              <!--省-->
              <picker-view-column>
                <view
                    v-for="(item, indexs) in provArray"
                    :key="indexs"
                >
                  {{ item.areaName }}
                </view>
              </picker-view-column>
              <!--地级市-->
              <picker-view-column>
                <view
                    v-for="(item, indexss) in cityArray"
                    :key="indexss"
                >
                  {{ item.areaName }}
                </view>
              </picker-view-column>
              <!--区县-->
              <picker-view-column>
                <view
                    v-for="(item, indexsss) in areaArray"
                    :key="indexsss"
                >
                  {{ item.areaName }}
                </view>
              </picker-view-column>
            </picker-view>
          </view>
        </view>

        <view class="arrow">
          <image src="@/static/images/icon/more.png"/>
        </view>
      </view>
      <view class="section">
        <text>详细地址</text>
        <input
            placeholder="如楼号/单元/门牌号"
            type="text"
            :value="addr"
            @input="onAddrInput"
        >
      </view>
    </view>
    <!-- end input列表 -->
    <!-- 功能按钮 -->
    <view class="btn-box">
      <view
          class="keep btn"
          @tap="onSaveAddr"
      >
        <text>保存收货地址</text>
      </view>

      <view
          v-if="addrId!==0"
          class="clear btn"
          @tap="onDeleteAddr"
      >
        <text>删除收货地址</text>
      </view>
    </view>
    <!-- end 功能按钮 -->
  </view>
</template>

<script setup>
import {ref} from "vue";
import {onLoad} from "@dcloudio/uni-app";
import {request} from "../../utils/http";

const addrId = ref(0)
const city = ref('')
const area = ref('')
const provinceId = ref(0)
const cityId = ref(0)
const areaId = ref(0)
const receiver = ref('')
const mobile = ref('')
const addr = ref('')
const province = ref('')

const valArr = ref([0, 0, 0])
const provArray = ref([])
const areaArray = ref([])
const cityArray = ref([])

const initCityData = (provinceId, cityId, areaId) => {
  request({
    url: "/order/area/level/get?levelId=0",
    method: "GET",
    callBack: (res) => {
      provArray.value = res.data;
      if (provinceId) {
        for (let i = 0; i < provArray.value.length; i++) {
          if (provArray.value[i].id === provinceId) {
            valArr.value[0] = i;
            break;
          }
        }
      }
      getCityArray(provinceId || provArray.value[0].id, cityId, areaId);
    }
  })
}

onLoad((options) => {
  valArr.value = [0, 0, 0];
  provinceId.value = 0;
  cityId.value = 0;
  areaId.value = 0;
  if (options.addrId) {
    addrId.value = options.addrId;
    request({
      url: "/order/area/address/get?addressId=" + addrId.value,
      method: "GET",
      callBack: (res) => {
        const data = res.data;
        receiver.value = data.receiver;
        mobile.value = data.mobile;
        addr.value = data.address;
        province.value = data.area.province.areaName;
        city.value = data.area.city.areaName;
        area.value = data.area.area.areaName;
        provinceId.value = data.area.province.id;
        cityId.value = data.area.city.id;
        areaId.value = data.area.area.id;
      }
    });
  }
  initCityData(provinceId.value, cityId.value, areaId.value);

});

const getCityArray = (provinceId, cityId, areaId) => {
  request({
    url: "/order/area/level/get?levelId=" + provinceId,
    method: "GET",
    callBack: (res) => {
      cityArray.value = res.data;
      if (cityId) {
        for (let i = 0; i < cityArray.value.length; i++) {
          if (cityArray.value[i].id === cityId) {
            valArr.value[1] = i;
            break;
          }
        }
      }
      getAreaArray(cityId || cityArray.value[0].id, areaId);
    }
  })
}

const getAreaArray = (cityId_, areaId_) => {
  request({
    url: "/order/area/level/get?levelId=" + cityId_,
    method: "GET",
    callBack: (res) => {
      areaArray.value = res.data;
      if (areaId_) {
        for (let i = 0; i < areaArray.value.length; i++) {
          if (areaArray.value[i].id === areaId_) {
            valArr.value[2] = i;
            break;
          }
        }
      } else {
        province.value = provArray.value[valArr.value[0]].areaName;
        city.value = cityArray.value[valArr.value[1]].areaName;
        area.value = areaArray.value[valArr.value[2]].areaName;
        provinceId.value = provArray.value[valArr.value[0]].id;
        cityId.value = cityArray.value[valArr.value[1]].id;
        areaId.value = areaArray.value[valArr.value[2]].id;
      }
    }
  })
}

let indexArr = [18, 0, 0]

/**
 * 滑动事件
 */
const bindChange = (e) => {
  if (!provArray.value[valArr.value[0]] || !cityArray.value[valArr.value[1]] || !areaArray.value[valArr.value[2]]) {
    return;
  }
  // 判断滑动的是第几个column
  const val = e.detail.value
  // 若省份column做了滑动则定位到地级市和区县第一位
  if (indexArr[0] !== val[0]) {
    val[1] = 0
    val[2] = 0 // 更新数据
    // 获取地级市数据
    getCityArray(provArray.value[val[0]].id)
  } else {
    // 若省份column未做滑动，地级市做了滑动则定位区县第一位
    if (indexArr[1] !== val[1]) {
      val[2] = 0 // 更新数据
      getAreaArray(cityArray.value[val[1]].id) // 获取区县数据
    }
  }
  indexArr = val
  valArr.value = [val[0], val[1], val[2]]
  province.value = provArray.value[valArr.value[0]].areaName
  city.value = cityArray.value[valArr.value[1]].areaName
  area.value = areaArray.value[valArr.value[2]].areaName
  provinceId.value = provArray.value[valArr.value[0]].id
  cityId.value = cityArray.value[valArr.value[1]].id
  areaId.value = areaArray.value[valArr.value[2]].id
}

let t = 0
let moveY = 200
const show = ref('')
/**
 * 移动按钮点击事件
 */
const translate = () => {
  if (t === 0) {
    moveY = 0
    show.value = true
    t = 1
  } else {
    moveY = 200
    show.value = false
    t = 0
  }
  animationEvents(moveY, show.value)
}

/**
 * 隐藏弹窗浮层
 */
const hiddenFloatView = () => {
  moveY = 200
  show.value = false
  t = 0
  animationEvents(moveY, show.value)
}

const animation = ref('')
/**
 * 动画事件
 */
const animationEvents = (moveY, showParam) => {
  animation.value = uni.createAnimation({
    transformOrigin: '50% 50%',
    duration: 400,
    timingFunction: 'ease',
    delay: 0
  })
  animation.value.translateY(moveY + 'vh').step()
  animation.value = animation.value.export()
  show.value = showParam
}

/**
 * 保存地址
 */
const onSaveAddr = () => {
  const receiverParam = receiver.value
  const mobileParam = mobile.value
  const addrParam = addr.value

  if (!receiverParam.trim()) {
    receiver.value = ''
    uni.showToast({
      title: '请输入收货人姓名',
      icon: 'none'
    })
    return
  }

  if (!mobileParam) {
    uni.showToast({
      title: '请输入手机号码',
      icon: 'none'
    })
    return
  }

  if (mobileParam.length !== 11) {
    uni.showToast({
      title: '请输入正确的手机号码',
      icon: 'none'
    })
    return
  }

  if (!addrParam.trim()) {
    receiver.value = ''
    uni.showToast({
      title: '请输入详细地址',
      icon: 'none'
    })
    return;
  }

  request({
    url: "/order/area/address/update",
    data: {
      action: addrId.value === 0 ? 1 : 3,
      data: {
        id: addrId.value,
        receiver: receiver.value,
        mobile: mobile.value,
        address: addr.value,
        area: {
          areaId: areaId.value,
          province: {
            id: provinceId.value
          },
          city: {
            id: cityId.value
          },
          area: {
            id: areaId.value
          }
        }
      }
    },
    callBack: (res) => {
      uni.navigateBack({
        delta: 1
      })
    }
  })
}
const onReceiverInput = (e) => {
  receiver.value = e.detail.value
}
const onMobileInput = (e) => {
  mobile.value = e.detail.value
}
const onAddrInput = (e) => {
  addr.value = e.detail.value
}

/**
 * 删除配送地址
 */
const onDeleteAddr = () => {
  uni.showModal({
    title: '',
    content: '确定要删除此收货地址吗？',
    confirmColor: '#eb2444',
    success(res) {
      if (res.confirm) {
        request({
          url: "/order/area/address/update",
          data: {
            action: 2,
            data: {
              id: addrId.value
            }
          },
          callBack: (res) => {
            uni.navigateBack({
              delta: 1
            })
          }
        })
      }
    }
  })
}
</script>

<style scoped lang="scss">
@use './editAddress.scss';
</style>
