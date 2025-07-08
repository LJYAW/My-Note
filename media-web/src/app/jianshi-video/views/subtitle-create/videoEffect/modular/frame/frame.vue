<!--
 * @Author: your name
 * @Date: 2021-07-23 14:42:52
 * @LastEditTime: 2021-10-26 18:04:59
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/frame.vue
-->
<template>
  <div class="frame wrap">
    <div class="sub-title">画面</div>
    <SubtitleBack />
    <SubtitleeVideoBack v-if="target_ratio === '9:16' " />
  </div>
</template>

<script>
import SubtitleBack from './components/subtitleBack'
import SubtitleeVideoBack from './components/subtitleeVideoback'
import { mapState } from 'vuex'

export default {
  components: {
    SubtitleBack, SubtitleeVideoBack
  },
  props: {

  },
  data() {
    return {
      isHidden: ''
    }
  },
  computed: {
    ...mapState('jianshi', ['target_ratio', 'subTitleSingle'])
  },
  watch: {
    subTitleSingle: {
      handler(newName, oldName) {
        const { video_width, video_height } = newName
        const hidden = video_width > video_height ? '16:9' : '9:16'
        this.isHidden = hidden
      },
      deep: true,
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style  lang="scss">

</style>
