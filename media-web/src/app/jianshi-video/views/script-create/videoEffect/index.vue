<!--
 * @Author: your name
 * @Date: 2021-07-21 19:09:27
 * @LastEditTime: 2021-09-15 19:44:15
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/index.vue
-->
<template>
  <div class="video-effect">
    <div class="top-title">视频效果选项</div>
    <el-form v-if="!loading" ref="form" label-width="110px" label-position="left" :model="form">
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
      form: {},
      loading: false
    }
  },
  computed: {
    ...mapState('jianshi', ['effectData', 'materialData', 'material_tabname'])
  },
  watch: {
    materialData(val) {
      this.initUpData(val)
    }
  },
  created() {
    // this.getInitData()
  },
  mounted() {
    this.$bus.$on('resetData', this.upData)
  },
  beforeDestroy() {
    this.$bus.$off('resetData', this.upData)
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
    },
    // 更新数据
    upData() {
      this.loading = true
      this.getInitData()
      this.loading = false
    },
    // init初始化数据
    getInitData() {
      // this.loading = true
      this.$get('intelligent-creation/list-of-options')
        .then(res => {
          if (res.data.code === '0000') {
            this.loading = false
            const data = res.data.data
            // 存入store
            this.$store.commit('jianshi/SET_INItDATA', data)
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .finally(() => {
          console.log('skjbvsjhvbn')
          this.loading = false
        })
    }
  }
}
</script>

<style  lang="scss">
@import "../videoEffect/css/comment.scss";

@import "../videoEffect/css/index.scss"
</style>
