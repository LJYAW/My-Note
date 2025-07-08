/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-07-20 18:51:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tvm-prod-system/src/store/getters.js
 */
const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,

  // user
  token: state => state.user.token,
  userInfo: state => state.user.userInfo,
  role: state => state.user.roles,
  permission_routes: state => state.permission.routes
}
export default getters
