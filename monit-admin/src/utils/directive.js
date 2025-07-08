/*
 * @Author: your name
 * @Date: 2021-07-14 14:40:42
 * @LastEditTime: 2021-07-14 14:40:53
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor-msg-h5/src/utils/directive.js
 */
import Vue from 'vue'
// 自定义指令 ,只能输入字母和数字
Vue.directive('Alphabet', {
  inserted: function(el) {
    const input = el.querySelector('#valueInput')
    input.onkeyup = function(e) {
      input.value = input.value.replace(/[^A-Za-z0-9]/g, '')
    }
    input.onblur = function(e) {
      input.value = input.value.replace(/[^A-Za-z0-9]/g, '')
    }
  }
})
