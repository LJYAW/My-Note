/*
 * @Author: your name
 * @Date: 2021-10-20 11:19:06
 * @LastEditTime: 2021-12-08 16:10:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/router/index.js
 */
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  }
]

export const asyncRoutes = [
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  { path: '*', redirect: '/404', hidden: true },

  // 订单管理
  {
    path: '/',
    component: Layout,
    redirect: '/product',
    meta: {
      title: '订单管理',
      roles: ['superman', '订单管理'] // you can set roles in root nav
    },
    children: [
      {
        path: 'order-manage',
        name: 'OrderManage',
        component: () => import('@/views/order-manage/index.vue'),
        meta: {
          title: '订单管理',
          icon: '',
          roles: ['superman', '订单管理']
        }
      },
      {
        path: 'order-detail',
        name: 'OrderDetail',
        component: () => import('@/views/order-manage/detail/index.vue'),
        meta: {
          title: '订单详情',
          icon: '',
          activeMenu: '/order-manage',
          roles: ['superman', '订单管理']
        },
        hidden: true
      },
      {
        path: 'logistics-message',
        name: 'LogisticsInformation',
        component: () => import('@/views/order-manage/logistics/index.vue'),
        meta: {
          title: '物流信息',
          icon: '',
          activeMenu: '/order-manage',
          roles: ['superman', '订单管理']
        },
        hidden: true
      }
    ]
  },
  // 产品管理
  {
    path: '/product',
    component: Layout,
    redirect: '/product-manage',
    meta: {
      title: '产品管理',
      roles: ['superman', '产品管理'] // you can set roles in root nav
    },
    children: [
      {
        path: '/product-manage',
        name: 'ProductManage',
        component: () => import('@/views/product-manage/index.vue'),
        meta: {
          title: '产品管理',
          icon: '',
          roles: ['superman', '产品管理']
        }
      },
      {
        path: '/new-product',
        name: 'NewProduct',
        component: () => import('@/views/product-manage/new-product/index.vue'),
        meta: {
          title: '新建产品',
          icon: 'dashboard',
          activeMenu: '/product-manage',
          roles: ['superman', '产品管理']
        },
        hidden: true
      },
      {
        path: '/edit-product',
        name: 'EditProduct',
        component: () =>
          import('@/views/product-manage/edit-product/index.vue'),
        meta: {
          title: '编辑产品',
          icon: 'dashboard',
          activeMenu: '/product-manage',
          roles: ['superman', '产品管理']
        },
        hidden: true
      }
    ]
  },
  // 分类管理
  {
    path: '/classify',
    component: Layout,
    redirect: '/classify-manage',
    meta: {
      title: '分类管理',
      roles: ['superman', '分类管理']
    },
    children: [
      {
        path: '/classify-manage',
        name: 'ClassifyManage',
        component: () => import('@/views/classify-manage/index.vue'),
        meta: {
          title: '分类管理',
          icon: '',
          roles: ['superman', '分类管理']
        }
      },
      {
        path: '/new-class',
        name: 'NewClass',
        component: () => import('@/views/classify-manage/new-class/index.vue'),
        meta: {
          title: '新建分类',
          icon: 'dashboard',
          activeMenu: '/classify-manage',
          roles: ['superman', '分类管理']
        },
        hidden: true
      },
      {
        path: '/edit-class',
        name: 'EditClass',
        component: () => import('@/views/classify-manage/edit-class/index.vue'),
        meta: {
          title: '编辑分类',
          icon: 'dashboard',
          activeMenu: '/classify-manage',
          roles: ['superman', '分类管理']
        },
        hidden: true
      },
      {
        path: '/class-goods',
        name: 'ClassGoods',
        component: () =>
          import('@/views/classify-manage/class-goods/index.vue'),
        meta: {
          title: '产品列表',
          icon: 'dashboard',
          activeMenu: '/classify-manage',
          roles: ['superman', '分类管理']
        },
        hidden: true
      }
    ]
  },
  // 主页管理
  {
    path: '/home',
    component: Layout,
    redirect: '/home-manage',
    meta: {
      title: '主页管理',
      roles: ['superman', '主页管理']
    },
    children: [
      {
        path: '/home-manage',
        name: 'HomeManage',
        component: () => import('@/views/home-manage/index.vue'),
        meta: {
          title: '主页管理',
          icon: '',
          roles: ['superman', '主页管理']
        }
      }
    ]
  },
  // 用户管理
  {
    path: '/user',
    component: Layout,
    redirect: '/user-manage',
    meta: {
      title: '用户管理',
      roles: ['superman', '用户管理']
    },
    children: [
      {
        path: '/user-manage',
        name: 'UserManage',
        component: () => import('@/views/user-manage/index.vue'),
        meta: {
          title: '用户管理',
          icon: '',
          roles: ['superman', '用户管理']
        }
      },
      {
        path: '/user-detail',
        name: 'UserDetail',
        component: () => import('@/views/user-manage/detail/index.vue'),
        meta: {
          title: '用户详情',
          icon: 'dashboard',
          activeMenu: '/user-manage',
          roles: ['superman', '用户管理']
        },
        hidden: true
      }
    ]
  },
  {
    path: '/account',
    component: Layout,
    redirect: '/account-manage',
    meta: {
      title: '子账号管理',
      roles: ['superman', '子账号管理']
    },
    children: [
      {
        path: '/account-manage',
        name: 'AccountManage',
        component: () => import('@/views/account-manage/index.vue'),
        meta: {
          title: '子账号管理',
          icon: '',
          roles: ['superman', '子账号管理']
        }
      }
    ]
  },
  {
    path: '/auction',
    component: Layout,
    redirect: '/auction/list',
    meta: {
      title: '竞拍管理',
      roles: ['superman', '子账号管理']
    },
    children: [
      {
        path: 'list',
        name: 'Auction',
        component: () => import('@/views/auction/list.vue'),
        meta: {
          title: '竞拍列表',
          icon: '',
          roles: ['superman', '子账号管理']
        }
      },
      {
        path: '/auction-details',
        name: 'AuctionDetails',
        component: () => import('@/views/auction/details.vue'),
        meta: {
          title: '竞拍详情',
          icon: '',
          roles: ['superman', '子账号管理']
        },
        hidden: true
      }
    ]
  }
  // 404 page must be placed at the end !!!
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
