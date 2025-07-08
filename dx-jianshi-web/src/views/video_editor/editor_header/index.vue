/* eslint-disable no-undef */
/* eslint-disable no-redeclare */
<template>
  <div class="editor-header">
    <!-- 剪辑器头部 -->
    <div class="editor-contenter d-flex align-items-center h-100">
      <router-link to="/"
                   class="home-btn h-100 border-right bc-r-ef d-flex align-items-center">
        <i class="iconfont iconshouye fz-25px px-25px "></i>
      </router-link>

      <div class="d-flex ml-25px">
        <div style="width: 70px; height: 27px"
             class="bg-logo"></div>

        <span class="fz-12px fc-333 mt-auto ml-10px">视频编辑器（试用版）</span>

      </div>

      <div></div>
      <div class="fz-12px fc-333 ml-auto h-100 d-flex align-items-center">
        <!-- <a class="hove-c  px-20px">保存</a> -->
        <div class="h-100 border-left bc-l-ef d-flex align-items-center">
          <button class="btn btn-primary btn-sm border-radius-30px mx-20px"
                  @click="setData()">
            <span v-if="!status_loading">发布</span>
            <span v-else>转码中...</span>
          </button>
        </div>

      </div>
    </div>

  </div>
</template>

<script>
import html2canvas from 'html2canvas'

