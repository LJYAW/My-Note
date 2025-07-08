/*
 * @Author: your name
 * @Date: 2020-12-03 11:51:04
 * @LastEditTime: 2021-09-26 16:39:32
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/intellect_create/js/common.js
 */
// 获取光标位置
const getCursortPosition = function(element) {
  var caretOffset = 0
  var doc = element.ownerDocument || element.document
  var win = doc.defaultView || doc.parentWindow
  var sel
  if (typeof win.getSelection !== 'undefined') {
    // 谷歌、火狐
    sel = win.getSelection()
    if (sel.rangeCount > 0) {
      // 选中的区域
      var range = win.getSelection().getRangeAt(0)
      var preCaretRange = range.cloneRange() // 克隆一个选中区域
      preCaretRange.selectNodeContents(element) // 设置选中区域的节点内容为当前节点
      preCaretRange.setEnd(range.endContainer, range.endOffset) // 重置选中区域的结束位置
      caretOffset = preCaretRange.toString().length
    }
  } else if ((sel = doc.selection) && sel.type !== 'Control') {
    // IE
    var textRange = sel.createRange()
    var preCaretTextRange = doc.body.createTextRange()
    preCaretTextRange.moveToElementText(element)
    preCaretTextRange.setEndPoint('EndToEnd', textRange)
    caretOffset = preCaretTextRange.text.length
  }
  return caretOffset
}

const setSelectPos = function(el, rge) {
  var range = 0
  if (range) {
    range = rge
  } else {
    range = document.createRange()
  }

  range.selectNodeContents(el)

  range.collapse(false)

  var sel = window.getSelection()

  sel.removeAllRanges()

  sel.addRange(range)
}

// 监控按enter键和空格键，如果按了enter键，则取消原事件，用<BR/ >代替。此处还等待修改！！！！！！如果后端能实现各个浏览器回车键产生的P，div, br的输出问题话就无需采用这段JS、

const enterkey = function(e) {
  e = event.keyCode

  if (e === 13 || e === 32) {
    var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode

    event.returnValue = false // 取消此事件的默认操作

    if (document.selection && document.selection.createRange) {
      var myRange = document.selection.createRange()

      myRange.pasteHTML('<break></break>')
    } else if (window.getSelection) {
      var selection = window.getSelection()

      var range = window.getSelection().getRangeAt(0)

      range.deleteContents()

      var newP = document.createElement('break')

      range.insertNode(newP)
    }
  }
}

export { getCursortPosition, setSelectPos, enterkey }
