/*
 * @Author: zzx
 * @Date: 2020-05-22 16:43:11
 * @LastEditTime: 2021-02-02 15:11:07
 * @FilePath: /weijian_web/src/router/index.js
 */

import Vue from 'vue'
import VueRouter from 'vue-router'
import effect_children from './effect_router_children'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: () => import(/* webpackChunkName: "about" */ '../views/login/index.vue')
  },
  {
    path: '/home',
    name: 'home',
    component: () => import(/* webpackChunkName: "about" */ '../page/vhome.vue'),
    //重定向
    redirect: '/user',
    children: [
      {
        path: '/user',
        name: 'user',
        component: () => import(/* webpackChunkName: "about" */ '../page/user/index.vue')
      },
      {
        path: '/works',
        name: 'works',
        component: () => import(/* webpackChunkName: "about" */ '../page/works/index.vue')
      },
      {
        path: '/effect',
        name: 'effect',
        component: () => import(/* webpackChunkName: "about" */ '../page/effect/index.vue'),
        redirect: '/effect/host',
        children: [...effect_children]
      },
      {
        path: '/task',
        name: 'task',
        component: () => import(/* webpackChunkName: "about" */ '../page/task/index.vue')
      },
      {
        path: '/works_square',
        name: 'works_square',
        component: () => import(/* webpackChunkName: "about" */ '../page/works_square/index.vue')
      },
      {
        path: '/material',
        name: 'material',
        component: () => import(/* webpackChunkName: "about" */ '../page/material/new/index.vue')
      },
      // 素材管理详情
      {
        path: '/material/deatils',
        name: 'material_deatils',
        component: () => import(/* webpackChunkName: "about" */ '../page/material/new/deatils.vue')
      },
      // 任务管理新增
      {
        path: '/task/add',
        name: 'add',
        component: () => import(/* webpackChunkName: "about" */ '../page/task/add.vue'),
        meta: {
          // keepAlive: true,
        }
      },
      // 医师列表
      {
        path: '/task/doctor-table',
        name: 'doctorTable',
        component: () => import(/* webpackChunkName: "about" */ '../page/task/add_tsk/doctor_table.vue'),
      },
      // 标签视频
      {
        path: '/material/tags_video',
        name: 'tags_video',
        component: () => import(/* webpackChunkName: "about" */ '../page/material/new/tags_video.vue')
      },
      // 标签列表
      {
        path: '/material/tags_list',
        name: 'tags_list',
        component: () => import(/* webpackChunkName: "about" */ '../page/material/new/tags_list.vue')
      },
      // 用户管理查看企业用户详情
      {
        path: '/user/enterprise_deatils',
        name: 'enterprise_deatils',
        component: () => import(/* webpackChunkName: "about" */ '../page/user/deatils/enterprise_deatils.vue')
      },
      {
        path: '/user/person_deatils',
        name: 'person_deatils',
        component: () => import(/* webpackChunkName: "about" */ '../page/user/deatils/person_deatils.vue')
      },
      // 任务管理审核
      {
        path: '/task/examine',
        name: 'examine',
        component: () => import(/* webpackChunkName: "about" */ '../page/task/examine.vue')
      },
      // 任务管理有表格审核素材
      {
        path: '/task/tab-examine',
        name: 'tabExamine',
        component: () => import(/* webpackChunkName: "about" */ '../page/task/havetab_examine.vue')
      },
      // // 音频审核

      {
        path: '/works/deatils',
        name: 'works_deatils',
        component: () => import(/* webpackChunkName: "about" */ '../page/works/deatils.vue')
      }
      //作品管理详情
      // {
      //   path: '/works/deatils',
      //   name: 'works_deatils',
      //   component: () => import(/* webpackChunkName: "about" */ '../page/works/deatils.vue')
      // },
      // //作品广场
      // {
      //   path: '/works/square',
      //   name: 'square',
      //   component: () => import(/* webpackChunkName: "about" */ '../page/works/works_square.vue')
      // }
    ]
  },
  {
    path: '*',
    name: '404',
    component: () => import(/* webpackChunkName: "about" */ '../views/404/index.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
