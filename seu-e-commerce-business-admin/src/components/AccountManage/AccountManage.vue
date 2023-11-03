<script setup lang="ts">
import {ref} from 'vue';
import {ElTable} from 'element-plus';
import {getRoleName, getRegisterStateName} from '@/utils'

interface UserInfo {
  id: number,
  nickname: string,
  account: string,
  phoneNumber: string,
  image: string,
  roleType: number,
  registerState: number,
  createTime: string
}

const tableRef = ref<InstanceType<typeof ElTable>>();

const page = {
  pageNum: 1,
  pageSize: 10,
  total: 100
}

const tableData: UserInfo[] = [
  {
    id: 1,
    nickname: '测试',
    account: '12345',
    phoneNumber: "",
    image: "",
    roleType: 2,
    registerState: 0,
    createTime: '2023-10-1'
  }
];

// 获取数据封装
const listData = () => {
  const params = {

  }
}


const getRoleTag = (user: UserInfo) => {
  switch (user.roleType) {
    case 1:
      return 'success';
    case 2:
      return '';
    default:
      return 'warning';
  }
}

const getRegisterStateTag = (user: UserInfo) => {
  switch (user.registerState) {
    case 0:
      return 'warning'
    case 1:
      return 'success'
    default:
      return 'danger'
  }
}

// 审核过程
const clickReview = (user: UserInfo) => {

}

// 封号过程
const clickDelete = (user: UserInfo) => {

}
</script>

<template>
  <el-row>
    
  </el-row>
  <el-row>
    <el-table
        ref="tableRef"
        stripe
        border
        :data="tableData">
      <el-table-column type="selection"/>
      <el-table-column property="id" label="ID"/>
      <el-table-column property="nickname" label="昵称"/>
      <el-table-column property="account" label="账号"/>
      <el-table-column property="roleType" label="角色">
        <template #default="scope">
          <el-tag
            :type="getRoleTag(scope.row)"
            disable-transitions
          >
            {{getRoleName(scope.row.roleType)}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column property="registerState" label="注册状态">
        <template #default="scope">
          <el-tag
            :type="getRegisterStateTag(scope.row)"
            disable-transitions>
            {{getRegisterStateName(scope.row.registerState)}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column property="createTime" label="注册时间"/>
      <el-table-column label="操作" fixed="right">
        <template #default="scope">
          <el-button
              size="small"
              type="warning"
              @click="clickReview(scope.row)"
              v-if="scope.row.registerState === 0">
            审核
          </el-button>
          <el-button
            size="small"
            type="danger"
            @click="clickDelete(scope.row)">
            注销
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-row>

  <el-row justify="center">
    <el-pagination
        v-model:current-page="page.pageNum"
        v-model:page-size="page.pageSize"
        :page-sizes="[5, 10, 20]"
        :small="false"
        :disabled="false"
        :background="false"
        layout="sizes, prev, pager, next, total"
        :total="page.total"/>
  </el-row>


</template>

<style scoped>

</style>