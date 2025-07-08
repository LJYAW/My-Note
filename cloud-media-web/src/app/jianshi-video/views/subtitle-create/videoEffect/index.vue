<!--
 * @Author: your name
 * @Date: 2021-07-21 19:09:27
 * @LastEditTime: 2021-09-16 17:30:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/index.vue
-->
<template>
  <div class="video-effect">
    <div class="top-title">视频效果选项</div>
    <el-form ref="form" label-width="110px" label-position="left" :model="form">
      <!-- 声音 -->
      <Voice />
      <!-- 画面 -->
      <Frame />
      <!-- 附加 -->
      <Additional />

    </el-form>
  </div>
</template>

<script>
import { GetMaterials } from '../../../api/materisls/index'
import Voice from './modular/voice/voice'
import Frame from './modular/frame/frame'
import Additional from './modular/additional/additional'
import { mapState } from 'vuex'
export default {
  components: {
    Voice, Frame, Additional
  },
  props: {

  },
  data() {
    return {
      form: {}
    }
  },
  computed: {
    ...mapState('jianshi', [])
  },
  watch: {
  },
  created() {
  },
  mounted() {
  },
  methods: {
    // 回显数据
    initUpData(data) {
      const obj = this.$store.state.jianshi.effectData
      Object.keys(obj).forEach(item => {
        obj[item] = data[item]
      })
      if (!data.subtitle) {
        obj.subtitle = { margin_bottom: 0.3 }
      }
    }
  }
}
</script>

<style  lang="scss">
@import "../videoEffect/css/comment.scss";

@import "../videoEffect/css/index.scss"
</style>
