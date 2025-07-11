import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  // {
  //   path: '/',
  //   component: Layout,
  //   redirect: '/home',
  //   children: [
  //     {
  //       path: 'home',
  //       name: 'Home',
  //       component: () => import('@/views/home/index'),
  //       meta: { title: '首页概览', icon: 'dashboard' }
  //     }
  //   ]
  // },
  {
    path: '/',
    component: Layout,
    redirect: 'user',
    meta: {
      title: '用户管理',
      icon: 'form'
    },
    children: [
      {
        path: 'user',
        name: 'user',
        component: () => import('@/views/users/index'),
        meta: { title: '用户管理', icon: 'form', activeMenu: '/user' }
      },
      {
        path: 'add',
        name: 'add',
        component: () => import('@/views/users/components/add.vue'),
        meta: { title: '用户新增', icon: 'form', activeMenu: '/user' },
        hidden: true
      },
      {
        path: 'edit',
        name: 'edit',
        component: () => import('@/views/users/components/edit.vue'),
        meta: { title: '用户修改', icon: 'form', activeMenu: '/user' },
        hidden: true
      }
    ]
  },
  {
    path: '/channel',
    component: Layout,
    meta: {
      title: '频道管理',
      icon: 'form'
    },
    redirect: 'list',
    children: [
      {
        path: 'list',
        name: 'channel',
        component: () => import('@/views/channel/index'),
        meta: { title: '频道管理', icon: 'form' },
        activeMenu: '/channel'
      },
      {
        path: 'update-channel',
        name: 'UpdateChannel',
        component: () =>
          import('@/views/channel/pages/updateChannel.vue'),
        meta: {
          title: '修改频道信息',
          icon: 'form',
          activeMenu: '/channel/list'
        },

        hidden: true
      },
      {
        path: 'export-live',
        name: 'ExportLive',
        component: () =>
          import('@/views/channel/pages/exportLive/index.vue'),
        meta: {
          title: '导入直播流',
          icon: 'form',
          activeMenu: '/channel/list'
        },

        hidden: true
      }
    ]
  },
  {
    path: '/permission',
    component: Layout,
    children: [
      {
        path: 'permission',
        name: 'permission',
        component: () => import('@/views/permission/index'),
        meta: { title: '权限分级', icon: 'form' }
      }
    ]
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
