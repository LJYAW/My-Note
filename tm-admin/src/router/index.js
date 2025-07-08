/*
 * @Author: your name
 * @Date: 2021-03-28 11:45:00
 * @LastEditTime: 2021-06-08 14:42:51
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /instant-clip-video-web/src/router/index.js
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layout'

Vue.use(VueRouter)

export const asyncRoutes = [
  // 任务管理 Start
  {
    path: '/',
    component: Layout,
    name: 'home',
    redirect: '/task',
    meta: {
      title: '任务管理',
      icon: 'el-icon-s-home',
      roles: ['*', '任务管理', '任务查看', '任务删除']
    },
    children: [
      {
        path: '/task',
        name: 'task',
        component: () => import('@/view/task_mgt/index.vue'),
        meta: {
          activeMenu: '/',
          roles: ['*', '任务管理', '任务查看', '任务删除']
        },
        hidden: true
      },
      {
        path: '/addTask',
        name: 'addTask',
        component: () => import('@/view/task_mgt/components/addTask.vue'),
        meta: {
          title: '新增任务',
          activeMenu: '/',
          roles: ['*', '任务管理']
        },
        hidden: true
      },
      {
        path: '/editTask',
        name: 'editTask',
        component: () => import('@/view/task_mgt/components/editTask.vue'),
        meta: {
          title: '编辑任务',
          activeMenu: '/',
          roles: ['*', '任务管理', '任务删除']
        },
        hidden: true
      }
    ]
  },
  // 专题定制 End
  // 视频管理
  {
    path: '/VideoAdmin',
    component: Layout,
    name: 'videoAdmin',
    redirect: '/video-admin-list',
    meta: {
      title: '视频管理',
      icon: 'el-icon-s-home',
      roles: ['*', '视频查看', '视频编辑', '视频删除']
    },
    children: [
      {
        path: '/video-admin-list',
        name: 'VideoAdminList',
        component: () => import('@/view/VideoAdmin/index'),
        meta: {
          activeMenu: '/VideoAdmin',
          roles: ['*', '视频查看', '视频编辑', '视频删除']
        },
        hidden: true
      }
    ]
  },
  // 爬取路径
  // {
  //   path: '/CrawlingPath',
  //   component: Layout,
  //   name: 'CrawlingPath',
  //   redirect: '/CrawlingPathList',
  //   meta: {
  //     title: '爬取路径',
  //     icon: 'el-icon-s-home'
  //   },
  //   children: [
  //     {
  //       path: '/CrawlingPathList',
  //       name: 'CrawlingPathList',
  //       component: () => import('@/view/CrawlingPath/index'),
  //       meta: {
  //         activeMenu: '/'
  //       },
  //       hidden: true
  //     }
  //   ]
  // },
  // 舆情监测 Start
  // 任务管理 End

  // 用户管理 Start
  {
    path: '/user',
    component: Layout,
    name: 'user',
    redirect: '/user/mgt',
    meta: {
      title: '用户管理',
      icon: 'el-icon-s-data',
      roles: ['*', '用户查看', '用户编辑', '用户删除']
    },
    children: [
      {
        path: '/user/mgt',
        name: 'user_mgt',
        component: () => import('@/view/user_mgt/index.vue'),
        meta: {
          activeMenu: '/user',
          roles: ['*', '用户查看', '用户编辑', '用户删除']
        },
        hidden: true
      }
    ]
  }
  // 用户管理 End
]

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/view/login/index'),
    hidden: true
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
