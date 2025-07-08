/*
 * @Author: your name
 * @Date: 2021-08-03 14:39:12
 * @LastEditTime: 2021-10-19 11:54:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/router/mange-center.js
 */
export default [
  {
    path: 'account-info',
    component: () => import('@/views/manage-center/account-info/index.vue'),
    meta: {
      title: '账号信息',
      roles: ['帐号信息'],
      icon: 'home'
    }
  },
  {
    path: 'videos-manage',
    component: () => import('@/views/manage-center/videos-manage/index.vue'),
    meta: {
      title: '视频管理',
      roles: ['视频管理'],
      icon: 'video'
    }
  },
  {
    path: 'creative-center',
    component: () =>
      import('@/app/jianshi-video/views/manage-center/video-manage/index.vue'),
    meta: {
      icon: 'create',
      title: '创作中心',
      roles: ['创作中心']
    }
  },
  {
    path: 'material-center',
    component: () => import('@/views/manage-center/material-center/index.vue'),
    meta: {
      icon: 'material',
      title: '素材中心',
      roles: ['素材中心'],
      keepAlive: true
    }
  },
  {
    path: 'directory-manage',
    component: () => import('@/views/manage-center/directory-manage/index.vue'),
    meta: {
      title: '目录管理',
      roles: ['目录管理'],
      icon: 'directory'
    }
  },
  {
    path: 'enterprise-info',
    component: () => import('@/views/manage-center/enterprise-info/index.vue'),
    meta: {
      title: '企业信息',
      roles: ['企业信息'],
      icon: 'enterprise'
    }
  },
  // {
  //   path: 'material-center',
  //   component: () => import('@/views/manage-center/material-center/index.vue'),
  //   meta: {
  //     title: '素材中心',
  //     roles: ['素材中心'],
  //     icon: 'material'
  //   }
  // },
  {
    path: 'operation-log',
    component: () => import('@/views/manage-center/operation-log/index.vue'),
    meta: {
      title: '操作记录',
      roles: ['操作记录'],
      icon: 'operation'
    }
  },
  {
    path: 'order-info',
    component: () => import('@/views/manage-center/order-info/index.vue'),
    meta: {
      title: '订单管理',
      roles: ['订单管理'],
      icon: 'order'
    }
  },
  {
    path: 'pay',
    component: () => import('@/views/manage-center/pay/index.vue'),
    meta: {
      title: '购买续费',
      roles: ['购买续费'],
      icon: 'pay'
    }
  },
  // {
  //   path: 'pay-qr',
  //   component: () => import('@/views/manage-center/pay-qr/index.vue'),
  //   meta: {
  //     hidden: true,
  //     title: '购买续费',
  //     roles: ['购买套餐'],
  //     icon: 'pay'
  //   }
  // },
  {
    path: 'user-manage',
    component: () => import('@/views/manage-center/user-manage/index.vue'),
    meta: {
      title: '用户管理',
      roles: ['用户管理'],
      icon: 'user'
    }
  },
  {
    path: 'user-manage/role-manage',
    component: () => import('@/views/manage-center/user-manage/roleList.vue'),
    meta: {
      title: '角色管理',
      hidden: true,
      roles: ['用户管理'],
      icon: 'user'
    }
  },
  {
    path: 'videos-manage/videos-detail',
    component: () =>
      import('@/views/manage-center/videos-manage/video-detail/index.vue'),
    meta: {
      hidden: true,
      title: '视频详情',
      roles: ['视频管理']
    }
  },
  {
    path: 'videos-manage/add-videos',
    component: () =>
      import('@/views/manage-center/videos-manage/add-video/index.vue'),
    meta: {
      hidden: true,
      title: '上传视频',
      roles: ['视频管理']
    }
  }
]
