/*
 * @Author: zzx
 * @Date: 2020-08-10 16:18:02
 * @LastEditTime: 2021-05-08 19:12:35
 * @FilePath: /video_edit/src/components/time_line/src/mixin.js
 */
export const mixinMethods = {
  data() {
    return {
      domList: [],
      start: 0,
      parentWidth: 0,
      lastStart: 0,
      size: 30, // 默认显示的条数
      minWidth: 2000 // 最小是 2000像素的 时间轴
    }
  },
  methods: {

    setList() {
      // let size = Math.floor(this.minWidth / this.BLOCK_WIDTH)
      // let index = this.start * size
      for (let i = 0; i < this.block_num; i++) {
        this.domList[i] = {
          id: i,
          width: `${this.BLOCK_WIDTH}px`,
          transform: `translateX(${this.BLOCK_WIDTH * i}px)`
        }
      }
    },
    updataList() {
      let index = this.start * this.size
      let size = this.size + this.size * this.start
      let arr = []
      let itemIndex = 0
      for (let i = index; i < size; i++) {
        arr.push({
          id: i,
          width: `${this.BLOCK_WIDTH}px`,
          transform: `translateX(${this.BLOCK_WIDTH * i}px)`
        })
      }
      this.domList.splice(index, size, ...arr)
    },
    handleScroller() {
      let scrollLeft = this.parentDom.scrollLeft
      this.start = Math.floor(scrollLeft / 500)
      if (this.lastStart !== this.start) {
        this.lastStart = this.start
        this.updataList()
      }
    },
    listenerScroll() {
      this.parentDom = document.querySelectorAll('.edit-pad')[0]
      this.parentWidth = this.parentDom.offsetWidth
      // 为了 定位到指定帧 暂时不用
      // this.parentDom.addEventListener('scroll', this.handleScroller, false)
    },
     msToSecond(s) {
      var result = '00:00:00'

      var minute, second, ms
      if (s > 0) {
        second = Math.floor(s / 60000)
        if (second < 10) {
          second = '0' + second
        }
        minute = Math.floor(s / 1000 - second * 60)
        if (minute < 10) {
          minute = '0' + minute
        }
        ms = (Math.floor(parseFloat(s) - second * 60000 - minute * 1000) / 10).toFixed()
        if (ms < 10) {
          ms = '0' + ms
          result = second + ':' + minute + '.' + ms
        }
      }
      return result
    }
  }
}
