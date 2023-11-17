<script setup lang="ts">

import {inject, ref, watch} from "vue";
import {http} from "@/utils/http";
import type {FormInstance} from "element-plus";
import {ElMessage} from "element-plus";

const props = defineProps(['categoryId', 'parentId', 'open', 'handleClose'])

const drawOpen = ref(false);

const formData = ref({
  action: 1,
  id: 0,
  parentId: 0,
  categoryName: "",
  level: 1,
  status: 1,
});

const options = ref([
  {
    value: 0,
    label: "无上级分类"
  }
]);

const formRef = ref<FormInstance>();

const init = () => {
  formData.value = {
    action: 1,
    id: 0,
    parentId: 0,
    categoryName: "",
    level: 1,
    status: 1,
  };
  const params = {
    url: "/product/category/all/get",
    method: 'get',
    closeLoading: true,
    callBack: (res) => {
      options.value = [
        {
          value: 0,
          label: "无上级分类"
        }
      ];
      res.data.forEach((value) => {
        options.value.push({
          value: value.id,
          label: value.categoryName
        });
      })
    }
  }
  http(params);
  if ((!props.categoryId) || parseInt(props.categoryId) === 0) {
    formData.value.parentId = parseInt(props.parentId);
    formData.value.action = 1;
    formData.value.level = formData.value.parentId === 0 ? 1 : 2;
    return;
  }
  const params2 = {
    url: "/product/category/one/get?id=" + props.categoryId,
    method: 'get',
    closeLoading: true,
    callBack: (res) => {
      const data = res.data;
      formData.value = {
        action: 3,
        id: data.id,
        parentId: data.parentId,
        categoryName: data.categoryName,
        level: data.level,
        status: data.status
      }
    }
  }
  http(params2);
}

watch(props, (newParams) => {
  drawOpen.value = newParams.open;
  init();
})

// 表单配置
const formRule = ref({
  categoryName: [
    {required: true, message: '分类名称不能为空', trigger: 'blur'}
  ]
})

const parentChange = () => {
  formData.value.level = formData.value.parentId === 0 ? 1 : 2;
}

const submitEdit = (form) => {
  if (!form) return;
  form.validate((valid) => {
    if (valid) {
      const params = {
        url: "/product/category/update",
        data: {
          action: formData.value.action,
          data: {
            id: formData.value.id,
            shopId: 0,
            parentId: formData.value.parentId,
            categoryName: formData.value.categoryName,
            status: formData.value.status,
            level: formData.value.level
          }
        },
        callBack: (res) => {
          ElMessage({
            message: "修改成功",
            type: "success"
          });
          drawOpen.value = false;
        }
      }
      http(params);
    }
  })
}
</script>

<template>
  <el-drawer
      v-model="drawOpen"
      direction="rtl"
      @close="props.handleClose">
    <el-card shadow="always">
      <template #header>
        {{ parseInt(categoryId) === 0 ? '添加商品分类' : '编辑商品分类' }}
      </template>
      <el-form :model="formData"
               :rules="formRule"
               ref="formRef"
               @keyup.enter.native=""
               status-icon>
        <el-form-item label="类别名称" prop="categoryName">
          <el-input type="text" v-model="formData.categoryName" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="上级分类" prop="parentId">
          <el-select v-model="formData.parentId" @change="parentChange">
            <el-option v-for="item in options"
                       :key="item.value"
                       :value="item.value"
                       :label="item.label"/>
          </el-select>
        </el-form-item>
        <el-form-item label="分类层级" prop="level">
          <el-input type="number" v-model="formData.level" style="width: 150px" disabled/>
        </el-form-item>
        <el-form-item label="是否开启">
          <el-switch v-model="formData.status" :inactive-value="0" :active-value="1"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitEdit(formRef)">
            保存
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </el-drawer>

</template>

<style scoped>

</style>