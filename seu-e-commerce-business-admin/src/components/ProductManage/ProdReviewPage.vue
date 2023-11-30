<script setup>

import {ref, watch} from "vue";
import {http} from "@/utils/http";
import {getProdStatusTag} from "@/utils";
import {Document, Right, Stopwatch, Timer, User} from "@element-plus/icons-vue";

const props = defineProps(["prodId"]);
const prodId = ref(parseInt(props.prodId));

watch(props, (newParams) => {
  prodId.value = parseInt(newParams.prodId);
  init();
})

const reviews = ref([
  {
    modifier: "测试店铺",
    remark: "添加商品",
    status: 0,
    oldStatus: 0,
    createTime: "2023-11-30 19:01:38"
  }
])

const init = () => {
  const params = {
    url: "/product/tob/prod/review/get?prodId=" + prodId.value,
    method: "get",
    callBack: (res) => {
      reviews.value = res.data;
    }
  }
  http(params)
}

init();

</script>

<template>
  <el-button type="primary" @click="init" size="small">刷新</el-button>
  <el-card shadow="always" v-for="item in reviews">
    <el-descriptions :column="1">
      <el-descriptions-item>
        <template #label>
          <el-icon>
            <Stopwatch/>
          </el-icon>
          &nbsp;状态变化
        </template>
        <el-tag :type="getProdStatusTag(item.oldStatus).type">
          {{getProdStatusTag(item.oldStatus).name}}
        </el-tag>
        <el-icon><Right /></el-icon>
        <el-tag :type="getProdStatusTag(item.status).type">
          {{getProdStatusTag(item.status).name}}
        </el-tag>
      </el-descriptions-item>

      <el-descriptions-item>
        <template #label>
          <el-icon>
            <Document/>
          </el-icon>
          &nbsp;操作内容
        </template>
        {{item.remark}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon>
            <User/>
          </el-icon>
          操作人
        </template>
        {{item.modifier}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon>
            <Timer/>
          </el-icon>
          操作时间
        </template>
        {{item.createTime}}
      </el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>