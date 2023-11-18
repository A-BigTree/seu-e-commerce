<script setup lang="ts">
import {inject, ref, watch} from "vue";
import type {FormInstance} from "element-plus";
import {http} from '@/utils/http';
import {getCreatorTag} from '@/utils';
import {Delete, Plus} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

const props = defineProps(['propId', 'open', 'handleClose'])

const roleType = inject("roleType");

const drawOpen = ref(false);

const formRef = ref<FormInstance>();

const formData = ref({
  action: 1,
  id: 0,
  propName: '',
  propType: 1,
  selfAdd: 1,
  shopId: 1,
  values: [
    {
      key: 0,
      id: 0,
      shopId: 0,
      valueName: ''
    }
  ]
});


const init = () => {
  formData.value = {
    action: 1,
    id: 0,
    propName: '',
    propType: 1,
    shopId: roleType.value === 3 ? 0 : 1,
    selfAdd: 1,
    values: []
  }
  if (parseInt(props.propId) === 0) {
    formData.value.values.push({
      key: 1,
      id: 0,
      shopId: roleType.value === 3 ? 0 : 1,
      valueName: ''
    });
    return;
  }
  const param = {
    url: "/product/category/prop/get?id=" + props.propId,
    method: 'get',
    callBack: (res) => {
      const data = res.data;
      formData.value.action = 3;
      formData.value.id = data.id;
      formData.value.propName = data.propName;
      formData.value.propType = data.propType;
      formData.value.selfAdd = data.selfAdd;
      formData.value.shopId = data.shopId;
      if (data.value.length > 0) {
        data.value.forEach(value => {
          formData.value.values.push({
            key: value.id,
            id: value.id,
            shopId: value.shopId,
            valueName: value.valueName
          })
        })
      }
    }
  }
  http(param);
}

watch(props, (newParams) => {
  drawOpen.value = newParams.open;
  init();
})

const canEdit = (shopId) => {
  return (shopId === 0 && roleType.value === 2);
}

const deleteValue = (value) => {
  const index = formData.value.values.indexOf(value);
  if (index !== -1) {
    formData.value.values.splice(index, 1);
  }
}

const addValue = () => {
  formData.value.values.push({
    key: Date.now(),
    id: 0,
    shopId: roleType.value === 3 ? 0 : 1,
    valueName: ''
  })
}

const submitEdit = (form) => {
  if (!form) return;
  form.validate((valid) => {
    if (valid) {
      const values = [];
      formData.value.values.forEach(value => {
        values.push({
          id: value.id,
          valueName: value.valueName,
          propId: formData.value.id,
          shopId: value.shopId,
          createTime: ''
        })
      })
      const param = {
        url: "/product/category/prop/update",
        data: {
          action: formData.value.action,
          data: {
            id: formData.value.id,
            propName: formData.value.propName,
            propType: formData.value.propType,
            shopId: formData.value.shopId,
            selfAdd: formData.value.selfAdd,
            createTime: '',
            value: values
          }
        },
        callBack: (res) => {
          ElMessage({
            message: "编辑成功",
            type: "success"
          });
          drawOpen.value = false;
        }
      };
      http(param);
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
        {{ parseInt(propId) === 0 ? '添加商品属性' : '编辑商品属性' }}
      </template>
      <el-form :model="formData"
               ref="formRef"
               @keyup.enter.native="submitEdit(formRef)"
               status-icon>
        <el-form-item prop="propName" label="属性名称" :rules="{required:true, message: '属性值不能为空', trigger: 'blur'}">
          <el-input type="text" v-model="formData.propName" :disabled="canEdit(formData.shopId)"/>
        </el-form-item>
        <el-form-item prop="propType" label="属性类型" :rules="{required:true, message: '属性类型不能为空', trigger: 'blur'}">
          <el-select v-model="formData.propType" :disabled="canEdit(formData.shopId)">
            <el-option :value="1" label="商品规格"/>
            <el-option :value="2" label="商品参数"/>
          </el-select>
        </el-form-item>
        <el-form-item label="是否可扩展">
          <el-switch v-model="formData.selfAdd" :inactive-value="0" :active-value="1"
                     :disabled="canEdit(formData.shopId)"/>
        </el-form-item>
        <el-card shadow="always">
          <template #header>
            属性值
          </template>
          <el-form-item
              v-for="(value, index) in formData.values"
              :key="value.key"
              :prop="'values.' + index + '.valueName'"
              :rules="{required:true, message: '属性值不能为空', trigger: 'blur'}">
            <el-input v-model="value.valueName" :disabled="canEdit(value.shopId)">
              <template #prepend>
                <el-tag :type="getCreatorTag(value.shopId).type">
                  {{getCreatorTag(value.shopId).name}}
                </el-tag>
              </template>
              <template #append>
                <el-button type="danger" :icon="Delete" :disabled="canEdit(value.shopId)"
                           @click.prevent="deleteValue(value)"/>
              </template>
            </el-input>
          </el-form-item>
          <el-button @click="addValue" type="warning" :icon="Plus" size="small"/>
        </el-card>

        <el-form-item>
          <el-button class="butt" type="primary" @click="submitEdit(formRef)" size="large">提交</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </el-drawer>
</template>

<style scoped>
.butt {
  margin-top: 10px;
}
</style>