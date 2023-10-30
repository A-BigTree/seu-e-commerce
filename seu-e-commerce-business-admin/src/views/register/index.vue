<script setup>
import EshopSteps from "@/components/EshopSteps/EshopSteps.vue";
import {ref} from "vue";

const active = ref(0);
function next() {
  if (active.value++ > 2) active.value = 0;
}
</script>

<template>
<EshopSteps :stepNum="3" :active="active"
            :titles="['步骤1', '步骤2', '确认信息']">
  <template v-slot:form>
    <div v-if="active === 0">
      注册表单1
    </div>
    <div v-if="active === 1">
      注册表单2
    </div>
  </template>
  <template v-slot:next>
    <el-row justify="center">
      <el-button  @click="next">
        Next step
      </el-button>
    </el-row>
  </template>
</EshopSteps>
</template>

<script>

</script>

<style scoped>

</style>