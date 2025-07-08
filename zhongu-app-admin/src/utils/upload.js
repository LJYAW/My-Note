/*
 * @Author: your name
 * @Date: 2021-10-27 11:54:05
 * @LastEditTime: 2021-10-27 18:39:37
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/utils/upload.js
 */
import { post } from '@/axios/http'
export const Upload = file => {
  const formData = new FormData()
  formData.append('photo', file)
  return new Promise((reslove, reject) => {
    post(`/admin/util/upload-img`, formData)
      .then(res => {
        reslove(res)
      })
      .catch(err => reject(err))
  })
}
