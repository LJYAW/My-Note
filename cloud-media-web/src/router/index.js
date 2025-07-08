/*
 * @Author: your name
 * @Date: 2021-06-23 11:09:31
 * @LastEditTime: 2021-10-18 09:56:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /opinion-monit-screen-v2/src/router/index.js
 */
/* Layout */
import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

import Layout from '@/layout'
// import jianshiLayout from '@/app/jianshi-video/layout/index.vue'

import ManageCenter from './manage-center'

import JianshiVideo from '@/app/jianshi-video/router/index.js'

const originalPush = Router.prototype.push

Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export const asyncRoutes = [
  {
    path: '/home',
    component: Layout,
    children: [
      {
        path: '/home',
        name: 'Home',
        component: () => import('@/views/home/index.vue'),
        meta: {
          roles: ['视频库', '视频管家'],
          title: '我的视频库'
        }
      },
      {
        path: '/video-edit',
        // component: () => import('@/app/video-edit-v2/main.vue'),
        component: () => import('@/views/sections/video-edit.vue'),
        meta: {
          roles: ['视频库', '视频管家'],
          title: '视频剪辑'
        }
      },
      {
        path: '/live-video',
        component: () => import('@/app/live-video/index.vue'),
        meta: {
          roles: ['视频工具箱|虚拟直播', '虚拟直播']
        }
      },
      {
        path: '/live-video/create',
        component: () => import('@/app/live-video/create.vue'),
        meta: {
          roles: ['视频工具箱|虚拟直播', '虚拟直播']
        }
      },
      {
        path: '/app-tools',
        component: () => import('@/views/app-tools/index.vue'),
        meta: {
          roles: ['视频工具箱']
        }
      },

      {
        path: '/video-masking',
        redirect: '/video-masking/add-videos',
        component: () => import('@/app/video-masking/index.vue'),
        meta: {
          roles: ['视频工具箱|视频水印', '视频水印']
        },
        children: [
          {
            path: '/video-masking/add-videos',
            component: () =>
              import('@/app/video-masking/page/add-videos/index.vue')
          },
          {
            path: '/video-masking/view',
            component: () => import('@/app/video-masking/page/view.vue')
          },
          {
            path: '/video-masking/video-edit-view',
            component: () =>
              import('@/app/video-masking/page/video-edit-view.vue')
          }
        ]
      },
      {
        path: '/phonetic-transcription',
        redirect: '/phonetic-transcription/view',
        component: () =>
          import('@/app/phonetic-transcription/index.vue'),
        meta: {
          roles: ['视频工具箱|语音转写', '语音转写']
        },
        children: [
          {
            path: '/phonetic-transcription/view',
            component: () =>
              import('@/app/phonetic-transcription/page/view.vue')
          },
          {
            path: '/phonetic-transcription/details',
            component: () =>
              import('@/app/phonetic-transcription/page/details.vue')
          }
        ]
      },
      {
        path: '/video-detail',
        component: () => import('@/views/video-detail/index.vue'),
        meta: {
          roles: ['视频库', '视频管家'],
          title: '视频详情'
        }
      },
      {
        path: '/my-cut-videos',
        component: () => import('@/views/my-cut-videos/index.vue'),
        meta: {
          roles: ['视频库', '视频管家'],
          title: '我的剪辑'
        }
      },
      {
        path: '/my-collection',
        component: () => import('@/views/collection/index.vue'),
        meta: {
          roles: ['视频库', '视频管家'],
          title: '我的收藏'
        }
      },
      {
        path: '/manage-center',
        component: () => import('@/views/manage-center/index.vue'),
        children: [...ManageCenter],
        meta: {
          type: '管理中心'
        }
      }
    ]
  },
  {
    path: '/jianshi-video',
    component: () => import('@/app/jianshi-video/views/index.vue'),
    redirect: '/jianshi-video/add-videos',
    children: [...JianshiVideo],
    meta: {
      type: '简视频',
      roles: ['视频工具箱|简视视频创作']
    }
  }
]

export const constantRoutes = [
  // {
  //   path: '*',
  //   redirect: '/',
  //   hidden: true
  // },
  // {
  //   path: '/login',
  //   component: () => import('@/views/login/index.vue'),
  //   meta: {
  //     hidden: true
  //   }
  // }
  {
    path: '/',
    component: Layout,
    redirect: '/login',
    children: [
      {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/guide/index.vue'),
        meta: {
          hidden: true
        }
      },
      {
        path: '/guide/script-creation',
        component: () => import('@/views/guide/script-creation.vue'),
        meta: {
          hidden: true,
          appName: '快速创作'
        }
      },
      {
        path: '/guide/video-home',
        component: () => import('@/views/guide/video-home.vue'),
        meta: {
          hidden: true,
          appName: '视频管家'
        }
      },
      {
        path: '/guide/subtitle-make',
        component: () => import('@/views/guide/subtitle-make.vue'),
        meta: {
          hidden: true,
          appName: '字幕创作'
        }
      },
      {
        path: '/pay-qr',
        component: () => import('@/views/pay-qr/index.vue'),
        meta: {
          hidden: true
        }
      },
      {
        path: '/guide/service',
        component: () => import('@/views/guide/service.vue'),
        meta: {
          hidden: true,
          appName: '企业服务'
        }
      },
      {
        path: '/guide/phonetic-make',
        component: () => import('@/views/guide/phonetic-make.vue'),
        meta: {
          hidden: true,
          appName: '语音创作'
        }
      },
      {
        path: '/guide/video-masking',
        component: () => import('@/views/guide/video-masking.vue'),
        meta: {
          hidden: true,
          appName: '视频水印'
        }
      },
      {
        path: '/guide/hot-qa',
        component: () => import('@/views/guide/hot-qa.vue'),
        meta: {
          hidden: true,
          appName: '使用帮助'
        }
      }
    ]
  },
  {
    path: '/reset-password',
    component: () => import('@/views/login/resetPwd.vue'),
    meta: {
      hidden: true
    }
  },
  {
    path: '/share-video',
    component: () => import('@/app/share-video-h5/list.vue'),
    meta: {
      hidden: true
    }
  },
  {
    path: '/live-video/share',
    component: () => import('@/app/live-video/share.vue'),
    meta: {
      hidden: true
    }
  },
  {
    path: '/share-detail',
    component: () => import('@/app/share-video-h5/detail.vue'),
    meta: {
      hidden: true
    }
  },
  {
    path: '/404',
    component: () => import('@/views/sections/404.vue'),
    meta: {
      hidden: true
    }
  }
]

const createRouter = () =>
  new Router({
    mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  })

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
