<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
    globalData: {
      // 定义全局请求队列
      requestQueue: [],
      // 是否正在进行登陆
      isLanding: false,
      // 购物车商品数量
      totalCartCount: 0
    },
    methods: {

    }
	}
</script>

<style>
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
