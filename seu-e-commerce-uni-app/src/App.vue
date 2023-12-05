<script>
export default {
  onLaunch: function () {
    console.log('App Launch')
  },
  onShow: function () {
    console.log('App Show')
  },
  onHide: function () {
    console.log('App Hide')
  },
}
</script>

<style lang="scss" scoped>
@import "./app.css";
/* 隐藏头部 */
uni-page-head {
  display: none;
}
/* 轮播图指示点 */
uni-swiper .uni-swiper-dots-horizontal {
  bottom: 20px !important;
}
</style>
