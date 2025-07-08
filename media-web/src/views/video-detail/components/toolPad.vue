<!--
 * @Author: your name
 * @Date: 2021-07-29 11:03:05
 * @LastEditTime: 2021-09-27 12:04:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/video-detail/components/pad.vue
-->
<template>
  <div class="tool-pad">
    <el-tabs v-model="activeName">
      <el-tab-pane name="first">
        <span slot="label"> <svg-icon icon-class="信息" class="svg" />视频信息</span>
        <Information />
      </el-tab-pane>
      <el-tab-pane v-if="videoData.vca_status===1" name="second">
        <span slot="label"> <svg-icon icon-class="识别" class="svg" />识别信息</span>
        <Tools />
      </el-tab-pane>
      <!-- <el-tab-pane name="third">
        <span slot="label"><i class="el-icon-time" /> 操作记录</span>
        <Record />
      </el-tab-pane> -->
    </el-tabs>
  </div>
</template>

<script>
import Tools from './tools'
import Information from './information'
import Record from './record'
export default {
  components: {
    Tools,
    Information
    // Record
  },
  props: {

  },
  data() {
    return {
      activeName: 'first'
    }
  },
  computed: {
    videoData() {
      return this.$store.state.videoStatus.videoData
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.tool-pad {
  flex-shrink: 0;
  width: 464px;
  max-height: 88vh;
  height: 950px;
  background: #fff;
  margin-left: 24px;
  padding: 20px;
  // overflow: auto;
  .svg {
    margin-right: 5px;

    ::v-deep svg {

      .icon-信息_icon {
        stroke: red
      }
    }

  }
}
</style>
