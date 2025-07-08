/*
 * @Author: your name
 * @Date: 2021-08-02 11:50:02
 * @LastEditTime: 2021-08-30 16:02:51
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoMaterial/materialList/js/check.js
 */
// export function checkData(data, maxs, TEXT_MS) {
//   const MIN_S = 10
//   let MAX_S = maxs / TEXT_MS
//   if (MAX_S >= 180) {
//     MAX_S = 300
//   }

//   const TEXT_MAX_MS = 3000

//   let err_msg = ''
//   if (data.title.length < 1) {
//     err_msg = '请输入标题'
//   }

//   if (data.title.length > 40) {
//     err_msg = '标题不能大于40个字'
//   }

//   const caption_tracks = data.caption_tracks
//   const tracks = data.tracks

//   let duration = 0
//   caption_tracks.forEach((item) => {
//     duration += item.duration / 1000
//   })

//   if (duration < MIN_MS) {
//     err_msg = `总时长最短不能少于 ${MIN_S} 秒`
//   }

//   if (duration > MAX_S) {
//     err_msg = `总时长最长不能超过 ${MAX_S} 秒`
//   }

//   for (let i = 0; i < caption_tracks.length; i++) {
//     const item = caption_tracks[i]
//     const img_item = tracks[i]
//     duration += item.duration / 1000

//     if (!img_item.resource_detail) {
//       err_msg = '请编辑素材'
//       break
//     }

//     if (img_item.duration < TEXT_MAX_MS) {
//       err_msg = `单个素材最短不能低于 ${TEXT_MAX_MS / 1000} 秒`
//       break
//     }

//     if (
//       'image_url' in img_item.resource_detail &&
//       !img_item.resource_detail.image_url
//     ) {
//       err_msg = '请添加素材'
//       break
//     }
//   }

//   return err_msg
// }

// 检查是否是有图片加载错误
export function checkImgListError(imgList) {
  let err_msg = ''

  for (let i = 0; i < imgList.length; i++) {
    const img_item = imgList[i]
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

// 检查 URL是否合法
// 建议的正则
export function checkoutIsURL(str) {
  let err_msg = ''
  if (!str) {
    err_msg = '请输入资讯链接'
  } else {
    /* eslint-disable no-useless-escape */
    var is_error = str.match(
      /(((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$/g
    )

    !is_error ? (err_msg = '请输入正确的 资讯链接地址哦 ^_^') : (err_msg = '')
  }
  return err_msg
}
