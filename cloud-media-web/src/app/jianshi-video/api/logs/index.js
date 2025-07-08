/*
 * @Author: your name
 * @Date: 2021-09-16 15:19:56
 * @LastEditTime: 2021-09-16 17:32:15
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/api/logs/index.js
 */
import { get, post, put, deleteRun } from '../http'
import to from '@/utils/to-promise'

// 操作记录 相关接口

// params.id为获取单个项目详情 不传为获取列表
export const GetLogs = params => {
  return to(
    new Promise((reslove, reject) => {
      get(`/logs/${params.id || ''}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 添加
export const PostLogs = params => {
  return to(
    new Promise((reslove, reject) => {
      post(`/logs/`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 修改
export const PutLogs = params => {
  return to(
    new Promise((reslove, reject) => {
      put(`/logs/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 删除
export const DeleteLogs = params => {
  return to(
    new Promise((reslove, reject) => {
      deleteRun(`/logs/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
