<script setup>
import {ref} from "vue";
import AccountManage from "@/components/AccountManage/AccountManage.vue";
import ProductManage from "@/components/ProductManage/ProductManage.vue";
import {http} from '@/utils/http';
import {IMAGE_URL} from '@/utils/config'
import {ElMessage} from "element-plus";
import router from "@/router";

const roleType = ref(-1);

const headUrl = ref("");

const nickname = ref("");

const params = {
  url: "/account/user/info/get",
  method: "get",
  callBack: (res) => {
    const userInfo = res.data;
    roleType.value = userInfo.roleType;
    headUrl.value = userInfo.image ? IMAGE_URL + userInfo.image : IMAGE_URL + '/head/0.png';
    nickname.value = userInfo.nickname;
  }
}
http(params);

const index = roleType.value === 2 ? ref("0") : ref("1-1");

const select = (in_) => {
  index.value = in_;
}

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
          <el-menu-item index="message">
            消息
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
          <el-menu-item index="0" v-if="roleType===2">
            商家首页
          </el-menu-item>
          <el-sub-menu index="1" v-if="roleType===3">
            <template #title>
              账号管理
            </template>
            <el-menu-item index="1-1">
              账号列表
            </el-menu-item>
            <el-menu-item index="1-2">
              审核历史
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
            <el-menu-item index="2-3" v-if="roleType===3">
              审核历史
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-main>
        <AccountManage v-if="index === '1-1'"/>
        <ProductManage v-if="index === '2-1'"/>
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

