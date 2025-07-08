/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-06-07 18:43:11
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
  teamData: state => state.user.teamData,
  channelData: state => state.channel.channelData,
  itemData: state => state.channel.itemData,
  categoryData: state => state.channel.categoryData,
  roles: state => state.user.roles,
  permission_routes: state => state.permission.routes,
  limitData: state => state.user.limitData
}
export default getters
