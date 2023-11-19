<script setup lang="ts">
import {inject, ref, watch} from "vue";
import type {FormInstance} from "element-plus";
import {http} from '@/utils/http';

const props = defineProps(['categoryId', 'propType', 'open', 'handleClose'])

const roleType = inject("roleType");

const drawOpen = ref(false);

const formRef = ref<FormInstance>();

const formData = ref({
  categoryId: 0,
  propType: 0,
  props: [
    {
      key: 0,
      id: 0,
      categoryId: 0,
      propId: 0,
      propType: 0,
      propName: '',
      values:[
        {
          valueName: '',
          shopId: 0
        }
      ]
    }
  ]
});

const propsData = ref([
  {
    id: 0,
    propName: '',
    shopId: 0
  }
])

const listProps = () => {
  const param = {
    url: "/product/category/props/get?id=" + props.categoryId + "&type=" + props.propType,
    method: 'get',
    callBack: (res) => {
      const data = res.data;
      const propsValue = [];
      data.forEach(value => {
        const values = [];
        value.prop.value.forEach(v => {
          values.push({
            valueName: v.valueName,
            shopId: v.shopId
          })
        });
        propsValue.push({
          key: value.id,
          id: value.id,
          categoryId: value.categoryId,
          propId: value.propId,
          propType: value.propType,
          propName: value.prop.propName,
          values: values
        })
      });
      formData.value.categoryId = props.categoryId;
      formData.value.propType = props.propType;
      formData.value.props = propsValue;
    }
  }
  http(param);
}

const init = () => {

}

watch(props, (newParams) => {
  drawOpen.value = newParams.open;
  init();
})

const submitEdit = (form) => {
  if (!form) return;
  form.validate((valid) => {
    if (valid) {

    }
  });
}

const deleteValue = (value) => {

}

const addValue = () => {

}


</script>

<template>
  <el-drawer
      v-model="drawOpen"
      direction="rtl"
      title="绑定属性"
      @close="props.handleClose">

  </el-drawer>
</template>

<style scoped>

</style>