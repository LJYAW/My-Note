/*
 * @Author: your name
 * @Date: 2021-03-28 11:45:00
 * @LastEditTime: 2021-07-20 19:16:37
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /instant-clip-video-web/src/router/index.js
 */
import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  }
  // 404 page must be placed at the end !!!
]

export const asyncRoutes = [
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '*',
    redirect: '/404',
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/home-Page',
    children: [
      {
        path: 'home-Page',
        component: () => import('@/views/homePage/index.vue'),
        name: 'homePage',
        meta: {
          title: '首页',
          roles: ['superman', 'home']
        }
      },
      {
        path: '/down-Page',
        component: () => import('@/views/downPage/index.vue'),
        name: 'downPage',
        meta: {
          title: '下载页',
          roles: ['superman', 'down']
        }
      }
    ]
  }
]

const createRouter = () =>
  new VueRouter({
    mode: 'history', // require service support
    scrollBehavior: () => ({
      y: 0
    }),
    routes: constantRoutes
  })

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router

// const routes = []

// const router = new VueRouter({
//   routes
// })

// export default router
