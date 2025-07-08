/*
 * @Author: your name
 * @Date: 2021-05-28 16:44:47
 * @LastEditTime: 2021-05-28 16:47:52
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/task_mgt/js/addPathRules.js
 */
const rules = {
  pathSource: [
    {
      required: true,
      message: '请输入路径来源',
      trigger: 'blur'
    }
  ],
  path: [
    {
      required: true,
      message: '请选择路径',
      trigger: 'change'
    }
  ],
  listUrl: [
    {
      required: true,
      message: '请输入列表页 URL',
      trigger: 'blur'
    }
  ],
  playUrl: [
    {
      required: true,
      message: '请输入播放页 URL',
      trigger: 'blur'
    }
  ]
}
export default rules
