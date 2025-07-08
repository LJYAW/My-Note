/* eslint-disable prefer-const */
/*
 * @Author: your name
 * @Date: 2021-03-16 10:55:43
 * @LastEditTime: 2021-03-18 11:29:09
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/js/video-preview.js
 */
import SetIndexDB from '@/utils/indexDB'

function canvasImgSrc(videoDom, scale = { w: 1, h: 1 }) {
  return new Promise((resolve, reject) => {
    let canvas = document.createElement('canvas')
    let dom = videoDom

    canvas.width = dom.videoWidth * scale.w
    canvas.height = dom.videoHeight * scale.h

    canvas.getContext('2d').drawImage(dom, 0, 0, canvas.width, canvas.height)
    let dataUrl = canvas.toDataURL('image/png')
    if (!dataUrl) {
      reject('canvas转换图片失败')
    } else {
      canvas = null
      dom = null
      resolve(dataUrl)
    }
  })
}

function setCanvasImg(videoUrl, ms) {
  return new Promise((resolve, reject) => {
    let video = document.createElement('video')
    video.src = videoUrl
    video.crossOrigin = 'anonymous'
    video.currentTime = ms || 0.1

    video.addEventListener('canplay', e => {
      // 待优化
      // const [err, imgUrl] = await to(canvasImgSrc(video))
      // if (err) {
      //   reject('出错啦')
      // } else {
      //   resolve(imgUrl)
      // }

      resolve(video)
    })

    video.addEventListener('error', function(e) {
      reject('出错了')
    })
  })
}

async function asyncVideoImgTask(videoUrl, duration) {
  console.log('🚀 ~ file: video-preview.js ~ line 58 ~ asyncVideoImgTask ~ videoUrl', videoUrl)
  const baseMs = 1000
  const length = Math.ceil((duration * 1000) / baseMs)
  const arr = []
  let index = 0

  // let AlreadyDataLegnth = await SetIndexDB.keys()
  // if (AlreadyDataLegnth) {
  //   index = AlreadyDataLegnth.length
  // }
  console.log(
    '🚀 ~ file: video-preview.js ~ line 67 ~ asyncVideoImgTask ~ index',
    index
  )

  // 回调函数
  console.time()
  for (let i = index; i < length; i++) {
    console.log('🚀 ~ file: video-preview.js ~ line 71 ~ asyncVideoImgTask ~ index', index)
    const ms = (baseMs * i) / 1000
    let err, videoDom, imgUrl;

    [err, videoDom] = await to(setCanvasImg(videoUrl, ms))
    if (err) {
      asyncVideoImgTask(videoUrl, duration)
      throw new Error('视频加载出错')
    }

    [err, imgUrl] = await to(canvasImgSrc(videoDom))
    if (err) {
      asyncVideoImgTask(videoUrl, duration)
      throw new Error('canvas 绘制出错')
    }

    SetIndexDB.setItem(i, {
      currentTime: ms,
      imgUrl: imgUrl
    })

    imgUrl = null
  }
  console.timeEnd()
}

function to(promise) {
  return promise
    .then(data => {
      return [null, data]
    })
    .catch(err => [err])
}

export default asyncVideoImgTask
