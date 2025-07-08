/*
 * @Author: your name
 * @Date: 2021-09-23 14:46:32
 * @LastEditTime: 2021-09-26 14:05:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/api/script_creation/index.js
 */
import { get, post, put, deleteRun } from '../http'
import to from '@/utils/to-promise'

// 快速创作 相关接口

// params.id为获取单个项目详情 不传为获取列表
export const GetScriptCreate = params => {
  return to(
    new Promise((reslove, reject) => {
      get(`/script-creation/${params.id || ''}`,
        params
      )
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 添加
export const PostScriptCreate = params => {
  return to(
    new Promise((reslove, reject) => {
      post(`/script-creation/`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 修改
export const PutScriptCreate = params => {
  return to(
    new Promise((reslove, reject) => {
      put(`/script-creation/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 删除
export const DeleteScriptCreate = params => {
  return to(
    new Promise((reslove, reject) => {
      deleteRun(`/script-creation/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 下载
export const DownScriptCreate = params => {
  return to(
    new Promise((reslove, reject) => {
      get(`/script-creation/dl-url?uuid=${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 文章分析
export const AnalysisArticleByUrl = params => {
  return to(
    new Promise((reslove, reject) => {
      post(
        `/script-creation/analysis-article-by-url`,
        params
      )
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
