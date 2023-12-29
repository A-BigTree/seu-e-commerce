<script setup lang="ts">
import {ref} from 'vue'
import {picDomain, wsDomain} from '../../utils/config'
import {onLoad} from "@dcloudio/uni-app";
import {request} from "../../utils/http";

const socket = ref<WebSocket>()
const fromUserId = ref(0);
const toUserId = ref(0)

const fromUserInfo = ref(null);

const messageList = ref([
  {
    toUserId: 0,
    headImg: '',
    nickname: '',
    unreadCount: 0,
    lastMessage: {
      content: '',
      createTime: 0
    }
  }
]);

const currSession = ref({
  toUserId: 0,
  headImg: '',
  nickname: '',
  unreadCount: 0
});

const openMessage = ref(false);

const currMessages = ref([{
  id: 0,
  fromUserId: 0,
  toUserId: 0,
  content: '',
  createTime: 0
}]);

const showMessage = ref([])

onLoad((options) => {
  if (options) {
    if (options.shopId) {
      toUserId.value = options.shopId;
    }
  }
  fromUserInfo.value = uni.getStorageSync("userInfo");
  if (!fromUserInfo.value) {
    uni.showModal({
      title: "提示",
      content: "您还未登录，是否前往登录",
      cancelText: "取消",
      confirmText: "确定",
      success: res => {
        if (res.confirm) {
          uni.navigateTo({
            url: "/pages/login/login"
          }).then(r => {
          });
        } else {
          uni.navigateTo({
            url: "/pages/index/index"
          }).then(r => {
          });
        }
      }
    });
  }
  openMessage.value = false;
  fromUserId.value = parseInt(fromUserInfo.value.id);
  socket.value = new WebSocket(wsDomain + '/server/' + fromUserId.value);
  // 连接
  socket.value.onopen = function () {
    console.log("websocket已连接")
  }
  // 监听消息
  socket.value.onmessage = function (res) {
    onMessage(JSON.parse(res.data));
  }
  // 监听错误
  socket.value.onerror = function (res) {
    console.log("收到错误", res)
  }
  // 监听关闭
  socket.value.onclose = function () {
    console.log("WebSocket关闭")
  }
});

const onMessage = (message) => {
  if (message.msgType == 2) {
    messageList.value = JSON.parse(message.content);
  }
}

const selectMessage = (item) => {
  currSession.value = item;
  toUserId.value = item.toUserId;
  getPageMessage();
  openMessage.value = true;
  currMessages.value = [];
  showMessage.value = [];
}

const closePopup = () => {
  openMessage.value = false;
  currSession.value = null;
  toUserId.value = 0;
  currMessages.value = [];
  showMessage.value = [];
}

const page = ref({
  pageNum: 1,
  pageSize: 10
})

const getPageMessage = () => {
  request({
    url: "/im/message/page/list",
    data: {
      fromUserId: fromUserId.value,
      toUserId: toUserId.value,
      page: page.value,
    },
    callBack: (res) => {
      const data = res.data;
      let tmp = currMessages.value;
      currMessages.value = tmp.concat(data.records);
      showMessage.value = [...currMessages.value].reverse();
    }
  })

}

/**
 * 时间戳转换为日期格式
 */
const timestampToTime = (timestamp) => {
  const date = new Date(timestamp);
  const Y = date.getFullYear() + '-';
  const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
  const D = date.getDate() + ' ';
  const h = date.getHours() + ':';
  const m = date.getMinutes();
  return Y + M + D + h + m;
}

</script>

<template>
  <view class="container">
    <view>
      <view>
        <uni-list :border="true" v-for="(item) in messageList">
          <uni-list-chat v-if="item.unreadCount > 0"
                         :title="item.nickname"
                         :avatar="item.headImg ? picDomain + item.headImg : '/static/images/icon/head04.png'"
                         :time="timestampToTime(item.lastMessage.createTime)"
                         :note="item.lastMessage.content"
                         :badge-text="item.unreadCount"
                         clickable @click="selectMessage(item)"
          />
          <uni-list-chat v-else
                         :title="item.nickname"
                         :avatar="item.headImg ? picDomain + item.headImg : '/static/images/icon/head04.png'"
                         :time="timestampToTime(item.lastMessage.createTime)"
                         :note="item.lastMessage.content"
                         clickable @click="selectMessage(item)"
          />
        </uni-list>
      </view>
    </view>

    <view class="message-popup" v-if="openMessage">
      <view class="cmt-tit">
        <view class="cmt-t">
          {{ currSession.nickname }}
        </view>
        <text class="close"
              @tap="closePopup"
        />
      </view>
      <view v-if="currMessages">
        <block v-for="(item, index) in showMessage" :key="index">
          <view>
            {{ timestampToTime(item.createTime) }}:{{ currSession.nickname }}:{{ item.content }}
          </view>
        </block>
      </view>
      <div class="chat-input-container">
        <input type="text" placeholder="输入消息...">
        <button>发送</button>
      </div>
    </view>
  </view>


</template>

<style scoped lang="scss">
@use './message.scss';
</style>