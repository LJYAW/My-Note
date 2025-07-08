<!--
 * @Author: zzx
 * @Date: 2020-10-16 14:37:10
 * @LastEditTime: 2020-11-12 15:45:35
 * @FilePath: /zhijian_web/src/views/active_video/create_video/header_bar.vue
-->
<template>
  <div class="header-bar">
    <div class="container d-flex">
      <div class="d-flex justify-content-center align-items-center">
        <vsvg icon="iconfanhui" style="width: 10px; height: 10px" @click.native="goToVideoList"></vsvg>
        <p class="sub-title">{{activeVideoTitle.project_name}}</p>
      </div>
      <div class="d-flex ml-auto btn-list">
        <a>
          <icon-img :src="require('@/assets/images/active_video/save.png')" />
          <span class="ml-10px mr-40px">保存</span>
        </a>
        <a @click="save_as()">
          <icon-img :src="require('@/assets/images/active_video/save_as.png')" />
          <span class="ml-10px mr-40px">另存为</span>
        </a>
        <a>
          <icon-img :src="require('@/assets/images/active_video/paly.png')" />
          <span class="ml-10px mr-40px">预览</span>
        </a>
        <a @click="release()">
          <icon-img :src="require('@/assets/images/active_video/publish.png')" />
          <span class="ml-10px mr-40px">发布</span>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import saveAs from '../../models/save_as'
import release from '../../models/release'
import $iframe from '../../models/layer'
import { btnAttr, eventFnList } from '../video_btn_modal/btn_attr'
import letter from '../js/en_letter'
import { checkVideoSize } from '@/utils/check_video_size.js'

export default {
  name: 'HeaderBar',
  props: {},
  data() {
    return {}
  },
  computed: {
    activeVideoTitle() {
      return JSON.parse(localStorage.getItem('active_video_title'))
    }
  },
  watch: {},
  methods: {
    goToVideoList() {
      this.$router.push('/active_video')
    },
    save_as() {
      $iframe({
        content: saveAs,
        title: '另存为',
        width: '500px',
        height: '216px'
      })
    },
    async setGenerateData() {
      let active_video_list = JSON.parse(localStorage.getItem('active_video_data_list')) || []

      if (active_video_list.length < 1) return

      let obj = {
        // "interactive_video_id": "string",
        title: this.activeVideoTitle.project_name,
        ratio: this.activeVideoTitle.video_proportion, //16:9 or 9:16
        video_clips: []
      }
      for (let i = 0; i < active_video_list.length; i++) {
        const item = active_video_list[i]
        const index = i
        let btnList = []
        // let videoSize = await checkVideoSize(item.material.resource_url)
        // 暂时写死
        let videoCanvas = {
          height: 324,
          width: 576
        }

        item.btn_effect.btn_list.forEach(btn => {
          btnList.push({
            type: eventFnList[btn.eventFn].type,
            data: btn.eventParams,
            button_id: btn.selectBtn.id,
            button_material_id: '',
            text: '',
            top: this.percentage(btn.y, videoCanvas.height),
            left: this.percentage(btn.x, videoCanvas.width),
            width: this.percentage(btn.w, videoCanvas.width),
            height: this.percentage(btn.h, videoCanvas.height)
          })
        })

        obj.video_clips.push({
          order: index,
          clip_name: letter[index],
          material_id: item.material.material_id,
          has_event: item.btn_effect.btn_list ? '是' : '否',
          event_config: {
            start_time: item.material.start_ms,
            end_time: item.material.end_ms,
            triger_event: {
              type: item.btn_effect.radio_event,
              auto_button_index: item.btn_effect.play_auto_btn
            },
            button: btnList
          }
        })
      }
      return obj
    },
    async release() {
      let data = await this.setGenerateData()
      this.$post('/interactive-video/generate', data).then(res => {
        if (res.data.code == '0000') {
          this.$alertMsg('发布成功')
        }
      })
    },
    // 求百分比
    percentage(num, total) {
      if (num == 0 || total == 0) {
        return 0
      }
      return Math.round((num / total) * 10000) / 100.0 // 小数点后两位百分比
    }
  },
  components: {
    saveAs,
    release
  },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
@media screen and (max-width: 1200px) and (min-width: 768px) {
  .header-bar {
    max-width: 1200px;
    min-width: 1200px;
  }
}
.header-bar {
  background: white;
  padding: 26px 0;
  font-size: 16px;
  .sub-title {
    margin-left: 20px;
  }
  .btn-list {
    color: #165dd9;
    img {
      vertical-align: sub;
    }
  }
}
@media screen and (max-width: 768px) {
  .header-bar {
    max-width: 1000px;
    min-width: 1000px;
  }
}
@media screen and (max-width: 1200px) and (min-width: 768px) {
  .header-bar {
    max-width: 1200px;
    min-width: 1200px;
  }
}
</style>
