/*
 * @Author: your name
 * @Date: 2020-11-04 17:23:52
 * @LastEditTime: 2020-11-13 11:53:59
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/active_video/video_btn_modal/btn_attr.js
 */
const btnAttr = {
  x: 0,
  y: 0,
  w: 100,
  h: 30,
  eventFn: 'jump',
  eventParams: 'A',
  text: '选项',
}

let eventFnList = {
  // paramsType 1.是选择框选择 2.是输入框输入
  jump: {
    name: '跳转视频片段',
    activeName: '至',
    paramsType: 1, // input框的样式
    type: 'seek'
  },
  link: {
    name: '跳转链接',
    activeName: '链接',
    paramsType: 2,
    type: 'href'
  },
  phone: {
    name: '拨打电话',
    activeName: '电话',
    paramsType: 2,
    type: 'phone'
  },
}
export { btnAttr, eventFnList }
