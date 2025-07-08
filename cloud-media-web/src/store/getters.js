/*
 * @Author: your name
 * @Date: 2021-02-22 19:58:59
 * @LastEditTime: 2021-09-22 16:15:15
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /opinion-monit /src/store/getters.js
 */
const getters = {
  userInfo: state => state.user.userInfo,
  roles: state => state.user.roles,
  addRoutes: state => state.permission.addRoutes,
  permission_routes: state => state.permission.routes,
  directoryData: state => state.directoryData.directoryData,
  userDirectData: state => state.directoryData.userDirectData,
  userRoleData: state => state.directoryData.userRoleData,
  userList: state => state.directoryData.userList,
  companyList: state => state.directoryData.companyList,
  companyInfo: state => state.company.companyInfo,
  showHeader: state => state.settings.showHeader
}
export default getters
