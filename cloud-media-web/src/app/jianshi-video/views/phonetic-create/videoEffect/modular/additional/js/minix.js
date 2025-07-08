/*
 * @Author: your name
 * @Date: 2021-07-31 18:31:45
 * @LastEditTime: 2021-09-16 16:03:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/additional/js/minix.js
 */
export default {
  data() {
    return {
      tabIndex: 0,
      dialogVisible: false,
      params: {
        page: 0,
        limit: 100,
        title: '',
        tag: ''
      }
    }
  },
  methods: {
    // 展示弹框
    upload() {
      this.dialogVisible = true
    },
    // 点击切换样式
    tabClick(item, index) {
      this.tabIndex = index
      this.setData(item)
    }
  }
}
