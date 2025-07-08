/*
 * @Author: your name
 * @Date: 2021-04-25 16:39:50
 * @LastEditTime: 2021-04-25 17:13:08
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/utils/simulate.js
 */
export function simulate() {
  var btn = document.querySelector('body')
  btn.addEventListener('click', function(event) {
    console.log('OH~!You clicked me~!')
    // this.playerOptions.muted = false
  }, false)
  var ev = new MouseEvent('click', {
    cancelable: true,
    bubble: true,
    view: window
  })
  btn.dispatchEvent(ev)
}

export function theEvent() {
  var theEvent = document.createEvent('MouseEvent')
  theEvent.initMouseEvent('click', true, true, window, 0, 202, 302, 1, 1, false, false, false, false, 0, null)
}
