/*
 * @Author: your name
 * @Date: 2021-09-16 15:21:57
 * @LastEditTime: 2021-10-11 10:30:44
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/api/videos/index.js
 */

import { get, post, put, deleteRun } from '../http'
import to from '@/utils/to-promise'

// 视频  相关接口

// params.id为获取单个项目详情 不传为获取列表
export const GetVideos = params => {
  return to(
    new Promise((reslove, reject) => {
      get(`/videos/${params.id || ''}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 添加
export const PostVideos = params => {
  return to(
    new Promise((reslove, reject) => {
      post(`/videos/`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 修改
export const PutVideos = params => {
  return to(
    new Promise((reslove, reject) => {
      put(`/videos/${params.id || 0}?types=2`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 删除
export const DeleteVideos = params => {
  return to(
    new Promise((reslove, reject) => {
      deleteRun(`/videos/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}

// 下载文件
export const DownLoadVideo = params => {
  return to(
    new Promise((reslove, reject) => {
      get(`/videos/downloadvideo/${params.id || 0}`)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}

// 导出视频字幕文件
export const Importsrt = params => {
  return to(
    new Promise((reslove, reject) => {
      get(`/videos/importsrt/${params.id || 0}`)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}

// 翻译
export const Translatebyq = (q, l) => {
  return to(
    new Promise((reslove, reject) => {
      get(`/videos/translatebyq?q=${q}&l=${l}`)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}

// 生成视频

export const Upstatus = params => {
  return to(
    new Promise((reslove, reject) => {
      put(`/videos/upstatus/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
