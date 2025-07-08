/*
 * @Author: your name
 * @Date: 2021-01-14 10:40:17
 * @LastEditTime: 2021-06-18 10:41:09
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /TM-prod-system/src/views/order_mgt/components/validate.js
 */
// var checkOrderType = (rule, value, callback) => {

// }

export default {
  channel_id: [
    {
      required: true,
      message: '请选择频道信息',
      trigger: 'change'
    }
  ],
  item_name: [
    {
      required: true,
      message: '请输入栏目名称',
      trigger: 'blur'
    }
  ]
}
