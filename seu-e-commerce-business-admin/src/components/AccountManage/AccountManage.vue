<script setup lang="ts">
import {ref} from 'vue';
import {ElTable} from 'element-plus';
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
// TODO 封号


// 审核过程
const clickReview = (user: UserInfo) => {
  accountId.value = user.id;
  openReviewDialog.value = true;

}

// 封号过程
const clickDelete = (user: UserInfo) => {

}
</script>

<template>
  <el-row>
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
  </el-row>
  <el-row>
    <el-pagination
        v-model:current-page="page.pageNum"
        v-model:page-size="page.pageSize"
        @current-change="pageChange"
        @size-change="pageSizeChange"
        :page-sizes="[5, 10, 20]"
        :small="false"
        :disabled="false"
        :background="false"
        layout="sizes, prev, pager, next, total"
        :total="page.total"/>
  </el-row>

  <!--
  <el-dialog v-model="openReviewDialog" title="账号详情" v-if="dialogData.id" :close-on-click-modal="false">
    <img style="width: 80px" :src="dialogData.image? IMAGE_URL + dialogData.image : DEFAULT_HEAD_IMAGE" alt="">
    <el-card shadow="always">
      <el-descriptions title="基本信息" :column="2" border>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <User/>
              </el-icon>
              &nbsp;
              用户名
            </div>
          </template>
          {{ dialogData.nickname }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <Message/>
              </el-icon>
              &nbsp;账户
            </div>
          </template>
          {{ dialogData.account }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <Position/>
              </el-icon>
              &nbsp;角色
            </div>
          </template>
          <el-tag :type="getRoleTag(dialogData.roleType)">
            {{ getRoleName(dialogData.roleType) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <Cellphone/>
              </el-icon>
              &nbsp;电话
            </div>
          </template>
          {{ dialogData.phoneNumber ? dialogData.phoneNumber : '无' }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card shadow="always" v-if="dialogData.desc">
      <el-descriptions title="账号介绍">
        <el-descriptions-item>
          {{ dialogData.desc }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card shadow="always">
      <el-descriptions title="账号状态" :column="1">
        <el-descriptions-item>
          <template #label>
            <el-icon>
              <Stopwatch/>
            </el-icon>
            &nbsp;注册状态
          </template>
          <el-tag :type="getRegisterStateTag(dialogData.state)">
            {{ getRegisterStateName(dialogData.state) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <el-icon>
              <Timer/>
            </el-icon>
            注册时间
          </template>
          {{ dialogData.createTime }}
        </el-descriptions-item>
        <el-descriptions-item v-if="dialogData.review">
          <template #label>
            <el-icon>
              <Document/>
            </el-icon>
            &nbsp;审核意见
          </template>
          {{ dialogData.review.remark }}
        </el-descriptions-item>
        <el-descriptions-item v-if="dialogData.review">
          <template #label>
            <el-icon>
              <Timer/>
            </el-icon>
            审核时间
          </template>
          {{ dialogData.review.createTime }}
        </el-descriptions-item>
      </el-descriptions>
      <el-form :model="dialogForm"
               ref="dialogFormRef"
               :rules="dialogFormRule"
               v-if="dialogData.state === 0"
               status-icon>
        <el-form-item label="是否通过" prop="reviewState">
          <el-radio-group v-model="dialogForm.reviewState">
            <el-radio :label="1">审核通过</el-radio>
            <el-radio :label="2">审核不通过</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核意见" prop="remark" v-if="dialogForm.reviewState != 1">
          <el-input
              v-model="dialogForm.remark"
              placeholder="请输入审核意见"
              :autosize="{ minRows: 3, maxRows: 3 }"
              maxlength="200"
              show-word-limit
              type="textarea"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitReview(dialogFormRef)">提交</el-button>
        </el-form-item>
      </el-form>
    </el-card>

  </el-dialog>
  -->
  <ReviewDialog :open-review-dialog="openReviewDialog"
                :account-id="accountId"
                :handle-close="handleClose"
                v-if="openReviewDialog"/>

</template>

<style scoped>
.cell-item {
  display: flex;
  align-items: center;
}
</style>