/*
 * @Author: zzx
 * @Date: 2020-07-22 19:46:46
 * @LastEditTime: 2020-07-23 15:07:58
 * @FilePath: /weijian_web/src/router/user_router_children.js
 */
var effect_json = [
  {
    name: 'host',
    router: '/effect/host',
    file_path: 'host'
  },
  {
    name: 'back_music',
    router: '/effect/back_music',
    file_path: 'back_music'
  },
  {
    name: 'frame',
    router: '/effect/frame',
    file_path: 'frame'
  },
  {
    name: 'title',
    router: '/effect/title',
    file_path: 'title'
  },
  {
    name: 'film',
    router: '/effect/film',
    file_path: 'film'
  }
]

var effect_children = []
effect_json.forEach(item => {
  var temp = {
    path: item.router,
    name: item.name,
    component: () => import(`../page/effect/effect_tab/${item.file_path}`),
    meta: { title: item.name }
  }
  effect_children.push(temp)
})

export default effect_children
