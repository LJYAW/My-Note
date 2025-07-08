/*
 * @Author: your name
 * @Date: 2020-10-29 11:34:12
 * @LastEditTime: 2021-08-30 16:11:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/utils/check_video_size.js
 */

//  检测视频比列 是不是 16:9

export function checkVideoDetails(file) {
  return new Promise(function(resolve, reject) {
    if (file.type !== 'video/mp4') {
      console.log('请上传正确的文件类型')
      reject('')
    }
    // 获取上传的视频的宽高
    console.log('checkVideoSize -> file', file)

    var videoUrl = URL.createObjectURL(file)
    var videoObj = document.createElement('video')
    videoObj.src = videoUrl
    videoObj.onloadedmetadata = function(evt) {
      URL.revokeObjectURL(videoUrl)
      const num = gcd(videoObj.videoWidth, videoObj.videoHeight)
      const obj = {
        ratio: videoObj.videoWidth / num + ':' + videoObj.videoHeight / num,
        duration: videoObj.duration
      }
      resolve(obj)
    }
  })
}

export function checkVideoSize(url) {
  return new Promise(function(resolve, reject) {
    // 获取上传的视频的宽高
    var videoUrl = url
    var videoObj = document.createElement('video')
    videoObj.src = videoUrl
    videoObj.onloadedmetadata = function(evt) {
      resolve({ videoWidth: videoObj.videoWidth, videoHieght: videoObj.videoHeight })
    }
  })
}

export function getVideoDuantion(url) {
  return new Promise(function(resolve, reject) {
    // 获取上传的视频的宽高
    var videoUrl = url
    var videoObj = document.createElement('video')
    videoObj.src = videoUrl
    videoObj.onloadedmetadata = function(evt) {
      resolve(videoObj.duration)
    }
  })
}

function gcd(a, b) {
  // 防止因除数为0而崩溃
  if (a === 0 || b === 0) {
    return 1
  }

  if (a % b === 0) {
    return b
  }

  return gcd(b, a % b)
}
