const TEXT_MS = 4.5 // 一秒6个字
const MIN_MS = 10
// 素材 最少秒数  10秒 最大300秒

// 生成视频 数据整合编辑
export function setBaseCaptionTracks(edtor_text_list, edtor_img_list, subtitle_list) {
  let text_list = edtor_text_list
  let img_list = edtor_img_list

  let caption_tracks = []
  let tracks = []

  for (let i = 0; i < img_list.length; i++) {
    let resource_detail = null
    let item = img_list[i]
    let duration = item.duration
    let text_str = ''
    let subtitle = subtitle_list[0].text

    if (text_list.length > 1) {
      text_str = text_list[i].text
      subtitle = subtitle_list[i].text // 副标题 如果文本超过两行说明不是直接生成的问题本
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
      if (item.type == 'video') {
        console.log(item)

        resource_detail = {
          uuid: item.uuid,
          video_url: item.resource_url,
          video_origin: item.video_origin,
          video_hd_str: item.video_hd_str,
          start_ms: item.start_ms,
          end_ms: item.end_ms
        }
      }

      if (item.type == 'image') {
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

    let textItemTemp = {
      txt: text_str,
      start_time: start_time,
      duration: duration
    }

    let imgItemTemp = {
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
    let text = text_list[0].text
    let duration = text_list[0].duration
    let start_time = 0

    let textItemTemp = {
      txt: text,
      start_time: start_time,
      duration: getMs(text)
    }
    caption_tracks = [textItemTemp]
  }

  return {
    caption_tracks: caption_tracks,
    tracks: tracks
  }
}

// 将传入的文本 根据换行分成上下两个文本
export function textToSlice(text, index) {
  let sReg = /\n/g
  let text_temp = text
  let match_index = sReg.exec(text_temp).index

  let pre_str = text_temp.slice(0, match_index).replace(/\n/g, '')
  let naxt_str = text_temp.slice(match_index, text.length).replace(/\n/g, '')

  return {
    pre_str: pre_str,
    naxt_str: naxt_str
  }
}

// 当只有一条数据时 且是只能生成的
/**
 *
 * @param {*} text 只有一条文本
 * @param {*} img_length 获取的img 长度
 * @param {*} i 当前索引 用来截取字符串
 */
export function getText(text, img_length, i) {
  let one_text_length = Math.floor(text.length / img_length) // 每个多少个字
  let str_index = i * one_text_length
  let text_str = text.substr(str_index, one_text_length)
  // 最后一个
  if (i == img_length - 1) {
    text_str = text.substr(str_index, text.length)
  }
  return text_str
}

export function getMs(text) {
  let code = '#pau#'
  let code_ms = 500
  let sum = text.split(code).length - 1

  let length = text.length - code.length * sum
  return Math.ceil((length / TEXT_MS) * 1000) + code_ms * sum
}

export function getDuration(arr) {
  let duration = 0
  arr.forEach(item => {
    duration += getMs(item.text)
  })
  return duration / 1000
}

export function checkData(data, maxs, TEXT_MS) {
  const MIN_S = 10
  let MAX_S = maxs / TEXT_MS
  if (MAX_S >= 180) {
    MAX_S = 300
  }

  const TEXT_MAX_MS = 3000

  let err_msg = ''
  if (data.title.length < 1) {
    err_msg = '请输入标题'
  }

  if (data.title.length > 40) {
    err_msg = '标题不能大于40个字'
  }

  let caption_tracks = data.caption_tracks
  let tracks = data.tracks

  let duration = 0
  caption_tracks.forEach(item => {
    duration += item.duration / 1000
  })

  if (duration < MIN_MS) {
    err_msg = `总时长最短不能少于 ${MIN_S} 秒`
  }

  if (duration > MAX_S) {
    err_msg = `总时长最长不能超过 ${MAX_S} 秒`
  }

  for (let i = 0; i < caption_tracks.length; i++) {
    let item = caption_tracks[i]
    let img_item = tracks[i]
    duration += item.duration / 1000

    if (!img_item.resource_detail) {
      err_msg = '请编辑素材'
      break
    }

    if (img_item.duration < TEXT_MAX_MS) {
      err_msg = `单个素材最短不能低于 ${TEXT_MAX_MS / 1000} 秒`
      break
    }

    if ('image_url' in img_item.resource_detail && !img_item.resource_detail.image_url) {
      err_msg = '请添加素材'
      break
    }
  }

  return err_msg
}

export function setTextDom() {
  var textDom = document.querySelector('.el-textarea__inner')

  if (textDom) {
    textDom.style.minHeight = '100px'
  }
}

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

// 指定位置插入 字符串
export function insertAtCursor(el, insertValue) {
  if (el.selectionStart || el.selectionStart == '0') {
    // 获取光标目前选中的开始位置和结束位置
    var startPos = el.selectionStart
    var endPos = el.selectionEnd

    // 插入内容
    el.value = el.value.substring(0, startPos) + insertValue + el.value.substring(endPos, el.value.length)

    // 恢复焦点
    el.focus()

    // 恢复光标位置
    el.selectionStart = startPos + insertValue.length
    el.selectionEnd = startPos + insertValue.length
  } else {
    el.value += insertValue
    el.focus()
  }
}
