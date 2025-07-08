/*
 * @Author: your name
 * @Date: 2021-10-20 11:19:06
 * @LastEditTime: 2021-11-01 16:41:32
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/store/getters.js
 */
const getters = {
  permission_routes: (state) => state.permission.routes,
  sidebar: (state) => state.app.sidebar,
  roles: (state) => state.user.roles,
  device: (state) => state.app.device,
  userInfo: (state) => state.user.userInfo,
  fileUrl: (state) => state.user.fileUrl,
  adminMenus: (state) => state.user.adminMenus,
  token: (state) => state.user.token
}
export default getters
