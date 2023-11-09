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
                    path: 'product/category/manage/:roleType/:parentId',
                    name: 'prod-category-manage',
                    component: () => import('@/components/ProductManage/CategoryManage.vue'),
                    props: true
                },
                {
                    path: 'product/category/edit/:categoryId/:parentId',
                    name: 'prod-category-edit',
                    component: () => import('@/components/ProductManage/CategoryEdit.vue'),
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
