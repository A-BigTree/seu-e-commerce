<script setup lang="ts">
import {inject, ref, watch} from "vue";
import type {FormInstance} from "element-plus";
import {http} from '@/utils/http';

const props = defineProps(['propId', 'open', 'handleClose'])

const roleType = inject("roleType");

const drawOpen = ref(false);

const formRef = ref<FormInstance>();

const formData = ref({
  id: 0,
  propName: '',
  propType: 1,
  selfAdd: 1,
  values: [
    {
      key: 0,
      id: 0,
      shopId: 0,
      valueName: ''
    }
  ]
});

const formRule = ref({
  categoryName: [
    {required: true, message: '分类名称不能为空', trigger: 'blur'}
  ]
})

const init = () => {
  formData.value = {
    id: 0,
    propName: '',
    propType: 1,
    selfAdd: 1,
    values: []
  }
  if (parseInt(props.propId) === 0) {
    formData.value.values.push({
      key: 1,
      id: 0,
      shopId: roleType === 3?0:1,
      valueName: ''
    });
    return;
  }
  const param = {
    url: "/product/category/prop/get?id=" + props.propId,
    method: 'get',
    callBack: (res) => {
      const data = res.data;
      formData.value.id = data.id;
      formData.value.propName = data.propName;
      formData.value.propType = data.propType;
      formData.value.selfAdd = data.selfAdd;
      if (data.value.length > 0) {
        data.value.forEach(value => {
          formData.value.values.push({
            key: value.id,
            id: value.id,
            shopId: value.shopId,
            valueName: value.valueName
          })
        })
      } else {
        formData.value.values.push({
          key: 1,
          id: 0,
          shopId: roleType === 3?0:1,
          valueName: ''
        });
      }
    }
  }
  http(param);
}

watch(props, (newParams) => {
  drawOpen.value = newParams.open;
  init();
})


</script>

<template>
  <el-drawer
      v-model="drawOpen"
      direction="rtl"
      @close="props.handleClose">
    <el-card shadow="always">
      <template #header>
        {{ parseInt(propId) === 0 ? '添加商品属性' : '编辑商品属性' }}
      </template>
      <el-form :model="formData"
               :rules="formRule"
               ref="formRef"
               @keyup.enter.native=""
               status-icon>

      </el-form>
    </el-card>
  </el-drawer>
</template>

<style scoped>

</style>