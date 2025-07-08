/*
 * @Author: your name
 * @Date: 2021-07-26 20:05:41
 * @LastEditTime: 2021-11-04 17:05:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/permission.js
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
  // router.push({ query: to.query })

  // determine whether the user has logged in
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      to.query.dateid = new Date().getTime()
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      const hasRoles = store.getters.roles && store.getters.roles.length > 0
      if (hasRoles) {
        next()
      } else {
        try {
          await store.dispatch('user/getUserInfo')
          await store.dispatch('user/getConfigBase')
          const accessRoutes = await store.dispatch(
            'permission/generateRoutes',
            store.getters.roles
          )
          router.addRoutes(accessRoutes)
          const firstRoute = accessRoutes.find(item => !item.hidden)
          let path = ''
          firstRoute.children && firstRoute.children.length > 0
            ? (path = firstRoute.children[0].path)
            : (path = firstRoute.path)
          to.path === '/'
            ? next({ path: path })
            : next({ ...to, replace: true })
        } catch (error) {
          // remove token and go to login page to re-login
          //   await store.dispatch('user/resetToken')
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
