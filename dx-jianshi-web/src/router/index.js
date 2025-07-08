/*
 * @Author: zzx
 * @Date: 2020-07-22 20:26:52
 * @LastEditTime: 2021-07-27 19:56:50
 * @LastEditTime: 2020-11-10 19:49:06
 * @FilePath: /zhijian_web/src/router/index.js
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import home from '../views/vhome.vue'
import user_children from './user_router_children'
import assignRouterConfig from './assign_router'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/intellect-create',
    component: home,
    // meta: {
    //   requireAuth: true
    // },
    children: [
      // {
      //   path: '/',
      //   name: 'homePage',
      //   component: resolve => require(['@/views/home_pages/main.vue'], resolve)
      // },
      {
        path: '/intellect-create',
        name: 'intellectCreate',
        component: resolve => require(['@/views/intellect_create/index.vue'], resolve)
      },
      // 素材库
      {
        path: '/material-library',
        name: 'materialLibrary',
        component: resolve => require(['@/views/material_library/index.vue'], resolve)
      },
      // 素材库详情
      {
        path: '/material-deatils',
        name: 'materialDeatils',
        component: resolve => require(['@/views/material_library/deatils.vue'], resolve)
      },
      {
        path: '/active_video',
        name: 'activeVideoHome',
        component: resolve => require(['@/views/active_video/index.vue'], resolve),
        children: [
          {
            path: '/active_video',
            name: 'activeVideo',
            component: resolve => require(['@/views/active_video/my_videos/index.vue'], resolve)
          },
          {
            path: '/active_video/create_active_video',
            name: 'createActiveVideo',
            component: resolve => require(['@/views/active_video/create_video/index.vue'], resolve)
          }
        ]
      },
      {
        path: '/my-videos',
        name: 'myVideo',
        component: resolve => require(['@/views/my_video/index.vue'], resolve)
      },
      {
        path: '/account_bind',
        name: 'accountBind',
        component: resolve => require(['@/views/account_bind/main.vue'], resolve)
      },
      {
        path: '/publish_video',
        name: 'publish',
        component: resolve => require(['@/views/publish/main.vue'], resolve)
      },
      {
        path: '/publish_history',
        name: 'publishHistory',
        component: resolve => require(['@/views/publish/publish_history.vue'], resolve)
      },
      {
        path: '/vip_page',
        name: 'vip',
        component: resolve => require(['@/views/user_info/vip/index.vue'], resolve)
      },
      {
        path: '/user-info',
        name: 'userInfoHome',
        component: resolve => require(['@/views/user_info/index.vue'], resolve),
        children: [
          ...user_children,
          {
            path: '/user-info',
            name: 'userInfo',
            redirect: '/user-info/ai-created'
          }
        ]
      },
      ...assignRouterConfig
    ]
  },
  {
    path: '/mobile-login',
    name: 'mobileLogin',
    component: resolve => require(['@/views/mobile_login/index.vue'], resolve)
  },
  {
    path: '/vip',
    name: 'mobilePay',
    component: resolve => require(['@/views/mobile_pay/index.vue'], resolve)
  },
  {
    path: '/pay-qr',
    name: 'payQr',
    component: resolve => require(['@/views/mobile_pay/pay_qr.vue'], resolve)
  },
  {
    path: '/invition_table_admin_test',
    name: 'invitionTableTest',
    component: resolve => require(['@/views/invition_table/index.vue'], resolve)
  }
]
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
export default router
