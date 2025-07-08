/*
 * @Author: your name
 * @Date: 2021-12-07 11:51:57
 * @LastEditTime: 2021-12-07 12:00:20
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: /zhongu-app-admin/src/utils/download.js
 */
export default function(formData, url, name = 'zhonggu') {
  return new Promise((resolve, reject) => {
    var xhr = new XMLHttpRequest()
    xhr.open('GET', url, true) // 也可以使用POST方式，根据接口
    xhr.responseType = 'blob' // 返回类型blob
    // 定义请求完成的处理函数，请求前也可以增加加载框/禁用下载按钮逻辑
    xhr.onload = function() {
      // 请求完成
      if (this.status === 200) {
        // 返回200
        var blob = this.response
        var reader = new FileReader()
        reader.readAsDataURL(blob) // 转换为base64，可以直接放入a表情href
        reader.onload = function(e) {
          // 转换完成，创建一个a标签用于下载
          var a = document.createElement('a')
          a.download = name + '.xlsx'
          a.href = e.target.result
          document.body.append(a) // 修复firefox中无法触发click
          a.click()
          resolve(200)
          a.remove()
        }
      }
    }
    // 发送ajax请求
    xhr.send(formData)
  })
}
