import * as setDuration from './set-duration'
const TEXT_MS = 4.5 // 一秒6个字
const MIN_MS = 10
// 素材 最少秒数  10秒 最大300秒

// 生成视频 数据整合编辑
export function setBaseCaptionTracks(edtor_text_list, edtor_img_list, subtitle_list) {
  const text_list = edtor_text_list
  const img_list = edtor_img_list
  let caption_tracks = []
  const tracks = []
  for (let i = 0; i < img_list.length; i++) {
    let resource_detail = null
    const item = img_list[i]
    const duration = item.duration
    let text_str = ''
    let subtitle = subtitle_list[0].title

    if (text_list.length > 1) {
      text_str = text_list[i].text
      subtitle = subtitle_list[i].title // 副标题 如果文本超过两行说明不是直接生成的问题本
    }
    let start_time = i * duration

    // 设置开始时间
    i < 1 ? (start_time = 0) : (start_time = caption_tracks[i - 1].start_time + caption_tracks[i - 1].duration)

    let sub_type = 0
    // 用户上传的资源
    if (item.id) {
      sub_type = 2
      resource_detail = {
        user_resource_id: item.id
      }
    } else {
      // 选择的资源
      sub_type = 1
      if (item.type === 'video') {
        const decorateList = item.decorateList
        const videoSize = item.videoSize

        resource_detail = {
          uuid: item.uuid,
          video_url: item.resource_url,
          video_origin: item.video_origin,
          video_hd_str: item.video_hd_str,
          start_ms: item.start_ms,
          end_ms: item.end_ms,
          is_retouched: !decorateList ? '否' : '是'
        }

        // 给裁剪 9：16 做兼容
        if (decorateList) {
          resource_detail.retouch_detail = {
            crop: {
              x: decorateList.x,
              y: decorateList.y,
              width: decorateList.width,
              height: decorateList.height
            },
            video_size: {
              width: videoSize.width,
              height: videoSize.height
            }
          }
        }
      }

      if (item.type === 'image') {
        resource_detail = {
          image_url: item.origin_url
        }
      }

      if (item.is_ai_image) {
        sub_type = 3
        resource_detail = {
          image_url: item.resource_url
        }
      }
    }

    const textItemTemp = {
      txt: text_str,
      start_time: start_time,
      duration: duration
    }

    const imgItemTemp = {
      resource_type: item.type,
      start_time: start_time,
      duration: duration,
      sub_type: sub_type,
      subtitle: subtitle,
      resource_detail: resource_detail
    }

    caption_tracks.push(textItemTemp)
    tracks.push(imgItemTemp)
  }

  // 给直接生成做的适配
  if (text_list.length < 2) {
    const text = text_list[0].text
    const start_time = 0

    const textItemTemp = {
      txt: text,
      start_time: start_time,
      duration: setDuration(text)
    }
    caption_tracks = [textItemTemp]
  }

  return {
    caption_tracks: caption_tracks,
    tracks: tracks
  }
}
// 链接获取，计算每个图片的时长
export function getText(text, img_length, i) {
  const one_text_length = Math.floor(text.length / img_length) // 每个多少个字
  const str_index = i * one_text_length
  let text_str = text.substr(str_index, one_text_length)
  // 最后一个
  if (i === img_length - 1) {
    text_str = text.substr(str_index, text.length)
  }
  return text_str
}
