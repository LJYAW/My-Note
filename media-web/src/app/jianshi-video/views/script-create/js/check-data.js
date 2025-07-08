/*
 * @Author: your name
 * @Date: 2021-09-23 19:02:35
 * @LastEditTime: 2021-10-22 19:14:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/script-create/js/check-data.js
 */
const TEXT_MS = 4.5 // 一秒6个字
const MIN_MS = 10
import store from '@/app/jianshi-video/store/index'
export function checkData() {
  const {
    title,
    img_list,
    max_size,
    estimate_duration,
    text_list
  } = store.state
  let MAX_S = max_size / TEXT_MS
  if (MAX_S >= 180) {
    MAX_S = 300
  }

  const TEXT_MAX_MS = 3000
  let err_msg = ''
  if (!title) {
    err_msg = '请输入标题'
  }

  if (title.length > 40) {
    err_msg = '标题不能大于40个字'
  }

  if (estimate_duration < MIN_MS) {
    err_msg = `总时长最短不能少于 ${MIN_MS} 秒`
  }

  if (estimate_duration > MAX_S) {
    err_msg = `总时长最长不能超过 ${MAX_S} 秒`
  }
  for (let i = 0; i < img_list.length; i++) {
    const img_item = img_list[i]
    if (!img_item.duration) {
      err_msg = `单个文本素材不能为空`
      break
    }
  }
  // for (let i = 0; i < img_list.length; i++) {
  //   const img_item = img_list[i]
  //   if (img_item.duration < TEXT_MAX_MS) {
  //     err_msg = `单个素材最短不能低于 ${TEXT_MAX_MS / 1000} 秒`
  //     break
  //   }
  // }
  for (let i = 0; i < text_list.length; i++) {
    const text_item = text_list[i]
    if (text_item.text.length > 280) {
      err_msg = '单个素材不能超过280个汉字'
      break
    }
  }

  return err_msg
}
export function checkImgListError() {
  const { img_list } = store.state
  let err_msg = ''
  for (let i = 0; i < img_list.length; i++) {
    const img_item = img_list[i]
    if (img_item.imgError) {
      err_msg = '素材列表中有未获取成功的资源，请重新编辑后再提交'
      break
    }
    if (!img_item.resource_url) {
      err_msg = '请添加素材'
      break
    }
  }
  return err_msg
}
