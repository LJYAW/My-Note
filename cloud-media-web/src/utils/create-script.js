
/*
 * @Author: your name
 * @Date: 2021-09-06 17:38:24
 * @LastEditTime: 2021-09-06 18:31:19
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/utils/create-scripet.js
 */
export default function(src, callback) {
  return new Promise((resolve, reject) => {
    var oHead = document.getElementsByTagName('HEAD').item(0)
    var script = document.createElement('script')
    script.setAttribute('id', 'newScript')
    script.type = 'text/javascript'
    script.src = src
    console.log('🚀 ~ file: create-script.js ~ line 17 ~ returnnewPromise ~ script', script)
    oHead.appendChild(script)

    script.onload = script.onreadystatechange = function() {
      if (
        !this.readyState ||
        this.readyState === 'loaded' ||
        this.readyState === 'complete'
      ) {
        console.log('加载成功')
        resolve(window.baidubce)
        script.onload = script.onreadystatechange = null
      }
    }
  })
}
