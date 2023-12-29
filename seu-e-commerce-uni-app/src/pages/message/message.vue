<script setup lang="ts">
import {ref, watch, nextTick} from 'vue'
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

const showMessage = ref([{
  id: 0,
  fromUserId: 0,
  toUserId: 0,
  content: '',
  createTime: 0,
  status: 0
}]);


onLoad((options) => {
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
  // 商品页面进入
  if (options) {
    if (options.shopId) {
      toUserId.value = parseInt(options.shopId);
      currMessages.value = [];
      showMessage.value = [];
      messageList.value.forEach(msg => {
        if (msg.toUserId === toUserId.value) {
          currSession.value = msg;
        }
      });
      getPageMessage();
      openMessage.value = true;
    }
  }

});

const onMessage = (message) => {
  if (message.msgType == 2) {
    messageList.value = JSON.parse(message.content);
    if (toUserId.value !== 0 && currSession.value === null) {
      messageList.value.forEach(msg => {
        if (msg.toUserId === toUserId.value) {
          currSession.value = msg;
          return;
        }
      });
    }
  } else if (message.msgType == 3) {
    const ids = JSON.parse(message.content);
    if (message.fromUserId !== toUserId.value) {
      return;
    }
    showMessage.value.forEach(msg => {
      if (ids.includes(msg.id)) {
        msg.status = 1;
      }
    });
  } else if (message.msgType == 4) {
    const id = message.fromUserId === fromUserId.value ? message.toUserId : message.fromUserId;
    if (message.fromUserId === toUserId.value || toUserId.value === message.toUserId) {
      showMessage.value.push(message);
      currMessages.value = [message].concat(currMessages.value);
      let flag = false;
      messageList.value.forEach(msg => {
        if (msg.toUserId === id) {
          msg.lastMessage = message;
          flag = true;
          return;
        }
      });
      if (!flag) {
        socket.value.send(JSON.stringify({
          msgType: 2,
          fromUserId: fromUserId.value,
          toUserId: 0
        }));
      }
    } else {
      let flag = false;
      messageList.value.forEach(msg => {
        if (msg.toUserId === id) {
          msg.unreadCount = msg.unreadCount + 1;
          msg.lastMessage = message;
          flag = true;
          return;
        }
      });
      if (!flag) {
        socket.value.send(JSON.stringify({
          msgType: 2,
          fromUserId: fromUserId.value,
          toUserId: 0
        }));
      }
    }
  }
}

const selectMessage = (item) => {
  currSession.value = item;
  toUserId.value = item.toUserId;
  currMessages.value = [];
  showMessage.value = [];
  getPageMessage();
  openMessage.value = true;
}

const closePopup = () => {
  openMessage.value = false;
  currSession.value = null;
  toUserId.value = 0;
  currMessages.value = [];
  showMessage.value = [];
  page.value = {
    pageNum: 1,
    pageSize: 10
  }
  socket.value.send(JSON.stringify({
    msgType: 2,
    fromUserId: fromUserId.value,
    toUserId: 0
  }));
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

watch(showMessage, (newMessage) => {
  if (newMessage.length > 0) {
    nextTick(() => {
      window.scrollTo(0, document.getElementById("chatMain").scrollHeight)
    })
    const ids = [];
    newMessage.forEach(msg => {
      if (msg.status === 0 && msg.fromUserId !== fromUserId.value) {
        ids.push(msg.id);
      }
    });
    if (ids.length > 0) {
      socket.value.send(JSON.stringify({
        msgType: 3,
        fromUserId: fromUserId.value,
        toUserId: toUserId.value,
        content: JSON.stringify(ids)
      }));
    }
  }
}, {deep: true});

const message = ref("");

const sendMessage = () => {
  if (message.value) {
    const data = {
      msgType: 4,
      fromUserId: fromUserId.value,
      toUserId: toUserId.value,
      content: message.value,
      status: 0
    }
    socket.value.send(JSON.stringify(data));
    message.value = "";
  } else {
    uni.showToast({
      title: "消息不能为空",
      icon: "none"
    })
  }
}

</script>

<template>
  <view>
    <view v-if="!openMessage">
      <view v-if="messageList">
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
      <view v-else class="empty">
        <text>暂时没有消息~</text>
      </view>
    </view>

    <view v-if="openMessage">
      <view v-if="currMessages" class="chat-main" id="chatMain">
        <block v-for="(item, index) in showMessage" :key="index">
          <view class="chat-container">
            <view v-if="item.toUserId === fromUserId" class="message received">
              <img class="user-avatar-received"
                   :src="currSession.headImg ? picDomain + currSession.headImg : '/static/images/icon/head04.png'"/>
              <view class="message-content">
                <view class="timestamp">{{ timestampToTime(item.createTime) }}</view>
                <view class="message-text">{{ item.content }}</view>
                <view :class="item.status === 0 ? 'status unread' : 'status'">
                  {{ item.status === 0 ? '未读' : '已读' }}
                </view>
              </view>
            </view>

            <view v-else class="message sent">
              <view class="message-content">
                <view class="timestamp">{{ timestampToTime(item.createTime) }}</view>
                <view class="message-text">{{ item.content }}</view>
                <view :class="item.status === 0 ? 'status unread' : 'status'">
                  {{ item.status === 0 ? '未读' : '已读' }}
                </view>
              </view>
              <img class="user-avatar-sent"
                   :src="fromUserInfo.image ? picDomain + fromUserInfo.image : '/static/images/icon/head04.png'"/>
            </view>
          </view>
        </block>
      </view>
      <view v-else class="empty">
        <text>暂时没有消息~</text>
      </view>
      <view class="chat-input-container">
        <input type="text" placeholder="输入消息..." style="width: 70%" v-model="message">
        <button @click="sendMessage">发送</button>
      </view>
    </view>

    <uni-fab
        v-if="openMessage"
        :pattern="{icon: 'closeempty'}"
        horizontal="right"
        vertical="top"
        :popMenu="false"
        @fabClick="closePopup"
    />
  </view>


</template>

<style scoped lang="scss">
@use './message.scss';
</style>