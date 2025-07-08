/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-04-26 11:20:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /TM-prod-system/src/store/getters.js
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
  CustomClassList: state => state.channel.CustomClassList,
  CusClassData: state => state.channel.CusClassData,
  keepAliveComponents: state => state.keepAlive.keepAliveComponents
}
export default getters
