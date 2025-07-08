/*
 * @Author: your name
 * @Date: 2021-06-02 14:49:48
 * @LastEditTime: 2021-06-02 14:50:09
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/task_mgt/js/setQueryParams.js
 */
export default function(query) {
  let params = ''
  for (const key in query) {
    query[key] || query[key] === '0' ? (params += `${key}:${query[key]},`) : ''
  }
  return params.replace(/,$/gi, '')
}
