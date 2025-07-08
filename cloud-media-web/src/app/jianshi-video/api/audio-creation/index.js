/*
 * @Author: your name
 * @Date: 2021-09-26 18:19:34
 * @LastEditTime: 2021-10-08 17:20:13
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/api/audio-creation/index.js
 */
import { get, post, put, deleteRun } from '../http'
import to from '@/utils/to-promise'

// 音频创作 相关接口

// params.id为获取单个项目详情 不传为获取列表
export const GetAudioCreation = params => {
  return to(
    new Promise((reslove, reject) => {
      get(`/audio-creation/${params.id || ''}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 添加
export const PostAudioCreation = params => {
  return to(
    new Promise((reslove, reject) => {
      post(`/audio-creation/`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 修改
export const PutAudioCreation = params => {
  return to(
    new Promise((reslove, reject) => {
      put(`/audio-creation/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 删除
export const DeleteAudioCreation = params => {
  return to(
    new Promise((reslove, reject) => {
      deleteRun(`/audio-creation/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 下载
export const DownAudioCreation = params => {
  return to(
    new Promise((reslove, reject) => {
      get(`/script-creation/dl-url?uuid=${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 语音创作下载
export const DownPhonetiCreate = params => {
  return to(
    new Promise((reslove, reject) => {
      get(`/audio-creation/dl-url?id=${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}

// 创建任务
export const PostAudioCreationTask = params => {
  return to(
    new Promise((reslove, reject) => {
      post(`/audio-creation/create-audio-creation-task`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}

// 上传文件

export const PostAudio = params => {
  return to(
    new Promise((reslove, reject) => {
      post(`/audio-creation/analysis-audio`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
