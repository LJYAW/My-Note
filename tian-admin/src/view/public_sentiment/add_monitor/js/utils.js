/*
 * @Author: your name
 * @Date: 2021-04-16 15:28:38
 * @LastEditTime: 2021-04-29 11:51:25
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/public_sentiment/add_monitor/js/utils.js
 */
import { get, post, put, deleteRun } from '@/axios/http.js'

// 校验是否监测 logo
export function checkTableData(arrAll, arrSome) {
  const newArr = []
  get('/api/topics', { limit: 10000 }).then((data) => {
    const { res } = data
    res.data.forEach((ele) => {
      arrAll.forEach((item) => {
        if (ele.contents === item) {
          newArr.push({
            name: ele.company,
            personage: ele.contents,
            status: arrSome.includes(item) || ''
          })
        }
      })
    })
  })

  return newArr
}

export function toArray(str) {
  return str ? str.split(',') : []
}

export function toString(array) {
  return array.join()
}
