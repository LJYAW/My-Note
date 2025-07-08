/*
 * @Author: your name
 * @Date: 2021-07-26 20:05:41
 * @LastEditTime: 2021-10-11 17:44:32
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/permission.js
 */
import router from './router'
import { constantRoutes } from '@/router'

import store from './store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie

// router.onError((error) => {
//   const pattern = /Loading chunk (\d)+ failed/g // 这个是网上大部分遇到的
//   const cssPattern = /Loading CSS chunk (\d)+ failed/g // 这个是我实际项目中遇到的
//   const isChunkLoadFailed = error.message.match(pattern) || error.message.match(cssPattern)
//   if (isChunkLoadFailed) {
//     // 用路由的replace方法，并没有相当于F5刷新页面，失败的js文件并没有从新请求，会导致一直尝试replace页面导致死循环，而用 location.reload 方法，相当于触发F5刷新页面，虽然用户体验上来说会有刷新加载察觉，但不会导致页面卡死及死循环，从而曲线救国解决该问题
//     location.reload()
//     // const targetPath = $router.history.pending.fullPath;
//     // $router.replace(targetPath);
//   }
// })

NProgress.configure({ showSpinner: true }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelis

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  if (to.meta.keepAlive) {
    store.commit('keepAlive/keepAlive', to.name)
  }

  const settings = { fixedHeader: false }
  if (to.path === '/home') {
    settings.fixedHeader = true
  }

  store.dispatch('settings/changeSetting', settings)

  const hasToken = getToken()
  if (hasToken) {
    const hasRoles = store.getters.roles && store.getters.roles.length > 1

    if (hasRoles) {
      next()
    } else {
      try {
        await store.dispatch('user/getUserInfo')
        await store.dispatch('user/getDirInfo')
        await store.dispatch('company/getCompanyInfo')

        const roles = store.getters.roles

        const accessRoutes = await store.dispatch('permission/generateRoutes', roles)
        router.addRoutes(accessRoutes)

        next({ ...to })
      } catch (error) {
        await store.dispatch('user/resetToken')
        next(`/login`)
        NProgress.done()
      }
    }
  } else {
    /* has no token*/
    next()
    NProgress.done()
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
