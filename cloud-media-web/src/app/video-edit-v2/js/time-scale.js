/*
 * @Author: your name
 * @Date: 2021-09-01 15:18:51
 * @LastEditTime: 2021-09-01 15:23:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit-v2/js/time-scale.js
 */
const LING_H = 8 // 段落刻度尺高度
const S_LING_H = 6 // 刻度尺高度
const LING_COLOR = '#979797' // 段落线颜色
const S_LING_COLOR = '#f0f0f0' // 刻度线颜色
const FOND_Y = 20 // 刻度线距离顶部颜色

export default class TimeLineScale {
  constructor(options = {}) {
    this.options = Object.assign(
      {
        lingH: LING_H,
        sLineH: S_LING_H,
        lingColor: LING_COLOR,
        sLineColor: S_LING_COLOR,
        FOND_Y: FOND_Y
      },
      options
    )
  }
}
