/*
 * @Author: your name
 * @Date: 2021-07-09 15:11:21
 * @LastEditTime: 2021-07-09 15:26:39
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/video_edit_m/src/utils/key-map.js
 */
export default function keyMap(cb) {
  document.οnkeydοwn = event => {
    const e = event || window.event
    // e.keyCode==40){ //下
    // }
    // e.keyCode==37){ //左
    // }
    // e.keyCode==39){ //右
    // }
    // e.keyCode==38){ // 上
    if (e && cb && typeof cb == 'function') {
      cb(e)
    }
  }
}
