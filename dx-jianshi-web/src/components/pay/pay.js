/*
 * @Author: your name
 * @Date: 2020-11-23 10:59:08
 * @LastEditTime: 2020-12-16 17:42:42
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/utils/pay.js
 */
import self from '@/main.js'
import buy from '@/components/pay/pay_model.vue'
import buySuccesss from '@/components/pay/pay_success.vue'

const pay = {
  buy: function(data, callBack) {
    self.$layer.iframe({
      content: {
        content: buy,
        parent: self,
        data: data
      },
      area: ['700px', '500px'],
      title: '',
      maxmin: false,
      shade: true, //是否显示遮罩
      shadeClose: true,
      cancel: callBack,
      move: false
    })
  },
  buySuccess: function(data, callBack) {
    self.$layer.iframe({
      content: {
        content: buySuccesss,
        parent: self,
        data: data
      },
      area: ['500px', '400px'],
      title: '',
      maxmin: false,
      shade: true, //是否显示遮罩
      shadeClose: true,
      cancel: callBack,
      move: false
    })
  }
}

export default pay
