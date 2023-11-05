<script setup lang="ts">
import {DEFAULT_HEAD_IMAGE, IMAGE_URL} from "@/utils/config";
import {getRegisterStateName, getRegisterStateTag, getRoleName, getRoleTag} from "@/utils";
import {ref} from "vue";
import type {FormInstance} from "element-plus";
import {Cellphone, Document, Message, Position, Stopwatch, Timer, User} from "@element-plus/icons-vue";
import {http} from "@/utils/http";
import {ElMessage} from "element-plus";

const props = defineProps(['openReviewDialog', 'accountId', 'handleClose']);

//审核
const dialogData = ref({
  id: null,
  image: null,
  nickname: null,
  account: null,
  phoneNumber: null,
  roleType: null,
  state: null,
  desc: null,
  createTime: null,
  review: {
    remark: null,
    createTime: null,
    modifier: null
  }
})
const dialogForm = ref({
  accountId: props.accountId,
  remark: "",
  reviewState: 1
});

const dialogFormRule = ref({
  reviewState: [
    {required: true, message: '审议结果不能为空', trigger: 'blur'}
  ],
  remark: [
    {required: true, message: '审核意见不能为空', trigger: 'blur'}
  ]
})
const dialogFormRef = ref<FormInstance>();
const getAccountInfo = () => {
  dialogData.value.id = null;
  const params = {
    url: "/account/account/info/get?id=" + props.accountId,
    method: "get",
    closeLoading: true,
    callBack: (res) => {
      dialogData.value = res.data;
    }
  }
  http(params);
}

getAccountInfo()

const updateReview = () => {
  const params = {
    url: "/account/register/state/update",
    data: {
      accountId: dialogForm.value.accountId,
      remark: dialogForm.value.remark,
      reviewState: dialogForm.value.reviewState,
      modifier: ""
    },
    callBack: (res) => {
      ElMessage({
        message: "提交成功",
        type: "success"
      });
      getAccountInfo();
    }
  };
  http(params);
}

const submitReview = (formRef: FormInstance) => {
  if (dialogForm.value.reviewState === 1) {
    dialogForm.value.remark = '审核通过';
  }
  if (!formRef) return;
  formRef.validate((valid) => {
    if (valid) {
      console.log(dialogForm.value);
      updateReview();
    }
  })
}
</script>

<template>
  <el-dialog v-model="props.openReviewDialog" title="账号详情" v-if="dialogData.id" :close-on-click-modal="false" :before-close="props.handleClose">
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
              <User/>
            </el-icon>
            审核人
          </template>
          {{dialogData.review.modifier}}
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
        <el-form-item label="审核意见" prop="remark" v-if="dialogForm.reviewState !== 1">
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
</template>

<style scoped>

</style>