/*
 * @Author: your name
 * @Date: 2021-09-23 21:05:39
 * @LastEditTime: 2021-09-24 11:09:50
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/utils/batch-upload.js
 */
import sdkUploadFile from '@/utils/sdk-upload-file.js'
import {
  getBase64ToImg,
  dataURLtoFile
} from '@/utils/file.js'

/**
 * @param imgList []
 */

export const batchUploadImg = async(imgList, cb) => {
  for (let i = 0; i < imgList.length; i++) {
    const imgUrl = imgList[i].resource_url
    const imgName = new Date().getTime() + '.jpg'
    const src = await getBase64ToImg(imgUrl)
    const imgFile = await dataURLtoFile(src, imgName)
    const imgData = await sdkUploadFile(imgFile)
    const index = imgList[i].index
    typeof cb === 'function' && cb(imgData, index)
  }
}
