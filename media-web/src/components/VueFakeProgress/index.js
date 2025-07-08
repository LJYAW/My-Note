/*
 * @Author: your name
 * @Date: 2021-02-23 16:19:39
 * @LastEditTime: 2021-05-07 14:51:07
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 */
// 导入button组件
import VueFakeProgress from './src/index.vue'

/* istanbul ignore next */
VueFakeProgress.install = function(Vue) {
  Vue.component(VueFakeProgress.name, VueFakeProgress)
}

export default VueFakeProgress
