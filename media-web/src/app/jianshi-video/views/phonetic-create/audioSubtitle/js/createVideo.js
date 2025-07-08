/*
 * @Author: your name
 * @Date: 2021-09-26 19:24:37
 * @LastEditTime: 2021-10-14 16:33:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/phonetic-create/audioSubtitle/js/createVideo.js
 */
import store from '@/app/jianshi-video/store/index'

export function cheackFun() {
  const { caption_tracks, tracks } = store.state
  for (let i = 0; i < caption_tracks.length; i++) {
    if (caption_tracks[i].caption_txt_time_line.length <= 0) return false
  }
  for (let j = 0; j < tracks.length; j++) {
    if (!tracks[j].resource_detail.resource_material_id) return false
  }
  return true
}

export function createVideoData() {
  const { submitData, caption_tracks, tracks, phoneticTitle, phoneticVideoSale, subtitlePosition, hostPosition } = store.state
  const { bg_musice, caption_bg_id, logo, video_title_id, video_tail_id, virtual_presenter_obj } = submitData
  const params = {
    caption_tracks: caption_tracks, // 字幕
    tracks: tracks, // 素材
    title: phoneticTitle, // 标题
    target_ratio: phoneticVideoSale, // 视频比例
    // 视频效果选项
    custome_config: {
      video_begin: Object.keys(video_title_id).length !== 0 ? { // 片头
        duration: video_title_id.duration,
        file_height: video_title_id.height,
        file_width: video_title_id.width,
        video_begin_bos_key: video_title_id.file_key,
        video_begin_material_id: video_title_id.id,
        video_begin_type: 1
      } : { video_begin_type: 0 },
      video_end: Object.keys(video_tail_id).length !== 0 ? { // 片尾
        duration: video_tail_id.duration,
        file_height: video_tail_id.height,
        file_width: video_tail_id.width,
        video_end_bos_key: video_tail_id.file_key,
        video_end_material_id: video_tail_id.id,
        video_end_type: 1
      } : { video_end_type: 0 },
      video_logo: Object.keys(logo).length !== 0 ? { // 角标
        file_height: logo.height,
        file_width: logo.width,
        video_logo_bos_key: logo.file_key,
        video_logo_material_id: logo.id,
        video_logo_type: 1
      } : { video_logo_type: 0 }
    }
  }
  if (phoneticVideoSale === '9:16') {
    params.custome_config.subtitle = {
      margin_bottom: subtitlePosition.margin_bottom
    }
  }
  Object.keys(bg_musice).length > 0 ? params.custome_config.bg_music = { // 背景音乐
    bg_music_bos_key: bg_musice.file_key,
    bg_music_material_id: bg_musice.id
  } : ''
  Object.keys(caption_bg_id).length > 0 ? params.custome_config.caption_bg = { // 视频背景
    caption_bg_bos_key: caption_bg_id.file_key,
    caption_bg_material_id: caption_bg_id.id,
    file_height: caption_bg_id.height,
    file_width: caption_bg_id.width
  } : ''
  if (virtual_presenter_obj && phoneticVideoSale === '16:9') {
    const ext = JSON.parse(virtual_presenter_obj.ext)
    params.custome_config.virtual_presenter = { // 主持人
      duration: virtual_presenter_obj.duration,
      file_height: virtual_presenter_obj.height,
      file_width: virtual_presenter_obj.width,
      position: {
        pos_x: ext.position[hostPosition].x,
        pos_y: ext.position[hostPosition].y
      },
      virtual_presenter_bos_key: virtual_presenter_obj.file_key,
      virtual_presenter_material_id: virtual_presenter_obj.id
    }
  }
  return params
}
