/*
 * @Author: your name
 * @Date: 2021-09-16 17:50:29
 * @LastEditTime: 2021-09-18 16:49:08
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/utils/download.js
 */
import axios from '../api/index'
export function downloadJs(url, fileName, params) {
  var token = ''
  if (localStorage.getItem('token')) {
    token = localStorage.getItem('token')
  }
  axios({
    method: 'put',
    url: url,
    data: params,
    headers: {
      token: token
    },
    responseType: 'blob'
  }).then(res => {
    var blob = res.data
    var reader = new FileReader()
    reader.readAsDataURL(blob)
    reader.onload = function(e) {
      var a = document.createElement('a')
      a.download = (fileName || '字幕') + '.srt'
      a.href = e.target.result
      document.body.appendChild(a)
      a.click()
      document.body.removeChild(a)
    }
  })
}
export function downloadVideo(url, fileName) {
  var token = ''
  if (localStorage.getItem('token')) {
    token = localStorage.getItem('token')
  }
  axios({
    method: 'get',
    url: url,
    headers: {
      token: token
    },
    responseType: 'blob'
  }).then(res => {
    var blob = res.data
    var reader = new FileReader()
    reader.readAsDataURL(blob)
    reader.onload = function(e) {
      var a = document.createElement('a')
      a.download = (fileName || '视频') + '.mp4'
      a.href = e.target.result
      document.body.appendChild(a)
      a.click()
      document.body.removeChild(a)
    }
  })
}
