/*
 * @Author: your name
 * @Date: 2021-04-19 19:47:05
 * @LastEditTime: 2021-04-19 20:09:29
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /opinion_monitor_web/src/components/Progress/index.js
 */
class Progress {
  constructor() {
    this.done = false
    this.status = 0 // 0 未开始 // 1 加载中 // 2 加载完成
    this.value = 0
    this.doneValue = 0
    this.time = null
    this.ms = 1000
  }
  start() {
    if (this.value === this.doneValue) {
      this.time = null
      clearInterval(this.time)
    }

    this.time = setInterval(() => {
      this.get()
    }, this.ms)
  }

  get() {
    this.value++
    return this.value
  }

  end() {
    this.done = true
    this.value = 100
  }
}

export default new Progress()
