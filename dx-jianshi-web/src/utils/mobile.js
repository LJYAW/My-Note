/*
 * @Author: your name
 * @Date: 2020-11-27 16:04:32
 * @LastEditTime: 2020-12-11 12:06:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/utils/mobile.js
 */
//移动端适配
export function setRem(val = 750) {
  //得到手机屏幕的宽度
  let htmlWidth = document.documentElement.clientWidth || document.body.clientWidth
  //设置根元素字体大小
  document.documentElement.style.fontSize = (htmlWidth / val) * 100 + 'px'
}
