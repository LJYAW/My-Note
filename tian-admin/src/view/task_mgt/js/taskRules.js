/*
 * @Author: your name
 * @Date: 2021-05-28 15:34:11
 * @LastEditTime: 2021-06-17 17:41:59
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/task_mgt/js/taskRules.js
 */
const rules = {
  resourcetype: [
    {
      required: true,
      message: '请选择资源类型',
      trigger: 'change'
    }
  ],
  channel_id: [
    {
      required: true,
      message: '请选择频道名称',
      trigger: 'change'
    }
  ],
  program_id: [
    {
      required: true,
      message: '请选择栏目名称',
      trigger: 'change'
    }
  ],
  business: [
    {
      required: true,
      message: '请选择业务',
      trigger: 'change'
    }
  ],
  otherWork: [
    {
      required: true,
      message: '请输入其他业务',
      trigger: 'blur'
    }
  ],
  lastdate: [
    {
      required: true,
      message: '请选择现存视频日期',
      trigger: 'blur'
    }
  ]
}
export default rules
