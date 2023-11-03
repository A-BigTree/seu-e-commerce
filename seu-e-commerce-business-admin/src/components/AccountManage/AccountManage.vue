<script setup lang="ts">
import {ref} from 'vue';
import {ElTable} from 'element-plus';
import {getRoleName, getRegisterStateName} from '@/utils'
import {http} from '@/utils/http';

interface UserInfo {
  id: number,
  nickname: string,
  account: string,
  phoneNumber: string | null,
  image: string | null,
  roleType: number,
  registerState: number,
  createTime: string
}

const tableRef = ref<InstanceType<typeof ElTable>>();

const page = ref({
  pageNum: 1,
  pageSize: 10,
  total: 100
});

const tableData = ref([]);

const formData = ref({
  id: 0,
  nickname: "",
  email: "",
  registerState: -1,
  roleType: -1,
});

// 获取数据封装
const listData = () => {
  const params = {
    url: "/account/user/register/list",
    data: {
      id: formData.value.id,
      nickname: formData.value.nickname,
      email: formData.value.email,
      registerState: formData.value.registerState,
      roleType: formData.value.roleType,
      page: {
        pageNum: page.value.pageNum,
        pageSize: page.value.pageSize,
        total: 0,
        pageSum: 0
      }
    },
    callBack: (res)=>{
      const data = res.data;
      const records: UserInfo[] = data.records;
      if (tableData.value.length !== 0) {
        tableData.value = tableData.value.slice(0, 0);
      }
      records.forEach((value) => {
        tableData.value.push(value);
      });
      page.value.total = data.page.total;
    }
  }
  http(params);
}

// 初始化表格数据
listData();

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
// TODO 筛选

// TODO 分页

// TODO 审核

// TODO 封号


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
      <el-table-column property="id" label="ID" width="80"/>
      <el-table-column property="nickname" label="昵称" width="180"/>
      <el-table-column property="account" label="账号" width="220"/>
      <el-table-column property="roleType" label="角色" width="120">
        <template #default="scope">
          <el-tag
            :type="getRoleTag(scope.row)"
            disable-transitions
          >
            {{getRoleName(scope.row.roleType)}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column property="registerState" label="注册状态" width="120">
        <template #default="scope">
          <el-tag
            :type="getRegisterStateTag(scope.row)"
            disable-transitions>
            {{getRegisterStateName(scope.row.registerState)}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column property="createTime" label="注册时间" width="220"/>
      <el-table-column label="操作" fixed="right" width="100">
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
            @click="clickDelete(scope.row)"
            v-if="scope.row.registerState !== 0">
            注销
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-row>

  <el-row>
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