/*
 * @Author: your name
 * @Date: 2021-09-08 17:47:33
 * @LastEditTime: 2021-10-21 16:57:12
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/router/index.js
 */

export default [
  {
    path: 'script-create',
    name: 'scriptCreate',
    component: () =>
      import('@/app/jianshi-video/views/script-create/index.vue'),
    meta: {
      appName: '快速创作'
    }
  },
  {
    path: 'quick-script-create',
    name: 'QuickScriptCreate',
    component: () =>
      import('@/app/jianshi-video/views/quick-script-create/index.vue'),
    meta: {
      appName: '快速创作'
    }
  },
  {
    path: 'add-videos',
    name: 'addVideos',
    component: () => import('@/app/jianshi-video/views/add-videos/index.vue'),
    meta: {
      appName: '字幕创作'
    }
  },
  {
    path: 'add-videos-phonetic',
    name: 'addVideosPhonetic',
    component: () =>
      import('@/app/jianshi-video/views/phonetic-create/add-videos/index.vue'),
    meta: {
      appName: '语音创作'
    }
  },
  {
    path: 'phonetic-create',
    name: 'phoneticCreate',
    component: () =>
      import('@/app/jianshi-video/views/phonetic-create/index.vue'),
    meta: {
      appName: '语音创作'
    }
  },
  {
    path: 'subtitle-create',
    name: 'subtitleCreation',
    component: () =>
      import('@/app/jianshi-video/views/subtitle-create/index.vue'),
    meta: {
      appName: '字幕创作'
    }
  },
  {
    path: 'manage-center',
    redirect: 'manage-center/video-manage',
    component: () =>
      import('@/app/jianshi-video/views/manage-center/index.vue'),
    children: [
      {
        path: 'video-manage',
        component: () =>
          import(
            '@/app/jianshi-video/views/manage-center/video-manage/index.vue'
          ),
        meta: {
          icon: 'video',
          title: '视频管理'
        }
      },
      {
        path: 'material-center',
        component: () =>
          import(
            '@/app/jianshi-video/views/manage-center/material-center/index.vue'
          ),
        meta: {
          icon: 'material',
          title: '素材中心'
        }
      }
    ]
  }
]
