/*
 * @Author: your name
 * @Date: 2021-09-16 15:27:57
 * @LastEditTime: 2021-09-16 17:30:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/api/video_exts/index.js
 */
import { get, post, put, deleteRun } from '../http'
import to from '@/utils/to-promise'

// 视频扩展 相关接口

// params.id为获取单个项目详情 不传为获取列表
export const GetExts = params => {
  return to(
    new Promise((reslove, reject) => {
      get(`/video_exts/${params.id || ''}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 添加
export const PostExts = params => {
  return to(
    new Promise((reslove, reject) => {
      post(`/video_exts/`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 修改
export const PutExts = params => {
  return to(
    new Promise((reslove, reject) => {
      put(`/video_exts/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 删除
export const DeleteExts = params => {
  return to(
    new Promise((reslove, reject) => {
      deleteRun(`/video_exts/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
