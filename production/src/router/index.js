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
    path: '/column/pdf',
    name: 'ColumnPdf',
    component: () => import('@/views/pdf/ColumnPdf.vue'),
    meta: { title: 'pdf', icon: 'el-icon-user-solid' },
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
    path: '/column/create-column',
    component: () => import('@/views/column/index.vue'),
    name: '创建栏目',
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
    redirect: '/full-business/edit-task',
    meta: {
      title: '整档业务',
      icon: 'el-icon-s-order',
      roles: ['superman', '整档编辑', '整档编辑管理员'] // you can set roles in root nav
    },
    alwaysShow: true,
    children: [
      {
        path: '/full-business/edit-task',
        name: 'Home',
        component: () => import('@/views/full_business/edit_task/index.vue'),
        meta: {
          title: '编辑任务',
          icon: 'table',
          roles: ['superman', '整档编辑', '整档编辑管理员'] // you can set roles in root nav
        }
      },
      {
        path: '/full-business/task-choice/',
        name: 'taskChoice',
        component: () =>
          import('@/views/full_business/edit_task/task_choice/task_choice.vue'),
        meta: {
          title: '任务选择',
          icon: 'dashboard',
          activeMenu: '/full-business/edit-task'
        },
        hidden: true
      },
      {
        path: '/full-business',
        name: 'TaskMgt',
        component: () => import('@/views/full_business/task_mgt/index.vue'),
        redirect: '/full-business/task-mgt',
        meta: {
          title: '业务管理',
          icon: 'tree',
          roles: ['superman', '整档编辑管理员']
        },
        children: [
          {
            path: '/full-business/task-mgt',
            name: 'TaskList',
            component: () =>
              import('@/views/full_business/task_mgt/TaskMgt.vue'),
            meta: {
              title: '任务列表',
              icon: 'tree',
              roles: ['superman', '整档编辑管理员'], // you can set roles in root nav
              activeMenu: '/full-business',
              keepAlive: true
            },
            hidden: true
          },
          {
            path: '/full-business/video-list',
            name: 'VideoList',
            component: () =>
              import('@/views/full_business/task_mgt/video_list.vue'),
            meta: {
              title: '视频列表',
              icon: 'tree',
              roles: ['superman', '整档编辑管理员'], // you can set roles in root nav
              activeMenu: '/full-business'
            },
            hidden: true
          }
        ]
      }
    ]
  },
  {
    path: '/split-tasks',
    component: Layout,
    redirect: '/split-tasks/edit-task',
    name: 'SplitTasks',
    meta: {
      title: '拆条业务',
      icon: 'el-icon-s-ticket',
      roles: ['superman', '拆条编辑', '拆条编辑管理员'] // you can set roles in root nav
    },
    alwaysShow: true,
    children: [
      {
        path: '/split-tasks/edit-task',
        name: 'EditTask',
        component: () => import('@/views/split_tasks/edit_task'),
        meta: {
          title: '编辑任务',
          icon: 'table',
          roles: ['superman', '拆条编辑', '拆条编辑管理员'], // you can set roles in root nav
          keepAlive: true
        }
      },
      {
        path: '/split-tasks/task-choice',
        name: 'taskChoice',
        component: () =>
          import('@/views/split_tasks/edit_task/task_choice/task_choice.vue'),
        meta: {
          title: '任务选择',
          icon: 'dashboard',
          activeMenu: '/split-tasks/edit-task'
        },
        hidden: true
      },
      {
        path: '',
        name: 'Split',
        component: () => import('@/views/split_tasks/task_mgt/index.vue'),
        redirect: '/split-tasks/task-mgt',
        meta: {
          title: '业务管理',
          icon: 'tree',
          // roles: ['teamLeader'] // you can set roles in root nav
          roles: ['superman', '拆条编辑管理员']
        },
        children: [
          {
            path: '/split-tasks/task-mgt',
            name: 'TaskMgt',
            component: () => import('@/views/split_tasks/task_mgt/TaskMgt.vue'),
            meta: {
              title: '任务列表',
              icon: 'tree',
              roles: ['superman', '拆条编辑管理员'], // you can set roles in root nav
              activeMenu: '/split-tasks',
              keepAlive: true
            },
            hidden: true
          },
          {
            path: '/split-tasks/video-list',
            name: 'VideoList',
            component: () =>
              import('@/views/split_tasks/task_mgt/video_list.vue'),
            meta: {
              title: '视频列表',
              icon: 'tree',
              roles: ['superman', '拆条编辑管理员'], // you can set roles in root nav
              activeMenu: '/split-tasks'
            },
            hidden: true
          }
        ]
      }
    ]
  },
  {
    path: '/assign',
    redirect: '/order/assign',
    component: Layout,
    name: 'Assign',
    meta: {
      title: '工单指派',
      icon: 'el-icon-s-help',
      roles: ['superman'] // you can set roles in root nav
    },
    children: [
      {
        path: '/order/assign',
        name: 'OrderAssign',
        component: () => import('@/views/assign/index.vue'),
        meta: {
          title: '',
          icon: 'el-icon-user-solid',
          activeMenu: '/assign'
        },
        hidden: true
      }
    ]
  },
  {
    path: '/EPG',
    component: Layout,
    redirect: '/EPG/task-details',
    name: 'EPG',
    meta: {
      title: 'EPG业务',
      icon: 'el-icon-s-order',
      roles: [
        'superman',
        'EPG编辑',
        'EPG审核',
        'EPG编辑管理员',
        'EPG审核管理员'
      ] // you can set roles in root nav
    },
    alwaysShow: true,
    children: [
      {
        path: '/EPG/edit-task',
        name: 'EditTask',
        component: () => import('@/views/EPG/edit_task/index.vue'),
        meta: {
          title: '编辑任务',
          icon: 'table',
          roles: ['superman', 'EPG编辑', 'EPG编辑管理员']
        }
      },
      {
        path: '/split-tasks/new-system-pad',
        name: 'newEditSystem',
        component: () => import('@/views/EPG/new-system-pad/index.vue'),
        meta: {
          title: '编辑任务v2',
          icon: 'table',
          roles: ['superman', '拆条编辑', '拆条编辑管理员'], // you can set roles in root nav
          keepAlive: false
        },
        hidden: true
      },
      {
        path: '/EPG/examine-count',
        name: 'ExamineCount',
        component: () => import('@/views/EPG/examine/index.vue'),
        redirect: '/EPG/examine-count/list',
        meta: {
          title: '审核任务',
          icon: 'el-icon-s-claim',
          roles: ['superman', 'EPG审核', 'EPG审核管理员']
        },
        children: [
          {
            path: 'list',
            component: () =>
              import('@/views/EPG/examine/examine_count/index.vue'),
            meta: {
              title: '审核任务列表',
              icon: 'table',
              roles: ['superman', 'EPG审核', 'EPG审核管理员'],
              keepAlive: true
            }
          },
          {
            path: 'examine-task',
            name: 'ExamineTask',
            component: () =>
              import(
                '@/views/EPG/examine/examine_count/examine_task/index.vue'
              ),
            meta: {
              title: '审核任务',
              icon: 'el-icon-s-claim',
              roles: ['superman', 'EPG审核', 'EPG审核管理员'],
              activeMenu: '/EPG/examine-count/list'
            },
            hidden: true
          },
          {
            path: 'machine',
            component: () =>
              import('@/views/EPG/examine/examine_machine/index.vue'),
            meta: {
              title: '机器审核',
              icon: 'table',
              roles: ['superman', 'EPG审核', 'EPG核管理员'],
              keepAlive: true
            }
            // hidden: true
          }
        ]
      },
      {
        path: '/EPG/examine-task-log',
        name: 'ExamineTaskLog',
        component: () => import('@/views/EPG/examine_log/index.vue'),
        meta: {
          title: '审核任务日志',
          icon: 'table',
          roles: ['superman', 'EPG审核', 'EPG审核管理员']
        }
      },
      {
        path: '/EPG/business',
        name: 'business',
        component: () => import('@/views/EPG/business/index.vue'),
        meta: {
          title: '业务管理',
          icon: 'tree',
          roles: ['superman', 'EPG审核', 'EPG编辑管理员', 'EPG审核管理员']
        },
        redirect: '/EPG/edit-monitor',
        children: [
          {
            path: '/EPG/edit-monitor',
            name: 'EditMonitor',
            component: () => import('@/views/EPG/business/TaskMonitor.vue'),
            meta: {
              title: '任务监控',
              icon: 'el-icon-s-marketing',
              roles: ['superman', 'EPG审核', 'EPG编辑管理员', 'EPG审核管理员']
            }
          },
          {
            path: '/EPG/edit-custom-class',
            name: 'EditCustomClass',
            component: () =>
              import('@/views/EPG/business/edit_custom_class/index.vue'),
            meta: {
              title: '编辑客户定制分类',
              icon: 'el-icon-s-custom',
              roles: ['superman', 'EPG审核', 'EPG编辑管理员', 'EPG审核管理员']
            }
          },
          {
            path: '/EPG/playVideoFlow',
            name: 'playVideoFlow',
            component: () =>
              import('@/views/EPG/business/showDateilsModel/PlayVideo.vue'),
            hidden: true,
            meta: {
              title: '播放流',
              icon: 'el-icon-s-data',
              roles: ['superman', 'EPG审核', 'EPG编辑管理员', 'EPG审核管理员']
            }
          },
          {
            path: '/EPG/task-details',
            name: 'TaskDetails',
            component: () => import('@/views/EPG/business/TaskDetails.vue'),
            meta: {
              title: '任务详情',
              icon: 'el-icon-s-data',
              roles: ['superman', 'EPG审核', 'EPG编辑管理员', 'EPG审核管理员']
            }
          }
        ]
      }
    ]
  },

  {
    path: '/advertisement',
    component: Layout,
    redirect: '/advertisement/edit-task',
    name: 'advertisement',
    meta: {
      title: '广告业务',
      icon: 'el-icon-s-platform',
      roles: [
        'superman',
        '广告编辑',
        '广告审核',
        '广告编辑管理员',
        '广告审核管理员'
      ]
    },
    alwaysShow: true,
    children: [
      {
        path: '/advertisement/edit-task',
        name: 'EditTask',
        component: () => import('@/views/advertisement/edit_task/index.vue'),
        meta: {
          title: '编辑任务',
          icon: 'table',
          roles: ['superman', '广告编辑', '广告编辑管理员'],
          keepAlive: true
        }
      },
      {
        path: '/advertisement/examine-count',
        name: 'ExamineCount',
        component: () =>
          import('@/views/advertisement/examine_count/index.vue'),
        meta: {
          title: '审核任务',
          icon: 'el-icon-s-claim',
          roles: ['superman', '广告审核', '广告审核管理员'],
          keepAlive: true
        }
      },
      {
        path: '/advertisement/examine-task',
        name: 'ExamineTask',
        component: () =>
          import('@/views/advertisement/examine_count/examine_task/index.vue'),
        meta: {
          title: '审核任务',
          icon: 'el-icon-s-claim',
          roles: ['superman', '广告审核', '广告审核管理员'],
          activeMenu: '/advertisement/examine-count'
        },
        hidden: true
      },
      {
        path: '/advertisement/examine-task-log',
        name: 'AdvertisementTaskLog',
        component: () => import('@/views/advertisement/examine_log/index.vue'),
        meta: {
          title: '审核任务日志',
          icon: 'table',
          roles: ['superman', '广告审核', '广告审核管理员']
        }
      },
      {
        path: '/advertisement/edit-system',
        name: 'EditSystem',
        component: () =>
          import('@/views/advertisement/edit_task/edit_system/index.vue'),
        meta: {
          title: '编辑系统',
          icon: 'el-icon-user-solid',
          activeMenu: '/advertisement/edit-task',
          roles: ['superman', '广告编辑', '广告编辑管理员']
        },
        hidden: true
      },
      {
        path: '/advertisement/detail-system',
        name: 'DetailSystem',
        component: () =>
          import('@/views/advertisement/edit_task/edit_system/index.vue'),
        meta: {
          title: '编辑系统',
          icon: 'el-icon-user-solid',
          activeMenu: '/advertisement/deatils',
          roles: ['superman', '广告审核', '广告审核管理员', '广告编辑管理员']
        },
        hidden: true
      },
      {
        path: '/advertisement/sample-management',
        name: 'sample',
        component: () =>
          import('@/views/advertisement/sample_manage/index.vue'),
        meta: {
          title: '样本管理',
          icon: 'tree',
          roles: ['superman']
        },
        redirect: '/advertisement/sample-bank',
        children: [
          {
            path: '/advertisement/base-message',
            name: 'BaseMessage',
            component: () =>
              import('@/views/advertisement/sample_manage/basic/index.vue'),
            meta: {
              title: '基础信息',
              icon: 'el-icon-s-marketing',
              rroles: ['superman']
            }
          },
          {
            path: '/advertisement/sample-bank',
            name: 'SampleBank',
            component: () =>
              import(
                '@/views/advertisement/sample_manage/sample_bank/index.vue'
              ),
            meta: {
              title: '样本库',
              icon: 'el-icon-s-marketing',
              rroles: ['superman']
            }
          },
          {
            path: '/advertisement/sample-queue',
            name: 'SampleQueue',
            component: () =>
              import(
                '@/views/advertisement/sample_manage/sample_queue/index.vue'
              ),
            meta: {
              title: '添加队列',
              icon: 'el-icon-s-data',
              roles: ['superman']
            }
          }
        ]
      },
      {
        path: '/advertisement/business',
        name: 'business',
        component: () => import('@/views/advertisement/business/index.vue'),
        meta: {
          title: '业务管理',
          icon: 'tree',
          roles: ['superman', '广告编辑管理员', '广告审核管理员']
        },
        redirect: '/advertisement/monitor',
        children: [
          {
            path: '/advertisement/monitor',
            name: 'EditMonitor',
            component: () =>
              import('@/views/advertisement/business/Monitor.vue'),
            meta: {
              title: '任务监控',
              icon: 'el-icon-s-marketing',
              rroles: [
                'superman',
                '广告审核',
                '广告编辑管理员',
                '广告审核管理员'
              ]
            }
          },
          {
            path: '/advertisement/deatils',
            name: 'TaskDetails',
            component: () =>
              import('@/views/advertisement/business/Datetils.vue'),
            meta: {
              title: '任务详情',
              icon: 'el-icon-s-data',
              roles: [
                'superman',
                '广告审核',
                '广告编辑管理员',
                '广告审核管理员'
              ],
              keepAlive: true
            }
          }
        ]
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: '/system/user-mgt',
    name: 'SplitTasks',
    meta: {
      title: '系统管理',
      icon: 'el-icon-s-help',
      roles: ['superman'] // you can set roles in root nav
    },
    children: [
      {
        path: '/system/user-mgt',
        name: 'UserMgt',
        component: () => import('@/views/user_mgt/index'),
        meta: { title: '用户管理', icon: 'el-icon-user-solid' }
      }
    ]
  }
]

const createRouter = () => new Router({
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
