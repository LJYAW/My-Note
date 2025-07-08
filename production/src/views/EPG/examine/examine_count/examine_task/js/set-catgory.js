/*
 * @Author: your name
 * @Date: 2021-03-02 14:28:55
 * @LastEditTime: 2021-03-28 16:21:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/js/set-catgory.js
 */

export default function setModalData(list, index) {
  const obj = JSON.parse(JSON.stringify(list[index]))
  obj.curStatus = '修改中'
  list[index].componentName = 'ApprovedDetail'
  const firstIndex = index - 2 < 0 ? 0 : index - 2
  const lastIndex = index + 2 > list.length ? list.length : index + 2
  const newList = list.slice(firstIndex, lastIndex + 1)
  const curIndex = newList.findIndex(item => item.componentName === 'ApprovedDetail')
  newList.splice(curIndex, 0, obj)
  return newList
}
