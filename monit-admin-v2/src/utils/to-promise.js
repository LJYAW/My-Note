/*
 * @Author: your name
 * @Date: 2021-03-19 11:50:09
 * @LastEditTime: 2021-07-08 16:37:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/utils/to-promise.js
 */
function to(promise) {
  return promise
    .then((res) => {
      return { err: null, data: res }
    })
    .catch((err) => {
      return {
        err
      }
    })
}

export default to
