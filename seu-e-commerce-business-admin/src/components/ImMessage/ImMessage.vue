<script setup lang="ts">
import {inject, nextTick, onMounted, onUnmounted, ref, watch} from 'vue'
import {WSS_URL, IMAGE_URL, DEFAULT_HEAD_IMAGE} from '@/utils/config';
import {http} from '@/utils/http';
import {ElLoading, ElMessage, ElMessageBox} from "element-plus";

const socket = ref<WebSocket>()
const fromUserId = ref(0);
const toUserId = ref(0)

const interval = ref(null);

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

const userInfo = inject("userInfo");

const init = () => {
  showMessage.value = [];
  currMessages.value = [];
  if (!userInfo) {
    return;
  }
  fromUserId.value = userInfo.value.id;
  const loading = ElLoading.service({
    lock: true,
    text: '正在连接...',
    background: 'rgba(0, 0, 0, 0.7)'
  });
  socket.value = new WebSocket(WSS_URL + '/server/' + fromUserId.value);
  // 连接
  socket.value.onopen = function () {
    loading.close();
    console.log("websocket已连接")
    ElMessage({
      message: '连接成功',
      type: 'success'
    })
  }
  // 监听消息
  socket.value.onmessage = function (res) {
    if (res.data === "pong") return;
    onMessage(JSON.parse(res.data));
  }
  // 监听错误
  socket.value.onerror = function (res) {
    console.log("收到错误", res)
    loading.close();
    if (interval.value !== null) {
      clearInterval(interval.value);
    }
    ElMessageBox.confirm("连接失败，请重新连接",
        'danger',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }
    ).then(() => {
      location.reload();
    });
  }
  // 监听关闭
  socket.value.onclose = function () {
    console.log("WebSocket关闭")
  }
}

const onMessage = (message) => {
  if (message.msgType == 2) {
    messageList.value = JSON.parse(message.content);
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
      messageList.value.forEach(msg => {
        if (msg.toUserId === id) {
          msg.lastMessage = message;
          return;
        }
      })
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

const timestampToTime = (timestamp) => {
  const date = new Date(timestamp);
  const Y = date.getFullYear() + '-';
  const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
  const D = date.getDate() + ' ';
  const h = date.getHours() + ':';
  const m = date.getMinutes();
  return Y + M + D + h + m;
}

const currSession = ref({
  toUserId: 0,
  headImg: '',
  nickname: '',
  unreadCount: 0
});

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
}])

const page = ref({
  pageNum: 1,
  pageSize: 10,
});

const selectMessage = (item) => {
  currSession.value = item;
  toUserId.value = item.toUserId;
  currMessages.value = [];
  showMessage.value = [];
  isOver.value = false;
  page.value = {
    pageNum: 1,
    pageSize: 10
  }
  getPageMessage();
  item.unreadCount = 0;
}

const isOver = ref(false);
const getPageMessage = () => {
  if (isOver.value) {
    return;
  }
  http({
    url: "/im/message/page/list",
    data: {
      fromUserId: fromUserId.value,
      toUserId: toUserId.value,
      page: page.value,
    },
    callBack: (res) => {
      if (res.data.records.length == 0) {
        isOver.value = true;
        return;
      }
      currMessages.value = currMessages.value.concat(res.data.records);
      showMessage.value = [...currMessages.value].reverse();
      page.value.pageNum++;
    }
  })
}

const message = ref('');

const sendMessage = () => {
  if (message.value.trim() === '') {
    ElMessage({
      message: '请输入内容',
      type: 'warning'
    })
    return;
  }
  const msg = {
    msgType: 4,
    fromUserId: fromUserId.value,
    toUserId: toUserId.value,
    content: message.value,
    status: 0
  }
  socket.value.send(JSON.stringify(msg));
  message.value = '';
}

