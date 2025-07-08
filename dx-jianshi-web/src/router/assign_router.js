/*
 * @Author: your name
 * @Date: 2020-11-16 17:24:24
 * @LastEditTime: 2021-01-06 16:06:16
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/router/assign_router.js
 */
export default [
  {
    path: '/assign-platform',
    name: 'assignPlatformHome',
    component: resolve => require(['@/views/assignment/index.vue'], resolve),
    children: [
      {
        path: '/assign-platform',
        name: 'assignPlatform',
        component: resolve => require(['@/views/assignment/assign_platform/index.vue'], resolve)
      },
      {
        path: '/assign-platform/assign-detail',
        name: 'assignPlatformD',
        component: resolve => require(['@/views/assignment/assign_detail/index.vue'], resolve)
      }
    ]
  },
  {
    path: '/my-assign',
    name: 'assignMyHome',
    component: resolve => require(['@/views/assignment/index.vue'], resolve),
    children: [
      {
        path: '/my-assign',
        name: 'assignMy',
        component: resolve => require(['@/views/assignment/assign_my/index.vue'], resolve)
      },
      {
        path: '/my-assign/assign-detail',
        name: 'assignMy',
        component: resolve => require(['@/views/assignment/assign_detail/index.vue'], resolve)
      }
    ]
  }
]
