/*
 * @Author: your name
 * @Date: 2021-04-20 16:58:14
 * @LastEditTime: 2021-04-29 16:57:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-zzx-ui/packages/VueFakeProgress/src/fake-progress.js
 */
class FakeProgress {
  constructor() {
    this.status = null
    this.trickleSpeed = 2000
    this.minimum = 8
    this.timer = null
  }

  /*
    sets progress by status, where `n` is a number from `0` to `100`
    FakeProgress.set(0)
    FakeProgress.set(100)
  */
  set(number) {
    const n = clamp(number, this.minimum, 100)
    this.status = (n === 100 ? 100 : n)
  }
  start(callback = () => {}) {
    !this.status && this.set(0)

    const work = () => {
      if (this.status >= 100) {
        this.clearTimeer()
        return
      }

      this.timer = setTimeout(() => {
        this.inc()

        work()
        callback(this.status)
      }, this.trickleSpeed)
    }
    work()
  }

  isStarted() {
    return isNaN(this.status)
  }

  inc() {
    let n = this.status
    !n && this.start()
    let amount = 0

    if (n < 100) {
      // if (isNaN(amount)) {
      if (n >= 0 && n < 20) {
        amount = 10
      } else if (n >= 20 && n < 50) {
        amount = 4
      } else if (n >= 50 && n < 80) {
        amount = 2
      } else if (n >= 8 && n < 99) {
        amount = 0.5
      } else {
        amount = 0
      }
      // }
      n = clamp(n + amount, 0, 99)
      this.set(n)
    }
  }

  done() {
    console.log('🚀 ~ file: fake-progress.js ~ line 74 ~ FakeProgress ~ done ~ done')
    this.clearTimeer()
    this.set(100)
  }

  clearTimeer() {
    clearTimeout(this.timer)
    this.timer = null
  }
}

function clamp(n, min, max) {
  if (n < min) return min
  if (n > max) return max
  return n
}

export default new FakeProgress()
