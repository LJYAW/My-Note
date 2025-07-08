/*
 * @Author: your name
 * @Date: 2021-09-16 14:58:39
 * @LastEditTime: 2021-09-16 17:29:48
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/api/audios/index.js
 */
import { get, post, put, deleteRun } from '../http'
import to from '@/utils/to-promise'

// 替换的声音 相关接口

// params.id为获取单个项目详情 不传为获取列表
export const GetAudios = (params) => {
  return to(
    new Promise((reslove, reject) => {
      get(`/audios/${params.id || ''}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 添加
export const PostAudios = params => {
  return to(
    new Promise((reslove, reject) => {
      post(`/audios/`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 修改
export const PutAudios = (params) => {
  return to(
    new Promise((reslove, reject) => {
      put(`/audios/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 删除
export const DeleteAudios = (params) => {
  return to(
    new Promise((reslove, reject) => {
      deleteRun(`/audios/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
