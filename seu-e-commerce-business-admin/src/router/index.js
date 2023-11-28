import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: () => import('@/views/home/index.vue'),
            children: [
                {
                    path: 'account/manage',
                    component: () => import('@/components/AccountManage/AccountManage.vue')
                },
                {
                    path: 'product/manage',
                    component: () => import('@/components/ProductManage/ProductManage.vue')
                },
                {
                    path: 'product/category/manage/:parentId',
                    name: 'prod-category-manage',
                    component: () => import('@/components/ProductManage/CategoryManage.vue'),
                    props: true
                },
                {
                    path: 'product/prop/manage',
                    name: 'prod-prop-manage',
                    component: () => import('@/components/ProductManage/PropManage.vue')
                },
                {
                    path: 'product/edit/:prodId',
                    name: 'product-edit',
                    component: () => import('@/components/ProductManage/ProductEdit.vue'),
                    props: true
                }
            ]
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('@/views/login/index.vue')
        },
        {
            path: '/register',
            name: 'register',
            component: () => import("@/views/register/index.vue")
        }
    ]
})

export default router
