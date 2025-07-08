/*
 * @Author: your name
 * @Date: 2021-04-19 12:09:43
 * @LastEditTime: 2021-04-19 14:44:05
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/utils/package.js
 */
export function nConfirm(msg) {
  msg = msg || '确认消息'
  return new Promise((resolve, reject) => {
    this.$confirm(msg + '', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then((res) => {
      resolve(res)
    }).catch((err) => {
      reject(err)
    })
  })
}
export function vLoading(msg, el) {
  return this.$loading({
    target: el,
    lock: true,
    text: msg || '',
    background: 'rgba(0, 0, 0, 0.5)'
  })
}
