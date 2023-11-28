<script setup lang="ts">

import {inject, ref, watch} from "vue";
import {http} from '@/utils/http';
import {getDisplayPrice} from '@/utils';
import type {FormInstance} from 'element-plus'

const props = defineProps(['prodId']);

console.log(props.prodId);

const propId = parseInt(props.prodId);

const roleType = inject("roleType");

const step = ref(0);

const formRef = ref<FormInstance>();

const skuRef = ref<FormInstance>();

const formData = ref({
  id: 0,
  categoryId: 3,
  status: 0,
  prodName: "",
  originPrice: "",
  price: "",
  brief: "",
  content: "",
  pic: "",
  images: [],
  deliveryMode: 0,
  deliveryPrice: 0,
});

const skuForm = ref({
  skus: [
    {
      id: 0,
      prodId: 0,
      properties: [
        {
          prop: "",
          value: ""
        }
      ],
      skuName: "",
      skuCode: "",
      pic: "",
      originPrice: 0,
      price: 0,
      stocks: 0
    }
  ],
  parameters: [
    {
      prop: "",
      value: ""
    }
  ]
});

const categories = ref([
  {
    value: 0,
    label: "",
    children: [
      {
        value: 1,
        label: ""
      }
    ]
  }
]);

const init = () => {
  const params = {
    url: "/product/category/all/get",
    method: "get",
    callBack: (res) => {
      const data = res.data;
      categories.value = [];
      data.forEach(category => {
        const children = [];
        category.children.forEach(value => {
          children.push({
            value: value.id,
            label: value.categoryName
          });
        });
        if (children.length > 0) {
          categories.value.push({
            value: category.id,
            label: category.categoryName,
            children: children
          });
        }
      });
      console.log(categories.value);
    }
  }
  http(params);
}

watch(props, (newParams) => {
  init();
});

// 初始化
init();

const preStep = () => {
  step.value--;
}

const nextStep = () => {
  step.value++;
}

const submitData = () => {

}

const formRules = {}

</script>

<template>
  <el-steps :active="step" finish-status="success">
    <el-step title="商品基本信息"/>
    <el-step title="商品规格设置"/>
    <el-step title="确认信息"/>
  </el-steps>

  <el-form
      v-if="step === 0"
      ref="formRef"
      :model="formData"
      :rules="formRules"
      class="form"
      status-icon
  >
    <el-form-item label="商品分类" prop="category">
      <el-cascader v-model="formData.categoryId" :options="categories"/>
    </el-form-item>

  </el-form>


  <div class="form">
    <el-button v-if="step > 0" type="primary" @click="preStep">上一步</el-button>
    <el-button v-if="step < 2" type="primary" @click="nextStep">下一步</el-button>
    <el-button v-if="step === 2" type="primary" @click="submitData">提交</el-button>
  </div>


</template>

<style scoped>
.form {
  margin-top: 10px;
}
</style>