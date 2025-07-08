/*
 * @Author: your name
 * @Date: 2021-09-23 18:16:48
 * @LastEditTime: 2021-09-24 11:32:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/api/source-material/index.js
 */
import { post, get } from '@/axios/http.js'
import to from '@/utils/to-promise'

/**
 *
 *   @param source  个人 or 系统 非必传 上传的时候需要用
 *   @param type  播放音色 背景音乐 字幕背景 视频模板 虚拟主持人 角标 片头 片尾
 *   @param subtype 只有 系统的背景音乐 新闻 振奋 甜美 舒缓 浪漫 欢快 悲伤  视频模板 横屏 竖屏
 *
 */

export const getMaterial = params => {
  return to(
    new Promise((reslove, reject) => {
      get('/materials/', params)
        .then(res => {
          reslove(res.res)
        })
        .catch(err => reject(err))
    })
  )
}

export const posttMaterial = params => {
  return to(
    new Promise((reslove, reject) => {
      post('/materials/', params)
        .then(res => reslove(res))
        .catch(err => reject(err))
    })
  )
}
