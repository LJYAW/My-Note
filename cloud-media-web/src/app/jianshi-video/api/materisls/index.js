/*
 * @Author: your name
 * @Date: 2021-09-16 15:15:45
 * @LastEditTime: 2021-09-16 17:31:25
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/api/materisls/index.js
 */
import { get, post, put, deleteRun } from '../http'
import to from '@/utils/to-promise'

// 素材管理相关接口

// params.id为获取单个项目详情 不传为获取列表
export const GetMaterials = params => {
  return to(
    new Promise((reslove, reject) => {
      get(`/materials/${params.id || ''}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
// 添加
export const PostMaterials = params => {
  return to(
    new Promise((reslove, reject) => {
      post(`/materials/`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}

// 修改
export const PutMaterials = params => {
  return to(
    new Promise((reslove, reject) => {
      put(`/materials/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}

// 删除单项
export const DeleteMaterials = params => {
  return to(
    new Promise((reslove, reject) => {
      deleteRun(`/materials/${params.id || 0}`, params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
