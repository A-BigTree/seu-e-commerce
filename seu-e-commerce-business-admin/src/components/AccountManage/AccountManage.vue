<script setup lang="ts">
import {ref} from 'vue';
import {ElMessage, ElMessageBox, ElTable} from 'element-plus';
import {getRoleName, getRegisterStateName, getRoleTag, getRegisterStateTag} from '@/utils'
import {http} from '@/utils/http';
import ReviewDialog from "@/components/common/ReviewDialog.vue";

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
    callBack: (res) => {
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

// 筛选数据
const filterData = () => {
  listData();
}

// 分页
const pageChange = (pageNum: number) => {
  page.value.pageNum = pageNum;
  listData();
}
const pageSizeChange = (pageSize: number) => {
  page.value.pageSize = pageSize;
  listData();
}
//审核
const openReviewDialog = ref(false);
const accountId = ref(0);
const handleClose = () => {
  openReviewDialog.value = false;
  accountId.value = 0;
  listData();
}


// 审核过程
const clickReview = (user: UserInfo) => {
  accountId.value = user.id;
  openReviewDialog.value = true;
}

// 封号过程
const clickDelete = (user: UserInfo) => {
  ElMessageBox.prompt("请输入注销原因", "确认消息", {
    confirmButtonText: "提交",
    cancelButtonText: "取消",
    inputPattern: /^.+$/,
    inputErrorMessage: "注销原因不能为空"
  }).then((value) => {
    if (value.action !== 'confirm') return;
    const params = {
      url: "/account/register/state/update",
      data: {
        accountId: user.id,
        remark: value.value,
        reviewState: 3,
        modifier: ""
      },
      callBack: (res) => {
        ElMessage({
          message: "提交成功",
          type: "success"
        });
      }
    }
    http(params);
    listData();
  })
}
</script>

<template>
  <el-card shadow="always">
    <template #header>
      <el-form :inline="true" :model="formData">
        <el-form-item label="ID">
          <el-input type="number" v-model="formData.id"/>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="formData.email"/>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="formData.nickname"/>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="formData.roleType">
            <el-option label="全部" :value="-1"/>
            <el-option label="消费者" :value="1"/>
            <el-option label="商家" :value="2"/>
            <el-option label="平台管理" :value="3"/>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="formData.registerState">
            <el-option label="全部" :value="-1"/>
            <el-option label="待审核" :value="0"/>
            <el-option label="注册成功" :value="1"/>
            <el-option label="注册失败" :value="2"/>
            <el-option label="已注销" :value="3"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="filterData">
            筛选
          </el-button>
        </el-form-item>
      </el-form>
    </template>

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
              :type="getRoleTag(scope.row.roleType)"
              disable-transitions
          >
            {{ getRoleName(scope.row.roleType) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column property="registerState" label="注册状态" width="120">
        <template #default="scope">
          <el-tag
              :type="getRegisterStateTag(scope.row.registerState)"
              disable-transitions>
            {{ getRegisterStateName(scope.row.registerState) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column property="createTime" label="注册时间" width="220"/>
      <el-table-column label="操作" fixed="right" width="150">
        <template #default="scope">
          <el-button
              size="small"
              type="info"
              @click="clickReview(scope.row)"
              v-if="scope.row.registerState != 0">
            详情
          </el-button>
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
              v-if="scope.row.registerState == 1">
            注销
          </el-button>

        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="page"
        v-model:current-page="page.pageNum"
        v-model:page-size="page.pageSize"
        @current-change="pageChange"
        @size-change="pageSizeChange"
        :page-sizes="[5, 10, 20]"
        :small="false"
        :disabled="false"
        :background="false"
        layout="sizes, prev, pager, next, total, jumper"
        :total="page.total"/>
  </el-card>
  <ReviewDialog :open-review-dialog="openReviewDialog"
                :account-id="accountId"
                :handle-close="handleClose"
                v-if="openReviewDialog"/>

</template>

<style scoped>
.page {
  margin-top: 10px;
}
</style>