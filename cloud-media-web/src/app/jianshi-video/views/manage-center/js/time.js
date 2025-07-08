/*
 * @Author: your name
 * @Date: 2021-09-29 15:20:50
 * @LastEditTime: 2021-09-29 15:24:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/manage-center/js/time.js
 */
export function dateChangeFormat(format, date) {
  date = new Date(date)
  const dataItem = {
    'Y+': date.getFullYear().toString(),
    'm+': (date.getMonth() + 1).toString(),
    'd+': date.getDate().toString(),
    'H+': date.getHours().toString(),
    'M+': date.getMinutes().toString(),
    'S+': date.getSeconds().toString()
  }
  Object.keys(dataItem).forEach((item) => {
    const ret = new RegExp(`(${item})`).exec(format)
    if (ret) {
      format = format.replace(ret[1], ret[1].length === 1 ? dataItem[item] : dataItem[item].padStart(ret[1].length, '0'))
    }
  })
  return format
}
