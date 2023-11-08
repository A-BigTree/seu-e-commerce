<script setup>

import {ref, watch} from "vue";
import {http} from "@/utils/http";

const props = defineProps(['categoryId', 'parentId'])

const formData = ref({
  id: 0,
  parentId: 0,
  categoryName: "",
  level: 1,
  status: 1,
});

const firstLevel = ref([]);

watch(formData.value.parentId, (newValue) => {
  formData.value.level = newValue === 0 ? 1 : 2;
})

const init = () => {
  const params = {
    url: "/product/category/all/get",
    method: 'get',
    callBack: (res) => {
      firstLevel.value = res.data;
    }
  }
  http(params);
  if ((!props.categoryId) || parseInt(props.categoryId) === 0) {
    formData.value.parentId = parseInt(props.parentId);
    return;
  }
  const params2 = {
    url: "/product/category/one/get?id=" + props.categoryId,
    method: 'get',
    callBack: (res) => {
      const data = res.data;
      formData.value = {
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
// 初始化数据
init();

</script>

<template>

</template>

<style scoped>

</style>