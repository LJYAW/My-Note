/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-06-08 11:38:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/permission.js
 */
import router from './router'
import store from './store'
// import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie

NProgress.configure({ showSpinner: true }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
  // start progress bar
  to.query.dateid = new Date().getTime()
  NProgress.start()
  if (to.meta.keepAlive) {
    store.commit('keepAlive/keepAlive', to.name)
  }
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done() // hack: https://github.com/PanJiaChen/vue-element-admin/pull/2939
    } else {
      // determine whether the user has obtained his permission roles through getInfo
      const hasRoles = store.getters.roles && store.getters.roles.length > 0
      if (hasRoles) {
        next()
      } else {
        try {
          // get user info
          // note: roles must be a object array! such as: ['admin'] or ,['developer','editor']
          (await store.dispatch('user/getUserLimits'))
          const roles = store.getters.roles
          // generate accessible routes map based on roles
          const accessRoutes = await store.dispatch('permission/generateRoutes', roles)
          // dynamically add accessible routes
          router.addRoutes(accessRoutes)

          const firstRoute = accessRoutes.find((item) => !item.hidden)
          let path = ''
          firstRoute.children && firstRoute.children.length > 0 ? (path = firstRoute.children[0].path) : (path = firstRoute.path)

          // hack method to ensure that addRoutes is complete
          // set the replace: true, so the navigation will not leave a history record
          // next({ ...to, replace: true })
          to.path === '/' ? next({ path: path }) : next({ ...to, replace: true })
        } catch (error) {
          console.log('🚀 ~ file: permission.js ~ line 72 ~ router.beforeEach ~ error', error)
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          // Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
