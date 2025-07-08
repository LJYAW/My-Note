/*
 * @Author: your name
 * @Date: 2021-01-06 11:11:17
 * @LastEditTime: 2021-01-06 12:05:24
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/utils/isMobile.js
 */
export default function isMobile() {
  let userAgent = navigator.userAgent
  let mobileAgents = ['Android', 'iPhone', 'SymbianOS', 'Windows Phone', 'iPad', 'iPod']
  let defaultF = false
  for (let i = 0; i < mobileAgents.length; i++) {
    if (userAgent.indexOf(mobileAgents[i]) > 0) {
      defaultF = true
    } else {
    }
  }
  //或者依据当前屏幕分辨率进行判断
  let mobile_width = window.screen.width //屏幕分辨率宽
  let mobile_height = window.screen.height //屏幕分辨率高
  if (mobile_width < 500 && mobile_height < 800) {
    defaultF = true
  }
  return defaultF //将此标记return出去，若为true，则进行移动端，else为pc
}
