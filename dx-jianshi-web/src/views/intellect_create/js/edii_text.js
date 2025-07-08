/*
 * @Author: your name
 * @Date: 2020-12-03 17:12:21
 * @LastEditTime: 2021-07-30 10:27:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/intellect_create/js/edii_text.js
 */
// 获取光标位置
import { getCursortPosition, setSelectPos, enterkey } from './common.js'

export default {
  methods: {
    // 回车换行
    keyupEnter(txt, index, event) {
      enterkey(event)
      let el = event.target
      //   // 去除HTML 除了img
      var reg = /<\/?(?!img\b)\w+\b[^<>]*>/g

      let innerHTML = this.$refs.textareaText[index].innerHTML
      let tagIndex = innerHTML.indexOf('<break></break>')
      if (tagIndex != -1) {
        let htmlarr = innerHTML.split('<break></break>')
        let par = htmlarr[0]
          .replace(reg, '')
          .replace(/<img[^>]*>/g, '#pau#')
          .replace(/&nbsp;/gi, ' ')
        let next = htmlarr[1]
          .replace(reg, '')
          .replace(/<img[^>]*>/g, '#pau#')
          .replace(/&nbsp;/gi, ' ')
        let text = {
          pre_str: par,
          naxt_str: next
        }
        this.subTitle.splice(index + 1, 0, {
          title: '',
          show: false
        })
        this.resetEdtorList(index, text)
        this.resetImgListDuration()
        this.$nextTick(() => {
          setSelectPos(el, getCursortPosition(el))
        })
      }
    },
    // 添加朗读停顿
    addCode(index, text, event) {
      let el = event.target
      if (!this.isRightFocus()) return
      this.isEnter = false
      let img = document.createElement('img')
      img.className = 'code'
      console.log(this.range)
      this.range.insertNode(img)
      // this.changeText(index)
      // this.$refs.textareaText[index].focus()
      // this.getRange(index)
      // this.$nextTick(() => {
      //   setSelectPos(el, this.range)
      // })
    },
    isRightFocus() {
      return this.range.commonAncestorContainer
    }
  }
}
