/*
 * @Author: zzx
 * @Date: 2020-07-22 20:26:53
 * @LastEditTime: 2020-10-28 10:16:33
 * @FilePath: /zhijian_web/src/views/intellect_create/js/reset_edti.js
 */
export function setRedetDate(data) {
  let tracks = data.tracks
  let caption_tracks = data.caption_tracks

  let tracks_temp = []
  let subTitle = []
  tracks.forEach(item => {
    let img_obj = {}
    subTitle.push({
      show: false,
      title: item.subtitle
    })
    if (item.resource_type == 'video') {
      if (item.sub_type == 2) {
        img_obj = {
          duration: item.duration,
          id: item.resource_detail.user_resource_id,
          resource_url: item.resource_detail.resource_url,
          type: item.resource_type
        }
      } else {
        img_obj = {
          duration: item.duration,
          end_ms: item.resource_detail.end_ms,
          resource_url: item.resource_detail.video_url,
          start_ms: item.resource_detail.start_ms,
          type: item.resource_type,
          uuid: item.resource_detail.uuid,
          video_hd_str: item.resource_detail.video_hd_str,
          video_origin: item.resource_detail.video_origin
        }
      }
    } else if (item.resource_type == 'image') {
      if (item.sub_type == 2) {
        img_obj = {
          duration: item.duration,
          id: item.resource_detail.user_resource_id,
          origin_url: '',
          resource_url: item.resource_detail.resource_url,
          type: item.resource_type
        }
      } else {
        img_obj = Object.assign(
          {
            origin_url: item.resource_detail.image_url,
            resource_url: item.resource_detail.proxy_url,
            type: item.resource_type
          },
          item
        )
      }

      // 新增 ai 识别图片
      if (item.sub_type == 3) {
        img_obj = Object.assign(
          {
            origin_url: item.resource_detail.image_url,
            resource_url: item.resource_detail.image_url,
            is_ai_image: true,
            type: item.resource_type
          },
          item
        )
      }
    }

    tracks_temp.push(img_obj)
  })

  let caption_tracks_temp = []
  caption_tracks.forEach(item => {
    let text_obj = {
      duration: item.duration,
      text: item.txt
    }
    caption_tracks_temp.push(text_obj)
  })

  let edtor_img_list = tracks_temp
  let edtor_text_list = caption_tracks_temp

  return {
    edtor_img_list,
    edtor_text_list,
    subTitle
  }
}
