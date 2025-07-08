/*
 * @Author: your name
 * @Date: 2021-06-08 10:46:54
 * @LastEditTime: 2021-06-08 11:21:53
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /crawler-admin/src/utils/btn-permission.js
 */
// src/directives/permission.js
import Vue from 'vue'
import store from '@/store'

// 是否有权限
const hasPermission = userPermission => {
  const userPermissionList = Array.isArray(userPermission) ? userPermission : [userPermission]
  // 当前用户的权限列表
  const permissionList = store.getters.roles || []
  return userPermissionList.some(e => permissionList.includes(e))
}

// 指令
Vue.directive('has', {
  inserted: (el, binding, vnode) => {
    if (!hasPermission(binding.value)) {
      el.parentNode.removeChild(el)
    }
  }
})

// 全局判断方法
Vue.prototype.$_has = hasPermission
