/*
 * @Author: your name
 * @Date: 2021-07-07 16:52:16
 * @LastEditTime: 2021-07-07 16:56:26
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/channel/js/formRules.js
 */
const rules = {
  channelsname: [
    { required: true, message: '请输入频道名称', trigger: 'blur' }
  ],
  channelsuuid: [
    { required: true, message: '请选择直播流地址', trigger: 'change' }
  ],
  levels: [{ required: true, message: '请选择频道分级', trigger: 'change' }],
  types: [{ required: true, message: '请选择频道类型', trigger: 'change' }]
}
export default rules
