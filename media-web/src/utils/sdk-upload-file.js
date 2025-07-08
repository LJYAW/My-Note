
/*
 * @Author: your name
 * @Date: 2021-09-06 16:19:31
 * @LastEditTime: 2021-10-08 16:02:52
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-masking/js/upload-file.js
 */
import BaiduSDK from '@/utils/sdk.js'
import { get } from '@/axios/http'
// 获取STS权限
const getStsToken = (type) => {
  return new Promise((resolve, reject) => {
    const params = {
      media_type: type
    }
    get('/util/bos-sts', params).then(res => {
      resolve(res)
    }).catch(err => {
      reject(err)
    })
  })
}
export default async(file, source = '', cb) => {
  const fileName = file.name
  let type = 'other'
  var picreg = /\.(jpg|jpeg|png|GIF|JPG|PNG)$/
  var videoreg = /(.*)\.(MP4|mp4)$/
  var voicereg = /(.*)\.(MP3|mpeg|mp3|MPEG|acc|wav)$/

  picreg.test(fileName) && (type = 'pic')
  videoreg.test(fileName) && (type = 'video')
  voicereg.test(fileName) && (type = 'audio')

  const { err, res } = await getStsToken(type)

  return new Promise((resolve, reject) => {
    if (err) {
      reject(err)
    } else {
      if (source)res.data.acl_path += `${source}/`
      const uploader = new BaiduSDK(res.data)

      uploader.startPartUpload(file, data => {
        resolve(data)
      })

      uploader.getLimitProgress(process => {
        typeof cb === 'function' && cb(process)
      })
    }
  })
}
