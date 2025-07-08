/*
 * @Author: your name
 * @Date: 2021-03-19 11:50:09
 * @LastEditTime: 2021-03-19 11:50:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/utils/to-promise.js
 */
function to(promise) {
  return promise
    .then(data => {
      return [null, data]
    })
    .catch(err => [err])
}

export default to
