/*
 * @Author: zzx
 * @Date: 2020-07-22 19:46:46
 * @LastEditTime: 2020-07-23 15:07:58
 * @FilePath: /weijian_web/src/router/user_router_children.js
 */
var new_json = [
  {
    name: '我的作品',
    router: '/user-info/ai-created',
    file_path: 'my_videos'
  },
  {
    name: '我的素材库',
    router: '/user-info/my-library',
    file_path: 'my_library'
  },
  {
    name: '收藏夹',
    router: '/user-info/my-collection',
    file_path: 'my_collection'
  },
  {
    name: '发布记录',
    router: '/user-info/release-record',
    file_path: 'release_record'
  },
  {
    name: '购买记录',
    router: '/user-info/purchase-records',
    file_path: 'purchase_records'
  },
  {
    name: '消息中心',
    router: '/user-info/message-center',
    file_path: 'message_center'
  },
  {
    name: '发布者账号',
    router: '/user-info/publisher-account',
    file_path: 'publisher_account'
  },
  {
    name: '账号信息',
    router: '/user-info/account-info',
    file_path: 'account_info'
  }
]

var user_children = []
new_json.forEach((item) => {
  var temp = {
    path: item.router,
    name: item.name,
    component: () => import(`@/views/user_info/pages/${item.file_path}`),
    meta: { title: item.name }
  }
  user_children.push(temp)
})

export default user_children
