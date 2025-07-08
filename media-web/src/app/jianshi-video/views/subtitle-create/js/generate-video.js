/*
 * @Author: your name
 * @Date: 2021-09-17 18:47:48
 * @LastEditTime: 2021-10-13 16:43:42
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/subtitleCreation/js/generate-video.js
 */
import store from '@/app/jianshi-video/store/index'
export default function() {
  const {
    subtitles_ext,
    subtitles_ext_en,
    titles_ext,
    bg_zm_ext,
    videoSize
  } = setDataListPosition()
  const obj = store.state
  const { update_audio_id, bg_musice, bg, bg_zm_id, logo, video_title_id, video_tail_id } = obj.submitData

  const params = {
    id: obj.subTitleSingle.id,
    titles: obj.title,
    scale: obj.target_ratio === '16:9' ? 1 : 2,
    titles_flag: obj.showTitle,
    logos_id: logo.id || 0,
    heads_id: video_title_id.id || 0,
    ends_id: video_tail_id.id || 0,
    save_subtitles: JSON.stringify(obj.subTilteList),
    update_audio_id: obj.timbreSwitch ? update_audio_id.id : -1,
    bg_musice: bg_musice.id || 0,
    bg: bg.id || 0,
    bg_zm_id: bg_zm_id.id || 0,
    subtitles_ext: obj.language === 1 ? subtitles_ext_en : subtitles_ext,
    subtitles_ext_en: obj.language === 1 ? subtitles_ext : subtitles_ext_en,
    titles_ext,
    bg_zm_ext,
    video_width: videoSize.videoWidth,
    video_height: videoSize.videoHeight
  }
  console.log(
    '🚀 ~ file: generate-video.js ~ line 43 ~ function ~ params',
    params
  )
  return params
}

function setDataListPosition() {
  const { target_ratio, contentListTemp } = store.state
  const videoSize = setVideoSize(target_ratio)

  // 字幕的 坐标
  const subtitleCn = contentListTemp.find(item => item.type === 'text-cn')
  const subtitles_ext = setSubtitleBgRatioSize(subtitleCn, videoSize)

  // 英文的 坐标
  const subtitleEn = contentListTemp.find(item => item.type === 'text-en')
  const subtitles_ext_en = setSubtitleBgRatioSize(subtitleEn, videoSize)

  // 标题的坐标
  const videoTitle = contentListTemp.find(item => item.type === 'videoTitle')
  const titles_ext = setSubtitleBgRatioSize(videoTitle, videoSize)

  // 字幕背景的坐标
  const subtitleBgImage = contentListTemp.find(item => item.type === 'bgImg')
  const bg_zm_ext = setSubtitleBgRatioSize(subtitleBgImage, videoSize)

  return {
    subtitles_ext,
    subtitles_ext_en,
    titles_ext,
    bg_zm_ext,
    videoSize
  }
}

function setRatioSize(data, cupSize) {
  if (data) {
    const { x, y, w, h } = data
    const { videoHeight, videoWidth, canvasWidth, canvasHeight } = cupSize

    const cupW = Math.floor((w / canvasWidth) * videoWidth)
    const cupH = Math.floor((h / canvasHeight) * videoHeight)
    const cupX = Math.floor((x / canvasWidth) * videoWidth)
    const cupY = Math.floor(
      ((canvasHeight - y - h) / canvasHeight) * videoHeight
    )

    return cupX + ':' + cupY + ':' + cupW + ':' + cupH
  }
}
// 设置坐标
function setSubtitleBgRatioSize(data, cupSize) {
  if (data) {
    const { x, y, w, h } = data
    const { videoHeight, videoWidth, canvasWidth, canvasHeight } = cupSize
    const cupW = Math.floor((w / canvasWidth) * videoWidth)
    const cupH = Math.floor((h / canvasHeight) * videoHeight)
    const cupX = Math.floor((x / canvasWidth) * videoWidth)
    const cupY = Math.floor((y / canvasHeight) * videoHeight)

    return cupX + ':' + cupY + ':' + cupW + ':' + cupH
  }
}
function setVideoSize(ratio) {
  let videoHeight = 0
  let videoWidth = 0
  let canvasWidth = 0
  let canvasHeight = 0

  switch (ratio) {
    case '16:9':
      videoWidth = 1920
      videoHeight = 1080

      canvasWidth = 610
      canvasHeight = 344
      break
    case '9:16':
      videoHeight = 1280
      videoWidth = 720

      canvasHeight = 474
      canvasWidth = 266
      break
    default:
      videoWidth = 1920
      videoHeight = 1080

      canvasWidth = 610
      canvasHeight = 344
      break
  }

  return {
    videoHeight,
    videoWidth,
    canvasWidth,
    canvasHeight
  }
}

