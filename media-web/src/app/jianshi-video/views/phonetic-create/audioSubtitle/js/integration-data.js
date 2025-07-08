/*
 * @Author: your name
 * @Date: 2021-09-26 10:15:45
 * @LastEditTime: 2021-10-09 14:59:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/phonetic-create/audioSubtitle/js/integration-data.js
 */

export function setBaseCaptionTracks(list) {
  const caption_tracks = []; const tracks = []; let start_time = 0
  list.forEach((item, index) => {
    if (index < 1) {
      start_time = 0
    } else {
      start_time = start_time += Number(list[index - 1].duration)
    }
    caption_tracks.push({
      caption_txt_time_line: item.caption_txt_time_line,
      resource_detail: {
        file_bos_key: item.file_bos_key || ''
      },
      duration: item.duration || 0,
      resource_type: 'caption',
      start_time: start_time || 0
    })
    tracks.push({
      duration: item.duration || 0,
      resource_detail: {
        file_bos_key: item.tracks.file_key || 0,
        file_height: item.tracks.height || 0,
        file_width: item.tracks.width || 0,
        resource_material_id: item.tracks.id || ''
      },
      resource_type: item.tracks.media_type === 'video' ? 'video' : 'image' || '',
      start_time: start_time || 0
    })
  })

  return {
    caption_tracks: caption_tracks,
    tracks: tracks
  }
}
