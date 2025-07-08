/*
 * @Author: your name
 * @Date: 2021-04-19 15:01:34
 * @LastEditTime: 2021-06-08 10:23:15
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /opinion_monitor_web/src/utils/filter.js
 */
import Vue from 'vue'
import dateFun from '@/utils/time'
// import isLimit from '@/utils/isLimit'

Vue.filter('unixToDateStr', s => {
  // var result = '2021:10:10'
  return dateFun.convert(s, 'y-m-d')
})

Vue.filter('unixToHms', (ms) => {
  // var result = '2021:10:10 23:01:56'
  return dateFun.convert(ms, 'y-m-d h:m:s')
})

Vue.filter('unixToHmsStr', (ms) => {
  // var result = '23:01:56'
  return dateFun.convert(ms, 'y-m-d h:m:s').split(' ')[1]
})

// Vue.filter('isLimit', (arr) => {
//   // var result = '23:01:56'
//   return isLimit(arr)
// })
