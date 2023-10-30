<script setup>

defineProps({
  active: Number,
  stepNum: Number,
  titles: Object
});


</script>

<template>
  <el-steps :active="active"
            align-center
            finish-status="success">
    <el-step  v-for="i in stepNum"
              :title="titles[i - 1]"/>
  </el-steps>
  <slot name="form"></slot>
  <slot name="next"></slot>
</template>


<style scoped>

</style>