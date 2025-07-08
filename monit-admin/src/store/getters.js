/*
 * @Author: your name
 * @Date: 2021-07-02 16:37:54
 * @LastEditTime: 2021-07-16 16:25:08
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/store/getters.js
 */
const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  userInfo: state => state.user.userInfo,
  channelData: state => state.channel.channelData,
  channelCount: state => state.channel.channelCount,
  allChannelData: state => state.channel.allChannelData,
  channelLevel: state => state.channel.channelLevel,
  channelType: state => state.channel.channelType
}
export default getters
