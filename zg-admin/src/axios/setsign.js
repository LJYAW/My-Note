/*
 * @Author: zzx
 * @Date: 2020-05-20 17:37:29
 * @LastEditTime: 2021-10-21 16:48:32
 * @FilePath: /weijian_web/src/axios/setsign.js
 */
import md5 from 'js-md5'
const SIGN_SECRET = 'iWkEztZ3YLGlOI7Uwy+wvh2xFwef23fg'

function generateSignString(url, data) {
  const { entries } = Object
  let str = ''
  var sortArr = GetRequest(url)
  for (const [key, value] of entries(sortArr)) {
    str += key + value
  }
  const str_data = JSON.stringify(data)
  str = md5(SIGN_SECRET + str + (str_data || '') + SIGN_SECRET).toUpperCase()
  return str
}

function GetRequest(url) {
  // const url = location.search // 获取url中"?"符后的字串
  const theRequest = []
  if (url.indexOf('?') !== -1) {
    const str = url.substr(1)
    const strs = str.split('&')
    for (let i = 0; i < strs.length; i++) {
      theRequest[strs[i].split('=')[0]] = unescape(strs[i].split('=')[1])
    }
  }
  return objKeySort(theRequest)
}
function objKeySort(obj) {
  // 排序的函数
  var newkey = Object.keys(obj).sort()
  // 先用Object内置类的keys方法获取要排序对象的属性名，再利用Array原型上的sort方法对获取的属性名进行排序，newkey是一个数组
  var newObj = {} // 创建一个新的对象，用于存放排好序的键值对
  for (var i = 0; i < newkey.length; i++) {
    // 遍历newkey数组
    newObj[newkey[i]] = obj[newkey[i]] // 向新创建的对象中按照排好的顺序依次增加键值对
  }
  return newObj // 返回排好序的新对象
}

export default generateSignString
