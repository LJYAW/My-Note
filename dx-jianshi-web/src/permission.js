/*
 * @Author: your name
 * @Date: 2021-01-06 15:17:45
 * @LastEditTime: 2021-07-19 21:38:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/perssion.js
 */
import router from './router'
import isMobile from '@/router/is_mobile'
const whiteList = ['/intellect-create', '/mobile-login']

router.beforeEach((to, from, next) => {
  // to要跳转到的路径
  // from从哪个路径来
  // next往下执行的回调
  // 在localStorage中获取token
  let token = localStorage.getItem('user_info') || ''
  if (token) {
    next()
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      // 判断是否是移动端
      if (isMobile()) {
        next({
          path: '/mobile-login'
        })
      } else {
        // 否则跳转到login登录页面
        let login = document.getElementById('loginBtn')
        if (login) {
          login.click()
        }
        next({
          path: '/intellect-create'
        })
      }
    }
  }
})
