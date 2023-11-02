<script setup lang="ts">
import {ref} from "vue";
import AccountManage from "@/components/AccountManage/AccountManage.vue";
import ProductManage from "@/components/ProductManage/ProductManage.vue";
import {http} from '@/utils/http';

const roleType = ref(-1);

const params = {
  url: "/account/user/info/get",
  method: "get",
  callBack: (res) => {
    const userInfo = res.data;
    roleType.value = userInfo.roleType;
  }
}

http(params);


const index = roleType.value === 2 ? ref("0") : ref("1-1");

const select = (in_) => {
  console.log(in_);
  index.value = in_;
}

</script>

<template>
  <el-container v-if="roleType !== -1">
    <el-header>

    </el-header>
    <el-container>
      <el-aside>
        <el-menu
            @select="select"
            default-active="1-1">
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

