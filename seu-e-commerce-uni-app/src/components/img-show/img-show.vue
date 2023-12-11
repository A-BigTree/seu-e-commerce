<template>
  <image
    :src="props.src"
    :style="imgStyle"
    :class="classList"
    :mode="imgMode"
    @tap="handleTap"
  />
</template>

<script setup>
const props = defineProps({
  src: {
    type: String,
    default: ''
  },
  imgMode: {
    type: String,
    default: 'scaleToFill'
  },
  classList: {
    type: Array,
    default: () => {
      return []
    }
  },
  imgStyle: {
    type: Object,
    default: () => {
      return {}
    }
  },
  // 默认失败图片类型, false为默认图片, true为默认头像图片
  defaultImgType: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['handleTap'])

const handleTap = () => {
  emit('handleTap')
}
</script>

<style scoped>
image {
  width: 100%;
  height: 100%;
}
</style>
