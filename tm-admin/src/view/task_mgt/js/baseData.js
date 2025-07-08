/*
 * @Author: your name
 * @Date: 2021-05-27 17:41:27
 * @LastEditTime: 2021-07-05 16:17:06
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/task_mgt/js/baseData.js
 */
const baseData = {
  // 状态
  statusData: { 0: '待绑定', '-1': '待解析', '-2': '待解析开发', '-3': '无法解析', 1: '待爬取', 2: '爬取中', 3: '爬取完成', '-4': '取消爬取', '-5': '解析故障' },
  // 资源类型
  typeData: ['整档', '单集', '短视频'],
  // 业务
  workData: ['KG', 'Feed', '其它'],
  // 来源
  sourceData: ['自动', '人工']
}
export default baseData
