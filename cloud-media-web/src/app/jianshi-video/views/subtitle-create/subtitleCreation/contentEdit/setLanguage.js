/*
 * @Author: your name
 * @Date: 2021-09-28 15:23:02
 * @LastEditTime: 2021-09-28 15:33:50
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/subtitleCreation/contentEdit/setLanguage.js
 */
// 设置语言
export function setLanguage(word, displayLangage, language) {
  const obj = { chinese: '', english: '' }
  const arr = word.indexOf('\n') !== -1 ? word.split('\n') : word
  if (typeof arr === 'string') {
    obj.chinese = arr; obj.english = ''
  } else {
    switch (displayLangage) {
      case 1:// 输出语言为英文
        obj.chinese = language === 1 ? arr[0] : language === 2 ? arr[1] : ''
        break
      case 2:// 输出语言为中文
        obj.chinese = language === 2 ? arr[0] : language === 1 ? arr[1] : ''
        break
      case 3:// 输出语言为双语
        obj.chinese = arr[0]; obj.english = arr[1]
        break
      default:// 不传参数的时候
        obj.chinese = arr[0]; obj.english = arr[1]
        break
    }
  }

  return obj
}
