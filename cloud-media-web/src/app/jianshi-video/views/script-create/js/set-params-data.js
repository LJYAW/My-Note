/*
 * @Author: your name
 * @Date: 2021-09-23 18:31:40
 * @LastEditTime: 2021-10-22 17:07:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/script-create/js/set-params-data.js
 */
import store from '@/app/jianshi-video/store/index'
export default function(imgKeyList) {
  const {
    submitData,
    target_ratio,
    effectData,
    title,
    estimate_duration
  } = store.state
  const params = {
    bg_music_key: submitData.bg_musice.file_key || '',
    duration: Math.floor(estimate_duration * 1000),
    logo_key: submitData.logo.file_key || '',
    target_ratio,
    template_key: submitData.caption_bg_id
      ? submitData.caption_bg_id.file_key
      : '',
    title,
    tts_per_key: submitData.update_audio_id
      ? submitData.update_audio_id.file_key
      : '',
    video_begin_key: submitData.video_title_id
      ? submitData.video_title_id.file_key
      : '',
    video_end_key:
      submitData.video_tail_id ? submitData.video_tail_id.file_key : '',
    virtual_presenter_detail: submitData.virtual_presenter_obj ? setVirtualPresenterDetail(submitData) : null,
    ocr_bottom:
      target_ratio === '9:16' ? effectData.subtitle.margin_bottom : null,
    sections: setSection(imgKeyList)
  }
  return params
}
function setSection(imgKeyList) {
  const { img_list, text_list, estimate_duration, subTitle_list } = store.state
  let data = []
  if (text_list.length === 1) {
    data = [
      {
        content: text_list[0].text,
        duration: Math.floor(estimate_duration * 1000),
        start_time: 0,
        sub_title: subTitle_list[0].title || '',
        resource: getImgList(img_list, imgKeyList)
      }
    ]
  } else {
    text_list.forEach((item, index) => {
      data.push({
        content: item.text,
        duration: Math.floor(img_list[index].duration),
        resource: [getImgList(img_list, imgKeyList)[index]],
        sub_title: subTitle_list[index].title || '',
        start_time:
          index < 1 ? 0 : data[index - 1].start_time + data[index - 1].duration
      })
    })
  }
  return data
}
function getImgList(imgList, imgKeyList) {
  const arr = []
  imgList.forEach((item, index) => {
    const imgKeyIndex = imgKeyList.findIndex(
      ketItem => ketItem.index === index
    )
    arr.push({
      duration: item.duration,
      file_key: imgKeyIndex !== -1 ? imgKeyList[imgKeyIndex].key : item.file_key,
      //   file_url: item.resource_url,
      start_time:
        index < 1 ? 0 : arr[index - 1].start_time + arr[index - 1].duration,
      type: item.type
    })
  })
  return arr
}
function setVirtualPresenterDetail(submitData) {
  let position = ''
  let virtual_presenter_key = ''
  if (submitData.virtual_presenter_obj) {
    position = Object.keys(JSON.parse(submitData.virtual_presenter_obj.ext).position).join(',')
    virtual_presenter_key = submitData.virtual_presenter_obj.file_key
  }
  return {
    position: position,
    virtual_presenter_key: virtual_presenter_key
  }
}
