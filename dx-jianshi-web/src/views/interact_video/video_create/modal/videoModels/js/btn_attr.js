/*
 * @Author: zzx
 * @Date: 2020-09-28 18:16:16
 * @LastEditTime: 2020-09-29 15:15:06
 * @FilePath: /zhijian_web/src/views/interact_video/video_create/modal/videoModels/js/btn_attr.js
 */
export default function btnAttr() {
  return {
    width: 100 + 'px',
    height: 30 + 'px',
    eventFn: '跳转视频片段',
    jumpTo: 'A',
    overBreak: '暂停',
    img: '',
    text: '选项',
    eventParams: '',
    popoverVisible: false,
    editable: false
  }
}
