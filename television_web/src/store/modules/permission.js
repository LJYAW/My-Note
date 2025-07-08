/*
 * @Author: your name
 * @Date: 2021-01-20 16:24:41
 * @LastEditTime: 2021-07-20 19:08:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/store/modules/permission.js
 */
import { asyncRoutes, constantRoutes } from '@/router'

function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some((role) => route.meta.roles.includes(role))
  } else {
    return true
  }
}

export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })
  return res
}

const state = {
  addRoutes: [],
  routes: []
}
const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = routes.concat(constantRoutes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise(resolve => {
      let accessedRoutes
      if (roles.includes('superman')) {
        accessedRoutes = asyncRoutes || []
      } else {
        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      }
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}
export default {
  namespaced: true,
  state,
  mutations,
  actions
}
