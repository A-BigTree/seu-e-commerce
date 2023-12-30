<script setup>
import {ref, watch, inject} from "vue";
import {http} from '@/utils/http';
import {IMAGE_URL} from '@/utils/config'
import {ElMessage} from "element-plus";
import router from "@/router";

const roleType = ref(-1);
const headUrl = ref("");
const nickname = ref("");
const index = ref("0");

const roleTypeRef = inject("roleType");
const userInfo1 = inject("userInfo");

const params = {
  url: "/account/user/info/get",
  method: "get",
  callBack: (res) => {
    const userInfo = res.data;
    userInfo1.value = res.data;
    roleType.value = userInfo.roleType;
    roleTypeRef.value = roleType.value;
    headUrl.value = userInfo.image ? IMAGE_URL + userInfo.image : IMAGE_URL + '/head/0.png';
    nickname.value = userInfo.nickname;
    index.value = roleType.value === 2 ? '2-1' : '1-1';
  }
}
http(params);

const unreadCount = ref(0);

http({
  url: "/im/message/unread/count",
  method: "get",
  callBack: (res) => {
    unreadCount.value = parseInt(res.data);
    if (unreadCount.value === 0) {
      return;
    }
    ElMessage({
      message: "您有" + unreadCount.value + "条未读消息",
      type: "success"
    });
  }
})

const select = (in_) => {
  index.value = in_;
}

watch(index, (newIndex) => {
  switch (newIndex) {
    case '0':
      router.push();
      return;
    case '1-1':
      router.push('/account/manage');
      return;
    case '2-1':
      router.push('/product/manage');
      return;
    case '2-2':
      router.push({
        name: 'product-edit',
        params: {
          prodId: 0
        }
      });
      return;
    case '2-3':
      router.push({
        name: 'prod-category-manage',
        params: {
          parentId: 0
        }
      });
      return;
    case '2-4':
      router.push({
        name: 'prod-prop-manage'
      });
      return;
    case '3-1':
      router.push({
        name: 'order-manage'
      });
      return;
    case '4-1':
      router.push({
        name: 'message-worksheet'
      });
      return;
  }
}, {deep: true});

const logout = () => {
  const request = {
    url: "/account/user/logout",
    callBack: (res) => {
      ElMessage({
        message: "退出成功",
        type: "success"
      });
      setTimeout(() => {
        router.push({
          name: "login"
        });
      }, 1000);
    }
  }
  http(request);
}

const toMessage = () => {
  index.value = '4-1';
}

</script>

<template>
  <el-container v-if="roleType !== -1">
    <el-header>
      <el-menu
          default-active="logo"
          :ellipsis="false"
          mode="horizontal">
        <el-menu-item index="logo">
          <img style="width: 100px" class="logo" src="@/assets/img/logo.png" alt="">
        </el-menu-item>
        <div class="flex-grow"/>
        <el-menu-item index="head">
          <img style="width: 80px" class="logo" :src="headUrl" alt="">
        </el-menu-item>
        <el-sub-menu index="user">
          <template #title>
            {{ nickname }}
          </template>
          <el-menu-item index="message" @click="toMessage">
            <el-badge :value="unreadCount">
                消息
            </el-badge>
          </el-menu-item>
          <el-menu-item index="logout" @click="logout">
            退出登录
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-header>
    <el-container>
      <el-aside>
        <el-menu
            @select="select"
            :default-active="index">
          <el-sub-menu index="1" v-if="roleType===3">
            <template #title>
              账号管理
            </template>
            <el-menu-item index="1-1">
              账号列表
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              商品管理
            </template>
            <el-menu-item index="2-1">
              商品列表
            </el-menu-item>
            <el-menu-item index="2-2" v-if="roleType===2">
              添加商品
            </el-menu-item>
            <el-menu-item index="2-3">
              商品类别
            </el-menu-item>
            <el-menu-item index="2-4">
              类别属性
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="3" v-if="roleType===2">
            <template #title>
              订单管理
            </template>
            <el-menu-item index="3-1">
              订单列表
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="4" v-if="roleType===2">
            <template #title>
              客服
            </template>
            <el-menu-item index="4-1">
              客服工作台
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.logo {
  transform: scale(0.5);
}

.flex-grow {
  flex-grow: 1;
}
</style>