export default {
  props: [],
  data() {
    return {
      params: {},
      text_png_list: [],
      status_loading: false
    }
  },
  components: {},

  computed: {
    cut_video_list() {
      return this.$store.state.videoM.cut_track_list
    },
    img_video_list() {
      return this.$store.state.videoM.image_track_list
    },
    text_list() {
      return this.$store.state.videoM.track_text_list
    },
    per_px_ms() {
      return this.$store.state.videoM.per_px_ms
    }
  },

  watch: {},

  methods: {
    setCutVideo() {
      // 视频的列表
      var cut_video_list = this.cut_video_list
      // 贴图列表
      var img_video_list = this.$store.state.videoM.image_track_list
      // 文本列表
      var text_list = this.$store.state.videoM.track_text_list
      var text_png_list = JSON.parse(JSON.stringify(this.text_png_list))

      // 每像素毫秒数
      var per_px_ms = this.$store.state.videoM.per_px_ms

      // console.log(cut_video_list);
      // console.log('img_video_list', img_video_list);
      // console.log('text_list', text_list);

      // 保存区间
      var part_arr = []
      function pushTime(...paraArr) {
        paraArr.forEach(arr => {
          arr.forEach(item => {
            if (item.type == 'video') {
              var start_time = item.start_time || 0
              part_arr.push([start_time, item.end_time])
            } else {
              var left = item.left || 0
              // eslint-disable-next-line no-redeclare
              var start_time = Math.floor(left * per_px_ms)
              var end_time = Math.floor((left + item.width) * per_px_ms)

              part_arr.push([start_time, end_time])
            }
          })
        })
      }
      pushTime(cut_video_list, img_video_list, text_list)
      var sort_part_arr = part_arr.sort((a, b) => {
        return a - b
      })
      console.log(sort_part_arr)

      // 循环出 多少个区间
      var cut_arr = []
      // for (let i = 0; i < sort_part_arr.length / 2; i++) {
      //   cut_arr[i] = {
      //     url: '',
      //     cmd_list: [
      //       {
      //         "cmd": "cut",
      //         start_time: sort_part_arr[i],
      //         end_time: sort_part_arr[i + 1],
      //       }
      //     ]
      //   }
      // }

      for (let i = 0; i < sort_part_arr.length; i++) {
        const start_time = sort_part_arr[i][0]
        const end_time = sort_part_arr[i][1]
        cut_arr.push({
          url: '',
          cmd_list: [
            {
              cmd: 'cut',
              start_time: start_time,
              end_time: end_time
            }
          ]
        })
      }

      console.log(cut_arr)

      // return

      // 区间内添加数据
      cut_arr.forEach(item => {
        var cut_start_time = item.cmd_list[0].start_time
        var cut_end_time = item.cmd_list[0].end_time
        // 添加文字数据
        // text_list.forEach((text, index) => {
        //   var left = text.left || 0
        //   var start_time = Math.floor(left * per_px_ms)
        //   var end_time = Math.floor((left + text.width) * per_px_ms)
        //   if (cut_start_time >= start_time && cut_end_time <= end_time) {
        //     item.cmd_list.push({
        //       cmd: 'overlay',
        //       url: text_png_list[index],
        //       x: parseInt(text.position.left), // 矩形左上角x坐标
        //       y: parseInt(text.position.top), // y坐标
        //       w: parseInt(text.style.width), // 矩形宽度
        //       h: parseInt(text.style.height) // 高度
        //     });
        //   }
        // })

        // // 添加图片数据 目前只有 马赛克 功能
        // img_video_list.forEach(img => {
        //   var left = img.left || 0
        //   var start_time = Math.floor(left * per_px_ms)
        //   var end_time = Math.floor((left + img.width) * per_px_ms)

        //   if (cut_start_time >= start_time && cut_end_time <= end_time) {
        //     item.cmd_list.push({
        //       cmd: 'delogo',
        //       x: parseInt(img.position.left), // 矩形左上角x坐标
        //       y: parseInt(img.position.top), // y坐标
        //       w: parseInt(img.style.width), // 矩形宽度
        //       h: parseInt(img.style.height) // 高度
        //     });
        //   }
        // })

        // 添加视频url
        cut_video_list.forEach(video => {
          var start_time = video.start_time || 0
          var end_time = video.end_time

          console.log(cut_start_time)
          console.log(cut_end_time)

          if (cut_start_time >= start_time && cut_end_time <= end_time) {
            item.url = video.video_url
          }
        })
      })

      // cut_arr = cut_arr.filter(item => {
      //   return item.url
      // })

      console.log(cut_arr)

      // return

      var process_list = cut_arr

      this.params = {
        video_width: 1280,
        video_height: 720,
        video_bitrate: 1000, // 1000k
        process_list: process_list
      }
    },
    setCutList() {
      // 保存区间
      var part_arr = []
      function pushTime(...paraArr) {
        paraArr.forEach(arr => {
          arr.forEach(item => {
            var left = item.left || 0
            var end = left + item.width
            part_arr.push(left)
            part_arr.push(end)
          })
        })
      }
      pushTime(this.cut_video_list, this.img_video_list, this.text_list)
      // eslint-disable-next-line no-redeclare
      var part_arr = part_arr.sort((a, b) => {
        return a - b
      })

      const sort_arr = []
      for (let i = 0; i < part_arr.length - 1; i++) {
        const left = part_arr[i]
        const end = part_arr[i + 1]

        if (end - left > 1) {
          sort_arr.push([left, end])
        }
      }

      console.log(sort_arr)

      // 切割出 视频片段
      var cut_arr = []
      for (let i = 0; i < sort_arr.length; i++) {
        const left = sort_arr[i][0]
        const end = sort_arr[i][1]

        for (let j = 0; j < this.cut_video_list.length; j++) {
          const item = this.cut_video_list[j]
          const video_left = item.left || 0
          const video_end = video_left + item.width
          const video_end_time = item.end_time
          const video_start_time = item.start_time || 0

          // 如果 left == video_left 说明开始时间等于 video 的开始时间
          if (left == video_left && end == video_end) {
            cut_arr.push({
              left: left,
              end: end,
              url: item.video_url,
              cmd_list: [
                {
                  cmd: 'cut',
                  start_time: video_start_time,
                  end_time: video_end_time
                }
              ]
            })
          }

          // 说明 中间有其他的元素 需要重新计算 结束时间
          if (left == video_left && end < video_end) {
            const new_end_tiem = Math.floor((end - left) * this.per_px_ms + video_start_time)

            cut_arr.push({
              left: left,
              end: end,
              url: item.video_url,
              cmd_list: [
                {
                  cmd: 'cut',
                  start_time: video_start_time,
                  end_time: new_end_tiem
                }
              ]
            })
          }

          // 说明在中间 没有被切割
          if (left > video_left && end < video_end) {
            const new_start_time = Math.floor((left - video_left) * this.per_px_ms + video_start_time)
            const new_end_tiem = Math.floor((end - video_left) * this.per_px_ms + video_start_time)

            cut_arr.push({
              left: left,
              end: end,
              url: item.video_url,
              cmd_list: [
                {
                  cmd: 'cut',
                  start_time: new_start_time,
                  end_time: new_end_tiem
                }
              ]
            })
          }

          // 如果 left 大于 video_left的时候 结束位置== video的 结束位置 说明是最后一个元素
          if (left > video_left && end == video_end) {
            const new_start_time = Math.floor((left - video_left) * this.per_px_ms + video_start_time)

            cut_arr.push({
              left: left,
              end: end,
              url: item.video_url,
              cmd_list: [
                {
                  cmd: 'cut',
                  start_time: new_start_time,
                  end_time: video_end_time
                }
              ]
            })
          }
        }
      }

      console.log(cut_arr)

      for (let i = 0; i < cut_arr.length; i++) {
        const left = cut_arr[i].left
        const end = cut_arr[i].end

        // 添加文字
        this.text_list.forEach((text, index) => {
          const text_left = text.left || 0
          const text_end = text.left + text.width

          if (text_left <= left && text_end >= end) {
            cut_arr[i].cmd_list.push({
              cmd: 'overlay',
              url: this.text_png_list[index],
              x: parseInt(text.position.left), // 矩形左上角x坐标
              y: parseInt(text.position.top), // y坐标
              w: parseInt(text.style.width), // 矩形宽度
              h: parseInt(text.style.height) // 高度
            })
          }
        })

        this.img_video_list.forEach(img => {
          const img_video_left = img.left || 0
          const img_video_end = img.left + img.width

          if (img_video_left <= left && img_video_end >= end) {
            cut_arr[i].cmd_list.push({
              cmd: 'delogo',
              x: parseInt(img.position.left), // 矩形左上角x坐标
              y: parseInt(img.position.top), // y坐标
              w: parseInt(img.style.width), // 矩形宽度
              h: parseInt(img.style.height) // 高度
            })
          }
        })
      }

      var process_list = cut_arr

      this.params = {
        video_width: 1280,
        video_height: 720,
        video_bitrate: 1000, // 1000k
        process_list: process_list
      }
    },
    save() {
      if (this.$store.state.videoM.cut_track_list.length < 1) {
        return
      }

      var self = this
      // 对旧的接口做适配
      var cut_list = this.params.process_list
      cut_list.forEach(item => {
        console.log(item)
        var start_time = item.cmd_list[0].start_time / 1000
        var end_time = item.cmd_list[0].end_time / 1000
        delete item.cmd_list[0].start_time
        delete item.cmd_list[0].end_time
        delete item.left
        delete item.end
        item.cmd_list[0].start = start_time
        item.cmd_list[0].end = end_time
      })

      console.log(cut_list)

      var params = {
        m: 62011,
        mjson: JSON.stringify(this.params)
      }

      var form = new FormData()
      form.append('m', 62011)
      form.append('mjson', JSON.stringify(this.params))

      var xmlhttp = new XMLHttpRequest()
      // eslint-disable-next-line no-undef
      xmlhttp.open('POST', HOWDU_URL, true)
      xmlhttp.send(form)
      // readyState == 4 为请求完成，status == 200为请求陈宫返回的状态
      xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          var res = JSON.parse(xmlhttp.responseText)
          console.log(res)
          if (res.status == 1) {
            // var routerUrl = self.$router.resolve({
            //   path: '/video_editor/video_cut_buy',
            //   query: { task_id: res.data.task_id }
            // })
            // console.log('跳转到购买页面');
            // window.open(routerUrl.href, '_blank')
            const routerDate = self.$router.resolve({
              path: '/video_release/short_details',
              query: {
                task_id: res.data.task_id,
                is_editor: true
              }
            })
            window.open(routerDate.href, '_blank')
          } else {
            self.$store.commit('alertMsg', '出错了')
          }
          self.status_loading = false
          self.text_png_list = []
        } else {
          self.$store.commit('alertMsg', '服务器端出错')
        }
      }
      console.log(this.params)
      console.log('发送剪辑请求执行完毕')
    },
    ajaxPng(form) {
      return new Promise((resolve, reject) => {
        var xmlhttp = new XMLHttpRequest()

        // eslint-disable-next-line no-undef
        xmlhttp.open('POST', HOWDU_URL, true)
        xmlhttp.send(form)
        // readyState == 4 为请求完成，status == 200为请求陈宫返回的状态
        xmlhttp.onreadystatechange = function() {
          if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var res = JSON.parse(xmlhttp.responseText)
            if (res.status == 1) {
              resolve(res.data)
            } else {
              reject(res.msg)
            }
          } else {
            self.$store.commit('alertMsg', '服务器端出错')
          }
        }
      })
    },
    // 改成同步函数
    async getDom() {
      var textDom = Array.from(document.querySelectorAll('.video-text'))

      for (let i = 0; i <= textDom.length - 1; i++) {
        console.log(i)

        var cloneDom = textDom[i].cloneNode(true)
        document.body.appendChild(cloneDom)
        cloneDom.style.display = 'block'
        cloneDom.style.zIndex = -1

        // 拿到DOM 获取 base64
        const canvas = await html2canvas(cloneDom, {
          useCORS: true,
          logging: true,
          backgroundColor: 'transparent'
        })
        var base64 = canvas.toDataURL() // BASE64 的 图片URL
        console.log(base64)

        // 设置数据 进行请求
        var form = new FormData()
        form.append('m', 62010)
        form.append('type', 1)
        form.append('image', base64)
        const res = await this.ajaxPng(form)
        this.text_png_list.push(res)
        cloneDom.remove()
      }
    },
    async setData() {
      if (this.cut_video_list.length < 1) {
        return
      }
      this.status_loading = true
      await this.getDom()
      // this.setCutVideo()
      this.setCutList()
      this.save()
    }
  },

  created() {},

  mounted() {}
}
</script>

<style scoped lang="scss">
.editor-header {
  height: 60px;
}
</style>
