<script setup lang="ts">
import {ref} from 'vue';
import {ElTable} from 'element-plus';

interface UserInfo {
  id: number,
  nickname: string,
  account: string,
  phoneNumber: string,
  image: string,
  roleType: string,
  registerState: string,
  createTime: string
}

const tableRef = ref<InstanceType<typeof ElTable>>();

const tableData: UserInfo[] = [
  {
    id: 1,
    nickname: '测试',
    account: '12345',
    phoneNumber: "",
    image: "",
    roleType: '商家',
    registerState: '待审核',
    createTime: '2023-10-1'
  }
];
</script>

<template>
  <el-row>
    
  </el-row>
  <el-table
      ref="tableRef"
      :data="tableData">
    <el-table-column type="selection"/>
    <el-table-column property="id" label="ID"/>
    <el-table-column property="nickname" label="昵称"/>
    <el-table-column property="account" label="账号"/>

  </el-table>

</template>

<style scoped>

</style>