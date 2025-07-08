/*
 * @Author: your name
 * @Date: 2021-04-25 14:13:35
 * @LastEditTime: 2021-04-26 14:16:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work_order_web/src/store/modules/keepAlive.js
 */
export default {
  namespaced: true,
  state: {
    keepAliveComponents: [] // 缓存数组
  },
  mutations: {
    keepAlive(state, component) {
      // 注：防止重复添加（当然也可以使用Set）
      !state.keepAliveComponents.includes(component) &&
        state.keepAliveComponents.push(component)
    },
    noKeepAlive(state, component) {
      const index = state.keepAliveComponents.indexOf(component)
      index !== -1 && state.keepAliveComponents.splice(index, 1)
    }
  }
}
