import { createApp, ref } from 'vue';
import VueCookies from 'vue3-cookies';
import App from './App.vue';
import router from './router';

import ElementPlus from 'element-plus';
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import 'element-plus/dist/index.css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';

const app = createApp(App);

const roleType = ref(0);
const userInfo = ref({});

app.provide("roleType", roleType);
app.provide("userInfo", userInfo);

app.config.globalProperties.$roleType = roleType;

app.use(router);
app.use(ElementPlus, {
    locale: zhCn
});
app.use(VueCookies)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}

app.mount('#app');