watch(showMessage, (newMessage) => {
  if (newMessage.length > 0) {
    let main = document.getElementById("chatMain");
    nextTick(()=>{
      main.scrollTop = main.scrollHeight;
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

}, {deep: true})

onMounted(() => {
  init();
  interval.value = setInterval(() => {
        socket.value.send(JSON.stringify({
          msgType: 1,
          fromUserId: fromUserId.value,
          content: "ping"
        }));
      }, 30000);
})

onUnmounted(() => {
  clearInterval(interval.value);
  socket.value.close();
})

const chatMain = ref(null);

</script>

<template>
  <el-card shadow="always">
    <el-container>
      <el-aside width="200px" class="aside-message">
        <ul class="user-list">
          <li class="user-item" v-for="item in messageList" @click="selectMessage(item)">
            <el-badge v-if="item.unreadCount > 0" :value="item.unreadCount" :max="10">
              <img class="user-avatar" :src="item.headImg?IMAGE_URL + item.headImg : DEFAULT_HEAD_IMAGE"/>
            </el-badge>
            <img v-else class="user-avatar" :src="item.headImg?IMAGE_URL + item.headImg : DEFAULT_HEAD_IMAGE"/>
            <div class="user-details">
              <div class="user-nickname">{{ item.nickname }}</div>
              <div class="latest-message">{{ item.lastMessage.content }}</div>
            </div>
          </li>
        </ul>
      </el-aside>
      <el-container>
        <el-main style="height: 600px" class="main-message" id="chatMain">
          <div  v-if="showMessage.length > 0">
            <div style="margin: 10px auto;text-align:center;" v-if="!isOver">
              <el-button plain size="small" @click="getPageMessage">加载更多</el-button>
            </div>
            <div v-for="item in showMessage" class="chat-history">
              <div v-if="item.toUserId === fromUserId" class="message received">
                <img class="avatar" :src="currSession.headImg ? IMAGE_URL + currSession.headImg : DEFAULT_HEAD_IMAGE"/>
                <div class="message-bubble">
                  <div class="timestamp">{{ timestampToTime(item.createTime) }}</div>
                  <p>{{ item.content }}</p>
                  <div :class="item.status === 0 ? 'unread-status' : 'read-status'">
                    {{ item.status === 0 ? '未读' : '已读' }}
                  </div>
                </div>
              </div>

              <div v-else class="message sent">
                <div class="message-bubble">
                  <div class="timestamp">{{ timestampToTime(item.createTime) }}</div>
                  <p>{{ item.content }}</p>
                  <div :class="item.status === 0 ? 'unread-status' : 'read-status'">
                    {{ item.status === 0 ? '未读' : '已读' }}
                  </div>
                </div>
                <img class="avatar-sent" :src="userInfo.image ? IMAGE_URL + userInfo.image : DEFAULT_HEAD_IMAGE"/>
              </div>
            </div>
          </div>

          <el-empty v-else description="暂无消息"></el-empty>
        </el-main>
        <el-footer v-if="showMessage.length > 0" height="120px" class="input-area">
          <div>
            <el-input type="textarea" v-model="message" placeholder="请输入内容" style="width: 100%"></el-input>
          </div>
          <div class="bnt">
            <el-button type="primary" @click="sendMessage" size="large">发送</el-button>
          </div>

        </el-footer>
      </el-container>
    </el-container>
  </el-card>

</template>

<style scoped>
.aside-message {
  border-right: 1px solid #ccc;
}

.main-message {
  overflow-y: auto;
  height: 600px;
}

.input-area {
  padding: 20px;
  border-top: 1px solid #ccc;
}

.user-list {
  list-style: none;
  padding: 0;
}

.user-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  cursor: pointer;
  border-bottom: 2px solid #ccc;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.user-details {
  flex: 1;
}

.user-nickname {
  font-weight: bold;
}

.latest-message {
  font-size: 12px;
  color: #95a5a6;
}

.chat-history {
  padding: 20px;
}

.message {
  margin-bottom: 20px;
  display: flex;
  align-items: flex-start;
}

.message .avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.message .avatar-sent {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-left: 10px;
}

.message.sent {
  justify-content: flex-end;
  align-items: flex-end;
}

.message-bubble {
  max-width: 70%;
  padding: 10px;
  border-radius: 10px;
  background-color: #3498db;
  color: #fff;
  position: relative;
}

.message-bubble .timestamp {
  font-size: 12px;
  color: #95a5a6;
  right: 5px;
}

.message.sent .message-bubble {
  background-color: #3498db;
  color: #fff;
}

.message.received .message-bubble {
  background-color: #ecf0f1;
  color: #333;
}

.read-status {
  font-size: 12px;
  color: #2ecc71;
  margin-left: 5px;
}

.unread-status {
  font-size: 12px;
  color: #e74c3c;
  margin-left: 5px;
}

.bnt {
  margin-top: 5px;
  float: right;
}
</style>